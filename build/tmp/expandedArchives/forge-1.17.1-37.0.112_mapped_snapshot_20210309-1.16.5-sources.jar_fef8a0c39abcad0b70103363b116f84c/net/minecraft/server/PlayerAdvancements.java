package net.minecraft.server;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerAdvancements {
   private static final Logger f_135958_ = LogManager.getLogger();
   private static final int f_179926_ = 2;
   private static final Gson f_135959_ = (new GsonBuilder()).registerTypeAdapter(AdvancementProgress.class, new AdvancementProgress.Serializer()).registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).setPrettyPrinting().create();
   private static final TypeToken<Map<ResourceLocation, AdvancementProgress>> f_135960_ = new TypeToken<Map<ResourceLocation, AdvancementProgress>>() {
   };
   private final DataFixer f_135961_;
   private final PlayerList f_135962_;
   private final File f_135963_;
   private final Map<Advancement, AdvancementProgress> f_135964_ = Maps.newLinkedHashMap();
   private final Set<Advancement> f_135965_ = Sets.newLinkedHashSet();
   private final Set<Advancement> f_135966_ = Sets.newLinkedHashSet();
   private final Set<Advancement> f_135967_ = Sets.newLinkedHashSet();
   private ServerPlayer f_135968_;
   @Nullable
   private Advancement f_135969_;
   private boolean f_135970_ = true;

   public PlayerAdvancements(DataFixer p_135973_, PlayerList p_135974_, ServerAdvancementManager p_135975_, File p_135976_, ServerPlayer p_135977_) {
      this.f_135961_ = p_135973_;
      this.f_135962_ = p_135974_;
      this.f_135963_ = p_135976_;
      this.f_135968_ = p_135977_;
      this.m_136006_(p_135975_);
   }

   public void m_135979_(ServerPlayer p_135980_) {
      this.f_135968_ = p_135980_;
   }

   public void m_135978_() {
      for(CriterionTrigger<?> criteriontrigger : CriteriaTriggers.m_10594_()) {
         criteriontrigger.m_5656_(this);
      }

   }

   public void m_135981_(ServerAdvancementManager p_135982_) {
      this.m_135978_();
      this.f_135964_.clear();
      this.f_135965_.clear();
      this.f_135966_.clear();
      this.f_135967_.clear();
      this.f_135970_ = true;
      this.f_135969_ = null;
      this.m_136006_(p_135982_);
   }

   private void m_135994_(ServerAdvancementManager p_135995_) {
      for(Advancement advancement : p_135995_.m_136028_()) {
         this.m_136004_(advancement);
      }

   }

   private void m_136001_() {
      List<Advancement> list = Lists.newArrayList();

      for(Entry<Advancement, AdvancementProgress> entry : this.f_135964_.entrySet()) {
         if (entry.getValue().m_8193_()) {
            list.add(entry.getKey());
            this.f_135967_.add(entry.getKey());
         }
      }

      for(Advancement advancement : list) {
         this.m_136010_(advancement);
      }

   }

   private void m_136002_(ServerAdvancementManager p_136003_) {
      for(Advancement advancement : p_136003_.m_136028_()) {
         if (advancement.m_138325_().isEmpty()) {
            this.m_135988_(advancement, "");
            advancement.m_138321_().m_9989_(this.f_135968_);
         }
      }

   }

   private void m_136006_(ServerAdvancementManager p_136007_) {
      if (this.f_135963_.isFile()) {
         try {
            JsonReader jsonreader = new JsonReader(new StringReader(Files.toString(this.f_135963_, StandardCharsets.UTF_8)));

            try {
               jsonreader.setLenient(false);
               Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, Streams.parse(jsonreader));
               if (!dynamic.get("DataVersion").asNumber().result().isPresent()) {
                  dynamic = dynamic.set("DataVersion", dynamic.createInt(1343));
               }

               dynamic = this.f_135961_.update(DataFixTypes.ADVANCEMENTS.m_14504_(), dynamic, dynamic.get("DataVersion").asInt(0), SharedConstants.m_136187_().getWorldVersion());
               dynamic = dynamic.remove("DataVersion");
               Map<ResourceLocation, AdvancementProgress> map = f_135959_.getAdapter(f_135960_).fromJsonTree(dynamic.getValue());
               if (map == null) {
                  throw new JsonParseException("Found null for advancements");
               }

               Stream<Entry<ResourceLocation, AdvancementProgress>> stream = map.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));

               for(Entry<ResourceLocation, AdvancementProgress> entry : stream.collect(Collectors.toList())) {
                  Advancement advancement = p_136007_.m_136041_(entry.getKey());
                  if (advancement == null) {
                     f_135958_.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", entry.getKey(), this.f_135963_);
                  } else {
                     this.m_135985_(advancement, entry.getValue());
                  }
               }
            } catch (Throwable throwable1) {
               try {
                  jsonreader.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }

               throw throwable1;
            }

            jsonreader.close();
         } catch (JsonParseException jsonparseexception) {
            f_135958_.error("Couldn't parse player advancements in {}", this.f_135963_, jsonparseexception);
         } catch (IOException ioexception) {
            f_135958_.error("Couldn't access player advancements in {}", this.f_135963_, ioexception);
         }
      }

      this.m_136002_(p_136007_);

      if (net.minecraftforge.common.ForgeConfig.SERVER.fixAdvancementLoading.get())
         net.minecraftforge.common.AdvancementLoadFix.loadVisibility(this, this.f_135965_, this.f_135966_, this.f_135964_, this.f_135967_, this::m_136012_);
      else
      this.m_136001_();
      this.m_135994_(p_136007_);
   }

   public void m_135991_() {
      Map<ResourceLocation, AdvancementProgress> map = Maps.newHashMap();

      for(Entry<Advancement, AdvancementProgress> entry : this.f_135964_.entrySet()) {
         AdvancementProgress advancementprogress = entry.getValue();
         if (advancementprogress.m_8206_()) {
            map.put(entry.getKey().m_138327_(), advancementprogress);
         }
      }

      if (this.f_135963_.getParentFile() != null) {
         this.f_135963_.getParentFile().mkdirs();
      }

      JsonElement jsonelement = f_135959_.toJsonTree(map);
      jsonelement.getAsJsonObject().addProperty("DataVersion", SharedConstants.m_136187_().getWorldVersion());

      try {
         OutputStream outputstream = new FileOutputStream(this.f_135963_);

         try {
            Writer writer = new OutputStreamWriter(outputstream, Charsets.UTF_8.newEncoder());

            try {
               f_135959_.toJson(jsonelement, writer);
            } catch (Throwable throwable2) {
               try {
                  writer.close();
               } catch (Throwable throwable1) {
                  throwable2.addSuppressed(throwable1);
               }

               throw throwable2;
            }

            writer.close();
         } catch (Throwable throwable3) {
            try {
               outputstream.close();
            } catch (Throwable throwable) {
               throwable3.addSuppressed(throwable);
            }

            throw throwable3;
         }

         outputstream.close();
      } catch (IOException ioexception) {
         f_135958_.error("Couldn't save player advancements to {}", this.f_135963_, ioexception);
      }

   }

   public boolean m_135988_(Advancement p_135989_, String p_135990_) {
      // Forge: don't grant advancements for fake players
      if (this.f_135968_ instanceof net.minecraftforge.common.util.FakePlayer) return false;
      boolean flag = false;
      AdvancementProgress advancementprogress = this.m_135996_(p_135989_);
      boolean flag1 = advancementprogress.m_8193_();
      if (advancementprogress.m_8196_(p_135990_)) {
         this.m_136008_(p_135989_);
         this.f_135967_.add(p_135989_);
         flag = true;
         if (!flag1 && advancementprogress.m_8193_()) {
            p_135989_.m_138321_().m_9989_(this.f_135968_);
            if (p_135989_.m_138320_() != null && p_135989_.m_138320_().m_14996_() && this.f_135968_.f_19853_.m_46469_().m_46207_(GameRules.f_46153_)) {
               this.f_135962_.m_11264_(new TranslatableComponent("chat.type.advancement." + p_135989_.m_138320_().m_14992_().m_15548_(), this.f_135968_.m_5446_(), p_135989_.m_138330_()), ChatType.SYSTEM, Util.f_137441_);
            }
            net.minecraftforge.common.ForgeHooks.onAdvancement(this.f_135968_, p_135989_);
         }
      }

      if (advancementprogress.m_8193_()) {
         this.m_136010_(p_135989_);
      }

      return flag;
   }

   public boolean m_135998_(Advancement p_135999_, String p_136000_) {
      boolean flag = false;
      AdvancementProgress advancementprogress = this.m_135996_(p_135999_);
      if (advancementprogress.m_8209_(p_136000_)) {
         this.m_136004_(p_135999_);
         this.f_135967_.add(p_135999_);
         flag = true;
      }

      if (!advancementprogress.m_8206_()) {
         this.m_136010_(p_135999_);
      }

      return flag;
   }

   private void m_136004_(Advancement p_136005_) {
      AdvancementProgress advancementprogress = this.m_135996_(p_136005_);
      if (!advancementprogress.m_8193_()) {
         for(Entry<String, Criterion> entry : p_136005_.m_138325_().entrySet()) {
            CriterionProgress criterionprogress = advancementprogress.m_8214_(entry.getKey());
            if (criterionprogress != null && !criterionprogress.m_12911_()) {
               CriterionTriggerInstance criteriontriggerinstance = entry.getValue().m_11416_();
               if (criteriontriggerinstance != null) {
                  CriterionTrigger<CriterionTriggerInstance> criteriontrigger = CriteriaTriggers.m_10597_(criteriontriggerinstance.m_7294_());
                  if (criteriontrigger != null) {
                     criteriontrigger.m_6467_(this, new CriterionTrigger.Listener<>(criteriontriggerinstance, p_136005_, entry.getKey()));
                  }
               }
            }
         }

      }
   }

   private void m_136008_(Advancement p_136009_) {
      AdvancementProgress advancementprogress = this.m_135996_(p_136009_);

      for(Entry<String, Criterion> entry : p_136009_.m_138325_().entrySet()) {
         CriterionProgress criterionprogress = advancementprogress.m_8214_(entry.getKey());
         if (criterionprogress != null && (criterionprogress.m_12911_() || advancementprogress.m_8193_())) {
            CriterionTriggerInstance criteriontriggerinstance = entry.getValue().m_11416_();
            if (criteriontriggerinstance != null) {
               CriterionTrigger<CriterionTriggerInstance> criteriontrigger = CriteriaTriggers.m_10597_(criteriontriggerinstance.m_7294_());
               if (criteriontrigger != null) {
                  criteriontrigger.m_6468_(this, new CriterionTrigger.Listener<>(criteriontriggerinstance, p_136009_, entry.getKey()));
               }
            }
         }
      }

   }

   public void m_135992_(ServerPlayer p_135993_) {
      if (this.f_135970_ || !this.f_135966_.isEmpty() || !this.f_135967_.isEmpty()) {
         Map<ResourceLocation, AdvancementProgress> map = Maps.newHashMap();
         Set<Advancement> set = Sets.newLinkedHashSet();
         Set<ResourceLocation> set1 = Sets.newLinkedHashSet();

         for(Advancement advancement : this.f_135967_) {
            if (this.f_135965_.contains(advancement)) {
               map.put(advancement.m_138327_(), this.f_135964_.get(advancement));
            }
         }

         for(Advancement advancement1 : this.f_135966_) {
            if (this.f_135965_.contains(advancement1)) {
               set.add(advancement1);
            } else {
               set1.add(advancement1.m_138327_());
            }
         }

         if (this.f_135970_ || !map.isEmpty() || !set.isEmpty() || !set1.isEmpty()) {
            p_135993_.f_8906_.m_141995_(new ClientboundUpdateAdvancementsPacket(this.f_135970_, set, set1, map));
            this.f_135966_.clear();
            this.f_135967_.clear();
         }
      }

      this.f_135970_ = false;
   }

   public void m_135983_(@Nullable Advancement p_135984_) {
      Advancement advancement = this.f_135969_;
      if (p_135984_ != null && p_135984_.m_138319_() == null && p_135984_.m_138320_() != null) {
         this.f_135969_ = p_135984_;
      } else {
         this.f_135969_ = null;
      }

      if (advancement != this.f_135969_) {
         this.f_135968_.f_8906_.m_141995_(new ClientboundSelectAdvancementsTabPacket(this.f_135969_ == null ? null : this.f_135969_.m_138327_()));
      }

   }

   public AdvancementProgress m_135996_(Advancement p_135997_) {
      AdvancementProgress advancementprogress = this.f_135964_.get(p_135997_);
      if (advancementprogress == null) {
         advancementprogress = new AdvancementProgress();
         this.m_135985_(p_135997_, advancementprogress);
      }

      return advancementprogress;
   }

   private void m_135985_(Advancement p_135986_, AdvancementProgress p_135987_) {
      p_135987_.m_8198_(p_135986_.m_138325_(), p_135986_.m_138329_());
      this.f_135964_.put(p_135986_, p_135987_);
   }

   private void m_136010_(Advancement p_136011_) {
      boolean flag = this.m_136012_(p_136011_);
      boolean flag1 = this.f_135965_.contains(p_136011_);
      if (flag && !flag1) {
         this.f_135965_.add(p_136011_);
         this.f_135966_.add(p_136011_);
         if (this.f_135964_.containsKey(p_136011_)) {
            this.f_135967_.add(p_136011_);
         }
      } else if (!flag && flag1) {
         this.f_135965_.remove(p_136011_);
         this.f_135966_.add(p_136011_);
      }

      if (flag != flag1 && p_136011_.m_138319_() != null) {
         this.m_136010_(p_136011_.m_138319_());
      }

      for(Advancement advancement : p_136011_.m_138322_()) {
         this.m_136010_(advancement);
      }

   }

   private boolean m_136012_(Advancement p_136013_) {
      for(int i = 0; p_136013_ != null && i <= 2; ++i) {
         if (i == 0 && this.m_136014_(p_136013_)) {
            return true;
         }

         if (p_136013_.m_138320_() == null) {
            return false;
         }

         AdvancementProgress advancementprogress = this.m_135996_(p_136013_);
         if (advancementprogress.m_8193_()) {
            return true;
         }

         if (p_136013_.m_138320_().m_14997_()) {
            return false;
         }

         p_136013_ = p_136013_.m_138319_();
      }

      return false;
   }

   private boolean m_136014_(Advancement p_136015_) {
      AdvancementProgress advancementprogress = this.m_135996_(p_136015_);
      if (advancementprogress.m_8193_()) {
         return true;
      } else {
         for(Advancement advancement : p_136015_.m_138322_()) {
            if (this.m_136014_(advancement)) {
               return true;
            }
         }

         return false;
      }
   }
}
