package net.minecraft.world.level.gameevent.vibrations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;

public class VibrationPath {
   public static final Codec<VibrationPath> f_157929_ = RecordCodecBuilder.create((p_157940_) -> {
      return p_157940_.group(BlockPos.f_121852_.fieldOf("origin").forGetter((p_157953_) -> {
         return p_157953_.f_157930_;
      }), PositionSource.f_157868_.fieldOf("destination").forGetter((p_157950_) -> {
         return p_157950_.f_157931_;
      }), Codec.INT.fieldOf("arrival_in_ticks").forGetter((p_157942_) -> {
         return p_157942_.f_157932_;
      })).apply(p_157940_, VibrationPath::new);
   });
   private final BlockPos f_157930_;
   private final PositionSource f_157931_;
   private final int f_157932_;

   public VibrationPath(BlockPos p_157935_, PositionSource p_157936_, int p_157937_) {
      this.f_157930_ = p_157935_;
      this.f_157931_ = p_157936_;
      this.f_157932_ = p_157937_;
   }

   public int m_157938_() {
      return this.f_157932_;
   }

   public BlockPos m_157948_() {
      return this.f_157930_;
   }

   public PositionSource m_157951_() {
      return this.f_157931_;
   }

   public static VibrationPath m_157943_(FriendlyByteBuf p_157944_) {
      BlockPos blockpos = p_157944_.m_130135_();
      PositionSource positionsource = PositionSourceType.m_157885_(p_157944_);
      int i = p_157944_.m_130242_();
      return new VibrationPath(blockpos, positionsource, i);
   }

   public static void m_157945_(FriendlyByteBuf p_157946_, VibrationPath p_157947_) {
      p_157946_.m_130064_(p_157947_.f_157930_);
      PositionSourceType.m_157874_(p_157947_.f_157931_, p_157946_);
      p_157946_.m_130130_(p_157947_.f_157932_);
   }
}