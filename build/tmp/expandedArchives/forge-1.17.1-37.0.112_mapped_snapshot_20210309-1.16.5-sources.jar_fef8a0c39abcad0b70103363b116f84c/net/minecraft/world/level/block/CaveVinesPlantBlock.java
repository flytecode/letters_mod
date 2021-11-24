package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class CaveVinesPlantBlock extends GrowingPlantBodyBlock implements BonemealableBlock, CaveVines {
   public CaveVinesPlantBlock(BlockBehaviour.Properties p_153000_) {
      super(p_153000_, Direction.DOWN, f_152948_, false);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_152949_, Boolean.valueOf(false)));
   }

   protected GrowingPlantHeadBlock m_7272_() {
      return (GrowingPlantHeadBlock)Blocks.f_152538_;
   }

   protected BlockState m_142644_(BlockState p_153028_, BlockState p_153029_) {
      return p_153029_.m_61124_(f_152949_, p_153028_.m_61143_(f_152949_));
   }

   public ItemStack m_7397_(BlockGetter p_153007_, BlockPos p_153008_, BlockState p_153009_) {
      return new ItemStack(Items.f_151079_);
   }

   public InteractionResult m_6227_(BlockState p_153021_, Level p_153022_, BlockPos p_153023_, Player p_153024_, InteractionHand p_153025_, BlockHitResult p_153026_) {
      return CaveVines.m_152953_(p_153021_, p_153022_, p_153023_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153031_) {
      p_153031_.m_61104_(f_152949_);
   }

   public boolean m_7370_(BlockGetter p_153011_, BlockPos p_153012_, BlockState p_153013_, boolean p_153014_) {
      return !p_153013_.m_61143_(f_152949_);
   }

   public boolean m_5491_(Level p_153016_, Random p_153017_, BlockPos p_153018_, BlockState p_153019_) {
      return true;
   }

   public void m_7719_(ServerLevel p_153002_, Random p_153003_, BlockPos p_153004_, BlockState p_153005_) {
      p_153002_.m_7731_(p_153004_, p_153005_.m_61124_(f_152949_, Boolean.valueOf(true)), 2);
   }
}