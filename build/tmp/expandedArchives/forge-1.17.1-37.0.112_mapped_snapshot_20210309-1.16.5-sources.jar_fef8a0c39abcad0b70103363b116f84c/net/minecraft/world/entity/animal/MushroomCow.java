package net.minecraft.world.entity.animal;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.apache.commons.lang3.tuple.Pair;

public class MushroomCow extends Cow implements Shearable, net.minecraftforge.common.IForgeShearable {
   private static final EntityDataAccessor<String> f_28908_ = SynchedEntityData.m_135353_(MushroomCow.class, EntityDataSerializers.f_135030_);
   private static final int f_148934_ = 1024;
   private MobEffect f_28909_;
   private int f_28910_;
   private UUID f_28911_;

   public MushroomCow(EntityType<? extends MushroomCow> p_28914_, Level p_28915_) {
      super(p_28914_, p_28915_);
   }

   public float m_5610_(BlockPos p_28933_, LevelReader p_28934_) {
      return p_28934_.m_8055_(p_28933_.m_7495_()).m_60713_(Blocks.f_50195_) ? 10.0F : p_28934_.m_46863_(p_28933_) - 0.5F;
   }

   public static boolean m_28948_(EntityType<MushroomCow> p_28949_, LevelAccessor p_28950_, MobSpawnType p_28951_, BlockPos p_28952_, Random p_28953_) {
      return p_28950_.m_8055_(p_28952_.m_7495_()).m_60713_(Blocks.f_50195_) && p_28950_.m_45524_(p_28952_, 0) > 8;
   }

