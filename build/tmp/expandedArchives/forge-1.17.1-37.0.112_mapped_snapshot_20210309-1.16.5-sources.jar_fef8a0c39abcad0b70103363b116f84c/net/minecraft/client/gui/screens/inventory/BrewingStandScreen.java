package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BrewingStandScreen extends AbstractContainerScreen<BrewingStandMenu> {
   private static final ResourceLocation f_98328_ = new ResourceLocation("textures/gui/container/brewing_stand.png");
   private static final int[] f_98329_ = new int[]{29, 24, 20, 16, 11, 6, 0};

   public BrewingStandScreen(BrewingStandMenu p_98332_, Inventory p_98333_, Component p_98334_) {
      super(p_98332_, p_98333_, p_98334_);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_97728_ = (this.f_97726_ - this.f_96547_.m_92852_(this.f_96539_)) / 2;
   }

   public void m_6305_(PoseStack p_98341_, int p_98342_, int p_98343_, float p_98344_) {
      this.m_7333_(p_98341_);
      super.m_6305_(p_98341_, p_98342_, p_98343_, p_98344_);
      this.m_7025_(p_98341_, p_98342_, p_98343_);
   }

   protected void m_7286_(PoseStack p_98336_, float p_98337_, int p_98338_, int p_98339_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_98328_);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(p_98336_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      int k = this.f_97732_.m_39102_();
      int l = Mth.m_14045_((18 * k + 20 - 1) / 20, 0, 18);
      if (l > 0) {
         this.m_93228_(p_98336_, i + 60, j + 44, 176, 29, l, 4);
      }

      int i1 = this.f_97732_.m_39103_();
      if (i1 > 0) {
         int j1 = (int)(28.0F * (1.0F - (float)i1 / 400.0F));
         if (j1 > 0) {
            this.m_93228_(p_98336_, i + 97, j + 16, 176, 0, 9, j1);
         }

         j1 = f_98329_[i1 / 2 % 7];
         if (j1 > 0) {
            this.m_93228_(p_98336_, i + 63, j + 14 + 29 - j1, 185, 29 - j1, 12, j1);
         }
      }

   }
}