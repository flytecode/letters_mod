package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;

public class BiomeSpecialEffects {
   public static final Codec<BiomeSpecialEffects> f_47926_ = RecordCodecBuilder.create((p_47971_) -> {
      return p_47971_.group(Codec.INT.fieldOf("fog_color").forGetter((p_151782_) -> {
         return p_151782_.f_47927_;
      }), Codec.INT.fieldOf("water_color").forGetter((p_151780_) -> {
         return p_151780_.f_47928_;
      }), Codec.INT.fieldOf("water_fog_color").forGetter((p_151778_) -> {
         return p_151778_.f_47929_;
      }), Codec.INT.fieldOf("sky_color").forGetter((p_151776_) -> {
         return p_151776_.f_47930_;
      }), Codec.INT.optionalFieldOf("foliage_color").forGetter((p_151774_) -> {
         return p_151774_.f_47931_;
      }), Codec.INT.optionalFieldOf("grass_color").forGetter((p_151772_) -> {
         return p_151772_.f_47932_;
      }), BiomeSpecialEffects.GrassColorModifier.f_48050_.optionalFieldOf("grass_color_modifier", BiomeSpecialEffects.GrassColorModifier.NONE).forGetter((p_151770_) -> {
         return p_151770_.f_47933_;
      }), AmbientParticleSettings.f_47412_.optionalFieldOf("particle").forGetter((p_151768_) -> {
         return p_151768_.f_47934_;
      }), SoundEvent.f_11655_.optionalFieldOf("ambient_sound").forGetter((p_151766_) -> {
         return p_151766_.f_47935_;
      }), AmbientMoodSettings.f_47386_.optionalFieldOf("mood_sound").forGetter((p_151764_) -> {
         return p_151764_.f_47936_;
      }), AmbientAdditionsSettings.f_47371_.optionalFieldOf("additions_sound").forGetter((p_151762_) -> {
         return p_151762_.f_47937_;
      }), Music.f_11620_.optionalFieldOf("music").forGetter((p_151760_) -> {
         return p_151760_.f_47938_;
      })).apply(p_47971_, BiomeSpecialEffects::new);
   });
   private final int f_47927_;
   private final int f_47928_;
   private final int f_47929_;
   private final int f_47930_;
   private final Optional<Integer> f_47931_;
   private final Optional<Integer> f_47932_;
   private final BiomeSpecialEffects.GrassColorModifier f_47933_;
   private final Optional<AmbientParticleSettings> f_47934_;
   private final Optional<SoundEvent> f_47935_;
   private final Optional<AmbientMoodSettings> f_47936_;
   private final Optional<AmbientAdditionsSettings> f_47937_;
   private final Optional<Music> f_47938_;

   BiomeSpecialEffects(int p_47941_, int p_47942_, int p_47943_, int p_47944_, Optional<Integer> p_47945_, Optional<Integer> p_47946_, BiomeSpecialEffects.GrassColorModifier p_47947_, Optional<AmbientParticleSettings> p_47948_, Optional<SoundEvent> p_47949_, Optional<AmbientMoodSettings> p_47950_, Optional<AmbientAdditionsSettings> p_47951_, Optional<Music> p_47952_) {
      this.f_47927_ = p_47941_;
      this.f_47928_ = p_47942_;
      this.f_47929_ = p_47943_;
      this.f_47930_ = p_47944_;
      this.f_47931_ = p_47945_;
      this.f_47932_ = p_47946_;
      this.f_47933_ = p_47947_;
      this.f_47934_ = p_47948_;
      this.f_47935_ = p_47949_;
      this.f_47936_ = p_47950_;
      this.f_47937_ = p_47951_;
      this.f_47938_ = p_47952_;
   }

   public int m_47967_() {
      return this.f_47927_;
   }

   public int m_47972_() {
      return this.f_47928_;
   }

   public int m_47975_() {
      return this.f_47929_;
   }

   public int m_47978_() {
      return this.f_47930_;
   }

   public Optional<Integer> m_47981_() {
      return this.f_47931_;
   }

   public Optional<Integer> m_47984_() {
      return this.f_47932_;
   }

   public BiomeSpecialEffects.GrassColorModifier m_47987_() {
      return this.f_47933_;
   }

   public Optional<AmbientParticleSettings> m_47990_() {
      return this.f_47934_;
   }

   public Optional<SoundEvent> m_47993_() {
      return this.f_47935_;
   }

   public Optional<AmbientMoodSettings> m_47996_() {
      return this.f_47936_;
   }

   public Optional<AmbientAdditionsSettings> m_47999_() {
      return this.f_47937_;
   }

   public Optional<Music> m_48002_() {
      return this.f_47938_;
   }

