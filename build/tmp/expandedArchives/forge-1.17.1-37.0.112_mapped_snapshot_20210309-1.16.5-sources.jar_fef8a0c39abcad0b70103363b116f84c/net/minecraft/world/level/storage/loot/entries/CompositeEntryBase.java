package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public abstract class CompositeEntryBase extends LootPoolEntryContainer {
   protected final LootPoolEntryContainer[] f_79428_;
   private final ComposableEntryContainer f_79429_;

   protected CompositeEntryBase(LootPoolEntryContainer[] p_79431_, LootItemCondition[] p_79432_) {
      super(p_79432_);
      this.f_79428_ = p_79431_;
      this.f_79429_ = this.m_5690_(p_79431_);
   }

   public void m_6165_(ValidationContext p_79434_) {
      super.m_6165_(p_79434_);
      if (this.f_79428_.length == 0) {
         p_79434_.m_79357_("Empty children list");
      }

      for(int i = 0; i < this.f_79428_.length; ++i) {
         this.f_79428_[i].m_6165_(p_79434_.m_79365_(".entry[" + i + "]"));
      }

   }

   protected abstract ComposableEntryContainer m_5690_(ComposableEntryContainer[] p_79437_);

   public final boolean m_6562_(LootContext p_79439_, Consumer<LootPoolEntry> p_79440_) {
      return !this.m_79639_(p_79439_) ? false : this.f_79429_.m_6562_(p_79439_, p_79440_);
   }

   public static <T extends CompositeEntryBase> LootPoolEntryContainer.Serializer<T> m_79435_(final CompositeEntryBase.CompositeEntryConstructor<T> p_79436_) {
      return new LootPoolEntryContainer.Serializer<T>() {
         public void m_7219_(JsonObject p_79449_, T p_79450_, JsonSerializationContext p_79451_) {
            p_79449_.add("children", p_79451_.serialize(p_79450_.f_79428_));
         }

         public final T m_5921_(JsonObject p_79445_, JsonDeserializationContext p_79446_, LootItemCondition[] p_79447_) {
            LootPoolEntryContainer[] alootpoolentrycontainer = GsonHelper.m_13836_(p_79445_, "children", p_79446_, LootPoolEntryContainer[].class);
            return p_79436_.m_79460_(alootpoolentrycontainer, p_79447_);
         }
      };
   }

   @FunctionalInterface
   public interface CompositeEntryConstructor<T extends CompositeEntryBase> {
      T m_79460_(LootPoolEntryContainer[] p_79461_, LootItemCondition[] p_79462_);
   }
}