package net.minecraft.world.level.block;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;

public class CarvedPumpkinBlock extends HorizontalDirectionalBlock implements Wearable {
   public static final DirectionProperty f_51367_ = HorizontalDirectionalBlock.f_54117_;
   @Nullable
   private BlockPattern f_51368_;
   @Nullable
   private BlockPattern f_51369_;
   @Nullable
   private BlockPattern f_51370_;
   @Nullable
   private BlockPattern f_51371_;
   private static final Predicate<BlockState> f_51372_ = (p_51396_) -> {
      return p_51396_ != null && (p_51396_.m_60713_(Blocks.f_50143_) || p_51396_.m_60713_(Blocks.f_50144_));
   };

   public CarvedPumpkinBlock(BlockBehaviour.Properties p_51375_) {
      super(p_51375_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51367_, Direction.NORTH));
   }

   public void m_6807_(BlockState p_51387_, Level p_51388_, BlockPos p_51389_, BlockState p_51390_, boolean p_51391_) {
      if (!p_51390_.m_60713_(p_51387_.m_60734_())) {
         this.m_51378_(p_51388_, p_51389_);
      }
   }

   public boolean m_51381_(LevelReader p_51382_, BlockPos p_51383_) {
      return this.m_51392_().m_61184_(p_51382_, p_51383_) != null || this.m_51394_().m_61184_(p_51382_, p_51383_) != null;
   }

   private void m_51378_(Level p_51379_, BlockPos p_51380_) {
      BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.m_51393_().m_61184_(p_51379_, p_51380_);
      if (blockpattern$blockpatternmatch != null) {
         for(int i = 0; i < this.m_51393_().m_61202_(); ++i) {
            BlockInWorld blockinworld = blockpattern$blockpatternmatch.m_61229_(0, i, 0);
            p_51379_.m_7731_(blockinworld.m_61176_(), Blocks.f_50016_.m_49966_(), 2);
            p_51379_.m_46796_(2001, blockinworld.m_61176_(), Block.m_49956_(blockinworld.m_61168_()));
         }

         SnowGolem snowgolem = EntityType.f_20528_.m_20615_(p_51379_);
         BlockPos blockpos1 = blockpattern$blockpatternmatch.m_61229_(0, 2, 0).m_61176_();
         snowgolem.m_7678_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.05D, (double)blockpos1.m_123343_() + 0.5D, 0.0F, 0.0F);
         p_51379_.m_7967_(snowgolem);

         for(ServerPlayer serverplayer : p_51379_.m_45976_(ServerPlayer.class, snowgolem.m_142469_().m_82400_(5.0D))) {
            CriteriaTriggers.f_10580_.m_68256_(serverplayer, snowgolem);
         }

         for(int l = 0; l < this.m_51393_().m_61202_(); ++l) {
            BlockInWorld blockinworld3 = blockpattern$blockpatternmatch.m_61229_(0, l, 0);
            p_51379_.m_6289_(blockinworld3.m_61176_(), Blocks.f_50016_);
         }
      } else {
         blockpattern$blockpatternmatch = this.m_51397_().m_61184_(p_51379_, p_51380_);
         if (blockpattern$blockpatternmatch != null) {
            for(int j = 0; j < this.m_51397_().m_61203_(); ++j) {
               for(int k = 0; k < this.m_51397_().m_61202_(); ++k) {
                  BlockInWorld blockinworld2 = blockpattern$blockpatternmatch.m_61229_(j, k, 0);
                  p_51379_.m_7731_(blockinworld2.m_61176_(), Blocks.f_50016_.m_49966_(), 2);
                  p_51379_.m_46796_(2001, blockinworld2.m_61176_(), Block.m_49956_(blockinworld2.m_61168_()));
               }
            }

            BlockPos blockpos = blockpattern$blockpatternmatch.m_61229_(1, 2, 0).m_61176_();
            IronGolem irongolem = EntityType.f_20460_.m_20615_(p_51379_);
            irongolem.m_28887_(true);
            irongolem.m_7678_((double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_() + 0.05D, (double)blockpos.m_123343_() + 0.5D, 0.0F, 0.0F);
            p_51379_.m_7967_(irongolem);

            for(ServerPlayer serverplayer1 : p_51379_.m_45976_(ServerPlayer.class, irongolem.m_142469_().m_82400_(5.0D))) {
               CriteriaTriggers.f_10580_.m_68256_(serverplayer1, irongolem);
            }

            for(int i1 = 0; i1 < this.m_51397_().m_61203_(); ++i1) {
               for(int j1 = 0; j1 < this.m_51397_().m_61202_(); ++j1) {
                  BlockInWorld blockinworld1 = blockpattern$blockpatternmatch.m_61229_(i1, j1, 0);
                  p_51379_.m_6289_(blockinworld1.m_61176_(), Blocks.f_50016_);
               }
            }
         }
      }

   }

   public BlockState m_5573_(BlockPlaceContext p_51377_) {
      return this.m_49966_().m_61124_(f_51367_, p_51377_.m_8125_().m_122424_());
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51385_) {
      p_51385_.m_61104_(f_51367_);
   }

   private BlockPattern m_51392_() {
      if (this.f_51368_ == null) {
         this.f_51368_ = BlockPatternBuilder.m_61243_().m_61247_(" ", "#", "#").m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50127_))).m_61249_();
      }

      return this.f_51368_;
   }

   private BlockPattern m_51393_() {
      if (this.f_51369_ == null) {
         this.f_51369_ = BlockPatternBuilder.m_61243_().m_61247_("^", "#", "#").m_61244_('^', BlockInWorld.m_61169_(f_51372_)).m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50127_))).m_61249_();
      }

      return this.f_51369_;
   }

   private BlockPattern m_51394_() {
      if (this.f_51370_ == null) {
         this.f_51370_ = BlockPatternBuilder.m_61243_().m_61247_("~ ~", "###", "~#~").m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50075_))).m_61244_('~', BlockInWorld.m_61169_(BlockMaterialPredicate.m_61262_(Material.f_76296_))).m_61249_();
      }

      return this.f_51370_;
   }

   private BlockPattern m_51397_() {
      if (this.f_51371_ == null) {
         this.f_51371_ = BlockPatternBuilder.m_61243_().m_61247_("~^~", "###", "~#~").m_61244_('^', BlockInWorld.m_61169_(f_51372_)).m_61244_('#', BlockInWorld.m_61169_(BlockStatePredicate.m_61287_(Blocks.f_50075_))).m_61244_('~', BlockInWorld.m_61169_(BlockMaterialPredicate.m_61262_(Material.f_76296_))).m_61249_();
      }

      return this.f_51371_;
   }
}