package net.minecraft.world.entity.npc;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.InteractGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.world.entity.ai.goal.UseItemGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WanderingTrader extends AbstractVillager {
   private static final int f_150044_ = 5;
   @Nullable
   private BlockPos f_35840_;
   private int f_35841_;

   public WanderingTrader(EntityType<? extends WanderingTrader> p_35843_, Level p_35844_) {
      super(p_35843_, p_35844_);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new UseItemGoal<>(this, PotionUtils.m_43549_(new ItemStack(Items.f_42589_), Potions.f_43605_), SoundEvents.f_12584_, (p_35882_) -> {
         return this.f_19853_.m_46462_() && !p_35882_.m_20145_();
      }));
      this.f_21345_.m_25352_(0, new UseItemGoal<>(this, new ItemStack(Items.f_42455_), SoundEvents.f_12589_, (p_35880_) -> {
         return this.f_19853_.m_46461_() && p_35880_.m_20145_();
      }));
      this.f_21345_.m_25352_(1, new TradeWithPlayerGoal(this));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal<>(this, Zombie.class, 8.0F, 0.5D, 0.5D));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal<>(this, Evoker.class, 12.0F, 0.5D, 0.5D));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal<>(this, Vindicator.class, 8.0F, 0.5D, 0.5D));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal<>(this, Vex.class, 8.0F, 0.5D, 0.5D));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal<>(this, Pillager.class, 15.0F, 0.5D, 0.5D));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal<>(this, Illusioner.class, 12.0F, 0.5D, 0.5D));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal<>(this, Zoglin.class, 10.0F, 0.5D, 0.5D));
      this.f_21345_.m_25352_(1, new PanicGoal(this, 0.5D));
      this.f_21345_.m_25352_(1, new LookAtTradingPlayerGoal(this));
      this.f_21345_.m_25352_(2, new WanderingTrader.WanderToPositionGoal(this, 2.0D, 0.35D));
      this.f_21345_.m_25352_(4, new MoveTowardsRestrictionGoal(this, 0.35D));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
      this.f_21345_.m_25352_(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_150046_, AgeableMob p_150047_) {
      return null;
   }

   public boolean m_7826_() {
      return false;
   }

   public InteractionResult m_6071_(Player p_35856_, InteractionHand p_35857_) {
      ItemStack itemstack = p_35856_.m_21120_(p_35857_);
      if (!itemstack.m_150930_(Items.f_42601_) && this.m_6084_() && !this.m_35306_() && !this.m_6162_()) {
         if (p_35857_ == InteractionHand.MAIN_HAND) {
            p_35856_.m_36220_(Stats.f_12940_);
         }

         if (this.m_6616_().isEmpty()) {
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         } else {
            if (!this.f_19853_.f_46443_) {
               this.m_7189_(p_35856_);
               this.m_45301_(p_35856_, this.m_5446_(), 1);
            }

            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }
      } else {
         return super.m_6071_(p_35856_, p_35857_);
      }
   }

   protected void m_7604_() {
      VillagerTrades.ItemListing[] avillagertrades$itemlisting = VillagerTrades.f_35628_.get(1);
      VillagerTrades.ItemListing[] avillagertrades$itemlisting1 = VillagerTrades.f_35628_.get(2);
      if (avillagertrades$itemlisting != null && avillagertrades$itemlisting1 != null) {
         MerchantOffers merchantoffers = this.m_6616_();
         this.m_35277_(merchantoffers, avillagertrades$itemlisting, 5);
         int i = this.f_19796_.nextInt(avillagertrades$itemlisting1.length);
         VillagerTrades.ItemListing villagertrades$itemlisting = avillagertrades$itemlisting1[i];
         MerchantOffer merchantoffer = villagertrades$itemlisting.m_5670_(this, this.f_19796_);
         if (merchantoffer != null) {
            merchantoffers.add(merchantoffer);
         }

      }
   }

   public void m_7380_(CompoundTag p_35861_) {
      super.m_7380_(p_35861_);
      p_35861_.m_128405_("DespawnDelay", this.f_35841_);
      if (this.f_35840_ != null) {
         p_35861_.m_128365_("WanderTarget", NbtUtils.m_129224_(this.f_35840_));
      }

   }

   public void m_7378_(CompoundTag p_35852_) {
      super.m_7378_(p_35852_);
      if (p_35852_.m_128425_("DespawnDelay", 99)) {
         this.f_35841_ = p_35852_.m_128451_("DespawnDelay");
      }

      if (p_35852_.m_128441_("WanderTarget")) {
         this.f_35840_ = NbtUtils.m_129239_(p_35852_.m_128469_("WanderTarget"));
      }

      this.m_146762_(Math.max(0, this.m_146764_()));
   }

   public boolean m_6785_(double p_35886_) {
      return false;
   }

   protected void m_8058_(MerchantOffer p_35859_) {
      if (p_35859_.m_45383_()) {
         int i = 3 + this.f_19796_.nextInt(4);
         this.f_19853_.m_7967_(new ExperienceOrb(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5D, this.m_20189_(), i));
      }

   }

   protected SoundEvent m_7515_() {
      return this.m_35306_() ? SoundEvents.f_12538_ : SoundEvents.f_12582_;
   }

   protected SoundEvent m_7975_(DamageSource p_35870_) {
      return SoundEvents.f_12587_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12583_;
   }

   protected SoundEvent m_7838_(ItemStack p_35865_) {
      return p_35865_.m_150930_(Items.f_42455_) ? SoundEvents.f_12585_ : SoundEvents.f_12586_;
   }

   protected SoundEvent m_6068_(boolean p_35890_) {
      return p_35890_ ? SoundEvents.f_12539_ : SoundEvents.f_12588_;
   }

   public SoundEvent m_7596_() {
      return SoundEvents.f_12539_;
   }

   public void m_35891_(int p_35892_) {
      this.f_35841_ = p_35892_;
   }

   public int m_35876_() {
      return this.f_35841_;
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.f_19853_.f_46443_) {
         this.m_35877_();
      }

   }

   private void m_35877_() {
      if (this.f_35841_ > 0 && !this.m_35306_() && --this.f_35841_ == 0) {
         this.m_146870_();
      }

   }

   public void m_35883_(@Nullable BlockPos p_35884_) {
      this.f_35840_ = p_35884_;
   }

   @Nullable
   BlockPos m_35878_() {
      return this.f_35840_;
   }

   class WanderToPositionGoal extends Goal {
      final WanderingTrader f_35893_;
      final double f_35894_;
      final double f_35895_;

      WanderToPositionGoal(WanderingTrader p_35899_, double p_35900_, double p_35901_) {
         this.f_35893_ = p_35899_;
         this.f_35894_ = p_35900_;
         this.f_35895_ = p_35901_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public void m_8041_() {
         this.f_35893_.m_35883_((BlockPos)null);
         WanderingTrader.this.f_21344_.m_26573_();
      }

      public boolean m_8036_() {
         BlockPos blockpos = this.f_35893_.m_35878_();
         return blockpos != null && this.m_35903_(blockpos, this.f_35894_);
      }

      public void m_8037_() {
         BlockPos blockpos = this.f_35893_.m_35878_();
         if (blockpos != null && WanderingTrader.this.f_21344_.m_26571_()) {
            if (this.m_35903_(blockpos, 10.0D)) {
               Vec3 vec3 = (new Vec3((double)blockpos.m_123341_() - this.f_35893_.m_20185_(), (double)blockpos.m_123342_() - this.f_35893_.m_20186_(), (double)blockpos.m_123343_() - this.f_35893_.m_20189_())).m_82541_();
               Vec3 vec31 = vec3.m_82490_(10.0D).m_82520_(this.f_35893_.m_20185_(), this.f_35893_.m_20186_(), this.f_35893_.m_20189_());
               WanderingTrader.this.f_21344_.m_26519_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, this.f_35895_);
            } else {
               WanderingTrader.this.f_21344_.m_26519_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), this.f_35895_);
            }
         }

      }

      private boolean m_35903_(BlockPos p_35904_, double p_35905_) {
         return !p_35904_.m_123306_(this.f_35893_.m_20182_(), p_35905_);
      }
   }
}