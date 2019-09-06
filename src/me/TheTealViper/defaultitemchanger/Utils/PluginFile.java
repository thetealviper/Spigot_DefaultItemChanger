package me.TheTealViper.defaultitemchanger.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;









public class PluginFile
  extends YamlConfiguration
{
  private File file;
  private String defaults;
  private JavaPlugin plugin;
  
  public PluginFile(JavaPlugin plugin, String fileName) { this(plugin, fileName, null); }







  
  public PluginFile(JavaPlugin plugin, String fileName, String defaultsName) {
    this.plugin = plugin;
    this.defaults = defaultsName;
    this.file = new File(plugin.getDataFolder(), fileName);
    reload();
  }




  
  public void reload() {
    if (!this.file.exists()) {
      
      try {
        this.file.getParentFile().mkdirs();
        this.file.createNewFile();
      }
      catch (IOException exception) {
        exception.printStackTrace();
        //this.plugin.getLogger().severe("Error while creating file " + this.file.getName());
      } 
    }

    
    try {
      load(this.file);
      
      if (this.defaults != null) {
        InputStreamReader reader = new InputStreamReader(this.plugin.getResource(this.defaults));
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(reader);
        
        setDefaults(yamlConfiguration);
        options().copyDefaults(true);
        
        reader.close();
        save();
      }
    
    } catch (IOException exception) {
      exception.printStackTrace();
      //this.plugin.getLogger().severe("Error while loading file " + this.file.getName());
    }
    catch (InvalidConfigurationException exception) {
      exception.printStackTrace();
      //this.plugin.getLogger().severe("Error while loading file " + this.file.getName());
    } 
  }






  
  public void save() {
    try {
      options().indent(2);
      save(this.file);
    }
    catch (IOException exception) {
      exception.printStackTrace();
      //this.plugin.getLogger().severe("Error while saving file " + this.file.getName());
    } 
  }
}
