package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class EffectRenderingInventoryScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
   protected boolean f_98699_;

   public EffectRenderingInventoryScreen(T p_98701_, Inventory p_98702_, Component p_98703_) {
      super(p_98701_, p_98702_, p_98703_);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_7824_();
   }

   protected void m_7824_() {
      if (this.f_96541_.f_91074_.m_21220_().isEmpty()) {
         this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
         this.f_98699_ = false;
      } else {
         if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent(this)))
            this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
         else
         this.f_97735_ = 160 + (this.f_96543_ - this.f_97726_ - 200) / 2;
         this.f_98699_ = true;
      }

   }

   public void m_6305_(PoseStack p_98705_, int p_98706_, int p_98707_, float p_98708_) {
      super.m_6305_(p_98705_, p_98706_, p_98707_, p_98708_);
      if (this.f_98699_) {
         this.m_98715_(p_98705_);
      }

   }

   private void m_98715_(PoseStack p_98716_) {
      int i = this.f_97735_ - 124;
      Collection<MobEffectInstance> collection = this.f_96541_.f_91074_.m_21220_();
      if (!collection.isEmpty()) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         int j = 33;
         if (collection.size() > 5) {
            j = 132 / (collection.size() - 1);
         }

         Iterable<MobEffectInstance> iterable = collection.stream().filter(net.minecraftforge.client.ForgeHooksClient::shouldRender).sorted().collect(java.util.stream.Collectors.toList());
         this.m_98709_(p_98716_, i, j, iterable);
         this.m_98717_(p_98716_, i, j, iterable);
         this.m_98722_(p_98716_, i, j, iterable);
      }
   }

   private void m_98709_(PoseStack p_98710_, int p_98711_, int p_98712_, Iterable<MobEffectInstance> p_98713_) {
      RenderSystem.m_157456_(0, f_97725_);
      int i = this.f_97736_;

      for(MobEffectInstance mobeffectinstance : p_98713_) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         this.m_93228_(p_98710_, p_98711_, i, 0, 166, 140, 32);
         i += p_98712_;
      }

   }

   private void m_98717_(PoseStack p_98718_, int p_98719_, int p_98720_, Iterable<MobEffectInstance> p_98721_) {
      MobEffectTextureManager mobeffecttexturemanager = this.f_96541_.m_91306_();
      int i = this.f_97736_;

      for(MobEffectInstance mobeffectinstance : p_98721_) {
         MobEffect mobeffect = mobeffectinstance.m_19544_();
         TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.m_118732_(mobeffect);
         RenderSystem.m_157456_(0, textureatlassprite.m_118414_().m_118330_());
         m_93200_(p_98718_, p_98719_ + 6, i + 7, this.m_93252_(), 18, 18, textureatlassprite);
         i += p_98720_;
      }

   }

   private void m_98722_(PoseStack p_98723_, int p_98724_, int p_98725_, Iterable<MobEffectInstance> p_98726_) {
      int i = this.f_97736_;

      for(MobEffectInstance mobeffectinstance : p_98726_) {
         net.minecraftforge.client.EffectRenderer renderer = net.minecraftforge.client.RenderProperties.getEffectRenderer(mobeffectinstance);
         renderer.renderInventoryEffect(mobeffectinstance, this, p_98723_, p_98724_, i, this.m_93252_());
         if (!renderer.shouldRenderInvText(mobeffectinstance)) {
            i += p_98725_;
            continue;
         }
         String s = I18n.m_118938_(mobeffectinstance.m_19544_().m_19481_());
         if (mobeffectinstance.m_19564_() >= 1 && mobeffectinstance.m_19564_() <= 9) {
            s = s + " " + I18n.m_118938_("enchantment.level." + (mobeffectinstance.m_19564_() + 1));
         }

         this.f_96547_.m_92750_(p_98723_, s, (float)(p_98724_ + 10 + 18), (float)(i + 6), 16777215);
         String s1 = MobEffectUtil.m_19581_(mobeffectinstance, 1.0F);
         this.f_96547_.m_92750_(p_98723_, s1, (float)(p_98724_ + 10 + 18), (float)(i + 6 + 10), 8355711);
         i += p_98725_;
      }

   }
}
