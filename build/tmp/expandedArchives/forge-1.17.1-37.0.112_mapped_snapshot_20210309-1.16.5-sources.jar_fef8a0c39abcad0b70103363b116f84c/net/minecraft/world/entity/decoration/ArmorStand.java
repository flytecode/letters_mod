package net.minecraft.world.entity.decoration;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ArmorStand extends LivingEntity {
   public static final int f_149592_ = 5;
   private static final boolean f_149595_ = true;
   private static final Rotations f_31529_ = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations f_31530_ = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations f_31531_ = new Rotations(-10.0F, 0.0F, -10.0F);
   private static final Rotations f_31532_ = new Rotations(-15.0F, 0.0F, 10.0F);
   private static final Rotations f_31533_ = new Rotations(-1.0F, 0.0F, -1.0F);
   private static final Rotations f_31534_ = new Rotations(1.0F, 0.0F, 1.0F);
   private static final EntityDimensions f_31535_ = new EntityDimensions(0.0F, 0.0F, true);
   private static final EntityDimensions f_31536_ = EntityType.f_20529_.m_20680_().m_20388_(0.5F);
   private static final double f_149596_ = 0.1D;
   private static final double f_149597_ = 0.9D;
   private static final double f_149598_ = 0.4D;
   private static final double f_149600_ = 1.6D;
   public static final int f_149599_ = 8;
   public static final int f_149601_ = 16;
   public static final int f_149602_ = 1;
   public static final int f_149603_ = 4;
   public static final int f_149593_ = 8;
   public static final int f_149594_ = 16;
   public static final EntityDataAccessor<Byte> f_31524_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135027_);
   public static final EntityDataAccessor<Rotations> f_31546_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
   public static final EntityDataAccessor<Rotations> f_31547_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
   public static final EntityDataAccessor<Rotations> f_31548_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
   public static final EntityDataAccessor<Rotations> f_31549_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
   public static final EntityDataAccessor<Rotations> f_31550_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
   public static final EntityDataAccessor<Rotations> f_31527_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
   private static final Predicate<Entity> f_31537_ = (p_31582_) -> {
      return p_31582_ instanceof AbstractMinecart && ((AbstractMinecart)p_31582_).canBeRidden();
   };
   private final NonNullList<ItemStack> f_31538_ = NonNullList.m_122780_(2, ItemStack.f_41583_);
   private final NonNullList<ItemStack> f_31539_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
   private boolean f_31540_;
   public long f_31528_;
   private int f_31541_;
   private Rotations f_31542_ = f_31529_;
   private Rotations f_31543_ = f_31530_;
   private Rotations f_31544_ = f_31531_;
   private Rotations f_31545_ = f_31532_;
   private Rotations f_31525_ = f_31533_;
   private Rotations f_31526_ = f_31534_;

   public ArmorStand(EntityType<? extends ArmorStand> p_31553_, Level p_31554_) {
      super(p_31553_, p_31554_);
      this.f_19793_ = 0.0F;
   }

   public ArmorStand(Level p_31556_, double p_31557_, double p_31558_, double p_31559_) {
      this(EntityType.f_20529_, p_31556_);
      this.m_6034_(p_31557_, p_31558_, p_31559_);
   }

   public void m_6210_() {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      super.m_6210_();
      this.m_6034_(d0, d1, d2);
   }

   private boolean m_31560_() {
      return !this.m_31677_() && !this.m_20068_();
   }

   public boolean m_6142_() {
      return super.m_6142_() && this.m_31560_();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_31524_, (byte)0);
      this.f_19804_.m_135372_(f_31546_, f_31529_);
      this.f_19804_.m_135372_(f_31547_, f_31530_);
      this.f_19804_.m_135372_(f_31548_, f_31531_);
      this.f_19804_.m_135372_(f_31549_, f_31532_);
      this.f_19804_.m_135372_(f_31550_, f_31533_);
      this.f_19804_.m_135372_(f_31527_, f_31534_);
   }

   public Iterable<ItemStack> m_6167_() {
      return this.f_31538_;
   }

   public Iterable<ItemStack> m_6168_() {
      return this.f_31539_;
   }

   public ItemStack m_6844_(EquipmentSlot p_31612_) {
      switch(p_31612_.m_20743_()) {
      case HAND:
         return this.f_31538_.get(p_31612_.m_20749_());
      case ARMOR:
         return this.f_31539_.get(p_31612_.m_20749_());
      default:
         return ItemStack.f_41583_;
      }
   }

   public void m_8061_(EquipmentSlot p_31584_, ItemStack p_31585_) {
      this.m_181122_(p_31585_);
      switch(p_31584_.m_20743_()) {
      case HAND:
         this.m_147218_(p_31585_);
         this.f_31538_.set(p_31584_.m_20749_(), p_31585_);
         break;
      case ARMOR:
         this.m_147218_(p_31585_);
         this.f_31539_.set(p_31584_.m_20749_(), p_31585_);
      }

   }

   public boolean m_7066_(ItemStack p_31638_) {
      EquipmentSlot equipmentslot = Mob.m_147233_(p_31638_);
      return this.m_6844_(equipmentslot).m_41619_() && !this.m_31626_(equipmentslot);
   }

   public void m_7380_(CompoundTag p_31619_) {
      super.m_7380_(p_31619_);
      ListTag listtag = new ListTag();

      for(ItemStack itemstack : this.f_31539_) {
         CompoundTag compoundtag = new CompoundTag();
         if (!itemstack.m_41619_()) {
            itemstack.m_41739_(compoundtag);
         }

         listtag.add(compoundtag);
      }

      p_31619_.m_128365_("ArmorItems", listtag);
      ListTag listtag1 = new ListTag();

      for(ItemStack itemstack1 : this.f_31538_) {
         CompoundTag compoundtag1 = new CompoundTag();
         if (!itemstack1.m_41619_()) {
            itemstack1.m_41739_(compoundtag1);
         }

         listtag1.add(compoundtag1);
      }

      p_31619_.m_128365_("HandItems", listtag1);
      p_31619_.m_128379_("Invisible", this.m_20145_());
      p_31619_.m_128379_("Small", this.m_31666_());
      p_31619_.m_128379_("ShowArms", this.m_31671_());
      p_31619_.m_128405_("DisabledSlots", this.f_31541_);
      p_31619_.m_128379_("NoBasePlate", this.m_31674_());
      if (this.m_31677_()) {
         p_31619_.m_128379_("Marker", this.m_31677_());
      }

      p_31619_.m_128365_("Pose", this.m_31561_());
   }

   public void m_7378_(CompoundTag p_31600_) {
      super.m_7378_(p_31600_);
      if (p_31600_.m_128425_("ArmorItems", 9)) {
         ListTag listtag = p_31600_.m_128437_("ArmorItems", 10);

         for(int i = 0; i < this.f_31539_.size(); ++i) {
            this.f_31539_.set(i, ItemStack.m_41712_(listtag.m_128728_(i)));
         }
      }

      if (p_31600_.m_128425_("HandItems", 9)) {
         ListTag listtag1 = p_31600_.m_128437_("HandItems", 10);

         for(int j = 0; j < this.f_31538_.size(); ++j) {
            this.f_31538_.set(j, ItemStack.m_41712_(listtag1.m_128728_(j)));
         }
      }

      this.m_6842_(p_31600_.m_128471_("Invisible"));
      this.m_31603_(p_31600_.m_128471_("Small"));
      this.m_31675_(p_31600_.m_128471_("ShowArms"));
      this.f_31541_ = p_31600_.m_128451_("DisabledSlots");
      this.m_31678_(p_31600_.m_128471_("NoBasePlate"));
      this.m_31681_(p_31600_.m_128471_("Marker"));
      this.f_19794_ = !this.m_31560_();
      CompoundTag compoundtag = p_31600_.m_128469_("Pose");
      this.m_31657_(compoundtag);
   }

   private void m_31657_(CompoundTag p_31658_) {
      ListTag listtag = p_31658_.m_128437_("Head", 5);
      this.m_31597_(listtag.isEmpty() ? f_31529_ : new Rotations(listtag));
      ListTag listtag1 = p_31658_.m_128437_("Body", 5);
      this.m_31616_(listtag1.isEmpty() ? f_31530_ : new Rotations(listtag1));
      ListTag listtag2 = p_31658_.m_128437_("LeftArm", 5);
      this.m_31623_(listtag2.isEmpty() ? f_31531_ : new Rotations(listtag2));
      ListTag listtag3 = p_31658_.m_128437_("RightArm", 5);
      this.m_31628_(listtag3.isEmpty() ? f_31532_ : new Rotations(listtag3));
      ListTag listtag4 = p_31658_.m_128437_("LeftLeg", 5);
      this.m_31639_(listtag4.isEmpty() ? f_31533_ : new Rotations(listtag4));
      ListTag listtag5 = p_31658_.m_128437_("RightLeg", 5);
      this.m_31651_(listtag5.isEmpty() ? f_31534_ : new Rotations(listtag5));
   }

   private CompoundTag m_31561_() {
      CompoundTag compoundtag = new CompoundTag();
      if (!f_31529_.equals(this.f_31542_)) {
         compoundtag.m_128365_("Head", this.f_31542_.m_123155_());
      }

      if (!f_31530_.equals(this.f_31543_)) {
         compoundtag.m_128365_("Body", this.f_31543_.m_123155_());
      }

      if (!f_31531_.equals(this.f_31544_)) {
         compoundtag.m_128365_("LeftArm", this.f_31544_.m_123155_());
      }

      if (!f_31532_.equals(this.f_31545_)) {
         compoundtag.m_128365_("RightArm", this.f_31545_.m_123155_());
      }

      if (!f_31533_.equals(this.f_31525_)) {
         compoundtag.m_128365_("LeftLeg", this.f_31525_.m_123155_());
      }

      if (!f_31534_.equals(this.f_31526_)) {
         compoundtag.m_128365_("RightLeg", this.f_31526_.m_123155_());
      }

      return compoundtag;
   }

   public boolean m_6094_() {
      return false;
   }

   protected void m_7324_(Entity p_31564_) {
   }

   protected void m_6138_() {
      List<Entity> list = this.f_19853_.m_6249_(this, this.m_142469_(), f_31537_);

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = list.get(i);
         if (this.m_20280_(entity) <= 0.2D) {
            entity.m_7334_(this);
         }
      }

   }

   public InteractionResult m_7111_(Player p_31594_, Vec3 p_31595_, InteractionHand p_31596_) {
      ItemStack itemstack = p_31594_.m_21120_(p_31596_);
      if (!this.m_31677_() && !itemstack.m_150930_(Items.f_42656_)) {
         if (p_31594_.m_5833_()) {
            return InteractionResult.SUCCESS;
         } else if (p_31594_.f_19853_.f_46443_) {
            return InteractionResult.CONSUME;
         } else {
            EquipmentSlot equipmentslot = Mob.m_147233_(itemstack);
            if (itemstack.m_41619_()) {
               EquipmentSlot equipmentslot1 = this.m_31659_(p_31595_);
               EquipmentSlot equipmentslot2 = this.m_31626_(equipmentslot1) ? equipmentslot : equipmentslot1;
               if (this.m_21033_(equipmentslot2) && this.m_31588_(p_31594_, equipmentslot2, itemstack, p_31596_)) {
                  return InteractionResult.SUCCESS;
               }
            } else {
               if (this.m_31626_(equipmentslot)) {
                  return InteractionResult.FAIL;
               }

               if (equipmentslot.m_20743_() == EquipmentSlot.Type.HAND && !this.m_31671_()) {
                  return InteractionResult.FAIL;
               }

               if (this.m_31588_(p_31594_, equipmentslot, itemstack, p_31596_)) {
                  return InteractionResult.SUCCESS;
               }
            }

            return InteractionResult.PASS;
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   private EquipmentSlot m_31659_(Vec3 p_31660_) {
      EquipmentSlot equipmentslot = EquipmentSlot.MAINHAND;
      boolean flag = this.m_31666_();
      double d0 = flag ? p_31660_.f_82480_ * 2.0D : p_31660_.f_82480_;
      EquipmentSlot equipmentslot1 = EquipmentSlot.FEET;
      if (d0 >= 0.1D && d0 < 0.1D + (flag ? 0.8D : 0.45D) && this.m_21033_(equipmentslot1)) {
         equipmentslot = EquipmentSlot.FEET;
      } else if (d0 >= 0.9D + (flag ? 0.3D : 0.0D) && d0 < 0.9D + (flag ? 1.0D : 0.7D) && this.m_21033_(EquipmentSlot.CHEST)) {
         equipmentslot = EquipmentSlot.CHEST;
      } else if (d0 >= 0.4D && d0 < 0.4D + (flag ? 1.0D : 0.8D) && this.m_21033_(EquipmentSlot.LEGS)) {
         equipmentslot = EquipmentSlot.LEGS;
      } else if (d0 >= 1.6D && this.m_21033_(EquipmentSlot.HEAD)) {
         equipmentslot = EquipmentSlot.HEAD;
      } else if (!this.m_21033_(EquipmentSlot.MAINHAND) && this.m_21033_(EquipmentSlot.OFFHAND)) {
         equipmentslot = EquipmentSlot.OFFHAND;
      }

      return equipmentslot;
   }

   private boolean m_31626_(EquipmentSlot p_31627_) {
      return (this.f_31541_ & 1 << p_31627_.m_20750_()) != 0 || p_31627_.m_20743_() == EquipmentSlot.Type.HAND && !this.m_31671_();
   }

   private boolean m_31588_(Player p_31589_, EquipmentSlot p_31590_, ItemStack p_31591_, InteractionHand p_31592_) {
      ItemStack itemstack = this.m_6844_(p_31590_);
      if (!itemstack.m_41619_() && (this.f_31541_ & 1 << p_31590_.m_20750_() + 8) != 0) {
         return false;
      } else if (itemstack.m_41619_() && (this.f_31541_ & 1 << p_31590_.m_20750_() + 16) != 0) {
         return false;
      } else if (p_31589_.m_150110_().f_35937_ && itemstack.m_41619_() && !p_31591_.m_41619_()) {
         ItemStack itemstack2 = p_31591_.m_41777_();
         itemstack2.m_41764_(1);
         this.m_8061_(p_31590_, itemstack2);
         return true;
      } else if (!p_31591_.m_41619_() && p_31591_.m_41613_() > 1) {
         if (!itemstack.m_41619_()) {
            return false;
         } else {
            ItemStack itemstack1 = p_31591_.m_41777_();
            itemstack1.m_41764_(1);
            this.m_8061_(p_31590_, itemstack1);
            p_31591_.m_41774_(1);
            return true;
         }
      } else {
         this.m_8061_(p_31590_, p_31591_);
         p_31589_.m_21008_(p_31592_, itemstack);
         return true;
      }
   }

   public boolean m_6469_(DamageSource p_31579_, float p_31580_) {
      if (!this.f_19853_.f_46443_ && !this.m_146910_()) {
         if (DamageSource.f_19317_.equals(p_31579_)) {
            this.m_6074_();
            return false;
         } else if (!this.m_6673_(p_31579_) && !this.f_31540_ && !this.m_31677_()) {
            if (p_31579_.m_19372_()) {
               this.m_31653_(p_31579_);
               this.m_6074_();
               return false;
            } else if (DamageSource.f_19305_.equals(p_31579_)) {
               if (this.m_6060_()) {
                  this.m_31648_(p_31579_, 0.15F);
               } else {
                  this.m_20254_(5);
               }

               return false;
            } else if (DamageSource.f_19307_.equals(p_31579_) && this.m_21223_() > 0.5F) {
               this.m_31648_(p_31579_, 4.0F);
               return false;
            } else {
               boolean flag = p_31579_.m_7640_() instanceof AbstractArrow;
               boolean flag1 = flag && ((AbstractArrow)p_31579_.m_7640_()).m_36796_() > 0;
               boolean flag2 = "player".equals(p_31579_.m_19385_());
               if (!flag2 && !flag) {
                  return false;
               } else if (p_31579_.m_7639_() instanceof Player && !((Player)p_31579_.m_7639_()).m_150110_().f_35938_) {
                  return false;
               } else if (p_31579_.m_19390_()) {
                  this.m_31566_();
                  this.m_31565_();
                  this.m_6074_();
                  return flag1;
               } else {
                  long i = this.f_19853_.m_46467_();
                  if (i - this.f_31528_ > 5L && !flag) {
                     this.f_19853_.m_7605_(this, (byte)32);
                     this.m_146852_(GameEvent.f_157808_, p_31579_.m_7639_());
                     this.f_31528_ = i;
                  } else {
                     this.m_31646_(p_31579_);
                     this.m_31565_();
                     this.m_6074_();
                  }

                  return true;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void m_7822_(byte p_31568_) {
      if (p_31568_ == 32) {
         if (this.f_19853_.f_46443_) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11683_, this.m_5720_(), 0.3F, 1.0F, false);
            this.f_31528_ = this.f_19853_.m_46467_();
         }
      } else {
         super.m_7822_(p_31568_);
      }

   }

   public boolean m_6783_(double p_31574_) {
      double d0 = this.m_142469_().m_82309_() * 4.0D;
      if (Double.isNaN(d0) || d0 == 0.0D) {
         d0 = 4.0D;
      }

      d0 = d0 * 64.0D;
      return p_31574_ < d0 * d0;
   }

   private void m_31565_() {
      if (this.f_19853_ instanceof ServerLevel) {
         ((ServerLevel)this.f_19853_).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, Blocks.f_50705_.m_49966_()), this.m_20185_(), this.m_20227_(0.6666666666666666D), this.m_20189_(), 10, (double)(this.m_20205_() / 4.0F), (double)(this.m_20206_() / 4.0F), (double)(this.m_20205_() / 4.0F), 0.05D);
      }

   }

   private void m_31648_(DamageSource p_31649_, float p_31650_) {
      float f = this.m_21223_();
      f = f - p_31650_;
      if (f <= 0.5F) {
         this.m_31653_(p_31649_);
         this.m_6074_();
      } else {
         this.m_21153_(f);
         this.m_146852_(GameEvent.f_157808_, p_31649_.m_7639_());
      }

   }

   private void m_31646_(DamageSource p_31647_) {
      Block.m_49840_(this.f_19853_, this.m_142538_(), new ItemStack(Items.f_42650_));
      this.m_31653_(p_31647_);
   }

   private void m_31653_(DamageSource p_31654_) {
      this.m_31566_();
      this.m_6668_(p_31654_);

      for(int i = 0; i < this.f_31538_.size(); ++i) {
         ItemStack itemstack = this.f_31538_.get(i);
         if (!itemstack.m_41619_()) {
            Block.m_49840_(this.f_19853_, this.m_142538_().m_7494_(), itemstack);
            this.f_31538_.set(i, ItemStack.f_41583_);
         }
      }

      for(int j = 0; j < this.f_31539_.size(); ++j) {
         ItemStack itemstack1 = this.f_31539_.get(j);
         if (!itemstack1.m_41619_()) {
            Block.m_49840_(this.f_19853_, this.m_142538_().m_7494_(), itemstack1);
            this.f_31539_.set(j, ItemStack.f_41583_);
         }
      }

   }

   private void m_31566_() {
      this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11681_, this.m_5720_(), 1.0F, 1.0F);
   }

   protected float m_5632_(float p_31644_, float p_31645_) {
      this.f_20884_ = this.f_19859_;
      this.f_20883_ = this.m_146908_();
      return 0.0F;
   }

   protected float m_6431_(Pose p_31614_, EntityDimensions p_31615_) {
      return p_31615_.f_20378_ * (this.m_6162_() ? 0.5F : 0.9F);
   }

   public double m_6049_() {
      return this.m_31677_() ? 0.0D : (double)0.1F;
   }

   public void m_7023_(Vec3 p_31656_) {
      if (this.m_31560_()) {
         super.m_7023_(p_31656_);
      }
   }

   public void m_5618_(float p_31670_) {
      this.f_20884_ = this.f_19859_ = p_31670_;
      this.f_20886_ = this.f_20885_ = p_31670_;
   }

   public void m_5616_(float p_31668_) {
      this.f_20884_ = this.f_19859_ = p_31668_;
      this.f_20886_ = this.f_20885_ = p_31668_;
   }

   public void m_8119_() {
      super.m_8119_();
      Rotations rotations = this.f_19804_.m_135370_(f_31546_);
      if (!this.f_31542_.equals(rotations)) {
         this.m_31597_(rotations);
      }

      Rotations rotations1 = this.f_19804_.m_135370_(f_31547_);
      if (!this.f_31543_.equals(rotations1)) {
         this.m_31616_(rotations1);
      }

      Rotations rotations2 = this.f_19804_.m_135370_(f_31548_);
      if (!this.f_31544_.equals(rotations2)) {
         this.m_31623_(rotations2);
      }

      Rotations rotations3 = this.f_19804_.m_135370_(f_31549_);
      if (!this.f_31545_.equals(rotations3)) {
         this.m_31628_(rotations3);
      }

      Rotations rotations4 = this.f_19804_.m_135370_(f_31550_);
      if (!this.f_31525_.equals(rotations4)) {
         this.m_31639_(rotations4);
      }

      Rotations rotations5 = this.f_19804_.m_135370_(f_31527_);
      if (!this.f_31526_.equals(rotations5)) {
         this.m_31651_(rotations5);
      }

   }

   protected void m_8034_() {
      this.m_6842_(this.f_31540_);
   }

   public void m_6842_(boolean p_31663_) {
      this.f_31540_ = p_31663_;
      super.m_6842_(p_31663_);
   }

   public boolean m_6162_() {
      return this.m_31666_();
   }

   public void m_6074_() {
      this.m_142687_(Entity.RemovalReason.KILLED);
   }

   public boolean m_6128_() {
      return this.m_20145_();
   }

   public PushReaction m_7752_() {
      return this.m_31677_() ? PushReaction.IGNORE : super.m_7752_();
   }

   private void m_31603_(boolean p_31604_) {
      this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 1, p_31604_));
   }

   public boolean m_31666_() {
      return (this.f_19804_.m_135370_(f_31524_) & 1) != 0;
   }

   private void m_31675_(boolean p_31676_) {
      this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 4, p_31676_));
   }

   public boolean m_31671_() {
      return (this.f_19804_.m_135370_(f_31524_) & 4) != 0;
   }

   private void m_31678_(boolean p_31679_) {
      this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 8, p_31679_));
   }

   public boolean m_31674_() {
      return (this.f_19804_.m_135370_(f_31524_) & 8) != 0;
   }

   private void m_31681_(boolean p_31682_) {
      this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 16, p_31682_));
   }

   public boolean m_31677_() {
      return (this.f_19804_.m_135370_(f_31524_) & 16) != 0;
   }

   private byte m_31569_(byte p_31570_, int p_31571_, boolean p_31572_) {
      if (p_31572_) {
         p_31570_ = (byte)(p_31570_ | p_31571_);
      } else {
         p_31570_ = (byte)(p_31570_ & ~p_31571_);
      }

      return p_31570_;
   }

   public void m_31597_(Rotations p_31598_) {
      this.f_31542_ = p_31598_;
      this.f_19804_.m_135381_(f_31546_, p_31598_);
   }

   public void m_31616_(Rotations p_31617_) {
      this.f_31543_ = p_31617_;
      this.f_19804_.m_135381_(f_31547_, p_31617_);
   }

   public void m_31623_(Rotations p_31624_) {
      this.f_31544_ = p_31624_;
      this.f_19804_.m_135381_(f_31548_, p_31624_);
   }

   public void m_31628_(Rotations p_31629_) {
      this.f_31545_ = p_31629_;
      this.f_19804_.m_135381_(f_31549_, p_31629_);
   }

   public void m_31639_(Rotations p_31640_) {
      this.f_31525_ = p_31640_;
      this.f_19804_.m_135381_(f_31550_, p_31640_);
   }

   public void m_31651_(Rotations p_31652_) {
      this.f_31526_ = p_31652_;
      this.f_19804_.m_135381_(f_31527_, p_31652_);
   }

   public Rotations m_31680_() {
      return this.f_31542_;
   }

   public Rotations m_31685_() {
      return this.f_31543_;
   }

   public Rotations m_31688_() {
      return this.f_31544_;
   }

   public Rotations m_31689_() {
      return this.f_31545_;
   }

   public Rotations m_31691_() {
      return this.f_31525_;
   }

   public Rotations m_31694_() {
      return this.f_31526_;
   }

   public boolean m_6087_() {
      return super.m_6087_() && !this.m_31677_();
   }

   public boolean m_7313_(Entity p_31687_) {
      return p_31687_ instanceof Player && !this.f_19853_.m_7966_((Player)p_31687_, this.m_142538_());
   }

   public HumanoidArm m_5737_() {
      return HumanoidArm.RIGHT;
   }

   protected SoundEvent m_5896_(int p_31673_) {
      return SoundEvents.f_11682_;
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource p_31636_) {
      return SoundEvents.f_11683_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_11681_;
   }

   public void m_8038_(ServerLevel p_31576_, LightningBolt p_31577_) {
   }

   public boolean m_5801_() {
      return false;
   }

   public void m_7350_(EntityDataAccessor<?> p_31602_) {
      if (f_31524_.equals(p_31602_)) {
         this.m_6210_();
         this.f_19850_ = !this.m_31677_();
      }

      super.m_7350_(p_31602_);
   }

   public boolean m_5789_() {
      return false;
   }

   public EntityDimensions m_6972_(Pose p_31587_) {
      return this.m_31683_(this.m_31677_());
   }

   private EntityDimensions m_31683_(boolean p_31684_) {
      if (p_31684_) {
         return f_31535_;
      } else {
         return this.m_6162_() ? f_31536_ : this.m_6095_().m_20680_();
      }
   }

   public Vec3 m_7371_(float p_31665_) {
      if (this.m_31677_()) {
         AABB aabb = this.m_31683_(false).m_20393_(this.m_20182_());
         BlockPos blockpos = this.m_142538_();
         int i = Integer.MIN_VALUE;

         for(BlockPos blockpos1 : BlockPos.m_121940_(new BlockPos(aabb.f_82288_, aabb.f_82289_, aabb.f_82290_), new BlockPos(aabb.f_82291_, aabb.f_82292_, aabb.f_82293_))) {
            int j = Math.max(this.f_19853_.m_45517_(LightLayer.BLOCK, blockpos1), this.f_19853_.m_45517_(LightLayer.SKY, blockpos1));
            if (j == 15) {
               return Vec3.m_82512_(blockpos1);
            }

            if (j > i) {
               i = j;
               blockpos = blockpos1.m_7949_();
            }
         }

         return Vec3.m_82512_(blockpos);
      } else {
         return super.m_7371_(p_31665_);
      }
   }

   public ItemStack m_142340_() {
      return new ItemStack(Items.f_42650_);
   }

   public boolean m_142065_() {
      return !this.m_20145_() && !this.m_31677_();
   }
}
