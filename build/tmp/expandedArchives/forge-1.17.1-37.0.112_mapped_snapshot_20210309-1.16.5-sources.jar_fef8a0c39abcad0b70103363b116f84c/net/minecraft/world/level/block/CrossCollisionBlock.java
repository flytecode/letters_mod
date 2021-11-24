package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrossCollisionBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_52309_ = PipeBlock.f_55148_;
   public static final BooleanProperty f_52310_ = PipeBlock.f_55149_;
   public static final BooleanProperty f_52311_ = PipeBlock.f_55150_;
   public static final BooleanProperty f_52312_ = PipeBlock.f_55151_;
   public static final BooleanProperty f_52313_ = BlockStateProperties.f_61362_;
   protected static final Map<Direction, BooleanProperty> f_52314_ = PipeBlock.f_55154_.entrySet().stream().filter((p_52346_) -> {
      return p_52346_.getKey().m_122434_().m_122479_();
   }).collect(Util.m_137448_());
   protected final VoxelShape[] f_52315_;
   protected final VoxelShape[] f_52316_;
   private final Object2IntMap<BlockState> f_52317_ = new Object2IntOpenHashMap<>();

   public CrossCollisionBlock(float p_52320_, float p_52321_, float p_52322_, float p_52323_, float p_52324_, BlockBehaviour.Properties p_52325_) {
      super(p_52325_);
      this.f_52315_ = this.m_52326_(p_52320_, p_52321_, p_52324_, 0.0F, p_52324_);
      this.f_52316_ = this.m_52326_(p_52320_, p_52321_, p_52322_, 0.0F, p_52323_);

      for(BlockState blockstate : this.f_49792_.m_61056_()) {
         this.m_52363_(blockstate);
      }

   }

   protected VoxelShape[] m_52326_(float p_52327_, float p_52328_, float p_52329_, float p_52330_, float p_52331_) {
      float f = 8.0F - p_52327_;
      float f1 = 8.0F + p_52327_;
      float f2 = 8.0F - p_52328_;
      float f3 = 8.0F + p_52328_;
      VoxelShape voxelshape = Block.m_49796_((double)f, 0.0D, (double)f, (double)f1, (double)p_52329_, (double)f1);
      VoxelShape voxelshape1 = Block.m_49796_((double)f2, (double)p_52330_, 0.0D, (double)f3, (double)p_52331_, (double)f3);
      VoxelShape voxelshape2 = Block.m_49796_((double)f2, (double)p_52330_, (double)f2, (double)f3, (double)p_52331_, 16.0D);
      VoxelShape voxelshape3 = Block.m_49796_(0.0D, (double)p_52330_, (double)f2, (double)f3, (double)p_52331_, (double)f3);
      VoxelShape voxelshape4 = Block.m_49796_((double)f2, (double)p_52330_, (double)f2, 16.0D, (double)p_52331_, (double)f3);
      VoxelShape voxelshape5 = Shapes.m_83110_(voxelshape1, voxelshape4);
      VoxelShape voxelshape6 = Shapes.m_83110_(voxelshape2, voxelshape3);
      VoxelShape[] avoxelshape = new VoxelShape[]{Shapes.m_83040_(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, Shapes.m_83110_(voxelshape2, voxelshape1), Shapes.m_83110_(voxelshape3, voxelshape1), Shapes.m_83110_(voxelshape6, voxelshape1), voxelshape4, Shapes.m_83110_(voxelshape2, voxelshape4), Shapes.m_83110_(voxelshape3, voxelshape4), Shapes.m_83110_(voxelshape6, voxelshape4), voxelshape5, Shapes.m_83110_(voxelshape2, voxelshape5), Shapes.m_83110_(voxelshape3, voxelshape5), Shapes.m_83110_(voxelshape6, voxelshape5)};

      for(int i = 0; i < 16; ++i) {
         avoxelshape[i] = Shapes.m_83110_(voxelshape, avoxelshape[i]);
      }

      return avoxelshape;
   }

   public boolean m_7420_(BlockState p_52348_, BlockGetter p_52349_, BlockPos p_52350_) {
      return !p_52348_.m_61143_(f_52313_);
   }

   public VoxelShape m_5940_(BlockState p_52352_, BlockGetter p_52353_, BlockPos p_52354_, CollisionContext p_52355_) {
      return this.f_52316_[this.m_52363_(p_52352_)];
   }

   public VoxelShape m_5939_(BlockState p_52357_, BlockGetter p_52358_, BlockPos p_52359_, CollisionContext p_52360_) {
      return this.f_52315_[this.m_52363_(p_52357_)];
   }

   private static int m_52343_(Direction p_52344_) {
      return 1 << p_52344_.m_122416_();
   }

   protected int m_52363_(BlockState p_52364_) {
      return this.f_52317_.computeIntIfAbsent(p_52364_, (p_52366_) -> {
         int i = 0;
         if (p_52366_.m_61143_(f_52309_)) {
            i |= m_52343_(Direction.NORTH);
         }

         if (p_52366_.m_61143_(f_52310_)) {
            i |= m_52343_(Direction.EAST);
         }

         if (p_52366_.m_61143_(f_52311_)) {
            i |= m_52343_(Direction.SOUTH);
         }

         if (p_52366_.m_61143_(f_52312_)) {
            i |= m_52343_(Direction.WEST);
         }

         return i;
      });
   }

   public FluidState m_5888_(BlockState p_52362_) {
      return p_52362_.m_61143_(f_52313_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_52362_);
   }

   public boolean m_7357_(BlockState p_52333_, BlockGetter p_52334_, BlockPos p_52335_, PathComputationType p_52336_) {
      return false;
   }

   public BlockState m_6843_(BlockState p_52341_, Rotation p_52342_) {
      switch(p_52342_) {
      case CLOCKWISE_180:
         return p_52341_.m_61124_(f_52309_, p_52341_.m_61143_(f_52311_)).m_61124_(f_52310_, p_52341_.m_61143_(f_52312_)).m_61124_(f_52311_, p_52341_.m_61143_(f_52309_)).m_61124_(f_52312_, p_52341_.m_61143_(f_52310_));
      case COUNTERCLOCKWISE_90:
         return p_52341_.m_61124_(f_52309_, p_52341_.m_61143_(f_52310_)).m_61124_(f_52310_, p_52341_.m_61143_(f_52311_)).m_61124_(f_52311_, p_52341_.m_61143_(f_52312_)).m_61124_(f_52312_, p_52341_.m_61143_(f_52309_));
      case CLOCKWISE_90:
         return p_52341_.m_61124_(f_52309_, p_52341_.m_61143_(f_52312_)).m_61124_(f_52310_, p_52341_.m_61143_(f_52309_)).m_61124_(f_52311_, p_52341_.m_61143_(f_52310_)).m_61124_(f_52312_, p_52341_.m_61143_(f_52311_));
      default:
         return p_52341_;
      }
   }

   public BlockState m_6943_(BlockState p_52338_, Mirror p_52339_) {
      switch(p_52339_) {
      case LEFT_RIGHT:
         return p_52338_.m_61124_(f_52309_, p_52338_.m_61143_(f_52311_)).m_61124_(f_52311_, p_52338_.m_61143_(f_52309_));
      case FRONT_BACK:
         return p_52338_.m_61124_(f_52310_, p_52338_.m_61143_(f_52312_)).m_61124_(f_52312_, p_52338_.m_61143_(f_52310_));
      default:
         return super.m_6943_(p_52338_, p_52339_);
      }
   }
}