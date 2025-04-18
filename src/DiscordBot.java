import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.data.DataType;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import commands.HelpCommand;
import commands.Command;

public class DiscordBot extends ListenerAdapter {

    public static void main(String[] args) {
        Command[] commandInstances = new Command[1];

        JDA client = JDABuilder.createLight(System.getenv("TOKEN"), EnumSet.noneOf(GatewayIntent.class))
                .addEventListeners(new DiscordBot())
                .build();

        Connection connection;

        try {
            connection = DriverManager.getConnection(System.getenv("DATABASE_URL"), System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"));
            System.out.println("Connected to the database!");
        } catch (SQLException exception) {
            System.out.println("Yikes, db! " + exception.getMessage());
            System.out.println("Cannot start, shutting down!");
            return;
        }

        CommandListUpdateAction commandList = client.updateCommands();

        HelpCommand helpCommand = new HelpCommand();
        commandList.addCommands(helpCommand.buildOptions());
        commandInstances[0] = helpCommand;
        commandList.queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;
        String commandName = event.getFullCommandName();

        if (commandName.equals("cat")) {
            event.deferReply().queue();
        } else if (commandName.equals("say")) {
            event.reply(Objects.requireNonNull(event.getOption("content")).getAsString()).queue();
        }

    }
}