package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class FireworkStarItem extends Item {
   public FireworkStarItem(Item.Properties p_41248_) {
      super(p_41248_);
   }

   public void m_7373_(ItemStack p_41252_, @Nullable Level p_41253_, List<Component> p_41254_, TooltipFlag p_41255_) {
      CompoundTag compoundtag = p_41252_.m_41737_("Explosion");
      if (compoundtag != null) {
         m_41256_(compoundtag, p_41254_);
      }

   }

   public static void m_41256_(CompoundTag p_41257_, List<Component> p_41258_) {
      FireworkRocketItem.Shape fireworkrocketitem$shape = FireworkRocketItem.Shape.m_41237_(p_41257_.m_128445_("Type"));
      p_41258_.add((new TranslatableComponent("item.minecraft.firework_star.shape." + fireworkrocketitem$shape.m_41241_())).m_130940_(ChatFormatting.GRAY));
      int[] aint = p_41257_.m_128465_("Colors");
      if (aint.length > 0) {
         p_41258_.add(m_41259_((new TextComponent("")).m_130940_(ChatFormatting.GRAY), aint));
      }

      int[] aint1 = p_41257_.m_128465_("FadeColors");
      if (aint1.length > 0) {
         p_41258_.add(m_41259_((new TranslatableComponent("item.minecraft.firework_star.fade_to")).m_130946_(" ").m_130940_(ChatFormatting.GRAY), aint1));
      }

      if (p_41257_.m_128471_("Trail")) {
         p_41258_.add((new TranslatableComponent("item.minecraft.firework_star.trail")).m_130940_(ChatFormatting.GRAY));
      }

      if (p_41257_.m_128471_("Flicker")) {
         p_41258_.add((new TranslatableComponent("item.minecraft.firework_star.flicker")).m_130940_(ChatFormatting.GRAY));
      }

   }

   private static Component m_41259_(MutableComponent p_41260_, int[] p_41261_) {
      for(int i = 0; i < p_41261_.length; ++i) {
         if (i > 0) {
            p_41260_.m_130946_(", ");
         }

         p_41260_.m_7220_(m_41249_(p_41261_[i]));
      }

      return p_41260_;
   }

   private static Component m_41249_(int p_41250_) {
      DyeColor dyecolor = DyeColor.m_41061_(p_41250_);
      return dyecolor == null ? new TranslatableComponent("item.minecraft.firework_star.custom_color") : new TranslatableComponent("item.minecraft.firework_star." + dyecolor.m_41065_());
   }
}