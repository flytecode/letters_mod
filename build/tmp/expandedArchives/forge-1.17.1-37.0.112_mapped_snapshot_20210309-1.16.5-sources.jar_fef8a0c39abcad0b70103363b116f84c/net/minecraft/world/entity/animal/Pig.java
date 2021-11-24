package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemBasedSteering;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Pig extends Animal implements ItemSteerable, Saddleable {
   private static final EntityDataAccessor<Boolean> f_29456_ = SynchedEntityData.m_135353_(Pig.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_29457_ = SynchedEntityData.m_135353_(Pig.class, EntityDataSerializers.f_135028_);
   private static final Ingredient f_29458_ = Ingredient.m_43929_(Items.f_42619_, Items.f_42620_, Items.f_42732_);
   private final ItemBasedSteering f_29459_ = new ItemBasedSteering(this.f_19804_, f_29457_, f_29456_);

   public Pig(EntityType<? extends Pig> p_29462_, Level p_29463_) {
      super(p_29462_, p_29463_);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new PanicGoal(this, 1.25D));
      this.f_21345_.m_25352_(3, new BreedGoal(this, 1.0D));
      this.f_21345_.m_25352_(4, new TemptGoal(this, 1.2D, Ingredient.m_43929_(Items.f_42684_), false));
      this.f_21345_.m_25352_(4, new TemptGoal(this, 1.2D, f_29458_, false));
      this.f_21345_.m_25352_(5, new FollowParentGoal(this, 1.1D));
      this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
   }

   public static AttributeSupplier.Builder m_29503_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, 0.25D);
   }

   @Nullable
   public Entity m_6688_() {
      return this.m_146895_();
   }

   public boolean m_5807_() {
      Entity entity = this.m_6688_();
      if (!(entity instanceof Player)) {
         return false;
      } else {
         Player player = (Player)entity;
         return player.m_21205_().m_150930_(Items.f_42684_) || player.m_21206_().m_150930_(Items.f_42684_);
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_29480_) {
      if (f_29457_.equals(p_29480_) && this.f_19853_.f_46443_) {
         this.f_29459_.m_20844_();
      }

      super.m_7350_(p_29480_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29456_, false);
      this.f_19804_.m_135372_(f_29457_, 0);
   }

   public void m_7380_(CompoundTag p_29495_) {
      super.m_7380_(p_29495_);
      this.f_29459_.m_20847_(p_29495_);
   }

   public void m_7378_(CompoundTag p_29478_) {
      super.m_7378_(p_29478_);
      this.f_29459_.m_20852_(p_29478_);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12233_;
   }

   protected SoundEvent m_7975_(DamageSource p_29502_) {
      return SoundEvents.f_12235_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12234_;
   }

   protected void m_7355_(BlockPos p_29492_, BlockState p_29493_) {
      this.m_5496_(SoundEvents.f_12237_, 0.15F, 1.0F);
   }

   public InteractionResult m_6071_(Player p_29489_, InteractionHand p_29490_) {
      boolean flag = this.m_6898_(p_29489_.m_21120_(p_29490_));
      if (!flag && this.m_6254_() && !this.m_20160_() && !p_29489_.m_36341_()) {
         if (!this.f_19853_.f_46443_) {
            p_29489_.m_20329_(this);
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         InteractionResult interactionresult = super.m_6071_(p_29489_, p_29490_);
         if (!interactionresult.m_19077_()) {
            ItemStack itemstack = p_29489_.m_21120_(p_29490_);
            return itemstack.m_150930_(Items.f_42450_) ? itemstack.m_41647_(p_29489_, this, p_29490_) : InteractionResult.PASS;
         } else {
            return interactionresult;
         }
      }
   }

   public boolean m_6741_() {
      return this.m_6084_() && !this.m_6162_();
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.m_6254_()) {
         this.m_19998_(Items.f_42450_);
      }

   }

   public boolean m_6254_() {
      return this.f_29459_.m_20851_();
   }

   public void m_5853_(@Nullable SoundSource p_29476_) {
      this.f_29459_.m_20849_(true);
      if (p_29476_ != null) {
         this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12236_, p_29476_, 0.5F, 1.0F);
      }

   }

   public Vec3 m_7688_(LivingEntity p_29487_) {
      Direction direction = this.m_6374_();
      if (direction.m_122434_() == Direction.Axis.Y) {
         return super.m_7688_(p_29487_);
      } else {
         int[][] aint = DismountHelper.m_38467_(direction);
         BlockPos blockpos = this.m_142538_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(Pose pose : p_29487_.m_7431_()) {
            AABB aabb = p_29487_.m_21270_(pose);

            for(int[] aint1 : aint) {
               blockpos$mutableblockpos.m_122178_(blockpos.m_123341_() + aint1[0], blockpos.m_123342_(), blockpos.m_123343_() + aint1[1]);
               double d0 = this.f_19853_.m_45573_(blockpos$mutableblockpos);
               if (DismountHelper.m_38439_(d0)) {
                  Vec3 vec3 = Vec3.m_82514_(blockpos$mutableblockpos, d0);
                  if (DismountHelper.m_38456_(this.f_19853_, p_29487_, aabb.m_82383_(vec3))) {
                     p_29487_.m_20124_(pose);
                     return vec3;
                  }
               }
            }
         }

         return super.m_7688_(p_29487_);
      }
   }

   public void m_8038_(ServerLevel p_29473_, LightningBolt p_29474_) {
      if (p_29473_.m_46791_() != Difficulty.PEACEFUL && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, EntityType.f_20531_, (timer) -> {})) {
         ZombifiedPiglin zombifiedpiglin = EntityType.f_20531_.m_20615_(p_29473_);
         zombifiedpiglin.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42430_));
         zombifiedpiglin.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
         zombifiedpiglin.m_21557_(this.m_21525_());
         zombifiedpiglin.m_6863_(this.m_6162_());
         if (this.m_8077_()) {
            zombifiedpiglin.m_6593_(this.m_7770_());
            zombifiedpiglin.m_20340_(this.m_20151_());
         }

         zombifiedpiglin.m_21530_();
         net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zombifiedpiglin);
         p_29473_.m_7967_(zombifiedpiglin);
         this.m_146870_();
      } else {
         super.m_8038_(p_29473_, p_29474_);
      }

   }

   public void m_7023_(Vec3 p_29506_) {
      this.m_20854_(this, this.f_29459_, p_29506_);
   }

   public float m_6748_() {
      return (float)this.m_21133_(Attributes.f_22279_) * 0.225F;
   }

   public void m_7760_(Vec3 p_29482_) {
      super.m_7023_(p_29482_);
   }

   public boolean m_6746_() {
      return this.f_29459_.m_20845_(this.m_21187_());
   }

   public Pig m_142606_(ServerLevel p_149001_, AgeableMob p_149002_) {
      return EntityType.f_20510_.m_20615_(p_149001_);
   }

   public boolean m_6898_(ItemStack p_29508_) {
      return f_29458_.test(p_29508_);
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.6F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }
}
