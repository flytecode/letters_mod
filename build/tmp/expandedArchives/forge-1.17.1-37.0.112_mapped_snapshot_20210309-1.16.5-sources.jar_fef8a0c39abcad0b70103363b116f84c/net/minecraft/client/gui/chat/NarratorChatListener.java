package net.minecraft.client.gui.chat;

import com.mojang.text2speech.Narrator;
import java.util.UUID;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class NarratorChatListener implements ChatListener {
   public static final Component f_93310_ = TextComponent.f_131282_;
   private static final Logger f_93312_ = LogManager.getLogger();
   public static final NarratorChatListener f_93311_ = new NarratorChatListener();
   private final Narrator f_93313_ = Narrator.getNarrator();

   public void m_7437_(ChatType p_93322_, Component p_93323_, UUID p_93324_) {
      NarratorStatus narratorstatus = m_93330_();
      if (narratorstatus != NarratorStatus.OFF) {
         if (!this.f_93313_.active()) {
            this.m_168787_(p_93323_.getString());
         } else {
            if (narratorstatus == NarratorStatus.ALL || narratorstatus == NarratorStatus.CHAT && p_93322_ == ChatType.CHAT || narratorstatus == NarratorStatus.SYSTEM && p_93322_ == ChatType.SYSTEM) {
               Component component;
               if (p_93323_ instanceof TranslatableComponent && "chat.type.text".equals(((TranslatableComponent)p_93323_).m_131328_())) {
                  component = new TranslatableComponent("chat.type.text.narrate", ((TranslatableComponent)p_93323_).m_131329_());
               } else {
                  component = p_93323_;
               }

               String s = component.getString();
               this.m_168787_(s);
               this.f_93313_.say(s, p_93322_.m_130613_());
            }

         }
      }
   }

   public void m_168785_(Component p_168786_) {
      this.m_93319_(p_168786_.getString());
   }

   public void m_93319_(String p_93320_) {
      NarratorStatus narratorstatus = m_93330_();
      if (narratorstatus != NarratorStatus.OFF && narratorstatus != NarratorStatus.CHAT && !p_93320_.isEmpty()) {
         this.m_168787_(p_93320_);
         if (this.f_93313_.active()) {
            this.f_93313_.clear();
            this.f_93313_.say(p_93320_, true);
         }
      }

   }

   private static NarratorStatus m_93330_() {
      return Minecraft.m_91087_().f_91066_.f_92074_;
   }

   private void m_168787_(String p_168788_) {
      if (SharedConstants.f_136183_) {
         f_93312_.debug("Narrating: {}", (Object)p_168788_.replaceAll("\n", "\\\\n"));
      }

   }

   public void m_93317_(NarratorStatus p_93318_) {
      this.m_93328_();
      this.f_93313_.say((new TranslatableComponent("options.narrator")).m_130946_(" : ").m_7220_(p_93318_.m_91621_()).getString(), true);
      ToastComponent toastcomponent = Minecraft.m_91087_().m_91300_();
      if (this.f_93313_.active()) {
         if (p_93318_ == NarratorStatus.OFF) {
            SystemToast.m_94869_(toastcomponent, SystemToast.SystemToastIds.NARRATOR_TOGGLE, new TranslatableComponent("narrator.toast.disabled"), (Component)null);
         } else {
            SystemToast.m_94869_(toastcomponent, SystemToast.SystemToastIds.NARRATOR_TOGGLE, new TranslatableComponent("narrator.toast.enabled"), p_93318_.m_91621_());
         }
      } else {
         SystemToast.m_94869_(toastcomponent, SystemToast.SystemToastIds.NARRATOR_TOGGLE, new TranslatableComponent("narrator.toast.disabled"), new TranslatableComponent("options.narrator.notavailable"));
      }

   }

   public boolean m_93316_() {
      return this.f_93313_.active();
   }

   public void m_93328_() {
      if (m_93330_() != NarratorStatus.OFF && this.f_93313_.active()) {
         this.f_93313_.clear();
      }
   }

   public void m_93329_() {
      this.f_93313_.destroy();
   }
}