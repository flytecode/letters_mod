package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StandardChatListener implements ChatListener {
   private final Minecraft f_93357_;

   public StandardChatListener(Minecraft p_93359_) {
      this.f_93357_ = p_93359_;
   }

   public void m_7437_(ChatType p_93361_, Component p_93362_, UUID p_93363_) {
      if (p_93361_ != ChatType.CHAT) {
         this.f_93357_.f_91065_.m_93076_().m_93785_(p_93362_);
      } else {
         this.f_93357_.f_91065_.m_93076_().m_93808_(p_93362_);
      }

   }
}