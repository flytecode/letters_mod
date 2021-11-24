package net.minecraft.world.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;

public class BlockItem extends Item {
   public static final String f_150696_ = "BlockEntityTag";
   public static final String f_150697_ = "BlockStateTag";
   @Deprecated
   private final Block f_40563_;

   public BlockItem(Block p_40565_, Item.Properties p_40566_) {
      super(p_40566_);
      this.f_40563_ = p_40565_;
   }

   public InteractionResult m_6225_(UseOnContext p_40581_) {
      InteractionResult interactionresult = this.m_40576_(new BlockPlaceContext(p_40581_));
      if (!interactionresult.m_19077_() && this.m_41472_()) {
         InteractionResult interactionresult1 = this.m_7203_(p_40581_.m_43725_(), p_40581_.m_43723_(), p_40581_.m_43724_()).m_19089_();
         return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
      } else {
         return interactionresult;
      }
   }

   public InteractionResult m_40576_(BlockPlaceContext p_40577_) {
      if (!p_40577_.m_7059_()) {
         return InteractionResult.FAIL;
      } else {
         BlockPlaceContext blockplacecontext = this.m_7732_(p_40577_);
         if (blockplacecontext == null) {
            return InteractionResult.FAIL;
         } else {
            BlockState blockstate = this.m_5965_(blockplacecontext);
            if (blockstate == null) {
               return InteractionResult.FAIL;
            } else if (!this.m_7429_(blockplacecontext, blockstate)) {
               return InteractionResult.FAIL;
            } else {
               BlockPos blockpos = blockplacecontext.m_8083_();
               Level level = blockplacecontext.m_43725_();
               Player player = blockplacecontext.m_43723_();
               ItemStack itemstack = blockplacecontext.m_43722_();
               BlockState blockstate1 = level.m_8055_(blockpos);
               if (blockstate1.m_60713_(blockstate.m_60734_())) {
                  blockstate1 = this.m_40602_(blockpos, level, itemstack, blockstate1);
                  this.m_7274_(blockpos, level, player, itemstack, blockstate1);
                  blockstate1.m_60734_().m_6402_(level, blockpos, blockstate1, player, itemstack);
                  if (player instanceof ServerPlayer) {
                     CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)player, blockpos, itemstack);
                  }
               }

               level.m_142346_(player, GameEvent.f_157797_, blockpos);
               SoundType soundtype = blockstate1.getSoundType(level, blockpos, p_40577_.m_43723_());
               level.m_5594_(player, blockpos, this.getPlaceSound(blockstate1, level, blockpos, p_40577_.m_43723_()), SoundSource.BLOCKS, (soundtype.m_56773_() + 1.0F) / 2.0F, soundtype.m_56774_() * 0.8F);
               if (player == null || !player.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
               }

               return InteractionResult.m_19078_(level.f_46443_);
            }
         }
      }
   }

   @Deprecated //Forge: Use more sensitive version {@link BlockItem#getPlaceSound(BlockState, IBlockReader, BlockPos, Entity) }
   protected SoundEvent m_40587_(BlockState p_40588_) {
      return p_40588_.m_60827_().m_56777_();
   }

   //Forge: Sensitive version of BlockItem#getPlaceSound
   protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity) {
      return state.getSoundType(world, pos, entity).m_56777_();
   }

   @Nullable
   public BlockPlaceContext m_7732_(BlockPlaceContext p_40609_) {
      return p_40609_;
   }

   protected boolean m_7274_(BlockPos p_40597_, Level p_40598_, @Nullable Player p_40599_, ItemStack p_40600_, BlockState p_40601_) {
      return m_40582_(p_40598_, p_40599_, p_40597_, p_40600_);
   }

   @Nullable
   protected BlockState m_5965_(BlockPlaceContext p_40613_) {
      BlockState blockstate = this.m_40614_().m_5573_(p_40613_);
      return blockstate != null && this.m_40610_(p_40613_, blockstate) ? blockstate : null;
   }

   private BlockState m_40602_(BlockPos p_40603_, Level p_40604_, ItemStack p_40605_, BlockState p_40606_) {
      BlockState blockstate = p_40606_;
      CompoundTag compoundtag = p_40605_.m_41783_();
      if (compoundtag != null) {
         CompoundTag compoundtag1 = compoundtag.m_128469_("BlockStateTag");
         StateDefinition<Block, BlockState> statedefinition = p_40606_.m_60734_().m_49965_();

         for(String s : compoundtag1.m_128431_()) {
            Property<?> property = statedefinition.m_61081_(s);
            if (property != null) {
               String s1 = compoundtag1.m_128423_(s).m_7916_();
               blockstate = m_40593_(blockstate, property, s1);
            }
         }
      }

      if (blockstate != p_40606_) {
         p_40604_.m_7731_(p_40603_, blockstate, 2);
      }

      return blockstate;
   }

   private static <T extends Comparable<T>> BlockState m_40593_(BlockState p_40594_, Property<T> p_40595_, String p_40596_) {
      return p_40595_.m_6215_(p_40596_).map((p_40592_) -> {
         return p_40594_.m_61124_(p_40595_, p_40592_);
      }).orElse(p_40594_);
   }

   protected boolean m_40610_(BlockPlaceContext p_40611_, BlockState p_40612_) {
      Player player = p_40611_.m_43723_();
      CollisionContext collisioncontext = player == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_(player);
      return (!this.m_6652_() || p_40612_.m_60710_(p_40611_.m_43725_(), p_40611_.m_8083_())) && p_40611_.m_43725_().m_45752_(p_40612_, p_40611_.m_8083_(), collisioncontext);
   }

   protected boolean m_6652_() {
      return true;
   }

   protected boolean m_7429_(BlockPlaceContext p_40578_, BlockState p_40579_) {
      return p_40578_.m_43725_().m_7731_(p_40578_.m_8083_(), p_40579_, 11);
   }

   public static boolean m_40582_(Level p_40583_, @Nullable Player p_40584_, BlockPos p_40585_, ItemStack p_40586_) {
      MinecraftServer minecraftserver = p_40583_.m_142572_();
      if (minecraftserver == null) {
         return false;
      } else {
         CompoundTag compoundtag = p_40586_.m_41737_("BlockEntityTag");
         if (compoundtag != null) {
            BlockEntity blockentity = p_40583_.m_7702_(p_40585_);
            if (blockentity != null) {
               if (!p_40583_.f_46443_ && blockentity.m_6326_() && (p_40584_ == null || !p_40584_.m_36337_())) {
                  return false;
               }

               CompoundTag compoundtag1 = blockentity.m_6945_(new CompoundTag());
               CompoundTag compoundtag2 = compoundtag1.m_6426_();
               compoundtag1.m_128391_(compoundtag);
               compoundtag1.m_128405_("x", p_40585_.m_123341_());
               compoundtag1.m_128405_("y", p_40585_.m_123342_());
               compoundtag1.m_128405_("z", p_40585_.m_123343_());
               if (!compoundtag1.equals(compoundtag2)) {
                  blockentity.m_142466_(compoundtag1);
                  blockentity.m_6596_();
                  return true;
               }
            }
         }

         return false;
      }
   }

   public String m_5524_() {
      return this.m_40614_().m_7705_();
   }

   public void m_6787_(CreativeModeTab p_40569_, NonNullList<ItemStack> p_40570_) {
      if (this.m_41389_(p_40569_)) {
         this.m_40614_().m_49811_(p_40569_, p_40570_);
      }

   }

   public void m_7373_(ItemStack p_40572_, @Nullable Level p_40573_, List<Component> p_40574_, TooltipFlag p_40575_) {
      super.m_7373_(p_40572_, p_40573_, p_40574_, p_40575_);
      this.m_40614_().m_5871_(p_40572_, p_40573_, p_40574_, p_40575_);
   }

   public Block m_40614_() {
      return this.getBlockRaw() == null ? null : this.getBlockRaw().delegate.get();
   }

   private Block getBlockRaw() {
      return this.f_40563_;
   }

   public void m_6192_(Map<Block, Item> p_40607_, Item p_40608_) {
      p_40607_.put(this.m_40614_(), p_40608_);
   }

   public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
      blockToItemMap.remove(this.m_40614_());
   }

   public boolean m_142095_() {
      return !(this.f_40563_ instanceof ShulkerBoxBlock);
   }

   public void m_142023_(ItemEntity p_150700_) {
      if (this.f_40563_ instanceof ShulkerBoxBlock) {
         CompoundTag compoundtag = p_150700_.m_32055_().m_41783_();
         if (compoundtag != null) {
            ListTag listtag = compoundtag.m_128469_("BlockEntityTag").m_128437_("Items", 10);
            ItemUtils.m_150952_(p_150700_, listtag.stream().map(CompoundTag.class::cast).map(ItemStack::m_41712_));
         }
      }

   }
}
