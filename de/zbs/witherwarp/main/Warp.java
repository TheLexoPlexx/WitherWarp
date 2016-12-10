package de.zbs.witherwarp.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Warp {
	
	static String path = "plugins/" + WitherWarp.plugin.getName();
	
	private int slot;
	private String name;
	private List<String> description;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private String worldUUID;
	private Material material;
	private boolean enchanted;
	private List<String> alias;

	public static Warp getWarp(int slot) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("plugins/WitherWarp", "warps.yml"));
		if (cfg.getString(slot + "") != null) {
			return new Warp(slot);
		} else {
			return null;
		}
	}
	
	public static ItemStack getItemStack(int slot) {
		File f = new File(path, "warps.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		Material m = Material.getMaterial(cfg.getString(slot + ".material"));
		ItemStack is = new ItemStack(m);
		ItemMeta im = is.getItemMeta();
		if (cfg.getBoolean(slot + ".enchanted")) {
			im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		}
		im.addItemFlags(
				ItemFlag.HIDE_ATTRIBUTES,
				ItemFlag.HIDE_DESTROYS,
				ItemFlag.HIDE_ENCHANTS,
				ItemFlag.HIDE_PLACED_ON,
				ItemFlag.HIDE_POTION_EFFECTS,
				ItemFlag.HIDE_UNBREAKABLE);
		String name = cfg.getString(slot + ".name");
		name.replace("\\xa71", "&");
		im.setDisplayName(ChatColor.DARK_AQUA + ChatColor.translateAlternateColorCodes('&', name));
		List<String> lore = new ArrayList<String>();
		for (String s : cfg.getStringList(slot + ".description")) {
			s.replace("\\xa71", "&");
			lore.add(ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', s));
		}
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	public static Location getLocation(int slot) {
		File f = new File(path, "warps.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		return new Location(
				Bukkit.getWorld(UUID.fromString(cfg.getString(slot + ".world"))),
				cfg.getDouble(slot + ".x"),
				cfg.getDouble(slot + ".y"),
				cfg.getDouble(slot + ".z"),
				cfg.getInt(slot + ".yaw"),
				cfg.getInt(slot + ".pitch"));
	}
	
	public Location getLocation() {
		return new Location(Bukkit.getWorld(UUID.fromString(this.worldUUID)), this.x, this.y, this.z, this.yaw, this.pitch);
	}
	
	private Warp(int slot) {
		File f = new File(path, "warps.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		this.name = cfg.getString(this.slot + ".name");
		this.description = cfg.getStringList(this.slot + ".description");
		this.x = cfg.getDouble(this.slot + ".x");
		this.y = cfg.getDouble(this.slot + ".y");
		this.z = cfg.getDouble(this.slot + ".z");
		this.yaw = cfg.getInt(this.slot + ".yaw");
		this.pitch = cfg.getInt(this.slot + ".pitch");
		this.worldUUID = cfg.getString(this.slot + ".world");
		this.material = Material.getMaterial(cfg.getString(this.slot + ".material"));
		this.enchanted = cfg.getBoolean(this.slot + ".enchanted");
		this.alias = cfg.getStringList(this.slot + ".alias");
	}
	
	public Warp(Location loc, ItemStack item, int slot) {
		this.slot = slot;
		this.name = item.getItemMeta().getDisplayName();
		if (item.getItemMeta().getLore() != null) {
			this.description = item.getItemMeta().getLore();
		} else {
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.DARK_GRAY + "Default description :(");
			this.description = lore;
		}
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.yaw = loc.getYaw();
		this.pitch = loc.getPitch();
		this.worldUUID = loc.getWorld().getUID().toString();
		this.material = item.getType();
		this.enchanted = item.getItemMeta().hasEnchants();
		this.alias = new ArrayList<String>();
		save();
	}
	
	public void delete() {
		File f = new File(path, "warps.yml");
		final FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set(this.slot + "", null);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		File f = new File(path, "warps.yml");
		final FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set(this.slot + ".name", this.name);
		cfg.set(this.slot + ".description", this.description);
		cfg.set(this.slot + ".x", this.x);
		cfg.set(this.slot + ".y", this.y);
		cfg.set(this.slot + ".z", this.z);
		cfg.set(this.slot + ".yaw", this.yaw);
		cfg.set(this.slot + ".pitch", this.pitch);
		cfg.set(this.slot + ".world", this.worldUUID);
		cfg.set(this.slot + ".material", this.material.name());
		cfg.set(this.slot + ".enchanted", this.enchanted);
		cfg.set(this.alias + ".alias", this.alias);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getSlot() {
		return slot;
	}

	public String getName() {
		return name;
	}

	public List<String> getDescription() {
		return description;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public String getWorldUUID() {
		return worldUUID;
	}

	public Material getMaterial() {
		return material;
	}

	public boolean isEnchanted() {
		return enchanted;
	}

	public List<String> getAlias() {
		return alias;
	}
	
	

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setWorldUUID(String worldUUID) {
		this.worldUUID = worldUUID;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setEnchanted(boolean enchanted) {
		this.enchanted = enchanted;
	}

	public void addAlias(String alias) {
		this.alias.add(alias);
	}

	public void removeAlias(String alias) {
		this.alias.remove(alias);
	}
}