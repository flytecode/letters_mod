package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BedRenderer implements BlockEntityRenderer<BedBlockEntity> {
   private final ModelPart f_173537_;
   private final ModelPart f_173538_;

   public BedRenderer(BlockEntityRendererProvider.Context p_173540_) {
      this.f_173537_ = p_173540_.m_173582_(ModelLayers.f_171267_);
      this.f_173538_ = p_173540_.m_173582_(ModelLayers.f_171266_);
   }

   public static LayerDefinition m_173550_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("main", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.f_171404_);
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(50, 6).m_171481_(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), PartPose.m_171430_(((float)Math.PI / 2F), 0.0F, ((float)Math.PI / 2F)));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(50, 18).m_171481_(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), PartPose.m_171430_(((float)Math.PI / 2F), 0.0F, (float)Math.PI));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition m_173551_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("main", CubeListBuilder.m_171558_().m_171514_(0, 22).m_171481_(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.f_171404_);
      partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(50, 0).m_171481_(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), PartPose.m_171430_(((float)Math.PI / 2F), 0.0F, 0.0F));
      partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(50, 12).m_171481_(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), PartPose.m_171430_(((float)Math.PI / 2F), 0.0F, ((float)Math.PI * 1.5F)));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6922_(BedBlockEntity p_112205_, float p_112206_, PoseStack p_112207_, MultiBufferSource p_112208_, int p_112209_, int p_112210_) {
      Material material = Sheets.f_110744_[p_112205_.m_58731_().m_41060_()];
      Level level = p_112205_.m_58904_();
      if (level != null) {
         BlockState blockstate = p_112205_.m_58900_();
         DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> neighborcombineresult = DoubleBlockCombiner.m_52822_(BlockEntityType.f_58940_, BedBlock::m_49559_, BedBlock::m_49557_, ChestBlock.f_51478_, blockstate, level, p_112205_.m_58899_(), (p_112202_, p_112203_) -> {
            return false;
         });
         int i = neighborcombineresult.<Int2IntFunction>m_5649_(new BrightnessCombiner<>()).get(p_112209_);
         this.m_173541_(p_112207_, p_112208_, blockstate.m_61143_(BedBlock.f_49440_) == BedPart.HEAD ? this.f_173537_ : this.f_173538_, blockstate.m_61143_(BedBlock.f_54117_), material, i, p_112210_, false);
      } else {
         this.m_173541_(p_112207_, p_112208_, this.f_173537_, Direction.SOUTH, material, p_112209_, p_112210_, false);
         this.m_173541_(p_112207_, p_112208_, this.f_173538_, Direction.SOUTH, material, p_112209_, p_112210_, true);
      }

   }

   private void m_173541_(PoseStack p_173542_, MultiBufferSource p_173543_, ModelPart p_173544_, Direction p_173545_, Material p_173546_, int p_173547_, int p_173548_, boolean p_173549_) {
      p_173542_.m_85836_();
      p_173542_.m_85837_(0.0D, 0.5625D, p_173549_ ? -1.0D : 0.0D);
      p_173542_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      p_173542_.m_85837_(0.5D, 0.5D, 0.5D);
      p_173542_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F + p_173545_.m_122435_()));
      p_173542_.m_85837_(-0.5D, -0.5D, -0.5D);
      VertexConsumer vertexconsumer = p_173546_.m_119194_(p_173543_, RenderType::m_110446_);
      p_173544_.m_104301_(p_173542_, vertexconsumer, p_173547_, p_173548_);
      p_173542_.m_85849_();
   }
}