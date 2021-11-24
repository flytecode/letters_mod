package net.minecraft.client;

import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.InputConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFWDropCallback;

@OnlyIn(Dist.CLIENT)
public class MouseHandler {
   private final Minecraft f_91503_;
   private boolean f_91504_;
   private boolean f_91505_;
   private boolean f_91506_;
   private double f_91507_;
   private double f_91508_;
   private int f_91509_;
   private int f_91510_ = -1;
   private boolean f_91511_ = true;
   private int f_91512_;
   private double f_91513_;
   private final SmoothDouble f_91514_ = new SmoothDouble();
   private final SmoothDouble f_91515_ = new SmoothDouble();
   private double f_91516_;
   private double f_91517_;
   private double f_91518_;
   private double f_91519_ = Double.MIN_VALUE;
   private boolean f_91520_;

   public MouseHandler(Minecraft p_91522_) {
      this.f_91503_ = p_91522_;
   }

   private void m_91530_(long p_91531_, int p_91532_, int p_91533_, int p_91534_) {
      if (p_91531_ == this.f_91503_.m_91268_().m_85439_()) {
         boolean flag = p_91533_ == 1;
         if (Minecraft.f_91002_ && p_91532_ == 0) {
            if (flag) {
               if ((p_91534_ & 2) == 2) {
                  p_91532_ = 1;
                  ++this.f_91509_;
               }
            } else if (this.f_91509_ > 0) {
               p_91532_ = 1;
               --this.f_91509_;
            }
         }

         int i = p_91532_;
         if (flag) {
            if (this.f_91503_.f_91066_.f_92051_ && this.f_91512_++ > 0) {
               return;
            }

            this.f_91510_ = i;
            this.f_91513_ = Blaze3D.m_83640_();
         } else if (this.f_91510_ != -1) {
            if (this.f_91503_.f_91066_.f_92051_ && --this.f_91512_ > 0) {
               return;
            }

            this.f_91510_ = -1;
         }

         if (net.minecraftforge.client.ForgeHooksClient.onRawMouseClicked(p_91532_, p_91533_, p_91534_)) return;
         boolean[] aboolean = new boolean[]{false};
         if (this.f_91503_.m_91265_() == null) {
            if (this.f_91503_.f_91080_ == null) {
               if (!this.f_91520_ && flag) {
                  this.m_91601_();
               }
            } else {
               double d0 = this.f_91507_ * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
               double d1 = this.f_91508_ * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
               Screen screen = this.f_91503_.f_91080_;
               if (flag) {
                  screen.m_169415_();
                  Screen.m_96579_(() -> {
                     aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiMouseClickedPre(this.f_91503_.f_91080_, d0, d1, i);
                     if (!aboolean[0]) aboolean[0] = this.f_91503_.f_91080_.m_6375_(d0, d1, i);
                     if (!aboolean[0]) aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiMouseClickedPost(this.f_91503_.f_91080_, d0, d1, i);
                  }, "mouseClicked event handler", screen.getClass().getCanonicalName());
               } else {
                  Screen.m_96579_(() -> {
                     aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiMouseReleasedPre(this.f_91503_.f_91080_, d0, d1, i);
                     if (!aboolean[0]) aboolean[0] = this.f_91503_.f_91080_.m_6348_(d0, d1, i);
                     if (!aboolean[0]) aboolean[0] = net.minecraftforge.client.ForgeHooksClient.onGuiMouseReleasedPost(this.f_91503_.f_91080_, d0, d1, i);
                  }, "mouseReleased event handler", screen.getClass().getCanonicalName());
               }
            }
         }

         if (!aboolean[0] && (this.f_91503_.f_91080_ == null || this.f_91503_.f_91080_.f_96546_) && this.f_91503_.m_91265_() == null) {
            if (i == 0) {
               this.f_91504_ = flag;
            } else if (i == 2) {
               this.f_91505_ = flag;
            } else if (i == 1) {
               this.f_91506_ = flag;
            }

            KeyMapping.m_90837_(InputConstants.Type.MOUSE.m_84895_(i), flag);
            if (flag) {
               if (this.f_91503_.f_91074_.m_5833_() && i == 2) {
                  this.f_91503_.f_91065_.m_93085_().m_94793_();
               } else {
                  KeyMapping.m_90835_(InputConstants.Type.MOUSE.m_84895_(i));
               }
            }
         }
         net.minecraftforge.client.ForgeHooksClient.fireMouseInput(p_91532_, p_91533_, p_91534_);
      }
   }

