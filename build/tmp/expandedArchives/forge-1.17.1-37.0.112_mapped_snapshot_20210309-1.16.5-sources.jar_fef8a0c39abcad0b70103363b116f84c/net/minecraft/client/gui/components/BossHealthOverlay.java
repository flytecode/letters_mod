package net.minecraft.client.gui.components;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BossHealthOverlay extends GuiComponent {
   private static final ResourceLocation f_93697_ = new ResourceLocation("textures/gui/bars.png");
   private static final int f_168805_ = 182;
   private static final int f_168806_ = 5;
   private static final int f_168807_ = 80;
   private final Minecraft f_93698_;
   final Map<UUID, LerpingBossEvent> f_93699_ = Maps.newLinkedHashMap();

   public BossHealthOverlay(Minecraft p_93702_) {
      this.f_93698_ = p_93702_;
   }

   public void m_93704_(PoseStack p_93705_) {
      if (!this.f_93699_.isEmpty()) {
         int i = this.f_93698_.m_91268_().m_85445_();
         int j = 12;

         for(LerpingBossEvent lerpingbossevent : this.f_93699_.values()) {
            int k = i / 2 - 91;
            net.minecraftforge.client.event.RenderGameOverlayEvent.BossInfo event =
               net.minecraftforge.client.ForgeHooksClient.bossBarRenderPre(p_93705_, this.f_93698_.m_91268_(), lerpingbossevent, k, j, 10 + this.f_93698_.f_91062_.f_92710_);
            if (!event.isCanceled()) {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.m_157456_(0, f_93697_);
            this.m_93706_(p_93705_, k, j, lerpingbossevent);
            Component component = lerpingbossevent.m_18861_();
            int l = this.f_93698_.f_91062_.m_92852_(component);
            int i1 = i / 2 - l / 2;
            int j1 = j - 9;
            this.f_93698_.f_91062_.m_92763_(p_93705_, component, (float)i1, (float)j1, 16777215);
            }
            j += event.getIncrement();
            net.minecraftforge.client.ForgeHooksClient.bossBarRenderPost(p_93705_, this.f_93698_.m_91268_());
            if (j >= this.f_93698_.m_91268_().m_85446_() / 3) {
               break;
            }
         }

      }
   }

   private void m_93706_(PoseStack p_93707_, int p_93708_, int p_93709_, BossEvent p_93710_) {
      this.m_93228_(p_93707_, p_93708_, p_93709_, 0, p_93710_.m_18862_().ordinal() * 5 * 2, 182, 5);
      if (p_93710_.m_18863_() != BossEvent.BossBarOverlay.PROGRESS) {
         this.m_93228_(p_93707_, p_93708_, p_93709_, 0, 80 + (p_93710_.m_18863_().ordinal() - 1) * 5 * 2, 182, 5);
      }

      int i = (int)(p_93710_.m_142717_() * 183.0F);
      if (i > 0) {
         this.m_93228_(p_93707_, p_93708_, p_93709_, 0, p_93710_.m_18862_().ordinal() * 5 * 2 + 5, i, 5);
         if (p_93710_.m_18863_() != BossEvent.BossBarOverlay.PROGRESS) {
            this.m_93228_(p_93707_, p_93708_, p_93709_, 0, 80 + (p_93710_.m_18863_().ordinal() - 1) * 5 * 2 + 5, i, 5);
         }
      }

   }

   public void m_93711_(ClientboundBossEventPacket p_93712_) {
      p_93712_.m_178643_(new ClientboundBossEventPacket.Handler() {
         public void m_142107_(UUID p_168824_, Component p_168825_, float p_168826_, BossEvent.BossBarColor p_168827_, BossEvent.BossBarOverlay p_168828_, boolean p_168829_, boolean p_168830_, boolean p_168831_) {
            BossHealthOverlay.this.f_93699_.put(p_168824_, new LerpingBossEvent(p_168824_, p_168825_, p_168826_, p_168827_, p_168828_, p_168829_, p_168830_, p_168831_));
         }

         public void m_142751_(UUID p_168812_) {
            BossHealthOverlay.this.f_93699_.remove(p_168812_);
         }

         public void m_142653_(UUID p_168814_, float p_168815_) {
            BossHealthOverlay.this.f_93699_.get(p_168814_).m_142711_(p_168815_);
         }

         public void m_142366_(UUID p_168821_, Component p_168822_) {
            BossHealthOverlay.this.f_93699_.get(p_168821_).m_6456_(p_168822_);
         }

         public void m_142358_(UUID p_168817_, BossEvent.BossBarColor p_168818_, BossEvent.BossBarOverlay p_168819_) {
            LerpingBossEvent lerpingbossevent = BossHealthOverlay.this.f_93699_.get(p_168817_);
            lerpingbossevent.m_6451_(p_168818_);
            lerpingbossevent.m_5648_(p_168819_);
         }

         public void m_142513_(UUID p_168833_, boolean p_168834_, boolean p_168835_, boolean p_168836_) {
            LerpingBossEvent lerpingbossevent = BossHealthOverlay.this.f_93699_.get(p_168833_);
            lerpingbossevent.m_7003_(p_168834_);
            lerpingbossevent.m_7005_(p_168835_);
            lerpingbossevent.m_7006_(p_168836_);
         }
      });
   }

   public void m_93703_() {
      this.f_93699_.clear();
   }

   public boolean m_93713_() {
      if (!this.f_93699_.isEmpty()) {
         for(BossEvent bossevent : this.f_93699_.values()) {
            if (bossevent.m_18865_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_93714_() {
      if (!this.f_93699_.isEmpty()) {
         for(BossEvent bossevent : this.f_93699_.values()) {
            if (bossevent.m_18864_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_93715_() {
      if (!this.f_93699_.isEmpty()) {
         for(BossEvent bossevent : this.f_93699_.values()) {
            if (bossevent.m_18866_()) {
               return true;
            }
         }
      }

      return false;
   }
}
