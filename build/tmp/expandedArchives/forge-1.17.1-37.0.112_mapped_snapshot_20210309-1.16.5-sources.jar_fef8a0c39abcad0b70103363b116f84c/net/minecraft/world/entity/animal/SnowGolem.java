package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class SnowGolem extends AbstractGolem implements Shearable, RangedAttackMob, net.minecraftforge.common.IForgeShearable {
   private static final EntityDataAccessor<Byte> f_29899_ = SynchedEntityData.m_135353_(SnowGolem.class, EntityDataSerializers.f_135027_);
   private static final byte f_149047_ = 16;
   private static final float f_149048_ = 1.7F;

   public SnowGolem(EntityType<? extends SnowGolem> p_29902_, Level p_29903_) {
      super(p_29902_, p_29903_);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new RangedAttackGoal(this, 1.25D, 20, 10.0F));
      this.f_21345_.m_25352_(2, new WaterAvoidingRandomStrollGoal(this, 1.0D, 1.0000001E-5F));
      this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, (p_29932_) -> {
         return p_29932_ instanceof Enemy;
      }));
   }

   public static AttributeSupplier.Builder m_29934_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 4.0D).m_22268_(Attributes.f_22279_, (double)0.2F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29899_, (byte)16);
   }

   public void m_7380_(CompoundTag p_29923_) {
      super.m_7380_(p_29923_);
      p_29923_.m_128379_("Pumpkin", this.m_29930_());
   }

   public void m_7378_(CompoundTag p_29915_) {
      super.m_7378_(p_29915_);
      if (p_29915_.m_128441_("Pumpkin")) {
         this.m_29936_(p_29915_.m_128471_("Pumpkin"));
      }

   }

   public boolean m_6126_() {
      return true;
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.f_19853_.f_46443_) {
         int i = Mth.m_14107_(this.m_20185_());
         int j = Mth.m_14107_(this.m_20186_());
         int k = Mth.m_14107_(this.m_20189_());
         if (this.f_19853_.m_46857_(new BlockPos(i, 0, k)).m_47505_(new BlockPos(i, j, k)) > 1.0F) {
            this.m_6469_(DamageSource.f_19307_, 1.0F);
         }

         if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            return;
         }

         BlockState blockstate = Blocks.f_50125_.m_49966_();

         for(int l = 0; l < 4; ++l) {
            i = Mth.m_14107_(this.m_20185_() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
            j = Mth.m_14107_(this.m_20186_());
            k = Mth.m_14107_(this.m_20189_() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos blockpos = new BlockPos(i, j, k);
            if (this.f_19853_.m_46859_(blockpos) && this.f_19853_.m_46857_(blockpos).m_47505_(blockpos) < 0.8F && blockstate.m_60710_(this.f_19853_, blockpos)) {
               this.f_19853_.m_46597_(blockpos, blockstate);
            }
         }
      }

   }

   public void m_6504_(LivingEntity p_29912_, float p_29913_) {
      Snowball snowball = new Snowball(this.f_19853_, this);
      double d0 = p_29912_.m_20188_() - (double)1.1F;
      double d1 = p_29912_.m_20185_() - this.m_20185_();
      double d2 = d0 - snowball.m_20186_();
      double d3 = p_29912_.m_20189_() - this.m_20189_();
      double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double)0.2F;
      snowball.m_6686_(d1, d2 + d4, d3, 1.6F, 12.0F);
      this.m_5496_(SoundEvents.f_12479_, 1.0F, 0.4F / (this.m_21187_().nextFloat() * 0.4F + 0.8F));
      this.f_19853_.m_7967_(snowball);
   }

   protected float m_6431_(Pose p_29917_, EntityDimensions p_29918_) {
      return 1.7F;
   }

   protected InteractionResult m_6071_(Player p_29920_, InteractionHand p_29921_) {
      ItemStack itemstack = p_29920_.m_21120_(p_29921_);
      if (false && itemstack.m_41720_() == Items.f_42574_ && this.m_6220_()) { //Forge: Moved to onSheared
         this.m_5851_(SoundSource.PLAYERS);
         this.m_146852_(GameEvent.f_157781_, p_29920_);
         if (!this.f_19853_.f_46443_) {
            itemstack.m_41622_(1, p_29920_, (p_29910_) -> {
               p_29910_.m_21190_(p_29921_);
            });
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   public void m_5851_(SoundSource p_29907_) {
      this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12480_, p_29907_, 1.0F, 1.0F);
      if (!this.f_19853_.m_5776_()) {
         this.m_29936_(false);
         this.m_5552_(new ItemStack(Items.f_42047_), 1.7F);
      }

   }

   public boolean m_6220_() {
      return this.m_6084_() && this.m_29930_();
   }

   public boolean m_29930_() {
      return (this.f_19804_.m_135370_(f_29899_) & 16) != 0;
   }

   public void m_29936_(boolean p_29937_) {
      byte b0 = this.f_19804_.m_135370_(f_29899_);
      if (p_29937_) {
         this.f_19804_.m_135381_(f_29899_, (byte)(b0 | 16));
      } else {
         this.f_19804_.m_135381_(f_29899_, (byte)(b0 & -17));
      }

   }

   @Nullable
   protected SoundEvent m_7515_() {
      return SoundEvents.f_12476_;
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource p_29929_) {
      return SoundEvents.f_12478_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_12477_;
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.75F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }

   @Override
   public boolean isShearable(@javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos) {
      return m_6220_();
   }

   @javax.annotation.Nonnull
   @Override
   public java.util.List<ItemStack> onSheared(@Nullable Player player, @javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
      world.m_6269_(null, this, SoundEvents.f_12480_, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
      if (!world.m_5776_()) {
         m_29936_(false);
         return java.util.Collections.singletonList(new ItemStack(Items.f_42047_));
      }
      return java.util.Collections.emptyList();
   }
}
