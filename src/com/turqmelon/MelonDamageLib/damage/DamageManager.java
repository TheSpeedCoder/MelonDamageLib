package com.turqmelon.MelonDamageLib.damage;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import com.turqmelon.MelonDamageLib.DamageLib;
import com.turqmelon.MelonDamageLib.utils.LocationUtil;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.*;

public class DamageManager {

    private static Map<UUID, List<DamageTick>> damageTicks = new HashMap<>();

    public static void dump(UUID uuid){
        if (getDamageTicks().containsKey(uuid)){
            getDamageTicks().remove(uuid);
        }
    }

    public static List<KillAssist> getPossibleAssists(List<DamageTick> ticks){
        if (ticks.size() == 0) return new ArrayList<>();
        List<KillAssist> assists = new ArrayList<>();
        List<PlayerDamageTick> playerDamage = new ArrayList<>();

        PlayerDamageTick killingTick = null;
        DamageTick lastTick = ticks.get(ticks.size()-1);
        if ((lastTick instanceof PlayerDamageTick)){
            killingTick = (PlayerDamageTick)lastTick;
        }

        for(DamageTick tick : ticks){
            if ((tick instanceof PlayerDamageTick)){
                PlayerDamageTick pt = (PlayerDamageTick)tick;
                if (killingTick != null && pt.getPlayer().getUniqueId().equals(killingTick.getPlayer().getUniqueId()))
                    continue;
                playerDamage.add(pt);
            }
        }


        double totaldmg = 0;
        for(PlayerDamageTick tick : playerDamage){
            totaldmg+=tick.getDamage();
        }

        for(PlayerDamageTick tick : playerDamage){
            double dmg = tick.getDamage();
            int perc = (int) ((dmg/totaldmg)*100);
            if (perc >= DamageLib.ASSIST_PERCENTAGE_THRESHOLD){
                assists.add(new KillAssist(tick.getPlayer(), dmg, perc));
            }
        }

        Collections.sort(assists);
        return assists;
    }

    public static List<String> getDamageSummary(List<DamageTick> ticks){
        List<String> messages = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.#");

        for(DamageTick tick : ticks){
            messages.add(DamageLib.PUNCTUATION_COLOR + " - " + DamageLib.ACCENT_COLOR + ChatColor.BOLD + df.format(tick.getDamage()) + " DMG" +
            DamageLib.PUNCTUATION_COLOR + ": " + tick.getSingleLineSummary() + DamageLib.PUNCTUATION_COLOR + " (" + tick.timeDiff() + ")");
        }

        return messages;
    }

    public static void logTick(UUID uuid, DamageTick tick){
        DamageTick logged = getLoggedTick(uuid, tick);
        if (logged != null){
            logged.setDamage(logged.getDamage()+tick.getDamage());
            logged.setTimestamp(System.currentTimeMillis());
        }
        else{
            List<DamageTick> ticks = getLoggedTicks(uuid);
            ticks.add(tick);
            getDamageTicks().put(uuid, ticks);
        }
    }

    public static DamageTick getLoggedTick(UUID uuid, DamageTick newTick){
        for(DamageTick tick : getLoggedTicks(uuid)){
            if (tick.getCause() == newTick.getCause()){
                if ((newTick instanceof FallDamageTick) && (tick instanceof FallDamageTick)){
                }
                else if ((newTick instanceof MonsterDamageTick) && (tick instanceof MonsterDamageTick)){
                    if (((MonsterDamageTick) newTick).getEntity().getUniqueId().equals(((MonsterDamageTick) tick).getEntity().getUniqueId())){
                        return tick;
                    }
                }
                else if ((newTick instanceof BlockDamageTick) && (tick instanceof BlockDamageTick)){
                    if (((BlockDamageTick) newTick).getType() == ((BlockDamageTick) tick).getType() &&
                            LocationUtil.samePlace(((BlockDamageTick) newTick).getLocation(), ((BlockDamageTick) tick).getLocation())){
                        return tick;
                    }
                }
                else{
                    return tick;
                }
            }
        }
        return null;
    }

    public static List<DamageTick> getLoggedTicks(UUID uuid) {
        return getDamageTicks().containsKey(uuid) ?
                cleanup(getDamageTicks().get(uuid)) :
                new ArrayList<>();
    }

    private static List<DamageTick> cleanup(List<DamageTick> ticks){
        for(int i = 0; i < ticks.size(); i++){
            DamageTick tick = ticks.get(i);
            if (System.currentTimeMillis()-tick.getTimestamp() > DamageLib.DAMAGE_TIMEOUT){
                ticks.remove(tick);
            }
        }
        Collections.sort(ticks);
        return ticks;
    }

    private static Map<UUID, List<DamageTick>> getDamageTicks() {
        return damageTicks;
    }
}
