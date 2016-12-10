package de.zbs.witherwarp.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pro.tesserakt.zcore.api.config.Messages;

public class WitherWarp extends JavaPlugin {
	
	public static WitherWarp plugin;
	public static ArrayList<String> warping = new ArrayList<String>();
	public static String line = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "------>---------------------------------------<------";
	
	public static Messages msg;

	@Override
	public void onEnable() {
		plugin = this;
		
		if (Bukkit.getPluginManager().getPlugin("EffectLib") == null || !Bukkit.getPluginManager().getPlugin("EffectLib").isEnabled()) {
			getLogger().warning("Install EffectLib first!");
			this.setEnabled(false);
			return;
		} else {
			HashMap<String, String> def = new HashMap<>();
			def.put("prefix.message", "&8Warp &9» &3");
			def.put("prefix.error", "&8Warp &9» &c");

			def.put("error.nopermission", "No Permission!");
			def.put("error.toomanyarguments", "Too many Arguments!");
			def.put("error.toofewarguments", "Too few Arguments!");
			def.put("error.subcommand", "Unknown Subcommand!");
			def.put("error.number", "{NUMBER} needs to be a Number!");
			def.put("help.warp", "Open the Warp-Inventory");
			def.put("help.delwarp", "Delete a Warp");
			def.put("help.setwarp", "Set a new Warp");
			def.put("help.nopermission", "You don't have Permission to use any Commands!");
			def.put("warp.howto", "You need to hold an Item with Metadata. This will be the Warp-Icon.");
			def.put("warp.potions", "Potions are not supported at the Moment!");
			def.put("warp.higherthan53", "The Number you entered is higher than 53!");
			def.put("warp.newwarp", "New Warp created:");
			def.put("warp.editwarp.alias.create", "New Alias created!");
			def.put("warp.editwarp.alias.removed", "Alias successfully removed!");
			def.put("warp.editwarp.location", "Location updated!");
			def.put("warp.editwarp.name", "Name updated!");

			def.put("warping.timeleft", "&3Warp in... &b{timeleft}");
			def.put("warping.dontmove", "Don't Move!");
			def.put("warping.success", "Warp successful!");
			def.put("warping.moved", "You moved!");
			def.put("warping.aborted", "Warp cancelled!");
			def.put("warping.nocommands", "Commands are disabled during Warp!");
			msg = new Messages(this, def);
			
			//Commands
			this.getCommand("witherwarp").setExecutor(new WitherWarpCommand());
			
			//Events
			PluginManager pm = Bukkit.getPluginManager();
			pm.registerEvents(new WarpInventory(), this);
			
			this.getLogger().info("Successfully enabled");
		}
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("Successfully disabled.");
	}
}