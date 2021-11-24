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

public class CaveVinesBlock extends GrowingPlantHeadBlock implements BonemealableBlock, CaveVines {
   private static final float f_152957_ = 0.11F;

   public CaveVinesBlock(BlockBehaviour.Properties p_152959_) {
      super(p_152959_, Direction.DOWN, f_152948_, false, 0.1D);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_53924_, Integer.valueOf(0)).m_61124_(f_152949_, Boolean.valueOf(false)));
   }

   protected int m_7363_(Random p_152995_) {
      return 1;
   }

   protected boolean m_5971_(BlockState p_152998_) {
      return p_152998_.m_60795_();
   }

   protected Block m_7777_() {
      return Blocks.f_152539_;
   }

   protected BlockState m_142643_(BlockState p_152987_, BlockState p_152988_) {
      return p_152988_.m_61124_(f_152949_, p_152987_.m_61143_(f_152949_));
   }

   protected BlockState m_142452_(BlockState p_152990_, Random p_152991_) {
      return super.m_142452_(p_152990_, p_152991_).m_61124_(f_152949_, Boolean.valueOf(p_152991_.nextFloat() < 0.11F));
   }

   public ItemStack m_7397_(BlockGetter p_152966_, BlockPos p_152967_, BlockState p_152968_) {
      return new ItemStack(Items.f_151079_);
   }

   public InteractionResult m_6227_(BlockState p_152980_, Level p_152981_, BlockPos p_152982_, Player p_152983_, InteractionHand p_152984_, BlockHitResult p_152985_) {
      return CaveVines.m_152953_(p_152980_, p_152981_, p_152982_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_152993_) {
      super.m_7926_(p_152993_);
      p_152993_.m_61104_(f_152949_);
   }

   public boolean m_7370_(BlockGetter p_152970_, BlockPos p_152971_, BlockState p_152972_, boolean p_152973_) {
      return !p_152972_.m_61143_(f_152949_);
   }

   public boolean m_5491_(Level p_152975_, Random p_152976_, BlockPos p_152977_, BlockState p_152978_) {
      return true;
   }

   public void m_7719_(ServerLevel p_152961_, Random p_152962_, BlockPos p_152963_, BlockState p_152964_) {
      p_152961_.m_7731_(p_152963_, p_152964_.m_61124_(f_152949_, Boolean.valueOf(true)), 2);
   }
}