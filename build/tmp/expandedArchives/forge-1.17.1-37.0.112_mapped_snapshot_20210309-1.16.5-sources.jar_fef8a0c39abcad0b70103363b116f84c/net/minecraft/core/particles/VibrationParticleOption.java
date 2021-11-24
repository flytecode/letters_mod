package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;

public class VibrationParticleOption implements ParticleOptions {
   public static final Codec<VibrationParticleOption> f_175842_ = RecordCodecBuilder.create((p_175850_) -> {
      return p_175850_.group(VibrationPath.f_157929_.fieldOf("vibration").forGetter((p_175852_) -> {
         return p_175852_.f_175844_;
      })).apply(p_175850_, VibrationParticleOption::new);
   });
   public static final ParticleOptions.Deserializer<VibrationParticleOption> f_175843_ = new ParticleOptions.Deserializer<VibrationParticleOption>() {
      public VibrationParticleOption m_5739_(ParticleType<VibrationParticleOption> p_175859_, StringReader p_175860_) throws CommandSyntaxException {
         p_175860_.expect(' ');
         float f = (float)p_175860_.readDouble();
         p_175860_.expect(' ');
         float f1 = (float)p_175860_.readDouble();
         p_175860_.expect(' ');
         float f2 = (float)p_175860_.readDouble();
         p_175860_.expect(' ');
         float f3 = (float)p_175860_.readDouble();
         p_175860_.expect(' ');
         float f4 = (float)p_175860_.readDouble();
         p_175860_.expect(' ');
         float f5 = (float)p_175860_.readDouble();
         p_175860_.expect(' ');
         int i = p_175860_.readInt();
         BlockPos blockpos = new BlockPos((double)f, (double)f1, (double)f2);
         BlockPos blockpos1 = new BlockPos((double)f3, (double)f4, (double)f5);
         return new VibrationParticleOption(new VibrationPath(blockpos, new BlockPositionSource(blockpos1), i));
      }

      public VibrationParticleOption m_6507_(ParticleType<VibrationParticleOption> p_175862_, FriendlyByteBuf p_175863_) {
         VibrationPath vibrationpath = VibrationPath.m_157943_(p_175863_);
         return new VibrationParticleOption(vibrationpath);
      }
   };
   private final VibrationPath f_175844_;

   public VibrationParticleOption(VibrationPath p_175847_) {
      this.f_175844_ = p_175847_;
   }

   public void m_7711_(FriendlyByteBuf p_175854_) {
      VibrationPath.m_157945_(p_175854_, this.f_175844_);
   }

   public String m_5942_() {
      BlockPos blockpos = this.f_175844_.m_157948_();
      double d0 = (double)blockpos.m_123341_();
      double d1 = (double)blockpos.m_123342_();
      double d2 = (double)blockpos.m_123343_();
      return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f %d", Registry.f_122829_.m_7981_(this.m_6012_()), d0, d1, d2, d0, d1, d2, this.f_175844_.m_157938_());
   }

   public ParticleType<VibrationParticleOption> m_6012_() {
      return ParticleTypes.f_175820_;
   }

   public VibrationPath m_175856_() {
      return this.f_175844_;
   }
}