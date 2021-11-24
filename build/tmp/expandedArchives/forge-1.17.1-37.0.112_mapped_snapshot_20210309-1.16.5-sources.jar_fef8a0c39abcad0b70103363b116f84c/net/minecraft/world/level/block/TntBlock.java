package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class TntBlock extends Block {
   public static final BooleanProperty f_57419_ = BlockStateProperties.f_61361_;

   public TntBlock(BlockBehaviour.Properties p_57422_) {
      super(p_57422_);
      this.m_49959_(this.m_49966_().m_61124_(f_57419_, Boolean.valueOf(false)));
   }

   public void catchFire(BlockState state, Level world, BlockPos pos, @Nullable net.minecraft.core.Direction face, @Nullable LivingEntity igniter) {
      m_57436_(world, pos, igniter);
   }

   public void m_6807_(BlockState p_57466_, Level p_57467_, BlockPos p_57468_, BlockState p_57469_, boolean p_57470_) {
      if (!p_57469_.m_60713_(p_57466_.m_60734_())) {
         if (p_57467_.m_46753_(p_57468_)) {
            catchFire(p_57466_, p_57467_, p_57468_, null, null);
            p_57467_.m_7471_(p_57468_, false);
         }

      }
   }

   public void m_6861_(BlockState p_57457_, Level p_57458_, BlockPos p_57459_, Block p_57460_, BlockPos p_57461_, boolean p_57462_) {
      if (p_57458_.m_46753_(p_57459_)) {
         catchFire(p_57457_, p_57458_, p_57459_, null, null);
         p_57458_.m_7471_(p_57459_, false);
      }

   }

   public void m_5707_(Level p_57445_, BlockPos p_57446_, BlockState p_57447_, Player p_57448_) {
      if (!p_57445_.m_5776_() && !p_57448_.m_7500_() && p_57447_.m_61143_(f_57419_)) {
         catchFire(p_57447_, p_57445_, p_57446_, null, null);
      }

      super.m_5707_(p_57445_, p_57446_, p_57447_, p_57448_);
   }

   public void m_7592_(Level p_57441_, BlockPos p_57442_, Explosion p_57443_) {
      if (!p_57441_.f_46443_) {
         PrimedTnt primedtnt = new PrimedTnt(p_57441_, (double)p_57442_.m_123341_() + 0.5D, (double)p_57442_.m_123342_(), (double)p_57442_.m_123343_() + 0.5D, p_57443_.m_46079_());
         int i = primedtnt.m_32100_();
         primedtnt.m_32085_((short)(p_57441_.f_46441_.nextInt(i / 4) + i / 8));
         p_57441_.m_7967_(primedtnt);
      }
   }

   @Deprecated //Forge: Prefer using IForgeBlock#catchFire
   public static void m_57433_(Level p_57434_, BlockPos p_57435_) {
      m_57436_(p_57434_, p_57435_, (LivingEntity)null);
   }

   @Deprecated //Forge: Prefer using IForgeBlock#catchFire
   private static void m_57436_(Level p_57437_, BlockPos p_57438_, @Nullable LivingEntity p_57439_) {
      if (!p_57437_.f_46443_) {
         PrimedTnt primedtnt = new PrimedTnt(p_57437_, (double)p_57438_.m_123341_() + 0.5D, (double)p_57438_.m_123342_(), (double)p_57438_.m_123343_() + 0.5D, p_57439_);
         p_57437_.m_7967_(primedtnt);
         p_57437_.m_6263_((Player)null, primedtnt.m_20185_(), primedtnt.m_20186_(), primedtnt.m_20189_(), SoundEvents.f_12512_, SoundSource.BLOCKS, 1.0F, 1.0F);
         p_57437_.m_142346_(p_57439_, GameEvent.f_157776_, p_57438_);
      }
   }

   public InteractionResult m_6227_(BlockState p_57450_, Level p_57451_, BlockPos p_57452_, Player p_57453_, InteractionHand p_57454_, BlockHitResult p_57455_) {
      ItemStack itemstack = p_57453_.m_21120_(p_57454_);
      if (!itemstack.m_150930_(Items.f_42409_) && !itemstack.m_150930_(Items.f_42613_)) {
         return super.m_6227_(p_57450_, p_57451_, p_57452_, p_57453_, p_57454_, p_57455_);
      } else {
         catchFire(p_57450_, p_57451_, p_57452_, p_57455_.m_82434_(), p_57453_);
         p_57451_.m_7731_(p_57452_, Blocks.f_50016_.m_49966_(), 11);
         Item item = itemstack.m_41720_();
         if (!p_57453_.m_7500_()) {
            if (itemstack.m_150930_(Items.f_42409_)) {
               itemstack.m_41622_(1, p_57453_, (p_57425_) -> {
                  p_57425_.m_21190_(p_57454_);
               });
            } else {
               itemstack.m_41774_(1);
            }
         }

         p_57453_.m_36246_(Stats.f_12982_.m_12902_(item));
         return InteractionResult.m_19078_(p_57451_.f_46443_);
      }
   }

   public void m_5581_(Level p_57429_, BlockState p_57430_, BlockHitResult p_57431_, Projectile p_57432_) {
      if (!p_57429_.f_46443_) {
         BlockPos blockpos = p_57431_.m_82425_();
         Entity entity = p_57432_.m_37282_();
         if (p_57432_.m_6060_() && p_57432_.m_142265_(p_57429_, blockpos)) {
            catchFire(p_57430_, p_57429_, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
            p_57429_.m_7471_(blockpos, false);
         }
      }

   }

   public boolean m_6903_(Explosion p_57427_) {
      return false;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57464_) {
      p_57464_.m_61104_(f_57419_);
   }
}
