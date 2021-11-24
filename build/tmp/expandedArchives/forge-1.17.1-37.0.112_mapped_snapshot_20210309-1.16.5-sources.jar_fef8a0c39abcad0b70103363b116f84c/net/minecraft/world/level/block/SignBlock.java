package net.minecraft.world.level.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class SignBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_56268_ = BlockStateProperties.f_61362_;
   protected static final float f_154554_ = 4.0F;
   protected static final VoxelShape f_56269_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
   private final WoodType f_56270_;

   protected SignBlock(BlockBehaviour.Properties p_56273_, WoodType p_56274_) {
      super(p_56273_);
      this.f_56270_ = p_56274_;
   }

   public BlockState m_7417_(BlockState p_56285_, Direction p_56286_, BlockState p_56287_, LevelAccessor p_56288_, BlockPos p_56289_, BlockPos p_56290_) {
      if (p_56285_.m_61143_(f_56268_)) {
         p_56288_.m_6217_().m_5945_(p_56289_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56288_));
      }

      return super.m_7417_(p_56285_, p_56286_, p_56287_, p_56288_, p_56289_, p_56290_);
   }

   public VoxelShape m_5940_(BlockState p_56293_, BlockGetter p_56294_, BlockPos p_56295_, CollisionContext p_56296_) {
      return f_56269_;
   }

   public boolean m_5568_() {
      return true;
   }

   public BlockEntity m_142194_(BlockPos p_154556_, BlockState p_154557_) {
      return new SignBlockEntity(p_154556_, p_154557_);
   }

   public InteractionResult m_6227_(BlockState p_56278_, Level p_56279_, BlockPos p_56280_, Player p_56281_, InteractionHand p_56282_, BlockHitResult p_56283_) {
      ItemStack itemstack = p_56281_.m_21120_(p_56282_);
      Item item = itemstack.m_41720_();
      boolean flag = item instanceof DyeItem;
      boolean flag1 = itemstack.m_150930_(Items.f_151056_);
      boolean flag2 = itemstack.m_150930_(Items.f_42532_);
      boolean flag3 = (flag1 || flag || flag2) && p_56281_.m_150110_().f_35938_;
      if (p_56279_.f_46443_) {
         return flag3 ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
      } else {
         BlockEntity blockentity = p_56279_.m_7702_(p_56280_);
         if (!(blockentity instanceof SignBlockEntity)) {
            return InteractionResult.PASS;
         } else {
            SignBlockEntity signblockentity = (SignBlockEntity)blockentity;
            boolean flag4 = signblockentity.m_155727_();
            if ((!flag1 || !flag4) && (!flag2 || flag4)) {
               if (flag3) {
                  boolean flag5;
                  if (flag1) {
                     p_56279_.m_5594_((Player)null, p_56280_, SoundEvents.f_144153_, SoundSource.BLOCKS, 1.0F, 1.0F);
                     flag5 = signblockentity.m_155722_(true);
                     if (p_56281_ instanceof ServerPlayer) {
                        CriteriaTriggers.f_10562_.m_45482_((ServerPlayer)p_56281_, p_56280_, itemstack);
                     }
                  } else if (flag2) {
                     p_56279_.m_5594_((Player)null, p_56280_, SoundEvents.f_144181_, SoundSource.BLOCKS, 1.0F, 1.0F);
                     flag5 = signblockentity.m_155722_(false);
                  } else {
                     p_56279_.m_5594_((Player)null, p_56280_, SoundEvents.f_144133_, SoundSource.BLOCKS, 1.0F, 1.0F);
                     flag5 = signblockentity.m_59739_(((DyeItem)item).m_41089_());
                  }

                  if (flag5) {
                     if (!p_56281_.m_7500_()) {
                        itemstack.m_41774_(1);
                     }

                     p_56281_.m_36246_(Stats.f_12982_.m_12902_(item));
                  }
               }

               return signblockentity.m_155709_((ServerPlayer)p_56281_) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            } else {
               return InteractionResult.PASS;
            }
         }
      }
   }

   public FluidState m_5888_(BlockState p_56299_) {
      return p_56299_.m_61143_(f_56268_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_56299_);
   }

   public WoodType m_56297_() {
      return this.f_56270_;
   }
}