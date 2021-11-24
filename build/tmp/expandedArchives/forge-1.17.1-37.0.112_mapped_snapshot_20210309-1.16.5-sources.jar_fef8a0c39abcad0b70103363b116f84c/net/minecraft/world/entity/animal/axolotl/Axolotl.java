package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class Axolotl extends Animal implements LerpingModel, Bucketable {
   public static final int f_149090_ = 200;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Axolotl>>> f_149091_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26822_, SensorType.f_26814_, SensorType.f_148315_, SensorType.f_148316_);
   protected static final ImmutableList<? extends MemoryModuleType<?>> f_149092_ = ImmutableList.of(MemoryModuleType.f_26375_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26371_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_, MemoryModuleType.f_26331_, MemoryModuleType.f_26382_, MemoryModuleType.f_148195_, MemoryModuleType.f_148194_, MemoryModuleType.f_148196_, MemoryModuleType.f_148197_, MemoryModuleType.f_148198_, MemoryModuleType.f_148201_);
   private static final EntityDataAccessor<Integer> f_149096_ = SynchedEntityData.m_135353_(Axolotl.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_149097_ = SynchedEntityData.m_135353_(Axolotl.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> f_149098_ = SynchedEntityData.m_135353_(Axolotl.class, EntityDataSerializers.f_135035_);
   public static final double f_149093_ = 20.0D;
   public static final int f_149094_ = 1200;
   private static final int f_149099_ = 6000;
   public static final String f_149095_ = "Variant";
   private static final int f_149100_ = 1800;
   private static final int f_181147_ = 2400;
   private final Map<String, Vector3f> f_149101_ = Maps.newHashMap();
   private static final int f_149102_ = 100;

   public Axolotl(EntityType<? extends Axolotl> p_149105_, Level p_149106_) {
      super(p_149105_, p_149106_);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_21342_ = new Axolotl.AxolotlMoveControl(this);
      this.f_21365_ = new Axolotl.AxolotlLookControl(this, 20);
      this.f_19793_ = 1.0F;
   }

   public Map<String, Vector3f> m_142115_() {
      return this.f_149101_;
   }

   public float m_5610_(BlockPos p_149140_, LevelReader p_149141_) {
      return 0.0F;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_149096_, 0);
      this.f_19804_.m_135372_(f_149097_, false);
      this.f_19804_.m_135372_(f_149098_, false);
   }

   public void m_7380_(CompoundTag p_149158_) {
      super.m_7380_(p_149158_);
      p_149158_.m_128405_("Variant", this.m_149179_().m_149242_());
      p_149158_.m_128379_("FromBucket", this.m_142392_());
   }

   public void m_7378_(CompoundTag p_149145_) {
      super.m_7378_(p_149145_);
      this.m_149117_(Axolotl.Variant.f_149230_[p_149145_.m_128451_("Variant")]);
      this.m_142139_(p_149145_.m_128471_("FromBucket"));
   }

   public void m_8032_() {
      if (!this.m_149175_()) {
         super.m_8032_();
      }
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor p_149132_, DifficultyInstance p_149133_, MobSpawnType p_149134_, @Nullable SpawnGroupData p_149135_, @Nullable CompoundTag p_149136_) {
      boolean flag = false;
      if (p_149134_ == MobSpawnType.BUCKET) {
         return p_149135_;
      } else {
         if (p_149135_ instanceof Axolotl.AxolotlGroupData) {
            if (((Axolotl.AxolotlGroupData)p_149135_).m_146777_() >= 2) {
               flag = true;
            }
         } else {
            p_149135_ = new Axolotl.AxolotlGroupData(Axolotl.Variant.m_149245_(this.f_19853_.f_46441_), Axolotl.Variant.m_149245_(this.f_19853_.f_46441_));
         }

         this.m_149117_(((Axolotl.AxolotlGroupData)p_149135_).m_149205_(this.f_19853_.f_46441_));
         if (flag) {
            this.m_146762_(-24000);
         }

         return super.m_6518_(p_149132_, p_149133_, p_149134_, p_149135_, p_149136_);
      }
   }

   public void m_6075_() {
      int i = this.m_20146_();
      super.m_6075_();
      if (!this.m_21525_()) {
         this.m_149193_(i);
      }

   }

   protected void m_149193_(int p_149194_) {
      if (this.m_6084_() && !this.m_20071_()) {
         this.m_20301_(p_149194_ - 1);
         if (this.m_20146_() == -20) {
            this.m_20301_(0);
            this.m_6469_(DamageSource.f_19324_, 2.0F);
         }
      } else {
         this.m_20301_(this.m_6062_());
      }

   }

   public void m_149177_() {
      int i = this.m_20146_() + 1800;
      this.m_20301_(Math.min(i, this.m_6062_()));
   }

   public int m_6062_() {
      return 6000;
   }

   public Axolotl.Variant m_149179_() {
      return Axolotl.Variant.f_149230_[this.f_19804_.m_135370_(f_149096_)];
   }

   private void m_149117_(Axolotl.Variant p_149118_) {
      this.f_19804_.m_135381_(f_149096_, p_149118_.m_149242_());
   }

   private static boolean m_149142_(Random p_149143_) {
      return p_149143_.nextInt(1200) == 0;
   }

   public boolean m_6914_(LevelReader p_149130_) {
      return p_149130_.m_45784_(this);
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6063_() {
      return false;
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public void m_149198_(boolean p_149199_) {
      this.f_19804_.m_135381_(f_149097_, p_149199_);
   }

   public boolean m_149175_() {
      return this.f_19804_.m_135370_(f_149097_);
   }

   public boolean m_142392_() {
      return this.f_19804_.m_135370_(f_149098_);
   }

   public void m_142139_(boolean p_149196_) {
      this.f_19804_.m_135381_(f_149098_, p_149196_);
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_149112_, AgeableMob p_149113_) {
      Axolotl axolotl = EntityType.f_147039_.m_20615_(p_149112_);
      if (axolotl != null) {
         Axolotl.Variant axolotl$variant;
         if (m_149142_(this.f_19796_)) {
            axolotl$variant = Axolotl.Variant.m_149256_(this.f_19796_);
         } else {
            axolotl$variant = this.f_19796_.nextBoolean() ? this.m_149179_() : ((Axolotl)p_149113_).m_149179_();
         }

         axolotl.m_149117_(axolotl$variant);
         axolotl.m_21530_();
      }

      return axolotl;
   }

   public double m_142593_(LivingEntity p_149185_) {
      return 1.5D + (double)p_149185_.m_20205_() * 2.0D;
   }

   public boolean m_6898_(ItemStack p_149189_) {
      return ItemTags.f_144321_.m_8110_(p_149189_.m_41720_());
   }

   public boolean m_6573_(Player p_149122_) {
      return true;
   }

   protected void m_8024_() {
      this.f_19853_.m_46473_().m_6180_("axolotlBrain");
      this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("axolotlActivityUpdate");
      AxolotlAi.m_149292_(this);
      this.f_19853_.m_46473_().m_7238_();
      if (!this.m_21525_()) {
         Optional<Integer> optional = this.m_6274_().m_21952_(MemoryModuleType.f_148195_);
         this.m_149198_(optional.isPresent() && optional.get() > 0);
      }

   }

   public static AttributeSupplier.Builder m_149176_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 14.0D).m_22268_(Attributes.f_22279_, 1.0D).m_22268_(Attributes.f_22281_, 2.0D);
   }

   protected PathNavigation m_6037_(Level p_149128_) {
      return new Axolotl.AxolotlPathNavigation(this, p_149128_);
   }

   public boolean m_7327_(Entity p_149201_) {
      boolean flag = p_149201_.m_6469_(DamageSource.m_19370_(this), (float)((int)this.m_21133_(Attributes.f_22281_)));
      if (flag) {
         this.m_19970_(this, p_149201_);
         this.m_5496_(SoundEvents.f_144061_, 1.0F, 1.0F);
      }

      return flag;
   }

   public boolean m_6469_(DamageSource p_149115_, float p_149116_) {
      float f = this.m_21223_();
      if (!this.f_19853_.f_46443_ && !this.m_21525_() && this.f_19853_.f_46441_.nextInt(3) == 0 && ((float)this.f_19853_.f_46441_.nextInt(3) < p_149116_ || f / this.m_21233_() < 0.5F) && p_149116_ < f && this.m_20069_() && (p_149115_.m_7639_() != null || p_149115_.m_7640_() != null) && !this.m_149175_()) {
         this.f_20939_.m_21879_(MemoryModuleType.f_148195_, 200);
      }

      return super.m_6469_(p_149115_, p_149116_);
   }

   protected float m_6431_(Pose p_149152_, EntityDimensions p_149153_) {
      return p_149153_.f_20378_ * 0.655F;
   }

   public int m_8132_() {
      return 1;
   }

   public int m_8085_() {
      return 1;
   }

   public InteractionResult m_6071_(Player p_149155_, InteractionHand p_149156_) {
      return Bucketable.m_148828_(p_149155_, p_149156_, this).orElse(super.m_6071_(p_149155_, p_149156_));
   }

   public void m_142146_(ItemStack p_149187_) {
      Bucketable.m_148822_(this, p_149187_);
      CompoundTag compoundtag = p_149187_.m_41784_();
      compoundtag.m_128405_("Variant", this.m_149179_().m_149242_());
      compoundtag.m_128405_("Age", this.m_146764_());
      Brain<?> brain = this.m_6274_();
      if (brain.m_21874_(MemoryModuleType.f_148201_)) {
         compoundtag.m_128356_("HuntingCooldown", brain.m_147341_(MemoryModuleType.f_148201_));
      }

   }

   public void m_142278_(CompoundTag p_149163_) {
      Bucketable.m_148825_(this, p_149163_);
      this.m_149117_(Axolotl.Variant.f_149230_[p_149163_.m_128451_("Variant")]);
      if (p_149163_.m_128441_("Age")) {
         this.m_146762_(p_149163_.m_128451_("Age"));
      }

      if (p_149163_.m_128441_("HuntingCooldown")) {
         this.m_6274_().m_21882_(MemoryModuleType.f_148201_, true, p_149163_.m_128454_("HuntingCooldown"));
      }

   }

   public ItemStack m_142563_() {
      return new ItemStack(Items.f_151057_);
   }

   public SoundEvent m_142623_() {
      return SoundEvents.f_144077_;
   }

   public boolean m_142066_() {
      return !this.m_149175_() && super.m_142066_();
   }

   public static void m_149119_(Axolotl p_149120_) {
      Optional<LivingEntity> optional = p_149120_.m_6274_().m_21952_(MemoryModuleType.f_26372_);
      if (optional.isPresent()) {
         Level level = p_149120_.f_19853_;
         LivingEntity livingentity = optional.get();
         if (livingentity.m_21224_()) {
            DamageSource damagesource = livingentity.m_21225_();
            if (damagesource != null) {
               Entity entity = damagesource.m_7639_();
               if (entity != null && entity.m_6095_() == EntityType.f_20532_) {
                  Player player = (Player)entity;
                  List<Player> list = level.m_45976_(Player.class, p_149120_.m_142469_().m_82400_(20.0D));
                  if (list.contains(player)) {
                     p_149120_.m_149173_(player);
                  }
               }
            }
         }

      }
   }

   public void m_149173_(Player p_149174_) {
      MobEffectInstance mobeffectinstance = p_149174_.m_21124_(MobEffects.f_19605_);
      int i = mobeffectinstance != null ? mobeffectinstance.m_19557_() : 0;
      if (i < 2400) {
         i = Math.min(2400, 100 + i);
         p_149174_.m_147207_(new MobEffectInstance(MobEffects.f_19605_, i, 0), this);
      }

      p_149174_.m_21195_(MobEffects.f_19599_);
   }

   public boolean m_8023_() {
      return super.m_8023_() || this.m_142392_();
   }

   protected SoundEvent m_7975_(DamageSource p_149161_) {
      return SoundEvents.f_144063_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_144062_;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return this.m_20069_() ? SoundEvents.f_144065_ : SoundEvents.f_144064_;
   }

   protected SoundEvent m_5509_() {
      return SoundEvents.f_144066_;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_144067_;
   }

   protected Brain.Provider<Axolotl> m_5490_() {
      return Brain.m_21923_(f_149092_, f_149091_);
   }

   protected Brain<?> m_8075_(Dynamic<?> p_149138_) {
      return AxolotlAi.m_149290_(this.m_5490_().m_22073_(p_149138_));
   }

   public Brain<Axolotl> m_6274_() {
      return (Brain<Axolotl>)super.m_6274_();
   }

   protected void m_8025_() {
      super.m_8025_();
      DebugPackets.m_133695_(this);
   }

   public void m_7023_(Vec3 p_149181_) {
      if (this.m_6142_() && this.m_20069_()) {
         this.m_19920_(this.m_6113_(), p_149181_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
      } else {
         super.m_7023_(p_149181_);
      }

   }

   protected void m_142075_(Player p_149124_, InteractionHand p_149125_, ItemStack p_149126_) {
      if (p_149126_.m_150930_(Items.f_42459_)) {
         p_149124_.m_21008_(p_149125_, new ItemStack(Items.f_42447_));
      } else {
         super.m_142075_(p_149124_, p_149125_, p_149126_);
      }

   }

   public boolean m_6785_(double p_149183_) {
      return !this.m_142392_() && !this.m_8077_();
   }

   public static class AxolotlGroupData extends AgeableMob.AgeableMobGroupData {
      public final Axolotl.Variant[] f_149202_;

      public AxolotlGroupData(Axolotl.Variant... p_149204_) {
         super(false);
         this.f_149202_ = p_149204_;
      }

      public Axolotl.Variant m_149205_(Random p_149206_) {
         return this.f_149202_[p_149206_.nextInt(this.f_149202_.length)];
      }
   }

   class AxolotlLookControl extends SmoothSwimmingLookControl {
      public AxolotlLookControl(Axolotl p_149210_, int p_149211_) {
         super(p_149210_, p_149211_);
      }

      public void m_8128_() {
         if (!Axolotl.this.m_149175_()) {
            super.m_8128_();
         }

      }
   }

   static class AxolotlMoveControl extends SmoothSwimmingMoveControl {
      private final Axolotl f_149213_;

      public AxolotlMoveControl(Axolotl p_149215_) {
         super(p_149215_, 85, 10, 0.1F, 0.5F, false);
         this.f_149213_ = p_149215_;
      }

      public void m_8126_() {
         if (!this.f_149213_.m_149175_()) {
            super.m_8126_();
         }

      }
   }

   static class AxolotlPathNavigation extends WaterBoundPathNavigation {
      AxolotlPathNavigation(Axolotl p_149218_, Level p_149219_) {
         super(p_149218_, p_149219_);
      }

      protected boolean m_7632_() {
         return true;
      }

      protected PathFinder m_5532_(int p_149222_) {
         this.f_26508_ = new AmphibiousNodeEvaluator(false);
         return new PathFinder(this.f_26508_, p_149222_);
      }

      public boolean m_6342_(BlockPos p_149224_) {
         return !this.f_26495_.m_8055_(p_149224_.m_7495_()).m_60795_();
      }
   }

   public static enum Variant {
      LUCY(0, "lucy", true),
      WILD(1, "wild", true),
      GOLD(2, "gold", true),
      CYAN(3, "cyan", true),
      BLUE(4, "blue", false);

      public static final Axolotl.Variant[] f_149230_ = Arrays.stream(values()).sorted(Comparator.comparingInt(Axolotl.Variant::m_149242_)).toArray((p_149255_) -> {
         return new Axolotl.Variant[p_149255_];
      });
      private final int f_149231_;
      private final String f_149232_;
      private final boolean f_149233_;

      private Variant(int p_149239_, String p_149240_, boolean p_149241_) {
         this.f_149231_ = p_149239_;
         this.f_149232_ = p_149240_;
         this.f_149233_ = p_149241_;
      }

      public int m_149242_() {
         return this.f_149231_;
      }

      public String m_149253_() {
         return this.f_149232_;
      }

      public static Axolotl.Variant m_149245_(Random p_149246_) {
         return m_149247_(p_149246_, true);
      }

      public static Axolotl.Variant m_149256_(Random p_149257_) {
         return m_149247_(p_149257_, false);
      }

      private static Axolotl.Variant m_149247_(Random p_149248_, boolean p_149249_) {
         Axolotl.Variant[] aaxolotl$variant = Arrays.stream(f_149230_).filter((p_149252_) -> {
            return p_149252_.f_149233_ == p_149249_;
         }).toArray((p_149244_) -> {
            return new Axolotl.Variant[p_149244_];
         });
         return Util.m_137545_(aaxolotl$variant, p_149248_);
      }
   }
}