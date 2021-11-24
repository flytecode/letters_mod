package net.minecraft.client.model.geom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ModelPart {
   public float f_104200_;
   public float f_104201_;
   public float f_104202_;
   public float f_104203_;
   public float f_104204_;
   public float f_104205_;
   public boolean f_104207_ = true;
   private final List<ModelPart.Cube> f_104212_;
   private final Map<String, ModelPart> f_104213_;

   public ModelPart(List<ModelPart.Cube> p_171306_, Map<String, ModelPart> p_171307_) {
      this.f_104212_ = p_171306_;
      this.f_104213_ = p_171307_;
   }

   public PartPose m_171308_() {
      return PartPose.m_171423_(this.f_104200_, this.f_104201_, this.f_104202_, this.f_104203_, this.f_104204_, this.f_104205_);
   }

   public void m_171322_(PartPose p_171323_) {
      this.f_104200_ = p_171323_.f_171405_;
      this.f_104201_ = p_171323_.f_171406_;
      this.f_104202_ = p_171323_.f_171407_;
      this.f_104203_ = p_171323_.f_171408_;
      this.f_104204_ = p_171323_.f_171409_;
      this.f_104205_ = p_171323_.f_171410_;
   }

   public void m_104315_(ModelPart p_104316_) {
      this.f_104203_ = p_104316_.f_104203_;
      this.f_104204_ = p_104316_.f_104204_;
      this.f_104205_ = p_104316_.f_104205_;
      this.f_104200_ = p_104316_.f_104200_;
      this.f_104201_ = p_104316_.f_104201_;
      this.f_104202_ = p_104316_.f_104202_;
   }

   public ModelPart m_171324_(String p_171325_) {
      ModelPart modelpart = this.f_104213_.get(p_171325_);
      if (modelpart == null) {
         throw new NoSuchElementException("Can't find part " + p_171325_);
      } else {
         return modelpart;
      }
   }

   public void m_104227_(float p_104228_, float p_104229_, float p_104230_) {
      this.f_104200_ = p_104228_;
      this.f_104201_ = p_104229_;
      this.f_104202_ = p_104230_;
   }

   public void m_171327_(float p_171328_, float p_171329_, float p_171330_) {
      this.f_104203_ = p_171328_;
      this.f_104204_ = p_171329_;
      this.f_104205_ = p_171330_;
   }

   public void m_104301_(PoseStack p_104302_, VertexConsumer p_104303_, int p_104304_, int p_104305_) {
      this.m_104306_(p_104302_, p_104303_, p_104304_, p_104305_, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void m_104306_(PoseStack p_104307_, VertexConsumer p_104308_, int p_104309_, int p_104310_, float p_104311_, float p_104312_, float p_104313_, float p_104314_) {
      if (this.f_104207_) {
         if (!this.f_104212_.isEmpty() || !this.f_104213_.isEmpty()) {
            p_104307_.m_85836_();
            this.m_104299_(p_104307_);
            this.m_104290_(p_104307_.m_85850_(), p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);

            for(ModelPart modelpart : this.f_104213_.values()) {
               modelpart.m_104306_(p_104307_, p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);
            }

            p_104307_.m_85849_();
         }
      }
   }

   public void m_171309_(PoseStack p_171310_, ModelPart.Visitor p_171311_) {
      this.m_171312_(p_171310_, p_171311_, "");
   }

   private void m_171312_(PoseStack p_171313_, ModelPart.Visitor p_171314_, String p_171315_) {
      if (!this.f_104212_.isEmpty() || !this.f_104213_.isEmpty()) {
         p_171313_.m_85836_();
         this.m_104299_(p_171313_);
         PoseStack.Pose posestack$pose = p_171313_.m_85850_();

         for(int i = 0; i < this.f_104212_.size(); ++i) {
            p_171314_.m_171341_(posestack$pose, p_171315_, i, this.f_104212_.get(i));
         }

         String s = p_171315_ + "/";
         this.f_104213_.forEach((p_171320_, p_171321_) -> {
            p_171321_.m_171312_(p_171313_, p_171314_, s + p_171320_);
         });
         p_171313_.m_85849_();
      }
   }

   public void m_104299_(PoseStack p_104300_) {
      p_104300_.m_85837_((double)(this.f_104200_ / 16.0F), (double)(this.f_104201_ / 16.0F), (double)(this.f_104202_ / 16.0F));
      if (this.f_104205_ != 0.0F) {
         p_104300_.m_85845_(Vector3f.f_122227_.m_122270_(this.f_104205_));
      }

      if (this.f_104204_ != 0.0F) {
         p_104300_.m_85845_(Vector3f.f_122225_.m_122270_(this.f_104204_));
      }

      if (this.f_104203_ != 0.0F) {
         p_104300_.m_85845_(Vector3f.f_122223_.m_122270_(this.f_104203_));
      }

   }

   private void m_104290_(PoseStack.Pose p_104291_, VertexConsumer p_104292_, int p_104293_, int p_104294_, float p_104295_, float p_104296_, float p_104297_, float p_104298_) {
      for(ModelPart.Cube modelpart$cube : this.f_104212_) {
         modelpart$cube.m_171332_(p_104291_, p_104292_, p_104293_, p_104294_, p_104295_, p_104296_, p_104297_, p_104298_);
      }

   }

   public ModelPart.Cube m_104328_(Random p_104329_) {
      return this.f_104212_.get(p_104329_.nextInt(this.f_104212_.size()));
   }

   public boolean m_171326_() {
      return this.f_104212_.isEmpty();
   }

   public Stream<ModelPart> m_171331_() {
      return Stream.concat(Stream.of(this), this.f_104213_.values().stream().flatMap(ModelPart::m_171331_));
   }

   @OnlyIn(Dist.CLIENT)
   public static class Cube {
      private final ModelPart.Polygon[] f_104341_;
      public final float f_104335_;
      public final float f_104336_;
      public final float f_104337_;
      public final float f_104338_;
      public final float f_104339_;
      public final float f_104340_;

      public Cube(int p_104343_, int p_104344_, float p_104345_, float p_104346_, float p_104347_, float p_104348_, float p_104349_, float p_104350_, float p_104351_, float p_104352_, float p_104353_, boolean p_104354_, float p_104355_, float p_104356_) {
         this.f_104335_ = p_104345_;
         this.f_104336_ = p_104346_;
         this.f_104337_ = p_104347_;
         this.f_104338_ = p_104345_ + p_104348_;
         this.f_104339_ = p_104346_ + p_104349_;
         this.f_104340_ = p_104347_ + p_104350_;
         this.f_104341_ = new ModelPart.Polygon[6];
         float f = p_104345_ + p_104348_;
         float f1 = p_104346_ + p_104349_;
         float f2 = p_104347_ + p_104350_;
         p_104345_ = p_104345_ - p_104351_;
         p_104346_ = p_104346_ - p_104352_;
         p_104347_ = p_104347_ - p_104353_;
         f = f + p_104351_;
         f1 = f1 + p_104352_;
         f2 = f2 + p_104353_;
         if (p_104354_) {
            float f3 = f;
            f = p_104345_;
            p_104345_ = f3;
         }

         ModelPart.Vertex modelpart$vertex7 = new ModelPart.Vertex(p_104345_, p_104346_, p_104347_, 0.0F, 0.0F);
         ModelPart.Vertex modelpart$vertex = new ModelPart.Vertex(f, p_104346_, p_104347_, 0.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex1 = new ModelPart.Vertex(f, f1, p_104347_, 8.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex2 = new ModelPart.Vertex(p_104345_, f1, p_104347_, 8.0F, 0.0F);
         ModelPart.Vertex modelpart$vertex3 = new ModelPart.Vertex(p_104345_, p_104346_, f2, 0.0F, 0.0F);
         ModelPart.Vertex modelpart$vertex4 = new ModelPart.Vertex(f, p_104346_, f2, 0.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex5 = new ModelPart.Vertex(f, f1, f2, 8.0F, 8.0F);
         ModelPart.Vertex modelpart$vertex6 = new ModelPart.Vertex(p_104345_, f1, f2, 8.0F, 0.0F);
         float f4 = (float)p_104343_;
         float f5 = (float)p_104343_ + p_104350_;
         float f6 = (float)p_104343_ + p_104350_ + p_104348_;
         float f7 = (float)p_104343_ + p_104350_ + p_104348_ + p_104348_;
         float f8 = (float)p_104343_ + p_104350_ + p_104348_ + p_104350_;
         float f9 = (float)p_104343_ + p_104350_ + p_104348_ + p_104350_ + p_104348_;
         float f10 = (float)p_104344_;
         float f11 = (float)p_104344_ + p_104350_;
         float f12 = (float)p_104344_ + p_104350_ + p_104349_;
         this.f_104341_[2] = new ModelPart.Polygon(new ModelPart.Vertex[]{modelpart$vertex4, modelpart$vertex3, modelpart$vertex7, modelpart$vertex}, f5, f10, f6, f11, p_104355_, p_104356_, p_104354_, Direction.DOWN);
         this.f_104341_[3] = new ModelPart.Polygon(new ModelPart.Vertex[]{modelpart$vertex1, modelpart$vertex2, modelpart$vertex6, modelpart$vertex5}, f6, f11, f7, f10, p_104355_, p_104356_, p_104354_, Direction.UP);
         this.f_104341_[1] = new ModelPart.Polygon(new ModelPart.Vertex[]{modelpart$vertex7, modelpart$vertex3, modelpart$vertex6, modelpart$vertex2}, f4, f11, f5, f12, p_104355_, p_104356_, p_104354_, Direction.WEST);
         this.f_104341_[4] = new ModelPart.Polygon(new ModelPart.Vertex[]{modelpart$vertex, modelpart$vertex7, modelpart$vertex2, modelpart$vertex1}, f5, f11, f6, f12, p_104355_, p_104356_, p_104354_, Direction.NORTH);
         this.f_104341_[0] = new ModelPart.Polygon(new ModelPart.Vertex[]{modelpart$vertex4, modelpart$vertex, modelpart$vertex1, modelpart$vertex5}, f6, f11, f8, f12, p_104355_, p_104356_, p_104354_, Direction.EAST);
         this.f_104341_[5] = new ModelPart.Polygon(new ModelPart.Vertex[]{modelpart$vertex3, modelpart$vertex4, modelpart$vertex5, modelpart$vertex6}, f8, f11, f9, f12, p_104355_, p_104356_, p_104354_, Direction.SOUTH);
      }

      public void m_171332_(PoseStack.Pose p_171333_, VertexConsumer p_171334_, int p_171335_, int p_171336_, float p_171337_, float p_171338_, float p_171339_, float p_171340_) {
         Matrix4f matrix4f = p_171333_.m_85861_();
         Matrix3f matrix3f = p_171333_.m_85864_();

         for(ModelPart.Polygon modelpart$polygon : this.f_104341_) {
            Vector3f vector3f = modelpart$polygon.f_104360_.m_122281_();
            vector3f.m_122249_(matrix3f);
            float f = vector3f.m_122239_();
            float f1 = vector3f.m_122260_();
            float f2 = vector3f.m_122269_();

            for(ModelPart.Vertex modelpart$vertex : modelpart$polygon.f_104359_) {
               float f3 = modelpart$vertex.f_104371_.m_122239_() / 16.0F;
               float f4 = modelpart$vertex.f_104371_.m_122260_() / 16.0F;
               float f5 = modelpart$vertex.f_104371_.m_122269_() / 16.0F;
               Vector4f vector4f = new Vector4f(f3, f4, f5, 1.0F);
               vector4f.m_123607_(matrix4f);
               p_171334_.m_5954_(vector4f.m_123601_(), vector4f.m_123615_(), vector4f.m_123616_(), p_171337_, p_171338_, p_171339_, p_171340_, modelpart$vertex.f_104372_, modelpart$vertex.f_104373_, p_171336_, p_171335_, f, f1, f2);
            }
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Polygon {
      public final ModelPart.Vertex[] f_104359_;
      public final Vector3f f_104360_;

      public Polygon(ModelPart.Vertex[] p_104362_, float p_104363_, float p_104364_, float p_104365_, float p_104366_, float p_104367_, float p_104368_, boolean p_104369_, Direction p_104370_) {
         this.f_104359_ = p_104362_;
         float f = 0.0F / p_104367_;
         float f1 = 0.0F / p_104368_;
         p_104362_[0] = p_104362_[0].m_104384_(p_104365_ / p_104367_ - f, p_104364_ / p_104368_ + f1);
         p_104362_[1] = p_104362_[1].m_104384_(p_104363_ / p_104367_ + f, p_104364_ / p_104368_ + f1);
         p_104362_[2] = p_104362_[2].m_104384_(p_104363_ / p_104367_ + f, p_104366_ / p_104368_ - f1);
         p_104362_[3] = p_104362_[3].m_104384_(p_104365_ / p_104367_ - f, p_104366_ / p_104368_ - f1);
         if (p_104369_) {
            int i = p_104362_.length;

            for(int j = 0; j < i / 2; ++j) {
               ModelPart.Vertex modelpart$vertex = p_104362_[j];
               p_104362_[j] = p_104362_[i - 1 - j];
               p_104362_[i - 1 - j] = modelpart$vertex;
            }
         }

         this.f_104360_ = p_104370_.m_122432_();
         if (p_104369_) {
            this.f_104360_.m_122263_(-1.0F, 1.0F, 1.0F);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Vertex {
      public final Vector3f f_104371_;
      public final float f_104372_;
      public final float f_104373_;

      public Vertex(float p_104375_, float p_104376_, float p_104377_, float p_104378_, float p_104379_) {
         this(new Vector3f(p_104375_, p_104376_, p_104377_), p_104378_, p_104379_);
      }

      public ModelPart.Vertex m_104384_(float p_104385_, float p_104386_) {
         return new ModelPart.Vertex(this.f_104371_, p_104385_, p_104386_);
      }

      public Vertex(Vector3f p_104381_, float p_104382_, float p_104383_) {
         this.f_104371_ = p_104381_;
         this.f_104372_ = p_104382_;
         this.f_104373_ = p_104383_;
      }
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface Visitor {
      void m_171341_(PoseStack.Pose p_171342_, String p_171343_, int p_171344_, ModelPart.Cube p_171345_);
   }
}