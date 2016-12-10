package de.zbs.witherwarp.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WitherWarpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cs instanceof Player) {
			final Player p = (Player) cs;
			if (label.equalsIgnoreCase("witherwarp")) {
				if (p.hasPermission("witherwarp.info")) {
		            p.sendMessage(WitherWarp.line);
					p.sendMessage(ChatColor.AQUA + "        " + ChatColor.BOLD + WitherWarp.plugin.getDescription().getName() + ChatColor.RESET + ChatColor.GRAY + "v" + WitherWarp.plugin.getDescription().getVersion() + " by " + ChatColor.BLUE + WitherWarp.plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
					boolean warp = true;
					boolean delwarp = true;
					boolean setwarp = true;
					if (p.hasPermission("witherwarp.warp")) {
						p.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "warp/warps " + ChatColor.GRAY + WitherWarp.msg.get("help.warp"));
						warp = false;
					}
					if (p.hasPermission("witherwarp.delwarp")) {
						p.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "delwarp " + ChatColor.GRAY + WitherWarp.msg.get("help.delwarp"));
						delwarp = false;
					}
					if (p.hasPermission("witherwarp.setwarp")) {
						p.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.DARK_AQUA + "setwarp " + ChatColor.GRAY + WitherWarp.msg.get("help.setwarp"));
						setwarp = false;
					}
					if ((warp) && (setwarp) && (delwarp)) {
						p.sendMessage(WitherWarp.msg.get("prefix.info") + ChatColor.GRAY + WitherWarp.msg.get("help.nopermission"));
					}
		            p.sendMessage(WitherWarp.line);
				} else {
					p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.nopermission"));
				}
			} else if (label.equalsIgnoreCase("getwarpclock")) {
				if (p.hasPermission("witherwarp.clock")) {
					ItemStack cc = new ItemStack(Material.getMaterial(WitherWarp.msg.get("item.material")));
					ItemMeta im = cc.getItemMeta();
					im.setDisplayName(WitherWarp.msg.get("item.name"));
					List<String> lore = new ArrayList<String>();
					String[] desc = WitherWarp.msg.get("item.description").split("\n");
					for (int i = 0; i < desc.length - 1; i++) {
						lore.add(desc[i]);
					}
					im.setLore(lore);
					cc.setItemMeta(im);
					p.getInventory().addItem(cc);
				} else {
					p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.nopermission"));
				}
			} else if (label.equalsIgnoreCase("warp")) {
				WarpInventory.open(p);
			} else if (label.equalsIgnoreCase("warps")) {
				WarpInventory.open(p);
			} else if (label.equalsIgnoreCase("delwarp")) {
				if (p.hasPermission("witherwarp.delwarp")) {
					//TODO
//					if (args.length == 0) {
//						p.sendMessage(WitherWarp.msg.get("prefix.info") + "Zu wenig Argumente");
//						p.sendMessage(WitherWarp.msg.get("prefix.info") + "/delwarp <slotnummer>");
//					} else if (args.length == 1) {
//						int slot = Integer.parseInt(args[0]);
//						Warp.getWarp(slot).delete();
//					} else {
//						p.sendMessage(WitherWarp.msg.get("prefix.info") + "Zu viele Argumente");
//						p.sendMessage(WitherWarp.msg.get("prefix.info") + "/delwarp <slotnummer>");
//					}
				} else {
					p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.nopermission"));
				}
			} else if (label.equalsIgnoreCase("editwarp")) {
				if (p.hasPermission("witherwarp.setwarp")) {
					if (args.length == 0) {
						p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.toofewarguments"));
						p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp <slot> <type> <parameter>");
					} else if (args.length == 3) {
						if (NumberUtils.isNumber(args[0])) {
							Warp w = Warp.getWarp(Integer.parseInt(args[0]));
							if (args[1].equalsIgnoreCase("name")) {
								w.setName(args[2]);
								p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.name"));
							} else if (args[1].equalsIgnoreCase("locx")) {
								if (NumberUtils.isNumber(args[2])) {
									w.setX(Double.parseDouble(args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.location"));
								} else {
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.number").replace("{NUMBER}", args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp " + args[0] + " locx <location-x>");
								}
							} else if (args[1].equalsIgnoreCase("locy")) {
								if (NumberUtils.isNumber(args[2])) {
									w.setY(Double.parseDouble(args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.location"));
								} else {
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.number").replace("{NUMBER}", args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp " + args[0] + " locy <location-y>");
								}
							} else if (args[1].equalsIgnoreCase("locz")) {
								if (NumberUtils.isNumber(args[2])) {
									w.setZ(Double.parseDouble(args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.location"));
								} else {
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.number").replace("{NUMBER}", args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp " + args[0] + " locz <location-z>");
								}
							} else if (args[1].equalsIgnoreCase("locyaw")) {
								if (NumberUtils.isNumber(args[2])) {
									w.setYaw(Float.parseFloat(args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.location"));
								} else {
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.number").replace("{NUMBER}", args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp " + args[0] + " locyaw <location-yaw>");
								}
							} else if (args[1].equalsIgnoreCase("locpitch")) {
								if (NumberUtils.isNumber(args[2])) {
									w.setPitch(Float.parseFloat(args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.location"));
								} else {
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.number").replace("{NUMBER}", args[2]));
									p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp " + args[0] + " locpitch <location-pitch>");
								}
							} else if (args[1].equalsIgnoreCase("alias")) {
								if (w.getAlias().contains(args[2])) {
									w.removeAlias(args[2]);
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.alias.removed"));
								} else {
									w.addAlias(args[2]);
									p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("warp.editwarp.alias.create"));
								}
							} else {
								p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.subcommand"));
								p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp <slot> <type> <parameter>");
							}
						} else {
							p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.number").replace("{NUMBER}", args[0]));
							p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp <slot> <type> <parameter>");
						}
					} else {
						p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.toofewarguments"));
						p.sendMessage(WitherWarp.msg.get("prefix.error") + "/editwarp <slot> <type> <parameter>");
					}
				} else {
					p.sendMessage(WitherWarp.msg.get("prefix.info") + WitherWarp.msg.get("error.nopermission"));
				}
			} else if (label.equalsIgnoreCase("setwarp")) {
				if (p.hasPermission("witherwarp.setwarp")) {
					if (args.length == 0) {
						p.sendMessage(WitherWarp.msg.get("prefix.error") + WitherWarp.msg.get("error.toofewarguments"));
						p.sendMessage(WitherWarp.msg.get("prefix.error") + "/setwarp <slot>");
					} else if (args.length == 1) {
						if (p.getItemOnCursor() == null || p.getItemOnCursor().getType().equals(Material.AIR) || p.getItemOnCursor().getItemMeta().getDisplayName() == null || (!p.getItemOnCursor().hasItemMeta()) || (!p.getItemOnCursor().getItemMeta().hasDisplayName())) {
							p.sendMessage(WitherWarp.msg.get("prefix.info") + WitherWarp.msg.get("warp.howto"));
						} else if (p.getItemOnCursor().getType().equals(Material.POTION)) {
							p.sendMessage(WitherWarp.msg.get("prefix.info") + WitherWarp.msg.get("warp.potions"));
						} else {
							if (Integer.parseInt(args[0]) >= 54) {
								p.sendMessage(WitherWarp.msg.get("prefix.info") + WitherWarp.msg.get("warp.higherthan53"));
							} else {
								Warp w = new Warp(p.getLocation(), p.getItemOnCursor(), Integer.parseInt(args[0]));
								p.sendMessage(ChatColor.YELLOW + WitherWarp.msg.get("warp.newwarp"));
								String b = ChatColor.DARK_GRAY + "- " + ChatColor.DARK_AQUA + "";
								p.sendMessage(b + "Name: " + ChatColor.GRAY + w.getName());
								p.sendMessage(b + "Description: " + ChatColor.GRAY + w.getDescription().toString().replace("]", ChatColor.GRAY + "]"));
	/* 							p.sendMessage(b + "Description:");
								for (String s : w.getDescription()) {
									p.sendMessage(bb + ChatColor.GRAY + s);
								}*/
								p.sendMessage(b + "Material: " + ChatColor.GRAY + w.getMaterial().name());
								p.sendMessage(b + "Enchanted: " + ChatColor.GRAY + w.isEnchanted());
								p.sendMessage(b + "World: " + ChatColor.GRAY + Bukkit.getWorld(UUID.fromString(w.getWorldUUID())).getName());
								p.sendMessage(b + "X: " + ChatColor.GRAY + w.getX());
								p.sendMessage(b + "Y: " + ChatColor.GRAY + w.getY());
								p.sendMessage(b + "Z: " + ChatColor.GRAY + w.getZ());
								p.sendMessage(b + "Yaw: " + ChatColor.GRAY + w.getYaw());
								p.sendMessage(b + "Pitch: " + ChatColor.GRAY + w.getPitch());
							}
						}
					} else if (args.length >= 2) {
						p.sendMessage(WitherWarp.msg.get("prefix.info") + WitherWarp.msg.get("error.toomanyarguments"));
						p.sendMessage(WitherWarp.msg.get("prefix.info") + "/setwarp <slot>");
					}
				} else {
					p.sendMessage(WitherWarp.msg.get("prefix.info") + WitherWarp.msg.get("error.nopermission"));
				}
			}
		} else {
			cs.sendMessage(WitherWarp.msg.get("prefix.info") + "You need to be a Player!");
	    }
		return true;
	}
}