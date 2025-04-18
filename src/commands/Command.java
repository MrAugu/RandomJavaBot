package commands;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class Command {
    String name;
    String description;

    public Command (
            String commandName,
            String commandDescription
    ) {
        this.name = commandName;
        this.description = commandDescription;
    }

    public abstract SlashCommandData buildOptions ();
}
