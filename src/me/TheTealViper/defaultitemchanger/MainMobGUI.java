package me.TheTealViper.defaultitemchanger;

import java.util.ArrayList;
import java.util.List;
import me.TheTealViper.defaultitemchanger.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMobGUI
  implements Listener
{
  public static DefaultItemChanger plugin = null;
  
  public static void open(DefaultItemChanger plugin, Player p) {
    MainMobGUI.plugin = plugin;
    Bukkit.getPluginManager().registerEvents(new MainMobGUI(), plugin);
    Inventory inv = Bukkit.createInventory(null, 54, String.valueOf(StringUtils.makeColors("&r&7&lMob Menu")) + DefaultItemChanger.MAINMOBIDENTIFIER);
    int index = 0;
    for (EntityType type : DefaultItemChanger.entities) {
      ItemStack item = new ItemStack(Material.STONE_BUTTON);
      ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
      meta.setDisplayName(StringUtils.makeColors("&r" + type.toString()));
      List<String> lore = new ArrayList<String>();
      lore.add(StringUtils.makeColors("&r&7Click me to edit this mob's drops."));
      meta.setLore(lore);
      item.setItemMeta(meta);
      inv.setItem(index, item);
      index++;
    } 
    p.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getClickedInventory() != null && e.getView().getTitle().contains(DefaultItemChanger.MAINMOBIDENTIFIER)) {
      e.setCancelled(true);
      if (e.getCurrentItem() == null)
        return; 
      String typeString = e.getCurrentItem().getItemMeta().getDisplayName().replace(ChatColor.RESET + "", "");
      
      (new MobChangeGUI()).open(plugin, typeString, (Player)e.getWhoClicked());
    } 
  }
}
