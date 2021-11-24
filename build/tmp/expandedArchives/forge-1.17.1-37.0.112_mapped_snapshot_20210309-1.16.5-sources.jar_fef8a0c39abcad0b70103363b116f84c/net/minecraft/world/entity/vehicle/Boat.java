package net.minecraft.world.entity.vehicle;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Boat extends Entity {
   private static final EntityDataAccessor<Integer> f_38282_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_38283_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> f_38284_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> f_38285_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_38286_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> f_38287_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_38262_ = SynchedEntityData.m_135353_(Boat.class, EntityDataSerializers.f_135028_);
   public static final int f_150268_ = 0;
   public static final int f_150269_ = 1;
   private static final int f_150266_ = 60;
   private static final double f_150267_ = (double)((float)Math.PI / 8F);
   public static final double f_150270_ = (double)((float)Math.PI / 4F);
   public static final int f_150271_ = 60;
   private final float[] f_38263_ = new float[2];
   private float f_38264_;
   private float f_38265_;
   private float f_38266_;
   private int f_38267_;
   private double f_38268_;
   private double f_38269_;
   private double f_38270_;
   private double f_38271_;
   private double f_38272_;
   private boolean f_38273_;
   private boolean f_38274_;
   private boolean f_38275_;
   private boolean f_38276_;
   private double f_38277_;
   private float f_38278_;
   private Boat.Status f_38279_;
   private Boat.Status f_38280_;
   private double f_38281_;
   private boolean f_38257_;
   private boolean f_38258_;
   private float f_38259_;
   private float f_38260_;
   private float f_38261_;

   public Boat(EntityType<? extends Boat> p_38290_, Level p_38291_) {
      super(p_38290_, p_38291_);
      this.f_19850_ = true;
   }

   public Boat(Level p_38293_, double p_38294_, double p_38295_, double p_38296_) {
      this(EntityType.f_20552_, p_38293_);
      this.m_6034_(p_38294_, p_38295_, p_38296_);
      this.f_19854_ = p_38294_;
      this.f_19855_ = p_38295_;
      this.f_19856_ = p_38296_;
   }

   protected float m_6380_(Pose p_38327_, EntityDimensions p_38328_) {
      return p_38328_.f_20378_;
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(f_38282_, 0);
      this.f_19804_.m_135372_(f_38283_, 1);
      this.f_19804_.m_135372_(f_38284_, 0.0F);
      this.f_19804_.m_135372_(f_38285_, Boat.Type.OAK.ordinal());
      this.f_19804_.m_135372_(f_38286_, false);
      this.f_19804_.m_135372_(f_38287_, false);
      this.f_19804_.m_135372_(f_38262_, 0);
   }

   public boolean m_7337_(Entity p_38376_) {
      return m_38323_(this, p_38376_);
   }

   public static boolean m_38323_(Entity p_38324_, Entity p_38325_) {
      return (p_38325_.m_5829_() || p_38325_.m_6094_()) && !p_38324_.m_20365_(p_38325_);
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6094_() {
      return true;
   }

   protected Vec3 m_7643_(Direction.Axis p_38335_, BlockUtil.FoundRectangle p_38336_) {
      return LivingEntity.m_21289_(super.m_7643_(p_38335_, p_38336_));
   }

   public double m_6048_() {
      return -0.1D;
   }

   public boolean m_6469_(DamageSource p_38319_, float p_38320_) {
      if (this.m_6673_(p_38319_)) {
         return false;
      } else if (!this.f_19853_.f_46443_ && !this.m_146910_()) {
         this.m_38362_(-this.m_38386_());
         this.m_38354_(10);
         this.m_38311_(this.m_38384_() + p_38320_ * 10.0F);
         this.m_5834_();
         this.m_146852_(GameEvent.f_157808_, p_38319_.m_7639_());
         boolean flag = p_38319_.m_7639_() instanceof Player && ((Player)p_38319_.m_7639_()).m_150110_().f_35937_;
         if (flag || this.m_38384_() > 40.0F) {
            if (!flag && this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
               this.m_19998_(this.m_38369_());
            }

            this.m_146870_();
         }

         return true;
      } else {
         return true;
      }
   }

   public void m_6845_(boolean p_38381_) {
      if (!this.f_19853_.f_46443_) {
         this.f_38257_ = true;
         this.f_38258_ = p_38381_;
         if (this.m_38397_() == 0) {
            this.m_38366_(60);
         }
      }

      this.f_19853_.m_7106_(ParticleTypes.f_123769_, this.m_20185_() + (double)this.f_19796_.nextFloat(), this.m_20186_() + 0.7D, this.m_20189_() + (double)this.f_19796_.nextFloat(), 0.0D, 0.0D, 0.0D);
      if (this.f_19796_.nextInt(20) == 0) {
         this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_5509_(), this.m_5720_(), 1.0F, 0.8F + 0.4F * this.f_19796_.nextFloat(), false);
      }

      this.m_146852_(GameEvent.f_157784_, this.m_6688_());
   }

   public void m_7334_(Entity p_38373_) {
      if (p_38373_ instanceof Boat) {
         if (p_38373_.m_142469_().f_82289_ < this.m_142469_().f_82292_) {
            super.m_7334_(p_38373_);
         }
      } else if (p_38373_.m_142469_().f_82289_ <= this.m_142469_().f_82289_) {
         super.m_7334_(p_38373_);
      }

   }

   public Item m_38369_() {
      switch(this.m_38387_()) {
      case OAK:
      default:
         return Items.f_42453_;
      case SPRUCE:
         return Items.f_42742_;
      case BIRCH:
         return Items.f_42743_;
      case JUNGLE:
         return Items.f_42744_;
      case ACACIA:
         return Items.f_42745_;
      case DARK_OAK:
         return Items.f_42746_;
      }
   }

   public void m_6053_() {
      this.m_38362_(-this.m_38386_());
      this.m_38354_(10);
      this.m_38311_(this.m_38384_() * 11.0F);
   }

   public boolean m_6087_() {
      return !this.m_146910_();
   }

   public void m_6453_(double p_38299_, double p_38300_, double p_38301_, float p_38302_, float p_38303_, int p_38304_, boolean p_38305_) {
      this.f_38268_ = p_38299_;
      this.f_38269_ = p_38300_;
      this.f_38270_ = p_38301_;
      this.f_38271_ = (double)p_38302_;
      this.f_38272_ = (double)p_38303_;
      this.f_38267_ = 10;
   }

   public Direction m_6374_() {
      return this.m_6350_().m_122427_();
   }

   public void m_8119_() {
      this.f_38280_ = this.f_38279_;
      this.f_38279_ = this.m_38392_();
      if (this.f_38279_ != Boat.Status.UNDER_WATER && this.f_38279_ != Boat.Status.UNDER_FLOWING_WATER) {
         this.f_38265_ = 0.0F;
      } else {
         ++this.f_38265_;
      }

      if (!this.f_19853_.f_46443_ && this.f_38265_ >= 60.0F) {
         this.m_20153_();
      }

      if (this.m_38385_() > 0) {
         this.m_38354_(this.m_38385_() - 1);
      }

      if (this.m_38384_() > 0.0F) {
         this.m_38311_(this.m_38384_() - 1.0F);
      }

      super.m_8119_();
      this.m_38391_();
      if (this.m_6109_()) {
         if (!(this.m_146895_() instanceof Player)) {
            this.m_38339_(false, false);
         }

         this.m_38395_();
         if (this.f_19853_.f_46443_) {
            this.m_38396_();
            this.f_19853_.m_5503_(new ServerboundPaddleBoatPacket(this.m_38313_(0), this.m_38313_(1)));
         }

         this.m_6478_(MoverType.SELF, this.m_20184_());
      } else {
         this.m_20256_(Vec3.f_82478_);
      }

      this.m_38388_();

      for(int i = 0; i <= 1; ++i) {
         if (this.m_38313_(i)) {
            if (!this.m_20067_() && (double)(this.f_38263_[i] % ((float)Math.PI * 2F)) <= (double)((float)Math.PI / 4F) && ((double)this.f_38263_[i] + (double)((float)Math.PI / 8F)) % (double)((float)Math.PI * 2F) >= (double)((float)Math.PI / 4F)) {
               SoundEvent soundevent = this.m_38370_();
               if (soundevent != null) {
                  Vec3 vec3 = this.m_20252_(1.0F);
                  double d0 = i == 1 ? -vec3.f_82481_ : vec3.f_82481_;
                  double d1 = i == 1 ? vec3.f_82479_ : -vec3.f_82479_;
                  this.f_19853_.m_6263_((Player)null, this.m_20185_() + d0, this.m_20186_(), this.m_20189_() + d1, soundevent, this.m_5720_(), 1.0F, 0.8F + 0.4F * this.f_19796_.nextFloat());
                  this.f_19853_.m_142346_(this.m_6688_(), GameEvent.f_157784_, new BlockPos(this.m_20185_() + d0, this.m_20186_(), this.m_20189_() + d1));
               }
            }

            this.f_38263_[i] = (float)((double)this.f_38263_[i] + (double)((float)Math.PI / 8F));
         } else {
            this.f_38263_[i] = 0.0F;
         }
      }

      this.m_20101_();
      List<Entity> list = this.f_19853_.m_6249_(this, this.m_142469_().m_82377_((double)0.2F, (double)-0.01F, (double)0.2F), EntitySelector.m_20421_(this));
      if (!list.isEmpty()) {
         boolean flag = !this.f_19853_.f_46443_ && !(this.m_6688_() instanceof Player);

         for(int j = 0; j < list.size(); ++j) {
            Entity entity = list.get(j);
            if (!entity.m_20363_(this)) {
               if (flag && this.m_20197_().size() < 2 && !entity.m_20159_() && entity.m_20205_() < this.m_20205_() && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
                  entity.m_20329_(this);
               } else {
                  this.m_7334_(entity);
               }
            }
         }
      }

   }

   private void m_38388_() {
      if (this.f_19853_.f_46443_) {
         int i = this.m_38397_();
         if (i > 0) {
            this.f_38259_ += 0.05F;
         } else {
            this.f_38259_ -= 0.1F;
         }

         this.f_38259_ = Mth.m_14036_(this.f_38259_, 0.0F, 1.0F);
         this.f_38261_ = this.f_38260_;
         this.f_38260_ = 10.0F * (float)Math.sin((double)(0.5F * (float)this.f_19853_.m_46467_())) * this.f_38259_;
      } else {
         if (!this.f_38257_) {
            this.m_38366_(0);
         }

         int k = this.m_38397_();
         if (k > 0) {
            --k;
            this.m_38366_(k);
            int j = 60 - k - 1;
            if (j > 0 && k == 0) {
               this.m_38366_(0);
               Vec3 vec3 = this.m_20184_();
               if (this.f_38258_) {
                  this.m_20256_(vec3.m_82520_(0.0D, -0.7D, 0.0D));
                  this.m_20153_();
               } else {
                  this.m_20334_(vec3.f_82479_, this.m_146862_((p_150274_) -> {
                     return p_150274_ instanceof Player;
                  }) ? 2.7D : 0.6D, vec3.f_82481_);
               }
            }

            this.f_38257_ = false;
         }
      }

   }

   @Nullable
   protected SoundEvent m_38370_() {
      switch(this.m_38392_()) {
      case IN_WATER:
      case UNDER_WATER:
      case UNDER_FLOWING_WATER:
         return SoundEvents.f_11707_;
      case ON_LAND:
         return SoundEvents.f_11706_;
      case IN_AIR:
      default:
         return null;
      }
   }

   private void m_38391_() {
      if (this.m_6109_()) {
         this.f_38267_ = 0;
         this.m_20167_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

      if (this.f_38267_ > 0) {
         double d0 = this.m_20185_() + (this.f_38268_ - this.m_20185_()) / (double)this.f_38267_;
         double d1 = this.m_20186_() + (this.f_38269_ - this.m_20186_()) / (double)this.f_38267_;
         double d2 = this.m_20189_() + (this.f_38270_ - this.m_20189_()) / (double)this.f_38267_;
         double d3 = Mth.m_14175_(this.f_38271_ - (double)this.m_146908_());
         this.m_146922_(this.m_146908_() + (float)d3 / (float)this.f_38267_);
         this.m_146926_(this.m_146909_() + (float)(this.f_38272_ - (double)this.m_146909_()) / (float)this.f_38267_);
         --this.f_38267_;
         this.m_6034_(d0, d1, d2);
         this.m_19915_(this.m_146908_(), this.m_146909_());
      }
   }

   public void m_38339_(boolean p_38340_, boolean p_38341_) {
      this.f_19804_.m_135381_(f_38286_, p_38340_);
      this.f_19804_.m_135381_(f_38287_, p_38341_);
   }

   public float m_38315_(int p_38316_, float p_38317_) {
      return this.m_38313_(p_38316_) ? (float)Mth.m_14085_((double)this.f_38263_[p_38316_] - (double)((float)Math.PI / 8F), (double)this.f_38263_[p_38316_], (double)p_38317_) : 0.0F;
   }

   private Boat.Status m_38392_() {
      Boat.Status boat$status = this.m_38394_();
      if (boat$status != null) {
         this.f_38277_ = this.m_142469_().f_82292_;
         return boat$status;
      } else if (this.m_38393_()) {
         return Boat.Status.IN_WATER;
      } else {
         float f = this.m_38377_();
         if (f > 0.0F) {
            this.f_38278_ = f;
            return Boat.Status.ON_LAND;
         } else {
            return Boat.Status.IN_AIR;
         }
      }
   }

   public float m_38371_() {
      AABB aabb = this.m_142469_();
      int i = Mth.m_14107_(aabb.f_82288_);
      int j = Mth.m_14165_(aabb.f_82291_);
      int k = Mth.m_14107_(aabb.f_82292_);
      int l = Mth.m_14165_(aabb.f_82292_ - this.f_38281_);
      int i1 = Mth.m_14107_(aabb.f_82290_);
      int j1 = Mth.m_14165_(aabb.f_82293_);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      label39:
      for(int k1 = k; k1 < l; ++k1) {
         float f = 0.0F;

         for(int l1 = i; l1 < j; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutableblockpos.m_122178_(l1, k1, i2);
               FluidState fluidstate = this.f_19853_.m_6425_(blockpos$mutableblockpos);
               if (fluidstate.m_76153_(FluidTags.f_13131_)) {
                  f = Math.max(f, fluidstate.m_76155_(this.f_19853_, blockpos$mutableblockpos));
               }

               if (f >= 1.0F) {
                  continue label39;
               }
            }
         }

         if (f < 1.0F) {
            return (float)blockpos$mutableblockpos.m_123342_() + f;
         }
      }

      return (float)(l + 1);
   }

   public float m_38377_() {
      AABB aabb = this.m_142469_();
      AABB aabb1 = new AABB(aabb.f_82288_, aabb.f_82289_ - 0.001D, aabb.f_82290_, aabb.f_82291_, aabb.f_82289_, aabb.f_82293_);
      int i = Mth.m_14107_(aabb1.f_82288_) - 1;
      int j = Mth.m_14165_(aabb1.f_82291_) + 1;
      int k = Mth.m_14107_(aabb1.f_82289_) - 1;
      int l = Mth.m_14165_(aabb1.f_82292_) + 1;
      int i1 = Mth.m_14107_(aabb1.f_82290_) - 1;
      int j1 = Mth.m_14165_(aabb1.f_82293_) + 1;
      VoxelShape voxelshape = Shapes.m_83064_(aabb1);
      float f = 0.0F;
      int k1 = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int l1 = i; l1 < j; ++l1) {
         for(int i2 = i1; i2 < j1; ++i2) {
            int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
            if (j2 != 2) {
               for(int k2 = k; k2 < l; ++k2) {
                  if (j2 <= 0 || k2 != k && k2 != l - 1) {
                     blockpos$mutableblockpos.m_122178_(l1, k2, i2);
                     BlockState blockstate = this.f_19853_.m_8055_(blockpos$mutableblockpos);
                     if (!(blockstate.m_60734_() instanceof WaterlilyBlock) && Shapes.m_83157_(blockstate.m_60812_(this.f_19853_, blockpos$mutableblockpos).m_83216_((double)l1, (double)k2, (double)i2), voxelshape, BooleanOp.f_82689_)) {
                        f += blockstate.getFriction(this.f_19853_, blockpos$mutableblockpos, this);
                        ++k1;
                     }
                  }
               }
            }
         }
      }

      return f / (float)k1;
   }

   private boolean m_38393_() {
      AABB aabb = this.m_142469_();
      int i = Mth.m_14107_(aabb.f_82288_);
      int j = Mth.m_14165_(aabb.f_82291_);
      int k = Mth.m_14107_(aabb.f_82289_);
      int l = Mth.m_14165_(aabb.f_82289_ + 0.001D);
      int i1 = Mth.m_14107_(aabb.f_82290_);
      int j1 = Mth.m_14165_(aabb.f_82293_);
      boolean flag = false;
      this.f_38277_ = -Double.MAX_VALUE;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutableblockpos.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.f_19853_.m_6425_(blockpos$mutableblockpos);
               if (fluidstate.m_76153_(FluidTags.f_13131_)) {
                  float f = (float)l1 + fluidstate.m_76155_(this.f_19853_, blockpos$mutableblockpos);
                  this.f_38277_ = Math.max((double)f, this.f_38277_);
                  flag |= aabb.f_82289_ < (double)f;
               }
            }
         }
      }

      return flag;
   }

   @Nullable
   private Boat.Status m_38394_() {
      AABB aabb = this.m_142469_();
      double d0 = aabb.f_82292_ + 0.001D;
      int i = Mth.m_14107_(aabb.f_82288_);
      int j = Mth.m_14165_(aabb.f_82291_);
      int k = Mth.m_14107_(aabb.f_82292_);
      int l = Mth.m_14165_(d0);
      int i1 = Mth.m_14107_(aabb.f_82290_);
      int j1 = Mth.m_14165_(aabb.f_82293_);
      boolean flag = false;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutableblockpos.m_122178_(k1, l1, i2);
               FluidState fluidstate = this.f_19853_.m_6425_(blockpos$mutableblockpos);
               if (fluidstate.m_76153_(FluidTags.f_13131_) && d0 < (double)((float)blockpos$mutableblockpos.m_123342_() + fluidstate.m_76155_(this.f_19853_, blockpos$mutableblockpos))) {
                  if (!fluidstate.m_76170_()) {
                     return Boat.Status.UNDER_FLOWING_WATER;
                  }

                  flag = true;
               }
            }
         }
      }

      return flag ? Boat.Status.UNDER_WATER : null;
   }

   private void m_38395_() {
      double d0 = (double)-0.04F;
      double d1 = this.m_20068_() ? 0.0D : (double)-0.04F;
      double d2 = 0.0D;
      this.f_38264_ = 0.05F;
      if (this.f_38280_ == Boat.Status.IN_AIR && this.f_38279_ != Boat.Status.IN_AIR && this.f_38279_ != Boat.Status.ON_LAND) {
         this.f_38277_ = this.m_20227_(1.0D);
         this.m_6034_(this.m_20185_(), (double)(this.m_38371_() - this.m_20206_()) + 0.101D, this.m_20189_());
         this.m_20256_(this.m_20184_().m_82542_(1.0D, 0.0D, 1.0D));
         this.f_38281_ = 0.0D;
         this.f_38279_ = Boat.Status.IN_WATER;
      } else {
         if (this.f_38279_ == Boat.Status.IN_WATER) {
            d2 = (this.f_38277_ - this.m_20186_()) / (double)this.m_20206_();
            this.f_38264_ = 0.9F;
         } else if (this.f_38279_ == Boat.Status.UNDER_FLOWING_WATER) {
            d1 = -7.0E-4D;
            this.f_38264_ = 0.9F;
         } else if (this.f_38279_ == Boat.Status.UNDER_WATER) {
            d2 = (double)0.01F;
            this.f_38264_ = 0.45F;
         } else if (this.f_38279_ == Boat.Status.IN_AIR) {
            this.f_38264_ = 0.9F;
         } else if (this.f_38279_ == Boat.Status.ON_LAND) {
            this.f_38264_ = this.f_38278_;
            if (this.m_6688_() instanceof Player) {
               this.f_38278_ /= 2.0F;
            }
         }

         Vec3 vec3 = this.m_20184_();
         this.m_20334_(vec3.f_82479_ * (double)this.f_38264_, vec3.f_82480_ + d1, vec3.f_82481_ * (double)this.f_38264_);
         this.f_38266_ *= this.f_38264_;
         if (d2 > 0.0D) {
            Vec3 vec31 = this.m_20184_();
            this.m_20334_(vec31.f_82479_, (vec31.f_82480_ + d2 * 0.06153846016296973D) * 0.75D, vec31.f_82481_);
         }
      }

   }

   private void m_38396_() {
      if (this.m_20160_()) {
         float f = 0.0F;
         if (this.f_38273_) {
            --this.f_38266_;
         }

         if (this.f_38274_) {
            ++this.f_38266_;
         }

         if (this.f_38274_ != this.f_38273_ && !this.f_38275_ && !this.f_38276_) {
            f += 0.005F;
         }

         this.m_146922_(this.m_146908_() + this.f_38266_);
         if (this.f_38275_) {
            f += 0.04F;
         }

         if (this.f_38276_) {
            f -= 0.005F;
         }

         this.m_20256_(this.m_20184_().m_82520_((double)(Mth.m_14031_(-this.m_146908_() * ((float)Math.PI / 180F)) * f), 0.0D, (double)(Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)) * f)));
         this.m_38339_(this.f_38274_ && !this.f_38273_ || this.f_38275_, this.f_38273_ && !this.f_38274_ || this.f_38275_);
      }
   }

   public void m_7332_(Entity p_38379_) {
      if (this.m_20363_(p_38379_)) {
         float f = 0.0F;
         float f1 = (float)((this.m_146910_() ? (double)0.01F : this.m_6048_()) + p_38379_.m_6049_());
         if (this.m_20197_().size() > 1) {
            int i = this.m_20197_().indexOf(p_38379_);
            if (i == 0) {
               f = 0.2F;
            } else {
               f = -0.6F;
            }

            if (p_38379_ instanceof Animal) {
               f = (float)((double)f + 0.2D);
            }
         }

         Vec3 vec3 = (new Vec3((double)f, 0.0D, 0.0D)).m_82524_(-this.m_146908_() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
         p_38379_.m_6034_(this.m_20185_() + vec3.f_82479_, this.m_20186_() + (double)f1, this.m_20189_() + vec3.f_82481_);
         p_38379_.m_146922_(p_38379_.m_146908_() + this.f_38266_);
         p_38379_.m_5616_(p_38379_.m_6080_() + this.f_38266_);
         this.m_38321_(p_38379_);
         if (p_38379_ instanceof Animal && this.m_20197_().size() > 1) {
            int j = p_38379_.m_142049_() % 2 == 0 ? 90 : 270;
            p_38379_.m_5618_(((Animal)p_38379_).f_20883_ + (float)j);
            p_38379_.m_5616_(p_38379_.m_6080_() + (float)j);
         }

      }
   }

   public Vec3 m_7688_(LivingEntity p_38357_) {
      Vec3 vec3 = m_19903_((double)(this.m_20205_() * Mth.f_13994_), (double)p_38357_.m_20205_(), p_38357_.m_146908_());
      double d0 = this.m_20185_() + vec3.f_82479_;
      double d1 = this.m_20189_() + vec3.f_82481_;
      BlockPos blockpos = new BlockPos(d0, this.m_142469_().f_82292_, d1);
      BlockPos blockpos1 = blockpos.m_7495_();
      if (!this.f_19853_.m_46801_(blockpos1)) {
         List<Vec3> list = Lists.newArrayList();
         double d2 = this.f_19853_.m_45573_(blockpos);
         if (DismountHelper.m_38439_(d2)) {
            list.add(new Vec3(d0, (double)blockpos.m_123342_() + d2, d1));
         }

         double d3 = this.f_19853_.m_45573_(blockpos1);
         if (DismountHelper.m_38439_(d3)) {
            list.add(new Vec3(d0, (double)blockpos1.m_123342_() + d3, d1));
         }

         for(Pose pose : p_38357_.m_7431_()) {
            for(Vec3 vec31 : list) {
               if (DismountHelper.m_150279_(this.f_19853_, vec31, p_38357_, pose)) {
                  p_38357_.m_20124_(pose);
                  return vec31;
               }
            }
         }
      }

      return super.m_7688_(p_38357_);
   }

   protected void m_38321_(Entity p_38322_) {
      p_38322_.m_5618_(this.m_146908_());
      float f = Mth.m_14177_(p_38322_.m_146908_() - this.m_146908_());
      float f1 = Mth.m_14036_(f, -105.0F, 105.0F);
      p_38322_.f_19859_ += f1 - f;
      p_38322_.m_146922_(p_38322_.m_146908_() + f1 - f);
      p_38322_.m_5616_(p_38322_.m_146908_());
   }

   public void m_7340_(Entity p_38383_) {
      this.m_38321_(p_38383_);
   }

   protected void m_7380_(CompoundTag p_38359_) {
      p_38359_.m_128359_("Type", this.m_38387_().m_38429_());
   }

   protected void m_7378_(CompoundTag p_38338_) {
      if (p_38338_.m_128425_("Type", 8)) {
         this.m_38332_(Boat.Type.m_38432_(p_38338_.m_128461_("Type")));
      }

   }

   public InteractionResult m_6096_(Player p_38330_, InteractionHand p_38331_) {
      if (p_38330_.m_36341_()) {
         return InteractionResult.PASS;
      } else if (this.f_38265_ < 60.0F) {
         if (!this.f_19853_.f_46443_) {
            return p_38330_.m_20329_(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
         } else {
            return InteractionResult.SUCCESS;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   protected void m_7840_(double p_38307_, boolean p_38308_, BlockState p_38309_, BlockPos p_38310_) {
      this.f_38281_ = this.m_20184_().f_82480_;
      if (!this.m_20159_()) {
         if (p_38308_) {
            if (this.f_19789_ > 3.0F) {
               if (this.f_38279_ != Boat.Status.ON_LAND) {
                  this.f_19789_ = 0.0F;
                  return;
               }

               this.m_142535_(this.f_19789_, 1.0F, DamageSource.f_19315_);
               if (!this.f_19853_.f_46443_ && !this.m_146910_()) {
                  this.m_6074_();
                  if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                     for(int i = 0; i < 3; ++i) {
                        this.m_19998_(this.m_38387_().m_38434_());
                     }

                     for(int j = 0; j < 2; ++j) {
                        this.m_19998_(Items.f_42398_);
                     }
                  }
               }
            }

            this.f_19789_ = 0.0F;
         } else if (!this.f_19853_.m_6425_(this.m_142538_().m_7495_()).m_76153_(FluidTags.f_13131_) && p_38307_ < 0.0D) {
            this.f_19789_ = (float)((double)this.f_19789_ - p_38307_);
         }

      }
   }

   public boolean m_38313_(int p_38314_) {
      return this.f_19804_.<Boolean>m_135370_(p_38314_ == 0 ? f_38286_ : f_38287_) && this.m_6688_() != null;
   }

   public void m_38311_(float p_38312_) {
      this.f_19804_.m_135381_(f_38284_, p_38312_);
   }

   public float m_38384_() {
      return this.f_19804_.m_135370_(f_38284_);
   }

   public void m_38354_(int p_38355_) {
      this.f_19804_.m_135381_(f_38282_, p_38355_);
   }

   public int m_38385_() {
      return this.f_19804_.m_135370_(f_38282_);
   }

   private void m_38366_(int p_38367_) {
      this.f_19804_.m_135381_(f_38262_, p_38367_);
   }

   private int m_38397_() {
      return this.f_19804_.m_135370_(f_38262_);
   }

   public float m_38352_(float p_38353_) {
      return Mth.m_14179_(p_38353_, this.f_38261_, this.f_38260_);
   }

   public void m_38362_(int p_38363_) {
      this.f_19804_.m_135381_(f_38283_, p_38363_);
   }

   public int m_38386_() {
      return this.f_19804_.m_135370_(f_38283_);
   }

   public void m_38332_(Boat.Type p_38333_) {
      this.f_19804_.m_135381_(f_38285_, p_38333_.ordinal());
   }

   public Boat.Type m_38387_() {
      return Boat.Type.m_38430_(this.f_19804_.m_135370_(f_38285_));
   }

   protected boolean m_7310_(Entity p_38390_) {
      return this.m_20197_().size() < 2 && !this.m_19941_(FluidTags.f_13131_);
   }

   @Nullable
   public Entity m_6688_() {
      return this.m_146895_();
   }

   public void m_38342_(boolean p_38343_, boolean p_38344_, boolean p_38345_, boolean p_38346_) {
      this.f_38273_ = p_38343_;
      this.f_38274_ = p_38344_;
      this.f_38275_ = p_38345_;
      this.f_38276_ = p_38346_;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public boolean m_5842_() {
      return this.f_38279_ == Boat.Status.UNDER_WATER || this.f_38279_ == Boat.Status.UNDER_FLOWING_WATER;
   }

   // Forge: Fix MC-119811 by instantly completing lerp on board
   @Override
   protected void m_20348_(Entity passenger) {
      super.m_20348_(passenger);
      if (this.m_6109_() && this.f_38267_ > 0) {
         this.f_38267_ = 0;
         this.m_19890_(this.f_38268_, this.f_38269_, this.f_38270_, (float)this.f_38271_, (float)this.f_38272_);
      }
   }

   public ItemStack m_142340_() {
      return new ItemStack(this.m_38369_());
   }

   public static enum Status {
      IN_WATER,
      UNDER_WATER,
      UNDER_FLOWING_WATER,
      ON_LAND,
      IN_AIR;
   }

   public static enum Type {
      OAK(Blocks.f_50705_, "oak"),
      SPRUCE(Blocks.f_50741_, "spruce"),
      BIRCH(Blocks.f_50742_, "birch"),
      JUNGLE(Blocks.f_50743_, "jungle"),
      ACACIA(Blocks.f_50744_, "acacia"),
      DARK_OAK(Blocks.f_50745_, "dark_oak");

      private final String f_38420_;
      private final Block f_38421_;

      private Type(Block p_38427_, String p_38428_) {
         this.f_38420_ = p_38428_;
         this.f_38421_ = p_38427_;
      }

      public String m_38429_() {
         return this.f_38420_;
      }

      public Block m_38434_() {
         return this.f_38421_;
      }

      public String toString() {
         return this.f_38420_;
      }

      public static Boat.Type m_38430_(int p_38431_) {
         Boat.Type[] aboat$type = values();
         if (p_38431_ < 0 || p_38431_ >= aboat$type.length) {
            p_38431_ = 0;
         }

         return aboat$type[p_38431_];
      }

      public static Boat.Type m_38432_(String p_38433_) {
         Boat.Type[] aboat$type = values();

         for(int i = 0; i < aboat$type.length; ++i) {
            if (aboat$type[i].m_38429_().equals(p_38433_)) {
               return aboat$type[i];
            }
         }

         return aboat$type[0];
      }
   }
}
