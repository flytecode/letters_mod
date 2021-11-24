package net.minecraft.world.entity.animal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Panda extends Animal {
   private static final EntityDataAccessor<Integer> f_29073_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_29074_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_29075_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Byte> f_29076_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Byte> f_29077_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Byte> f_29078_ = SynchedEntityData.m_135353_(Panda.class, EntityDataSerializers.f_135027_);
   static final TargetingConditions f_29079_ = TargetingConditions.m_148353_().m_26883_(8.0D);
   private static final int f_148960_ = 2;
   private static final int f_148961_ = 4;
   private static final int f_148962_ = 8;
   private static final int f_148963_ = 16;
   private static final int f_148964_ = 5;
   public static final int f_148959_ = 32;
   private static final int f_148965_ = 32;
   boolean f_29080_;
   boolean f_29081_;
   public int f_29072_;
   private Vec3 f_29082_;
   private float f_29083_;
   private float f_29065_;
   private float f_29066_;
   private float f_29067_;
   private float f_29068_;
   private float f_29069_;
   Panda.PandaLookAtPlayerGoal f_29070_;
   static final Predicate<ItemEntity> f_29071_ = (p_29133_) -> {
      ItemStack itemstack = p_29133_.m_32055_();
      return (itemstack.m_150930_(Blocks.f_50571_.m_5456_()) || itemstack.m_150930_(Blocks.f_50145_.m_5456_())) && p_29133_.m_6084_() && !p_29133_.m_32063_();
   };

   public Panda(EntityType<? extends Panda> p_29086_, Level p_29087_) {
      super(p_29086_, p_29087_);
      this.f_21342_ = new Panda.PandaMoveControl(this);
      if (!this.m_6162_()) {
         this.m_21553_(true);
      }

   }

   public boolean m_7066_(ItemStack p_29146_) {
      EquipmentSlot equipmentslot = Mob.m_147233_(p_29146_);
      if (!this.m_6844_(equipmentslot).m_41619_()) {
         return false;
      } else {
         return equipmentslot == EquipmentSlot.MAINHAND && super.m_7066_(p_29146_);
      }
   }

   public int m_29148_() {
      return this.f_19804_.m_135370_(f_29073_);
   }

   public void m_29206_(int p_29207_) {
      this.f_19804_.m_135381_(f_29073_, p_29207_);
   }

   public boolean m_29149_() {
      return this.m_29218_(2);
   }

   public boolean m_29150_() {
      return this.m_29218_(8);
   }

   public void m_29208_(boolean p_29209_) {
      this.m_29134_(8, p_29209_);
   }

   public boolean m_29151_() {
      return this.m_29218_(16);
   }

   public void m_29212_(boolean p_29213_) {
      this.m_29134_(16, p_29213_);
   }

   public boolean m_29152_() {
      return this.f_19804_.m_135370_(f_29075_) > 0;
   }

   public void m_29216_(boolean p_29217_) {
      this.f_19804_.m_135381_(f_29075_, p_29217_ ? 1 : 0);
   }

   private int m_29170_() {
      return this.f_19804_.m_135370_(f_29075_);
   }

   private void m_29214_(int p_29215_) {
      this.f_19804_.m_135381_(f_29075_, p_29215_);
   }

   public void m_29220_(boolean p_29221_) {
      this.m_29134_(2, p_29221_);
      if (!p_29221_) {
         this.m_29210_(0);
      }

   }

   public int m_29153_() {
      return this.f_19804_.m_135370_(f_29074_);
   }

   public void m_29210_(int p_29211_) {
      this.f_19804_.m_135381_(f_29074_, p_29211_);
   }

   public Panda.Gene m_29154_() {
      return Panda.Gene.m_29248_(this.f_19804_.m_135370_(f_29076_));
   }

   public void m_29099_(Panda.Gene p_29100_) {
      if (p_29100_.m_29247_() > 6) {
         p_29100_ = Panda.Gene.m_29255_(this.f_19796_);
      }

      this.f_19804_.m_135381_(f_29076_, (byte)p_29100_.m_29247_());
   }

   public Panda.Gene m_29155_() {
      return Panda.Gene.m_29248_(this.f_19804_.m_135370_(f_29077_));
   }

   public void m_29116_(Panda.Gene p_29117_) {
      if (p_29117_.m_29247_() > 6) {
         p_29117_ = Panda.Gene.m_29255_(this.f_19796_);
      }

      this.f_19804_.m_135381_(f_29077_, (byte)p_29117_.m_29247_());
   }

   public boolean m_29156_() {
      return this.m_29218_(4);
   }

   public void m_29222_(boolean p_29223_) {
      this.m_29134_(4, p_29223_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29073_, 0);
      this.f_19804_.m_135372_(f_29074_, 0);
      this.f_19804_.m_135372_(f_29076_, (byte)0);
      this.f_19804_.m_135372_(f_29077_, (byte)0);
      this.f_19804_.m_135372_(f_29078_, (byte)0);
      this.f_19804_.m_135372_(f_29075_, 0);
   }

   private boolean m_29218_(int p_29219_) {
      return (this.f_19804_.m_135370_(f_29078_) & p_29219_) != 0;
   }

   private void m_29134_(int p_29135_, boolean p_29136_) {
      byte b0 = this.f_19804_.m_135370_(f_29078_);
      if (p_29136_) {
         this.f_19804_.m_135381_(f_29078_, (byte)(b0 | p_29135_));
      } else {
         this.f_19804_.m_135381_(f_29078_, (byte)(b0 & ~p_29135_));
      }

   }

   public void m_7380_(CompoundTag p_29129_) {
      super.m_7380_(p_29129_);
      p_29129_.m_128359_("MainGene", this.m_29154_().m_29257_());
      p_29129_.m_128359_("HiddenGene", this.m_29155_().m_29257_());
   }

   public void m_7378_(CompoundTag p_29115_) {
      super.m_7378_(p_29115_);
      this.m_29099_(Panda.Gene.m_29253_(p_29115_.m_128461_("MainGene")));
      this.m_29116_(Panda.Gene.m_29253_(p_29115_.m_128461_("HiddenGene")));
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_148967_, AgeableMob p_148968_) {
      Panda panda = EntityType.f_20507_.m_20615_(p_148967_);
      if (p_148968_ instanceof Panda) {
         panda.m_29103_(this, (Panda)p_148968_);
      }

      panda.m_29166_();
      return panda;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new Panda.PandaPanicGoal(this, 2.0D));
      this.f_21345_.m_25352_(2, new Panda.PandaBreedGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new Panda.PandaAttackGoal(this, (double)1.2F, true));
      this.f_21345_.m_25352_(4, new TemptGoal(this, 1.0D, Ingredient.m_43929_(Blocks.f_50571_.m_5456_()), false));
      this.f_21345_.m_25352_(6, new Panda.PandaAvoidGoal<>(this, Player.class, 8.0F, 2.0D, 2.0D));
      this.f_21345_.m_25352_(6, new Panda.PandaAvoidGoal<>(this, Monster.class, 4.0F, 2.0D, 2.0D));
      this.f_21345_.m_25352_(7, new Panda.PandaSitGoal());
      this.f_21345_.m_25352_(8, new Panda.PandaLieOnBackGoal(this));
      this.f_21345_.m_25352_(8, new Panda.PandaSneezeGoal(this));
      this.f_29070_ = new Panda.PandaLookAtPlayerGoal(this, Player.class, 6.0F);
      this.f_21345_.m_25352_(9, this.f_29070_);
      this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(12, new Panda.PandaRollGoal(this));
      this.f_21345_.m_25352_(13, new FollowParentGoal(this, 1.25D));
      this.f_21345_.m_25352_(14, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21346_.m_25352_(1, (new Panda.PandaHurtByTargetGoal(this)).m_26044_(new Class[0]));
   }

   public static AttributeSupplier.Builder m_29157_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, (double)0.15F).m_22268_(Attributes.f_22281_, 6.0D);
   }

   public Panda.Gene m_29158_() {
      return Panda.Gene.m_29260_(this.m_29154_(), this.m_29155_());
   }

   public boolean m_29161_() {
      return this.m_29158_() == Panda.Gene.LAZY;
   }

   public boolean m_29162_() {
      return this.m_29158_() == Panda.Gene.WORRIED;
   }

   public boolean m_29163_() {
      return this.m_29158_() == Panda.Gene.PLAYFUL;
   }

   public boolean m_148973_() {
      return this.m_29158_() == Panda.Gene.BROWN;
   }

   public boolean m_29164_() {
      return this.m_29158_() == Panda.Gene.WEAK;
   }

   public boolean m_5912_() {
      return this.m_29158_() == Panda.Gene.AGGRESSIVE;
   }

   public boolean m_6573_(Player p_29107_) {
      return false;
   }

   public boolean m_7327_(Entity p_29091_) {
      this.m_5496_(SoundEvents.f_12187_, 1.0F, 1.0F);
      if (!this.m_5912_()) {
         this.f_29081_ = true;
      }

      return super.m_7327_(p_29091_);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_29162_()) {
         if (this.f_19853_.m_46470_() && !this.m_20069_()) {
            this.m_29208_(true);
            this.m_29216_(false);
         } else if (!this.m_29152_()) {
            this.m_29208_(false);
         }
      }

      if (this.m_5448_() == null) {
         this.f_29080_ = false;
         this.f_29081_ = false;
      }

      if (this.m_29148_() > 0) {
         if (this.m_5448_() != null) {
            this.m_21391_(this.m_5448_(), 90.0F, 90.0F);
         }

         if (this.m_29148_() == 29 || this.m_29148_() == 14) {
            this.m_5496_(SoundEvents.f_12183_, 1.0F, 1.0F);
         }

         this.m_29206_(this.m_29148_() - 1);
      }

      if (this.m_29149_()) {
         this.m_29210_(this.m_29153_() + 1);
         if (this.m_29153_() > 20) {
            this.m_29220_(false);
            this.m_29177_();
         } else if (this.m_29153_() == 1) {
            this.m_5496_(SoundEvents.f_12177_, 1.0F, 1.0F);
         }
      }

      if (this.m_29156_()) {
         this.m_29176_();
      } else {
         this.f_29072_ = 0;
      }

      if (this.m_29150_()) {
         this.m_146926_(0.0F);
      }

      this.m_29173_();
      this.m_29171_();
      this.m_29174_();
      this.m_29175_();
   }

   public boolean m_29165_() {
      return this.m_29162_() && this.f_19853_.m_46470_();
   }

   private void m_29171_() {
      if (!this.m_29152_() && this.m_29150_() && !this.m_29165_() && !this.m_6844_(EquipmentSlot.MAINHAND).m_41619_() && this.f_19796_.nextInt(80) == 1) {
         this.m_29216_(true);
      } else if (this.m_6844_(EquipmentSlot.MAINHAND).m_41619_() || !this.m_29150_()) {
         this.m_29216_(false);
      }

      if (this.m_29152_()) {
         this.m_29172_();
         if (!this.f_19853_.f_46443_ && this.m_29170_() > 80 && this.f_19796_.nextInt(20) == 1) {
            if (this.m_29170_() > 100 && this.m_29195_(this.m_6844_(EquipmentSlot.MAINHAND))) {
               if (!this.f_19853_.f_46443_) {
                  this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
                  this.m_146859_(GameEvent.f_157806_, this.m_146901_());
               }

               this.m_29208_(false);
            }

            this.m_29216_(false);
            return;
         }

         this.m_29214_(this.m_29170_() + 1);
      }

   }

   private void m_29172_() {
      if (this.m_29170_() % 5 == 0) {
         this.m_5496_(SoundEvents.f_12181_, 0.5F + 0.5F * (float)this.f_19796_.nextInt(2), (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);

         for(int i = 0; i < 6; ++i) {
            Vec3 vec3 = new Vec3(((double)this.f_19796_.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double)this.f_19796_.nextFloat() - 0.5D) * 0.1D);
            vec3 = vec3.m_82496_(-this.m_146909_() * ((float)Math.PI / 180F));
            vec3 = vec3.m_82524_(-this.m_146908_() * ((float)Math.PI / 180F));
            double d0 = (double)(-this.f_19796_.nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double)this.f_19796_.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double)this.f_19796_.nextFloat() - 0.5D) * 0.4D);
            vec31 = vec31.m_82524_(-this.f_20883_ * ((float)Math.PI / 180F));
            vec31 = vec31.m_82520_(this.m_20185_(), this.m_20188_() + 1.0D, this.m_20189_());
            this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, this.m_6844_(EquipmentSlot.MAINHAND)), vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, vec3.f_82479_, vec3.f_82480_ + 0.05D, vec3.f_82481_);
         }
      }

   }

   private void m_29173_() {
      this.f_29065_ = this.f_29083_;
      if (this.m_29150_()) {
         this.f_29083_ = Math.min(1.0F, this.f_29083_ + 0.15F);
      } else {
         this.f_29083_ = Math.max(0.0F, this.f_29083_ - 0.19F);
      }

   }

   private void m_29174_() {
      this.f_29067_ = this.f_29066_;
      if (this.m_29151_()) {
         this.f_29066_ = Math.min(1.0F, this.f_29066_ + 0.15F);
      } else {
         this.f_29066_ = Math.max(0.0F, this.f_29066_ - 0.19F);
      }

   }

   private void m_29175_() {
      this.f_29069_ = this.f_29068_;
      if (this.m_29156_()) {
         this.f_29068_ = Math.min(1.0F, this.f_29068_ + 0.15F);
      } else {
         this.f_29068_ = Math.max(0.0F, this.f_29068_ - 0.19F);
      }

   }

   public float m_29224_(float p_29225_) {
      return Mth.m_14179_(p_29225_, this.f_29065_, this.f_29083_);
   }

   public float m_29226_(float p_29227_) {
      return Mth.m_14179_(p_29227_, this.f_29067_, this.f_29066_);
   }

   public float m_29088_(float p_29089_) {
      return Mth.m_14179_(p_29089_, this.f_29069_, this.f_29068_);
   }

   private void m_29176_() {
      ++this.f_29072_;
      if (this.f_29072_ > 32) {
         this.m_29222_(false);
      } else {
         if (!this.f_19853_.f_46443_) {
            Vec3 vec3 = this.m_20184_();
            if (this.f_29072_ == 1) {
               float f = this.m_146908_() * ((float)Math.PI / 180F);
               float f1 = this.m_6162_() ? 0.1F : 0.2F;
               this.f_29082_ = new Vec3(vec3.f_82479_ + (double)(-Mth.m_14031_(f) * f1), 0.0D, vec3.f_82481_ + (double)(Mth.m_14089_(f) * f1));
               this.m_20256_(this.f_29082_.m_82520_(0.0D, 0.27D, 0.0D));
            } else if ((float)this.f_29072_ != 7.0F && (float)this.f_29072_ != 15.0F && (float)this.f_29072_ != 23.0F) {
               this.m_20334_(this.f_29082_.f_82479_, vec3.f_82480_, this.f_29082_.f_82481_);
            } else {
               this.m_20334_(0.0D, this.f_19861_ ? 0.27D : vec3.f_82480_, 0.0D);
            }
         }

      }
   }

   private void m_29177_() {
      Vec3 vec3 = this.m_20184_();
      this.f_19853_.m_7106_(ParticleTypes.f_123763_, this.m_20185_() - (double)(this.m_20205_() + 1.0F) * 0.5D * (double)Mth.m_14031_(this.f_20883_ * ((float)Math.PI / 180F)), this.m_20188_() - (double)0.1F, this.m_20189_() + (double)(this.m_20205_() + 1.0F) * 0.5D * (double)Mth.m_14089_(this.f_20883_ * ((float)Math.PI / 180F)), vec3.f_82479_, 0.0D, vec3.f_82481_);
      this.m_5496_(SoundEvents.f_12178_, 1.0F, 1.0F);

      for(Panda panda : this.f_19853_.m_45976_(Panda.class, this.m_142469_().m_82400_(10.0D))) {
         if (!panda.m_6162_() && panda.f_19861_ && !panda.m_20069_() && panda.m_29167_()) {
            panda.m_6135_();
         }
      }

      if (!this.f_19853_.m_5776_() && this.f_19796_.nextInt(700) == 0 && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_)) {
         this.m_19998_(Items.f_42518_);
      }

   }

   protected void m_7581_(ItemEntity p_29121_) {
      if (this.m_6844_(EquipmentSlot.MAINHAND).m_41619_() && f_29071_.test(p_29121_)) {
         this.m_21053_(p_29121_);
         ItemStack itemstack = p_29121_.m_32055_();
         this.m_8061_(EquipmentSlot.MAINHAND, itemstack);
         this.f_21347_[EquipmentSlot.MAINHAND.m_20749_()] = 2.0F;
         this.m_7938_(p_29121_, itemstack.m_41613_());
         p_29121_.m_146870_();
      }

   }

   public boolean m_6469_(DamageSource p_29097_, float p_29098_) {
      this.m_29208_(false);
      return super.m_6469_(p_29097_, p_29098_);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_29109_, DifficultyInstance p_29110_, MobSpawnType p_29111_, @Nullable SpawnGroupData p_29112_, @Nullable CompoundTag p_29113_) {
      this.m_29099_(Panda.Gene.m_29255_(this.f_19796_));
      this.m_29116_(Panda.Gene.m_29255_(this.f_19796_));
      this.m_29166_();
      if (p_29112_ == null) {
         p_29112_ = new AgeableMob.AgeableMobGroupData(0.2F);
      }

      return super.m_6518_(p_29109_, p_29110_, p_29111_, p_29112_, p_29113_);
   }

   public void m_29103_(Panda p_29104_, @Nullable Panda p_29105_) {
      if (p_29105_ == null) {
         if (this.f_19796_.nextBoolean()) {
            this.m_29099_(p_29104_.m_29178_());
            this.m_29116_(Panda.Gene.m_29255_(this.f_19796_));
         } else {
            this.m_29099_(Panda.Gene.m_29255_(this.f_19796_));
            this.m_29116_(p_29104_.m_29178_());
         }
      } else if (this.f_19796_.nextBoolean()) {
         this.m_29099_(p_29104_.m_29178_());
         this.m_29116_(p_29105_.m_29178_());
      } else {
         this.m_29099_(p_29105_.m_29178_());
         this.m_29116_(p_29104_.m_29178_());
      }

      if (this.f_19796_.nextInt(32) == 0) {
         this.m_29099_(Panda.Gene.m_29255_(this.f_19796_));
      }

      if (this.f_19796_.nextInt(32) == 0) {
         this.m_29116_(Panda.Gene.m_29255_(this.f_19796_));
      }

   }

   private Panda.Gene m_29178_() {
      return this.f_19796_.nextBoolean() ? this.m_29154_() : this.m_29155_();
   }

   public void m_29166_() {
      if (this.m_29164_()) {
         this.m_21051_(Attributes.f_22276_).m_22100_(10.0D);
      }

      if (this.m_29161_()) {
         this.m_21051_(Attributes.f_22279_).m_22100_((double)0.07F);
      }

   }

   void m_29179_() {
      if (!this.m_20069_()) {
         this.m_21564_(0.0F);
         this.m_21573_().m_26573_();
         this.m_29208_(true);
      }

   }

   public InteractionResult m_6071_(Player p_29123_, InteractionHand p_29124_) {
      ItemStack itemstack = p_29123_.m_21120_(p_29124_);
      if (this.m_29165_()) {
         return InteractionResult.PASS;
      } else if (this.m_29151_()) {
         this.m_29212_(false);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else if (this.m_6898_(itemstack)) {
         if (this.m_5448_() != null) {
            this.f_29080_ = true;
         }

         if (this.m_6162_()) {
            this.m_142075_(p_29123_, p_29124_, itemstack);
            this.m_146740_((int)((float)(-this.m_146764_() / 20) * 0.1F), true);
            this.m_146859_(GameEvent.f_157771_, this.m_146901_());
         } else if (!this.f_19853_.f_46443_ && this.m_146764_() == 0 && this.m_5957_()) {
            this.m_142075_(p_29123_, p_29124_, itemstack);
            this.m_27595_(p_29123_);
            this.m_146859_(GameEvent.f_157771_, this.m_146901_());
         } else {
            if (this.f_19853_.f_46443_ || this.m_29150_() || this.m_20069_()) {
               return InteractionResult.PASS;
            }

            this.m_29179_();
            this.m_29216_(true);
            ItemStack itemstack1 = this.m_6844_(EquipmentSlot.MAINHAND);
            if (!itemstack1.m_41619_() && !p_29123_.m_150110_().f_35937_) {
               this.m_19983_(itemstack1);
            }

            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(itemstack.m_41720_(), 1));
            this.m_142075_(p_29123_, p_29124_, itemstack);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Nullable
   protected SoundEvent m_7515_() {
      if (this.m_5912_()) {
         return SoundEvents.f_12184_;
      } else {
         return this.m_29162_() ? SoundEvents.f_12185_ : SoundEvents.f_12179_;
      }
   }

   protected void m_7355_(BlockPos p_29126_, BlockState p_29127_) {
      this.m_5496_(SoundEvents.f_12182_, 0.15F, 1.0F);
   }

   public boolean m_6898_(ItemStack p_29192_) {
      return p_29192_.m_150930_(Blocks.f_50571_.m_5456_());
   }

   private boolean m_29195_(ItemStack p_29196_) {
      return this.m_6898_(p_29196_) || p_29196_.m_150930_(Blocks.f_50145_.m_5456_());
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_12180_;
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource p_29142_) {
      return SoundEvents.f_12186_;
   }

   public boolean m_29167_() {
      return !this.m_29151_() && !this.m_29165_() && !this.m_29152_() && !this.m_29156_() && !this.m_29150_();
   }

   public static enum Gene {
      NORMAL(0, "normal", false),
      LAZY(1, "lazy", false),
      WORRIED(2, "worried", false),
      PLAYFUL(3, "playful", false),
      BROWN(4, "brown", true),
      WEAK(5, "weak", true),
      AGGRESSIVE(6, "aggressive", false);

      private static final Panda.Gene[] f_29235_ = Arrays.stream(values()).sorted(Comparator.comparingInt(Panda.Gene::m_29247_)).toArray((p_29259_) -> {
         return new Panda.Gene[p_29259_];
      });
      private static final int f_148982_ = 6;
      private final int f_29236_;
      private final String f_29237_;
      private final boolean f_29238_;

      private Gene(int p_29244_, String p_29245_, boolean p_29246_) {
         this.f_29236_ = p_29244_;
         this.f_29237_ = p_29245_;
         this.f_29238_ = p_29246_;
      }

      public int m_29247_() {
         return this.f_29236_;
      }

      public String m_29257_() {
         return this.f_29237_;
      }

      public boolean m_29263_() {
         return this.f_29238_;
      }

      static Panda.Gene m_29260_(Panda.Gene p_29261_, Panda.Gene p_29262_) {
         if (p_29261_.m_29263_()) {
            return p_29261_ == p_29262_ ? p_29261_ : NORMAL;
         } else {
            return p_29261_;
         }
      }

      public static Panda.Gene m_29248_(int p_29249_) {
         if (p_29249_ < 0 || p_29249_ >= f_29235_.length) {
            p_29249_ = 0;
         }

         return f_29235_[p_29249_];
      }

      public static Panda.Gene m_29253_(String p_29254_) {
         for(Panda.Gene panda$gene : values()) {
            if (panda$gene.f_29237_.equals(p_29254_)) {
               return panda$gene;
            }
         }

         return NORMAL;
      }

      public static Panda.Gene m_29255_(Random p_29256_) {
         int i = p_29256_.nextInt(16);
         if (i == 0) {
            return LAZY;
         } else if (i == 1) {
            return WORRIED;
         } else if (i == 2) {
            return PLAYFUL;
         } else if (i == 4) {
            return AGGRESSIVE;
         } else if (i < 9) {
            return WEAK;
         } else {
            return i < 11 ? BROWN : NORMAL;
         }
      }
   }

   static class PandaAttackGoal extends MeleeAttackGoal {
      private final Panda f_29267_;

      public PandaAttackGoal(Panda p_29269_, double p_29270_, boolean p_29271_) {
         super(p_29269_, p_29270_, p_29271_);
         this.f_29267_ = p_29269_;
      }

      public boolean m_8036_() {
         return this.f_29267_.m_29167_() && super.m_8036_();
      }
   }

   static class PandaAvoidGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Panda f_29273_;

      public PandaAvoidGoal(Panda p_29275_, Class<T> p_29276_, float p_29277_, double p_29278_, double p_29279_) {
         super(p_29275_, p_29276_, p_29277_, p_29278_, p_29279_, EntitySelector.f_20408_::test);
         this.f_29273_ = p_29275_;
      }

      public boolean m_8036_() {
         return this.f_29273_.m_29162_() && this.f_29273_.m_29167_() && super.m_8036_();
      }
   }

   class PandaBreedGoal extends BreedGoal {
      private final Panda f_29282_;
      private int f_29283_;

      public PandaBreedGoal(Panda p_29286_, double p_29287_) {
         super(p_29286_, p_29287_);
         this.f_29282_ = p_29286_;
      }

      public boolean m_8036_() {
         if (super.m_8036_() && this.f_29282_.m_29148_() == 0) {
            if (!this.m_29289_()) {
               if (this.f_29283_ <= this.f_29282_.f_19797_) {
                  this.f_29282_.m_29206_(32);
                  this.f_29283_ = this.f_29282_.f_19797_ + 600;
                  if (this.f_29282_.m_6142_()) {
                     Player player = this.f_25114_.m_45946_(Panda.f_29079_, this.f_29282_);
                     this.f_29282_.f_29070_.m_29312_(player);
                  }
               }

               return false;
            } else {
               return true;
            }
         } else {
            return false;
         }
      }

      private boolean m_29289_() {
         BlockPos blockpos = this.f_29282_.m_142538_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 8; ++j) {
               for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                  for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                     blockpos$mutableblockpos.m_122154_(blockpos, k, i, l);
                     if (this.f_25114_.m_8055_(blockpos$mutableblockpos).m_60713_(Blocks.f_50571_)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   static class PandaHurtByTargetGoal extends HurtByTargetGoal {
      private final Panda f_29290_;

      public PandaHurtByTargetGoal(Panda p_29292_, Class<?>... p_29293_) {
         super(p_29292_, p_29293_);
         this.f_29290_ = p_29292_;
      }

      public boolean m_8045_() {
         if (!this.f_29290_.f_29080_ && !this.f_29290_.f_29081_) {
            return super.m_8045_();
         } else {
            this.f_29290_.m_6710_((LivingEntity)null);
            return false;
         }
      }

      protected void m_5766_(Mob p_29295_, LivingEntity p_29296_) {
         if (p_29295_ instanceof Panda && ((Panda)p_29295_).m_5912_()) {
            p_29295_.m_6710_(p_29296_);
         }

      }
   }

   static class PandaLieOnBackGoal extends Goal {
      private final Panda f_29298_;
      private int f_29299_;

      public PandaLieOnBackGoal(Panda p_29301_) {
         this.f_29298_ = p_29301_;
      }

      public boolean m_8036_() {
         return this.f_29299_ < this.f_29298_.f_19797_ && this.f_29298_.m_29161_() && this.f_29298_.m_29167_() && this.f_29298_.f_19796_.nextInt(400) == 1;
      }

      public boolean m_8045_() {
         if (!this.f_29298_.m_20069_() && (this.f_29298_.m_29161_() || this.f_29298_.f_19796_.nextInt(600) != 1)) {
            return this.f_29298_.f_19796_.nextInt(2000) != 1;
         } else {
            return false;
         }
      }

      public void m_8056_() {
         this.f_29298_.m_29212_(true);
         this.f_29299_ = 0;
      }

      public void m_8041_() {
         this.f_29298_.m_29212_(false);
         this.f_29299_ = this.f_29298_.f_19797_ + 200;
      }
   }

   static class PandaLookAtPlayerGoal extends LookAtPlayerGoal {
      private final Panda f_29306_;

      public PandaLookAtPlayerGoal(Panda p_29308_, Class<? extends LivingEntity> p_29309_, float p_29310_) {
         super(p_29308_, p_29309_, p_29310_);
         this.f_29306_ = p_29308_;
      }

      public void m_29312_(LivingEntity p_29313_) {
         this.f_25513_ = p_29313_;
      }

      public boolean m_8045_() {
         return this.f_25513_ != null && super.m_8045_();
      }

      public boolean m_8036_() {
         if (this.f_25512_.m_21187_().nextFloat() >= this.f_25515_) {
            return false;
         } else {
            if (this.f_25513_ == null) {
               if (this.f_25516_ == Player.class) {
                  this.f_25513_ = this.f_25512_.f_19853_.m_45949_(this.f_25517_, this.f_25512_, this.f_25512_.m_20185_(), this.f_25512_.m_20188_(), this.f_25512_.m_20189_());
               } else {
                  this.f_25513_ = this.f_25512_.f_19853_.m_45982_(this.f_25512_.f_19853_.m_6443_(this.f_25516_, this.f_25512_.m_142469_().m_82377_((double)this.f_25514_, 3.0D, (double)this.f_25514_), (p_148985_) -> {
                     return true;
                  }), this.f_25517_, this.f_25512_, this.f_25512_.m_20185_(), this.f_25512_.m_20188_(), this.f_25512_.m_20189_());
               }
            }

            return this.f_29306_.m_29167_() && this.f_25513_ != null;
         }
      }

      public void m_8037_() {
         if (this.f_25513_ != null) {
            super.m_8037_();
         }

      }
   }

   static class PandaMoveControl extends MoveControl {
      private final Panda f_29316_;

      public PandaMoveControl(Panda p_29318_) {
         super(p_29318_);
         this.f_29316_ = p_29318_;
      }

      public void m_8126_() {
         if (this.f_29316_.m_29167_()) {
            super.m_8126_();
         }
      }
   }

   static class PandaPanicGoal extends PanicGoal {
      private final Panda f_29320_;

      public PandaPanicGoal(Panda p_29322_, double p_29323_) {
         super(p_29322_, p_29323_);
         this.f_29320_ = p_29322_;
      }

      public boolean m_8036_() {
         if (!this.f_29320_.m_6060_()) {
            return false;
         } else {
            BlockPos blockpos = this.m_25694_(this.f_25684_.f_19853_, this.f_25684_, 5, 4);
            if (blockpos != null) {
               this.f_25686_ = (double)blockpos.m_123341_();
               this.f_25687_ = (double)blockpos.m_123342_();
               this.f_25688_ = (double)blockpos.m_123343_();
               return true;
            } else {
               return this.m_25702_();
            }
         }
      }

      public boolean m_8045_() {
         if (this.f_29320_.m_29150_()) {
            this.f_29320_.m_21573_().m_26573_();
            return false;
         } else {
            return super.m_8045_();
         }
      }
   }

   static class PandaRollGoal extends Goal {
      private final Panda f_29326_;

      public PandaRollGoal(Panda p_29328_) {
         this.f_29326_ = p_29328_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
      }

      public boolean m_8036_() {
         if ((this.f_29326_.m_6162_() || this.f_29326_.m_29163_()) && this.f_29326_.f_19861_) {
            if (!this.f_29326_.m_29167_()) {
               return false;
            } else {
               float f = this.f_29326_.m_146908_() * ((float)Math.PI / 180F);
               int i = 0;
               int j = 0;
               float f1 = -Mth.m_14031_(f);
               float f2 = Mth.m_14089_(f);
               if ((double)Math.abs(f1) > 0.5D) {
                  i = (int)((float)i + f1 / Math.abs(f1));
               }

               if ((double)Math.abs(f2) > 0.5D) {
                  j = (int)((float)j + f2 / Math.abs(f2));
               }

               if (this.f_29326_.f_19853_.m_8055_(this.f_29326_.m_142538_().m_142082_(i, -1, j)).m_60795_()) {
                  return true;
               } else if (this.f_29326_.m_29163_() && this.f_29326_.f_19796_.nextInt(60) == 1) {
                  return true;
               } else {
                  return this.f_29326_.f_19796_.nextInt(500) == 1;
               }
            }
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8056_() {
         this.f_29326_.m_29222_(true);
      }

      public boolean m_6767_() {
         return false;
      }
   }

   class PandaSitGoal extends Goal {
      private int f_29334_;

      public PandaSitGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         if (this.f_29334_ <= Panda.this.f_19797_ && !Panda.this.m_6162_() && !Panda.this.m_20069_() && Panda.this.m_29167_() && Panda.this.m_29148_() <= 0) {
            List<ItemEntity> list = Panda.this.f_19853_.m_6443_(ItemEntity.class, Panda.this.m_142469_().m_82377_(6.0D, 6.0D, 6.0D), Panda.f_29071_);
            return !list.isEmpty() || !Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_();
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         if (!Panda.this.m_20069_() && (Panda.this.m_29161_() || Panda.this.f_19796_.nextInt(600) != 1)) {
            return Panda.this.f_19796_.nextInt(2000) != 1;
         } else {
            return false;
         }
      }

      public void m_8037_() {
         if (!Panda.this.m_29150_() && !Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
            Panda.this.m_29179_();
         }

      }

      public void m_8056_() {
         List<ItemEntity> list = Panda.this.f_19853_.m_6443_(ItemEntity.class, Panda.this.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), Panda.f_29071_);
         if (!list.isEmpty() && Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
            Panda.this.m_21573_().m_5624_(list.get(0), (double)1.2F);
         } else if (!Panda.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
            Panda.this.m_29179_();
         }

         this.f_29334_ = 0;
      }

      public void m_8041_() {
         ItemStack itemstack = Panda.this.m_6844_(EquipmentSlot.MAINHAND);
         if (!itemstack.m_41619_()) {
            Panda.this.m_19983_(itemstack);
            Panda.this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            int i = Panda.this.m_29161_() ? Panda.this.f_19796_.nextInt(50) + 10 : Panda.this.f_19796_.nextInt(150) + 10;
            this.f_29334_ = Panda.this.f_19797_ + i * 20;
         }

         Panda.this.m_29208_(false);
      }
   }

   static class PandaSneezeGoal extends Goal {
      private final Panda f_29342_;

      public PandaSneezeGoal(Panda p_29344_) {
         this.f_29342_ = p_29344_;
      }

      public boolean m_8036_() {
         if (this.f_29342_.m_6162_() && this.f_29342_.m_29167_()) {
            if (this.f_29342_.m_29164_() && this.f_29342_.f_19796_.nextInt(500) == 1) {
               return true;
            } else {
               return this.f_29342_.f_19796_.nextInt(6000) == 1;
            }
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8056_() {
         this.f_29342_.m_29220_(true);
      }
   }
}