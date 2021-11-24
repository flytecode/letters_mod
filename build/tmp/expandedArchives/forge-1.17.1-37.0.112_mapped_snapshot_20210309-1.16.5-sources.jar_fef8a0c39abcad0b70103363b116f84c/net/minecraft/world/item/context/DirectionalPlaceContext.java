package net.minecraft.world.item.context;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class DirectionalPlaceContext extends BlockPlaceContext {
   private final Direction f_43648_;

   public DirectionalPlaceContext(Level p_43650_, BlockPos p_43651_, Direction p_43652_, ItemStack p_43653_, Direction p_43654_) {
      super(p_43650_, (Player)null, InteractionHand.MAIN_HAND, p_43653_, new BlockHitResult(Vec3.m_82539_(p_43651_), p_43654_, p_43651_, false));
      this.f_43648_ = p_43652_;
   }

   public BlockPos m_8083_() {
      return this.m_43718_().m_82425_();
   }

   public boolean m_7059_() {
      return this.m_43725_().m_8055_(this.m_43718_().m_82425_()).m_60629_(this);
   }

   public boolean m_7058_() {
      return this.m_7059_();
   }

   public Direction m_7820_() {
      return Direction.DOWN;
   }

   public Direction[] m_6232_() {
      switch(this.f_43648_) {
      case DOWN:
      default:
         return new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};
      case UP:
         return new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
      case NORTH:
         return new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.SOUTH};
      case SOUTH:
         return new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.NORTH};
      case WEST:
         return new Direction[]{Direction.DOWN, Direction.WEST, Direction.SOUTH, Direction.UP, Direction.NORTH, Direction.EAST};
      case EAST:
         return new Direction[]{Direction.DOWN, Direction.EAST, Direction.SOUTH, Direction.UP, Direction.NORTH, Direction.WEST};
      }
   }

   public Direction m_8125_() {
      return this.f_43648_.m_122434_() == Direction.Axis.Y ? Direction.NORTH : this.f_43648_;
   }

   public boolean m_7078_() {
      return false;
   }

   public float m_7074_() {
      return (float)(this.f_43648_.m_122416_() * 90);
   }
}