package net.minecraft.world.level.block.piston;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PistonBaseBlock extends DirectionalBlock {
   public static final BooleanProperty f_60153_ = BlockStateProperties.f_61432_;
   public static final int f_155888_ = 0;
   public static final int f_155889_ = 1;
   public static final int f_155890_ = 2;
   public static final float f_155891_ = 4.0F;
   protected static final VoxelShape f_60154_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_60155_ = Block.m_49796_(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_60156_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D);
   protected static final VoxelShape f_60157_ = Block.m_49796_(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_60158_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
   protected static final VoxelShape f_60159_ = Block.m_49796_(0.0D, 4.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   private final boolean f_60160_;

   public PistonBaseBlock(boolean p_60163_, BlockBehaviour.Properties p_60164_) {
      super(p_60164_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52588_, Direction.NORTH).m_61124_(f_60153_, Boolean.valueOf(false)));
      this.f_60160_ = p_60163_;
   }

   public VoxelShape m_5940_(BlockState p_60220_, BlockGetter p_60221_, BlockPos p_60222_, CollisionContext p_60223_) {
      if (p_60220_.m_61143_(f_60153_)) {
         switch((Direction)p_60220_.m_61143_(f_52588_)) {
         case DOWN:
            return f_60159_;
         case UP:
         default:
            return f_60158_;
         case NORTH:
            return f_60157_;
         case SOUTH:
            return f_60156_;
         case WEST:
            return f_60155_;
         case EAST:
            return f_60154_;
         }
      } else {
         return Shapes.m_83144_();
      }
   }

   public void m_6402_(Level p_60172_, BlockPos p_60173_, BlockState p_60174_, LivingEntity p_60175_, ItemStack p_60176_) {
      if (!p_60172_.f_46443_) {
         this.m_60167_(p_60172_, p_60173_, p_60174_);
      }

   }

   public void m_6861_(BlockState p_60198_, Level p_60199_, BlockPos p_60200_, Block p_60201_, BlockPos p_60202_, boolean p_60203_) {
      if (!p_60199_.f_46443_) {
         this.m_60167_(p_60199_, p_60200_, p_60198_);
      }

   }

   public void m_6807_(BlockState p_60225_, Level p_60226_, BlockPos p_60227_, BlockState p_60228_, boolean p_60229_) {
      if (!p_60228_.m_60713_(p_60225_.m_60734_())) {
         if (!p_60226_.f_46443_ && p_60226_.m_7702_(p_60227_) == null) {
            this.m_60167_(p_60226_, p_60227_, p_60225_);
         }

      }
   }

   public BlockState m_5573_(BlockPlaceContext p_60166_) {
      return this.m_49966_().m_61124_(f_52588_, p_60166_.m_7820_().m_122424_()).m_61124_(f_60153_, Boolean.valueOf(false));
   }

   private void m_60167_(Level p_60168_, BlockPos p_60169_, BlockState p_60170_) {
      Direction direction = p_60170_.m_61143_(f_52588_);
      boolean flag = this.m_60177_(p_60168_, p_60169_, direction);
      if (flag && !p_60170_.m_61143_(f_60153_)) {
         if ((new PistonStructureResolver(p_60168_, p_60169_, direction, true)).m_60422_()) {
            p_60168_.m_7696_(p_60169_, this, 0, direction.m_122411_());
         }
      } else if (!flag && p_60170_.m_61143_(f_60153_)) {
         BlockPos blockpos = p_60169_.m_5484_(direction, 2);
         BlockState blockstate = p_60168_.m_8055_(blockpos);
         int i = 1;
         if (blockstate.m_60713_(Blocks.f_50110_) && blockstate.m_61143_(f_52588_) == direction) {
            BlockEntity blockentity = p_60168_.m_7702_(blockpos);
            if (blockentity instanceof PistonMovingBlockEntity) {
               PistonMovingBlockEntity pistonmovingblockentity = (PistonMovingBlockEntity)blockentity;
               if (pistonmovingblockentity.m_60387_() && (pistonmovingblockentity.m_60350_(0.0F) < 0.5F || p_60168_.m_46467_() == pistonmovingblockentity.m_60402_() || ((ServerLevel)p_60168_).m_8874_())) {
                  i = 2;
               }
            }
         }

         p_60168_.m_7696_(p_60169_, this, i, direction.m_122411_());
      }

   }

   private boolean m_60177_(Level p_60178_, BlockPos p_60179_, Direction p_60180_) {
      for(Direction direction : Direction.values()) {
         if (direction != p_60180_ && p_60178_.m_46616_(p_60179_.m_142300_(direction), direction)) {
            return true;
         }
      }

      if (p_60178_.m_46616_(p_60179_, Direction.DOWN)) {
         return true;
      } else {
         BlockPos blockpos = p_60179_.m_7494_();

         for(Direction direction1 : Direction.values()) {
            if (direction1 != Direction.DOWN && p_60178_.m_46616_(blockpos.m_142300_(direction1), direction1)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean m_8133_(BlockState p_60192_, Level p_60193_, BlockPos p_60194_, int p_60195_, int p_60196_) {
      Direction direction = p_60192_.m_61143_(f_52588_);
      if (!p_60193_.f_46443_) {
         boolean flag = this.m_60177_(p_60193_, p_60194_, direction);
         if (flag && (p_60195_ == 1 || p_60195_ == 2)) {
            p_60193_.m_7731_(p_60194_, p_60192_.m_61124_(f_60153_, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!flag && p_60195_ == 0) {
            return false;
         }
      }

      if (p_60195_ == 0) {
         if (net.minecraftforge.event.ForgeEventFactory.onPistonMovePre(p_60193_, p_60194_, direction, true)) return false;
         if (!this.m_60181_(p_60193_, p_60194_, direction, true)) {
            return false;
         }

         p_60193_.m_7731_(p_60194_, p_60192_.m_61124_(f_60153_, Boolean.valueOf(true)), 67);
         p_60193_.m_5594_((Player)null, p_60194_, SoundEvents.f_12312_, SoundSource.BLOCKS, 0.5F, p_60193_.f_46441_.nextFloat() * 0.25F + 0.6F);
         p_60193_.m_151555_(GameEvent.f_157775_, p_60194_);
      } else if (p_60195_ == 1 || p_60195_ == 2) {
         if (net.minecraftforge.event.ForgeEventFactory.onPistonMovePre(p_60193_, p_60194_, direction, false)) return false;
         BlockEntity blockentity1 = p_60193_.m_7702_(p_60194_.m_142300_(direction));
         if (blockentity1 instanceof PistonMovingBlockEntity) {
            ((PistonMovingBlockEntity)blockentity1).m_60401_();
         }

         BlockState blockstate = Blocks.f_50110_.m_49966_().m_61124_(MovingPistonBlock.f_60046_, direction).m_61124_(MovingPistonBlock.f_60047_, this.f_60160_ ? PistonType.STICKY : PistonType.DEFAULT);
         p_60193_.m_7731_(p_60194_, blockstate, 20);
         p_60193_.m_151523_(MovingPistonBlock.m_155881_(p_60194_, blockstate, this.m_49966_().m_61124_(f_52588_, Direction.m_122376_(p_60196_ & 7)), direction, false, true));
         p_60193_.m_6289_(p_60194_, blockstate.m_60734_());
         blockstate.m_60701_(p_60193_, p_60194_, 2);
         if (this.f_60160_) {
            BlockPos blockpos = p_60194_.m_142082_(direction.m_122429_() * 2, direction.m_122430_() * 2, direction.m_122431_() * 2);
            BlockState blockstate1 = p_60193_.m_8055_(blockpos);
            boolean flag1 = false;
            if (blockstate1.m_60713_(Blocks.f_50110_)) {
               BlockEntity blockentity = p_60193_.m_7702_(blockpos);
               if (blockentity instanceof PistonMovingBlockEntity) {
                  PistonMovingBlockEntity pistonmovingblockentity = (PistonMovingBlockEntity)blockentity;
                  if (pistonmovingblockentity.m_60392_() == direction && pistonmovingblockentity.m_60387_()) {
                     pistonmovingblockentity.m_60401_();
                     flag1 = true;
                  }
               }
            }

            if (!flag1) {
               if (p_60195_ != 1 || blockstate1.m_60795_() || !m_60204_(blockstate1, p_60193_, blockpos, direction.m_122424_(), false, direction) || blockstate1.m_60811_() != PushReaction.NORMAL && !blockstate1.m_60713_(Blocks.f_50039_) && !blockstate1.m_60713_(Blocks.f_50032_)) {
                  p_60193_.m_7471_(p_60194_.m_142300_(direction), false);
               } else {
                  this.m_60181_(p_60193_, p_60194_, direction, false);
               }
            }
         } else {
            p_60193_.m_7471_(p_60194_.m_142300_(direction), false);
         }

         p_60193_.m_5594_((Player)null, p_60194_, SoundEvents.f_12311_, SoundSource.BLOCKS, 0.5F, p_60193_.f_46441_.nextFloat() * 0.15F + 0.6F);
         p_60193_.m_151555_(GameEvent.f_157774_, p_60194_);
      }

      net.minecraftforge.event.ForgeEventFactory.onPistonMovePost(p_60193_, p_60194_, direction, (p_60195_ == 0));
      return true;
   }

   public static boolean m_60204_(BlockState p_60205_, Level p_60206_, BlockPos p_60207_, Direction p_60208_, boolean p_60209_, Direction p_60210_) {
      if (p_60207_.m_123342_() >= p_60206_.m_141937_() && p_60207_.m_123342_() <= p_60206_.m_151558_() - 1 && p_60206_.m_6857_().m_61937_(p_60207_)) {
         if (p_60205_.m_60795_()) {
            return true;
         } else if (!p_60205_.m_60713_(Blocks.f_50080_) && !p_60205_.m_60713_(Blocks.f_50723_) && !p_60205_.m_60713_(Blocks.f_50724_)) {
            if (p_60208_ == Direction.DOWN && p_60207_.m_123342_() == p_60206_.m_141937_()) {
               return false;
            } else if (p_60208_ == Direction.UP && p_60207_.m_123342_() == p_60206_.m_151558_() - 1) {
               return false;
            } else {
               if (!p_60205_.m_60713_(Blocks.f_50039_) && !p_60205_.m_60713_(Blocks.f_50032_)) {
                  if (p_60205_.m_60800_(p_60206_, p_60207_) == -1.0F) {
                     return false;
                  }

                  switch(p_60205_.m_60811_()) {
                  case BLOCK:
                     return false;
                  case DESTROY:
                     return p_60209_;
                  case PUSH_ONLY:
                     return p_60208_ == p_60210_;
                  }
               } else if (p_60205_.m_61143_(f_60153_)) {
                  return false;
               }

               return !p_60205_.m_155947_();
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean m_60181_(Level p_60182_, BlockPos p_60183_, Direction p_60184_, boolean p_60185_) {
      BlockPos blockpos = p_60183_.m_142300_(p_60184_);
      if (!p_60185_ && p_60182_.m_8055_(blockpos).m_60713_(Blocks.f_50040_)) {
         p_60182_.m_7731_(blockpos, Blocks.f_50016_.m_49966_(), 20);
      }

      PistonStructureResolver pistonstructureresolver = new PistonStructureResolver(p_60182_, p_60183_, p_60184_, p_60185_);
      if (!pistonstructureresolver.m_60422_()) {
         return false;
      } else {
         Map<BlockPos, BlockState> map = Maps.newHashMap();
         List<BlockPos> list = pistonstructureresolver.m_60436_();
         List<BlockState> list1 = Lists.newArrayList();

         for(int i = 0; i < list.size(); ++i) {
            BlockPos blockpos1 = list.get(i);
            BlockState blockstate = p_60182_.m_8055_(blockpos1);
            list1.add(blockstate);
            map.put(blockpos1, blockstate);
         }

         List<BlockPos> list2 = pistonstructureresolver.m_60437_();
         BlockState[] ablockstate = new BlockState[list.size() + list2.size()];
         Direction direction = p_60185_ ? p_60184_ : p_60184_.m_122424_();
         int j = 0;

         for(int k = list2.size() - 1; k >= 0; --k) {
            BlockPos blockpos2 = list2.get(k);
            BlockState blockstate1 = p_60182_.m_8055_(blockpos2);
            BlockEntity blockentity = blockstate1.m_155947_() ? p_60182_.m_7702_(blockpos2) : null;
            m_49892_(blockstate1, p_60182_, blockpos2, blockentity);
            p_60182_.m_7731_(blockpos2, Blocks.f_50016_.m_49966_(), 18);
            if (!blockstate1.m_60620_(BlockTags.f_13076_)) {
               p_60182_.m_142052_(blockpos2, blockstate1);
            }

            ablockstate[j++] = blockstate1;
         }

         for(int l = list.size() - 1; l >= 0; --l) {
            BlockPos blockpos3 = list.get(l);
            BlockState blockstate5 = p_60182_.m_8055_(blockpos3);
            blockpos3 = blockpos3.m_142300_(direction);
            map.remove(blockpos3);
            BlockState blockstate8 = Blocks.f_50110_.m_49966_().m_61124_(f_52588_, p_60184_);
            p_60182_.m_7731_(blockpos3, blockstate8, 68);
            p_60182_.m_151523_(MovingPistonBlock.m_155881_(blockpos3, blockstate8, list1.get(l), p_60184_, p_60185_, false));
            ablockstate[j++] = blockstate5;
         }

         if (p_60185_) {
            PistonType pistontype = this.f_60160_ ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState blockstate4 = Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_52588_, p_60184_).m_61124_(PistonHeadBlock.f_60235_, pistontype);
            BlockState blockstate6 = Blocks.f_50110_.m_49966_().m_61124_(MovingPistonBlock.f_60046_, p_60184_).m_61124_(MovingPistonBlock.f_60047_, this.f_60160_ ? PistonType.STICKY : PistonType.DEFAULT);
            map.remove(blockpos);
            p_60182_.m_7731_(blockpos, blockstate6, 68);
            p_60182_.m_151523_(MovingPistonBlock.m_155881_(blockpos, blockstate6, blockstate4, p_60184_, true, true));
         }

         BlockState blockstate3 = Blocks.f_50016_.m_49966_();

         for(BlockPos blockpos4 : map.keySet()) {
            p_60182_.m_7731_(blockpos4, blockstate3, 82);
         }

         for(Entry<BlockPos, BlockState> entry : map.entrySet()) {
            BlockPos blockpos5 = entry.getKey();
            BlockState blockstate2 = entry.getValue();
            blockstate2.m_60758_(p_60182_, blockpos5, 2);
            blockstate3.m_60701_(p_60182_, blockpos5, 2);
            blockstate3.m_60758_(p_60182_, blockpos5, 2);
         }

         j = 0;

         for(int i1 = list2.size() - 1; i1 >= 0; --i1) {
            BlockState blockstate7 = ablockstate[j++];
            BlockPos blockpos6 = list2.get(i1);
            blockstate7.m_60758_(p_60182_, blockpos6, 2);
            p_60182_.m_46672_(blockpos6, blockstate7.m_60734_());
         }

         for(int j1 = list.size() - 1; j1 >= 0; --j1) {
            p_60182_.m_46672_(list.get(j1), ablockstate[j++].m_60734_());
         }

         if (p_60185_) {
            p_60182_.m_46672_(blockpos, Blocks.f_50040_);
         }

         return true;
      }
   }

   public BlockState m_6843_(BlockState p_60215_, Rotation p_60216_) {
      return p_60215_.m_61124_(f_52588_, p_60216_.m_55954_(p_60215_.m_61143_(f_52588_)));
   }

   public BlockState rotate(BlockState state, net.minecraft.world.level.LevelAccessor world, BlockPos pos, Rotation direction) {
       return state.m_61143_(f_60153_) ? state : super.rotate(state, world, pos, direction);
   }

   public BlockState m_6943_(BlockState p_60212_, Mirror p_60213_) {
      return p_60212_.m_60717_(p_60213_.m_54846_(p_60212_.m_61143_(f_52588_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_60218_) {
      p_60218_.m_61104_(f_52588_, f_60153_);
   }

   public boolean m_7923_(BlockState p_60231_) {
      return p_60231_.m_61143_(f_60153_);
   }

   public boolean m_7357_(BlockState p_60187_, BlockGetter p_60188_, BlockPos p_60189_, PathComputationType p_60190_) {
      return false;
   }
}
