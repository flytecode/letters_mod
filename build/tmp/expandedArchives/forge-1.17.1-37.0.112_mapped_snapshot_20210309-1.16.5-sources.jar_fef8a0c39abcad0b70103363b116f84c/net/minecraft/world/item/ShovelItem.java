package net.minecraft.world.item;

import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ShovelItem extends DiggerItem {
   protected static final Map<Block, BlockState> f_43110_ = Maps.newHashMap((new Builder()).put(Blocks.f_50440_, Blocks.f_152481_.m_49966_()).put(Blocks.f_50493_, Blocks.f_152481_.m_49966_()).put(Blocks.f_50599_, Blocks.f_152481_.m_49966_()).put(Blocks.f_50546_, Blocks.f_152481_.m_49966_()).put(Blocks.f_50195_, Blocks.f_152481_.m_49966_()).put(Blocks.f_152549_, Blocks.f_152481_.m_49966_()).build());

   public ShovelItem(Tier p_43114_, float p_43115_, float p_43116_, Item.Properties p_43117_) {
      super(p_43115_, p_43116_, p_43114_, BlockTags.f_144283_, p_43117_);
   }

   public InteractionResult m_6225_(UseOnContext p_43119_) {
      Level level = p_43119_.m_43725_();
      BlockPos blockpos = p_43119_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (p_43119_.m_43719_() == Direction.DOWN) {
         return InteractionResult.PASS;
      } else {
         Player player = p_43119_.m_43723_();
         BlockState blockstate1 = blockstate.getToolModifiedState(level, blockpos, player, p_43119_.m_43722_(), net.minecraftforge.common.ToolActions.SHOVEL_FLATTEN);
         BlockState blockstate2 = null;
         if (blockstate1 != null && level.m_46859_(blockpos.m_7494_())) {
            level.m_5594_(player, blockpos, SoundEvents.f_12406_, SoundSource.BLOCKS, 1.0F, 1.0F);
            blockstate2 = blockstate1;
         } else if (blockstate.m_60734_() instanceof CampfireBlock && blockstate.m_61143_(CampfireBlock.f_51227_)) {
            if (!level.m_5776_()) {
               level.m_5898_((Player)null, 1009, blockpos, 0);
            }

            CampfireBlock.m_152749_(p_43119_.m_43723_(), level, blockpos, blockstate);
            blockstate2 = blockstate.m_61124_(CampfireBlock.f_51227_, Boolean.valueOf(false));
         }

         if (blockstate2 != null) {
            if (!level.f_46443_) {
               level.m_7731_(blockpos, blockstate2, 11);
               if (player != null) {
                  p_43119_.m_43722_().m_41622_(1, player, (p_43122_) -> {
                     p_43122_.m_21190_(p_43119_.m_43724_());
                  });
               }
            }

            return InteractionResult.m_19078_(level.f_46443_);
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   @javax.annotation.Nullable
   public static BlockState getShovelPathingState(BlockState originalState) {
      return f_43110_.get(originalState.m_60734_());
   }

   @Override
   public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
      return net.minecraftforge.common.ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolAction);
   }
}
