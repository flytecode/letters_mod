package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class DispenserBlock extends BaseEntityBlock {
   public static final DirectionProperty f_52659_ = DirectionalBlock.f_52588_;
   public static final BooleanProperty f_52660_ = BlockStateProperties.f_61360_;
   private static final Map<Item, DispenseItemBehavior> f_52661_ = Util.m_137469_(new Object2ObjectOpenHashMap<>(), (p_52723_) -> {
      p_52723_.defaultReturnValue(new DefaultDispenseItemBehavior());
   });
   private static final int f_153160_ = 4;

   public static void m_52672_(ItemLike p_52673_, DispenseItemBehavior p_52674_) {
      f_52661_.put(p_52673_.m_5456_(), p_52674_);
   }

   public DispenserBlock(BlockBehaviour.Properties p_52664_) {
      super(p_52664_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52659_, Direction.NORTH).m_61124_(f_52660_, Boolean.valueOf(false)));
   }

   public InteractionResult m_6227_(BlockState p_52693_, Level p_52694_, BlockPos p_52695_, Player p_52696_, InteractionHand p_52697_, BlockHitResult p_52698_) {
      if (p_52694_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         BlockEntity blockentity = p_52694_.m_7702_(p_52695_);
         if (blockentity instanceof DispenserBlockEntity) {
            p_52696_.m_5893_((DispenserBlockEntity)blockentity);
            if (blockentity instanceof DropperBlockEntity) {
               p_52696_.m_36220_(Stats.f_12956_);
            } else {
               p_52696_.m_36220_(Stats.f_12958_);
            }
         }

         return InteractionResult.CONSUME;
      }
   }

   protected void m_5824_(ServerLevel p_52665_, BlockPos p_52666_) {
      BlockSourceImpl blocksourceimpl = new BlockSourceImpl(p_52665_, p_52666_);
      DispenserBlockEntity dispenserblockentity = blocksourceimpl.m_8118_();
      int i = dispenserblockentity.m_59248_();
      if (i < 0) {
         p_52665_.m_46796_(1001, p_52666_, 0);
         p_52665_.m_151555_(GameEvent.f_157804_, p_52666_);
      } else {
         ItemStack itemstack = dispenserblockentity.m_8020_(i);
         DispenseItemBehavior dispenseitembehavior = this.m_7216_(itemstack);
         if (dispenseitembehavior != DispenseItemBehavior.f_123393_) {
            dispenserblockentity.m_6836_(i, dispenseitembehavior.m_6115_(blocksourceimpl, itemstack));
         }

      }
   }

   protected DispenseItemBehavior m_7216_(ItemStack p_52667_) {
      return f_52661_.get(p_52667_.m_41720_());
   }

   public void m_6861_(BlockState p_52700_, Level p_52701_, BlockPos p_52702_, Block p_52703_, BlockPos p_52704_, boolean p_52705_) {
      boolean flag = p_52701_.m_46753_(p_52702_) || p_52701_.m_46753_(p_52702_.m_7494_());
      boolean flag1 = p_52700_.m_61143_(f_52660_);
      if (flag && !flag1) {
         p_52701_.m_6219_().m_5945_(p_52702_, this, 4);
         p_52701_.m_7731_(p_52702_, p_52700_.m_61124_(f_52660_, Boolean.valueOf(true)), 4);
      } else if (!flag && flag1) {
         p_52701_.m_7731_(p_52702_, p_52700_.m_61124_(f_52660_, Boolean.valueOf(false)), 4);
      }

   }

   public void m_7458_(BlockState p_52684_, ServerLevel p_52685_, BlockPos p_52686_, Random p_52687_) {
      this.m_5824_(p_52685_, p_52686_);
   }

   public BlockEntity m_142194_(BlockPos p_153162_, BlockState p_153163_) {
      return new DispenserBlockEntity(p_153162_, p_153163_);
   }

   public BlockState m_5573_(BlockPlaceContext p_52669_) {
      return this.m_49966_().m_61124_(f_52659_, p_52669_.m_7820_().m_122424_());
   }

   public void m_6402_(Level p_52676_, BlockPos p_52677_, BlockState p_52678_, LivingEntity p_52679_, ItemStack p_52680_) {
      if (p_52680_.m_41788_()) {
         BlockEntity blockentity = p_52676_.m_7702_(p_52677_);
         if (blockentity instanceof DispenserBlockEntity) {
            ((DispenserBlockEntity)blockentity).m_58638_(p_52680_.m_41786_());
         }
      }

   }

   public void m_6810_(BlockState p_52707_, Level p_52708_, BlockPos p_52709_, BlockState p_52710_, boolean p_52711_) {
      if (!p_52707_.m_60713_(p_52710_.m_60734_())) {
         BlockEntity blockentity = p_52708_.m_7702_(p_52709_);
         if (blockentity instanceof DispenserBlockEntity) {
            Containers.m_19002_(p_52708_, p_52709_, (DispenserBlockEntity)blockentity);
            p_52708_.m_46717_(p_52709_, this);
         }

         super.m_6810_(p_52707_, p_52708_, p_52709_, p_52710_, p_52711_);
      }
   }

   public static Position m_52720_(BlockSource p_52721_) {
      Direction direction = p_52721_.m_6414_().m_61143_(f_52659_);
      double d0 = p_52721_.m_7096_() + 0.7D * (double)direction.m_122429_();
      double d1 = p_52721_.m_7098_() + 0.7D * (double)direction.m_122430_();
      double d2 = p_52721_.m_7094_() + 0.7D * (double)direction.m_122431_();
      return new PositionImpl(d0, d1, d2);
   }

   public boolean m_7278_(BlockState p_52682_) {
      return true;
   }

   public int m_6782_(BlockState p_52689_, Level p_52690_, BlockPos p_52691_) {
      return AbstractContainerMenu.m_38918_(p_52690_.m_7702_(p_52691_));
   }

   public RenderShape m_7514_(BlockState p_52725_) {
      return RenderShape.MODEL;
   }

   public BlockState m_6843_(BlockState p_52716_, Rotation p_52717_) {
      return p_52716_.m_61124_(f_52659_, p_52717_.m_55954_(p_52716_.m_61143_(f_52659_)));
   }

   public BlockState m_6943_(BlockState p_52713_, Mirror p_52714_) {
      return p_52713_.m_60717_(p_52714_.m_54846_(p_52713_.m_61143_(f_52659_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52719_) {
      p_52719_.m_61104_(f_52659_, f_52660_);
   }
}