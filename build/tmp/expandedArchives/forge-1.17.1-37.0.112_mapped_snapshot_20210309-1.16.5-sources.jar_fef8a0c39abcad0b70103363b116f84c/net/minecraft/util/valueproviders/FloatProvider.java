package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.Registry;

public abstract class FloatProvider {
   private static final Codec<Either<Float, FloatProvider>> f_146501_ = Codec.either(Codec.FLOAT, Registry.f_175415_.dispatch(FloatProvider::m_141961_, FloatProviderType::m_146529_));
   public static final Codec<FloatProvider> f_146502_ = f_146501_.xmap((p_146515_) -> {
      return p_146515_.map(ConstantFloat::m_146458_, (p_146518_) -> {
         return p_146518_;
      });
   }, (p_146513_) -> {
      return p_146513_.m_141961_() == FloatProviderType.f_146519_ ? Either.left(((ConstantFloat)p_146513_).m_146474_()) : Either.right(p_146513_);
   });

   public static Codec<FloatProvider> m_146505_(float p_146506_, float p_146507_) {
      Function<FloatProvider, DataResult<FloatProvider>> function = (p_146511_) -> {
         if (p_146511_.m_142735_() < p_146506_) {
            return DataResult.error("Value provider too low: " + p_146506_ + " [" + p_146511_.m_142735_() + "-" + p_146511_.m_142734_() + "]");
         } else {
            return p_146511_.m_142734_() > p_146507_ ? DataResult.error("Value provider too high: " + p_146507_ + " [" + p_146511_.m_142735_() + "-" + p_146511_.m_142734_() + "]") : DataResult.success(p_146511_);
         }
      };
      return f_146502_.flatXmap(function, function);
   }

   public abstract float m_142269_(Random p_146516_);

   public abstract float m_142735_();

   public abstract float m_142734_();

   public abstract FloatProviderType<?> m_141961_();
}