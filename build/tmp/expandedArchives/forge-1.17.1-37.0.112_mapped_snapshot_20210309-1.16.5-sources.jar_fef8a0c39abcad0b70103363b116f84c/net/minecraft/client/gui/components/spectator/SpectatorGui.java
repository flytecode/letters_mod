package net.minecraft.client.gui.components.spectator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenuListener;
import net.minecraft.client.gui.spectator.categories.SpectatorPage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectatorGui extends GuiComponent implements SpectatorMenuListener {
   private static final ResourceLocation f_94761_ = new ResourceLocation("textures/gui/widgets.png");
   public static final ResourceLocation f_94760_ = new ResourceLocation("textures/gui/spectator_widgets.png");
   private static final long f_169074_ = 5000L;
   private static final long f_169075_ = 2000L;
   private final Minecraft f_94762_;
   private long f_94763_;
   private SpectatorMenu f_94764_;

   public SpectatorGui(Minecraft p_94767_) {
      this.f_94762_ = p_94767_;
   }

   public void m_94771_(int p_94772_) {
      this.f_94763_ = Util.m_137550_();
      if (this.f_94764_ != null) {
         this.f_94764_.m_101797_(p_94772_);
      } else {
         this.f_94764_ = new SpectatorMenu(this);
      }

   }

   private float m_94794_() {
      long i = this.f_94763_ - Util.m_137550_() + 5000L;
      return Mth.m_14036_((float)i / 2000.0F, 0.0F, 1.0F);
   }

   public void m_94775_(PoseStack p_94776_, float p_94777_) {
      if (this.f_94764_ != null) {
         float f = this.m_94794_();
         if (f <= 0.0F) {
            this.f_94764_.m_101800_();
         } else {
            int i = this.f_94762_.m_91268_().m_85445_() / 2;
            int j = this.m_93252_();
            this.m_93250_(-90);
            int k = Mth.m_14143_((float)this.f_94762_.m_91268_().m_85446_() - 22.0F * f);
            SpectatorPage spectatorpage = this.f_94764_.m_101802_();
            this.m_94778_(p_94776_, f, i, k, spectatorpage);
            this.m_93250_(j);
         }
      }
   }

   protected void m_94778_(PoseStack p_94779_, float p_94780_, int p_94781_, int p_94782_, SpectatorPage p_94783_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, p_94780_);
      RenderSystem.m_157456_(0, f_94761_);
      this.m_93228_(p_94779_, p_94781_ - 91, p_94782_, 0, 0, 182, 22);
      if (p_94783_.m_101853_() >= 0) {
         this.m_93228_(p_94779_, p_94781_ - 91 - 1 + p_94783_.m_101853_() * 20, p_94782_ - 1, 0, 22, 24, 22);
      }

      for(int i = 0; i < 9; ++i) {
         this.m_94784_(p_94779_, i, this.f_94762_.m_91268_().m_85445_() / 2 - 90 + i * 20 + 2, (float)(p_94782_ + 3), p_94780_, p_94783_.m_101851_(i));
      }

      RenderSystem.m_69461_();
   }

   private void m_94784_(PoseStack p_94785_, int p_94786_, int p_94787_, float p_94788_, float p_94789_, SpectatorMenuItem p_94790_) {
      RenderSystem.m_157456_(0, f_94760_);
      if (p_94790_ != SpectatorMenu.f_101771_) {
         int i = (int)(p_94789_ * 255.0F);
         p_94785_.m_85836_();
         p_94785_.m_85837_((double)p_94787_, (double)p_94788_, 0.0D);
         float f = p_94790_.m_7304_() ? 1.0F : 0.25F;
         RenderSystem.m_157429_(f, f, f, p_94789_);
         p_94790_.m_6252_(p_94785_, f, i);
         p_94785_.m_85849_();
         if (i > 3 && p_94790_.m_7304_()) {
            Component component = this.f_94762_.f_91066_.f_92056_[p_94786_].m_90863_();
            this.f_94762_.f_91062_.m_92763_(p_94785_, component, (float)(p_94787_ + 19 - 2 - this.f_94762_.f_91062_.m_92852_(component)), p_94788_ + 6.0F + 3.0F, 16777215 + (i << 24));
         }
      }

   }

   public void m_94773_(PoseStack p_94774_) {
      int i = (int)(this.m_94794_() * 255.0F);
      if (i > 3 && this.f_94764_ != null) {
         SpectatorMenuItem spectatormenuitem = this.f_94764_.m_101796_();
         Component component = spectatormenuitem == SpectatorMenu.f_101771_ ? this.f_94764_.m_101799_().m_5878_() : spectatormenuitem.m_7869_();
         if (component != null) {
            int j = (this.f_94762_.m_91268_().m_85445_() - this.f_94762_.f_91062_.m_92852_(component)) / 2;
            int k = this.f_94762_.m_91268_().m_85446_() - 35;
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            this.f_94762_.f_91062_.m_92763_(p_94774_, component, (float)j, (float)k, 16777215 + (i << 24));
            RenderSystem.m_69461_();
         }
      }

   }

   public void m_7613_(SpectatorMenu p_94792_) {
      this.f_94764_ = null;
      this.f_94763_ = 0L;
   }

   public boolean m_94768_() {
      return this.f_94764_ != null;
   }

   public void m_94769_(double p_94770_) {
      int i;
      for(i = this.f_94764_.m_101801_() + (int)p_94770_; i >= 0 && i <= 8 && (this.f_94764_.m_101787_(i) == SpectatorMenu.f_101771_ || !this.f_94764_.m_101787_(i).m_7304_()); i = (int)((double)i + p_94770_)) {
      }

      if (i >= 0 && i <= 8) {
         this.f_94764_.m_101797_(i);
         this.f_94763_ = Util.m_137550_();
      }

   }

   public void m_94793_() {
      this.f_94763_ = Util.m_137550_();
      if (this.m_94768_()) {
         int i = this.f_94764_.m_101801_();
         if (i != -1) {
            this.f_94764_.m_101797_(i);
         }
      } else {
         this.f_94764_ = new SpectatorMenu(this);
      }

   }
}