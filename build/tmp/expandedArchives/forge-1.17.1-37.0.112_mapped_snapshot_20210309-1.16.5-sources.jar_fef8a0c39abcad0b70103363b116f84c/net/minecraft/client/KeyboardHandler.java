package net.minecraft.client;

import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.ClipboardManager;
import com.mojang.blaze3d.platform.InputConstants;
import java.text.MessageFormat;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeyboardHandler {
   public static final int f_167812_ = 10000;
   private final Minecraft f_90867_;
   private boolean f_90868_;
   private final ClipboardManager f_90869_ = new ClipboardManager();
   private long f_90870_ = -1L;
   private long f_90871_ = -1L;
   private long f_90872_ = -1L;
   private boolean f_90873_;

   public KeyboardHandler(Minecraft p_90875_) {
      this.f_90867_ = p_90875_;
   }

   private boolean m_167813_(int p_167814_) {
      switch(p_167814_) {
      case 69:
         this.f_90867_.f_90978_ = !this.f_90867_.f_90978_;
         this.m_167837_("ChunkPath: {0}", this.f_90867_.f_90978_ ? "shown" : "hidden");
         return true;
      case 76:
         this.f_90867_.f_90980_ = !this.f_90867_.f_90980_;
         this.m_167837_("SmartCull: {0}", this.f_90867_.f_90980_ ? "enabled" : "disabled");
         return true;
      case 85:
         if (Screen.m_96638_()) {
            this.f_90867_.f_91060_.m_173019_();
            this.m_167837_("Killed frustum");
         } else {
            this.f_90867_.f_91060_.m_173018_();
            this.m_167837_("Captured frustum");
         }

         return true;
      case 86:
         this.f_90867_.f_90979_ = !this.f_90867_.f_90979_;
         this.m_167837_("ChunkVisibility: {0}", this.f_90867_.f_90979_ ? "enabled" : "disabled");
         return true;
      case 87:
         this.f_90867_.f_167842_ = !this.f_90867_.f_167842_;
         this.m_167837_("WireFrame: {0}", this.f_90867_.f_167842_ ? "enabled" : "disabled");
         return true;
      default:
         return false;
      }
   }

   private void m_167824_(ChatFormatting p_167825_, Component p_167826_) {
      this.f_90867_.f_91065_.m_93076_().m_93785_((new TextComponent("")).m_7220_((new TranslatableComponent("debug.prefix")).m_130944_(new ChatFormatting[]{p_167825_, ChatFormatting.BOLD})).m_130946_(" ").m_7220_(p_167826_));
   }

   private void m_167822_(Component p_167823_) {
      this.m_167824_(ChatFormatting.YELLOW, p_167823_);
   }

   private void m_90913_(String p_90914_, Object... p_90915_) {
      this.m_167822_(new TranslatableComponent(p_90914_, p_90915_));
   }

   private void m_90948_(String p_90949_, Object... p_90950_) {
      this.m_167824_(ChatFormatting.RED, new TranslatableComponent(p_90949_, p_90950_));
   }

   private void m_167837_(String p_167838_, Object... p_167839_) {
      this.m_167822_(new TextComponent(MessageFormat.format(p_167838_, p_167839_)));
   }

   private boolean m_90932_(int p_90933_) {
      if (this.f_90870_ > 0L && this.f_90870_ < Util.m_137550_() - 100L) {
         return true;
      } else {
         switch(p_90933_) {
         case 65:
            this.f_90867_.f_91060_.m_109818_();
            this.m_90913_("debug.reload_chunks.message");
            return true;
         case 66:
            boolean flag = !this.f_90867_.m_91290_().m_114377_();
            this.f_90867_.m_91290_().m_114473_(flag);
            this.m_90913_(flag ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off");
            return true;
         case 67:
            if (this.f_90867_.f_91074_.m_36330_()) {
               return false;
            } else {
               ClientPacketListener clientpacketlistener = this.f_90867_.f_91074_.f_108617_;
               if (clientpacketlistener == null) {
                  return false;
               }

               this.m_90913_("debug.copy_location.message");
               this.m_90911_(String.format(Locale.ROOT, "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f", this.f_90867_.f_91074_.f_19853_.m_46472_().m_135782_(), this.f_90867_.f_91074_.m_20185_(), this.f_90867_.f_91074_.m_20186_(), this.f_90867_.f_91074_.m_20189_(), this.f_90867_.f_91074_.m_146908_(), this.f_90867_.f_91074_.m_146909_()));
               return true;
            }
         case 68:
            if (this.f_90867_.f_91065_ != null) {
               this.f_90867_.f_91065_.m_93076_().m_93795_(false);
            }

            return true;
         case 70:
            Option.f_91675_.m_92223_(this.f_90867_.f_91066_, Mth.m_14008_((double)(this.f_90867_.f_91066_.f_92106_ + (Screen.m_96638_() ? -1 : 1)), Option.f_91675_.m_92232_(), Option.f_91675_.m_92235_()));
            this.m_90913_("debug.cycle_renderdistance.message", this.f_90867_.f_91066_.f_92106_);
            return true;
         case 71:
            boolean flag1 = this.f_90867_.f_91064_.m_113506_();
            this.m_90913_(flag1 ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off");
            return true;
         case 72:
            this.f_90867_.f_91066_.f_92125_ = !this.f_90867_.f_91066_.f_92125_;
            this.m_90913_(this.f_90867_.f_91066_.f_92125_ ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off");
            this.f_90867_.f_91066_.m_92169_();
            return true;
         case 73:
            if (!this.f_90867_.f_91074_.m_36330_()) {
               this.m_90928_(this.f_90867_.f_91074_.m_20310_(2), !Screen.m_96638_());
            }

            return true;
         case 76:
            if (this.f_90867_.m_167946_(this::m_167822_)) {
               this.m_90913_("debug.profiling.start", 10);
            }

            return true;
         case 78:
            if (!this.f_90867_.f_91074_.m_20310_(2)) {
               this.m_90913_("debug.creative_spectator.error");
            } else if (!this.f_90867_.f_91074_.m_5833_()) {
               this.f_90867_.f_91074_.m_108739_("/gamemode spectator");
            } else {
               this.f_90867_.f_91074_.m_108739_("/gamemode " + MoreObjects.firstNonNull(this.f_90867_.f_91072_.m_105294_(), GameType.CREATIVE).m_46405_());
            }

            return true;
         case 80:
            this.f_90867_.f_91066_.f_92126_ = !this.f_90867_.f_91066_.f_92126_;
            this.f_90867_.f_91066_.m_92169_();
            this.m_90913_(this.f_90867_.f_91066_.f_92126_ ? "debug.pause_focus.on" : "debug.pause_focus.off");
            return true;
         case 81:
            this.m_90913_("debug.help.message");
            ChatComponent chatcomponent = this.f_90867_.f_91065_.m_93076_();
            chatcomponent.m_93785_(new TranslatableComponent("debug.reload_chunks.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.show_hitboxes.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.copy_location.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.clear_chat.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.cycle_renderdistance.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.chunk_boundaries.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.advanced_tooltips.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.inspect.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.profiling.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.creative_spectator.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.pause_focus.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.help.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.reload_resourcepacks.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.pause.help"));
            chatcomponent.m_93785_(new TranslatableComponent("debug.gamemodes.help"));
            return true;
         case 84:
            this.m_90913_("debug.reload_resourcepacks.message");
            this.f_90867_.m_91391_();
            return true;
         case 293:
            if (!this.f_90867_.f_91074_.m_20310_(2)) {
               this.m_90913_("debug.gamemodes.error");
            } else {
               this.f_90867_.m_91152_(new GameModeSwitcherScreen());
            }

            return true;
         default:
            return false;
         }
      }
   }

   private void m_90928_(boolean p_90929_, boolean p_90930_) {
      HitResult hitresult = this.f_90867_.f_91077_;
      if (hitresult != null) {
         switch(hitresult.m_6662_()) {
         case BLOCK:
            BlockPos blockpos = ((BlockHitResult)hitresult).m_82425_();
            BlockState blockstate = this.f_90867_.f_91074_.f_19853_.m_8055_(blockpos);
            if (p_90929_) {
               if (p_90930_) {
                  this.f_90867_.f_91074_.f_108617_.m_105149_().m_90708_(blockpos, (p_90947_) -> {
                     this.m_90899_(blockstate, blockpos, p_90947_);
                     this.m_90913_("debug.inspect.server.block");
                  });
               } else {
                  BlockEntity blockentity = this.f_90867_.f_91074_.f_19853_.m_7702_(blockpos);
                  CompoundTag compoundtag1 = blockentity != null ? blockentity.m_6945_(new CompoundTag()) : null;
                  this.m_90899_(blockstate, blockpos, compoundtag1);
                  this.m_90913_("debug.inspect.client.block");
               }
            } else {
               this.m_90899_(blockstate, blockpos, (CompoundTag)null);
               this.m_90913_("debug.inspect.client.block");
            }
            break;
         case ENTITY:
            Entity entity = ((EntityHitResult)hitresult).m_82443_();
            ResourceLocation resourcelocation = Registry.f_122826_.m_7981_(entity.m_6095_());
            if (p_90929_) {
               if (p_90930_) {
                  this.f_90867_.f_91074_.f_108617_.m_105149_().m_90702_(entity.m_142049_(), (p_90921_) -> {
                     this.m_90922_(resourcelocation, entity.m_20182_(), p_90921_);
                     this.m_90913_("debug.inspect.server.entity");
                  });
               } else {
                  CompoundTag compoundtag = entity.m_20240_(new CompoundTag());
                  this.m_90922_(resourcelocation, entity.m_20182_(), compoundtag);
                  this.m_90913_("debug.inspect.client.entity");
               }
            } else {
               this.m_90922_(resourcelocation, entity.m_20182_(), (CompoundTag)null);
               this.m_90913_("debug.inspect.client.entity");
            }
         }

      }
   }

   private void m_90899_(BlockState p_90900_, BlockPos p_90901_, @Nullable CompoundTag p_90902_) {
      if (p_90902_ != null) {
         p_90902_.m_128473_("x");
         p_90902_.m_128473_("y");
         p_90902_.m_128473_("z");
         p_90902_.m_128473_("id");
      }

      StringBuilder stringbuilder = new StringBuilder(BlockStateParser.m_116769_(p_90900_));
      if (p_90902_ != null) {
         stringbuilder.append((Object)p_90902_);
      }

      String s = String.format(Locale.ROOT, "/setblock %d %d %d %s", p_90901_.m_123341_(), p_90901_.m_123342_(), p_90901_.m_123343_(), stringbuilder);
      this.m_90911_(s);
   }

   private void m_90922_(ResourceLocation p_90923_, Vec3 p_90924_, @Nullable CompoundTag p_90925_) {
      String s;
      if (p_90925_ != null) {
         p_90925_.m_128473_("UUID");
         p_90925_.m_128473_("Pos");
         p_90925_.m_128473_("Dimension");
         String s1 = NbtUtils.m_178061_(p_90925_).getString();
         s = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", p_90923_.toString(), p_90924_.f_82479_, p_90924_.f_82480_, p_90924_.f_82481_, s1);
      } else {
         s = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", p_90923_.toString(), p_90924_.f_82479_, p_90924_.f_82480_, p_90924_.f_82481_);
      }

      this.m_90911_(s);
   }

   public void m_90893_(long p_90894_, int p_90895_, int p_90896_, int p_90897_, int p_90898_) {
      if (p_90894_ == this.f_90867_.m_91268_().m_85439_()) {
         if (this.f_90870_ > 0L) {
            if (!InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 67) || !InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292)) {
               this.f_90870_ = -1L;
            }
         } else if (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 67) && InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292)) {
            this.f_90873_ = true;
            this.f_90870_ = Util.m_137550_();
            this.f_90871_ = Util.m_137550_();
            this.f_90872_ = 0L;
         }

         Screen screen = this.f_90867_.f_91080_;

         if ((!(this.f_90867_.f_91080_ instanceof ControlsScreen) || ((ControlsScreen)screen).f_97515_ <= Util.m_137550_() - 20L)) {
            if (p_90897_ == 1) {
            if (this.f_90867_.f_91066_.f_92105_.m_90832_(p_90895_, p_90896_)) {
               this.f_90867_.m_91268_().m_85438_();
               this.f_90867_.f_91066_.f_92052_ = this.f_90867_.m_91268_().m_85440_();
               this.f_90867_.f_91066_.m_92169_();
               return;
            }

            if (this.f_90867_.f_91066_.f_92102_.m_90832_(p_90895_, p_90896_)) {
               if (Screen.m_96637_()) {
               }

               Screenshot.m_92289_(this.f_90867_.f_91069_, this.f_90867_.m_91385_(), (p_90917_) -> {
                  this.f_90867_.execute(() -> {
                     this.f_90867_.f_91065_.m_93076_().m_93785_(p_90917_);
                  });
               });
               return;
            }
            } else if (p_90897_ == 0 /*GLFW_RELEASE*/ && this.f_90867_.f_91080_ instanceof ControlsScreen)
               ((ControlsScreen)this.f_90867_.f_91080_).f_97514_ = null; //Forge: Unset pure modifiers.
         }

         if (NarratorChatListener.f_93311_.m_93316_()) {
            boolean flag = screen == null || !(screen.m_7222_() instanceof EditBox) || !((EditBox)screen.m_7222_()).m_94204_();
            if (p_90897_ != 0 && p_90895_ == 66 && Screen.m_96637_() && flag) {
               boolean flag1 = this.f_90867_.f_91066_.f_92074_ == NarratorStatus.OFF;
               this.f_90867_.f_91066_.f_92074_ = NarratorStatus.m_91619_(this.f_90867_.f_91066_.f_92074_.m_91618_() + 1);
               NarratorChatListener.f_93311_.m_93317_(this.f_90867_.f_91066_.f_92074_);
               if (screen instanceof SimpleOptionsSubScreen) {
                  ((SimpleOptionsSubScreen)screen).m_96682_();
               }

               if (flag1 && screen != null) {
                  screen.m_169418_();
               }
            }
         }

         if (screen != null) {
            boolean[] aboolean = new boolean[]{false};
            Screen.m_96579_(() -> {
               if (p_90897_ != 1 && (p_90897_ != 2 || !this.f_90868_)) {
                  if (p_90897_ == 0) {
                     aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiKeyReleasedPre(this.f_90867_.f_91080_, p_90895_, p_90896_, p_90898_);
                     if (!aboolean[0]) aboolean[0] = screen.m_7920_(p_90895_, p_90896_, p_90898_);
                     if (!aboolean[0]) aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiKeyReleasedPost(this.f_90867_.f_91080_, p_90895_, p_90896_, p_90898_);
                  }
               } else {
                  screen.m_169416_();
                  aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiKeyPressedPre(this.f_90867_.f_91080_, p_90895_, p_90896_, p_90898_);
                  if (!aboolean[0]) aboolean[0] = screen.m_7933_(p_90895_, p_90896_, p_90898_);
                  if (!aboolean[0]) aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiKeyPressedPost(this.f_90867_.f_91080_, p_90895_, p_90896_, p_90898_);
               }

            }, "keyPressed event handler", screen.getClass().getCanonicalName());
            if (aboolean[0]) {
               return;
            }
         }

         if (this.f_90867_.f_91080_ == null || this.f_90867_.f_91080_.f_96546_) {
            InputConstants.Key inputconstants$key = InputConstants.m_84827_(p_90895_, p_90896_);
            if (p_90897_ == 0) {
               KeyMapping.m_90837_(inputconstants$key, false);
               if (p_90895_ == 292) {
                  if (this.f_90873_) {
                     this.f_90873_ = false;
                  } else {
                     this.f_90867_.f_91066_.f_92063_ = !this.f_90867_.f_91066_.f_92063_;
                     this.f_90867_.f_91066_.f_92064_ = this.f_90867_.f_91066_.f_92063_ && Screen.m_96638_();
                     this.f_90867_.f_91066_.f_92065_ = this.f_90867_.f_91066_.f_92063_ && Screen.m_96639_();
                  }
               }
            } else {
               if (p_90895_ == 293 && this.f_90867_.f_91063_ != null) {
                  this.f_90867_.f_91063_.m_109130_();
               }

               boolean flag3 = false;
               if (this.f_90867_.f_91080_ == null) {
                  if (p_90895_ == 256) {
                     boolean flag2 = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292);
                     this.f_90867_.m_91358_(flag2);
                  }

                  flag3 = InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 292) && this.m_90932_(p_90895_);
                  this.f_90873_ |= flag3;
                  if (p_90895_ == 290) {
                     this.f_90867_.f_91066_.f_92062_ = !this.f_90867_.f_91066_.f_92062_;
                  }
               }

               if (flag3) {
                  KeyMapping.m_90837_(inputconstants$key, false);
               } else {
                  KeyMapping.m_90837_(inputconstants$key, true);
                  KeyMapping.m_90835_(inputconstants$key);
               }

               if (this.f_90867_.f_91066_.f_92064_ && p_90895_ >= 48 && p_90895_ <= 57) {
                  this.f_90867_.m_91111_(p_90895_ - 48);
               }
            }
         }
         net.minecraftforge.client.ForgeHooksClient.fireKeyInput(p_90895_, p_90896_, p_90897_, p_90898_);
      }
   }

   private void m_90889_(long p_90890_, int p_90891_, int p_90892_) {
      if (p_90890_ == this.f_90867_.m_91268_().m_85439_()) {
         GuiEventListener guieventlistener = this.f_90867_.f_91080_;
         if (guieventlistener != null && this.f_90867_.m_91265_() == null) {
            if (Character.charCount(p_90891_) == 1) {
               Screen.m_96579_(() -> {
                  if (net.minecraftforge.client.ForgeHooksClient.onGuiCharTypedPre(this.f_90867_.f_91080_, (char)p_90891_, p_90892_)) return;
                  if (guieventlistener.m_5534_((char)p_90891_, p_90892_)) return;
                  net.minecraftforge.client.ForgeHooksClient.onGuiCharTypedPost(this.f_90867_.f_91080_, (char)p_90891_, p_90892_);
               }, "charTyped event handler", guieventlistener.getClass().getCanonicalName());
            } else {
               for(char c0 : Character.toChars(p_90891_)) {
                  Screen.m_96579_(() -> {
                     if (net.minecraftforge.client.ForgeHooksClient.onGuiCharTypedPre(this.f_90867_.f_91080_, c0, p_90892_)) return;
                     if (guieventlistener.m_5534_(c0, p_90892_)) return;
                     net.minecraftforge.client.ForgeHooksClient.onGuiCharTypedPost(this.f_90867_.f_91080_, c0, p_90892_);
                  }, "charTyped event handler", guieventlistener.getClass().getCanonicalName());
               }
            }

         }
      }
   }

   public void m_90926_(boolean p_90927_) {
      this.f_90868_ = p_90927_;
   }

   public void m_90887_(long p_90888_) {
      InputConstants.m_84844_(p_90888_, (p_90939_, p_90940_, p_90941_, p_90942_, p_90943_) -> {
         this.f_90867_.execute(() -> {
            this.m_90893_(p_90939_, p_90940_, p_90941_, p_90942_, p_90943_);
         });
      }, (p_90935_, p_90936_, p_90937_) -> {
         this.f_90867_.execute(() -> {
            this.m_90889_(p_90935_, p_90936_, p_90937_);
         });
      });
   }

   public String m_90876_() {
      return this.f_90869_.m_83995_(this.f_90867_.m_91268_().m_85439_(), (p_90878_, p_90879_) -> {
         if (p_90878_ != 65545) {
            this.f_90867_.m_91268_().m_85382_(p_90878_, p_90879_);
         }

      });
   }

   public void m_90911_(String p_90912_) {
      if (!p_90912_.isEmpty()) {
         this.f_90869_.m_83988_(this.f_90867_.m_91268_().m_85439_(), p_90912_);
      }

   }

   public void m_90931_() {
      if (this.f_90870_ > 0L) {
         long i = Util.m_137550_();
         long j = 10000L - (i - this.f_90870_);
         long k = i - this.f_90871_;
         if (j < 0L) {
            if (Screen.m_96637_()) {
               Blaze3D.m_83639_();
            }

            throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
         }

         if (k >= 1000L) {
            if (this.f_90872_ == 0L) {
               this.m_90913_("debug.crash.message");
            } else {
               this.m_90948_("debug.crash.warning", Mth.m_14167_((float)j / 1000.0F));
            }

            this.f_90871_ = i;
            ++this.f_90872_;
         }
      }

   }
}
