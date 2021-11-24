package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetLoreFunction extends LootItemConditionalFunction {
   final boolean f_81079_;
   final List<Component> f_81080_;
   @Nullable
   final LootContext.EntityTarget f_81081_;

   public SetLoreFunction(LootItemCondition[] p_81083_, boolean p_81084_, List<Component> p_81085_, @Nullable LootContext.EntityTarget p_81086_) {
      super(p_81083_);
      this.f_81079_ = p_81084_;
      this.f_81080_ = ImmutableList.copyOf(p_81085_);
      this.f_81081_ = p_81086_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80753_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_81081_ != null ? ImmutableSet.of(this.f_81081_.m_79003_()) : ImmutableSet.of();
   }

   public ItemStack m_7372_(ItemStack p_81089_, LootContext p_81090_) {
      ListTag listtag = this.m_81091_(p_81089_, !this.f_81080_.isEmpty());
      if (listtag != null) {
         if (this.f_81079_) {
            listtag.clear();
         }

         UnaryOperator<Component> unaryoperator = SetNameFunction.m_81139_(p_81090_, this.f_81081_);
         this.f_81080_.stream().map(unaryoperator).map(Component.Serializer::m_130703_).map(StringTag::m_129297_).forEach(listtag::add);
      }

      return p_81089_;
   }

   @Nullable
   private ListTag m_81091_(ItemStack p_81092_, boolean p_81093_) {
      CompoundTag compoundtag;
      if (p_81092_.m_41782_()) {
         compoundtag = p_81092_.m_41783_();
      } else {
         if (!p_81093_) {
            return null;
         }

         compoundtag = new CompoundTag();
         p_81092_.m_41751_(compoundtag);
      }

      CompoundTag compoundtag1;
      if (compoundtag.m_128425_("display", 10)) {
         compoundtag1 = compoundtag.m_128469_("display");
      } else {
         if (!p_81093_) {
            return null;
         }

         compoundtag1 = new CompoundTag();
         compoundtag.m_128365_("display", compoundtag1);
      }

      if (compoundtag1.m_128425_("Lore", 9)) {
         return compoundtag1.m_128437_("Lore", 8);
      } else if (p_81093_) {
         ListTag listtag = new ListTag();
         compoundtag1.m_128365_("Lore", listtag);
         return listtag;
      } else {
         return null;
      }
   }

   public static SetLoreFunction.Builder m_165443_() {
      return new SetLoreFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetLoreFunction.Builder> {
      private boolean f_165444_;
      private LootContext.EntityTarget f_165445_;
      private final List<Component> f_165446_ = Lists.newArrayList();

      public SetLoreFunction.Builder m_165453_(boolean p_165454_) {
         this.f_165444_ = p_165454_;
         return this;
      }

      public SetLoreFunction.Builder m_165449_(LootContext.EntityTarget p_165450_) {
         this.f_165445_ = p_165450_;
         return this;
      }

      public SetLoreFunction.Builder m_165451_(Component p_165452_) {
         this.f_165446_.add(p_165452_);
         return this;
      }

      protected SetLoreFunction.Builder m_6477_() {
         return this;
      }

      public LootItemFunction m_7453_() {
         return new SetLoreFunction(this.m_80699_(), this.f_165444_, this.f_165446_, this.f_165445_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetLoreFunction> {
      public void m_6170_(JsonObject p_81111_, SetLoreFunction p_81112_, JsonSerializationContext p_81113_) {
         super.m_6170_(p_81111_, p_81112_, p_81113_);
         p_81111_.addProperty("replace", p_81112_.f_81079_);
         JsonArray jsonarray = new JsonArray();

         for(Component component : p_81112_.f_81080_) {
            jsonarray.add(Component.Serializer.m_130716_(component));
         }

         p_81111_.add("lore", jsonarray);
         if (p_81112_.f_81081_ != null) {
            p_81111_.add("entity", p_81113_.serialize(p_81112_.f_81081_));
         }

      }

      public SetLoreFunction m_6821_(JsonObject p_81103_, JsonDeserializationContext p_81104_, LootItemCondition[] p_81105_) {
         boolean flag = GsonHelper.m_13855_(p_81103_, "replace", false);
         List<Component> list = Streams.stream(GsonHelper.m_13933_(p_81103_, "lore")).map(Component.Serializer::m_130691_).collect(ImmutableList.toImmutableList());
         LootContext.EntityTarget lootcontext$entitytarget = GsonHelper.m_13845_(p_81103_, "entity", (LootContext.EntityTarget)null, p_81104_, LootContext.EntityTarget.class);
         return new SetLoreFunction(p_81105_, flag, list, lootcontext$entitytarget);
      }
   }
}