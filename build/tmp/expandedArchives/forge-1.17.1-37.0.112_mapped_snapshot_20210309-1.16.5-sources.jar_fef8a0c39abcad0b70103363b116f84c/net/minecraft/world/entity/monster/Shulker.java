package net.minecraft.world.entity.monster;

import com.mojang.math.Vector3f;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Shulker extends AbstractGolem implements Enemy {
   private static final UUID f_33394_ = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
   private static final AttributeModifier f_33395_ = new AttributeModifier(f_33394_, "Covered armor bonus", 20.0D, AttributeModifier.Operation.ADDITION);
   protected static final EntityDataAccessor<Direction> f_33392_ = SynchedEntityData.m_135353_(Shulker.class, EntityDataSerializers.f_135040_);
   protected static final EntityDataAccessor<Byte> f_33401_ = SynchedEntityData.m_135353_(Shulker.class, EntityDataSerializers.f_135027_);
   protected static final EntityDataAccessor<Byte> f_33393_ = SynchedEntityData.m_135353_(Shulker.class, EntityDataSerializers.f_135027_);
   private static final int f_149756_ = 6;
   private static final byte f_149757_ = 16;
   private static final byte f_149758_ = 16;
   private static final int f_149759_ = 8;
   private static final int f_149760_ = 8;
   private static final int f_149761_ = 5;
   private static final float f_149762_ = 0.05F;
   static final Vector3f f_149763_ = Util.m_137537_(() -> {
      Vec3i vec3i = Direction.SOUTH.m_122436_();
      return new Vector3f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_());
   });
   private float f_33396_;
   private float f_33397_;
   @Nullable
   private BlockPos f_149764_;
   private int f_33399_;
   private static final float f_149765_ = 1.0F;

   public Shulker(EntityType<? extends Shulker> p_33404_, Level p_33405_) {
      super(p_33404_, p_33405_);
      this.f_21364_ = 5;
      this.f_21365_ = new Shulker.ShulkerLookControl(this);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new LookAtPlayerGoal(this, Player.class, 8.0F, 0.02F, true));
      this.f_21345_.m_25352_(4, new Shulker.ShulkerAttackGoal());
      this.f_21345_.m_25352_(7, new Shulker.ShulkerPeekGoal());
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, this.getClass())).m_26044_());
      this.f_21346_.m_25352_(2, new Shulker.ShulkerNearestAttackGoal(this));
      this.f_21346_.m_25352_(3, new Shulker.ShulkerDefenseAttackGoal(this));
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12407_;
   }

   public void m_8032_() {
      if (!this.m_33468_()) {
         super.m_8032_();
      }

   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12413_;
   }

   protected SoundEvent m_7975_(DamageSource p_33457_) {
      return this.m_33468_() ? SoundEvents.f_12415_ : SoundEvents.f_12414_;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33392_, Direction.DOWN);
      this.f_19804_.m_135372_(f_33401_, (byte)0);
      this.f_19804_.m_135372_(f_33393_, (byte)16);
   }

   public static AttributeSupplier.Builder m_33477_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 30.0D);
   }

   protected BodyRotationControl m_7560_() {
      return new Shulker.ShulkerBodyRotationControl(this);
   }

   public void m_7378_(CompoundTag p_33432_) {
      super.m_7378_(p_33432_);
      this.m_149788_(Direction.m_122376_(p_33432_.m_128445_("AttachFace")));
      this.f_19804_.m_135381_(f_33401_, p_33432_.m_128445_("Peek"));
      if (p_33432_.m_128425_("Color", 99)) {
         this.f_19804_.m_135381_(f_33393_, p_33432_.m_128445_("Color"));
      }

   }

   public void m_7380_(CompoundTag p_33443_) {
      super.m_7380_(p_33443_);
      p_33443_.m_128344_("AttachFace", (byte)this.m_33461_().m_122411_());
      p_33443_.m_128344_("Peek", this.f_19804_.m_135370_(f_33401_));
      p_33443_.m_128344_("Color", this.f_19804_.m_135370_(f_33393_));
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && !this.m_20159_() && !this.m_149785_(this.m_142538_(), this.m_33461_())) {
         this.m_149807_();
      }

      if (this.m_149808_()) {
         this.m_149809_();
      }

      if (this.f_19853_.f_46443_) {
         if (this.f_33399_ > 0) {
            --this.f_33399_;
         } else {
            this.f_149764_ = null;
         }
      }

   }

   private void m_149807_() {
      Direction direction = this.m_149810_(this.m_142538_());
      if (direction != null) {
         this.m_149788_(direction);
      } else {
         this.m_33460_();
      }

   }

   protected AABB m_142242_() {
      float f = m_149768_(this.f_33397_);
      Direction direction = this.m_33461_().m_122424_();
      float f1 = this.m_6095_().m_20678_() / 2.0F;
      return m_149790_(direction, f).m_82386_(this.m_20185_() - (double)f1, this.m_20186_(), this.m_20189_() - (double)f1);
   }

   private static float m_149768_(float p_149769_) {
      return 0.5F - Mth.m_14031_((0.5F + p_149769_) * (float)Math.PI) * 0.5F;
   }

   private boolean m_149808_() {
      this.f_33396_ = this.f_33397_;
      float f = (float)this.m_33463_() * 0.01F;
      if (this.f_33397_ == f) {
         return false;
      } else {
         if (this.f_33397_ > f) {
            this.f_33397_ = Mth.m_14036_(this.f_33397_ - 0.05F, f, 1.0F);
         } else {
            this.f_33397_ = Mth.m_14036_(this.f_33397_ + 0.05F, 0.0F, f);
         }

         return true;
      }
   }

   private void m_149809_() {
      this.m_20090_();
      float f = m_149768_(this.f_33397_);
      float f1 = m_149768_(this.f_33396_);
      Direction direction = this.m_33461_().m_122424_();
      float f2 = f - f1;
      if (!(f2 <= 0.0F)) {
         for(Entity entity : this.f_19853_.m_6249_(this, m_149793_(direction, f1, f).m_82386_(this.m_20185_() - 0.5D, this.m_20186_(), this.m_20189_() - 0.5D), EntitySelector.f_20408_.and((p_149771_) -> {
            return !p_149771_.m_20365_(this);
         }))) {
            if (!(entity instanceof Shulker) && !entity.f_19794_) {
               entity.m_6478_(MoverType.SHULKER, new Vec3((double)(f2 * (float)direction.m_122429_()), (double)(f2 * (float)direction.m_122430_()), (double)(f2 * (float)direction.m_122431_())));
            }
         }

      }
   }

   public static AABB m_149790_(Direction p_149791_, float p_149792_) {
      return m_149793_(p_149791_, -1.0F, p_149792_);
   }

   public static AABB m_149793_(Direction p_149794_, float p_149795_, float p_149796_) {
      double d0 = (double)Math.max(p_149795_, p_149796_);
      double d1 = (double)Math.min(p_149795_, p_149796_);
      return (new AABB(BlockPos.f_121853_)).m_82363_((double)p_149794_.m_122429_() * d0, (double)p_149794_.m_122430_() * d0, (double)p_149794_.m_122431_() * d0).m_82310_((double)(-p_149794_.m_122429_()) * (1.0D + d1), (double)(-p_149794_.m_122430_()) * (1.0D + d1), (double)(-p_149794_.m_122431_()) * (1.0D + d1));
   }

   public double m_6049_() {
      EntityType<?> entitytype = this.m_20202_().m_6095_();
      return entitytype != EntityType.f_20552_ && entitytype != EntityType.f_20469_ ? super.m_6049_() : 0.1875D - this.m_20202_().m_6048_();
   }

   public boolean m_7998_(Entity p_149773_, boolean p_149774_) {
      if (this.f_19853_.m_5776_()) {
         this.f_149764_ = null;
         this.f_33399_ = 0;
      }

      this.m_149788_(Direction.DOWN);
      return super.m_7998_(p_149773_, p_149774_);
   }

   public void m_8127_() {
      super.m_8127_();
      if (this.f_19853_.f_46443_) {
         this.f_149764_ = this.m_142538_();
      }

      this.f_20884_ = 0.0F;
      this.f_20883_ = 0.0F;
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_149780_, DifficultyInstance p_149781_, MobSpawnType p_149782_, @Nullable SpawnGroupData p_149783_, @Nullable CompoundTag p_149784_) {
      this.m_146922_(0.0F);
      this.f_20885_ = this.m_146908_();
      this.m_146867_();
      return super.m_6518_(p_149780_, p_149781_, p_149782_, p_149783_, p_149784_);
   }

   public void m_6478_(MoverType p_33424_, Vec3 p_33425_) {
      if (p_33424_ == MoverType.SHULKER_BOX) {
         this.m_33460_();
      } else {
         super.m_6478_(p_33424_, p_33425_);
      }

   }

   public Vec3 m_20184_() {
      return Vec3.f_82478_;
   }

   public void m_20256_(Vec3 p_149804_) {
   }

   public void m_6034_(double p_33449_, double p_33450_, double p_33451_) {
      BlockPos blockpos = this.m_142538_();
      if (this.m_20159_()) {
         super.m_6034_(p_33449_, p_33450_, p_33451_);
      } else {
         super.m_6034_((double)Mth.m_14107_(p_33449_) + 0.5D, (double)Mth.m_14107_(p_33450_ + 0.5D), (double)Mth.m_14107_(p_33451_) + 0.5D);
      }

      if (this.f_19797_ != 0) {
         BlockPos blockpos1 = this.m_142538_();
         if (!blockpos1.equals(blockpos)) {
            this.f_19804_.m_135381_(f_33401_, (byte)0);
            this.f_19812_ = true;
            if (this.f_19853_.f_46443_ && !this.m_20159_() && !blockpos1.equals(this.f_149764_)) {
               this.f_149764_ = blockpos;
               this.f_33399_ = 6;
               this.f_19790_ = this.m_20185_();
               this.f_19791_ = this.m_20186_();
               this.f_19792_ = this.m_20189_();
            }
         }

      }
   }

   @Nullable
   protected Direction m_149810_(BlockPos p_149811_) {
      for(Direction direction : Direction.values()) {
         if (this.m_149785_(p_149811_, direction)) {
            return direction;
         }
      }

      return null;
   }

   boolean m_149785_(BlockPos p_149786_, Direction p_149787_) {
      if (this.m_149812_(p_149786_)) {
         return false;
      } else {
         Direction direction = p_149787_.m_122424_();
         if (!this.f_19853_.m_46578_(p_149786_.m_142300_(p_149787_), this, direction)) {
            return false;
         } else {
            AABB aabb = m_149790_(direction, 1.0F).m_82338_(p_149786_).m_82406_(1.0E-6D);
            return this.f_19853_.m_45756_(this, aabb);
         }
      }
   }

   private boolean m_149812_(BlockPos p_149813_) {
      BlockState blockstate = this.f_19853_.m_8055_(p_149813_);
      if (blockstate.m_60795_()) {
         return false;
      } else {
         boolean flag = blockstate.m_60713_(Blocks.f_50110_) && p_149813_.equals(this.m_142538_());
         return !flag;
      }
   }

   protected boolean m_33460_() {
      if (!this.m_21525_() && this.m_6084_()) {
         BlockPos blockpos = this.m_142538_();

         for(int i = 0; i < 5; ++i) {
            BlockPos blockpos1 = blockpos.m_142082_(Mth.m_144928_(this.f_19796_, -8, 8), Mth.m_144928_(this.f_19796_, -8, 8), Mth.m_144928_(this.f_19796_, -8, 8));
            if (blockpos1.m_123342_() > this.f_19853_.m_141937_() && this.f_19853_.m_46859_(blockpos1) && this.f_19853_.m_6857_().m_61937_(blockpos1) && this.f_19853_.m_45756_(this, (new AABB(blockpos1)).m_82406_(1.0E-6D))) {
               Direction direction = this.m_149810_(blockpos1);
               if (direction != null) {
                  this.m_19877_();
                  this.m_149788_(direction);
                  this.m_5496_(SoundEvents.f_12418_, 1.0F, 1.0F);
                  this.m_6034_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_() + 0.5D);
                  this.f_19804_.m_135381_(f_33401_, (byte)0);
                  this.m_6710_((LivingEntity)null);
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void m_6453_(double p_33411_, double p_33412_, double p_33413_, float p_33414_, float p_33415_, int p_33416_, boolean p_33417_) {
      this.f_20903_ = 0;
      this.m_6034_(p_33411_, p_33412_, p_33413_);
      this.m_19915_(p_33414_, p_33415_);
   }

   public boolean m_6469_(DamageSource p_33421_, float p_33422_) {
      if (this.m_33468_()) {
         Entity entity = p_33421_.m_7640_();
         if (entity instanceof AbstractArrow) {
            return false;
         }
      }

      if (!super.m_6469_(p_33421_, p_33422_)) {
         return false;
      } else {
         if ((double)this.m_21223_() < (double)this.m_21233_() * 0.5D && this.f_19796_.nextInt(4) == 0) {
            this.m_33460_();
         } else if (p_33421_.m_19360_()) {
            Entity entity1 = p_33421_.m_7640_();
            if (entity1 != null && entity1.m_6095_() == EntityType.f_20522_) {
               this.m_149805_();
            }
         }

         return true;
      }
   }

   private boolean m_33468_() {
      return this.m_33463_() == 0;
   }

   private void m_149805_() {
      Vec3 vec3 = this.m_20182_();
      AABB aabb = this.m_142469_();
      if (!this.m_33468_() && this.m_33460_()) {
         int i = this.f_19853_.m_142425_(EntityType.f_20521_, aabb.m_82400_(8.0D), Entity::m_6084_).size();
         float f = (float)(i - 1) / 5.0F;
         if (!(this.f_19853_.f_46441_.nextFloat() < f)) {
            Shulker shulker = EntityType.f_20521_.m_20615_(this.f_19853_);
            DyeColor dyecolor = this.m_33467_();
            if (dyecolor != null) {
               shulker.m_149777_(dyecolor);
            }

            shulker.m_20219_(vec3);
            this.f_19853_.m_7967_(shulker);
         }
      }
   }

   public boolean m_5829_() {
      return this.m_6084_();
   }

   public Direction m_33461_() {
      return this.f_19804_.m_135370_(f_33392_);
   }

   private void m_149788_(Direction p_149789_) {
      this.f_19804_.m_135381_(f_33392_, p_149789_);
   }

   public void m_7350_(EntityDataAccessor<?> p_33434_) {
      if (f_33392_.equals(p_33434_)) {
         this.m_20011_(this.m_142242_());
      }

      super.m_7350_(p_33434_);
   }

   private int m_33463_() {
      return this.f_19804_.m_135370_(f_33401_);
   }

   void m_33418_(int p_33419_) {
      if (!this.f_19853_.f_46443_) {
         this.m_21051_(Attributes.f_22284_).m_22130_(f_33395_);
         if (p_33419_ == 0) {
            this.m_21051_(Attributes.f_22284_).m_22125_(f_33395_);
            this.m_5496_(SoundEvents.f_12412_, 1.0F, 1.0F);
            this.m_146850_(GameEvent.f_157782_);
         } else {
            this.m_5496_(SoundEvents.f_12416_, 1.0F, 1.0F);
            this.m_146850_(GameEvent.f_157783_);
         }
      }

      this.f_19804_.m_135381_(f_33401_, (byte)p_33419_);
   }

   public float m_33480_(float p_33481_) {
      return Mth.m_14179_(p_33481_, this.f_33396_, this.f_33397_);
   }

   protected float m_6431_(Pose p_33438_, EntityDimensions p_33439_) {
      return 0.5F;
   }

   public void m_142223_(ClientboundAddMobPacket p_149798_) {
      super.m_142223_(p_149798_);
      this.f_20883_ = 0.0F;
   }

   public int m_8132_() {
      return 180;
   }

   public int m_8085_() {
      return 180;
   }

   public void m_7334_(Entity p_33474_) {
   }

   public float m_6143_() {
      return 0.0F;
   }

   public Optional<Vec3> m_149766_(float p_149767_) {
      if (this.f_149764_ != null && this.f_33399_ > 0) {
         double d0 = (double)((float)this.f_33399_ - p_149767_) / 6.0D;
         d0 = d0 * d0;
         BlockPos blockpos = this.m_142538_();
         double d1 = (double)(blockpos.m_123341_() - this.f_149764_.m_123341_()) * d0;
         double d2 = (double)(blockpos.m_123342_() - this.f_149764_.m_123342_()) * d0;
         double d3 = (double)(blockpos.m_123343_() - this.f_149764_.m_123343_()) * d0;
         return Optional.of(new Vec3(-d1, -d2, -d3));
      } else {
         return Optional.empty();
      }
   }

   private void m_149777_(DyeColor p_149778_) {
      this.f_19804_.m_135381_(f_33393_, (byte)p_149778_.m_41060_());
   }

   @Nullable
   public DyeColor m_33467_() {
      byte b0 = this.f_19804_.m_135370_(f_33393_);
      return b0 != 16 && b0 <= 15 ? DyeColor.m_41053_(b0) : null;
   }

   class ShulkerAttackGoal extends Goal {
      private int f_33483_;

      public ShulkerAttackGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity livingentity = Shulker.this.m_5448_();
         if (livingentity != null && livingentity.m_6084_()) {
            return Shulker.this.f_19853_.m_46791_() != Difficulty.PEACEFUL;
         } else {
            return false;
         }
      }

      public void m_8056_() {
         this.f_33483_ = 20;
         Shulker.this.m_33418_(100);
      }

      public void m_8041_() {
         Shulker.this.m_33418_(0);
      }

      public void m_8037_() {
         if (Shulker.this.f_19853_.m_46791_() != Difficulty.PEACEFUL) {
            --this.f_33483_;
            LivingEntity livingentity = Shulker.this.m_5448_();
            Shulker.this.m_21563_().m_24960_(livingentity, 180.0F, 180.0F);
            double d0 = Shulker.this.m_20280_(livingentity);
            if (d0 < 400.0D) {
               if (this.f_33483_ <= 0) {
                  this.f_33483_ = 20 + Shulker.this.f_19796_.nextInt(10) * 20 / 2;
                  Shulker.this.f_19853_.m_7967_(new ShulkerBullet(Shulker.this.f_19853_, Shulker.this, livingentity, Shulker.this.m_33461_().m_122434_()));
                  Shulker.this.m_5496_(SoundEvents.f_12417_, 2.0F, (Shulker.this.f_19796_.nextFloat() - Shulker.this.f_19796_.nextFloat()) * 0.2F + 1.0F);
               }
            } else {
               Shulker.this.m_6710_((LivingEntity)null);
            }

            super.m_8037_();
         }
      }
   }

   static class ShulkerBodyRotationControl extends BodyRotationControl {
      public ShulkerBodyRotationControl(Mob p_149816_) {
         super(p_149816_);
      }

      public void m_8121_() {
      }
   }

   static class ShulkerDefenseAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
      public ShulkerDefenseAttackGoal(Shulker p_33496_) {
         super(p_33496_, LivingEntity.class, 10, true, false, (p_33501_) -> {
            return p_33501_ instanceof Enemy;
         });
      }

      public boolean m_8036_() {
         return this.f_26135_.m_5647_() == null ? false : super.m_8036_();
      }

      protected AABB m_7255_(double p_33499_) {
         Direction direction = ((Shulker)this.f_26135_).m_33461_();
         if (direction.m_122434_() == Direction.Axis.X) {
            return this.f_26135_.m_142469_().m_82377_(4.0D, p_33499_, p_33499_);
         } else {
            return direction.m_122434_() == Direction.Axis.Z ? this.f_26135_.m_142469_().m_82377_(p_33499_, p_33499_, 4.0D) : this.f_26135_.m_142469_().m_82377_(p_33499_, 4.0D, p_33499_);
         }
      }
   }

   class ShulkerLookControl extends LookControl {
      public ShulkerLookControl(Mob p_149820_) {
         super(p_149820_);
      }

      protected void m_142586_() {
      }

      protected Optional<Float> m_180896_() {
         Direction direction = Shulker.this.m_33461_().m_122424_();
         Vector3f vector3f = Shulker.f_149763_.m_122281_();
         vector3f.m_122251_(direction.m_122406_());
         Vec3i vec3i = direction.m_122436_();
         Vector3f vector3f1 = new Vector3f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_());
         vector3f1.m_122279_(vector3f);
         double d0 = this.f_24941_ - this.f_24937_.m_20185_();
         double d1 = this.f_24942_ - this.f_24937_.m_20188_();
         double d2 = this.f_24943_ - this.f_24937_.m_20189_();
         Vector3f vector3f2 = new Vector3f((float)d0, (float)d1, (float)d2);
         float f = vector3f1.m_122276_(vector3f2);
         float f1 = vector3f.m_122276_(vector3f2);
         return !(Math.abs(f) > 1.0E-5F) && !(Math.abs(f1) > 1.0E-5F) ? Optional.empty() : Optional.of((float)(Mth.m_14136_((double)(-f), (double)f1) * (double)(180F / (float)Math.PI)));
      }

      protected Optional<Float> m_180897_() {
         return Optional.of(0.0F);
      }
   }

   class ShulkerNearestAttackGoal extends NearestAttackableTargetGoal<Player> {
      public ShulkerNearestAttackGoal(Shulker p_33505_) {
         super(p_33505_, Player.class, true);
      }

      public boolean m_8036_() {
         return Shulker.this.f_19853_.m_46791_() == Difficulty.PEACEFUL ? false : super.m_8036_();
      }

      protected AABB m_7255_(double p_33508_) {
         Direction direction = ((Shulker)this.f_26135_).m_33461_();
         if (direction.m_122434_() == Direction.Axis.X) {
            return this.f_26135_.m_142469_().m_82377_(4.0D, p_33508_, p_33508_);
         } else {
            return direction.m_122434_() == Direction.Axis.Z ? this.f_26135_.m_142469_().m_82377_(p_33508_, p_33508_, 4.0D) : this.f_26135_.m_142469_().m_82377_(p_33508_, 4.0D, p_33508_);
         }
      }
   }

   class ShulkerPeekGoal extends Goal {
      private int f_33510_;

      public boolean m_8036_() {
         return Shulker.this.m_5448_() == null && Shulker.this.f_19796_.nextInt(40) == 0 && Shulker.this.m_149785_(Shulker.this.m_142538_(), Shulker.this.m_33461_());
      }

      public boolean m_8045_() {
         return Shulker.this.m_5448_() == null && this.f_33510_ > 0;
      }

      public void m_8056_() {
         this.f_33510_ = 20 * (1 + Shulker.this.f_19796_.nextInt(3));
         Shulker.this.m_33418_(30);
      }

      public void m_8041_() {
         if (Shulker.this.m_5448_() == null) {
            Shulker.this.m_33418_(0);
         }

      }

      public void m_8037_() {
         --this.f_33510_;
      }
   }
}