package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.util.stream.IntStream;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SignEditScreen extends Screen {
   private final SignBlockEntity f_99254_;
   private int f_99255_;
   private int f_99256_;
   private TextFieldHelper f_99257_;
   private WoodType f_169809_;
   private SignRenderer.SignModel f_99253_;
   private final String[] f_99258_;

   public SignEditScreen(SignBlockEntity p_169811_, boolean p_169812_) {
      super(new TranslatableComponent("sign.edit"));
      this.f_99258_ = IntStream.range(0, 4).mapToObj((p_169818_) -> {
         return p_169811_.m_155706_(p_169818_, p_169812_);
      }).map(Component::getString).toArray((p_169814_) -> {
         return new String[p_169814_];
      });
      this.f_99254_ = p_169811_;
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120, 200, 20, CommonComponents.f_130655_, (p_169820_) -> {
         this.m_99285_();
      }));
      this.f_99254_.m_59746_(false);
      this.f_99257_ = new TextFieldHelper(() -> {
         return this.f_99258_[this.f_99256_];
      }, (p_169824_) -> {
         this.f_99258_[this.f_99256_] = p_169824_;
         this.f_99254_.m_59732_(this.f_99256_, new TextComponent(p_169824_));
      }, TextFieldHelper.m_95153_(this.f_96541_), TextFieldHelper.m_95182_(this.f_96541_), (p_169822_) -> {
         return this.f_96541_.f_91062_.m_92895_(p_169822_) <= 90;
      });
      BlockState blockstate = this.f_99254_.m_58900_();
      this.f_169809_ = SignRenderer.m_173637_(blockstate.m_60734_());
      this.f_99253_ = SignRenderer.m_173646_(this.f_96541_.m_167973_(), this.f_169809_);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
      ClientPacketListener clientpacketlistener = this.f_96541_.m_91403_();
      if (clientpacketlistener != null) {
         clientpacketlistener.m_104955_(new ServerboundSignUpdatePacket(this.f_99254_.m_58899_(), this.f_99258_[0], this.f_99258_[1], this.f_99258_[2], this.f_99258_[3]));
      }

      this.f_99254_.m_59746_(true);
   }

   public void m_96624_() {
      ++this.f_99255_;
      if (!this.f_99254_.m_58903_().m_155262_(this.f_99254_.m_58900_())) {
         this.m_99285_();
      }

   }

   private void m_99285_() {
      this.f_99254_.m_6596_();
      this.f_96541_.m_91152_((Screen)null);
   }

   public boolean m_5534_(char p_99262_, int p_99263_) {
      this.f_99257_.m_95143_(p_99262_);
      return true;
   }

   public void m_7379_() {
      this.m_99285_();
   }

   public boolean m_7933_(int p_99267_, int p_99268_, int p_99269_) {
      if (p_99267_ == 265) {
         this.f_99256_ = this.f_99256_ - 1 & 3;
         this.f_99257_.m_95193_();
         return true;
      } else if (p_99267_ != 264 && p_99267_ != 257 && p_99267_ != 335) {
         return this.f_99257_.m_95145_(p_99267_) ? true : super.m_7933_(p_99267_, p_99268_, p_99269_);
      } else {
         this.f_99256_ = this.f_99256_ + 1 & 3;
         this.f_99257_.m_95193_();
         return true;
      }
   }

   public void m_6305_(PoseStack p_99271_, int p_99272_, int p_99273_, float p_99274_) {
      Lighting.m_84930_();
      this.m_7333_(p_99271_);
      m_93215_(p_99271_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 40, 16777215);
      p_99271_.m_85836_();
      p_99271_.m_85837_((double)(this.f_96543_ / 2), 0.0D, 50.0D);
      float f = 93.75F;
      p_99271_.m_85841_(93.75F, -93.75F, 93.75F);
      p_99271_.m_85837_(0.0D, -1.3125D, 0.0D);
      BlockState blockstate = this.f_99254_.m_58900_();
      boolean flag = blockstate.m_60734_() instanceof StandingSignBlock;
      if (!flag) {
         p_99271_.m_85837_(0.0D, -0.3125D, 0.0D);
      }

      boolean flag1 = this.f_99255_ / 6 % 2 == 0;
      float f1 = 0.6666667F;
      p_99271_.m_85836_();
      p_99271_.m_85841_(0.6666667F, -0.6666667F, -0.6666667F);
      MultiBufferSource.BufferSource multibuffersource$buffersource = this.f_96541_.m_91269_().m_110104_();
      Material material = Sheets.m_173381_(this.f_169809_);
      VertexConsumer vertexconsumer = material.m_119194_(multibuffersource$buffersource, this.f_99253_::m_103119_);
      this.f_99253_.f_112507_.f_104207_ = flag;
      this.f_99253_.f_173655_.m_104301_(p_99271_, vertexconsumer, 15728880, OverlayTexture.f_118083_);
      p_99271_.m_85849_();
      float f2 = 0.010416667F;
      p_99271_.m_85837_(0.0D, (double)0.33333334F, (double)0.046666667F);
      p_99271_.m_85841_(0.010416667F, -0.010416667F, 0.010416667F);
      int i = this.f_99254_.m_59753_().m_41071_();
      int j = this.f_99257_.m_95194_();
      int k = this.f_99257_.m_95197_();
      int l = this.f_99256_ * 10 - this.f_99258_.length * 5;
      Matrix4f matrix4f = p_99271_.m_85850_().m_85861_();

      for(int i1 = 0; i1 < this.f_99258_.length; ++i1) {
         String s = this.f_99258_[i1];
         if (s != null) {
            if (this.f_96547_.m_92718_()) {
               s = this.f_96547_.m_92801_(s);
            }

            float f3 = (float)(-this.f_96541_.f_91062_.m_92895_(s) / 2);
            this.f_96541_.f_91062_.m_92822_(s, f3, (float)(i1 * 10 - this.f_99258_.length * 5), i, false, matrix4f, multibuffersource$buffersource, false, 0, 15728880, false);
            if (i1 == this.f_99256_ && j >= 0 && flag1) {
               int j1 = this.f_96541_.f_91062_.m_92895_(s.substring(0, Math.max(Math.min(j, s.length()), 0)));
               int k1 = j1 - this.f_96541_.f_91062_.m_92895_(s) / 2;
               if (j >= s.length()) {
                  this.f_96541_.f_91062_.m_92822_("_", (float)k1, (float)l, i, false, matrix4f, multibuffersource$buffersource, false, 0, 15728880, false);
               }
            }
         }
      }

      multibuffersource$buffersource.m_109911_();

      for(int i3 = 0; i3 < this.f_99258_.length; ++i3) {
         String s1 = this.f_99258_[i3];
         if (s1 != null && i3 == this.f_99256_ && j >= 0) {
            int j3 = this.f_96541_.f_91062_.m_92895_(s1.substring(0, Math.max(Math.min(j, s1.length()), 0)));
            int k3 = j3 - this.f_96541_.f_91062_.m_92895_(s1) / 2;
            if (flag1 && j < s1.length()) {
               m_93172_(p_99271_, k3, l - 1, k3 + 1, l + 9, -16777216 | i);
            }

            if (k != j) {
               int l3 = Math.min(j, k);
               int l1 = Math.max(j, k);
               int i2 = this.f_96541_.f_91062_.m_92895_(s1.substring(0, l3)) - this.f_96541_.f_91062_.m_92895_(s1) / 2;
               int j2 = this.f_96541_.f_91062_.m_92895_(s1.substring(0, l1)) - this.f_96541_.f_91062_.m_92895_(s1) / 2;
               int k2 = Math.min(i2, j2);
               int l2 = Math.max(i2, j2);
               Tesselator tesselator = Tesselator.m_85913_();
               BufferBuilder bufferbuilder = tesselator.m_85915_();
               RenderSystem.m_157427_(GameRenderer::m_172811_);
               RenderSystem.m_69472_();
               RenderSystem.m_69479_();
               RenderSystem.m_69835_(GlStateManager.LogicOp.OR_REVERSE);
               bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
               bufferbuilder.m_85982_(matrix4f, (float)k2, (float)(l + 9), 0.0F).m_6122_(0, 0, 255, 255).m_5752_();
               bufferbuilder.m_85982_(matrix4f, (float)l2, (float)(l + 9), 0.0F).m_6122_(0, 0, 255, 255).m_5752_();
               bufferbuilder.m_85982_(matrix4f, (float)l2, (float)l, 0.0F).m_6122_(0, 0, 255, 255).m_5752_();
               bufferbuilder.m_85982_(matrix4f, (float)k2, (float)l, 0.0F).m_6122_(0, 0, 255, 255).m_5752_();
               bufferbuilder.m_85721_();
               BufferUploader.m_85761_(bufferbuilder);
               RenderSystem.m_69462_();
               RenderSystem.m_69493_();
            }
         }
      }

      p_99271_.m_85849_();
      Lighting.m_84931_();
      super.m_6305_(p_99271_, p_99272_, p_99273_, p_99274_);
   }
}