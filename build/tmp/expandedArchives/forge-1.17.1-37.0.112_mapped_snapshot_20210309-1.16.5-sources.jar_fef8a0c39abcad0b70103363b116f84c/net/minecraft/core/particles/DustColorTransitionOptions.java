package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class DustColorTransitionOptions extends DustParticleOptionsBase {
   public static final Vector3f f_175751_ = new Vector3f(Vec3.m_82501_(3790560));
   public static final DustColorTransitionOptions f_175752_ = new DustColorTransitionOptions(f_175751_, DustParticleOptions.f_175788_, 1.0F);
   public static final Codec<DustColorTransitionOptions> f_175753_ = RecordCodecBuilder.create((p_175763_) -> {
      return p_175763_.group(Vector3f.f_176762_.fieldOf("fromColor").forGetter((p_175773_) -> {
         return p_175773_.f_175800_;
      }), Vector3f.f_176762_.fieldOf("toColor").forGetter((p_175770_) -> {
         return p_175770_.f_175755_;
      }), Codec.FLOAT.fieldOf("scale").forGetter((p_175765_) -> {
         return p_175765_.f_175801_;
      })).apply(p_175763_, DustColorTransitionOptions::new);
   });
   public static final ParticleOptions.Deserializer<DustColorTransitionOptions> f_175754_ = new ParticleOptions.Deserializer<DustColorTransitionOptions>() {
      public DustColorTransitionOptions m_5739_(ParticleType<DustColorTransitionOptions> p_175777_, StringReader p_175778_) throws CommandSyntaxException {
         Vector3f vector3f = DustParticleOptionsBase.m_175806_(p_175778_);
         p_175778_.expect(' ');
         float f = p_175778_.readFloat();
         Vector3f vector3f1 = DustParticleOptionsBase.m_175806_(p_175778_);
         return new DustColorTransitionOptions(vector3f, vector3f1, f);
      }

      public DustColorTransitionOptions m_6507_(ParticleType<DustColorTransitionOptions> p_175780_, FriendlyByteBuf p_175781_) {
         Vector3f vector3f = DustParticleOptionsBase.m_175810_(p_175781_);
         float f = p_175781_.readFloat();
         Vector3f vector3f1 = DustParticleOptionsBase.m_175810_(p_175781_);
         return new DustColorTransitionOptions(vector3f, vector3f1, f);
      }
   };
   private final Vector3f f_175755_;

   public DustColorTransitionOptions(Vector3f p_175758_, Vector3f p_175759_, float p_175760_) {
      super(p_175758_, p_175760_);
      this.f_175755_ = p_175759_;
   }

   public Vector3f m_175771_() {
      return this.f_175800_;
   }

   public Vector3f m_175774_() {
      return this.f_175755_;
   }

   public void m_7711_(FriendlyByteBuf p_175767_) {
      super.m_7711_(p_175767_);
      p_175767_.writeFloat(this.f_175755_.m_122239_());
      p_175767_.writeFloat(this.f_175755_.m_122260_());
      p_175767_.writeFloat(this.f_175755_.m_122269_());
   }

   public String m_5942_() {
      return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f", Registry.f_122829_.m_7981_(this.m_6012_()), this.f_175800_.m_122239_(), this.f_175800_.m_122260_(), this.f_175800_.m_122269_(), this.f_175801_, this.f_175755_.m_122239_(), this.f_175755_.m_122260_(), this.f_175755_.m_122269_());
   }

   public ParticleType<DustColorTransitionOptions> m_6012_() {
      return ParticleTypes.f_175836_;
   }
}