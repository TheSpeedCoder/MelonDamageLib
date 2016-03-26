package com.turqmelon.MelonDamageLib.listeners;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.api.EntityDamagedByPlayerTNTEvent;
import com.turqmelon.MelonDamageLib.damage.*;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event){

        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)){
            return;
        }

        DamageTick item = null;
        double dmg = event.getFinalDamage();

        if (event.isCancelled() || dmg == 0)return;


        if ((event instanceof EntityDamageByEntityEvent)){
            EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent)event;

            Entity damager = evt.getDamager();

            LivingEntity attacker = null;
            double distance = 0;

            if ((damager instanceof LivingEntity)){
                attacker = (LivingEntity)damager;
            }
            else if ((damager instanceof Projectile)){
                Projectile projectile = (Projectile)damager;
                if ((projectile.getShooter() instanceof LivingEntity)){
                    attacker = (LivingEntity) projectile.getShooter();
                    distance = attacker.getLocation().distance(entity.getLocation());
                }

            }
            else if ((damager instanceof TNTPrimed)){
                TNTPrimed tnt = (TNTPrimed) damager;
                if (tnt.getSource() != null && ((tnt.getSource() instanceof Player)) && !tnt.getSource().getUniqueId().equals(entity.getUniqueId())) {

                    EntityDamagedByPlayerTNTEvent e = new EntityDamagedByPlayerTNTEvent((Player) tnt.getSource(), entity, tnt, dmg);
                    Bukkit.getPluginManager().callEvent(e);
                    if (e.isCancelled()) {
                        event.setCancelled(true);
                        return;
                    }
                    item = new TNTDamageTick(dmg, "TNT", System.currentTimeMillis(), (Player) tnt.getSource(), tnt.getLocation());
                } else {
                    item = new BlockDamageTick(dmg, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, "TNT", System.currentTimeMillis(), Material.TNT, damager.getLocation());
                }
            }

            if (attacker != null){
                if ((attacker instanceof Player)){
                    if (distance > 0){
                        item = new PlayerDamageTick(dmg, "PVP", System.currentTimeMillis(), (Player)attacker, distance);
                    }
                    else{
                        item = new PlayerDamageTick(dmg, "PVP", System.currentTimeMillis(), (Player)attacker);
                    }
                }
                else{
                    if (distance > 0){
                        item = new MonsterDamageTick(dmg, "PVE", System.currentTimeMillis(), attacker, distance);
                    }
                    else{
                        item = new MonsterDamageTick(dmg, "PVE", System.currentTimeMillis(), attacker);
                    }
                }
            }

        }
        else if ((event instanceof EntityDamageByBlockEvent)){
            EntityDamageByBlockEvent evt = (EntityDamageByBlockEvent)event;
            item = new BlockDamageTick(dmg, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, "BLOCK", System.currentTimeMillis(), evt.getDamager().getType(), evt.getDamager().getLocation());
        }
        else{

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL){
                item = new FallDamageTick(dmg, "Fall", System.currentTimeMillis(), entity.getFallDistance());
            }
            else{

                EntityDamageEvent.DamageCause cause = event.getCause();
                if (cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                    cause = EntityDamageEvent.DamageCause.FIRE;
                }

                String name = cause.name();
                name = WordUtils.capitalizeFully(name).replace("_", " ");

                item = new OtherDamageTick(dmg, cause, name, System.currentTimeMillis());
            }

        }

        if (item != null){
            DamageManager.logTick(entity.getUniqueId(), item);
        }
    }

}
