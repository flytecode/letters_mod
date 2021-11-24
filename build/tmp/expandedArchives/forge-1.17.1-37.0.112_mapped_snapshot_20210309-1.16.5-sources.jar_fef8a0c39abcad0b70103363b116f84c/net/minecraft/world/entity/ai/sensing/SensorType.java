package net.minecraft.world.entity.ai.sensing;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.animal.goat.GoatAi;

public class SensorType<U extends Sensor<?>> extends net.minecraftforge.registries.ForgeRegistryEntry<SensorType<?>> {
   public static final SensorType<DummySensor> f_26809_ = m_26828_("dummy", DummySensor::new);
   public static final SensorType<NearestItemSensor> f_26810_ = m_26828_("nearest_items", NearestItemSensor::new);
   public static final SensorType<NearestLivingEntitySensor> f_26811_ = m_26828_("nearest_living_entities", NearestLivingEntitySensor::new);
   public static final SensorType<PlayerSensor> f_26812_ = m_26828_("nearest_players", PlayerSensor::new);
   public static final SensorType<NearestBedSensor> f_26813_ = m_26828_("nearest_bed", NearestBedSensor::new);
   public static final SensorType<HurtBySensor> f_26814_ = m_26828_("hurt_by", HurtBySensor::new);
   public static final SensorType<VillagerHostilesSensor> f_26815_ = m_26828_("villager_hostiles", VillagerHostilesSensor::new);
   public static final SensorType<VillagerBabiesSensor> f_26816_ = m_26828_("villager_babies", VillagerBabiesSensor::new);
   public static final SensorType<SecondaryPoiSensor> f_26817_ = m_26828_("secondary_pois", SecondaryPoiSensor::new);
   public static final SensorType<GolemSensor> f_26818_ = m_26828_("golem_detected", GolemSensor::new);
   public static final SensorType<PiglinSpecificSensor> f_26819_ = m_26828_("piglin_specific_sensor", PiglinSpecificSensor::new);
   public static final SensorType<PiglinBruteSpecificSensor> f_26820_ = m_26828_("piglin_brute_specific_sensor", PiglinBruteSpecificSensor::new);
   public static final SensorType<HoglinSpecificSensor> f_26821_ = m_26828_("hoglin_specific_sensor", HoglinSpecificSensor::new);
   public static final SensorType<AdultSensor> f_26822_ = m_26828_("nearest_adult", AdultSensor::new);
   public static final SensorType<AxolotlAttackablesSensor> f_148315_ = m_26828_("axolotl_attackables", AxolotlAttackablesSensor::new);
   public static final SensorType<TemptingSensor> f_148316_ = m_26828_("axolotl_temptations", () -> {
      return new TemptingSensor(AxolotlAi.m_149287_());
   });
   public static final SensorType<TemptingSensor> f_148317_ = m_26828_("goat_temptations", () -> {
      return new TemptingSensor(GoatAi.m_149444_());
   });
   private final Supplier<U> f_26823_;

   public SensorType(Supplier<U> p_26826_) {
      this.f_26823_ = p_26826_;
   }

   public U m_26827_() {
      return this.f_26823_.get();
   }

   private static <U extends Sensor<?>> SensorType<U> m_26828_(String p_26829_, Supplier<U> p_26830_) {
      return Registry.m_122965_(Registry.f_122872_, new ResourceLocation(p_26829_), new SensorType<>(p_26830_));
   }
}
