package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public abstract class AbstractProjectileDispenseBehavior extends DefaultDispenseItemBehavior {
   public ItemStack m_7498_(BlockSource p_123366_, ItemStack p_123367_) {
      Level level = p_123366_.m_7727_();
      Position position = DispenserBlock.m_52720_(p_123366_);
      Direction direction = p_123366_.m_6414_().m_61143_(DispenserBlock.f_52659_);
      Projectile projectile = this.m_6895_(level, position, p_123367_);
      projectile.m_6686_((double)direction.m_122429_(), (double)((float)direction.m_122430_() + 0.1F), (double)direction.m_122431_(), this.m_7104_(), this.m_7101_());
      level.m_7967_(projectile);
      p_123367_.m_41774_(1);
      return p_123367_;
   }

   protected void m_6823_(BlockSource p_123364_) {
      p_123364_.m_7727_().m_46796_(1002, p_123364_.m_7961_(), 0);
   }

   protected abstract Projectile m_6895_(Level p_123360_, Position p_123361_, ItemStack p_123362_);

   protected float m_7101_() {
      return 6.0F;
   }

   protected float m_7104_() {
      return 1.1F;
   }
}