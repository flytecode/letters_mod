package net.minecraft.world.level.storage;

import com.mojang.bridge.game.GameVersion;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import org.apache.commons.lang3.StringUtils;

public class LevelSummary implements Comparable<LevelSummary> {
   private final LevelSettings f_78344_;
   private final LevelVersion f_78345_;
   private final String f_78346_;
   private final boolean f_78347_;
   private final boolean f_78348_;
   private final File f_78349_;
   @Nullable
   private Component f_78350_;

   public LevelSummary(LevelSettings p_78352_, LevelVersion p_78353_, String p_78354_, boolean p_78355_, boolean p_78356_, File p_78357_) {
      this.f_78344_ = p_78352_;
      this.f_78345_ = p_78353_;
      this.f_78346_ = p_78354_;
      this.f_78348_ = p_78356_;
      this.f_78349_ = p_78357_;
      this.f_78347_ = p_78355_;
   }

   public String m_78358_() {
      return this.f_78346_;
   }

   public String m_78361_() {
      return StringUtils.isEmpty(this.f_78344_.m_46917_()) ? this.f_78346_ : this.f_78344_.m_46917_();
   }

   public File m_78362_() {
      return this.f_78349_;
   }

   public boolean m_78365_() {
      return this.f_78347_;
   }

   public long m_78366_() {
      return this.f_78345_.m_78392_();
   }

   public int compareTo(LevelSummary p_78360_) {
      if (this.f_78345_.m_78392_() < p_78360_.f_78345_.m_78392_()) {
         return 1;
      } else {
         return this.f_78345_.m_78392_() > p_78360_.f_78345_.m_78392_() ? -1 : this.f_78346_.compareTo(p_78360_.f_78346_);
      }
   }

   public LevelSettings m_164913_() {
      return this.f_78344_;
   }

   public GameType m_78367_() {
      return this.f_78344_.m_46929_();
   }

   public boolean m_78368_() {
      return this.f_78344_.m_46930_();
   }

   public boolean m_78369_() {
      return this.f_78344_.m_46932_();
   }

   public MutableComponent m_78370_() {
      return (MutableComponent)(StringUtil.m_14408_(this.f_78345_.m_78393_()) ? new TranslatableComponent("selectWorld.versionUnknown") : new TextComponent(this.f_78345_.m_78393_()));
   }

   public LevelVersion m_78371_() {
      return this.f_78345_;
   }

   public boolean m_78372_() {
      return this.m_78373_() || !SharedConstants.m_136187_().isStable() && !this.f_78345_.m_78395_() || this.m_164914_().m_164931_();
   }

   public boolean m_78373_() {
      return this.f_78345_.m_78394_() > SharedConstants.m_136187_().getWorldVersion();
   }

   public LevelSummary.BackupStatus m_164914_() {
      GameVersion gameversion = SharedConstants.m_136187_();
      int i = gameversion.getWorldVersion();
      int j = this.f_78345_.m_78394_();
      if (!gameversion.isStable() && j < i) {
         return LevelSummary.BackupStatus.UPGRADE_TO_SNAPSHOT;
      } else {
         return j > i ? LevelSummary.BackupStatus.DOWNGRADE : LevelSummary.BackupStatus.NONE;
      }
   }

   public boolean m_78375_() {
      return this.f_78348_;
   }

   public boolean m_164915_() {
      int i = this.f_78345_.m_78394_();
      boolean flag = i > 2692 && i <= 2706;
      return flag;
   }

   public boolean m_164916_() {
      return this.m_78375_() || this.m_164915_();
   }

   public Component m_78376_() {
      if (this.f_78350_ == null) {
         this.f_78350_ = this.m_78377_();
      }

      return this.f_78350_;
   }

   private Component m_78377_() {
      if (this.m_78375_()) {
         return (new TranslatableComponent("selectWorld.locked")).m_130940_(ChatFormatting.RED);
      } else if (this.m_164915_()) {
         return (new TranslatableComponent("selectWorld.pre_worldheight")).m_130940_(ChatFormatting.RED);
      } else if (this.m_78365_()) {
         return new TranslatableComponent("selectWorld.conversion");
      } else {
         MutableComponent mutablecomponent = (MutableComponent)(this.m_78368_() ? (new TextComponent("")).m_7220_((new TranslatableComponent("gameMode.hardcore")).m_130940_(ChatFormatting.DARK_RED)) : new TranslatableComponent("gameMode." + this.m_78367_().m_46405_()));
         if (this.m_78369_()) {
            mutablecomponent.m_130946_(", ").m_7220_(new TranslatableComponent("selectWorld.cheats"));
         }

         MutableComponent mutablecomponent1 = this.m_78370_();
         MutableComponent mutablecomponent2 = (new TextComponent(", ")).m_7220_(new TranslatableComponent("selectWorld.version")).m_130946_(" ");
         if (this.m_78372_()) {
            mutablecomponent2.m_7220_(mutablecomponent1.m_130940_(this.m_78373_() ? ChatFormatting.RED : ChatFormatting.ITALIC));
         } else {
            mutablecomponent2.m_7220_(mutablecomponent1);
         }

         mutablecomponent.m_7220_(mutablecomponent2);
         return mutablecomponent;
      }
   }

   public static enum BackupStatus {
      NONE(false, false, ""),
      DOWNGRADE(true, true, "downgrade"),
      UPGRADE_TO_SNAPSHOT(true, false, "snapshot");

      private final boolean f_164920_;
      private final boolean f_164921_;
      private final String f_164922_;

      private BackupStatus(boolean p_164928_, boolean p_164929_, String p_164930_) {
         this.f_164920_ = p_164928_;
         this.f_164921_ = p_164929_;
         this.f_164922_ = p_164930_;
      }

      public boolean m_164931_() {
         return this.f_164920_;
      }

      public boolean m_164932_() {
         return this.f_164921_;
      }

      public String m_164933_() {
         return this.f_164922_;
      }
   }
}