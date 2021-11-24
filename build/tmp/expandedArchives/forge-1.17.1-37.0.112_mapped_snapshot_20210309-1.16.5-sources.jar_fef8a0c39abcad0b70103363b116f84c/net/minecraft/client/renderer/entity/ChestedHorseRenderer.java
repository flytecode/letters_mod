package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestedHorseRenderer<T extends AbstractChestedHorse> extends AbstractHorseRenderer<T, ChestedHorseModel<T>> {
   private static final Map<EntityType<?>, ResourceLocation> f_113979_ = Maps.newHashMap(ImmutableMap.of(EntityType.f_20560_, new ResourceLocation("textures/entity/horse/donkey.png"), EntityType.f_20503_, new ResourceLocation("textures/entity/horse/mule.png")));

   public ChestedHorseRenderer(EntityRendererProvider.Context p_173948_, float p_173949_, ModelLayerLocation p_173950_) {
      super(p_173948_, new ChestedHorseModel<>(p_173948_.m_174023_(p_173950_)), p_173949_);
   }

   public ResourceLocation m_5478_(T p_113987_) {
      return f_113979_.get(p_113987_.m_6095_());
   }
}