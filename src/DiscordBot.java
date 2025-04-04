import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DiscordBot extends ListenerAdapter {
    public static void main(String[] args) {
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



    }
}