package net.minecraft.world.entity.animal;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.OfferFlowerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.DefendVillageTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class IronGolem extends AbstractGolem implements NeutralMob {
   protected static final EntityDataAccessor<Byte> f_28826_ = SynchedEntityData.m_135353_(IronGolem.class, EntityDataSerializers.f_135027_);
   private static final int f_148932_ = 25;
   private int f_28830_;
   private int f_28831_;
   private static final UniformInt f_28827_ = TimeUtil.m_145020_(20, 39);
   private int f_28828_;
   private UUID f_28829_;

   public IronGolem(EntityType<? extends IronGolem> p_28834_, Level p_28835_) {
      super(p_28834_, p_28835_);
      this.f_19793_ = 1.0F;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 1.0D, true));
      this.f_21345_.m_25352_(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
      this.f_21345_.m_25352_(2, new MoveBackToVillageGoal(this, 0.6D, false));
      this.f_21345_.m_25352_(4, new GolemRandomStrollInVillageGoal(this, 0.6D));
      this.f_21345_.m_25352_(5, new OfferFlowerGoal(this));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new DefendVillageTargetGoal(this));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::m_21674_));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> {
         return p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper);
      }));
      this.f_21346_.m_25352_(4, new ResetUniversalAngerTargetGoal<>(this, false));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_28826_, (byte)0);
   }

   public static AttributeSupplier.Builder m_28883_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 100.0D).m_22268_(Attributes.f_22279_, 0.25D).m_22268_(Attributes.f_22278_, 1.0D).m_22268_(Attributes.f_22281_, 15.0D);
   }

   protected int m_7302_(int p_28882_) {
      return p_28882_;
   }

   protected void m_7324_(Entity p_28839_) {
      if (p_28839_ instanceof Enemy && !(p_28839_ instanceof Creeper) && this.m_21187_().nextInt(20) == 0) {
         this.m_6710_((LivingEntity)p_28839_);
      }

      super.m_7324_(p_28839_);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.f_28830_ > 0) {
         --this.f_28830_;
      }

      if (this.f_28831_ > 0) {
         --this.f_28831_;
      }

      if (this.m_20184_().m_165925_() > (double)2.5000003E-7F && this.f_19796_.nextInt(5) == 0) {
         int i = Mth.m_14107_(this.m_20185_());
         int j = Mth.m_14107_(this.m_20186_() - (double)0.2F);
         int k = Mth.m_14107_(this.m_20189_());
         BlockPos pos = new BlockPos(i, j, k);
         BlockState blockstate = this.f_19853_.m_8055_(pos);
         if (!blockstate.m_60795_()) {
            this.f_19853_.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, blockstate).setPos(pos), this.m_20185_() + ((double)this.f_19796_.nextFloat() - 0.5D) * (double)this.m_20205_(), this.m_20186_() + 0.1D, this.m_20189_() + ((double)this.f_19796_.nextFloat() - 0.5D) * (double)this.m_20205_(), 4.0D * ((double)this.f_19796_.nextFloat() - 0.5D), 0.5D, ((double)this.f_19796_.nextFloat() - 0.5D) * 4.0D);
         }
      }

      if (!this.f_19853_.f_46443_) {
         this.m_21666_((ServerLevel)this.f_19853_, true);
      }

   }

   public boolean m_6549_(EntityType<?> p_28851_) {
      if (this.m_28876_() && p_28851_ == EntityType.f_20532_) {
         return false;
      } else {
         return p_28851_ == EntityType.f_20558_ ? false : super.m_6549_(p_28851_);
      }
   }

   public void m_7380_(CompoundTag p_28867_) {
      super.m_7380_(p_28867_);
      p_28867_.m_128379_("PlayerCreated", this.m_28876_());
      this.m_21678_(p_28867_);
   }

   public void m_7378_(CompoundTag p_28857_) {
      super.m_7378_(p_28857_);
      this.m_28887_(p_28857_.m_128471_("PlayerCreated"));
      this.m_147285_(this.f_19853_, p_28857_);
   }

   public void m_6825_() {
      this.m_7870_(f_28827_.m_142270_(this.f_19796_));
   }

   public void m_7870_(int p_28859_) {
      this.f_28828_ = p_28859_;
   }

   public int m_6784_() {
      return this.f_28828_;
   }

   public void m_6925_(@Nullable UUID p_28855_) {
      this.f_28829_ = p_28855_;
   }

   public UUID m_6120_() {
      return this.f_28829_;
   }

   private float m_28877_() {
      return (float)this.m_21133_(Attributes.f_22281_);
   }

   public boolean m_7327_(Entity p_28837_) {
      this.f_28830_ = 10;
      this.f_19853_.m_7605_(this, (byte)4);
      float f = this.m_28877_();
      float f1 = (int)f > 0 ? f / 2.0F + (float)this.f_19796_.nextInt((int)f) : f;
      boolean flag = p_28837_.m_6469_(DamageSource.m_19370_(this), f1);
      if (flag) {
         p_28837_.m_20256_(p_28837_.m_20184_().m_82520_(0.0D, (double)0.4F, 0.0D));
         this.m_19970_(this, p_28837_);
      }

      this.m_5496_(SoundEvents.f_12057_, 1.0F, 1.0F);
      return flag;
   }

   public boolean m_6469_(DamageSource p_28848_, float p_28849_) {
      IronGolem.Crackiness irongolem$crackiness = this.m_28873_();
      boolean flag = super.m_6469_(p_28848_, p_28849_);
      if (flag && this.m_28873_() != irongolem$crackiness) {
         this.m_5496_(SoundEvents.f_12058_, 1.0F, 1.0F);
      }

      return flag;
   }

   public IronGolem.Crackiness m_28873_() {
      return IronGolem.Crackiness.m_28901_(this.m_21223_() / this.m_21233_());
   }

   public void m_7822_(byte p_28844_) {
      if (p_28844_ == 4) {
         this.f_28830_ = 10;
         this.m_5496_(SoundEvents.f_12057_, 1.0F, 1.0F);
      } else if (p_28844_ == 11) {
         this.f_28831_ = 400;
      } else if (p_28844_ == 34) {
         this.f_28831_ = 0;
      } else {
         super.m_7822_(p_28844_);
      }

   }

   public int m_28874_() {
      return this.f_28830_;
   }

   public void m_28885_(boolean p_28886_) {
      if (p_28886_) {
         this.f_28831_ = 400;
         this.f_19853_.m_7605_(this, (byte)11);
      } else {
         this.f_28831_ = 0;
         this.f_19853_.m_7605_(this, (byte)34);
      }

   }

   protected SoundEvent m_7975_(DamageSource p_28872_) {
      return SoundEvents.f_12008_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12059_;
   }

   protected InteractionResult m_6071_(Player p_28861_, InteractionHand p_28862_) {
      ItemStack itemstack = p_28861_.m_21120_(p_28862_);
      if (!itemstack.m_150930_(Items.f_42416_)) {
         return InteractionResult.PASS;
      } else {
         float f = this.m_21223_();
         this.m_5634_(25.0F);
         if (this.m_21223_() == f) {
            return InteractionResult.PASS;
         } else {
            float f1 = 1.0F + (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F;
            this.m_5496_(SoundEvents.f_12009_, 1.0F, f1);
            this.m_146859_(GameEvent.f_157771_, this.m_146901_());
            if (!p_28861_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }
      }
   }

   protected void m_7355_(BlockPos p_28864_, BlockState p_28865_) {
      this.m_5496_(SoundEvents.f_12010_, 1.0F, 1.0F);
   }

   public int m_28875_() {
      return this.f_28831_;
   }

   public boolean m_28876_() {
      return (this.f_19804_.m_135370_(f_28826_) & 1) != 0;
   }

   public void m_28887_(boolean p_28888_) {
      byte b0 = this.f_19804_.m_135370_(f_28826_);
      if (p_28888_) {
         this.f_19804_.m_135381_(f_28826_, (byte)(b0 | 1));
      } else {
         this.f_19804_.m_135381_(f_28826_, (byte)(b0 & -2));
      }

   }

   public void m_6667_(DamageSource p_28846_) {
      super.m_6667_(p_28846_);
   }

   public boolean m_6914_(LevelReader p_28853_) {
      BlockPos blockpos = this.m_142538_();
      BlockPos blockpos1 = blockpos.m_7495_();
      BlockState blockstate = p_28853_.m_8055_(blockpos1);
      if (!blockstate.m_60634_(p_28853_, blockpos1, this)) {
         return false;
      } else {
         for(int i = 1; i < 3; ++i) {
            BlockPos blockpos2 = blockpos.m_6630_(i);
            BlockState blockstate1 = p_28853_.m_8055_(blockpos2);
            if (!NaturalSpawner.m_47056_(p_28853_, blockpos2, blockstate1, blockstate1.m_60819_(), EntityType.f_20460_)) {
               return false;
            }
         }

         return NaturalSpawner.m_47056_(p_28853_, blockpos, p_28853_.m_8055_(blockpos), Fluids.f_76191_.m_76145_(), EntityType.f_20460_) && p_28853_.m_45784_(this);
      }
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.875F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }

   public static enum Crackiness {
      NONE(1.0F),
      LOW(0.75F),
      MEDIUM(0.5F),
      HIGH(0.25F);

      private static final List<IronGolem.Crackiness> f_28893_ = Stream.of(values()).sorted(Comparator.comparingDouble((p_28904_) -> {
         return (double)p_28904_.f_28894_;
      })).collect(ImmutableList.toImmutableList());
      private final float f_28894_;

      private Crackiness(float p_28900_) {
         this.f_28894_ = p_28900_;
      }

      public static IronGolem.Crackiness m_28901_(float p_28902_) {
         for(IronGolem.Crackiness irongolem$crackiness : f_28893_) {
            if (p_28902_ < irongolem$crackiness.f_28894_) {
               return irongolem$crackiness;
            }
         }

         return NONE;
      }
   }
}
