package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;

public class CopyNbtFunction extends LootItemConditionalFunction {
   final NbtProvider f_80234_;
   final List<CopyNbtFunction.CopyOperation> f_80235_;

   CopyNbtFunction(LootItemCondition[] p_165175_, NbtProvider p_165176_, List<CopyNbtFunction.CopyOperation> p_165177_) {
      super(p_165175_);
      this.f_80234_ = p_165176_;
      this.f_80235_ = ImmutableList.copyOf(p_165177_);
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80755_;
   }

   static NbtPathArgument.NbtPath m_80267_(String p_80268_) {
      try {
         return (new NbtPathArgument()).parse(new StringReader(p_80268_));
      } catch (CommandSyntaxException commandsyntaxexception) {
         throw new IllegalArgumentException("Failed to parse path " + p_80268_, commandsyntaxexception);
      }
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_80234_.m_142677_();
   }

   public ItemStack m_7372_(ItemStack p_80250_, LootContext p_80251_) {
      Tag tag = this.f_80234_.m_142301_(p_80251_);
      if (tag != null) {
         this.f_80235_.forEach((p_80255_) -> {
            p_80255_.m_80305_(p_80250_::m_41784_, tag);
         });
      }

      return p_80250_;
   }

   public static CopyNbtFunction.Builder m_165180_(NbtProvider p_165181_) {
      return new CopyNbtFunction.Builder(p_165181_);
   }

   public static CopyNbtFunction.Builder m_165178_(LootContext.EntityTarget p_165179_) {
      return new CopyNbtFunction.Builder(ContextNbtProvider.m_165570_(p_165179_));
   }

   public static class Builder extends LootItemConditionalFunction.Builder<CopyNbtFunction.Builder> {
      private final NbtProvider f_80271_;
      private final List<CopyNbtFunction.CopyOperation> f_80272_ = Lists.newArrayList();

      Builder(NbtProvider p_165183_) {
         this.f_80271_ = p_165183_;
      }

      public CopyNbtFunction.Builder m_80282_(String p_80283_, String p_80284_, CopyNbtFunction.MergeStrategy p_80285_) {
         this.f_80272_.add(new CopyNbtFunction.CopyOperation(p_80283_, p_80284_, p_80285_));
         return this;
      }

      public CopyNbtFunction.Builder m_80279_(String p_80280_, String p_80281_) {
         return this.m_80282_(p_80280_, p_80281_, CopyNbtFunction.MergeStrategy.REPLACE);
      }

      protected CopyNbtFunction.Builder m_6477_() {
         return this;
      }

      public LootItemFunction m_7453_() {
         return new CopyNbtFunction(this.m_80699_(), this.f_80271_, this.f_80272_);
      }
   }

   static class CopyOperation {
      private final String f_80288_;
      private final NbtPathArgument.NbtPath f_80289_;
      private final String f_80290_;
      private final NbtPathArgument.NbtPath f_80291_;
      private final CopyNbtFunction.MergeStrategy f_80292_;

      CopyOperation(String p_80294_, String p_80295_, CopyNbtFunction.MergeStrategy p_80296_) {
         this.f_80288_ = p_80294_;
         this.f_80289_ = CopyNbtFunction.m_80267_(p_80294_);
         this.f_80290_ = p_80295_;
         this.f_80291_ = CopyNbtFunction.m_80267_(p_80295_);
         this.f_80292_ = p_80296_;
      }

      public void m_80305_(Supplier<Tag> p_80306_, Tag p_80307_) {
         try {
            List<Tag> list = this.f_80289_.m_99638_(p_80307_);
            if (!list.isEmpty()) {
               this.f_80292_.m_6706_(p_80306_.get(), this.f_80291_, list);
            }
         } catch (CommandSyntaxException commandsyntaxexception) {
         }

      }

      public JsonObject m_80302_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("source", this.f_80288_);
         jsonobject.addProperty("target", this.f_80290_);
         jsonobject.addProperty("op", this.f_80292_.f_80335_);
         return jsonobject;
      }

