package net.minecraft.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable {
   static final Logger f_79107_ = LogManager.getLogger();
   public static final LootTable f_79105_ = new LootTable(LootContextParamSets.f_81410_, new LootPool[0], new LootItemFunction[0]);
   public static final LootContextParamSet f_79106_ = LootContextParamSets.f_81420_;
   final LootContextParamSet f_79108_;
   private final List<LootPool> f_79109_;
   final LootItemFunction[] f_79110_;
   private final BiFunction<ItemStack, LootContext, ItemStack> f_79111_;

   LootTable(LootContextParamSet p_79114_, LootPool[] p_79115_, LootItemFunction[] p_79116_) {
      this.f_79108_ = p_79114_;
      this.f_79109_ = Lists.newArrayList(p_79115_);
      this.f_79110_ = p_79116_;
      this.f_79111_ = LootItemFunctions.m_80770_(p_79116_);
   }

   public static Consumer<ItemStack> m_79142_(Consumer<ItemStack> p_79143_) {
      return (p_79146_) -> {
         if (p_79146_.m_41613_() < p_79146_.m_41741_()) {
            p_79143_.accept(p_79146_);
         } else {
            int i = p_79146_.m_41613_();

            while(i > 0) {
               ItemStack itemstack = p_79146_.m_41777_();
               itemstack.m_41764_(Math.min(p_79146_.m_41741_(), i));
               i -= itemstack.m_41613_();
               p_79143_.accept(itemstack);
            }
         }

      };
   }

   public void m_79131_(LootContext p_79132_, Consumer<ItemStack> p_79133_) {
      if (p_79132_.m_78934_(this)) {
         Consumer<ItemStack> consumer = LootItemFunction.m_80724_(this.f_79111_, p_79133_, p_79132_);

         for(LootPool lootpool : this.f_79109_) {
            lootpool.m_79053_(consumer, p_79132_);
         }

         p_79132_.m_78946_(this);
      } else {
         f_79107_.warn("Detected infinite loop in loot tables");
      }

   }

   @Deprecated //Use other method or manually call ForgeHooks.modifyLoot
   public void m_79148_(LootContext p_79149_, Consumer<ItemStack> p_79150_) {
      this.m_79131_(p_79149_, m_79142_(p_79150_));
   }

   public List<ItemStack> m_79129_(LootContext p_79130_) {
      List<ItemStack> list = Lists.newArrayList();
      this.m_79148_(p_79130_, list::add);
      list = net.minecraftforge.common.ForgeHooks.modifyLoot(this.getLootTableId(), list, p_79130_);
      return list;
   }

   public LootContextParamSet m_79122_() {
      return this.f_79108_;
   }

   public void m_79136_(ValidationContext p_79137_) {
      for(int i = 0; i < this.f_79109_.size(); ++i) {
         this.f_79109_.get(i).m_79051_(p_79137_.m_79365_(".pools[" + i + "]"));
      }

      for(int j = 0; j < this.f_79110_.length; ++j) {
         this.f_79110_[j].m_6169_(p_79137_.m_79365_(".functions[" + j + "]"));
      }

   }

   public void m_79123_(Container p_79124_, LootContext p_79125_) {
      List<ItemStack> list = this.m_79129_(p_79125_);
      Random random = p_79125_.m_78933_();
      List<Integer> list1 = this.m_79126_(p_79124_, random);
      this.m_79138_(list, list1.size(), random);

      for(ItemStack itemstack : list) {
         if (list1.isEmpty()) {
            f_79107_.warn("Tried to over-fill a container");
            return;
         }

         if (itemstack.m_41619_()) {
            p_79124_.m_6836_(list1.remove(list1.size() - 1), ItemStack.f_41583_);
         } else {
            p_79124_.m_6836_(list1.remove(list1.size() - 1), itemstack);
         }
      }

   }

   private void m_79138_(List<ItemStack> p_79139_, int p_79140_, Random p_79141_) {
      List<ItemStack> list = Lists.newArrayList();
      Iterator<ItemStack> iterator = p_79139_.iterator();

      while(iterator.hasNext()) {
         ItemStack itemstack = iterator.next();
         if (itemstack.m_41619_()) {
            iterator.remove();
         } else if (itemstack.m_41613_() > 1) {
            list.add(itemstack);
            iterator.remove();
         }
      }

      while(p_79140_ - p_79139_.size() - list.size() > 0 && !list.isEmpty()) {
         ItemStack itemstack2 = list.remove(Mth.m_14072_(p_79141_, 0, list.size() - 1));
         int i = Mth.m_14072_(p_79141_, 1, itemstack2.m_41613_() / 2);
         ItemStack itemstack1 = itemstack2.m_41620_(i);
         if (itemstack2.m_41613_() > 1 && p_79141_.nextBoolean()) {
            list.add(itemstack2);
         } else {
            p_79139_.add(itemstack2);
         }

         if (itemstack1.m_41613_() > 1 && p_79141_.nextBoolean()) {
            list.add(itemstack1);
         } else {
            p_79139_.add(itemstack1);
         }
      }

      p_79139_.addAll(list);
      Collections.shuffle(p_79139_, p_79141_);
   }

   private List<Integer> m_79126_(Container p_79127_, Random p_79128_) {
      List<Integer> list = Lists.newArrayList();

      for(int i = 0; i < p_79127_.m_6643_(); ++i) {
         if (p_79127_.m_8020_(i).m_41619_()) {
            list.add(i);
         }
      }

      Collections.shuffle(list, p_79128_);
      return list;
   }

   public static LootTable.Builder m_79147_() {
      return new LootTable.Builder();
   }

   //======================== FORGE START =============================================
   private boolean isFrozen = false;
   public void freeze() {
      this.isFrozen = true;
      this.f_79109_.forEach(LootPool::freeze);
   }
   public boolean isFrozen(){ return this.isFrozen; }
   private void checkFrozen() {
      if (this.isFrozen())
         throw new RuntimeException("Attempted to modify LootTable after being finalized!");
   }

   private ResourceLocation lootTableId;
   public void setLootTableId(final ResourceLocation id) {
      if (this.lootTableId != null) throw new IllegalStateException("Attempted to rename loot table from '" + this.lootTableId + "' to '" + id + "': this is not supported");
      this.lootTableId = java.util.Objects.requireNonNull(id);
   }
   public ResourceLocation getLootTableId() { return this.lootTableId; }

   public LootPool getPool(String name) {
      return f_79109_.stream().filter(e -> name.equals(e.getName())).findFirst().orElse(null);
   }

   public LootPool removePool(String name) {
      checkFrozen();
      for (LootPool pool : this.f_79109_) {
         if (name.equals(pool.getName())) {
            this.f_79109_.remove(pool);
            return pool;
         }
      }
      return null;
   }

   public void addPool(LootPool pool) {
      checkFrozen();
      if (f_79109_.stream().anyMatch(e -> e == pool || e.getName() != null && e.getName().equals(pool.getName())))
         throw new RuntimeException("Attempted to add a duplicate pool to loot table: " + pool.getName());
      this.f_79109_.add(pool);
   }
   //======================== FORGE END ===============================================

   public static class Builder implements FunctionUserBuilder<LootTable.Builder> {
      private final List<LootPool> f_79156_ = Lists.newArrayList();
      private final List<LootItemFunction> f_79157_ = Lists.newArrayList();
      private LootContextParamSet f_79158_ = LootTable.f_79106_;

      public LootTable.Builder m_79161_(LootPool.Builder p_79162_) {
         this.f_79156_.add(p_79162_.m_79082_());
         return this;
      }

      public LootTable.Builder m_79165_(LootContextParamSet p_79166_) {
         this.f_79158_ = p_79166_;
         return this;
      }

      public LootTable.Builder m_5577_(LootItemFunction.Builder p_79164_) {
         this.f_79157_.add(p_79164_.m_7453_());
         return this;
      }

      public LootTable.Builder m_5476_() {
         return this;
      }

      public LootTable m_79167_() {
         return new LootTable(this.f_79158_, this.f_79156_.toArray(new LootPool[0]), this.f_79157_.toArray(new LootItemFunction[0]));
      }
   }

   public static class Serializer implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
      public LootTable deserialize(JsonElement p_79173_, Type p_79174_, JsonDeserializationContext p_79175_) throws JsonParseException {
         JsonObject jsonobject = GsonHelper.m_13918_(p_79173_, "loot table");
         LootPool[] alootpool = GsonHelper.m_13845_(jsonobject, "pools", new LootPool[0], p_79175_, LootPool[].class);
         LootContextParamSet lootcontextparamset = null;
         if (jsonobject.has("type")) {
            String s = GsonHelper.m_13906_(jsonobject, "type");
            lootcontextparamset = LootContextParamSets.m_81431_(new ResourceLocation(s));
         }

         LootItemFunction[] alootitemfunction = GsonHelper.m_13845_(jsonobject, "functions", new LootItemFunction[0], p_79175_, LootItemFunction[].class);
         return new LootTable(lootcontextparamset != null ? lootcontextparamset : LootContextParamSets.f_81420_, alootpool, alootitemfunction);
      }

      public JsonElement serialize(LootTable p_79177_, Type p_79178_, JsonSerializationContext p_79179_) {
         JsonObject jsonobject = new JsonObject();
         if (p_79177_.f_79108_ != LootTable.f_79106_) {
            ResourceLocation resourcelocation = LootContextParamSets.m_81426_(p_79177_.f_79108_);
            if (resourcelocation != null) {
               jsonobject.addProperty("type", resourcelocation.toString());
            } else {
               LootTable.f_79107_.warn("Failed to find id for param set {}", (Object)p_79177_.f_79108_);
            }
         }

         if (!p_79177_.f_79109_.isEmpty()) {
            jsonobject.add("pools", p_79179_.serialize(p_79177_.f_79109_));
         }

         if (!ArrayUtils.isEmpty((Object[])p_79177_.f_79110_)) {
            jsonobject.add("functions", p_79179_.serialize(p_79177_.f_79110_));
         }

         return jsonobject;
      }
   }
}
