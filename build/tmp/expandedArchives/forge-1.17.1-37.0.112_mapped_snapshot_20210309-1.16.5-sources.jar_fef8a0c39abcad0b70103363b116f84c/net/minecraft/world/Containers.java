package net.minecraft.world;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Containers {
   private static final Random f_18984_ = new Random();

   public static void m_19002_(Level p_19003_, BlockPos p_19004_, Container p_19005_) {
      m_18986_(p_19003_, (double)p_19004_.m_123341_(), (double)p_19004_.m_123342_(), (double)p_19004_.m_123343_(), p_19005_);
   }

   public static void m_18998_(Level p_18999_, Entity p_19000_, Container p_19001_) {
      m_18986_(p_18999_, p_19000_.m_20185_(), p_19000_.m_20186_(), p_19000_.m_20189_(), p_19001_);
   }

   private static void m_18986_(Level p_18987_, double p_18988_, double p_18989_, double p_18990_, Container p_18991_) {
      for(int i = 0; i < p_18991_.m_6643_(); ++i) {
         m_18992_(p_18987_, p_18988_, p_18989_, p_18990_, p_18991_.m_8020_(i));
      }

   }

   public static void m_19010_(Level p_19011_, BlockPos p_19012_, NonNullList<ItemStack> p_19013_) {
      p_19013_.forEach((p_19009_) -> {
         m_18992_(p_19011_, (double)p_19012_.m_123341_(), (double)p_19012_.m_123342_(), (double)p_19012_.m_123343_(), p_19009_);
      });
   }

   public static void m_18992_(Level p_18993_, double p_18994_, double p_18995_, double p_18996_, ItemStack p_18997_) {
      double d0 = (double)EntityType.f_20461_.m_20678_();
      double d1 = 1.0D - d0;
      double d2 = d0 / 2.0D;
      double d3 = Math.floor(p_18994_) + f_18984_.nextDouble() * d1 + d2;
      double d4 = Math.floor(p_18995_) + f_18984_.nextDouble() * d1;
      double d5 = Math.floor(p_18996_) + f_18984_.nextDouble() * d1 + d2;

      while(!p_18997_.m_41619_()) {
         ItemEntity itementity = new ItemEntity(p_18993_, d3, d4, d5, p_18997_.m_41620_(f_18984_.nextInt(21) + 10));
         float f = 0.05F;
         itementity.m_20334_(f_18984_.nextGaussian() * (double)0.05F, f_18984_.nextGaussian() * (double)0.05F + (double)0.2F, f_18984_.nextGaussian() * (double)0.05F);
         p_18993_.m_7967_(itementity);
      }

   }
}