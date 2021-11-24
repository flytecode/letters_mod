package net.minecraft.world.level.storage.loot.entries;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class EntryGroup extends CompositeEntryBase {
   EntryGroup(LootPoolEntryContainer[] p_79550_, LootItemCondition[] p_79551_) {
      super(p_79550_, p_79551_);
   }

   public LootPoolEntryType m_6751_() {
      return LootPoolEntries.f_79626_;
   }

   protected ComposableEntryContainer m_5690_(ComposableEntryContainer[] p_79559_) {
      switch(p_79559_.length) {
      case 0:
         return f_79406_;
      case 1:
         return p_79559_[0];
      case 2:
         ComposableEntryContainer composableentrycontainer = p_79559_[0];
         ComposableEntryContainer composableentrycontainer1 = p_79559_[1];
         return (p_79556_, p_79557_) -> {
            composableentrycontainer.m_6562_(p_79556_, p_79557_);
            composableentrycontainer1.m_6562_(p_79556_, p_79557_);
            return true;
         };
      default:
         return (p_79562_, p_79563_) -> {
            for(ComposableEntryContainer composableentrycontainer2 : p_79559_) {
               composableentrycontainer2.m_6562_(p_79562_, p_79563_);
            }

            return true;
         };
      }
   }

   public static EntryGroup.Builder m_165137_(LootPoolEntryContainer.Builder<?>... p_165138_) {
      return new EntryGroup.Builder(p_165138_);
   }

   public static class Builder extends LootPoolEntryContainer.Builder<EntryGroup.Builder> {
      private final List<LootPoolEntryContainer> f_165139_ = Lists.newArrayList();

      public Builder(LootPoolEntryContainer.Builder<?>... p_165141_) {
         for(LootPoolEntryContainer.Builder<?> builder : p_165141_) {
            this.f_165139_.add(builder.m_7512_());
         }

      }

      protected EntryGroup.Builder m_6897_() {
         return this;
      }

      public EntryGroup.Builder m_142719_(LootPoolEntryContainer.Builder<?> p_165145_) {
         this.f_165139_.add(p_165145_.m_7512_());
         return this;
      }

      public LootPoolEntryContainer m_7512_() {
         return new EntryGroup(this.f_165139_.toArray(new LootPoolEntryContainer[0]), this.m_79651_());
      }
   }
}