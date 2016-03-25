package com.turqmelon.MelonDamageLib.damage;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.DamageLib;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class OtherDamageTick extends DamageTick {

    public OtherDamageTick(double damage, EntityDamageEvent.DamageCause cause, String name, long timestamp) {
        super(damage, cause, name, timestamp);
    }

    @Override
    public boolean matches(DamageTick tick) {
        return (tick instanceof OtherDamageTick) && tick.getName().equals(getName());
    }

    @Override
    public String getDeathMessage(Player player) {
        return DamageLib.ACCENT_COLOR + player.getDisplayName() + DamageLib.BASE_COLOR + " was killed by " + DamageLib.ACCENT_COLOR + getName();
    }

    @Override
    public String getSingleLineSummary() {
        return DamageLib.ACCENT_COLOR + getName() + DamageLib.BASE_COLOR + " damage";
    }
}
