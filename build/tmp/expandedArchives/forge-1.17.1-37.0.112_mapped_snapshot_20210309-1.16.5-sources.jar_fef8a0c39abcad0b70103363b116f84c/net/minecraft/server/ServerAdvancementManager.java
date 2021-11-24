package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.TreeNodePosition;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.PredicateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerAdvancementManager extends SimpleJsonResourceReloadListener {
   private static final Logger f_136021_ = LogManager.getLogger();
   private static final Gson f_136022_ = (new GsonBuilder()).create();
   private AdvancementList f_136023_ = new AdvancementList();
   private final PredicateManager f_136024_;

   public ServerAdvancementManager(PredicateManager p_136027_) {
      super(f_136022_, "advancements");
      this.f_136024_ = p_136027_;
   }

   protected void m_5787_(Map<ResourceLocation, JsonElement> p_136034_, ResourceManager p_136035_, ProfilerFiller p_136036_) {
      Map<ResourceLocation, Advancement.Builder> map = Maps.newHashMap();
      p_136034_.forEach((p_136039_, p_136040_) -> {
         try {
            JsonObject jsonobject = GsonHelper.m_13918_(p_136040_, "advancement");
            Advancement.Builder advancement$builder = Advancement.Builder.m_138380_(jsonobject, new DeserializationContext(p_136039_, this.f_136024_));
            if (advancement$builder == null) {
                f_136021_.debug("Skipping loading advancement {} as it's conditions were not met", p_136039_);
                return;
            }
            map.put(p_136039_, advancement$builder);
         } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
            f_136021_.error("Parsing error loading custom advancement {}: {}", p_136039_, jsonparseexception.getMessage());
         }

      });
      AdvancementList advancementlist = new AdvancementList();
      advancementlist.m_139333_(map);

      for(Advancement advancement : advancementlist.m_139343_()) {
         if (advancement.m_138320_() != null) {
            TreeNodePosition.m_16587_(advancement);
         }
      }

      this.f_136023_ = advancementlist;
   }

   @Nullable
   public Advancement m_136041_(ResourceLocation p_136042_) {
      return this.f_136023_.m_139337_(p_136042_);
   }

   public Collection<Advancement> m_136028_() {
      return this.f_136023_.m_139344_();
   }
}
