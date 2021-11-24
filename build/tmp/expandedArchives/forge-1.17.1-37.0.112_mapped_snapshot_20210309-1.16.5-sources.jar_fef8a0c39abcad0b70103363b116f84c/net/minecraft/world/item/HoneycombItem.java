package net.minecraft.world.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HoneycombItem extends Item {
   public static final Supplier<BiMap<Block, Block>> f_150863_ = Suppliers.memoize(() -> {
      return ImmutableBiMap.<Block, Block>builder().put(Blocks.f_152504_, Blocks.f_152571_).put(Blocks.f_152503_, Blocks.f_152573_).put(Blocks.f_152502_, Blocks.f_152572_).put(Blocks.f_152501_, Blocks.f_152574_).put(Blocks.f_152510_, Blocks.f_152578_).put(Blocks.f_152509_, Blocks.f_152577_).put(Blocks.f_152508_, Blocks.f_152576_).put(Blocks.f_152507_, Blocks.f_152575_).put(Blocks.f_152570_, Blocks.f_152586_).put(Blocks.f_152569_, Blocks.f_152585_).put(Blocks.f_152568_, Blocks.f_152584_).put(Blocks.f_152567_, Blocks.f_152583_).put(Blocks.f_152566_, Blocks.f_152582_).put(Blocks.f_152565_, Blocks.f_152581_).put(Blocks.f_152564_, Blocks.f_152580_).put(Blocks.f_152563_, Blocks.f_152579_).build();
   });
   public static final Supplier<BiMap<Block, Block>> f_150864_ = Suppliers.memoize(() -> {
      return f_150863_.get().inverse();
   });

   public HoneycombItem(Item.Properties p_150867_) {
      super(p_150867_);
   }

   public InteractionResult m_6225_(UseOnContext p_150869_) {
      Level level = p_150869_.m_43725_();
      BlockPos blockpos = p_150869_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      return m_150878_(blockstate).map((p_150874_) -> {
         Player player = p_150869_.m_43723_();
         ItemStack itemstack = p_150869_.m_43722_();
         if (player instanceof ServerPlayer) {
            CriteriaTriggers.f_10562_.m_45482_((ServerPlayer)player, blockpos, itemstack);
         }

         itemstack.m_41774_(1);
         level.m_7731_(blockpos, p_150874_, 11);
         level.m_5898_(player, 3003, blockpos, 0);
         return InteractionResult.m_19078_(level.f_46443_);
      }).orElse(InteractionResult.PASS);
   }

   public static Optional<BlockState> m_150878_(BlockState p_150879_) {
      return Optional.ofNullable(f_150863_.get().get(p_150879_.m_60734_())).map((p_150877_) -> {
         return p_150877_.m_152465_(p_150879_);
      });
   }
}