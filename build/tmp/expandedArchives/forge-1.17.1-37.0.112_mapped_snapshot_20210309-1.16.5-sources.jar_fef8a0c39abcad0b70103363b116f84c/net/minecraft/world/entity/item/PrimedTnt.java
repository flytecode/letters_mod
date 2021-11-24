package net.minecraft.world.entity.item;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class PrimedTnt extends Entity {
   private static final EntityDataAccessor<Integer> f_32071_ = SynchedEntityData.m_135353_(PrimedTnt.class, EntityDataSerializers.f_135028_);
   private static final int f_149679_ = 80;
   @Nullable
   private LivingEntity f_32072_;

   public PrimedTnt(EntityType<? extends PrimedTnt> p_32076_, Level p_32077_) {
      super(p_32076_, p_32077_);
      this.f_19850_ = true;
   }

   public PrimedTnt(Level p_32079_, double p_32080_, double p_32081_, double p_32082_, @Nullable LivingEntity p_32083_) {
      this(EntityType.f_20515_, p_32079_);
      this.m_6034_(p_32080_, p_32081_, p_32082_);
      double d0 = p_32079_.f_46441_.nextDouble() * (double)((float)Math.PI * 2F);
      this.m_20334_(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
      this.m_32085_(80);
      this.f_19854_ = p_32080_;
      this.f_19855_ = p_32081_;
      this.f_19856_ = p_32082_;
      this.f_32072_ = p_32083_;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(f_32071_, 80);
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   public boolean m_6087_() {
      return !this.m_146910_();
   }

   public void m_8119_() {
      if (!this.m_20068_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.04D, 0.0D));
      }

      this.m_6478_(MoverType.SELF, this.m_20184_());
      this.m_20256_(this.m_20184_().m_82490_(0.98D));
      if (this.f_19861_) {
         this.m_20256_(this.m_20184_().m_82542_(0.7D, -0.5D, 0.7D));
      }

      int i = this.m_32100_() - 1;
      this.m_32085_(i);
      if (i <= 0) {
         this.m_146870_();
         if (!this.f_19853_.f_46443_) {
            this.m_32103_();
         }
      } else {
         this.m_20073_();
         if (this.f_19853_.f_46443_) {
            this.f_19853_.m_7106_(ParticleTypes.f_123762_, this.m_20185_(), this.m_20186_() + 0.5D, this.m_20189_(), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   protected void m_32103_() {
      float f = 4.0F;
      this.f_19853_.m_46511_(this, this.m_20185_(), this.m_20227_(0.0625D), this.m_20189_(), 4.0F, Explosion.BlockInteraction.BREAK);
   }

   protected void m_7380_(CompoundTag p_32097_) {
      p_32097_.m_128376_("Fuse", (short)this.m_32100_());
   }

   protected void m_7378_(CompoundTag p_32091_) {
      this.m_32085_(p_32091_.m_128448_("Fuse"));
   }

   @Nullable
   public LivingEntity m_32099_() {
      return this.f_32072_;
   }

   protected float m_6380_(Pose p_32088_, EntityDimensions p_32089_) {
      return 0.15F;
   }

   public void m_32085_(int p_32086_) {
      this.f_19804_.m_135381_(f_32071_, p_32086_);
   }

   public int m_32100_() {
      return this.f_19804_.m_135370_(f_32071_);
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }
}