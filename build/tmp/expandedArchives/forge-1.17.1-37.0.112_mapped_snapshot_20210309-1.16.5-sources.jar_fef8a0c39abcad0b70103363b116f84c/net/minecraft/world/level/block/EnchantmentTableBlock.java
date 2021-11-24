package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnchantmentTableBlock extends BaseEntityBlock {
   protected static final VoxelShape f_52950_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

   public EnchantmentTableBlock(BlockBehaviour.Properties p_52953_) {
      super(p_52953_);
   }

   public boolean m_7923_(BlockState p_52997_) {
      return true;
   }

   public VoxelShape m_5940_(BlockState p_52988_, BlockGetter p_52989_, BlockPos p_52990_, CollisionContext p_52991_) {
      return f_52950_;
   }

   public void m_7100_(BlockState p_52981_, Level p_52982_, BlockPos p_52983_, Random p_52984_) {
      super.m_7100_(p_52981_, p_52982_, p_52983_, p_52984_);

      for(int i = -2; i <= 2; ++i) {
         for(int j = -2; j <= 2; ++j) {
            if (i > -2 && i < 2 && j == -1) {
               j = 2;
            }

            if (p_52984_.nextInt(16) == 0) {
               for(int k = 0; k <= 1; ++k) {
                  BlockPos blockpos = p_52983_.m_142082_(i, k, j);
                  if (p_52982_.m_8055_(blockpos).getEnchantPowerBonus(p_52982_, blockpos) > 0) {
                     if (!p_52982_.m_46859_(p_52983_.m_142082_(i / 2, 0, j / 2))) {
                        break;
                     }

                     p_52982_.m_7106_(ParticleTypes.f_123809_, (double)p_52983_.m_123341_() + 0.5D, (double)p_52983_.m_123342_() + 2.0D, (double)p_52983_.m_123343_() + 0.5D, (double)((float)i + p_52984_.nextFloat()) - 0.5D, (double)((float)k - p_52984_.nextFloat() - 1.0F), (double)((float)j + p_52984_.nextFloat()) - 0.5D);
                  }
               }
            }
         }
      }

   }

   public RenderShape m_7514_(BlockState p_52986_) {
      return RenderShape.MODEL;
   }

   public BlockEntity m_142194_(BlockPos p_153186_, BlockState p_153187_) {
      return new EnchantmentTableBlockEntity(p_153186_, p_153187_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153182_, BlockState p_153183_, BlockEntityType<T> p_153184_) {
      return p_153182_.f_46443_ ? m_152132_(p_153184_, BlockEntityType.f_58928_, EnchantmentTableBlockEntity::m_155503_) : null;
   }

   public InteractionResult m_6227_(BlockState p_52974_, Level p_52975_, BlockPos p_52976_, Player p_52977_, InteractionHand p_52978_, BlockHitResult p_52979_) {
      if (p_52975_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         p_52977_.m_5893_(p_52974_.m_60750_(p_52975_, p_52976_));
         return InteractionResult.CONSUME;
      }
   }

   @Nullable
   public MenuProvider m_7246_(BlockState p_52993_, Level p_52994_, BlockPos p_52995_) {
      BlockEntity blockentity = p_52994_.m_7702_(p_52995_);
      if (blockentity instanceof EnchantmentTableBlockEntity) {
         Component component = ((Nameable)blockentity).m_5446_();
         return new SimpleMenuProvider((p_52959_, p_52960_, p_52961_) -> {
            return new EnchantmentMenu(p_52959_, p_52960_, ContainerLevelAccess.m_39289_(p_52994_, p_52995_));
         }, component);
      } else {
         return null;
      }
   }

   public void m_6402_(Level p_52963_, BlockPos p_52964_, BlockState p_52965_, LivingEntity p_52966_, ItemStack p_52967_) {
      if (p_52967_.m_41788_()) {
         BlockEntity blockentity = p_52963_.m_7702_(p_52964_);
         if (blockentity instanceof EnchantmentTableBlockEntity) {
            ((EnchantmentTableBlockEntity)blockentity).m_59272_(p_52967_.m_41786_());
         }
      }

   }

   public boolean m_7357_(BlockState p_52969_, BlockGetter p_52970_, BlockPos p_52971_, PathComputationType p_52972_) {
      return false;
   }
}
