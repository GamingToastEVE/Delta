package org.ToastiCodingStuff.Delta;

import java.io.PrintStream;
import java.sql.*;

public class databaseHandler {

    private final Connection connection;
    public databaseHandler() {
        Connection connection1;
        try {
            connection1 = DriverManager.getConnection("jdbc:sqlite:server.db");
        }catch (SQLException e) {
            connection1 = null;
            System.out.println(e);
        }
        this.connection = connection1;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Log Channel Databasekram

    public String getLogChannelID (String guildID) {
        try {
            connection.setAutoCommit(false);
            String hasLogChannel1 = "SELECT channelid FROM log_channels WHERE guildid=?";
            PreparedStatement hasLogChannel2 = connection.prepareStatement(hasLogChannel1);
            hasLogChannel2.setString(1, guildID);
            ResultSet rs = hasLogChannel2.executeQuery();
            String channelid = rs.getString("channelid");
            if (channelid == null || channelid.equals("0")) {
                return "Couldnt find a Log Channel";
            }
            return channelid;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e;
        }
    }

    public boolean hasLogChannel (String guildID) {
        try {
            connection.setAutoCommit(false);
            String hasLogChannel1 = "SELECT channelid FROM log_channels WHERE guildid=?";
            PreparedStatement hasLogChannel2 = connection.prepareStatement(hasLogChannel1);
            hasLogChannel2.setString(1, guildID);
            ResultSet rs = hasLogChannel2.executeQuery();
            String channelid = rs.getString("channelid");
            if (channelid == null || channelid.equals("0")) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String setLogChannel (String guildID, String channelID) {
        try {
            connection.setAutoCommit(false);
            if (hasLogChannel(guildID)) {
                String setLogChannel = "UPDATE log_channels SET channelid=? WHERE guildid=?";
                PreparedStatement updateLogChannel = connection.prepareStatement(setLogChannel);
                updateLogChannel.setString(1, channelID);
                updateLogChannel.setString(2, guildID);
                updateLogChannel.executeUpdate();
                connection.commit();
                return channelID;
            } else {
                String setLogChannel = "INSERT INTO log_channels(guildid, channelid) VALUES(?,?)";
                PreparedStatement updateLogChannel = connection.prepareStatement(setLogChannel);
                updateLogChannel.setString(1, guildID);
                updateLogChannel.setString(2, channelID);
                updateLogChannel.executeUpdate();
                connection.commit();
                return channelID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }


    //Warn System Kram
    public boolean hasWarnSystemSettings (String guildID) {
        try {
            String hasWarnSystemSettingsAndStuff = "SELECT max_warns, minutes_muted, role_id, warn_time_hours FROM warn_system_settings WHERE guild_id=?";
            PreparedStatement hasWarnSystem = connection.prepareStatement(hasWarnSystemSettingsAndStuff);
            hasWarnSystem.setString(1, guildID);
            ResultSet rs = hasWarnSystem.executeQuery();
            if (rs == null) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
