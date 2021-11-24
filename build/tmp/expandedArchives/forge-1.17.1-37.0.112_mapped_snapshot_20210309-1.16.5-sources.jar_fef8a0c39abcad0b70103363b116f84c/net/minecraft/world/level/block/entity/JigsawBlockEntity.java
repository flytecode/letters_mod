package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.feature.structures.SinglePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class JigsawBlockEntity extends BlockEntity {
   public static final String f_155599_ = "target";
   public static final String f_155600_ = "pool";
   public static final String f_155601_ = "joint";
   public static final String f_155602_ = "name";
   public static final String f_155603_ = "final_state";
   private ResourceLocation f_59411_ = new ResourceLocation("empty");
   private ResourceLocation f_59412_ = new ResourceLocation("empty");
   private ResourceLocation f_59413_ = new ResourceLocation("empty");
   private JigsawBlockEntity.JointType f_59414_ = JigsawBlockEntity.JointType.ROLLABLE;
   private String f_59415_ = "minecraft:air";

   public JigsawBlockEntity(BlockPos p_155605_, BlockState p_155606_) {
      super(BlockEntityType.f_58910_, p_155605_, p_155606_);
   }

   public ResourceLocation m_59442_() {
      return this.f_59411_;
   }

   public ResourceLocation m_59443_() {
      return this.f_59412_;
   }

   public ResourceLocation m_59444_() {
      return this.f_59413_;
   }

   public String m_59445_() {
      return this.f_59415_;
   }

   public JigsawBlockEntity.JointType m_59446_() {
      return this.f_59414_;
   }

   public void m_59435_(ResourceLocation p_59436_) {
      this.f_59411_ = p_59436_;
   }

   public void m_59438_(ResourceLocation p_59439_) {
      this.f_59412_ = p_59439_;
   }

   public void m_59440_(ResourceLocation p_59441_) {
      this.f_59413_ = p_59441_;
   }

   public void m_59431_(String p_59432_) {
      this.f_59415_ = p_59432_;
   }

   public void m_59424_(JigsawBlockEntity.JointType p_59425_) {
      this.f_59414_ = p_59425_;
   }

   public CompoundTag m_6945_(CompoundTag p_59434_) {
      super.m_6945_(p_59434_);
      p_59434_.m_128359_("name", this.f_59411_.toString());
      p_59434_.m_128359_("target", this.f_59412_.toString());
      p_59434_.m_128359_("pool", this.f_59413_.toString());
      p_59434_.m_128359_("final_state", this.f_59415_);
      p_59434_.m_128359_("joint", this.f_59414_.m_7912_());
      return p_59434_;
   }

   public void m_142466_(CompoundTag p_155608_) {
      super.m_142466_(p_155608_);
      this.f_59411_ = new ResourceLocation(p_155608_.m_128461_("name"));
      this.f_59412_ = new ResourceLocation(p_155608_.m_128461_("target"));
      this.f_59413_ = new ResourceLocation(p_155608_.m_128461_("pool"));
      this.f_59415_ = p_155608_.m_128461_("final_state");
      this.f_59414_ = JigsawBlockEntity.JointType.m_59457_(p_155608_.m_128461_("joint")).orElseGet(() -> {
         return JigsawBlock.m_54250_(this.m_58900_()).m_122434_().m_122479_() ? JigsawBlockEntity.JointType.ALIGNED : JigsawBlockEntity.JointType.ROLLABLE;
      });
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 12, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   public void m_59420_(ServerLevel p_59421_, int p_59422_, boolean p_59423_) {
      ChunkGenerator chunkgenerator = p_59421_.m_7726_().m_8481_();
      StructureManager structuremanager = p_59421_.m_8875_();
      StructureFeatureManager structurefeaturemanager = p_59421_.m_8595_();
      Random random = p_59421_.m_5822_();
      BlockPos blockpos = this.m_58899_();
      List<PoolElementStructurePiece> list = Lists.newArrayList();
      StructureTemplate structuretemplate = new StructureTemplate();
      structuretemplate.m_163802_(p_59421_, blockpos, new Vec3i(1, 1, 1), false, (Block)null);
      StructurePoolElement structurepoolelement = new SinglePoolElement(structuretemplate);
      PoolElementStructurePiece poolelementstructurepiece = new PoolElementStructurePiece(structuremanager, structurepoolelement, blockpos, 1, Rotation.NONE, new BoundingBox(blockpos));
      JigsawPlacement.m_161624_(p_59421_.m_5962_(), poolelementstructurepiece, p_59422_, PoolElementStructurePiece::new, chunkgenerator, structuremanager, list, random, p_59421_);

      for(PoolElementStructurePiece poolelementstructurepiece1 : list) {
         poolelementstructurepiece1.m_72627_(p_59421_, structurefeaturemanager, chunkgenerator, random, BoundingBox.m_71044_(), blockpos, p_59423_);
      }

   }

   public static enum JointType implements StringRepresentable {
      ROLLABLE("rollable"),
      ALIGNED("aligned");

      private final String f_59449_;

      private JointType(String p_59455_) {
         this.f_59449_ = p_59455_;
      }

      public String m_7912_() {
         return this.f_59449_;
      }

      public static Optional<JigsawBlockEntity.JointType> m_59457_(String p_59458_) {
         return Arrays.stream(values()).filter((p_59461_) -> {
            return p_59461_.m_7912_().equals(p_59458_);
         }).findFirst();
      }

      public Component m_155610_() {
         return new TranslatableComponent("jigsaw_block.joint." + this.f_59449_);
      }
   }
}