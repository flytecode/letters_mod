package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BambooSaplingBlock extends Block implements BonemealableBlock {
   protected static final float f_152100_ = 4.0F;
   protected static final VoxelShape f_48954_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);

   public BambooSaplingBlock(BlockBehaviour.Properties p_48957_) {
      super(p_48957_);
   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XZ;
   }

   public VoxelShape m_5940_(BlockState p_49003_, BlockGetter p_49004_, BlockPos p_49005_, CollisionContext p_49006_) {
      Vec3 vec3 = p_49003_.m_60824_(p_49004_, p_49005_);
      return f_48954_.m_83216_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
   }

   public void m_7455_(BlockState p_48998_, ServerLevel p_48999_, BlockPos p_49000_, Random p_49001_) {
      if (p_49001_.nextInt(3) == 0 && p_48999_.m_46859_(p_49000_.m_7494_()) && p_48999_.m_45524_(p_49000_.m_7494_(), 0) >= 9) {
         this.m_48972_(p_48999_, p_49000_);
      }

   }

   public boolean m_7898_(BlockState p_48986_, LevelReader p_48987_, BlockPos p_48988_) {
      return p_48987_.m_8055_(p_48988_.m_7495_()).m_60620_(BlockTags.f_13065_);
   }

   public BlockState m_7417_(BlockState p_48990_, Direction p_48991_, BlockState p_48992_, LevelAccessor p_48993_, BlockPos p_48994_, BlockPos p_48995_) {
      if (!p_48990_.m_60710_(p_48993_, p_48994_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         if (p_48991_ == Direction.UP && p_48992_.m_60713_(Blocks.f_50571_)) {
            p_48993_.m_7731_(p_48994_, Blocks.f_50571_.m_49966_(), 2);
         }

         return super.m_7417_(p_48990_, p_48991_, p_48992_, p_48993_, p_48994_, p_48995_);
      }
   }

   public ItemStack m_7397_(BlockGetter p_48964_, BlockPos p_48965_, BlockState p_48966_) {
      return new ItemStack(Items.f_41911_);
   }

   public boolean m_7370_(BlockGetter p_48968_, BlockPos p_48969_, BlockState p_48970_, boolean p_48971_) {
      return p_48968_.m_8055_(p_48969_.m_7494_()).m_60795_();
   }

   public boolean m_5491_(Level p_48976_, Random p_48977_, BlockPos p_48978_, BlockState p_48979_) {
      return true;
   }

   public void m_7719_(ServerLevel p_48959_, Random p_48960_, BlockPos p_48961_, BlockState p_48962_) {
      this.m_48972_(p_48959_, p_48961_);
   }

   public float m_5880_(BlockState p_48981_, Player p_48982_, BlockGetter p_48983_, BlockPos p_48984_) {
      return p_48982_.m_21205_().canPerformAction(net.minecraftforge.common.ToolActions.SWORD_DIG) ? 1.0F : super.m_5880_(p_48981_, p_48982_, p_48983_, p_48984_);
   }

   protected void m_48972_(Level p_48973_, BlockPos p_48974_) {
      p_48973_.m_7731_(p_48974_.m_7494_(), Blocks.f_50571_.m_49966_().m_61124_(BambooBlock.f_48870_, BambooLeaves.SMALL), 3);
   }
}