   private void m_91526_(long p_91527_, double p_91528_, double p_91529_) {
      if (p_91527_ == Minecraft.m_91087_().m_91268_().m_85439_()) {
         // FORGE: Allows for Horizontal Scroll to be recognized as Vertical Scroll - Fixes MC-121772
         double offset = p_91529_;
         if (Minecraft.f_91002_ && p_91529_ == 0) {
            offset = p_91528_;
         }
         double d0 = (this.f_91503_.f_91066_.f_92045_ ? Math.signum(offset) : offset) * this.f_91503_.f_91066_.f_92033_;
         if (this.f_91503_.m_91265_() == null) {
            if (this.f_91503_.f_91080_ != null) {
               double d1 = this.f_91507_ * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
               double d2 = this.f_91508_ * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
               this.f_91503_.f_91080_.m_169415_();
               if (net.minecraftforge.client.ForgeHooksClient.onGuiMouseScrollPre(this, this.f_91503_.f_91080_, d0)) return;
               if (this.f_91503_.f_91080_.m_6050_(d1, d2, d0)) return;
               net.minecraftforge.client.ForgeHooksClient.onGuiMouseScrollPost(this, this.f_91503_.f_91080_, d0);
            } else if (this.f_91503_.f_91074_ != null) {
               if (this.f_91518_ != 0.0D && Math.signum(d0) != Math.signum(this.f_91518_)) {
                  this.f_91518_ = 0.0D;
               }

               this.f_91518_ += d0;
               float f1 = (float)((int)this.f_91518_);
               if (f1 == 0.0F) {
                  return;
               }

               this.f_91518_ -= (double)f1;
               if (net.minecraftforge.client.ForgeHooksClient.onMouseScroll(this, d0)) return;
               if (this.f_91503_.f_91074_.m_5833_()) {
                  if (this.f_91503_.f_91065_.m_93085_().m_94768_()) {
                     this.f_91503_.f_91065_.m_93085_().m_94769_((double)(-f1));
                  } else {
                     float f = Mth.m_14036_(this.f_91503_.f_91074_.m_150110_().m_35942_() + f1 * 0.005F, 0.0F, 0.2F);
                     this.f_91503_.f_91074_.m_150110_().m_35943_(f);
                  }
               } else {
                  this.f_91503_.f_91074_.m_150109_().m_35988_((double)f1);
               }
            }
         }
      }

   }

   private void m_91539_(long p_91540_, List<Path> p_91541_) {
      if (this.f_91503_.f_91080_ != null) {
         this.f_91503_.f_91080_.m_7400_(p_91541_);
      }

   }

   public void m_91524_(long p_91525_) {
      InputConstants.m_84838_(p_91525_, (p_91591_, p_91592_, p_91593_) -> {
         this.f_91503_.execute(() -> {
            this.m_91561_(p_91591_, p_91592_, p_91593_);
         });
      }, (p_91566_, p_91567_, p_91568_, p_91569_) -> {
         this.f_91503_.execute(() -> {
            this.m_91530_(p_91566_, p_91567_, p_91568_, p_91569_);
         });
      }, (p_91576_, p_91577_, p_91578_) -> {
         this.f_91503_.execute(() -> {
            this.m_91526_(p_91576_, p_91577_, p_91578_);
         });
      }, (p_91536_, p_91537_, p_91538_) -> {
         Path[] apath = new Path[p_91537_];

         for(int i = 0; i < p_91537_; ++i) {
            apath[i] = Paths.get(GLFWDropCallback.getName(p_91538_, i));
         }

         this.f_91503_.execute(() -> {
            this.m_91539_(p_91536_, Arrays.asList(apath));
         });
      });
   }

   private void m_91561_(long p_91562_, double p_91563_, double p_91564_) {
      if (p_91562_ == Minecraft.m_91087_().m_91268_().m_85439_()) {
         if (this.f_91511_) {
            this.f_91507_ = p_91563_;
            this.f_91508_ = p_91564_;
            this.f_91511_ = false;
         }

         Screen screen = this.f_91503_.f_91080_;
         if (screen != null && this.f_91503_.m_91265_() == null) {
            double d0 = p_91563_ * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
            double d1 = p_91564_ * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
            Screen.m_96579_(() -> {
               screen.m_94757_(d0, d1);
            }, "mouseMoved event handler", screen.getClass().getCanonicalName());
            if (this.f_91510_ != -1 && this.f_91513_ > 0.0D) {
               double d2 = (p_91563_ - this.f_91507_) * (double)this.f_91503_.m_91268_().m_85445_() / (double)this.f_91503_.m_91268_().m_85443_();
               double d3 = (p_91564_ - this.f_91508_) * (double)this.f_91503_.m_91268_().m_85446_() / (double)this.f_91503_.m_91268_().m_85444_();
               Screen.m_96579_(() -> {
                  if (net.minecraftforge.client.ForgeHooksClient.onGuiMouseDragPre(this.f_91503_.f_91080_, d0, d1, this.f_91510_, d2, d3)) return;
                  if (screen.m_7979_(d0, d1, this.f_91510_, d2, d3)) return;
                  net.minecraftforge.client.ForgeHooksClient.onGuiMouseDragPost(this.f_91503_.f_91080_, d0, d1, this.f_91510_, d2, d3);
               }, "mouseDragged event handler", screen.getClass().getCanonicalName());
            }

            screen.m_169414_();
         }

         this.f_91503_.m_91307_().m_6180_("mouse");
         if (this.m_91600_() && this.f_91503_.m_91302_()) {
            this.f_91516_ += p_91563_ - this.f_91507_;
            this.f_91517_ += p_91564_ - this.f_91508_;
         }

         this.m_91523_();
         this.f_91507_ = p_91563_;
         this.f_91508_ = p_91564_;
         this.f_91503_.m_91307_().m_7238_();
      }
   }

