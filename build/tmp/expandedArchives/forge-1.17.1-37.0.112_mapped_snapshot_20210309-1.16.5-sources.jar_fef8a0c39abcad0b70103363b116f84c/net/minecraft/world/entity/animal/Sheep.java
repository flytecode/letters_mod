package net.minecraft.world.entity.animal;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class Sheep extends Animal implements Shearable, net.minecraftforge.common.IForgeShearable {
   private static final int f_149039_ = 40;
   private static final EntityDataAccessor<Byte> f_29799_ = SynchedEntityData.m_135353_(Sheep.class, EntityDataSerializers.f_135027_);
   private static final Map<DyeColor, ItemLike> f_29800_ = Util.m_137469_(Maps.newEnumMap(DyeColor.class), (p_29841_) -> {
      p_29841_.put(DyeColor.WHITE, Blocks.f_50041_);
      p_29841_.put(DyeColor.ORANGE, Blocks.f_50042_);
      p_29841_.put(DyeColor.MAGENTA, Blocks.f_50096_);
      p_29841_.put(DyeColor.LIGHT_BLUE, Blocks.f_50097_);
      p_29841_.put(DyeColor.YELLOW, Blocks.f_50098_);
      p_29841_.put(DyeColor.LIME, Blocks.f_50099_);
      p_29841_.put(DyeColor.PINK, Blocks.f_50100_);
      p_29841_.put(DyeColor.GRAY, Blocks.f_50101_);
      p_29841_.put(DyeColor.LIGHT_GRAY, Blocks.f_50102_);
      p_29841_.put(DyeColor.CYAN, Blocks.f_50103_);
      p_29841_.put(DyeColor.PURPLE, Blocks.f_50104_);
      p_29841_.put(DyeColor.BLUE, Blocks.f_50105_);
      p_29841_.put(DyeColor.BROWN, Blocks.f_50106_);
      p_29841_.put(DyeColor.GREEN, Blocks.f_50107_);
      p_29841_.put(DyeColor.RED, Blocks.f_50108_);
      p_29841_.put(DyeColor.BLACK, Blocks.f_50109_);
   });
   private static final Map<DyeColor, float[]> f_29801_ = Maps.<DyeColor, float[]>newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((p_29868_) -> {
      return p_29868_;
   }, Sheep::m_29865_)));
   private int f_29802_;
   private EatBlockGoal f_29803_;

   private static float[] m_29865_(DyeColor p_29866_) {
      if (p_29866_ == DyeColor.WHITE) {
         return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
      } else {
         float[] afloat = p_29866_.m_41068_();
         float f = 0.75F;
         return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
      }
   }

   public static float[] m_29829_(DyeColor p_29830_) {
      return f_29801_.get(p_29830_);
   }

   public Sheep(EntityType<? extends Sheep> p_29806_, Level p_29807_) {
      super(p_29806_, p_29807_);
   }

   protected void m_8099_() {
      this.f_29803_ = new EatBlockGoal(this);
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new PanicGoal(this, 1.25D));
      this.f_21345_.m_25352_(2, new BreedGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new TemptGoal(this, 1.1D, Ingredient.m_43929_(Items.f_42405_), false));
      this.f_21345_.m_25352_(4, new FollowParentGoal(this, 1.1D));
      this.f_21345_.m_25352_(5, this.f_29803_);
      this.f_21345_.m_25352_(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
   }

   protected void m_8024_() {
      this.f_29802_ = this.f_29803_.m_25213_();
      super.m_8024_();
   }

   public void m_8107_() {
      if (this.f_19853_.f_46443_) {
         this.f_29802_ = Math.max(0, this.f_29802_ - 1);
      }

      super.m_8107_();
   }

   public static AttributeSupplier.Builder m_29873_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 8.0D).m_22268_(Attributes.f_22279_, (double)0.23F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29799_, (byte)0);
   }

   public ResourceLocation m_7582_() {
      if (this.m_29875_()) {
         return this.m_6095_().m_20677_();
      } else {
         switch(this.m_29874_()) {
         case WHITE:
         default:
            return BuiltInLootTables.f_78702_;
         case ORANGE:
            return BuiltInLootTables.f_78703_;
         case MAGENTA:
            return BuiltInLootTables.f_78704_;
         case LIGHT_BLUE:
            return BuiltInLootTables.f_78705_;
         case YELLOW:
            return BuiltInLootTables.f_78706_;
         case LIME:
            return BuiltInLootTables.f_78707_;
         case PINK:
            return BuiltInLootTables.f_78708_;
         case GRAY:
            return BuiltInLootTables.f_78709_;
         case LIGHT_GRAY:
            return BuiltInLootTables.f_78710_;
         case CYAN:
            return BuiltInLootTables.f_78711_;
         case PURPLE:
            return BuiltInLootTables.f_78714_;
         case BLUE:
            return BuiltInLootTables.f_78715_;
         case BROWN:
            return BuiltInLootTables.f_78716_;
         case GREEN:
            return BuiltInLootTables.f_78717_;
         case RED:
            return BuiltInLootTables.f_78718_;
         case BLACK:
            return BuiltInLootTables.f_78719_;
         }
      }
   }

   public void m_7822_(byte p_29814_) {
      if (p_29814_ == 10) {
         this.f_29802_ = 40;
      } else {
         super.m_7822_(p_29814_);
      }

   }

   public float m_29880_(float p_29881_) {
      if (this.f_29802_ <= 0) {
         return 0.0F;
      } else if (this.f_29802_ >= 4 && this.f_29802_ <= 36) {
         return 1.0F;
      } else {
         return this.f_29802_ < 4 ? ((float)this.f_29802_ - p_29881_) / 4.0F : -((float)(this.f_29802_ - 40) - p_29881_) / 4.0F;
      }
   }

   public float m_29882_(float p_29883_) {
      if (this.f_29802_ > 4 && this.f_29802_ <= 36) {
         float f = ((float)(this.f_29802_ - 4) - p_29883_) / 32.0F;
         return ((float)Math.PI / 5F) + 0.21991149F * Mth.m_14031_(f * 28.7F);
      } else {
         return this.f_29802_ > 0 ? ((float)Math.PI / 5F) : this.m_146909_() * ((float)Math.PI / 180F);
      }
   }

   public InteractionResult m_6071_(Player p_29853_, InteractionHand p_29854_) {
      ItemStack itemstack = p_29853_.m_21120_(p_29854_);
      if (false && itemstack.m_41720_() == Items.f_42574_) { //Forge: Moved to onSheared
         if (!this.f_19853_.f_46443_ && this.m_6220_()) {
            this.m_5851_(SoundSource.PLAYERS);
            this.m_146852_(GameEvent.f_157781_, p_29853_);
            itemstack.m_41622_(1, p_29853_, (p_29822_) -> {
               p_29822_.m_21190_(p_29854_);
            });
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.CONSUME;
         }
      } else {
         return super.m_6071_(p_29853_, p_29854_);
      }
   }

   public void m_5851_(SoundSource p_29819_) {
      this.f_19853_.m_6269_((Player)null, this, SoundEvents.f_12344_, p_29819_, 1.0F, 1.0F);
      this.m_29878_(true);
      int i = 1 + this.f_19796_.nextInt(3);

      for(int j = 0; j < i; ++j) {
         ItemEntity itementity = this.m_20000_(f_29800_.get(this.m_29874_()), 1);
         if (itementity != null) {
            itementity.m_20256_(itementity.m_20184_().m_82520_((double)((this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.1F), (double)(this.f_19796_.nextFloat() * 0.05F), (double)((this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.1F)));
         }
      }

   }

   public boolean m_6220_() {
      return this.m_6084_() && !this.m_29875_() && !this.m_6162_();
   }

   public void m_7380_(CompoundTag p_29864_) {
      super.m_7380_(p_29864_);
      p_29864_.m_128379_("Sheared", this.m_29875_());
      p_29864_.m_128344_("Color", (byte)this.m_29874_().m_41060_());
   }

   public void m_7378_(CompoundTag p_29845_) {
      super.m_7378_(p_29845_);
      this.m_29878_(p_29845_.m_128471_("Sheared"));
      this.m_29855_(DyeColor.m_41053_(p_29845_.m_128445_("Color")));
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12341_;
   }

   protected SoundEvent m_7975_(DamageSource p_29872_) {
      return SoundEvents.f_12343_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12342_;
   }

   protected void m_7355_(BlockPos p_29861_, BlockState p_29862_) {
      this.m_5496_(SoundEvents.f_12345_, 0.15F, 1.0F);
   }

   public DyeColor m_29874_() {
      return DyeColor.m_41053_(this.f_19804_.m_135370_(f_29799_) & 15);
   }

   public void m_29855_(DyeColor p_29856_) {
      byte b0 = this.f_19804_.m_135370_(f_29799_);
      this.f_19804_.m_135381_(f_29799_, (byte)(b0 & 240 | p_29856_.m_41060_() & 15));
   }

   public boolean m_29875_() {
      return (this.f_19804_.m_135370_(f_29799_) & 16) != 0;
   }

   public void m_29878_(boolean p_29879_) {
      byte b0 = this.f_19804_.m_135370_(f_29799_);
      if (p_29879_) {
         this.f_19804_.m_135381_(f_29799_, (byte)(b0 | 16));
      } else {
         this.f_19804_.m_135381_(f_29799_, (byte)(b0 & -17));
      }

   }

   public static DyeColor m_29842_(Random p_29843_) {
      int i = p_29843_.nextInt(100);
      if (i < 5) {
         return DyeColor.BLACK;
      } else if (i < 10) {
         return DyeColor.GRAY;
      } else if (i < 15) {
         return DyeColor.LIGHT_GRAY;
      } else if (i < 18) {
         return DyeColor.BROWN;
      } else {
         return p_29843_.nextInt(500) == 0 ? DyeColor.PINK : DyeColor.WHITE;
      }
   }

   public Sheep m_142606_(ServerLevel p_149044_, AgeableMob p_149045_) {
      Sheep sheep = (Sheep)p_149045_;
      Sheep sheep1 = EntityType.f_20520_.m_20615_(p_149044_);
      sheep1.m_29855_(this.m_29823_(this, sheep));
      return sheep1;
   }

   public void m_8035_() {
      this.m_29878_(false);
      if (this.m_6162_()) {
         this.m_146758_(60);
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_29835_, DifficultyInstance p_29836_, MobSpawnType p_29837_, @Nullable SpawnGroupData p_29838_, @Nullable CompoundTag p_29839_) {
      this.m_29855_(m_29842_(p_29835_.m_5822_()));
      return super.m_6518_(p_29835_, p_29836_, p_29837_, p_29838_, p_29839_);
   }

   private DyeColor m_29823_(Animal p_29824_, Animal p_29825_) {
      DyeColor dyecolor = ((Sheep)p_29824_).m_29874_();
      DyeColor dyecolor1 = ((Sheep)p_29825_).m_29874_();
      CraftingContainer craftingcontainer = m_29831_(dyecolor, dyecolor1);
      return this.f_19853_.m_7465_().m_44015_(RecipeType.f_44107_, craftingcontainer, this.f_19853_).map((p_29828_) -> {
         return p_29828_.m_5874_(craftingcontainer);
      }).map(ItemStack::m_41720_).filter(DyeItem.class::isInstance).map(DyeItem.class::cast).map(DyeItem::m_41089_).orElseGet(() -> {
         return this.f_19853_.f_46441_.nextBoolean() ? dyecolor : dyecolor1;
      });
   }

   private static CraftingContainer m_29831_(DyeColor p_29832_, DyeColor p_29833_) {
      CraftingContainer craftingcontainer = new CraftingContainer(new AbstractContainerMenu((MenuType)null, -1) {
         public boolean m_6875_(Player p_29888_) {
            return false;
         }
      }, 2, 1);
      craftingcontainer.m_6836_(0, new ItemStack(DyeItem.m_41082_(p_29832_)));
      craftingcontainer.m_6836_(1, new ItemStack(DyeItem.m_41082_(p_29833_)));
      return craftingcontainer;
   }

   protected float m_6431_(Pose p_29850_, EntityDimensions p_29851_) {
      return 0.95F * p_29851_.f_20378_;
   }

   @Override
   public boolean isShearable(@javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos) {
      return m_6220_();
   }

   @javax.annotation.Nonnull
   @Override
   public java.util.List<ItemStack> onSheared(@Nullable Player player, @javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
      world.m_6269_(null, this, SoundEvents.f_12344_, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
      if (!world.f_46443_) {
         this.m_29878_(true);
         int i = 1 + this.f_19796_.nextInt(3);

         java.util.List<ItemStack> items = new java.util.ArrayList<>();
         for (int j = 0; j < i; ++j) {
            items.add(new ItemStack(f_29800_.get(this.m_29874_())));
         }
         return items;
      }
      return java.util.Collections.emptyList();
   }
}
