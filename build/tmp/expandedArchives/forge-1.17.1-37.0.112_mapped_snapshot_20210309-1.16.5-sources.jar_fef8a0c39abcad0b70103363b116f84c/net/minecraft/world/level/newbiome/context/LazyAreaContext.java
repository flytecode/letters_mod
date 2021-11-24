package net.minecraft.world.level.newbiome.context;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.world.level.levelgen.SimpleRandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.newbiome.area.LazyArea;
import net.minecraft.world.level.newbiome.layer.traits.PixelTransformer;

public class LazyAreaContext implements BigContext<LazyArea> {
   private static final int f_164539_ = 1024;
   private final Long2IntLinkedOpenHashMap f_76517_;
   private final int f_76518_;
   private final ImprovedNoise f_76519_;
   private final long f_76520_;
   private long f_76521_;

   public LazyAreaContext(int p_76523_, long p_76524_, long p_76525_) {
      this.f_76520_ = m_76548_(p_76524_, p_76525_);
      this.f_76519_ = new ImprovedNoise(new SimpleRandomSource(p_76524_));
      this.f_76517_ = new Long2IntLinkedOpenHashMap(16, 0.25F);
      this.f_76517_.defaultReturnValue(Integer.MIN_VALUE);
      this.f_76518_ = p_76523_;
   }

   public LazyArea m_7851_(PixelTransformer p_76552_) {
      return new LazyArea(this.f_76517_, this.f_76518_, p_76552_);
   }

   public LazyArea m_6678_(PixelTransformer p_76541_, LazyArea p_76542_) {
      return new LazyArea(this.f_76517_, Math.min(1024, p_76542_.m_76496_() * 4), p_76541_);
   }

   public LazyArea m_6887_(PixelTransformer p_76544_, LazyArea p_76545_, LazyArea p_76546_) {
      return new LazyArea(this.f_76517_, Math.min(1024, Math.max(p_76545_.m_76496_(), p_76546_.m_76496_()) * 4), p_76544_);
   }

   public void m_6687_(long p_76529_, long p_76530_) {
      long i = this.f_76520_;
      i = LinearCongruentialGenerator.m_13972_(i, p_76529_);
      i = LinearCongruentialGenerator.m_13972_(i, p_76530_);
      i = LinearCongruentialGenerator.m_13972_(i, p_76529_);
      i = LinearCongruentialGenerator.m_13972_(i, p_76530_);
      this.f_76521_ = i;
   }

   public int m_5826_(int p_76527_) {
      int i = Math.floorMod(this.f_76521_ >> 24, p_76527_);
      this.f_76521_ = LinearCongruentialGenerator.m_13972_(this.f_76521_, this.f_76520_);
      return i;
   }

   public ImprovedNoise m_8029_() {
      return this.f_76519_;
   }

   private static long m_76548_(long p_76549_, long p_76550_) {
      long i = LinearCongruentialGenerator.m_13972_(p_76550_, p_76550_);
      i = LinearCongruentialGenerator.m_13972_(i, p_76550_);
      i = LinearCongruentialGenerator.m_13972_(i, p_76550_);
      long j = LinearCongruentialGenerator.m_13972_(p_76549_, i);
      j = LinearCongruentialGenerator.m_13972_(j, i);
      return LinearCongruentialGenerator.m_13972_(j, i);
   }
}