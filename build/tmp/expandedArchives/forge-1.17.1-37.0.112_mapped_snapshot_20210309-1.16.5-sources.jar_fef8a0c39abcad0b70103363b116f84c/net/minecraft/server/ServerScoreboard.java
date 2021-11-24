package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardSaveData;

public class ServerScoreboard extends Scoreboard {
   private final MinecraftServer f_136193_;
   private final Set<Objective> f_136194_ = Sets.newHashSet();
   private final List<Runnable> f_136195_ = Lists.newArrayList();

   public ServerScoreboard(MinecraftServer p_136197_) {
      this.f_136193_ = p_136197_;
   }

   public void m_5734_(Score p_136206_) {
      super.m_5734_(p_136206_);
      if (this.f_136194_.contains(p_136206_.m_83404_())) {
         this.f_136193_.m_6846_().m_11268_(new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, p_136206_.m_83404_().m_83320_(), p_136206_.m_83405_(), p_136206_.m_83400_()));
      }

      this.m_136217_();
   }

   public void m_7182_(String p_136210_) {
      super.m_7182_(p_136210_);
      this.f_136193_.m_6846_().m_11268_(new ClientboundSetScorePacket(ServerScoreboard.Method.REMOVE, (String)null, p_136210_, 0));
      this.m_136217_();
   }

   public void m_5973_(String p_136212_, Objective p_136213_) {
      super.m_5973_(p_136212_, p_136213_);
      if (this.f_136194_.contains(p_136213_)) {
         this.f_136193_.m_6846_().m_11268_(new ClientboundSetScorePacket(ServerScoreboard.Method.REMOVE, p_136213_.m_83320_(), p_136212_, 0));
      }

      this.m_136217_();
   }

   public void m_7136_(int p_136199_, @Nullable Objective p_136200_) {
      Objective objective = this.m_83416_(p_136199_);
      super.m_7136_(p_136199_, p_136200_);
      if (objective != p_136200_ && objective != null) {
         if (this.m_136237_(objective) > 0) {
            this.f_136193_.m_6846_().m_11268_(new ClientboundSetDisplayObjectivePacket(p_136199_, p_136200_));
         } else {
            this.m_136235_(objective);
         }
      }

      if (p_136200_ != null) {
         if (this.f_136194_.contains(p_136200_)) {
            this.f_136193_.m_6846_().m_11268_(new ClientboundSetDisplayObjectivePacket(p_136199_, p_136200_));
         } else {
            this.m_136231_(p_136200_);
         }
      }

      this.m_136217_();
   }

   public boolean m_6546_(String p_136215_, PlayerTeam p_136216_) {
      if (super.m_6546_(p_136215_, p_136216_)) {
         this.f_136193_.m_6846_().m_11268_(ClientboundSetPlayerTeamPacket.m_179328_(p_136216_, p_136215_, ClientboundSetPlayerTeamPacket.Action.ADD));
         this.m_136217_();
         return true;
      } else {
         return false;
      }
   }

   public void m_6519_(String p_136223_, PlayerTeam p_136224_) {
      super.m_6519_(p_136223_, p_136224_);
      this.f_136193_.m_6846_().m_11268_(ClientboundSetPlayerTeamPacket.m_179328_(p_136224_, p_136223_, ClientboundSetPlayerTeamPacket.Action.REMOVE));
      this.m_136217_();
   }

   public void m_7092_(Objective p_136202_) {
      super.m_7092_(p_136202_);
      this.m_136217_();
   }

   public void m_7091_(Objective p_136219_) {
      super.m_7091_(p_136219_);
      if (this.f_136194_.contains(p_136219_)) {
         this.f_136193_.m_6846_().m_11268_(new ClientboundSetObjectivePacket(p_136219_, 2));
      }

      this.m_136217_();
   }

   public void m_7093_(Objective p_136226_) {
      super.m_7093_(p_136226_);
      if (this.f_136194_.contains(p_136226_)) {
         this.m_136235_(p_136226_);
      }

      this.m_136217_();
   }

   public void m_7650_(PlayerTeam p_136204_) {
      super.m_7650_(p_136204_);
      this.f_136193_.m_6846_().m_11268_(ClientboundSetPlayerTeamPacket.m_179332_(p_136204_, true));
      this.m_136217_();
   }

   public void m_7645_(PlayerTeam p_136221_) {
      super.m_7645_(p_136221_);
      this.f_136193_.m_6846_().m_11268_(ClientboundSetPlayerTeamPacket.m_179332_(p_136221_, false));
      this.m_136217_();
   }

   public void m_7644_(PlayerTeam p_136228_) {
      super.m_7644_(p_136228_);
      this.f_136193_.m_6846_().m_11268_(ClientboundSetPlayerTeamPacket.m_179326_(p_136228_));
      this.m_136217_();
   }

   public void m_136207_(Runnable p_136208_) {
      this.f_136195_.add(p_136208_);
   }

   protected void m_136217_() {
      for(Runnable runnable : this.f_136195_) {
         runnable.run();
      }

   }

   public List<Packet<?>> m_136229_(Objective p_136230_) {
      List<Packet<?>> list = Lists.newArrayList();
      list.add(new ClientboundSetObjectivePacket(p_136230_, 0));

      for(int i = 0; i < 19; ++i) {
         if (this.m_83416_(i) == p_136230_) {
            list.add(new ClientboundSetDisplayObjectivePacket(i, p_136230_));
         }
      }

      for(Score score : this.m_83498_(p_136230_)) {
         list.add(new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, score.m_83404_().m_83320_(), score.m_83405_(), score.m_83400_()));
      }

      return list;
   }

   public void m_136231_(Objective p_136232_) {
      List<Packet<?>> list = this.m_136229_(p_136232_);

      for(ServerPlayer serverplayer : this.f_136193_.m_6846_().m_11314_()) {
         for(Packet<?> packet : list) {
            serverplayer.f_8906_.m_141995_(packet);
         }
      }

      this.f_136194_.add(p_136232_);
   }

   public List<Packet<?>> m_136233_(Objective p_136234_) {
      List<Packet<?>> list = Lists.newArrayList();
      list.add(new ClientboundSetObjectivePacket(p_136234_, 1));

      for(int i = 0; i < 19; ++i) {
         if (this.m_83416_(i) == p_136234_) {
            list.add(new ClientboundSetDisplayObjectivePacket(i, p_136234_));
         }
      }

      return list;
   }

   public void m_136235_(Objective p_136236_) {
      List<Packet<?>> list = this.m_136233_(p_136236_);

      for(ServerPlayer serverplayer : this.f_136193_.m_6846_().m_11314_()) {
         for(Packet<?> packet : list) {
            serverplayer.f_8906_.m_141995_(packet);
         }
      }

      this.f_136194_.remove(p_136236_);
   }

   public int m_136237_(Objective p_136238_) {
      int i = 0;

      for(int j = 0; j < 19; ++j) {
         if (this.m_83416_(j) == p_136238_) {
            ++i;
         }
      }

      return i;
   }

   public ScoreboardSaveData m_180015_() {
      ScoreboardSaveData scoreboardsavedata = new ScoreboardSaveData(this);
      this.m_136207_(scoreboardsavedata::m_77762_);
      return scoreboardsavedata;
   }

   public ScoreboardSaveData m_180013_(CompoundTag p_180014_) {
      return this.m_180015_().m_166102_(p_180014_);
   }

   public static enum Method {
      CHANGE,
      REMOVE;
   }
}