package net.minecraft.world.item;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class AxeItem extends DiggerItem {
   protected static final Map<Block, Block> f_150683_ = (new Builder<Block, Block>()).put(Blocks.f_50011_, Blocks.f_50044_).put(Blocks.f_49999_, Blocks.f_50010_).put(Blocks.f_50043_, Blocks.f_50049_).put(Blocks.f_50004_, Blocks.f_50009_).put(Blocks.f_50015_, Blocks.f_50048_).put(Blocks.f_50003_, Blocks.f_50008_).put(Blocks.f_50013_, Blocks.f_50046_).put(Blocks.f_50001_, Blocks.f_50006_).put(Blocks.f_50014_, Blocks.f_50047_).put(Blocks.f_50002_, Blocks.f_50007_).put(Blocks.f_50012_, Blocks.f_50045_).put(Blocks.f_50000_, Blocks.f_50005_).put(Blocks.f_50686_, Blocks.f_50687_).put(Blocks.f_50688_, Blocks.f_50689_).put(Blocks.f_50695_, Blocks.f_50696_).put(Blocks.f_50697_, Blocks.f_50698_).build();

   public AxeItem(Tier p_40521_, float p_40522_, float p_40523_, Item.Properties p_40524_) {
      super(p_40522_, p_40523_, p_40521_, BlockTags.f_144280_, p_40524_);
   }

   public InteractionResult m_6225_(UseOnContext p_40529_) {
      Level level = p_40529_.m_43725_();
      BlockPos blockpos = p_40529_.m_8083_();
      Player player = p_40529_.m_43723_();
      BlockState blockstate = level.m_8055_(blockpos);
      Optional<BlockState> optional = Optional.ofNullable(blockstate.getToolModifiedState(level, blockpos, player, p_40529_.m_43722_(), net.minecraftforge.common.ToolActions.AXE_STRIP));
      Optional<BlockState> optional1 = Optional.ofNullable(blockstate.getToolModifiedState(level, blockpos, player, p_40529_.m_43722_(), net.minecraftforge.common.ToolActions.AXE_SCRAPE));
      Optional<BlockState> optional2 = Optional.ofNullable(blockstate.getToolModifiedState(level, blockpos, player, p_40529_.m_43722_(), net.minecraftforge.common.ToolActions.AXE_WAX_OFF));
      ItemStack itemstack = p_40529_.m_43722_();
      Optional<BlockState> optional3 = Optional.empty();
      if (optional.isPresent()) {
         level.m_5594_(player, blockpos, SoundEvents.f_11688_, SoundSource.BLOCKS, 1.0F, 1.0F);
         optional3 = optional;
      } else if (optional1.isPresent()) {
         level.m_5594_(player, blockpos, SoundEvents.f_144059_, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.m_5898_(player, 3005, blockpos, 0);
         optional3 = optional1;
      } else if (optional2.isPresent()) {
         level.m_5594_(player, blockpos, SoundEvents.f_144060_, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.m_5898_(player, 3004, blockpos, 0);
         optional3 = optional2;
      }

      if (optional3.isPresent()) {
         if (player instanceof ServerPlayer) {
            CriteriaTriggers.f_10562_.m_45482_((ServerPlayer)player, blockpos, itemstack);
         }

         level.m_7731_(blockpos, optional3.get(), 11);
         if (player != null) {
            itemstack.m_41622_(1, player, (p_150686_) -> {
               p_150686_.m_21190_(p_40529_.m_43724_());
            });
         }

         return InteractionResult.m_19078_(level.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   @javax.annotation.Nullable
   public static BlockState getAxeStrippingState(BlockState originalState) {
      Block block = f_150683_.get(originalState.m_60734_());
      return block != null ? block.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, originalState.m_61143_(RotatedPillarBlock.f_55923_)) : null;
   }

   private Optional<BlockState> m_150690_(BlockState p_150691_) {
      return Optional.ofNullable(f_150683_.get(p_150691_.m_60734_())).map((p_150689_) -> {
         return p_150689_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, p_150691_.m_61143_(RotatedPillarBlock.f_55923_));
      });
   }

   @Override
   public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
      return net.minecraftforge.common.ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction);
   }
}