      public static CopyNbtFunction.CopyOperation m_80303_(JsonObject p_80304_) {
         String s = GsonHelper.m_13906_(p_80304_, "source");
         String s1 = GsonHelper.m_13906_(p_80304_, "target");
         CopyNbtFunction.MergeStrategy copynbtfunction$mergestrategy = CopyNbtFunction.MergeStrategy.m_80349_(GsonHelper.m_13906_(p_80304_, "op"));
         return new CopyNbtFunction.CopyOperation(s, s1, copynbtfunction$mergestrategy);
      }
   }

   public static enum MergeStrategy {
      REPLACE("replace") {
         public void m_6706_(Tag p_80362_, NbtPathArgument.NbtPath p_80363_, List<Tag> p_80364_) throws CommandSyntaxException {
            p_80363_.m_99645_(p_80362_, Iterables.getLast(p_80364_)::m_6426_);
         }
      },
      APPEND("append") {
         public void m_6706_(Tag p_80373_, NbtPathArgument.NbtPath p_80374_, List<Tag> p_80375_) throws CommandSyntaxException {
            List<Tag> list = p_80374_.m_99640_(p_80373_, ListTag::new);
            list.forEach((p_80371_) -> {
               if (p_80371_ instanceof ListTag) {
                  p_80375_.forEach((p_165187_) -> {
                     ((ListTag)p_80371_).add(p_165187_.m_6426_());
                  });
               }

            });
         }
      },
      MERGE("merge") {
         public void m_6706_(Tag p_80387_, NbtPathArgument.NbtPath p_80388_, List<Tag> p_80389_) throws CommandSyntaxException {
            List<Tag> list = p_80388_.m_99640_(p_80387_, CompoundTag::new);
            list.forEach((p_80385_) -> {
               if (p_80385_ instanceof CompoundTag) {
                  p_80389_.forEach((p_165190_) -> {
                     if (p_165190_ instanceof CompoundTag) {
                        ((CompoundTag)p_80385_).m_128391_((CompoundTag)p_165190_);
                     }

                  });
               }

            });
         }
      };

      final String f_80335_;

      public abstract void m_6706_(Tag p_80351_, NbtPathArgument.NbtPath p_80352_, List<Tag> p_80353_) throws CommandSyntaxException;

      MergeStrategy(String p_80341_) {
         this.f_80335_ = p_80341_;
      }

      public static CopyNbtFunction.MergeStrategy m_80349_(String p_80350_) {
         for(CopyNbtFunction.MergeStrategy copynbtfunction$mergestrategy : values()) {
            if (copynbtfunction$mergestrategy.f_80335_.equals(p_80350_)) {
               return copynbtfunction$mergestrategy;
            }
         }

         throw new IllegalArgumentException("Invalid merge strategy" + p_80350_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<CopyNbtFunction> {
      public void m_6170_(JsonObject p_80399_, CopyNbtFunction p_80400_, JsonSerializationContext p_80401_) {
         super.m_6170_(p_80399_, p_80400_, p_80401_);
         p_80399_.add("source", p_80401_.serialize(p_80400_.f_80234_));
         JsonArray jsonarray = new JsonArray();
         p_80400_.f_80235_.stream().map(CopyNbtFunction.CopyOperation::m_80302_).forEach(jsonarray::add);
         p_80399_.add("ops", jsonarray);
      }

      public CopyNbtFunction m_6821_(JsonObject p_80395_, JsonDeserializationContext p_80396_, LootItemCondition[] p_80397_) {
         NbtProvider nbtprovider = GsonHelper.m_13836_(p_80395_, "source", p_80396_, NbtProvider.class);
         List<CopyNbtFunction.CopyOperation> list = Lists.newArrayList();

         for(JsonElement jsonelement : GsonHelper.m_13933_(p_80395_, "ops")) {
            JsonObject jsonobject = GsonHelper.m_13918_(jsonelement, "op");
            list.add(CopyNbtFunction.CopyOperation.m_80303_(jsonobject));
         }

         return new CopyNbtFunction(p_80397_, nbtprovider, list);
      }
   }
}