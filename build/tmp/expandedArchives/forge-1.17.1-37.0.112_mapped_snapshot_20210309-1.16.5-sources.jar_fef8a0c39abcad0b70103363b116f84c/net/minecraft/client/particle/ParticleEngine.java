package net.minecraft.client.particle;

import com.google.common.base.Charsets;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleEngine implements PreparableReloadListener {
   private static final int f_172264_ = 16384;
   private static final List<ParticleRenderType> f_107288_ = ImmutableList.of(ParticleRenderType.f_107429_, ParticleRenderType.f_107430_, ParticleRenderType.f_107432_, ParticleRenderType.f_107431_, ParticleRenderType.f_107433_);
   protected ClientLevel f_107287_;
   private final Map<ParticleRenderType, Queue<Particle>> f_107289_ = Maps.newIdentityHashMap();
   private final Queue<TrackingEmitter> f_107290_ = Queues.newArrayDeque();
   private final TextureManager f_107291_;
   private final Random f_107292_ = new Random();
   private final Map<ResourceLocation, ParticleProvider<?>> f_107293_ = new java.util.HashMap<>();
   private final Queue<Particle> f_107294_ = Queues.newArrayDeque();
   private final Map<ResourceLocation, ParticleEngine.MutableSpriteSet> f_107295_ = Maps.newHashMap();
   private final TextureAtlas f_107296_;
   private final Object2IntOpenHashMap<ParticleGroup> f_172265_ = new Object2IntOpenHashMap<>();

   public ParticleEngine(ClientLevel p_107299_, TextureManager p_107300_) {
      this.f_107296_ = new TextureAtlas(TextureAtlas.f_118260_);
      p_107300_.m_118495_(this.f_107296_.m_118330_(), this.f_107296_);
      this.f_107287_ = p_107299_;
      this.f_107291_ = p_107300_;
      this.m_107404_();
   }

   private void m_107404_() {
      this.m_107378_(ParticleTypes.f_123770_, SpellParticle.AmbientMobProvider::new);
      this.m_107378_(ParticleTypes.f_123792_, HeartParticle.AngryVillagerProvider::new);
      this.m_107381_(ParticleTypes.f_123793_, new StationaryItemParticle.BarrierProvider());
      this.m_107381_(ParticleTypes.f_175835_, new StationaryItemParticle.LightProvider());
      this.m_107381_(ParticleTypes.f_123794_, new TerrainParticle.Provider());
      this.m_107378_(ParticleTypes.f_123795_, BubbleParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123774_, BubbleColumnUpParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123772_, BubblePopParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123777_, CampfireSmokeParticle.CosyProvider::new);
      this.m_107378_(ParticleTypes.f_123778_, CampfireSmokeParticle.SignalProvider::new);
      this.m_107378_(ParticleTypes.f_123796_, PlayerCloudParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123749_, SuspendedTownParticle.ComposterFillProvider::new);
      this.m_107378_(ParticleTypes.f_123797_, CritParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123773_, WaterCurrentDownParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123798_, CritParticle.DamageIndicatorProvider::new);
      this.m_107378_(ParticleTypes.f_123799_, DragonBreathParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123776_, SuspendedTownParticle.DolphinSpeedProvider::new);
      this.m_107378_(ParticleTypes.f_123800_, DripParticle.LavaHangProvider::new);
      this.m_107378_(ParticleTypes.f_123801_, DripParticle.LavaFallProvider::new);
      this.m_107378_(ParticleTypes.f_123802_, DripParticle.LavaLandProvider::new);
      this.m_107378_(ParticleTypes.f_123803_, DripParticle.WaterHangProvider::new);
      this.m_107378_(ParticleTypes.f_123804_, DripParticle.WaterFallProvider::new);
      this.m_107378_(ParticleTypes.f_123805_, DustParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_175836_, DustColorTransitionParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123806_, SpellParticle.Provider::new);
      this.m_107381_(ParticleTypes.f_123807_, new MobAppearanceParticle.Provider());
      this.m_107378_(ParticleTypes.f_123808_, CritParticle.MagicProvider::new);
      this.m_107378_(ParticleTypes.f_123809_, EnchantmentTableParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123810_, EndRodParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123811_, SpellParticle.MobProvider::new);
      this.m_107381_(ParticleTypes.f_123812_, new HugeExplosionSeedParticle.Provider());
      this.m_107378_(ParticleTypes.f_123813_, HugeExplosionParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123814_, FallingDustParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123815_, FireworkParticles.SparkProvider::new);
      this.m_107378_(ParticleTypes.f_123816_, WakeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123744_, FlameParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123746_, SoulParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123745_, FlameParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123747_, FireworkParticles.FlashProvider::new);
      this.m_107378_(ParticleTypes.f_123748_, SuspendedTownParticle.HappyVillagerProvider::new);
      this.m_107378_(ParticleTypes.f_123750_, HeartParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123751_, SpellParticle.InstantProvider::new);
      this.m_107381_(ParticleTypes.f_123752_, new BreakingItemParticle.Provider());
      this.m_107381_(ParticleTypes.f_123753_, new BreakingItemParticle.SlimeProvider());
      this.m_107381_(ParticleTypes.f_123754_, new BreakingItemParticle.SnowballProvider());
      this.m_107378_(ParticleTypes.f_123755_, LargeSmokeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123756_, LavaParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123757_, SuspendedTownParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123775_, EnchantmentTableParticle.NautilusProvider::new);
      this.m_107378_(ParticleTypes.f_123758_, NoteParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123759_, ExplodeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123760_, PortalParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123761_, WaterDropParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123762_, SmokeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123763_, PlayerCloudParticle.SneezeProvider::new);
      this.m_107378_(ParticleTypes.f_175821_, SnowflakeParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123764_, SpitParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123766_, AttackSweepParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123767_, TotemParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123765_, SquidInkParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123768_, SuspendedParticle.UnderwaterProvider::new);
      this.m_107378_(ParticleTypes.f_123769_, SplashParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123771_, SpellParticle.WitchProvider::new);
      this.m_107378_(ParticleTypes.f_123779_, DripParticle.HoneyHangProvider::new);
      this.m_107378_(ParticleTypes.f_123780_, DripParticle.HoneyFallProvider::new);
      this.m_107378_(ParticleTypes.f_123781_, DripParticle.HoneyLandProvider::new);
      this.m_107378_(ParticleTypes.f_123782_, DripParticle.NectarFallProvider::new);
      this.m_107378_(ParticleTypes.f_175832_, DripParticle.SporeBlossomFallProvider::new);
      this.m_107378_(ParticleTypes.f_175833_, SuspendedParticle.SporeBlossomAirProvider::new);
      this.m_107378_(ParticleTypes.f_123783_, AshParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_123784_, SuspendedParticle.CrimsonSporeProvider::new);
      this.m_107378_(ParticleTypes.f_123785_, SuspendedParticle.WarpedSporeProvider::new);
      this.m_107378_(ParticleTypes.f_123786_, DripParticle.ObsidianTearHangProvider::new);
      this.m_107378_(ParticleTypes.f_123787_, DripParticle.ObsidianTearFallProvider::new);
      this.m_107378_(ParticleTypes.f_123788_, DripParticle.ObsidianTearLandProvider::new);
      this.m_107378_(ParticleTypes.f_123789_, ReversePortalParticle.ReversePortalProvider::new);
      this.m_107378_(ParticleTypes.f_123790_, WhiteAshParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_175834_, FlameParticle.SmallFlameProvider::new);
      this.m_107378_(ParticleTypes.f_175824_, DripParticle.DripstoneWaterHangProvider::new);
      this.m_107378_(ParticleTypes.f_175825_, DripParticle.DripstoneWaterFallProvider::new);
      this.m_107378_(ParticleTypes.f_175822_, DripParticle.DripstoneLavaHangProvider::new);
      this.m_107378_(ParticleTypes.f_175823_, DripParticle.DripstoneLavaFallProvider::new);
      this.m_107378_(ParticleTypes.f_175820_, VibrationSignalParticle.Provider::new);
      this.m_107378_(ParticleTypes.f_175826_, SquidInkParticle.GlowInkProvider::new);
      this.m_107378_(ParticleTypes.f_175827_, GlowParticle.GlowSquidProvider::new);
      this.m_107378_(ParticleTypes.f_175828_, GlowParticle.WaxOnProvider::new);
      this.m_107378_(ParticleTypes.f_175829_, GlowParticle.WaxOffProvider::new);
      this.m_107378_(ParticleTypes.f_175830_, GlowParticle.ElectricSparkProvider::new);
      this.m_107378_(ParticleTypes.f_175831_, GlowParticle.ScrapeProvider::new);
   }

   public <T extends ParticleOptions> void m_107381_(ParticleType<T> p_107382_, ParticleProvider<T> p_107383_) {
      this.f_107293_.put(Registry.f_122829_.m_7981_(p_107382_), p_107383_);
   }

   public <T extends ParticleOptions> void m_107378_(ParticleType<T> p_107379_, ParticleEngine.SpriteParticleRegistration<T> p_107380_) {
      ParticleEngine.MutableSpriteSet particleengine$mutablespriteset = new ParticleEngine.MutableSpriteSet();
      this.f_107295_.put(Registry.f_122829_.m_7981_(p_107379_), particleengine$mutablespriteset);
      this.f_107293_.put(Registry.f_122829_.m_7981_(p_107379_), p_107380_.m_107419_(particleengine$mutablespriteset));
   }

   public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_107305_, ResourceManager p_107306_, ProfilerFiller p_107307_, ProfilerFiller p_107308_, Executor p_107309_, Executor p_107310_) {
      Map<ResourceLocation, List<ResourceLocation>> map = Maps.newConcurrentMap();
      CompletableFuture<?>[] completablefuture = Registry.f_122829_.m_6566_().stream().map((p_107315_) -> {
         return CompletableFuture.runAsync(() -> {
            this.m_107316_(p_107306_, p_107315_, map);
         }, p_107309_);
      }).toArray((p_107303_) -> {
         return new CompletableFuture[p_107303_];
      });
      return CompletableFuture.allOf(completablefuture).thenApplyAsync((p_107324_) -> {
         p_107307_.m_7242_();
         p_107307_.m_6180_("stitching");
         TextureAtlas.Preparations textureatlas$preparations = this.f_107296_.m_118307_(p_107306_, map.values().stream().flatMap(Collection::stream), p_107307_, 0);
         p_107307_.m_7238_();
         p_107307_.m_7241_();
         return textureatlas$preparations;
      }, p_107309_).thenCompose(p_107305_::m_6769_).thenAcceptAsync((p_107328_) -> {
         this.f_107289_.clear();
         p_107308_.m_7242_();
         p_107308_.m_6180_("upload");
         this.f_107296_.m_118312_(p_107328_);
         p_107308_.m_6182_("bindSpriteSets");
         TextureAtlasSprite textureatlassprite = this.f_107296_.m_118316_(MissingTextureAtlasSprite.m_118071_());
         map.forEach((p_172268_, p_172269_) -> {
            ImmutableList<TextureAtlasSprite> immutablelist = p_172269_.isEmpty() ? ImmutableList.of(textureatlassprite) : p_172269_.stream().map(this.f_107296_::m_118316_).collect(ImmutableList.toImmutableList());
            this.f_107295_.get(p_172268_).m_107415_(immutablelist);
         });
         p_107308_.m_7238_();
         p_107308_.m_7241_();
      }, p_107310_);
   }

   public void m_107301_() {
      this.f_107296_.m_118329_();
   }

   private void m_107316_(ResourceManager p_107317_, ResourceLocation p_107318_, Map<ResourceLocation, List<ResourceLocation>> p_107319_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_107318_.m_135827_(), "particles/" + p_107318_.m_135815_() + ".json");

      try {
         Resource resource = p_107317_.m_142591_(resourcelocation);

         try {
            Reader reader = new InputStreamReader(resource.m_6679_(), Charsets.UTF_8);

            try {
               ParticleDescription particledescription = ParticleDescription.m_107285_(GsonHelper.m_13859_(reader));
               List<ResourceLocation> list = particledescription.m_107282_();
               boolean flag = this.f_107295_.containsKey(p_107318_);
               if (list == null) {
                  if (flag) {
                     throw new IllegalStateException("Missing texture list for particle " + p_107318_);
                  }
               } else {
                  if (!flag) {
                     throw new IllegalStateException("Redundant texture list for particle " + p_107318_);
                  }

                  p_107319_.put(p_107318_, list.stream().map((p_107387_) -> {
                     return new ResourceLocation(p_107387_.m_135827_(), "particle/" + p_107387_.m_135815_());
                  }).collect(Collectors.toList()));
               }
            } catch (Throwable throwable2) {
               try {
                  reader.close();
               } catch (Throwable throwable1) {
                  throwable2.addSuppressed(throwable1);
               }

               throw throwable2;
            }

            reader.close();
         } catch (Throwable throwable3) {
            if (resource != null) {
               try {
                  resource.close();
               } catch (Throwable throwable) {
                  throwable3.addSuppressed(throwable);
               }
            }

            throw throwable3;
         }

         if (resource != null) {
            resource.close();
         }

      } catch (IOException ioexception) {
         throw new IllegalStateException("Failed to load description for particle " + p_107318_, ioexception);
      }
   }

   public void m_107329_(Entity p_107330_, ParticleOptions p_107331_) {
      this.f_107290_.add(new TrackingEmitter(this.f_107287_, p_107330_, p_107331_));
   }

   public void m_107332_(Entity p_107333_, ParticleOptions p_107334_, int p_107335_) {
      this.f_107290_.add(new TrackingEmitter(this.f_107287_, p_107333_, p_107334_, p_107335_));
   }

   @Nullable
   public Particle m_107370_(ParticleOptions p_107371_, double p_107372_, double p_107373_, double p_107374_, double p_107375_, double p_107376_, double p_107377_) {
      Particle particle = this.m_107395_(p_107371_, p_107372_, p_107373_, p_107374_, p_107375_, p_107376_, p_107377_);
      if (particle != null) {
         this.m_107344_(particle);
         return particle;
      } else {
         return null;
      }
   }

   @Nullable
   private <T extends ParticleOptions> Particle m_107395_(T p_107396_, double p_107397_, double p_107398_, double p_107399_, double p_107400_, double p_107401_, double p_107402_) {
      ParticleProvider<T> particleprovider = (ParticleProvider<T>)this.f_107293_.get(Registry.f_122829_.m_7981_(p_107396_.m_6012_()));
      return particleprovider == null ? null : particleprovider.m_6966_(p_107396_, this.f_107287_, p_107397_, p_107398_, p_107399_, p_107400_, p_107401_, p_107402_);
   }

   public void m_107344_(Particle p_107345_) {
      Optional<ParticleGroup> optional = p_107345_.m_142654_();
      if (optional.isPresent()) {
         if (this.m_172279_(optional.get())) {
            this.f_107294_.add(p_107345_);
            this.m_172281_(optional.get(), 1);
         }
      } else {
         this.f_107294_.add(p_107345_);
      }

   }

   public void m_107388_() {
      this.f_107289_.forEach((p_107349_, p_107350_) -> {
         this.f_107287_.m_46473_().m_6180_(p_107349_.toString());
         this.m_107384_(p_107350_);
         this.f_107287_.m_46473_().m_7238_();
      });
      if (!this.f_107290_.isEmpty()) {
         List<TrackingEmitter> list = Lists.newArrayList();

         for(TrackingEmitter trackingemitter : this.f_107290_) {
            trackingemitter.m_5989_();
            if (!trackingemitter.m_107276_()) {
               list.add(trackingemitter);
            }
         }

         this.f_107290_.removeAll(list);
      }

      Particle particle;
      if (!this.f_107294_.isEmpty()) {
         while((particle = this.f_107294_.poll()) != null) {
            this.f_107289_.computeIfAbsent(particle.m_7556_(), (p_107347_) -> {
               return EvictingQueue.create(16384);
            }).add(particle);
         }
      }

   }

   private void m_107384_(Collection<Particle> p_107385_) {
      if (!p_107385_.isEmpty()) {
         Iterator<Particle> iterator = p_107385_.iterator();

         while(iterator.hasNext()) {
            Particle particle = iterator.next();
            this.m_107393_(particle);
            if (!particle.m_107276_()) {
               particle.m_142654_().ifPresent((p_172289_) -> {
                  this.m_172281_(p_172289_, -1);
               });
               iterator.remove();
            }
         }
      }

   }

   private void m_172281_(ParticleGroup p_172282_, int p_172283_) {
      this.f_172265_.addTo(p_172282_, p_172283_);
   }

   private void m_107393_(Particle p_107394_) {
      try {
         p_107394_.m_5989_();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.m_127521_(throwable, "Ticking Particle");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Particle being ticked");
         crashreportcategory.m_128165_("Particle", p_107394_::toString);
         crashreportcategory.m_128165_("Particle Type", p_107394_.m_7556_()::toString);
         throw new ReportedException(crashreport);
      }
   }

   /**@deprecated Forge: use {@link #render(PoseStack, MultiBufferSource.BufferSource, LightTexture, Camera, float, net.minecraft.client.renderer.culling.Frustum)} with Frustum as additional parameter*/
   @Deprecated
   public void m_107336_(PoseStack p_107337_, MultiBufferSource.BufferSource p_107338_, LightTexture p_107339_, Camera p_107340_, float p_107341_) {
       render(p_107337_, p_107338_, p_107339_, p_107340_, p_107341_, null);
   }

   public void render(PoseStack p_107337_, MultiBufferSource.BufferSource p_107338_, LightTexture p_107339_, Camera p_107340_, float p_107341_, @Nullable net.minecraft.client.renderer.culling.Frustum clippingHelper) {
      p_107339_.m_109896_();
      RenderSystem.m_69482_();
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      posestack.m_166854_(p_107337_.m_85850_().m_85861_());
      RenderSystem.m_157182_();

      for(ParticleRenderType particlerendertype : this.f_107289_.keySet()) { // Forge: allow custom IParticleRenderType's
         if (particlerendertype == ParticleRenderType.f_107434_) continue;
         Iterable<Particle> iterable = this.f_107289_.get(particlerendertype);
         if (iterable != null) {
            RenderSystem.m_157427_(GameRenderer::m_172829_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            Tesselator tesselator = Tesselator.m_85913_();
            BufferBuilder bufferbuilder = tesselator.m_85915_();
            particlerendertype.m_6505_(bufferbuilder, this.f_107291_);

            for(Particle particle : iterable) {
               if (clippingHelper != null && particle.shouldCull() && !clippingHelper.m_113029_(particle.m_107277_())) continue;
               try {
                  particle.m_5744_(bufferbuilder, p_107340_, p_107341_);
               } catch (Throwable throwable) {
                  CrashReport crashreport = CrashReport.m_127521_(throwable, "Rendering Particle");
                  CrashReportCategory crashreportcategory = crashreport.m_127514_("Particle being rendered");
                  crashreportcategory.m_128165_("Particle", particle::toString);
                  crashreportcategory.m_128165_("Particle Type", particlerendertype::toString);
                  throw new ReportedException(crashreport);
               }
            }

            particlerendertype.m_6294_(tesselator);
         }
      }

      posestack.m_85849_();
      RenderSystem.m_157182_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69461_();
      p_107339_.m_109891_();
   }

   public void m_107342_(@Nullable ClientLevel p_107343_) {
      this.f_107287_ = p_107343_;
      this.f_107289_.clear();
      this.f_107290_.clear();
      this.f_172265_.clear();
   }

   public void m_107355_(BlockPos p_107356_, BlockState p_107357_) {
      if (!p_107357_.m_60795_() && !net.minecraftforge.client.RenderProperties.get(p_107357_).addDestroyEffects(p_107357_, this.f_107287_, p_107356_, this)) {
         VoxelShape voxelshape = p_107357_.m_60808_(this.f_107287_, p_107356_);
         double d0 = 0.25D;
         voxelshape.m_83286_((p_172273_, p_172274_, p_172275_, p_172276_, p_172277_, p_172278_) -> {
            double d1 = Math.min(1.0D, p_172276_ - p_172273_);
            double d2 = Math.min(1.0D, p_172277_ - p_172274_);
            double d3 = Math.min(1.0D, p_172278_ - p_172275_);
            int i = Math.max(2, Mth.m_14165_(d1 / 0.25D));
            int j = Math.max(2, Mth.m_14165_(d2 / 0.25D));
            int k = Math.max(2, Mth.m_14165_(d3 / 0.25D));

            for(int l = 0; l < i; ++l) {
               for(int i1 = 0; i1 < j; ++i1) {
                  for(int j1 = 0; j1 < k; ++j1) {
                     double d4 = ((double)l + 0.5D) / (double)i;
                     double d5 = ((double)i1 + 0.5D) / (double)j;
                     double d6 = ((double)j1 + 0.5D) / (double)k;
                     double d7 = d4 * d1 + p_172273_;
                     double d8 = d5 * d2 + p_172274_;
                     double d9 = d6 * d3 + p_172275_;
                     this.m_107344_(new TerrainParticle(this.f_107287_, (double)p_107356_.m_123341_() + d7, (double)p_107356_.m_123342_() + d8, (double)p_107356_.m_123343_() + d9, d4 - 0.5D, d5 - 0.5D, d6 - 0.5D, p_107357_, p_107356_).updateSprite(p_107357_, p_107356_));
                  }
               }
            }

         });
      }
   }

   public void m_107367_(BlockPos p_107368_, Direction p_107369_) {
      BlockState blockstate = this.f_107287_.m_8055_(p_107368_);
      if (blockstate.m_60799_() != RenderShape.INVISIBLE) {
         int i = p_107368_.m_123341_();
         int j = p_107368_.m_123342_();
         int k = p_107368_.m_123343_();
         float f = 0.1F;
         AABB aabb = blockstate.m_60808_(this.f_107287_, p_107368_).m_83215_();
         double d0 = (double)i + this.f_107292_.nextDouble() * (aabb.f_82291_ - aabb.f_82288_ - (double)0.2F) + (double)0.1F + aabb.f_82288_;
         double d1 = (double)j + this.f_107292_.nextDouble() * (aabb.f_82292_ - aabb.f_82289_ - (double)0.2F) + (double)0.1F + aabb.f_82289_;
         double d2 = (double)k + this.f_107292_.nextDouble() * (aabb.f_82293_ - aabb.f_82290_ - (double)0.2F) + (double)0.1F + aabb.f_82290_;
         if (p_107369_ == Direction.DOWN) {
            d1 = (double)j + aabb.f_82289_ - (double)0.1F;
         }

         if (p_107369_ == Direction.UP) {
            d1 = (double)j + aabb.f_82292_ + (double)0.1F;
         }

         if (p_107369_ == Direction.NORTH) {
            d2 = (double)k + aabb.f_82290_ - (double)0.1F;
         }

         if (p_107369_ == Direction.SOUTH) {
            d2 = (double)k + aabb.f_82293_ + (double)0.1F;
         }

         if (p_107369_ == Direction.WEST) {
            d0 = (double)i + aabb.f_82288_ - (double)0.1F;
         }

         if (p_107369_ == Direction.EAST) {
            d0 = (double)i + aabb.f_82291_ + (double)0.1F;
         }

         this.m_107344_((new TerrainParticle(this.f_107287_, d0, d1, d2, 0.0D, 0.0D, 0.0D, blockstate, p_107368_).updateSprite(blockstate, p_107368_)).m_107268_(0.2F).m_6569_(0.6F));
      }
   }

   public String m_107403_() {
      return String.valueOf(this.f_107289_.values().stream().mapToInt(Collection::size).sum());
   }

   public void addBlockHitEffects(BlockPos pos, net.minecraft.world.phys.BlockHitResult target) {
      BlockState state = f_107287_.m_8055_(pos);
      if (!net.minecraftforge.client.RenderProperties.get(state).addHitEffects(state, f_107287_, target, this))
         m_107367_(pos, target.m_82434_());
   }

   private boolean m_172279_(ParticleGroup p_172280_) {
      return this.f_172265_.getInt(p_172280_) < p_172280_.m_175819_();
   }

   @OnlyIn(Dist.CLIENT)
   class MutableSpriteSet implements SpriteSet {
      private List<TextureAtlasSprite> f_107406_;

      public TextureAtlasSprite m_5819_(int p_107413_, int p_107414_) {
         return this.f_107406_.get(p_107413_ * (this.f_107406_.size() - 1) / p_107414_);
      }

      public TextureAtlasSprite m_7102_(Random p_107418_) {
         return this.f_107406_.get(p_107418_.nextInt(this.f_107406_.size()));
      }

      public void m_107415_(List<TextureAtlasSprite> p_107416_) {
         this.f_107406_ = ImmutableList.copyOf(p_107416_);
      }
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface SpriteParticleRegistration<T extends ParticleOptions> {
      ParticleProvider<T> m_107419_(SpriteSet p_107420_);
   }
}
