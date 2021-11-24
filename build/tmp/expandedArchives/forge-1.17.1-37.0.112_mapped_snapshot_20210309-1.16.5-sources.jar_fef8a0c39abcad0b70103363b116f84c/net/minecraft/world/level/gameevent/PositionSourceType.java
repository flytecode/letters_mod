package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface PositionSourceType<T extends PositionSource> {
   PositionSourceType<BlockPositionSource> f_157871_ = m_157877_("block", new BlockPositionSource.Type());
   PositionSourceType<EntityPositionSource> f_157872_ = m_157877_("entity", new EntityPositionSource.Type());

   T m_142281_(FriendlyByteBuf p_157884_);

   void m_142235_(FriendlyByteBuf p_157880_, T p_157881_);

   Codec<T> m_142341_();

   static <S extends PositionSourceType<T>, T extends PositionSource> S m_157877_(String p_157878_, S p_157879_) {
      return Registry.m_122961_(Registry.f_175420_, p_157878_, p_157879_);
   }

   static PositionSource m_157885_(FriendlyByteBuf p_157886_) {
      ResourceLocation resourcelocation = p_157886_.m_130281_();
      return Registry.f_175420_.m_6612_(resourcelocation).orElseThrow(() -> {
         return new IllegalArgumentException("Unknown position source type " + resourcelocation);
      }).m_142281_(p_157886_);
   }

   static <T extends PositionSource> void m_157874_(T p_157875_, FriendlyByteBuf p_157876_) {
      p_157876_.m_130085_(Registry.f_175420_.m_7981_(p_157875_.m_142510_()));
      ((PositionSourceType<T>)p_157875_.m_142510_()).m_142235_(p_157876_, p_157875_);
   }
}