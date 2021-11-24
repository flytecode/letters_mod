package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.Registry;

public abstract class IntProvider {
   private static final Codec<Either<Integer, IntProvider>> f_146530_ = Codec.either(Codec.INT, Registry.f_175417_.dispatch(IntProvider::m_141948_, IntProviderType::m_146560_));
   public static final Codec<IntProvider> f_146531_ = f_146530_.xmap((p_146543_) -> {
      return p_146543_.map(ConstantInt::m_146483_, (p_146549_) -> {
         return p_146549_;
      });
   }, (p_146541_) -> {
      return p_146541_.m_141948_() == IntProviderType.f_146550_ ? Either.left(((ConstantInt)p_146541_).m_146499_()) : Either.right(p_146541_);
   });
   public static final Codec<IntProvider> f_146532_ = m_146545_(0, Integer.MAX_VALUE);
   public static final Codec<IntProvider> f_146533_ = m_146545_(1, Integer.MAX_VALUE);

   public static Codec<IntProvider> m_146545_(int p_146546_, int p_146547_) {
      Function<IntProvider, DataResult<IntProvider>> function = (p_146539_) -> {
         if (p_146539_.m_142739_() < p_146546_) {
            return DataResult.error("Value provider too low: " + p_146546_ + " [" + p_146539_.m_142739_() + "-" + p_146539_.m_142737_() + "]");
         } else {
            return p_146539_.m_142737_() > p_146547_ ? DataResult.error("Value provider too high: " + p_146547_ + " [" + p_146539_.m_142739_() + "-" + p_146539_.m_142737_() + "]") : DataResult.success(p_146539_);
         }
      };
      return f_146531_.flatXmap(function, function);
   }

   public abstract int m_142270_(Random p_146544_);

   public abstract int m_142739_();

   public abstract int m_142737_();

   public abstract IntProviderType<?> m_141948_();
}