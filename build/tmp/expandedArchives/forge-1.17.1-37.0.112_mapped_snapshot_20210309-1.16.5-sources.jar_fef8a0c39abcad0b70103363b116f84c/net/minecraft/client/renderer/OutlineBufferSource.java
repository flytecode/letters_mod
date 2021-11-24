package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultedVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import java.util.Optional;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OutlineBufferSource implements MultiBufferSource {
   private final MultiBufferSource.BufferSource f_109920_;
   private final MultiBufferSource.BufferSource f_109921_ = MultiBufferSource.m_109898_(new BufferBuilder(256));
   private int f_109922_ = 255;
   private int f_109923_ = 255;
   private int f_109924_ = 255;
   private int f_109925_ = 255;

   public OutlineBufferSource(MultiBufferSource.BufferSource p_109927_) {
      this.f_109920_ = p_109927_;
   }

   public VertexConsumer m_6299_(RenderType p_109935_) {
      if (p_109935_.m_5492_()) {
         VertexConsumer vertexconsumer2 = this.f_109921_.m_6299_(p_109935_);
         return new OutlineBufferSource.EntityOutlineGenerator(vertexconsumer2, this.f_109922_, this.f_109923_, this.f_109924_, this.f_109925_);
      } else {
         VertexConsumer vertexconsumer = this.f_109920_.m_6299_(p_109935_);
         Optional<RenderType> optional = p_109935_.m_7280_();
         if (optional.isPresent()) {
            VertexConsumer vertexconsumer1 = this.f_109921_.m_6299_(optional.get());
            OutlineBufferSource.EntityOutlineGenerator outlinebuffersource$entityoutlinegenerator = new OutlineBufferSource.EntityOutlineGenerator(vertexconsumer1, this.f_109922_, this.f_109923_, this.f_109924_, this.f_109925_);
            return VertexMultiConsumer.m_86168_(outlinebuffersource$entityoutlinegenerator, vertexconsumer);
         } else {
            return vertexconsumer;
         }
      }
   }

   public void m_109929_(int p_109930_, int p_109931_, int p_109932_, int p_109933_) {
      this.f_109922_ = p_109930_;
      this.f_109923_ = p_109931_;
      this.f_109924_ = p_109932_;
      this.f_109925_ = p_109933_;
   }

   public void m_109928_() {
      this.f_109921_.m_109911_();
   }

   @OnlyIn(Dist.CLIENT)
   static class EntityOutlineGenerator extends DefaultedVertexConsumer {
      private final VertexConsumer f_109936_;
      private double f_109937_;
      private double f_109938_;
      private double f_109939_;
      private float f_109940_;
      private float f_109941_;

      EntityOutlineGenerator(VertexConsumer p_109943_, int p_109944_, int p_109945_, int p_109946_, int p_109947_) {
         this.f_109936_ = p_109943_;
         super.m_142461_(p_109944_, p_109945_, p_109946_, p_109947_);
      }

      public void m_142461_(int p_109993_, int p_109994_, int p_109995_, int p_109996_) {
      }

      public void m_141991_() {
      }

      public VertexConsumer m_5483_(double p_109956_, double p_109957_, double p_109958_) {
         this.f_109937_ = p_109956_;
         this.f_109938_ = p_109957_;
         this.f_109939_ = p_109958_;
         return this;
      }

      public VertexConsumer m_6122_(int p_109981_, int p_109982_, int p_109983_, int p_109984_) {
         return this;
      }

      public VertexConsumer m_7421_(float p_109960_, float p_109961_) {
         this.f_109940_ = p_109960_;
         this.f_109941_ = p_109961_;
         return this;
      }

      public VertexConsumer m_7122_(int p_109978_, int p_109979_) {
         return this;
      }

      public VertexConsumer m_7120_(int p_109990_, int p_109991_) {
         return this;
      }

      public VertexConsumer m_5601_(float p_109986_, float p_109987_, float p_109988_) {
         return this;
      }

      public void m_5954_(float p_109963_, float p_109964_, float p_109965_, float p_109966_, float p_109967_, float p_109968_, float p_109969_, float p_109970_, float p_109971_, int p_109972_, int p_109973_, float p_109974_, float p_109975_, float p_109976_) {
         this.f_109936_.m_5483_((double)p_109963_, (double)p_109964_, (double)p_109965_).m_6122_(this.f_85825_, this.f_85826_, this.f_85827_, this.f_85828_).m_7421_(p_109970_, p_109971_).m_5752_();
      }

      public void m_5752_() {
         this.f_109936_.m_5483_(this.f_109937_, this.f_109938_, this.f_109939_).m_6122_(this.f_85825_, this.f_85826_, this.f_85827_, this.f_85828_).m_7421_(this.f_109940_, this.f_109941_).m_5752_();
      }
   }
}