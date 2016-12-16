package com.turqmelon.MelonDamageLib;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.listeners.ConnectionListner;
import com.turqmelon.MelonDamageLib.listeners.DamageListener;
import com.turqmelon.MelonDamageLib.listeners.DeathListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageLib extends JavaPlugin {

    public static final long DAMAGE_TIMEOUT = 5000;
    public static final int ASSIST_PERCENTAGE_THRESHOLD = 20;
    public static final ChatColor BASE_COLOR = ChatColor.GRAY;
    public static final ChatColor ACCENT_COLOR = ChatColor.DARK_RED;
    public static final ChatColor PUNCTUATION_COLOR = ChatColor.DARK_GRAY;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new ConnectionListner(), this);
    }
}
