package com.turqmelon.MelonDamageLib.damage;

import com.turqmelon.MelonDamageLib.DamageLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class TNTDamageTick extends DamageTick {

	private Location location;
	private Player placer;

	public TNTDamageTick(double damage, EntityDamageEvent.DamageCause cause, String name, long timestamp, Location location, Player placer) {
		super(damage, cause, name, timestamp);
		this.location = location;
		this.placer = placer;
	}

	public Location getLocation(){
		return location;
	}

	public Player getPlacer(){
		return placer;
	}

	@Override
	public boolean matches(DamageTick tick) {
		return tick instanceof TNTDamageTick && ((TNTDamageTick) tick).getLocation().equals(location) && ((TNTDamageTick) tick).getPlacer().equals(placer);
	}

	@Override
	public String getDeathMessage(Player player) {
		return DamageLib.ACCENT_COLOR + player.getDisplayName() + DamageLib.BASE_COLOR + " was killed by " + placer.getName() + "'s TNT";
	}

	@Override
	public String getSingleLineSummary() {
		return DamageLib.BASE_COLOR + "Hurt by " + DamageLib.ACCENT_COLOR + placer.getName() + "'s TNT";
	}

}
