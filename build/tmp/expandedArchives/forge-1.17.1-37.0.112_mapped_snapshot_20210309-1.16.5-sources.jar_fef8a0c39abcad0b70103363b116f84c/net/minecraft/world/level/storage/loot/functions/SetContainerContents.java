package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetContainerContents extends LootItemConditionalFunction {
   final List<LootPoolEntryContainer> f_80902_;

   SetContainerContents(LootItemCondition[] p_80904_, List<LootPoolEntryContainer> p_80905_) {
      super(p_80904_);
      this.f_80902_ = ImmutableList.copyOf(p_80905_);
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80748_;
   }

   public ItemStack m_7372_(ItemStack p_80911_, LootContext p_80912_) {
      if (p_80911_.m_41619_()) {
         return p_80911_;
      } else {
         NonNullList<ItemStack> nonnulllist = NonNullList.m_122779_();
         this.f_80902_.forEach((p_80916_) -> {
            p_80916_.m_6562_(p_80912_, (p_165321_) -> {
               p_165321_.m_6941_(LootTable.m_79142_(nonnulllist::add), p_80912_);
            });
         });
         CompoundTag compoundtag = new CompoundTag();
         ContainerHelper.m_18973_(compoundtag, nonnulllist);
         CompoundTag compoundtag1 = p_80911_.m_41784_();
         compoundtag1.m_128365_("BlockEntityTag", compoundtag.m_128391_(compoundtag1.m_128469_("BlockEntityTag")));
         return p_80911_;
      }
   }

   public void m_6169_(ValidationContext p_80918_) {
      super.m_6169_(p_80918_);

      for(int i = 0; i < this.f_80902_.size(); ++i) {
         this.f_80902_.get(i).m_6165_(p_80918_.m_79365_(".entry[" + i + "]"));
      }

   }

   public static SetContainerContents.Builder m_80926_() {
      return new SetContainerContents.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetContainerContents.Builder> {
      private final List<LootPoolEntryContainer> f_80927_ = Lists.newArrayList();

      protected SetContainerContents.Builder m_6477_() {
         return this;
      }

      public SetContainerContents.Builder m_80930_(LootPoolEntryContainer.Builder<?> p_80931_) {
         this.f_80927_.add(p_80931_.m_7512_());
         return this;
      }

      public LootItemFunction m_7453_() {
         return new SetContainerContents(this.m_80699_(), this.f_80927_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetContainerContents> {
      public void m_6170_(JsonObject p_80944_, SetContainerContents p_80945_, JsonSerializationContext p_80946_) {
         super.m_6170_(p_80944_, p_80945_, p_80946_);
         p_80944_.add("entries", p_80946_.serialize(p_80945_.f_80902_));
      }

      public SetContainerContents m_6821_(JsonObject p_80936_, JsonDeserializationContext p_80937_, LootItemCondition[] p_80938_) {
         LootPoolEntryContainer[] alootpoolentrycontainer = GsonHelper.m_13836_(p_80936_, "entries", p_80937_, LootPoolEntryContainer[].class);
         return new SetContainerContents(p_80938_, Arrays.asList(alootpoolentrycontainer));
      }
   }
}