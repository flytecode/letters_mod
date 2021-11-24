package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemPickupParticle extends Particle {
   private static final int f_172257_ = 3;
   private final RenderBuffers f_107020_;
   private final Entity f_107021_;
   private final Entity f_107017_;
   private int f_107018_;
   private final EntityRenderDispatcher f_107019_;

   public ItemPickupParticle(EntityRenderDispatcher p_107023_, RenderBuffers p_107024_, ClientLevel p_107025_, Entity p_107026_, Entity p_107027_) {
      this(p_107023_, p_107024_, p_107025_, p_107026_, p_107027_, p_107026_.m_20184_());
   }

   private ItemPickupParticle(EntityRenderDispatcher p_107029_, RenderBuffers p_107030_, ClientLevel p_107031_, Entity p_107032_, Entity p_107033_, Vec3 p_107034_) {
      super(p_107031_, p_107032_.m_20185_(), p_107032_.m_20186_(), p_107032_.m_20189_(), p_107034_.f_82479_, p_107034_.f_82480_, p_107034_.f_82481_);
      this.f_107020_ = p_107030_;
      this.f_107021_ = this.m_107036_(p_107032_);
      this.f_107017_ = p_107033_;
      this.f_107019_ = p_107029_;
   }

   private Entity m_107036_(Entity p_107037_) {
      return (Entity)(!(p_107037_ instanceof ItemEntity) ? p_107037_ : ((ItemEntity)p_107037_).m_32066_());
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   public void m_5744_(VertexConsumer p_107039_, Camera p_107040_, float p_107041_) {
      float f = ((float)this.f_107018_ + p_107041_) / 3.0F;
      f = f * f;
      double d0 = Mth.m_14139_((double)p_107041_, this.f_107017_.f_19790_, this.f_107017_.m_20185_());
      double d1 = Mth.m_14139_((double)p_107041_, this.f_107017_.f_19791_, this.f_107017_.m_20186_()) + 0.5D;
      double d2 = Mth.m_14139_((double)p_107041_, this.f_107017_.f_19792_, this.f_107017_.m_20189_());
      double d3 = Mth.m_14139_((double)f, this.f_107021_.m_20185_(), d0);
      double d4 = Mth.m_14139_((double)f, this.f_107021_.m_20186_(), d1);
      double d5 = Mth.m_14139_((double)f, this.f_107021_.m_20189_(), d2);
      MultiBufferSource.BufferSource multibuffersource$buffersource = this.f_107020_.m_110104_();
      Vec3 vec3 = p_107040_.m_90583_();
      this.f_107019_.m_114384_(this.f_107021_, d3 - vec3.m_7096_(), d4 - vec3.m_7098_(), d5 - vec3.m_7094_(), this.f_107021_.m_146908_(), p_107041_, new PoseStack(), multibuffersource$buffersource, this.f_107019_.m_114394_(this.f_107021_, p_107041_));
      multibuffersource$buffersource.m_109911_();
   }

   public void m_5989_() {
      ++this.f_107018_;
      if (this.f_107018_ == 3) {
         this.m_107274_();
      }

   }
}