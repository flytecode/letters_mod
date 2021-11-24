package net.minecraft.world.entity.decoration;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;

public abstract class HangingEntity extends Entity {
   protected static final Predicate<Entity> f_31697_ = (p_31734_) -> {
      return p_31734_ instanceof HangingEntity;
   };
   private int f_31700_;
   protected BlockPos f_31698_;
   protected Direction f_31699_ = Direction.SOUTH;

   protected HangingEntity(EntityType<? extends HangingEntity> p_31703_, Level p_31704_) {
      super(p_31703_, p_31704_);
   }

   protected HangingEntity(EntityType<? extends HangingEntity> p_31706_, Level p_31707_, BlockPos p_31708_) {
      this(p_31706_, p_31707_);
      this.f_31698_ = p_31708_;
   }

   protected void m_8097_() {
   }

   protected void m_6022_(Direction p_31728_) {
      Validate.notNull(p_31728_);
      Validate.isTrue(p_31728_.m_122434_().m_122479_());
      this.f_31699_ = p_31728_;
      this.m_146922_((float)(this.f_31699_.m_122416_() * 90));
      this.f_19859_ = this.m_146908_();
      this.m_7087_();
   }

   protected void m_7087_() {
      if (this.f_31699_ != null) {
         double d0 = (double)this.f_31698_.m_123341_() + 0.5D;
         double d1 = (double)this.f_31698_.m_123342_() + 0.5D;
         double d2 = (double)this.f_31698_.m_123343_() + 0.5D;
         double d3 = 0.46875D;
         double d4 = this.m_31709_(this.m_7076_());
         double d5 = this.m_31709_(this.m_7068_());
         d0 = d0 - (double)this.f_31699_.m_122429_() * 0.46875D;
         d2 = d2 - (double)this.f_31699_.m_122431_() * 0.46875D;
         d1 = d1 + d5;
         Direction direction = this.f_31699_.m_122428_();
         d0 = d0 + d4 * (double)direction.m_122429_();
         d2 = d2 + d4 * (double)direction.m_122431_();
         this.m_20343_(d0, d1, d2);
         double d6 = (double)this.m_7076_();
         double d7 = (double)this.m_7068_();
         double d8 = (double)this.m_7076_();
         if (this.f_31699_.m_122434_() == Direction.Axis.Z) {
            d8 = 1.0D;
         } else {
            d6 = 1.0D;
         }

         d6 = d6 / 32.0D;
         d7 = d7 / 32.0D;
         d8 = d8 / 32.0D;
         this.m_20011_(new AABB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
      }
   }

   private double m_31709_(int p_31710_) {
      return p_31710_ % 32 == 0 ? 0.5D : 0.0D;
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         this.m_146871_();
         if (this.f_31700_++ == 100) {
            this.f_31700_ = 0;
            if (!this.m_146910_() && !this.m_7088_()) {
               this.m_146870_();
               this.m_5553_((Entity)null);
            }
         }
      }

   }

