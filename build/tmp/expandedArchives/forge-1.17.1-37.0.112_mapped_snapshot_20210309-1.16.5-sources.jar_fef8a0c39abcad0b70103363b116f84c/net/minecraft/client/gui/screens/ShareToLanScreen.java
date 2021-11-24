package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShareToLanScreen extends Screen {
   private static final Component f_96640_ = new TranslatableComponent("selectWorld.allowCommands");
   private static final Component f_96641_ = new TranslatableComponent("selectWorld.gameMode");
   private static final Component f_96642_ = new TranslatableComponent("lanServer.otherPlayers");
   private final Screen f_96643_;
   private GameType f_169427_ = GameType.SURVIVAL;
   private boolean f_96647_;

   public ShareToLanScreen(Screen p_96650_) {
      super(new TranslatableComponent("lanServer.title"));
      this.f_96643_ = p_96650_;
   }

   protected void m_7856_() {
      this.m_142416_(CycleButton.m_168894_(GameType::m_151500_).m_168961_(GameType.SURVIVAL, GameType.SPECTATOR, GameType.CREATIVE, GameType.ADVENTURE).m_168948_(this.f_169427_).m_168936_(this.f_96543_ / 2 - 155, 100, 150, 20, f_96641_, (p_169429_, p_169430_) -> {
         this.f_169427_ = p_169430_;
      }));
      this.m_142416_(CycleButton.m_168916_(this.f_96647_).m_168936_(this.f_96543_ / 2 + 5, 100, 150, 20, f_96640_, (p_169432_, p_169433_) -> {
         this.f_96647_ = p_169433_;
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 28, 150, 20, new TranslatableComponent("lanServer.start"), (p_96660_) -> {
         this.f_96541_.m_91152_((Screen)null);
         int i = HttpUtil.m_13939_();
         Component component;
         if (this.f_96541_.m_91092_().m_7386_(this.f_169427_, this.f_96647_, i)) {
            component = new TranslatableComponent("commands.publish.started", i);
         } else {
            component = new TranslatableComponent("commands.publish.failed");
         }

         this.f_96541_.f_91065_.m_93076_().m_93785_(component);
         this.f_96541_.m_91341_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, (p_96657_) -> {
         this.f_96541_.m_91152_(this.f_96643_);
      }));
   }

   public void m_6305_(PoseStack p_96652_, int p_96653_, int p_96654_, float p_96655_) {
      this.m_7333_(p_96652_);
      m_93215_(p_96652_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 50, 16777215);
      m_93215_(p_96652_, this.f_96547_, f_96642_, this.f_96543_ / 2, 82, 16777215);
      super.m_6305_(p_96652_, p_96653_, p_96654_, p_96655_);
   }
}