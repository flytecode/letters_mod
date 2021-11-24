package net.minecraft.world.entity.projectile;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownPotion extends ThrowableItemProjectile implements ItemSupplier {
   public static final double f_150190_ = 4.0D;
   private static final double f_150191_ = 16.0D;
   public static final Predicate<LivingEntity> f_37524_ = LivingEntity::m_6126_;

   public ThrownPotion(EntityType<? extends ThrownPotion> p_37527_, Level p_37528_) {
      super(p_37527_, p_37528_);
   }

   public ThrownPotion(Level p_37535_, LivingEntity p_37536_) {
      super(EntityType.f_20486_, p_37536_, p_37535_);
   }

   public ThrownPotion(Level p_37530_, double p_37531_, double p_37532_, double p_37533_) {
      super(EntityType.f_20486_, p_37531_, p_37532_, p_37533_, p_37530_);
   }

   protected Item m_7881_() {
      return Items.f_42736_;
   }

   protected float m_7139_() {
      return 0.05F;
   }

   protected void m_8060_(BlockHitResult p_37541_) {
      super.m_8060_(p_37541_);
      if (!this.f_19853_.f_46443_) {
         ItemStack itemstack = this.m_7846_();
         Potion potion = PotionUtils.m_43579_(itemstack);
         List<MobEffectInstance> list = PotionUtils.m_43547_(itemstack);
         boolean flag = potion == Potions.f_43599_ && list.isEmpty();
         Direction direction = p_37541_.m_82434_();
         BlockPos blockpos = p_37541_.m_82425_();
         BlockPos blockpos1 = blockpos.m_142300_(direction);
         if (flag) {
            this.m_150192_(blockpos1);
            this.m_150192_(blockpos1.m_142300_(direction.m_122424_()));

            for(Direction direction1 : Direction.Plane.HORIZONTAL) {
               this.m_150192_(blockpos1.m_142300_(direction1));
            }
         }

      }
   }

   protected void m_6532_(HitResult p_37543_) {
      super.m_6532_(p_37543_);
      if (!this.f_19853_.f_46443_) {
         ItemStack itemstack = this.m_7846_();
         Potion potion = PotionUtils.m_43579_(itemstack);
         List<MobEffectInstance> list = PotionUtils.m_43547_(itemstack);
         boolean flag = potion == Potions.f_43599_ && list.isEmpty();
         if (flag) {
            this.m_37552_();
         } else if (!list.isEmpty()) {
            if (this.m_37553_()) {
               this.m_37537_(itemstack, potion);
            } else {
               this.m_37547_(list, p_37543_.m_6662_() == HitResult.Type.ENTITY ? ((EntityHitResult)p_37543_).m_82443_() : null);
            }
         }

         int i = potion.m_43491_() ? 2007 : 2002;
         this.f_19853_.m_46796_(i, this.m_142538_(), PotionUtils.m_43575_(itemstack));
         this.m_146870_();
      }
   }

   private void m_37552_() {
      AABB aabb = this.m_142469_().m_82377_(4.0D, 2.0D, 4.0D);
      List<LivingEntity> list = this.f_19853_.m_6443_(LivingEntity.class, aabb, f_37524_);
      if (!list.isEmpty()) {
         for(LivingEntity livingentity : list) {
            double d0 = this.m_20280_(livingentity);
            if (d0 < 16.0D && livingentity.m_6126_()) {
               livingentity.m_6469_(DamageSource.m_19367_(livingentity, this.m_37282_()), 1.0F);
            }
         }
      }

      for(Axolotl axolotl : this.f_19853_.m_45976_(Axolotl.class, aabb)) {
         axolotl.m_149177_();
      }

   }

   private void m_37547_(List<MobEffectInstance> p_37548_, @Nullable Entity p_37549_) {
      AABB aabb = this.m_142469_().m_82377_(4.0D, 2.0D, 4.0D);
      List<LivingEntity> list = this.f_19853_.m_45976_(LivingEntity.class, aabb);
      if (!list.isEmpty()) {
         Entity entity = this.m_150173_();

         for(LivingEntity livingentity : list) {
            if (livingentity.m_5801_()) {
               double d0 = this.m_20280_(livingentity);
               if (d0 < 16.0D) {
                  double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
                  if (livingentity == p_37549_) {
                     d1 = 1.0D;
                  }

                  for(MobEffectInstance mobeffectinstance : p_37548_) {
                     MobEffect mobeffect = mobeffectinstance.m_19544_();
                     if (mobeffect.m_8093_()) {
                        mobeffect.m_19461_(this, this.m_37282_(), livingentity, mobeffectinstance.m_19564_(), d1);
                     } else {
                        int i = (int)(d1 * (double)mobeffectinstance.m_19557_() + 0.5D);
                        if (i > 20) {
                           livingentity.m_147207_(new MobEffectInstance(mobeffect, i, mobeffectinstance.m_19564_(), mobeffectinstance.m_19571_(), mobeffectinstance.m_19572_()), entity);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void m_37537_(ItemStack p_37538_, Potion p_37539_) {
      AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
      Entity entity = this.m_37282_();
      if (entity instanceof LivingEntity) {
         areaeffectcloud.m_19718_((LivingEntity)entity);
      }

      areaeffectcloud.m_19712_(3.0F);
      areaeffectcloud.m_19732_(-0.5F);
      areaeffectcloud.m_19740_(10);
      areaeffectcloud.m_19738_(-areaeffectcloud.m_19743_() / (float)areaeffectcloud.m_19748_());
      areaeffectcloud.m_19722_(p_37539_);

      for(MobEffectInstance mobeffectinstance : PotionUtils.m_43571_(p_37538_)) {
         areaeffectcloud.m_19716_(new MobEffectInstance(mobeffectinstance));
      }

      CompoundTag compoundtag = p_37538_.m_41783_();
      if (compoundtag != null && compoundtag.m_128425_("CustomPotionColor", 99)) {
         areaeffectcloud.m_19714_(compoundtag.m_128451_("CustomPotionColor"));
      }

      this.f_19853_.m_7967_(areaeffectcloud);
   }

   private boolean m_37553_() {
      return this.m_7846_().m_150930_(Items.f_42739_);
   }

   private void m_150192_(BlockPos p_150193_) {
      BlockState blockstate = this.f_19853_.m_8055_(p_150193_);
      if (blockstate.m_60620_(BlockTags.f_13076_)) {
         this.f_19853_.m_7471_(p_150193_, false);
      } else if (AbstractCandleBlock.m_151933_(blockstate)) {
         AbstractCandleBlock.m_151899_((Player)null, blockstate, this.f_19853_, p_150193_);
      } else if (CampfireBlock.m_51319_(blockstate)) {
         this.f_19853_.m_5898_((Player)null, 1009, p_150193_, 0);
         CampfireBlock.m_152749_(this.m_37282_(), this.f_19853_, p_150193_, blockstate);
         this.f_19853_.m_46597_(p_150193_, blockstate.m_61124_(CampfireBlock.f_51227_, Boolean.valueOf(false)));
      }

   }
}