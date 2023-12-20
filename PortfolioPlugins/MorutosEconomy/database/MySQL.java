package net.moruto.economy.database;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class MySQL {
    private Connection connection;

    public void connect() {
        if (!isConnected()) {
            try {
                final String host = "localhost", port = "3306", database = "normalDatabase", username = "Moruto", password = "whatever";
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);

                try (PreparedStatement statement = connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS player_data (uuid VARCHAR(36) PRIMARY KEY, balance DOUBLE)")) {
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void savePlayerData(HashMap<UUID, Double> playerData) {
        try {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO player_data (uuid, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = VALUES(balance)")) {

                for (UUID uuid : playerData.keySet()) {
                    statement.setString(1, uuid.toString());
                    statement.setDouble(2, playerData.get(uuid));
                    statement.addBatch();
                }

                statement.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<UUID, Double> loadPlayerData() {
        HashMap<UUID, Double> playerData = new HashMap<>();

        try {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_data")) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                    double balance = resultSet.getDouble("balance");

                    playerData.put(uuid, balance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerData;
    }
}
