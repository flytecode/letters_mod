package net.minecraft.client.model;

import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class EntityModel<T extends Entity> extends Model {
   public float f_102608_;
   public boolean f_102609_;
   public boolean f_102610_ = true;

   protected EntityModel() {
      this(RenderType::m_110458_);
   }

   protected EntityModel(Function<ResourceLocation, RenderType> p_102613_) {
      super(p_102613_);
   }

   public abstract void m_6973_(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_);

   public void m_6839_(T p_102614_, float p_102615_, float p_102616_, float p_102617_) {
   }

   public void m_102624_(EntityModel<T> p_102625_) {
      p_102625_.f_102608_ = this.f_102608_;
      p_102625_.f_102609_ = this.f_102609_;
      p_102625_.f_102610_ = this.f_102610_;
   }
}