package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class EnderMan extends Monster implements NeutralMob {
   private static final UUID f_32472_ = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final AttributeModifier f_32481_ = new AttributeModifier(f_32472_, "Attacking speed boost", (double)0.15F, AttributeModifier.Operation.ADDITION);
   private static final int f_149694_ = 400;
   private static final int f_149693_ = 600;
   private static final EntityDataAccessor<Optional<BlockState>> f_32482_ = SynchedEntityData.m_135353_(EnderMan.class, EntityDataSerializers.f_135034_);
   private static final EntityDataAccessor<Boolean> f_32473_ = SynchedEntityData.m_135353_(EnderMan.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> f_32474_ = SynchedEntityData.m_135353_(EnderMan.class, EntityDataSerializers.f_135035_);
   private int f_32476_ = Integer.MIN_VALUE;
   private int f_32477_;
   private static final UniformInt f_32478_ = TimeUtil.m_145020_(20, 39);
   private int f_32479_;
   private UUID f_32480_;

   public EnderMan(EntityType<? extends EnderMan> p_32485_, Level p_32486_) {
      super(p_32485_, p_32486_);
      this.f_19793_ = 1.0F;
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new EnderMan.EndermanFreezeWhenLookedAt(this));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(10, new EnderMan.EndermanLeaveBlockGoal(this));
      this.f_21345_.m_25352_(11, new EnderMan.EndermanTakeBlockGoal(this));
      this.f_21346_.m_25352_(1, new EnderMan.EndermanLookForPlayerGoal(this, this::m_21674_));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, Endermite.class, true, false));
      this.f_21346_.m_25352_(4, new ResetUniversalAngerTargetGoal<>(this, false));
   }

   public static AttributeSupplier.Builder m_32541_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, 7.0D).m_22268_(Attributes.f_22277_, 64.0D);
   }

   public void m_6710_(@Nullable LivingEntity p_32537_) {
      AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
      if (p_32537_ == null) {
         this.f_32477_ = 0;
         this.f_19804_.m_135381_(f_32473_, false);
         this.f_19804_.m_135381_(f_32474_, false);
         attributeinstance.m_22130_(f_32481_);
      } else {
         this.f_32477_ = this.f_19797_;
         this.f_19804_.m_135381_(f_32473_, true);
         if (!attributeinstance.m_22109_(f_32481_)) {
            attributeinstance.m_22118_(f_32481_);
         }
      }

      super.m_6710_(p_32537_); //Forge: Moved down to allow event handlers to write data manager values.
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_32482_, Optional.empty());
      this.f_19804_.m_135372_(f_32473_, false);
      this.f_19804_.m_135372_(f_32474_, false);
   }

   public void m_6825_() {
      this.m_7870_(f_32478_.m_142270_(this.f_19796_));
   }

   public void m_7870_(int p_32515_) {
      this.f_32479_ = p_32515_;
   }

   public int m_6784_() {
      return this.f_32479_;
   }

   public void m_6925_(@Nullable UUID p_32509_) {
      this.f_32480_ = p_32509_;
   }

   public UUID m_6120_() {
      return this.f_32480_;
   }

   public void m_32528_() {
      if (this.f_19797_ >= this.f_32476_ + 400) {
         this.f_32476_ = this.f_19797_;
         if (!this.m_20067_()) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20188_(), this.m_20189_(), SoundEvents.f_11851_, this.m_5720_(), 2.5F, 1.0F, false);
         }
      }

   }

   public void m_7350_(EntityDataAccessor<?> p_32513_) {
      if (f_32473_.equals(p_32513_) && this.m_32532_() && this.f_19853_.f_46443_) {
         this.m_32528_();
      }

      super.m_7350_(p_32513_);
   }

   public void m_7380_(CompoundTag p_32520_) {
      super.m_7380_(p_32520_);
      BlockState blockstate = this.m_32530_();
      if (blockstate != null) {
         p_32520_.m_128365_("carriedBlockState", NbtUtils.m_129202_(blockstate));
      }

      this.m_21678_(p_32520_);
   }

   public void m_7378_(CompoundTag p_32511_) {
      super.m_7378_(p_32511_);
      BlockState blockstate = null;
      if (p_32511_.m_128425_("carriedBlockState", 10)) {
         blockstate = NbtUtils.m_129241_(p_32511_.m_128469_("carriedBlockState"));
         if (blockstate.m_60795_()) {
            blockstate = null;
         }
      }

      this.m_32521_(blockstate);
      this.m_147285_(this.f_19853_, p_32511_);
   }

   boolean m_32534_(Player p_32535_) {
      ItemStack itemstack = p_32535_.m_150109_().f_35975_.get(3);
      if (itemstack.isEnderMask(p_32535_, this)) {
         return false;
      } else {
         Vec3 vec3 = p_32535_.m_20252_(1.0F).m_82541_();
         Vec3 vec31 = new Vec3(this.m_20185_() - p_32535_.m_20185_(), this.m_20188_() - p_32535_.m_20188_(), this.m_20189_() - p_32535_.m_20189_());
         double d0 = vec31.m_82553_();
         vec31 = vec31.m_82541_();
         double d1 = vec3.m_82526_(vec31);
         return d1 > 1.0D - 0.025D / d0 ? p_32535_.m_142582_(this) : false;
      }
   }

   protected float m_6431_(Pose p_32517_, EntityDimensions p_32518_) {
      return 2.55F;
   }

   public void m_8107_() {
      if (this.f_19853_.f_46443_) {
         for(int i = 0; i < 2; ++i) {
            this.f_19853_.m_7106_(ParticleTypes.f_123760_, this.m_20208_(0.5D), this.m_20187_() - 0.25D, this.m_20262_(0.5D), (this.f_19796_.nextDouble() - 0.5D) * 2.0D, -this.f_19796_.nextDouble(), (this.f_19796_.nextDouble() - 0.5D) * 2.0D);
         }
      }

      this.f_20899_ = false;
      if (!this.f_19853_.f_46443_) {
         this.m_21666_((ServerLevel)this.f_19853_, true);
      }

      super.m_8107_();
   }

   public boolean m_6126_() {
      return true;
   }

   protected void m_8024_() {
      if (this.f_19853_.m_46461_() && this.f_19797_ >= this.f_32477_ + 600) {
         float f = this.m_6073_();
         if (f > 0.5F && this.f_19853_.m_45527_(this.m_142538_()) && this.f_19796_.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
            this.m_6710_((LivingEntity)null);
            this.m_32529_();
         }
      }

      super.m_8024_();
   }

   protected boolean m_32529_() {
      if (!this.f_19853_.m_5776_() && this.m_6084_()) {
         double d0 = this.m_20185_() + (this.f_19796_.nextDouble() - 0.5D) * 64.0D;
         double d1 = this.m_20186_() + (double)(this.f_19796_.nextInt(64) - 32);
         double d2 = this.m_20189_() + (this.f_19796_.nextDouble() - 0.5D) * 64.0D;
         return this.m_32543_(d0, d1, d2);
      } else {
         return false;
      }
   }

   boolean m_32500_(Entity p_32501_) {
      Vec3 vec3 = new Vec3(this.m_20185_() - p_32501_.m_20185_(), this.m_20227_(0.5D) - p_32501_.m_20188_(), this.m_20189_() - p_32501_.m_20189_());
      vec3 = vec3.m_82541_();
      double d0 = 16.0D;
      double d1 = this.m_20185_() + (this.f_19796_.nextDouble() - 0.5D) * 8.0D - vec3.f_82479_ * 16.0D;
      double d2 = this.m_20186_() + (double)(this.f_19796_.nextInt(16) - 8) - vec3.f_82480_ * 16.0D;
      double d3 = this.m_20189_() + (this.f_19796_.nextDouble() - 0.5D) * 8.0D - vec3.f_82481_ * 16.0D;
      return this.m_32543_(d1, d2, d3);
   }

   private boolean m_32543_(double p_32544_, double p_32545_, double p_32546_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);

      while(blockpos$mutableblockpos.m_123342_() > this.f_19853_.m_141937_() && !this.f_19853_.m_8055_(blockpos$mutableblockpos).m_60767_().m_76334_()) {
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
      }

      BlockState blockstate = this.f_19853_.m_8055_(blockpos$mutableblockpos);
      boolean flag = blockstate.m_60767_().m_76334_();
      boolean flag1 = blockstate.m_60819_().m_76153_(FluidTags.f_13131_);
      if (flag && !flag1) {
         net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(this, p_32544_, p_32545_, p_32546_);
         if (event.isCanceled()) return false;
         boolean flag2 = this.m_20984_(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
         if (flag2 && !this.m_20067_()) {
            this.f_19853_.m_6263_((Player)null, this.f_19854_, this.f_19855_, this.f_19856_, SoundEvents.f_11852_, this.m_5720_(), 1.0F, 1.0F);
            this.m_5496_(SoundEvents.f_11852_, 1.0F, 1.0F);
         }

         return flag2;
      } else {
         return false;
      }
   }

   protected SoundEvent m_7515_() {
      return this.m_32531_() ? SoundEvents.f_11850_ : SoundEvents.f_11899_;
   }

   protected SoundEvent m_7975_(DamageSource p_32527_) {
      return SoundEvents.f_11849_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11900_;
   }

   protected void m_7472_(DamageSource p_32497_, int p_32498_, boolean p_32499_) {
      super.m_7472_(p_32497_, p_32498_, p_32499_);
      BlockState blockstate = this.m_32530_();
      if (blockstate != null) {
         this.m_19998_(blockstate.m_60734_());
      }

   }

   public void m_32521_(@Nullable BlockState p_32522_) {
      this.f_19804_.m_135381_(f_32482_, Optional.ofNullable(p_32522_));
   }

   @Nullable
   public BlockState m_32530_() {
      return this.f_19804_.m_135370_(f_32482_).orElse((BlockState)null);
   }

   public boolean m_6469_(DamageSource p_32494_, float p_32495_) {
      if (this.m_6673_(p_32494_)) {
         return false;
      } else if (p_32494_ instanceof IndirectEntityDamageSource) {
         for(int i = 0; i < 64; ++i) {
            if (this.m_32529_()) {
               return true;
            }
         }

         return false;
      } else {
         boolean flag = super.m_6469_(p_32494_, p_32495_);
         if (!this.f_19853_.m_5776_() && !(p_32494_.m_7639_() instanceof LivingEntity) && this.f_19796_.nextInt(10) != 0) {
            this.m_32529_();
         }

         return flag;
      }
   }

   public boolean m_32531_() {
      return this.f_19804_.m_135370_(f_32473_);
   }

   public boolean m_32532_() {
      return this.f_19804_.m_135370_(f_32474_);
   }

   public void m_32533_() {
      this.f_19804_.m_135381_(f_32474_, true);
   }

   public boolean m_8023_() {
      return super.m_8023_() || this.m_32530_() != null;
   }

   static class EndermanFreezeWhenLookedAt extends Goal {
      private final EnderMan f_32547_;
      private LivingEntity f_32548_;

      public EndermanFreezeWhenLookedAt(EnderMan p_32550_) {
         this.f_32547_ = p_32550_;
         this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         this.f_32548_ = this.f_32547_.m_5448_();
         if (!(this.f_32548_ instanceof Player)) {
            return false;
         } else {
            double d0 = this.f_32548_.m_20280_(this.f_32547_);
            return d0 > 256.0D ? false : this.f_32547_.m_32534_((Player)this.f_32548_);
         }
      }

      public void m_8056_() {
         this.f_32547_.m_21573_().m_26573_();
      }

      public void m_8037_() {
         this.f_32547_.m_21563_().m_24946_(this.f_32548_.m_20185_(), this.f_32548_.m_20188_(), this.f_32548_.m_20189_());
      }
   }

   static class EndermanLeaveBlockGoal extends Goal {
      private final EnderMan f_32554_;

      public EndermanLeaveBlockGoal(EnderMan p_32556_) {
         this.f_32554_ = p_32556_;
      }

      public boolean m_8036_() {
         if (this.f_32554_.m_32530_() == null) {
            return false;
         } else if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_32554_.f_19853_, this.f_32554_)) {
            return false;
         } else {
            return this.f_32554_.m_21187_().nextInt(2000) == 0;
         }
      }

      public void m_8037_() {
         Random random = this.f_32554_.m_21187_();
         Level level = this.f_32554_.f_19853_;
         int i = Mth.m_14107_(this.f_32554_.m_20185_() - 1.0D + random.nextDouble() * 2.0D);
         int j = Mth.m_14107_(this.f_32554_.m_20186_() + random.nextDouble() * 2.0D);
         int k = Mth.m_14107_(this.f_32554_.m_20189_() - 1.0D + random.nextDouble() * 2.0D);
         BlockPos blockpos = new BlockPos(i, j, k);
         BlockState blockstate = level.m_8055_(blockpos);
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate1 = level.m_8055_(blockpos1);
         BlockState blockstate2 = this.f_32554_.m_32530_();
         if (blockstate2 != null) {
            blockstate2 = Block.m_49931_(blockstate2, this.f_32554_.f_19853_, blockpos);
            if (this.m_32558_(level, blockpos, blockstate2, blockstate, blockstate1, blockpos1) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(f_32554_, net.minecraftforge.common.util.BlockSnapshot.create(level.m_46472_(), level, blockpos1), net.minecraft.core.Direction.UP)) {
               level.m_7731_(blockpos, blockstate2, 3);
               level.m_142346_(this.f_32554_, GameEvent.f_157797_, blockpos);
               this.f_32554_.m_32521_((BlockState)null);
            }

         }
      }

      private boolean m_32558_(Level p_32559_, BlockPos p_32560_, BlockState p_32561_, BlockState p_32562_, BlockState p_32563_, BlockPos p_32564_) {
         return p_32562_.m_60795_() && !p_32563_.m_60795_() && !p_32563_.m_60713_(Blocks.f_50752_) && !p_32563_.m_60620_(net.minecraftforge.common.Tags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST) && p_32563_.m_60838_(p_32559_, p_32564_) && p_32561_.m_60710_(p_32559_, p_32560_) && p_32559_.m_45933_(this.f_32554_, AABB.m_82333_(Vec3.m_82528_(p_32560_))).isEmpty();
      }
   }

   static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
      private final EnderMan f_32566_;
      private Player f_32567_;
      private int f_32568_;
      private int f_32569_;
      private final TargetingConditions f_32570_;
      private final TargetingConditions f_32571_ = TargetingConditions.m_148352_().m_148355_();

      public EndermanLookForPlayerGoal(EnderMan p_32573_, @Nullable Predicate<LivingEntity> p_32574_) {
         super(p_32573_, Player.class, 10, false, false, p_32574_);
         this.f_32566_ = p_32573_;
         this.f_32570_ = TargetingConditions.m_148352_().m_26883_(this.m_7623_()).m_26888_((p_32578_) -> {
            return p_32573_.m_32534_((Player)p_32578_);
         });
      }

      public boolean m_8036_() {
         this.f_32567_ = this.f_32566_.f_19853_.m_45946_(this.f_32570_, this.f_32566_);
         return this.f_32567_ != null;
      }

      public void m_8056_() {
         this.f_32568_ = 5;
         this.f_32569_ = 0;
         this.f_32566_.m_32533_();
      }

      public void m_8041_() {
         this.f_32567_ = null;
         super.m_8041_();
      }

      public boolean m_8045_() {
         if (this.f_32567_ != null) {
            if (!this.f_32566_.m_32534_(this.f_32567_)) {
               return false;
            } else {
               this.f_32566_.m_21391_(this.f_32567_, 10.0F, 10.0F);
               return true;
            }
         } else {
            return this.f_26050_ != null && this.f_32571_.m_26885_(this.f_32566_, this.f_26050_) ? true : super.m_8045_();
         }
      }

      public void m_8037_() {
         if (this.f_32566_.m_5448_() == null) {
            super.m_26070_((LivingEntity)null);
         }

         if (this.f_32567_ != null) {
            if (--this.f_32568_ <= 0) {
               this.f_26050_ = this.f_32567_;
               this.f_32567_ = null;
               super.m_8056_();
            }
         } else {
            if (this.f_26050_ != null && !this.f_32566_.m_20159_()) {
               if (this.f_32566_.m_32534_((Player)this.f_26050_)) {
                  if (this.f_26050_.m_20280_(this.f_32566_) < 16.0D) {
                     this.f_32566_.m_32529_();
                  }

                  this.f_32569_ = 0;
               } else if (this.f_26050_.m_20280_(this.f_32566_) > 256.0D && this.f_32569_++ >= 30 && this.f_32566_.m_32500_(this.f_26050_)) {
                  this.f_32569_ = 0;
               }
            }

            super.m_8037_();
         }

      }
   }

   static class EndermanTakeBlockGoal extends Goal {
      private final EnderMan f_32583_;

      public EndermanTakeBlockGoal(EnderMan p_32585_) {
         this.f_32583_ = p_32585_;
      }

      public boolean m_8036_() {
         if (this.f_32583_.m_32530_() != null) {
            return false;
         } else if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_32583_.f_19853_, this.f_32583_)) {
            return false;
         } else {
            return this.f_32583_.m_21187_().nextInt(20) == 0;
         }
      }

      public void m_8037_() {
         Random random = this.f_32583_.m_21187_();
         Level level = this.f_32583_.f_19853_;
         int i = Mth.m_14107_(this.f_32583_.m_20185_() - 2.0D + random.nextDouble() * 4.0D);
         int j = Mth.m_14107_(this.f_32583_.m_20186_() + random.nextDouble() * 3.0D);
         int k = Mth.m_14107_(this.f_32583_.m_20189_() - 2.0D + random.nextDouble() * 4.0D);
         BlockPos blockpos = new BlockPos(i, j, k);
         BlockState blockstate = level.m_8055_(blockpos);
         Vec3 vec3 = new Vec3((double)this.f_32583_.m_146903_() + 0.5D, (double)j + 0.5D, (double)this.f_32583_.m_146907_() + 0.5D);
         Vec3 vec31 = new Vec3((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
         BlockHitResult blockhitresult = level.m_45547_(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this.f_32583_));
         boolean flag = blockhitresult.m_82425_().equals(blockpos);
         if (blockstate.m_60620_(BlockTags.f_13046_) && flag) {
            level.m_7471_(blockpos, false);
            level.m_142346_(this.f_32583_, GameEvent.f_157794_, blockpos);
            this.f_32583_.m_32521_(blockstate.m_60734_().m_49966_());
         }

      }
   }
}
