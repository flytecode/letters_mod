package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BellRenderer implements BlockEntityRenderer<BellBlockEntity> {
   public static final Material f_112227_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/bell/bell_body"));
   private static final String f_173552_ = "bell_body";
   private final ModelPart f_112228_;

   public BellRenderer(BlockEntityRendererProvider.Context p_173554_) {
      ModelPart modelpart = p_173554_.m_173582_(ModelLayers.f_171269_);
      this.f_112228_ = modelpart.m_171324_("bell_body");
   }

   public static LayerDefinition m_173555_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      PartDefinition partdefinition1 = partdefinition.m_171599_("bell_body", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0F, -6.0F, -3.0F, 6.0F, 7.0F, 6.0F), PartPose.m_171419_(8.0F, 12.0F, 8.0F));
      partdefinition1.m_171599_("bell_base", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171481_(4.0F, 4.0F, 4.0F, 8.0F, 2.0F, 8.0F), PartPose.m_171419_(-8.0F, -12.0F, -8.0F));
      return LayerDefinition.m_171565_(meshdefinition, 32, 32);
   }

   public void m_6922_(BellBlockEntity p_112233_, float p_112234_, PoseStack p_112235_, MultiBufferSource p_112236_, int p_112237_, int p_112238_) {
      float f = (float)p_112233_.f_58813_ + p_112234_;
      float f1 = 0.0F;
      float f2 = 0.0F;
      if (p_112233_.f_58814_) {
         float f3 = Mth.m_14031_(f / (float)Math.PI) / (4.0F + f / 3.0F);
         if (p_112233_.f_58815_ == Direction.NORTH) {
            f1 = -f3;
         } else if (p_112233_.f_58815_ == Direction.SOUTH) {
            f1 = f3;
         } else if (p_112233_.f_58815_ == Direction.EAST) {
            f2 = -f3;
         } else if (p_112233_.f_58815_ == Direction.WEST) {
            f2 = f3;
         }
      }

      this.f_112228_.f_104203_ = f1;
      this.f_112228_.f_104205_ = f2;
      VertexConsumer vertexconsumer = f_112227_.m_119194_(p_112236_, RenderType::m_110446_);
      this.f_112228_.m_104301_(p_112235_, vertexconsumer, p_112237_, p_112238_);
   }
}