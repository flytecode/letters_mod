package net.minecraft.world.level.block.entity;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CampfireBlockEntity extends BlockEntity implements Clearable {
   private static final int f_155298_ = 2;
   private static final int f_155299_ = 4;
   private final NonNullList<ItemStack> f_59042_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
   private final int[] f_59043_ = new int[4];
   private final int[] f_59044_ = new int[4];

   public CampfireBlockEntity(BlockPos p_155301_, BlockState p_155302_) {
      super(BlockEntityType.f_58911_, p_155301_, p_155302_);
   }

   public static void m_155306_(Level p_155307_, BlockPos p_155308_, BlockState p_155309_, CampfireBlockEntity p_155310_) {
      boolean flag = false;

      for(int i = 0; i < p_155310_.f_59042_.size(); ++i) {
         ItemStack itemstack = p_155310_.f_59042_.get(i);
         if (!itemstack.m_41619_()) {
            flag = true;
            int j = p_155310_.f_59043_[i]++;
            if (p_155310_.f_59043_[i] >= p_155310_.f_59044_[i]) {
               Container container = new SimpleContainer(itemstack);
               ItemStack itemstack1 = p_155307_.m_7465_().m_44015_(RecipeType.f_44111_, container, p_155307_).map((p_155305_) -> {
                  return p_155305_.m_5874_(container);
               }).orElse(itemstack);
               Containers.m_18992_(p_155307_, (double)p_155308_.m_123341_(), (double)p_155308_.m_123342_(), (double)p_155308_.m_123343_(), itemstack1);
               p_155310_.f_59042_.set(i, ItemStack.f_41583_);
               p_155307_.m_7260_(p_155308_, p_155309_, p_155309_, 3);
            }
         }
      }

      if (flag) {
         m_155232_(p_155307_, p_155308_, p_155309_);
      }

   }

   public static void m_155313_(Level p_155314_, BlockPos p_155315_, BlockState p_155316_, CampfireBlockEntity p_155317_) {
      boolean flag = false;

      for(int i = 0; i < p_155317_.f_59042_.size(); ++i) {
         if (p_155317_.f_59043_[i] > 0) {
            flag = true;
            p_155317_.f_59043_[i] = Mth.m_14045_(p_155317_.f_59043_[i] - 2, 0, p_155317_.f_59044_[i]);
         }
      }

      if (flag) {
         m_155232_(p_155314_, p_155315_, p_155316_);
      }

   }

   public static void m_155318_(Level p_155319_, BlockPos p_155320_, BlockState p_155321_, CampfireBlockEntity p_155322_) {
      Random random = p_155319_.f_46441_;
      if (random.nextFloat() < 0.11F) {
         for(int i = 0; i < random.nextInt(2) + 2; ++i) {
            CampfireBlock.m_51251_(p_155319_, p_155320_, p_155321_.m_61143_(CampfireBlock.f_51228_), false);
         }
      }

      int l = p_155321_.m_61143_(CampfireBlock.f_51230_).m_122416_();

      for(int j = 0; j < p_155322_.f_59042_.size(); ++j) {
         if (!p_155322_.f_59042_.get(j).m_41619_() && random.nextFloat() < 0.2F) {
            Direction direction = Direction.m_122407_(Math.floorMod(j + l, 4));
            float f = 0.3125F;
            double d0 = (double)p_155320_.m_123341_() + 0.5D - (double)((float)direction.m_122429_() * 0.3125F) + (double)((float)direction.m_122427_().m_122429_() * 0.3125F);
            double d1 = (double)p_155320_.m_123342_() + 0.5D;
            double d2 = (double)p_155320_.m_123343_() + 0.5D - (double)((float)direction.m_122431_() * 0.3125F) + (double)((float)direction.m_122427_().m_122431_() * 0.3125F);

            for(int k = 0; k < 4; ++k) {
               p_155319_.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
            }
         }
      }

   }

   public NonNullList<ItemStack> m_59065_() {
      return this.f_59042_;
   }

   public void m_142466_(CompoundTag p_155312_) {
      super.m_142466_(p_155312_);
      this.f_59042_.clear();
      ContainerHelper.m_18980_(p_155312_, this.f_59042_);
      if (p_155312_.m_128425_("CookingTimes", 11)) {
         int[] aint = p_155312_.m_128465_("CookingTimes");
         System.arraycopy(aint, 0, this.f_59043_, 0, Math.min(this.f_59044_.length, aint.length));
      }

      if (p_155312_.m_128425_("CookingTotalTimes", 11)) {
         int[] aint1 = p_155312_.m_128465_("CookingTotalTimes");
         System.arraycopy(aint1, 0, this.f_59044_, 0, Math.min(this.f_59044_.length, aint1.length));
      }

   }

   public CompoundTag m_6945_(CompoundTag p_59060_) {
      this.m_59063_(p_59060_);
      p_59060_.m_128385_("CookingTimes", this.f_59043_);
      p_59060_.m_128385_("CookingTotalTimes", this.f_59044_);
      return p_59060_;
   }

   private CompoundTag m_59063_(CompoundTag p_59064_) {
      super.m_6945_(p_59064_);
      ContainerHelper.m_18976_(p_59064_, this.f_59042_, true);
      return p_59064_;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 13, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_59063_(new CompoundTag());
   }

   public Optional<CampfireCookingRecipe> m_59051_(ItemStack p_59052_) {
      return this.f_59042_.stream().noneMatch(ItemStack::m_41619_) ? Optional.empty() : this.f_58857_.m_7465_().m_44015_(RecipeType.f_44111_, new SimpleContainer(p_59052_), this.f_58857_);
   }

   public boolean m_59053_(ItemStack p_59054_, int p_59055_) {
      for(int i = 0; i < this.f_59042_.size(); ++i) {
         ItemStack itemstack = this.f_59042_.get(i);
         if (itemstack.m_41619_()) {
            this.f_59044_[i] = p_59055_;
            this.f_59043_[i] = 0;
            this.f_59042_.set(i, p_59054_.m_41620_(1));
            this.m_59069_();
            return true;
         }
      }

      return false;
   }

   private void m_59069_() {
      this.m_6596_();
      this.m_58904_().m_7260_(this.m_58899_(), this.m_58900_(), this.m_58900_(), 3);
   }

   public void m_6211_() {
      this.f_59042_.clear();
   }

   public void m_59066_() {
      if (this.f_58857_ != null) {
         this.m_59069_();
      }

   }
}