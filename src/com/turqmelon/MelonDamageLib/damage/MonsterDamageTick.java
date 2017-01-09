package com.turqmelon.MelonDamageLib.damage;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.DamageLib;
import com.turqmelon.MelonDamageLib.utils.EntityUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.DecimalFormat;
import java.util.UUID;

public class MonsterDamageTick extends DamageTick {

    //private LivingEntity entity;
    private UUID entityUUID;
    private String entityName;
    private double distance;
    private boolean ranged = false;

    public MonsterDamageTick(double damage, String name, long timestamp, LivingEntity entity) {
        super(damage, EntityDamageEvent.DamageCause.ENTITY_ATTACK, name, timestamp);
        //this.entity = entity;
        this.entityUUID = entity.getUniqueId();
        this.entityName = EntityUtil.getEntityName(entity);
    }

    public MonsterDamageTick(double damage, String name, long timestamp, LivingEntity entity, double distance) {
        super(damage, EntityDamageEvent.DamageCause.ENTITY_ATTACK, name, timestamp);
        //this.entity = entity;
        this.entityUUID = entity.getUniqueId();
        this.entityName = EntityUtil.getEntityName(entity);
        this.distance = distance;
        this.ranged = true;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isRanged() {
        return ranged;
    }

    protected String getMessageTemplate(){
        if (isRanged()){
            DecimalFormat df = new DecimalFormat("#.#");
            return DamageLib.BASE_COLOR + "Shot by " + DamageLib.ACCENT_COLOR + "{ATTACKER}" + DamageLib.BASE_COLOR + " from "+
                    DamageLib.ACCENT_COLOR + df.format(getDistance()) + "m " + DamageLib.BASE_COLOR + "away";
        }
        else{
            return DamageLib.BASE_COLOR + "Attacked by " + DamageLib.ACCENT_COLOR +  "{ATTACKER}";

        }
    }

    public UUID getEntityUUID() {
        return entityUUID;
    }

    public String getEntityName() {
        return entityName;
    }

    protected String getDeathMessageTemplate(Player player){
        if (isRanged()){
            DecimalFormat df = new DecimalFormat("#.#");
            return DamageLib.ACCENT_COLOR + player.getDisplayName() + DamageLib.BASE_COLOR + " was killed by " + DamageLib.ACCENT_COLOR + "{KILLER}" + DamageLib.BASE_COLOR + " from "+
                    DamageLib.ACCENT_COLOR + df.format(getDistance()) + "m " + DamageLib.BASE_COLOR + "away";
        }
        else{
            return DamageLib.ACCENT_COLOR + player.getDisplayName() + DamageLib.BASE_COLOR + " was killed by " + DamageLib.ACCENT_COLOR +  "{KILLER}";

        }
    }

    @Override
    public boolean matches(DamageTick tick) {
        return (tick instanceof MonsterDamageTick) && this.entityUUID.equals(((MonsterDamageTick) tick).getEntityUUID());
    }

    @Override
    public String getDeathMessage(Player player) {
        return getDeathMessageTemplate(player).replace("{KILLER}", this.entityName);

    }

    @Override
    public String getSingleLineSummary() {
        return getMessageTemplate().replace("{ATTACKER}", this.entityName);
    }
}
