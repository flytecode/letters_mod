package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StationaryItemParticle extends TextureSheetParticle {
   StationaryItemParticle(ClientLevel p_172356_, double p_172357_, double p_172358_, double p_172359_, ItemLike p_172360_) {
      super(p_172356_, p_172357_, p_172358_, p_172359_);
      this.m_108337_(Minecraft.m_91087_().m_91291_().m_115103_().m_109401_(p_172360_));
      this.f_107226_ = 0.0F;
      this.f_107225_ = 80;
      this.f_107219_ = false;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107429_;
   }

   public float m_5902_(float p_172363_) {
      return 0.5F;
   }

   @OnlyIn(Dist.CLIENT)
   public static class BarrierProvider implements ParticleProvider<SimpleParticleType> {
      public Particle m_6966_(SimpleParticleType p_172375_, ClientLevel p_172376_, double p_172377_, double p_172378_, double p_172379_, double p_172380_, double p_172381_, double p_172382_) {
         return new StationaryItemParticle(p_172376_, p_172377_, p_172378_, p_172379_, Blocks.f_50375_.m_5456_());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LightProvider implements ParticleProvider<SimpleParticleType> {
      public Particle m_6966_(SimpleParticleType p_172394_, ClientLevel p_172395_, double p_172396_, double p_172397_, double p_172398_, double p_172399_, double p_172400_, double p_172401_) {
         return new StationaryItemParticle(p_172395_, p_172396_, p_172397_, p_172398_, Items.f_151033_);
      }
   }
}