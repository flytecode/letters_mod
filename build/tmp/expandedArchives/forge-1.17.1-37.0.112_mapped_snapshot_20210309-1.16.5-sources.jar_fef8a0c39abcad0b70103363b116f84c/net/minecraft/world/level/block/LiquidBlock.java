package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LiquidBlock extends Block implements BucketPickup {
   public static final IntegerProperty f_54688_ = BlockStateProperties.f_61422_;
   protected final FlowingFluid f_54689_;
   private final List<FluidState> f_54691_;
   public static final VoxelShape f_54690_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   public static final ImmutableList<Direction> f_181233_ = ImmutableList.of(Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);

   @Deprecated  // Forge: Use the constructor that takes a supplier
   public LiquidBlock(FlowingFluid p_54694_, BlockBehaviour.Properties p_54695_) {
      super(p_54695_);
      this.f_54689_ = p_54694_;
      this.f_54691_ = Lists.newArrayList();
      this.f_54691_.add(p_54694_.m_76068_(false));

      for(int i = 1; i < 8; ++i) {
         this.f_54691_.add(p_54694_.m_75953_(8 - i, false));
      }

      this.f_54691_.add(p_54694_.m_75953_(8, true));
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54688_, Integer.valueOf(0)));
      fluidStateCacheInitialized = true;
      supplier = p_54694_.delegate;
   }

   /**
    * @param supplier A fluid supplier such as {@link net.minecraftforge.fmllegacy.RegistryObject<Fluid>}
    */
   public LiquidBlock(java.util.function.Supplier<? extends FlowingFluid> p_54694_, BlockBehaviour.Properties p_54695_) {
      super(p_54695_);
      this.f_54689_ = null;
      this.f_54691_ = Lists.newArrayList();
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54688_, Integer.valueOf(0)));
      this.supplier = p_54694_;
   }

   public VoxelShape m_5939_(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
      return p_54763_.m_6513_(f_54690_, p_54762_, true) && p_54760_.m_61143_(f_54688_) == 0 && p_54763_.m_7875_(p_54761_.m_6425_(p_54762_.m_7494_()), this.f_54689_) ? f_54690_ : Shapes.m_83040_();
   }

   public boolean m_6724_(BlockState p_54732_) {
      return p_54732_.m_60819_().m_76187_();
   }

   public void m_7455_(BlockState p_54740_, ServerLevel p_54741_, BlockPos p_54742_, Random p_54743_) {
      p_54740_.m_60819_().m_76174_(p_54741_, p_54742_, p_54743_);
   }

   public boolean m_7420_(BlockState p_54745_, BlockGetter p_54746_, BlockPos p_54747_) {
      return false;
   }

   public boolean m_7357_(BlockState p_54704_, BlockGetter p_54705_, BlockPos p_54706_, PathComputationType p_54707_) {
      return !this.f_54689_.m_76108_(FluidTags.f_13132_);
   }

   public FluidState m_5888_(BlockState p_54765_) {
      int i = p_54765_.m_61143_(f_54688_);
      if (!fluidStateCacheInitialized) initFluidStateCache();
      return this.f_54691_.get(Math.min(i, 8));
   }

   public boolean m_6104_(BlockState p_54716_, BlockState p_54717_, Direction p_54718_) {
      return p_54717_.m_60819_().m_76152_().m_6212_(this.f_54689_);
   }

   public RenderShape m_7514_(BlockState p_54738_) {
      return RenderShape.INVISIBLE;
   }

   public List<ItemStack> m_7381_(BlockState p_54720_, LootContext.Builder p_54721_) {
      return Collections.emptyList();
   }

   public VoxelShape m_5940_(BlockState p_54749_, BlockGetter p_54750_, BlockPos p_54751_, CollisionContext p_54752_) {
      return Shapes.m_83040_();
   }

   public void m_6807_(BlockState p_54754_, Level p_54755_, BlockPos p_54756_, BlockState p_54757_, boolean p_54758_) {
      if (this.m_54696_(p_54755_, p_54756_, p_54754_)) {
         p_54755_.m_6217_().m_5945_(p_54756_, p_54754_.m_60819_().m_76152_(), this.f_54689_.m_6718_(p_54755_));
      }

   }

   public BlockState m_7417_(BlockState p_54723_, Direction p_54724_, BlockState p_54725_, LevelAccessor p_54726_, BlockPos p_54727_, BlockPos p_54728_) {
      if (p_54723_.m_60819_().m_76170_() || p_54725_.m_60819_().m_76170_()) {
         p_54726_.m_6217_().m_5945_(p_54727_, p_54723_.m_60819_().m_76152_(), this.f_54689_.m_6718_(p_54726_));
      }

      return super.m_7417_(p_54723_, p_54724_, p_54725_, p_54726_, p_54727_, p_54728_);
   }

   public void m_6861_(BlockState p_54709_, Level p_54710_, BlockPos p_54711_, Block p_54712_, BlockPos p_54713_, boolean p_54714_) {
      if (this.m_54696_(p_54710_, p_54711_, p_54709_)) {
         p_54710_.m_6217_().m_5945_(p_54711_, p_54709_.m_60819_().m_76152_(), this.f_54689_.m_6718_(p_54710_));
      }

   }

   private boolean m_54696_(Level p_54697_, BlockPos p_54698_, BlockState p_54699_) {
      if (this.f_54689_.m_76108_(FluidTags.f_13132_)) {
         boolean flag = p_54697_.m_8055_(p_54698_.m_7495_()).m_60713_(Blocks.f_50136_);

         for(Direction direction : f_181233_) {
            BlockPos blockpos = p_54698_.m_142300_(direction.m_122424_());
            if (p_54697_.m_6425_(blockpos).m_76153_(FluidTags.f_13131_)) {
               Block block = p_54697_.m_6425_(p_54698_).m_76170_() ? Blocks.f_50080_ : Blocks.f_50652_;
               p_54697_.m_46597_(p_54698_, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_54697_, p_54698_, p_54698_, block.m_49966_()));
               this.m_54700_(p_54697_, p_54698_);
               return false;
            }

            if (flag && p_54697_.m_8055_(blockpos).m_60713_(Blocks.f_50568_)) {
               p_54697_.m_46597_(p_54698_, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_54697_, p_54698_, p_54698_, Blocks.f_50137_.m_49966_()));
               this.m_54700_(p_54697_, p_54698_);
               return false;
            }
         }
      }

      return true;
   }

   private void m_54700_(LevelAccessor p_54701_, BlockPos p_54702_) {
      p_54701_.m_46796_(1501, p_54702_, 0);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54730_) {
      p_54730_.m_61104_(f_54688_);
   }

   public ItemStack m_142598_(LevelAccessor p_153772_, BlockPos p_153773_, BlockState p_153774_) {
      if (p_153774_.m_61143_(f_54688_) == 0) {
         p_153772_.m_7731_(p_153773_, Blocks.f_50016_.m_49966_(), 11);
         return new ItemStack(this.f_54689_.m_6859_());
      } else {
         return ItemStack.f_41583_;
      }
   }

   // Forge start
   private final java.util.function.Supplier<? extends net.minecraft.world.level.material.Fluid> supplier;
   public FlowingFluid getFluid() {
      return (FlowingFluid)supplier.get();
   }

   private boolean fluidStateCacheInitialized = false;
   protected synchronized void initFluidStateCache() {
      if (fluidStateCacheInitialized == false) {
         this.f_54691_.add(getFluid().m_76068_(false));

         for (int i = 1; i < 8; ++i)
            this.f_54691_.add(getFluid().m_75953_(8 - i, false));

         this.f_54691_.add(getFluid().m_75953_(8, true));
         fluidStateCacheInitialized = true;
      }
   }

   public Optional<SoundEvent> m_142298_() {
      return this.f_54689_.m_142520_();
   }
}
