package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MoveThroughVillageGoal extends Goal {
   protected final PathfinderMob f_25573_;
   private final double f_25574_;
   private Path f_25575_;
   private BlockPos f_25576_;
   private final boolean f_25577_;
   private final List<BlockPos> f_25578_ = Lists.newArrayList();
   private final int f_25579_;
   private final BooleanSupplier f_25580_;

   public MoveThroughVillageGoal(PathfinderMob p_25582_, double p_25583_, boolean p_25584_, int p_25585_, BooleanSupplier p_25586_) {
      this.f_25573_ = p_25582_;
      this.f_25574_ = p_25583_;
      this.f_25577_ = p_25584_;
      this.f_25579_ = p_25585_;
      this.f_25580_ = p_25586_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      if (!GoalUtils.m_26894_(p_25582_)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   public boolean m_8036_() {
      if (!GoalUtils.m_26894_(this.f_25573_)) {
         return false;
      } else {
         this.m_25597_();
         if (this.f_25577_ && this.f_25573_.f_19853_.m_46461_()) {
            return false;
         } else {
            ServerLevel serverlevel = (ServerLevel)this.f_25573_.f_19853_;
            BlockPos blockpos = this.f_25573_.m_142538_();
            if (!serverlevel.m_8736_(blockpos, 6)) {
               return false;
            } else {
               Vec3 vec3 = LandRandomPos.m_148503_(this.f_25573_, 15, 7, (p_25591_) -> {
                  if (!serverlevel.m_8802_(p_25591_)) {
                     return Double.NEGATIVE_INFINITY;
                  } else {
                     Optional<BlockPos> optional1 = serverlevel.m_8904_().m_27186_(PoiType.f_27330_, this::m_25592_, p_25591_, 10, PoiManager.Occupancy.IS_OCCUPIED);
                     return !optional1.isPresent() ? Double.NEGATIVE_INFINITY : -optional1.get().m_123331_(blockpos);
                  }
               });
               if (vec3 == null) {
                  return false;
               } else {
                  Optional<BlockPos> optional = serverlevel.m_8904_().m_27186_(PoiType.f_27330_, this::m_25592_, new BlockPos(vec3), 10, PoiManager.Occupancy.IS_OCCUPIED);
                  if (!optional.isPresent()) {
                     return false;
                  } else {
                     this.f_25576_ = optional.get().m_7949_();
                     GroundPathNavigation groundpathnavigation = (GroundPathNavigation)this.f_25573_.m_21573_();
                     boolean flag = groundpathnavigation.m_26492_();
                     groundpathnavigation.m_26477_(this.f_25580_.getAsBoolean());
                     this.f_25575_ = groundpathnavigation.m_7864_(this.f_25576_, 0);
                     groundpathnavigation.m_26477_(flag);
                     if (this.f_25575_ == null) {
                        Vec3 vec31 = DefaultRandomPos.m_148412_(this.f_25573_, 10, 7, Vec3.m_82539_(this.f_25576_), (double)((float)Math.PI / 2F));
                        if (vec31 == null) {
                           return false;
                        }

                        groundpathnavigation.m_26477_(this.f_25580_.getAsBoolean());
                        this.f_25575_ = this.f_25573_.m_21573_().m_26524_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, 0);
                        groundpathnavigation.m_26477_(flag);
                        if (this.f_25575_ == null) {
                           return false;
                        }
                     }

                     for(int i = 0; i < this.f_25575_.m_77398_(); ++i) {
                        Node node = this.f_25575_.m_77375_(i);
                        BlockPos blockpos1 = new BlockPos(node.f_77271_, node.f_77272_ + 1, node.f_77273_);
                        if (DoorBlock.m_52745_(this.f_25573_.f_19853_, blockpos1)) {
                           this.f_25575_ = this.f_25573_.m_21573_().m_26524_((double)node.f_77271_, (double)node.f_77272_, (double)node.f_77273_, 0);
                           break;
                        }
                     }

                     return this.f_25575_ != null;
                  }
               }
            }
         }
      }
   }

   public boolean m_8045_() {
      if (this.f_25573_.m_21573_().m_26571_()) {
         return false;
      } else {
         return !this.f_25576_.m_123306_(this.f_25573_.m_20182_(), (double)(this.f_25573_.m_20205_() + (float)this.f_25579_));
      }
   }

   public void m_8056_() {
      this.f_25573_.m_21573_().m_26536_(this.f_25575_, this.f_25574_);
   }

   public void m_8041_() {
      if (this.f_25573_.m_21573_().m_26571_() || this.f_25576_.m_123306_(this.f_25573_.m_20182_(), (double)this.f_25579_)) {
         this.f_25578_.add(this.f_25576_);
      }

   }

   private boolean m_25592_(BlockPos p_25593_) {
      for(BlockPos blockpos : this.f_25578_) {
         if (Objects.equals(p_25593_, blockpos)) {
            return false;
         }
      }

      return true;
   }

   private void m_25597_() {
      if (this.f_25578_.size() > 15) {
         this.f_25578_.remove(0);
      }

   }
}