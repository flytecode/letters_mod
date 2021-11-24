package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.structures.JigsawJunction;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoolElementStructurePiece extends StructurePiece {
   private static final Logger f_72600_ = LogManager.getLogger();
   protected final StructurePoolElement f_72597_;
   protected BlockPos f_72598_;
   private final int f_72601_;
   protected final Rotation f_72599_;
   private final List<JigsawJunction> f_72602_ = Lists.newArrayList();
   private final StructureManager f_72603_;

   public PoolElementStructurePiece(StructureManager p_72606_, StructurePoolElement p_72607_, BlockPos p_72608_, int p_72609_, Rotation p_72610_, BoundingBox p_72611_) {
      super(StructurePieceType.f_67136_, 0, p_72611_);
      this.f_72603_ = p_72606_;
      this.f_72597_ = p_72607_;
      this.f_72598_ = p_72608_;
      this.f_72601_ = p_72609_;
      this.f_72599_ = p_72610_;
   }

   public PoolElementStructurePiece(ServerLevel p_163118_, CompoundTag p_163119_) {
      super(StructurePieceType.f_67136_, p_163119_);
      this.f_72603_ = p_163118_.m_8875_();
      this.f_72598_ = new BlockPos(p_163119_.m_128451_("PosX"), p_163119_.m_128451_("PosY"), p_163119_.m_128451_("PosZ"));
      this.f_72601_ = p_163119_.m_128451_("ground_level_delta");
      RegistryReadOps<Tag> registryreadops = RegistryReadOps.m_179882_(NbtOps.f_128958_, p_163118_.m_142572_().m_177941_(), p_163118_.m_142572_().m_129911_());
      this.f_72597_ = StructurePoolElement.f_69153_.parse(registryreadops, p_163119_.m_128469_("pool_element")).resultOrPartial(f_72600_::error).orElseThrow(() -> {
         return new IllegalStateException("Invalid pool element found");
      });
      this.f_72599_ = Rotation.valueOf(p_163119_.m_128461_("rotation"));
      this.f_73383_ = this.f_72597_.m_6867_(this.f_72603_, this.f_72598_, this.f_72599_);
      ListTag listtag = p_163119_.m_128437_("junctions", 10);
      this.f_72602_.clear();
      listtag.forEach((p_163128_) -> {
         this.f_72602_.add(JigsawJunction.m_68931_(new Dynamic<>(registryreadops, p_163128_)));
      });
   }

   protected void m_142347_(ServerLevel p_163121_, CompoundTag p_163122_) {
      p_163122_.m_128405_("PosX", this.f_72598_.m_123341_());
      p_163122_.m_128405_("PosY", this.f_72598_.m_123342_());
      p_163122_.m_128405_("PosZ", this.f_72598_.m_123343_());
      p_163122_.m_128405_("ground_level_delta", this.f_72601_);
      RegistryWriteOps<Tag> registrywriteops = RegistryWriteOps.m_135767_(NbtOps.f_128958_, p_163121_.m_142572_().m_129911_());
      StructurePoolElement.f_69153_.encodeStart(registrywriteops, this.f_72597_).resultOrPartial(f_72600_::error).ifPresent((p_163125_) -> {
         p_163122_.m_128365_("pool_element", p_163125_);
      });
      p_163122_.m_128359_("rotation", this.f_72599_.name());
      ListTag listtag = new ListTag();

      for(JigsawJunction jigsawjunction : this.f_72602_) {
         listtag.add(jigsawjunction.m_68933_(registrywriteops).getValue());
      }

      p_163122_.m_128365_("junctions", listtag);
   }

   public boolean m_7832_(WorldGenLevel p_72620_, StructureFeatureManager p_72621_, ChunkGenerator p_72622_, Random p_72623_, BoundingBox p_72624_, ChunkPos p_72625_, BlockPos p_72626_) {
      return this.m_72627_(p_72620_, p_72621_, p_72622_, p_72623_, p_72624_, p_72626_, false);
   }

   public boolean m_72627_(WorldGenLevel p_72628_, StructureFeatureManager p_72629_, ChunkGenerator p_72630_, Random p_72631_, BoundingBox p_72632_, BlockPos p_72633_, boolean p_72634_) {
      return this.f_72597_.m_6791_(this.f_72603_, p_72628_, p_72629_, p_72630_, this.f_72598_, p_72633_, this.f_72599_, p_72632_, p_72631_, p_72634_);
   }

   public void m_6324_(int p_72616_, int p_72617_, int p_72618_) {
      super.m_6324_(p_72616_, p_72617_, p_72618_);
      this.f_72598_ = this.f_72598_.m_142082_(p_72616_, p_72617_, p_72618_);
   }

   public Rotation m_6830_() {
      return this.f_72599_;
   }

   public String toString() {
      return String.format("<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.f_72598_, this.f_72599_, this.f_72597_);
   }

   public StructurePoolElement m_72645_() {
      return this.f_72597_;
   }

   public BlockPos m_72646_() {
      return this.f_72598_;
   }

   public int m_72647_() {
      return this.f_72601_;
   }

   public void m_72635_(JigsawJunction p_72636_) {
      this.f_72602_.add(p_72636_);
   }

   public List<JigsawJunction> m_72648_() {
      return this.f_72602_;
   }
}