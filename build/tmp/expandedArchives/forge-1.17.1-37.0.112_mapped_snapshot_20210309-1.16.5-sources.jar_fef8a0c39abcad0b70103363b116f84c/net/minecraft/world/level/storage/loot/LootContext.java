package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootContext {
   private final Random f_78907_;
   private final float f_78908_;
   private final ServerLevel f_78909_;
   private final Function<ResourceLocation, LootTable> f_78910_;
   private final Set<LootTable> f_78911_ = Sets.newLinkedHashSet();
   private final Function<ResourceLocation, LootItemCondition> f_78912_;
   private final Set<LootItemCondition> f_78913_ = Sets.newLinkedHashSet();
   private final Map<LootContextParam<?>, Object> f_78914_;
   private final Map<ResourceLocation, LootContext.DynamicDrop> f_78915_;

   LootContext(Random p_78917_, float p_78918_, ServerLevel p_78919_, Function<ResourceLocation, LootTable> p_78920_, Function<ResourceLocation, LootItemCondition> p_78921_, Map<LootContextParam<?>, Object> p_78922_, Map<ResourceLocation, LootContext.DynamicDrop> p_78923_) {
      this.f_78907_ = p_78917_;
      this.f_78908_ = p_78918_;
      this.f_78909_ = p_78919_;
      this.f_78910_ = p_78920_;
      this.f_78912_ = p_78921_;
      this.f_78914_ = ImmutableMap.copyOf(p_78922_);
      this.f_78915_ = ImmutableMap.copyOf(p_78923_);
   }

   public boolean m_78936_(LootContextParam<?> p_78937_) {
      return this.f_78914_.containsKey(p_78937_);
   }

   public <T> T m_165124_(LootContextParam<T> p_165125_) {
      T t = (T)this.f_78914_.get(p_165125_);
      if (t == null) {
         throw new NoSuchElementException(p_165125_.m_81284_().toString());
      } else {
         return t;
      }
   }

   public void m_78942_(ResourceLocation p_78943_, Consumer<ItemStack> p_78944_) {
      LootContext.DynamicDrop lootcontext$dynamicdrop = this.f_78915_.get(p_78943_);
      if (lootcontext$dynamicdrop != null) {
         lootcontext$dynamicdrop.m_78987_(this, p_78944_);
      }

   }

   @Nullable
   public <T> T m_78953_(LootContextParam<T> p_78954_) {
      return (T)this.f_78914_.get(p_78954_);
   }

   public boolean m_78934_(LootTable p_78935_) {
      return this.f_78911_.add(p_78935_);
   }

   public void m_78946_(LootTable p_78947_) {
      this.f_78911_.remove(p_78947_);
   }

   public boolean m_78938_(LootItemCondition p_78939_) {
      return this.f_78913_.add(p_78939_);
   }

   public void m_78948_(LootItemCondition p_78949_) {
      this.f_78913_.remove(p_78949_);
   }

   public LootTable m_78940_(ResourceLocation p_78941_) {
      return this.f_78910_.apply(p_78941_);
   }

   public LootItemCondition m_78950_(ResourceLocation p_78951_) {
      return this.f_78912_.apply(p_78951_);
   }

   public Random m_78933_() {
      return this.f_78907_;
   }

   public float m_78945_() {
      return this.f_78908_;
   }

   public ServerLevel m_78952_() {
      return this.f_78909_;
   }

   // ============================== FORGE START ==============================
   public int getLootingModifier() {
      return net.minecraftforge.common.ForgeHooks.getLootingLevel(m_78953_(LootContextParams.f_81455_), m_78953_(LootContextParams.f_81458_), m_78953_(LootContextParams.f_81457_));
   }

   private ResourceLocation queriedLootTableId;

   private LootContext(Random rand, float luckIn, ServerLevel worldIn, Function<ResourceLocation, LootTable> lootTableManagerIn, Function<ResourceLocation, LootItemCondition> p_i225885_5_, Map<LootContextParam<?>, Object> parametersIn, Map<ResourceLocation, LootContext.DynamicDrop> conditionsIn, ResourceLocation queriedLootTableId) {
      this(rand, luckIn, worldIn, lootTableManagerIn, p_i225885_5_, parametersIn, conditionsIn);
      if (queriedLootTableId != null) this.queriedLootTableId = queriedLootTableId;
   }

   public void setQueriedLootTableId(ResourceLocation queriedLootTableId) {
      if (this.queriedLootTableId == null && queriedLootTableId != null) this.queriedLootTableId = queriedLootTableId;
   }
   public ResourceLocation getQueriedLootTableId() {
      return this.queriedLootTableId == null? net.minecraftforge.common.loot.LootTableIdCondition.UNKNOWN_LOOT_TABLE : this.queriedLootTableId;
   }
   // =============================== FORGE END ===============================

   public static class Builder {
      private final ServerLevel f_78955_;
      private final Map<LootContextParam<?>, Object> f_78956_ = Maps.newIdentityHashMap();
      private final Map<ResourceLocation, LootContext.DynamicDrop> f_78957_ = Maps.newHashMap();
      private Random f_78958_;
      private float f_78959_;
      private ResourceLocation queriedLootTableId; // Forge: correctly pass around loot table ID with copy constructor

      public Builder(ServerLevel p_78961_) {
         this.f_78955_ = p_78961_;
      }

      public Builder(LootContext context) {
         this.f_78955_ = context.f_78909_;
         this.f_78956_.putAll(context.f_78914_);
         this.f_78957_.putAll(context.f_78915_);
         this.f_78958_ = context.f_78907_;
         this.f_78959_ = context.f_78908_;
         this.queriedLootTableId = context.queriedLootTableId;
      }

      public LootContext.Builder m_78977_(Random p_78978_) {
         this.f_78958_ = p_78978_;
         return this;
      }

      public LootContext.Builder m_78965_(long p_78966_) {
         if (p_78966_ != 0L) {
            this.f_78958_ = new Random(p_78966_);
         }

         return this;
      }

      public LootContext.Builder m_78967_(long p_78968_, Random p_78969_) {
         if (p_78968_ == 0L) {
            this.f_78958_ = p_78969_;
         } else {
            this.f_78958_ = new Random(p_78968_);
         }

         return this;
      }

      public LootContext.Builder m_78963_(float p_78964_) {
         this.f_78959_ = p_78964_;
         return this;
      }

      public <T> LootContext.Builder m_78972_(LootContextParam<T> p_78973_, T p_78974_) {
         this.f_78956_.put(p_78973_, p_78974_);
         return this;
      }

      public <T> LootContext.Builder m_78984_(LootContextParam<T> p_78985_, @Nullable T p_78986_) {
         if (p_78986_ == null) {
            this.f_78956_.remove(p_78985_);
         } else {
            this.f_78956_.put(p_78985_, p_78986_);
         }

         return this;
      }

      public LootContext.Builder m_78979_(ResourceLocation p_78980_, LootContext.DynamicDrop p_78981_) {
         LootContext.DynamicDrop lootcontext$dynamicdrop = this.f_78957_.put(p_78980_, p_78981_);
         if (lootcontext$dynamicdrop != null) {
            throw new IllegalStateException("Duplicated dynamic drop '" + this.f_78957_ + "'");
         } else {
            return this;
         }
      }

      public ServerLevel m_78962_() {
         return this.f_78955_;
      }

      public <T> T m_78970_(LootContextParam<T> p_78971_) {
         T t = (T)this.f_78956_.get(p_78971_);
         if (t == null) {
            throw new IllegalArgumentException("No parameter " + p_78971_);
         } else {
            return t;
         }
      }

      @Nullable
      public <T> T m_78982_(LootContextParam<T> p_78983_) {
         return (T)this.f_78956_.get(p_78983_);
      }

      public LootContext m_78975_(LootContextParamSet p_78976_) {
         Set<LootContextParam<?>> set = Sets.difference(this.f_78956_.keySet(), p_78976_.m_81398_());
         if (false && !set.isEmpty()) { // Forge: Allow mods to pass custom loot parameters (not part of the vanilla loot table) to the loot context.
            throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + set);
         } else {
            Set<LootContextParam<?>> set1 = Sets.difference(p_78976_.m_81394_(), this.f_78956_.keySet());
            if (!set1.isEmpty()) {
               throw new IllegalArgumentException("Missing required parameters: " + set1);
            } else {
               Random random = this.f_78958_;
               if (random == null) {
                  random = new Random();
               }

               MinecraftServer minecraftserver = this.f_78955_.m_142572_();
               return new LootContext(random, this.f_78959_, this.f_78955_, minecraftserver.m_129898_()::m_79217_, minecraftserver.m_129899_()::m_79252_, this.f_78956_, this.f_78957_, this.queriedLootTableId);
            }
         }
      }
   }

   @FunctionalInterface
   public interface DynamicDrop {
      void m_78987_(LootContext p_78988_, Consumer<ItemStack> p_78989_);
   }

   public static enum EntityTarget {
      THIS("this", LootContextParams.f_81455_),
      KILLER("killer", LootContextParams.f_81458_),
      DIRECT_KILLER("direct_killer", LootContextParams.f_81459_),
      KILLER_PLAYER("killer_player", LootContextParams.f_81456_);

      final String f_78994_;
      private final LootContextParam<? extends Entity> f_78995_;

      private EntityTarget(String p_79001_, LootContextParam<? extends Entity> p_79002_) {
         this.f_78994_ = p_79001_;
         this.f_78995_ = p_79002_;
      }

      public LootContextParam<? extends Entity> m_79003_() {
         return this.f_78995_;
      }

      public static LootContext.EntityTarget m_79006_(String p_79007_) {
         for(LootContext.EntityTarget lootcontext$entitytarget : values()) {
            if (lootcontext$entitytarget.f_78994_.equals(p_79007_)) {
               return lootcontext$entitytarget;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + p_79007_);
      }

      public static class Serializer extends TypeAdapter<LootContext.EntityTarget> {
         public void write(JsonWriter p_79015_, LootContext.EntityTarget p_79016_) throws IOException {
            p_79015_.value(p_79016_.f_78994_);
         }

         public LootContext.EntityTarget read(JsonReader p_79013_) throws IOException {
            return LootContext.EntityTarget.m_79006_(p_79013_.nextString());
         }
      }
   }
}
