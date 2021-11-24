package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlackstoneReplaceProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockAgeProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.LavaSubmergedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RuinedPortalPiece extends TemplateStructurePiece {
   private static final Logger f_72650_ = LogManager.getLogger();
   private static final float f_163130_ = 0.3F;
   private static final float f_163131_ = 0.07F;
   private static final float f_163132_ = 0.2F;
   private static final float f_163133_ = 0.2F;
   private final RuinedPortalPiece.VerticalPlacement f_72654_;
   private final RuinedPortalPiece.Properties f_72655_;

   public RuinedPortalPiece(StructureManager p_163138_, BlockPos p_163139_, RuinedPortalPiece.VerticalPlacement p_163140_, RuinedPortalPiece.Properties p_163141_, ResourceLocation p_163142_, StructureTemplate p_163143_, Rotation p_163144_, Mirror p_163145_, BlockPos p_163146_) {
      super(StructurePieceType.f_67115_, 0, p_163138_, p_163142_, p_163142_.toString(), m_163154_(p_163145_, p_163144_, p_163140_, p_163146_, p_163141_), p_163139_);
      this.f_72654_ = p_163140_;
      this.f_72655_ = p_163141_;
   }

   public RuinedPortalPiece(ServerLevel p_163135_, CompoundTag p_163136_) {
      super(StructurePieceType.f_67115_, p_163136_, p_163135_, (p_163173_) -> {
         return m_163150_(p_163135_, p_163136_, p_163173_);
      });
      this.f_72654_ = RuinedPortalPiece.VerticalPlacement.m_72782_(p_163136_.m_128461_("VerticalPlacement"));
      this.f_72655_ = RuinedPortalPiece.Properties.f_72735_.parse(new Dynamic<>(NbtOps.f_128958_, p_163136_.m_128423_("Properties"))).getOrThrow(true, f_72650_::error);
   }

   protected void m_142347_(ServerLevel p_163148_, CompoundTag p_163149_) {
      super.m_142347_(p_163148_, p_163149_);
      p_163149_.m_128359_("Rotation", this.f_73657_.m_74404_().name());
      p_163149_.m_128359_("Mirror", this.f_73657_.m_74401_().name());
      p_163149_.m_128359_("VerticalPlacement", this.f_72654_.m_72779_());
      RuinedPortalPiece.Properties.f_72735_.encodeStart(NbtOps.f_128958_, this.f_72655_).resultOrPartial(f_72650_::error).ifPresent((p_163169_) -> {
         p_163149_.m_128365_("Properties", p_163169_);
      });
   }

   private static StructurePlaceSettings m_163150_(ServerLevel p_163151_, CompoundTag p_163152_, ResourceLocation p_163153_) {
      StructureTemplate structuretemplate = p_163151_.m_8875_().m_74341_(p_163153_);
      BlockPos blockpos = new BlockPos(structuretemplate.m_163801_().m_123341_() / 2, 0, structuretemplate.m_163801_().m_123343_() / 2);
      return m_163154_(Mirror.valueOf(p_163152_.m_128461_("Mirror")), Rotation.valueOf(p_163152_.m_128461_("Rotation")), RuinedPortalPiece.VerticalPlacement.m_72782_(p_163152_.m_128461_("VerticalPlacement")), blockpos, RuinedPortalPiece.Properties.f_72735_.parse(new Dynamic<>(NbtOps.f_128958_, p_163152_.m_128423_("Properties"))).getOrThrow(true, f_72650_::error));
   }

   private static StructurePlaceSettings m_163154_(Mirror p_163155_, Rotation p_163156_, RuinedPortalPiece.VerticalPlacement p_163157_, BlockPos p_163158_, RuinedPortalPiece.Properties p_163159_) {
      BlockIgnoreProcessor blockignoreprocessor = p_163159_.f_72738_ ? BlockIgnoreProcessor.f_74046_ : BlockIgnoreProcessor.f_74048_;
      List<ProcessorRule> list = Lists.newArrayList();
      list.add(m_72685_(Blocks.f_50074_, 0.3F, Blocks.f_50016_));
      list.add(m_163160_(p_163157_, p_163159_));
      if (!p_163159_.f_72736_) {
         list.add(m_72685_(Blocks.f_50134_, 0.07F, Blocks.f_50450_));
      }

      StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).m_74379_(p_163156_).m_74377_(p_163155_).m_74385_(p_163158_).m_74383_(blockignoreprocessor).m_74383_(new RuleProcessor(list)).m_74383_(new BlockAgeProcessor(p_163159_.f_72737_)).m_74383_(new ProtectedBlockProcessor(BlockTags.f_144287_.m_6979_())).m_74383_(new LavaSubmergedBlockProcessor());
      if (p_163159_.f_72741_) {
         structureplacesettings.m_74383_(BlackstoneReplaceProcessor.f_73994_);
      }

      return structureplacesettings;
   }

   private static ProcessorRule m_163160_(RuinedPortalPiece.VerticalPlacement p_163161_, RuinedPortalPiece.Properties p_163162_) {
      if (p_163161_ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR) {
         return m_72689_(Blocks.f_49991_, Blocks.f_50450_);
      } else {
         return p_163162_.f_72736_ ? m_72689_(Blocks.f_49991_, Blocks.f_50134_) : m_72685_(Blocks.f_49991_, 0.2F, Blocks.f_50450_);
      }
   }

   public boolean m_7832_(WorldGenLevel p_72678_, StructureFeatureManager p_72679_, ChunkGenerator p_72680_, Random p_72681_, BoundingBox p_72682_, ChunkPos p_72683_, BlockPos p_72684_) {
      BoundingBox boundingbox = this.f_73656_.m_74633_(this.f_73657_, this.f_73658_);
      if (!p_72682_.m_71051_(boundingbox.m_162394_())) {
         return true;
      } else {
         p_72682_.m_162386_(boundingbox);
         boolean flag = super.m_7832_(p_72678_, p_72679_, p_72680_, p_72681_, p_72682_, p_72683_, p_72684_);
         this.m_72719_(p_72681_, p_72678_);
         this.m_72703_(p_72681_, p_72678_);
         if (this.f_72655_.f_72740_ || this.f_72655_.f_72739_) {
            BlockPos.m_121919_(this.m_73547_()).forEach((p_163166_) -> {
               if (this.f_72655_.f_72740_) {
                  this.m_72706_(p_72681_, p_72678_, p_163166_);
               }

               if (this.f_72655_.f_72739_) {
                  this.m_72722_(p_72681_, p_72678_, p_163166_);
               }

            });
         }

         return flag;
      }
   }

   protected void m_7756_(String p_72698_, BlockPos p_72699_, ServerLevelAccessor p_72700_, Random p_72701_, BoundingBox p_72702_) {
   }

   private void m_72706_(Random p_72707_, LevelAccessor p_72708_, BlockPos p_72709_) {
      BlockState blockstate = p_72708_.m_8055_(p_72709_);
      if (!blockstate.m_60795_() && !blockstate.m_60713_(Blocks.f_50191_)) {
         Direction direction = m_163580_(p_72707_);
         BlockPos blockpos = p_72709_.m_142300_(direction);
         BlockState blockstate1 = p_72708_.m_8055_(blockpos);
         if (blockstate1.m_60795_()) {
            if (Block.m_49918_(blockstate.m_60812_(p_72708_, p_72709_), direction)) {
               BooleanProperty booleanproperty = VineBlock.m_57883_(direction.m_122424_());
               p_72708_.m_7731_(blockpos, Blocks.f_50191_.m_49966_().m_61124_(booleanproperty, Boolean.valueOf(true)), 3);
            }
         }
      }
   }

   private void m_72722_(Random p_72723_, LevelAccessor p_72724_, BlockPos p_72725_) {
      if (p_72723_.nextFloat() < 0.5F && p_72724_.m_8055_(p_72725_).m_60713_(Blocks.f_50134_) && p_72724_.m_8055_(p_72725_.m_7494_()).m_60795_()) {
         p_72724_.m_7731_(p_72725_.m_7494_(), Blocks.f_50053_.m_49966_().m_61124_(LeavesBlock.f_54419_, Boolean.valueOf(true)), 3);
      }

   }

   private void m_72703_(Random p_72704_, LevelAccessor p_72705_) {
      for(int i = this.f_73383_.m_162395_() + 1; i < this.f_73383_.m_162399_(); ++i) {
         for(int j = this.f_73383_.m_162398_() + 1; j < this.f_73383_.m_162401_(); ++j) {
            BlockPos blockpos = new BlockPos(i, this.f_73383_.m_162396_(), j);
            if (p_72705_.m_8055_(blockpos).m_60713_(Blocks.f_50134_)) {
               this.m_72727_(p_72704_, p_72705_, blockpos.m_7495_());
            }
         }
      }

   }

   private void m_72727_(Random p_72728_, LevelAccessor p_72729_, BlockPos p_72730_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_72730_.m_122032_();
      this.m_72731_(p_72728_, p_72729_, blockpos$mutableblockpos);
      int i = 8;

      while(i > 0 && p_72728_.nextFloat() < 0.5F) {
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         --i;
         this.m_72731_(p_72728_, p_72729_, blockpos$mutableblockpos);
      }

   }

   private void m_72719_(Random p_72720_, LevelAccessor p_72721_) {
      boolean flag = this.f_72654_ == RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE || this.f_72654_ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR;
      BlockPos blockpos = this.f_73383_.m_162394_();
      int i = blockpos.m_123341_();
      int j = blockpos.m_123343_();
      float[] afloat = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
      int k = afloat.length;
      int l = (this.f_73383_.m_71056_() + this.f_73383_.m_71058_()) / 2;
      int i1 = p_72720_.nextInt(Math.max(1, 8 - l / 2));
      int j1 = 3;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = BlockPos.f_121853_.m_122032_();

      for(int k1 = i - k; k1 <= i + k; ++k1) {
         for(int l1 = j - k; l1 <= j + k; ++l1) {
            int i2 = Math.abs(k1 - i) + Math.abs(l1 - j);
            int j2 = Math.max(0, i2 + i1);
            if (j2 < k) {
               float f = afloat[j2];
               if (p_72720_.nextDouble() < (double)f) {
                  int k2 = m_72669_(p_72721_, k1, l1, this.f_72654_);
                  int l2 = flag ? k2 : Math.min(this.f_73383_.m_162396_(), k2);
                  blockpos$mutableblockpos.m_122178_(k1, l2, l1);
                  if (Math.abs(l2 - this.f_73383_.m_162396_()) <= 3 && this.m_72674_(p_72721_, blockpos$mutableblockpos)) {
                     this.m_72731_(p_72720_, p_72721_, blockpos$mutableblockpos);
                     if (this.f_72655_.f_72739_) {
                        this.m_72722_(p_72720_, p_72721_, blockpos$mutableblockpos);
                     }

                     this.m_72727_(p_72720_, p_72721_, blockpos$mutableblockpos.m_7495_());
                  }
               }
            }
         }
      }

   }

   private boolean m_72674_(LevelAccessor p_72675_, BlockPos p_72676_) {
      BlockState blockstate = p_72675_.m_8055_(p_72676_);
      return !blockstate.m_60713_(Blocks.f_50016_) && !blockstate.m_60713_(Blocks.f_50080_) && !blockstate.m_60620_(BlockTags.f_144287_) && (this.f_72654_ == RuinedPortalPiece.VerticalPlacement.IN_NETHER || !blockstate.m_60713_(Blocks.f_49991_));
   }

   private void m_72731_(Random p_72732_, LevelAccessor p_72733_, BlockPos p_72734_) {
      if (!this.f_72655_.f_72736_ && p_72732_.nextFloat() < 0.07F) {
         p_72733_.m_7731_(p_72734_, Blocks.f_50450_.m_49966_(), 3);
      } else {
         p_72733_.m_7731_(p_72734_, Blocks.f_50134_.m_49966_(), 3);
      }

   }

   private static int m_72669_(LevelAccessor p_72670_, int p_72671_, int p_72672_, RuinedPortalPiece.VerticalPlacement p_72673_) {
      return p_72670_.m_6924_(m_72692_(p_72673_), p_72671_, p_72672_) - 1;
   }

   public static Heightmap.Types m_72692_(RuinedPortalPiece.VerticalPlacement p_72693_) {
      return p_72693_ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
   }

   private static ProcessorRule m_72685_(Block p_72686_, float p_72687_, Block p_72688_) {
      return new ProcessorRule(new RandomBlockMatchTest(p_72686_, p_72687_), AlwaysTrueTest.f_73954_, p_72688_.m_49966_());
   }

   private static ProcessorRule m_72689_(Block p_72690_, Block p_72691_) {
      return new ProcessorRule(new BlockMatchTest(p_72690_), AlwaysTrueTest.f_73954_, p_72691_.m_49966_());
   }

   public static class Properties {
      public static final Codec<RuinedPortalPiece.Properties> f_72735_ = RecordCodecBuilder.create((p_72752_) -> {
         return p_72752_.group(Codec.BOOL.fieldOf("cold").forGetter((p_163185_) -> {
            return p_163185_.f_72736_;
         }), Codec.FLOAT.fieldOf("mossiness").forGetter((p_163183_) -> {
            return p_163183_.f_72737_;
         }), Codec.BOOL.fieldOf("air_pocket").forGetter((p_163181_) -> {
            return p_163181_.f_72738_;
         }), Codec.BOOL.fieldOf("overgrown").forGetter((p_163179_) -> {
            return p_163179_.f_72739_;
         }), Codec.BOOL.fieldOf("vines").forGetter((p_163177_) -> {
            return p_163177_.f_72740_;
         }), Codec.BOOL.fieldOf("replace_with_blackstone").forGetter((p_163175_) -> {
            return p_163175_.f_72741_;
         })).apply(p_72752_, RuinedPortalPiece.Properties::new);
      });
      public boolean f_72736_;
      public float f_72737_ = 0.2F;
      public boolean f_72738_;
      public boolean f_72739_;
      public boolean f_72740_;
      public boolean f_72741_;

      public Properties() {
      }

      public <T> Properties(boolean p_72745_, float p_72746_, boolean p_72747_, boolean p_72748_, boolean p_72749_, boolean p_72750_) {
         this.f_72736_ = p_72745_;
         this.f_72737_ = p_72746_;
         this.f_72738_ = p_72747_;
         this.f_72739_ = p_72748_;
         this.f_72740_ = p_72749_;
         this.f_72741_ = p_72750_;
      }
   }

   public static enum VerticalPlacement {
      ON_LAND_SURFACE("on_land_surface"),
      PARTLY_BURIED("partly_buried"),
      ON_OCEAN_FLOOR("on_ocean_floor"),
      IN_MOUNTAIN("in_mountain"),
      UNDERGROUND("underground"),
      IN_NETHER("in_nether");

      private static final Map<String, RuinedPortalPiece.VerticalPlacement> f_72771_ = Arrays.stream(values()).collect(Collectors.toMap(RuinedPortalPiece.VerticalPlacement::m_72779_, (p_72781_) -> {
         return p_72781_;
      }));
      private final String f_72772_;

      private VerticalPlacement(String p_72778_) {
         this.f_72772_ = p_72778_;
      }

      public String m_72779_() {
         return this.f_72772_;
      }

      public static RuinedPortalPiece.VerticalPlacement m_72782_(String p_72783_) {
         return f_72771_.get(p_72783_);
      }
   }
}