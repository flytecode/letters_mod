package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LevelLoadingScreen extends Screen {
   private static final long f_169309_ = 2000L;
   private final StoringChunkProgressListener f_96138_;
   private long f_96139_ = -1L;
   private boolean f_169310_;
   private static final Object2IntMap<ChunkStatus> f_96140_ = Util.m_137469_(new Object2IntOpenHashMap<>(), (p_96157_) -> {
      p_96157_.defaultReturnValue(0);
      p_96157_.put(ChunkStatus.f_62314_, 5526612);
      p_96157_.put(ChunkStatus.f_62315_, 10066329);
      p_96157_.put(ChunkStatus.f_62316_, 6250897);
      p_96157_.put(ChunkStatus.f_62317_, 8434258);
      p_96157_.put(ChunkStatus.f_62318_, 13750737);
      p_96157_.put(ChunkStatus.f_62319_, 7497737);
      p_96157_.put(ChunkStatus.f_62320_, 7169628);
      p_96157_.put(ChunkStatus.f_62321_, 3159410);
      p_96157_.put(ChunkStatus.f_62322_, 2213376);
      p_96157_.put(ChunkStatus.f_62323_, 13421772);
      p_96157_.put(ChunkStatus.f_62324_, 15884384);
      p_96157_.put(ChunkStatus.f_62325_, 15658734);
      p_96157_.put(ChunkStatus.f_62326_, 16777215);
   });

   public LevelLoadingScreen(StoringChunkProgressListener p_96143_) {
      super(NarratorChatListener.f_93310_);
      this.f_96138_ = p_96143_;
   }

   public boolean m_6913_() {
      return false;
   }

   public void m_7861_() {
      this.f_169310_ = true;
      this.m_169407_(true);
   }

   protected void m_142227_(NarrationElementOutput p_169312_) {
      if (this.f_169310_) {
         p_169312_.m_169146_(NarratedElementType.TITLE, new TranslatableComponent("narrator.loading.done"));
      } else {
         String s = this.m_169313_();
         p_169312_.m_169143_(NarratedElementType.TITLE, s);
      }

   }

   private String m_169313_() {
      return Mth.m_14045_(this.f_96138_.m_9674_(), 0, 100) + "%";
   }

   public void m_6305_(PoseStack p_96145_, int p_96146_, int p_96147_, float p_96148_) {
      this.m_7333_(p_96145_);
      long i = Util.m_137550_();
      if (i - this.f_96139_ > 2000L) {
         this.f_96139_ = i;
         this.m_169407_(true);
      }

      int j = this.f_96543_ / 2;
      int k = this.f_96544_ / 2;
      int l = 30;
      m_96149_(p_96145_, this.f_96138_, j, k + 30, 2, 0);
      m_93208_(p_96145_, this.f_96547_, this.m_169313_(), j, k - 9 / 2 - 30, 16777215);
   }

   public static void m_96149_(PoseStack p_96150_, StoringChunkProgressListener p_96151_, int p_96152_, int p_96153_, int p_96154_, int p_96155_) {
      int i = p_96154_ + p_96155_;
      int j = p_96151_.m_9672_();
      int k = j * i - p_96155_;
      int l = p_96151_.m_9673_();
      int i1 = l * i - p_96155_;
      int j1 = p_96152_ - i1 / 2;
      int k1 = p_96153_ - i1 / 2;
      int l1 = k / 2 + 1;
      int i2 = -16772609;
      if (p_96155_ != 0) {
         m_93172_(p_96150_, p_96152_ - l1, p_96153_ - l1, p_96152_ - l1 + 1, p_96153_ + l1, -16772609);
         m_93172_(p_96150_, p_96152_ + l1 - 1, p_96153_ - l1, p_96152_ + l1, p_96153_ + l1, -16772609);
         m_93172_(p_96150_, p_96152_ - l1, p_96153_ - l1, p_96152_ + l1, p_96153_ - l1 + 1, -16772609);
         m_93172_(p_96150_, p_96152_ - l1, p_96153_ + l1 - 1, p_96152_ + l1, p_96153_ + l1, -16772609);
      }

      for(int j2 = 0; j2 < l; ++j2) {
         for(int k2 = 0; k2 < l; ++k2) {
            ChunkStatus chunkstatus = p_96151_.m_9663_(j2, k2);
            int l2 = j1 + j2 * i;
            int i3 = k1 + k2 * i;
            m_93172_(p_96150_, l2, i3, l2 + p_96154_, i3 + p_96154_, f_96140_.getInt(chunkstatus) | -16777216);
         }
      }

   }
}