package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class JunglePyramidPiece extends ScatteredFeaturePiece {
   private boolean f_71267_;
   private boolean f_71268_;
   private boolean f_71269_;
   private boolean f_71270_;
   private static final JunglePyramidPiece.MossStoneSelector f_71271_ = new JunglePyramidPiece.MossStoneSelector();

   public JunglePyramidPiece(Random p_71277_, int p_71278_, int p_71279_) {
      super(StructurePieceType.f_67112_, p_71278_, 64, p_71279_, 12, 10, 15, m_163580_(p_71277_));
   }

   public JunglePyramidPiece(ServerLevel p_162457_, CompoundTag p_162458_) {
      super(StructurePieceType.f_67112_, p_162458_);
      this.f_71267_ = p_162458_.m_128471_("placedMainChest");
      this.f_71268_ = p_162458_.m_128471_("placedHiddenChest");
      this.f_71269_ = p_162458_.m_128471_("placedTrap1");
      this.f_71270_ = p_162458_.m_128471_("placedTrap2");
   }

   protected void m_142347_(ServerLevel p_162460_, CompoundTag p_162461_) {
      super.m_142347_(p_162460_, p_162461_);
      p_162461_.m_128379_("placedMainChest", this.f_71267_);
      p_162461_.m_128379_("placedHiddenChest", this.f_71268_);
      p_162461_.m_128379_("placedTrap1", this.f_71269_);
      p_162461_.m_128379_("placedTrap2", this.f_71270_);
   }

   public boolean m_7832_(WorldGenLevel p_71281_, StructureFeatureManager p_71282_, ChunkGenerator p_71283_, Random p_71284_, BoundingBox p_71285_, ChunkPos p_71286_, BlockPos p_71287_) {
      if (!this.m_72803_(p_71281_, p_71285_, 0)) {
         return false;
      } else {
         this.m_73464_(p_71281_, p_71285_, 0, -4, 0, this.f_72787_ - 1, 0, this.f_72789_ - 1, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 2, 1, 2, 9, 2, 2, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 2, 1, 12, 9, 2, 12, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 2, 1, 3, 2, 2, 11, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 9, 1, 3, 9, 2, 11, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 1, 3, 1, 10, 6, 1, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 1, 3, 13, 10, 6, 13, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 1, 3, 2, 1, 6, 12, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 10, 3, 2, 10, 6, 12, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 2, 3, 2, 9, 3, 12, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 2, 6, 2, 9, 6, 12, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 3, 7, 3, 8, 7, 11, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 4, 8, 4, 7, 8, 10, false, p_71284_, f_71271_);
         this.m_73535_(p_71281_, p_71285_, 3, 1, 3, 8, 2, 11);
         this.m_73535_(p_71281_, p_71285_, 4, 3, 6, 7, 3, 9);
         this.m_73535_(p_71281_, p_71285_, 2, 4, 2, 9, 5, 12);
         this.m_73535_(p_71281_, p_71285_, 4, 6, 5, 7, 6, 9);
         this.m_73535_(p_71281_, p_71285_, 5, 7, 6, 6, 7, 8);
         this.m_73535_(p_71281_, p_71285_, 5, 1, 2, 6, 2, 2);
         this.m_73535_(p_71281_, p_71285_, 5, 2, 12, 6, 2, 12);
         this.m_73535_(p_71281_, p_71285_, 5, 5, 1, 6, 5, 1);
         this.m_73535_(p_71281_, p_71285_, 5, 5, 13, 6, 5, 13);
         this.m_73434_(p_71281_, Blocks.f_50016_.m_49966_(), 1, 5, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50016_.m_49966_(), 10, 5, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50016_.m_49966_(), 1, 5, 9, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50016_.m_49966_(), 10, 5, 9, p_71285_);

         for(int i = 0; i <= 14; i += 14) {
            this.m_73464_(p_71281_, p_71285_, 2, 4, i, 2, 5, i, false, p_71284_, f_71271_);
            this.m_73464_(p_71281_, p_71285_, 4, 4, i, 4, 5, i, false, p_71284_, f_71271_);
            this.m_73464_(p_71281_, p_71285_, 7, 4, i, 7, 5, i, false, p_71284_, f_71271_);
            this.m_73464_(p_71281_, p_71285_, 9, 4, i, 9, 5, i, false, p_71284_, f_71271_);
         }

         this.m_73464_(p_71281_, p_71285_, 5, 6, 0, 6, 6, 0, false, p_71284_, f_71271_);

         for(int l = 0; l <= 11; l += 11) {
            for(int j = 2; j <= 12; j += 2) {
               this.m_73464_(p_71281_, p_71285_, l, 4, j, l, 5, j, false, p_71284_, f_71271_);
            }

            this.m_73464_(p_71281_, p_71285_, l, 6, 5, l, 6, 5, false, p_71284_, f_71271_);
            this.m_73464_(p_71281_, p_71285_, l, 6, 9, l, 6, 9, false, p_71284_, f_71271_);
         }

         this.m_73464_(p_71281_, p_71285_, 2, 7, 2, 2, 9, 2, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 9, 7, 2, 9, 9, 2, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 2, 7, 12, 2, 9, 12, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 9, 7, 12, 9, 9, 12, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 4, 9, 4, 4, 9, 4, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 7, 9, 4, 7, 9, 4, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 4, 9, 10, 4, 9, 10, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 7, 9, 10, 7, 9, 10, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 5, 9, 7, 6, 9, 7, false, p_71284_, f_71271_);
         BlockState blockstate3 = Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.EAST);
         BlockState blockstate4 = Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.WEST);
         BlockState blockstate = Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
         BlockState blockstate1 = Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
         this.m_73434_(p_71281_, blockstate1, 5, 9, 6, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 6, 9, 6, p_71285_);
         this.m_73434_(p_71281_, blockstate, 5, 9, 8, p_71285_);
         this.m_73434_(p_71281_, blockstate, 6, 9, 8, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 4, 0, 0, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 5, 0, 0, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 6, 0, 0, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 7, 0, 0, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 4, 1, 8, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 4, 2, 9, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 4, 3, 10, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 7, 1, 8, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 7, 2, 9, p_71285_);
         this.m_73434_(p_71281_, blockstate1, 7, 3, 10, p_71285_);
         this.m_73464_(p_71281_, p_71285_, 4, 1, 9, 4, 1, 9, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 7, 1, 9, 7, 1, 9, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 4, 1, 10, 7, 2, 10, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 5, 4, 5, 6, 4, 5, false, p_71284_, f_71271_);
         this.m_73434_(p_71281_, blockstate3, 4, 4, 5, p_71285_);
         this.m_73434_(p_71281_, blockstate4, 7, 4, 5, p_71285_);

         for(int k = 0; k < 4; ++k) {
            this.m_73434_(p_71281_, blockstate, 5, 0 - k, 6 + k, p_71285_);
            this.m_73434_(p_71281_, blockstate, 6, 0 - k, 6 + k, p_71285_);
            this.m_73535_(p_71281_, p_71285_, 5, 0 - k, 7 + k, 6, 0 - k, 9 + k);
         }

         this.m_73535_(p_71281_, p_71285_, 1, -3, 12, 10, -1, 13);
         this.m_73535_(p_71281_, p_71285_, 1, -3, 1, 3, -1, 13);
         this.m_73535_(p_71281_, p_71285_, 1, -3, 1, 9, -1, 5);

         for(int i1 = 1; i1 <= 13; i1 += 2) {
            this.m_73464_(p_71281_, p_71285_, 1, -3, i1, 1, -2, i1, false, p_71284_, f_71271_);
         }

         for(int j1 = 2; j1 <= 12; j1 += 2) {
            this.m_73464_(p_71281_, p_71285_, 1, -1, j1, 3, -1, j1, false, p_71284_, f_71271_);
         }

         this.m_73464_(p_71281_, p_71285_, 2, -2, 1, 5, -2, 1, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 7, -2, 1, 9, -2, 1, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 6, -3, 1, 6, -3, 1, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 6, -1, 1, 6, -1, 1, false, p_71284_, f_71271_);
         this.m_73434_(p_71281_, Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.EAST).m_61124_(TripWireHookBlock.f_57669_, Boolean.valueOf(true)), 1, -3, 8, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.WEST).m_61124_(TripWireHookBlock.f_57669_, Boolean.valueOf(true)), 4, -3, 8, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57594_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57596_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57591_, Boolean.valueOf(true)), 2, -3, 8, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57594_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57596_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57591_, Boolean.valueOf(true)), 3, -3, 8, p_71285_);
         BlockState blockstate5 = Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.SIDE);
         this.m_73434_(p_71281_, blockstate5, 5, -3, 7, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 5, -3, 6, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 5, -3, 5, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 5, -3, 4, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 5, -3, 3, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 5, -3, 2, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 5, -3, 1, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55497_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 4, -3, 1, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 3, -3, 1, p_71285_);
         if (!this.f_71269_) {
            this.f_71269_ = this.m_73500_(p_71281_, p_71285_, p_71284_, 3, -2, 1, Direction.NORTH, BuiltInLootTables.f_78687_);
         }

         this.m_73434_(p_71281_, Blocks.f_50191_.m_49966_().m_61124_(VineBlock.f_57836_, Boolean.valueOf(true)), 3, -2, 2, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.NORTH).m_61124_(TripWireHookBlock.f_57669_, Boolean.valueOf(true)), 7, -3, 1, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50266_.m_49966_().m_61124_(TripWireHookBlock.f_57667_, Direction.SOUTH).m_61124_(TripWireHookBlock.f_57669_, Boolean.valueOf(true)), 7, -3, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57593_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57595_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57591_, Boolean.valueOf(true)), 7, -3, 2, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57593_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57595_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57591_, Boolean.valueOf(true)), 7, -3, 3, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50267_.m_49966_().m_61124_(TripWireBlock.f_57593_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57595_, Boolean.valueOf(true)).m_61124_(TripWireBlock.f_57591_, Boolean.valueOf(true)), 7, -3, 4, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55497_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 8, -3, 6, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.SIDE), 9, -3, 6, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.UP), 9, -3, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 9, -3, 4, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 9, -2, 4, p_71285_);
         if (!this.f_71270_) {
            this.f_71270_ = this.m_73500_(p_71281_, p_71285_, p_71284_, 9, -2, 3, Direction.WEST, BuiltInLootTables.f_78687_);
         }

         this.m_73434_(p_71281_, Blocks.f_50191_.m_49966_().m_61124_(VineBlock.f_57835_, Boolean.valueOf(true)), 8, -1, 3, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50191_.m_49966_().m_61124_(VineBlock.f_57835_, Boolean.valueOf(true)), 8, -2, 3, p_71285_);
         if (!this.f_71267_) {
            this.f_71267_ = this.m_5606_(p_71281_, p_71285_, p_71284_, 8, -3, 3, BuiltInLootTables.f_78686_);
         }

         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 9, -3, 2, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 8, -3, 1, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 4, -3, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 5, -2, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 5, -1, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 6, -3, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 7, -2, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 7, -1, 5, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 8, -3, 5, p_71285_);
         this.m_73464_(p_71281_, p_71285_, 9, -1, 1, 9, -1, 5, false, p_71284_, f_71271_);
         this.m_73535_(p_71281_, p_71285_, 8, -3, 8, 10, -1, 10);
         this.m_73434_(p_71281_, Blocks.f_50225_.m_49966_(), 8, -2, 11, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50225_.m_49966_(), 9, -2, 11, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50225_.m_49966_(), 10, -2, 11, p_71285_);
         BlockState blockstate2 = Blocks.f_50164_.m_49966_().m_61124_(LeverBlock.f_54117_, Direction.NORTH).m_61124_(LeverBlock.f_53179_, AttachFace.WALL);
         this.m_73434_(p_71281_, blockstate2, 8, -2, 12, p_71285_);
         this.m_73434_(p_71281_, blockstate2, 9, -2, 12, p_71285_);
         this.m_73434_(p_71281_, blockstate2, 10, -2, 12, p_71285_);
         this.m_73464_(p_71281_, p_71285_, 8, -3, 8, 8, -3, 10, false, p_71284_, f_71271_);
         this.m_73464_(p_71281_, p_71285_, 10, -3, 8, 10, -3, 10, false, p_71284_, f_71271_);
         this.m_73434_(p_71281_, Blocks.f_50079_.m_49966_(), 10, -2, 9, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 8, -2, 9, p_71285_);
         this.m_73434_(p_71281_, blockstate5, 8, -2, 10, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50088_.m_49966_().m_61124_(RedStoneWireBlock.f_55496_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55498_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55497_, RedstoneSide.SIDE).m_61124_(RedStoneWireBlock.f_55499_, RedstoneSide.SIDE), 10, -1, 9, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50032_.m_49966_().m_61124_(PistonBaseBlock.f_52588_, Direction.UP), 9, -2, 8, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50032_.m_49966_().m_61124_(PistonBaseBlock.f_52588_, Direction.WEST), 10, -2, 8, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50032_.m_49966_().m_61124_(PistonBaseBlock.f_52588_, Direction.WEST), 10, -1, 8, p_71285_);
         this.m_73434_(p_71281_, Blocks.f_50146_.m_49966_().m_61124_(RepeaterBlock.f_54117_, Direction.NORTH), 10, -2, 10, p_71285_);
         if (!this.f_71268_) {
            this.f_71268_ = this.m_5606_(p_71281_, p_71285_, p_71284_, 9, -3, 10, BuiltInLootTables.f_78686_);
         }

         return true;
      }
   }

   static class MossStoneSelector extends StructurePiece.BlockSelector {
      public void m_7889_(Random p_71294_, int p_71295_, int p_71296_, int p_71297_, boolean p_71298_) {
         if (p_71294_.nextFloat() < 0.4F) {
            this.f_73553_ = Blocks.f_50652_.m_49966_();
         } else {
            this.f_73553_ = Blocks.f_50079_.m_49966_();
         }

      }
   }
}