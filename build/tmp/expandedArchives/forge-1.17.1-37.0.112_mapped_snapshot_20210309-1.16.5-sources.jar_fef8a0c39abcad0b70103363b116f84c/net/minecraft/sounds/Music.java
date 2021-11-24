package net.minecraft.sounds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Music {
   public static final Codec<Music> f_11620_ = RecordCodecBuilder.create((p_11635_) -> {
      return p_11635_.group(SoundEvent.f_11655_.fieldOf("sound").forGetter((p_144041_) -> {
         return p_144041_.f_11621_;
      }), Codec.INT.fieldOf("min_delay").forGetter((p_144039_) -> {
         return p_144039_.f_11622_;
      }), Codec.INT.fieldOf("max_delay").forGetter((p_144037_) -> {
         return p_144037_.f_11623_;
      }), Codec.BOOL.fieldOf("replace_current_music").forGetter((p_144035_) -> {
         return p_144035_.f_11624_;
      })).apply(p_11635_, Music::new);
   });
   private final SoundEvent f_11621_;
   private final int f_11622_;
   private final int f_11623_;
   private final boolean f_11624_;

   public Music(SoundEvent p_11627_, int p_11628_, int p_11629_, boolean p_11630_) {
      this.f_11621_ = p_11627_;
      this.f_11622_ = p_11628_;
      this.f_11623_ = p_11629_;
      this.f_11624_ = p_11630_;
   }

   public SoundEvent m_11631_() {
      return this.f_11621_;
   }

   public int m_11636_() {
      return this.f_11622_;
   }

   public int m_11639_() {
      return this.f_11623_;
   }

   public boolean m_11642_() {
      return this.f_11624_;
   }
}