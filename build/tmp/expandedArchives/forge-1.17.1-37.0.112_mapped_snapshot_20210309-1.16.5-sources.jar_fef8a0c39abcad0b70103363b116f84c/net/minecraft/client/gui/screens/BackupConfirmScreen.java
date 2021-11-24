package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BackupConfirmScreen extends Screen {
   @Nullable
   private final Screen f_95537_;
   protected final BackupConfirmScreen.Listener f_95536_;
   private final Component f_95538_;
   private final boolean f_95539_;
   private MultiLineLabel f_95540_ = MultiLineLabel.f_94331_;
   protected int f_169233_;
   private Checkbox f_95541_;

   public BackupConfirmScreen(@Nullable Screen p_95543_, BackupConfirmScreen.Listener p_95544_, Component p_95545_, Component p_95546_, boolean p_95547_) {
      super(p_95545_);
      this.f_95537_ = p_95543_;
      this.f_95536_ = p_95544_;
      this.f_95538_ = p_95546_;
      this.f_95539_ = p_95547_;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_95540_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_95538_, this.f_96543_ - 50);
      int i = (this.f_95540_.m_5770_() + 1) * 9;
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, 100 + i, 150, 20, new TranslatableComponent("selectWorld.backupJoinConfirmButton"), (p_95564_) -> {
         this.f_95536_.m_95565_(true, this.f_95541_.m_93840_());
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, 100 + i, 150, 20, new TranslatableComponent("selectWorld.backupJoinSkipButton"), (p_95562_) -> {
         this.f_95536_.m_95565_(false, this.f_95541_.m_93840_());
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 80, 124 + i, 150, 20, CommonComponents.f_130656_, (p_95558_) -> {
         this.f_96541_.m_91152_(this.f_95537_);
      }));
      this.f_95541_ = new Checkbox(this.f_96543_ / 2 - 155 + 80, 76 + i, 150, 20, new TranslatableComponent("selectWorld.backupEraseCache"), false);
      if (this.f_95539_) {
         this.m_142416_(this.f_95541_);
      }

   }

   public void m_6305_(PoseStack p_95553_, int p_95554_, int p_95555_, float p_95556_) {
      this.m_7333_(p_95553_);
      m_93215_(p_95553_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 50, 16777215);
      this.f_95540_.m_6276_(p_95553_, this.f_96543_ / 2, 70);
      super.m_6305_(p_95553_, p_95554_, p_95555_, p_95556_);
   }

   public boolean m_6913_() {
      return false;
   }

   public boolean m_7933_(int p_95549_, int p_95550_, int p_95551_) {
      if (p_95549_ == 256) {
         this.f_96541_.m_91152_(this.f_95537_);
         return true;
      } else {
         return super.m_7933_(p_95549_, p_95550_, p_95551_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public interface Listener {
      void m_95565_(boolean p_95566_, boolean p_95567_);
   }
}