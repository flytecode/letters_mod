package net.minecraft.world.level.storage.loot.parameters;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public class LootContextParamSets {
   private static final BiMap<ResourceLocation, LootContextParamSet> f_81422_ = HashBiMap.create();
   public static final LootContextParamSet f_81410_ = m_81428_("empty", (p_81454_) -> {
   });
   public static final LootContextParamSet f_81411_ = m_81428_("chest", (p_81452_) -> {
      p_81452_.m_81406_(LootContextParams.f_81460_).m_81408_(LootContextParams.f_81455_);
      p_81452_.m_81408_(LootContextParams.f_81458_); //Forge: Chest Minecarts can have killers.
   });
   public static final LootContextParamSet f_81412_ = m_81428_("command", (p_81450_) -> {
      p_81450_.m_81406_(LootContextParams.f_81460_).m_81408_(LootContextParams.f_81455_);
   });
   public static final LootContextParamSet f_81413_ = m_81428_("selector", (p_81448_) -> {
      p_81448_.m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81455_);
   });
   public static final LootContextParamSet f_81414_ = m_81428_("fishing", (p_81446_) -> {
      p_81446_.m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81463_).m_81408_(LootContextParams.f_81455_);
      p_81446_.m_81408_(LootContextParams.f_81458_).m_81408_(LootContextParams.f_81455_); //Forge: Allow fisher, and bobber
   });
   public static final LootContextParamSet f_81415_ = m_81428_("entity", (p_81444_) -> {
      p_81444_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81457_).m_81408_(LootContextParams.f_81458_).m_81408_(LootContextParams.f_81459_).m_81408_(LootContextParams.f_81456_);
   });
   public static final LootContextParamSet f_81416_ = m_81428_("gift", (p_81442_) -> {
      p_81442_.m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81455_);
   });
   public static final LootContextParamSet f_81417_ = m_81428_("barter", (p_81440_) -> {
      p_81440_.m_81406_(LootContextParams.f_81455_);
   });
   public static final LootContextParamSet f_81418_ = m_81428_("advancement_reward", (p_81438_) -> {
      p_81438_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81460_);
   });
   public static final LootContextParamSet f_81419_ = m_81428_("advancement_entity", (p_81436_) -> {
      p_81436_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81460_);
   });
   public static final LootContextParamSet f_81420_ = m_81428_("generic", (p_81434_) -> {
      p_81434_.m_81406_(LootContextParams.f_81455_).m_81406_(LootContextParams.f_81456_).m_81406_(LootContextParams.f_81457_).m_81406_(LootContextParams.f_81458_).m_81406_(LootContextParams.f_81459_).m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81461_).m_81406_(LootContextParams.f_81462_).m_81406_(LootContextParams.f_81463_).m_81406_(LootContextParams.f_81464_);
   });
   public static final LootContextParamSet f_81421_ = m_81428_("block", (p_81425_) -> {
      p_81425_.m_81406_(LootContextParams.f_81461_).m_81406_(LootContextParams.f_81460_).m_81406_(LootContextParams.f_81463_).m_81408_(LootContextParams.f_81455_).m_81408_(LootContextParams.f_81462_).m_81408_(LootContextParams.f_81464_);
   });

   private static LootContextParamSet m_81428_(String p_81429_, Consumer<LootContextParamSet.Builder> p_81430_) {
      LootContextParamSet.Builder lootcontextparamset$builder = new LootContextParamSet.Builder();
      p_81430_.accept(lootcontextparamset$builder);
      LootContextParamSet lootcontextparamset = lootcontextparamset$builder.m_81405_();
      ResourceLocation resourcelocation = new ResourceLocation(p_81429_);
      LootContextParamSet lootcontextparamset1 = f_81422_.put(resourcelocation, lootcontextparamset);
      if (lootcontextparamset1 != null) {
         throw new IllegalStateException("Loot table parameter set " + resourcelocation + " is already registered");
      } else {
         return lootcontextparamset;
      }
   }

   @Nullable
   public static LootContextParamSet m_81431_(ResourceLocation p_81432_) {
      return f_81422_.get(p_81432_);
   }

   @Nullable
   public static ResourceLocation m_81426_(LootContextParamSet p_81427_) {
      return f_81422_.inverse().get(p_81427_);
   }
}
