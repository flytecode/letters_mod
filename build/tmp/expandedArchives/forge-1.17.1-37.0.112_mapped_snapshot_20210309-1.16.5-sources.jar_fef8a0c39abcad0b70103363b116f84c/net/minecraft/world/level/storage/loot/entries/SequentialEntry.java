package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SequentialEntry extends CompositeEntryBase {
   SequentialEntry(LootPoolEntryContainer[] p_79812_, LootItemCondition[] p_79813_) {
      super(p_79812_, p_79813_);
   }

   public LootPoolEntryType m_6751_() {
      return LootPoolEntries.f_79625_;
   }

   protected ComposableEntryContainer m_5690_(ComposableEntryContainer[] p_79816_) {
      switch(p_79816_.length) {
      case 0:
         return f_79406_;
      case 1:
         return p_79816_[0];
      case 2:
         return p_79816_[0].m_79411_(p_79816_[1]);
      default:
         return (p_79819_, p_79820_) -> {
            for(ComposableEntryContainer composableentrycontainer : p_79816_) {
               if (!composableentrycontainer.m_6562_(p_79819_, p_79820_)) {
                  return false;
               }
            }

            return true;
         };
      }
   }

   public static SequentialEntry.Builder m_165152_(LootPoolEntryContainer.Builder<?>... p_165153_) {
      return new SequentialEntry.Builder(p_165153_);
   }

   public static class Builder extends LootPoolEntryContainer.Builder<SequentialEntry.Builder> {
      private final List<LootPoolEntryContainer> f_165154_ = Lists.newArrayList();

      public Builder(LootPoolEntryContainer.Builder<?>... p_165156_) {
         for(LootPoolEntryContainer.Builder<?> builder : p_165156_) {
            this.f_165154_.add(builder.m_7512_());
         }

      }

      protected SequentialEntry.Builder m_6897_() {
         return this;
      }

      public SequentialEntry.Builder m_142639_(LootPoolEntryContainer.Builder<?> p_165160_) {
         this.f_165154_.add(p_165160_.m_7512_());
         return this;
      }

      public LootPoolEntryContainer m_7512_() {
         return new SequentialEntry(this.f_165154_.toArray(new LootPoolEntryContainer[0]), this.m_79651_());
      }
   }
}