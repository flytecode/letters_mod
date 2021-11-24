package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ComposterBlock extends Block implements WorldlyContainerHolder {
   public static final int f_153088_ = 8;
   public static final int f_153089_ = 0;
   public static final int f_153090_ = 7;
   public static final IntegerProperty f_51913_ = BlockStateProperties.f_61419_;
   public static final Object2FloatMap<ItemLike> f_51914_ = new Object2FloatOpenHashMap<>();
   private static final int f_153091_ = 2;
   private static final VoxelShape f_51915_ = Shapes.m_83144_();
   private static final VoxelShape[] f_51916_ = Util.m_137469_(new VoxelShape[9], (p_51967_) -> {
      for(int i = 0; i < 8; ++i) {
         p_51967_[i] = Shapes.m_83113_(f_51915_, Block.m_49796_(2.0D, (double)Math.max(2, 1 + i * 2), 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.f_82685_);
      }

      p_51967_[8] = p_51967_[7];
   });

   public static void m_51988_() {
      f_51914_.defaultReturnValue(-1.0F);
      float f = 0.3F;
      float f1 = 0.5F;
      float f2 = 0.65F;
      float f3 = 0.85F;
      float f4 = 1.0F;
      m_51920_(0.3F, Items.f_41899_);
      m_51920_(0.3F, Items.f_41896_);
      m_51920_(0.3F, Items.f_41897_);
      m_51920_(0.3F, Items.f_41901_);
      m_51920_(0.3F, Items.f_41900_);
      m_51920_(0.3F, Items.f_41898_);
      m_51920_(0.3F, Items.f_151009_);
      m_51920_(0.3F, Items.f_42799_);
      m_51920_(0.3F, Items.f_42800_);
      m_51920_(0.3F, Items.f_42801_);
      m_51920_(0.3F, Items.f_41826_);
      m_51920_(0.3F, Items.f_41827_);
      m_51920_(0.3F, Items.f_41828_);
      m_51920_(0.3F, Items.f_42733_);
      m_51920_(0.3F, Items.f_42576_);
      m_51920_(0.3F, Items.f_41864_);
      m_51920_(0.3F, Items.f_41910_);
      m_51920_(0.3F, Items.f_42578_);
      m_51920_(0.3F, Items.f_42577_);
      m_51920_(0.3F, Items.f_41867_);
      m_51920_(0.3F, Items.f_42780_);
      m_51920_(0.3F, Items.f_151079_);
      m_51920_(0.3F, Items.f_42404_);
      m_51920_(0.3F, Items.f_151015_);
      m_51920_(0.3F, Items.f_151019_);
      m_51920_(0.3F, Items.f_151017_);
      m_51920_(0.5F, Items.f_42515_);
      m_51920_(0.5F, Items.f_42210_);
      m_51920_(0.5F, Items.f_151010_);
      m_51920_(0.5F, Items.f_41982_);
      m_51920_(0.5F, Items.f_41909_);
      m_51920_(0.5F, Items.f_42029_);
      m_51920_(0.5F, Items.f_41906_);
      m_51920_(0.5F, Items.f_41907_);
      m_51920_(0.5F, Items.f_41908_);
      m_51920_(0.5F, Items.f_42575_);
      m_51920_(0.5F, Items.f_151025_);
      m_51920_(0.65F, Items.f_41868_);
      m_51920_(0.65F, Items.f_42094_);
      m_51920_(0.65F, Items.f_42046_);
      m_51920_(0.65F, Items.f_42047_);
      m_51920_(0.65F, Items.f_42028_);
      m_51920_(0.65F, Items.f_42410_);
      m_51920_(0.65F, Items.f_42732_);
      m_51920_(0.65F, Items.f_42619_);
      m_51920_(0.65F, Items.f_42533_);
      m_51920_(0.65F, Items.f_42620_);
      m_51920_(0.65F, Items.f_42405_);
      m_51920_(0.65F, Items.f_41952_);
      m_51920_(0.65F, Items.f_41953_);
      m_51920_(0.65F, Items.f_42024_);
      m_51920_(0.65F, Items.f_41954_);
      m_51920_(0.65F, Items.f_41955_);
      m_51920_(0.65F, Items.f_42588_);
      m_51920_(0.65F, Items.f_41956_);
      m_51920_(0.65F, Items.f_41957_);
      m_51920_(0.65F, Items.f_42783_);
      m_51920_(0.65F, Items.f_41939_);
      m_51920_(0.65F, Items.f_41940_);
      m_51920_(0.65F, Items.f_41941_);
      m_51920_(0.65F, Items.f_41942_);
      m_51920_(0.65F, Items.f_41943_);
      m_51920_(0.65F, Items.f_41944_);
      m_51920_(0.65F, Items.f_41945_);
      m_51920_(0.65F, Items.f_41946_);
      m_51920_(0.65F, Items.f_41947_);
      m_51920_(0.65F, Items.f_41948_);
      m_51920_(0.65F, Items.f_41949_);
      m_51920_(0.65F, Items.f_41950_);
      m_51920_(0.65F, Items.f_41951_);
      m_51920_(0.65F, Items.f_41865_);
      m_51920_(0.65F, Items.f_42206_);
      m_51920_(0.65F, Items.f_42207_);
      m_51920_(0.65F, Items.f_42208_);
      m_51920_(0.65F, Items.f_42209_);
      m_51920_(0.65F, Items.f_42211_);
      m_51920_(0.65F, Items.f_151014_);
      m_51920_(0.65F, Items.f_151012_);
      m_51920_(0.65F, Items.f_151016_);
      m_51920_(0.65F, Items.f_151018_);
      m_51920_(0.85F, Items.f_42129_);
      m_51920_(0.85F, Items.f_42022_);
      m_51920_(0.85F, Items.f_42023_);
      m_51920_(0.85F, Items.f_42259_);
      m_51920_(0.85F, Items.f_42260_);
      m_51920_(0.85F, Items.f_151013_);
      m_51920_(0.85F, Items.f_42406_);
      m_51920_(0.85F, Items.f_42674_);
      m_51920_(0.85F, Items.f_42572_);
      m_51920_(1.0F, Items.f_42502_);
      m_51920_(1.0F, Items.f_42687_);
   }

   private static void m_51920_(float p_51921_, ItemLike p_51922_) {
      f_51914_.put(p_51922_.m_5456_(), p_51921_);
   }

   public ComposterBlock(BlockBehaviour.Properties p_51919_) {
      super(p_51919_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51913_, Integer.valueOf(0)));
   }

   public static void m_51923_(Level p_51924_, BlockPos p_51925_, boolean p_51926_) {
      BlockState blockstate = p_51924_.m_8055_(p_51925_);
      p_51924_.m_7785_((double)p_51925_.m_123341_(), (double)p_51925_.m_123342_(), (double)p_51925_.m_123343_(), p_51926_ ? SoundEvents.f_11765_ : SoundEvents.f_11764_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
      double d0 = blockstate.m_60808_(p_51924_, p_51925_).m_83290_(Direction.Axis.Y, 0.5D, 0.5D) + 0.03125D;
      double d1 = (double)0.13125F;
      double d2 = (double)0.7375F;
      Random random = p_51924_.m_5822_();

      for(int i = 0; i < 10; ++i) {
         double d3 = random.nextGaussian() * 0.02D;
         double d4 = random.nextGaussian() * 0.02D;
         double d5 = random.nextGaussian() * 0.02D;
         p_51924_.m_7106_(ParticleTypes.f_123749_, (double)p_51925_.m_123341_() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), (double)p_51925_.m_123342_() + d0 + (double)random.nextFloat() * (1.0D - d0), (double)p_51925_.m_123343_() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), d3, d4, d5);
      }

   }

   public VoxelShape m_5940_(BlockState p_51973_, BlockGetter p_51974_, BlockPos p_51975_, CollisionContext p_51976_) {
      return f_51916_[p_51973_.m_61143_(f_51913_)];
   }

   public VoxelShape m_6079_(BlockState p_51969_, BlockGetter p_51970_, BlockPos p_51971_) {
      return f_51915_;
   }

   public VoxelShape m_5939_(BlockState p_51990_, BlockGetter p_51991_, BlockPos p_51992_, CollisionContext p_51993_) {
      return f_51916_[0];
   }

   public void m_6807_(BlockState p_51978_, Level p_51979_, BlockPos p_51980_, BlockState p_51981_, boolean p_51982_) {
      if (p_51978_.m_61143_(f_51913_) == 7) {
         p_51979_.m_6219_().m_5945_(p_51980_, p_51978_.m_60734_(), 20);
      }

   }

   public InteractionResult m_6227_(BlockState p_51949_, Level p_51950_, BlockPos p_51951_, Player p_51952_, InteractionHand p_51953_, BlockHitResult p_51954_) {
      int i = p_51949_.m_61143_(f_51913_);
      ItemStack itemstack = p_51952_.m_21120_(p_51953_);
      if (i < 8 && f_51914_.containsKey(itemstack.m_41720_())) {
         if (i < 7 && !p_51950_.f_46443_) {
            BlockState blockstate = m_51983_(p_51949_, p_51950_, p_51951_, itemstack);
            p_51950_.m_46796_(1500, p_51951_, p_51949_ != blockstate ? 1 : 0);
            p_51952_.m_36246_(Stats.f_12982_.m_12902_(itemstack.m_41720_()));
            if (!p_51952_.m_150110_().f_35937_) {
               itemstack.m_41774_(1);
            }
         }

         return InteractionResult.m_19078_(p_51950_.f_46443_);
      } else if (i == 8) {
         m_51998_(p_51949_, p_51950_, p_51951_);
         return InteractionResult.m_19078_(p_51950_.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   public static BlockState m_51929_(BlockState p_51930_, ServerLevel p_51931_, ItemStack p_51932_, BlockPos p_51933_) {
      int i = p_51930_.m_61143_(f_51913_);
      if (i < 7 && f_51914_.containsKey(p_51932_.m_41720_())) {
         BlockState blockstate = m_51983_(p_51930_, p_51931_, p_51933_, p_51932_);
         p_51932_.m_41774_(1);
         return blockstate;
      } else {
         return p_51930_;
      }
   }

   public static BlockState m_51998_(BlockState p_51999_, Level p_52000_, BlockPos p_52001_) {
      if (!p_52000_.f_46443_) {
         float f = 0.7F;
         double d0 = (double)(p_52000_.f_46441_.nextFloat() * 0.7F) + (double)0.15F;
         double d1 = (double)(p_52000_.f_46441_.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
         double d2 = (double)(p_52000_.f_46441_.nextFloat() * 0.7F) + (double)0.15F;
         ItemEntity itementity = new ItemEntity(p_52000_, (double)p_52001_.m_123341_() + d0, (double)p_52001_.m_123342_() + d1, (double)p_52001_.m_123343_() + d2, new ItemStack(Items.f_42499_));
         itementity.m_32060_();
         p_52000_.m_7967_(itementity);
      }

      BlockState blockstate = m_52002_(p_51999_, p_52000_, p_52001_);
      p_52000_.m_5594_((Player)null, p_52001_, SoundEvents.f_11763_, SoundSource.BLOCKS, 1.0F, 1.0F);
      return blockstate;
   }

   static BlockState m_52002_(BlockState p_52003_, LevelAccessor p_52004_, BlockPos p_52005_) {
      BlockState blockstate = p_52003_.m_61124_(f_51913_, Integer.valueOf(0));
      p_52004_.m_7731_(p_52005_, blockstate, 3);
      return blockstate;
   }

   static BlockState m_51983_(BlockState p_51984_, LevelAccessor p_51985_, BlockPos p_51986_, ItemStack p_51987_) {
      int i = p_51984_.m_61143_(f_51913_);
      float f = f_51914_.getFloat(p_51987_.m_41720_());
      if ((i != 0 || !(f > 0.0F)) && !(p_51985_.m_5822_().nextDouble() < (double)f)) {
         return p_51984_;
      } else {
         int j = i + 1;
         BlockState blockstate = p_51984_.m_61124_(f_51913_, Integer.valueOf(j));
         p_51985_.m_7731_(p_51986_, blockstate, 3);
         if (j == 7) {
            p_51985_.m_6219_().m_5945_(p_51986_, p_51984_.m_60734_(), 20);
         }

         return blockstate;
      }
   }

   public void m_7458_(BlockState p_51935_, ServerLevel p_51936_, BlockPos p_51937_, Random p_51938_) {
      if (p_51935_.m_61143_(f_51913_) == 7) {
         p_51936_.m_7731_(p_51937_, p_51935_.m_61122_(f_51913_), 3);
         p_51936_.m_5594_((Player)null, p_51937_, SoundEvents.f_11766_, SoundSource.BLOCKS, 1.0F, 1.0F);
      }

   }

   public boolean m_7278_(BlockState p_51928_) {
      return true;
   }

   public int m_6782_(BlockState p_51945_, Level p_51946_, BlockPos p_51947_) {
      return p_51945_.m_61143_(f_51913_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51965_) {
      p_51965_.m_61104_(f_51913_);
   }

   public boolean m_7357_(BlockState p_51940_, BlockGetter p_51941_, BlockPos p_51942_, PathComputationType p_51943_) {
      return false;
   }

   public WorldlyContainer m_5840_(BlockState p_51956_, LevelAccessor p_51957_, BlockPos p_51958_) {
      int i = p_51956_.m_61143_(f_51913_);
      if (i == 8) {
         return new ComposterBlock.OutputContainer(p_51956_, p_51957_, p_51958_, new ItemStack(Items.f_42499_));
      } else {
         return (WorldlyContainer)(i < 7 ? new ComposterBlock.InputContainer(p_51956_, p_51957_, p_51958_) : new ComposterBlock.EmptyContainer());
      }
   }

   static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
      public EmptyContainer() {
         super(0);
      }

      public int[] m_7071_(Direction p_52012_) {
         return new int[0];
      }

      public boolean m_7155_(int p_52008_, ItemStack p_52009_, @Nullable Direction p_52010_) {
         return false;
      }

      public boolean m_7157_(int p_52014_, ItemStack p_52015_, Direction p_52016_) {
         return false;
      }
   }

   static class InputContainer extends SimpleContainer implements WorldlyContainer {
      private final BlockState f_52017_;
      private final LevelAccessor f_52018_;
      private final BlockPos f_52019_;
      private boolean f_52020_;

      public InputContainer(BlockState p_52022_, LevelAccessor p_52023_, BlockPos p_52024_) {
         super(1);
         this.f_52017_ = p_52022_;
         this.f_52018_ = p_52023_;
         this.f_52019_ = p_52024_;
      }

      public int m_6893_() {
         return 1;
      }

      public int[] m_7071_(Direction p_52032_) {
         return p_52032_ == Direction.UP ? new int[]{0} : new int[0];
      }

      public boolean m_7155_(int p_52028_, ItemStack p_52029_, @Nullable Direction p_52030_) {
         return !this.f_52020_ && p_52030_ == Direction.UP && ComposterBlock.f_51914_.containsKey(p_52029_.m_41720_());
      }

      public boolean m_7157_(int p_52034_, ItemStack p_52035_, Direction p_52036_) {
         return false;
      }

      public void m_6596_() {
         ItemStack itemstack = this.m_8020_(0);
         if (!itemstack.m_41619_()) {
            this.f_52020_ = true;
            BlockState blockstate = ComposterBlock.m_51983_(this.f_52017_, this.f_52018_, this.f_52019_, itemstack);
            this.f_52018_.m_46796_(1500, this.f_52019_, blockstate != this.f_52017_ ? 1 : 0);
            this.m_8016_(0);
         }

      }
   }

   static class OutputContainer extends SimpleContainer implements WorldlyContainer {
      private final BlockState f_52037_;
      private final LevelAccessor f_52038_;
      private final BlockPos f_52039_;
      private boolean f_52040_;

      public OutputContainer(BlockState p_52042_, LevelAccessor p_52043_, BlockPos p_52044_, ItemStack p_52045_) {
         super(p_52045_);
         this.f_52037_ = p_52042_;
         this.f_52038_ = p_52043_;
         this.f_52039_ = p_52044_;
      }

      public int m_6893_() {
         return 1;
      }

      public int[] m_7071_(Direction p_52053_) {
         return p_52053_ == Direction.DOWN ? new int[]{0} : new int[0];
      }

      public boolean m_7155_(int p_52049_, ItemStack p_52050_, @Nullable Direction p_52051_) {
         return false;
      }

      public boolean m_7157_(int p_52055_, ItemStack p_52056_, Direction p_52057_) {
         return !this.f_52040_ && p_52057_ == Direction.DOWN && p_52056_.m_150930_(Items.f_42499_);
      }

      public void m_6596_() {
         ComposterBlock.m_52002_(this.f_52037_, this.f_52038_, this.f_52039_);
         this.f_52040_ = true;
      }
   }
}