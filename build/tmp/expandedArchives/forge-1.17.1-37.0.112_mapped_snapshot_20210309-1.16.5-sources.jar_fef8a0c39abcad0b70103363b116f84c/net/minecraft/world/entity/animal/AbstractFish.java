package net.minecraft.world.entity.animal;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFish extends WaterAnimal implements Bucketable {
   private static final EntityDataAccessor<Boolean> f_27458_ = SynchedEntityData.m_135353_(AbstractFish.class, EntityDataSerializers.f_135035_);

   public AbstractFish(EntityType<? extends AbstractFish> p_27461_, Level p_27462_) {
      super(p_27461_, p_27462_);
      this.f_21342_ = new AbstractFish.FishMoveControl(this);
   }

   protected float m_6431_(Pose p_27474_, EntityDimensions p_27475_) {
      return p_27475_.f_20378_ * 0.65F;
   }

   public static AttributeSupplier.Builder m_27495_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 3.0D);
   }

   public boolean m_8023_() {
      return super.m_8023_() || this.m_142392_();
   }

   public static boolean m_27467_(EntityType<? extends AbstractFish> p_27468_, LevelAccessor p_27469_, MobSpawnType p_27470_, BlockPos p_27471_, Random p_27472_) {
      return p_27469_.m_8055_(p_27471_).m_60713_(Blocks.f_49990_) && p_27469_.m_8055_(p_27471_.m_7494_()).m_60713_(Blocks.f_49990_);
   }

   public boolean m_6785_(double p_27492_) {
      return !this.m_142392_() && !this.m_8077_();
   }

   public int m_5792_() {
      return 8;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_27458_, false);
   }

   public boolean m_142392_() {
      return this.f_19804_.m_135370_(f_27458_);
   }

   public void m_142139_(boolean p_27498_) {
      this.f_19804_.m_135381_(f_27458_, p_27498_);
   }

   public void m_7380_(CompoundTag p_27485_) {
      super.m_7380_(p_27485_);
      p_27485_.m_128379_("FromBucket", this.m_142392_());
   }

   public void m_7378_(CompoundTag p_27465_) {
      super.m_7378_(p_27465_);
      this.m_142139_(p_27465_.m_128471_("FromBucket"));
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new PanicGoal(this, 1.25D));
      this.f_21345_.m_25352_(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, EntitySelector.f_20408_::test));
      this.f_21345_.m_25352_(4, new AbstractFish.FishSwimGoal(this));
   }

   protected PathNavigation m_6037_(Level p_27480_) {
      return new WaterBoundPathNavigation(this, p_27480_);
   }

   public void m_7023_(Vec3 p_27490_) {
      if (this.m_6142_() && this.m_20069_()) {
         this.m_19920_(0.01F, p_27490_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
         if (this.m_5448_() == null) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.m_7023_(p_27490_);
      }

   }

   public void m_8107_() {
      if (!this.m_20069_() && this.f_19861_ && this.f_19863_) {
         this.m_20256_(this.m_20184_().m_82520_((double)((this.f_19796_.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.4F, (double)((this.f_19796_.nextFloat() * 2.0F - 1.0F) * 0.05F)));
         this.f_19861_ = false;
         this.f_19812_ = true;
         this.m_5496_(this.m_5699_(), this.m_6121_(), this.m_6100_());
      }

      super.m_8107_();
   }

   protected InteractionResult m_6071_(Player p_27477_, InteractionHand p_27478_) {
      return Bucketable.m_148828_(p_27477_, p_27478_, this).orElse(super.m_6071_(p_27477_, p_27478_));
   }

   public void m_142146_(ItemStack p_27494_) {
      Bucketable.m_148822_(this, p_27494_);
   }

   public void m_142278_(CompoundTag p_148708_) {
      Bucketable.m_148825_(this, p_148708_);
   }

   public SoundEvent m_142623_() {
      return SoundEvents.f_11782_;
   }

   protected boolean m_6004_() {
      return true;
   }

   protected abstract SoundEvent m_5699_();

   protected SoundEvent m_5501_() {
      return SoundEvents.f_11938_;
   }

   protected void m_7355_(BlockPos p_27482_, BlockState p_27483_) {
   }

   static class FishMoveControl extends MoveControl {
      private final AbstractFish f_27499_;

      FishMoveControl(AbstractFish p_27501_) {
         super(p_27501_);
         this.f_27499_ = p_27501_;
      }

      public void m_8126_() {
         if (this.f_27499_.m_19941_(FluidTags.f_13131_)) {
            this.f_27499_.m_20256_(this.f_27499_.m_20184_().m_82520_(0.0D, 0.005D, 0.0D));
         }

         if (this.f_24981_ == MoveControl.Operation.MOVE_TO && !this.f_27499_.m_21573_().m_26571_()) {
            float f = (float)(this.f_24978_ * this.f_27499_.m_21133_(Attributes.f_22279_));
            this.f_27499_.m_7910_(Mth.m_14179_(0.125F, this.f_27499_.m_6113_(), f));
            double d0 = this.f_24975_ - this.f_27499_.m_20185_();
            double d1 = this.f_24976_ - this.f_27499_.m_20186_();
            double d2 = this.f_24977_ - this.f_27499_.m_20189_();
            if (d1 != 0.0D) {
               double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
               this.f_27499_.m_20256_(this.f_27499_.m_20184_().m_82520_(0.0D, (double)this.f_27499_.m_6113_() * (d1 / d3) * 0.1D, 0.0D));
            }

            if (d0 != 0.0D || d2 != 0.0D) {
               float f1 = (float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
               this.f_27499_.m_146922_(this.m_24991_(this.f_27499_.m_146908_(), f1, 90.0F));
               this.f_27499_.f_20883_ = this.f_27499_.m_146908_();
            }

         } else {
            this.f_27499_.m_7910_(0.0F);
         }
      }
   }

   static class FishSwimGoal extends RandomSwimmingGoal {
      private final AbstractFish f_27503_;

      public FishSwimGoal(AbstractFish p_27505_) {
         super(p_27505_, 1.0D, 40);
         this.f_27503_ = p_27505_;
      }

      public boolean m_8036_() {
         return this.f_27503_.m_6004_() && super.m_8036_();
      }
   }
}