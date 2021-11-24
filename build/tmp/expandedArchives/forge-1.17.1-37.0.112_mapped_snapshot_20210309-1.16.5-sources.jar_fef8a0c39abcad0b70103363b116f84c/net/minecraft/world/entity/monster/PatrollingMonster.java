package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public abstract class PatrollingMonster extends Monster {
   private BlockPos f_33042_;
   private boolean f_33043_;
   private boolean f_33044_;

   protected PatrollingMonster(EntityType<? extends PatrollingMonster> p_33046_, Level p_33047_) {
      super(p_33046_, p_33047_);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(4, new PatrollingMonster.LongDistancePatrolGoal<>(this, 0.7D, 0.595D));
   }

   public void m_7380_(CompoundTag p_33063_) {
      super.m_7380_(p_33063_);
      if (this.f_33042_ != null) {
         p_33063_.m_128365_("PatrolTarget", NbtUtils.m_129224_(this.f_33042_));
      }

      p_33063_.m_128379_("PatrolLeader", this.f_33043_);
      p_33063_.m_128379_("Patrolling", this.f_33044_);
   }

   public void m_7378_(CompoundTag p_33055_) {
      super.m_7378_(p_33055_);
      if (p_33055_.m_128441_("PatrolTarget")) {
         this.f_33042_ = NbtUtils.m_129239_(p_33055_.m_128469_("PatrolTarget"));
      }

      this.f_33043_ = p_33055_.m_128471_("PatrolLeader");
      this.f_33044_ = p_33055_.m_128471_("Patrolling");
   }

   public double m_6049_() {
      return -0.45D;
   }

   public boolean m_7490_() {
      return true;
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_33049_, DifficultyInstance p_33050_, MobSpawnType p_33051_, @Nullable SpawnGroupData p_33052_, @Nullable CompoundTag p_33053_) {
      if (p_33051_ != MobSpawnType.PATROL && p_33051_ != MobSpawnType.EVENT && p_33051_ != MobSpawnType.STRUCTURE && this.f_19796_.nextFloat() < 0.06F && this.m_7490_()) {
         this.f_33043_ = true;
      }

      if (this.m_33067_()) {
         this.m_8061_(EquipmentSlot.HEAD, Raid.m_37779_());
         this.m_21409_(EquipmentSlot.HEAD, 2.0F);
      }

      if (p_33051_ == MobSpawnType.PATROL) {
         this.f_33044_ = true;
      }

      return super.m_6518_(p_33049_, p_33050_, p_33051_, p_33052_, p_33053_);
   }

   public static boolean m_33056_(EntityType<? extends PatrollingMonster> p_33057_, LevelAccessor p_33058_, MobSpawnType p_33059_, BlockPos p_33060_, Random p_33061_) {
      return p_33058_.m_45517_(LightLayer.BLOCK, p_33060_) > 8 ? false : m_33023_(p_33057_, p_33058_, p_33059_, p_33060_, p_33061_);
   }

   public boolean m_6785_(double p_33073_) {
      return !this.f_33044_ || p_33073_ > 16384.0D;
   }

   public void m_33070_(BlockPos p_33071_) {
      this.f_33042_ = p_33071_;
      this.f_33044_ = true;
   }

   public BlockPos m_33065_() {
      return this.f_33042_;
   }

   public boolean m_33066_() {
      return this.f_33042_ != null;
   }

   public void m_33075_(boolean p_33076_) {
      this.f_33043_ = p_33076_;
      this.f_33044_ = true;
   }

   public boolean m_33067_() {
      return this.f_33043_;
   }

   public boolean m_7492_() {
      return true;
   }

   public void m_33068_() {
      this.f_33042_ = this.m_142538_().m_142082_(-500 + this.f_19796_.nextInt(1000), 0, -500 + this.f_19796_.nextInt(1000));
      this.f_33044_ = true;
   }

   protected boolean m_33069_() {
      return this.f_33044_;
   }

   protected void m_33077_(boolean p_33078_) {
      this.f_33044_ = p_33078_;
   }

   public static class LongDistancePatrolGoal<T extends PatrollingMonster> extends Goal {
      private static final int f_149720_ = 200;
      private final T f_33079_;
      private final double f_33080_;
      private final double f_33081_;
      private long f_33082_;

      public LongDistancePatrolGoal(T p_33084_, double p_33085_, double p_33086_) {
         this.f_33079_ = p_33084_;
         this.f_33080_ = p_33085_;
         this.f_33081_ = p_33086_;
         this.f_33082_ = -1L;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         boolean flag = this.f_33079_.f_19853_.m_46467_() < this.f_33082_;
         return this.f_33079_.m_33069_() && this.f_33079_.m_5448_() == null && !this.f_33079_.m_20160_() && this.f_33079_.m_33066_() && !flag;
      }

      public void m_8056_() {
      }

      public void m_8041_() {
      }

      public void m_8037_() {
         boolean flag = this.f_33079_.m_33067_();
         PathNavigation pathnavigation = this.f_33079_.m_21573_();
         if (pathnavigation.m_26571_()) {
            List<PatrollingMonster> list = this.m_33093_();
            if (this.f_33079_.m_33069_() && list.isEmpty()) {
               this.f_33079_.m_33077_(false);
            } else if (flag && this.f_33079_.m_33065_().m_123306_(this.f_33079_.m_20182_(), 10.0D)) {
               this.f_33079_.m_33068_();
            } else {
               Vec3 vec3 = Vec3.m_82539_(this.f_33079_.m_33065_());
               Vec3 vec31 = this.f_33079_.m_20182_();
               Vec3 vec32 = vec31.m_82546_(vec3);
               vec3 = vec32.m_82524_(90.0F).m_82490_(0.4D).m_82549_(vec3);
               Vec3 vec33 = vec3.m_82546_(vec31).m_82541_().m_82490_(10.0D).m_82549_(vec31);
               BlockPos blockpos = new BlockPos(vec33);
               blockpos = this.f_33079_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos);
               if (!pathnavigation.m_26519_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), flag ? this.f_33081_ : this.f_33080_)) {
                  this.m_33094_();
                  this.f_33082_ = this.f_33079_.f_19853_.m_46467_() + 200L;
               } else if (flag) {
                  for(PatrollingMonster patrollingmonster : list) {
                     patrollingmonster.m_33070_(blockpos);
                  }
               }
            }
         }

      }

      private List<PatrollingMonster> m_33093_() {
         return this.f_33079_.f_19853_.m_6443_(PatrollingMonster.class, this.f_33079_.m_142469_().m_82400_(16.0D), (p_33089_) -> {
            return p_33089_.m_7492_() && !p_33089_.m_7306_(this.f_33079_);
         });
      }

      private boolean m_33094_() {
         Random random = this.f_33079_.m_21187_();
         BlockPos blockpos = this.f_33079_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.f_33079_.m_142538_().m_142082_(-8 + random.nextInt(16), 0, -8 + random.nextInt(16)));
         return this.f_33079_.m_21573_().m_26519_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), this.f_33080_);
      }
   }
}