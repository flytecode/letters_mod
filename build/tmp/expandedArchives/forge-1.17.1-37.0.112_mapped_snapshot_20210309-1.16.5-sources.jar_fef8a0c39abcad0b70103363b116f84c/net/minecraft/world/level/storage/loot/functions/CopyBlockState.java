package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class CopyBlockState extends LootItemConditionalFunction {
   final Block f_80047_;
   final Set<Property<?>> f_80048_;

   CopyBlockState(LootItemCondition[] p_80050_, Block p_80051_, Set<Property<?>> p_80052_) {
      super(p_80050_);
      this.f_80047_ = p_80051_;
      this.f_80048_ = p_80052_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80756_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81461_);
   }

   protected ItemStack m_7372_(ItemStack p_80060_, LootContext p_80061_) {
      BlockState blockstate = p_80061_.m_78953_(LootContextParams.f_81461_);
      if (blockstate != null) {
         CompoundTag compoundtag = p_80060_.m_41784_();
         CompoundTag compoundtag1;
         if (compoundtag.m_128425_("BlockStateTag", 10)) {
            compoundtag1 = compoundtag.m_128469_("BlockStateTag");
         } else {
            compoundtag1 = new CompoundTag();
            compoundtag.m_128365_("BlockStateTag", compoundtag1);
         }

         this.f_80048_.stream().filter(blockstate::m_61138_).forEach((p_80072_) -> {
            compoundtag1.m_128359_(p_80072_.m_61708_(), m_80064_(blockstate, p_80072_));
         });
      }

      return p_80060_;
   }

   public static CopyBlockState.Builder m_80062_(Block p_80063_) {
      return new CopyBlockState.Builder(p_80063_);
   }

   private static <T extends Comparable<T>> String m_80064_(BlockState p_80065_, Property<T> p_80066_) {
      T t = p_80065_.m_61143_(p_80066_);
      return p_80066_.m_6940_(t);
   }

   public static class Builder extends LootItemConditionalFunction.Builder<CopyBlockState.Builder> {
      private final Block f_80076_;
      private final Set<Property<?>> f_80077_ = Sets.newHashSet();

      Builder(Block p_80079_) {
         this.f_80076_ = p_80079_;
      }

      public CopyBlockState.Builder m_80084_(Property<?> p_80085_) {
         if (!this.f_80076_.m_49965_().m_61092_().contains(p_80085_)) {
            throw new IllegalStateException("Property " + p_80085_ + " is not present on block " + this.f_80076_);
         } else {
            this.f_80077_.add(p_80085_);
            return this;
         }
      }

      protected CopyBlockState.Builder m_6477_() {
         return this;
      }

      public LootItemFunction m_7453_() {
         return new CopyBlockState(this.m_80699_(), this.f_80076_, this.f_80077_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<CopyBlockState> {
      public void m_6170_(JsonObject p_80097_, CopyBlockState p_80098_, JsonSerializationContext p_80099_) {
         super.m_6170_(p_80097_, p_80098_, p_80099_);
         p_80097_.addProperty("block", Registry.f_122824_.m_7981_(p_80098_.f_80047_).toString());
         JsonArray jsonarray = new JsonArray();
         p_80098_.f_80048_.forEach((p_80091_) -> {
            jsonarray.add(p_80091_.m_61708_());
         });
         p_80097_.add("properties", jsonarray);
      }

      public CopyBlockState m_6821_(JsonObject p_80093_, JsonDeserializationContext p_80094_, LootItemCondition[] p_80095_) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_80093_, "block"));
         Block block = Registry.f_122824_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new IllegalArgumentException("Can't find block " + resourcelocation);
         });
         StateDefinition<Block, BlockState> statedefinition = block.m_49965_();
         Set<Property<?>> set = Sets.newHashSet();
         JsonArray jsonarray = GsonHelper.m_13832_(p_80093_, "properties", (JsonArray)null);
         if (jsonarray != null) {
            jsonarray.forEach((p_80111_) -> {
               set.add(statedefinition.m_61081_(GsonHelper.m_13805_(p_80111_, "property")));
            });
         }

         return new CopyBlockState(p_80095_, block, set);
      }
   }
}