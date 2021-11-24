package net.minecraft.world.entity;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Team;

public final class EntitySelector {
   public static final Predicate<Entity> f_20402_ = Entity::m_6084_;
   public static final Predicate<Entity> f_20403_ = (p_20442_) -> {
      return p_20442_.m_6084_() && p_20442_ instanceof LivingEntity;
   };
   public static final Predicate<Entity> f_20404_ = (p_20440_) -> {
      return p_20440_.m_6084_() && !p_20440_.m_20160_() && !p_20440_.m_20159_();
   };
   public static final Predicate<Entity> f_20405_ = (p_20438_) -> {
      return p_20438_ instanceof Container && p_20438_.m_6084_();
   };
   public static final Predicate<Entity> f_20406_ = (p_20436_) -> {
      return !(p_20436_ instanceof Player) || !p_20436_.m_5833_() && !((Player)p_20436_).m_7500_();
   };
   public static final Predicate<Entity> f_20408_ = (p_20434_) -> {
      return !p_20434_.m_5833_();
   };

   private EntitySelector() {
   }

   public static Predicate<Entity> m_20410_(double p_20411_, double p_20412_, double p_20413_, double p_20414_) {
      double d0 = p_20414_ * p_20414_;
      return (p_20420_) -> {
         return p_20420_ != null && p_20420_.m_20275_(p_20411_, p_20412_, p_20413_) <= d0;
      };
   }

   public static Predicate<Entity> m_20421_(Entity p_20422_) {
      Team team = p_20422_.m_5647_();
      Team.CollisionRule team$collisionrule = team == null ? Team.CollisionRule.ALWAYS : team.m_7156_();
      return (Predicate<Entity>)(team$collisionrule == Team.CollisionRule.NEVER ? Predicates.alwaysFalse() : f_20408_.and((p_20430_) -> {
         if (!p_20430_.m_6094_()) {
            return false;
         } else if (!p_20422_.f_19853_.f_46443_ || p_20430_ instanceof Player && ((Player)p_20430_).m_7578_()) {
            Team team1 = p_20430_.m_5647_();
            Team.CollisionRule team$collisionrule1 = team1 == null ? Team.CollisionRule.ALWAYS : team1.m_7156_();
            if (team$collisionrule1 == Team.CollisionRule.NEVER) {
               return false;
            } else {
               boolean flag = team != null && team.m_83536_(team1);
               if ((team$collisionrule == Team.CollisionRule.PUSH_OWN_TEAM || team$collisionrule1 == Team.CollisionRule.PUSH_OWN_TEAM) && flag) {
                  return false;
               } else {
                  return team$collisionrule != Team.CollisionRule.PUSH_OTHER_TEAMS && team$collisionrule1 != Team.CollisionRule.PUSH_OTHER_TEAMS || flag;
               }
            }
         } else {
            return false;
         }
      }));
   }

   public static Predicate<Entity> m_20431_(Entity p_20432_) {
      return (p_20425_) -> {
         while(true) {
            if (p_20425_.m_20159_()) {
               p_20425_ = p_20425_.m_20202_();
               if (p_20425_ != p_20432_) {
                  continue;
               }

               return false;
            }

            return true;
         }
      };
   }

   public static class MobCanWearArmorEntitySelector implements Predicate<Entity> {
      private final ItemStack f_20443_;

      public MobCanWearArmorEntitySelector(ItemStack p_20445_) {
         this.f_20443_ = p_20445_;
      }

      public boolean test(@Nullable Entity p_20447_) {
         if (!p_20447_.m_6084_()) {
            return false;
         } else if (!(p_20447_ instanceof LivingEntity)) {
            return false;
         } else {
            LivingEntity livingentity = (LivingEntity)p_20447_;
            return livingentity.m_7066_(this.f_20443_);
         }
      }
   }
}