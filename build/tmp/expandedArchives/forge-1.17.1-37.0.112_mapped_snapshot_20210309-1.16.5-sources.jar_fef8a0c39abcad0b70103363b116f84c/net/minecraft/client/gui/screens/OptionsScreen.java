package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.LockIconButton;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.Difficulty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionsScreen extends Screen {
   private static final Option[] f_96234_ = new Option[]{Option.f_91667_};
   private final Screen f_96235_;
   private final Options f_96236_;
   private CycleButton<Difficulty> f_96237_;
   private LockIconButton f_96238_;

   public OptionsScreen(Screen p_96242_, Options p_96243_) {
      super(new TranslatableComponent("options.title"));
      this.f_96235_ = p_96242_;
      this.f_96236_ = p_96243_;
   }

   protected void m_7856_() {
      int i = 0;

      for(Option option : f_96234_) {
         int j = this.f_96543_ / 2 - 155 + i % 2 * 160;
         int k = this.f_96544_ / 6 - 12 + 24 * (i >> 1);
         this.m_142416_(option.m_7496_(this.f_96541_.f_91066_, j, k, 150));
         ++i;
      }

      if (this.f_96541_.f_91073_ != null) {
         this.f_96237_ = this.m_142416_(CycleButton.m_168894_(Difficulty::m_19033_).m_168961_(Difficulty.values()).m_168948_(this.f_96541_.f_91073_.m_46791_()).m_168936_(this.f_96543_ / 2 - 155 + i % 2 * 160, this.f_96544_ / 6 - 12 + 24 * (i >> 1), 150, 20, new TranslatableComponent("options.difficulty"), (p_169330_, p_169331_) -> {
            this.f_96541_.m_91403_().m_104955_(new ServerboundChangeDifficultyPacket(p_169331_));
         }));
         if (this.f_96541_.m_91091_() && !this.f_96541_.f_91073_.m_6106_().m_5466_()) {
            this.f_96237_.m_93674_(this.f_96237_.m_5711_() - 20);
            this.f_96238_ = this.m_142416_(new LockIconButton(this.f_96237_.f_93620_ + this.f_96237_.m_5711_(), this.f_96237_.f_93621_, (p_96278_) -> {
               this.f_96541_.m_91152_(new ConfirmScreen(this::m_96260_, new TranslatableComponent("difficulty.lock.title"), new TranslatableComponent("difficulty.lock.question", this.f_96541_.f_91073_.m_6106_().m_5472_().m_19033_())));
            }));
            this.f_96238_.m_94309_(this.f_96541_.f_91073_.m_6106_().m_5474_());
            this.f_96238_.f_93623_ = !this.f_96238_.m_94302_();
            this.f_96237_.f_93623_ = !this.f_96238_.m_94302_();
         } else {
            this.f_96237_.f_93623_ = false;
         }
      } else {
         this.m_142416_(Option.f_91642_.m_7496_(this.f_96236_, this.f_96543_ / 2 - 155 + i % 2 * 160, this.f_96544_ / 6 - 12 + 24 * (i >> 1), 150));
      }

      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 48 - 6, 150, 20, new TranslatableComponent("options.skinCustomisation"), (p_96276_) -> {
         this.f_96541_.m_91152_(new SkinCustomizationScreen(this, this.f_96236_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 48 - 6, 150, 20, new TranslatableComponent("options.sounds"), (p_96274_) -> {
         this.f_96541_.m_91152_(new SoundOptionsScreen(this, this.f_96236_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 72 - 6, 150, 20, new TranslatableComponent("options.video"), (p_96272_) -> {
         this.f_96541_.m_91152_(new VideoSettingsScreen(this, this.f_96236_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 72 - 6, 150, 20, new TranslatableComponent("options.controls"), (p_96270_) -> {
         this.f_96541_.m_91152_(new ControlsScreen(this, this.f_96236_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 96 - 6, 150, 20, new TranslatableComponent("options.language"), (p_96268_) -> {
         this.f_96541_.m_91152_(new LanguageSelectScreen(this, this.f_96236_, this.f_96541_.m_91102_()));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 96 - 6, 150, 20, new TranslatableComponent("options.chat.title"), (p_96266_) -> {
         this.f_96541_.m_91152_(new ChatOptionsScreen(this, this.f_96236_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 120 - 6, 150, 20, new TranslatableComponent("options.resourcepack"), (p_96263_) -> {
         this.f_96541_.m_91152_(new PackSelectionScreen(this, this.f_96541_.m_91099_(), this::m_96244_, this.f_96541_.m_91101_(), new TranslatableComponent("resourcePack.title")));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 120 - 6, 150, 20, new TranslatableComponent("options.accessibility.title"), (p_96259_) -> {
         this.f_96541_.m_91152_(new AccessibilityOptionsScreen(this, this.f_96236_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168, 200, 20, CommonComponents.f_130655_, (p_96257_) -> {
         this.f_96541_.m_91152_(this.f_96235_);
      }));
   }

   private void m_96244_(PackRepository p_96245_) {
      List<String> list = ImmutableList.copyOf(this.f_96236_.f_92117_);
      this.f_96236_.f_92117_.clear();
      this.f_96236_.f_92118_.clear();

      for(Pack pack : p_96245_.m_10524_()) {
         if (!pack.m_10450_()) {
            this.f_96236_.f_92117_.add(pack.m_10446_());
            if (!pack.m_10443_().m_10489_()) {
               this.f_96236_.f_92118_.add(pack.m_10446_());
            }
         }
      }

      this.f_96236_.m_92169_();
      List<String> list1 = ImmutableList.copyOf(this.f_96236_.f_92117_);
      if (!list1.equals(list)) {
         this.f_96541_.m_91391_();
      }

   }

   private void m_96260_(boolean p_96261_) {
      this.f_96541_.m_91152_(this);
      if (p_96261_ && this.f_96541_.f_91073_ != null) {
         this.f_96541_.m_91403_().m_104955_(new ServerboundLockDifficultyPacket(true));
         this.f_96238_.m_94309_(true);
         this.f_96238_.f_93623_ = false;
         this.f_96237_.f_93623_ = false;
      }

   }

   public void m_7861_() {
      this.f_96236_.m_92169_();
   }

   public void m_6305_(PoseStack p_96249_, int p_96250_, int p_96251_, float p_96252_) {
      this.m_7333_(p_96249_);
      m_93215_(p_96249_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      super.m_6305_(p_96249_, p_96250_, p_96251_, p_96252_);
   }

    @Override
    public void m_7379_() {
        // We need to consider 2 potential parent screens here:
        // 1. From the main menu, in which case display the main menu
        // 2. From the pause menu, in which case exit back to game
        this.f_96541_.m_91152_(this.f_96235_ instanceof PauseScreen ? null : this.f_96235_);
    }
}
