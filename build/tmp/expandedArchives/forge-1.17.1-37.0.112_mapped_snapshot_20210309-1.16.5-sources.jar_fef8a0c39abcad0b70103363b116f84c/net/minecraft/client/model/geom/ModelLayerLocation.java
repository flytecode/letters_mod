package net.minecraft.client.model.geom;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ModelLayerLocation {
   private final ResourceLocation f_171118_;
   private final String f_171119_;

   public ModelLayerLocation(ResourceLocation p_171121_, String p_171122_) {
      this.f_171118_ = p_171121_;
      this.f_171119_ = p_171122_;
   }

   public ResourceLocation m_171123_() {
      return this.f_171118_;
   }

   public String m_171124_() {
      return this.f_171119_;
   }

   public boolean equals(Object p_171126_) {
      if (this == p_171126_) {
         return true;
      } else if (!(p_171126_ instanceof ModelLayerLocation)) {
         return false;
      } else {
         ModelLayerLocation modellayerlocation = (ModelLayerLocation)p_171126_;
         return this.f_171118_.equals(modellayerlocation.f_171118_) && this.f_171119_.equals(modellayerlocation.f_171119_);
      }
   }

   public int hashCode() {
      int i = this.f_171118_.hashCode();
      return 31 * i + this.f_171119_.hashCode();
   }

   public String toString() {
      return this.f_171118_ + "#" + this.f_171119_;
   }
}