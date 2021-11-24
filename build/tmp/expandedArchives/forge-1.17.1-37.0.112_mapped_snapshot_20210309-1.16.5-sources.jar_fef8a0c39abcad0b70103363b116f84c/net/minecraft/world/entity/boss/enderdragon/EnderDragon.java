package net.minecraft.world.entity.boss.enderdragon;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BinaryHeap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragon extends Mob implements Enemy {
   private static final Logger f_31087_ = LogManager.getLogger();
   public static final EntityDataAccessor<Integer> f_31067_ = SynchedEntityData.m_135353_(EnderDragon.class, EntityDataSerializers.f_135028_);
   private static final TargetingConditions f_31088_ = TargetingConditions.m_148352_().m_26883_(64.0D);
   private static final int f_149566_ = 200;
   private static final int f_149567_ = 400;
   private static final float f_149568_ = 0.25F;
   private static final String f_149569_ = "DragonDeathTime";
   private static final String f_149570_ = "DragonPhase";
   public final double[][] f_31092_ = new double[64][3];
   public int f_31093_ = -1;
   private final EnderDragonPart[] f_31089_;
   public final EnderDragonPart f_31080_;
   private final EnderDragonPart f_31090_;
   private final EnderDragonPart f_31091_;
   private final EnderDragonPart f_31068_;
   private final EnderDragonPart f_31069_;
   private final EnderDragonPart f_31070_;
   private final EnderDragonPart f_31071_;
   private final EnderDragonPart f_31072_;
   public float f_31081_;
   public float f_31082_;
   public boolean f_31083_;
   public int f_31084_;
   public float f_31085_;
   @Nullable
   public EndCrystal f_31086_;
   @Nullable
   private final EndDragonFight f_31073_;
   private final EnderDragonPhaseManager f_31074_;
   private int f_31075_ = 100;
   private int f_31076_;
   private final Node[] f_31077_ = new Node[24];
   private final int[] f_31078_ = new int[24];
   private final BinaryHeap f_31079_ = new BinaryHeap();

   public EnderDragon(EntityType<? extends EnderDragon> p_31096_, Level p_31097_) {
      super(EntityType.f_20565_, p_31097_);
      this.f_31080_ = new EnderDragonPart(this, "head", 1.0F, 1.0F);
      this.f_31090_ = new EnderDragonPart(this, "neck", 3.0F, 3.0F);
      this.f_31091_ = new EnderDragonPart(this, "body", 5.0F, 3.0F);
      this.f_31068_ = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.f_31069_ = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.f_31070_ = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.f_31071_ = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
      this.f_31072_ = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
      this.f_31089_ = new EnderDragonPart[]{this.f_31080_, this.f_31090_, this.f_31091_, this.f_31068_, this.f_31069_, this.f_31070_, this.f_31071_, this.f_31072_};
      this.m_21153_(this.m_21233_());
      this.f_19794_ = true;
      this.f_19811_ = true;
      if (p_31097_ instanceof ServerLevel) {
         this.f_31073_ = ((ServerLevel)p_31097_).m_8586_();
      } else {
         this.f_31073_ = null;
      }

      this.f_31074_ = new EnderDragonPhaseManager(this);
   }

   public static AttributeSupplier.Builder m_31167_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 200.0D);
   }

   public boolean m_142039_() {
      float f = Mth.m_14089_(this.f_31082_ * ((float)Math.PI * 2F));
      float f1 = Mth.m_14089_(this.f_31081_ * ((float)Math.PI * 2F));
      return f1 <= -0.3F && f >= -0.3F;
   }

   public void m_142043_() {
      if (this.f_19853_.f_46443_ && !this.m_20067_()) {
         this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11893_, this.m_5720_(), 5.0F, 0.8F + this.f_19796_.nextFloat() * 0.3F, false);
      }

   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(f_31067_, EnderDragonPhase.f_31387_.m_31405_());
   }

   public double[] m_31101_(int p_31102_, float p_31103_) {
      if (this.m_21224_()) {
         p_31103_ = 0.0F;
      }

      p_31103_ = 1.0F - p_31103_;
      int i = this.f_31093_ - p_31102_ & 63;
      int j = this.f_31093_ - p_31102_ - 1 & 63;
      double[] adouble = new double[3];
      double d0 = this.f_31092_[i][0];
      double d1 = Mth.m_14175_(this.f_31092_[j][0] - d0);
      adouble[0] = d0 + d1 * (double)p_31103_;
      d0 = this.f_31092_[i][1];
      d1 = this.f_31092_[j][1] - d0;
      adouble[1] = d0 + d1 * (double)p_31103_;
      adouble[2] = Mth.m_14139_((double)p_31103_, this.f_31092_[i][2], this.f_31092_[j][2]);
      return adouble;
   }

   public void m_8107_() {
      this.m_146874_();
      if (this.f_19853_.f_46443_) {
         this.m_21153_(this.m_21223_());
         if (!this.m_20067_() && !this.f_31074_.m_31415_().m_7080_() && --this.f_31075_ < 0) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11894_, this.m_5720_(), 2.5F, 0.8F + this.f_19796_.nextFloat() * 0.3F, false);
            this.f_31075_ = 200 + this.f_19796_.nextInt(200);
         }
      }

      this.f_31081_ = this.f_31082_;
      if (this.m_21224_()) {
         float f8 = (this.f_19796_.nextFloat() - 0.5F) * 8.0F;
         float f9 = (this.f_19796_.nextFloat() - 0.5F) * 4.0F;
         float f10 = (this.f_19796_.nextFloat() - 0.5F) * 8.0F;
         this.f_19853_.m_7106_(ParticleTypes.f_123813_, this.m_20185_() + (double)f8, this.m_20186_() + 2.0D + (double)f9, this.m_20189_() + (double)f10, 0.0D, 0.0D, 0.0D);
      } else {
         this.m_31160_();
         Vec3 vec3 = this.m_20184_();
         float f = 0.2F / ((float)vec3.m_165924_() * 10.0F + 1.0F);
         f = f * (float)Math.pow(2.0D, vec3.f_82480_);
         if (this.f_31074_.m_31415_().m_7080_()) {
            this.f_31082_ += 0.1F;
         } else if (this.f_31083_) {
            this.f_31082_ += f * 0.5F;
         } else {
            this.f_31082_ += f;
         }

         this.m_146922_(Mth.m_14177_(this.m_146908_()));
         if (this.m_21525_()) {
            this.f_31082_ = 0.5F;
         } else {
            if (this.f_31093_ < 0) {
               for(int i = 0; i < this.f_31092_.length; ++i) {
                  this.f_31092_[i][0] = (double)this.m_146908_();
                  this.f_31092_[i][1] = this.m_20186_();
               }
            }

            if (++this.f_31093_ == this.f_31092_.length) {
               this.f_31093_ = 0;
            }

            this.f_31092_[this.f_31093_][0] = (double)this.m_146908_();
            this.f_31092_[this.f_31093_][1] = this.m_20186_();
            if (this.f_19853_.f_46443_) {
               if (this.f_20903_ > 0) {
                  double d7 = this.m_20185_() + (this.f_20904_ - this.m_20185_()) / (double)this.f_20903_;
                  double d0 = this.m_20186_() + (this.f_20905_ - this.m_20186_()) / (double)this.f_20903_;
                  double d1 = this.m_20189_() + (this.f_20906_ - this.m_20189_()) / (double)this.f_20903_;
                  double d2 = Mth.m_14175_(this.f_20907_ - (double)this.m_146908_());
                  this.m_146922_(this.m_146908_() + (float)d2 / (float)this.f_20903_);
                  this.m_146926_(this.m_146909_() + (float)(this.f_20908_ - (double)this.m_146909_()) / (float)this.f_20903_);
                  --this.f_20903_;
                  this.m_6034_(d7, d0, d1);
                  this.m_19915_(this.m_146908_(), this.m_146909_());
               }

               this.f_31074_.m_31415_().m_6991_();
            } else {
               DragonPhaseInstance dragonphaseinstance = this.f_31074_.m_31415_();
               dragonphaseinstance.m_6989_();
               if (this.f_31074_.m_31415_() != dragonphaseinstance) {
                  dragonphaseinstance = this.f_31074_.m_31415_();
                  dragonphaseinstance.m_6989_();
               }

               Vec3 vec31 = dragonphaseinstance.m_5535_();
               if (vec31 != null) {
                  double d8 = vec31.f_82479_ - this.m_20185_();
                  double d9 = vec31.f_82480_ - this.m_20186_();
                  double d10 = vec31.f_82481_ - this.m_20189_();
                  double d3 = d8 * d8 + d9 * d9 + d10 * d10;
                  float f5 = dragonphaseinstance.m_7072_();
                  double d4 = Math.sqrt(d8 * d8 + d10 * d10);
                  if (d4 > 0.0D) {
                     d9 = Mth.m_14008_(d9 / d4, (double)(-f5), (double)f5);
                  }

                  this.m_20256_(this.m_20184_().m_82520_(0.0D, d9 * 0.01D, 0.0D));
                  this.m_146922_(Mth.m_14177_(this.m_146908_()));
                  Vec3 vec32 = vec31.m_82492_(this.m_20185_(), this.m_20186_(), this.m_20189_()).m_82541_();
                  Vec3 vec33 = (new Vec3((double)Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)), this.m_20184_().f_82480_, (double)(-Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F))))).m_82541_();
                  float f6 = Math.max(((float)vec33.m_82526_(vec32) + 0.5F) / 1.5F, 0.0F);
                  if (Math.abs(d8) > (double)1.0E-5F || Math.abs(d10) > (double)1.0E-5F) {
                     double d5 = Mth.m_14008_(Mth.m_14175_(180.0D - Mth.m_14136_(d8, d10) * (double)(180F / (float)Math.PI) - (double)this.m_146908_()), -50.0D, 50.0D);
                     this.f_31085_ *= 0.8F;
                     this.f_31085_ = (float)((double)this.f_31085_ + d5 * (double)dragonphaseinstance.m_7089_());
                     this.m_146922_(this.m_146908_() + this.f_31085_ * 0.1F);
                  }

                  float f18 = (float)(2.0D / (d3 + 1.0D));
                  float f7 = 0.06F;
                  this.m_19920_(0.06F * (f6 * f18 + (1.0F - f18)), new Vec3(0.0D, 0.0D, -1.0D));
                  if (this.f_31083_) {
                     this.m_6478_(MoverType.SELF, this.m_20184_().m_82490_((double)0.8F));
                  } else {
                     this.m_6478_(MoverType.SELF, this.m_20184_());
                  }

                  Vec3 vec34 = this.m_20184_().m_82541_();
                  double d6 = 0.8D + 0.15D * (vec34.m_82526_(vec33) + 1.0D) / 2.0D;
                  this.m_20256_(this.m_20184_().m_82542_(d6, (double)0.91F, d6));
               }
            }

            this.f_20883_ = this.m_146908_();
            Vec3[] avec3 = new Vec3[this.f_31089_.length];

            for(int j = 0; j < this.f_31089_.length; ++j) {
               avec3[j] = new Vec3(this.f_31089_[j].m_20185_(), this.f_31089_[j].m_20186_(), this.f_31089_[j].m_20189_());
            }

            float f11 = (float)(this.m_31101_(5, 1.0F)[1] - this.m_31101_(10, 1.0F)[1]) * 10.0F * ((float)Math.PI / 180F);
            float f12 = Mth.m_14089_(f11);
            float f1 = Mth.m_14031_(f11);
            float f13 = this.m_146908_() * ((float)Math.PI / 180F);
            float f2 = Mth.m_14031_(f13);
            float f14 = Mth.m_14089_(f13);
            this.m_31115_(this.f_31091_, (double)(f2 * 0.5F), 0.0D, (double)(-f14 * 0.5F));
            this.m_31115_(this.f_31071_, (double)(f14 * 4.5F), 2.0D, (double)(f2 * 4.5F));
            this.m_31115_(this.f_31072_, (double)(f14 * -4.5F), 2.0D, (double)(f2 * -4.5F));
            if (!this.f_19853_.f_46443_ && this.f_20916_ == 0) {
               this.m_31131_(this.f_19853_.m_6249_(this, this.f_31071_.m_142469_().m_82377_(4.0D, 2.0D, 4.0D).m_82386_(0.0D, -2.0D, 0.0D), EntitySelector.f_20406_));
               this.m_31131_(this.f_19853_.m_6249_(this, this.f_31072_.m_142469_().m_82377_(4.0D, 2.0D, 4.0D).m_82386_(0.0D, -2.0D, 0.0D), EntitySelector.f_20406_));
               this.m_31141_(this.f_19853_.m_6249_(this, this.f_31080_.m_142469_().m_82400_(1.0D), EntitySelector.f_20406_));
               this.m_31141_(this.f_19853_.m_6249_(this, this.f_31090_.m_142469_().m_82400_(1.0D), EntitySelector.f_20406_));
            }

            float f3 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F) - this.f_31085_ * 0.01F);
            float f15 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F) - this.f_31085_ * 0.01F);
            float f4 = this.m_31159_();
            this.m_31115_(this.f_31080_, (double)(f3 * 6.5F * f12), (double)(f4 + f1 * 6.5F), (double)(-f15 * 6.5F * f12));
            this.m_31115_(this.f_31090_, (double)(f3 * 5.5F * f12), (double)(f4 + f1 * 5.5F), (double)(-f15 * 5.5F * f12));
            double[] adouble = this.m_31101_(5, 1.0F);

            for(int k = 0; k < 3; ++k) {
               EnderDragonPart enderdragonpart = null;
               if (k == 0) {
                  enderdragonpart = this.f_31068_;
               }

               if (k == 1) {
                  enderdragonpart = this.f_31069_;
               }

               if (k == 2) {
                  enderdragonpart = this.f_31070_;
               }

               double[] adouble1 = this.m_31101_(12 + k * 2, 1.0F);
               float f16 = this.m_146908_() * ((float)Math.PI / 180F) + this.m_31164_(adouble1[0] - adouble[0]) * ((float)Math.PI / 180F);
               float f17 = Mth.m_14031_(f16);
               float f19 = Mth.m_14089_(f16);
               float f20 = 1.5F;
               float f21 = (float)(k + 1) * 2.0F;
               this.m_31115_(enderdragonpart, (double)(-(f2 * 1.5F + f17 * f21) * f12), adouble1[1] - adouble[1] - (double)((f21 + 1.5F) * f1) + 1.5D, (double)((f14 * 1.5F + f19 * f21) * f12));
            }

            if (!this.f_19853_.f_46443_) {
               this.f_31083_ = this.m_31139_(this.f_31080_.m_142469_()) | this.m_31139_(this.f_31090_.m_142469_()) | this.m_31139_(this.f_31091_.m_142469_());
               if (this.f_31073_ != null) {
                  this.f_31073_.m_64096_(this);
               }
            }

            for(int l = 0; l < this.f_31089_.length; ++l) {
               this.f_31089_[l].f_19854_ = avec3[l].f_82479_;
               this.f_31089_[l].f_19855_ = avec3[l].f_82480_;
               this.f_31089_[l].f_19856_ = avec3[l].f_82481_;
               this.f_31089_[l].f_19790_ = avec3[l].f_82479_;
               this.f_31089_[l].f_19791_ = avec3[l].f_82480_;
               this.f_31089_[l].f_19792_ = avec3[l].f_82481_;
            }

         }
      }
   }

   private void m_31115_(EnderDragonPart p_31116_, double p_31117_, double p_31118_, double p_31119_) {
      p_31116_.m_6034_(this.m_20185_() + p_31117_, this.m_20186_() + p_31118_, this.m_20189_() + p_31119_);
   }

   private float m_31159_() {
      if (this.f_31074_.m_31415_().m_7080_()) {
         return -1.0F;
      } else {
         double[] adouble = this.m_31101_(5, 1.0F);
         double[] adouble1 = this.m_31101_(0, 1.0F);
         return (float)(adouble[1] - adouble1[1]);
      }
   }

   private void m_31160_() {
      if (this.f_31086_ != null) {
         if (this.f_31086_.m_146910_()) {
            this.f_31086_ = null;
         } else if (this.f_19797_ % 10 == 0 && this.m_21223_() < this.m_21233_()) {
            this.m_21153_(this.m_21223_() + 1.0F);
         }
      }

      if (this.f_19796_.nextInt(10) == 0) {
         List<EndCrystal> list = this.f_19853_.m_45976_(EndCrystal.class, this.m_142469_().m_82400_(32.0D));
         EndCrystal endcrystal = null;
         double d0 = Double.MAX_VALUE;

         for(EndCrystal endcrystal1 : list) {
            double d1 = endcrystal1.m_20280_(this);
            if (d1 < d0) {
               d0 = d1;
               endcrystal = endcrystal1;
            }
         }

         this.f_31086_ = endcrystal;
      }

   }

   private void m_31131_(List<Entity> p_31132_) {
      double d0 = (this.f_31091_.m_142469_().f_82288_ + this.f_31091_.m_142469_().f_82291_) / 2.0D;
      double d1 = (this.f_31091_.m_142469_().f_82290_ + this.f_31091_.m_142469_().f_82293_) / 2.0D;

      for(Entity entity : p_31132_) {
         if (entity instanceof LivingEntity) {
            double d2 = entity.m_20185_() - d0;
            double d3 = entity.m_20189_() - d1;
            double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
            entity.m_5997_(d2 / d4 * 4.0D, (double)0.2F, d3 / d4 * 4.0D);
            if (!this.f_31074_.m_31415_().m_7080_() && ((LivingEntity)entity).m_21213_() < entity.f_19797_ - 2) {
               entity.m_6469_(DamageSource.m_19370_(this), 5.0F);
               this.m_19970_(this, entity);
            }
         }
      }

   }

   private void m_31141_(List<Entity> p_31142_) {
      for(Entity entity : p_31142_) {
         if (entity instanceof LivingEntity) {
            entity.m_6469_(DamageSource.m_19370_(this), 10.0F);
            this.m_19970_(this, entity);
         }
      }

   }

   private float m_31164_(double p_31165_) {
      return (float)Mth.m_14175_(p_31165_);
   }

   private boolean m_31139_(AABB p_31140_) {
      int i = Mth.m_14107_(p_31140_.f_82288_);
      int j = Mth.m_14107_(p_31140_.f_82289_);
      int k = Mth.m_14107_(p_31140_.f_82290_);
      int l = Mth.m_14107_(p_31140_.f_82291_);
      int i1 = Mth.m_14107_(p_31140_.f_82292_);
      int j1 = Mth.m_14107_(p_31140_.f_82293_);
      boolean flag = false;
      boolean flag1 = false;

      for(int k1 = i; k1 <= l; ++k1) {
         for(int l1 = j; l1 <= i1; ++l1) {
            for(int i2 = k; i2 <= j1; ++i2) {
               BlockPos blockpos = new BlockPos(k1, l1, i2);
               BlockState blockstate = this.f_19853_.m_8055_(blockpos);
               if (!blockstate.m_60795_() && blockstate.m_60767_() != Material.f_76309_) {
                  if (net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.f_19853_, blockpos, this) && !BlockTags.f_13069_.m_8110_(blockstate.m_60734_())) {
                     flag1 = this.f_19853_.m_7471_(blockpos, false) || flag1;
                  } else {
                     flag = true;
                  }
               }
            }
         }
      }

      if (flag1) {
         BlockPos blockpos1 = new BlockPos(i + this.f_19796_.nextInt(l - i + 1), j + this.f_19796_.nextInt(i1 - j + 1), k + this.f_19796_.nextInt(j1 - k + 1));
         this.f_19853_.m_46796_(2008, blockpos1, 0);
      }

      return flag;
   }

   public boolean m_31120_(EnderDragonPart p_31121_, DamageSource p_31122_, float p_31123_) {
      if (this.f_31074_.m_31415_().m_7309_() == EnderDragonPhase.f_31386_) {
         return false;
      } else {
         p_31123_ = this.f_31074_.m_31415_().m_7584_(p_31122_, p_31123_);
         if (p_31121_ != this.f_31080_) {
            p_31123_ = p_31123_ / 4.0F + Math.min(p_31123_, 1.0F);
         }

         if (p_31123_ < 0.01F) {
            return false;
         } else {
            if (p_31122_.m_7639_() instanceof Player || p_31122_.m_19372_()) {
               float f = this.m_21223_();
               this.m_31161_(p_31122_, p_31123_);
               if (this.m_21224_() && !this.f_31074_.m_31415_().m_7080_()) {
                  this.m_21153_(1.0F);
                  this.f_31074_.m_31416_(EnderDragonPhase.f_31386_);
               }

               if (this.f_31074_.m_31415_().m_7080_()) {
                  this.f_31076_ = (int)((float)this.f_31076_ + (f - this.m_21223_()));
                  if ((float)this.f_31076_ > 0.25F * this.m_21233_()) {
                     this.f_31076_ = 0;
                     this.f_31074_.m_31416_(EnderDragonPhase.f_31381_);
                  }
               }
            }

            return true;
         }
      }
   }

   public boolean m_6469_(DamageSource p_31113_, float p_31114_) {
      if (p_31113_ instanceof EntityDamageSource && ((EntityDamageSource)p_31113_).m_19403_()) {
         this.m_31120_(this.f_31091_, p_31113_, p_31114_);
      }

      return false;
   }

   protected boolean m_31161_(DamageSource p_31162_, float p_31163_) {
      return super.m_6469_(p_31162_, p_31163_);
   }

   public void m_6074_() {
      this.m_142687_(Entity.RemovalReason.KILLED);
      if (this.f_31073_ != null) {
         this.f_31073_.m_64096_(this);
         this.f_31073_.m_64085_(this);
      }

   }

   protected void m_6153_() {
      if (this.f_31073_ != null) {
         this.f_31073_.m_64096_(this);
      }

      ++this.f_31084_;
      if (this.f_31084_ >= 180 && this.f_31084_ <= 200) {
         float f = (this.f_19796_.nextFloat() - 0.5F) * 8.0F;
         float f1 = (this.f_19796_.nextFloat() - 0.5F) * 4.0F;
         float f2 = (this.f_19796_.nextFloat() - 0.5F) * 8.0F;
         this.f_19853_.m_7106_(ParticleTypes.f_123812_, this.m_20185_() + (double)f, this.m_20186_() + 2.0D + (double)f1, this.m_20189_() + (double)f2, 0.0D, 0.0D, 0.0D);
      }

      boolean flag = this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_);
      int i = 500;
      if (this.f_31073_ != null && !this.f_31073_.m_64099_()) {
         i = 12000;
      }

      if (this.f_19853_ instanceof ServerLevel) {
         if (this.f_31084_ > 150 && this.f_31084_ % 5 == 0 && flag) {
            ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), Mth.m_14143_((float)i * 0.08F));
         }

         if (this.f_31084_ == 1 && !this.m_20067_()) {
            this.f_19853_.m_6798_(1028, this.m_142538_(), 0);
         }
      }

      this.m_6478_(MoverType.SELF, new Vec3(0.0D, (double)0.1F, 0.0D));
      this.m_146922_(this.m_146908_() + 20.0F);
      this.f_20883_ = this.m_146908_();
      if (this.f_31084_ == 200 && this.f_19853_ instanceof ServerLevel) {
         if (flag) {
            ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), Mth.m_14143_((float)i * 0.2F));
         }

         if (this.f_31073_ != null) {
            this.f_31073_.m_64085_(this);
         }

         this.m_142687_(Entity.RemovalReason.KILLED);
      }

   }

   public int m_31155_() {
      if (this.f_31077_[0] == null) {
         for(int i = 0; i < 24; ++i) {
            int j = 5;
            int l;
            int i1;
            if (i < 12) {
               l = Mth.m_14143_(60.0F * Mth.m_14089_(2.0F * (-(float)Math.PI + 0.2617994F * (float)i)));
               i1 = Mth.m_14143_(60.0F * Mth.m_14031_(2.0F * (-(float)Math.PI + 0.2617994F * (float)i)));
            } else if (i < 20) {
               int k = i - 12;
               l = Mth.m_14143_(40.0F * Mth.m_14089_(2.0F * (-(float)Math.PI + ((float)Math.PI / 8F) * (float)k)));
               i1 = Mth.m_14143_(40.0F * Mth.m_14031_(2.0F * (-(float)Math.PI + ((float)Math.PI / 8F) * (float)k)));
               j += 10;
            } else {
               int k1 = i - 20;
               l = Mth.m_14143_(20.0F * Mth.m_14089_(2.0F * (-(float)Math.PI + ((float)Math.PI / 4F) * (float)k1)));
               i1 = Mth.m_14143_(20.0F * Mth.m_14031_(2.0F * (-(float)Math.PI + ((float)Math.PI / 4F) * (float)k1)));
            }

            int j1 = Math.max(this.f_19853_.m_5736_() + 10, this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(l, 0, i1)).m_123342_() + j);
            this.f_31077_[i] = new Node(l, j1, i1);
         }

         this.f_31078_[0] = 6146;
         this.f_31078_[1] = 8197;
         this.f_31078_[2] = 8202;
         this.f_31078_[3] = 16404;
         this.f_31078_[4] = 32808;
         this.f_31078_[5] = 32848;
         this.f_31078_[6] = 65696;
         this.f_31078_[7] = 131392;
         this.f_31078_[8] = 131712;
         this.f_31078_[9] = 263424;
         this.f_31078_[10] = 526848;
         this.f_31078_[11] = 525313;
         this.f_31078_[12] = 1581057;
         this.f_31078_[13] = 3166214;
         this.f_31078_[14] = 2138120;
         this.f_31078_[15] = 6373424;
         this.f_31078_[16] = 4358208;
         this.f_31078_[17] = 12910976;
         this.f_31078_[18] = 9044480;
         this.f_31078_[19] = 9706496;
         this.f_31078_[20] = 15216640;
         this.f_31078_[21] = 13688832;
         this.f_31078_[22] = 11763712;
         this.f_31078_[23] = 8257536;
      }

      return this.m_31170_(this.m_20185_(), this.m_20186_(), this.m_20189_());
   }

   public int m_31170_(double p_31171_, double p_31172_, double p_31173_) {
      float f = 10000.0F;
      int i = 0;
      Node node = new Node(Mth.m_14107_(p_31171_), Mth.m_14107_(p_31172_), Mth.m_14107_(p_31173_));
      int j = 0;
      if (this.f_31073_ == null || this.f_31073_.m_64098_() == 0) {
         j = 12;
      }

      for(int k = j; k < 24; ++k) {
         if (this.f_31077_[k] != null) {
            float f1 = this.f_31077_[k].m_77299_(node);
            if (f1 < f) {
               f = f1;
               i = k;
            }
         }
      }

      return i;
   }

   @Nullable
   public Path m_31104_(int p_31105_, int p_31106_, @Nullable Node p_31107_) {
      for(int i = 0; i < 24; ++i) {
         Node node = this.f_31077_[i];
         node.f_77279_ = false;
         node.f_77277_ = 0.0F;
         node.f_77275_ = 0.0F;
         node.f_77276_ = 0.0F;
         node.f_77278_ = null;
         node.f_77274_ = -1;
      }

      Node node4 = this.f_31077_[p_31105_];
      Node node5 = this.f_31077_[p_31106_];
      node4.f_77275_ = 0.0F;
      node4.f_77276_ = node4.m_77293_(node5);
      node4.f_77277_ = node4.f_77276_;
      this.f_31079_.m_77081_();
      this.f_31079_.m_77084_(node4);
      Node node1 = node4;
      int j = 0;
      if (this.f_31073_ == null || this.f_31073_.m_64098_() == 0) {
         j = 12;
      }

      while(!this.f_31079_.m_77092_()) {
         Node node2 = this.f_31079_.m_77091_();
         if (node2.equals(node5)) {
            if (p_31107_ != null) {
               p_31107_.f_77278_ = node5;
               node5 = p_31107_;
            }

            return this.m_31128_(node4, node5);
         }

         if (node2.m_77293_(node5) < node1.m_77293_(node5)) {
            node1 = node2;
         }

         node2.f_77279_ = true;
         int k = 0;

         for(int l = 0; l < 24; ++l) {
            if (this.f_31077_[l] == node2) {
               k = l;
               break;
            }
         }

         for(int i1 = j; i1 < 24; ++i1) {
            if ((this.f_31078_[k] & 1 << i1) > 0) {
               Node node3 = this.f_31077_[i1];
               if (!node3.f_77279_) {
                  float f = node2.f_77275_ + node2.m_77293_(node3);
                  if (!node3.m_77303_() || f < node3.f_77275_) {
                     node3.f_77278_ = node2;
                     node3.f_77275_ = f;
                     node3.f_77276_ = node3.m_77293_(node5);
                     if (node3.m_77303_()) {
                        this.f_31079_.m_77086_(node3, node3.f_77275_ + node3.f_77276_);
                     } else {
                        node3.f_77277_ = node3.f_77275_ + node3.f_77276_;
                        this.f_31079_.m_77084_(node3);
                     }
                  }
               }
            }
         }
      }

      if (node1 == node4) {
         return null;
      } else {
         f_31087_.debug("Failed to find path from {} to {}", p_31105_, p_31106_);
         if (p_31107_ != null) {
            p_31107_.f_77278_ = node1;
            node1 = p_31107_;
         }

         return this.m_31128_(node4, node1);
      }
   }

   private Path m_31128_(Node p_31129_, Node p_31130_) {
      List<Node> list = Lists.newArrayList();
      Node node = p_31130_;
      list.add(0, p_31130_);

      while(node.f_77278_ != null) {
         node = node.f_77278_;
         list.add(0, node);
      }

      return new Path(list, new BlockPos(p_31130_.f_77271_, p_31130_.f_77272_, p_31130_.f_77273_), true);
   }

   public void m_7380_(CompoundTag p_31144_) {
      super.m_7380_(p_31144_);
      p_31144_.m_128405_("DragonPhase", this.f_31074_.m_31415_().m_7309_().m_31405_());
      p_31144_.m_128405_("DragonDeathTime", this.f_31084_);
   }

   public void m_7378_(CompoundTag p_31134_) {
      super.m_7378_(p_31134_);
      if (p_31134_.m_128441_("DragonPhase")) {
         this.f_31074_.m_31416_(EnderDragonPhase.m_31398_(p_31134_.m_128451_("DragonPhase")));
      }

      if (p_31134_.m_128441_("DragonDeathTime")) {
         this.f_31084_ = p_31134_.m_128451_("DragonDeathTime");
      }

   }

   public void m_6043_() {
   }

   public EnderDragonPart[] m_31156_() {
      return this.f_31089_;
   }

   public boolean m_6087_() {
      return false;
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11890_;
   }

   protected SoundEvent m_7975_(DamageSource p_31154_) {
      return SoundEvents.f_11895_;
   }

   protected float m_6121_() {
      return 5.0F;
   }

   public float m_31108_(int p_31109_, double[] p_31110_, double[] p_31111_) {
      DragonPhaseInstance dragonphaseinstance = this.f_31074_.m_31415_();
      EnderDragonPhase<? extends DragonPhaseInstance> enderdragonphase = dragonphaseinstance.m_7309_();
      double d0;
      if (enderdragonphase != EnderDragonPhase.f_31380_ && enderdragonphase != EnderDragonPhase.f_31381_) {
         if (dragonphaseinstance.m_7080_()) {
            d0 = (double)p_31109_;
         } else if (p_31109_ == 6) {
            d0 = 0.0D;
         } else {
            d0 = p_31111_[1] - p_31110_[1];
         }
      } else {
         BlockPos blockpos = this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
         double d1 = Math.max(Math.sqrt(blockpos.m_123309_(this.m_20182_(), true)) / 4.0D, 1.0D);
         d0 = (double)p_31109_ / d1;
      }

      return (float)d0;
   }

   public Vec3 m_31174_(float p_31175_) {
      DragonPhaseInstance dragonphaseinstance = this.f_31074_.m_31415_();
      EnderDragonPhase<? extends DragonPhaseInstance> enderdragonphase = dragonphaseinstance.m_7309_();
      Vec3 vec3;
      if (enderdragonphase != EnderDragonPhase.f_31380_ && enderdragonphase != EnderDragonPhase.f_31381_) {
         if (dragonphaseinstance.m_7080_()) {
            float f4 = this.m_146909_();
            float f5 = 1.5F;
            this.m_146926_(-45.0F);
            vec3 = this.m_20252_(p_31175_);
            this.m_146926_(f4);
         } else {
            vec3 = this.m_20252_(p_31175_);
         }
      } else {
         BlockPos blockpos = this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_);
         float f = Math.max((float)Math.sqrt(blockpos.m_123309_(this.m_20182_(), true)) / 4.0F, 1.0F);
         float f1 = 6.0F / f;
         float f2 = this.m_146909_();
         float f3 = 1.5F;
         this.m_146926_(-f1 * 1.5F * 5.0F);
         vec3 = this.m_20252_(p_31175_);
         this.m_146926_(f2);
      }

      return vec3;
   }

   public void m_31124_(EndCrystal p_31125_, BlockPos p_31126_, DamageSource p_31127_) {
      Player player;
      if (p_31127_.m_7639_() instanceof Player) {
         player = (Player)p_31127_.m_7639_();
      } else {
         player = this.f_19853_.m_45941_(f_31088_, (double)p_31126_.m_123341_(), (double)p_31126_.m_123342_(), (double)p_31126_.m_123343_());
      }

      if (p_31125_ == this.f_31086_) {
         this.m_31120_(this.f_31080_, DamageSource.m_19373_(player), 10.0F);
      }

      this.f_31074_.m_31415_().m_8059_(p_31125_, p_31126_, p_31127_, player);
   }

   public void m_7350_(EntityDataAccessor<?> p_31136_) {
      if (f_31067_.equals(p_31136_) && this.f_19853_.f_46443_) {
         this.f_31074_.m_31416_(EnderDragonPhase.m_31398_(this.m_20088_().m_135370_(f_31067_)));
      }

      super.m_7350_(p_31136_);
   }

   public EnderDragonPhaseManager m_31157_() {
      return this.f_31074_;
   }

   @Nullable
   public EndDragonFight m_31158_() {
      return this.f_31073_;
   }

   public boolean m_147207_(MobEffectInstance p_182394_, @Nullable Entity p_182395_) {
      return false;
   }

   protected boolean m_7341_(Entity p_31169_) {
      return false;
   }

   public boolean m_6072_() {
      return false;
   }

   @Override
   public boolean isMultipartEntity() {
      return true;
   }

   @Override
   public net.minecraftforge.entity.PartEntity<?>[] getParts() {
      return this.f_31089_;
   }

   public void m_142223_(ClientboundAddMobPacket p_149572_) {
      super.m_142223_(p_149572_);
      EnderDragonPart[] aenderdragonpart = this.m_31156_();

      for(int i = 0; i < aenderdragonpart.length; ++i) {
         aenderdragonpart[i].m_20234_(i + p_149572_.m_131552_());
      }

   }

   public boolean m_6779_(LivingEntity p_149576_) {
      return p_149576_.m_142066_();
   }
}
