package net.minecraft.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Screenshot {
   private static final Logger f_92276_ = LogManager.getLogger();
   private static final DateFormat f_92277_ = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
   private int f_168594_;
   private final DataOutputStream f_168595_;
   private final byte[] f_168596_;
   private final int f_168597_;
   private final int f_168598_;
   private File f_168599_;

   public static void m_92289_(File p_92290_, RenderTarget p_92293_, Consumer<Component> p_92294_) {
      m_92295_(p_92290_, (String)null, p_92293_, p_92294_);
   }

   public static void m_92295_(File p_92296_, @Nullable String p_92297_, RenderTarget p_92300_, Consumer<Component> p_92301_) {
      if (!RenderSystem.m_69586_()) {
         RenderSystem.m_69879_(() -> {
            m_92305_(p_92296_, p_92297_, p_92300_, p_92301_);
         });
      } else {
         m_92305_(p_92296_, p_92297_, p_92300_, p_92301_);
      }

   }

   private static void m_92305_(File p_92306_, @Nullable String p_92307_, RenderTarget p_92310_, Consumer<Component> p_92311_) {
      NativeImage nativeimage = m_92279_(p_92310_);
      File file1 = new File(p_92306_, "screenshots");
      file1.mkdir();
      File file2;
      if (p_92307_ == null) {
         file2 = m_92287_(file1);
      } else {
         file2 = new File(file1, p_92307_);
      }

      net.minecraftforge.client.event.ScreenshotEvent event = net.minecraftforge.client.ForgeHooksClient.onScreenshot(nativeimage, file2);
      if (event.isCanceled()) {
         p_92311_.accept(event.getCancelMessage());
         return;
      }
      final File target = event.getScreenshotFile();

      Util.m_137579_().execute(() -> {
         try {
            nativeimage.m_85056_(target);
            Component component = (new TextComponent(file2.getName())).m_130940_(ChatFormatting.UNDERLINE).m_130938_((p_168608_) -> {
               return p_168608_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, target.getAbsolutePath()));
            });
            if (event.getResultMessage() != null)
               p_92311_.accept(event.getResultMessage());
            else
               p_92311_.accept(new TranslatableComponent("screenshot.success", component));
         } catch (Exception exception) {
            f_92276_.warn("Couldn't save screenshot", (Throwable)exception);
            p_92311_.accept(new TranslatableComponent("screenshot.failure", exception.getMessage()));
         } finally {
            nativeimage.close();
         }

      });
   }

   public static NativeImage m_92279_(RenderTarget p_92282_) {
      int i = p_92282_.f_83915_;
      int j = p_92282_.f_83916_;
      NativeImage nativeimage = new NativeImage(i, j, false);
      RenderSystem.m_69396_(p_92282_.m_83975_());
      nativeimage.m_85045_(0, true);
      nativeimage.m_85122_();
      return nativeimage;
   }

   private static File m_92287_(File p_92288_) {
      String s = f_92277_.format(new Date());
      int i = 1;

      while(true) {
         File file1 = new File(p_92288_, s + (i == 1 ? "" : "_" + i) + ".png");
         if (!file1.exists()) {
            return file1;
         }

         ++i;
      }
   }

   public Screenshot(File p_168601_, int p_168602_, int p_168603_, int p_168604_) throws IOException {
      this.f_168597_ = p_168602_;
      this.f_168598_ = p_168603_;
      this.f_168594_ = p_168604_;
      File file1 = new File(p_168601_, "screenshots");
      file1.mkdir();
      String s = "huge_" + f_92277_.format(new Date());

      for(int i = 1; (this.f_168599_ = new File(file1, s + (i == 1 ? "" : "_" + i) + ".tga")).exists(); ++i) {
      }

      byte[] abyte = new byte[18];
      abyte[2] = 2;
      abyte[12] = (byte)(p_168602_ % 256);
      abyte[13] = (byte)(p_168602_ / 256);
      abyte[14] = (byte)(p_168603_ % 256);
      abyte[15] = (byte)(p_168603_ / 256);
      abyte[16] = 24;
      this.f_168596_ = new byte[p_168602_ * p_168604_ * 3];
      this.f_168595_ = new DataOutputStream(new FileOutputStream(this.f_168599_));
      this.f_168595_.write(abyte);
   }

   public void m_168609_(ByteBuffer p_168610_, int p_168611_, int p_168612_, int p_168613_, int p_168614_) {
      int i = p_168613_;
      int j = p_168614_;
      if (p_168613_ > this.f_168597_ - p_168611_) {
         i = this.f_168597_ - p_168611_;
      }

      if (p_168614_ > this.f_168598_ - p_168612_) {
         j = this.f_168598_ - p_168612_;
      }

      this.f_168594_ = j;

      for(int k = 0; k < j; ++k) {
         p_168610_.position((p_168614_ - j) * p_168613_ * 3 + k * p_168613_ * 3);
         int l = (p_168611_ + k * this.f_168597_) * 3;
         p_168610_.get(this.f_168596_, l, i * 3);
      }

   }

   public void m_168605_() throws IOException {
      this.f_168595_.write(this.f_168596_, 0, this.f_168597_ * 3 * this.f_168594_);
   }

   public File m_168615_() throws IOException {
      this.f_168595_.close();
      return this.f_168599_;
   }
}
