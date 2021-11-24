package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BucketItem extends Item implements DispensibleContainerItem {
   private final Fluid f_40687_;

   // Forge: Use the other constructor that takes a Supplier
   @Deprecated
   public BucketItem(Fluid p_40689_, Item.Properties p_40690_) {
      super(p_40690_);
      this.f_40687_ = p_40689_;
      this.fluidSupplier = p_40689_.delegate;
   }

   /**
    * @param supplier A fluid supplier such as {@link net.minecraftforge.fmllegacy.RegistryObject<Fluid>}
    */
   public BucketItem(java.util.function.Supplier<? extends Fluid> supplier, Item.Properties builder) {
      super(builder);
      this.f_40687_ = null;
      this.fluidSupplier = supplier;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_40703_, Player p_40704_, InteractionHand p_40705_) {
      ItemStack itemstack = p_40704_.m_21120_(p_40705_);
      BlockHitResult blockhitresult = m_41435_(p_40703_, p_40704_, this.f_40687_ == Fluids.f_76191_ ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
      InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(p_40704_, p_40703_, itemstack, blockhitresult);
      if (ret != null) return ret;
      if (blockhitresult.m_6662_() == HitResult.Type.MISS) {
         return InteractionResultHolder.m_19098_(itemstack);
      } else if (blockhitresult.m_6662_() != HitResult.Type.BLOCK) {
         return InteractionResultHolder.m_19098_(itemstack);
      } else {
         BlockPos blockpos = blockhitresult.m_82425_();
         Direction direction = blockhitresult.m_82434_();
         BlockPos blockpos1 = blockpos.m_142300_(direction);
         if (p_40703_.m_7966_(p_40704_, blockpos) && p_40704_.m_36204_(blockpos1, direction, itemstack)) {
            if (this.f_40687_ == Fluids.f_76191_) {
               BlockState blockstate1 = p_40703_.m_8055_(blockpos);
               if (blockstate1.m_60734_() instanceof BucketPickup) {
                  BucketPickup bucketpickup = (BucketPickup)blockstate1.m_60734_();
                  ItemStack itemstack1 = bucketpickup.m_142598_(p_40703_, blockpos, blockstate1);
                  if (!itemstack1.m_41619_()) {
                     p_40704_.m_36246_(Stats.f_12982_.m_12902_(this));
                     bucketpickup.m_142298_().ifPresent((p_150709_) -> {
                        p_40704_.m_5496_(p_150709_, 1.0F, 1.0F);
                     });
                     p_40703_.m_142346_(p_40704_, GameEvent.f_157816_, blockpos);
                     ItemStack itemstack2 = ItemUtils.m_41813_(itemstack, p_40704_, itemstack1);
                     if (!p_40703_.f_46443_) {
                        CriteriaTriggers.f_10576_.m_38772_((ServerPlayer)p_40704_, itemstack1);
                     }

                     return InteractionResultHolder.m_19092_(itemstack2, p_40703_.m_5776_());
                  }
               }

               return InteractionResultHolder.m_19100_(itemstack);
            } else {
               BlockState blockstate = p_40703_.m_8055_(blockpos);
               BlockPos blockpos2 = canBlockContainFluid(p_40703_, blockpos, blockstate) ? blockpos : blockpos1;
               if (this.m_142073_(p_40704_, p_40703_, blockpos2, blockhitresult)) {
                  this.m_142131_(p_40704_, p_40703_, itemstack, blockpos2);
                  if (p_40704_ instanceof ServerPlayer) {
                     CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)p_40704_, blockpos2, itemstack);
                  }

                  p_40704_.m_36246_(Stats.f_12982_.m_12902_(this));
                  return InteractionResultHolder.m_19092_(m_40699_(itemstack, p_40704_), p_40703_.m_5776_());
               } else {
                  return InteractionResultHolder.m_19100_(itemstack);
               }
            }
         } else {
            return InteractionResultHolder.m_19100_(itemstack);
         }
      }
   }

   public static ItemStack m_40699_(ItemStack p_40700_, Player p_40701_) {
      return !p_40701_.m_150110_().f_35937_ ? new ItemStack(Items.f_42446_) : p_40700_;
   }

   public void m_142131_(@Nullable Player p_150711_, Level p_150712_, ItemStack p_150713_, BlockPos p_150714_) {
   }

   public boolean m_142073_(@Nullable Player p_150716_, Level p_150717_, BlockPos p_150718_, @Nullable BlockHitResult p_150719_) {
      if (!(this.f_40687_ instanceof FlowingFluid)) {
         return false;
      } else {
         BlockState blockstate = p_150717_.m_8055_(p_150718_);
         Block block = blockstate.m_60734_();
         Material material = blockstate.m_60767_();
         boolean flag = blockstate.m_60722_(this.f_40687_);
         boolean flag1 = blockstate.m_60795_() || flag || block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).m_6044_(p_150717_, p_150718_, blockstate, this.f_40687_);
         if (!flag1) {
            return p_150719_ != null && this.m_142073_(p_150716_, p_150717_, p_150719_.m_82425_().m_142300_(p_150719_.m_82434_()), (BlockHitResult)null);
         } else if (p_150717_.m_6042_().m_63951_() && this.f_40687_.m_76108_(FluidTags.f_13131_)) {
            int i = p_150718_.m_123341_();
            int j = p_150718_.m_123342_();
            int k = p_150718_.m_123343_();
            p_150717_.m_5594_(p_150716_, p_150718_, SoundEvents.f_11937_, SoundSource.BLOCKS, 0.5F, 2.6F + (p_150717_.f_46441_.nextFloat() - p_150717_.f_46441_.nextFloat()) * 0.8F);

            for(int l = 0; l < 8; ++l) {
               p_150717_.m_7106_(ParticleTypes.f_123755_, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
            }

            return true;
         } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).m_6044_(p_150717_,p_150718_,blockstate,f_40687_)) {
            ((LiquidBlockContainer)block).m_7361_(p_150717_, p_150718_, blockstate, ((FlowingFluid)this.f_40687_).m_76068_(false));
            this.m_7718_(p_150716_, p_150717_, p_150718_);
            return true;
         } else {
            if (!p_150717_.f_46443_ && flag && !material.m_76332_()) {
               p_150717_.m_46961_(p_150718_, true);
            }

            if (!p_150717_.m_7731_(p_150718_, this.f_40687_.m_76145_().m_76188_(), 11) && !blockstate.m_60819_().m_76170_()) {
               return false;
            } else {
               this.m_7718_(p_150716_, p_150717_, p_150718_);
               return true;
            }
         }
      }
   }

   protected void m_7718_(@Nullable Player p_40696_, LevelAccessor p_40697_, BlockPos p_40698_) {
      SoundEvent soundevent = this.f_40687_.getAttributes().getEmptySound();
      if(soundevent == null) soundevent = this.f_40687_.m_76108_(FluidTags.f_13132_) ? SoundEvents.f_11780_ : SoundEvents.f_11778_;
      p_40697_.m_5594_(p_40696_, p_40698_, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
      p_40697_.m_142346_(p_40696_, GameEvent.f_157769_, p_40698_);
   }

   @Override
   public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
      if (this.getClass() == BucketItem.class)
         return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
      else
         return super.initCapabilities(stack, nbt);
   }

   private final java.util.function.Supplier<? extends Fluid> fluidSupplier;
   public Fluid getFluid() { return fluidSupplier.get(); }

   private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate)
   {
      return blockstate.m_60734_() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.m_60734_()).m_6044_(worldIn, posIn, blockstate, this.f_40687_);
   }
}
