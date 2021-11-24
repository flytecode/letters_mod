package net.minecraft.world.item.context;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlockPlaceContext extends UseOnContext {
   private final BlockPos f_43629_;
   protected boolean f_43628_ = true;

   public BlockPlaceContext(Player p_43631_, InteractionHand p_43632_, ItemStack p_43633_, BlockHitResult p_43634_) {
      this(p_43631_.f_19853_, p_43631_, p_43632_, p_43633_, p_43634_);
   }

   public BlockPlaceContext(UseOnContext p_43636_) {
      this(p_43636_.m_43725_(), p_43636_.m_43723_(), p_43636_.m_43724_(), p_43636_.m_43722_(), p_43636_.m_43718_());
   }

   public BlockPlaceContext(Level p_43638_, @Nullable Player p_43639_, InteractionHand p_43640_, ItemStack p_43641_, BlockHitResult p_43642_) {
      super(p_43638_, p_43639_, p_43640_, p_43641_, p_43642_);
      this.f_43629_ = p_43642_.m_82425_().m_142300_(p_43642_.m_82434_());
      this.f_43628_ = p_43638_.m_8055_(p_43642_.m_82425_()).m_60629_(this);
   }

   public static BlockPlaceContext m_43644_(BlockPlaceContext p_43645_, BlockPos p_43646_, Direction p_43647_) {
      return new BlockPlaceContext(p_43645_.m_43725_(), p_43645_.m_43723_(), p_43645_.m_43724_(), p_43645_.m_43722_(), new BlockHitResult(new Vec3((double)p_43646_.m_123341_() + 0.5D + (double)p_43647_.m_122429_() * 0.5D, (double)p_43646_.m_123342_() + 0.5D + (double)p_43647_.m_122430_() * 0.5D, (double)p_43646_.m_123343_() + 0.5D + (double)p_43647_.m_122431_() * 0.5D), p_43647_, p_43646_, false));
   }

   public BlockPos m_8083_() {
      return this.f_43628_ ? super.m_8083_() : this.f_43629_;
   }

   public boolean m_7059_() {
      return this.f_43628_ || this.m_43725_().m_8055_(this.m_8083_()).m_60629_(this);
   }

   public boolean m_7058_() {
      return this.f_43628_;
   }

   public Direction m_7820_() {
      return Direction.m_122382_(this.m_43723_())[0];
   }

   public Direction m_151260_() {
      return Direction.m_175357_(this.m_43723_(), Direction.Axis.Y);
   }

   public Direction[] m_6232_() {
      Direction[] adirection = Direction.m_122382_(this.m_43723_());
      if (this.f_43628_) {
         return adirection;
      } else {
         Direction direction = this.m_43719_();

         int i;
         for(i = 0; i < adirection.length && adirection[i] != direction.m_122424_(); ++i) {
         }

         if (i > 0) {
            System.arraycopy(adirection, 0, adirection, 1, i);
            adirection[0] = direction.m_122424_();
         }

         return adirection;
      }
   }
}