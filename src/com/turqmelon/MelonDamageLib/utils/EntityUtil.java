package com.turqmelon.MelonDamageLib.utils;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.Entity;

public class EntityUtil {

    public static String getEntityName(Entity entity){
        if (entity.getCustomName() != null){
            return entity.getCustomName();
        }
        else{
            String name = entity.getType().name();
            name = name.replace("_", " ");
            return WordUtils.capitalizeFully(name);
        }
    }

}
