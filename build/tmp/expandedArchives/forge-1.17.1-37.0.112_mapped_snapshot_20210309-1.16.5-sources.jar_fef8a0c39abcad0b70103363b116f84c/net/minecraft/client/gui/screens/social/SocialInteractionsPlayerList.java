package net.minecraft.client.gui.screens.social;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SocialInteractionsPlayerList extends ContainerObjectSelectionList<PlayerEntry> {
   private final SocialInteractionsScreen f_100692_;
   private final List<PlayerEntry> f_100694_ = Lists.newArrayList();
   @Nullable
   private String f_100695_;

   public SocialInteractionsPlayerList(SocialInteractionsScreen p_100697_, Minecraft p_100698_, int p_100699_, int p_100700_, int p_100701_, int p_100702_, int p_100703_) {
      super(p_100698_, p_100699_, p_100700_, p_100701_, p_100702_, p_100703_);
      this.f_100692_ = p_100697_;
      this.m_93488_(false);
      this.m_93496_(false);
   }

   public void m_6305_(PoseStack p_100705_, int p_100706_, int p_100707_, float p_100708_) {
      double d0 = this.f_93386_.m_91268_().m_85449_();
      RenderSystem.m_69488_((int)((double)this.m_5747_() * d0), (int)((double)(this.f_93389_ - this.f_93391_) * d0), (int)((double)(this.m_5756_() + 6) * d0), (int)((double)(this.f_93389_ - (this.f_93389_ - this.f_93391_) - this.f_93390_ - 4) * d0));
      super.m_6305_(p_100705_, p_100706_, p_100707_, p_100708_);
      RenderSystem.m_69471_();
   }

   public void m_100719_(Collection<UUID> p_100720_, double p_100721_) {
      this.f_100694_.clear();

      for(UUID uuid : p_100720_) {
         PlayerInfo playerinfo = this.f_93386_.f_91074_.f_108617_.m_104949_(uuid);
         if (playerinfo != null) {
            this.f_100694_.add(new PlayerEntry(this.f_93386_, this.f_100692_, playerinfo.m_105312_().getId(), playerinfo.m_105312_().getName(), playerinfo::m_105337_));
         }
      }

      this.m_100725_();
      this.f_100694_.sort((p_100712_, p_100713_) -> {
         return p_100712_.m_100600_().compareToIgnoreCase(p_100713_.m_100600_());
      });
      this.m_5988_(this.f_100694_);
      this.m_93410_(p_100721_);
   }

   private void m_100725_() {
      if (this.f_100695_ != null) {
         this.f_100694_.removeIf((p_100710_) -> {
            return !p_100710_.m_100600_().toLowerCase(Locale.ROOT).contains(this.f_100695_);
         });
         this.m_5988_(this.f_100694_);
      }

   }

   public void m_100717_(String p_100718_) {
      this.f_100695_ = p_100718_;
   }

   public boolean m_100724_() {
      return this.f_100694_.isEmpty();
   }

   public void m_100714_(PlayerInfo p_100715_, SocialInteractionsScreen.Page p_100716_) {
      UUID uuid = p_100715_.m_105312_().getId();

      for(PlayerEntry playerentry : this.f_100694_) {
         if (playerentry.m_100618_().equals(uuid)) {
            playerentry.m_100619_(false);
            return;
         }
      }

      if ((p_100716_ == SocialInteractionsScreen.Page.ALL || this.f_93386_.m_91266_().m_100684_(uuid)) && (Strings.isNullOrEmpty(this.f_100695_) || p_100715_.m_105312_().getName().toLowerCase(Locale.ROOT).contains(this.f_100695_))) {
         PlayerEntry playerentry1 = new PlayerEntry(this.f_93386_, this.f_100692_, p_100715_.m_105312_().getId(), p_100715_.m_105312_().getName(), p_100715_::m_105337_);
         this.m_7085_(playerentry1);
         this.f_100694_.add(playerentry1);
      }

   }

   public void m_100722_(UUID p_100723_) {
      for(PlayerEntry playerentry : this.f_100694_) {
         if (playerentry.m_100618_().equals(p_100723_)) {
            playerentry.m_100619_(true);
            return;
         }
      }

   }
}