package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class BoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
   private final DefaultDispenseItemBehavior f_123368_ = new DefaultDispenseItemBehavior();
   private final Boat.Type f_123369_;

   public BoatDispenseItemBehavior(Boat.Type p_123371_) {
      this.f_123369_ = p_123371_;
   }

   public ItemStack m_7498_(BlockSource p_123375_, ItemStack p_123376_) {
      Direction direction = p_123375_.m_6414_().m_61143_(DispenserBlock.f_52659_);
      Level level = p_123375_.m_7727_();
      double d0 = p_123375_.m_7096_() + (double)((float)direction.m_122429_() * 1.125F);
      double d1 = p_123375_.m_7098_() + (double)((float)direction.m_122430_() * 1.125F);
      double d2 = p_123375_.m_7094_() + (double)((float)direction.m_122431_() * 1.125F);
      BlockPos blockpos = p_123375_.m_7961_().m_142300_(direction);
      double d3;
      if (level.m_6425_(blockpos).m_76153_(FluidTags.f_13131_)) {
         d3 = 1.0D;
      } else {
         if (!level.m_8055_(blockpos).m_60795_() || !level.m_6425_(blockpos.m_7495_()).m_76153_(FluidTags.f_13131_)) {
            return this.f_123368_.m_6115_(p_123375_, p_123376_);
         }

         d3 = 0.0D;
      }

      Boat boat = new Boat(level, d0, d1 + d3, d2);
      boat.m_38332_(this.f_123369_);
      boat.m_146922_(direction.m_122435_());
      level.m_7967_(boat);
      p_123376_.m_41774_(1);
      return p_123376_;
   }

   protected void m_6823_(BlockSource p_123373_) {
      p_123373_.m_7727_().m_46796_(1000, p_123373_.m_7961_(), 0);
   }
}