package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SubtitleOverlay extends GuiComponent implements SoundEventListener {
   private static final long f_169070_ = 3000L;
   private final Minecraft f_94637_;
   private final List<SubtitleOverlay.Subtitle> f_94638_ = Lists.newArrayList();
   private boolean f_94639_;

   public SubtitleOverlay(Minecraft p_94641_) {
      this.f_94637_ = p_94641_;
   }

   public void m_94642_(PoseStack p_94643_) {
      if (!this.f_94639_ && this.f_94637_.f_91066_.f_92049_) {
         this.f_94637_.m_91106_().m_120374_(this);
         this.f_94639_ = true;
      } else if (this.f_94639_ && !this.f_94637_.f_91066_.f_92049_) {
         this.f_94637_.m_91106_().m_120401_(this);
         this.f_94639_ = false;
      }

      if (this.f_94639_ && !this.f_94638_.isEmpty()) {
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         Vec3 vec3 = new Vec3(this.f_94637_.f_91074_.m_20185_(), this.f_94637_.f_91074_.m_20188_(), this.f_94637_.f_91074_.m_20189_());
         Vec3 vec31 = (new Vec3(0.0D, 0.0D, -1.0D)).m_82496_(-this.f_94637_.f_91074_.m_146909_() * ((float)Math.PI / 180F)).m_82524_(-this.f_94637_.f_91074_.m_146908_() * ((float)Math.PI / 180F));
         Vec3 vec32 = (new Vec3(0.0D, 1.0D, 0.0D)).m_82496_(-this.f_94637_.f_91074_.m_146909_() * ((float)Math.PI / 180F)).m_82524_(-this.f_94637_.f_91074_.m_146908_() * ((float)Math.PI / 180F));
         Vec3 vec33 = vec31.m_82537_(vec32);
         int i = 0;
         int j = 0;
         Iterator<SubtitleOverlay.Subtitle> iterator = this.f_94638_.iterator();

         while(iterator.hasNext()) {
            SubtitleOverlay.Subtitle subtitleoverlay$subtitle = iterator.next();
            if (subtitleoverlay$subtitle.m_94658_() + 3000L <= Util.m_137550_()) {
               iterator.remove();
            } else {
               j = Math.max(j, this.f_94637_.f_91062_.m_92852_(subtitleoverlay$subtitle.m_94655_()));
            }
         }

         j = j + this.f_94637_.f_91062_.m_92895_("<") + this.f_94637_.f_91062_.m_92895_(" ") + this.f_94637_.f_91062_.m_92895_(">") + this.f_94637_.f_91062_.m_92895_(" ");

         for(SubtitleOverlay.Subtitle subtitleoverlay$subtitle1 : this.f_94638_) {
            int k = 255;
            Component component = subtitleoverlay$subtitle1.m_94655_();
            Vec3 vec34 = subtitleoverlay$subtitle1.m_94659_().m_82546_(vec3).m_82541_();
            double d0 = -vec33.m_82526_(vec34);
            double d1 = -vec31.m_82526_(vec34);
            boolean flag = d1 > 0.5D;
            int l = j / 2;
            int i1 = 9;
            int j1 = i1 / 2;
            float f = 1.0F;
            int k1 = this.f_94637_.f_91062_.m_92852_(component);
            int l1 = Mth.m_14143_(Mth.m_144920_(255.0F, 75.0F, (float)(Util.m_137550_() - subtitleoverlay$subtitle1.m_94658_()) / 3000.0F));
            int i2 = l1 << 16 | l1 << 8 | l1;
            p_94643_.m_85836_();
            p_94643_.m_85837_((double)((float)this.f_94637_.m_91268_().m_85445_() - (float)l * 1.0F - 2.0F), (double)((float)(this.f_94637_.m_91268_().m_85446_() - 30) - (float)(i * (i1 + 1)) * 1.0F), 0.0D);
            p_94643_.m_85841_(1.0F, 1.0F, 1.0F);
            m_93172_(p_94643_, -l - 1, -j1 - 1, l + 1, j1 + 1, this.f_94637_.f_91066_.m_92170_(0.8F));
            RenderSystem.m_69478_();
            if (!flag) {
               if (d0 > 0.0D) {
                  this.f_94637_.f_91062_.m_92883_(p_94643_, ">", (float)(l - this.f_94637_.f_91062_.m_92895_(">")), (float)(-j1), i2 + -16777216);
               } else if (d0 < 0.0D) {
                  this.f_94637_.f_91062_.m_92883_(p_94643_, "<", (float)(-l), (float)(-j1), i2 + -16777216);
               }
            }

            this.f_94637_.f_91062_.m_92889_(p_94643_, component, (float)(-k1 / 2), (float)(-j1), i2 + -16777216);
            p_94643_.m_85849_();
            ++i;
         }

         RenderSystem.m_69461_();
      }
   }

   public void m_6985_(SoundInstance p_94645_, WeighedSoundEvents p_94646_) {
      if (p_94646_.m_120453_() != null) {
         Component component = p_94646_.m_120453_();
         if (!this.f_94638_.isEmpty()) {
            for(SubtitleOverlay.Subtitle subtitleoverlay$subtitle : this.f_94638_) {
               if (subtitleoverlay$subtitle.m_94655_().equals(component)) {
                  subtitleoverlay$subtitle.m_94656_(new Vec3(p_94645_.m_7772_(), p_94645_.m_7780_(), p_94645_.m_7778_()));
                  return;
               }
            }
         }

         this.f_94638_.add(new SubtitleOverlay.Subtitle(component, new Vec3(p_94645_.m_7772_(), p_94645_.m_7780_(), p_94645_.m_7778_())));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Subtitle {
      private final Component f_94648_;
      private long f_94649_;
      private Vec3 f_94650_;

      public Subtitle(Component p_169072_, Vec3 p_169073_) {
         this.f_94648_ = p_169072_;
         this.f_94650_ = p_169073_;
         this.f_94649_ = Util.m_137550_();
      }

      public Component m_94655_() {
         return this.f_94648_;
      }

      public long m_94658_() {
         return this.f_94649_;
      }

      public Vec3 m_94659_() {
         return this.f_94650_;
      }

      public void m_94656_(Vec3 p_94657_) {
         this.f_94650_ = p_94657_;
         this.f_94649_ = Util.m_137550_();
      }
   }
}