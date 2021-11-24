package net.minecraft.world.level.block.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantmentTableBlockEntity extends BlockEntity implements Nameable {
   public int f_59251_;
   public float f_59252_;
   public float f_59253_;
   public float f_59254_;
   public float f_59255_;
   public float f_59256_;
   public float f_59257_;
   public float f_59258_;
   public float f_59259_;
   public float f_59260_;
   private static final Random f_59261_ = new Random();
   private Component f_59262_;

   public EnchantmentTableBlockEntity(BlockPos p_155501_, BlockState p_155502_) {
      super(BlockEntityType.f_58928_, p_155501_, p_155502_);
   }

   public CompoundTag m_6945_(CompoundTag p_59271_) {
      super.m_6945_(p_59271_);
      if (this.m_8077_()) {
         p_59271_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_59262_));
      }

      return p_59271_;
   }

   public void m_142466_(CompoundTag p_155509_) {
      super.m_142466_(p_155509_);
      if (p_155509_.m_128425_("CustomName", 8)) {
         this.f_59262_ = Component.Serializer.m_130701_(p_155509_.m_128461_("CustomName"));
      }

   }

   public static void m_155503_(Level p_155504_, BlockPos p_155505_, BlockState p_155506_, EnchantmentTableBlockEntity p_155507_) {
      p_155507_.f_59257_ = p_155507_.f_59256_;
      p_155507_.f_59259_ = p_155507_.f_59258_;
      Player player = p_155504_.m_45924_((double)p_155505_.m_123341_() + 0.5D, (double)p_155505_.m_123342_() + 0.5D, (double)p_155505_.m_123343_() + 0.5D, 3.0D, false);
      if (player != null) {
         double d0 = player.m_20185_() - ((double)p_155505_.m_123341_() + 0.5D);
         double d1 = player.m_20189_() - ((double)p_155505_.m_123343_() + 0.5D);
         p_155507_.f_59260_ = (float)Mth.m_14136_(d1, d0);
         p_155507_.f_59256_ += 0.1F;
         if (p_155507_.f_59256_ < 0.5F || f_59261_.nextInt(40) == 0) {
            float f1 = p_155507_.f_59254_;

            do {
               p_155507_.f_59254_ += (float)(f_59261_.nextInt(4) - f_59261_.nextInt(4));
            } while(f1 == p_155507_.f_59254_);
         }
      } else {
         p_155507_.f_59260_ += 0.02F;
         p_155507_.f_59256_ -= 0.1F;
      }

      while(p_155507_.f_59258_ >= (float)Math.PI) {
         p_155507_.f_59258_ -= ((float)Math.PI * 2F);
      }

      while(p_155507_.f_59258_ < -(float)Math.PI) {
         p_155507_.f_59258_ += ((float)Math.PI * 2F);
      }

      while(p_155507_.f_59260_ >= (float)Math.PI) {
         p_155507_.f_59260_ -= ((float)Math.PI * 2F);
      }

      while(p_155507_.f_59260_ < -(float)Math.PI) {
         p_155507_.f_59260_ += ((float)Math.PI * 2F);
      }

      float f2;
      for(f2 = p_155507_.f_59260_ - p_155507_.f_59258_; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
      }

      while(f2 < -(float)Math.PI) {
         f2 += ((float)Math.PI * 2F);
      }

      p_155507_.f_59258_ += f2 * 0.4F;
      p_155507_.f_59256_ = Mth.m_14036_(p_155507_.f_59256_, 0.0F, 1.0F);
      ++p_155507_.f_59251_;
      p_155507_.f_59253_ = p_155507_.f_59252_;
      float f = (p_155507_.f_59254_ - p_155507_.f_59252_) * 0.4F;
      float f3 = 0.2F;
      f = Mth.m_14036_(f, -0.2F, 0.2F);
      p_155507_.f_59255_ += (f - p_155507_.f_59255_) * 0.9F;
      p_155507_.f_59252_ += p_155507_.f_59255_;
   }

   public Component m_7755_() {
      return (Component)(this.f_59262_ != null ? this.f_59262_ : new TranslatableComponent("container.enchant"));
   }

   public void m_59272_(@Nullable Component p_59273_) {
      this.f_59262_ = p_59273_;
   }

   @Nullable
   public Component m_7770_() {
      return this.f_59262_;
   }
}