package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StonecutterScreen extends AbstractContainerScreen<StonecutterMenu> {
   private static final ResourceLocation f_99303_ = new ResourceLocation("textures/gui/container/stonecutter.png");
   private static final int f_169826_ = 12;
   private static final int f_169827_ = 15;
   private static final int f_169828_ = 4;
   private static final int f_169829_ = 3;
   private static final int f_169830_ = 16;
   private static final int f_169831_ = 18;
   private static final int f_169832_ = 54;
   private static final int f_169833_ = 52;
   private static final int f_169834_ = 14;
   private float f_99304_;
   private boolean f_99305_;
   private int f_99306_;
   private boolean f_99307_;

   public StonecutterScreen(StonecutterMenu p_99310_, Inventory p_99311_, Component p_99312_) {
      super(p_99310_, p_99311_, p_99312_);
      p_99310_.m_40323_(this::m_99354_);
      --this.f_97729_;
   }

   public void m_6305_(PoseStack p_99337_, int p_99338_, int p_99339_, float p_99340_) {
      super.m_6305_(p_99337_, p_99338_, p_99339_, p_99340_);
      this.m_7025_(p_99337_, p_99338_, p_99339_);
   }

   protected void m_7286_(PoseStack p_99328_, float p_99329_, int p_99330_, int p_99331_) {
      this.m_7333_(p_99328_);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_99303_);
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_93228_(p_99328_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      int k = (int)(41.0F * this.f_99304_);
      this.m_93228_(p_99328_, i + 119, j + 15 + k, 176 + (this.m_99353_() ? 0 : 12), 0, 12, 15);
      int l = this.f_97735_ + 52;
      int i1 = this.f_97736_ + 14;
      int j1 = this.f_99306_ + 12;
      this.m_99341_(p_99328_, p_99330_, p_99331_, l, i1, j1);
      this.m_99348_(l, i1, j1);
   }

   protected void m_7025_(PoseStack p_99333_, int p_99334_, int p_99335_) {
      super.m_7025_(p_99333_, p_99334_, p_99335_);
      if (this.f_99307_) {
         int i = this.f_97735_ + 52;
         int j = this.f_97736_ + 14;
         int k = this.f_99306_ + 12;
         List<StonecutterRecipe> list = this.f_97732_.m_40339_();

         for(int l = this.f_99306_; l < k && l < this.f_97732_.m_40340_(); ++l) {
            int i1 = l - this.f_99306_;
            int j1 = i + i1 % 4 * 16;
            int k1 = j + i1 / 4 * 18 + 2;
            if (p_99334_ >= j1 && p_99334_ < j1 + 16 && p_99335_ >= k1 && p_99335_ < k1 + 18) {
               this.m_6057_(p_99333_, list.get(l).m_8043_(), p_99334_, p_99335_);
            }
         }
      }

   }

   private void m_99341_(PoseStack p_99342_, int p_99343_, int p_99344_, int p_99345_, int p_99346_, int p_99347_) {
      for(int i = this.f_99306_; i < p_99347_ && i < this.f_97732_.m_40340_(); ++i) {
         int j = i - this.f_99306_;
         int k = p_99345_ + j % 4 * 16;
         int l = j / 4;
         int i1 = p_99346_ + l * 18 + 2;
         int j1 = this.f_97727_;
         if (i == this.f_97732_.m_40338_()) {
            j1 += 18;
         } else if (p_99343_ >= k && p_99344_ >= i1 && p_99343_ < k + 16 && p_99344_ < i1 + 18) {
            j1 += 36;
         }

         this.m_93228_(p_99342_, k, i1 - 1, 0, j1, 16, 18);
      }

   }

   private void m_99348_(int p_99349_, int p_99350_, int p_99351_) {
      List<StonecutterRecipe> list = this.f_97732_.m_40339_();

      for(int i = this.f_99306_; i < p_99351_ && i < this.f_97732_.m_40340_(); ++i) {
         int j = i - this.f_99306_;
         int k = p_99349_ + j % 4 * 16;
         int l = j / 4;
         int i1 = p_99350_ + l * 18 + 2;
         this.f_96541_.m_91291_().m_115203_(list.get(i).m_8043_(), k, i1);
      }

   }

   public boolean m_6375_(double p_99318_, double p_99319_, int p_99320_) {
      this.f_99305_ = false;
      if (this.f_99307_) {
         int i = this.f_97735_ + 52;
         int j = this.f_97736_ + 14;
         int k = this.f_99306_ + 12;

         for(int l = this.f_99306_; l < k; ++l) {
            int i1 = l - this.f_99306_;
            double d0 = p_99318_ - (double)(i + i1 % 4 * 16);
            double d1 = p_99319_ - (double)(j + i1 / 4 * 18);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.f_97732_.m_6366_(this.f_96541_.f_91074_, l)) {
               Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12495_, 1.0F));
               this.f_96541_.f_91072_.m_105208_((this.f_97732_).f_38840_, l);
               return true;
            }
         }

         i = this.f_97735_ + 119;
         j = this.f_97736_ + 9;
         if (p_99318_ >= (double)i && p_99318_ < (double)(i + 12) && p_99319_ >= (double)j && p_99319_ < (double)(j + 54)) {
            this.f_99305_ = true;
         }
      }

      return super.m_6375_(p_99318_, p_99319_, p_99320_);
   }

   public boolean m_7979_(double p_99322_, double p_99323_, int p_99324_, double p_99325_, double p_99326_) {
      if (this.f_99305_ && this.m_99353_()) {
         int i = this.f_97736_ + 14;
         int j = i + 54;
         this.f_99304_ = ((float)p_99323_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
         this.f_99304_ = Mth.m_14036_(this.f_99304_, 0.0F, 1.0F);
         this.f_99306_ = (int)((double)(this.f_99304_ * (float)this.m_99352_()) + 0.5D) * 4;
         return true;
      } else {
         return super.m_7979_(p_99322_, p_99323_, p_99324_, p_99325_, p_99326_);
      }
   }

   public boolean m_6050_(double p_99314_, double p_99315_, double p_99316_) {
      if (this.m_99353_()) {
         int i = this.m_99352_();
         this.f_99304_ = (float)((double)this.f_99304_ - p_99316_ / (double)i);
         this.f_99304_ = Mth.m_14036_(this.f_99304_, 0.0F, 1.0F);
         this.f_99306_ = (int)((double)(this.f_99304_ * (float)i) + 0.5D) * 4;
      }

      return true;
   }

   private boolean m_99353_() {
      return this.f_99307_ && this.f_97732_.m_40340_() > 12;
   }

   protected int m_99352_() {
      return (this.f_97732_.m_40340_() + 4 - 1) / 4 - 3;
   }

   private void m_99354_() {
      this.f_99307_ = this.f_97732_.m_40341_();
      if (!this.f_99307_) {
         this.f_99304_ = 0.0F;
         this.f_99306_ = 0;
      }

   }
}