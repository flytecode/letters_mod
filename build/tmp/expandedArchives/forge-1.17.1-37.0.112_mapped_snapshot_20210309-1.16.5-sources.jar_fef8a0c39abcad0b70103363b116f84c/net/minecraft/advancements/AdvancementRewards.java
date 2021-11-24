package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class AdvancementRewards {
   public static final AdvancementRewards f_9978_ = new AdvancementRewards(0, new ResourceLocation[0], new ResourceLocation[0], CommandFunction.CacheableFunction.f_77990_);
   private final int f_9979_;
   private final ResourceLocation[] f_9980_;
   private final ResourceLocation[] f_9981_;
   private final CommandFunction.CacheableFunction f_9982_;

   public AdvancementRewards(int p_9985_, ResourceLocation[] p_9986_, ResourceLocation[] p_9987_, CommandFunction.CacheableFunction p_9988_) {
      this.f_9979_ = p_9985_;
      this.f_9980_ = p_9986_;
      this.f_9981_ = p_9987_;
      this.f_9982_ = p_9988_;
   }

   public ResourceLocation[] m_144821_() {
      return this.f_9981_;
   }

   public void m_9989_(ServerPlayer p_9990_) {
      p_9990_.m_6756_(this.f_9979_);
      LootContext lootcontext = (new LootContext.Builder(p_9990_.m_9236_())).m_78972_(LootContextParams.f_81455_, p_9990_).m_78972_(LootContextParams.f_81460_, p_9990_.m_20182_()).m_78977_(p_9990_.m_21187_()).m_78963_(p_9990_.m_36336_()).m_78975_(LootContextParamSets.f_81418_); // FORGE: luck to LootContext
      boolean flag = false;

      for(ResourceLocation resourcelocation : this.f_9980_) {
         for(ItemStack itemstack : p_9990_.f_8924_.m_129898_().m_79217_(resourcelocation).m_79129_(lootcontext)) {
            if (p_9990_.m_36356_(itemstack)) {
               p_9990_.f_19853_.m_6263_((Player)null, p_9990_.m_20185_(), p_9990_.m_20186_(), p_9990_.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2F, ((p_9990_.m_21187_().nextFloat() - p_9990_.m_21187_().nextFloat()) * 0.7F + 1.0F) * 2.0F);
               flag = true;
            } else {
               ItemEntity itementity = p_9990_.m_36176_(itemstack, false);
               if (itementity != null) {
                  itementity.m_32061_();
                  itementity.m_32047_(p_9990_.m_142081_());
               }
            }
         }
      }

      if (flag) {
         p_9990_.f_36096_.m_38946_();
      }

      if (this.f_9981_.length > 0) {
         p_9990_.m_7902_(this.f_9981_);
      }

      MinecraftServer minecraftserver = p_9990_.f_8924_;
      this.f_9982_.m_78002_(minecraftserver.m_129890_()).ifPresent((p_9996_) -> {
         minecraftserver.m_129890_().m_136112_(p_9996_, p_9990_.m_20203_().m_81324_().m_81325_(2));
      });
   }

   public String toString() {
      return "AdvancementRewards{experience=" + this.f_9979_ + ", loot=" + Arrays.toString((Object[])this.f_9980_) + ", recipes=" + Arrays.toString((Object[])this.f_9981_) + ", function=" + this.f_9982_ + "}";
   }

   public JsonElement m_9997_() {
      if (this == f_9978_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.f_9979_ != 0) {
            jsonobject.addProperty("experience", this.f_9979_);
         }

         if (this.f_9980_.length > 0) {
            JsonArray jsonarray = new JsonArray();

            for(ResourceLocation resourcelocation : this.f_9980_) {
               jsonarray.add(resourcelocation.toString());
            }

            jsonobject.add("loot", jsonarray);
         }

         if (this.f_9981_.length > 0) {
            JsonArray jsonarray1 = new JsonArray();

            for(ResourceLocation resourcelocation1 : this.f_9981_) {
               jsonarray1.add(resourcelocation1.toString());
            }

            jsonobject.add("recipes", jsonarray1);
         }

         if (this.f_9982_.m_77999_() != null) {
            jsonobject.addProperty("function", this.f_9982_.m_77999_().toString());
         }

         return jsonobject;
      }
   }

   public static AdvancementRewards m_9991_(JsonObject p_9992_) throws JsonParseException {
      int i = GsonHelper.m_13824_(p_9992_, "experience", 0);
      JsonArray jsonarray = GsonHelper.m_13832_(p_9992_, "loot", new JsonArray());
      ResourceLocation[] aresourcelocation = new ResourceLocation[jsonarray.size()];

      for(int j = 0; j < aresourcelocation.length; ++j) {
         aresourcelocation[j] = new ResourceLocation(GsonHelper.m_13805_(jsonarray.get(j), "loot[" + j + "]"));
      }

      JsonArray jsonarray1 = GsonHelper.m_13832_(p_9992_, "recipes", new JsonArray());
      ResourceLocation[] aresourcelocation1 = new ResourceLocation[jsonarray1.size()];

      for(int k = 0; k < aresourcelocation1.length; ++k) {
         aresourcelocation1[k] = new ResourceLocation(GsonHelper.m_13805_(jsonarray1.get(k), "recipes[" + k + "]"));
      }

      CommandFunction.CacheableFunction commandfunction$cacheablefunction;
      if (p_9992_.has("function")) {
         commandfunction$cacheablefunction = new CommandFunction.CacheableFunction(new ResourceLocation(GsonHelper.m_13906_(p_9992_, "function")));
      } else {
         commandfunction$cacheablefunction = CommandFunction.CacheableFunction.f_77990_;
      }

      return new AdvancementRewards(i, aresourcelocation, aresourcelocation1, commandfunction$cacheablefunction);
   }

   public static class Builder {
      private int f_9999_;
      private final List<ResourceLocation> f_10000_ = Lists.newArrayList();
      private final List<ResourceLocation> f_10001_ = Lists.newArrayList();
      @Nullable
      private ResourceLocation f_10002_;

      public static AdvancementRewards.Builder m_10005_(int p_10006_) {
         return (new AdvancementRewards.Builder()).m_10007_(p_10006_);
      }

      public AdvancementRewards.Builder m_10007_(int p_10008_) {
         this.f_9999_ += p_10008_;
         return this;
      }

      public static AdvancementRewards.Builder m_144822_(ResourceLocation p_144823_) {
         return (new AdvancementRewards.Builder()).m_144824_(p_144823_);
      }

      public AdvancementRewards.Builder m_144824_(ResourceLocation p_144825_) {
         this.f_10000_.add(p_144825_);
         return this;
      }

      public static AdvancementRewards.Builder m_10009_(ResourceLocation p_10010_) {
         return (new AdvancementRewards.Builder()).m_10011_(p_10010_);
      }

      public AdvancementRewards.Builder m_10011_(ResourceLocation p_10012_) {
         this.f_10001_.add(p_10012_);
         return this;
      }

      public static AdvancementRewards.Builder m_144826_(ResourceLocation p_144827_) {
         return (new AdvancementRewards.Builder()).m_144828_(p_144827_);
      }

      public AdvancementRewards.Builder m_144828_(ResourceLocation p_144829_) {
         this.f_10002_ = p_144829_;
         return this;
      }

      public AdvancementRewards m_10004_() {
         return new AdvancementRewards(this.f_9999_, this.f_10000_.toArray(new ResourceLocation[0]), this.f_10001_.toArray(new ResourceLocation[0]), this.f_10002_ == null ? CommandFunction.CacheableFunction.f_77990_ : new CommandFunction.CacheableFunction(this.f_10002_));
      }
   }
}
