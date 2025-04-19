package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

public abstract class Command {
    public String name;
    public String description;

    public Command (
            String commandName,
            String commandDescription
    ) {
        this.name = commandName;
        this.description = commandDescription;
    }

    public abstract SlashCommandData buildOptions ();
    public abstract void runSlashInteraction (@NotNull SlashCommandInteractionEvent event, Connection databaseConnection);
}
