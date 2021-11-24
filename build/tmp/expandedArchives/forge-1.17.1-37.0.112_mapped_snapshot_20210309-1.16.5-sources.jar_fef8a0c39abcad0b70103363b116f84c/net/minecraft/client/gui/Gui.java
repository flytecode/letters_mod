package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.chat.ChatListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.chat.OverlayChatListener;
import net.minecraft.client.gui.chat.StandardChatListener;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.StringDecomposer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

@OnlyIn(Dist.CLIENT)
public class Gui extends GuiComponent {
   protected static final ResourceLocation f_92981_ = new ResourceLocation("textures/misc/vignette.png");
   protected static final ResourceLocation f_92982_ = new ResourceLocation("textures/gui/widgets.png");
   protected static final ResourceLocation f_92983_ = new ResourceLocation("textures/misc/pumpkinblur.png");
   protected static final ResourceLocation f_168665_ = new ResourceLocation("textures/misc/spyglass_scope.png");
   protected static final ResourceLocation f_168666_ = new ResourceLocation("textures/misc/powder_snow_outline.png");
   protected static final Component f_92984_ = new TranslatableComponent("demo.demoExpired");
   protected static final int f_168667_ = 16777215;
   protected static final float f_168668_ = 5.0F;
   protected static final int f_168669_ = 10;
   protected static final int f_168670_ = 10;
   protected static final String f_168671_ = ": ";
   protected static final float f_168672_ = 0.2F;
   protected static final int f_168673_ = 9;
   protected static final int f_168674_ = 8;
   protected final Random f_92985_ = new Random();
   protected final Minecraft f_92986_;
   protected final ItemRenderer f_92987_;
   protected final ChatComponent f_92988_;
   protected int f_92989_;
   @Nullable
   protected Component f_92990_;
   protected int f_92991_;
   protected boolean f_92992_;
   public float f_92980_ = 1.0F;
   protected int f_92993_;
   protected ItemStack f_92994_ = ItemStack.f_41583_;
   protected final DebugScreenOverlay f_92995_;
   protected final SubtitleOverlay f_92996_;
   protected final SpectatorGui f_92997_;
   protected final PlayerTabOverlay f_92998_;
   protected final BossHealthOverlay f_92999_;
   protected int f_93000_;
   @Nullable
   protected Component f_93001_;
   @Nullable
   protected Component f_93002_;
   protected int f_92970_;
   protected int f_92971_;
   protected int f_92972_;
   protected int f_92973_;
   protected int f_92974_;
   protected long f_92975_;
   protected long f_92976_;
   protected int f_92977_;
   protected int f_92978_;
   protected final Map<ChatType, List<ChatListener>> f_92979_ = Maps.newHashMap();
   protected float f_168664_;

   public Gui(Minecraft p_93005_) {
      this.f_92986_ = p_93005_;
      this.f_92987_ = p_93005_.m_91291_();
      this.f_92995_ = new DebugScreenOverlay(p_93005_);
      this.f_92997_ = new SpectatorGui(p_93005_);
      this.f_92988_ = new ChatComponent(p_93005_);
      this.f_92998_ = new PlayerTabOverlay(p_93005_, this);
      this.f_92999_ = new BossHealthOverlay(p_93005_);
      this.f_92996_ = new SubtitleOverlay(p_93005_);

      for(ChatType chattype : ChatType.values()) {
         this.f_92979_.put(chattype, Lists.newArrayList());
      }

      ChatListener chatlistener = NarratorChatListener.f_93311_;
      this.f_92979_.get(ChatType.CHAT).add(new StandardChatListener(p_93005_));
      this.f_92979_.get(ChatType.CHAT).add(chatlistener);
      this.f_92979_.get(ChatType.SYSTEM).add(new StandardChatListener(p_93005_));
      this.f_92979_.get(ChatType.SYSTEM).add(chatlistener);
      this.f_92979_.get(ChatType.GAME_INFO).add(new OverlayChatListener(p_93005_));
      this.m_93006_();
   }

   public void m_93006_() {
      this.f_92970_ = 10;
      this.f_92971_ = 70;
      this.f_92972_ = 20;
   }

