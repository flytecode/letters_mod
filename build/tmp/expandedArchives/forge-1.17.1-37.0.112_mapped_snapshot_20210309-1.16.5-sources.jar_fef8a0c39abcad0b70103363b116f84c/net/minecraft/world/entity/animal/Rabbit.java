package net.minecraft.world.entity.animal;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class Rabbit extends Animal {
   public static final double f_149016_ = 0.6D;
   public static final double f_149017_ = 0.8D;
   public static final double f_149018_ = 1.0D;
   public static final double f_149019_ = 2.2D;
   public static final double f_149020_ = 1.4D;
   private static final EntityDataAccessor<Integer> f_29647_ = SynchedEntityData.m_135353_(Rabbit.class, EntityDataSerializers.f_135028_);
   public static final int f_149021_ = 0;
   public static final int f_149022_ = 1;
   public static final int f_149023_ = 2;
   public static final int f_149024_ = 3;
   public static final int f_149025_ = 4;
   public static final int f_149026_ = 5;
   public static final int f_149027_ = 99;
   private static final ResourceLocation f_29648_ = new ResourceLocation("killer_bunny");
   public static final int f_149028_ = 8;
   public static final int f_149029_ = 8;
   private static final int f_149030_ = 40;
   private int f_29649_;
   private int f_29650_;
   private boolean f_29651_;
   private int f_29652_;
   int f_29653_;

   public Rabbit(EntityType<? extends Rabbit> p_29656_, Level p_29657_) {
      super(p_29656_, p_29657_);
      this.f_21343_ = new Rabbit.RabbitJumpControl(this);
      this.f_21342_ = new Rabbit.RabbitMoveControl(this);
      this.m_29725_(0.0D);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new Rabbit.RabbitPanicGoal(this, 2.2D));
      this.f_21345_.m_25352_(2, new BreedGoal(this, 0.8D));
      this.f_21345_.m_25352_(3, new TemptGoal(this, 1.0D, Ingredient.m_43929_(Items.f_42619_, Items.f_42677_, Blocks.f_50111_), false));
      this.f_21345_.m_25352_(4, new Rabbit.RabbitAvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D));
      this.f_21345_.m_25352_(4, new Rabbit.RabbitAvoidEntityGoal<>(this, Wolf.class, 10.0F, 2.2D, 2.2D));
      this.f_21345_.m_25352_(4, new Rabbit.RabbitAvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D));
      this.f_21345_.m_25352_(5, new Rabbit.RaidGardenGoal(this));
      this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 0.6D));
      this.f_21345_.m_25352_(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
   }

   protected float m_6118_() {
      if (!this.f_19862_ && (!this.f_21342_.m_24995_() || !(this.f_21342_.m_25001_() > this.m_20186_() + 0.5D))) {
         Path path = this.f_21344_.m_26570_();
         if (path != null && !path.m_77392_()) {
            Vec3 vec3 = path.m_77380_(this);
            if (vec3.f_82480_ > this.m_20186_() + 0.5D) {
               return 0.5F;
            }
         }

         return this.f_21342_.m_24999_() <= 0.6D ? 0.2F : 0.3F;
      } else {
         return 0.5F;
      }
   }

   protected void m_6135_() {
      super.m_6135_();
      double d0 = this.f_21342_.m_24999_();
      if (d0 > 0.0D) {
         double d1 = this.m_20184_().m_165925_();
         if (d1 < 0.01D) {
            this.m_19920_(0.1F, new Vec3(0.0D, 0.0D, 1.0D));
         }
      }

      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_7605_(this, (byte)1);
      }

   }

   public float m_29735_(float p_29736_) {
      return this.f_29650_ == 0 ? 0.0F : ((float)this.f_29649_ + p_29736_) / (float)this.f_29650_;
   }

   public void m_29725_(double p_29726_) {
      this.m_21573_().m_26517_(p_29726_);
      this.f_21342_.m_6849_(this.f_21342_.m_25000_(), this.f_21342_.m_25001_(), this.f_21342_.m_25002_(), p_29726_);
   }

   public void m_6862_(boolean p_29732_) {
      super.m_6862_(p_29732_);
      if (p_29732_) {
         this.m_5496_(this.m_29718_(), this.m_6121_(), ((this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F) * 0.8F);
      }

   }

   public void m_29716_() {
      this.m_6862_(true);
      this.f_29650_ = 10;
      this.f_29649_ = 0;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29647_, 0);
   }

   public void m_8024_() {
      if (this.f_29652_ > 0) {
         --this.f_29652_;
      }

      if (this.f_29653_ > 0) {
         this.f_29653_ -= this.f_19796_.nextInt(3);
         if (this.f_29653_ < 0) {
            this.f_29653_ = 0;
         }
      }

      if (this.f_19861_) {
         if (!this.f_29651_) {
            this.m_6862_(false);
            this.m_29723_();
         }

         if (this.m_29719_() == 99 && this.f_29652_ == 0) {
            LivingEntity livingentity = this.m_5448_();
            if (livingentity != null && this.m_20280_(livingentity) < 16.0D) {
               this.m_29686_(livingentity.m_20185_(), livingentity.m_20189_());
               this.f_21342_.m_6849_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_(), this.f_21342_.m_24999_());
               this.m_29716_();
               this.f_29651_ = true;
            }
         }

         Rabbit.RabbitJumpControl rabbit$rabbitjumpcontrol = (Rabbit.RabbitJumpControl)this.f_21343_;
         if (!rabbit$rabbitjumpcontrol.m_29761_()) {
            if (this.f_21342_.m_24995_() && this.f_29652_ == 0) {
               Path path = this.f_21344_.m_26570_();
               Vec3 vec3 = new Vec3(this.f_21342_.m_25000_(), this.f_21342_.m_25001_(), this.f_21342_.m_25002_());
               if (path != null && !path.m_77392_()) {
                  vec3 = path.m_77380_(this);
               }

               this.m_29686_(vec3.f_82479_, vec3.f_82481_);
               this.m_29716_();
            }
         } else if (!rabbit$rabbitjumpcontrol.m_29762_()) {
            this.m_29720_();
         }
      }

      this.f_29651_ = this.f_19861_;
   }

   public boolean m_5843_() {
      return false;
   }

   private void m_29686_(double p_29687_, double p_29688_) {
      this.m_146922_((float)(Mth.m_14136_(p_29688_ - this.m_20189_(), p_29687_ - this.m_20185_()) * (double)(180F / (float)Math.PI)) - 90.0F);
   }

   private void m_29720_() {
      ((Rabbit.RabbitJumpControl)this.f_21343_).m_29758_(true);
   }

   private void m_29721_() {
      ((Rabbit.RabbitJumpControl)this.f_21343_).m_29758_(false);
   }

   private void m_29722_() {
      if (this.f_21342_.m_24999_() < 2.2D) {
         this.f_29652_ = 10;
      } else {
         this.f_29652_ = 1;
      }

   }

   private void m_29723_() {
      this.m_29722_();
      this.m_29721_();
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.f_29649_ != this.f_29650_) {
         ++this.f_29649_;
      } else if (this.f_29650_ != 0) {
         this.f_29649_ = 0;
         this.f_29650_ = 0;
         this.m_6862_(false);
      }

   }

   public static AttributeSupplier.Builder m_29717_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 3.0D).m_22268_(Attributes.f_22279_, (double)0.3F);
   }

   public void m_7380_(CompoundTag p_29697_) {
      super.m_7380_(p_29697_);
      p_29697_.m_128405_("RabbitType", this.m_29719_());
      p_29697_.m_128405_("MoreCarrotTicks", this.f_29653_);
   }

   public void m_7378_(CompoundTag p_29684_) {
      super.m_7378_(p_29684_);
      this.m_29733_(p_29684_.m_128451_("RabbitType"));
      this.f_29653_ = p_29684_.m_128451_("MoreCarrotTicks");
   }

   protected SoundEvent m_29718_() {
      return SoundEvents.f_12354_;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12297_;
   }

   protected SoundEvent m_7975_(DamageSource p_29715_) {
      return SoundEvents.f_12353_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12352_;
   }

   public boolean m_7327_(Entity p_29659_) {
      if (this.m_29719_() == 99) {
         this.m_5496_(SoundEvents.f_12298_, 1.0F, (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
         return p_29659_.m_6469_(DamageSource.m_19370_(this), 8.0F);
      } else {
         return p_29659_.m_6469_(DamageSource.m_19370_(this), 3.0F);
      }
   }

   public SoundSource m_5720_() {
      return this.m_29719_() == 99 ? SoundSource.HOSTILE : SoundSource.NEUTRAL;
   }

   private static boolean m_149037_(ItemStack p_149038_) {
      return p_149038_.m_150930_(Items.f_42619_) || p_149038_.m_150930_(Items.f_42677_) || p_149038_.m_150930_(Blocks.f_50111_.m_5456_());
   }

   public Rabbit m_142606_(ServerLevel p_149035_, AgeableMob p_149036_) {
      Rabbit rabbit = EntityType.f_20517_.m_20615_(p_149035_);
      int i = this.m_29675_(p_149035_);
      if (this.f_19796_.nextInt(20) != 0) {
         if (p_149036_ instanceof Rabbit && this.f_19796_.nextBoolean()) {
            i = ((Rabbit)p_149036_).m_29719_();
         } else {
            i = this.m_29719_();
         }
      }

      rabbit.m_29733_(i);
      return rabbit;
   }

   public boolean m_6898_(ItemStack p_29729_) {
      return m_149037_(p_29729_);
   }

   public int m_29719_() {
      return this.f_19804_.m_135370_(f_29647_);
   }

   public void m_29733_(int p_29734_) {
      if (p_29734_ == 99) {
         this.m_21051_(Attributes.f_22284_).m_22100_(8.0D);
         this.f_21345_.m_25352_(4, new Rabbit.EvilRabbitAttackGoal(this));
         this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this)).m_26044_());
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
         this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Wolf.class, true));
         if (!this.m_8077_()) {
            this.m_6593_(new TranslatableComponent(Util.m_137492_("entity", f_29648_)));
         }
      }

      this.f_19804_.m_135381_(f_29647_, p_29734_);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_) {
      int i = this.m_29675_(p_29678_);
      if (p_29681_ instanceof Rabbit.RabbitGroupData) {
         i = ((Rabbit.RabbitGroupData)p_29681_).f_29749_;
      } else {
         p_29681_ = new Rabbit.RabbitGroupData(i);
      }

      this.m_29733_(i);
      return super.m_6518_(p_29678_, p_29679_, p_29680_, p_29681_, p_29682_);
   }

   private int m_29675_(LevelAccessor p_29676_) {
      Biome biome = p_29676_.m_46857_(this.m_142538_());
      int i = this.f_19796_.nextInt(100);
      if (biome.m_47530_() == Biome.Precipitation.SNOW) {
         return i < 80 ? 1 : 3;
      } else if (biome.m_47567_() == Biome.BiomeCategory.DESERT) {
         return 4;
      } else {
         return i < 50 ? 0 : (i < 90 ? 5 : 2);
      }
   }

   public static boolean m_29698_(EntityType<Rabbit> p_29699_, LevelAccessor p_29700_, MobSpawnType p_29701_, BlockPos p_29702_, Random p_29703_) {
      BlockState blockstate = p_29700_.m_8055_(p_29702_.m_7495_());
      return (blockstate.m_60713_(Blocks.f_50440_) || blockstate.m_60713_(Blocks.f_50125_) || blockstate.m_60713_(Blocks.f_49992_)) && p_29700_.m_45524_(p_29702_, 0) > 8;
   }

   boolean m_29724_() {
      return this.f_29653_ == 0;
   }

   public void m_7822_(byte p_29663_) {
      if (p_29663_ == 1) {
         this.m_20076_();
         this.f_29650_ = 10;
         this.f_29649_ = 0;
      } else {
         super.m_7822_(p_29663_);
      }

   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.6F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }

   static class EvilRabbitAttackGoal extends MeleeAttackGoal {
      public EvilRabbitAttackGoal(Rabbit p_29738_) {
         super(p_29738_, 1.4D, true);
      }

      protected double m_6639_(LivingEntity p_29740_) {
         return (double)(4.0F + p_29740_.m_20205_());
      }
   }

   static class RabbitAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Rabbit f_29741_;

      public RabbitAvoidEntityGoal(Rabbit p_29743_, Class<T> p_29744_, float p_29745_, double p_29746_, double p_29747_) {
         super(p_29743_, p_29744_, p_29745_, p_29746_, p_29747_);
         this.f_29741_ = p_29743_;
      }

      public boolean m_8036_() {
         return this.f_29741_.m_29719_() != 99 && super.m_8036_();
      }
   }

   public static class RabbitGroupData extends AgeableMob.AgeableMobGroupData {
      public final int f_29749_;

      public RabbitGroupData(int p_29751_) {
         super(1.0F);
         this.f_29749_ = p_29751_;
      }
   }

   public class RabbitJumpControl extends JumpControl {
      private final Rabbit f_29753_;
      private boolean f_29754_;

      public RabbitJumpControl(Rabbit p_29757_) {
         super(p_29757_);
         this.f_29753_ = p_29757_;
      }

      public boolean m_29761_() {
         return this.f_24897_;
      }

      public boolean m_29762_() {
         return this.f_29754_;
      }

      public void m_29758_(boolean p_29759_) {
         this.f_29754_ = p_29759_;
      }

      public void m_8124_() {
         if (this.f_24897_) {
            this.f_29753_.m_29716_();
            this.f_24897_ = false;
         }

      }
   }

   static class RabbitMoveControl extends MoveControl {
      private final Rabbit f_29763_;
      private double f_29764_;

      public RabbitMoveControl(Rabbit p_29766_) {
         super(p_29766_);
         this.f_29763_ = p_29766_;
      }

      public void m_8126_() {
         if (this.f_29763_.f_19861_ && !this.f_29763_.f_20899_ && !((Rabbit.RabbitJumpControl)this.f_29763_.f_21343_).m_29761_()) {
            this.f_29763_.m_29725_(0.0D);
         } else if (this.m_24995_()) {
            this.f_29763_.m_29725_(this.f_29764_);
         }

         super.m_8126_();
      }

      public void m_6849_(double p_29769_, double p_29770_, double p_29771_, double p_29772_) {
         if (this.f_29763_.m_20069_()) {
            p_29772_ = 1.5D;
         }

         super.m_6849_(p_29769_, p_29770_, p_29771_, p_29772_);
         if (p_29772_ > 0.0D) {
            this.f_29764_ = p_29772_;
         }

      }
   }

   static class RabbitPanicGoal extends PanicGoal {
      private final Rabbit f_29773_;

      public RabbitPanicGoal(Rabbit p_29775_, double p_29776_) {
         super(p_29775_, p_29776_);
         this.f_29773_ = p_29775_;
      }

      public void m_8037_() {
         super.m_8037_();
         this.f_29773_.m_29725_(this.f_25685_);
      }
   }

   static class RaidGardenGoal extends MoveToBlockGoal {
      private final Rabbit f_29778_;
      private boolean f_29779_;
      private boolean f_29780_;

      public RaidGardenGoal(Rabbit p_29782_) {
         super(p_29782_, (double)0.7F, 16);
         this.f_29778_ = p_29782_;
      }

      public boolean m_8036_() {
         if (this.f_25600_ <= 0) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_29778_.f_19853_, this.f_29778_)) {
               return false;
            }

            this.f_29780_ = false;
            this.f_29779_ = this.f_29778_.m_29724_();
            this.f_29779_ = true;
         }

         return super.m_8036_();
      }

      public boolean m_8045_() {
         return this.f_29780_ && super.m_8045_();
      }

      public void m_8037_() {
         super.m_8037_();
         this.f_29778_.m_21563_().m_24950_((double)this.f_25602_.m_123341_() + 0.5D, (double)(this.f_25602_.m_123342_() + 1), (double)this.f_25602_.m_123343_() + 0.5D, 10.0F, (float)this.f_29778_.m_8132_());
         if (this.m_25625_()) {
            Level level = this.f_29778_.f_19853_;
            BlockPos blockpos = this.f_25602_.m_7494_();
            BlockState blockstate = level.m_8055_(blockpos);
            Block block = blockstate.m_60734_();
            if (this.f_29780_ && block instanceof CarrotBlock) {
               int i = blockstate.m_61143_(CarrotBlock.f_52244_);
               if (i == 0) {
                  level.m_7731_(blockpos, Blocks.f_50016_.m_49966_(), 2);
                  level.m_46953_(blockpos, true, this.f_29778_);
               } else {
                  level.m_7731_(blockpos, blockstate.m_61124_(CarrotBlock.f_52244_, Integer.valueOf(i - 1)), 2);
                  level.m_46796_(2001, blockpos, Block.m_49956_(blockstate));
               }

               this.f_29778_.f_29653_ = 40;
            }

            this.f_29780_ = false;
            this.f_25600_ = 10;
         }

      }

      protected boolean m_6465_(LevelReader p_29785_, BlockPos p_29786_) {
         BlockState blockstate = p_29785_.m_8055_(p_29786_);
         if (blockstate.m_60713_(Blocks.f_50093_) && this.f_29779_ && !this.f_29780_) {
            blockstate = p_29785_.m_8055_(p_29786_.m_7494_());
            if (blockstate.m_60734_() instanceof CarrotBlock && ((CarrotBlock)blockstate.m_60734_()).m_52307_(blockstate)) {
               this.f_29780_ = true;
               return true;
            }
         }

         return false;
      }
   }
}
