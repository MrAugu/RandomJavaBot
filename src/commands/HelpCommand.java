package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

import configuration.Colors;

public class HelpCommand extends Command {
    public HelpCommand () {
        super("help", "A command showcasing bot's menu.");
    }

    @Override
    public SlashCommandData buildOptions () {
        return Commands.slash(this.name, this.description);
    }

    @Override
    public void runSlashInteraction (@NotNull SlashCommandInteractionEvent event, Connection databaseConnection) {
        User invoker = event.getUser();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setAuthor(invoker.getEffectiveName(), null, invoker.getEffectiveAvatarUrl())
                .addField("Utility Commands", "`/help` - Shows a list of commands", false)
                .setColor(Colors.ORANGE);
        System.out.println(invoker.getId().length());
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
