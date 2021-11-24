package net.minecraft.client;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Options {
   static final Logger f_92077_ = LogManager.getLogger();
   private static final Gson f_92078_ = new Gson();
   private static final TypeToken<List<String>> f_92079_ = new TypeToken<List<String>>() {
   };
   public static final int f_168406_ = 2;
   public static final int f_168407_ = 4;
   public static final int f_168409_ = 8;
   public static final int f_168410_ = 12;
   public static final int f_168411_ = 16;
   public static final int f_168412_ = 32;
   private static final Splitter f_92107_ = Splitter.on(':').limit(2);
   private static final float f_168408_ = 1.0F;
   public boolean f_168413_;
   public double f_92053_ = 0.5D;
   public int f_92106_;
   public float f_92112_ = 1.0F;
   public int f_92113_ = 120;
   public CloudStatus f_92114_ = CloudStatus.FANCY;
   public GraphicsStatus f_92115_ = GraphicsStatus.FANCY;
   public AmbientOcclusionStatus f_92116_ = AmbientOcclusionStatus.MAX;
   public List<String> f_92117_ = Lists.newArrayList();
   public List<String> f_92118_ = Lists.newArrayList();
   public ChatVisiblity f_92119_ = ChatVisiblity.FULL;
   public double f_92120_ = 1.0D;
   public double f_92121_;
   public double f_92122_ = 0.5D;
   @Nullable
   public String f_92123_;
   public boolean f_92124_;
   public boolean f_92125_;
   public boolean f_92126_ = true;
   private final Set<PlayerModelPart> f_92108_ = EnumSet.allOf(PlayerModelPart.class);
   public HumanoidArm f_92127_ = HumanoidArm.RIGHT;
   public int f_92128_;
   public int f_92129_;
   public boolean f_92130_ = true;
   public double f_92131_ = 1.0D;
   public double f_92132_ = 1.0D;
   public double f_92133_ = (double)0.44366196F;
   public double f_92134_ = 1.0D;
   public double f_92135_;
   public int f_92027_ = 4;
   private final Object2FloatMap<SoundSource> f_92109_ = Util.m_137469_(new Object2FloatOpenHashMap<>(), (p_168434_) -> {
      p_168434_.defaultReturnValue(1.0F);
   });
   public boolean f_92028_ = true;
   public AttackIndicatorStatus f_92029_ = AttackIndicatorStatus.CROSSHAIR;
   public TutorialSteps f_92030_ = TutorialSteps.MOVEMENT;
   public boolean f_92031_ = false;
   public boolean f_168405_ = false;
   public int f_92032_ = 2;
   public double f_92033_ = 1.0D;
   public boolean f_92034_ = true;
   public int f_92035_ = 1;
   public boolean f_92036_ = true;
   public boolean f_92037_ = true;
   public boolean f_92038_ = true;
   public boolean f_92039_ = true;
   public boolean f_92040_ = true;
   public boolean f_92041_ = true;
   public boolean f_92042_ = true;
   public boolean f_92043_;
   public boolean f_92044_;
   public boolean f_92045_;
   public boolean f_92046_ = true;
   public boolean f_92047_;
   public boolean f_92048_ = true;
   public boolean f_92049_;
   public boolean f_92050_ = true;
   public boolean f_92051_;
   public boolean f_92052_;
   public boolean f_92080_ = true;
   public boolean f_92081_;
   public boolean f_92082_;
   public boolean f_92083_;
   public boolean f_92084_ = true;
   public final KeyMapping f_92085_ = new KeyMapping("key.forward", 87, "key.categories.movement");
   public final KeyMapping f_92086_ = new KeyMapping("key.left", 65, "key.categories.movement");
   public final KeyMapping f_92087_ = new KeyMapping("key.back", 83, "key.categories.movement");
   public final KeyMapping f_92088_ = new KeyMapping("key.right", 68, "key.categories.movement");
   public final KeyMapping f_92089_ = new KeyMapping("key.jump", 32, "key.categories.movement");
   public final KeyMapping f_92090_ = new ToggleKeyMapping("key.sneak", 340, "key.categories.movement", () -> {
      return this.f_92081_;
   });
   public final KeyMapping f_92091_ = new ToggleKeyMapping("key.sprint", 341, "key.categories.movement", () -> {
      return this.f_92082_;
   });
   public final KeyMapping f_92092_ = new KeyMapping("key.inventory", 69, "key.categories.inventory");
   public final KeyMapping f_92093_ = new KeyMapping("key.swapOffhand", 70, "key.categories.inventory");
   public final KeyMapping f_92094_ = new KeyMapping("key.drop", 81, "key.categories.inventory");
   public final KeyMapping f_92095_ = new KeyMapping("key.use", InputConstants.Type.MOUSE, 1, "key.categories.gameplay");
   public final KeyMapping f_92096_ = new KeyMapping("key.attack", InputConstants.Type.MOUSE, 0, "key.categories.gameplay");
   public final KeyMapping f_92097_ = new KeyMapping("key.pickItem", InputConstants.Type.MOUSE, 2, "key.categories.gameplay");
   public final KeyMapping f_92098_ = new KeyMapping("key.chat", 84, "key.categories.multiplayer");
   public final KeyMapping f_92099_ = new KeyMapping("key.playerlist", 258, "key.categories.multiplayer");
   public final KeyMapping f_92100_ = new KeyMapping("key.command", 47, "key.categories.multiplayer");
   public final KeyMapping f_92101_ = new KeyMapping("key.socialInteractions", 80, "key.categories.multiplayer");
   public final KeyMapping f_92102_ = new KeyMapping("key.screenshot", 291, "key.categories.misc");
   public final KeyMapping f_92103_ = new KeyMapping("key.togglePerspective", 294, "key.categories.misc");
   public final KeyMapping f_92104_ = new KeyMapping("key.smoothCamera", InputConstants.f_84822_.m_84873_(), "key.categories.misc");
   public final KeyMapping f_92105_ = new KeyMapping("key.fullscreen", 300, "key.categories.misc");
   public final KeyMapping f_92054_ = new KeyMapping("key.spectatorOutlines", InputConstants.f_84822_.m_84873_(), "key.categories.misc");
   public final KeyMapping f_92055_ = new KeyMapping("key.advancements", 76, "key.categories.misc");
   public final KeyMapping[] f_92056_ = new KeyMapping[]{new KeyMapping("key.hotbar.1", 49, "key.categories.inventory"), new KeyMapping("key.hotbar.2", 50, "key.categories.inventory"), new KeyMapping("key.hotbar.3", 51, "key.categories.inventory"), new KeyMapping("key.hotbar.4", 52, "key.categories.inventory"), new KeyMapping("key.hotbar.5", 53, "key.categories.inventory"), new KeyMapping("key.hotbar.6", 54, "key.categories.inventory"), new KeyMapping("key.hotbar.7", 55, "key.categories.inventory"), new KeyMapping("key.hotbar.8", 56, "key.categories.inventory"), new KeyMapping("key.hotbar.9", 57, "key.categories.inventory")};
   public final KeyMapping f_92057_ = new KeyMapping("key.saveToolbarActivator", 67, "key.categories.creative");
   public final KeyMapping f_92058_ = new KeyMapping("key.loadToolbarActivator", 88, "key.categories.creative");
   public KeyMapping[] f_92059_ = ArrayUtils.addAll(new KeyMapping[]{this.f_92096_, this.f_92095_, this.f_92085_, this.f_92086_, this.f_92087_, this.f_92088_, this.f_92089_, this.f_92090_, this.f_92091_, this.f_92094_, this.f_92092_, this.f_92098_, this.f_92099_, this.f_92097_, this.f_92100_, this.f_92101_, this.f_92102_, this.f_92103_, this.f_92104_, this.f_92105_, this.f_92054_, this.f_92093_, this.f_92057_, this.f_92058_, this.f_92055_}, this.f_92056_);
   protected Minecraft f_92060_;
   private final File f_92110_;
   public Difficulty f_92061_ = Difficulty.NORMAL;
   public boolean f_92062_;
   private CameraType f_92111_ = CameraType.FIRST_PERSON;
   public boolean f_92063_;
   public boolean f_92064_;
   public boolean f_92065_;
   public String f_92066_ = "";
   public boolean f_92067_;
   public double f_92068_ = 70.0D;
   public float f_92069_ = 1.0F;
   public float f_92070_ = 1.0F;
   public double f_92071_;
   public int f_92072_;
   public ParticleStatus f_92073_ = ParticleStatus.ALL;
   public NarratorStatus f_92074_ = NarratorStatus.OFF;
   public String f_92075_ = "en_us";
   public boolean f_92076_;

   public Options(Minecraft p_92138_, File p_92139_) {
      setForgeKeybindProperties();
      this.f_92060_ = p_92138_;
      this.f_92110_ = new File(p_92139_, "options.txt");
      if (p_92138_.m_91103_() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         Option.f_91675_.m_92219_(32.0F);
      } else {
         Option.f_91675_.m_92219_(16.0F);
      }

      this.f_92106_ = p_92138_.m_91103_() ? 12 : 8;
      this.f_92076_ = Util.m_137581_() == Util.OS.WINDOWS;
      this.m_92140_();
   }

   public float m_92141_(float p_92142_) {
      return this.f_92050_ ? p_92142_ : (float)this.f_92122_;
   }

   public int m_92170_(float p_92171_) {
      return (int)(this.m_92141_(p_92171_) * 255.0F) << 24 & -16777216;
   }

   public int m_92143_(int p_92144_) {
      return this.f_92050_ ? p_92144_ : (int)(this.f_92122_ * 255.0D) << 24 & -16777216;
   }

   public void m_92159_(KeyMapping p_92160_, InputConstants.Key p_92161_) {
      p_92160_.m_90848_(p_92161_);
      this.m_92169_();
   }

   private void m_168427_(Options.FieldAccess p_168428_) {
      this.f_92036_ = p_168428_.m_142682_("autoJump", this.f_92036_);
      this.f_92037_ = p_168428_.m_142682_("autoSuggestions", this.f_92037_);
      this.f_92038_ = p_168428_.m_142682_("chatColors", this.f_92038_);
      this.f_92039_ = p_168428_.m_142682_("chatLinks", this.f_92039_);
      this.f_92040_ = p_168428_.m_142682_("chatLinksPrompt", this.f_92040_);
      this.f_92041_ = p_168428_.m_142682_("enableVsync", this.f_92041_);
      this.f_92042_ = p_168428_.m_142682_("entityShadows", this.f_92042_);
      this.f_92043_ = p_168428_.m_142682_("forceUnicodeFont", this.f_92043_);
      this.f_92045_ = p_168428_.m_142682_("discrete_mouse_scroll", this.f_92045_);
      this.f_92044_ = p_168428_.m_142682_("invertYMouse", this.f_92044_);
      this.f_92046_ = p_168428_.m_142682_("realmsNotifications", this.f_92046_);
      this.f_92047_ = p_168428_.m_142682_("reducedDebugInfo", this.f_92047_);
      this.f_92048_ = p_168428_.m_142682_("snooperEnabled", this.f_92048_);
      this.f_92049_ = p_168428_.m_142682_("showSubtitles", this.f_92049_);
      this.f_92051_ = p_168428_.m_142682_("touchscreen", this.f_92051_);
      this.f_92052_ = p_168428_.m_142682_("fullscreen", this.f_92052_);
      this.f_92080_ = p_168428_.m_142682_("bobView", this.f_92080_);
      this.f_92081_ = p_168428_.m_142682_("toggleCrouch", this.f_92081_);
      this.f_92082_ = p_168428_.m_142682_("toggleSprint", this.f_92082_);
      this.f_168413_ = p_168428_.m_142682_("darkMojangStudiosBackground", this.f_168413_);
      this.f_92053_ = p_168428_.m_142547_("mouseSensitivity", this.f_92053_);
      this.f_92068_ = p_168428_.m_142547_("fov", (this.f_92068_ - 70.0D) / 40.0D) * 40.0D + 70.0D;
      this.f_92069_ = p_168428_.m_142432_("screenEffectScale", this.f_92069_);
      this.f_92070_ = p_168428_.m_142432_("fovEffectScale", this.f_92070_);
      this.f_92071_ = p_168428_.m_142547_("gamma", this.f_92071_);
      this.f_92106_ = p_168428_.m_142708_("renderDistance", this.f_92106_);
      this.f_92112_ = p_168428_.m_142432_("entityDistanceScaling", this.f_92112_);
      this.f_92072_ = p_168428_.m_142708_("guiScale", this.f_92072_);
      this.f_92073_ = p_168428_.m_142316_("particles", this.f_92073_, ParticleStatus::m_92196_, ParticleStatus::m_92198_);
      this.f_92113_ = p_168428_.m_142708_("maxFps", this.f_92113_);
      this.f_92061_ = p_168428_.m_142316_("difficulty", this.f_92061_, Difficulty::m_19029_, Difficulty::m_19028_);
      this.f_92115_ = p_168428_.m_142316_("graphicsMode", this.f_92115_, GraphicsStatus::m_90774_, GraphicsStatus::m_90773_);
      this.f_92116_ = p_168428_.m_142634_("ao", this.f_92116_, Options::m_168446_, (p_168424_) -> {
         return Integer.toString(p_168424_.m_90486_());
      });
      this.f_92032_ = p_168428_.m_142708_("biomeBlendRadius", this.f_92032_);
      this.f_92114_ = p_168428_.m_142634_("renderClouds", this.f_92114_, Options::m_168444_, Options::m_168425_);
      this.f_92117_ = p_168428_.m_142634_("resourcePacks", this.f_92117_, Options::m_168442_, f_92078_::toJson);
      this.f_92118_ = p_168428_.m_142634_("incompatibleResourcePacks", this.f_92118_, Options::m_168442_, f_92078_::toJson);
      this.f_92066_ = p_168428_.m_141943_("lastServer", this.f_92066_);
      this.f_92075_ = p_168428_.m_141943_("lang", this.f_92075_);
      this.f_92119_ = p_168428_.m_142316_("chatVisibility", this.f_92119_, ChatVisiblity::m_35966_, ChatVisiblity::m_35965_);
      this.f_92120_ = p_168428_.m_142547_("chatOpacity", this.f_92120_);
      this.f_92121_ = p_168428_.m_142547_("chatLineSpacing", this.f_92121_);
      this.f_92122_ = p_168428_.m_142547_("textBackgroundOpacity", this.f_92122_);
      this.f_92050_ = p_168428_.m_142682_("backgroundForChatOnly", this.f_92050_);
      this.f_92124_ = p_168428_.m_142682_("hideServerAddress", this.f_92124_);
      this.f_92125_ = p_168428_.m_142682_("advancedItemTooltips", this.f_92125_);
      this.f_92126_ = p_168428_.m_142682_("pauseOnLostFocus", this.f_92126_);
      this.f_92128_ = p_168428_.m_142708_("overrideWidth", this.f_92128_);
      this.f_92129_ = p_168428_.m_142708_("overrideHeight", this.f_92129_);
      this.f_92130_ = p_168428_.m_142682_("heldItemTooltips", this.f_92130_);
      this.f_92134_ = p_168428_.m_142547_("chatHeightFocused", this.f_92134_);
      this.f_92135_ = p_168428_.m_142547_("chatDelay", this.f_92135_);
      this.f_92133_ = p_168428_.m_142547_("chatHeightUnfocused", this.f_92133_);
      this.f_92131_ = p_168428_.m_142547_("chatScale", this.f_92131_);
      this.f_92132_ = p_168428_.m_142547_("chatWidth", this.f_92132_);
      this.f_92027_ = p_168428_.m_142708_("mipmapLevels", this.f_92027_);
      this.f_92028_ = p_168428_.m_142682_("useNativeTransport", this.f_92028_);
      this.f_92127_ = p_168428_.m_142634_("mainHand", this.f_92127_, Options::m_168448_, Options::m_168414_);
      this.f_92029_ = p_168428_.m_142316_("attackIndicator", this.f_92029_, AttackIndicatorStatus::m_90509_, AttackIndicatorStatus::m_90508_);
      this.f_92074_ = p_168428_.m_142316_("narrator", this.f_92074_, NarratorStatus::m_91619_, NarratorStatus::m_91618_);
      this.f_92030_ = p_168428_.m_142634_("tutorialStep", this.f_92030_, TutorialSteps::m_120642_, TutorialSteps::m_120639_);
      this.f_92033_ = p_168428_.m_142547_("mouseWheelSensitivity", this.f_92033_);
      this.f_92034_ = p_168428_.m_142682_("rawMouseInput", this.f_92034_);
      this.f_92035_ = p_168428_.m_142708_("glDebugVerbosity", this.f_92035_);
      this.f_92083_ = p_168428_.m_142682_("skipMultiplayerWarning", this.f_92083_);
      this.f_92084_ = p_168428_.m_142682_("hideMatchedNames", this.f_92084_);
      this.f_92031_ = p_168428_.m_142682_("joinedFirstServer", this.f_92031_);
      this.f_168405_ = p_168428_.m_142682_("hideBundleTutorial", this.f_168405_);
      this.f_92076_ = p_168428_.m_142682_("syncChunkWrites", this.f_92076_);

      for(KeyMapping keymapping : this.f_92059_) {
         String s = keymapping.m_90865_() + (keymapping.getKeyModifier() != net.minecraftforge.client.settings.KeyModifier.NONE ? ":" + keymapping.getKeyModifier() : "");
         String s1 = p_168428_.m_141943_("key_" + keymapping.m_90860_(), s);
         if (!s.equals(s1)) {
            if (s1.indexOf(':') != -1) {
               String[] pts = s1.split(":");
               keymapping.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.valueFromString(pts[1]), InputConstants.m_84851_(pts[0]));
            } else
               keymapping.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.NONE, InputConstants.m_84851_(s1));
         }
      }

      for(SoundSource soundsource : SoundSource.values()) {
         this.f_92109_.computeFloat(soundsource, (p_168431_, p_168432_) -> {
            return p_168428_.m_142432_("soundCategory_" + p_168431_.m_12676_(), p_168432_ != null ? p_168432_ : 1.0F);
         });
      }

      for(PlayerModelPart playermodelpart : PlayerModelPart.values()) {
         boolean flag = this.f_92108_.contains(playermodelpart);
         boolean flag1 = p_168428_.m_142682_("modelPart_" + playermodelpart.m_36446_(), flag);
         if (flag1 != flag) {
            this.m_92154_(playermodelpart, flag1);
         }
      }

   }

   public void m_92140_() {
      try {
         if (!this.f_92110_.exists()) {
            return;
         }

         this.f_92109_.clear();
         CompoundTag compoundtag = new CompoundTag();
         BufferedReader bufferedreader = Files.newReader(this.f_92110_, Charsets.UTF_8);

         try {
            bufferedreader.lines().forEach((p_168439_) -> {
               try {
                  Iterator<String> iterator = f_92107_.split(p_168439_).iterator();
                  compoundtag.m_128359_(iterator.next(), iterator.next());
               } catch (Exception exception1) {
                  f_92077_.warn("Skipping bad option: {}", (Object)p_168439_);
               }

            });
         } catch (Throwable throwable1) {
            if (bufferedreader != null) {
               try {
                  bufferedreader.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (bufferedreader != null) {
            bufferedreader.close();
         }

         final CompoundTag compoundtag1 = this.m_92164_(compoundtag);
         if (!compoundtag1.m_128441_("graphicsMode") && compoundtag1.m_128441_("fancyGraphics")) {
            if (m_168435_(compoundtag1.m_128461_("fancyGraphics"))) {
               this.f_92115_ = GraphicsStatus.FANCY;
            } else {
               this.f_92115_ = GraphicsStatus.FAST;
            }
         }

         this.m_168427_(new Options.FieldAccess() {
            @Nullable
            private String m_168458_(String p_168459_) {
               return compoundtag1.m_128441_(p_168459_) ? compoundtag1.m_128461_(p_168459_) : null;
            }

            public int m_142708_(String p_168467_, int p_168468_) {
               String s = this.m_168458_(p_168467_);
               if (s != null) {
                  try {
                     return Integer.parseInt(s);
                  } catch (NumberFormatException numberformatexception) {
                     Options.f_92077_.warn("Invalid integer value for option {} = {}", p_168467_, s, numberformatexception);
                  }
               }

               return p_168468_;
            }

            public boolean m_142682_(String p_168483_, boolean p_168484_) {
               String s = this.m_168458_(p_168483_);
               return s != null ? Options.m_168435_(s) : p_168484_;
            }

            public String m_141943_(String p_168480_, String p_168481_) {
               return MoreObjects.firstNonNull(this.m_168458_(p_168480_), p_168481_);
            }

            public double m_142547_(String p_168461_, double p_168462_) {
               String s = this.m_168458_(p_168461_);
               if (s != null) {
                  if (Options.m_168435_(s)) {
                     return 1.0D;
                  }

                  if (Options.m_168440_(s)) {
                     return 0.0D;
                  }

                  try {
                     return Double.parseDouble(s);
                  } catch (NumberFormatException numberformatexception) {
                     Options.f_92077_.warn("Invalid floating point value for option {} = {}", p_168461_, s, numberformatexception);
                  }
               }

               return p_168462_;
            }

            public float m_142432_(String p_168464_, float p_168465_) {
               String s = this.m_168458_(p_168464_);
               if (s != null) {
                  if (Options.m_168435_(s)) {
                     return 1.0F;
                  }

                  if (Options.m_168440_(s)) {
                     return 0.0F;
                  }

                  try {
                     return Float.parseFloat(s);
                  } catch (NumberFormatException numberformatexception) {
                     Options.f_92077_.warn("Invalid floating point value for option {} = {}", p_168464_, s, numberformatexception);
                  }
               }

               return p_168465_;
            }

            public <T> T m_142634_(String p_168470_, T p_168471_, Function<String, T> p_168472_, Function<T, String> p_168473_) {
               String s = this.m_168458_(p_168470_);
               return (T)(s == null ? p_168471_ : p_168472_.apply(s));
            }

            public <T> T m_142316_(String p_168475_, T p_168476_, IntFunction<T> p_168477_, ToIntFunction<T> p_168478_) {
               String s = this.m_168458_(p_168475_);
               if (s != null) {
                  try {
                     return p_168477_.apply(Integer.parseInt(s));
                  } catch (Exception exception1) {
                     Options.f_92077_.warn("Invalid integer value for option {} = {}", p_168475_, s, exception1);
                  }
               }

               return p_168476_;
            }
         });
         if (compoundtag1.m_128441_("fullscreenResolution")) {
            this.f_92123_ = compoundtag1.m_128461_("fullscreenResolution");
         }

         if (this.f_92060_.m_91268_() != null) {
            this.f_92060_.m_91268_().m_85380_(this.f_92113_);
         }

         KeyMapping.m_90854_();
      } catch (Exception exception) {
         f_92077_.error("Failed to load options", (Throwable)exception);
      }

   }

   static boolean m_168435_(String p_168436_) {
      return "true".equals(p_168436_);
   }

   static boolean m_168440_(String p_168441_) {
      return "false".equals(p_168441_);
   }

   private CompoundTag m_92164_(CompoundTag p_92165_) {
      int i = 0;

      try {
         i = Integer.parseInt(p_92165_.m_128461_("version"));
      } catch (RuntimeException runtimeexception) {
      }

      return NbtUtils.m_129213_(this.f_92060_.m_91295_(), DataFixTypes.OPTIONS, p_92165_, i);
   }

   public void m_92169_() {
      if (net.minecraftforge.fmlclient.ClientModLoader.isLoading()) return; //Don't save settings before mods add keybindigns and the like to prevent them from being deleted.
      try {
         final PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.f_92110_), StandardCharsets.UTF_8));

         try {
            printwriter.println("version:" + SharedConstants.m_136187_().getWorldVersion());
            this.m_168427_(new Options.FieldAccess() {
               public void m_168490_(String p_168491_) {
                  printwriter.print(p_168491_);
                  printwriter.print(':');
               }

               public int m_142708_(String p_168499_, int p_168500_) {
                  this.m_168490_(p_168499_);
                  printwriter.println(p_168500_);
                  return p_168500_;
               }

               public boolean m_142682_(String p_168515_, boolean p_168516_) {
                  this.m_168490_(p_168515_);
                  printwriter.println(p_168516_);
                  return p_168516_;
               }

               public String m_141943_(String p_168512_, String p_168513_) {
                  this.m_168490_(p_168512_);
                  printwriter.println(p_168513_);
                  return p_168513_;
               }

               public double m_142547_(String p_168493_, double p_168494_) {
                  this.m_168490_(p_168493_);
                  printwriter.println(p_168494_);
                  return p_168494_;
               }

               public float m_142432_(String p_168496_, float p_168497_) {
                  this.m_168490_(p_168496_);
                  printwriter.println(p_168497_);
                  return p_168497_;
               }

               public <T> T m_142634_(String p_168502_, T p_168503_, Function<String, T> p_168504_, Function<T, String> p_168505_) {
                  this.m_168490_(p_168502_);
                  printwriter.println(p_168505_.apply(p_168503_));
                  return p_168503_;
               }

               public <T> T m_142316_(String p_168507_, T p_168508_, IntFunction<T> p_168509_, ToIntFunction<T> p_168510_) {
                  this.m_168490_(p_168507_);
                  printwriter.println(p_168510_.applyAsInt(p_168508_));
                  return p_168508_;
               }
            });
            if (this.f_92060_.m_91268_().m_85436_().isPresent()) {
               printwriter.println("fullscreenResolution:" + this.f_92060_.m_91268_().m_85436_().get().m_85342_());
            }
         } catch (Throwable throwable1) {
            try {
               printwriter.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }

            throw throwable1;
         }

         printwriter.close();
      } catch (Exception exception) {
         f_92077_.error("Failed to save options", (Throwable)exception);
      }

      this.m_92172_();
   }

   public float m_92147_(SoundSource p_92148_) {
      return this.f_92109_.getFloat(p_92148_);
   }

   public void m_92149_(SoundSource p_92150_, float p_92151_) {
      this.f_92109_.put(p_92150_, p_92151_);
      this.f_92060_.m_91106_().m_120358_(p_92150_, p_92151_);
   }

   public void m_92172_() {
      if (this.f_92060_.f_91074_ != null) {
         int i = 0;

         for(PlayerModelPart playermodelpart : this.f_92108_) {
            i |= playermodelpart.m_36445_();
         }

         this.f_92060_.f_91074_.f_108617_.m_104955_(new ServerboundClientInformationPacket(this.f_92075_, this.f_92106_, this.f_92119_, this.f_92038_, i, this.f_92127_, this.f_92060_.m_167974_()));
      }

   }

   private void m_92154_(PlayerModelPart p_92155_, boolean p_92156_) {
      if (p_92156_) {
         this.f_92108_.add(p_92155_);
      } else {
         this.f_92108_.remove(p_92155_);
      }

   }

   public boolean m_168416_(PlayerModelPart p_168417_) {
      return this.f_92108_.contains(p_168417_);
   }

   public void m_168418_(PlayerModelPart p_168419_, boolean p_168420_) {
      this.m_92154_(p_168419_, p_168420_);
      this.m_92172_();
   }

   public CloudStatus m_92174_() {
      return this.f_92106_ >= 4 ? this.f_92114_ : CloudStatus.OFF;
   }

   public boolean m_92175_() {
      return this.f_92028_;
   }

   public void m_92145_(PackRepository p_92146_) {
      Set<String> set = Sets.newLinkedHashSet();
      Iterator<String> iterator = this.f_92117_.iterator();

      while(iterator.hasNext()) {
         String s = iterator.next();
         Pack pack = p_92146_.m_10507_(s);
         if (pack == null && !s.startsWith("file/")) {
            pack = p_92146_.m_10507_("file/" + s);
         }

         if (pack == null) {
            f_92077_.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", (Object)s);
            iterator.remove();
         } else if (!pack.m_10443_().m_10489_() && !this.f_92118_.contains(s)) {
            f_92077_.warn("Removed resource pack {} from options because it is no longer compatible", (Object)s);
            iterator.remove();
         } else if (pack.m_10443_().m_10489_() && this.f_92118_.contains(s)) {
            f_92077_.info("Removed resource pack {} from incompatibility list because it's now compatible", (Object)s);
            this.f_92118_.remove(s);
         } else {
            set.add(pack.m_10446_());
         }
      }

      p_92146_.m_10509_(set);
   }

   private void setForgeKeybindProperties() {
      net.minecraftforge.client.settings.KeyConflictContext inGame = net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
      f_92085_.setKeyConflictContext(inGame);
      f_92086_.setKeyConflictContext(inGame);
      f_92087_.setKeyConflictContext(inGame);
      f_92088_.setKeyConflictContext(inGame);
      f_92089_.setKeyConflictContext(inGame);
      f_92090_.setKeyConflictContext(inGame);
      f_92091_.setKeyConflictContext(inGame);
      f_92096_.setKeyConflictContext(inGame);
      f_92098_.setKeyConflictContext(inGame);
      f_92099_.setKeyConflictContext(inGame);
      f_92100_.setKeyConflictContext(inGame);
      f_92103_.setKeyConflictContext(inGame);
      f_92104_.setKeyConflictContext(inGame);
   }

   public CameraType m_92176_() {
      return this.f_92111_;
   }

   public void m_92157_(CameraType p_92158_) {
      this.f_92111_ = p_92158_;
   }

   private static List<String> m_168442_(String p_168443_) {
      List<String> list = GsonHelper.m_13785_(f_92078_, p_168443_, f_92079_);
      return (List<String>)(list != null ? list : Lists.newArrayList());
   }

   private static CloudStatus m_168444_(String p_168445_) {
      switch(p_168445_) {
      case "true":
         return CloudStatus.FANCY;
      case "fast":
         return CloudStatus.FAST;
      case "false":
      default:
         return CloudStatus.OFF;
      }
   }

   private static String m_168425_(CloudStatus p_168426_) {
      switch(p_168426_) {
      case FANCY:
         return "true";
      case FAST:
         return "fast";
      case OFF:
      default:
         return "false";
      }
   }

   private static AmbientOcclusionStatus m_168446_(String p_168447_) {
      if (m_168435_(p_168447_)) {
         return AmbientOcclusionStatus.MAX;
      } else {
         return m_168440_(p_168447_) ? AmbientOcclusionStatus.OFF : AmbientOcclusionStatus.m_90487_(Integer.parseInt(p_168447_));
      }
   }

   private static HumanoidArm m_168448_(String p_168449_) {
      return "left".equals(p_168449_) ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   private static String m_168414_(HumanoidArm p_168415_) {
      return p_168415_ == HumanoidArm.LEFT ? "left" : "right";
   }

   public File m_168450_() {
      return this.f_92110_;
   }

   public String m_168451_() {
      ImmutableList<Pair<String, String>> immutablelist = ImmutableList.<Pair<String, String>>builder().add(Pair.of("ao", String.valueOf((Object)this.f_92116_))).add(Pair.of("biomeBlendRadius", String.valueOf(this.f_92032_))).add(Pair.of("enableVsync", String.valueOf(this.f_92041_))).add(Pair.of("entityDistanceScaling", String.valueOf(this.f_92112_))).add(Pair.of("entityShadows", String.valueOf(this.f_92042_))).add(Pair.of("forceUnicodeFont", String.valueOf(this.f_92043_))).add(Pair.of("fov", String.valueOf(this.f_92068_))).add(Pair.of("fovEffectScale", String.valueOf(this.f_92070_))).add(Pair.of("fullscreen", String.valueOf(this.f_92052_))).add(Pair.of("fullscreenResolution", String.valueOf((Object)this.f_92123_))).add(Pair.of("gamma", String.valueOf(this.f_92071_))).add(Pair.of("glDebugVerbosity", String.valueOf(this.f_92035_))).add(Pair.of("graphicsMode", String.valueOf((Object)this.f_92115_))).add(Pair.of("guiScale", String.valueOf(this.f_92072_))).add(Pair.of("maxFps", String.valueOf(this.f_92113_))).add(Pair.of("mipmapLevels", String.valueOf(this.f_92027_))).add(Pair.of("narrator", String.valueOf((Object)this.f_92074_))).add(Pair.of("overrideHeight", String.valueOf(this.f_92129_))).add(Pair.of("overrideWidth", String.valueOf(this.f_92128_))).add(Pair.of("particles", String.valueOf((Object)this.f_92073_))).add(Pair.of("reducedDebugInfo", String.valueOf(this.f_92047_))).add(Pair.of("renderClouds", String.valueOf((Object)this.f_92114_))).add(Pair.of("renderDistance", String.valueOf(this.f_92106_))).add(Pair.of("resourcePacks", String.valueOf((Object)this.f_92117_))).add(Pair.of("screenEffectScale", String.valueOf(this.f_92069_))).add(Pair.of("syncChunkWrites", String.valueOf(this.f_92076_))).add(Pair.of("useNativeTransport", String.valueOf(this.f_92028_))).build();
      return immutablelist.stream().map((p_168422_) -> {
         return (String)p_168422_.getFirst() + ": " + (String)p_168422_.getSecond();
      }).collect(Collectors.joining(System.lineSeparator()));
   }

   @OnlyIn(Dist.CLIENT)
   interface FieldAccess {
      int m_142708_(String p_168523_, int p_168524_);

      boolean m_142682_(String p_168535_, boolean p_168536_);

      String m_141943_(String p_168533_, String p_168534_);

      double m_142547_(String p_168519_, double p_168520_);

      float m_142432_(String p_168521_, float p_168522_);

      <T> T m_142634_(String p_168525_, T p_168526_, Function<String, T> p_168527_, Function<T, String> p_168528_);

      <T> T m_142316_(String p_168529_, T p_168530_, IntFunction<T> p_168531_, ToIntFunction<T> p_168532_);
   }
}
