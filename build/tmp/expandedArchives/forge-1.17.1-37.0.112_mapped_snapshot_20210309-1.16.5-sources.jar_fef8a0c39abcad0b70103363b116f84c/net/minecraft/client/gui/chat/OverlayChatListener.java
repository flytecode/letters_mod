package net.minecraft.client.gui.chat;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayChatListener implements ChatListener {
   private final Minecraft f_93350_;

   public OverlayChatListener(Minecraft p_93352_) {
      this.f_93350_ = p_93352_;
   }

   public void m_7437_(ChatType p_93354_, Component p_93355_, UUID p_93356_) {
      this.f_93350_.f_91065_.m_93063_(p_93355_, false);
   }
}