   public void m_91523_() {
      double d0 = Blaze3D.m_83640_();
      double d1 = d0 - this.f_91519_;
      this.f_91519_ = d0;
      if (this.m_91600_() && this.f_91503_.m_91302_()) {
         double d4 = this.f_91503_.f_91066_.f_92053_ * (double)0.6F + (double)0.2F;
         double d5 = d4 * d4 * d4;
         double d6 = d5 * 8.0D;
         double d2;
         double d3;
         if (this.f_91503_.f_91066_.f_92067_) {
            double d7 = this.f_91514_.m_14237_(this.f_91516_ * d6, d1 * d6);
            double d8 = this.f_91515_.m_14237_(this.f_91517_ * d6, d1 * d6);
            d2 = d7;
            d3 = d8;
         } else if (this.f_91503_.f_91066_.m_92176_().m_90612_() && this.f_91503_.f_91074_.m_150108_()) {
            this.f_91514_.m_14236_();
            this.f_91515_.m_14236_();
            d2 = this.f_91516_ * d5;
            d3 = this.f_91517_ * d5;
         } else {
            this.f_91514_.m_14236_();
            this.f_91515_.m_14236_();
            d2 = this.f_91516_ * d6;
            d3 = this.f_91517_ * d6;
         }

         this.f_91516_ = 0.0D;
         this.f_91517_ = 0.0D;
         int i = 1;
         if (this.f_91503_.f_91066_.f_92044_) {
            i = -1;
         }

         this.f_91503_.m_91301_().m_120565_(d2, d3);
         if (this.f_91503_.f_91074_ != null) {
            this.f_91503_.f_91074_.m_19884_(d2, d3 * (double)i);
         }

      } else {
         this.f_91516_ = 0.0D;
         this.f_91517_ = 0.0D;
      }
   }

   public boolean m_91560_() {
      return this.f_91504_;
   }

   public boolean m_168090_() {
      return this.f_91505_;
   }

   public boolean m_91584_() {
      return this.f_91506_;
   }

   public double m_91589_() {
      return this.f_91507_;
   }

   public double m_91594_() {
      return this.f_91508_;
   }

   public double getXVelocity() {
      return this.f_91516_;
   }

   public double getYVelocity() {
      return this.f_91517_;
   }

   public void m_91599_() {
      this.f_91511_ = true;
   }

   public boolean m_91600_() {
      return this.f_91520_;
   }

   public void m_91601_() {
      if (this.f_91503_.m_91302_()) {
         if (!this.f_91520_) {
            if (!Minecraft.f_91002_) {
               KeyMapping.m_90829_();
            }

            this.f_91520_ = true;
            this.f_91507_ = (double)(this.f_91503_.m_91268_().m_85443_() / 2);
            this.f_91508_ = (double)(this.f_91503_.m_91268_().m_85444_() / 2);
            InputConstants.m_84833_(this.f_91503_.m_91268_().m_85439_(), 212995, this.f_91507_, this.f_91508_);
            this.f_91503_.m_91152_((Screen)null);
            this.f_91503_.f_91078_ = 10000;
            this.f_91511_ = true;
         }
      }
   }

   public void m_91602_() {
      if (this.f_91520_) {
         this.f_91520_ = false;
         this.f_91507_ = (double)(this.f_91503_.m_91268_().m_85443_() / 2);
         this.f_91508_ = (double)(this.f_91503_.m_91268_().m_85444_() / 2);
         InputConstants.m_84833_(this.f_91503_.m_91268_().m_85439_(), 212993, this.f_91507_, this.f_91508_);
      }
   }

   public void m_91603_() {
      this.f_91511_ = true;
   }
}
