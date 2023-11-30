package net.moruto.economy.utils;

import net.moruto.economy.EconomyImplementer;
import net.moruto.economy.MorutosEconomy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StorageManager implements Listener {
    private final ArrayList<File> dataFiles = new ArrayList<>();
    public EconomyImplementer eco;

    public StorageManager(EconomyImplementer eco) {
        this.eco = eco;

        MorutosEconomy.getInstance().getServer().getPluginManager().registerEvents(this, MorutosEconomy.getInstance());
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!eco.hasAccount(player)) {
                eco.createPlayerAccount(player);
            }
            MorutosEconomy.getInstance().playerBank.putIfAbsent(player.getUniqueId(), 0D);

            File file = new File(MorutosEconomy.getInstance().getDataFolder(), "/balances/" + player.getUniqueId() + ".json");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    FileWriter writer = new FileWriter(file);
                    JSONObject data = new JSONObject();
                    data.put(player.getUniqueId(), eco.getBalance(player.getName()));
                    writer.write(data.toJSONString());
                    writer.flush();
                }
            } catch (IOException e) {e.printStackTrace();}
            dataFiles.add(file);
        }
    }

    @EventHandler
    private void loadData(PluginEnableEvent event) {
        if (event.getPlugin() != MorutosEconomy.getInstance()) return;

        try {
            for (File dataFile : dataFiles) {
                FileReader reader = new FileReader(dataFile);
                JSONParser parser = new JSONParser();
                JSONObject data = (JSONObject) parser.parse(reader);

                for (Object key : data.keySet()) {
                    Object valueObject = data.get(key);

                    if (valueObject instanceof Number) {
                        double value = ((Number) valueObject).doubleValue();
                        Player player = Bukkit.getPlayer(UUID.fromString(key.toString().split("=")[0]));

                        if (player != null) {
                            double playerOriginalAmount = eco.getBalance(player.getName());
                            eco.depositPlayer(player.getName(), -playerOriginalAmount);
                            eco.depositPlayer(player.getName(), value);
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    private void saveData(PluginDisableEvent event) {
        if (event.getPlugin() != MorutosEconomy.getInstance()) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (File dataFile : dataFiles) {
                final HashMap<UUID, Double> dataInput = new HashMap<>();
                dataInput.put(player.getUniqueId(), eco.getBalance(player.getName()));
                JSONObject data = new JSONObject(dataInput);

                try (FileWriter writer = new FileWriter(dataFile)) {
                    writer.write(data.toJSONString());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if (!eco.hasAccount(event.getPlayer())) {
            eco.createPlayerAccount(event.getPlayer());
        }
    }
}
