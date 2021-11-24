package net.minecraft.world.entity.ai.goal;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;

public class FollowBoatGoal extends Goal {
   private int f_25233_;
   private final PathfinderMob f_25234_;
   private Player f_25235_;
   private BoatGoals f_25236_;

   public FollowBoatGoal(PathfinderMob p_25238_) {
      this.f_25234_ = p_25238_;
   }

   public boolean m_8036_() {
      List<Boat> list = this.f_25234_.f_19853_.m_45976_(Boat.class, this.f_25234_.m_142469_().m_82400_(5.0D));
      boolean flag = false;

      for(Boat boat : list) {
         Entity entity = boat.m_6688_();
         if (entity instanceof Player && (Mth.m_14154_(((Player)entity).f_20900_) > 0.0F || Mth.m_14154_(((Player)entity).f_20902_) > 0.0F)) {
            flag = true;
            break;
         }
      }

      return this.f_25235_ != null && (Mth.m_14154_(this.f_25235_.f_20900_) > 0.0F || Mth.m_14154_(this.f_25235_.f_20902_) > 0.0F) || flag;
   }

   public boolean m_6767_() {
      return true;
   }

   public boolean m_8045_() {
      return this.f_25235_ != null && this.f_25235_.m_20159_() && (Mth.m_14154_(this.f_25235_.f_20900_) > 0.0F || Mth.m_14154_(this.f_25235_.f_20902_) > 0.0F);
   }

   public void m_8056_() {
      for(Boat boat : this.f_25234_.f_19853_.m_45976_(Boat.class, this.f_25234_.m_142469_().m_82400_(5.0D))) {
         if (boat.m_6688_() != null && boat.m_6688_() instanceof Player) {
            this.f_25235_ = (Player)boat.m_6688_();
            break;
         }
      }

      this.f_25233_ = 0;
      this.f_25236_ = BoatGoals.GO_TO_BOAT;
   }

   public void m_8041_() {
      this.f_25235_ = null;
   }

   public void m_8037_() {
      boolean flag = Mth.m_14154_(this.f_25235_.f_20900_) > 0.0F || Mth.m_14154_(this.f_25235_.f_20902_) > 0.0F;
      float f = this.f_25236_ == BoatGoals.GO_IN_BOAT_DIRECTION ? (flag ? 0.01F : 0.0F) : 0.015F;
      this.f_25234_.m_19920_(f, new Vec3((double)this.f_25234_.f_20900_, (double)this.f_25234_.f_20901_, (double)this.f_25234_.f_20902_));
      this.f_25234_.m_6478_(MoverType.SELF, this.f_25234_.m_20184_());
      if (--this.f_25233_ <= 0) {
         this.f_25233_ = 10;
         if (this.f_25236_ == BoatGoals.GO_TO_BOAT) {
            BlockPos blockpos = this.f_25235_.m_142538_().m_142300_(this.f_25235_.m_6350_().m_122424_());
            blockpos = blockpos.m_142082_(0, -1, 0);
            this.f_25234_.m_21573_().m_26519_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), 1.0D);
            if (this.f_25234_.m_20270_(this.f_25235_) < 4.0F) {
               this.f_25233_ = 0;
               this.f_25236_ = BoatGoals.GO_IN_BOAT_DIRECTION;
            }
         } else if (this.f_25236_ == BoatGoals.GO_IN_BOAT_DIRECTION) {
            Direction direction = this.f_25235_.m_6374_();
            BlockPos blockpos1 = this.f_25235_.m_142538_().m_5484_(direction, 10);
            this.f_25234_.m_21573_().m_26519_((double)blockpos1.m_123341_(), (double)(blockpos1.m_123342_() - 1), (double)blockpos1.m_123343_(), 1.0D);
            if (this.f_25234_.m_20270_(this.f_25235_) > 12.0F) {
               this.f_25233_ = 0;
               this.f_25236_ = BoatGoals.GO_TO_BOAT;
            }
         }

      }
   }
}