   public void m_93030_(PoseStack p_93031_, float p_93032_) {
      this.f_92977_ = this.f_92986_.m_91268_().m_85445_();
      this.f_92978_ = this.f_92986_.m_91268_().m_85446_();
      Font font = this.m_93082_();
      RenderSystem.m_69478_();
      if (Minecraft.m_91405_()) {
         this.m_93067_(this.f_92986_.m_91288_());
      } else {
         RenderSystem.m_69482_();
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_69453_();
      }

      float f = this.f_92986_.m_91297_();
      this.f_168664_ = Mth.m_14179_(0.5F * f, this.f_168664_, 1.125F);
      if (this.f_92986_.f_91066_.m_92176_().m_90612_()) {
         if (this.f_92986_.f_91074_.m_150108_()) {
            this.m_168675_(this.f_168664_);
         } else {
            this.f_168664_ = 0.5F;
            ItemStack itemstack = this.f_92986_.f_91074_.m_150109_().m_36052_(3);
            if (itemstack.m_150930_(Blocks.f_50143_.m_5456_())) {
               this.m_168708_(f_92983_, 1.0F);
            }
         }
      }

      if (this.f_92986_.f_91074_.m_146888_() > 0) {
         this.m_168708_(f_168666_, this.f_92986_.f_91074_.m_146889_());
      }

      float f2 = Mth.m_14179_(p_93032_, this.f_92986_.f_91074_.f_108590_, this.f_92986_.f_91074_.f_108589_);
      if (f2 > 0.0F && !this.f_92986_.f_91074_.m_21023_(MobEffects.f_19604_)) {
         this.m_93007_(f2);
      }

      if (this.f_92986_.f_91072_.m_105295_() == GameType.SPECTATOR) {
         this.f_92997_.m_94775_(p_93031_, p_93032_);
      } else if (!this.f_92986_.f_91066_.f_92062_) {
         this.m_93009_(p_93032_, p_93031_);
      }

      if (!this.f_92986_.f_91066_.f_92062_) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_93098_);
         RenderSystem.m_69478_();
         this.m_93080_(p_93031_);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_69453_();
         this.f_92986_.m_91307_().m_6180_("bossHealth");
         this.f_92999_.m_93704_(p_93031_);
         this.f_92986_.m_91307_().m_7238_();
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, f_93098_);
         if (this.f_92986_.f_91072_.m_105205_()) {
            this.m_93083_(p_93031_);
         }

         this.m_93086_(p_93031_);
         RenderSystem.m_69461_();
         int i = this.f_92977_ / 2 - 91;
         if (this.f_92986_.f_91074_.m_108633_()) {
            this.m_93033_(p_93031_, i);
         } else if (this.f_92986_.f_91072_.m_105288_()) {
            this.m_93071_(p_93031_, i);
         }

         if (this.f_92986_.f_91066_.f_92130_ && this.f_92986_.f_91072_.m_105295_() != GameType.SPECTATOR) {
            this.m_93069_(p_93031_);
         } else if (this.f_92986_.f_91074_.m_5833_()) {
            this.f_92997_.m_94773_(p_93031_);
         }
      }

      if (this.f_92986_.f_91074_.m_36318_() > 0) {
         this.f_92986_.m_91307_().m_6180_("sleep");
         RenderSystem.m_69465_();
         float f3 = (float)this.f_92986_.f_91074_.m_36318_();
         float f1 = f3 / 100.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F - (f3 - 100.0F) / 10.0F;
         }

         int j = (int)(220.0F * f1) << 24 | 1052704;
         m_93172_(p_93031_, 0, 0, this.f_92977_, this.f_92978_, j);
         RenderSystem.m_69482_();
         this.f_92986_.m_91307_().m_7238_();
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (this.f_92986_.m_91402_()) {
         this.m_93077_(p_93031_);
      }

      this.m_93028_(p_93031_);
      if (this.f_92986_.f_91066_.f_92063_) {
         this.f_92995_.m_94056_(p_93031_);
      }

      if (!this.f_92986_.f_91066_.f_92062_) {
         if (this.f_92990_ != null && this.f_92991_ > 0) {
            this.f_92986_.m_91307_().m_6180_("overlayMessage");
            float f4 = (float)this.f_92991_ - p_93032_;
            int i1 = (int)(f4 * 255.0F / 20.0F);
            if (i1 > 255) {
               i1 = 255;
            }

            if (i1 > 8) {
               p_93031_.m_85836_();
               p_93031_.m_85837_((double)(this.f_92977_ / 2), (double)(this.f_92978_ - 68), 0.0D);
               RenderSystem.m_69478_();
               RenderSystem.m_69453_();
               int k1 = 16777215;
               if (this.f_92992_) {
                  k1 = Mth.m_14169_(f4 / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               int k = i1 << 24 & -16777216;
               int l = font.m_92852_(this.f_92990_);
               this.m_93039_(p_93031_, font, -4, l, 16777215 | k);
               font.m_92889_(p_93031_, this.f_92990_, (float)(-l / 2), -4.0F, k1 | k);
               RenderSystem.m_69461_();
               p_93031_.m_85849_();
            }

            this.f_92986_.m_91307_().m_7238_();
         }

         if (this.f_93001_ != null && this.f_93000_ > 0) {
            this.f_92986_.m_91307_().m_6180_("titleAndSubtitle");
            float f5 = (float)this.f_93000_ - p_93032_;
            int j1 = 255;
            if (this.f_93000_ > this.f_92972_ + this.f_92971_) {
               float f6 = (float)(this.f_92970_ + this.f_92971_ + this.f_92972_) - f5;
               j1 = (int)(f6 * 255.0F / (float)this.f_92970_);
            }

            if (this.f_93000_ <= this.f_92972_) {
               j1 = (int)(f5 * 255.0F / (float)this.f_92972_);
            }

            j1 = Mth.m_14045_(j1, 0, 255);
            if (j1 > 8) {
               p_93031_.m_85836_();
               p_93031_.m_85837_((double)(this.f_92977_ / 2), (double)(this.f_92978_ / 2), 0.0D);
               RenderSystem.m_69478_();
               RenderSystem.m_69453_();
               p_93031_.m_85836_();
               p_93031_.m_85841_(4.0F, 4.0F, 4.0F);
               int l1 = j1 << 24 & -16777216;
               int i2 = font.m_92852_(this.f_93001_);
               this.m_93039_(p_93031_, font, -10, i2, 16777215 | l1);
               font.m_92763_(p_93031_, this.f_93001_, (float)(-i2 / 2), -10.0F, 16777215 | l1);
               p_93031_.m_85849_();
               if (this.f_93002_ != null) {
                  p_93031_.m_85836_();
                  p_93031_.m_85841_(2.0F, 2.0F, 2.0F);
                  int k2 = font.m_92852_(this.f_93002_);
                  this.m_93039_(p_93031_, font, 5, k2, 16777215 | l1);
                  font.m_92763_(p_93031_, this.f_93002_, (float)(-k2 / 2), 5.0F, 16777215 | l1);
                  p_93031_.m_85849_();
               }

               RenderSystem.m_69461_();
               p_93031_.m_85849_();
            }

            this.f_92986_.m_91307_().m_7238_();
         }

         this.f_92996_.m_94642_(p_93031_);
         Scoreboard scoreboard = this.f_92986_.f_91073_.m_6188_();
         Objective objective = null;
         PlayerTeam playerteam = scoreboard.m_83500_(this.f_92986_.f_91074_.m_6302_());
         if (playerteam != null) {
            int j2 = playerteam.m_7414_().m_126656_();
            if (j2 >= 0) {
               objective = scoreboard.m_83416_(3 + j2);
            }
         }

         Objective objective1 = objective != null ? objective : scoreboard.m_83416_(1);
         if (objective1 != null) {
            this.m_93036_(p_93031_, objective1);
         }

         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         p_93031_.m_85836_();
         p_93031_.m_85837_(0.0D, (double)(this.f_92978_ - 48), 0.0D);
         this.f_92986_.m_91307_().m_6180_("chat");
         this.f_92988_.m_93780_(p_93031_, this.f_92989_);
         this.f_92986_.m_91307_().m_7238_();
         p_93031_.m_85849_();
         objective1 = scoreboard.m_83416_(0);
         if (!this.f_92986_.f_91066_.f_92099_.m_90857_() || this.f_92986_.m_91090_() && this.f_92986_.f_91074_.f_108617_.m_105142_().size() <= 1 && objective1 == null) {
            this.f_92998_.m_94556_(false);
         } else {
            this.f_92998_.m_94556_(true);
            this.f_92998_.m_94544_(p_93031_, this.f_92977_, scoreboard, objective1);
         }
      }

      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected void m_93039_(PoseStack p_93040_, Font p_93041_, int p_93042_, int p_93043_, int p_93044_) {
      int i = this.f_92986_.f_91066_.m_92170_(0.0F);
      if (i != 0) {
         int j = -p_93043_ / 2;
         m_93172_(p_93040_, j - 2, p_93042_ - 2, j + p_93043_ + 2, p_93042_ + 9 + 2, FastColor.ARGB32.m_13657_(i, p_93044_));
      }

   }

   protected void m_93080_(PoseStack p_93081_) {
      Options options = this.f_92986_.f_91066_;
      if (options.m_92176_().m_90612_()) {
         if (this.f_92986_.f_91072_.m_105295_() != GameType.SPECTATOR || this.m_93024_(this.f_92986_.f_91077_)) {
            if (options.f_92063_ && !options.f_92062_ && !this.f_92986_.f_91074_.m_36330_() && !options.f_92047_) {
               Camera camera = this.f_92986_.f_91063_.m_109153_();
               PoseStack posestack = RenderSystem.m_157191_();
               posestack.m_85836_();
               posestack.m_85837_((double)(this.f_92977_ / 2), (double)(this.f_92978_ / 2), (double)this.m_93252_());
               posestack.m_85845_(Vector3f.f_122222_.m_122240_(camera.m_90589_()));
               posestack.m_85845_(Vector3f.f_122225_.m_122240_(camera.m_90590_()));
               posestack.m_85841_(-1.0F, -1.0F, -1.0F);
               RenderSystem.m_157182_();
               RenderSystem.m_69881_(10);
               posestack.m_85849_();
               RenderSystem.m_157182_();
            } else {
               RenderSystem.m_69416_(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
               int i = 15;
               this.m_93228_(p_93081_, (this.f_92977_ - 15) / 2, (this.f_92978_ - 15) / 2, 0, 0, 15, 15);
               if (this.f_92986_.f_91066_.f_92029_ == AttackIndicatorStatus.CROSSHAIR) {
                  float f = this.f_92986_.f_91074_.m_36403_(0.0F);
                  boolean flag = false;
                  if (this.f_92986_.f_91076_ != null && this.f_92986_.f_91076_ instanceof LivingEntity && f >= 1.0F) {
                     flag = this.f_92986_.f_91074_.m_36333_() > 5.0F;
                     flag = flag & this.f_92986_.f_91076_.m_6084_();
                  }

                  int j = this.f_92978_ / 2 - 7 + 16;
                  int k = this.f_92977_ / 2 - 8;
                  if (flag) {
                     this.m_93228_(p_93081_, k, j, 68, 94, 16, 16);
                  } else if (f < 1.0F) {
                     int l = (int)(f * 17.0F);
                     this.m_93228_(p_93081_, k, j, 36, 94, 16, 4);
                     this.m_93228_(p_93081_, k, j, 52, 94, l, 4);
                  }
               }
            }

         }
      }
   }

   private boolean m_93024_(HitResult p_93025_) {
      if (p_93025_ == null) {
         return false;
      } else if (p_93025_.m_6662_() == HitResult.Type.ENTITY) {
         return ((EntityHitResult)p_93025_).m_82443_() instanceof MenuProvider;
      } else if (p_93025_.m_6662_() == HitResult.Type.BLOCK) {
         BlockPos blockpos = ((BlockHitResult)p_93025_).m_82425_();
         Level level = this.f_92986_.f_91073_;
         return level.m_8055_(blockpos).m_60750_(level, blockpos) != null;
      } else {
         return false;
      }
   }

   protected void m_93028_(PoseStack p_93029_) {
      Collection<MobEffectInstance> collection = this.f_92986_.f_91074_.m_21220_();
      if (!collection.isEmpty()) {
         RenderSystem.m_69478_();
         int i = 0;
         int j = 0;
         MobEffectTextureManager mobeffecttexturemanager = this.f_92986_.m_91306_();
         List<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());
         RenderSystem.m_157456_(0, AbstractContainerScreen.f_97725_);

         for(MobEffectInstance mobeffectinstance : Ordering.natural().reverse().sortedCopy(collection)) {
            MobEffect mobeffect = mobeffectinstance.m_19544_();
            net.minecraftforge.client.EffectRenderer renderer = net.minecraftforge.client.RenderProperties.getEffectRenderer(mobeffectinstance);
            if (!renderer.shouldRenderHUD(mobeffectinstance)) continue;
            // Rebind in case previous renderHUDEffect changed texture
            RenderSystem.m_157456_(0, AbstractContainerScreen.f_97725_);
            if (mobeffectinstance.m_19575_()) {
               int k = this.f_92977_;
               int l = 1;
               if (this.f_92986_.m_91402_()) {
                  l += 15;
               }

               if (mobeffect.m_19486_()) {
                  ++i;
                  k = k - 25 * i;
               } else {
                  ++j;
                  k = k - 25 * j;
                  l += 26;
               }

               RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
               float f = 1.0F;
               if (mobeffectinstance.m_19571_()) {
                  this.m_93228_(p_93029_, k, l, 165, 166, 24, 24);
               } else {
                  this.m_93228_(p_93029_, k, l, 141, 166, 24, 24);
                  if (mobeffectinstance.m_19557_() <= 200) {
                     int i1 = 10 - mobeffectinstance.m_19557_() / 20;
                     f = Mth.m_14036_((float)mobeffectinstance.m_19557_() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F) + Mth.m_14089_((float)mobeffectinstance.m_19557_() * (float)Math.PI / 5.0F) * Mth.m_14036_((float)i1 / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.m_118732_(mobeffect);
               int j1 = k;
               int k1 = l;
               float f1 = f;
               list.add(() -> {
                  RenderSystem.m_157456_(0, textureatlassprite.m_118414_().m_118330_());
                  RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, f1);
                  m_93200_(p_93029_, j1 + 3, k1 + 3, this.m_93252_(), 18, 18, textureatlassprite);
               });
               renderer.renderHUDEffect(mobeffectinstance,this, p_93029_, k, l, this.m_93252_(), f);
            }
         }

         list.forEach(Runnable::run);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   protected void m_93009_(float p_93010_, PoseStack p_93011_) {
      Player player = this.m_93092_();
      if (player != null) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_92982_);
         ItemStack itemstack = player.m_21206_();
         HumanoidArm humanoidarm = player.m_5737_().m_20828_();
         int i = this.f_92977_ / 2;
         int j = this.m_93252_();
         int k = 182;
         int l = 91;
         this.m_93250_(-90);
         this.m_93228_(p_93011_, i - 91, this.f_92978_ - 22, 0, 0, 182, 22);
         this.m_93228_(p_93011_, i - 91 - 1 + player.m_150109_().f_35977_ * 20, this.f_92978_ - 22 - 1, 0, 22, 24, 22);
         if (!itemstack.m_41619_()) {
            if (humanoidarm == HumanoidArm.LEFT) {
               this.m_93228_(p_93011_, i - 91 - 29, this.f_92978_ - 23, 24, 22, 29, 24);
            } else {
               this.m_93228_(p_93011_, i + 91, this.f_92978_ - 23, 53, 22, 29, 24);
            }
         }

         this.m_93250_(j);
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         int i1 = 1;

         for(int j1 = 0; j1 < 9; ++j1) {
            int k1 = i - 90 + j1 * 20 + 2;
            int l1 = this.f_92978_ - 16 - 3;
            this.m_168677_(k1, l1, p_93010_, player, player.m_150109_().f_35974_.get(j1), i1++);
         }

         if (!itemstack.m_41619_()) {
            int j2 = this.f_92978_ - 16 - 3;
            if (humanoidarm == HumanoidArm.LEFT) {
               this.m_168677_(i - 91 - 26, j2, p_93010_, player, itemstack, i1++);
            } else {
               this.m_168677_(i + 91 + 10, j2, p_93010_, player, itemstack, i1++);
            }
         }

         if (this.f_92986_.f_91066_.f_92029_ == AttackIndicatorStatus.HOTBAR) {
            float f = this.f_92986_.f_91074_.m_36403_(0.0F);
            if (f < 1.0F) {
               int k2 = this.f_92978_ - 20;
               int l2 = i + 91 + 6;
               if (humanoidarm == HumanoidArm.RIGHT) {
                  l2 = i - 91 - 22;
               }

               RenderSystem.m_157456_(0, GuiComponent.f_93098_);
               int i2 = (int)(f * 19.0F);
               RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
               this.m_93228_(p_93011_, l2, k2, 0, 94, 18, 18);
               this.m_93228_(p_93011_, l2, k2 + 18 - i2, 18, 112 - i2, 18, i2);
            }
         }

         RenderSystem.m_69461_();
      }
   }

   public void m_93033_(PoseStack p_93034_, int p_93035_) {
      this.f_92986_.m_91307_().m_6180_("jumpBar");
      RenderSystem.m_157456_(0, GuiComponent.f_93098_);
      float f = this.f_92986_.f_91074_.m_108634_();
      int i = 182;
      int j = (int)(f * 183.0F);
      int k = this.f_92978_ - 32 + 3;
      this.m_93228_(p_93034_, p_93035_, k, 0, 84, 182, 5);
      if (j > 0) {
         this.m_93228_(p_93034_, p_93035_, k, 0, 89, j, 5);
      }

      this.f_92986_.m_91307_().m_7238_();
   }

   public void m_93071_(PoseStack p_93072_, int p_93073_) {
      this.f_92986_.m_91307_().m_6180_("expBar");
      RenderSystem.m_157456_(0, GuiComponent.f_93098_);
      int i = this.f_92986_.f_91074_.m_36323_();
      if (i > 0) {
         int j = 182;
         int k = (int)(this.f_92986_.f_91074_.f_36080_ * 183.0F);
         int l = this.f_92978_ - 32 + 3;
         this.m_93228_(p_93072_, p_93073_, l, 0, 64, 182, 5);
         if (k > 0) {
            this.m_93228_(p_93072_, p_93073_, l, 0, 69, k, 5);
         }
      }

      this.f_92986_.m_91307_().m_7238_();
      if (this.f_92986_.f_91074_.f_36078_ > 0) {
         this.f_92986_.m_91307_().m_6180_("expLevel");
         String s = "" + this.f_92986_.f_91074_.f_36078_;
         int i1 = (this.f_92977_ - this.m_93082_().m_92895_(s)) / 2;
         int j1 = this.f_92978_ - 31 - 4;
         this.m_93082_().m_92883_(p_93072_, s, (float)(i1 + 1), (float)j1, 0);
         this.m_93082_().m_92883_(p_93072_, s, (float)(i1 - 1), (float)j1, 0);
         this.m_93082_().m_92883_(p_93072_, s, (float)i1, (float)(j1 + 1), 0);
         this.m_93082_().m_92883_(p_93072_, s, (float)i1, (float)(j1 - 1), 0);
         this.m_93082_().m_92883_(p_93072_, s, (float)i1, (float)j1, 8453920);
         this.f_92986_.m_91307_().m_7238_();
      }

   }

   public void m_93069_(PoseStack p_93070_) {
      this.f_92986_.m_91307_().m_6180_("selectedItemName");
      if (this.f_92993_ > 0 && !this.f_92994_.m_41619_()) {
         MutableComponent mutablecomponent = (new TextComponent("")).m_7220_(this.f_92994_.m_41786_()).m_130940_(this.f_92994_.m_41791_().f_43022_);
         if (this.f_92994_.m_41788_()) {
            mutablecomponent.m_130940_(ChatFormatting.ITALIC);
         }

         Component highlightTip = this.f_92994_.getHighlightTip(mutablecomponent);
         int i = this.m_93082_().m_92852_(highlightTip);
         int j = (this.f_92977_ - i) / 2;
         int k = this.f_92978_ - 59;
         if (!this.f_92986_.f_91072_.m_105205_()) {
            k += 14;
         }

         int l = (int)((float)this.f_92993_ * 256.0F / 10.0F);
         if (l > 255) {
            l = 255;
         }

         if (l > 0) {
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            m_93172_(p_93070_, j - 2, k - 2, j + i + 2, k + 9 + 2, this.f_92986_.f_91066_.m_92143_(0));
            Font font = net.minecraftforge.client.RenderProperties.get(f_92994_).getFont(f_92994_);
            if (font == null) {
               this.m_93082_().m_92763_(p_93070_, highlightTip, (float)j, (float)k, 16777215 + (l << 24));
            } else {
               j = (this.f_92977_ - font.m_92852_(highlightTip)) / 2;
               font.m_92763_(p_93070_, highlightTip, (float)j, (float)k, 16777215 + (l << 24));
            }
            RenderSystem.m_69461_();
         }
      }

      this.f_92986_.m_91307_().m_7238_();
   }

   public void m_93077_(PoseStack p_93078_) {
      this.f_92986_.m_91307_().m_6180_("demo");
      Component component;
      if (this.f_92986_.f_91073_.m_46467_() >= 120500L) {
         component = f_92984_;
      } else {
         component = new TranslatableComponent("demo.remainingTime", StringUtil.m_14404_((int)(120500L - this.f_92986_.f_91073_.m_46467_())));
      }

      int i = this.m_93082_().m_92852_(component);
      this.m_93082_().m_92763_(p_93078_, component, (float)(this.f_92977_ - i - 10), 5.0F, 16777215);
      this.f_92986_.m_91307_().m_7238_();
   }

   protected void m_93036_(PoseStack p_93037_, Objective p_93038_) {
      Scoreboard scoreboard = p_93038_.m_83313_();
      Collection<Score> collection = scoreboard.m_83498_(p_93038_);
      List<Score> list = collection.stream().filter((p_93027_) -> {
         return p_93027_.m_83405_() != null && !p_93027_.m_83405_().startsWith("#");
      }).collect(Collectors.toList());
      if (list.size() > 15) {
         collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
      } else {
         collection = list;
      }

      List<Pair<Score, Component>> list1 = Lists.newArrayListWithCapacity(collection.size());
      Component component = p_93038_.m_83322_();
      int i = this.m_93082_().m_92852_(component);
      int j = i;
      int k = this.m_93082_().m_92895_(": ");

      for(Score score : collection) {
         PlayerTeam playerteam = scoreboard.m_83500_(score.m_83405_());
         Component component1 = PlayerTeam.m_83348_(playerteam, new TextComponent(score.m_83405_()));
         list1.add(Pair.of(score, component1));
         j = Math.max(j, this.m_93082_().m_92852_(component1) + k + this.m_93082_().m_92895_(Integer.toString(score.m_83400_())));
      }

      int i2 = collection.size() * 9;
      int j2 = this.f_92978_ / 2 + i2 / 3;
      int k2 = 3;
      int l2 = this.f_92977_ - j - 3;
      int l = 0;
      int i1 = this.f_92986_.f_91066_.m_92170_(0.3F);
      int j1 = this.f_92986_.f_91066_.m_92170_(0.4F);

      for(Pair<Score, Component> pair : list1) {
         ++l;
         Score score1 = pair.getFirst();
         Component component2 = pair.getSecond();
         String s = "" + ChatFormatting.RED + score1.m_83400_();
         int k1 = j2 - l * 9;
         int l1 = this.f_92977_ - 3 + 2;
         m_93172_(p_93037_, l2 - 2, k1, l1, k1 + 9, i1);
         this.m_93082_().m_92889_(p_93037_, component2, (float)l2, (float)k1, -1);
         this.m_93082_().m_92883_(p_93037_, s, (float)(l1 - this.m_93082_().m_92895_(s)), (float)k1, -1);
         if (l == collection.size()) {
            m_93172_(p_93037_, l2 - 2, k1 - 9 - 1, l1, k1 - 1, j1);
            m_93172_(p_93037_, l2 - 2, k1 - 1, l1, k1, i1);
            this.m_93082_().m_92889_(p_93037_, component, (float)(l2 + j / 2 - i / 2), (float)(k1 - 9), -1);
         }
      }

   }

   private Player m_93092_() {
      return !(this.f_92986_.m_91288_() instanceof Player) ? null : (Player)this.f_92986_.m_91288_();
   }

   private LivingEntity m_93093_() {
      Player player = this.m_93092_();
      if (player != null) {
         Entity entity = player.m_20202_();
         if (entity == null) {
            return null;
         }

         if (entity instanceof LivingEntity) {
            return (LivingEntity)entity;
         }
      }

      return null;
   }

   private int m_93022_(LivingEntity p_93023_) {
      if (p_93023_ != null && p_93023_.m_20152_()) {
         float f = p_93023_.m_21233_();
         int i = (int)(f + 0.5F) / 2;
         if (i > 30) {
            i = 30;
         }

         return i;
      } else {
         return 0;
      }
   }

   private int m_93012_(int p_93013_) {
      return (int)Math.ceil((double)p_93013_ / 10.0D);
   }

   private void m_93083_(PoseStack p_93084_) {
      Player player = this.m_93092_();
      if (player != null) {
         int i = Mth.m_14167_(player.m_21223_());
         boolean flag = this.f_92976_ > (long)this.f_92989_ && (this.f_92976_ - (long)this.f_92989_) / 3L % 2L == 1L;
         long j = Util.m_137550_();
         if (i < this.f_92973_ && player.f_19802_ > 0) {
            this.f_92975_ = j;
            this.f_92976_ = (long)(this.f_92989_ + 20);
         } else if (i > this.f_92973_ && player.f_19802_ > 0) {
            this.f_92975_ = j;
            this.f_92976_ = (long)(this.f_92989_ + 10);
         }

         if (j - this.f_92975_ > 1000L) {
            this.f_92973_ = i;
            this.f_92974_ = i;
            this.f_92975_ = j;
         }

         this.f_92973_ = i;
         int k = this.f_92974_;
         this.f_92985_.setSeed((long)(this.f_92989_ * 312871));
         FoodData fooddata = player.m_36324_();
         int l = fooddata.m_38702_();
         int i1 = this.f_92977_ / 2 - 91;
         int j1 = this.f_92977_ / 2 + 91;
         int k1 = this.f_92978_ - 39;
         float f = Math.max((float)player.m_21133_(Attributes.f_22276_), (float)Math.max(k, i));
         int l1 = Mth.m_14167_(player.m_6103_());
         int i2 = Mth.m_14167_((f + (float)l1) / 2.0F / 10.0F);
         int j2 = Math.max(10 - (i2 - 2), 3);
         int k2 = k1 - (i2 - 1) * j2 - 10;
         int l2 = k1 - 10;
         int i3 = player.m_21230_();
         int j3 = -1;
         if (player.m_21023_(MobEffects.f_19605_)) {
            j3 = this.f_92989_ % Mth.m_14167_(f + 5.0F);
         }

         this.f_92986_.m_91307_().m_6180_("armor");

         for(int k3 = 0; k3 < 10; ++k3) {
            if (i3 > 0) {
               int l3 = i1 + k3 * 8;
               if (k3 * 2 + 1 < i3) {
                  this.m_93228_(p_93084_, l3, k2, 34, 9, 9, 9);
               }

               if (k3 * 2 + 1 == i3) {
                  this.m_93228_(p_93084_, l3, k2, 25, 9, 9, 9);
               }

               if (k3 * 2 + 1 > i3) {
                  this.m_93228_(p_93084_, l3, k2, 16, 9, 9, 9);
               }
            }
         }

         this.f_92986_.m_91307_().m_6182_("health");
         this.m_168688_(p_93084_, player, i1, k1, j2, j3, f, i, k, l1, flag);
         LivingEntity livingentity = this.m_93093_();
         int k5 = this.m_93022_(livingentity);
         if (k5 == 0) {
            this.f_92986_.m_91307_().m_6182_("food");

            for(int i4 = 0; i4 < 10; ++i4) {
               int j4 = k1;
               int k4 = 16;
               int l4 = 0;
               if (player.m_21023_(MobEffects.f_19612_)) {
                  k4 += 36;
                  l4 = 13;
               }

               if (player.m_36324_().m_38722_() <= 0.0F && this.f_92989_ % (l * 3 + 1) == 0) {
                  j4 = k1 + (this.f_92985_.nextInt(3) - 1);
               }

               int i5 = j1 - i4 * 8 - 9;
               this.m_93228_(p_93084_, i5, j4, 16 + l4 * 9, 27, 9, 9);
               if (i4 * 2 + 1 < l) {
                  this.m_93228_(p_93084_, i5, j4, k4 + 36, 27, 9, 9);
               }

               if (i4 * 2 + 1 == l) {
                  this.m_93228_(p_93084_, i5, j4, k4 + 45, 27, 9, 9);
               }
            }

            l2 -= 10;
         }

         this.f_92986_.m_91307_().m_6182_("air");
         int l5 = player.m_6062_();
         int i6 = Math.min(player.m_20146_(), l5);
         if (player.m_19941_(FluidTags.f_13131_) || i6 < l5) {
            int j6 = this.m_93012_(k5) - 1;
            l2 = l2 - j6 * 10;
            int k6 = Mth.m_14165_((double)(i6 - 2) * 10.0D / (double)l5);
            int l6 = Mth.m_14165_((double)i6 * 10.0D / (double)l5) - k6;

            for(int j5 = 0; j5 < k6 + l6; ++j5) {
               if (j5 < k6) {
                  this.m_93228_(p_93084_, j1 - j5 * 8 - 9, l2, 16, 18, 9, 9);
               } else {
                  this.m_93228_(p_93084_, j1 - j5 * 8 - 9, l2, 25, 18, 9, 9);
               }
            }
         }

         this.f_92986_.m_91307_().m_7238_();
      }
   }

   protected void m_168688_(PoseStack p_168689_, Player p_168690_, int p_168691_, int p_168692_, int p_168693_, int p_168694_, float p_168695_, int p_168696_, int p_168697_, int p_168698_, boolean p_168699_) {
      Gui.HeartType gui$hearttype = Gui.HeartType.m_168732_(p_168690_);
      int i = 9 * (p_168690_.f_19853_.m_6106_().m_5466_() ? 5 : 0);
      int j = Mth.m_14165_((double)p_168695_ / 2.0D);
      int k = Mth.m_14165_((double)p_168698_ / 2.0D);
      int l = j * 2;

      for(int i1 = j + k - 1; i1 >= 0; --i1) {
         int j1 = i1 / 10;
         int k1 = i1 % 10;
         int l1 = p_168691_ + k1 * 8;
         int i2 = p_168692_ - j1 * p_168693_;
         if (p_168696_ + p_168698_ <= 4) {
            i2 += this.f_92985_.nextInt(2);
         }

         if (i1 < j && i1 == p_168694_) {
            i2 -= 2;
         }

         this.m_168700_(p_168689_, Gui.HeartType.CONTAINER, l1, i2, i, p_168699_, false);
         int j2 = i1 * 2;
         boolean flag = i1 >= j;
         if (flag) {
            int k2 = j2 - l;
            if (k2 < p_168698_) {
               boolean flag1 = k2 + 1 == p_168698_;
               this.m_168700_(p_168689_, gui$hearttype == Gui.HeartType.WITHERED ? gui$hearttype : Gui.HeartType.ABSORBING, l1, i2, i, false, flag1);
            }
         }

         if (p_168699_ && j2 < p_168697_) {
            boolean flag2 = j2 + 1 == p_168697_;
            this.m_168700_(p_168689_, gui$hearttype, l1, i2, i, true, flag2);
         }

         if (j2 < p_168696_) {
            boolean flag3 = j2 + 1 == p_168696_;
            this.m_168700_(p_168689_, gui$hearttype, l1, i2, i, false, flag3);
         }
      }

   }

   private void m_168700_(PoseStack p_168701_, Gui.HeartType p_168702_, int p_168703_, int p_168704_, int p_168705_, boolean p_168706_, boolean p_168707_) {
      this.m_93228_(p_168701_, p_168703_, p_168704_, p_168702_.m_168734_(p_168707_, p_168706_), p_168705_, 9, 9);
   }

   private void m_93086_(PoseStack p_93087_) {
      LivingEntity livingentity = this.m_93093_();
      if (livingentity != null) {
         int i = this.m_93022_(livingentity);
         if (i != 0) {
            int j = (int)Math.ceil((double)livingentity.m_21223_());
            this.f_92986_.m_91307_().m_6182_("mountHealth");
            int k = this.f_92978_ - 39;
            int l = this.f_92977_ / 2 + 91;
            int i1 = k;
            int j1 = 0;

            for(boolean flag = false; i > 0; j1 += 20) {
               int k1 = Math.min(i, 10);
               i -= k1;

               for(int l1 = 0; l1 < k1; ++l1) {
                  int i2 = 52;
                  int j2 = 0;
                  int k2 = l - l1 * 8 - 9;
                  this.m_93228_(p_93087_, k2, i1, 52 + j2 * 9, 9, 9, 9);
                  if (l1 * 2 + 1 + j1 < j) {
                     this.m_93228_(p_93087_, k2, i1, 88, 9, 9, 9);
                  }

                  if (l1 * 2 + 1 + j1 == j) {
                     this.m_93228_(p_93087_, k2, i1, 97, 9, 9, 9);
                  }
               }

               i1 -= 10;
            }

         }
      }
   }

   protected void m_168708_(ResourceLocation p_168709_, float p_168710_) {
      RenderSystem.m_69465_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, p_168710_);
      RenderSystem.m_157456_(0, p_168709_);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_5483_(0.0D, (double)this.f_92978_, -90.0D).m_7421_(0.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)this.f_92978_, -90.0D).m_7421_(1.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, 0.0D, -90.0D).m_7421_(1.0F, 0.0F).m_5752_();
      bufferbuilder.m_5483_(0.0D, 0.0D, -90.0D).m_7421_(0.0F, 0.0F).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69482_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected void m_168675_(float p_168676_) {
      RenderSystem.m_69465_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_168665_);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      float f = (float)Math.min(this.f_92977_, this.f_92978_);
      float f1 = Math.min((float)this.f_92977_ / f, (float)this.f_92978_ / f) * p_168676_;
      float f2 = f * f1;
      float f3 = f * f1;
      float f4 = ((float)this.f_92977_ - f2) / 2.0F;
      float f5 = ((float)this.f_92978_ - f3) / 2.0F;
      float f6 = f4 + f2;
      float f7 = f5 + f3;
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_5483_((double)f4, (double)f7, -90.0D).m_7421_(0.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)f6, (double)f7, -90.0D).m_7421_(1.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)f6, (double)f5, -90.0D).m_7421_(1.0F, 0.0F).m_5752_();
      bufferbuilder.m_5483_((double)f4, (double)f5, -90.0D).m_7421_(0.0F, 0.0F).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      RenderSystem.m_69472_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
      bufferbuilder.m_5483_(0.0D, (double)this.f_92978_, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)this.f_92978_, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)f7, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_(0.0D, (double)f7, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_(0.0D, (double)f5, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)f5, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, 0.0D, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_(0.0D, 0.0D, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_(0.0D, (double)f7, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)f4, (double)f7, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)f4, (double)f5, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_(0.0D, (double)f5, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)f6, (double)f7, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)f7, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)f5, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      bufferbuilder.m_5483_((double)f6, (double)f5, -90.0D).m_6122_(0, 0, 0, 255).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69493_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69482_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void m_93020_(Entity p_93021_) {
      if (p_93021_ != null) {
         float f = Mth.m_14036_(1.0F - p_93021_.m_6073_(), 0.0F, 1.0F);
         this.f_92980_ = (float)((double)this.f_92980_ + (double)(f - this.f_92980_) * 0.01D);
      }
   }

   protected void m_93067_(Entity p_93068_) {
      WorldBorder worldborder = this.f_92986_.f_91073_.m_6857_();
      float f = (float)worldborder.m_61925_(p_93068_);
      double d0 = Math.min(worldborder.m_61966_() * (double)worldborder.m_61967_() * 1000.0D, Math.abs(worldborder.m_61961_() - worldborder.m_61959_()));
      double d1 = Math.max((double)worldborder.m_61968_(), d0);
      if ((double)f < d1) {
         f = 1.0F - (float)((double)f / d1);
      } else {
         f = 0.0F;
      }

      RenderSystem.m_69465_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_69416_(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      if (f > 0.0F) {
         f = Mth.m_14036_(f, 0.0F, 1.0F);
         RenderSystem.m_157429_(0.0F, f, f, 1.0F);
      } else {
         float f1 = this.f_92980_;
         f1 = Mth.m_14036_(f1, 0.0F, 1.0F);
         RenderSystem.m_157429_(f1, f1, f1, 1.0F);
      }

      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_92981_);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_5483_(0.0D, (double)this.f_92978_, -90.0D).m_7421_(0.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)this.f_92978_, -90.0D).m_7421_(1.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, 0.0D, -90.0D).m_7421_(1.0F, 0.0F).m_5752_();
      bufferbuilder.m_5483_(0.0D, 0.0D, -90.0D).m_7421_(0.0F, 0.0F).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69482_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69453_();
   }

   protected void m_93007_(float p_93008_) {
      if (p_93008_ < 1.0F) {
         p_93008_ = p_93008_ * p_93008_;
         p_93008_ = p_93008_ * p_93008_;
         p_93008_ = p_93008_ * 0.8F + 0.2F;
      }

      RenderSystem.m_69465_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_69453_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, p_93008_);
      RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      TextureAtlasSprite textureatlassprite = this.f_92986_.m_91289_().m_110907_().m_110882_(Blocks.f_50142_.m_49966_());
      float f = textureatlassprite.m_118409_();
      float f1 = textureatlassprite.m_118411_();
      float f2 = textureatlassprite.m_118410_();
      float f3 = textureatlassprite.m_118412_();
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_5483_(0.0D, (double)this.f_92978_, -90.0D).m_7421_(f, f3).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, (double)this.f_92978_, -90.0D).m_7421_(f2, f3).m_5752_();
      bufferbuilder.m_5483_((double)this.f_92977_, 0.0D, -90.0D).m_7421_(f2, f1).m_5752_();
      bufferbuilder.m_5483_(0.0D, 0.0D, -90.0D).m_7421_(f, f1).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69482_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void m_168677_(int p_168678_, int p_168679_, float p_168680_, Player p_168681_, ItemStack p_168682_, int p_168683_) {
      if (!p_168682_.m_41619_()) {
         PoseStack posestack = RenderSystem.m_157191_();
         float f = (float)p_168682_.m_41612_() - p_168680_;
         if (f > 0.0F) {
            float f1 = 1.0F + f / 5.0F;
            posestack.m_85836_();
            posestack.m_85837_((double)(p_168678_ + 8), (double)(p_168679_ + 12), 0.0D);
            posestack.m_85841_(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            posestack.m_85837_((double)(-(p_168678_ + 8)), (double)(-(p_168679_ + 12)), 0.0D);
            RenderSystem.m_157182_();
         }

         this.f_92987_.m_174229_(p_168681_, p_168682_, p_168678_, p_168679_, p_168683_);
         RenderSystem.m_157427_(GameRenderer::m_172811_);
         if (f > 0.0F) {
            posestack.m_85849_();
            RenderSystem.m_157182_();
         }

         this.f_92987_.m_115169_(this.f_92986_.f_91062_, p_168682_, p_168678_, p_168679_);
      }
   }

   public void m_93066_() {
      if (this.f_92991_ > 0) {
         --this.f_92991_;
      }

      if (this.f_93000_ > 0) {
         --this.f_93000_;
         if (this.f_93000_ <= 0) {
            this.f_93001_ = null;
            this.f_93002_ = null;
         }
      }

      ++this.f_92989_;
      Entity entity = this.f_92986_.m_91288_();
      if (entity != null) {
         this.m_93020_(entity);
      }

      if (this.f_92986_.f_91074_ != null) {
         ItemStack itemstack = this.f_92986_.f_91074_.m_150109_().m_36056_();
         if (itemstack.m_41619_()) {
            this.f_92993_ = 0;
         } else if (!this.f_92994_.m_41619_() && itemstack.m_41720_() == this.f_92994_.m_41720_() && (itemstack.m_41786_().equals(this.f_92994_.m_41786_()) && itemstack.getHighlightTip(itemstack.m_41786_()).equals(f_92994_.getHighlightTip(f_92994_.m_41786_())))) {
            if (this.f_92993_ > 0) {
               --this.f_92993_;
            }
         } else {
            this.f_92993_ = 40;
         }

         this.f_92994_ = itemstack;
      }

   }

   public void m_93055_(Component p_93056_) {
      this.m_93063_(new TranslatableComponent("record.nowPlaying", p_93056_), true);
   }

   public void m_93063_(Component p_93064_, boolean p_93065_) {
      this.f_92990_ = p_93064_;
      this.f_92991_ = 60;
      this.f_92992_ = p_93065_;
   }

   public void m_168684_(int p_168685_, int p_168686_, int p_168687_) {
      if (p_168685_ >= 0) {
         this.f_92970_ = p_168685_;
      }

      if (p_168686_ >= 0) {
         this.f_92971_ = p_168686_;
      }

      if (p_168687_ >= 0) {
         this.f_92972_ = p_168687_;
      }

      if (this.f_93000_ > 0) {
         this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
      }

   }

   public void m_168711_(Component p_168712_) {
      this.f_93002_ = p_168712_;
   }

   public void m_168714_(Component p_168715_) {
      this.f_93001_ = p_168715_;
      this.f_93000_ = this.f_92970_ + this.f_92971_ + this.f_92972_;
   }

   public void m_168713_() {
      this.f_93001_ = null;
      this.f_93002_ = null;
      this.f_93000_ = 0;
   }

   public UUID m_93074_(Component p_93075_) {
      String s = StringDecomposer.m_14326_(p_93075_);
      String s1 = StringUtils.substringBetween(s, "<", ">");
      return s1 == null ? Util.f_137441_ : this.f_92986_.m_91266_().m_100678_(s1);
   }

   public void m_93051_(ChatType p_93052_, Component p_93053_, UUID p_93054_) {
      if (!this.f_92986_.m_91246_(p_93054_)) {
         if (!this.f_92986_.f_91066_.f_92084_ || !this.f_92986_.m_91246_(this.m_93074_(p_93053_))) {
            for(ChatListener chatlistener : this.f_92979_.get(p_93052_)) {
               chatlistener.m_7437_(p_93052_, p_93053_, p_93054_);
            }

         }
      }
   }

   public ChatComponent m_93076_() {
      return this.f_92988_;
   }

   public int m_93079_() {
      return this.f_92989_;
   }

   public Font m_93082_() {
      return this.f_92986_.f_91062_;
   }

   public SpectatorGui m_93085_() {
      return this.f_92997_;
   }

   public PlayerTabOverlay m_93088_() {
      return this.f_92998_;
   }

   public void m_93089_() {
      this.f_92998_.m_94529_();
      this.f_92999_.m_93703_();
      this.f_92986_.m_91300_().m_94919_();
      this.f_92986_.f_91066_.f_92063_ = false;
      this.f_92988_.m_93795_(true);
   }

   public BossHealthOverlay m_93090_() {
      return this.f_92999_;
   }

   public void m_93091_() {
      this.f_92995_.m_94040_();
   }

   @OnlyIn(Dist.CLIENT)
   static enum HeartType {
      CONTAINER(0, false),
      NORMAL(2, true),
      POISIONED(4, true),
      WITHERED(6, true),
      ABSORBING(8, false),
      FROZEN(9, false);

      private final int f_168722_;
      private final boolean f_168723_;

      private HeartType(int p_168729_, boolean p_168730_) {
         this.f_168722_ = p_168729_;
         this.f_168723_ = p_168730_;
      }

      public int m_168734_(boolean p_168735_, boolean p_168736_) {
         int i;
         if (this == CONTAINER) {
            i = p_168736_ ? 1 : 0;
         } else {
            int j = p_168735_ ? 1 : 0;
            int k = this.f_168723_ && p_168736_ ? 2 : 0;
            i = j + k;
         }

         return 16 + (this.f_168722_ * 2 + i) * 9;
      }

      static Gui.HeartType m_168732_(Player p_168733_) {
         Gui.HeartType gui$hearttype;
         if (p_168733_.m_21023_(MobEffects.f_19614_)) {
            gui$hearttype = POISIONED;
         } else if (p_168733_.m_21023_(MobEffects.f_19615_)) {
            gui$hearttype = WITHERED;
         } else if (p_168733_.m_146890_()) {
            gui$hearttype = FROZEN;
         } else {
            gui$hearttype = NORMAL;
         }

         return gui$hearttype;
      }
   }
}
