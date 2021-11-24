package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DefaultDispenseItemBehavior implements DispenseItemBehavior {
   public final ItemStack m_6115_(BlockSource p_123391_, ItemStack p_123392_) {
      ItemStack itemstack = this.m_7498_(p_123391_, p_123392_);
      this.m_6823_(p_123391_);
      this.m_123387_(p_123391_, p_123391_.m_6414_().m_61143_(DispenserBlock.f_52659_));
      return itemstack;
   }

   protected ItemStack m_7498_(BlockSource p_123385_, ItemStack p_123386_) {
      Direction direction = p_123385_.m_6414_().m_61143_(DispenserBlock.f_52659_);
      Position position = DispenserBlock.m_52720_(p_123385_);
      ItemStack itemstack = p_123386_.m_41620_(1);
      m_123378_(p_123385_.m_7727_(), itemstack, 6, direction, position);
      return p_123386_;
   }

   public static void m_123378_(Level p_123379_, ItemStack p_123380_, int p_123381_, Direction p_123382_, Position p_123383_) {
      double d0 = p_123383_.m_7096_();
      double d1 = p_123383_.m_7098_();
      double d2 = p_123383_.m_7094_();
      if (p_123382_.m_122434_() == Direction.Axis.Y) {
         d1 = d1 - 0.125D;
      } else {
         d1 = d1 - 0.15625D;
      }

      ItemEntity itementity = new ItemEntity(p_123379_, d0, d1, d2, p_123380_);
      double d3 = p_123379_.f_46441_.nextDouble() * 0.1D + 0.2D;
      itementity.m_20334_(p_123379_.f_46441_.nextGaussian() * (double)0.0075F * (double)p_123381_ + (double)p_123382_.m_122429_() * d3, p_123379_.f_46441_.nextGaussian() * (double)0.0075F * (double)p_123381_ + (double)0.2F, p_123379_.f_46441_.nextGaussian() * (double)0.0075F * (double)p_123381_ + (double)p_123382_.m_122431_() * d3);
      p_123379_.m_7967_(itementity);
   }

   protected void m_6823_(BlockSource p_123384_) {
      p_123384_.m_7727_().m_46796_(1000, p_123384_.m_7961_(), 0);
   }

   protected void m_123387_(BlockSource p_123388_, Direction p_123389_) {
      p_123388_.m_7727_().m_46796_(2000, p_123388_.m_7961_(), p_123389_.m_122411_());
   }
}