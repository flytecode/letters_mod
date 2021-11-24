package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class ConstantInt extends IntProvider {
   public static final ConstantInt f_146476_ = new ConstantInt(0);
   public static final Codec<ConstantInt> f_146477_ = Codec.either(Codec.INT, RecordCodecBuilder.<ConstantInt>create((p_146490_) -> {
      return p_146490_.group(Codec.INT.fieldOf("value").forGetter((p_146498_) -> {
         return p_146498_.f_146478_;
      })).apply(p_146490_, ConstantInt::new);
   })).xmap((p_146488_) -> {
      return p_146488_.map(ConstantInt::m_146483_, (p_146495_) -> {
         return p_146495_;
      });
   }, (p_146486_) -> {
      return Either.left(p_146486_.f_146478_);
   });
   private final int f_146478_;

   public static ConstantInt m_146483_(int p_146484_) {
      return p_146484_ == 0 ? f_146476_ : new ConstantInt(p_146484_);
   }

   private ConstantInt(int p_146481_) {
      this.f_146478_ = p_146481_;
   }

   public int m_146499_() {
      return this.f_146478_;
   }

   public int m_142270_(Random p_146492_) {
      return this.f_146478_;
   }

   public int m_142739_() {
      return this.f_146478_;
   }

   public int m_142737_() {
      return this.f_146478_;
   }

   public IntProviderType<?> m_141948_() {
      return IntProviderType.f_146550_;
   }

   public String toString() {
      return Integer.toString(this.f_146478_);
   }
}