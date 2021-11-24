package net.minecraft.world.entity.animal;

import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public abstract class Animal extends AgeableMob {
   static final int f_148714_ = 6000;
   private int f_27554_;
   private UUID f_27555_;

   protected Animal(EntityType<? extends Animal> p_27557_, Level p_27558_) {
      super(p_27557_, p_27558_);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 16.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, -1.0F);
   }

   protected void m_8024_() {
      if (this.m_146764_() != 0) {
         this.f_27554_ = 0;
      }

      super.m_8024_();
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_146764_() != 0) {
         this.f_27554_ = 0;
      }

      if (this.f_27554_ > 0) {
         --this.f_27554_;
         if (this.f_27554_ % 10 == 0) {
            double d0 = this.f_19796_.nextGaussian() * 0.02D;
            double d1 = this.f_19796_.nextGaussian() * 0.02D;
            double d2 = this.f_19796_.nextGaussian() * 0.02D;
            this.f_19853_.m_7106_(ParticleTypes.f_123750_, this.m_20208_(1.0D), this.m_20187_() + 0.5D, this.m_20262_(1.0D), d0, d1, d2);
         }
      }

   }

   public boolean m_6469_(DamageSource p_27567_, float p_27568_) {
      if (this.m_6673_(p_27567_)) {
         return false;
      } else {
         this.f_27554_ = 0;
         return super.m_6469_(p_27567_, p_27568_);
      }
   }

   public float m_5610_(BlockPos p_27573_, LevelReader p_27574_) {
      return p_27574_.m_8055_(p_27573_.m_7495_()).m_60713_(Blocks.f_50440_) ? 10.0F : p_27574_.m_46863_(p_27573_) - 0.5F;
   }

   public void m_7380_(CompoundTag p_27587_) {
      super.m_7380_(p_27587_);
      p_27587_.m_128405_("InLove", this.f_27554_);
      if (this.f_27555_ != null) {
         p_27587_.m_128362_("LoveCause", this.f_27555_);
      }

   }

   public double m_6049_() {
      return 0.14D;
   }

   public void m_7378_(CompoundTag p_27576_) {
      super.m_7378_(p_27576_);
      this.f_27554_ = p_27576_.m_128451_("InLove");
      this.f_27555_ = p_27576_.m_128403_("LoveCause") ? p_27576_.m_128342_("LoveCause") : null;
   }

   public static boolean m_27577_(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
      return p_27579_.m_8055_(p_27581_.m_7495_()).m_60713_(Blocks.f_50440_) && p_27579_.m_45524_(p_27581_, 0) > 8;
   }

   public int m_8100_() {
      return 120;
   }

   public boolean m_6785_(double p_27598_) {
      return false;
   }

   protected int m_6552_(Player p_27590_) {
      return 1 + this.f_19853_.f_46441_.nextInt(3);
   }

   public boolean m_6898_(ItemStack p_27600_) {
      return p_27600_.m_150930_(Items.f_42405_);
   }

   public InteractionResult m_6071_(Player p_27584_, InteractionHand p_27585_) {
      ItemStack itemstack = p_27584_.m_21120_(p_27585_);
      if (this.m_6898_(itemstack)) {
         int i = this.m_146764_();
         if (!this.f_19853_.f_46443_ && i == 0 && this.m_5957_()) {
            this.m_142075_(p_27584_, p_27585_, itemstack);
            this.m_27595_(p_27584_);
            this.m_146859_(GameEvent.f_157771_, this.m_146901_());
            return InteractionResult.SUCCESS;
         }

         if (this.m_6162_()) {
            this.m_142075_(p_27584_, p_27585_, itemstack);
            this.m_146740_((int)((float)(-i / 20) * 0.1F), true);
            this.m_146859_(GameEvent.f_157771_, this.m_146901_());
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
         }

         if (this.f_19853_.f_46443_) {
            return InteractionResult.CONSUME;
         }
      }

      return super.m_6071_(p_27584_, p_27585_);
   }

   protected void m_142075_(Player p_148715_, InteractionHand p_148716_, ItemStack p_148717_) {
      if (!p_148715_.m_150110_().f_35937_) {
         p_148717_.m_41774_(1);
      }

   }

   public boolean m_5957_() {
      return this.f_27554_ <= 0;
   }

   public void m_27595_(@Nullable Player p_27596_) {
      this.f_27554_ = 600;
      if (p_27596_ != null) {
         this.f_27555_ = p_27596_.m_142081_();
      }

      this.f_19853_.m_7605_(this, (byte)18);
   }

   public void m_27601_(int p_27602_) {
      this.f_27554_ = p_27602_;
   }

   public int m_27591_() {
      return this.f_27554_;
   }

   @Nullable
   public ServerPlayer m_27592_() {
      if (this.f_27555_ == null) {
         return null;
      } else {
         Player player = this.f_19853_.m_46003_(this.f_27555_);
         return player instanceof ServerPlayer ? (ServerPlayer)player : null;
      }
   }

   public boolean m_27593_() {
      return this.f_27554_ > 0;
   }

   public void m_27594_() {
      this.f_27554_ = 0;
   }

   public boolean m_7848_(Animal p_27569_) {
      if (p_27569_ == this) {
         return false;
      } else if (p_27569_.getClass() != this.getClass()) {
         return false;
      } else {
         return this.m_27593_() && p_27569_.m_27593_();
      }
   }

   public void m_27563_(ServerLevel p_27564_, Animal p_27565_) {
      AgeableMob ageablemob = this.m_142606_(p_27564_, p_27565_);
      final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(this, p_27565_, ageablemob);
      final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
      ageablemob = event.getChild();
      if (cancelled) {
         //Reset the "inLove" state for the animals
         this.m_146762_(6000);
         p_27565_.m_146762_(6000);
         this.m_27594_();
         p_27565_.m_27594_();
         return;
      }
      if (ageablemob != null) {
         ServerPlayer serverplayer = this.m_27592_();
         if (serverplayer == null && p_27565_.m_27592_() != null) {
            serverplayer = p_27565_.m_27592_();
         }

         if (serverplayer != null) {
            serverplayer.m_36220_(Stats.f_12937_);
            CriteriaTriggers.f_10581_.m_147278_(serverplayer, this, p_27565_, ageablemob);
         }

         this.m_146762_(6000);
         p_27565_.m_146762_(6000);
         this.m_27594_();
         p_27565_.m_27594_();
         ageablemob.m_6863_(true);
         ageablemob.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0F, 0.0F);
         p_27564_.m_47205_(ageablemob);
         p_27564_.m_7605_(this, (byte)18);
         if (p_27564_.m_46469_().m_46207_(GameRules.f_46135_)) {
            p_27564_.m_7967_(new ExperienceOrb(p_27564_, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_21187_().nextInt(7) + 1));
         }

      }
   }

   public void m_7822_(byte p_27562_) {
      if (p_27562_ == 18) {
         for(int i = 0; i < 7; ++i) {
            double d0 = this.f_19796_.nextGaussian() * 0.02D;
            double d1 = this.f_19796_.nextGaussian() * 0.02D;
            double d2 = this.f_19796_.nextGaussian() * 0.02D;
            this.f_19853_.m_7106_(ParticleTypes.f_123750_, this.m_20208_(1.0D), this.m_20187_() + 0.5D, this.m_20262_(1.0D), d0, d1, d2);
         }
      } else {
         super.m_7822_(p_27562_);
      }

   }
}
