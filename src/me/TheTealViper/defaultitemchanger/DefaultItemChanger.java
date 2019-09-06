package me.TheTealViper.defaultitemchanger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import me.TheTealViper.defaultitemchanger.Utils.EnableShit;
import me.TheTealViper.defaultitemchanger.Utils.PluginFile;
import me.TheTealViper.defaultitemchanger.Utils.StringUtils;



public class DefaultItemChanger
  extends JavaPlugin
  implements Listener
{
  public static String MAINMOBIDENTIFIER = StringUtils.encodeString("%DIC1");
  public static String MOBCHANGEIDENTIFIER = StringUtils.encodeString("%DIC2");
  public static String ITEMCHANGEIDENTIFIER = StringUtils.encodeString("%DIC3");
  
  List<Material> durMats = new ArrayList();
  public static List<EntityType> entities = new ArrayList();
  
  public void onEnable() {
    EnableShit.handleOnEnable(this, this, "49569");
    this.durMats.add(Material.DIAMOND_SHOVEL); this.durMats.add(Material.GOLDEN_SHOVEL); this.durMats.add(Material.IRON_SHOVEL);
    this.durMats.add(Material.STONE_SHOVEL); this.durMats.add(Material.WOODEN_SHOVEL);
    this.durMats.add(Material.DIAMOND_PICKAXE); this.durMats.add(Material.GOLDEN_PICKAXE); this.durMats.add(Material.IRON_PICKAXE);
    this.durMats.add(Material.STONE_PICKAXE); this.durMats.add(Material.WOODEN_PICKAXE);
    this.durMats.add(Material.DIAMOND_AXE); this.durMats.add(Material.GOLDEN_AXE); this.durMats.add(Material.IRON_AXE);
    this.durMats.add(Material.STONE_AXE); this.durMats.add(Material.WOODEN_AXE);
    this.durMats.add(Material.DIAMOND_HOE); this.durMats.add(Material.GOLDEN_HOE); this.durMats.add(Material.IRON_HOE);
    this.durMats.add(Material.STONE_HOE); this.durMats.add(Material.WOODEN_HOE);
    this.durMats.add(Material.DIAMOND_SWORD); this.durMats.add(Material.GOLDEN_SWORD); this.durMats.add(Material.IRON_SWORD);
    this.durMats.add(Material.STONE_SWORD); this.durMats.add(Material.WOODEN_SWORD);
    this.durMats.add(Material.CHAINMAIL_HELMET); this.durMats.add(Material.DIAMOND_HELMET); this.durMats.add(Material.GOLDEN_HELMET);
    this.durMats.add(Material.IRON_HELMET); this.durMats.add(Material.LEATHER_HELMET);
    this.durMats.add(Material.CHAINMAIL_CHESTPLATE); this.durMats.add(Material.DIAMOND_CHESTPLATE); this.durMats.add(Material.GOLDEN_CHESTPLATE);
    this.durMats.add(Material.IRON_CHESTPLATE); this.durMats.add(Material.LEATHER_CHESTPLATE);
    this.durMats.add(Material.CHAINMAIL_LEGGINGS); this.durMats.add(Material.DIAMOND_LEGGINGS); this.durMats.add(Material.LEATHER_LEGGINGS);
    this.durMats.add(Material.IRON_LEGGINGS); this.durMats.add(Material.GOLDEN_LEGGINGS);
    this.durMats.add(Material.CHAINMAIL_BOOTS); this.durMats.add(Material.DIAMOND_BOOTS); this.durMats.add(Material.GOLDEN_BOOTS);
    this.durMats.add(Material.IRON_BOOTS); this.durMats.add(Material.LEATHER_BOOTS);
    this.durMats.add(Material.BOW);
    
    entities.add(EntityType.BAT); entities.add(EntityType.BLAZE); entities.add(EntityType.CAVE_SPIDER); entities.add(EntityType.CHICKEN);
    entities.add(EntityType.COW); entities.add(EntityType.CREEPER); entities.add(EntityType.DONKEY); entities.add(EntityType.ELDER_GUARDIAN);
    entities.add(EntityType.ENDER_DRAGON); entities.add(EntityType.ENDERMAN); entities.add(EntityType.ENDERMITE); entities.add(EntityType.EVOKER);
    entities.add(EntityType.GHAST); entities.add(EntityType.GUARDIAN); entities.add(EntityType.HORSE); entities.add(EntityType.HUSK);
    entities.add(EntityType.ILLUSIONER); entities.add(EntityType.IRON_GOLEM); entities.add(EntityType.LLAMA); entities.add(EntityType.MAGMA_CUBE);
    entities.add(EntityType.MULE); entities.add(EntityType.MUSHROOM_COW); entities.add(EntityType.OCELOT); entities.add(EntityType.PARROT);
    entities.add(EntityType.PIG); entities.add(EntityType.PIG_ZOMBIE); entities.add(EntityType.POLAR_BEAR); entities.add(EntityType.RABBIT);
    entities.add(EntityType.SHEEP); entities.add(EntityType.SHULKER); entities.add(EntityType.SILVERFISH); entities.add(EntityType.SKELETON);
    entities.add(EntityType.SKELETON_HORSE); entities.add(EntityType.SLIME); entities.add(EntityType.SNOWMAN); entities.add(EntityType.SPIDER);
    entities.add(EntityType.SQUID); entities.add(EntityType.STRAY); entities.add(EntityType.VILLAGER); entities.add(EntityType.VINDICATOR);
    entities.add(EntityType.WITCH); entities.add(EntityType.WITHER); entities.add(EntityType.WITHER_SKELETON); entities.add(EntityType.ZOMBIE);
    entities.add(EntityType.ZOMBIE_HORSE); entities.add(EntityType.ZOMBIE_VILLAGER);
    
    loadShit();
  }

  
  public void onDisable() { 
	  //getLogger().info("DefaultItemChanger from TheTealViper shutting down. Bshzzzzzz"); 
  }

  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player)sender;
      boolean explain = false;
      if (args.length == 0 && p.hasPermission("defaultitemchanger.admin")) {
        explain = true;
      } else if (args.length == 1) {
        if (args[0].equalsIgnoreCase("mob") && p.hasPermission("defaultitemchanger.admin"))
        { MainMobGUI.open(this, p); }
        else if (args[0].equalsIgnoreCase("item") && p.hasPermission("defaultitemchanger.admin"))
        { if (p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR))
          { (new ItemChangeGUI()).open(this, String.valueOf(p.getItemInHand().getType()) + "_" + p.getItemInHand().getDurability(), p); }
          else
          { p.sendMessage("You must hold the vanilla item in your hand."); }  }
        else if (args[0].equalsIgnoreCase("reload") && p.hasPermission("defaultitemchanger.admin"))
        { reload();
          p.sendMessage("Reloaded successfully."); }
        else
        { explain = true; } 
      } else if (args.length == 2 && 
        args[0].equalsIgnoreCase("mob") && p.hasPermission("defaultitemchanger.admin")) {
        try {
          EntityType.valueOf(args[1].toUpperCase());
          (new MobChangeGUI()).open(this, args[1].toUpperCase(), p);
        } catch (Exception e) {
          p.sendMessage("That is not an entity option.");
        } 
      } 
      
      if (explain) {
        p.sendMessage("DefaultItemChanger Commands:");
        p.sendMessage("/dic item" + ChatColor.GRAY + " - Opens the item changer for the item in hand.");
        p.sendMessage("/dic mob (mob_name)" + ChatColor.GRAY + " - Opens the mob item changer.");
        p.sendMessage("/dic reload" + ChatColor.GRAY + " - I refuse to explain this.");
      } 
    } 
    return false;
  }
  
  @EventHandler
  public void onKill(EntityDeathEvent e) {
    if (!MobItemCreator.itemMap.containsKey(e.getEntityType()))
      return; 
    for (MobItemCreator.MobItem mi : MobItemCreator.itemMap.get(e.getEntityType())) {
      double number = Math.random() * 100.0D;
      if (number < mi.chance) {
        e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), mi.item);
        if (!getConfig().getBoolean("Allow_Multiple_Item_Drops")) {
          return;
        }
      } 
    } 
    e.getDrops().clear();
  }
  
  @EventHandler
  public void onItemDrop(ItemSpawnEvent e) {
    if (getConfig().getBoolean("Use_In_Game_Editor_Over_Config")) {
      ItemStack item = e.getEntity().getItemStack();
      String ID = String.valueOf(item.getType()) + "_" + item.getDurability();
      if (!DefaultItemCreator.itemMap.containsKey(ID))
        return; 
      for (DefaultItemCreator.DefaultItem mi : DefaultItemCreator.itemMap.get(ID)) {
        double number = Math.random() * 100.0D;
        if (number < mi.chance) {
          Item i = e.getLocation().getWorld().dropItemNaturally(e.getLocation(), mi.item);
          i.setVelocity(e.getEntity().getVelocity());
          if (!getConfig().getBoolean("Allow_Multiple_Item_Drops")) {
            e.getEntity().getItemStack().setAmount(0);
            return;
          } 
        } 
      } 
      e.getEntity().getItemStack().setAmount(0);
    } else {
      ItemStack item = e.getEntity().getItemStack();
      String id = String.valueOf(e.getEntity().getItemStack().getType()) + ":" + e.getEntity().getItemStack().getDurability();
      if (getConfig().contains(id) && (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())) {
        e.getEntity().setItemStack(getItem(id, e.getEntity().getItemStack().getAmount()));
      }
    } 
  }
  
  @EventHandler
  public void onInventory(InventoryClickEvent e) {
    try {
      if (e.getClickedInventory().equals(e.getInventory()))
        if (getConfig().getBoolean("Use_In_Game_Editor_Over_Config")) {
          int slot = e.getRawSlot();
          ItemStack item = e.getInventory().getItem(slot);
          String ID = String.valueOf(item.getType()) + "_" + item.getDurability();
          if (!DefaultItemCreator.itemMap.containsKey(ID))
            return; 
          if (item != null && (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())) {
            for (DefaultItemCreator.DefaultItem mi : DefaultItemCreator.itemMap.get(ID)) {
              double number = Math.random() * 100.0D;
              if (number < mi.chance) {
                mi.item.setAmount(item.getAmount());
                e.getInventory().setItem(slot, mi.item);
                return;
              } 
            } 
          }
        } else {
          int slot = e.getSlot();
          ItemStack item = e.getInventory().getItem(slot);
          if (item != null && (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())) {
            e.getInventory().setItem(slot, getItem(String.valueOf(item.getType()) + ":" + item.getDurability(), item.getAmount()));
          }
        }  
    } catch (Exception exception) {}
  }


  
  public void loadShit() {
    File mobFolder = new File("plugins/DefaultItemChanger/mobs");
    if (!mobFolder.exists()) {
      mobFolder.mkdirs();
      for (EntityType et : entities) {
        try {
          YamlConfiguration.loadConfiguration(new InputStreamReader(getResource("mob.yml"))).save("plugins/DefaultItemChanger/mobs/" + et.toString() + ".yml");
        } catch (IOException e) {
          
          e.printStackTrace();
        } 
      } 
    } 
    File itemFolder = new File("plugins/DefaultItemChanger/items");
    if (!itemFolder.exists())
      itemFolder.mkdirs();  byte b; int j;
    File[] arrayOfFile;
    for (File f : mobFolder.listFiles()) {
      PluginFile entityConfig = new PluginFile(this, "mobs/" + f.getName().replace(".yml", "") + ".yml");
      for (int i = 0; i < 54; i++) {
        if (entityConfig.contains("Drops." + i))
          MobItemCreator.loadItemFromConfiguration(f.getName().replace(".yml", ""), entityConfig, i, true); 
      } 
    }
    
    for (File f : itemFolder.listFiles()) {
      PluginFile itemConfig = new PluginFile(this, "items/" + f.getName().replace(".yml", "") + ".yml");
	  for (int i = 0; i < 54; i++) {
	    if (itemConfig.contains("Drops." + i))
	      DefaultItemCreator.loadItemFromConfiguration(f.getName().replace(".yml", ""), itemConfig, i, true); 
	  } 
    }
  
  }
  
  public void reload() {
    MobItemCreator.reload();
    DefaultItemCreator.reload();
    reloadConfig();
    loadShit();
  }
  
  public ItemStack getItem(String id, int amount) {
    ItemStack item = new ItemStack(Material.valueOf(id.split(":")[0]), amount, Short.valueOf(id.split(":")[1]));
    ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
    meta.setDisplayName(StringUtils.makeColors(getConfig().getString(String.valueOf(id) + ".name")));
    List<String> lore = getConfig().contains(String.valueOf(id) + ".lore") ? getConfig().getStringList(String.valueOf(id) + ".lore") : new ArrayList();
    for (int i = 0; i < lore.size(); i++)
      lore.set(i, StringUtils.makeColors((String)lore.get(i))); 
    if (!lore.isEmpty())
      meta.setLore(lore); 
    item.setItemMeta(meta);
    List<String> enchantData = getConfig().contains(String.valueOf(id) + ".enchantments") ? getConfig().getStringList(String.valueOf(id) + ".enchantments") : new ArrayList();
    for (String s : enchantData) {
      String enchantment = s.split(":")[0];
      int level = Integer.valueOf(s.split(":")[1]).intValue();
      if (enchantment.equalsIgnoreCase("arrowdamage")) {
        item.addEnchantment(Enchantment.ARROW_DAMAGE, level); continue;
      }  if (enchantment.equalsIgnoreCase("arrowfire")) {
        item.addUnsafeEnchantment(Enchantment.ARROW_FIRE, level); continue;
      }  if (enchantment.equalsIgnoreCase("arrowinfinite")) {
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, level); continue;
      }  if (enchantment.equalsIgnoreCase("arrowknockback")) {
        item.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, level); continue;
      }  if (enchantment.equalsIgnoreCase("damage")) {
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, level); continue;
      }  if (enchantment.equalsIgnoreCase("digspeed")) {
        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, level); continue;
      }  if (enchantment.equalsIgnoreCase("durability")) {
        item.addUnsafeEnchantment(Enchantment.DURABILITY, level); continue;
      }  if (enchantment.equalsIgnoreCase("fireaspect")) {
        item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, level); continue;
      }  if (enchantment.equalsIgnoreCase("knockback")) {
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, level); continue;
      }  if (enchantment.equalsIgnoreCase("lootbonusblock")) {
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, level); continue;
      }  if (enchantment.equalsIgnoreCase("lootbonusmob")) {
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, level); continue;
      }  if (enchantment.equalsIgnoreCase("luck")) {
        item.addUnsafeEnchantment(Enchantment.LUCK, level); continue;
      }  if (enchantment.equalsIgnoreCase("protectionfall")) {
        item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, level); continue;
      }  if (enchantment.equalsIgnoreCase("protectionfire")) {
        item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, level); continue;
      }  if (enchantment.equalsIgnoreCase("silktouch")) {
        item.addUnsafeEnchantment(Enchantment.SILK_TOUCH, level);
      }
    } 
    List<String> tags = getConfig().contains(String.valueOf(id) + ".tags") ? getConfig().getStringList(String.valueOf(id) + ".tags") : new ArrayList();
    for (String s : tags) {
      if (s.startsWith("skullskin") && item.getType().equals(Material.PLAYER_HEAD)) {
        SkullMeta skull = (SkullMeta)item.getData();
        skull.setOwner(s.replace("skullskin:", ""));
        item.setData((MaterialData)skull); continue;
      }  if (s.startsWith("durability") && this.durMats.contains(item.getType())) {
        item.getData().setData(Byte.valueOf(s.replace("durability:", "")).byteValue());
        item.setDurability(Short.valueOf(s.replace("durability:", "")).shortValue());
      } 
    } 
    return item;
  }
}
