package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UseBonemeal extends Behavior<Villager> {
   private static final int f_148035_ = 80;
   private long f_24461_;
   private long f_24462_;
   private int f_24463_;
   private Optional<BlockPos> f_24464_ = Optional.empty();

   public UseBonemeal() {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT));
   }

   protected boolean m_6114_(ServerLevel p_24474_, Villager p_24475_) {
      if (p_24475_.f_19797_ % 10 == 0 && (this.f_24462_ == 0L || this.f_24462_ + 160L <= (long)p_24475_.f_19797_)) {
         if (p_24475_.m_141944_().m_18947_(Items.f_42499_) <= 0) {
            return false;
         } else {
            this.f_24464_ = this.m_24492_(p_24474_, p_24475_);
            return this.f_24464_.isPresent();
         }
      } else {
         return false;
      }
   }

   protected boolean m_6737_(ServerLevel p_24477_, Villager p_24478_, long p_24479_) {
      return this.f_24463_ < 80 && this.f_24464_.isPresent();
   }

   private Optional<BlockPos> m_24492_(ServerLevel p_24493_, Villager p_24494_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      Optional<BlockPos> optional = Optional.empty();
      int i = 0;

      for(int j = -1; j <= 1; ++j) {
         for(int k = -1; k <= 1; ++k) {
            for(int l = -1; l <= 1; ++l) {
               blockpos$mutableblockpos.m_122154_(p_24494_.m_142538_(), j, k, l);
               if (this.m_24485_(blockpos$mutableblockpos, p_24493_)) {
                  ++i;
                  if (p_24493_.f_46441_.nextInt(i) == 0) {
                     optional = Optional.of(blockpos$mutableblockpos.m_7949_());
                  }
               }
            }
         }
      }

      return optional;
   }

   private boolean m_24485_(BlockPos p_24486_, ServerLevel p_24487_) {
      BlockState blockstate = p_24487_.m_8055_(p_24486_);
      Block block = blockstate.m_60734_();
      return block instanceof CropBlock && !((CropBlock)block).m_52307_(blockstate);
   }

   protected void m_6735_(ServerLevel p_24496_, Villager p_24497_, long p_24498_) {
      this.m_24480_(p_24497_);
      p_24497_.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42499_));
      this.f_24461_ = p_24498_;
      this.f_24463_ = 0;
   }

   private void m_24480_(Villager p_24481_) {
      this.f_24464_.ifPresent((p_24484_) -> {
         BlockPosTracker blockpostracker = new BlockPosTracker(p_24484_);
         p_24481_.m_6274_().m_21879_(MemoryModuleType.f_26371_, blockpostracker);
         p_24481_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(blockpostracker, 0.5F, 1));
      });
   }

   protected void m_6732_(ServerLevel p_24504_, Villager p_24505_, long p_24506_) {
      p_24505_.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
      this.f_24462_ = (long)p_24505_.f_19797_;
   }

   protected void m_6725_(ServerLevel p_24512_, Villager p_24513_, long p_24514_) {
      BlockPos blockpos = this.f_24464_.get();
      if (p_24514_ >= this.f_24461_ && blockpos.m_123306_(p_24513_.m_20182_(), 1.0D)) {
         ItemStack itemstack = ItemStack.f_41583_;
         SimpleContainer simplecontainer = p_24513_.m_141944_();
         int i = simplecontainer.m_6643_();

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack1 = simplecontainer.m_8020_(j);
            if (itemstack1.m_150930_(Items.f_42499_)) {
               itemstack = itemstack1;
               break;
            }
         }

         if (!itemstack.m_41619_() && BoneMealItem.m_40627_(itemstack, p_24512_, blockpos)) {
            p_24512_.m_46796_(1505, blockpos, 0);
            this.f_24464_ = this.m_24492_(p_24512_, p_24513_);
            this.m_24480_(p_24513_);
            this.f_24461_ = p_24514_ + 40L;
         }

         ++this.f_24463_;
      }
   }
}