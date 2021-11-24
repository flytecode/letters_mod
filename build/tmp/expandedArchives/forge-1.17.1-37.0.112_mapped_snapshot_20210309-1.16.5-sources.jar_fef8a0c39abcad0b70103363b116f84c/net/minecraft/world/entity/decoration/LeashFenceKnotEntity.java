package net.minecraft.world.entity.decoration;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LeashFenceKnotEntity extends HangingEntity {
   public static final double f_149638_ = 0.375D;

   public LeashFenceKnotEntity(EntityType<? extends LeashFenceKnotEntity> p_31828_, Level p_31829_) {
      super(p_31828_, p_31829_);
   }

   public LeashFenceKnotEntity(Level p_31831_, BlockPos p_31832_) {
      super(EntityType.f_20464_, p_31831_, p_31832_);
      this.m_6034_((double)p_31832_.m_123341_(), (double)p_31832_.m_123342_(), (double)p_31832_.m_123343_());
   }

   protected void m_7087_() {
      this.m_20343_((double)this.f_31698_.m_123341_() + 0.5D, (double)this.f_31698_.m_123342_() + 0.375D, (double)this.f_31698_.m_123343_() + 0.5D);
      double d0 = (double)this.m_6095_().m_20678_() / 2.0D;
      double d1 = (double)this.m_6095_().m_20679_();
      this.m_20011_(new AABB(this.m_20185_() - d0, this.m_20186_(), this.m_20189_() - d0, this.m_20185_() + d0, this.m_20186_() + d1, this.m_20189_() + d0));
   }

   public void m_6022_(Direction p_31848_) {
   }

   public int m_7076_() {
      return 9;
   }

   public int m_7068_() {
      return 9;
   }

   protected float m_6380_(Pose p_31839_, EntityDimensions p_31840_) {
      return 0.0625F;
   }

   public boolean m_6783_(double p_31835_) {
      return p_31835_ < 1024.0D;
   }

   public void m_5553_(@Nullable Entity p_31837_) {
      this.m_5496_(SoundEvents.f_12033_, 1.0F, 1.0F);
   }

   public void m_7380_(CompoundTag p_31852_) {
   }

   public void m_7378_(CompoundTag p_31850_) {
   }

   public InteractionResult m_6096_(Player p_31842_, InteractionHand p_31843_) {
      if (this.f_19853_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         boolean flag = false;
         double d0 = 7.0D;
         List<Mob> list = this.f_19853_.m_45976_(Mob.class, new AABB(this.m_20185_() - 7.0D, this.m_20186_() - 7.0D, this.m_20189_() - 7.0D, this.m_20185_() + 7.0D, this.m_20186_() + 7.0D, this.m_20189_() + 7.0D));

         for(Mob mob : list) {
            if (mob.m_21524_() == p_31842_) {
               mob.m_21463_(this, true);
               flag = true;
            }
         }

         if (!flag) {
            this.m_146870_();
            if (p_31842_.m_150110_().f_35937_) {
               for(Mob mob1 : list) {
                  if (mob1.m_21523_() && mob1.m_21524_() == this) {
                     mob1.m_21455_(true, false);
                  }
               }
            }
         }

         return InteractionResult.CONSUME;
      }
   }

   public boolean m_7088_() {
      return this.f_19853_.m_8055_(this.f_31698_).m_60620_(BlockTags.f_13039_);
   }

   public static LeashFenceKnotEntity m_31844_(Level p_31845_, BlockPos p_31846_) {
      int i = p_31846_.m_123341_();
      int j = p_31846_.m_123342_();
      int k = p_31846_.m_123343_();

      for(LeashFenceKnotEntity leashfenceknotentity : p_31845_.m_45976_(LeashFenceKnotEntity.class, new AABB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D))) {
         if (leashfenceknotentity.m_31748_().equals(p_31846_)) {
            return leashfenceknotentity;
         }
      }

      LeashFenceKnotEntity leashfenceknotentity1 = new LeashFenceKnotEntity(p_31845_, p_31846_);
      p_31845_.m_7967_(leashfenceknotentity1);
      return leashfenceknotentity1;
   }

   public void m_7084_() {
      this.m_5496_(SoundEvents.f_12087_, 1.0F, 1.0F);
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this, this.m_6095_(), 0, this.m_31748_());
   }

   public Vec3 m_7398_(float p_31863_) {
      return this.m_20318_(p_31863_).m_82520_(0.0D, 0.2D, 0.0D);
   }

   public ItemStack m_142340_() {
      return new ItemStack(Items.f_42655_);
   }
}