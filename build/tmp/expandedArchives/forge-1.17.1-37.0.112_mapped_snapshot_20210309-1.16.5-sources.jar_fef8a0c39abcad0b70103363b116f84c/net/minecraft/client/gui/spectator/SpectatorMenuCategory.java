package net.minecraft.client.gui.spectator;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface SpectatorMenuCategory {
   List<SpectatorMenuItem> m_5919_();

   Component m_5878_();
}