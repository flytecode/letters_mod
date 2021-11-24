package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetContainerLootTable extends LootItemConditionalFunction {
   final ResourceLocation f_80955_;
   final long f_80956_;

   SetContainerLootTable(LootItemCondition[] p_80958_, ResourceLocation p_80959_, long p_80960_) {
      super(p_80958_);
      this.f_80955_ = p_80959_;
      this.f_80956_ = p_80960_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80751_;
   }

   public ItemStack m_7372_(ItemStack p_80967_, LootContext p_80968_) {
      if (p_80967_.m_41619_()) {
         return p_80967_;
      } else {
         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128359_("LootTable", this.f_80955_.toString());
         if (this.f_80956_ != 0L) {
            compoundtag.m_128356_("LootTableSeed", this.f_80956_);
         }

         p_80967_.m_41784_().m_128365_("BlockEntityTag", compoundtag);
         return p_80967_;
      }
   }

   public void m_6169_(ValidationContext p_80970_) {
      if (p_80970_.m_79362_(this.f_80955_)) {
         p_80970_.m_79357_("Table " + this.f_80955_ + " is recursively called");
      } else {
         super.m_6169_(p_80970_);
         LootTable loottable = p_80970_.m_79375_(this.f_80955_);
         if (loottable == null) {
            p_80970_.m_79357_("Unknown loot table called " + this.f_80955_);
         } else {
            loottable.m_79136_(p_80970_.m_79359_("->{" + this.f_80955_ + "}", this.f_80955_));
         }

      }
   }

   public static LootItemConditionalFunction.Builder<?> m_165322_(ResourceLocation p_165323_) {
      return m_80683_((p_165333_) -> {
         return new SetContainerLootTable(p_165333_, p_165323_, 0L);
      });
   }

   public static LootItemConditionalFunction.Builder<?> m_165324_(ResourceLocation p_165325_, long p_165326_) {
      return m_80683_((p_165330_) -> {
         return new SetContainerLootTable(p_165330_, p_165325_, p_165326_);
      });
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetContainerLootTable> {
      public void m_6170_(JsonObject p_80986_, SetContainerLootTable p_80987_, JsonSerializationContext p_80988_) {
         super.m_6170_(p_80986_, p_80987_, p_80988_);
         p_80986_.addProperty("name", p_80987_.f_80955_.toString());
         if (p_80987_.f_80956_ != 0L) {
            p_80986_.addProperty("seed", p_80987_.f_80956_);
         }

      }

      public SetContainerLootTable m_6821_(JsonObject p_80978_, JsonDeserializationContext p_80979_, LootItemCondition[] p_80980_) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_80978_, "name"));
         long i = GsonHelper.m_13828_(p_80978_, "seed", 0L);
         return new SetContainerLootTable(p_80980_, resourcelocation, i);
      }
   }
}