package net.minecraft.client.gui.spectator;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayerMenuCategory;
import net.minecraft.client.gui.spectator.categories.TeleportToTeamMenuCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RootSpectatorMenuCategory implements SpectatorMenuCategory {
   private static final Component f_101765_ = new TranslatableComponent("spectatorMenu.root.prompt");
   private final List<SpectatorMenuItem> f_101766_ = Lists.newArrayList();

   public RootSpectatorMenuCategory() {
      this.f_101766_.add(new TeleportToPlayerMenuCategory());
      this.f_101766_.add(new TeleportToTeamMenuCategory());
   }

   public List<SpectatorMenuItem> m_5919_() {
      return this.f_101766_;
   }

   public Component m_5878_() {
      return f_101765_;
   }
}