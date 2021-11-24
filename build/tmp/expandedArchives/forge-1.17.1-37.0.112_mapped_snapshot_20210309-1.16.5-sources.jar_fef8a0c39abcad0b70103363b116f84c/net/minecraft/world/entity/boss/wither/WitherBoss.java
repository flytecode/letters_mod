package net.minecraft.world.entity.boss.wither;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WitherBoss extends Monster implements PowerableMob, RangedAttackMob {
   private static final EntityDataAccessor<Integer> f_31420_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_31433_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_31434_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
   private static final List<EntityDataAccessor<Integer>> f_31421_ = ImmutableList.of(f_31420_, f_31433_, f_31434_);
   private static final EntityDataAccessor<Integer> f_31422_ = SynchedEntityData.m_135353_(WitherBoss.class, EntityDataSerializers.f_135028_);
   private static final int f_149587_ = 220;
   private final float[] f_31423_ = new float[2];
   private final float[] f_31424_ = new float[2];
   private final float[] f_31425_ = new float[2];
   private final float[] f_31426_ = new float[2];
   private final int[] f_31427_ = new int[2];
   private final int[] f_31428_ = new int[2];
   private int f_31429_;
   private final ServerBossEvent f_31430_ = (ServerBossEvent)(new ServerBossEvent(this.m_5446_(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).m_7003_(true);
   private static final Predicate<LivingEntity> f_31431_ = (p_31504_) -> {
      return p_31504_.m_6336_() != MobType.f_21641_ && p_31504_.m_5789_();
   };
   private static final TargetingConditions f_31432_ = TargetingConditions.m_148352_().m_26883_(20.0D).m_26888_(f_31431_);

   public WitherBoss(EntityType<? extends WitherBoss> p_31437_, Level p_31438_) {
      super(p_31437_, p_31438_);
      this.m_21153_(this.m_21233_());
      this.m_21573_().m_7008_(true);
      this.f_21364_ = 50;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new WitherBoss.WitherDoNothingGoal());
      this.f_21345_.m_25352_(2, new RangedAttackGoal(this, 1.0D, 40, 20.0F));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Mob.class, 0, false, false, f_31431_));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_31420_, 0);
      this.f_19804_.m_135372_(f_31433_, 0);
      this.f_19804_.m_135372_(f_31434_, 0);
      this.f_19804_.m_135372_(f_31422_, 0);
   }

   public void m_7380_(CompoundTag p_31485_) {
      super.m_7380_(p_31485_);
      p_31485_.m_128405_("Invul", this.m_31502_());
   }

   public void m_7378_(CompoundTag p_31474_) {
      super.m_7378_(p_31474_);
      this.m_31510_(p_31474_.m_128451_("Invul"));
      if (this.m_8077_()) {
         this.f_31430_.m_6456_(this.m_5446_());
      }

   }

   public void m_6593_(@Nullable Component p_31476_) {
      super.m_6593_(p_31476_);
      this.f_31430_.m_6456_(this.m_5446_());
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12554_;
   }

   protected SoundEvent m_7975_(DamageSource p_31500_) {
      return SoundEvents.f_12557_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12556_;
   }

   public void m_8107_() {
      Vec3 vec3 = this.m_20184_().m_82542_(1.0D, 0.6D, 1.0D);
      if (!this.f_19853_.f_46443_ && this.m_31512_(0) > 0) {
         Entity entity = this.f_19853_.m_6815_(this.m_31512_(0));
         if (entity != null) {
            double d0 = vec3.f_82480_;
            if (this.m_20186_() < entity.m_20186_() || !this.m_7090_() && this.m_20186_() < entity.m_20186_() + 5.0D) {
               d0 = Math.max(0.0D, d0);
               d0 = d0 + (0.3D - d0 * (double)0.6F);
            }

            vec3 = new Vec3(vec3.f_82479_, d0, vec3.f_82481_);
            Vec3 vec31 = new Vec3(entity.m_20185_() - this.m_20185_(), 0.0D, entity.m_20189_() - this.m_20189_());
            if (vec31.m_165925_() > 9.0D) {
               Vec3 vec32 = vec31.m_82541_();
               vec3 = vec3.m_82520_(vec32.f_82479_ * 0.3D - vec3.f_82479_ * 0.6D, 0.0D, vec32.f_82481_ * 0.3D - vec3.f_82481_ * 0.6D);
            }
         }
      }

      this.m_20256_(vec3);
      if (vec3.m_165925_() > 0.05D) {
         this.m_146922_((float)Mth.m_14136_(vec3.f_82481_, vec3.f_82479_) * (180F / (float)Math.PI) - 90.0F);
      }

      super.m_8107_();

      for(int i = 0; i < 2; ++i) {
         this.f_31426_[i] = this.f_31424_[i];
         this.f_31425_[i] = this.f_31423_[i];
      }

      for(int j = 0; j < 2; ++j) {
         int k = this.m_31512_(j + 1);
         Entity entity1 = null;
         if (k > 0) {
            entity1 = this.f_19853_.m_6815_(k);
         }

         if (entity1 != null) {
            double d9 = this.m_31514_(j + 1);
            double d1 = this.m_31516_(j + 1);
            double d3 = this.m_31518_(j + 1);
            double d4 = entity1.m_20185_() - d9;
            double d5 = entity1.m_20188_() - d1;
            double d6 = entity1.m_20189_() - d3;
            double d7 = Math.sqrt(d4 * d4 + d6 * d6);
            float f = (float)(Mth.m_14136_(d6, d4) * (double)(180F / (float)Math.PI)) - 90.0F;
            float f1 = (float)(-(Mth.m_14136_(d5, d7) * (double)(180F / (float)Math.PI)));
            this.f_31423_[j] = this.m_31442_(this.f_31423_[j], f1, 40.0F);
            this.f_31424_[j] = this.m_31442_(this.f_31424_[j], f, 10.0F);
         } else {
            this.f_31424_[j] = this.m_31442_(this.f_31424_[j], this.f_20883_, 10.0F);
         }
      }

      boolean flag = this.m_7090_();

      for(int l = 0; l < 3; ++l) {
         double d8 = this.m_31514_(l);
         double d10 = this.m_31516_(l);
         double d2 = this.m_31518_(l);
         this.f_19853_.m_7106_(ParticleTypes.f_123762_, d8 + this.f_19796_.nextGaussian() * (double)0.3F, d10 + this.f_19796_.nextGaussian() * (double)0.3F, d2 + this.f_19796_.nextGaussian() * (double)0.3F, 0.0D, 0.0D, 0.0D);
         if (flag && this.f_19853_.f_46441_.nextInt(4) == 0) {
            this.f_19853_.m_7106_(ParticleTypes.f_123811_, d8 + this.f_19796_.nextGaussian() * (double)0.3F, d10 + this.f_19796_.nextGaussian() * (double)0.3F, d2 + this.f_19796_.nextGaussian() * (double)0.3F, (double)0.7F, (double)0.7F, 0.5D);
         }
      }

      if (this.m_31502_() > 0) {
         for(int i1 = 0; i1 < 3; ++i1) {
            this.f_19853_.m_7106_(ParticleTypes.f_123811_, this.m_20185_() + this.f_19796_.nextGaussian(), this.m_20186_() + (double)(this.f_19796_.nextFloat() * 3.3F), this.m_20189_() + this.f_19796_.nextGaussian(), (double)0.7F, (double)0.7F, (double)0.9F);
         }
      }

   }

   protected void m_8024_() {
      if (this.m_31502_() > 0) {
         int k1 = this.m_31502_() - 1;
         this.f_31430_.m_142711_(1.0F - (float)k1 / 220.0F);
         if (k1 <= 0) {
            Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20188_(), this.m_20189_(), 7.0F, false, explosion$blockinteraction);
            if (!this.m_20067_()) {
               this.f_19853_.m_6798_(1023, this.m_142538_(), 0);
            }
         }

         this.m_31510_(k1);
         if (this.f_19797_ % 10 == 0) {
            this.m_5634_(10.0F);
         }

      } else {
         super.m_8024_();

         for(int i = 1; i < 3; ++i) {
            if (this.f_19797_ >= this.f_31427_[i - 1]) {
               this.f_31427_[i - 1] = this.f_19797_ + 10 + this.f_19796_.nextInt(10);
               if (this.f_19853_.m_46791_() == Difficulty.NORMAL || this.f_19853_.m_46791_() == Difficulty.HARD) {
                  int i3 = i - 1;
                  int j3 = this.f_31428_[i - 1];
                  this.f_31428_[i3] = this.f_31428_[i - 1] + 1;
                  if (j3 > 15) {
                     float f = 10.0F;
                     float f1 = 5.0F;
                     double d0 = Mth.m_14064_(this.f_19796_, this.m_20185_() - 10.0D, this.m_20185_() + 10.0D);
                     double d1 = Mth.m_14064_(this.f_19796_, this.m_20186_() - 5.0D, this.m_20186_() + 5.0D);
                     double d2 = Mth.m_14064_(this.f_19796_, this.m_20189_() - 10.0D, this.m_20189_() + 10.0D);
                     this.m_31448_(i + 1, d0, d1, d2, true);
                     this.f_31428_[i - 1] = 0;
                  }
               }

               int l1 = this.m_31512_(i);
               if (l1 > 0) {
                  LivingEntity livingentity = (LivingEntity)this.f_19853_.m_6815_(l1);
                  if (livingentity != null && this.m_6779_(livingentity) && !(this.m_20280_(livingentity) > 900.0D) && this.m_142582_(livingentity)) {
                     this.m_31457_(i + 1, livingentity);
                     this.f_31427_[i - 1] = this.f_19797_ + 40 + this.f_19796_.nextInt(20);
                     this.f_31428_[i - 1] = 0;
                  } else {
                     this.m_31454_(i, 0);
                  }
               } else {
                  List<LivingEntity> list = this.f_19853_.m_45971_(LivingEntity.class, f_31432_, this, this.m_142469_().m_82377_(20.0D, 8.0D, 20.0D));
                  if (!list.isEmpty()) {
                     LivingEntity livingentity1 = list.get(this.f_19796_.nextInt(list.size()));
                     this.m_31454_(i, livingentity1.m_142049_());
                  }
               }
            }
         }

         if (this.m_5448_() != null) {
            this.m_31454_(0, this.m_5448_().m_142049_());
         } else {
            this.m_31454_(0, 0);
         }

         if (this.f_31429_ > 0) {
            --this.f_31429_;
            if (this.f_31429_ == 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
               int j1 = Mth.m_14107_(this.m_20186_());
               int i2 = Mth.m_14107_(this.m_20185_());
               int j2 = Mth.m_14107_(this.m_20189_());
               boolean flag = false;

               for(int j = -1; j <= 1; ++j) {
                  for(int k2 = -1; k2 <= 1; ++k2) {
                     for(int k = 0; k <= 3; ++k) {
                        int l2 = i2 + j;
                        int l = j1 + k;
                        int i1 = j2 + k2;
                        BlockPos blockpos = new BlockPos(l2, l, i1);
                        BlockState blockstate = this.f_19853_.m_8055_(blockpos);
                        if (blockstate.canEntityDestroy(this.f_19853_, blockpos, this) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                           flag = this.f_19853_.m_46953_(blockpos, true, this) || flag;
                        }
                     }
                  }
               }

               if (flag) {
                  this.f_19853_.m_5898_((Player)null, 1022, this.m_142538_(), 0);
               }
            }
         }

         if (this.f_19797_ % 20 == 0) {
            this.m_5634_(1.0F);
         }

         this.f_31430_.m_142711_(this.m_21223_() / this.m_21233_());
      }
   }

   @Deprecated //Forge: DO NOT USE use BlockState.canEntityDestroy
   public static boolean m_31491_(BlockState p_31492_) {
      return !p_31492_.m_60795_() && !p_31492_.m_60620_(BlockTags.f_13070_);
   }

   public void m_31506_() {
      this.m_31510_(220);
      this.f_31430_.m_142711_(0.0F);
      this.m_21153_(this.m_21233_() / 3.0F);
   }

   public void m_7601_(BlockState p_31471_, Vec3 p_31472_) {
   }

   public void m_6457_(ServerPlayer p_31483_) {
      super.m_6457_(p_31483_);
      this.f_31430_.m_6543_(p_31483_);
   }

   public void m_6452_(ServerPlayer p_31488_) {
      super.m_6452_(p_31488_);
      this.f_31430_.m_6539_(p_31488_);
   }

   private double m_31514_(int p_31515_) {
      if (p_31515_ <= 0) {
         return this.m_20185_();
      } else {
         float f = (this.f_20883_ + (float)(180 * (p_31515_ - 1))) * ((float)Math.PI / 180F);
         float f1 = Mth.m_14089_(f);
         return this.m_20185_() + (double)f1 * 1.3D;
      }
   }

   private double m_31516_(int p_31517_) {
      return p_31517_ <= 0 ? this.m_20186_() + 3.0D : this.m_20186_() + 2.2D;
   }

   private double m_31518_(int p_31519_) {
      if (p_31519_ <= 0) {
         return this.m_20189_();
      } else {
         float f = (this.f_20883_ + (float)(180 * (p_31519_ - 1))) * ((float)Math.PI / 180F);
         float f1 = Mth.m_14031_(f);
         return this.m_20189_() + (double)f1 * 1.3D;
      }
   }

   private float m_31442_(float p_31443_, float p_31444_, float p_31445_) {
      float f = Mth.m_14177_(p_31444_ - p_31443_);
      if (f > p_31445_) {
         f = p_31445_;
      }

      if (f < -p_31445_) {
         f = -p_31445_;
      }

      return p_31443_ + f;
   }

   private void m_31457_(int p_31458_, LivingEntity p_31459_) {
      this.m_31448_(p_31458_, p_31459_.m_20185_(), p_31459_.m_20186_() + (double)p_31459_.m_20192_() * 0.5D, p_31459_.m_20189_(), p_31458_ == 0 && this.f_19796_.nextFloat() < 0.001F);
   }

   private void m_31448_(int p_31449_, double p_31450_, double p_31451_, double p_31452_, boolean p_31453_) {
      if (!this.m_20067_()) {
         this.f_19853_.m_5898_((Player)null, 1024, this.m_142538_(), 0);
      }

      double d0 = this.m_31514_(p_31449_);
      double d1 = this.m_31516_(p_31449_);
      double d2 = this.m_31518_(p_31449_);
      double d3 = p_31450_ - d0;
      double d4 = p_31451_ - d1;
      double d5 = p_31452_ - d2;
      WitherSkull witherskull = new WitherSkull(this.f_19853_, this, d3, d4, d5);
      witherskull.m_5602_(this);
      if (p_31453_) {
         witherskull.m_37629_(true);
      }

      witherskull.m_20343_(d0, d1, d2);
      this.f_19853_.m_7967_(witherskull);
   }

   public void m_6504_(LivingEntity p_31468_, float p_31469_) {
      this.m_31457_(0, p_31468_);
   }

   public boolean m_6469_(DamageSource p_31461_, float p_31462_) {
      if (this.m_6673_(p_31461_)) {
         return false;
      } else if (p_31461_ != DamageSource.f_19312_ && !(p_31461_.m_7639_() instanceof WitherBoss)) {
         if (this.m_31502_() > 0 && p_31461_ != DamageSource.f_19317_) {
            return false;
         } else {
            if (this.m_7090_()) {
               Entity entity = p_31461_.m_7640_();
               if (entity instanceof AbstractArrow) {
                  return false;
               }
            }

            Entity entity1 = p_31461_.m_7639_();
            if (entity1 != null && !(entity1 instanceof Player) && entity1 instanceof LivingEntity && ((LivingEntity)entity1).m_6336_() == this.m_6336_()) {
               return false;
            } else {
               if (this.f_31429_ <= 0) {
                  this.f_31429_ = 20;
               }

               for(int i = 0; i < this.f_31428_.length; ++i) {
                  this.f_31428_[i] += 3;
               }

               return super.m_6469_(p_31461_, p_31462_);
            }
         }
      } else {
         return false;
      }
   }

   protected void m_7472_(DamageSource p_31464_, int p_31465_, boolean p_31466_) {
      super.m_7472_(p_31464_, p_31465_, p_31466_);
      ItemEntity itementity = this.m_19998_(Items.f_42686_);
      if (itementity != null) {
         itementity.m_32064_();
      }

   }

   public void m_6043_() {
      if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
         this.m_146870_();
      } else {
         this.f_20891_ = 0;
      }
   }

   public boolean m_142535_(float p_149589_, float p_149590_, DamageSource p_149591_) {
      return false;
   }

   public boolean m_147207_(MobEffectInstance p_182397_, @Nullable Entity p_182398_) {
      return false;
   }

   public static AttributeSupplier.Builder m_31501_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 300.0D).m_22268_(Attributes.f_22279_, (double)0.6F).m_22268_(Attributes.f_22277_, 40.0D).m_22268_(Attributes.f_22284_, 4.0D);
   }

   public float m_31446_(int p_31447_) {
      return this.f_31424_[p_31447_];
   }

   public float m_31480_(int p_31481_) {
      return this.f_31423_[p_31481_];
   }

   public int m_31502_() {
      return this.f_19804_.m_135370_(f_31422_);
   }

   public void m_31510_(int p_31511_) {
      this.f_19804_.m_135381_(f_31422_, p_31511_);
   }

   public int m_31512_(int p_31513_) {
      return this.f_19804_.m_135370_(f_31421_.get(p_31513_));
   }

   public void m_31454_(int p_31455_, int p_31456_) {
      this.f_19804_.m_135381_(f_31421_.get(p_31455_), p_31456_);
   }

   public boolean m_7090_() {
      return this.m_21223_() <= this.m_21233_() / 2.0F;
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }

   public boolean m_6072_() {
      return false;
   }

   public boolean m_7301_(MobEffectInstance p_31495_) {
      return p_31495_.m_19544_() == MobEffects.f_19615_ ? false : super.m_7301_(p_31495_);
   }

   class WitherDoNothingGoal extends Goal {
      public WitherDoNothingGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         return WitherBoss.this.m_31502_() > 0;
      }
   }
}
