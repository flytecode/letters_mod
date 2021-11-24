package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LlamaFollowCaravanGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class Llama extends AbstractChestedHorse implements RangedAttackMob {
   private static final int f_149535_ = 5;
   private static final int f_149536_ = 4;
   private static final Ingredient f_30744_ = Ingredient.m_43929_(Items.f_42405_, Blocks.f_50335_.m_5456_());
   private static final EntityDataAccessor<Integer> f_30745_ = SynchedEntityData.m_135353_(Llama.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_30746_ = SynchedEntityData.m_135353_(Llama.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_30747_ = SynchedEntityData.m_135353_(Llama.class, EntityDataSerializers.f_135028_);
   boolean f_30741_;
   @Nullable
   private Llama f_30742_;
   @Nullable
   private Llama f_30743_;

   public Llama(EntityType<? extends Llama> p_30750_, Level p_30751_) {
      super(p_30750_, p_30751_);
   }

   public boolean m_7565_() {
      return false;
   }

   private void m_30840_(int p_30841_) {
      this.f_19804_.m_135381_(f_30745_, Math.max(1, Math.min(5, p_30841_)));
   }

   private void m_30813_() {
      int i = this.f_19796_.nextFloat() < 0.04F ? 5 : 3;
      this.m_30840_(1 + this.f_19796_.nextInt(i));
   }

   public int m_30823_() {
      return this.f_19804_.m_135370_(f_30745_);
   }

   public void m_7380_(CompoundTag p_30793_) {
      super.m_7380_(p_30793_);
      p_30793_.m_128405_("Variant", this.m_30825_());
      p_30793_.m_128405_("Strength", this.m_30823_());
      if (!this.f_30520_.m_8020_(1).m_41619_()) {
         p_30793_.m_128365_("DecorItem", this.f_30520_.m_8020_(1).m_41739_(new CompoundTag()));
      }

   }

   public void m_7378_(CompoundTag p_30780_) {
      this.m_30840_(p_30780_.m_128451_("Strength"));
      super.m_7378_(p_30780_);
      this.m_30838_(p_30780_.m_128451_("Variant"));
      if (p_30780_.m_128425_("DecorItem", 10)) {
         this.f_30520_.m_6836_(1, ItemStack.m_41712_(p_30780_.m_128469_("DecorItem")));
      }

      this.m_7493_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new RunAroundLikeCrazyGoal(this, 1.2D));
      this.f_21345_.m_25352_(2, new LlamaFollowCaravanGoal(this, (double)2.1F));
      this.f_21345_.m_25352_(3, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
      this.f_21345_.m_25352_(3, new PanicGoal(this, 1.2D));
      this.f_21345_.m_25352_(4, new BreedGoal(this, 1.0D));
      this.f_21345_.m_25352_(5, new FollowParentGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new Llama.LlamaHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new Llama.LlamaAttackWolfGoal(this));
   }

   public static AttributeSupplier.Builder m_30824_() {
      return m_30501_().m_22268_(Attributes.f_22277_, 40.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_30745_, 0);
      this.f_19804_.m_135372_(f_30746_, -1);
      this.f_19804_.m_135372_(f_30747_, 0);
   }

   public int m_30825_() {
      return Mth.m_14045_(this.f_19804_.m_135370_(f_30747_), 0, 3);
   }

   public void m_30838_(int p_30839_) {
      this.f_19804_.m_135381_(f_30747_, p_30839_);
   }

   protected int m_7506_() {
      return this.m_30502_() ? 2 + 3 * this.m_7488_() : super.m_7506_();
   }

   public void m_7332_(Entity p_30830_) {
      if (this.m_20363_(p_30830_)) {
         float f = Mth.m_14089_(this.f_20883_ * ((float)Math.PI / 180F));
         float f1 = Mth.m_14031_(this.f_20883_ * ((float)Math.PI / 180F));
         float f2 = 0.3F;
         p_30830_.m_6034_(this.m_20185_() + (double)(0.3F * f1), this.m_20186_() + this.m_6048_() + p_30830_.m_6049_(), this.m_20189_() - (double)(0.3F * f));
      }
   }

   public double m_6048_() {
      return (double)this.m_20206_() * 0.67D;
   }

   public boolean m_5807_() {
      return false;
   }

   public boolean m_6898_(ItemStack p_30832_) {
      return f_30744_.test(p_30832_);
   }

   protected boolean m_5994_(Player p_30796_, ItemStack p_30797_) {
      int i = 0;
      int j = 0;
      float f = 0.0F;
      boolean flag = false;
      if (p_30797_.m_150930_(Items.f_42405_)) {
         i = 10;
         j = 3;
         f = 2.0F;
      } else if (p_30797_.m_150930_(Blocks.f_50335_.m_5456_())) {
         i = 90;
         j = 6;
         f = 10.0F;
         if (this.m_30614_() && this.m_146764_() == 0 && this.m_5957_()) {
            flag = true;
            this.m_27595_(p_30796_);
         }
      }

      if (this.m_21223_() < this.m_21233_() && f > 0.0F) {
         this.m_5634_(f);
         flag = true;
      }

      if (this.m_6162_() && i > 0) {
         this.f_19853_.m_7106_(ParticleTypes.f_123748_, this.m_20208_(1.0D), this.m_20187_() + 0.5D, this.m_20262_(1.0D), 0.0D, 0.0D, 0.0D);
         if (!this.f_19853_.f_46443_) {
            this.m_146758_(i);
         }

         flag = true;
      }

      if (j > 0 && (flag || !this.m_30614_()) && this.m_30624_() < this.m_7555_()) {
         flag = true;
         if (!this.f_19853_.f_46443_) {
            this.m_30653_(j);
         }
      }

      if (flag) {
         this.m_146859_(GameEvent.f_157771_, this.m_146901_());
         if (!this.m_20067_()) {
            SoundEvent soundevent = this.m_7872_();
            if (soundevent != null) {
               this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7872_(), this.m_5720_(), 1.0F, 1.0F + (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F);
            }
         }
      }

      return flag;
   }

   protected boolean m_6107_() {
      return this.m_21224_() || this.m_30617_();
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_30774_, DifficultyInstance p_30775_, MobSpawnType p_30776_, @Nullable SpawnGroupData p_30777_, @Nullable CompoundTag p_30778_) {
      this.m_30813_();
      int i;
      if (p_30777_ instanceof Llama.LlamaGroupData) {
         i = ((Llama.LlamaGroupData)p_30777_).f_30847_;
      } else {
         i = this.f_19796_.nextInt(4);
         p_30777_ = new Llama.LlamaGroupData(i);
      }

      this.m_30838_(i);
      return super.m_6518_(p_30774_, p_30775_, p_30776_, p_30777_, p_30778_);
   }

   protected SoundEvent m_7871_() {
      return SoundEvents.f_12093_;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12092_;
   }

   protected SoundEvent m_7975_(DamageSource p_30803_) {
      return SoundEvents.f_12097_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12095_;
   }

   @Nullable
   protected SoundEvent m_7872_() {
      return SoundEvents.f_12096_;
   }

   protected void m_7355_(BlockPos p_30790_, BlockState p_30791_) {
      this.m_5496_(SoundEvents.f_12099_, 0.15F, 1.0F);
   }

   protected void m_7609_() {
      this.m_5496_(SoundEvents.f_12094_, 1.0F, (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
   }

   public void m_7564_() {
      SoundEvent soundevent = this.m_7871_();
      if (soundevent != null) {
         this.m_5496_(soundevent, this.m_6121_(), this.m_6100_());
      }

   }

   public int m_7488_() {
      return this.m_30823_();
   }

   public boolean m_7482_() {
      return true;
   }

   public boolean m_7481_() {
      return !this.f_30520_.m_8020_(1).m_41619_();
   }

   public boolean m_6010_(ItemStack p_30834_) {
      return p_30834_.m_150922_(ItemTags.f_13172_);
   }

   public boolean m_6741_() {
      return false;
   }

   public void m_5757_(Container p_30760_) {
      DyeColor dyecolor = this.m_30826_();
      super.m_5757_(p_30760_);
      DyeColor dyecolor1 = this.m_30826_();
      if (this.f_19797_ > 20 && dyecolor1 != null && dyecolor1 != dyecolor) {
         this.m_5496_(SoundEvents.f_12100_, 0.5F, 1.0F);
      }

   }

   protected void m_7493_() {
      if (!this.f_19853_.f_46443_) {
         super.m_7493_();
         this.m_30771_(m_30835_(this.f_30520_.m_8020_(1)));
      }
   }

   private void m_30771_(@Nullable DyeColor p_30772_) {
      this.f_19804_.m_135381_(f_30746_, p_30772_ == null ? -1 : p_30772_.m_41060_());
   }

   @Nullable
   private static DyeColor m_30835_(ItemStack p_30836_) {
      Block block = Block.m_49814_(p_30836_.m_41720_());
      return block instanceof WoolCarpetBlock ? ((WoolCarpetBlock)block).m_58309_() : null;
   }

   @Nullable
   public DyeColor m_30826_() {
      int i = this.f_19804_.m_135370_(f_30746_);
      return i == -1 ? null : DyeColor.m_41053_(i);
   }

   public int m_7555_() {
      return 30;
   }

   public boolean m_7848_(Animal p_30765_) {
      return p_30765_ != this && p_30765_ instanceof Llama && this.m_30628_() && ((Llama)p_30765_).m_30628_();
   }

   public Llama m_142606_(ServerLevel p_149545_, AgeableMob p_149546_) {
      Llama llama = this.m_7127_();
      this.m_149508_(p_149546_, llama);
      Llama llama1 = (Llama)p_149546_;
      int i = this.f_19796_.nextInt(Math.max(this.m_30823_(), llama1.m_30823_())) + 1;
      if (this.f_19796_.nextFloat() < 0.03F) {
         ++i;
      }

      llama.m_30840_(i);
      llama.m_30838_(this.f_19796_.nextBoolean() ? this.m_30825_() : llama1.m_30825_());
      return llama;
   }

   protected Llama m_7127_() {
      return EntityType.f_20466_.m_20615_(this.f_19853_);
   }

   private void m_30827_(LivingEntity p_30828_) {
      LlamaSpit llamaspit = new LlamaSpit(this.f_19853_, this);
      double d0 = p_30828_.m_20185_() - this.m_20185_();
      double d1 = p_30828_.m_20227_(0.3333333333333333D) - llamaspit.m_20186_();
      double d2 = p_30828_.m_20189_() - this.m_20189_();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2) * (double)0.2F;
      llamaspit.m_6686_(d0, d1 + d3, d2, 1.5F, 10.0F);
      if (!this.m_20067_()) {
         this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12098_, this.m_5720_(), 1.0F, 1.0F + (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F);
      }

      this.f_19853_.m_7967_(llamaspit);
      this.f_30741_ = true;
   }

   void m_30752_(boolean p_30753_) {
      this.f_30741_ = p_30753_;
   }

   public boolean m_142535_(float p_149538_, float p_149539_, DamageSource p_149540_) {
      int i = this.m_5639_(p_149538_, p_149539_);
      if (i <= 0) {
         return false;
      } else {
         if (p_149538_ >= 6.0F) {
            this.m_6469_(p_149540_, (float)i);
            if (this.m_20160_()) {
               for(Entity entity : this.m_146897_()) {
                  entity.m_6469_(p_149540_, (float)i);
               }
            }
         }

         this.m_21229_();
         return true;
      }
   }

   public void m_30809_() {
      if (this.f_30742_ != null) {
         this.f_30742_.f_30743_ = null;
      }

      this.f_30742_ = null;
   }

   public void m_30766_(Llama p_30767_) {
      this.f_30742_ = p_30767_;
      this.f_30742_.f_30743_ = this;
   }

   public boolean m_30810_() {
      return this.f_30743_ != null;
   }

   public boolean m_30811_() {
      return this.f_30742_ != null;
   }

   @Nullable
   public Llama m_30812_() {
      return this.f_30742_;
   }

   protected double m_5823_() {
      return 2.0D;
   }

   protected void m_7567_() {
      if (!this.m_30811_() && this.m_6162_()) {
         super.m_7567_();
      }

   }

   public boolean m_7559_() {
      return false;
   }

   public void m_6504_(LivingEntity p_30762_, float p_30763_) {
      this.m_30827_(p_30762_);
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, 0.75D * (double)this.m_20192_(), (double)this.m_20205_() * 0.5D);
   }

   static class LlamaAttackWolfGoal extends NearestAttackableTargetGoal<Wolf> {
      public LlamaAttackWolfGoal(Llama p_30843_) {
         super(p_30843_, Wolf.class, 16, false, true, (p_30845_) -> {
            return !((Wolf)p_30845_).m_21824_();
         });
      }

      protected double m_7623_() {
         return super.m_7623_() * 0.25D;
      }
   }

   static class LlamaGroupData extends AgeableMob.AgeableMobGroupData {
      public final int f_30847_;

      LlamaGroupData(int p_30849_) {
         super(true);
         this.f_30847_ = p_30849_;
      }
   }

   static class LlamaHurtByTargetGoal extends HurtByTargetGoal {
      public LlamaHurtByTargetGoal(Llama p_30854_) {
         super(p_30854_);
      }

      public boolean m_8045_() {
         if (this.f_26135_ instanceof Llama) {
            Llama llama = (Llama)this.f_26135_;
            if (llama.f_30741_) {
               llama.m_30752_(false);
               return false;
            }
         }

         return super.m_8045_();
      }
   }
}