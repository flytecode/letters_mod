package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.FullscreenResolutionProgressOption;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VideoSettingsScreen extends OptionsSubScreen {
   private static final Component f_96794_ = (new TranslatableComponent("options.graphics.fabulous")).m_130940_(ChatFormatting.ITALIC);
   private static final Component f_96795_ = new TranslatableComponent("options.graphics.warning.message", f_96794_, f_96794_);
   private static final Component f_96796_ = (new TranslatableComponent("options.graphics.warning.title")).m_130940_(ChatFormatting.RED);
   private static final Component f_96797_ = new TranslatableComponent("options.graphics.warning.accept");
   private static final Component f_96798_ = new TranslatableComponent("options.graphics.warning.cancel");
   private static final Component f_96799_ = new TextComponent("\n");
   private static final Option[] f_96800_ = new Option[]{Option.f_91682_, Option.f_91675_, Option.f_91679_, Option.f_91670_, Option.f_91638_, Option.f_91650_, Option.f_91683_, Option.f_91680_, Option.f_91671_, Option.f_91629_, Option.f_91649_, Option.f_91628_, Option.f_91672_, Option.f_91639_, Option.f_91669_, Option.f_91676_, Option.f_91668_};
   private OptionsList f_96801_;
   private final GpuWarnlistManager f_96802_;
   private final int f_96803_;

   public VideoSettingsScreen(Screen p_96806_, Options p_96807_) {
      super(p_96806_, p_96807_, new TranslatableComponent("options.videoTitle"));
      this.f_96802_ = p_96806_.f_96541_.m_91105_();
      this.f_96802_.m_109252_();
      if (p_96807_.f_92115_ == GraphicsStatus.FABULOUS) {
         this.f_96802_.m_109248_();
      }

      this.f_96803_ = p_96807_.f_92027_;
   }

   protected void m_7856_() {
      this.f_96801_ = new OptionsList(this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 32, 25);
      this.f_96801_.m_94471_(new FullscreenResolutionProgressOption(this.f_96541_.m_91268_()));
      this.f_96801_.m_94471_(Option.f_91653_);
      this.f_96801_.m_94476_(f_96800_);
      this.m_7787_(this.f_96801_);
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 27, 200, 20, CommonComponents.f_130655_, (p_96827_) -> {
         this.f_96541_.f_91066_.m_92169_();
         this.f_96541_.m_91268_().m_85437_();
         this.f_96541_.m_91152_(this.f_96281_);
      }));
   }

   public void m_7861_() {
      if (this.f_96282_.f_92027_ != this.f_96803_) {
         this.f_96541_.m_91312_(this.f_96282_.f_92027_);
         this.f_96541_.m_91088_();
      }

      super.m_7861_();
   }

   public boolean m_6375_(double p_96809_, double p_96810_, int p_96811_) {
      int i = this.f_96282_.f_92072_;
      if (super.m_6375_(p_96809_, p_96810_, p_96811_)) {
         if (this.f_96282_.f_92072_ != i) {
            this.f_96541_.m_5741_();
         }

         if (this.f_96802_.m_109250_()) {
            List<Component> list = Lists.newArrayList(f_96795_, f_96799_);
            String s = this.f_96802_.m_109253_();
            if (s != null) {
               list.add(f_96799_);
               list.add((new TranslatableComponent("options.graphics.warning.renderer", s)).m_130940_(ChatFormatting.GRAY));
            }

            String s1 = this.f_96802_.m_109255_();
            if (s1 != null) {
               list.add(f_96799_);
               list.add((new TranslatableComponent("options.graphics.warning.vendor", s1)).m_130940_(ChatFormatting.GRAY));
            }

            String s2 = this.f_96802_.m_109254_();
            if (s2 != null) {
               list.add(f_96799_);
               list.add((new TranslatableComponent("options.graphics.warning.version", s2)).m_130940_(ChatFormatting.GRAY));
            }

            this.f_96541_.m_91152_(new PopupScreen(f_96796_, list, ImmutableList.of(new PopupScreen.ButtonOption(f_96797_, (p_96821_) -> {
               this.f_96282_.f_92115_ = GraphicsStatus.FABULOUS;
               Minecraft.m_91087_().f_91060_.m_109818_();
               this.f_96802_.m_109248_();
               this.f_96541_.m_91152_(this);
            }), new PopupScreen.ButtonOption(f_96798_, (p_96818_) -> {
               this.f_96802_.m_109249_();
               this.f_96541_.m_91152_(this);
            }))));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean m_6348_(double p_96823_, double p_96824_, int p_96825_) {
      int i = this.f_96282_.f_92072_;
      if (super.m_6348_(p_96823_, p_96824_, p_96825_)) {
         return true;
      } else if (this.f_96801_.m_6348_(p_96823_, p_96824_, p_96825_)) {
         if (this.f_96282_.f_92072_ != i) {
            this.f_96541_.m_5741_();
         }

         return true;
      } else {
         return false;
      }
   }

   public void m_6305_(PoseStack p_96813_, int p_96814_, int p_96815_, float p_96816_) {
      this.m_7333_(p_96813_);
      this.f_96801_.m_6305_(p_96813_, p_96814_, p_96815_, p_96816_);
      m_93215_(p_96813_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 5, 16777215);
      super.m_6305_(p_96813_, p_96814_, p_96815_, p_96816_);
      List<FormattedCharSequence> list = m_96287_(this.f_96801_, p_96814_, p_96815_);
      if (list != null) {
         this.m_96617_(p_96813_, list, p_96814_, p_96815_);
      }

   }
}