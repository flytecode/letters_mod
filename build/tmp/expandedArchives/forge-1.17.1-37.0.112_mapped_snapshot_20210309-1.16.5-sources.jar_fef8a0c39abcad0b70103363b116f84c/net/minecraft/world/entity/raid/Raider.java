package net.minecraft.world.entity.raid;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PathfindToRaidGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public abstract class Raider extends PatrollingMonster {
   protected static final EntityDataAccessor<Boolean> f_37835_ = SynchedEntityData.m_135353_(Raider.class, EntityDataSerializers.f_135035_);
   static final Predicate<ItemEntity> f_37831_ = (p_37872_) -> {
      return !p_37872_.m_32063_() && p_37872_.m_6084_() && ItemStack.m_41728_(p_37872_.m_32055_(), Raid.m_37779_());
   };
   @Nullable
   protected Raid f_37836_;
   private int f_37832_;
   private boolean f_37833_;
   private int f_37834_;

   protected Raider(EntityType<? extends Raider> p_37839_, Level p_37840_) {
      super(p_37839_, p_37840_);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new Raider.ObtainRaidLeaderBannerGoal<>(this));
      this.f_21345_.m_25352_(3, new PathfindToRaidGoal<>(this));
      this.f_21345_.m_25352_(4, new Raider.RaiderMoveThroughVillageGoal(this, (double)1.05F, 1));
      this.f_21345_.m_25352_(5, new Raider.RaiderCelebration(this));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_37835_, false);
   }

   public abstract void m_7895_(int p_37844_, boolean p_37845_);

   public boolean m_37882_() {
      return this.f_37833_;
   }

   public void m_37897_(boolean p_37898_) {
      this.f_37833_ = p_37898_;
   }

   public void m_8107_() {
      if (this.f_19853_ instanceof ServerLevel && this.m_6084_()) {
         Raid raid = this.m_37885_();
         if (this.m_37882_()) {
            if (raid == null) {
               if (this.f_19853_.m_46467_() % 20L == 0L) {
                  Raid raid1 = ((ServerLevel)this.f_19853_).m_8832_(this.m_142538_());
                  if (raid1 != null && Raids.m_37965_(this, raid1)) {
                     raid1.m_37713_(raid1.m_37771_(), this, (BlockPos)null, true);
                  }
               }
            } else {
               LivingEntity livingentity = this.m_5448_();
               if (livingentity != null && (livingentity.m_6095_() == EntityType.f_20532_ || livingentity.m_6095_() == EntityType.f_20460_)) {
                  this.f_20891_ = 0;
               }
            }
         }
      }

      super.m_8107_();
   }

   protected void m_7562_() {
      this.f_20891_ += 2;
   }

   public void m_6667_(DamageSource p_37847_) {
      if (this.f_19853_ instanceof ServerLevel) {
         Entity entity = p_37847_.m_7639_();
         Raid raid = this.m_37885_();
         if (raid != null) {
            if (this.m_33067_()) {
               raid.m_37758_(this.m_37887_());
            }

            if (entity != null && entity.m_6095_() == EntityType.f_20532_) {
               raid.m_37726_(entity);
            }

            raid.m_37740_(this, false);
         }

         if (this.m_33067_() && raid == null && ((ServerLevel)this.f_19853_).m_8832_(this.m_142538_()) == null) {
            ItemStack itemstack = this.m_6844_(EquipmentSlot.HEAD);
            Player player = null;
            if (entity instanceof Player) {
               player = (Player)entity;
            } else if (entity instanceof Wolf) {
               Wolf wolf = (Wolf)entity;
               LivingEntity livingentity = wolf.m_142480_();
               if (wolf.m_21824_() && livingentity instanceof Player) {
                  player = (Player)livingentity;
               }
            }

            if (!itemstack.m_41619_() && ItemStack.m_41728_(itemstack, Raid.m_37779_()) && player != null) {
               MobEffectInstance mobeffectinstance1 = player.m_21124_(MobEffects.f_19594_);
               int i = 1;
               if (mobeffectinstance1 != null) {
                  i += mobeffectinstance1.m_19564_();
                  player.m_6234_(MobEffects.f_19594_);
               } else {
                  --i;
               }

               i = Mth.m_14045_(i, 0, 4);
               MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.f_19594_, 120000, i, false, false, true);
               if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46154_)) {
                  player.m_7292_(mobeffectinstance);
               }
            }
         }
      }

      super.m_6667_(p_37847_);
   }

   public boolean m_7492_() {
      return !this.m_37886_();
   }

   public void m_37851_(@Nullable Raid p_37852_) {
      this.f_37836_ = p_37852_;
   }

   @Nullable
   public Raid m_37885_() {
      return this.f_37836_;
   }

   public boolean m_37886_() {
      return this.m_37885_() != null && this.m_37885_().m_37782_();
   }

   public void m_37842_(int p_37843_) {
      this.f_37832_ = p_37843_;
   }

   public int m_37887_() {
      return this.f_37832_;
   }

   public boolean m_37888_() {
      return this.f_19804_.m_135370_(f_37835_);
   }

   public void m_37899_(boolean p_37900_) {
      this.f_19804_.m_135381_(f_37835_, p_37900_);
   }

   public void m_7380_(CompoundTag p_37870_) {
      super.m_7380_(p_37870_);
      p_37870_.m_128405_("Wave", this.f_37832_);
      p_37870_.m_128379_("CanJoinRaid", this.f_37833_);
      if (this.f_37836_ != null) {
         p_37870_.m_128405_("RaidId", this.f_37836_.m_37781_());
      }

   }

   public void m_7378_(CompoundTag p_37862_) {
      super.m_7378_(p_37862_);
      this.f_37832_ = p_37862_.m_128451_("Wave");
      this.f_37833_ = p_37862_.m_128471_("CanJoinRaid");
      if (p_37862_.m_128425_("RaidId", 3)) {
         if (this.f_19853_ instanceof ServerLevel) {
            this.f_37836_ = ((ServerLevel)this.f_19853_).m_8905_().m_37958_(p_37862_.m_128451_("RaidId"));
         }

         if (this.f_37836_ != null) {
            this.f_37836_.m_37718_(this.f_37832_, this, false);
            if (this.m_33067_()) {
               this.f_37836_.m_37710_(this.f_37832_, this);
            }
         }
      }

   }

   protected void m_7581_(ItemEntity p_37866_) {
      ItemStack itemstack = p_37866_.m_32055_();
      boolean flag = this.m_37886_() && this.m_37885_().m_37750_(this.m_37887_()) != null;
      if (this.m_37886_() && !flag && ItemStack.m_41728_(itemstack, Raid.m_37779_())) {
         EquipmentSlot equipmentslot = EquipmentSlot.HEAD;
         ItemStack itemstack1 = this.m_6844_(equipmentslot);
         double d0 = (double)this.m_21519_(equipmentslot);
         if (!itemstack1.m_41619_() && (double)Math.max(this.f_19796_.nextFloat() - 0.1F, 0.0F) < d0) {
            this.m_19983_(itemstack1);
         }

         this.m_21053_(p_37866_);
         this.m_8061_(equipmentslot, itemstack);
         this.m_7938_(p_37866_, itemstack.m_41613_());
         p_37866_.m_146870_();
         this.m_37885_().m_37710_(this.m_37887_(), this);
         this.m_33075_(true);
      } else {
         super.m_7581_(p_37866_);
      }

   }

   public boolean m_6785_(double p_37894_) {
      return this.m_37885_() == null ? super.m_6785_(p_37894_) : false;
   }

   public boolean m_8023_() {
      return super.m_8023_() || this.m_37885_() != null;
   }

   public int m_37889_() {
      return this.f_37834_;
   }

   public void m_37863_(int p_37864_) {
      this.f_37834_ = p_37864_;
   }

   public boolean m_6469_(DamageSource p_37849_, float p_37850_) {
      if (this.m_37886_()) {
         this.m_37885_().m_37776_();
      }

      return super.m_6469_(p_37849_, p_37850_);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_37856_, DifficultyInstance p_37857_, MobSpawnType p_37858_, @Nullable SpawnGroupData p_37859_, @Nullable CompoundTag p_37860_) {
      this.m_37897_(this.m_6095_() != EntityType.f_20495_ || p_37858_ != MobSpawnType.NATURAL);
      return super.m_6518_(p_37856_, p_37857_, p_37858_, p_37859_, p_37860_);
   }

   public abstract SoundEvent m_7930_();

   protected class HoldGroundAttackGoal extends Goal {
      private final Raider f_37903_;
      private final float f_37904_;
      public final TargetingConditions f_37901_ = TargetingConditions.m_148353_().m_26883_(8.0D).m_148355_().m_26893_();

      public HoldGroundAttackGoal(AbstractIllager p_37907_, float p_37908_) {
         this.f_37903_ = p_37907_;
         this.f_37904_ = p_37908_ * p_37908_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity livingentity = this.f_37903_.m_142581_();
         return this.f_37903_.m_37885_() == null && this.f_37903_.m_33069_() && this.f_37903_.m_5448_() != null && !this.f_37903_.m_5912_() && (livingentity == null || livingentity.m_6095_() != EntityType.f_20532_);
      }

      public void m_8056_() {
         super.m_8056_();
         this.f_37903_.m_21573_().m_26573_();

         for(Raider raider : this.f_37903_.f_19853_.m_45971_(Raider.class, this.f_37901_, this.f_37903_, this.f_37903_.m_142469_().m_82377_(8.0D, 8.0D, 8.0D))) {
            raider.m_6710_(this.f_37903_.m_5448_());
         }

      }

      public void m_8041_() {
         super.m_8041_();
         LivingEntity livingentity = this.f_37903_.m_5448_();
         if (livingentity != null) {
            for(Raider raider : this.f_37903_.f_19853_.m_45971_(Raider.class, this.f_37901_, this.f_37903_, this.f_37903_.m_142469_().m_82377_(8.0D, 8.0D, 8.0D))) {
               raider.m_6710_(livingentity);
               raider.m_21561_(true);
            }

            this.f_37903_.m_21561_(true);
         }

      }

      public void m_8037_() {
         LivingEntity livingentity = this.f_37903_.m_5448_();
         if (livingentity != null) {
            if (this.f_37903_.m_20280_(livingentity) > (double)this.f_37904_) {
               this.f_37903_.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
               if (this.f_37903_.f_19796_.nextInt(50) == 0) {
                  this.f_37903_.m_8032_();
               }
            } else {
               this.f_37903_.m_21561_(true);
            }

            super.m_8037_();
         }
      }
   }

   public class ObtainRaidLeaderBannerGoal<T extends Raider> extends Goal {
      private final T f_37914_;

      public ObtainRaidLeaderBannerGoal(T p_37917_) {
         this.f_37914_ = p_37917_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         Raid raid = this.f_37914_.m_37885_();
         if (this.f_37914_.m_37886_() && !this.f_37914_.m_37885_().m_37706_() && this.f_37914_.m_7490_() && !ItemStack.m_41728_(this.f_37914_.m_6844_(EquipmentSlot.HEAD), Raid.m_37779_())) {
            Raider raider = raid.m_37750_(this.f_37914_.m_37887_());
            if (raider == null || !raider.m_6084_()) {
               List<ItemEntity> list = this.f_37914_.f_19853_.m_6443_(ItemEntity.class, this.f_37914_.m_142469_().m_82377_(16.0D, 8.0D, 16.0D), Raider.f_37831_);
               if (!list.isEmpty()) {
                  return this.f_37914_.m_21573_().m_5624_(list.get(0), (double)1.15F);
               }
            }

            return false;
         } else {
            return false;
         }
      }

      public void m_8037_() {
         if (this.f_37914_.m_21573_().m_26567_().m_123306_(this.f_37914_.m_20182_(), 1.414D)) {
            List<ItemEntity> list = this.f_37914_.f_19853_.m_6443_(ItemEntity.class, this.f_37914_.m_142469_().m_82377_(4.0D, 4.0D, 4.0D), Raider.f_37831_);
            if (!list.isEmpty()) {
               this.f_37914_.m_7581_(list.get(0));
            }
         }

      }
   }

   public class RaiderCelebration extends Goal {
      private final Raider f_37921_;

      RaiderCelebration(Raider p_37924_) {
         this.f_37921_ = p_37924_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         Raid raid = this.f_37921_.m_37885_();
         return this.f_37921_.m_6084_() && this.f_37921_.m_5448_() == null && raid != null && raid.m_37768_();
      }

      public void m_8056_() {
         this.f_37921_.m_37899_(true);
         super.m_8056_();
      }

      public void m_8041_() {
         this.f_37921_.m_37899_(false);
         super.m_8041_();
      }

      public void m_8037_() {
         if (!this.f_37921_.m_20067_() && this.f_37921_.f_19796_.nextInt(100) == 0) {
            Raider.this.m_5496_(Raider.this.m_7930_(), Raider.this.m_6121_(), Raider.this.m_6100_());
         }

         if (!this.f_37921_.m_20159_() && this.f_37921_.f_19796_.nextInt(50) == 0) {
            this.f_37921_.m_21569_().m_24901_();
         }

         super.m_8037_();
      }
   }

   static class RaiderMoveThroughVillageGoal extends Goal {
      private final Raider f_37929_;
      private final double f_37930_;
      private BlockPos f_37931_;
      private final List<BlockPos> f_37932_ = Lists.newArrayList();
      private final int f_37933_;
      private boolean f_37934_;

      public RaiderMoveThroughVillageGoal(Raider p_37936_, double p_37937_, int p_37938_) {
         this.f_37929_ = p_37936_;
         this.f_37930_ = p_37937_;
         this.f_37933_ = p_37938_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         this.m_37950_();
         return this.m_37948_() && this.m_37949_() && this.f_37929_.m_5448_() == null;
      }

      private boolean m_37948_() {
         return this.f_37929_.m_37886_() && !this.f_37929_.m_37885_().m_37706_();
      }

      private boolean m_37949_() {
         ServerLevel serverlevel = (ServerLevel)this.f_37929_.f_19853_;
         BlockPos blockpos = this.f_37929_.m_142538_();
         Optional<BlockPos> optional = serverlevel.m_8904_().m_27126_((p_37941_) -> {
            return p_37941_ == PoiType.f_27346_;
         }, this::m_37942_, PoiManager.Occupancy.ANY, blockpos, 48, this.f_37929_.f_19796_);
         if (!optional.isPresent()) {
            return false;
         } else {
            this.f_37931_ = optional.get().m_7949_();
            return true;
         }
      }

      public boolean m_8045_() {
         if (this.f_37929_.m_21573_().m_26571_()) {
            return false;
         } else {
            return this.f_37929_.m_5448_() == null && !this.f_37931_.m_123306_(this.f_37929_.m_20182_(), (double)(this.f_37929_.m_20205_() + (float)this.f_37933_)) && !this.f_37934_;
         }
      }

      public void m_8041_() {
         if (this.f_37931_.m_123306_(this.f_37929_.m_20182_(), (double)this.f_37933_)) {
            this.f_37932_.add(this.f_37931_);
         }

      }

      public void m_8056_() {
         super.m_8056_();
         this.f_37929_.m_21310_(0);
         this.f_37929_.m_21573_().m_26519_((double)this.f_37931_.m_123341_(), (double)this.f_37931_.m_123342_(), (double)this.f_37931_.m_123343_(), this.f_37930_);
         this.f_37934_ = false;
      }

      public void m_8037_() {
         if (this.f_37929_.m_21573_().m_26571_()) {
            Vec3 vec3 = Vec3.m_82539_(this.f_37931_);
            Vec3 vec31 = DefaultRandomPos.m_148412_(this.f_37929_, 16, 7, vec3, (double)((float)Math.PI / 10F));
            if (vec31 == null) {
               vec31 = DefaultRandomPos.m_148412_(this.f_37929_, 8, 7, vec3, (double)((float)Math.PI / 2F));
            }

            if (vec31 == null) {
               this.f_37934_ = true;
               return;
            }

            this.f_37929_.m_21573_().m_26519_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, this.f_37930_);
         }

      }

      private boolean m_37942_(BlockPos p_37943_) {
         for(BlockPos blockpos : this.f_37932_) {
            if (Objects.equals(p_37943_, blockpos)) {
               return false;
            }
         }

         return true;
      }

      private void m_37950_() {
         if (this.f_37932_.size() > 2) {
            this.f_37932_.remove(0);
         }

      }
   }
}