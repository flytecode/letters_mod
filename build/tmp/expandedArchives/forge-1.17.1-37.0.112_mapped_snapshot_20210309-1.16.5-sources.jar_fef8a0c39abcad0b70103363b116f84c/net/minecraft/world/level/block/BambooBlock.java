package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BambooBlock extends Block implements BonemealableBlock {
   protected static final float f_152092_ = 3.0F;
   protected static final float f_152093_ = 5.0F;
   protected static final float f_152094_ = 1.5F;
   protected static final VoxelShape f_48866_ = Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
   protected static final VoxelShape f_48867_ = Block.m_49796_(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
   protected static final VoxelShape f_48868_ = Block.m_49796_(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
   public static final IntegerProperty f_48869_ = BlockStateProperties.f_61405_;
   public static final EnumProperty<BambooLeaves> f_48870_ = BlockStateProperties.f_61400_;
   public static final IntegerProperty f_48871_ = BlockStateProperties.f_61387_;
   public static final int f_152095_ = 16;
   public static final int f_152096_ = 0;
   public static final int f_152097_ = 1;
   public static final int f_152098_ = 0;
   public static final int f_152099_ = 1;

   public BambooBlock(BlockBehaviour.Properties p_48874_) {
      super(p_48874_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_48869_, Integer.valueOf(0)).m_61124_(f_48870_, BambooLeaves.NONE).m_61124_(f_48871_, Integer.valueOf(0)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_48928_) {
      p_48928_.m_61104_(f_48869_, f_48870_, f_48871_);
   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XZ;
   }

   public boolean m_7420_(BlockState p_48941_, BlockGetter p_48942_, BlockPos p_48943_) {
      return true;
   }

   public VoxelShape m_5940_(BlockState p_48945_, BlockGetter p_48946_, BlockPos p_48947_, CollisionContext p_48948_) {
      VoxelShape voxelshape = p_48945_.m_61143_(f_48870_) == BambooLeaves.LARGE ? f_48867_ : f_48866_;
      Vec3 vec3 = p_48945_.m_60824_(p_48946_, p_48947_);
      return voxelshape.m_83216_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
   }

   public boolean m_7357_(BlockState p_48906_, BlockGetter p_48907_, BlockPos p_48908_, PathComputationType p_48909_) {
      return false;
   }

   public VoxelShape m_5939_(BlockState p_48950_, BlockGetter p_48951_, BlockPos p_48952_, CollisionContext p_48953_) {
      Vec3 vec3 = p_48950_.m_60824_(p_48951_, p_48952_);
      return f_48868_.m_83216_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
   }

   public boolean m_180643_(BlockState p_181159_, BlockGetter p_181160_, BlockPos p_181161_) {
      return false;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_48881_) {
      FluidState fluidstate = p_48881_.m_43725_().m_6425_(p_48881_.m_8083_());
      if (!fluidstate.m_76178_()) {
         return null;
      } else {
         BlockState blockstate = p_48881_.m_43725_().m_8055_(p_48881_.m_8083_().m_7495_());
         if (blockstate.m_60620_(BlockTags.f_13065_)) {
            if (blockstate.m_60713_(Blocks.f_50570_)) {
               return this.m_49966_().m_61124_(f_48869_, Integer.valueOf(0));
            } else if (blockstate.m_60713_(Blocks.f_50571_)) {
               int i = blockstate.m_61143_(f_48869_) > 0 ? 1 : 0;
               return this.m_49966_().m_61124_(f_48869_, Integer.valueOf(i));
            } else {
               BlockState blockstate1 = p_48881_.m_43725_().m_8055_(p_48881_.m_8083_().m_7494_());
               return blockstate1.m_60713_(Blocks.f_50571_) ? this.m_49966_().m_61124_(f_48869_, blockstate1.m_61143_(f_48869_)) : Blocks.f_50570_.m_49966_();
            }
         } else {
            return null;
         }
      }
   }

   public void m_7458_(BlockState p_48896_, ServerLevel p_48897_, BlockPos p_48898_, Random p_48899_) {
      if (!p_48896_.m_60710_(p_48897_, p_48898_)) {
         p_48897_.m_46961_(p_48898_, true);
      }

   }

   public boolean m_6724_(BlockState p_48930_) {
      return p_48930_.m_61143_(f_48871_) == 0;
   }

   public void m_7455_(BlockState p_48936_, ServerLevel p_48937_, BlockPos p_48938_, Random p_48939_) {
      if (p_48936_.m_61143_(f_48871_) == 0) {
         if (p_48937_.m_46859_(p_48938_.m_7494_()) && p_48937_.m_45524_(p_48938_.m_7494_(), 0) >= 9) {
            int i = this.m_48932_(p_48937_, p_48938_) + 1;
            if (i < 16 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_48937_, p_48938_, p_48936_, p_48939_.nextInt(3) == 0)) {
               this.m_48910_(p_48936_, p_48937_, p_48938_, p_48939_, i);
               net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_48937_, p_48938_, p_48936_);
            }
         }

      }
   }

   public boolean m_7898_(BlockState p_48917_, LevelReader p_48918_, BlockPos p_48919_) {
      return p_48918_.m_8055_(p_48919_.m_7495_()).m_60620_(BlockTags.f_13065_);
   }

   public BlockState m_7417_(BlockState p_48921_, Direction p_48922_, BlockState p_48923_, LevelAccessor p_48924_, BlockPos p_48925_, BlockPos p_48926_) {
      if (!p_48921_.m_60710_(p_48924_, p_48925_)) {
         p_48924_.m_6219_().m_5945_(p_48925_, this, 1);
      }

      if (p_48922_ == Direction.UP && p_48923_.m_60713_(Blocks.f_50571_) && p_48923_.m_61143_(f_48869_) > p_48921_.m_61143_(f_48869_)) {
         p_48924_.m_7731_(p_48925_, p_48921_.m_61122_(f_48869_), 2);
      }

      return super.m_7417_(p_48921_, p_48922_, p_48923_, p_48924_, p_48925_, p_48926_);
   }

   public boolean m_7370_(BlockGetter p_48886_, BlockPos p_48887_, BlockState p_48888_, boolean p_48889_) {
      int i = this.m_48882_(p_48886_, p_48887_);
      int j = this.m_48932_(p_48886_, p_48887_);
      return i + j + 1 < 16 && p_48886_.m_8055_(p_48887_.m_6630_(i)).m_61143_(f_48871_) != 1;
   }

   public boolean m_5491_(Level p_48891_, Random p_48892_, BlockPos p_48893_, BlockState p_48894_) {
      return true;
   }

   public void m_7719_(ServerLevel p_48876_, Random p_48877_, BlockPos p_48878_, BlockState p_48879_) {
      int i = this.m_48882_(p_48876_, p_48878_);
      int j = this.m_48932_(p_48876_, p_48878_);
      int k = i + j + 1;
      int l = 1 + p_48877_.nextInt(2);

      for(int i1 = 0; i1 < l; ++i1) {
         BlockPos blockpos = p_48878_.m_6630_(i);
         BlockState blockstate = p_48876_.m_8055_(blockpos);
         if (k >= 16 || blockstate.m_61143_(f_48871_) == 1 || !p_48876_.m_46859_(blockpos.m_7494_())) {
            return;
         }

         this.m_48910_(blockstate, p_48876_, blockpos, p_48877_, k);
         ++i;
         ++k;
      }

   }

   public float m_5880_(BlockState p_48901_, Player p_48902_, BlockGetter p_48903_, BlockPos p_48904_) {
      return p_48902_.m_21205_().canPerformAction(net.minecraftforge.common.ToolActions.SWORD_DIG) ? 1.0F : super.m_5880_(p_48901_, p_48902_, p_48903_, p_48904_);
   }

   protected void m_48910_(BlockState p_48911_, Level p_48912_, BlockPos p_48913_, Random p_48914_, int p_48915_) {
      BlockState blockstate = p_48912_.m_8055_(p_48913_.m_7495_());
      BlockPos blockpos = p_48913_.m_6625_(2);
      BlockState blockstate1 = p_48912_.m_8055_(blockpos);
      BambooLeaves bambooleaves = BambooLeaves.NONE;
      if (p_48915_ >= 1) {
         if (blockstate.m_60713_(Blocks.f_50571_) && blockstate.m_61143_(f_48870_) != BambooLeaves.NONE) {
            if (blockstate.m_60713_(Blocks.f_50571_) && blockstate.m_61143_(f_48870_) != BambooLeaves.NONE) {
               bambooleaves = BambooLeaves.LARGE;
               if (blockstate1.m_60713_(Blocks.f_50571_)) {
                  p_48912_.m_7731_(p_48913_.m_7495_(), blockstate.m_61124_(f_48870_, BambooLeaves.SMALL), 3);
                  p_48912_.m_7731_(blockpos, blockstate1.m_61124_(f_48870_, BambooLeaves.NONE), 3);
               }
            }
         } else {
            bambooleaves = BambooLeaves.SMALL;
         }
      }

      int i = p_48911_.m_61143_(f_48869_) != 1 && !blockstate1.m_60713_(Blocks.f_50571_) ? 0 : 1;
      int j = (p_48915_ < 11 || !(p_48914_.nextFloat() < 0.25F)) && p_48915_ != 15 ? 0 : 1;
      p_48912_.m_7731_(p_48913_.m_7494_(), this.m_49966_().m_61124_(f_48869_, Integer.valueOf(i)).m_61124_(f_48870_, bambooleaves).m_61124_(f_48871_, Integer.valueOf(j)), 3);
   }

   protected int m_48882_(BlockGetter p_48883_, BlockPos p_48884_) {
      int i;
      for(i = 0; i < 16 && p_48883_.m_8055_(p_48884_.m_6630_(i + 1)).m_60713_(Blocks.f_50571_); ++i) {
      }

      return i;
   }

   protected int m_48932_(BlockGetter p_48933_, BlockPos p_48934_) {
      int i;
      for(i = 0; i < 16 && p_48933_.m_8055_(p_48934_.m_6625_(i + 1)).m_60713_(Blocks.f_50571_); ++i) {
      }

      return i;
   }
}
