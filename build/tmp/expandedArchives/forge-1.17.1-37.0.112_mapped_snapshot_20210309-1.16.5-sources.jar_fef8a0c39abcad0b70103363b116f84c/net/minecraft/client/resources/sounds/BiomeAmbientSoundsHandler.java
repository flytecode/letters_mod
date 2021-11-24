package net.minecraft.client.resources.sounds;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiomeAmbientSoundsHandler implements AmbientSoundHandler {
   private static final int f_174920_ = 40;
   private static final float f_174921_ = 0.001F;
   private final LocalPlayer f_119629_;
   private final SoundManager f_119630_;
   private final BiomeManager f_119631_;
   private final Random f_119632_;
   private final Object2ObjectArrayMap<Biome, BiomeAmbientSoundsHandler.LoopSoundInstance> f_119633_ = new Object2ObjectArrayMap<>();
   private Optional<AmbientMoodSettings> f_119634_ = Optional.empty();
   private Optional<AmbientAdditionsSettings> f_119635_ = Optional.empty();
   private float f_119636_;
   private Biome f_119637_;

   public BiomeAmbientSoundsHandler(LocalPlayer p_119639_, SoundManager p_119640_, BiomeManager p_119641_) {
      this.f_119632_ = p_119639_.f_19853_.m_5822_();
      this.f_119629_ = p_119639_;
      this.f_119630_ = p_119640_;
      this.f_119631_ = p_119641_;
   }

   public float m_119654_() {
      return this.f_119636_;
   }

   public void m_7551_() {
      this.f_119633_.values().removeIf(AbstractTickableSoundInstance::m_7801_);
      Biome biome = this.f_119631_.m_47869_(this.f_119629_.m_20185_(), this.f_119629_.m_20186_(), this.f_119629_.m_20189_());
      if (biome != this.f_119637_) {
         this.f_119637_ = biome;
         this.f_119634_ = biome.m_47564_();
         this.f_119635_ = biome.m_47565_();
         this.f_119633_.values().forEach(BiomeAmbientSoundsHandler.LoopSoundInstance::m_119659_);
         biome.m_47563_().ifPresent((p_119653_) -> {
            this.f_119633_.compute(biome, (p_174924_, p_174925_) -> {
               if (p_174925_ == null) {
                  p_174925_ = new BiomeAmbientSoundsHandler.LoopSoundInstance(p_119653_);
                  this.f_119630_.m_120367_(p_174925_);
               }

               p_174925_.m_119660_();
               return p_174925_;
            });
         });
      }

      this.f_119635_.ifPresent((p_119648_) -> {
         if (this.f_119632_.nextDouble() < p_119648_.m_47383_()) {
            this.f_119630_.m_120367_(SimpleSoundInstance.m_119759_(p_119648_.m_47378_()));
         }

      });
      this.f_119634_.ifPresent((p_119650_) -> {
         Level level = this.f_119629_.f_19853_;
         int i = p_119650_.m_47406_() * 2 + 1;
         BlockPos blockpos = new BlockPos(this.f_119629_.m_20185_() + (double)this.f_119632_.nextInt(i) - (double)p_119650_.m_47406_(), this.f_119629_.m_20188_() + (double)this.f_119632_.nextInt(i) - (double)p_119650_.m_47406_(), this.f_119629_.m_20189_() + (double)this.f_119632_.nextInt(i) - (double)p_119650_.m_47406_());
         int j = level.m_45517_(LightLayer.SKY, blockpos);
         if (j > 0) {
            this.f_119636_ -= (float)j / (float)level.m_7469_() * 0.001F;
         } else {
            this.f_119636_ -= (float)(level.m_45517_(LightLayer.BLOCK, blockpos) - 1) / (float)p_119650_.m_47403_();
         }

         if (this.f_119636_ >= 1.0F) {
            double d0 = (double)blockpos.m_123341_() + 0.5D;
            double d1 = (double)blockpos.m_123342_() + 0.5D;
            double d2 = (double)blockpos.m_123343_() + 0.5D;
            double d3 = d0 - this.f_119629_.m_20185_();
            double d4 = d1 - this.f_119629_.m_20188_();
            double d5 = d2 - this.f_119629_.m_20189_();
            double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
            double d7 = d6 + p_119650_.m_47409_();
            SimpleSoundInstance simplesoundinstance = SimpleSoundInstance.m_119761_(p_119650_.m_47398_(), this.f_119629_.m_20185_() + d3 / d6 * d7, this.f_119629_.m_20188_() + d4 / d6 * d7, this.f_119629_.m_20189_() + d5 / d6 * d7);
            this.f_119630_.m_120367_(simplesoundinstance);
            this.f_119636_ = 0.0F;
         } else {
            this.f_119636_ = Math.max(this.f_119636_, 0.0F);
         }

      });
   }

   @OnlyIn(Dist.CLIENT)
   public static class LoopSoundInstance extends AbstractTickableSoundInstance {
      private int f_119655_;
      private int f_119656_;

      public LoopSoundInstance(SoundEvent p_119658_) {
         super(p_119658_, SoundSource.AMBIENT);
         this.f_119578_ = true;
         this.f_119579_ = 0;
         this.f_119573_ = 1.0F;
         this.f_119582_ = true;
      }

      public void m_7788_() {
         if (this.f_119656_ < 0) {
            this.m_119609_();
         }

         this.f_119656_ += this.f_119655_;
         this.f_119573_ = Mth.m_14036_((float)this.f_119656_ / 40.0F, 0.0F, 1.0F);
      }

      public void m_119659_() {
         this.f_119656_ = Math.min(this.f_119656_, 40);
         this.f_119655_ = -1;
      }

      public void m_119660_() {
         this.f_119656_ = Math.max(0, this.f_119656_);
         this.f_119655_ = 1;
      }
   }
}