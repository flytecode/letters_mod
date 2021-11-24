package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Calendar;
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
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
   private static final String f_173603_ = "bottom";
   private static final String f_173604_ = "lid";
   private static final String f_173605_ = "lock";
   private final ModelPart f_112350_;
   private final ModelPart f_112351_;
   private final ModelPart f_112352_;
   private final ModelPart f_112353_;
   private final ModelPart f_112354_;
   private final ModelPart f_112355_;
   private final ModelPart f_112356_;
   private final ModelPart f_112357_;
   private final ModelPart f_112358_;
   private boolean f_112359_;

   public ChestRenderer(BlockEntityRendererProvider.Context p_173607_) {
      Calendar calendar = Calendar.getInstance();
      if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
         this.f_112359_ = true;
      }

      ModelPart modelpart = p_173607_.m_173582_(ModelLayers.f_171275_);
      this.f_112351_ = modelpart.m_171324_("bottom");
      this.f_112350_ = modelpart.m_171324_("lid");
      this.f_112352_ = modelpart.m_171324_("lock");
      ModelPart modelpart1 = p_173607_.m_173582_(ModelLayers.f_171133_);
      this.f_112354_ = modelpart1.m_171324_("bottom");
      this.f_112353_ = modelpart1.m_171324_("lid");
      this.f_112355_ = modelpart1.m_171324_("lock");
      ModelPart modelpart2 = p_173607_.m_173582_(ModelLayers.f_171134_);
      this.f_112357_ = modelpart2.m_171324_("bottom");
      this.f_112356_ = modelpart2.m_171324_("lid");
      this.f_112358_ = modelpart2.m_171324_("lock");
   }

   public static LayerDefinition m_173608_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("bottom", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), PartPose.f_171404_);
      partdefinition.m_171599_("lid", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), PartPose.m_171419_(0.0F, 9.0F, 1.0F));
      partdefinition.m_171599_("lock", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F), PartPose.m_171419_(0.0F, 8.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition m_173609_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("bottom", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.f_171404_);
      partdefinition.m_171599_("lid", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), PartPose.m_171419_(0.0F, 9.0F, 1.0F));
      partdefinition.m_171599_("lock", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), PartPose.m_171419_(0.0F, 8.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public static LayerDefinition m_173610_() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("bottom", CubeListBuilder.m_171558_().m_171514_(0, 19).m_171481_(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.f_171404_);
      partdefinition.m_171599_("lid", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), PartPose.m_171419_(0.0F, 9.0F, 1.0F));
      partdefinition.m_171599_("lock", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), PartPose.m_171419_(0.0F, 8.0F, 0.0F));
      return LayerDefinition.m_171565_(meshdefinition, 64, 64);
   }

   public void m_6922_(T p_112363_, float p_112364_, PoseStack p_112365_, MultiBufferSource p_112366_, int p_112367_, int p_112368_) {
      Level level = p_112363_.m_58904_();
      boolean flag = level != null;
      BlockState blockstate = flag ? p_112363_.m_58900_() : Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, Direction.SOUTH);
      ChestType chesttype = blockstate.m_61138_(ChestBlock.f_51479_) ? blockstate.m_61143_(ChestBlock.f_51479_) : ChestType.SINGLE;
      Block block = blockstate.m_60734_();
      if (block instanceof AbstractChestBlock) {
         AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock)block;
         boolean flag1 = chesttype != ChestType.SINGLE;
         p_112365_.m_85836_();
         float f = blockstate.m_61143_(ChestBlock.f_51478_).m_122435_();
         p_112365_.m_85837_(0.5D, 0.5D, 0.5D);
         p_112365_.m_85845_(Vector3f.f_122225_.m_122240_(-f));
         p_112365_.m_85837_(-0.5D, -0.5D, -0.5D);
         DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> neighborcombineresult;
         if (flag) {
            neighborcombineresult = abstractchestblock.m_5641_(blockstate, level, p_112363_.m_58899_(), true);
         } else {
            neighborcombineresult = DoubleBlockCombiner.Combiner::m_6502_;
         }

         float f1 = neighborcombineresult.<Float2FloatFunction>m_5649_(ChestBlock.m_51517_(p_112363_)).get(p_112364_);
         f1 = 1.0F - f1;
         f1 = 1.0F - f1 * f1 * f1;
         int i = neighborcombineresult.<Int2IntFunction>m_5649_(new BrightnessCombiner<>()).applyAsInt(p_112367_);
         Material material = this.getMaterial(p_112363_, chesttype);
         VertexConsumer vertexconsumer = material.m_119194_(p_112366_, RenderType::m_110452_);
         if (flag1) {
            if (chesttype == ChestType.LEFT) {
               this.m_112369_(p_112365_, vertexconsumer, this.f_112353_, this.f_112355_, this.f_112354_, f1, i, p_112368_);
            } else {
               this.m_112369_(p_112365_, vertexconsumer, this.f_112356_, this.f_112358_, this.f_112357_, f1, i, p_112368_);
            }
         } else {
            this.m_112369_(p_112365_, vertexconsumer, this.f_112350_, this.f_112352_, this.f_112351_, f1, i, p_112368_);
         }

         p_112365_.m_85849_();
      }
   }

   private void m_112369_(PoseStack p_112370_, VertexConsumer p_112371_, ModelPart p_112372_, ModelPart p_112373_, ModelPart p_112374_, float p_112375_, int p_112376_, int p_112377_) {
      p_112372_.f_104203_ = -(p_112375_ * ((float)Math.PI / 2F));
      p_112373_.f_104203_ = p_112372_.f_104203_;
      p_112372_.m_104301_(p_112370_, p_112371_, p_112376_, p_112377_);
      p_112373_.m_104301_(p_112370_, p_112371_, p_112376_, p_112377_);
      p_112374_.m_104301_(p_112370_, p_112371_, p_112376_, p_112377_);
   }

   protected Material getMaterial(T blockEntity, ChestType chestType) {
      return Sheets.m_110767_(blockEntity, chestType, this.f_112359_);
   }
}
