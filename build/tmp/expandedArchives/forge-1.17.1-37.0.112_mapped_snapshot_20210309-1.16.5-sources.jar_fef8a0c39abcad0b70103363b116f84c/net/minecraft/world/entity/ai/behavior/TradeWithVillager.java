package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TradeWithVillager extends Behavior<Villager> {
   private static final int f_147996_ = 5;
   private static final float f_147997_ = 0.5F;
   private Set<Item> f_24406_ = ImmutableSet.of();

   public TradeWithVillager() {
      super(ImmutableMap.of(MemoryModuleType.f_26374_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT));
   }

   protected boolean m_6114_(ServerLevel p_24416_, Villager p_24417_) {
      return BehaviorUtils.m_22639_(p_24417_.m_6274_(), MemoryModuleType.f_26374_, EntityType.f_20492_);
   }

   protected boolean m_6737_(ServerLevel p_24419_, Villager p_24420_, long p_24421_) {
      return this.m_6114_(p_24419_, p_24420_);
   }

   protected void m_6735_(ServerLevel p_24437_, Villager p_24438_, long p_24439_) {
      Villager villager = (Villager)p_24438_.m_6274_().m_21952_(MemoryModuleType.f_26374_).get();
      BehaviorUtils.m_22602_(p_24438_, villager, 0.5F);
      this.f_24406_ = m_24422_(p_24438_, villager);
   }

   protected void m_6725_(ServerLevel p_24445_, Villager p_24446_, long p_24447_) {
      Villager villager = (Villager)p_24446_.m_6274_().m_21952_(MemoryModuleType.f_26374_).get();
      if (!(p_24446_.m_20280_(villager) > 5.0D)) {
         BehaviorUtils.m_22602_(p_24446_, villager, 0.5F);
         p_24446_.m_35411_(p_24445_, villager, p_24447_);
         if (p_24446_.m_35514_() && (p_24446_.m_7141_().m_35571_() == VillagerProfession.f_35590_ || villager.m_35515_())) {
            m_24425_(p_24446_, Villager.f_35369_.keySet(), villager);
         }

         if (villager.m_7141_().m_35571_() == VillagerProfession.f_35590_ && p_24446_.m_141944_().m_18947_(Items.f_42405_) > Items.f_42405_.m_41459_() / 2) {
            m_24425_(p_24446_, ImmutableSet.of(Items.f_42405_), villager);
         }

         if (!this.f_24406_.isEmpty() && p_24446_.m_141944_().m_18949_(this.f_24406_)) {
            m_24425_(p_24446_, this.f_24406_, villager);
         }

      }
   }

   protected void m_6732_(ServerLevel p_24453_, Villager p_24454_, long p_24455_) {
      p_24454_.m_6274_().m_21936_(MemoryModuleType.f_26374_);
   }

   private static Set<Item> m_24422_(Villager p_24423_, Villager p_24424_) {
      ImmutableSet<Item> immutableset = p_24424_.m_7141_().m_35571_().m_35623_();
      ImmutableSet<Item> immutableset1 = p_24423_.m_7141_().m_35571_().m_35623_();
      return immutableset.stream().filter((p_24431_) -> {
         return !immutableset1.contains(p_24431_);
      }).collect(Collectors.toSet());
   }

   private static void m_24425_(Villager p_24426_, Set<Item> p_24427_, LivingEntity p_24428_) {
      SimpleContainer simplecontainer = p_24426_.m_141944_();
      ItemStack itemstack = ItemStack.f_41583_;
      int i = 0;

      while(i < simplecontainer.m_6643_()) {
         ItemStack itemstack1;
         Item item;
         int j;
         label28: {
            itemstack1 = simplecontainer.m_8020_(i);
            if (!itemstack1.m_41619_()) {
               item = itemstack1.m_41720_();
               if (p_24427_.contains(item)) {
                  if (itemstack1.m_41613_() > itemstack1.m_41741_() / 2) {
                     j = itemstack1.m_41613_() / 2;
                     break label28;
                  }

                  if (itemstack1.m_41613_() > 24) {
                     j = itemstack1.m_41613_() - 24;
                     break label28;
                  }
               }
            }

            ++i;
            continue;
         }

         itemstack1.m_41774_(j);
         itemstack = new ItemStack(item, j);
         break;
      }

      if (!itemstack.m_41619_()) {
         BehaviorUtils.m_22613_(p_24426_, itemstack, p_24428_.m_20182_());
      }

   }
}