package net.minecraft.world.scores;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class Team {
   public boolean m_83536_(@Nullable Team p_83537_) {
      if (p_83537_ == null) {
         return false;
      } else {
         return this == p_83537_;
      }
   }

   public abstract String m_5758_();

   public abstract MutableComponent m_6870_(Component p_83538_);

   public abstract boolean m_6259_();

   public abstract boolean m_6260_();

   public abstract Team.Visibility m_7470_();

   public abstract ChatFormatting m_7414_();

   public abstract Collection<String> m_6809_();

   public abstract Team.Visibility m_7468_();

   public abstract Team.CollisionRule m_7156_();

   public static enum CollisionRule {
      ALWAYS("always", 0),
      NEVER("never", 1),
      PUSH_OTHER_TEAMS("pushOtherTeams", 2),
      PUSH_OWN_TEAM("pushOwnTeam", 3);

      private static final Map<String, Team.CollisionRule> f_83545_ = Arrays.stream(values()).collect(Collectors.toMap((p_83559_) -> {
         return p_83559_.f_83543_;
      }, (p_83554_) -> {
         return p_83554_;
      }));
      public final String f_83543_;
      public final int f_83544_;

      @Nullable
      public static Team.CollisionRule m_83555_(String p_83556_) {
         return f_83545_.get(p_83556_);
      }

      private CollisionRule(String p_83551_, int p_83552_) {
         this.f_83543_ = p_83551_;
         this.f_83544_ = p_83552_;
      }

      public Component m_83557_() {
         return new TranslatableComponent("team.collision." + this.f_83543_);
      }
   }

   public static enum Visibility {
      ALWAYS("always", 0),
      NEVER("never", 1),
      HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
      HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

      private static final Map<String, Team.Visibility> f_83569_ = Arrays.stream(values()).collect(Collectors.toMap((p_83583_) -> {
         return p_83583_.f_83567_;
      }, (p_83578_) -> {
         return p_83578_;
      }));
      public final String f_83567_;
      public final int f_83568_;

      public static String[] m_166105_() {
         return f_83569_.keySet().toArray(new String[f_83569_.size()]);
      }

      @Nullable
      public static Team.Visibility m_83579_(String p_83580_) {
         return f_83569_.get(p_83580_);
      }

      private Visibility(String p_83575_, int p_83576_) {
         this.f_83567_ = p_83575_;
         this.f_83568_ = p_83576_;
      }

      public Component m_83581_() {
         return new TranslatableComponent("team.visibility." + this.f_83567_);
      }
   }
}