package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SweetBerryBushBlock extends BushBlock implements BonemealableBlock {
   private static final float f_154738_ = 0.003F;
   public static final int f_154737_ = 3;
   public static final IntegerProperty f_57244_ = BlockStateProperties.f_61407_;
   private static final VoxelShape f_57245_ = Block.m_49796_(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
   private static final VoxelShape f_57246_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public SweetBerryBushBlock(BlockBehaviour.Properties p_57249_) {
      super(p_57249_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57244_, Integer.valueOf(0)));
   }

   public ItemStack m_7397_(BlockGetter p_57256_, BlockPos p_57257_, BlockState p_57258_) {
      return new ItemStack(Items.f_42780_);
   }

   public VoxelShape m_5940_(BlockState p_57291_, BlockGetter p_57292_, BlockPos p_57293_, CollisionContext p_57294_) {
      if (p_57291_.m_61143_(f_57244_) == 0) {
         return f_57245_;
      } else {
         return p_57291_.m_61143_(f_57244_) < 3 ? f_57246_ : super.m_5940_(p_57291_, p_57292_, p_57293_, p_57294_);
      }
   }

   public boolean m_6724_(BlockState p_57284_) {
      return p_57284_.m_61143_(f_57244_) < 3;
   }

   public void m_7455_(BlockState p_57286_, ServerLevel p_57287_, BlockPos p_57288_, Random p_57289_) {
      int i = p_57286_.m_61143_(f_57244_);
      if (i < 3 && p_57287_.m_45524_(p_57288_.m_7494_(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_57287_, p_57288_, p_57286_,p_57289_.nextInt(5) == 0)) {
         p_57287_.m_7731_(p_57288_, p_57286_.m_61124_(f_57244_, Integer.valueOf(i + 1)), 2);
         net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_57287_, p_57288_, p_57286_);
      }

   }

   public void m_7892_(BlockState p_57270_, Level p_57271_, BlockPos p_57272_, Entity p_57273_) {
      if (p_57273_ instanceof LivingEntity && p_57273_.m_6095_() != EntityType.f_20452_ && p_57273_.m_6095_() != EntityType.f_20550_) {
         p_57273_.m_7601_(p_57270_, new Vec3((double)0.8F, 0.75D, (double)0.8F));
         if (!p_57271_.f_46443_ && p_57270_.m_61143_(f_57244_) > 0 && (p_57273_.f_19790_ != p_57273_.m_20185_() || p_57273_.f_19792_ != p_57273_.m_20189_())) {
            double d0 = Math.abs(p_57273_.m_20185_() - p_57273_.f_19790_);
            double d1 = Math.abs(p_57273_.m_20189_() - p_57273_.f_19792_);
            if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
               p_57273_.m_6469_(DamageSource.f_19325_, 1.0F);
            }
         }

      }
   }

   public InteractionResult m_6227_(BlockState p_57275_, Level p_57276_, BlockPos p_57277_, Player p_57278_, InteractionHand p_57279_, BlockHitResult p_57280_) {
      int i = p_57275_.m_61143_(f_57244_);
      boolean flag = i == 3;
      if (!flag && p_57278_.m_21120_(p_57279_).m_150930_(Items.f_42499_)) {
         return InteractionResult.PASS;
      } else if (i > 1) {
         int j = 1 + p_57276_.f_46441_.nextInt(2);
         m_49840_(p_57276_, p_57277_, new ItemStack(Items.f_42780_, j + (flag ? 1 : 0)));
         p_57276_.m_5594_((Player)null, p_57277_, SoundEvents.f_12457_, SoundSource.BLOCKS, 1.0F, 0.8F + p_57276_.f_46441_.nextFloat() * 0.4F);
         p_57276_.m_7731_(p_57277_, p_57275_.m_61124_(f_57244_, Integer.valueOf(1)), 2);
         return InteractionResult.m_19078_(p_57276_.f_46443_);
      } else {
         return super.m_6227_(p_57275_, p_57276_, p_57277_, p_57278_, p_57279_, p_57280_);
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57282_) {
      p_57282_.m_61104_(f_57244_);
   }

   public boolean m_7370_(BlockGetter p_57260_, BlockPos p_57261_, BlockState p_57262_, boolean p_57263_) {
      return p_57262_.m_61143_(f_57244_) < 3;
   }

   public boolean m_5491_(Level p_57265_, Random p_57266_, BlockPos p_57267_, BlockState p_57268_) {
      return true;
   }

   public void m_7719_(ServerLevel p_57251_, Random p_57252_, BlockPos p_57253_, BlockState p_57254_) {
      int i = Math.min(3, p_57254_.m_61143_(f_57244_) + 1);
      p_57251_.m_7731_(p_57253_, p_57254_.m_61124_(f_57244_, Integer.valueOf(i)), 2);
   }
}
