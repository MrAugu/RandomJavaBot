import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import commands.HelpCommand;
import commands.Command;

public class DiscordBot extends ListenerAdapter {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
        Command[] commandInstances = new Command[1];
        HashMap<String, Integer> commandInstanceMap = new HashMap<>(1);

        JDA client = JDABuilder.createLight(System.getenv("TOKEN"), EnumSet.noneOf(GatewayIntent.class))
                .addEventListeners(new DiscordBot())
                .build();

        Connection databaseConnection;

        try {
            databaseConnection = DriverManager.getConnection(System.getenv("DATABASE_URL"), System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"));
            System.out.println("[Database]: Connection established.");
        } catch (SQLException exception) {
            System.out.println("[Database]: Cannot start due to being unable to establish a connection. SQL Exception: " + exception.getMessage());
            return;
        }

        CommandListUpdateAction commandList = client.updateCommands();

        HelpCommand helpCommand = new HelpCommand();
        commandInstances[0] = helpCommand;
        commandInstanceMap.put(helpCommand.name, 0);

        for (Command commandInstance : commandInstances) {
            commandList.addCommands(commandInstance.buildOptions());
        }

        commandList.queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;
        String commandName = event.getFullCommandName();
        Command commandToRun = commandInstances


    }
}