package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.worldupdate.WorldUpgrader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class OptimizeWorldScreen extends Screen {
   private static final Logger f_101298_ = LogManager.getLogger();
   private static final Object2IntMap<ResourceKey<Level>> f_101299_ = Util.m_137469_(new Object2IntOpenCustomHashMap<>(Util.m_137583_()), (p_101324_) -> {
      p_101324_.put(Level.f_46428_, -13408734);
      p_101324_.put(Level.f_46429_, -10075085);
      p_101324_.put(Level.f_46430_, -8943531);
      p_101324_.defaultReturnValue(-2236963);
   });
   private final BooleanConsumer f_101300_;
   private final WorldUpgrader f_101301_;

   @Nullable
   public static OptimizeWorldScreen m_101315_(Minecraft p_101316_, BooleanConsumer p_101317_, DataFixer p_101318_, LevelStorageSource.LevelStorageAccess p_101319_, boolean p_101320_) {
      RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();

      try {
         Minecraft.ServerStem minecraft$serverstem = p_101316_.m_91190_(registryaccess$registryholder, Minecraft::m_91133_, Minecraft::m_91135_, false, p_101319_);

         OptimizeWorldScreen optimizeworldscreen;
         try {
            WorldData worlddata = minecraft$serverstem.m_91434_();
            p_101319_.m_78287_(registryaccess$registryholder, worlddata);
            ImmutableSet<ResourceKey<Level>> immutableset = worlddata.m_5961_().m_64667_();
            optimizeworldscreen = new OptimizeWorldScreen(p_101317_, p_101318_, p_101319_, worlddata.m_5926_(), p_101320_, immutableset);
         } catch (Throwable throwable1) {
            if (minecraft$serverstem != null) {
               try {
                  minecraft$serverstem.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (minecraft$serverstem != null) {
            minecraft$serverstem.close();
         }

         return optimizeworldscreen;
      } catch (Exception exception) {
         f_101298_.warn("Failed to load datapacks, can't optimize world", (Throwable)exception);
         return null;
      }
   }

   private OptimizeWorldScreen(BooleanConsumer p_101304_, DataFixer p_101305_, LevelStorageSource.LevelStorageAccess p_101306_, LevelSettings p_101307_, boolean p_101308_, ImmutableSet<ResourceKey<Level>> p_101309_) {
      super(new TranslatableComponent("optimizeWorld.title", p_101307_.m_46917_()));
      this.f_101300_ = p_101304_;
      this.f_101301_ = new WorldUpgrader(p_101306_, p_101305_, p_101309_, p_101308_);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 150, 200, 20, CommonComponents.f_130656_, (p_101322_) -> {
         this.f_101301_.m_18820_();
         this.f_101300_.accept(false);
      }));
   }

   public void m_96624_() {
      if (this.f_101301_.m_18829_()) {
         this.f_101300_.accept(true);
      }

   }

   public void m_7379_() {
      this.f_101300_.accept(false);
   }

   public void m_7861_() {
      this.f_101301_.m_18820_();
   }

   public void m_6305_(PoseStack p_101311_, int p_101312_, int p_101313_, float p_101314_) {
      this.m_7333_(p_101311_);
      m_93215_(p_101311_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 16777215);
      int i = this.f_96543_ / 2 - 150;
      int j = this.f_96543_ / 2 + 150;
      int k = this.f_96544_ / 4 + 100;
      int l = k + 10;
      m_93215_(p_101311_, this.f_96547_, this.f_101301_.m_18837_(), this.f_96543_ / 2, k - 9 - 2, 10526880);
      if (this.f_101301_.m_18834_() > 0) {
         m_93172_(p_101311_, i - 1, k - 1, j + 1, l + 1, -16777216);
         m_93243_(p_101311_, this.f_96547_, new TranslatableComponent("optimizeWorld.info.converted", this.f_101301_.m_18835_()), i, 40, 10526880);
         m_93243_(p_101311_, this.f_96547_, new TranslatableComponent("optimizeWorld.info.skipped", this.f_101301_.m_18836_()), i, 40 + 9 + 3, 10526880);
         m_93243_(p_101311_, this.f_96547_, new TranslatableComponent("optimizeWorld.info.total", this.f_101301_.m_18834_()), i, 40 + (9 + 3) * 2, 10526880);
         int i1 = 0;

         for(ResourceKey<Level> resourcekey : this.f_101301_.m_18832_()) {
            int j1 = Mth.m_14143_(this.f_101301_.m_18827_(resourcekey) * (float)(j - i));
            m_93172_(p_101311_, i + i1, k, i + i1 + j1, l, f_101299_.getInt(resourcekey));
            i1 += j1;
         }

         int k1 = this.f_101301_.m_18835_() + this.f_101301_.m_18836_();
         m_93208_(p_101311_, this.f_96547_, k1 + " / " + this.f_101301_.m_18834_(), this.f_96543_ / 2, k + 2 * 9 + 2, 10526880);
         m_93208_(p_101311_, this.f_96547_, Mth.m_14143_(this.f_101301_.m_18833_() * 100.0F) + "%", this.f_96543_ / 2, k + (l - k) / 2 - 9 / 2, 10526880);
      }

      super.m_6305_(p_101311_, p_101312_, p_101313_, p_101314_);
   }
}