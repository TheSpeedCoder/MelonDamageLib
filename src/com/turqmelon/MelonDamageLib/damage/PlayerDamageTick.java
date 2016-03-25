package com.turqmelon.MelonDamageLib.damage;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.DamageLib;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class PlayerDamageTick extends MonsterDamageTick {

    public PlayerDamageTick(double damage, String name, long timestamp, Player player) {
        super(damage, name, timestamp, player);
    }

    public PlayerDamageTick(double damage, String name, long timestamp, Player player, double distance) {
        super(damage, name, timestamp, player, distance);
    }

    public Player getPlayer() {
        return (Player)getEntity();
    }

    @Override
    public String getDeathMessage(Player player) {
        DecimalFormat df = new DecimalFormat("#.#");
        return getDeathMessageTemplate(player).replace("{KILLER}", getPlayer().getDisplayName() + DamageLib.PUNCTUATION_COLOR + "(" + DamageLib.ACCENT_COLOR + df.format(getPlayer().getHealth()) + "❤" + DamageLib.PUNCTUATION_COLOR + ")");
    }

    @Override
    public String getSingleLineSummary() {
        DecimalFormat df = new DecimalFormat("#.#");
        return getMessageTemplate().replace("{ATTACKER}", getPlayer().getDisplayName() + DamageLib.PUNCTUATION_COLOR + "(" + DamageLib.ACCENT_COLOR + df.format(getPlayer().getHealth()) + "❤" + DamageLib.PUNCTUATION_COLOR + ")");
    }
}
