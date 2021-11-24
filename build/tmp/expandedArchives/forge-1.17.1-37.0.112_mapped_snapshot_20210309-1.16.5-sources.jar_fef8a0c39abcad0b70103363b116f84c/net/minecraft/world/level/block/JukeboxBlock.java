package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class JukeboxBlock extends BaseEntityBlock {
   public static final BooleanProperty f_54254_ = BlockStateProperties.f_61439_;

   public JukeboxBlock(BlockBehaviour.Properties p_54257_) {
      super(p_54257_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54254_, Boolean.valueOf(false)));
   }

   public void m_6402_(Level p_54264_, BlockPos p_54265_, BlockState p_54266_, @Nullable LivingEntity p_54267_, ItemStack p_54268_) {
      super.m_6402_(p_54264_, p_54265_, p_54266_, p_54267_, p_54268_);
      CompoundTag compoundtag = p_54268_.m_41784_();
      if (compoundtag.m_128441_("BlockEntityTag")) {
         CompoundTag compoundtag1 = compoundtag.m_128469_("BlockEntityTag");
         if (compoundtag1.m_128441_("RecordItem")) {
            p_54264_.m_7731_(p_54265_, p_54266_.m_61124_(f_54254_, Boolean.valueOf(true)), 2);
         }
      }

   }

   public InteractionResult m_6227_(BlockState p_54281_, Level p_54282_, BlockPos p_54283_, Player p_54284_, InteractionHand p_54285_, BlockHitResult p_54286_) {
      if (p_54281_.m_61143_(f_54254_)) {
         this.m_54260_(p_54282_, p_54283_);
         p_54281_ = p_54281_.m_61124_(f_54254_, Boolean.valueOf(false));
         p_54282_.m_7731_(p_54283_, p_54281_, 2);
         return InteractionResult.m_19078_(p_54282_.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   public void m_54269_(LevelAccessor p_54270_, BlockPos p_54271_, BlockState p_54272_, ItemStack p_54273_) {
      BlockEntity blockentity = p_54270_.m_7702_(p_54271_);
      if (blockentity instanceof JukeboxBlockEntity) {
         ((JukeboxBlockEntity)blockentity).m_59517_(p_54273_.m_41777_());
         p_54270_.m_7731_(p_54271_, p_54272_.m_61124_(f_54254_, Boolean.valueOf(true)), 2);
      }
   }

   private void m_54260_(Level p_54261_, BlockPos p_54262_) {
      if (!p_54261_.f_46443_) {
         BlockEntity blockentity = p_54261_.m_7702_(p_54262_);
         if (blockentity instanceof JukeboxBlockEntity) {
            JukeboxBlockEntity jukeboxblockentity = (JukeboxBlockEntity)blockentity;
            ItemStack itemstack = jukeboxblockentity.m_59524_();
            if (!itemstack.m_41619_()) {
               p_54261_.m_46796_(1010, p_54262_, 0);
               jukeboxblockentity.m_6211_();
               float f = 0.7F;
               double d0 = (double)(p_54261_.f_46441_.nextFloat() * 0.7F) + (double)0.15F;
               double d1 = (double)(p_54261_.f_46441_.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
               double d2 = (double)(p_54261_.f_46441_.nextFloat() * 0.7F) + (double)0.15F;
               ItemStack itemstack1 = itemstack.m_41777_();
               ItemEntity itementity = new ItemEntity(p_54261_, (double)p_54262_.m_123341_() + d0, (double)p_54262_.m_123342_() + d1, (double)p_54262_.m_123343_() + d2, itemstack1);
               itementity.m_32060_();
               p_54261_.m_7967_(itementity);
            }
         }
      }
   }

   public void m_6810_(BlockState p_54288_, Level p_54289_, BlockPos p_54290_, BlockState p_54291_, boolean p_54292_) {
      if (!p_54288_.m_60713_(p_54291_.m_60734_())) {
         this.m_54260_(p_54289_, p_54290_);
         super.m_6810_(p_54288_, p_54289_, p_54290_, p_54291_, p_54292_);
      }
   }

   public BlockEntity m_142194_(BlockPos p_153451_, BlockState p_153452_) {
      return new JukeboxBlockEntity(p_153451_, p_153452_);
   }

   public boolean m_7278_(BlockState p_54275_) {
      return true;
   }

   public int m_6782_(BlockState p_54277_, Level p_54278_, BlockPos p_54279_) {
      BlockEntity blockentity = p_54278_.m_7702_(p_54279_);
      if (blockentity instanceof JukeboxBlockEntity) {
         Item item = ((JukeboxBlockEntity)blockentity).m_59524_().m_41720_();
         if (item instanceof RecordItem) {
            return ((RecordItem)item).m_43049_();
         }
      }

      return 0;
   }

   public RenderShape m_7514_(BlockState p_54296_) {
      return RenderShape.MODEL;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54294_) {
      p_54294_.m_61104_(f_54254_);
   }
}