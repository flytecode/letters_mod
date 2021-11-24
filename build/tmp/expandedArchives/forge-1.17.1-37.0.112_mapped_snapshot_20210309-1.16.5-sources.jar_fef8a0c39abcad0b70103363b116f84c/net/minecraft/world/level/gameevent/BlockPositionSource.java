package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public class BlockPositionSource implements PositionSource {
   public static final Codec<BlockPositionSource> f_157699_ = RecordCodecBuilder.create((p_157710_) -> {
      return p_157710_.group(BlockPos.f_121852_.fieldOf("pos").xmap(Optional::of, Optional::get).forGetter((p_157712_) -> {
         return p_157712_.f_157700_;
      })).apply(p_157710_, BlockPositionSource::new);
   });
   final Optional<BlockPos> f_157700_;

   public BlockPositionSource(BlockPos p_157703_) {
      this(Optional.of(p_157703_));
   }

   public BlockPositionSource(Optional<BlockPos> p_157705_) {
      this.f_157700_ = p_157705_;
   }

   public Optional<BlockPos> m_142502_(Level p_157708_) {
      return this.f_157700_;
   }

   public PositionSourceType<?> m_142510_() {
      return PositionSourceType.f_157871_;
   }

   public static class Type implements PositionSourceType<BlockPositionSource> {
      public BlockPositionSource m_142281_(FriendlyByteBuf p_157716_) {
         return new BlockPositionSource(Optional.of(p_157716_.m_130135_()));
      }

      public void m_142235_(FriendlyByteBuf p_157718_, BlockPositionSource p_157719_) {
         p_157719_.f_157700_.ifPresent(p_157718_::m_130064_);
      }

      public Codec<BlockPositionSource> m_142341_() {
         return BlockPositionSource.f_157699_;
      }
   }
}