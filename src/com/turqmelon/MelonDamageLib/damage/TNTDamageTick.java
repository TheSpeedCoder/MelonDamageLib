package com.turqmelon.MelonDamageLib.damage;

import com.turqmelon.MelonDamageLib.DamageLib;
import com.turqmelon.MelonDamageLib.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TNTDamageTick extends PlayerDamageTick {

	private Location location;

	public TNTDamageTick(double damage, String name, long timestamp, Player player, Location location) {
		super(damage, name, timestamp, player);
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	@Override
	public boolean matches(DamageTick tick) {
		return (tick instanceof TNTDamageTick) && LocationUtil.samePlace(getLocation(), ((TNTDamageTick) tick).getLocation()) && getPlayer().getUniqueId().equals(((TNTDamageTick) tick).getPlayer().getUniqueId());
	}

	@Override
	public String getDeathMessage(Player player) {
		return DamageLib.ACCENT_COLOR + player.getDisplayName() + DamageLib.BASE_COLOR + " was killed by " + DamageLib.ACCENT_COLOR + getPlayer().getDisplayName() + DamageLib.BASE_COLOR + "'s TNT";
	}

	@Override
	public String getSingleLineSummary() {
		return DamageLib.BASE_COLOR + "Hurt by " + DamageLib.ACCENT_COLOR + getPlayer().getDisplayName() + DamageLib.BASE_COLOR + "'s TNT";
	}

}
