package com.turqmelon.MelonDamageLib.api;

/******************************************************************************
 * Copyright (c) 2016.  Written by Devon "Turqmelon": http://turqmelon.com    *
 * For more information, see LICENSE.TXT.                                     *
 ******************************************************************************/

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityDamagedByPlayerTNTEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player demoman;
    private Entity entity;
    private TNTPrimed tnt;
    private double finalDamage;

    public EntityDamagedByPlayerTNTEvent(Player demoman, Entity entity, TNTPrimed tnt, double finalDamage) {
        this.demoman = demoman;
        this.entity = entity;
        this.tnt = tnt;
        this.finalDamage = finalDamage;
    }

    private boolean cancelled = false;

    public Player getDemoman() {
        return demoman;
    }

    public Entity getEntity() {
        return entity;
    }

    public TNTPrimed getTnt() {
        return tnt;
    }

    public double getFinalDamage() {
        return finalDamage;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
