package commands;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class HelpCommand extends Command {
    public HelpCommand () {
        super("help", "A command showcasing bot's menu.");
    }

    @Override
    public SlashCommandData buildOptions() {
        return Commands.slash(this.name, this.description);
    }
}
