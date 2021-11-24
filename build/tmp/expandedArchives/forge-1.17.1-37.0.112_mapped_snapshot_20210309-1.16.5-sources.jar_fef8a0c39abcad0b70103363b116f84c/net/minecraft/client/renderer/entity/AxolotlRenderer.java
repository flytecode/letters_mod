package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AxolotlRenderer extends MobRenderer<Axolotl, AxolotlModel<Axolotl>> {
   private static final Map<Axolotl.Variant, ResourceLocation> f_173918_ = Util.m_137469_(Maps.newHashMap(), (p_173927_) -> {
      for(Axolotl.Variant axolotl$variant : Axolotl.Variant.f_149230_) {
         p_173927_.put(axolotl$variant, new ResourceLocation(String.format("textures/entity/axolotl/axolotl_%s.png", axolotl$variant.m_149253_())));
      }

   });

   public AxolotlRenderer(EntityRendererProvider.Context p_173921_) {
      super(p_173921_, new AxolotlModel<>(p_173921_.m_174023_(ModelLayers.f_171263_)), 0.5F);
   }

   public ResourceLocation m_5478_(Axolotl p_173925_) {
      return f_173918_.get(p_173925_.m_149179_());
   }
}