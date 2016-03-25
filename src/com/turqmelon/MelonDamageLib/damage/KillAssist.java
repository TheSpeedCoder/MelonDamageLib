package com.turqmelon.MelonDamageLib.damage;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import org.bukkit.entity.Player;

public class KillAssist implements Comparable {

    private Player attacker;
    private double damage;
    private int percentage;

    public KillAssist(Player attacker, double damage, int percentage) {
        this.attacker = attacker;
        this.damage = damage;
        this.percentage = percentage;
    }

    public Player getAttacker() {
        return attacker;
    }

    public double getDamage() {
        return damage;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public int compareTo(Object o) {
        if ((o instanceof KillAssist)){
            KillAssist assist = (KillAssist)o;
            if (assist.getPercentage() > getPercentage()){
                return -1;
            }
            else if (assist.getPercentage() < getPercentage()){
                return 1;
            }
        }
        return 0;
    }
}