   public void m_8038_(ServerLevel p_28921_, LightningBolt p_28922_) {
      UUID uuid = p_28922_.m_142081_();
      if (!uuid.equals(this.f_28911_)) {
         this.m_28928_(this.m_28955_() == MushroomCow.MushroomType.RED ? MushroomCow.MushroomType.BROWN : MushroomCow.MushroomType.RED);
         this.f_28911_ = uuid;
         this.m_5496_(SoundEvents.f_12071_, 2.0F, 1.0F);
      }

   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_28908_, MushroomCow.MushroomType.RED.f_28960_);
   }

   public InteractionResult m_6071_(Player p_28941_, InteractionHand p_28942_) {
      ItemStack itemstack = p_28941_.m_21120_(p_28942_);
      if (itemstack.m_150930_(Items.f_42399_) && !this.m_6162_()) {
         boolean flag = false;
         ItemStack itemstack1;
         if (this.f_28909_ != null) {
            flag = true;
            itemstack1 = new ItemStack(Items.f_42718_);
            SuspiciousStewItem.m_43258_(itemstack1, this.f_28909_, this.f_28910_);
            this.f_28909_ = null;
            this.f_28910_ = 0;
         } else {
            itemstack1 = new ItemStack(Items.f_42400_);
         }

         ItemStack itemstack2 = ItemUtils.m_41817_(itemstack, p_28941_, itemstack1, false);
         p_28941_.m_21008_(p_28942_, itemstack2);
         SoundEvent soundevent;
         if (flag) {
            soundevent = SoundEvents.f_12074_;
         } else {
            soundevent = SoundEvents.f_12073_;
         }

         this.m_5496_(soundevent, 1.0F, 1.0F);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else if (false && itemstack.m_41720_() == Items.f_42574_ && this.m_6220_()) { //Forge: Moved to onSheared
         this.m_5851_(SoundSource.PLAYERS);
         this.m_146852_(GameEvent.f_157781_, p_28941_);
         if (!this.f_19853_.f_46443_) {
            itemstack.m_41622_(1, p_28941_, (p_28927_) -> {
               p_28927_.m_21190_(p_28942_);
            });
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else if (this.m_28955_() == MushroomCow.MushroomType.BROWN && itemstack.m_150922_(ItemTags.f_13145_)) {
         if (this.f_28909_ != null) {
            for(int i = 0; i < 2; ++i) {
               this.f_19853_.m_7106_(ParticleTypes.f_123762_, this.m_20185_() + this.f_19796_.nextDouble() / 2.0D, this.m_20227_(0.5D), this.m_20189_() + this.f_19796_.nextDouble() / 2.0D, 0.0D, this.f_19796_.nextDouble() / 5.0D, 0.0D);
            }
         } else {
            Optional<Pair<MobEffect, Integer>> optional = this.m_28956_(itemstack);
            if (!optional.isPresent()) {
               return InteractionResult.PASS;
            }

            Pair<MobEffect, Integer> pair = optional.get();
            if (!p_28941_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }

            for(int j = 0; j < 4; ++j) {
               this.f_19853_.m_7106_(ParticleTypes.f_123806_, this.m_20185_() + this.f_19796_.nextDouble() / 2.0D, this.m_20227_(0.5D), this.m_20189_() + this.f_19796_.nextDouble() / 2.0D, 0.0D, this.f_19796_.nextDouble() / 5.0D, 0.0D);
            }

            this.f_28909_ = pair.getLeft();
            this.f_28910_ = pair.getRight();
            this.m_5496_(SoundEvents.f_12072_, 2.0F, 1.0F);
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         return super.m_6071_(p_28941_, p_28942_);
      }
   }

   @Override
   public java.util.List<ItemStack> onSheared(@javax.annotation.Nullable Player player, @javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
      return shearInternal(player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS);
   }

   public void m_5851_(SoundSource p_28924_) {
      shearInternal(p_28924_).forEach(s -> this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20227_(1.0D), this.m_20189_(), s)));
   }

   private java.util.List<ItemStack> shearInternal(SoundSource p_28924_) {
      this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12075_, p_28924_, 1.0F, 1.0F);
      if (!this.f_19853_.m_5776_()) {
         ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123813_, this.m_20185_(), this.m_20227_(0.5D), this.m_20189_(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
         this.m_146870_();
         Cow cow = EntityType.f_20557_.m_20615_(this.f_19853_);
         cow.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
         cow.m_21153_(this.m_21223_());
         cow.f_20883_ = this.f_20883_;
         if (this.m_8077_()) {
            cow.m_6593_(this.m_7770_());
            cow.m_20340_(this.m_20151_());
         }

         if (this.m_21532_()) {
            cow.m_21530_();
         }

         cow.m_20331_(this.m_20147_());
         this.f_19853_.m_7967_(cow);

         java.util.List<ItemStack> items = new java.util.ArrayList<>();
         for(int i = 0; i < 5; ++i) {
            items.add(new ItemStack(this.m_28955_().f_28961_.m_60734_()));
         }
         return items;
      }
      return java.util.Collections.emptyList();

   }

   public boolean m_6220_() {
      return this.m_6084_() && !this.m_6162_();
   }

   public void m_7380_(CompoundTag p_28944_) {
      super.m_7380_(p_28944_);
      p_28944_.m_128359_("Type", this.m_28955_().f_28960_);
      if (this.f_28909_ != null) {
         p_28944_.m_128344_("EffectId", (byte)MobEffect.m_19459_(this.f_28909_));
         p_28944_.m_128405_("EffectDuration", this.f_28910_);
      }

   }

   public void m_7378_(CompoundTag p_28936_) {
      super.m_7378_(p_28936_);
      this.m_28928_(MushroomCow.MushroomType.m_28976_(p_28936_.m_128461_("Type")));
      if (p_28936_.m_128425_("EffectId", 1)) {
         this.f_28909_ = MobEffect.m_19453_(p_28936_.m_128445_("EffectId"));
      }

      if (p_28936_.m_128425_("EffectDuration", 3)) {
         this.f_28910_ = p_28936_.m_128451_("EffectDuration");
      }

   }

   private Optional<Pair<MobEffect, Integer>> m_28956_(ItemStack p_28957_) {
      Item item = p_28957_.m_41720_();
      if (item instanceof BlockItem) {
         Block block = ((BlockItem)item).m_40614_();
         if (block instanceof FlowerBlock) {
            FlowerBlock flowerblock = (FlowerBlock)block;
            return Optional.of(Pair.of(flowerblock.m_53521_(), flowerblock.m_53522_()));
         }
      }

      return Optional.empty();
   }

   private void m_28928_(MushroomCow.MushroomType p_28929_) {
      this.f_19804_.m_135381_(f_28908_, p_28929_.f_28960_);
   }

   public MushroomCow.MushroomType m_28955_() {
      return MushroomCow.MushroomType.m_28976_(this.f_19804_.m_135370_(f_28908_));
   }

   public MushroomCow m_142606_(ServerLevel p_148942_, AgeableMob p_148943_) {
      MushroomCow mushroomcow = EntityType.f_20504_.m_20615_(p_148942_);
      mushroomcow.m_28928_(this.m_28930_((MushroomCow)p_148943_));
      return mushroomcow;
   }

   private MushroomCow.MushroomType m_28930_(MushroomCow p_28931_) {
      MushroomCow.MushroomType mushroomcow$mushroomtype = this.m_28955_();
      MushroomCow.MushroomType mushroomcow$mushroomtype1 = p_28931_.m_28955_();
      MushroomCow.MushroomType mushroomcow$mushroomtype2;
      if (mushroomcow$mushroomtype == mushroomcow$mushroomtype1 && this.f_19796_.nextInt(1024) == 0) {
         mushroomcow$mushroomtype2 = mushroomcow$mushroomtype == MushroomCow.MushroomType.BROWN ? MushroomCow.MushroomType.RED : MushroomCow.MushroomType.BROWN;
      } else {
         mushroomcow$mushroomtype2 = this.f_19796_.nextBoolean() ? mushroomcow$mushroomtype : mushroomcow$mushroomtype1;
      }

      return mushroomcow$mushroomtype2;
   }

   @Override
   public boolean isShearable(@javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos) {
      return m_6220_();
   }

   public static enum MushroomType {
      RED("red", Blocks.f_50073_.m_49966_()),
      BROWN("brown", Blocks.f_50072_.m_49966_());

      final String f_28960_;
      final BlockState f_28961_;

      private MushroomType(String p_28967_, BlockState p_28968_) {
         this.f_28960_ = p_28967_;
         this.f_28961_ = p_28968_;
      }

      public BlockState m_28969_() {
         return this.f_28961_;
      }

      static MushroomCow.MushroomType m_28976_(String p_28977_) {
         for(MushroomCow.MushroomType mushroomcow$mushroomtype : values()) {
            if (mushroomcow$mushroomtype.f_28960_.equals(p_28977_)) {
               return mushroomcow$mushroomtype;
            }
         }

         return RED;
      }
   }
}
