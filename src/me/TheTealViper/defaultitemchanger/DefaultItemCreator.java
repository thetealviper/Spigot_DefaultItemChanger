package me.TheTealViper.defaultitemchanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.TheTealViper.defaultitemchanger.Utils.PluginFile;
import org.bukkit.inventory.ItemStack;



public class DefaultItemCreator
{
  public static Map<String, List<DefaultItem>> itemMap = new HashMap();
  
  public static DefaultItem loadItemFromConfiguration(String itemType, PluginFile pf, int slot, boolean saveToList) {
    ItemStack item = pf.getItemStack("Drops." + slot);
    int chance = pf.getInt("Chance." + slot);
    String type = itemType;
    DefaultItem mi = (new DefaultItemCreator()).new DefaultItem(item, chance);
    if (saveToList) {
      List<DefaultItem> info = itemMap.containsKey(type) ? (List)itemMap.get(type) : new ArrayList();
      info.add(mi);
      itemMap.put(type, info);
    } 
    return mi;
  }

  
  public static void reload() { itemMap = new HashMap(); }
  
  public class DefaultItem {
    public ItemStack item;
    public int chance;
    
    public DefaultItem(ItemStack item, int chance) {
      this.item = item;
      this.chance = chance;
    }
  }
}
