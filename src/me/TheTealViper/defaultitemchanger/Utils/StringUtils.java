package me.TheTealViper.defaultitemchanger.Utils;

import java.nio.charset.Charset;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;




public class StringUtils
{
  private static final String SEQUENCE_HEADER = ChatColor.RESET + "" + ChatColor.UNDERLINE + ChatColor.RESET;
  private static final String SEQUENCE_FOOTER = ChatColor.RESET + "" + ChatColor.ITALIC + ChatColor.RESET;
  
  public static String convertToInvisibleString(String s) {
    String hidden = ""; byte b; int i; char[] arrayOfChar;
    for (char c : s.toCharArray()) {
    	hidden = String.valueOf(hidden) + "§" + c;}
    return hidden;
  }

  
  public static String convertBack(String s) { return s.replaceAll("§", ""); }

























  
  public static String makeColors(String s) { return s.replaceAll("&0", ChatColor.BLACK + "").replaceAll("&1", ChatColor.DARK_BLUE + "").replaceAll("&2", ChatColor.DARK_GREEN + "").replaceAll("&3", ChatColor.DARK_AQUA + "").replaceAll("&4", ChatColor.DARK_RED + "").replaceAll("&5", ChatColor.DARK_PURPLE + "").replaceAll("&6", ChatColor.GOLD + "").replaceAll("&7", ChatColor.GRAY + "").replaceAll("&8", ChatColor.DARK_GRAY + "").replaceAll("&9", ChatColor.BLUE + "").replaceAll("&a", ChatColor.GREEN + "").replaceAll("&b", ChatColor.AQUA + "").replaceAll("&c", ChatColor.RED + "").replaceAll("&d", ChatColor.LIGHT_PURPLE + "").replaceAll("&e", ChatColor.YELLOW + "").replaceAll("&f", ChatColor.WHITE + "").replaceAll("&r", ChatColor.RESET + "").replaceAll("&l", ChatColor.BOLD + "").replaceAll("&o", ChatColor.ITALIC + "").replaceAll("&k", ChatColor.MAGIC + "").replaceAll("&m", ChatColor.STRIKETHROUGH + "").replaceAll("&n", ChatColor.UNDERLINE + "").replaceAll("\\\\", " "); }


  
  public static String toLocString(Location loc, boolean detailed, boolean extended, String[] args) {
    String locString = String.valueOf(loc.getWorld().getName()) + "_";
    if (detailed) {
      locString = String.valueOf(locString) + loc.getX() + "_" + loc.getY() + "_" + loc.getZ();
    } else {
      locString = String.valueOf(locString) + loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();
    }  if (extended)
      if (detailed) {
        locString = String.valueOf(locString) + "_" + loc.getYaw() + "_" + loc.getPitch();
      } else {
        locString = String.valueOf(locString) + "_" + (int)loc.getYaw() + "_" + (int)loc.getPitch();
      }  
    if (args != null) {
      for(String s : args) {
        locString = String.valueOf(locString) + "_" + s;
      }
    
    }  return locString;
  }
  public static Location fromLocString(String locString, boolean extended) {
    String[] s = locString.split("_");
    if (!extended) {
      return new Location(Bukkit.getWorld(s[0]), Double.valueOf(s[1]).doubleValue(), Double.valueOf(s[2]).doubleValue(), Double.valueOf(s[3]).doubleValue());
    }
    return new Location(Bukkit.getWorld(s[0]), Double.valueOf(s[1]).doubleValue(), Double.valueOf(s[2]).doubleValue(), Double.valueOf(s[3]).doubleValue(), Float.valueOf(s[4]).floatValue(), Float.valueOf(s[5]).floatValue());
  }

  
  public static String encodeString(String hiddenString) { return quote(stringToColors(hiddenString)); }

  
  public static boolean hasHiddenString(String input) {
    if (input == null) return false;
    
    return (input.indexOf(SEQUENCE_HEADER) > -1 && input.indexOf(SEQUENCE_FOOTER) > -1);
  }

  
  public static String extractHiddenString(String input) { return colorsToString(extract(input)); }


  
  public static String replaceHiddenString(String input, String hiddenString) {
    if (input == null) return null;
    
    int start = input.indexOf(SEQUENCE_HEADER);
    int end = input.indexOf(SEQUENCE_FOOTER);
    
    if (start < 0 || end < 0) {
      return null;
    }
    
    return String.valueOf(input.substring(0, start + SEQUENCE_HEADER.length())) + stringToColors(hiddenString) + input.substring(end, input.length());
  }



  
  private static String quote(String input) {
    if (input == null) return null; 
    return String.valueOf(SEQUENCE_HEADER) + input + SEQUENCE_FOOTER;
  }
  
  private static String extract(String input) {
    if (input == null) return null;
    
    int start = input.indexOf(SEQUENCE_HEADER);
    int end = input.indexOf(SEQUENCE_FOOTER);
    
    if (start < 0 || end < 0) {
      return null;
    }
    
    return input.substring(start + SEQUENCE_HEADER.length(), end);
  }
  
  private static String stringToColors(String normal) {
    if (normal == null) return null;
    
    byte[] bytes = normal.getBytes(Charset.forName("UTF-8"));
    char[] chars = new char[bytes.length * 4];
    
    for (int i = 0; i < bytes.length; i++) {
      char[] hex = byteToHex(bytes[i]);
      chars[i * 4] = '§';
      chars[i * 4 + 1] = hex[0];
      chars[i * 4 + 2] = '§';
      chars[i * 4 + 3] = hex[1];
    } 
    
    return new String(chars);
  }
  
  private static String colorsToString(String colors) {
    if (colors == null) return null;
    
    colors = colors.toLowerCase().replace("§", "");
    
    if (colors.length() % 2 != 0) {
      colors = colors.substring(0, colors.length() / 2 * 2);
    }
    
    char[] chars = colors.toCharArray();
    byte[] bytes = new byte[chars.length / 2];
    
    for (int i = 0; i < chars.length; i += 2) {
      bytes[i / 2] = hexToByte(chars[i], chars[i + 1]);
    }
    
    return new String(bytes, Charset.forName("UTF-8"));
  }
  
  private static int hexToUnsignedInt(char c) {
    if (c >= '0' && c <= '9')
      return c - '0'; 
    if (c >= 'a' && c <= 'f') {
      return c - 'W';
    }
    throw new IllegalArgumentException("Invalid hex char: out of range");
  }

  
  private static char unsignedIntToHex(int i) {
    if (i >= 0 && i <= 9)
      return (char)(i + 48); 
    if (i >= 10 && i <= 15) {
      return (char)(i + 87);
    }
    throw new IllegalArgumentException("Invalid hex int: out of range");
  }


  
  private static byte hexToByte(char hex1, char hex0) { return (byte)((hexToUnsignedInt(hex1) << 4 | hexToUnsignedInt(hex0)) + -128); }

  
  private static char[] byteToHex(byte b) {
    int unsignedByte = b - Byte.MIN_VALUE;
    return new char[] { unsignedIntToHex(unsignedByte >> 4 & 0xF), unsignedIntToHex(unsignedByte & 0xF) };
  }
}
