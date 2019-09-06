package me.TheTealViper.defaultitemchanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import me.TheTealViper.defaultitemchanger.Utils.PluginFile;




public class MobItemCreator
{
  public static Map<EntityType, List<MobItem>> itemMap = new HashMap();
  
  public static MobItem loadItemFromConfiguration(String entitytype, PluginFile pf, int slot, boolean saveToList) {
    ItemStack item = pf.getItemStack("Drops." + slot);
    int chance = pf.getInt("Chance." + slot);
    EntityType type = EntityType.valueOf(entitytype);
    MobItem mi = (new MobItemCreator()).new MobItem(item, chance);
    if (saveToList) {
      List<MobItem> info = itemMap.containsKey(type) ? (List)itemMap.get(type) : new ArrayList();
      info.add(mi);
      itemMap.put(EntityType.valueOf(entitytype), info);
    } 
    return mi;
  }

  
  public static void reload() { itemMap = new HashMap(); }
  
  public class MobItem {
    public ItemStack item;
    public int chance;
    
    public MobItem(ItemStack item, int chance) {
      this.item = item;
      this.chance = chance;
    }
  }
}
