package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetAttributesFunction extends LootItemConditionalFunction {
   final List<SetAttributesFunction.Modifier> f_80831_;

   SetAttributesFunction(LootItemCondition[] p_80833_, List<SetAttributesFunction.Modifier> p_80834_) {
      super(p_80833_);
      this.f_80831_ = ImmutableList.copyOf(p_80834_);
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80743_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_80831_.stream().flatMap((p_165234_) -> {
         return p_165234_.f_80850_.m_6231_().stream();
      }).collect(ImmutableSet.toImmutableSet());
   }

   public ItemStack m_7372_(ItemStack p_80840_, LootContext p_80841_) {
      Random random = p_80841_.m_78933_();

      for(SetAttributesFunction.Modifier setattributesfunction$modifier : this.f_80831_) {
         UUID uuid = setattributesfunction$modifier.f_80851_;
         if (uuid == null) {
            uuid = UUID.randomUUID();
         }

         EquipmentSlot equipmentslot = Util.m_137545_(setattributesfunction$modifier.f_80852_, random);
         p_80840_.m_41643_(setattributesfunction$modifier.f_80848_, new AttributeModifier(uuid, setattributesfunction$modifier.f_80847_, (double)setattributesfunction$modifier.f_80850_.m_142688_(p_80841_), setattributesfunction$modifier.f_80849_), equipmentslot);
      }

      return p_80840_;
   }

   public static SetAttributesFunction.ModifierBuilder m_165235_(String p_165236_, Attribute p_165237_, AttributeModifier.Operation p_165238_, NumberProvider p_165239_) {
      return new SetAttributesFunction.ModifierBuilder(p_165236_, p_165237_, p_165238_, p_165239_);
   }

   public static SetAttributesFunction.Builder m_165241_() {
      return new SetAttributesFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetAttributesFunction.Builder> {
      private final List<SetAttributesFunction.Modifier> f_165242_ = Lists.newArrayList();

      protected SetAttributesFunction.Builder m_6477_() {
         return this;
      }

      public SetAttributesFunction.Builder m_165245_(SetAttributesFunction.ModifierBuilder p_165246_) {
         this.f_165242_.add(p_165246_.m_165267_());
         return this;
      }

      public LootItemFunction m_7453_() {
         return new SetAttributesFunction(this.m_80699_(), this.f_165242_);
      }
   }

   static class Modifier {
      final String f_80847_;
      final Attribute f_80848_;
      final AttributeModifier.Operation f_80849_;
      final NumberProvider f_80850_;
      @Nullable
      final UUID f_80851_;
      final EquipmentSlot[] f_80852_;

      Modifier(String p_165250_, Attribute p_165251_, AttributeModifier.Operation p_165252_, NumberProvider p_165253_, EquipmentSlot[] p_165254_, @Nullable UUID p_165255_) {
         this.f_80847_ = p_165250_;
         this.f_80848_ = p_165251_;
         this.f_80849_ = p_165252_;
         this.f_80850_ = p_165253_;
         this.f_80851_ = p_165255_;
         this.f_80852_ = p_165254_;
      }

      public JsonObject m_80865_(JsonSerializationContext p_80866_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("name", this.f_80847_);
         jsonobject.addProperty("attribute", Registry.f_122866_.m_7981_(this.f_80848_).toString());
         jsonobject.addProperty("operation", m_80860_(this.f_80849_));
         jsonobject.add("amount", p_80866_.serialize(this.f_80850_));
         if (this.f_80851_ != null) {
            jsonobject.addProperty("id", this.f_80851_.toString());
         }

         if (this.f_80852_.length == 1) {
            jsonobject.addProperty("slot", this.f_80852_[0].m_20751_());
         } else {
            JsonArray jsonarray = new JsonArray();

            for(EquipmentSlot equipmentslot : this.f_80852_) {
               jsonarray.add(new JsonPrimitive(equipmentslot.m_20751_()));
            }

            jsonobject.add("slot", jsonarray);
         }

         return jsonobject;
      }

      public static SetAttributesFunction.Modifier m_80862_(JsonObject p_80863_, JsonDeserializationContext p_80864_) {
         String s = GsonHelper.m_13906_(p_80863_, "name");
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_80863_, "attribute"));
         Attribute attribute = Registry.f_122866_.m_7745_(resourcelocation);
         if (attribute == null) {
            throw new JsonSyntaxException("Unknown attribute: " + resourcelocation);
         } else {
            AttributeModifier.Operation attributemodifier$operation = m_80869_(GsonHelper.m_13906_(p_80863_, "operation"));
            NumberProvider numberprovider = GsonHelper.m_13836_(p_80863_, "amount", p_80864_, NumberProvider.class);
            UUID uuid = null;
            EquipmentSlot[] aequipmentslot;
            if (GsonHelper.m_13813_(p_80863_, "slot")) {
               aequipmentslot = new EquipmentSlot[]{EquipmentSlot.m_20747_(GsonHelper.m_13906_(p_80863_, "slot"))};
            } else {
               if (!GsonHelper.m_13885_(p_80863_, "slot")) {
                  throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
               }

               JsonArray jsonarray = GsonHelper.m_13933_(p_80863_, "slot");
               aequipmentslot = new EquipmentSlot[jsonarray.size()];
               int i = 0;

               for(JsonElement jsonelement : jsonarray) {
                  aequipmentslot[i++] = EquipmentSlot.m_20747_(GsonHelper.m_13805_(jsonelement, "slot"));
               }

               if (aequipmentslot.length == 0) {
                  throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
               }
            }

            if (p_80863_.has("id")) {
               String s1 = GsonHelper.m_13906_(p_80863_, "id");

               try {
                  uuid = UUID.fromString(s1);
               } catch (IllegalArgumentException illegalargumentexception) {
                  throw new JsonSyntaxException("Invalid attribute modifier id '" + s1 + "' (must be UUID format, with dashes)");
               }
            }

            return new SetAttributesFunction.Modifier(s, attribute, attributemodifier$operation, numberprovider, aequipmentslot, uuid);
         }
      }

      private static String m_80860_(AttributeModifier.Operation p_80861_) {
         switch(p_80861_) {
         case ADDITION:
            return "addition";
         case MULTIPLY_BASE:
            return "multiply_base";
         case MULTIPLY_TOTAL:
            return "multiply_total";
         default:
            throw new IllegalArgumentException("Unknown operation " + p_80861_);
         }
      }

      private static AttributeModifier.Operation m_80869_(String p_80870_) {
         switch(p_80870_) {
         case "addition":
            return AttributeModifier.Operation.ADDITION;
         case "multiply_base":
            return AttributeModifier.Operation.MULTIPLY_BASE;
         case "multiply_total":
            return AttributeModifier.Operation.MULTIPLY_TOTAL;
         default:
            throw new JsonSyntaxException("Unknown attribute modifier operation " + p_80870_);
         }
      }
   }

   public static class ModifierBuilder {
      private final String f_165256_;
      private final Attribute f_165257_;
      private final AttributeModifier.Operation f_165258_;
      private final NumberProvider f_165259_;
      @Nullable
      private UUID f_165260_;
      private final Set<EquipmentSlot> f_165261_ = EnumSet.noneOf(EquipmentSlot.class);

      public ModifierBuilder(String p_165263_, Attribute p_165264_, AttributeModifier.Operation p_165265_, NumberProvider p_165266_) {
         this.f_165256_ = p_165263_;
         this.f_165257_ = p_165264_;
         this.f_165258_ = p_165265_;
         this.f_165259_ = p_165266_;
      }

      public SetAttributesFunction.ModifierBuilder m_165268_(EquipmentSlot p_165269_) {
         this.f_165261_.add(p_165269_);
         return this;
      }

      public SetAttributesFunction.ModifierBuilder m_165270_(UUID p_165271_) {
         this.f_165260_ = p_165271_;
         return this;
      }

      public SetAttributesFunction.Modifier m_165267_() {
         return new SetAttributesFunction.Modifier(this.f_165256_, this.f_165257_, this.f_165258_, this.f_165259_, this.f_165261_.toArray(new EquipmentSlot[0]), this.f_165260_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetAttributesFunction> {
      public void m_6170_(JsonObject p_80891_, SetAttributesFunction p_80892_, JsonSerializationContext p_80893_) {
         super.m_6170_(p_80891_, p_80892_, p_80893_);
         JsonArray jsonarray = new JsonArray();

         for(SetAttributesFunction.Modifier setattributesfunction$modifier : p_80892_.f_80831_) {
            jsonarray.add(setattributesfunction$modifier.m_80865_(p_80893_));
         }

         p_80891_.add("modifiers", jsonarray);
      }

      public SetAttributesFunction m_6821_(JsonObject p_80883_, JsonDeserializationContext p_80884_, LootItemCondition[] p_80885_) {
         JsonArray jsonarray = GsonHelper.m_13933_(p_80883_, "modifiers");
         List<SetAttributesFunction.Modifier> list = Lists.newArrayListWithExpectedSize(jsonarray.size());

         for(JsonElement jsonelement : jsonarray) {
            list.add(SetAttributesFunction.Modifier.m_80862_(GsonHelper.m_13918_(jsonelement, "modifier"), p_80884_));
         }

         if (list.isEmpty()) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new SetAttributesFunction(p_80885_, list);
         }
      }
   }
}