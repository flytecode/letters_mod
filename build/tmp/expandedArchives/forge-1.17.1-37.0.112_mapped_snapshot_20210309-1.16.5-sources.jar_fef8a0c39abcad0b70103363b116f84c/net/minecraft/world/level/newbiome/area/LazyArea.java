package net.minecraft.world.level.newbiome.area;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.newbiome.layer.traits.PixelTransformer;

public final class LazyArea implements Area {
   private final PixelTransformer f_76489_;
   private final Long2IntLinkedOpenHashMap f_76490_;
   private final int f_76491_;

   public LazyArea(Long2IntLinkedOpenHashMap p_76493_, int p_76494_, PixelTransformer p_76495_) {
      this.f_76490_ = p_76493_;
      this.f_76491_ = p_76494_;
      this.f_76489_ = p_76495_;
   }

   public int m_7929_(int p_76498_, int p_76499_) {
      long i = ChunkPos.m_45589_(p_76498_, p_76499_);
      synchronized(this.f_76490_) {
         int j = this.f_76490_.get(i);
         if (j != Integer.MIN_VALUE) {
            return j;
         } else {
            int k = this.f_76489_.m_77075_(p_76498_, p_76499_);
            this.f_76490_.put(i, k);
            if (this.f_76490_.size() > this.f_76491_) {
               for(int l = 0; l < this.f_76491_ / 16; ++l) {
                  this.f_76490_.removeFirstInt();
               }
            }

            return k;
         }
      }
   }

   public int m_76496_() {
      return this.f_76491_;
   }
}