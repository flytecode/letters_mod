package net.minecraft.world.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HoeItem extends DiggerItem {
   protected static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> f_41332_ = Maps.newHashMap(ImmutableMap.of(Blocks.f_50440_, Pair.of(HoeItem::m_150856_, m_150858_(Blocks.f_50093_.m_49966_())), Blocks.f_152481_, Pair.of(HoeItem::m_150856_, m_150858_(Blocks.f_50093_.m_49966_())), Blocks.f_50493_, Pair.of(HoeItem::m_150856_, m_150858_(Blocks.f_50093_.m_49966_())), Blocks.f_50546_, Pair.of(HoeItem::m_150856_, m_150858_(Blocks.f_50493_.m_49966_())), Blocks.f_152549_, Pair.of((p_150861_) -> {
      return true;
   }, m_150849_(Blocks.f_50493_.m_49966_(), Items.f_151017_))));

   public HoeItem(Tier p_41336_, int p_41337_, float p_41338_, Item.Properties p_41339_) {
      super((float)p_41337_, p_41338_, p_41336_, BlockTags.f_144281_, p_41339_);
   }

   public InteractionResult m_6225_(UseOnContext p_41341_) {
      Level level = p_41341_.m_43725_();
      BlockPos blockpos = p_41341_.m_8083_();
      Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = f_41332_.get(level.m_8055_(blockpos).m_60734_());
      int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(p_41341_);
      if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
      if (p_41341_.m_43719_() != Direction.DOWN && level.m_46859_(blockpos.m_7494_())) {
      if (pair == null) {
         return InteractionResult.PASS;
      } else {
         Predicate<UseOnContext> predicate = pair.getFirst();
         Consumer<UseOnContext> consumer = pair.getSecond();
         if (predicate.test(p_41341_)) {
            Player player = p_41341_.m_43723_();
            level.m_5594_(player, blockpos, SoundEvents.f_11955_, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.f_46443_) {
               consumer.accept(p_41341_);
               if (player != null) {
                  p_41341_.m_43722_().m_41622_(1, player, (p_150845_) -> {
                     p_150845_.m_21190_(p_41341_.m_43724_());
                  });
               }
            }

            return InteractionResult.m_19078_(level.f_46443_);
         } else {
            return InteractionResult.PASS;
         }
      }
      }

      return InteractionResult.PASS;
   }

   public static Consumer<UseOnContext> m_150858_(BlockState p_150859_) {
      return (p_150848_) -> {
         p_150848_.m_43725_().m_7731_(p_150848_.m_8083_(), p_150859_, 11);
      };
   }

   public static Consumer<UseOnContext> m_150849_(BlockState p_150850_, ItemLike p_150851_) {
      return (p_150855_) -> {
         p_150855_.m_43725_().m_7731_(p_150855_.m_8083_(), p_150850_, 11);
         Block.m_152435_(p_150855_.m_43725_(), p_150855_.m_8083_(), p_150855_.m_43719_(), new ItemStack(p_150851_));
      };
   }

   public static boolean m_150856_(UseOnContext p_150857_) {
      return p_150857_.m_43719_() != Direction.DOWN && p_150857_.m_43725_().m_8055_(p_150857_.m_8083_().m_7494_()).m_60795_();
   }

   @Override
   public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
      return net.minecraftforge.common.ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction);
   }
}
