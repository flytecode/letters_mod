package net.minecraft.client.renderer.entity;

import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@FunctionalInterface
@OnlyIn(Dist.CLIENT)
public interface EntityRendererProvider<T extends Entity> {
   EntityRenderer<T> m_174009_(EntityRendererProvider.Context p_174010_);

   @OnlyIn(Dist.CLIENT)
   public static class Context {
      private final EntityRenderDispatcher f_174011_;
      private final ItemRenderer f_174012_;
      private final ResourceManager f_174013_;
      private final EntityModelSet f_174014_;
      private final Font f_174015_;

      public Context(EntityRenderDispatcher p_174017_, ItemRenderer p_174018_, ResourceManager p_174019_, EntityModelSet p_174020_, Font p_174021_) {
         this.f_174011_ = p_174017_;
         this.f_174012_ = p_174018_;
         this.f_174013_ = p_174019_;
         this.f_174014_ = p_174020_;
         this.f_174015_ = p_174021_;
      }

      public EntityRenderDispatcher m_174022_() {
         return this.f_174011_;
      }

      public ItemRenderer m_174025_() {
         return this.f_174012_;
      }

      public ResourceManager m_174026_() {
         return this.f_174013_;
      }

      public EntityModelSet m_174027_() {
         return this.f_174014_;
      }

      public ModelPart m_174023_(ModelLayerLocation p_174024_) {
         return this.f_174014_.m_171103_(p_174024_);
      }

      public Font m_174028_() {
         return this.f_174015_;
      }
   }
}