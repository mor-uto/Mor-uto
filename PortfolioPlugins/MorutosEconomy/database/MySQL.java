package net.moruto.economy.database;

import net.moruto.economy.MorutosEconomy;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class MySQL {
    private Connection connection;
    private final String host, port, database, username, password;

    public MySQL() {
        host = MorutosEconomy.getInstance().getConfigManager().getHost();
        port = MorutosEconomy.getInstance().getConfigManager().getPort();
        database = MorutosEconomy.getInstance().getConfigManager().getDatabase();
        username = MorutosEconomy.getInstance().getConfigManager().getUsername();
        password = MorutosEconomy.getInstance().getConfigManager().getPassword();
    }

    public void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);

                try (PreparedStatement statement = connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS player_data (uuid VARCHAR(36) PRIMARY KEY, balance DOUBLE)")) {
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error connecting to MySQL: " + e.getMessage());
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
        if (!isConnected()) connect();

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
        if (!isConnected()) connect();

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
