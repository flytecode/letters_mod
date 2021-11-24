package net.minecraft.world.entity.boss.enderdragon;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.dimension.end.EndDragonFight;

public class EndCrystal extends Entity {
   private static final EntityDataAccessor<Optional<BlockPos>> f_31033_ = SynchedEntityData.m_135353_(EndCrystal.class, EntityDataSerializers.f_135039_);
   private static final EntityDataAccessor<Boolean> f_31034_ = SynchedEntityData.m_135353_(EndCrystal.class, EntityDataSerializers.f_135035_);
   public int f_31032_;

   public EndCrystal(EntityType<? extends EndCrystal> p_31037_, Level p_31038_) {
      super(p_31037_, p_31038_);
      this.f_19850_ = true;
      this.f_31032_ = this.f_19796_.nextInt(100000);
   }

   public EndCrystal(Level p_31040_, double p_31041_, double p_31042_, double p_31043_) {
      this(EntityType.f_20564_, p_31040_);
      this.m_6034_(p_31041_, p_31042_, p_31043_);
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.NONE;
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(f_31033_, Optional.empty());
      this.m_20088_().m_135372_(f_31034_, true);
   }

   public void m_8119_() {
      ++this.f_31032_;
      if (this.f_19853_ instanceof ServerLevel) {
         BlockPos blockpos = this.m_142538_();
         if (((ServerLevel)this.f_19853_).m_8586_() != null && this.f_19853_.m_8055_(blockpos).m_60795_()) {
            this.f_19853_.m_46597_(blockpos, BaseFireBlock.m_49245_(this.f_19853_, blockpos));
         }
      }

   }

   protected void m_7380_(CompoundTag p_31062_) {
      if (this.m_31064_() != null) {
         p_31062_.m_128365_("BeamTarget", NbtUtils.m_129224_(this.m_31064_()));
      }

      p_31062_.m_128379_("ShowBottom", this.m_31065_());
   }

   protected void m_7378_(CompoundTag p_31055_) {
      if (p_31055_.m_128425_("BeamTarget", 10)) {
         this.m_31052_(NbtUtils.m_129239_(p_31055_.m_128469_("BeamTarget")));
      }

      if (p_31055_.m_128425_("ShowBottom", 1)) {
         this.m_31056_(p_31055_.m_128471_("ShowBottom"));
      }

   }

   public boolean m_6087_() {
      return true;
   }

   public boolean m_6469_(DamageSource p_31050_, float p_31051_) {
      if (this.m_6673_(p_31050_)) {
         return false;
      } else if (p_31050_.m_7639_() instanceof EnderDragon) {
         return false;
      } else {
         if (!this.m_146910_() && !this.f_19853_.f_46443_) {
            this.m_142687_(Entity.RemovalReason.KILLED);
            if (!p_31050_.m_19372_()) {
               this.f_19853_.m_46511_((Entity)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), 6.0F, Explosion.BlockInteraction.DESTROY);
            }

            this.m_31047_(p_31050_);
         }

         return true;
      }
   }

   public void m_6074_() {
      this.m_31047_(DamageSource.f_19318_);
      super.m_6074_();
   }

   private void m_31047_(DamageSource p_31048_) {
      if (this.f_19853_ instanceof ServerLevel) {
         EndDragonFight enddragonfight = ((ServerLevel)this.f_19853_).m_8586_();
         if (enddragonfight != null) {
            enddragonfight.m_64082_(this, p_31048_);
         }
      }

   }

   public void m_31052_(@Nullable BlockPos p_31053_) {
      this.m_20088_().m_135381_(f_31033_, Optional.ofNullable(p_31053_));
   }

   @Nullable
   public BlockPos m_31064_() {
      return this.m_20088_().m_135370_(f_31033_).orElse((BlockPos)null);
   }

   public void m_31056_(boolean p_31057_) {
      this.m_20088_().m_135381_(f_31034_, p_31057_);
   }

   public boolean m_31065_() {
      return this.m_20088_().m_135370_(f_31034_);
   }

   public boolean m_6783_(double p_31046_) {
      return super.m_6783_(p_31046_) || this.m_31064_() != null;
   }

   public ItemStack m_142340_() {
      return new ItemStack(Items.f_42729_);
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }
}