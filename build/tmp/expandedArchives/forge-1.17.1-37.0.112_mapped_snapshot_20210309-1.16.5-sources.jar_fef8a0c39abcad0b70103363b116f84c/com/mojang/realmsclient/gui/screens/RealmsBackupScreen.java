package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.RealmsUtil;
import com.mojang.realmsclient.util.task.DownloadTask;
import com.mojang.realmsclient.util.task.RestoreTask;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsBackupScreen extends RealmsScreen {
   static final Logger f_88110_ = LogManager.getLogger();
   static final ResourceLocation f_88111_ = new ResourceLocation("realms", "textures/gui/realms/plus_icon.png");
   static final ResourceLocation f_88112_ = new ResourceLocation("realms", "textures/gui/realms/restore_icon.png");
   static final Component f_88113_ = new TranslatableComponent("mco.backup.button.restore");
   static final Component f_88114_ = new TranslatableComponent("mco.backup.changes.tooltip");
   private static final Component f_88115_ = new TranslatableComponent("mco.configure.world.backup");
   private static final Component f_88116_ = new TranslatableComponent("mco.backup.nobackups");
   static int f_88117_ = -1;
   private final RealmsConfigureWorldScreen f_88118_;
   List<Backup> f_88119_ = Collections.emptyList();
   @Nullable
   Component f_88120_;
   RealmsBackupScreen.BackupObjectSelectionList f_88121_;
   int f_88122_ = -1;
   private final int f_88123_;
   private Button f_88104_;
   private Button f_88105_;
   private Button f_88106_;
   Boolean f_88107_ = false;
   final RealmsServer f_88108_;
   private static final String f_167355_ = "Uploaded";

   public RealmsBackupScreen(RealmsConfigureWorldScreen p_88126_, RealmsServer p_88127_, int p_88128_) {
      super(new TranslatableComponent("mco.configure.world.backup"));
      this.f_88118_ = p_88126_;
      this.f_88108_ = p_88127_;
      this.f_88123_ = p_88128_;
   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_88121_ = new RealmsBackupScreen.BackupObjectSelectionList();
      if (f_88117_ != -1) {
         this.f_88121_.m_93410_((double)f_88117_);
      }

      (new Thread("Realms-fetch-backups") {
         public void run() {
            RealmsClient realmsclient = RealmsClient.m_87169_();

            try {
               List<Backup> list = realmsclient.m_87230_(RealmsBackupScreen.this.f_88108_.f_87473_).f_87405_;
               RealmsBackupScreen.this.f_96541_.execute(() -> {
                  RealmsBackupScreen.this.f_88119_ = list;
                  RealmsBackupScreen.this.f_88107_ = RealmsBackupScreen.this.f_88119_.isEmpty();
                  RealmsBackupScreen.this.f_88121_.m_7178_();

                  for(Backup backup : RealmsBackupScreen.this.f_88119_) {
                     RealmsBackupScreen.this.f_88121_.m_88234_(backup);
                  }

                  RealmsBackupScreen.this.m_88199_();
               });
            } catch (RealmsServiceException realmsserviceexception) {
               RealmsBackupScreen.f_88110_.error("Couldn't request backups", (Throwable)realmsserviceexception);
            }

         }
      }).start();
      this.f_88104_ = this.m_142416_(new Button(this.f_96543_ - 135, m_120774_(1), 120, 20, new TranslatableComponent("mco.backup.button.download"), (p_88185_) -> {
         this.m_88207_();
      }));
      this.f_88105_ = this.m_142416_(new Button(this.f_96543_ - 135, m_120774_(3), 120, 20, new TranslatableComponent("mco.backup.button.restore"), (p_88179_) -> {
         this.m_88166_(this.f_88122_);
      }));
      this.f_88106_ = this.m_142416_(new Button(this.f_96543_ - 135, m_120774_(5), 120, 20, new TranslatableComponent("mco.backup.changes.tooltip"), (p_88174_) -> {
         this.f_96541_.m_91152_(new RealmsBackupInfoScreen(this, this.f_88119_.get(this.f_88122_)));
         this.f_88122_ = -1;
      }));
      this.m_142416_(new Button(this.f_96543_ - 100, this.f_96544_ - 35, 85, 20, CommonComponents.f_130660_, (p_88164_) -> {
         this.f_96541_.m_91152_(this.f_88118_);
      }));
      this.m_7787_(this.f_88121_);
      this.m_94725_(this.f_88121_);
      this.m_88204_();
   }

   void m_88199_() {
      if (this.f_88119_.size() > 1) {
         for(int i = 0; i < this.f_88119_.size() - 1; ++i) {
            Backup backup = this.f_88119_.get(i);
            Backup backup1 = this.f_88119_.get(i + 1);
            if (!backup.f_87392_.isEmpty() && !backup1.f_87392_.isEmpty()) {
               for(String s : backup.f_87392_.keySet()) {
                  if (!s.contains("Uploaded") && backup1.f_87392_.containsKey(s)) {
                     if (!backup.f_87392_.get(s).equals(backup1.f_87392_.get(s))) {
                        this.m_88146_(backup, s);
                     }
                  } else {
                     this.m_88146_(backup, s);
                  }
               }
            }
         }

      }
   }

   private void m_88146_(Backup p_88147_, String p_88148_) {
      if (p_88148_.contains("Uploaded")) {
         String s = DateFormat.getDateTimeInstance(3, 3).format(p_88147_.f_87390_);
         p_88147_.f_87393_.put(p_88148_, s);
         p_88147_.m_87403_(true);
      } else {
         p_88147_.f_87393_.put(p_88148_, p_88147_.f_87392_.get(p_88148_));
      }

   }

   void m_88204_() {
      this.f_88105_.f_93624_ = this.m_88206_();
      this.f_88106_.f_93624_ = this.m_88205_();
   }

   private boolean m_88205_() {
      if (this.f_88122_ == -1) {
         return false;
      } else {
         return !(this.f_88119_.get(this.f_88122_)).f_87393_.isEmpty();
      }
   }

   private boolean m_88206_() {
      if (this.f_88122_ == -1) {
         return false;
      } else {
         return !this.f_88108_.f_87482_;
      }
   }

   public boolean m_7933_(int p_88133_, int p_88134_, int p_88135_) {
      if (p_88133_ == 256) {
         this.f_96541_.m_91152_(this.f_88118_);
         return true;
      } else {
         return super.m_7933_(p_88133_, p_88134_, p_88135_);
      }
   }

   void m_88166_(int p_88167_) {
      if (p_88167_ >= 0 && p_88167_ < this.f_88119_.size() && !this.f_88108_.f_87482_) {
         this.f_88122_ = p_88167_;
         Date date = (this.f_88119_.get(p_88167_)).f_87390_;
         String s = DateFormat.getDateTimeInstance(3, 3).format(date);
         String s1 = RealmsUtil.m_90223_(date);
         Component component = new TranslatableComponent("mco.configure.world.restore.question.line1", s, s1);
         Component component1 = new TranslatableComponent("mco.configure.world.restore.question.line2");
         this.f_96541_.m_91152_(new RealmsLongConfirmationScreen((p_88187_) -> {
            if (p_88187_) {
               this.m_88209_();
            } else {
               this.f_88122_ = -1;
               this.f_96541_.m_91152_(this);
            }

         }, RealmsLongConfirmationScreen.Type.Warning, component, component1, true));
      }

   }

   private void m_88207_() {
      Component component = new TranslatableComponent("mco.configure.world.restore.download.question.line1");
      Component component1 = new TranslatableComponent("mco.configure.world.restore.download.question.line2");
      this.f_96541_.m_91152_(new RealmsLongConfirmationScreen((p_88181_) -> {
         if (p_88181_) {
            this.m_88208_();
         } else {
            this.f_96541_.m_91152_(this);
         }

      }, RealmsLongConfirmationScreen.Type.Info, component, component1, true));
   }

   private void m_88208_() {
      this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88118_.m_88486_(), new DownloadTask(this.f_88108_.f_87473_, this.f_88123_, this.f_88108_.f_87475_ + " (" + this.f_88108_.f_87481_.get(this.f_88108_.f_87486_).m_87626_(this.f_88108_.f_87486_) + ")", this)));
   }

   private void m_88209_() {
      Backup backup = this.f_88119_.get(this.f_88122_);
      this.f_88122_ = -1;
      this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88118_.m_88486_(), new RestoreTask(backup, this.f_88108_.f_87473_, this.f_88118_)));
   }

   public void m_6305_(PoseStack p_88137_, int p_88138_, int p_88139_, float p_88140_) {
      this.f_88120_ = null;
      this.m_7333_(p_88137_);
      this.f_88121_.m_6305_(p_88137_, p_88138_, p_88139_, p_88140_);
      m_93215_(p_88137_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 12, 16777215);
      this.f_96547_.m_92889_(p_88137_, f_88115_, (float)((this.f_96543_ - 150) / 2 - 90), 20.0F, 10526880);
      if (this.f_88107_) {
         this.f_96547_.m_92889_(p_88137_, f_88116_, 20.0F, (float)(this.f_96544_ / 2 - 10), 16777215);
      }

      this.f_88104_.f_93623_ = !this.f_88107_;
      super.m_6305_(p_88137_, p_88138_, p_88139_, p_88140_);
      if (this.f_88120_ != null) {
         this.m_88141_(p_88137_, this.f_88120_, p_88138_, p_88139_);
      }

   }

   protected void m_88141_(PoseStack p_88142_, @Nullable Component p_88143_, int p_88144_, int p_88145_) {
      if (p_88143_ != null) {
         int i = p_88144_ + 12;
         int j = p_88145_ - 12;
         int k = this.f_96547_.m_92852_(p_88143_);
         this.m_93179_(p_88142_, i - 3, j - 3, i + k + 3, j + 8 + 3, -1073741824, -1073741824);
         this.f_96547_.m_92763_(p_88142_, p_88143_, (float)i, (float)j, 16777215);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class BackupObjectSelectionList extends RealmsObjectSelectionList<RealmsBackupScreen.Entry> {
      public BackupObjectSelectionList() {
         super(RealmsBackupScreen.this.f_96543_ - 150, RealmsBackupScreen.this.f_96544_, 32, RealmsBackupScreen.this.f_96544_ - 15, 36);
      }

      public void m_88234_(Backup p_88235_) {
         this.m_7085_(RealmsBackupScreen.this.new Entry(p_88235_));
      }

      public int m_5759_() {
         return (int)((double)this.f_93388_ * 0.93D);
      }

      public boolean m_5694_() {
         return RealmsBackupScreen.this.m_7222_() == this;
      }

      public int m_5775_() {
         return this.m_5773_() * 36;
      }

      public void m_7733_(PoseStack p_88233_) {
         RealmsBackupScreen.this.m_7333_(p_88233_);
      }

      public boolean m_6375_(double p_88221_, double p_88222_, int p_88223_) {
         if (p_88223_ != 0) {
            return false;
         } else if (p_88221_ < (double)this.m_5756_() && p_88222_ >= (double)this.f_93390_ && p_88222_ <= (double)this.f_93391_) {
            int i = this.f_93388_ / 2 - 92;
            int j = this.f_93388_;
            int k = (int)Math.floor(p_88222_ - (double)this.f_93390_) - this.f_93395_ + (int)this.m_93517_();
            int l = k / this.f_93387_;
            if (p_88221_ >= (double)i && p_88221_ <= (double)j && l >= 0 && k >= 0 && l < this.m_5773_()) {
               this.m_7109_(l);
               this.m_7980_(k, l, p_88221_, p_88222_, this.f_93388_);
            }

            return true;
         } else {
            return false;
         }
      }

      public int m_5756_() {
         return this.f_93388_ - 5;
      }

      public void m_7980_(int p_88227_, int p_88228_, double p_88229_, double p_88230_, int p_88231_) {
         int i = this.f_93388_ - 35;
         int j = p_88228_ * this.f_93387_ + 36 - (int)this.m_93517_();
         int k = i + 10;
         int l = j - 3;
         if (p_88229_ >= (double)i && p_88229_ <= (double)(i + 9) && p_88230_ >= (double)j && p_88230_ <= (double)(j + 9)) {
            if (!(RealmsBackupScreen.this.f_88119_.get(p_88228_)).f_87393_.isEmpty()) {
               RealmsBackupScreen.this.f_88122_ = -1;
               RealmsBackupScreen.f_88117_ = (int)this.m_93517_();
               this.f_93386_.m_91152_(new RealmsBackupInfoScreen(RealmsBackupScreen.this, RealmsBackupScreen.this.f_88119_.get(p_88228_)));
            }
         } else if (p_88229_ >= (double)k && p_88229_ < (double)(k + 13) && p_88230_ >= (double)l && p_88230_ < (double)(l + 15)) {
            RealmsBackupScreen.f_88117_ = (int)this.m_93517_();
            RealmsBackupScreen.this.m_88166_(p_88228_);
         }

      }

      public void m_7109_(int p_88225_) {
         super.m_7109_(p_88225_);
         this.m_88241_(p_88225_);
      }

      public void m_88241_(int p_88242_) {
         RealmsBackupScreen.this.f_88122_ = p_88242_;
         RealmsBackupScreen.this.m_88204_();
      }

      public void m_6987_(@Nullable RealmsBackupScreen.Entry p_88237_) {
         super.m_6987_(p_88237_);
         RealmsBackupScreen.this.f_88122_ = this.m_6702_().indexOf(p_88237_);
         RealmsBackupScreen.this.m_88204_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   class Entry extends ObjectSelectionList.Entry<RealmsBackupScreen.Entry> {
      private final Backup f_88247_;

      public Entry(Backup p_88250_) {
         this.f_88247_ = p_88250_;
      }

      public void m_6311_(PoseStack p_88258_, int p_88259_, int p_88260_, int p_88261_, int p_88262_, int p_88263_, int p_88264_, int p_88265_, boolean p_88266_, float p_88267_) {
         this.m_88268_(p_88258_, this.f_88247_, p_88261_ - 40, p_88260_, p_88264_, p_88265_);
      }

      private void m_88268_(PoseStack p_88269_, Backup p_88270_, int p_88271_, int p_88272_, int p_88273_, int p_88274_) {
         int i = p_88270_.m_87398_() ? -8388737 : 16777215;
         RealmsBackupScreen.this.f_96547_.m_92883_(p_88269_, "Backup (" + RealmsUtil.m_90223_(p_88270_.f_87390_) + ")", (float)(p_88271_ + 40), (float)(p_88272_ + 1), i);
         RealmsBackupScreen.this.f_96547_.m_92883_(p_88269_, this.m_88275_(p_88270_.f_87390_), (float)(p_88271_ + 40), (float)(p_88272_ + 12), 5000268);
         int j = RealmsBackupScreen.this.f_96543_ - 175;
         int k = -3;
         int l = j - 10;
         int i1 = 0;
         if (!RealmsBackupScreen.this.f_88108_.f_87482_) {
            this.m_88251_(p_88269_, j, p_88272_ + -3, p_88273_, p_88274_);
         }

         if (!p_88270_.f_87393_.isEmpty()) {
            this.m_88277_(p_88269_, l, p_88272_ + 0, p_88273_, p_88274_);
         }

      }

      private String m_88275_(Date p_88276_) {
         return DateFormat.getDateTimeInstance(3, 3).format(p_88276_);
      }

      private void m_88251_(PoseStack p_88252_, int p_88253_, int p_88254_, int p_88255_, int p_88256_) {
         boolean flag = p_88255_ >= p_88253_ && p_88255_ <= p_88253_ + 12 && p_88256_ >= p_88254_ && p_88256_ <= p_88254_ + 14 && p_88256_ < RealmsBackupScreen.this.f_96544_ - 15 && p_88256_ > 32;
         RenderSystem.m_157456_(0, RealmsBackupScreen.f_88112_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         p_88252_.m_85836_();
         p_88252_.m_85841_(0.5F, 0.5F, 0.5F);
         float f = flag ? 28.0F : 0.0F;
         GuiComponent.m_93133_(p_88252_, p_88253_ * 2, p_88254_ * 2, 0.0F, f, 23, 28, 23, 56);
         p_88252_.m_85849_();
         if (flag) {
            RealmsBackupScreen.this.f_88120_ = RealmsBackupScreen.f_88113_;
         }

      }

      private void m_88277_(PoseStack p_88278_, int p_88279_, int p_88280_, int p_88281_, int p_88282_) {
         boolean flag = p_88281_ >= p_88279_ && p_88281_ <= p_88279_ + 8 && p_88282_ >= p_88280_ && p_88282_ <= p_88280_ + 8 && p_88282_ < RealmsBackupScreen.this.f_96544_ - 15 && p_88282_ > 32;
         RenderSystem.m_157456_(0, RealmsBackupScreen.f_88111_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         p_88278_.m_85836_();
         p_88278_.m_85841_(0.5F, 0.5F, 0.5F);
         float f = flag ? 15.0F : 0.0F;
         GuiComponent.m_93133_(p_88278_, p_88279_ * 2, p_88280_ * 2, 0.0F, f, 15, 15, 15, 30);
         p_88278_.m_85849_();
         if (flag) {
            RealmsBackupScreen.this.f_88120_ = RealmsBackupScreen.f_88114_;
         }

      }

      public Component m_142172_() {
         return new TranslatableComponent("narrator.select", this.f_88247_.f_87390_.toString());
      }
   }
}