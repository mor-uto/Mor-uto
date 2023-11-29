package net.moruto.guns.listeners.listener;

import net.moruto.guns.Guns;
import net.moruto.guns.listeners.GunListener;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ListenersManager {
    public ArrayList<Listener> listeners = new ArrayList<>();

    public ListenersManager() {
        listeners.add(new GunListener());

        for (Listener listener : listeners) {
            Guns.getInstance().getServer().getPluginManager().registerEvents(listener, Guns.getInstance());
        }
    }
}
