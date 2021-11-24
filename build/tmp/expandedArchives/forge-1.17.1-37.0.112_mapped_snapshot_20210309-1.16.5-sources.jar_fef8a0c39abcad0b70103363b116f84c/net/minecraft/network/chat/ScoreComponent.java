package net.minecraft.network.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class ScoreComponent extends BaseComponent implements ContextAwareComponent {
   private static final String f_178512_ = "*";
   private final String f_131046_;
   @Nullable
   private final EntitySelector f_131047_;
   private final String f_131048_;

   @Nullable
   private static EntitySelector m_131066_(String p_131067_) {
      try {
         return (new EntitySelectorParser(new StringReader(p_131067_))).m_121377_();
      } catch (CommandSyntaxException commandsyntaxexception) {
         return null;
      }
   }

   public ScoreComponent(String p_131054_, String p_131055_) {
      this(p_131054_, m_131066_(p_131054_), p_131055_);
   }

   private ScoreComponent(String p_131050_, @Nullable EntitySelector p_131051_, String p_131052_) {
      this.f_131046_ = p_131050_;
      this.f_131047_ = p_131051_;
      this.f_131048_ = p_131052_;
   }

   public String m_131071_() {
      return this.f_131046_;
   }

   @Nullable
   public EntitySelector m_178513_() {
      return this.f_131047_;
   }

   public String m_131072_() {
      return this.f_131048_;
   }

   private String m_131056_(CommandSourceStack p_131057_) throws CommandSyntaxException {
      if (this.f_131047_ != null) {
         List<? extends Entity> list = this.f_131047_.m_121160_(p_131057_);
         if (!list.isEmpty()) {
            if (list.size() != 1) {
               throw EntityArgument.f_91436_.create();
            }

            return list.get(0).m_6302_();
         }
      }

      return this.f_131046_;
   }

   private String m_131062_(String p_131063_, CommandSourceStack p_131064_) {
      MinecraftServer minecraftserver = p_131064_.m_81377_();
      if (minecraftserver != null) {
         Scoreboard scoreboard = minecraftserver.m_129896_();
         Objective objective = scoreboard.m_83477_(this.f_131048_);
         if (scoreboard.m_83461_(p_131063_, objective)) {
            Score score = scoreboard.m_83471_(p_131063_, objective);
            return Integer.toString(score.m_83400_());
         }
      }

      return "";
   }

   public ScoreComponent m_6879_() {
      return new ScoreComponent(this.f_131046_, this.f_131047_, this.f_131048_);
   }

   public MutableComponent m_5638_(@Nullable CommandSourceStack p_131059_, @Nullable Entity p_131060_, int p_131061_) throws CommandSyntaxException {
      if (p_131059_ == null) {
         return new TextComponent("");
      } else {
         String s = this.m_131056_(p_131059_);
         String s1 = p_131060_ != null && s.equals("*") ? p_131060_.m_6302_() : s;
         return new TextComponent(this.m_131062_(s1, p_131059_));
      }
   }

   public boolean equals(Object p_131069_) {
      if (this == p_131069_) {
         return true;
      } else if (!(p_131069_ instanceof ScoreComponent)) {
         return false;
      } else {
         ScoreComponent scorecomponent = (ScoreComponent)p_131069_;
         return this.f_131046_.equals(scorecomponent.f_131046_) && this.f_131048_.equals(scorecomponent.f_131048_) && super.equals(p_131069_);
      }
   }

   public String toString() {
      return "ScoreComponent{name='" + this.f_131046_ + "'objective='" + this.f_131048_ + "', siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
   }
}