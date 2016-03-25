package com.turqmelon.MelonDamageLib.damage;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.DamageLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class BlockDamageTick extends DamageTick {

    private Material type;
    private Location location;

    public BlockDamageTick(double damage, EntityDamageEvent.DamageCause cause, String name, long timestamp, Material type, Location location) {
        super(damage, cause, name, timestamp);
        this.type = type;
        this.location = location;
    }

    public Material getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String getDeathMessage(Player player) {
        return DamageLib.ACCENT_COLOR + player.getDisplayName() + DamageLib.BASE_COLOR + " was killed by " + DamageLib.ACCENT_COLOR + getType().name().replace("_", " ");
    }

    @Override
    public String getSingleLineSummary() {
        return DamageLib.BASE_COLOR + "Hurt by " + DamageLib.ACCENT_COLOR + getType().name().replace("_", " ");
    }
}
