package utils;

import configuration.Luck;
import configuration.Settings;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ExperienceUpdate {
    public static void giveEventExperience (User user, Guild guild, Connection databaseConnection) {
        try {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            int experienceGain = (int)((Math.random() * Luck.EXPERIENCE_EVENT_UPPER_BOUND + Luck.EXPERIENCE_EVENT_LOWER_BOUND) * Luck.EXPERIENCE_EVENT_MULTIPLIER);

            final String UPDATE_XP_OR_INSERT_QUERY = "INSERT INTO users (user_id, guild_id, first_seen, balance, experience) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE experience = experience + ?";
            PreparedStatement statement = databaseConnection.prepareStatement(UPDATE_XP_OR_INSERT_QUERY);

            // 1. user_id; 2. guild_id; 3. first_seen; 4. balance; 5. experience; 6. experience gain
            statement.setString(1, user.getId());
            statement.setString(2, guild.getId());
            statement.setTimestamp(3, currentTimestamp);
            statement.setDouble(4, 0);
            statement.setInt(5, experienceGain);
            statement.setInt(6, experienceGain);

            statement.execute();

            if (Settings.isDebugEnabled) System.out.println("[ExperienceUpdate]: Gave event experience. userid=" + user.getId() + "; username=" + user.getGlobalName() + "; guildid=" + guild.getId() + "; amount=" + experienceGain);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void giveMessageExperience (User user, Guild guild, Connection databaseConnection) {
        try {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            int experienceGain = (int)((Math.random() * Luck.EXPERIENCE_MESSAGE_UPPER_BOUND + Luck.EXPERIENCE_MESSAGE_LOWER_BOUND) * Luck.EXPERIENCE_MESSAGE_MULTIPLIER);

            final String UPDATE_XP_OR_INSERT_QUERY = "INSERT INTO users (user_id, guild_id, first_seen, balance, experience) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE experience = experience + ?";
            PreparedStatement statement = databaseConnection.prepareStatement(UPDATE_XP_OR_INSERT_QUERY);

            // 1. user_id; 2. guild_id; 3. first_seen; 4. balance; 5. experience; 6. experience gain
            statement.setString(1, user.getId());
            statement.setString(2, guild.getId());
            statement.setTimestamp(3, currentTimestamp);
            statement.setDouble(4, 0);
            statement.setInt(5, experienceGain);
            statement.setInt(6, experienceGain);

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Giving event experience.");
    }
}
