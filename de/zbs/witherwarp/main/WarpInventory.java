package de.zbs.witherwarp.main;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.WarpEffect;
import de.slikey.effectlib.util.DynamicLocation;
import pro.tesserakt.zcore.api.NMSLoader;
import pro.tesserakt.zcore.main.ZCore;

public class WarpInventory implements Listener {

	@SuppressWarnings("deprecation")
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, "Warp...");
		for (int i = 0; i < 54; i++) {
			if (Warp.getWarp(i) == null) {
				ItemStack ph = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
				ItemMeta phm = ph.getItemMeta();
				if (p.hasPermission("witherwarp.slotnumber")) {
					phm.setDisplayName(ChatColor.RED + "Slot: " + ChatColor.DARK_RED + i);
				} else {
					phm.setDisplayName(ChatColor.AQUA + "");
				}
				ph.setItemMeta(phm);
				inv.setItem(i, ph);
			} else {
				inv.setItem(i, Warp.getItemStack(i));
			}
		}
		p.openInventory(inv);
	}

	EffectManager em = new EffectManager(WitherWarp.plugin);
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getInventory().getName().equalsIgnoreCase("Warp...")) {
				e.setCancelled(true);
				if (!e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
					double x = p.getLocation().getBlockX();
					double y = p.getLocation().getBlockY();
					double z = p.getLocation().getBlockZ();
					
					
					p.closeInventory();
					
					WitherWarp.warping.add(p.getUniqueId().toString());
					
					WarpEffect eff = new WarpEffect(em);
					eff.setDynamicOrigin(new DynamicLocation(p));
					eff.infinite();
					eff.start();
					
					ZCore.getNMS();
					NMSLoader.getTitle().sendTitle(ChatColor.DARK_AQUA + "Warping... " + ChatColor.RED + WitherWarp.msg.get("warping.dontmove"), 5, 50, 5, p);
					
					Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						int timeleft = p.hasPermission("witherwarp.instant") ? 1 : 300;
						public void run() {
							if (WitherWarp.warping.contains(p.getUniqueId().toString())) {
								if (timeleft > 0) {
									if (x == p.getLocation().getBlockX() &&
											y == p.getLocation().getBlockY() &&
											z == p.getLocation().getBlockZ()) {
										NMSLoader.getTitle().sendSubTitle(ChatColor.DARK_GRAY + "[ " + WitherWarp.msg.get("warping.timeleft").replace("{timeleft}", timeleft + "") + ChatColor.DARK_GRAY + " ]", 0, 10, 10, p);
										if (timeleft % 100 == 0) {
											p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
										}
										timeleft--;
									} else {
										eff.cancel();
										timer.cancel();
										p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
										WitherWarp.warping.remove(p.getUniqueId().toString());
										NMSLoader.getTitle().sendTitle(ChatColor.RED + WitherWarp.msg.get("warping.moved"), 5, 30, 10, p);
										NMSLoader.getTitle().sendSubTitle(ChatColor.DARK_GRAY + "[ " + ChatColor.DARK_AQUA + WitherWarp.msg.get("warping.aborted") + ChatColor.DARK_GRAY + " ]", 0, 10, 10, p);
									}
								}
								if (timeleft == 0) {
									p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 10.0F, 10.0F);
									WitherWarp.warping.remove(p.getUniqueId().toString());
									NMSLoader.getTitle().sendTitle(ChatColor.GREEN + WitherWarp.msg.get("warping.success"), 0, 40, 20, p);
									NMSLoader.getTitle().sendSubTitle(ChatColor.DARK_GRAY + "[ " + WitherWarp.msg.get("warping.timeleft").replace("{timeleft}", "0") + ChatColor.DARK_GRAY + " ]", 0, 10, 5, p);
									p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
									eff.cancel();
									timer.cancel();
									Bukkit.getScheduler().runTask(WitherWarp.plugin, new Runnable() {
										@Override
										public void run() {
											p.teleport(Warp.getLocation(e.getSlot()));
										}
									});
								}
							}
						}
					};
					timer.schedule(task, 1L, 10L);
				} else {
					p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (WitherWarp.warping.contains(e.getPlayer().getUniqueId().toString())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(WitherWarp.msg.get("prefix.info") + WitherWarp.msg.get("warping.nocommands"));
		}
	}
}