package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractBannerBlock extends BaseEntityBlock {
   private final DyeColor f_48657_;

   protected AbstractBannerBlock(DyeColor p_48659_, BlockBehaviour.Properties p_48660_) {
      super(p_48660_);
      this.f_48657_ = p_48659_;
   }

   public boolean m_5568_() {
      return true;
   }

   public BlockEntity m_142194_(BlockPos p_151892_, BlockState p_151893_) {
      return new BannerBlockEntity(p_151892_, p_151893_, this.f_48657_);
   }

   public void m_6402_(Level p_48668_, BlockPos p_48669_, BlockState p_48670_, @Nullable LivingEntity p_48671_, ItemStack p_48672_) {
      if (p_48672_.m_41788_()) {
         BlockEntity blockentity = p_48668_.m_7702_(p_48669_);
         if (blockentity instanceof BannerBlockEntity) {
            ((BannerBlockEntity)blockentity).m_58501_(p_48672_.m_41786_());
         }
      }

   }

   public ItemStack m_7397_(BlockGetter p_48664_, BlockPos p_48665_, BlockState p_48666_) {
      BlockEntity blockentity = p_48664_.m_7702_(p_48665_);
      return blockentity instanceof BannerBlockEntity ? ((BannerBlockEntity)blockentity).m_155043_() : super.m_7397_(p_48664_, p_48665_, p_48666_);
   }

   public DyeColor m_48674_() {
      return this.f_48657_;
   }
}