package com.turqmelon.MelonDamageLib.damage;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.DamageLib;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.DecimalFormat;

public class FallDamageTick extends DamageTick {

    private double distance;

    public FallDamageTick(double damage, String name, long timestamp, double distance) {
        super(damage, EntityDamageEvent.DamageCause.FALL, name, timestamp);
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String getDeathMessage(Player player) {
        DecimalFormat df = new DecimalFormat("#");
        return DamageLib.ACCENT_COLOR + player.getDisplayName() + DamageLib.BASE_COLOR + " was killed by " + DamageLib.ACCENT_COLOR + df.format(getDistance()) + DamageLib.BASE_COLOR + " block fall";
    }

    @Override
    public String getSingleLineSummary() {
        DecimalFormat df = new DecimalFormat("#");
        return DamageLib.BASE_COLOR + "Fell " + DamageLib.ACCENT_COLOR + df.format(getDistance()) + DamageLib.BASE_COLOR + " blocks";
    }
}
