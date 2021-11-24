package net.minecraft.world.level.levelgen.structure;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TemplateStructurePiece extends StructurePiece {
   private static final Logger f_73659_ = LogManager.getLogger();
   protected final String f_163658_;
   protected StructureTemplate f_73656_;
   protected StructurePlaceSettings f_73657_;
   protected BlockPos f_73658_;

   public TemplateStructurePiece(StructurePieceType p_163660_, int p_163661_, StructureManager p_163662_, ResourceLocation p_163663_, String p_163664_, StructurePlaceSettings p_163665_, BlockPos p_163666_) {
      super(p_163660_, p_163661_, p_163662_.m_74341_(p_163663_).m_74633_(p_163665_, p_163666_));
      this.m_73519_(Direction.NORTH);
      this.f_163658_ = p_163664_;
      this.f_73658_ = p_163666_;
      this.f_73656_ = p_163662_.m_74341_(p_163663_);
      this.f_73657_ = p_163665_;
   }

   public TemplateStructurePiece(StructurePieceType p_163668_, CompoundTag p_163669_, ServerLevel p_163670_, Function<ResourceLocation, StructurePlaceSettings> p_163671_) {
      super(p_163668_, p_163669_);
      this.m_73519_(Direction.NORTH);
      this.f_163658_ = p_163669_.m_128461_("Template");
      this.f_73658_ = new BlockPos(p_163669_.m_128451_("TPX"), p_163669_.m_128451_("TPY"), p_163669_.m_128451_("TPZ"));
      ResourceLocation resourcelocation = this.m_142415_();
      this.f_73656_ = p_163670_.m_8875_().m_74341_(resourcelocation);
      this.f_73657_ = p_163671_.apply(resourcelocation);
      this.f_73383_ = this.f_73656_.m_74633_(this.f_73657_, this.f_73658_);
   }

   protected ResourceLocation m_142415_() {
      return new ResourceLocation(this.f_163658_);
   }

   protected void m_142347_(ServerLevel p_163673_, CompoundTag p_163674_) {
      p_163674_.m_128405_("TPX", this.f_73658_.m_123341_());
      p_163674_.m_128405_("TPY", this.f_73658_.m_123342_());
      p_163674_.m_128405_("TPZ", this.f_73658_.m_123343_());
      p_163674_.m_128359_("Template", this.f_163658_);
   }

   public boolean m_7832_(WorldGenLevel p_73672_, StructureFeatureManager p_73673_, ChunkGenerator p_73674_, Random p_73675_, BoundingBox p_73676_, ChunkPos p_73677_, BlockPos p_73678_) {
      this.f_73657_.m_74381_(p_73676_);
      this.f_73383_ = this.f_73656_.m_74633_(this.f_73657_, this.f_73658_);
      if (this.f_73656_.m_74536_(p_73672_, this.f_73658_, p_73678_, this.f_73657_, p_73675_, 2)) {
         for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : this.f_73656_.m_74603_(this.f_73658_, this.f_73657_, Blocks.f_50677_)) {
            if (structuretemplate$structureblockinfo.f_74677_ != null) {
               StructureMode structuremode = StructureMode.valueOf(structuretemplate$structureblockinfo.f_74677_.m_128461_("mode"));
               if (structuremode == StructureMode.DATA) {
                  this.m_7756_(structuretemplate$structureblockinfo.f_74677_.m_128461_("metadata"), structuretemplate$structureblockinfo.f_74675_, p_73672_, p_73675_, p_73676_);
               }
            }
         }

         for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo1 : this.f_73656_.m_74603_(this.f_73658_, this.f_73657_, Blocks.f_50678_)) {
            if (structuretemplate$structureblockinfo1.f_74677_ != null) {
               String s = structuretemplate$structureblockinfo1.f_74677_.m_128461_("final_state");
               BlockStateParser blockstateparser = new BlockStateParser(new StringReader(s), false);
               BlockState blockstate = Blocks.f_50016_.m_49966_();

               try {
                  blockstateparser.m_116806_(true);
                  BlockState blockstate1 = blockstateparser.m_116808_();
                  if (blockstate1 != null) {
                     blockstate = blockstate1;
                  } else {
                     f_73659_.error("Error while parsing blockstate {} in jigsaw block @ {}", s, structuretemplate$structureblockinfo1.f_74675_);
                  }
               } catch (CommandSyntaxException commandsyntaxexception) {
                  f_73659_.error("Error while parsing blockstate {} in jigsaw block @ {}", s, structuretemplate$structureblockinfo1.f_74675_);
               }

               p_73672_.m_7731_(structuretemplate$structureblockinfo1.f_74675_, blockstate, 3);
            }
         }
      }

      return true;
   }

   protected abstract void m_7756_(String p_73683_, BlockPos p_73684_, ServerLevelAccessor p_73685_, Random p_73686_, BoundingBox p_73687_);

   public void m_6324_(int p_73668_, int p_73669_, int p_73670_) {
      super.m_6324_(p_73668_, p_73669_, p_73670_);
      this.f_73658_ = this.f_73658_.m_142082_(p_73668_, p_73669_, p_73670_);
   }

   public Rotation m_6830_() {
      return this.f_73657_.m_74404_();
   }
}