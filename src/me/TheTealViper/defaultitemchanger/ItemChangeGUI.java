package me.TheTealViper.defaultitemchanger;

import java.util.ArrayList;
import java.util.List;
import me.TheTealViper.defaultitemchanger.Utils.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemChangeGUI
  implements Listener
{
  public static DefaultItemChanger plugin = null;
  String typeString = null;
  Inventory inv = null;
  PluginFile pf = null;
  Player p = null;
  
  boolean listenForChat = false;
  String savedArgs = null;
  
  public void open(DefaultItemChanger plugin, String typeString, Player p) {
    this.typeString = typeString;
    this.inv = Bukkit.createInventory(null, 54, ChatColor.RESET + typeString + DefaultItemChanger.ITEMCHANGEIDENTIFIER);
    this.pf = new PluginFile(plugin, "items/" + typeString + ".yml");
    this.p = p;
    MobChangeGUI.plugin = plugin;
    Bukkit.getPluginManager().registerEvents(this, plugin);
    for (int i = 0; i < 54; i++) {
      if (this.pf.contains("Drops." + i)) {
        DefaultItemCreator.DefaultItem mi = DefaultItemCreator.loadItemFromConfiguration(typeString, this.pf, i, false);
        ItemStack item = mi.item.clone();
        item = makeItem(item, mi.chance);
        this.inv.setItem(i, item);
      } else {
        this.inv.setItem(i, getEmptyItem(i));
      } 
    } 
    p.openInventory(this.inv);
  }
  private void open() {
    this.inv = Bukkit.createInventory(null, 54, ChatColor.RESET + this.typeString + DefaultItemChanger.ITEMCHANGEIDENTIFIER);
    for (int i = 0; i < 54; i++) {
      if (this.pf.contains("Drops." + i)) {
        DefaultItemCreator.DefaultItem mi = DefaultItemCreator.loadItemFromConfiguration(this.typeString, this.pf, i, false);
        ItemStack item = mi.item.clone();
        item = makeItem(item, mi.chance);
        this.inv.setItem(i, item);
      } else {
        this.inv.setItem(i, getEmptyItem(i));
      } 
    } 
    this.p.openInventory(this.inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (this.p == null)
      return; 
    if (e.getClickedInventory() != null && e.getInventory().equals(e.getClickedInventory()) && ((Player)e.getWhoClicked()).equals(this.p)) {
      e.setCancelled(true);
      if (e.getCurrentItem().isSimilar(getEmptyItem(e.getRawSlot()))) {
        if (e.getClick().equals(ClickType.LEFT))
        { if (e.getCursor() != null && !e.getCursor().getType().equals(Material.AIR))
            e.setCurrentItem(makeItem(e.getCursor(), 0).clone());  }
        else { e.getClick().equals(ClickType.RIGHT); }

      
      }
      else if (e.getClick().equals(ClickType.LEFT)) {
        this.inv.setItem(e.getRawSlot(), getEmptyItem(e.getRawSlot()));
      } else if (e.getClick().equals(ClickType.RIGHT)) {
        this.listenForChat = true;
        this.p.closeInventory();
        this.savedArgs = "Chance." + e.getRawSlot();
        this.p.sendMessage("Please type chance % in chat.");
      } 
    } 
  }

  
  @EventHandler
  public void onChat(PlayerChatEvent e) {
    if (this.p == null)
      return; 
    if (e.getPlayer().getName().equals(this.p.getName()) && 
      this.listenForChat) {
      e.setCancelled(true);
      try {
        int i = Integer.valueOf(e.getMessage()).intValue();
        this.pf.set(this.savedArgs, Integer.valueOf(i));
        this.pf.save();
        this.p.sendMessage("Successfully changed %.");
        open();
      } catch (Exception ex) {
        this.p.sendMessage("That is not a number. Try again.");
      } 
    } 
  }

  
  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getPlayer().equals(this.p)) {
      saveInventory();
    }
    
    if (!this.listenForChat) {
      this.inv = null;
      this.listenForChat = false;
      this.p = null;
      this.pf = null;
      this.savedArgs = null;
    } 
  }
  
  public void saveInventory() {
    for (int i = 0; i < 54; i++) {
      if (this.inv.getItem(i).isSimilar(getEmptyItem(i))) {
        this.pf.set("Drops." + i, null);
        this.pf.set("Chance." + i, null);
        this.pf.save();
        return;
      } 
      ItemStack item = this.inv.getItem(i).clone();
      if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
        
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.remove(lore.size() - 1);
        lore.remove(lore.size() - 1);
        int chance = Integer.valueOf(((String)lore.get(lore.size() - 1)).split("Chance: ")[1].replace("%", "")).intValue();
        lore.remove(lore.size() - 1);
        lore.remove(lore.size() - 1);
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.pf.set("Drops." + i, item);
        this.pf.set("Chance." + i, Integer.valueOf(chance));
        this.pf.save();
      } 
    } 
    plugin.reload();
  }
  
  public static ItemStack makeItem(ItemStack item, int chance) {
    item = item.clone();
    ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.STICK);
    List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList();
    lore.add(ChatColor.RESET + "-------------------");
    lore.add(ChatColor.RESET + "Chance: " + chance + "%");
    lore.add(ChatColor.RESET + "Left click to remove me.");
    lore.add(ChatColor.RESET + "Right click to change my percent.");
    meta.setLore(lore);
    item.setItemMeta(meta);
    return item;
  }
  
  public static ItemStack getEmptyItem(int slot) {
    ItemStack filler = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS);
    ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
    meta.setDisplayName("Item " + (slot + 1));
    List<String> lore = new ArrayList<String>();
    lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Left click an item on me to add it.");
    meta.setLore(lore);
    filler.setItemMeta(meta);
    return filler;
  }
}
