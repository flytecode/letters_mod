package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTables extends SimpleJsonResourceReloadListener {
   private static final Logger f_79188_ = LogManager.getLogger();
   private static final Gson f_79189_ = Deserializers.m_78800_().create();
   private Map<ResourceLocation, LootTable> f_79190_ = ImmutableMap.of();
   private final PredicateManager f_79191_;

   public LootTables(PredicateManager p_79194_) {
      super(f_79189_, "loot_tables");
      this.f_79191_ = p_79194_;
   }

   public LootTable m_79217_(ResourceLocation p_79218_) {
      return this.f_79190_.getOrDefault(p_79218_, LootTable.f_79105_);
   }

   protected void m_5787_(Map<ResourceLocation, JsonElement> p_79214_, ResourceManager p_79215_, ProfilerFiller p_79216_) {
      Builder<ResourceLocation, LootTable> builder = ImmutableMap.builder();
      JsonElement jsonelement = p_79214_.remove(BuiltInLootTables.f_78712_);
      if (jsonelement != null) {
         f_79188_.warn("Datapack tried to redefine {} loot table, ignoring", (Object)BuiltInLootTables.f_78712_);
      }

      p_79214_.forEach((p_79198_, p_79199_) -> {
         try (net.minecraft.server.packs.resources.Resource res = p_79215_.m_142591_(getPreparedPath(p_79198_));){
            LootTable loottable = net.minecraftforge.common.ForgeHooks.loadLootTable(f_79189_, p_79198_, p_79199_, res == null || !res.m_7816_().equals("Default"), this);
            builder.put(p_79198_, loottable);
         } catch (Exception exception) {
            f_79188_.error("Couldn't parse loot table {}", p_79198_, exception);
         }

      });
      builder.put(BuiltInLootTables.f_78712_, LootTable.f_79105_);
      ImmutableMap<ResourceLocation, LootTable> immutablemap = builder.build();
      ValidationContext validationcontext = new ValidationContext(LootContextParamSets.f_81420_, this.f_79191_::m_79252_, immutablemap::get);
      immutablemap.forEach((p_79221_, p_79222_) -> {
         m_79202_(validationcontext, p_79221_, p_79222_);
      });
      validationcontext.m_79352_().forEach((p_79211_, p_79212_) -> {
         f_79188_.warn("Found validation problem in {}: {}", p_79211_, p_79212_);
      });
      this.f_79190_ = immutablemap;
   }

   public static void m_79202_(ValidationContext p_79203_, ResourceLocation p_79204_, LootTable p_79205_) {
      p_79205_.m_79136_(p_79203_.m_79355_(p_79205_.m_79122_()).m_79359_("{" + p_79204_ + "}", p_79204_));
   }

   public static JsonElement m_79200_(LootTable p_79201_) {
      return f_79189_.toJsonTree(p_79201_);
   }

   public Set<ResourceLocation> m_79195_() {
      return this.f_79190_.keySet();
   }
}
