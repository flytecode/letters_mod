package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EnderEyeItem extends Item {
   public EnderEyeItem(Item.Properties p_41180_) {
      super(p_41180_);
   }

   public InteractionResult m_6225_(UseOnContext p_41182_) {
      Level level = p_41182_.m_43725_();
      BlockPos blockpos = p_41182_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (blockstate.m_60713_(Blocks.f_50258_) && !blockstate.m_61143_(EndPortalFrameBlock.f_53043_)) {
         if (level.f_46443_) {
            return InteractionResult.SUCCESS;
         } else {
            BlockState blockstate1 = blockstate.m_61124_(EndPortalFrameBlock.f_53043_, Boolean.valueOf(true));
            Block.m_49897_(blockstate, blockstate1, level, blockpos);
            level.m_7731_(blockpos, blockstate1, 2);
            level.m_46717_(blockpos, Blocks.f_50258_);
            p_41182_.m_43722_().m_41774_(1);
            level.m_46796_(1503, blockpos, 0);
            BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = EndPortalFrameBlock.m_53077_().m_61184_(level, blockpos);
            if (blockpattern$blockpatternmatch != null) {
               BlockPos blockpos1 = blockpattern$blockpatternmatch.m_61228_().m_142082_(-3, 0, -3);

               for(int i = 0; i < 3; ++i) {
                  for(int j = 0; j < 3; ++j) {
                     level.m_7731_(blockpos1.m_142082_(i, 0, j), Blocks.f_50257_.m_49966_(), 2);
                  }
               }

               level.m_6798_(1038, blockpos1.m_142082_(1, 0, 1), 0);
            }

            return InteractionResult.CONSUME;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41184_, Player p_41185_, InteractionHand p_41186_) {
      ItemStack itemstack = p_41185_.m_21120_(p_41186_);
      HitResult hitresult = m_41435_(p_41184_, p_41185_, ClipContext.Fluid.NONE);
      if (hitresult.m_6662_() == HitResult.Type.BLOCK && p_41184_.m_8055_(((BlockHitResult)hitresult).m_82425_()).m_60713_(Blocks.f_50258_)) {
         return InteractionResultHolder.m_19098_(itemstack);
      } else {
         p_41185_.m_6672_(p_41186_);
         if (p_41184_ instanceof ServerLevel) {
            BlockPos blockpos = ((ServerLevel)p_41184_).m_7726_().m_8481_().m_62161_((ServerLevel)p_41184_, StructureFeature.f_67022_, p_41185_.m_142538_(), 100, false);
            if (blockpos != null) {
               EyeOfEnder eyeofender = new EyeOfEnder(p_41184_, p_41185_.m_20185_(), p_41185_.m_20227_(0.5D), p_41185_.m_20189_());
               eyeofender.m_36972_(itemstack);
               eyeofender.m_36967_(blockpos);
               p_41184_.m_7967_(eyeofender);
               if (p_41185_ instanceof ServerPlayer) {
                  CriteriaTriggers.f_10579_.m_73935_((ServerPlayer)p_41185_, blockpos);
               }

               p_41184_.m_6263_((Player)null, p_41185_.m_20185_(), p_41185_.m_20186_(), p_41185_.m_20189_(), SoundEvents.f_11898_, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_41184_.m_5822_().nextFloat() * 0.4F + 0.8F));
               p_41184_.m_5898_((Player)null, 1003, p_41185_.m_142538_(), 0);
               if (!p_41185_.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
               }

               p_41185_.m_36246_(Stats.f_12982_.m_12902_(this));
               p_41185_.m_21011_(p_41186_, true);
               return InteractionResultHolder.m_19090_(itemstack);
            }
         }

         return InteractionResultHolder.m_19096_(itemstack);
      }
   }
}