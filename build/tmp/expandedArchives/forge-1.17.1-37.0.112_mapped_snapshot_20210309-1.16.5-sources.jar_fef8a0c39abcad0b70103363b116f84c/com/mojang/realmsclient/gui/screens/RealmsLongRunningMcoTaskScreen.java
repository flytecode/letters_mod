package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
import com.mojang.realmsclient.gui.ErrorCallback;
import com.mojang.realmsclient.util.task.LongRunningTask;
import java.time.Duration;
import javax.annotation.Nullable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.RepeatedNarrator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsLongRunningMcoTaskScreen extends RealmsScreen implements ErrorCallback {
   private static final RepeatedNarrator f_167414_ = new RepeatedNarrator(Duration.ofSeconds(5L));
   private static final Logger f_88767_ = LogManager.getLogger();
   private final Screen f_88768_;
   private volatile Component f_88769_ = TextComponent.f_131282_;
   @Nullable
   private volatile Component f_88770_;
   private volatile boolean f_88771_;
   private int f_88772_;
   private final LongRunningTask f_88773_;
   private final int f_88774_ = 212;
   private Button f_167413_;
   public static final String[] f_88766_ = new String[]{"\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _"};

   public RealmsLongRunningMcoTaskScreen(Screen p_88777_, LongRunningTask p_88778_) {
      super(NarratorChatListener.f_93310_);
      this.f_88768_ = p_88777_;
      this.f_88773_ = p_88778_;
      p_88778_.m_90400_(this);
      Thread thread = new Thread(p_88778_, "Realms-long-running-task");
      thread.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(f_88767_));
      thread.start();
   }

   public void m_96624_() {
      super.m_96624_();
      f_167414_.m_175076_(this.f_88769_);
      ++this.f_88772_;
      this.f_88773_.m_5519_();
   }

   public boolean m_7933_(int p_88781_, int p_88782_, int p_88783_) {
      if (p_88781_ == 256) {
         this.m_88799_();
         return true;
      } else {
         return super.m_7933_(p_88781_, p_88782_, p_88783_);
      }
   }

   public void m_7856_() {
      this.f_88773_.m_90412_();
      this.f_167413_ = this.m_142416_(new Button(this.f_96543_ / 2 - 106, m_120774_(12), 212, 20, CommonComponents.f_130656_, (p_88795_) -> {
         this.m_88799_();
      }));
   }

   private void m_88799_() {
      this.f_88771_ = true;
      this.f_88773_.m_5520_();
      this.f_96541_.m_91152_(this.f_88768_);
   }

   public void m_6305_(PoseStack p_88785_, int p_88786_, int p_88787_, float p_88788_) {
      this.m_7333_(p_88785_);
      m_93215_(p_88785_, this.f_96547_, this.f_88769_, this.f_96543_ / 2, m_120774_(3), 16777215);
      Component component = this.f_88770_;
      if (component == null) {
         m_93208_(p_88785_, this.f_96547_, f_88766_[this.f_88772_ % f_88766_.length], this.f_96543_ / 2, m_120774_(8), 8421504);
      } else {
         m_93215_(p_88785_, this.f_96547_, component, this.f_96543_ / 2, m_120774_(8), 16711680);
      }

      super.m_6305_(p_88785_, p_88786_, p_88787_, p_88788_);
   }

   public void m_5673_(Component p_88792_) {
      this.f_88770_ = p_88792_;
      NarratorChatListener.f_93311_.m_168785_(p_88792_);
      this.f_96541_.execute(() -> {
         this.m_169411_(this.f_167413_);
         this.f_167413_ = this.m_142416_(new Button(this.f_96543_ / 2 - 106, this.f_96544_ / 4 + 120 + 12, 200, 20, CommonComponents.f_130660_, (p_88790_) -> {
            this.m_88799_();
         }));
      });
   }

   public void m_88796_(Component p_88797_) {
      this.f_88769_ = p_88797_;
   }

   public boolean m_88779_() {
      return this.f_88771_;
   }
}