   public boolean m_7088_() {
      if (!this.f_19853_.m_45786_(this)) {
         return false;
      } else {
         int i = Math.max(1, this.m_7076_() / 16);
         int j = Math.max(1, this.m_7068_() / 16);
         BlockPos blockpos = this.f_31698_.m_142300_(this.f_31699_.m_122424_());
         Direction direction = this.f_31699_.m_122428_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int k = 0; k < i; ++k) {
            for(int l = 0; l < j; ++l) {
               int i1 = (i - 1) / -2;
               int j1 = (j - 1) / -2;
               blockpos$mutableblockpos.m_122190_(blockpos).m_122175_(direction, k + i1).m_122175_(Direction.UP, l + j1);
               BlockState blockstate = this.f_19853_.m_8055_(blockpos$mutableblockpos);
               if (net.minecraft.world.level.block.Block.m_49863_(this.f_19853_, blockpos$mutableblockpos, this.f_31699_))
                  continue;
               if (!blockstate.m_60767_().m_76333_() && !DiodeBlock.m_52586_(blockstate)) {
                  return false;
               }
            }
         }

         return this.f_19853_.m_6249_(this, this.m_142469_(), f_31697_).isEmpty();
      }
   }

   public boolean m_6087_() {
      return true;
   }

   public boolean m_7313_(Entity p_31750_) {
      if (p_31750_ instanceof Player) {
         Player player = (Player)p_31750_;
         return !this.f_19853_.m_7966_(player, this.f_31698_) ? true : this.m_6469_(DamageSource.m_19344_(player), 0.0F);
      } else {
         return false;
      }
   }

   public Direction m_6350_() {
      return this.f_31699_;
   }

   public boolean m_6469_(DamageSource p_31715_, float p_31716_) {
      if (this.m_6673_(p_31715_)) {
         return false;
      } else {
         if (!this.m_146910_() && !this.f_19853_.f_46443_) {
            this.m_6074_();
            this.m_5834_();
            this.m_5553_(p_31715_.m_7639_());
         }

         return true;
      }
   }

   public void m_6478_(MoverType p_31719_, Vec3 p_31720_) {
      if (!this.f_19853_.f_46443_ && !this.m_146910_() && p_31720_.m_82556_() > 0.0D) {
         this.m_6074_();
         this.m_5553_((Entity)null);
      }

   }

   public void m_5997_(double p_31744_, double p_31745_, double p_31746_) {
      if (!this.f_19853_.f_46443_ && !this.m_146910_() && p_31744_ * p_31744_ + p_31745_ * p_31745_ + p_31746_ * p_31746_ > 0.0D) {
         this.m_6074_();
         this.m_5553_((Entity)null);
      }

   }

   public void m_7380_(CompoundTag p_31736_) {
      BlockPos blockpos = this.m_31748_();
      p_31736_.m_128405_("TileX", blockpos.m_123341_());
      p_31736_.m_128405_("TileY", blockpos.m_123342_());
      p_31736_.m_128405_("TileZ", blockpos.m_123343_());
   }

   public void m_7378_(CompoundTag p_31730_) {
      this.f_31698_ = new BlockPos(p_31730_.m_128451_("TileX"), p_31730_.m_128451_("TileY"), p_31730_.m_128451_("TileZ"));
   }

   public abstract int m_7076_();

   public abstract int m_7068_();

   public abstract void m_5553_(@Nullable Entity p_31717_);

   public abstract void m_7084_();

   public ItemEntity m_5552_(ItemStack p_31722_, float p_31723_) {
      ItemEntity itementity = new ItemEntity(this.f_19853_, this.m_20185_() + (double)((float)this.f_31699_.m_122429_() * 0.15F), this.m_20186_() + (double)p_31723_, this.m_20189_() + (double)((float)this.f_31699_.m_122431_() * 0.15F), p_31722_);
      itementity.m_32060_();
      this.f_19853_.m_7967_(itementity);
      return itementity;
   }

   protected boolean m_6093_() {
      return false;
   }

   public void m_6034_(double p_31739_, double p_31740_, double p_31741_) {
      this.f_31698_ = new BlockPos(p_31739_, p_31740_, p_31741_);
      this.m_7087_();
      this.f_19812_ = true;
   }

   public BlockPos m_31748_() {
      return this.f_31698_;
   }

   public float m_7890_(Rotation p_31727_) {
      if (this.f_31699_.m_122434_() != Direction.Axis.Y) {
         switch(p_31727_) {
         case CLOCKWISE_180:
            this.f_31699_ = this.f_31699_.m_122424_();
            break;
         case COUNTERCLOCKWISE_90:
            this.f_31699_ = this.f_31699_.m_122428_();
            break;
         case CLOCKWISE_90:
            this.f_31699_ = this.f_31699_.m_122427_();
         }
      }

      float f = Mth.m_14177_(this.m_146908_());
      switch(p_31727_) {
      case CLOCKWISE_180:
         return f + 180.0F;
      case COUNTERCLOCKWISE_90:
         return f + 90.0F;
      case CLOCKWISE_90:
         return f + 270.0F;
      default:
         return f;
      }
   }

   public float m_6961_(Mirror p_31725_) {
      return this.m_7890_(p_31725_.m_54846_(this.f_31699_));
   }

   public void m_8038_(ServerLevel p_31712_, LightningBolt p_31713_) {
   }

   public void m_6210_() {
   }
}
