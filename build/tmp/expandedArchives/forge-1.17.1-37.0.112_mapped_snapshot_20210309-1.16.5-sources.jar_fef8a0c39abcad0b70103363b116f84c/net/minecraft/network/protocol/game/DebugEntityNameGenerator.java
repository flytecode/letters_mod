package net.minecraft.network.protocol.game;

import java.util.Random;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class DebugEntityNameGenerator {
   private static final String[] f_133662_ = new String[]{"Slim", "Far", "River", "Silly", "Fat", "Thin", "Fish", "Bat", "Dark", "Oak", "Sly", "Bush", "Zen", "Bark", "Cry", "Slack", "Soup", "Grim", "Hook", "Dirt", "Mud", "Sad", "Hard", "Crook", "Sneak", "Stink", "Weird", "Fire", "Soot", "Soft", "Rough", "Cling", "Scar"};
   private static final String[] f_133663_ = new String[]{"Fox", "Tail", "Jaw", "Whisper", "Twig", "Root", "Finder", "Nose", "Brow", "Blade", "Fry", "Seek", "Wart", "Tooth", "Foot", "Leaf", "Stone", "Fall", "Face", "Tongue", "Voice", "Lip", "Mouth", "Snail", "Toe", "Ear", "Hair", "Beard", "Shirt", "Fist"};

   public static String m_179486_(Entity p_179487_) {
      if (p_179487_ instanceof Player) {
         return p_179487_.m_7755_().getString();
      } else {
         Component component = p_179487_.m_7770_();
         return component != null ? component.getString() : m_133668_(p_179487_.m_142081_());
      }
   }

   public static String m_133668_(UUID p_133669_) {
      Random random = m_133670_(p_133669_);
      return m_133665_(random, f_133662_) + m_133665_(random, f_133663_);
   }

   private static String m_133665_(Random p_133666_, String[] p_133667_) {
      return Util.m_137545_(p_133667_, p_133666_);
   }

   private static Random m_133670_(UUID p_133671_) {
      return new Random((long)(p_133671_.hashCode() >> 2));
   }
}