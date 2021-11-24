package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseInventoryScreen extends AbstractContainerScreen<HorseInventoryMenu> {
   private static final ResourceLocation f_98811_ = new ResourceLocation("textures/gui/container/horse.png");
   private final AbstractHorse f_98812_;
   private float f_98813_;
   private float f_98814_;

   public HorseInventoryScreen(HorseInventoryMenu p_98817_, Inventory p_98818_, AbstractHorse p_98819_) {
      super(p_98817_, p_98818_, p_98819_.m_5446_());
      this.f_98812_ = p_98819_;
      this.f_96546_ = false;
   }

   protected void m_7286_(PoseStack p_98821_, float p_98822_, int p_98823_, int p_98824_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_98811_);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(p_98821_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      if (this.f_98812_ instanceof AbstractChestedHorse) {
         AbstractChestedHorse abstractchestedhorse = (AbstractChestedHorse)this.f_98812_;
         if (abstractchestedhorse.m_30502_()) {
            this.m_93228_(p_98821_, i + 79, j + 17, 0, this.f_97727_, abstractchestedhorse.m_7488_() * 18, 54);
         }
      }

      if (this.f_98812_.m_6741_()) {
         this.m_93228_(p_98821_, i + 7, j + 35 - 18, 18, this.f_97727_ + 54, 18, 18);
      }

      if (this.f_98812_.m_7482_()) {
         if (this.f_98812_ instanceof Llama) {
            this.m_93228_(p_98821_, i + 7, j + 35, 36, this.f_97727_ + 54, 18, 18);
         } else {
            this.m_93228_(p_98821_, i + 7, j + 35, 0, this.f_97727_ + 54, 18, 18);
         }
      }

      InventoryScreen.m_98850_(i + 51, j + 60, 17, (float)(i + 51) - this.f_98813_, (float)(j + 75 - 50) - this.f_98814_, this.f_98812_);
   }

   public void m_6305_(PoseStack p_98826_, int p_98827_, int p_98828_, float p_98829_) {
      this.m_7333_(p_98826_);
      this.f_98813_ = (float)p_98827_;
      this.f_98814_ = (float)p_98828_;
      super.m_6305_(p_98826_, p_98827_, p_98828_, p_98829_);
      this.m_7025_(p_98826_, p_98827_, p_98828_);
   }
}