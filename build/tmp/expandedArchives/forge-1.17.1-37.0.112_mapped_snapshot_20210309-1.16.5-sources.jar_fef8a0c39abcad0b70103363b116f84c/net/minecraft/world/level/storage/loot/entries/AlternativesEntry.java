package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.commons.lang3.ArrayUtils;

public class AlternativesEntry extends CompositeEntryBase {
   AlternativesEntry(LootPoolEntryContainer[] p_79384_, LootItemCondition[] p_79385_) {
      super(p_79384_, p_79385_);
   }

   public LootPoolEntryType m_6751_() {
      return LootPoolEntries.f_79624_;
   }

   protected ComposableEntryContainer m_5690_(ComposableEntryContainer[] p_79390_) {
      switch(p_79390_.length) {
      case 0:
         return f_79405_;
      case 1:
         return p_79390_[0];
      case 2:
         return p_79390_[0].m_79420_(p_79390_[1]);
      default:
         return (p_79393_, p_79394_) -> {
            for(ComposableEntryContainer composableentrycontainer : p_79390_) {
               if (composableentrycontainer.m_6562_(p_79393_, p_79394_)) {
                  return true;
               }
            }

            return false;
         };
      }
   }

   public void m_6165_(ValidationContext p_79388_) {
      super.m_6165_(p_79388_);

      for(int i = 0; i < this.f_79428_.length - 1; ++i) {
         if (ArrayUtils.isEmpty((Object[])this.f_79428_[i].f_79636_)) {
            p_79388_.m_79357_("Unreachable entry!");
         }
      }

   }

   public static AlternativesEntry.Builder m_79395_(LootPoolEntryContainer.Builder<?>... p_79396_) {
      return new AlternativesEntry.Builder(p_79396_);
   }

   public static class Builder extends LootPoolEntryContainer.Builder<AlternativesEntry.Builder> {
      private final List<LootPoolEntryContainer> f_79397_ = Lists.newArrayList();

      public Builder(LootPoolEntryContainer.Builder<?>... p_79399_) {
         for(LootPoolEntryContainer.Builder<?> builder : p_79399_) {
            this.f_79397_.add(builder.m_7512_());
         }

      }

      protected AlternativesEntry.Builder m_6897_() {
         return this;
      }

      public AlternativesEntry.Builder m_7170_(LootPoolEntryContainer.Builder<?> p_79402_) {
         this.f_79397_.add(p_79402_.m_7512_());
         return this;
      }

      public LootPoolEntryContainer m_7512_() {
         return new AlternativesEntry(this.f_79397_.toArray(new LootPoolEntryContainer[0]), this.m_79651_());
      }
   }
}