package net.minecraft.world.entity.monster;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Evoker extends SpellcasterIllager {
   private Sheep f_32625_;

   public Evoker(EntityType<? extends Evoker> p_32627_, Level p_32628_) {
      super(p_32627_, p_32628_);
      this.f_21364_ = 10;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new Evoker.EvokerCastingSpellGoal());
      this.f_21345_.m_25352_(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D));
      this.f_21345_.m_25352_(4, new Evoker.EvokerSummonSpellGoal());
      this.f_21345_.m_25352_(5, new Evoker.EvokerAttackSpellGoal());
      this.f_21345_.m_25352_(6, new Evoker.EvokerWololoSpellGoal());
      this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6D));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, Raider.class)).m_26044_());
      this.f_21346_.m_25352_(2, (new NearestAttackableTargetGoal<>(this, Player.class, true)).m_26146_(300));
      this.f_21346_.m_25352_(3, (new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false)).m_26146_(300));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
   }

   public static AttributeSupplier.Builder m_32657_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.5D).m_22268_(Attributes.f_22277_, 12.0D).m_22268_(Attributes.f_22276_, 24.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_7378_(CompoundTag p_32642_) {
      super.m_7378_(p_32642_);
   }

   public SoundEvent m_7930_() {
      return SoundEvents.f_11863_;
   }

   public void m_7380_(CompoundTag p_32646_) {
      super.m_7380_(p_32646_);
   }

   protected void m_8024_() {
      super.m_8024_();
   }

   public boolean m_7307_(Entity p_32665_) {
      if (p_32665_ == null) {
         return false;
      } else if (p_32665_ == this) {
         return true;
      } else if (super.m_7307_(p_32665_)) {
         return true;
      } else if (p_32665_ instanceof Vex) {
         return this.m_7307_(((Vex)p_32665_).m_34026_());
      } else if (p_32665_ instanceof LivingEntity && ((LivingEntity)p_32665_).m_6336_() == MobType.f_21643_) {
         return this.m_5647_() == null && p_32665_.m_5647_() == null;
      } else {
         return false;
      }
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11861_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11864_;
   }

   protected SoundEvent m_7975_(DamageSource p_32654_) {
      return SoundEvents.f_11866_;
   }

   void m_32634_(@Nullable Sheep p_32635_) {
      this.f_32625_ = p_32635_;
   }

   @Nullable
   Sheep m_32662_() {
      return this.f_32625_;
   }

   protected SoundEvent m_7894_() {
      return SoundEvents.f_11862_;
   }

   public void m_7895_(int p_32632_, boolean p_32633_) {
   }

   class EvokerAttackSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      protected int m_8089_() {
         return 40;
      }

      protected int m_8067_() {
         return 100;
      }

      protected void m_8130_() {
         LivingEntity livingentity = Evoker.this.m_5448_();
         double d0 = Math.min(livingentity.m_20186_(), Evoker.this.m_20186_());
         double d1 = Math.max(livingentity.m_20186_(), Evoker.this.m_20186_()) + 1.0D;
         float f = (float)Mth.m_14136_(livingentity.m_20189_() - Evoker.this.m_20189_(), livingentity.m_20185_() - Evoker.this.m_20185_());
         if (Evoker.this.m_20280_(livingentity) < 9.0D) {
            for(int i = 0; i < 5; ++i) {
               float f1 = f + (float)i * (float)Math.PI * 0.4F;
               this.m_32672_(Evoker.this.m_20185_() + (double)Mth.m_14089_(f1) * 1.5D, Evoker.this.m_20189_() + (double)Mth.m_14031_(f1) * 1.5D, d0, d1, f1, 0);
            }

            for(int k = 0; k < 8; ++k) {
               float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + 1.2566371F;
               this.m_32672_(Evoker.this.m_20185_() + (double)Mth.m_14089_(f2) * 2.5D, Evoker.this.m_20189_() + (double)Mth.m_14031_(f2) * 2.5D, d0, d1, f2, 3);
            }
         } else {
            for(int l = 0; l < 16; ++l) {
               double d2 = 1.25D * (double)(l + 1);
               int j = 1 * l;
               this.m_32672_(Evoker.this.m_20185_() + (double)Mth.m_14089_(f) * d2, Evoker.this.m_20189_() + (double)Mth.m_14031_(f) * d2, d0, d1, f, j);
            }
         }

      }

      private void m_32672_(double p_32673_, double p_32674_, double p_32675_, double p_32676_, float p_32677_, int p_32678_) {
         BlockPos blockpos = new BlockPos(p_32673_, p_32676_, p_32674_);
         boolean flag = false;
         double d0 = 0.0D;

         do {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = Evoker.this.f_19853_.m_8055_(blockpos1);
            if (blockstate.m_60783_(Evoker.this.f_19853_, blockpos1, Direction.UP)) {
               if (!Evoker.this.f_19853_.m_46859_(blockpos)) {
                  BlockState blockstate1 = Evoker.this.f_19853_.m_8055_(blockpos);
                  VoxelShape voxelshape = blockstate1.m_60812_(Evoker.this.f_19853_, blockpos);
                  if (!voxelshape.m_83281_()) {
                     d0 = voxelshape.m_83297_(Direction.Axis.Y);
                  }
               }

               flag = true;
               break;
            }

            blockpos = blockpos.m_7495_();
         } while(blockpos.m_123342_() >= Mth.m_14107_(p_32675_) - 1);

         if (flag) {
            Evoker.this.f_19853_.m_7967_(new EvokerFangs(Evoker.this.f_19853_, p_32673_, (double)blockpos.m_123342_() + d0, p_32674_, p_32677_, p_32678_, Evoker.this));
         }

      }

      protected SoundEvent m_7030_() {
         return SoundEvents.f_11867_;
      }

      protected SpellcasterIllager.IllagerSpell m_7269_() {
         return SpellcasterIllager.IllagerSpell.FANGS;
      }
   }

   class EvokerCastingSpellGoal extends SpellcasterIllager.SpellcasterCastingSpellGoal {
      public void m_8037_() {
         if (Evoker.this.m_5448_() != null) {
            Evoker.this.m_21563_().m_24960_(Evoker.this.m_5448_(), (float)Evoker.this.m_8085_(), (float)Evoker.this.m_8132_());
         } else if (Evoker.this.m_32662_() != null) {
            Evoker.this.m_21563_().m_24960_(Evoker.this.m_32662_(), (float)Evoker.this.m_8085_(), (float)Evoker.this.m_8132_());
         }

      }
   }

   class EvokerSummonSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      private final TargetingConditions f_32692_ = TargetingConditions.m_148353_().m_26883_(16.0D).m_148355_().m_26893_();

      public boolean m_8036_() {
         if (!super.m_8036_()) {
            return false;
         } else {
            int i = Evoker.this.f_19853_.m_45971_(Vex.class, this.f_32692_, Evoker.this, Evoker.this.m_142469_().m_82400_(16.0D)).size();
            return Evoker.this.f_19796_.nextInt(8) + 1 > i;
         }
      }

      protected int m_8089_() {
         return 100;
      }

      protected int m_8067_() {
         return 340;
      }

      protected void m_8130_() {
         ServerLevel serverlevel = (ServerLevel)Evoker.this.f_19853_;

         for(int i = 0; i < 3; ++i) {
            BlockPos blockpos = Evoker.this.m_142538_().m_142082_(-2 + Evoker.this.f_19796_.nextInt(5), 1, -2 + Evoker.this.f_19796_.nextInt(5));
            Vex vex = EntityType.f_20491_.m_20615_(Evoker.this.f_19853_);
            vex.m_20035_(blockpos, 0.0F, 0.0F);
            vex.m_6518_(serverlevel, Evoker.this.f_19853_.m_6436_(blockpos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            vex.m_33994_(Evoker.this);
            vex.m_34033_(blockpos);
            vex.m_33987_(20 * (30 + Evoker.this.f_19796_.nextInt(90)));
            serverlevel.m_47205_(vex);
         }

      }

      protected SoundEvent m_7030_() {
         return SoundEvents.f_11868_;
      }

      protected SpellcasterIllager.IllagerSpell m_7269_() {
         return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
      }
   }

   public class EvokerWololoSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      private final TargetingConditions f_32705_ = TargetingConditions.m_148353_().m_26883_(16.0D).m_26888_((p_32710_) -> {
         return ((Sheep)p_32710_).m_29874_() == DyeColor.BLUE;
      });

      public boolean m_8036_() {
         if (Evoker.this.m_5448_() != null) {
            return false;
         } else if (Evoker.this.m_33736_()) {
            return false;
         } else if (Evoker.this.f_19797_ < this.f_33775_) {
            return false;
         } else if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(Evoker.this.f_19853_, Evoker.this)) {
            return false;
         } else {
            List<Sheep> list = Evoker.this.f_19853_.m_45971_(Sheep.class, this.f_32705_, Evoker.this, Evoker.this.m_142469_().m_82377_(16.0D, 4.0D, 16.0D));
            if (list.isEmpty()) {
               return false;
            } else {
               Evoker.this.m_32634_(list.get(Evoker.this.f_19796_.nextInt(list.size())));
               return true;
            }
         }
      }

      public boolean m_8045_() {
         return Evoker.this.m_32662_() != null && this.f_33774_ > 0;
      }

      public void m_8041_() {
         super.m_8041_();
         Evoker.this.m_32634_((Sheep)null);
      }

      protected void m_8130_() {
         Sheep sheep = Evoker.this.m_32662_();
         if (sheep != null && sheep.m_6084_()) {
            sheep.m_29855_(DyeColor.RED);
         }

      }

      protected int m_8069_() {
         return 40;
      }

      protected int m_8089_() {
         return 60;
      }

      protected int m_8067_() {
         return 140;
      }

      protected SoundEvent m_7030_() {
         return SoundEvents.f_11869_;
      }

      protected SpellcasterIllager.IllagerSpell m_7269_() {
         return SpellcasterIllager.IllagerSpell.WOLOLO;
      }
   }
}
