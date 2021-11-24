package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerrainParticle extends TextureSheetParticle {
   private final BlockPos f_108280_;
   private final float f_108277_;
   private final float f_108278_;

   public TerrainParticle(ClientLevel p_108282_, double p_108283_, double p_108284_, double p_108285_, double p_108286_, double p_108287_, double p_108288_, BlockState p_108289_) {
      this(p_108282_, p_108283_, p_108284_, p_108285_, p_108286_, p_108287_, p_108288_, p_108289_, new BlockPos(p_108283_, p_108284_, p_108285_));
   }

   public TerrainParticle(ClientLevel p_172451_, double p_172452_, double p_172453_, double p_172454_, double p_172455_, double p_172456_, double p_172457_, BlockState p_172458_, BlockPos p_172459_) {
      super(p_172451_, p_172452_, p_172453_, p_172454_, p_172455_, p_172456_, p_172457_);
      this.f_108280_ = p_172459_;
      this.m_108337_(Minecraft.m_91087_().m_91289_().m_110907_().m_110882_(p_172458_));
      this.f_107226_ = 1.0F;
      this.f_107227_ = 0.6F;
      this.f_107228_ = 0.6F;
      this.f_107229_ = 0.6F;
      if (!p_172458_.m_60713_(Blocks.f_50440_)) {
         int i = Minecraft.m_91087_().m_91298_().m_92577_(p_172458_, p_172451_, p_172459_, 0);
         this.f_107227_ *= (float)(i >> 16 & 255) / 255.0F;
         this.f_107228_ *= (float)(i >> 8 & 255) / 255.0F;
         this.f_107229_ *= (float)(i & 255) / 255.0F;
      }

      this.f_107663_ /= 2.0F;
      this.f_108277_ = this.f_107223_.nextFloat() * 3.0F;
      this.f_108278_ = this.f_107223_.nextFloat() * 3.0F;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107429_;
   }

   protected float m_5970_() {
      return this.f_108321_.m_118367_((double)((this.f_108277_ + 1.0F) / 4.0F * 16.0F));
   }

   protected float m_5952_() {
      return this.f_108321_.m_118367_((double)(this.f_108277_ / 4.0F * 16.0F));
   }

   protected float m_5951_() {
      return this.f_108321_.m_118393_((double)(this.f_108278_ / 4.0F * 16.0F));
   }

   protected float m_5950_() {
      return this.f_108321_.m_118393_((double)((this.f_108278_ + 1.0F) / 4.0F * 16.0F));
   }

   public int m_6355_(float p_108291_) {
      int i = super.m_6355_(p_108291_);
      return i == 0 && this.f_107208_.m_46805_(this.f_108280_) ? LevelRenderer.m_109541_(this.f_107208_, this.f_108280_) : i;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<BlockParticleOption> {
      public Particle m_6966_(BlockParticleOption p_108304_, ClientLevel p_108305_, double p_108306_, double p_108307_, double p_108308_, double p_108309_, double p_108310_, double p_108311_) {
         BlockState blockstate = p_108304_.m_123642_();
         return !blockstate.m_60795_() && !blockstate.m_60713_(Blocks.f_50110_) ? (new TerrainParticle(p_108305_, p_108306_, p_108307_, p_108308_, p_108309_, p_108310_, p_108311_, blockstate)).updateSprite(blockstate, p_108304_.getPos()) : null;
      }
   }

   public Particle updateSprite(BlockState state, BlockPos pos) { //FORGE: we cannot assume that the x y z of the particles match the block pos of the block.
      if (pos != null) // There are cases where we are not able to obtain the correct source pos, and need to fallback to the non-model data version
         this.m_108337_(Minecraft.m_91087_().m_91289_().m_110907_().getTexture(state, f_107208_, pos));
      return this;
   }
}
