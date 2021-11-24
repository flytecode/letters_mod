package net.minecraft.client.gui.screens;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InBedChatScreen extends ChatScreen {
   public InBedChatScreen() {
      super("");
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 40, 200, 20, new TranslatableComponent("multiplayer.stopSleeping"), (p_96074_) -> {
         this.m_96077_();
      }));
   }

   public void m_7379_() {
      this.m_96077_();
   }

   public boolean m_7933_(int p_96070_, int p_96071_, int p_96072_) {
      if (p_96070_ == 256) {
         this.m_96077_();
      } else if (p_96070_ == 257 || p_96070_ == 335) {
         String s = this.f_95573_.m_94155_().trim();
         if (!s.isEmpty()) {
            this.m_96615_(s);
         }

         this.f_95573_.m_94144_("");
         this.f_96541_.f_91065_.m_93076_().m_93810_();
         return true;
      }

      return super.m_7933_(p_96070_, p_96071_, p_96072_);
   }

   private void m_96077_() {
      ClientPacketListener clientpacketlistener = this.f_96541_.f_91074_.f_108617_;
      clientpacketlistener.m_104955_(new ServerboundPlayerCommandPacket(this.f_96541_.f_91074_, ServerboundPlayerCommandPacket.Action.STOP_SLEEPING));
   }
}