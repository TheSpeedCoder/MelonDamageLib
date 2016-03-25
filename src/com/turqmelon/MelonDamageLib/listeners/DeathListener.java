package com.turqmelon.MelonDamageLib.listeners;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.DamageLib;
import com.turqmelon.MelonDamageLib.api.PlayerAssistedWithDeathEvent;
import com.turqmelon.MelonDamageLib.damage.DamageManager;
import com.turqmelon.MelonDamageLib.damage.DamageTick;
import com.turqmelon.MelonDamageLib.damage.KillAssist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        LivingEntity entity = event.getEntity();
        if ((event instanceof PlayerDeathEvent)){
            ((PlayerDeathEvent) event).setDeathMessage(null);
            Player player = (Player)entity;
            List<DamageTick> ticks = DamageManager.getLoggedTicks(player.getUniqueId());
            List<String> summary = DamageManager.getDamageSummary(ticks);

            if (summary.size() > 0){
                player.sendMessage(DamageLib.PUNCTUATION_COLOR + "=+=+=+=+=+=[ " + DamageLib.ACCENT_COLOR + ChatColor.BOLD + " HOW YOU DIED " + DamageLib.PUNCTUATION_COLOR + "]=+=+=+=+=+=");
                for(String msg : summary){
                    player.sendMessage(msg);
                }
                player.sendMessage(ChatColor.BOLD+""); // Sends a blank line
                int more = ticks.size()-1;
                List<KillAssist> assists = DamageManager.getPossibleAssists(ticks);

                ((PlayerDeathEvent) event).setDeathMessage(ticks.get(ticks.size()-1).getDeathMessage(player));

                if (assists.size() > 0){
                    String assistText = DamageLib.BASE_COLOR + ", assisted by ";
                    List<String> names = new ArrayList<>();
                    int morePlayers = 0;
                    for(KillAssist assist : assists){
                        PlayerAssistedWithDeathEvent e = new PlayerAssistedWithDeathEvent(assist, player);
                        Bukkit.getPluginManager().callEvent(e);
                        if (e.isCancelled()){
                            continue;
                        }
                        if (names.size() >= 3){
                            morePlayers++;
                            continue;
                        }
                        names.add(DamageLib.ACCENT_COLOR + assist.getAttacker().getDisplayName() + DamageLib.BASE_COLOR);
                    }
                    assistText+=names.toString().replace("[", "").replace("]", "");
                    if (morePlayers > 0){
                        assistText+=" + " + morePlayers + " other player" + (morePlayers!=1?"s":"");
                    }
                    ((PlayerDeathEvent) event).setDeathMessage(((PlayerDeathEvent) event).getDeathMessage()+assistText);
                }
                else if (more > 0){
                    ((PlayerDeathEvent) event).setDeathMessage(((PlayerDeathEvent) event).getDeathMessage() + DamageLib.BASE_COLOR + " + " + more + " more");
                }

            }

        }
        DamageManager.dump(entity.getUniqueId());
    }

}
