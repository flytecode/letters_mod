package net.minecraft.world.scores;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ScoreboardSaveData extends SavedData {
   public static final String f_166099_ = "scoreboard";
   private final Scoreboard f_83509_;

   public ScoreboardSaveData(Scoreboard p_166101_) {
      this.f_83509_ = p_166101_;
   }

   public ScoreboardSaveData m_166102_(CompoundTag p_166103_) {
      this.m_83528_(p_166103_.m_128437_("Objectives", 10));
      this.f_83509_.m_83445_(p_166103_.m_128437_("PlayerScores", 10));
      if (p_166103_.m_128425_("DisplaySlots", 10)) {
         this.m_83530_(p_166103_.m_128469_("DisplaySlots"));
      }

      if (p_166103_.m_128425_("Teams", 9)) {
         this.m_83524_(p_166103_.m_128437_("Teams", 10));
      }

      return this;
   }

   private void m_83524_(ListTag p_83525_) {
      for(int i = 0; i < p_83525_.size(); ++i) {
         CompoundTag compoundtag = p_83525_.m_128728_(i);
         String s = compoundtag.m_128461_("Name");
         if (s.length() > 16) {
            s = s.substring(0, 16);
         }

         PlayerTeam playerteam = this.f_83509_.m_83492_(s);
         Component component = Component.Serializer.m_130701_(compoundtag.m_128461_("DisplayName"));
         if (component != null) {
            playerteam.m_83353_(component);
         }

         if (compoundtag.m_128425_("TeamColor", 8)) {
            playerteam.m_83351_(ChatFormatting.m_126657_(compoundtag.m_128461_("TeamColor")));
         }

         if (compoundtag.m_128425_("AllowFriendlyFire", 99)) {
            playerteam.m_83355_(compoundtag.m_128471_("AllowFriendlyFire"));
         }

         if (compoundtag.m_128425_("SeeFriendlyInvisibles", 99)) {
            playerteam.m_83362_(compoundtag.m_128471_("SeeFriendlyInvisibles"));
         }

         if (compoundtag.m_128425_("MemberNamePrefix", 8)) {
            Component component1 = Component.Serializer.m_130701_(compoundtag.m_128461_("MemberNamePrefix"));
            if (component1 != null) {
               playerteam.m_83360_(component1);
            }
         }

         if (compoundtag.m_128425_("MemberNameSuffix", 8)) {
            Component component2 = Component.Serializer.m_130701_(compoundtag.m_128461_("MemberNameSuffix"));
            if (component2 != null) {
               playerteam.m_83365_(component2);
            }
         }

         if (compoundtag.m_128425_("NameTagVisibility", 8)) {
            Team.Visibility team$visibility = Team.Visibility.m_83579_(compoundtag.m_128461_("NameTagVisibility"));
            if (team$visibility != null) {
               playerteam.m_83346_(team$visibility);
            }
         }

         if (compoundtag.m_128425_("DeathMessageVisibility", 8)) {
            Team.Visibility team$visibility1 = Team.Visibility.m_83579_(compoundtag.m_128461_("DeathMessageVisibility"));
            if (team$visibility1 != null) {
               playerteam.m_83358_(team$visibility1);
            }
         }

         if (compoundtag.m_128425_("CollisionRule", 8)) {
            Team.CollisionRule team$collisionrule = Team.CollisionRule.m_83555_(compoundtag.m_128461_("CollisionRule"));
            if (team$collisionrule != null) {
               playerteam.m_83344_(team$collisionrule);
            }
         }

         this.m_83514_(playerteam, compoundtag.m_128437_("Players", 8));
      }

   }

   private void m_83514_(PlayerTeam p_83515_, ListTag p_83516_) {
      for(int i = 0; i < p_83516_.size(); ++i) {
         this.f_83509_.m_6546_(p_83516_.m_128778_(i), p_83515_);
      }

   }

   private void m_83530_(CompoundTag p_83531_) {
      for(int i = 0; i < 19; ++i) {
         if (p_83531_.m_128425_("slot_" + i, 8)) {
            String s = p_83531_.m_128461_("slot_" + i);
            Objective objective = this.f_83509_.m_83477_(s);
            this.f_83509_.m_7136_(i, objective);
         }
      }

   }

   private void m_83528_(ListTag p_83529_) {
      for(int i = 0; i < p_83529_.size(); ++i) {
         CompoundTag compoundtag = p_83529_.m_128728_(i);
         ObjectiveCriteria.m_83614_(compoundtag.m_128461_("CriteriaName")).ifPresent((p_83523_) -> {
            String s = compoundtag.m_128461_("Name");
            if (s.length() > 16) {
               s = s.substring(0, 16);
            }

            Component component = Component.Serializer.m_130701_(compoundtag.m_128461_("DisplayName"));
            ObjectiveCriteria.RenderType objectivecriteria$rendertype = ObjectiveCriteria.RenderType.m_83634_(compoundtag.m_128461_("RenderType"));
            this.f_83509_.m_83436_(s, p_83523_, component, objectivecriteria$rendertype);
         });
      }

   }

   public CompoundTag m_7176_(CompoundTag p_83527_) {
      p_83527_.m_128365_("Objectives", this.m_83534_());
      p_83527_.m_128365_("PlayerScores", this.f_83509_.m_83497_());
      p_83527_.m_128365_("Teams", this.m_83513_());
      this.m_83532_(p_83527_);
      return p_83527_;
   }

   private ListTag m_83513_() {
      ListTag listtag = new ListTag();

      for(PlayerTeam playerteam : this.f_83509_.m_83491_()) {
         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128359_("Name", playerteam.m_5758_());
         compoundtag.m_128359_("DisplayName", Component.Serializer.m_130703_(playerteam.m_83364_()));
         if (playerteam.m_7414_().m_126656_() >= 0) {
            compoundtag.m_128359_("TeamColor", playerteam.m_7414_().m_126666_());
         }

         compoundtag.m_128379_("AllowFriendlyFire", playerteam.m_6260_());
         compoundtag.m_128379_("SeeFriendlyInvisibles", playerteam.m_6259_());
         compoundtag.m_128359_("MemberNamePrefix", Component.Serializer.m_130703_(playerteam.m_83370_()));
         compoundtag.m_128359_("MemberNameSuffix", Component.Serializer.m_130703_(playerteam.m_83371_()));
         compoundtag.m_128359_("NameTagVisibility", playerteam.m_7470_().f_83567_);
         compoundtag.m_128359_("DeathMessageVisibility", playerteam.m_7468_().f_83567_);
         compoundtag.m_128359_("CollisionRule", playerteam.m_7156_().f_83543_);
         ListTag listtag1 = new ListTag();

         for(String s : playerteam.m_6809_()) {
            listtag1.add(StringTag.m_129297_(s));
         }

         compoundtag.m_128365_("Players", listtag1);
         listtag.add(compoundtag);
      }

      return listtag;
   }

   private void m_83532_(CompoundTag p_83533_) {
      CompoundTag compoundtag = new CompoundTag();
      boolean flag = false;

      for(int i = 0; i < 19; ++i) {
         Objective objective = this.f_83509_.m_83416_(i);
         if (objective != null) {
            compoundtag.m_128359_("slot_" + i, objective.m_83320_());
            flag = true;
         }
      }

      if (flag) {
         p_83533_.m_128365_("DisplaySlots", compoundtag);
      }

   }

   private ListTag m_83534_() {
      ListTag listtag = new ListTag();

      for(Objective objective : this.f_83509_.m_83466_()) {
         if (objective.m_83321_() != null) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128359_("Name", objective.m_83320_());
            compoundtag.m_128359_("CriteriaName", objective.m_83321_().m_83620_());
            compoundtag.m_128359_("DisplayName", Component.Serializer.m_130703_(objective.m_83322_()));
            compoundtag.m_128359_("RenderType", objective.m_83324_().m_83633_());
            listtag.add(compoundtag);
         }
      }

      return listtag;
   }
}