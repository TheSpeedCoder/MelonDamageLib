package com.turqmelon.MelonDamageLib.listeners;

import com.turqmelon.MelonDamageLib.damage.DamageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListner implements Listener {

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        DamageManager.dump(event.getPlayer().getUniqueId());
    }
}
