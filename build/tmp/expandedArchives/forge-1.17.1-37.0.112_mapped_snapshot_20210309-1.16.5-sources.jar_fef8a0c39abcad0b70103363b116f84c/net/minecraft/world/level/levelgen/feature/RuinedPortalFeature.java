package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.RuinedPortalConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class RuinedPortalFeature extends StructureFeature<RuinedPortalConfiguration> {
   static final String[] f_66664_ = new String[]{"ruined_portal/portal_1", "ruined_portal/portal_2", "ruined_portal/portal_3", "ruined_portal/portal_4", "ruined_portal/portal_5", "ruined_portal/portal_6", "ruined_portal/portal_7", "ruined_portal/portal_8", "ruined_portal/portal_9", "ruined_portal/portal_10"};
   static final String[] f_66665_ = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};
   private static final float f_160264_ = 0.05F;
   private static final float f_160265_ = 0.5F;
   private static final float f_160266_ = 0.5F;
   private static final float f_160260_ = 0.8F;
   private static final float f_160261_ = 0.8F;
   private static final float f_160262_ = 0.5F;
   private static final int f_160263_ = 15;

   public RuinedPortalFeature(Codec<RuinedPortalConfiguration> p_66668_) {
      super(p_66668_);
   }

   public StructureFeature.StructureStartFactory<RuinedPortalConfiguration> m_6258_() {
      return RuinedPortalFeature.FeatureStart::new;
   }

   static boolean m_66688_(BlockPos p_66689_, Biome p_66690_) {
      return p_66690_.m_47505_(p_66689_) < 0.15F;
   }

   static int m_160271_(Random p_160272_, ChunkGenerator p_160273_, RuinedPortalPiece.VerticalPlacement p_160274_, boolean p_160275_, int p_160276_, int p_160277_, BoundingBox p_160278_, LevelHeightAccessor p_160279_) {
      int i;
      if (p_160274_ == RuinedPortalPiece.VerticalPlacement.IN_NETHER) {
         if (p_160275_) {
            i = Mth.m_144928_(p_160272_, 32, 100);
         } else if (p_160272_.nextFloat() < 0.5F) {
            i = Mth.m_144928_(p_160272_, 27, 29);
         } else {
            i = Mth.m_144928_(p_160272_, 29, 100);
         }
      } else if (p_160274_ == RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN) {
         int j = p_160276_ - p_160277_;
         i = m_66691_(p_160272_, 70, j);
      } else if (p_160274_ == RuinedPortalPiece.VerticalPlacement.UNDERGROUND) {
         int i1 = p_160276_ - p_160277_;
         i = m_66691_(p_160272_, 15, i1);
      } else if (p_160274_ == RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED) {
         i = p_160276_ - p_160277_ + Mth.m_144928_(p_160272_, 2, 8);
      } else {
         i = p_160276_;
      }

      List<BlockPos> list1 = ImmutableList.of(new BlockPos(p_160278_.m_162395_(), 0, p_160278_.m_162398_()), new BlockPos(p_160278_.m_162399_(), 0, p_160278_.m_162398_()), new BlockPos(p_160278_.m_162395_(), 0, p_160278_.m_162401_()), new BlockPos(p_160278_.m_162399_(), 0, p_160278_.m_162401_()));
      List<NoiseColumn> list = list1.stream().map((p_160270_) -> {
         return p_160273_.m_141914_(p_160270_.m_123341_(), p_160270_.m_123343_(), p_160279_);
      }).collect(Collectors.toList());
      Heightmap.Types heightmap$types = p_160274_ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      int k;
      for(k = i; k > 15; --k) {
         int l = 0;
         blockpos$mutableblockpos.m_122178_(0, k, 0);

         for(NoiseColumn noisecolumn : list) {
            BlockState blockstate = noisecolumn.m_47156_(blockpos$mutableblockpos);
            if (heightmap$types.m_64299_().test(blockstate)) {
               ++l;
               if (l == 3) {
                  return k;
               }
            }
         }
      }

      return k;
   }

   private static int m_66691_(Random p_66692_, int p_66693_, int p_66694_) {
      return p_66693_ < p_66694_ ? Mth.m_144928_(p_66692_, p_66693_, p_66694_) : p_66694_;
   }

   public static class FeatureStart extends StructureStart<RuinedPortalConfiguration> {
      protected FeatureStart(StructureFeature<RuinedPortalConfiguration> p_160281_, ChunkPos p_160282_, int p_160283_, long p_160284_) {
         super(p_160281_, p_160282_, p_160283_, p_160284_);
      }

      public void m_142743_(RegistryAccess p_160294_, ChunkGenerator p_160295_, StructureManager p_160296_, ChunkPos p_160297_, Biome p_160298_, RuinedPortalConfiguration p_160299_, LevelHeightAccessor p_160300_) {
         RuinedPortalPiece.Properties ruinedportalpiece$properties = new RuinedPortalPiece.Properties();
         RuinedPortalPiece.VerticalPlacement ruinedportalpiece$verticalplacement;
         if (p_160299_.f_68055_ == RuinedPortalFeature.Type.DESERT) {
            ruinedportalpiece$verticalplacement = RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED;
            ruinedportalpiece$properties.f_72738_ = false;
            ruinedportalpiece$properties.f_72737_ = 0.0F;
         } else if (p_160299_.f_68055_ == RuinedPortalFeature.Type.JUNGLE) {
            ruinedportalpiece$verticalplacement = RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE;
            ruinedportalpiece$properties.f_72738_ = this.f_73564_.nextFloat() < 0.5F;
            ruinedportalpiece$properties.f_72737_ = 0.8F;
            ruinedportalpiece$properties.f_72739_ = true;
            ruinedportalpiece$properties.f_72740_ = true;
         } else if (p_160299_.f_68055_ == RuinedPortalFeature.Type.SWAMP) {
            ruinedportalpiece$verticalplacement = RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR;
            ruinedportalpiece$properties.f_72738_ = false;
            ruinedportalpiece$properties.f_72737_ = 0.5F;
            ruinedportalpiece$properties.f_72740_ = true;
         } else if (p_160299_.f_68055_ == RuinedPortalFeature.Type.MOUNTAIN) {
            boolean flag = this.f_73564_.nextFloat() < 0.5F;
            ruinedportalpiece$verticalplacement = flag ? RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN : RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE;
            ruinedportalpiece$properties.f_72738_ = flag || this.f_73564_.nextFloat() < 0.5F;
         } else if (p_160299_.f_68055_ == RuinedPortalFeature.Type.OCEAN) {
            ruinedportalpiece$verticalplacement = RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR;
            ruinedportalpiece$properties.f_72738_ = false;
            ruinedportalpiece$properties.f_72737_ = 0.8F;
         } else if (p_160299_.f_68055_ == RuinedPortalFeature.Type.NETHER) {
            ruinedportalpiece$verticalplacement = RuinedPortalPiece.VerticalPlacement.IN_NETHER;
            ruinedportalpiece$properties.f_72738_ = this.f_73564_.nextFloat() < 0.5F;
            ruinedportalpiece$properties.f_72737_ = 0.0F;
            ruinedportalpiece$properties.f_72741_ = true;
         } else {
            boolean flag1 = this.f_73564_.nextFloat() < 0.5F;
            ruinedportalpiece$verticalplacement = flag1 ? RuinedPortalPiece.VerticalPlacement.UNDERGROUND : RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE;
            ruinedportalpiece$properties.f_72738_ = flag1 || this.f_73564_.nextFloat() < 0.5F;
         }

         ResourceLocation resourcelocation;
         if (this.f_73564_.nextFloat() < 0.05F) {
            resourcelocation = new ResourceLocation(RuinedPortalFeature.f_66665_[this.f_73564_.nextInt(RuinedPortalFeature.f_66665_.length)]);
         } else {
            resourcelocation = new ResourceLocation(RuinedPortalFeature.f_66664_[this.f_73564_.nextInt(RuinedPortalFeature.f_66664_.length)]);
         }

         StructureTemplate structuretemplate = p_160296_.m_74341_(resourcelocation);
         Rotation rotation = Util.m_137545_(Rotation.values(), this.f_73564_);
         Mirror mirror = this.f_73564_.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
         BlockPos blockpos = new BlockPos(structuretemplate.m_163801_().m_123341_() / 2, 0, structuretemplate.m_163801_().m_123343_() / 2);
         BlockPos blockpos1 = p_160297_.m_45615_();
         BoundingBox boundingbox = structuretemplate.m_74598_(blockpos1, rotation, blockpos, mirror);
         BlockPos blockpos2 = boundingbox.m_162394_();
         int i = blockpos2.m_123341_();
         int j = blockpos2.m_123343_();
         int k = p_160295_.m_142647_(i, j, RuinedPortalPiece.m_72692_(ruinedportalpiece$verticalplacement), p_160300_) - 1;
         int l = RuinedPortalFeature.m_160271_(this.f_73564_, p_160295_, ruinedportalpiece$verticalplacement, ruinedportalpiece$properties.f_72738_, k, boundingbox.m_71057_(), boundingbox, p_160300_);
         BlockPos blockpos3 = new BlockPos(blockpos1.m_123341_(), l, blockpos1.m_123343_());
         if (p_160299_.f_68055_ == RuinedPortalFeature.Type.MOUNTAIN || p_160299_.f_68055_ == RuinedPortalFeature.Type.OCEAN || p_160299_.f_68055_ == RuinedPortalFeature.Type.STANDARD) {
            ruinedportalpiece$properties.f_72736_ = RuinedPortalFeature.m_66688_(blockpos3, p_160298_);
         }

         this.m_142679_(new RuinedPortalPiece(p_160296_, blockpos3, ruinedportalpiece$verticalplacement, ruinedportalpiece$properties, resourcelocation, structuretemplate, rotation, mirror, blockpos));
      }
   }

   public static enum Type implements StringRepresentable {
      STANDARD("standard"),
      DESERT("desert"),
      JUNGLE("jungle"),
      SWAMP("swamp"),
      MOUNTAIN("mountain"),
      OCEAN("ocean"),
      NETHER("nether");

      public static final Codec<RuinedPortalFeature.Type> f_66735_ = StringRepresentable.m_14350_(RuinedPortalFeature.Type::values, RuinedPortalFeature.Type::m_66747_);
      private static final Map<String, RuinedPortalFeature.Type> f_66736_ = Arrays.stream(values()).collect(Collectors.toMap(RuinedPortalFeature.Type::m_66749_, (p_66746_) -> {
         return p_66746_;
      }));
      private final String f_66737_;

      private Type(String p_66743_) {
         this.f_66737_ = p_66743_;
      }

      public String m_66749_() {
         return this.f_66737_;
      }

      public static RuinedPortalFeature.Type m_66747_(String p_66748_) {
         return f_66736_.get(p_66748_);
      }

      public String m_7912_() {
         return this.f_66737_;
      }
   }
}