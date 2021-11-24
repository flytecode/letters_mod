package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;

public class MobBucketItem extends BucketItem {
   private final EntityType<?> f_151134_;
   private final SoundEvent f_151135_;
   private final java.util.function.Supplier<? extends EntityType<?>> fishTypeSupplier;
   private final java.util.function.Supplier<? extends SoundEvent> emptySoundSupplier;

   @Deprecated
   public MobBucketItem(EntityType<?> p_151137_, Fluid p_151138_, SoundEvent p_151139_, Item.Properties p_151140_) {
      super(p_151138_, p_151140_);
      this.f_151134_ = p_151137_;
      this.f_151135_ = p_151139_;
      this.emptySoundSupplier = null;
      this.fishTypeSupplier = () -> p_151137_;
   }

   public MobBucketItem(java.util.function.Supplier<? extends EntityType<?>> entitySupplier, java.util.function.Supplier<? extends Fluid> fluidSupplier, java.util.function.Supplier<? extends SoundEvent> soundSupplier, Item.Properties properties) {
      super(fluidSupplier, properties);
      this.f_151134_ = null;
      this.f_151135_ = null;
      this.emptySoundSupplier = soundSupplier;
      this.fishTypeSupplier = entitySupplier;
   }

   public void m_142131_(@Nullable Player p_151146_, Level p_151147_, ItemStack p_151148_, BlockPos p_151149_) {
      if (p_151147_ instanceof ServerLevel) {
         this.m_151141_((ServerLevel)p_151147_, p_151148_, p_151149_);
         p_151147_.m_142346_(p_151146_, GameEvent.f_157810_, p_151149_);
      }

   }

   protected void m_7718_(@Nullable Player p_151151_, LevelAccessor p_151152_, BlockPos p_151153_) {
      p_151152_.m_5594_(p_151151_, p_151153_, this.f_151135_, SoundSource.NEUTRAL, 1.0F, 1.0F);
   }

   private void m_151141_(ServerLevel p_151142_, ItemStack p_151143_, BlockPos p_151144_) {
      Entity entity = this.f_151134_.m_20592_(p_151142_, p_151143_, (Player)null, p_151144_, MobSpawnType.BUCKET, true, false);
      if (entity instanceof Bucketable) {
         Bucketable bucketable = (Bucketable)entity;
         bucketable.m_142278_(p_151143_.m_41784_());
         bucketable.m_142139_(true);
      }

   }

   public void m_7373_(ItemStack p_151155_, @Nullable Level p_151156_, List<Component> p_151157_, TooltipFlag p_151158_) {
      if (this.f_151134_ == EntityType.f_20489_) {
         CompoundTag compoundtag = p_151155_.m_41783_();
         if (compoundtag != null && compoundtag.m_128425_("BucketVariantTag", 3)) {
            int i = compoundtag.m_128451_("BucketVariantTag");
            ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
            String s = "color.minecraft." + TropicalFish.m_30050_(i);
            String s1 = "color.minecraft." + TropicalFish.m_30052_(i);

            for(int j = 0; j < TropicalFish.f_30007_.length; ++j) {
               if (i == TropicalFish.f_30007_[j]) {
                  p_151157_.add((new TranslatableComponent(TropicalFish.m_30030_(j))).m_130944_(achatformatting));
                  return;
               }
            }

            p_151157_.add((new TranslatableComponent(TropicalFish.m_30054_(i))).m_130944_(achatformatting));
            MutableComponent mutablecomponent = new TranslatableComponent(s);
            if (!s.equals(s1)) {
               mutablecomponent.m_130946_(", ").m_7220_(new TranslatableComponent(s1));
            }

            mutablecomponent.m_130944_(achatformatting);
            p_151157_.add(mutablecomponent);
         }
      }
   }

   // Forge Start
   protected EntityType<?> getFishType() {
      return fishTypeSupplier.get();
   }

   protected SoundEvent getEmptySound() {
      return emptySoundSupplier.get();
   }
}
