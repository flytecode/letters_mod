package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class DustParticleOptions extends DustParticleOptionsBase {
   public static final Vector3f f_175788_ = new Vector3f(Vec3.m_82501_(16711680));
   public static final DustParticleOptions f_123656_ = new DustParticleOptions(f_175788_, 1.0F);
   public static final Codec<DustParticleOptions> f_123657_ = RecordCodecBuilder.create((p_175793_) -> {
      return p_175793_.group(Vector3f.f_176762_.fieldOf("color").forGetter((p_175797_) -> {
         return p_175797_.f_175800_;
      }), Codec.FLOAT.fieldOf("scale").forGetter((p_175795_) -> {
         return p_175795_.f_175801_;
      })).apply(p_175793_, DustParticleOptions::new);
   });
   public static final ParticleOptions.Deserializer<DustParticleOptions> f_123658_ = new ParticleOptions.Deserializer<DustParticleOptions>() {
      public DustParticleOptions m_5739_(ParticleType<DustParticleOptions> p_123689_, StringReader p_123690_) throws CommandSyntaxException {
         Vector3f vector3f = DustParticleOptionsBase.m_175806_(p_123690_);
         p_123690_.expect(' ');
         float f = p_123690_.readFloat();
         return new DustParticleOptions(vector3f, f);
      }

      public DustParticleOptions m_6507_(ParticleType<DustParticleOptions> p_123692_, FriendlyByteBuf p_123693_) {
         return new DustParticleOptions(DustParticleOptionsBase.m_175810_(p_123693_), p_123693_.readFloat());
      }
   };

   public DustParticleOptions(Vector3f p_175790_, float p_175791_) {
      super(p_175790_, p_175791_);
   }

   public ParticleType<DustParticleOptions> m_6012_() {
      return ParticleTypes.f_123805_;
   }
}