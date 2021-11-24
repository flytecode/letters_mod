package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class EndCityPieces {
   private static final int f_162415_ = 8;
   static final EndCityPieces.SectionGenerator f_71101_ = new EndCityPieces.SectionGenerator() {
      public void m_7635_() {
      }

      public boolean m_6279_(StructureManager p_71161_, int p_71162_, EndCityPieces.EndCityPiece p_71163_, BlockPos p_71164_, List<StructurePiece> p_71165_, Random p_71166_) {
         if (p_71162_ > 8) {
            return false;
         } else {
            Rotation rotation = p_71163_.f_73657_.m_74404_();
            EndCityPieces.EndCityPiece endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71165_, EndCityPieces.m_71134_(p_71161_, p_71163_, p_71164_, "base_floor", rotation, true));
            int i = p_71166_.nextInt(3);
            if (i == 0) {
               EndCityPieces.m_71149_(p_71165_, EndCityPieces.m_71134_(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "base_roof", rotation, true));
            } else if (i == 1) {
               endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71165_, EndCityPieces.m_71134_(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
               endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71165_, EndCityPieces.m_71134_(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 8, -1), "second_roof", rotation, false));
               EndCityPieces.m_71141_(p_71161_, EndCityPieces.f_71103_, p_71162_ + 1, endcitypieces$endcitypiece, (BlockPos)null, p_71165_, p_71166_);
            } else if (i == 2) {
               endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71165_, EndCityPieces.m_71134_(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
               endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71165_, EndCityPieces.m_71134_(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "third_floor_2", rotation, false));
               endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71165_, EndCityPieces.m_71134_(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
               EndCityPieces.m_71141_(p_71161_, EndCityPieces.f_71103_, p_71162_ + 1, endcitypieces$endcitypiece, (BlockPos)null, p_71165_, p_71166_);
            }

            return true;
         }
      }
   };
   static final List<Tuple<Rotation, BlockPos>> f_71102_ = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(1, -1, 0)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6)));
   static final EndCityPieces.SectionGenerator f_71103_ = new EndCityPieces.SectionGenerator() {
      public void m_7635_() {
      }

      public boolean m_6279_(StructureManager p_71170_, int p_71171_, EndCityPieces.EndCityPiece p_71172_, BlockPos p_71173_, List<StructurePiece> p_71174_, Random p_71175_) {
         Rotation rotation = p_71172_.f_73657_.m_74404_();
         EndCityPieces.EndCityPiece endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71174_, EndCityPieces.m_71134_(p_71170_, p_71172_, new BlockPos(3 + p_71175_.nextInt(2), -3, 3 + p_71175_.nextInt(2)), "tower_base", rotation, true));
         endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71174_, EndCityPieces.m_71134_(p_71170_, endcitypieces$endcitypiece, new BlockPos(0, 7, 0), "tower_piece", rotation, true));
         EndCityPieces.EndCityPiece endcitypieces$endcitypiece1 = p_71175_.nextInt(3) == 0 ? endcitypieces$endcitypiece : null;
         int i = 1 + p_71175_.nextInt(3);

         for(int j = 0; j < i; ++j) {
            endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71174_, EndCityPieces.m_71134_(p_71170_, endcitypieces$endcitypiece, new BlockPos(0, 4, 0), "tower_piece", rotation, true));
            if (j < i - 1 && p_71175_.nextBoolean()) {
               endcitypieces$endcitypiece1 = endcitypieces$endcitypiece;
            }
         }

         if (endcitypieces$endcitypiece1 != null) {
            for(Tuple<Rotation, BlockPos> tuple : EndCityPieces.f_71102_) {
               if (p_71175_.nextBoolean()) {
                  EndCityPieces.EndCityPiece endcitypieces$endcitypiece2 = EndCityPieces.m_71149_(p_71174_, EndCityPieces.m_71134_(p_71170_, endcitypieces$endcitypiece1, tuple.m_14419_(), "bridge_end", rotation.m_55952_(tuple.m_14418_()), true));
                  EndCityPieces.m_71141_(p_71170_, EndCityPieces.f_71104_, p_71171_ + 1, endcitypieces$endcitypiece2, (BlockPos)null, p_71174_, p_71175_);
               }
            }

            EndCityPieces.m_71149_(p_71174_, EndCityPieces.m_71134_(p_71170_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
         } else {
            if (p_71171_ != 7) {
               return EndCityPieces.m_71141_(p_71170_, EndCityPieces.f_71106_, p_71171_ + 1, endcitypieces$endcitypiece, (BlockPos)null, p_71174_, p_71175_);
            }

            EndCityPieces.m_71149_(p_71174_, EndCityPieces.m_71134_(p_71170_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
         }

         return true;
      }
   };
   static final EndCityPieces.SectionGenerator f_71104_ = new EndCityPieces.SectionGenerator() {
      public boolean f_71176_;

      public void m_7635_() {
         this.f_71176_ = false;
      }

      public boolean m_6279_(StructureManager p_71180_, int p_71181_, EndCityPieces.EndCityPiece p_71182_, BlockPos p_71183_, List<StructurePiece> p_71184_, Random p_71185_) {
         Rotation rotation = p_71182_.f_73657_.m_74404_();
         int i = p_71185_.nextInt(4) + 1;
         EndCityPieces.EndCityPiece endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71184_, EndCityPieces.m_71134_(p_71180_, p_71182_, new BlockPos(0, 0, -4), "bridge_piece", rotation, true));
         endcitypieces$endcitypiece.f_73384_ = -1;
         int j = 0;

         for(int k = 0; k < i; ++k) {
            if (p_71185_.nextBoolean()) {
               endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71184_, EndCityPieces.m_71134_(p_71180_, endcitypieces$endcitypiece, new BlockPos(0, j, -4), "bridge_piece", rotation, true));
               j = 0;
            } else {
               if (p_71185_.nextBoolean()) {
                  endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71184_, EndCityPieces.m_71134_(p_71180_, endcitypieces$endcitypiece, new BlockPos(0, j, -4), "bridge_steep_stairs", rotation, true));
               } else {
                  endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71184_, EndCityPieces.m_71134_(p_71180_, endcitypieces$endcitypiece, new BlockPos(0, j, -8), "bridge_gentle_stairs", rotation, true));
               }

               j = 4;
            }
         }

         if (!this.f_71176_ && p_71185_.nextInt(10 - p_71181_) == 0) {
            EndCityPieces.m_71149_(p_71184_, EndCityPieces.m_71134_(p_71180_, endcitypieces$endcitypiece, new BlockPos(-8 + p_71185_.nextInt(8), j, -70 + p_71185_.nextInt(10)), "ship", rotation, true));
            this.f_71176_ = true;
         } else if (!EndCityPieces.m_71141_(p_71180_, EndCityPieces.f_71101_, p_71181_ + 1, endcitypieces$endcitypiece, new BlockPos(-3, j + 1, -11), p_71184_, p_71185_)) {
            return false;
         }

         endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71184_, EndCityPieces.m_71134_(p_71180_, endcitypieces$endcitypiece, new BlockPos(4, j, 0), "bridge_end", rotation.m_55952_(Rotation.CLOCKWISE_180), true));
         endcitypieces$endcitypiece.f_73384_ = -1;
         return true;
      }
   };
   static final List<Tuple<Rotation, BlockPos>> f_71105_ = Lists.newArrayList(new Tuple<>(Rotation.NONE, new BlockPos(4, -1, 0)), new Tuple<>(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple<>(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)), new Tuple<>(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12)));
   static final EndCityPieces.SectionGenerator f_71106_ = new EndCityPieces.SectionGenerator() {
      public void m_7635_() {
      }

      public boolean m_6279_(StructureManager p_71189_, int p_71190_, EndCityPieces.EndCityPiece p_71191_, BlockPos p_71192_, List<StructurePiece> p_71193_, Random p_71194_) {
         Rotation rotation = p_71191_.f_73657_.m_74404_();
         EndCityPieces.EndCityPiece endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71193_, EndCityPieces.m_71134_(p_71189_, p_71191_, new BlockPos(-3, 4, -3), "fat_tower_base", rotation, true));
         endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71193_, EndCityPieces.m_71134_(p_71189_, endcitypieces$endcitypiece, new BlockPos(0, 4, 0), "fat_tower_middle", rotation, true));

         for(int i = 0; i < 2 && p_71194_.nextInt(3) != 0; ++i) {
            endcitypieces$endcitypiece = EndCityPieces.m_71149_(p_71193_, EndCityPieces.m_71134_(p_71189_, endcitypieces$endcitypiece, new BlockPos(0, 8, 0), "fat_tower_middle", rotation, true));

            for(Tuple<Rotation, BlockPos> tuple : EndCityPieces.f_71105_) {
               if (p_71194_.nextBoolean()) {
                  EndCityPieces.EndCityPiece endcitypieces$endcitypiece1 = EndCityPieces.m_71149_(p_71193_, EndCityPieces.m_71134_(p_71189_, endcitypieces$endcitypiece, tuple.m_14419_(), "bridge_end", rotation.m_55952_(tuple.m_14418_()), true));
                  EndCityPieces.m_71141_(p_71189_, EndCityPieces.f_71104_, p_71190_ + 1, endcitypieces$endcitypiece1, (BlockPos)null, p_71193_, p_71194_);
               }
            }
         }

         EndCityPieces.m_71149_(p_71193_, EndCityPieces.m_71134_(p_71189_, endcitypieces$endcitypiece, new BlockPos(-2, 8, -2), "fat_tower_top", rotation, true));
         return true;
      }
   };

   static EndCityPieces.EndCityPiece m_71134_(StructureManager p_71135_, EndCityPieces.EndCityPiece p_71136_, BlockPos p_71137_, String p_71138_, Rotation p_71139_, boolean p_71140_) {
      EndCityPieces.EndCityPiece endcitypieces$endcitypiece = new EndCityPieces.EndCityPiece(p_71135_, p_71138_, p_71136_.f_73658_, p_71139_, p_71140_);
      BlockPos blockpos = p_71136_.f_73656_.m_74566_(p_71136_.f_73657_, p_71137_, endcitypieces$endcitypiece.f_73657_, BlockPos.f_121853_);
      endcitypieces$endcitypiece.m_6324_(blockpos.m_123341_(), blockpos.m_123342_(), blockpos.m_123343_());
      return endcitypieces$endcitypiece;
   }

   public static void m_71124_(StructureManager p_71125_, BlockPos p_71126_, Rotation p_71127_, List<StructurePiece> p_71128_, Random p_71129_) {
      f_71106_.m_7635_();
      f_71101_.m_7635_();
      f_71104_.m_7635_();
      f_71103_.m_7635_();
      EndCityPieces.EndCityPiece endcitypieces$endcitypiece = m_71149_(p_71128_, new EndCityPieces.EndCityPiece(p_71125_, "base_floor", p_71126_, p_71127_, true));
      endcitypieces$endcitypiece = m_71149_(p_71128_, m_71134_(p_71125_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor_1", p_71127_, false));
      endcitypieces$endcitypiece = m_71149_(p_71128_, m_71134_(p_71125_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "third_floor_1", p_71127_, false));
      endcitypieces$endcitypiece = m_71149_(p_71128_, m_71134_(p_71125_, endcitypieces$endcitypiece, new BlockPos(-1, 8, -1), "third_roof", p_71127_, true));
      m_71141_(p_71125_, f_71103_, 1, endcitypieces$endcitypiece, (BlockPos)null, p_71128_, p_71129_);
   }

   static EndCityPieces.EndCityPiece m_71149_(List<StructurePiece> p_71150_, EndCityPieces.EndCityPiece p_71151_) {
      p_71150_.add(p_71151_);
      return p_71151_;
   }

   static boolean m_71141_(StructureManager p_71142_, EndCityPieces.SectionGenerator p_71143_, int p_71144_, EndCityPieces.EndCityPiece p_71145_, BlockPos p_71146_, List<StructurePiece> p_71147_, Random p_71148_) {
      if (p_71144_ > 8) {
         return false;
      } else {
         List<StructurePiece> list = Lists.newArrayList();
         if (p_71143_.m_6279_(p_71142_, p_71144_, p_71145_, p_71146_, list, p_71148_)) {
            boolean flag = false;
            int i = p_71148_.nextInt();

            for(StructurePiece structurepiece : list) {
               structurepiece.f_73384_ = i;
               StructurePiece structurepiece1 = StructureStart.m_163622_(p_71147_, structurepiece.m_73547_());
               if (structurepiece1 != null && structurepiece1.f_73384_ != p_71145_.f_73384_) {
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               p_71147_.addAll(list);
               return true;
            }
         }

         return false;
      }
   }

   public static class EndCityPiece extends TemplateStructurePiece {
      public EndCityPiece(StructureManager p_71199_, String p_71200_, BlockPos p_71201_, Rotation p_71202_, boolean p_71203_) {
         super(StructurePieceType.f_67130_, 0, p_71199_, m_162424_(p_71200_), p_71200_, m_162429_(p_71203_, p_71202_), p_71201_);
      }

      public EndCityPiece(ServerLevel p_162418_, CompoundTag p_162419_) {
         super(StructurePieceType.f_67130_, p_162419_, p_162418_, (p_162428_) -> {
            return m_162429_(p_162419_.m_128471_("OW"), Rotation.valueOf(p_162419_.m_128461_("Rot")));
         });
      }

      private static StructurePlaceSettings m_162429_(boolean p_162430_, Rotation p_162431_) {
         BlockIgnoreProcessor blockignoreprocessor = p_162430_ ? BlockIgnoreProcessor.f_74046_ : BlockIgnoreProcessor.f_74048_;
         return (new StructurePlaceSettings()).m_74392_(true).m_74383_(blockignoreprocessor).m_74379_(p_162431_);
      }

      protected ResourceLocation m_142415_() {
         return m_162424_(this.f_163658_);
      }

      private static ResourceLocation m_162424_(String p_162425_) {
         return new ResourceLocation("end_city/" + p_162425_);
      }

      protected void m_142347_(ServerLevel p_162422_, CompoundTag p_162423_) {
         super.m_142347_(p_162422_, p_162423_);
         p_162423_.m_128359_("Rot", this.f_73657_.m_74404_().name());
         p_162423_.m_128379_("OW", this.f_73657_.m_74411_().get(0) == BlockIgnoreProcessor.f_74046_);
      }

      protected void m_7756_(String p_71210_, BlockPos p_71211_, ServerLevelAccessor p_71212_, Random p_71213_, BoundingBox p_71214_) {
         if (p_71210_.startsWith("Chest")) {
            BlockPos blockpos = p_71211_.m_7495_();
            if (p_71214_.m_71051_(blockpos)) {
               RandomizableContainerBlockEntity.m_59620_(p_71212_, p_71213_, blockpos, BuiltInLootTables.f_78741_);
            }
         } else if (p_71214_.m_71051_(p_71211_) && Level.m_46741_(p_71211_)) {
            if (p_71210_.startsWith("Sentry")) {
               Shulker shulker = EntityType.f_20521_.m_20615_(p_71212_.m_6018_());
               shulker.m_6034_((double)p_71211_.m_123341_() + 0.5D, (double)p_71211_.m_123342_(), (double)p_71211_.m_123343_() + 0.5D);
               p_71212_.m_7967_(shulker);
            } else if (p_71210_.startsWith("Elytra")) {
               ItemFrame itemframe = new ItemFrame(p_71212_.m_6018_(), p_71211_, this.f_73657_.m_74404_().m_55954_(Direction.SOUTH));
               itemframe.m_31789_(new ItemStack(Items.f_42741_), false);
               p_71212_.m_7967_(itemframe);
            }
         }

      }
   }

   interface SectionGenerator {
      void m_7635_();

      boolean m_6279_(StructureManager p_71217_, int p_71218_, EndCityPieces.EndCityPiece p_71219_, BlockPos p_71220_, List<StructurePiece> p_71221_, Random p_71222_);
   }
}