   public static class Builder {
      private OptionalInt f_48005_ = OptionalInt.empty();
      private OptionalInt f_48006_ = OptionalInt.empty();
      private OptionalInt f_48007_ = OptionalInt.empty();
      private OptionalInt f_48008_ = OptionalInt.empty();
      private Optional<Integer> f_48009_ = Optional.empty();
      private Optional<Integer> f_48010_ = Optional.empty();
      private BiomeSpecialEffects.GrassColorModifier f_48011_ = BiomeSpecialEffects.GrassColorModifier.NONE;
      private Optional<AmbientParticleSettings> f_48012_ = Optional.empty();
      private Optional<SoundEvent> f_48013_ = Optional.empty();
      private Optional<AmbientMoodSettings> f_48014_ = Optional.empty();
      private Optional<AmbientAdditionsSettings> f_48015_ = Optional.empty();
      private Optional<Music> f_48016_ = Optional.empty();

      public BiomeSpecialEffects.Builder m_48019_(int p_48020_) {
         this.f_48005_ = OptionalInt.of(p_48020_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48034_(int p_48035_) {
         this.f_48006_ = OptionalInt.of(p_48035_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48037_(int p_48038_) {
         this.f_48007_ = OptionalInt.of(p_48038_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48040_(int p_48041_) {
         this.f_48008_ = OptionalInt.of(p_48041_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48043_(int p_48044_) {
         this.f_48009_ = Optional.of(p_48044_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48045_(int p_48046_) {
         this.f_48010_ = Optional.of(p_48046_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48031_(BiomeSpecialEffects.GrassColorModifier p_48032_) {
         this.f_48011_ = p_48032_;
         return this;
      }

      public BiomeSpecialEffects.Builder m_48029_(AmbientParticleSettings p_48030_) {
         this.f_48012_ = Optional.of(p_48030_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48023_(SoundEvent p_48024_) {
         this.f_48013_ = Optional.of(p_48024_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48027_(AmbientMoodSettings p_48028_) {
         this.f_48014_ = Optional.of(p_48028_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48025_(AmbientAdditionsSettings p_48026_) {
         this.f_48015_ = Optional.of(p_48026_);
         return this;
      }

      public BiomeSpecialEffects.Builder m_48021_(Music p_48022_) {
         this.f_48016_ = Optional.of(p_48022_);
         return this;
      }

      public BiomeSpecialEffects m_48018_() {
         return new BiomeSpecialEffects(this.f_48005_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'fog' color.");
         }), this.f_48006_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water' color.");
         }), this.f_48007_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'water fog' color.");
         }), this.f_48008_.orElseThrow(() -> {
            return new IllegalStateException("Missing 'sky' color.");
         }), this.f_48009_, this.f_48010_, this.f_48011_, this.f_48012_, this.f_48013_, this.f_48014_, this.f_48015_, this.f_48016_);
      }
   }

   public static enum GrassColorModifier implements StringRepresentable, net.minecraftforge.common.IExtensibleEnum {
      NONE("none") {
         public int m_6583_(double p_48081_, double p_48082_, int p_48083_) {
            return p_48083_;
         }
      },
      DARK_FOREST("dark_forest") {
         public int m_6583_(double p_48089_, double p_48090_, int p_48091_) {
            return (p_48091_ & 16711422) + 2634762 >> 1;
         }
      },
      SWAMP("swamp") {
         public int m_6583_(double p_48097_, double p_48098_, int p_48099_) {
            double d0 = Biome.f_47433_.m_75449_(p_48097_ * 0.0225D, p_48098_ * 0.0225D, false);
            return d0 < -0.1D ? 5011004 : 6975545;
         }
      };

      private final String f_48051_;
      public static final Codec<BiomeSpecialEffects.GrassColorModifier> f_48050_ = net.minecraftforge.common.IExtensibleEnum.createCodecForExtensibleEnum(BiomeSpecialEffects.GrassColorModifier::values, BiomeSpecialEffects.GrassColorModifier::m_48070_);
      private static final Map<String, BiomeSpecialEffects.GrassColorModifier> f_48052_ = Arrays.stream(values()).collect(Collectors.toMap(BiomeSpecialEffects.GrassColorModifier::m_48072_, (p_48069_) -> {
         return p_48069_;
      }));

      public int m_6583_(double p_48065_, double p_48066_, int p_48067_) {
         return delegate.modifyGrassColor(p_48065_, p_48066_, p_48067_);
      }

      GrassColorModifier(String p_48058_) {
         this.f_48051_ = p_48058_;
      }

      private ColorModifier delegate;
      private GrassColorModifier(String name, ColorModifier delegate) {
         this(name);
         this.delegate = delegate;
      }
      public static GrassColorModifier create(String name, String id, ColorModifier delegate) {
         throw new IllegalStateException("Enum not extended");
      }
      @Override
      public void init() {
         f_48052_.put(this.m_48072_(), this);
      }
      @FunctionalInterface
      public interface ColorModifier {
         int modifyGrassColor(double x, double z, int color);
      }
      public String m_48072_() {
         return this.f_48051_;
      }

      public String m_7912_() {
         return this.f_48051_;
      }

      public static BiomeSpecialEffects.GrassColorModifier m_48070_(String p_48071_) {
         return f_48052_.get(p_48071_);
      }
   }
}
