package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitherSkullRenderer extends EntityRenderer<WitherSkull> {
   private static final ResourceLocation f_116463_ = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation f_116464_ = new ResourceLocation("textures/entity/wither/wither.png");
   private final SkullModel f_116465_;

   public WitherSkullRenderer(EntityRendererProvider.Context p_174449_) {
      super(p_174449_);
      this.f_116465_ = new SkullModel(p_174449_.m_174023_(ModelLayers.f_171220_));
   }

   public static LayerDefinition m_174450_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 35).m_171481_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.f_171404_);
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   protected int m_6086_(WitherSkull p_116491_, BlockPos p_116492_) {
      return 15;
   }

   public void m_7392_(WitherSkull p_116484_, float p_116485_, float p_116486_, PoseStack p_116487_, MultiBufferSource p_116488_, int p_116489_) {
      p_116487_.m_85836_();
      p_116487_.m_85841_(-1.0F, -1.0F, 1.0F);
      float f = Mth.m_14201_(p_116484_.f_19859_, p_116484_.m_146908_(), p_116486_);
      float f1 = Mth.m_14179_(p_116486_, p_116484_.f_19860_, p_116484_.m_146909_());
      VertexConsumer vertexconsumer = p_116488_.m_6299_(this.f_116465_.m_103119_(this.m_5478_(p_116484_)));
      this.f_116465_.m_142698_(0.0F, f, f1);
      this.f_116465_.m_7695_(p_116487_, vertexconsumer, p_116489_, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      p_116487_.m_85849_();
      super.m_7392_(p_116484_, p_116485_, p_116486_, p_116487_, p_116488_, p_116489_);
   }

   public ResourceLocation m_5478_(WitherSkull p_116482_) {
      return p_116482_.m_37635_() ? f_116463_ : f_116464_;
   }
}