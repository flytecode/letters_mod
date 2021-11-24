package net.minecraft.client.gui.screens.worldselection;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class SelectWorldScreen extends Screen {
   private static final Logger f_170237_ = LogManager.getLogger();
   protected final Screen f_101329_;
   private List<FormattedCharSequence> f_101331_;
   private Button f_101332_;
   private Button f_101333_;
   private Button f_101334_;
   private Button f_101335_;
   protected EditBox f_101330_;
   private WorldSelectionList f_101336_;

   public SelectWorldScreen(Screen p_101338_) {
      super(new TranslatableComponent("selectWorld.title"));
      this.f_101329_ = p_101338_;
   }

   public boolean m_6050_(double p_101343_, double p_101344_, double p_101345_) {
      return super.m_6050_(p_101343_, p_101344_, p_101345_);
   }

   public void m_96624_() {
      this.f_101330_.m_94120_();
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_101330_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 22, 200, 20, this.f_101330_, new TranslatableComponent("selectWorld.search"));
      this.f_101330_.m_94151_((p_101362_) -> {
         this.f_101336_.m_101676_(() -> {
            return p_101362_;
         }, false);
      });
      this.f_101336_ = new WorldSelectionList(this, this.f_96541_, this.f_96543_, this.f_96544_, 48, this.f_96544_ - 64, 36, () -> {
         return this.f_101330_.m_94155_();
      }, this.f_101336_);
      this.m_7787_(this.f_101330_);
      this.m_7787_(this.f_101336_);
      this.f_101333_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 52, 150, 20, new TranslatableComponent("selectWorld.select"), (p_101378_) -> {
         this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101704_);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 52, 150, 20, new TranslatableComponent("selectWorld.create"), (p_101376_) -> {
         this.f_96541_.m_91152_(CreateWorldScreen.m_100898_(this));
      }));
      this.f_101334_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 28, 72, 20, new TranslatableComponent("selectWorld.edit"), (p_101373_) -> {
         this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101739_);
      }));
      this.f_101332_ = this.m_142416_(new Button(this.f_96543_ / 2 - 76, this.f_96544_ - 28, 72, 20, new TranslatableComponent("selectWorld.delete"), (p_101366_) -> {
         this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101738_);
      }));
      this.f_101335_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 28, 72, 20, new TranslatableComponent("selectWorld.recreate"), (p_101360_) -> {
         this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101743_);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 82, this.f_96544_ - 28, 72, 20, CommonComponents.f_130656_, (p_101356_) -> {
         this.f_96541_.m_91152_(this.f_101329_);
      }));
      this.m_101369_(false);
      this.m_94718_(this.f_101330_);
   }

   public boolean m_7933_(int p_101347_, int p_101348_, int p_101349_) {
      return super.m_7933_(p_101347_, p_101348_, p_101349_) ? true : this.f_101330_.m_7933_(p_101347_, p_101348_, p_101349_);
   }

   public void m_7379_() {
      this.f_96541_.m_91152_(this.f_101329_);
   }

   public boolean m_5534_(char p_101340_, int p_101341_) {
      return this.f_101330_.m_5534_(p_101340_, p_101341_);
   }

   public void m_6305_(PoseStack p_101351_, int p_101352_, int p_101353_, float p_101354_) {
      this.f_101331_ = null;
      this.f_101336_.m_6305_(p_101351_, p_101352_, p_101353_, p_101354_);
      this.f_101330_.m_6305_(p_101351_, p_101352_, p_101353_, p_101354_);
      m_93215_(p_101351_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 16777215);
      super.m_6305_(p_101351_, p_101352_, p_101353_, p_101354_);
      if (this.f_101331_ != null) {
         this.m_96617_(p_101351_, this.f_101331_, p_101352_, p_101353_);
      }

   }

   public void m_101363_(List<FormattedCharSequence> p_101364_) {
      this.f_101331_ = p_101364_;
   }

   public void m_101369_(boolean p_101370_) {
      this.f_101333_.f_93623_ = p_101370_;
      this.f_101332_.f_93623_ = p_101370_;
      this.f_101334_.f_93623_ = p_101370_;
      this.f_101335_.f_93623_ = p_101370_;
   }

   public void m_7861_() {
      if (this.f_101336_ != null) {
         this.f_101336_.m_6702_().forEach(WorldSelectionList.WorldListEntry::close);
      }

   }
}