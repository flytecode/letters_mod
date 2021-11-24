package net.minecraft.client.gui.components;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerTabOverlay extends GuiComponent {
   private static final Ordering<PlayerInfo> f_94518_ = Ordering.from(new PlayerTabOverlay.PlayerInfoComparator());
   public static final int f_169049_ = 20;
   public static final int f_169050_ = 16;
   public static final int f_169051_ = 25;
   public static final int f_169052_ = 52;
   public static final int f_169053_ = 61;
   public static final int f_169054_ = 160;
   public static final int f_169055_ = 169;
   public static final int f_169056_ = 70;
   public static final int f_169057_ = 79;
   private final Minecraft f_94519_;
   private final Gui f_94520_;
   @Nullable
   private Component f_94521_;
   @Nullable
   private Component f_94522_;
   private long f_94523_;
   private boolean f_94524_;

   public PlayerTabOverlay(Minecraft p_94527_, Gui p_94528_) {
      this.f_94519_ = p_94527_;
      this.f_94520_ = p_94528_;
   }

   public Component m_94549_(PlayerInfo p_94550_) {
      return p_94550_.m_105342_() != null ? this.m_94551_(p_94550_, p_94550_.m_105342_().m_6881_()) : this.m_94551_(p_94550_, PlayerTeam.m_83348_(p_94550_.m_105340_(), new TextComponent(p_94550_.m_105312_().getName())));
   }

   private Component m_94551_(PlayerInfo p_94552_, MutableComponent p_94553_) {
      return p_94552_.m_105325_() == GameType.SPECTATOR ? p_94553_.m_130940_(ChatFormatting.ITALIC) : p_94553_;
   }

   public void m_94556_(boolean p_94557_) {
      if (p_94557_ && !this.f_94524_) {
         this.f_94523_ = Util.m_137550_();
      }

      this.f_94524_ = p_94557_;
   }

   public void m_94544_(PoseStack p_94545_, int p_94546_, Scoreboard p_94547_, @Nullable Objective p_94548_) {
      ClientPacketListener clientpacketlistener = this.f_94519_.f_91074_.f_108617_;
      List<PlayerInfo> list = f_94518_.sortedCopy(clientpacketlistener.m_105142_());
      int i = 0;
      int j = 0;

      for(PlayerInfo playerinfo : list) {
         int k = this.f_94519_.f_91062_.m_92852_(this.m_94549_(playerinfo));
         i = Math.max(i, k);
         if (p_94548_ != null && p_94548_.m_83324_() != ObjectiveCriteria.RenderType.HEARTS) {
            k = this.f_94519_.f_91062_.m_92895_(" " + p_94547_.m_83471_(playerinfo.m_105312_().getName(), p_94548_).m_83400_());
            j = Math.max(j, k);
         }
      }

      list = list.subList(0, Math.min(list.size(), 80));
      int i4 = list.size();
      int j4 = i4;

      int k4;
      for(k4 = 1; j4 > 20; j4 = (i4 + k4 - 1) / k4) {
         ++k4;
      }

      boolean flag = this.f_94519_.m_91090_() || this.f_94519_.m_91403_().m_6198_().m_129535_();
      int l;
      if (p_94548_ != null) {
         if (p_94548_.m_83324_() == ObjectiveCriteria.RenderType.HEARTS) {
            l = 90;
         } else {
            l = j;
         }
      } else {
         l = 0;
      }

      int i1 = Math.min(k4 * ((flag ? 9 : 0) + i + l + 13), p_94546_ - 50) / k4;
      int j1 = p_94546_ / 2 - (i1 * k4 + (k4 - 1) * 5) / 2;
      int k1 = 10;
      int l1 = i1 * k4 + (k4 - 1) * 5;
      List<FormattedCharSequence> list1 = null;
      if (this.f_94522_ != null) {
         list1 = this.f_94519_.f_91062_.m_92923_(this.f_94522_, p_94546_ - 50);

         for(FormattedCharSequence formattedcharsequence : list1) {
            l1 = Math.max(l1, this.f_94519_.f_91062_.m_92724_(formattedcharsequence));
         }
      }

      List<FormattedCharSequence> list2 = null;
      if (this.f_94521_ != null) {
         list2 = this.f_94519_.f_91062_.m_92923_(this.f_94521_, p_94546_ - 50);

         for(FormattedCharSequence formattedcharsequence1 : list2) {
            l1 = Math.max(l1, this.f_94519_.f_91062_.m_92724_(formattedcharsequence1));
         }
      }

      if (list1 != null) {
         m_93172_(p_94545_, p_94546_ / 2 - l1 / 2 - 1, k1 - 1, p_94546_ / 2 + l1 / 2 + 1, k1 + list1.size() * 9, Integer.MIN_VALUE);

         for(FormattedCharSequence formattedcharsequence2 : list1) {
            int i2 = this.f_94519_.f_91062_.m_92724_(formattedcharsequence2);
            this.f_94519_.f_91062_.m_92744_(p_94545_, formattedcharsequence2, (float)(p_94546_ / 2 - i2 / 2), (float)k1, -1);
            k1 += 9;
         }

         ++k1;
      }

      m_93172_(p_94545_, p_94546_ / 2 - l1 / 2 - 1, k1 - 1, p_94546_ / 2 + l1 / 2 + 1, k1 + j4 * 9, Integer.MIN_VALUE);
      int l4 = this.f_94519_.f_91066_.m_92143_(553648127);

      for(int i5 = 0; i5 < i4; ++i5) {
         int j5 = i5 / j4;
         int j2 = i5 % j4;
         int k2 = j1 + j5 * i1 + j5 * 5;
         int l2 = k1 + j2 * 9;
         m_93172_(p_94545_, k2, l2, k2 + i1, l2 + 8, l4);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         if (i5 < list.size()) {
            PlayerInfo playerinfo1 = list.get(i5);
            GameProfile gameprofile = playerinfo1.m_105312_();
            if (flag) {
               Player player = this.f_94519_.f_91073_.m_46003_(gameprofile.getId());
               boolean flag1 = player != null && player.m_36170_(PlayerModelPart.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
               RenderSystem.m_157456_(0, playerinfo1.m_105337_());
               int i3 = 8 + (flag1 ? 8 : 0);
               int j3 = 8 * (flag1 ? -1 : 1);
               GuiComponent.m_93160_(p_94545_, k2, l2, 8, 8, 8.0F, (float)i3, 8, j3, 64, 64);
               if (player != null && player.m_36170_(PlayerModelPart.HAT)) {
                  int k3 = 8 + (flag1 ? 8 : 0);
                  int l3 = 8 * (flag1 ? -1 : 1);
                  GuiComponent.m_93160_(p_94545_, k2, l2, 8, 8, 40.0F, (float)k3, 8, l3, 64, 64);
               }

               k2 += 9;
            }

            this.f_94519_.f_91062_.m_92763_(p_94545_, this.m_94549_(playerinfo1), (float)k2, (float)l2, playerinfo1.m_105325_() == GameType.SPECTATOR ? -1862270977 : -1);
            if (p_94548_ != null && playerinfo1.m_105325_() != GameType.SPECTATOR) {
               int l5 = k2 + i + 1;
               int i6 = l5 + l;
               if (i6 - l5 > 5) {
                  this.m_94530_(p_94548_, l2, gameprofile.getName(), l5, i6, playerinfo1, p_94545_);
               }
            }

            this.m_94538_(p_94545_, i1, k2 - (flag ? 9 : 0), l2, playerinfo1);
         }
      }

      if (list2 != null) {
         k1 = k1 + j4 * 9 + 1;
         m_93172_(p_94545_, p_94546_ / 2 - l1 / 2 - 1, k1 - 1, p_94546_ / 2 + l1 / 2 + 1, k1 + list2.size() * 9, Integer.MIN_VALUE);

         for(FormattedCharSequence formattedcharsequence3 : list2) {
            int k5 = this.f_94519_.f_91062_.m_92724_(formattedcharsequence3);
            this.f_94519_.f_91062_.m_92744_(p_94545_, formattedcharsequence3, (float)(p_94546_ / 2 - k5 / 2), (float)k1, -1);
            k1 += 9;
         }
      }

   }

   protected void m_94538_(PoseStack p_94539_, int p_94540_, int p_94541_, int p_94542_, PlayerInfo p_94543_) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_93098_);
      int i = 0;
      int j;
      if (p_94543_.m_105330_() < 0) {
         j = 5;
      } else if (p_94543_.m_105330_() < 150) {
         j = 0;
      } else if (p_94543_.m_105330_() < 300) {
         j = 1;
      } else if (p_94543_.m_105330_() < 600) {
         j = 2;
      } else if (p_94543_.m_105330_() < 1000) {
         j = 3;
      } else {
         j = 4;
      }

      this.m_93250_(this.m_93252_() + 100);
      this.m_93228_(p_94539_, p_94541_ + p_94540_ - 11, p_94542_, 0, 176 + j * 8, 10, 8);
      this.m_93250_(this.m_93252_() - 100);
   }

   private void m_94530_(Objective p_94531_, int p_94532_, String p_94533_, int p_94534_, int p_94535_, PlayerInfo p_94536_, PoseStack p_94537_) {
      int i = p_94531_.m_83313_().m_83471_(p_94533_, p_94531_).m_83400_();
      if (p_94531_.m_83324_() == ObjectiveCriteria.RenderType.HEARTS) {
         RenderSystem.m_157456_(0, f_93098_);
         long j = Util.m_137550_();
         if (this.f_94523_ == p_94536_.m_105347_()) {
            if (i < p_94536_.m_105343_()) {
               p_94536_.m_105315_(j);
               p_94536_.m_105328_((long)(this.f_94520_.m_93079_() + 20));
            } else if (i > p_94536_.m_105343_()) {
               p_94536_.m_105315_(j);
               p_94536_.m_105328_((long)(this.f_94520_.m_93079_() + 10));
            }
         }

         if (j - p_94536_.m_105345_() > 1000L || this.f_94523_ != p_94536_.m_105347_()) {
            p_94536_.m_105326_(i);
            p_94536_.m_105331_(i);
            p_94536_.m_105315_(j);
         }

         p_94536_.m_105333_(this.f_94523_);
         p_94536_.m_105326_(i);
         int k = Mth.m_14167_((float)Math.max(i, p_94536_.m_105344_()) / 2.0F);
         int l = Math.max(Mth.m_14167_((float)(i / 2)), Math.max(Mth.m_14167_((float)(p_94536_.m_105344_() / 2)), 10));
         boolean flag = p_94536_.m_105346_() > (long)this.f_94520_.m_93079_() && (p_94536_.m_105346_() - (long)this.f_94520_.m_93079_()) / 3L % 2L == 1L;
         if (k > 0) {
            int i1 = Mth.m_14143_(Math.min((float)(p_94535_ - p_94534_ - 4) / (float)l, 9.0F));
            if (i1 > 3) {
               for(int j1 = k; j1 < l; ++j1) {
                  this.m_93228_(p_94537_, p_94534_ + j1 * i1, p_94532_, flag ? 25 : 16, 0, 9, 9);
               }

               for(int l1 = 0; l1 < k; ++l1) {
                  this.m_93228_(p_94537_, p_94534_ + l1 * i1, p_94532_, flag ? 25 : 16, 0, 9, 9);
                  if (flag) {
                     if (l1 * 2 + 1 < p_94536_.m_105344_()) {
                        this.m_93228_(p_94537_, p_94534_ + l1 * i1, p_94532_, 70, 0, 9, 9);
                     }

                     if (l1 * 2 + 1 == p_94536_.m_105344_()) {
                        this.m_93228_(p_94537_, p_94534_ + l1 * i1, p_94532_, 79, 0, 9, 9);
                     }
                  }

                  if (l1 * 2 + 1 < i) {
                     this.m_93228_(p_94537_, p_94534_ + l1 * i1, p_94532_, l1 >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (l1 * 2 + 1 == i) {
                     this.m_93228_(p_94537_, p_94534_ + l1 * i1, p_94532_, l1 >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float f = Mth.m_14036_((float)i / 20.0F, 0.0F, 1.0F);
               int k1 = (int)((1.0F - f) * 255.0F) << 16 | (int)(f * 255.0F) << 8;
               String s = "" + (float)i / 2.0F;
               if (p_94535_ - this.f_94519_.f_91062_.m_92895_(s + "hp") >= p_94534_) {
                  s = s + "hp";
               }

               this.f_94519_.f_91062_.m_92750_(p_94537_, s, (float)((p_94535_ + p_94534_) / 2 - this.f_94519_.f_91062_.m_92895_(s) / 2), (float)p_94532_, k1);
            }
         }
      } else {
         String s1 = "" + ChatFormatting.YELLOW + i;
         this.f_94519_.f_91062_.m_92750_(p_94537_, s1, (float)(p_94535_ - this.f_94519_.f_91062_.m_92895_(s1)), (float)p_94532_, 16777215);
      }

   }

   public void m_94554_(@Nullable Component p_94555_) {
      this.f_94521_ = p_94555_;
   }

   public void m_94558_(@Nullable Component p_94559_) {
      this.f_94522_ = p_94559_;
   }

   public void m_94529_() {
      this.f_94522_ = null;
      this.f_94521_ = null;
   }

   @OnlyIn(Dist.CLIENT)
   static class PlayerInfoComparator implements Comparator<PlayerInfo> {
      public int compare(PlayerInfo p_94564_, PlayerInfo p_94565_) {
         PlayerTeam playerteam = p_94564_.m_105340_();
         PlayerTeam playerteam1 = p_94565_.m_105340_();
         return ComparisonChain.start().compareTrueFirst(p_94564_.m_105325_() != GameType.SPECTATOR, p_94565_.m_105325_() != GameType.SPECTATOR).compare(playerteam != null ? playerteam.m_5758_() : "", playerteam1 != null ? playerteam1.m_5758_() : "").compare(p_94564_.m_105312_().getName(), p_94565_.m_105312_().getName(), String::compareToIgnoreCase).result();
      }
   }
}