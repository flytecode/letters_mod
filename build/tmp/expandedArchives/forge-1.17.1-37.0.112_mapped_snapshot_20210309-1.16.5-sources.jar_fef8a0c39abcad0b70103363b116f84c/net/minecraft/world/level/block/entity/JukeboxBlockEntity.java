package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class JukeboxBlockEntity extends BlockEntity implements Clearable {
   private ItemStack f_59514_ = ItemStack.f_41583_;

   public JukeboxBlockEntity(BlockPos p_155613_, BlockState p_155614_) {
      super(BlockEntityType.f_58921_, p_155613_, p_155614_);
   }

   public void m_142466_(CompoundTag p_155616_) {
      super.m_142466_(p_155616_);
      if (p_155616_.m_128425_("RecordItem", 10)) {
         this.m_59517_(ItemStack.m_41712_(p_155616_.m_128469_("RecordItem")));
      }

   }

   public CompoundTag m_6945_(CompoundTag p_59523_) {
      super.m_6945_(p_59523_);
      if (!this.m_59524_().m_41619_()) {
         p_59523_.m_128365_("RecordItem", this.m_59524_().m_41739_(new CompoundTag()));
      }

      return p_59523_;
   }

   public ItemStack m_59524_() {
      return this.f_59514_;
   }

   public void m_59517_(ItemStack p_59518_) {
      this.f_59514_ = p_59518_;
      this.m_6596_();
   }

   public void m_6211_() {
      this.m_59517_(ItemStack.f_41583_);
   }
}