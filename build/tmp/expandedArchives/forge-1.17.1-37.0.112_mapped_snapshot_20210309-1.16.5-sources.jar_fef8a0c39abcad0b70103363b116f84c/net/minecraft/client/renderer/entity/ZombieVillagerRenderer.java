package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieVillagerRenderer extends HumanoidMobRenderer<ZombieVillager, ZombieVillagerModel<ZombieVillager>> {
   private static final ResourceLocation f_116547_ = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");

   public ZombieVillagerRenderer(EntityRendererProvider.Context p_174463_) {
      super(p_174463_, new ZombieVillagerModel<>(p_174463_.m_174023_(ModelLayers.f_171228_)), 0.5F);
      this.m_115326_(new HumanoidArmorLayer<>(this, new ZombieVillagerModel(p_174463_.m_174023_(ModelLayers.f_171229_)), new ZombieVillagerModel(p_174463_.m_174023_(ModelLayers.f_171230_))));
      this.m_115326_(new VillagerProfessionLayer<>(this, p_174463_.m_174026_(), "zombie_villager"));
   }

   public ResourceLocation m_5478_(ZombieVillager p_116559_) {
      return f_116547_;
   }

   protected boolean m_5936_(ZombieVillager p_116561_) {
      return super.m_5936_(p_116561_) || p_116561_.m_34408_();
   }
}