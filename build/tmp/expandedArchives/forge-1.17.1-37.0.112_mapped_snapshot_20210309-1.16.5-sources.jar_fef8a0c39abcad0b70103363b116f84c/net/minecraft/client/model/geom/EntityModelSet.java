package net.minecraft.client.model.geom;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EntityModelSet implements ResourceManagerReloadListener {
   private Map<ModelLayerLocation, LayerDefinition> f_171099_ = ImmutableMap.of();

   public ModelPart m_171103_(ModelLayerLocation p_171104_) {
      LayerDefinition layerdefinition = this.f_171099_.get(p_171104_);
      if (layerdefinition == null) {
         throw new IllegalArgumentException("No model for layer " + p_171104_);
      } else {
         return layerdefinition.m_171564_();
      }
   }

   public void m_6213_(ResourceManager p_171102_) {
      this.f_171099_ = ImmutableMap.copyOf(LayerDefinitions.m_171110_());
   }
}