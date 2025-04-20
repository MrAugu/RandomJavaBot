import configuration.Settings;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.UserTypingEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.dbcp2.BasicDataSource;

import commands.HelpCommand;
import commands.Command;

import utils.ExperienceUpdate;

public class DiscordBot extends ListenerAdapter {

    private static BasicDataSource dataSource = null;
    private static Command[] commandInstanceList = null;
    private static HashMap<String, Integer> commandInstanceMap = null;

    static {
        dataSource = new BasicDataSource();

        dataSource.setUrl(Settings.databaseUrl);
        dataSource.setUsername(Settings.databaseUsername);
        dataSource.setPassword(Settings.databasePassword);

        dataSource.setMinIdle(Settings.minimumIdleConnections);
        dataSource.setMaxIdle(Settings.maximumIdleConnections);
        dataSource.setMaxTotal(Settings.maximumTotalConnections);

        System.out.println("[Database]: Database connection pool initialised.");

        // Initializing the objects
        commandInstanceList = new Command[1];
        commandInstanceMap = new HashMap<String, Integer>(1);

        // Manually loading every command file
        HelpCommand helpCommand = new HelpCommand();
        commandInstanceList[0] = helpCommand;
        commandInstanceMap.put(helpCommand.name, 0);

        System.out.println("[Commands]: Commands initialised.");
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
        JDA client = JDABuilder.createLight(
                        Settings.token,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_TYPING,
                        GatewayIntent.GUILD_MESSAGE_POLLS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS
                )
                .addEventListeners(new DiscordBot())
                .build();

        CommandListUpdateAction commandList = client.updateCommands();

        for (Command commandInstance : commandInstanceList) {
            commandList.addCommands(commandInstance.buildOptions());
        }

        commandList.queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;

        String commandName = event.getFullCommandName();
        int commandIndex = commandInstanceMap.get(commandName);
        Command commandInstance = commandInstanceList[commandIndex];
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            commandInstance.runSlashInteraction(event, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUserTyping (@NotNull UserTypingEvent event) {
        Guild eventGuild = event.getGuild();
        if (eventGuild == null) return;

        Connection connection = null;
        User eventUser = event.getUser();

        try {
            connection = dataSource.getConnection();
            ExperienceUpdate.giveEventExperience(eventUser, eventGuild, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessageReceived (@NotNull MessageReceivedEvent event) {
        Guild eventGuild = event.getGuild();
        Connection connection = null;
        User eventUser = event.getAuthor();

        try {
            connection = dataSource.getConnection();
            ExperienceUpdate.giveMessageExperience(eventUser, eventGuild, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}