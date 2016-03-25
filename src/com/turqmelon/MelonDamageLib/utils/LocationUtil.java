package com.turqmelon.MelonDamageLib.utils;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import org.bukkit.Location;

public class LocationUtil {

    public static boolean samePlace(Location loc1, Location loc2){
        return loc1.getWorld().getName().equals(loc2.getWorld().getName()) &&
                loc1.getBlockX() == loc2.getBlockX() &&
                loc1.getBlockY() == loc2.getBlockY() &&
                loc1.getBlockZ() == loc2.getBlockZ();
    }

}
