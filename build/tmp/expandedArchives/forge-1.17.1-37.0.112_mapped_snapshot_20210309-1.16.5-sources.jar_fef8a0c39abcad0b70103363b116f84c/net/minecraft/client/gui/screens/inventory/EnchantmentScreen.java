package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentScreen extends AbstractContainerScreen<EnchantmentMenu> {
   private static final ResourceLocation f_98747_ = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation f_98748_ = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private final Random f_98750_ = new Random();
   private BookModel f_169756_;
   public int f_98740_;
   public float f_98741_;
   public float f_98742_;
   public float f_98743_;
   public float f_98744_;
   public float f_98745_;
   public float f_98746_;
   private ItemStack f_98751_ = ItemStack.f_41583_;

   public EnchantmentScreen(EnchantmentMenu p_98754_, Inventory p_98755_, Component p_98756_) {
      super(p_98754_, p_98755_, p_98756_);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_169756_ = new BookModel(this.f_96541_.m_167973_().m_171103_(ModelLayers.f_171271_));
   }

   public void m_181908_() {
      super.m_181908_();
      this.m_98772_();
   }

   public boolean m_6375_(double p_98758_, double p_98759_, int p_98760_) {
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;

      for(int k = 0; k < 3; ++k) {
         double d0 = p_98758_ - (double)(i + 60);
         double d1 = p_98759_ - (double)(j + 14 + 19 * k);
         if (d0 >= 0.0D && d1 >= 0.0D && d0 < 108.0D && d1 < 19.0D && this.f_97732_.m_6366_(this.f_96541_.f_91074_, k)) {
            this.f_96541_.f_91072_.m_105208_((this.f_97732_).f_38840_, k);
            return true;
         }
      }

      return super.m_6375_(p_98758_, p_98759_, p_98760_);
   }

   protected void m_7286_(PoseStack p_98762_, float p_98763_, int p_98764_, int p_98765_) {
      Lighting.m_84930_();
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_98747_);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(p_98762_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      int k = (int)this.f_96541_.m_91268_().m_85449_();
      RenderSystem.m_69949_((this.f_96543_ - 320) / 2 * k, (this.f_96544_ - 240) / 2 * k, 320 * k, 240 * k);
      Matrix4f matrix4f = Matrix4f.m_27653_(-0.34F, 0.23F, 0.0F);
      matrix4f.m_27644_(Matrix4f.m_27625_(90.0D, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.m_157183_();
      RenderSystem.m_157425_(matrix4f);
      p_98762_.m_85836_();
      PoseStack.Pose posestack$pose = p_98762_.m_85850_();
      posestack$pose.m_85861_().m_27624_();
      posestack$pose.m_85864_().m_8180_();
      p_98762_.m_85837_(0.0D, (double)3.3F, 1984.0D);
      float f = 5.0F;
      p_98762_.m_85841_(5.0F, 5.0F, 5.0F);
      p_98762_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
      p_98762_.m_85845_(Vector3f.f_122223_.m_122240_(20.0F));
      float f1 = Mth.m_14179_(p_98763_, this.f_98746_, this.f_98745_);
      p_98762_.m_85837_((double)((1.0F - f1) * 0.2F), (double)((1.0F - f1) * 0.1F), (double)((1.0F - f1) * 0.25F));
      float f2 = -(1.0F - f1) * 90.0F - 90.0F;
      p_98762_.m_85845_(Vector3f.f_122225_.m_122240_(f2));
      p_98762_.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
      float f3 = Mth.m_14179_(p_98763_, this.f_98742_, this.f_98741_) + 0.25F;
      float f4 = Mth.m_14179_(p_98763_, this.f_98742_, this.f_98741_) + 0.75F;
      f3 = (f3 - (float)Mth.m_14080_((double)f3)) * 1.6F - 0.3F;
      f4 = (f4 - (float)Mth.m_14080_((double)f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      }

      if (f4 < 0.0F) {
         f4 = 0.0F;
      }

      if (f3 > 1.0F) {
         f3 = 1.0F;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      this.f_169756_.m_102292_(0.0F, f3, f4, f1);
      MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
      VertexConsumer vertexconsumer = multibuffersource$buffersource.m_6299_(this.f_169756_.m_103119_(f_98748_));
      this.f_169756_.m_7695_(p_98762_, vertexconsumer, 15728880, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      multibuffersource$buffersource.m_109911_();
      p_98762_.m_85849_();
      RenderSystem.m_69949_(0, 0, this.f_96541_.m_91268_().m_85441_(), this.f_96541_.m_91268_().m_85442_());
      RenderSystem.m_157424_();
      Lighting.m_84931_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNames.m_98734_().m_98735_((long)this.f_97732_.m_39493_());
      int l = this.f_97732_.m_39492_();

      for(int i1 = 0; i1 < 3; ++i1) {
         int j1 = i + 60;
         int k1 = j1 + 20;
         this.m_93250_(0);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_98747_);
         int l1 = (this.f_97732_).f_39446_[i1];
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         if (l1 == 0) {
            this.m_93228_(p_98762_, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
         } else {
            String s = "" + l1;
            int i2 = 86 - this.f_96547_.m_92895_(s);
            FormattedText formattedtext = EnchantmentNames.m_98734_().m_98737_(this.f_96547_, i2);
            int j2 = 6839882;
            if (((l < i1 + 1 || this.f_96541_.f_91074_.f_36078_ < l1) && !this.f_96541_.f_91074_.m_150110_().f_35937_) || this.f_97732_.f_39447_[i1] == -1) { // Forge: render buttons as disabled when enchantable but enchantability not met on lower levels
               this.m_93228_(p_98762_, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
               this.m_93228_(p_98762_, j1 + 1, j + 15 + 19 * i1, 16 * i1, 239, 16, 16);
               this.f_96547_.m_92857_(formattedtext, k1, j + 16 + 19 * i1, i2, (j2 & 16711422) >> 1);
               j2 = 4226832;
            } else {
               int k2 = p_98764_ - (i + 60);
               int l2 = p_98765_ - (j + 14 + 19 * i1);
               if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                  this.m_93228_(p_98762_, j1, j + 14 + 19 * i1, 0, 204, 108, 19);
                  j2 = 16777088;
               } else {
                  this.m_93228_(p_98762_, j1, j + 14 + 19 * i1, 0, 166, 108, 19);
               }

               this.m_93228_(p_98762_, j1 + 1, j + 15 + 19 * i1, 16 * i1, 223, 16, 16);
               this.f_96547_.m_92857_(formattedtext, k1, j + 16 + 19 * i1, i2, j2);
               j2 = 8453920;
            }

            this.f_96547_.m_92750_(p_98762_, s, (float)(k1 + 86 - this.f_96547_.m_92895_(s)), (float)(j + 16 + 19 * i1 + 7), j2);
         }
      }

   }

   public void m_6305_(PoseStack p_98767_, int p_98768_, int p_98769_, float p_98770_) {
      p_98770_ = this.f_96541_.m_91296_();
      this.m_7333_(p_98767_);
      super.m_6305_(p_98767_, p_98768_, p_98769_, p_98770_);
      this.m_7025_(p_98767_, p_98768_, p_98769_);
      boolean flag = this.f_96541_.f_91074_.m_150110_().f_35937_;
      int i = this.f_97732_.m_39492_();

      for(int j = 0; j < 3; ++j) {
         int k = (this.f_97732_).f_39446_[j];
         Enchantment enchantment = Enchantment.m_44697_((this.f_97732_).f_39447_[j]);
         int l = (this.f_97732_).f_39448_[j];
         int i1 = j + 1;
         if (this.m_6774_(60, 14 + 19 * j, 108, 17, (double)p_98768_, (double)p_98769_) && k > 0) {
            List<Component> list = Lists.newArrayList();
            list.add((new TranslatableComponent("container.enchant.clue", enchantment == null ? "" : enchantment.m_44700_(l))).m_130940_(ChatFormatting.WHITE));
            if (enchantment == null) {
               list.add(new TextComponent(""));
               list.add(new TranslatableComponent("forge.container.enchant.limitedEnchantability").m_130940_(ChatFormatting.RED));
            } else if (!flag) {
               list.add(TextComponent.f_131282_);
               if (this.f_96541_.f_91074_.f_36078_ < k) {
                  list.add((new TranslatableComponent("container.enchant.level.requirement", (this.f_97732_).f_39446_[j])).m_130940_(ChatFormatting.RED));
               } else {
                  MutableComponent mutablecomponent;
                  if (i1 == 1) {
                     mutablecomponent = new TranslatableComponent("container.enchant.lapis.one");
                  } else {
                     mutablecomponent = new TranslatableComponent("container.enchant.lapis.many", i1);
                  }

                  list.add(mutablecomponent.m_130940_(i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED));
                  MutableComponent mutablecomponent1;
                  if (i1 == 1) {
                     mutablecomponent1 = new TranslatableComponent("container.enchant.level.one");
                  } else {
                     mutablecomponent1 = new TranslatableComponent("container.enchant.level.many", i1);
                  }

                  list.add(mutablecomponent1.m_130940_(ChatFormatting.GRAY));
               }
            }

            this.m_96597_(p_98767_, list, p_98768_, p_98769_);
            break;
         }
      }

   }

   public void m_98772_() {
      ItemStack itemstack = this.f_97732_.m_38853_(0).m_7993_();
      if (!ItemStack.m_41728_(itemstack, this.f_98751_)) {
         this.f_98751_ = itemstack;

         do {
            this.f_98743_ += (float)(this.f_98750_.nextInt(4) - this.f_98750_.nextInt(4));
         } while(this.f_98741_ <= this.f_98743_ + 1.0F && this.f_98741_ >= this.f_98743_ - 1.0F);
      }

      ++this.f_98740_;
      this.f_98742_ = this.f_98741_;
      this.f_98746_ = this.f_98745_;
      boolean flag = false;

      for(int i = 0; i < 3; ++i) {
         if ((this.f_97732_).f_39446_[i] != 0) {
            flag = true;
         }
      }

      if (flag) {
         this.f_98745_ += 0.2F;
      } else {
         this.f_98745_ -= 0.2F;
      }

      this.f_98745_ = Mth.m_14036_(this.f_98745_, 0.0F, 1.0F);
      float f1 = (this.f_98743_ - this.f_98741_) * 0.4F;
      float f = 0.2F;
      f1 = Mth.m_14036_(f1, -0.2F, 0.2F);
      this.f_98744_ += (f1 - this.f_98744_) * 0.9F;
      this.f_98741_ += this.f_98744_;
   }
}
