package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerModel<T extends Shulker> extends ListModel<T> {
   private static final String f_170919_ = "lid";
   private static final String f_170920_ = "base";
   private final ModelPart f_103722_;
   private final ModelPart f_103723_;
   private final ModelPart f_103724_;

   public ShulkerModel(ModelPart p_170922_) {
      super(RenderType::m_110464_);
      this.f_103723_ = p_170922_.m_171324_("lid");
      this.f_103722_ = p_170922_.m_171324_("base");
      this.f_103724_ = p_170922_.m_171324_("head");
   }

   public static LayerDefinition m_170923_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("lid", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      partdefinition.m_171599_("base", CubeListBuilder.m_171558_().m_171514_(0, 28).m_171481_(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F), PartPose.m_171419_(0.0F, 24.0F, 0.0F));
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 52).m_171481_(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.m_171419_(0.0F, 12.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6973_(T p_103735_, float p_103736_, float p_103737_, float p_103738_, float p_103739_, float p_103740_) {
      float f = p_103738_ - (float)p_103735_.f_19797_;
      float f1 = (0.5F + p_103735_.m_33480_(f)) * (float)Math.PI;
      float f2 = -1.0F + Mth.m_14031_(f1);
      float f3 = 0.0F;
      if (f1 > (float)Math.PI) {
         f3 = Mth.m_14031_(p_103738_ * 0.1F) * 0.7F;
      }

      this.f_103723_.m_104227_(0.0F, 16.0F + Mth.m_14031_(f1) * 8.0F + f3, 0.0F);
      if (p_103735_.m_33480_(f) > 0.3F) {
         this.f_103723_.f_104204_ = f2 * f2 * f2 * f2 * (float)Math.PI * 0.125F;
      } else {
         this.f_103723_.f_104204_ = 0.0F;
      }

      this.f_103724_.f_104203_ = p_103740_ * ((float)Math.PI / 180F);
      this.f_103724_.f_104204_ = (p_103735_.f_20885_ - 180.0F - p_103735_.f_20883_) * ((float)Math.PI / 180F);
   }

   public Iterable<ModelPart> m_6195_() {
      return ImmutableList.of(this.f_103722_, this.f_103723_);
   }

   public ModelPart m_103742_() {
      return this.f_103723_;
   }

   public ModelPart m_103743_() {
      return this.f_103724_;
   }
}