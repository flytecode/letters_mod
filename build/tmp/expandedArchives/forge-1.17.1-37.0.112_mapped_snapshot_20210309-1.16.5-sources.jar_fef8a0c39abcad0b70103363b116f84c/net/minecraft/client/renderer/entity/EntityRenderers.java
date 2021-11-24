package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class EntityRenderers {
   private static final Logger f_174030_ = LogManager.getLogger();
   public static final String f_174029_ = "default";
   private static final Map<EntityType<?>, EntityRendererProvider<?>> f_174031_ = Maps.newHashMap();
   private static final Map<String, EntityRendererProvider<AbstractClientPlayer>> f_174032_ = ImmutableMap.of("default", (p_174098_) -> {
      return new PlayerRenderer(p_174098_, false);
   }, "slim", (p_174096_) -> {
      return new PlayerRenderer(p_174096_, true);
   });

   public static <T extends Entity> void m_174036_(EntityType<? extends T> p_174037_, EntityRendererProvider<T> p_174038_) {
      f_174031_.put(p_174037_, p_174038_);
   }

   public static Map<EntityType<?>, EntityRenderer<?>> m_174049_(EntityRendererProvider.Context p_174050_) {
      Builder<EntityType<?>, EntityRenderer<?>> builder = ImmutableMap.builder();
      f_174031_.forEach((p_174042_, p_174043_) -> {
         try {
            builder.put(p_174042_, p_174043_.m_174009_(p_174050_));
         } catch (Exception exception) {
            throw new IllegalArgumentException("Failed to create model for " + Registry.f_122826_.m_7981_(p_174042_), exception);
         }
      });
      return builder.build();
   }

   public static Map<String, EntityRenderer<? extends Player>> m_174051_(EntityRendererProvider.Context p_174052_) {
      Builder<String, EntityRenderer<? extends Player>> builder = ImmutableMap.builder();
      f_174032_.forEach((p_174047_, p_174048_) -> {
         try {
            builder.put(p_174047_, p_174048_.m_174009_(p_174052_));
         } catch (Exception exception) {
            throw new IllegalArgumentException("Failed to create player model for " + p_174047_, exception);
         }
      });
      return builder.build();
   }

   public static boolean m_174035_() {
      boolean flag = true;

      for(EntityType<?> entitytype : Registry.f_122826_) {
         if (entitytype != EntityType.f_20532_ && !f_174031_.containsKey(entitytype)) {
            f_174030_.warn("No renderer registered for {}", (Object)Registry.f_122826_.m_7981_(entitytype));
            flag = false;
         }
      }

      return !flag;
   }

   static {
      m_174036_(EntityType.f_20476_, NoopRenderer::new);
      m_174036_(EntityType.f_20529_, ArmorStandRenderer::new);
      m_174036_(EntityType.f_20548_, TippableArrowRenderer::new);
      m_174036_(EntityType.f_147039_, AxolotlRenderer::new);
      m_174036_(EntityType.f_20549_, BatRenderer::new);
      m_174036_(EntityType.f_20550_, BeeRenderer::new);
      m_174036_(EntityType.f_20551_, BlazeRenderer::new);
      m_174036_(EntityType.f_20552_, BoatRenderer::new);
      m_174036_(EntityType.f_20553_, CatRenderer::new);
      m_174036_(EntityType.f_20554_, CaveSpiderRenderer::new);
      m_174036_(EntityType.f_20470_, (p_174094_) -> {
         return new MinecartRenderer<>(p_174094_, ModelLayers.f_171276_);
      });
      m_174036_(EntityType.f_20555_, ChickenRenderer::new);
      m_174036_(EntityType.f_20556_, CodRenderer::new);
      m_174036_(EntityType.f_20471_, (p_174092_) -> {
         return new MinecartRenderer<>(p_174092_, ModelLayers.f_171279_);
      });
      m_174036_(EntityType.f_20557_, CowRenderer::new);
      m_174036_(EntityType.f_20558_, CreeperRenderer::new);
      m_174036_(EntityType.f_20559_, DolphinRenderer::new);
      m_174036_(EntityType.f_20560_, (p_174090_) -> {
         return new ChestedHorseRenderer<>(p_174090_, 0.87F, ModelLayers.f_171132_);
      });
      m_174036_(EntityType.f_20561_, DragonFireballRenderer::new);
      m_174036_(EntityType.f_20562_, DrownedRenderer::new);
      m_174036_(EntityType.f_20483_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20563_, ElderGuardianRenderer::new);
      m_174036_(EntityType.f_20566_, EndermanRenderer::new);
      m_174036_(EntityType.f_20567_, EndermiteRenderer::new);
      m_174036_(EntityType.f_20565_, EnderDragonRenderer::new);
      m_174036_(EntityType.f_20484_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20564_, EndCrystalRenderer::new);
      m_174036_(EntityType.f_20568_, EvokerRenderer::new);
      m_174036_(EntityType.f_20569_, EvokerFangsRenderer::new);
      m_174036_(EntityType.f_20485_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20570_, ExperienceOrbRenderer::new);
      m_174036_(EntityType.f_20571_, (p_174088_) -> {
         return new ThrownItemRenderer<>(p_174088_, 1.0F, true);
      });
      m_174036_(EntityType.f_20450_, FallingBlockRenderer::new);
      m_174036_(EntityType.f_20463_, (p_174086_) -> {
         return new ThrownItemRenderer<>(p_174086_, 3.0F, true);
      });
      m_174036_(EntityType.f_20451_, FireworkEntityRenderer::new);
      m_174036_(EntityType.f_20533_, FishingHookRenderer::new);
      m_174036_(EntityType.f_20452_, FoxRenderer::new);
      m_174036_(EntityType.f_20472_, (p_174084_) -> {
         return new MinecartRenderer<>(p_174084_, ModelLayers.f_171149_);
      });
      m_174036_(EntityType.f_20453_, GhastRenderer::new);
      m_174036_(EntityType.f_20454_, (p_174082_) -> {
         return new GiantMobRenderer(p_174082_, 6.0F);
      });
      m_174036_(EntityType.f_147033_, ItemFrameRenderer::new);
      m_174036_(EntityType.f_147034_, (p_174080_) -> {
         return new GlowSquidRenderer(p_174080_, new SquidModel<>(p_174080_.m_174023_(ModelLayers.f_171154_)));
      });
      m_174036_(EntityType.f_147035_, GoatRenderer::new);
      m_174036_(EntityType.f_20455_, GuardianRenderer::new);
      m_174036_(EntityType.f_20456_, HoglinRenderer::new);
      m_174036_(EntityType.f_20473_, (p_174078_) -> {
         return new MinecartRenderer<>(p_174078_, ModelLayers.f_171185_);
      });
      m_174036_(EntityType.f_20457_, HorseRenderer::new);
      m_174036_(EntityType.f_20458_, HuskRenderer::new);
      m_174036_(EntityType.f_20459_, IllusionerRenderer::new);
      m_174036_(EntityType.f_20460_, IronGolemRenderer::new);
      m_174036_(EntityType.f_20461_, ItemEntityRenderer::new);
      m_174036_(EntityType.f_20462_, ItemFrameRenderer::new);
      m_174036_(EntityType.f_20464_, LeashKnotRenderer::new);
      m_174036_(EntityType.f_20465_, LightningBoltRenderer::new);
      m_174036_(EntityType.f_20466_, (p_174076_) -> {
         return new LlamaRenderer(p_174076_, ModelLayers.f_171194_);
      });
      m_174036_(EntityType.f_20467_, LlamaSpitRenderer::new);
      m_174036_(EntityType.f_20468_, MagmaCubeRenderer::new);
      m_174036_(EntityType.f_147036_, NoopRenderer::new);
      m_174036_(EntityType.f_20469_, (p_174074_) -> {
         return new MinecartRenderer<>(p_174074_, ModelLayers.f_171198_);
      });
      m_174036_(EntityType.f_20504_, MushroomCowRenderer::new);
      m_174036_(EntityType.f_20503_, (p_174072_) -> {
         return new ChestedHorseRenderer<>(p_174072_, 0.92F, ModelLayers.f_171200_);
      });
      m_174036_(EntityType.f_20505_, OcelotRenderer::new);
      m_174036_(EntityType.f_20506_, PaintingRenderer::new);
      m_174036_(EntityType.f_20507_, PandaRenderer::new);
      m_174036_(EntityType.f_20508_, ParrotRenderer::new);
      m_174036_(EntityType.f_20509_, PhantomRenderer::new);
      m_174036_(EntityType.f_20510_, PigRenderer::new);
      m_174036_(EntityType.f_20511_, (p_174070_) -> {
         return new PiglinRenderer(p_174070_, ModelLayers.f_171206_, ModelLayers.f_171158_, ModelLayers.f_171159_, false);
      });
      m_174036_(EntityType.f_20512_, (p_174068_) -> {
         return new PiglinRenderer(p_174068_, ModelLayers.f_171207_, ModelLayers.f_171156_, ModelLayers.f_171157_, false);
      });
      m_174036_(EntityType.f_20513_, PillagerRenderer::new);
      m_174036_(EntityType.f_20514_, PolarBearRenderer::new);
      m_174036_(EntityType.f_20486_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20516_, PufferfishRenderer::new);
      m_174036_(EntityType.f_20517_, RabbitRenderer::new);
      m_174036_(EntityType.f_20518_, RavagerRenderer::new);
      m_174036_(EntityType.f_20519_, SalmonRenderer::new);
      m_174036_(EntityType.f_20520_, SheepRenderer::new);
      m_174036_(EntityType.f_20521_, ShulkerRenderer::new);
      m_174036_(EntityType.f_20522_, ShulkerBulletRenderer::new);
      m_174036_(EntityType.f_20523_, SilverfishRenderer::new);
      m_174036_(EntityType.f_20524_, SkeletonRenderer::new);
      m_174036_(EntityType.f_20525_, (p_174066_) -> {
         return new UndeadHorseRenderer(p_174066_, ModelLayers.f_171237_);
      });
      m_174036_(EntityType.f_20526_, SlimeRenderer::new);
      m_174036_(EntityType.f_20527_, (p_174064_) -> {
         return new ThrownItemRenderer<>(p_174064_, 0.75F, true);
      });
      m_174036_(EntityType.f_20477_, ThrownItemRenderer::new);
      m_174036_(EntityType.f_20528_, SnowGolemRenderer::new);
      m_174036_(EntityType.f_20474_, (p_174062_) -> {
         return new MinecartRenderer<>(p_174062_, ModelLayers.f_171244_);
      });
      m_174036_(EntityType.f_20478_, SpectralArrowRenderer::new);
      m_174036_(EntityType.f_20479_, SpiderRenderer::new);
      m_174036_(EntityType.f_20480_, (p_174060_) -> {
         return new SquidRenderer<>(p_174060_, new SquidModel<>(p_174060_.m_174023_(ModelLayers.f_171246_)));
      });
      m_174036_(EntityType.f_20481_, StrayRenderer::new);
      m_174036_(EntityType.f_20482_, StriderRenderer::new);
      m_174036_(EntityType.f_20515_, TntRenderer::new);
      m_174036_(EntityType.f_20475_, TntMinecartRenderer::new);
      m_174036_(EntityType.f_20488_, (p_174058_) -> {
         return new LlamaRenderer(p_174058_, ModelLayers.f_171254_);
      });
      m_174036_(EntityType.f_20487_, ThrownTridentRenderer::new);
      m_174036_(EntityType.f_20489_, TropicalFishRenderer::new);
      m_174036_(EntityType.f_20490_, TurtleRenderer::new);
      m_174036_(EntityType.f_20491_, VexRenderer::new);
      m_174036_(EntityType.f_20492_, VillagerRenderer::new);
      m_174036_(EntityType.f_20493_, VindicatorRenderer::new);
      m_174036_(EntityType.f_20494_, WanderingTraderRenderer::new);
      m_174036_(EntityType.f_20495_, WitchRenderer::new);
      m_174036_(EntityType.f_20496_, WitherBossRenderer::new);
      m_174036_(EntityType.f_20497_, WitherSkeletonRenderer::new);
      m_174036_(EntityType.f_20498_, WitherSkullRenderer::new);
      m_174036_(EntityType.f_20499_, WolfRenderer::new);
      m_174036_(EntityType.f_20500_, ZoglinRenderer::new);
      m_174036_(EntityType.f_20501_, ZombieRenderer::new);
      m_174036_(EntityType.f_20502_, (p_174056_) -> {
         return new UndeadHorseRenderer(p_174056_, ModelLayers.f_171225_);
      });
      m_174036_(EntityType.f_20530_, ZombieVillagerRenderer::new);
      m_174036_(EntityType.f_20531_, (p_174054_) -> {
         return new PiglinRenderer(p_174054_, ModelLayers.f_171231_, ModelLayers.f_171232_, ModelLayers.f_171233_, true);
      });
   }
}