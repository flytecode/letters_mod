package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Window;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Option {
   protected static final int f_168106_ = 200;
   public static final ProgressOption f_91653_ = new ProgressOption("options.biomeBlendRadius", 0.0D, 7.0D, 1.0F, (p_91713_) -> {
      return (double)p_91713_.f_92032_;
   }, (p_92009_, p_92010_) -> {
      p_92009_.f_92032_ = Mth.m_14045_((int)p_92010_.doubleValue(), 0, 7);
      Minecraft.m_91087_().f_91060_.m_109818_();
   }, (p_92003_, p_92004_) -> {
      double d0 = p_92004_.m_92221_(p_92003_);
      int i = (int)d0 * 2 + 1;
      return p_92004_.m_91740_(new TranslatableComponent("options.biomeBlendRadius." + i));
   });
   public static final ProgressOption f_91660_ = new ProgressOption("options.chat.height.focused", 0.0D, 1.0D, 0.0F, (p_91711_) -> {
      return p_91711_.f_92134_;
   }, (p_91998_, p_91999_) -> {
      p_91998_.f_92134_ = p_91999_;
      Minecraft.m_91087_().f_91065_.m_93076_().m_93769_();
   }, (p_91992_, p_91993_) -> {
      double d0 = p_91993_.m_6917_(p_91993_.m_92221_(p_91992_));
      return p_91993_.m_91715_(ChatComponent.m_93811_(d0));
   });
   public static final ProgressOption f_91661_ = new ProgressOption("options.chat.height.unfocused", 0.0D, 1.0D, 0.0F, (p_91709_) -> {
      return p_91709_.f_92133_;
   }, (p_91987_, p_91988_) -> {
      p_91987_.f_92133_ = p_91988_;
      Minecraft.m_91087_().f_91065_.m_93076_().m_93769_();
   }, (p_91981_, p_91982_) -> {
      double d0 = p_91982_.m_6917_(p_91982_.m_92221_(p_91981_));
      return p_91982_.m_91715_(ChatComponent.m_93811_(d0));
   });
   public static final ProgressOption f_91662_ = new ProgressOption("options.chat.opacity", 0.0D, 1.0D, 0.0F, (p_91707_) -> {
      return p_91707_.f_92120_;
   }, (p_91976_, p_91977_) -> {
      p_91976_.f_92120_ = p_91977_;
      Minecraft.m_91087_().f_91065_.m_93076_().m_93769_();
   }, (p_91970_, p_91971_) -> {
      double d0 = p_91971_.m_6917_(p_91971_.m_92221_(p_91970_));
      return p_91971_.m_91762_(d0 * 0.9D + 0.1D);
   });
   public static final ProgressOption f_91663_ = new ProgressOption("options.chat.scale", 0.0D, 1.0D, 0.0F, (p_91705_) -> {
      return p_91705_.f_92131_;
   }, (p_91965_, p_91966_) -> {
      p_91965_.f_92131_ = p_91966_;
      Minecraft.m_91087_().f_91065_.m_93076_().m_93769_();
   }, (p_91959_, p_91960_) -> {
      double d0 = p_91960_.m_6917_(p_91960_.m_92221_(p_91959_));
      return (Component)(d0 == 0.0D ? CommonComponents.m_130663_(p_91960_.m_91714_(), false) : p_91960_.m_91762_(d0));
   });
   public static final ProgressOption f_91664_ = new ProgressOption("options.chat.width", 0.0D, 1.0D, 0.0F, (p_91703_) -> {
      return p_91703_.f_92132_;
   }, (p_91954_, p_91955_) -> {
      p_91954_.f_92132_ = p_91955_;
      Minecraft.m_91087_().f_91065_.m_93076_().m_93769_();
   }, (p_91948_, p_91949_) -> {
      double d0 = p_91949_.m_6917_(p_91949_.m_92221_(p_91948_));
      return p_91949_.m_91715_(ChatComponent.m_93798_(d0));
   });
   public static final ProgressOption f_91665_ = new ProgressOption("options.chat.line_spacing", 0.0D, 1.0D, 0.0F, (p_91701_) -> {
      return p_91701_.f_92121_;
   }, (p_91943_, p_91944_) -> {
      p_91943_.f_92121_ = p_91944_;
   }, (p_91937_, p_91938_) -> {
      return p_91938_.m_91762_(p_91938_.m_6917_(p_91938_.m_92221_(p_91937_)));
   });
   public static final ProgressOption f_91666_ = new ProgressOption("options.chat.delay_instant", 0.0D, 6.0D, 0.1F, (p_91699_) -> {
      return p_91699_.f_92135_;
   }, (p_91929_, p_91930_) -> {
      p_91929_.f_92135_ = p_91930_;
   }, (p_91923_, p_91924_) -> {
      double d0 = p_91924_.m_92221_(p_91923_);
      return d0 <= 0.0D ? new TranslatableComponent("options.chat.delay_none") : new TranslatableComponent("options.chat.delay", String.format("%.1f", d0));
   });
   public static final ProgressOption f_91667_ = new ProgressOption("options.fov", 30.0D, 110.0D, 1.0F, (p_91697_) -> {
      return p_91697_.f_92068_;
   }, (p_91912_, p_91913_) -> {
      p_91912_.f_92068_ = p_91913_;
      Minecraft.m_91087_().f_91060_.m_109826_();
   }, (p_91906_, p_91907_) -> {
      double d0 = p_91907_.m_92221_(p_91906_);
      if (d0 == 70.0D) {
         return p_91907_.m_91740_(new TranslatableComponent("options.fov.min"));
      } else {
         return d0 == p_91907_.m_92235_() ? p_91907_.m_91740_(new TranslatableComponent("options.fov.max")) : p_91907_.m_91764_((int)d0);
      }
   });
   private static final Component f_91651_ = new TranslatableComponent("options.fovEffectScale.tooltip");
   public static final ProgressOption f_91668_ = new ProgressOption("options.fovEffectScale", 0.0D, 1.0D, 0.0F, (p_91695_) -> {
      return Math.pow((double)p_91695_.f_92070_, 2.0D);
   }, (p_91895_, p_91896_) -> {
      p_91895_.f_92070_ = (float)Math.sqrt(p_91896_);
   }, (p_91889_, p_91890_) -> {
      double d0 = p_91890_.m_6917_(p_91890_.m_92221_(p_91889_));
      return d0 == 0.0D ? p_91890_.m_91740_(CommonComponents.f_130654_) : p_91890_.m_91762_(d0);
   }, (p_168230_) -> {
      return p_168230_.f_91062_.m_92923_(f_91651_, 200);
   });
   private static final Component f_91652_ = new TranslatableComponent("options.screenEffectScale.tooltip");
   public static final ProgressOption f_91669_ = new ProgressOption("options.screenEffectScale", 0.0D, 1.0D, 0.0F, (p_168139_) -> {
      return (double)p_168139_.f_92069_;
   }, (p_168313_, p_168314_) -> {
      p_168313_.f_92069_ = p_168314_.floatValue();
   }, (p_168310_, p_168311_) -> {
      double d0 = p_168311_.m_6917_(p_168311_.m_92221_(p_168310_));
      return d0 == 0.0D ? p_168311_.m_91740_(CommonComponents.f_130654_) : p_168311_.m_91762_(d0);
   }, (p_168215_) -> {
      return p_168215_.f_91062_.m_92923_(f_91652_, 200);
   });
   public static final ProgressOption f_91670_ = new ProgressOption("options.framerateLimit", 10.0D, 260.0D, 10.0F, (p_168137_) -> {
      return (double)p_168137_.f_92113_;
   }, (p_168301_, p_168302_) -> {
      p_168301_.f_92113_ = (int)p_168302_.doubleValue();
      Minecraft.m_91087_().m_91268_().m_85380_(p_168301_.f_92113_);
   }, (p_168298_, p_168299_) -> {
      double d0 = p_168299_.m_92221_(p_168298_);
      return d0 == p_168299_.m_92235_() ? p_168299_.m_91740_(new TranslatableComponent("options.framerateLimit.max")) : p_168299_.m_91740_(new TranslatableComponent("options.framerate", (int)d0));
   });
   public static final ProgressOption f_91671_ = new ProgressOption("options.gamma", 0.0D, 1.0D, 0.0F, (p_168135_) -> {
      return p_168135_.f_92071_;
   }, (p_168289_, p_168290_) -> {
      p_168289_.f_92071_ = p_168290_;
   }, (p_168286_, p_168287_) -> {
      double d0 = p_168287_.m_6917_(p_168287_.m_92221_(p_168286_));
      if (d0 == 0.0D) {
         return p_168287_.m_91740_(new TranslatableComponent("options.gamma.min"));
      } else {
         return d0 == 1.0D ? p_168287_.m_91740_(new TranslatableComponent("options.gamma.max")) : p_168287_.m_91743_((int)(d0 * 100.0D));
      }
   });
   public static final ProgressOption f_91672_ = new ProgressOption("options.mipmapLevels", 0.0D, 4.0D, 1.0F, (p_168133_) -> {
      return (double)p_168133_.f_92027_;
   }, (p_168277_, p_168278_) -> {
      p_168277_.f_92027_ = (int)p_168278_.doubleValue();
   }, (p_168274_, p_168275_) -> {
      double d0 = p_168275_.m_92221_(p_168274_);
      return (Component)(d0 == 0.0D ? CommonComponents.m_130663_(p_168275_.m_91714_(), false) : p_168275_.m_91764_((int)d0));
   });
   public static final ProgressOption f_91673_ = new LogaritmicProgressOption("options.mouseWheelSensitivity", 0.01D, 10.0D, 0.01F, (p_168131_) -> {
      return p_168131_.f_92033_;
   }, (p_168265_, p_168266_) -> {
      p_168265_.f_92033_ = p_168266_;
   }, (p_168262_, p_168263_) -> {
      double d0 = p_168263_.m_6917_(p_168263_.m_92221_(p_168262_));
      return p_168263_.m_91740_(new TextComponent(String.format("%.2f", p_168263_.m_6912_(d0))));
   });
   public static final CycleOption<Boolean> f_91674_ = CycleOption.m_167743_("options.rawMouseInput", (p_168129_) -> {
      return p_168129_.f_92034_;
   }, (p_168396_, p_168397_, p_168398_) -> {
      p_168396_.f_92034_ = p_168398_;
      Window window = Minecraft.m_91087_().m_91268_();
      if (window != null) {
         window.m_85424_(p_168398_);
      }

   });
   public static final ProgressOption f_91675_ = new ProgressOption("options.renderDistance", 2.0D, 16.0D, 1.0F, (p_168127_) -> {
      return (double)p_168127_.f_92106_;
   }, (p_168253_, p_168254_) -> {
      p_168253_.f_92106_ = (int)p_168254_.doubleValue();
      Minecraft.m_91087_().f_91060_.m_109826_();
   }, (p_168250_, p_168251_) -> {
      double d0 = p_168251_.m_92221_(p_168250_);
      return p_168251_.m_91740_(new TranslatableComponent("options.chunks", (int)d0));
   });
   public static final ProgressOption f_91676_ = new ProgressOption("options.entityDistanceScaling", 0.5D, 5.0D, 0.25F, (p_168125_) -> {
      return (double)p_168125_.f_92112_;
   }, (p_168241_, p_168242_) -> {
      p_168241_.f_92112_ = (float)p_168242_.doubleValue();
   }, (p_168238_, p_168239_) -> {
      double d0 = p_168239_.m_92221_(p_168238_);
      return p_168239_.m_91762_(d0);
   });
   public static final ProgressOption f_91677_ = new ProgressOption("options.sensitivity", 0.0D, 1.0D, 0.0F, (p_168123_) -> {
      return p_168123_.f_92053_;
   }, (p_168226_, p_168227_) -> {
      p_168226_.f_92053_ = p_168227_;
   }, (p_168223_, p_168224_) -> {
      double d0 = p_168224_.m_6917_(p_168224_.m_92221_(p_168223_));
      if (d0 == 0.0D) {
         return p_168224_.m_91740_(new TranslatableComponent("options.sensitivity.min"));
      } else {
         return d0 == 1.0D ? p_168224_.m_91740_(new TranslatableComponent("options.sensitivity.max")) : p_168224_.m_91762_(2.0D * d0);
      }
   });
   public static final ProgressOption f_91678_ = new ProgressOption("options.accessibility.text_background_opacity", 0.0D, 1.0D, 0.0F, (p_168121_) -> {
      return p_168121_.f_92122_;
   }, (p_168200_, p_168201_) -> {
      p_168200_.f_92122_ = p_168201_;
      Minecraft.m_91087_().f_91065_.m_93076_().m_93769_();
   }, (p_168197_, p_168198_) -> {
      return p_168198_.m_91762_(p_168198_.m_6917_(p_168198_.m_92221_(p_168197_)));
   });
   public static final CycleOption<AmbientOcclusionStatus> f_91679_ = CycleOption.m_167764_("options.ao", AmbientOcclusionStatus.values(), (p_168143_) -> {
      return new TranslatableComponent(p_168143_.m_90489_());
   }, (p_168119_) -> {
      return p_168119_.f_92116_;
   }, (p_168165_, p_168166_, p_168167_) -> {
      p_168165_.f_92116_ = p_168167_;
      Minecraft.m_91087_().f_91060_.m_109818_();
   });
   public static final CycleOption<AttackIndicatorStatus> f_91680_ = CycleOption.m_167764_("options.attackIndicator", AttackIndicatorStatus.values(), (p_168145_) -> {
      return new TranslatableComponent(p_168145_.m_90511_());
   }, (p_168117_) -> {
      return p_168117_.f_92029_;
   }, (p_168169_, p_168170_, p_168171_) -> {
      p_168169_.f_92029_ = p_168171_;
   });
   public static final CycleOption<ChatVisiblity> f_91681_ = CycleOption.m_167764_("options.chat.visibility", ChatVisiblity.values(), (p_168141_) -> {
      return new TranslatableComponent(p_168141_.m_35968_());
   }, (p_168115_) -> {
      return p_168115_.f_92119_;
   }, (p_168161_, p_168162_, p_168163_) -> {
      p_168161_.f_92119_ = p_168163_;
   });
   private static final Component f_91654_ = new TranslatableComponent("options.graphics.fast.tooltip");
   private static final Component f_91655_ = new TranslatableComponent("options.graphics.fabulous.tooltip", (new TranslatableComponent("options.graphics.fabulous")).m_130940_(ChatFormatting.ITALIC));
   private static final Component f_91656_ = new TranslatableComponent("options.graphics.fancy.tooltip");
   public static final CycleOption<GraphicsStatus> f_91682_ = CycleOption.m_167729_("options.graphics", Arrays.asList(GraphicsStatus.values()), Stream.of(GraphicsStatus.values()).filter((p_168213_) -> {
      return p_168213_ != GraphicsStatus.FABULOUS;
   }).collect(Collectors.toList()), () -> {
      return Minecraft.m_91087_().m_91105_().m_109251_();
   }, (p_168149_) -> {
      MutableComponent mutablecomponent = new TranslatableComponent(p_168149_.m_90776_());
      return p_168149_ == GraphicsStatus.FABULOUS ? mutablecomponent.m_130940_(ChatFormatting.ITALIC) : mutablecomponent;
   }, (p_168113_) -> {
      return p_168113_.f_92115_;
   }, (p_168177_, p_168178_, p_168179_) -> {
      Minecraft minecraft = Minecraft.m_91087_();
      GpuWarnlistManager gpuwarnlistmanager = minecraft.m_91105_();
      if (p_168179_ == GraphicsStatus.FABULOUS && gpuwarnlistmanager.m_109240_()) {
         gpuwarnlistmanager.m_109247_();
      } else {
         p_168177_.f_92115_ = p_168179_;
         minecraft.f_91060_.m_109818_();
      }
   }).m_167773_((p_168151_) -> {
      List<FormattedCharSequence> list = p_168151_.f_91062_.m_92923_(f_91654_, 200);
      List<FormattedCharSequence> list1 = p_168151_.f_91062_.m_92923_(f_91656_, 200);
      List<FormattedCharSequence> list2 = p_168151_.f_91062_.m_92923_(f_91655_, 200);
      return (p_168210_) -> {
         switch(p_168210_) {
         case FANCY:
            return list1;
         case FAST:
            return list;
         case FABULOUS:
            return list2;
         default:
            return ImmutableList.of();
         }
      };
   });
   public static final CycleOption f_91683_ = CycleOption.m_167747_("options.guiScale", () -> {
      return IntStream.rangeClosed(0, Minecraft.m_91087_().m_91268_().m_85385_(0, Minecraft.m_91087_().m_91390_())).boxed().collect(Collectors.toList());
   }, (p_168205_) -> {
      return (Component)(p_168205_ == 0 ? new TranslatableComponent("options.guiScale.auto") : new TextComponent(Integer.toString(p_168205_)));
   }, (p_168111_) -> {
      return p_168111_.f_92072_;
   }, (p_168193_, p_168194_, p_168195_) -> {
      p_168193_.f_92072_ = p_168195_;
   });
   public static final CycleOption<HumanoidArm> f_91684_ = CycleOption.m_167764_("options.mainHand", HumanoidArm.values(), HumanoidArm::m_20829_, (p_168404_) -> {
      return p_168404_.f_92127_;
   }, (p_168157_, p_168158_, p_168159_) -> {
      p_168157_.f_92127_ = p_168159_;
      p_168157_.m_92172_();
   });
   public static final CycleOption<NarratorStatus> f_91627_ = CycleOption.m_167764_("options.narrator", NarratorStatus.values(), (p_168153_) -> {
      return (Component)(NarratorChatListener.f_93311_.m_93316_() ? p_168153_.m_91621_() : new TranslatableComponent("options.narrator.notavailable"));
   }, (p_168402_) -> {
      return p_168402_.f_92074_;
   }, (p_168181_, p_168182_, p_168183_) -> {
      p_168181_.f_92074_ = p_168183_;
      NarratorChatListener.f_93311_.m_93317_(p_168183_);
   });
   public static final CycleOption<ParticleStatus> f_91628_ = CycleOption.m_167764_("options.particles", ParticleStatus.values(), (p_168203_) -> {
      return new TranslatableComponent(p_168203_.m_92195_());
   }, (p_168400_) -> {
      return p_168400_.f_92073_;
   }, (p_168185_, p_168186_, p_168187_) -> {
      p_168185_.f_92073_ = p_168187_;
   });
   public static final CycleOption<CloudStatus> f_91629_ = CycleOption.m_167764_("options.renderClouds", CloudStatus.values(), (p_168147_) -> {
      return new TranslatableComponent(p_168147_.m_90666_());
   }, (p_168394_) -> {
      return p_168394_.f_92114_;
   }, (p_168173_, p_168174_, p_168175_) -> {
      p_168173_.f_92114_ = p_168175_;
      if (Minecraft.m_91085_()) {
         RenderTarget rendertarget = Minecraft.m_91087_().f_91060_.m_109832_();
         if (rendertarget != null) {
            rendertarget.m_83954_(Minecraft.f_91002_);
         }
      }

   });
   public static final CycleOption<Boolean> f_91630_ = CycleOption.m_167758_("options.accessibility.text_background", new TranslatableComponent("options.accessibility.text_background.chat"), new TranslatableComponent("options.accessibility.text_background.everywhere"), (p_168388_) -> {
      return p_168388_.f_92050_;
   }, (p_168390_, p_168391_, p_168392_) -> {
      p_168390_.f_92050_ = p_168392_;
   });
   private static final Component f_91657_ = new TranslatableComponent("options.hideMatchedNames.tooltip");
   public static final CycleOption<Boolean> f_91631_ = CycleOption.m_167743_("options.autoJump", (p_168382_) -> {
      return p_168382_.f_92036_;
   }, (p_168384_, p_168385_, p_168386_) -> {
      p_168384_.f_92036_ = p_168386_;
   });
   public static final CycleOption<Boolean> f_91632_ = CycleOption.m_167743_("options.autoSuggestCommands", (p_168376_) -> {
      return p_168376_.f_92037_;
   }, (p_168378_, p_168379_, p_168380_) -> {
      p_168378_.f_92037_ = p_168380_;
   });
   public static final CycleOption<Boolean> f_91634_ = CycleOption.m_167743_("options.chat.color", (p_168370_) -> {
      return p_168370_.f_92038_;
   }, (p_168372_, p_168373_, p_168374_) -> {
      p_168372_.f_92038_ = p_168374_;
   });
   public static final CycleOption<Boolean> f_91633_ = CycleOption.m_167753_("options.hideMatchedNames", f_91657_, (p_168364_) -> {
      return p_168364_.f_92084_;
   }, (p_168366_, p_168367_, p_168368_) -> {
      p_168366_.f_92084_ = p_168368_;
   });
   public static final CycleOption<Boolean> f_91635_ = CycleOption.m_167743_("options.chat.links", (p_168358_) -> {
      return p_168358_.f_92039_;
   }, (p_168360_, p_168361_, p_168362_) -> {
      p_168360_.f_92039_ = p_168362_;
   });
   public static final CycleOption<Boolean> f_91636_ = CycleOption.m_167743_("options.chat.links.prompt", (p_168352_) -> {
      return p_168352_.f_92040_;
   }, (p_168354_, p_168355_, p_168356_) -> {
      p_168354_.f_92040_ = p_168356_;
   });
   public static final CycleOption<Boolean> f_91637_ = CycleOption.m_167743_("options.discrete_mouse_scroll", (p_168346_) -> {
      return p_168346_.f_92045_;
   }, (p_168348_, p_168349_, p_168350_) -> {
      p_168348_.f_92045_ = p_168350_;
   });
   public static final CycleOption<Boolean> f_91638_ = CycleOption.m_167743_("options.vsync", (p_168340_) -> {
      return p_168340_.f_92041_;
   }, (p_168342_, p_168343_, p_168344_) -> {
      p_168342_.f_92041_ = p_168344_;
      if (Minecraft.m_91087_().m_91268_() != null) {
         Minecraft.m_91087_().m_91268_().m_85409_(p_168342_.f_92041_);
      }

   });
   public static final CycleOption<Boolean> f_91639_ = CycleOption.m_167743_("options.entityShadows", (p_168334_) -> {
      return p_168334_.f_92042_;
   }, (p_168336_, p_168337_, p_168338_) -> {
      p_168336_.f_92042_ = p_168338_;
   });
   public static final CycleOption<Boolean> f_91640_ = CycleOption.m_167743_("options.forceUnicodeFont", (p_168328_) -> {
      return p_168328_.f_92043_;
   }, (p_168330_, p_168331_, p_168332_) -> {
      p_168330_.f_92043_ = p_168332_;
      Minecraft minecraft = Minecraft.m_91087_();
      if (minecraft.m_91268_() != null) {
         minecraft.m_91336_(p_168332_);
         minecraft.m_5741_();
      }

   });
   public static final CycleOption<Boolean> f_91641_ = CycleOption.m_167743_("options.invertMouse", (p_168322_) -> {
      return p_168322_.f_92044_;
   }, (p_168324_, p_168325_, p_168326_) -> {
      p_168324_.f_92044_ = p_168326_;
   });
   public static final CycleOption<Boolean> f_91642_ = CycleOption.m_167743_("options.realmsNotifications", (p_168316_) -> {
      return p_168316_.f_92046_;
   }, (p_168318_, p_168319_, p_168320_) -> {
      p_168318_.f_92046_ = p_168320_;
   });
   public static final CycleOption<Boolean> f_91643_ = CycleOption.m_167743_("options.reducedDebugInfo", (p_168304_) -> {
      return p_168304_.f_92047_;
   }, (p_168306_, p_168307_, p_168308_) -> {
      p_168306_.f_92047_ = p_168308_;
   });
   public static final CycleOption<Boolean> f_91644_ = CycleOption.m_167743_("options.showSubtitles", (p_168292_) -> {
      return p_168292_.f_92049_;
   }, (p_168294_, p_168295_, p_168296_) -> {
      p_168294_.f_92049_ = p_168296_;
   });
   public static final CycleOption<Boolean> f_91645_ = CycleOption.m_167743_("options.snooper", (p_168280_) -> {
      if (p_168280_.f_92048_) {
      }

      return false;
   }, (p_168282_, p_168283_, p_168284_) -> {
      p_168282_.f_92048_ = p_168284_;
   });
   private static final Component f_168107_ = new TranslatableComponent("options.key.toggle");
   private static final Component f_168108_ = new TranslatableComponent("options.key.hold");
   public static final CycleOption<Boolean> f_91646_ = CycleOption.m_167758_("key.sneak", f_168107_, f_168108_, (p_168268_) -> {
      return p_168268_.f_92081_;
   }, (p_168270_, p_168271_, p_168272_) -> {
      p_168270_.f_92081_ = p_168272_;
   });
   public static final CycleOption<Boolean> f_91647_ = CycleOption.m_167758_("key.sprint", f_168107_, f_168108_, (p_168256_) -> {
      return p_168256_.f_92082_;
   }, (p_168258_, p_168259_, p_168260_) -> {
      p_168258_.f_92082_ = p_168260_;
   });
   public static final CycleOption<Boolean> f_91648_ = CycleOption.m_167743_("options.touchscreen", (p_168244_) -> {
      return p_168244_.f_92051_;
   }, (p_168246_, p_168247_, p_168248_) -> {
      p_168246_.f_92051_ = p_168248_;
   });
   public static final CycleOption<Boolean> f_91649_ = CycleOption.m_167743_("options.fullscreen", (p_168232_) -> {
      return p_168232_.f_92052_;
   }, (p_168234_, p_168235_, p_168236_) -> {
      p_168234_.f_92052_ = p_168236_;
      Minecraft minecraft = Minecraft.m_91087_();
      if (minecraft.m_91268_() != null && minecraft.m_91268_().m_85440_() != p_168234_.f_92052_) {
         minecraft.m_91268_().m_85438_();
         p_168234_.f_92052_ = minecraft.m_91268_().m_85440_();
      }

   });
   public static final CycleOption<Boolean> f_91650_ = CycleOption.m_167743_("options.viewBobbing", (p_168217_) -> {
      return p_168217_.f_92080_;
   }, (p_168219_, p_168220_, p_168221_) -> {
      p_168219_.f_92080_ = p_168221_;
   });
   private static final Component f_168109_ = new TranslatableComponent("options.darkMojangStudiosBackgroundColor.tooltip");
   public static final CycleOption<Boolean> f_168105_ = CycleOption.m_167753_("options.darkMojangStudiosBackgroundColor", f_168109_, (p_168155_) -> {
      return p_168155_.f_168413_;
   }, (p_168189_, p_168190_, p_168191_) -> {
      p_168189_.f_168413_ = p_168191_;
   });
   private final Component f_91658_;

   public Option(String p_91687_) {
      this.f_91658_ = new TranslatableComponent(p_91687_);
   }

   public abstract AbstractWidget m_7496_(Options p_91719_, int p_91720_, int p_91721_, int p_91722_);

   protected Component m_91714_() {
      return this.f_91658_;
   }

   protected Component m_91715_(int p_91716_) {
      return new TranslatableComponent("options.pixel_value", this.m_91714_(), p_91716_);
   }

   protected Component m_91762_(double p_91763_) {
      return new TranslatableComponent("options.percent_value", this.m_91714_(), (int)(p_91763_ * 100.0D));
   }

   protected Component m_91743_(int p_91744_) {
      return new TranslatableComponent("options.percent_add_value", this.m_91714_(), p_91744_);
   }

   protected Component m_91740_(Component p_91741_) {
      return new TranslatableComponent("options.generic_value", this.m_91714_(), p_91741_);
   }

   protected Component m_91764_(int p_91765_) {
      return this.m_91740_(new TextComponent(Integer.toString(p_91765_)));
   }
}