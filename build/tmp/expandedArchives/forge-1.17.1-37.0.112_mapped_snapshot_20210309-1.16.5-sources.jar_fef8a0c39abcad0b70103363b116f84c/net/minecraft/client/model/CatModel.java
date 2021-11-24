package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Cat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatModel<T extends Cat> extends OcelotModel<T> {
   private float f_102325_;
   private float f_102326_;
   private float f_102327_;

   public CatModel(ModelPart p_170478_) {
      super(p_170478_);
   }

   public void m_6839_(T p_102343_, float p_102344_, float p_102345_, float p_102346_) {
      this.f_102325_ = p_102343_.m_28183_(p_102346_);
      this.f_102326_ = p_102343_.m_28187_(p_102346_);
      this.f_102327_ = p_102343_.m_28116_(p_102346_);
      if (this.f_102325_ <= 0.0F) {
         this.f_103135_.f_104203_ = 0.0F;
         this.f_103135_.f_104205_ = 0.0F;
         this.f_170755_.f_104203_ = 0.0F;
         this.f_170755_.f_104205_ = 0.0F;
         this.f_170756_.f_104203_ = 0.0F;
         this.f_170756_.f_104205_ = 0.0F;
         this.f_170756_.f_104200_ = -1.2F;
         this.f_170753_.f_104203_ = 0.0F;
         this.f_170754_.f_104203_ = 0.0F;
         this.f_170754_.f_104205_ = 0.0F;
         this.f_170754_.f_104200_ = -1.1F;
         this.f_170754_.f_104201_ = 18.0F;
      }

      super.m_6839_(p_102343_, p_102344_, p_102345_, p_102346_);
      if (p_102343_.m_21825_()) {
         this.f_103136_.f_104203_ = ((float)Math.PI / 4F);
         this.f_103136_.f_104201_ += -4.0F;
         this.f_103136_.f_104202_ += 5.0F;
         this.f_103135_.f_104201_ += -3.3F;
         ++this.f_103135_.f_104202_;
         this.f_103133_.f_104201_ += 8.0F;
         this.f_103133_.f_104202_ += -2.0F;
         this.f_103134_.f_104201_ += 2.0F;
         this.f_103134_.f_104202_ += -0.8F;
         this.f_103133_.f_104203_ = 1.7278761F;
         this.f_103134_.f_104203_ = 2.670354F;
         this.f_170755_.f_104203_ = -0.15707964F;
         this.f_170755_.f_104201_ = 16.1F;
         this.f_170755_.f_104202_ = -7.0F;
         this.f_170756_.f_104203_ = -0.15707964F;
         this.f_170756_.f_104201_ = 16.1F;
         this.f_170756_.f_104202_ = -7.0F;
         this.f_170753_.f_104203_ = (-(float)Math.PI / 2F);
         this.f_170753_.f_104201_ = 21.0F;
         this.f_170753_.f_104202_ = 1.0F;
         this.f_170754_.f_104203_ = (-(float)Math.PI / 2F);
         this.f_170754_.f_104201_ = 21.0F;
         this.f_170754_.f_104202_ = 1.0F;
         this.f_103137_ = 3;
      }

   }

   public void m_6973_(T p_102348_, float p_102349_, float p_102350_, float p_102351_, float p_102352_, float p_102353_) {
      super.m_6973_(p_102348_, p_102349_, p_102350_, p_102351_, p_102352_, p_102353_);
      if (this.f_102325_ > 0.0F) {
         this.f_103135_.f_104205_ = ModelUtils.m_103125_(this.f_103135_.f_104205_, -1.2707963F, this.f_102325_);
         this.f_103135_.f_104204_ = ModelUtils.m_103125_(this.f_103135_.f_104204_, 1.2707963F, this.f_102325_);
         this.f_170755_.f_104203_ = -1.2707963F;
         this.f_170756_.f_104203_ = -0.47079635F;
         this.f_170756_.f_104205_ = -0.2F;
         this.f_170756_.f_104200_ = -0.2F;
         this.f_170753_.f_104203_ = -0.4F;
         this.f_170754_.f_104203_ = 0.5F;
         this.f_170754_.f_104205_ = -0.5F;
         this.f_170754_.f_104200_ = -0.3F;
         this.f_170754_.f_104201_ = 20.0F;
         this.f_103133_.f_104203_ = ModelUtils.m_103125_(this.f_103133_.f_104203_, 0.8F, this.f_102326_);
         this.f_103134_.f_104203_ = ModelUtils.m_103125_(this.f_103134_.f_104203_, -0.4F, this.f_102326_);
      }

      if (this.f_102327_ > 0.0F) {
         this.f_103135_.f_104203_ = ModelUtils.m_103125_(this.f_103135_.f_104203_, -0.58177644F, this.f_102327_);
      }

   }
}