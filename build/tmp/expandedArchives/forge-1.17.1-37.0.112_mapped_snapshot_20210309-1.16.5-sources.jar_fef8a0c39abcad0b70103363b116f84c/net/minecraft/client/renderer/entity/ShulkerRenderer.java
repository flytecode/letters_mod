package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.layers.ShulkerHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerRenderer extends MobRenderer<Shulker, ShulkerModel<Shulker>> {
   private static final ResourceLocation f_115871_ = new ResourceLocation("textures/" + Sheets.f_110741_.m_119203_().m_135815_() + ".png");
   private static final ResourceLocation[] f_115872_ = Sheets.f_110742_.stream().map((p_115919_) -> {
      return new ResourceLocation("textures/" + p_115919_.m_119203_().m_135815_() + ".png");
   }).toArray((p_115877_) -> {
      return new ResourceLocation[p_115877_];
   });

   public ShulkerRenderer(EntityRendererProvider.Context p_174370_) {
      super(p_174370_, new ShulkerModel<>(p_174370_.m_174023_(ModelLayers.f_171180_)), 0.0F);
      this.m_115326_(new ShulkerHeadLayer(this));
   }

   public Vec3 m_7860_(Shulker p_115904_, float p_115905_) {
      return p_115904_.m_149766_(p_115905_).orElse(super.m_7860_(p_115904_, p_115905_));
   }

   public boolean m_5523_(Shulker p_115913_, Frustum p_115914_, double p_115915_, double p_115916_, double p_115917_) {
      return super.m_5523_(p_115913_, p_115914_, p_115915_, p_115916_, p_115917_) ? true : p_115913_.m_149766_(0.0F).filter((p_174374_) -> {
         EntityType<?> entitytype = p_115913_.m_6095_();
         float f = entitytype.m_20679_() / 2.0F;
         float f1 = entitytype.m_20678_() / 2.0F;
         Vec3 vec3 = Vec3.m_82539_(p_115913_.m_142538_());
         return p_115914_.m_113029_((new AABB(p_174374_.f_82479_, p_174374_.f_82480_ + (double)f, p_174374_.f_82481_, vec3.f_82479_, vec3.f_82480_ + (double)f, vec3.f_82481_)).m_82377_((double)f1, (double)f, (double)f1));
      }).isPresent();
   }

   public ResourceLocation m_5478_(Shulker p_115902_) {
      return m_174375_(p_115902_.m_33467_());
   }

   public static ResourceLocation m_174375_(@Nullable DyeColor p_174376_) {
      return p_174376_ == null ? f_115871_ : f_115872_[p_174376_.m_41060_()];
   }

   protected void m_7523_(Shulker p_115907_, PoseStack p_115908_, float p_115909_, float p_115910_, float p_115911_) {
      super.m_7523_(p_115907_, p_115908_, p_115909_, p_115910_ + 180.0F, p_115911_);
      p_115908_.m_85837_(0.0D, 0.5D, 0.0D);
      p_115908_.m_85845_(p_115907_.m_33461_().m_122424_().m_122406_());
      p_115908_.m_85837_(0.0D, -0.5D, 0.0D);
   }
}