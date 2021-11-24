package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DirectJoinServerScreen extends Screen {
   private static final Component f_95952_ = new TranslatableComponent("addServer.enterIp");
   private Button f_95953_;
   private final ServerData f_95954_;
   private EditBox f_95955_;
   private final BooleanConsumer f_95956_;
   private final Screen f_95957_;

   public DirectJoinServerScreen(Screen p_95960_, BooleanConsumer p_95961_, ServerData p_95962_) {
      super(new TranslatableComponent("selectServer.direct"));
      this.f_95957_ = p_95960_;
      this.f_95954_ = p_95962_;
      this.f_95956_ = p_95961_;
   }

   public void m_96624_() {
      this.f_95955_.m_94120_();
   }

   public boolean m_7933_(int p_95964_, int p_95965_, int p_95966_) {
      if (!this.f_95953_.f_93623_ || this.m_7222_() != this.f_95955_ || p_95964_ != 257 && p_95964_ != 335) {
         return super.m_7933_(p_95964_, p_95965_, p_95966_);
      } else {
         this.m_95986_();
         return true;
      }
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_95953_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 96 + 12, 200, 20, new TranslatableComponent("selectServer.select"), (p_95981_) -> {
         this.m_95986_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 12, 200, 20, CommonComponents.f_130656_, (p_95977_) -> {
         this.f_95956_.accept(false);
      }));
      this.f_95955_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 116, 200, 20, new TranslatableComponent("addServer.enterIp"));
      this.f_95955_.m_94199_(128);
      this.f_95955_.m_94178_(true);
      this.f_95955_.m_94144_(this.f_96541_.f_91066_.f_92066_);
      this.f_95955_.m_94151_((p_95983_) -> {
         this.m_95987_();
      });
      this.m_7787_(this.f_95955_);
      this.m_94718_(this.f_95955_);
      this.m_95987_();
   }

   public void m_6574_(Minecraft p_95973_, int p_95974_, int p_95975_) {
      String s = this.f_95955_.m_94155_();
      this.m_6575_(p_95973_, p_95974_, p_95975_);
      this.f_95955_.m_94144_(s);
   }

   private void m_95986_() {
      this.f_95954_.f_105363_ = this.f_95955_.m_94155_();
      this.f_95956_.accept(true);
   }

   public void m_7379_() {
      this.f_96541_.m_91152_(this.f_95957_);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
      this.f_96541_.f_91066_.f_92066_ = this.f_95955_.m_94155_();
      this.f_96541_.f_91066_.m_92169_();
   }

   private void m_95987_() {
      this.f_95953_.f_93623_ = ServerAddress.m_171867_(this.f_95955_.m_94155_());
   }

   public void m_6305_(PoseStack p_95968_, int p_95969_, int p_95970_, float p_95971_) {
      this.m_7333_(p_95968_);
      m_93215_(p_95968_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 16777215);
      m_93243_(p_95968_, this.f_96547_, f_95952_, this.f_96543_ / 2 - 100, 100, 10526880);
      this.f_95955_.m_6305_(p_95968_, p_95969_, p_95970_, p_95971_);
      super.m_6305_(p_95968_, p_95969_, p_95970_, p_95971_);
   }
}