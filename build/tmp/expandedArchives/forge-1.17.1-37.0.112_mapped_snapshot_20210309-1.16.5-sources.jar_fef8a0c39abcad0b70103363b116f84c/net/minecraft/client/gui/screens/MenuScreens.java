package net.minecraft.client.gui.screens;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.gui.screens.inventory.LecternScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.gui.screens.inventory.SmokerScreen;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class MenuScreens {
   private static final Logger f_96195_ = LogManager.getLogger();
   private static final Map<MenuType<?>, MenuScreens.ScreenConstructor<?, ?>> f_96196_ = Maps.newHashMap();

   public static <T extends AbstractContainerMenu> void m_96201_(@Nullable MenuType<T> p_96202_, Minecraft p_96203_, int p_96204_, Component p_96205_) {
      getScreenFactory(p_96202_, p_96203_, p_96204_, p_96205_).ifPresent(f -> f.m_96209_(p_96205_, p_96202_, p_96203_, p_96204_));
   }

   public static <T extends AbstractContainerMenu> java.util.Optional<ScreenConstructor<T, ?>> getScreenFactory(@Nullable MenuType<T> p_96202_, Minecraft p_96203_, int p_96204_, Component p_96205_) {
      if (p_96202_ == null) {
         f_96195_.warn("Trying to open invalid screen with name: {}", (Object)p_96205_.getString());
      } else {
         MenuScreens.ScreenConstructor<T, ?> screenconstructor = m_96199_(p_96202_);
         if (screenconstructor == null) {
            f_96195_.warn("Failed to create screen for menu type: {}", (Object)Registry.f_122863_.m_7981_(p_96202_));
         } else {
            return java.util.Optional.of(screenconstructor);
         }
      }
      return java.util.Optional.empty();
   }

   @Nullable
   private static <T extends AbstractContainerMenu> MenuScreens.ScreenConstructor<T, ?> m_96199_(MenuType<T> p_96200_) {
      return (MenuScreens.ScreenConstructor<T, ?>)f_96196_.get(p_96200_);
   }

   public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void m_96206_(MenuType<? extends M> p_96207_, MenuScreens.ScreenConstructor<M, U> p_96208_) {
      MenuScreens.ScreenConstructor<?, ?> screenconstructor = f_96196_.put(p_96207_, p_96208_);
      if (screenconstructor != null) {
         throw new IllegalStateException("Duplicate registration for " + Registry.f_122863_.m_7981_(p_96207_));
      }
   }

   public static boolean m_96198_() {
      boolean flag = false;

      for(MenuType<?> menutype : Registry.f_122863_) {
         if (!f_96196_.containsKey(menutype)) {
            f_96195_.debug("Menu {} has no matching screen", (Object)Registry.f_122863_.m_7981_(menutype));
            flag = true;
         }
      }

      return flag;
   }

   static {
      m_96206_(MenuType.f_39957_, ContainerScreen::new);
      m_96206_(MenuType.f_39958_, ContainerScreen::new);
      m_96206_(MenuType.f_39959_, ContainerScreen::new);
      m_96206_(MenuType.f_39960_, ContainerScreen::new);
      m_96206_(MenuType.f_39961_, ContainerScreen::new);
      m_96206_(MenuType.f_39962_, ContainerScreen::new);
      m_96206_(MenuType.f_39963_, DispenserScreen::new);
      m_96206_(MenuType.f_39964_, AnvilScreen::new);
      m_96206_(MenuType.f_39965_, BeaconScreen::new);
      m_96206_(MenuType.f_39966_, BlastFurnaceScreen::new);
      m_96206_(MenuType.f_39967_, BrewingStandScreen::new);
      m_96206_(MenuType.f_39968_, CraftingScreen::new);
      m_96206_(MenuType.f_39969_, EnchantmentScreen::new);
      m_96206_(MenuType.f_39970_, FurnaceScreen::new);
      m_96206_(MenuType.f_39971_, GrindstoneScreen::new);
      m_96206_(MenuType.f_39972_, HopperScreen::new);
      m_96206_(MenuType.f_39973_, LecternScreen::new);
      m_96206_(MenuType.f_39974_, LoomScreen::new);
      m_96206_(MenuType.f_39975_, MerchantScreen::new);
      m_96206_(MenuType.f_39976_, ShulkerBoxScreen::new);
      m_96206_(MenuType.f_39977_, SmithingScreen::new);
      m_96206_(MenuType.f_39978_, SmokerScreen::new);
      m_96206_(MenuType.f_39979_, CartographyTableScreen::new);
      m_96206_(MenuType.f_39980_, StonecutterScreen::new);
   }

   @OnlyIn(Dist.CLIENT)
   public interface ScreenConstructor<T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> {
      default void m_96209_(Component p_96210_, MenuType<T> p_96211_, Minecraft p_96212_, int p_96213_) {
         U u = this.m_96214_(p_96211_.m_39985_(p_96213_, p_96212_.f_91074_.m_150109_()), p_96212_.f_91074_.m_150109_(), p_96210_);
         p_96212_.f_91074_.f_36096_ = u.m_6262_();
         p_96212_.m_91152_(u);
      }

      U m_96214_(T p_96215_, Inventory p_96216_, Component p_96217_);
   }
}
