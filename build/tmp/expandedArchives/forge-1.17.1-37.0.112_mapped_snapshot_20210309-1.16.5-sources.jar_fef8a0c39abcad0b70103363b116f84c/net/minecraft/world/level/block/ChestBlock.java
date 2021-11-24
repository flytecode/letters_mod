package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChestBlock extends AbstractChestBlock<ChestBlockEntity> implements SimpleWaterloggedBlock {
   public static final DirectionProperty f_51478_ = HorizontalDirectionalBlock.f_54117_;
   public static final EnumProperty<ChestType> f_51479_ = BlockStateProperties.f_61392_;
   public static final BooleanProperty f_51480_ = BlockStateProperties.f_61362_;
   public static final int f_153051_ = 1;
   protected static final int f_153052_ = 1;
   protected static final int f_153053_ = 14;
   protected static final VoxelShape f_51481_ = Block.m_49796_(1.0D, 0.0D, 0.0D, 15.0D, 14.0D, 15.0D);
   protected static final VoxelShape f_51482_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 16.0D);
   protected static final VoxelShape f_51483_ = Block.m_49796_(0.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
   protected static final VoxelShape f_51484_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 16.0D, 14.0D, 15.0D);
   protected static final VoxelShape f_51485_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
   private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>> f_51486_ = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>>() {
      public Optional<Container> m_6959_(ChestBlockEntity p_51591_, ChestBlockEntity p_51592_) {
         return Optional.of(new CompoundContainer(p_51591_, p_51592_));
      }

      public Optional<Container> m_7693_(ChestBlockEntity p_51589_) {
         return Optional.of(p_51589_);
      }

      public Optional<Container> m_6502_() {
         return Optional.empty();
      }
   };
   private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> f_51487_ = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>>() {
      public Optional<MenuProvider> m_6959_(final ChestBlockEntity p_51604_, final ChestBlockEntity p_51605_) {
         final Container container = new CompoundContainer(p_51604_, p_51605_);
         return Optional.of(new MenuProvider() {
            @Nullable
            public AbstractContainerMenu m_7208_(int p_51622_, Inventory p_51623_, Player p_51624_) {
               if (p_51604_.m_7525_(p_51624_) && p_51605_.m_7525_(p_51624_)) {
                  p_51604_.m_59640_(p_51623_.f_35978_);
                  p_51605_.m_59640_(p_51623_.f_35978_);
                  return ChestMenu.m_39246_(p_51622_, p_51623_, container);
               } else {
                  return null;
               }
            }

            public Component m_5446_() {
               if (p_51604_.m_8077_()) {
                  return p_51604_.m_5446_();
               } else {
                  return (Component)(p_51605_.m_8077_() ? p_51605_.m_5446_() : new TranslatableComponent("container.chestDouble"));
               }
            }
         });
      }

      public Optional<MenuProvider> m_7693_(ChestBlockEntity p_51602_) {
         return Optional.of(p_51602_);
      }

      public Optional<MenuProvider> m_6502_() {
         return Optional.empty();
      }
   };

   public ChestBlock(BlockBehaviour.Properties p_51490_, Supplier<BlockEntityType<? extends ChestBlockEntity>> p_51491_) {
      super(p_51490_, p_51491_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51478_, Direction.NORTH).m_61124_(f_51479_, ChestType.SINGLE).m_61124_(f_51480_, Boolean.valueOf(false)));
   }

   public static DoubleBlockCombiner.BlockType m_51582_(BlockState p_51583_) {
      ChestType chesttype = p_51583_.m_61143_(f_51479_);
      if (chesttype == ChestType.SINGLE) {
         return DoubleBlockCombiner.BlockType.SINGLE;
      } else {
         return chesttype == ChestType.RIGHT ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
      }
   }

   public RenderShape m_7514_(BlockState p_51567_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public BlockState m_7417_(BlockState p_51555_, Direction p_51556_, BlockState p_51557_, LevelAccessor p_51558_, BlockPos p_51559_, BlockPos p_51560_) {
      if (p_51555_.m_61143_(f_51480_)) {
         p_51558_.m_6217_().m_5945_(p_51559_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_51558_));
      }

      if (p_51557_.m_60713_(this) && p_51556_.m_122434_().m_122479_()) {
         ChestType chesttype = p_51557_.m_61143_(f_51479_);
         if (p_51555_.m_61143_(f_51479_) == ChestType.SINGLE && chesttype != ChestType.SINGLE && p_51555_.m_61143_(f_51478_) == p_51557_.m_61143_(f_51478_) && m_51584_(p_51557_) == p_51556_.m_122424_()) {
            return p_51555_.m_61124_(f_51479_, chesttype.m_61486_());
         }
      } else if (m_51584_(p_51555_) == p_51556_) {
         return p_51555_.m_61124_(f_51479_, ChestType.SINGLE);
      }

      return super.m_7417_(p_51555_, p_51556_, p_51557_, p_51558_, p_51559_, p_51560_);
   }

   public VoxelShape m_5940_(BlockState p_51569_, BlockGetter p_51570_, BlockPos p_51571_, CollisionContext p_51572_) {
      if (p_51569_.m_61143_(f_51479_) == ChestType.SINGLE) {
         return f_51485_;
      } else {
         switch(m_51584_(p_51569_)) {
         case NORTH:
         default:
            return f_51481_;
         case SOUTH:
            return f_51482_;
         case WEST:
            return f_51483_;
         case EAST:
            return f_51484_;
         }
      }
   }

   public static Direction m_51584_(BlockState p_51585_) {
      Direction direction = p_51585_.m_61143_(f_51478_);
      return p_51585_.m_61143_(f_51479_) == ChestType.LEFT ? direction.m_122427_() : direction.m_122428_();
   }

   public BlockState m_5573_(BlockPlaceContext p_51493_) {
      ChestType chesttype = ChestType.SINGLE;
      Direction direction = p_51493_.m_8125_().m_122424_();
      FluidState fluidstate = p_51493_.m_43725_().m_6425_(p_51493_.m_8083_());
      boolean flag = p_51493_.m_7078_();
      Direction direction1 = p_51493_.m_43719_();
      if (direction1.m_122434_().m_122479_() && flag) {
         Direction direction2 = this.m_51494_(p_51493_, direction1.m_122424_());
         if (direction2 != null && direction2.m_122434_() != direction1.m_122434_()) {
            direction = direction2;
            chesttype = direction2.m_122428_() == direction1.m_122424_() ? ChestType.RIGHT : ChestType.LEFT;
         }
      }

      if (chesttype == ChestType.SINGLE && !flag) {
         if (direction == this.m_51494_(p_51493_, direction.m_122427_())) {
            chesttype = ChestType.LEFT;
         } else if (direction == this.m_51494_(p_51493_, direction.m_122428_())) {
            chesttype = ChestType.RIGHT;
         }
      }

      return this.m_49966_().m_61124_(f_51478_, direction).m_61124_(f_51479_, chesttype).m_61124_(f_51480_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
   }

   public FluidState m_5888_(BlockState p_51581_) {
      return p_51581_.m_61143_(f_51480_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_51581_);
   }

   @Nullable
   private Direction m_51494_(BlockPlaceContext p_51495_, Direction p_51496_) {
      BlockState blockstate = p_51495_.m_43725_().m_8055_(p_51495_.m_8083_().m_142300_(p_51496_));
      return blockstate.m_60713_(this) && blockstate.m_61143_(f_51479_) == ChestType.SINGLE ? blockstate.m_61143_(f_51478_) : null;
   }

   public void m_6402_(Level p_51503_, BlockPos p_51504_, BlockState p_51505_, LivingEntity p_51506_, ItemStack p_51507_) {
      if (p_51507_.m_41788_()) {
         BlockEntity blockentity = p_51503_.m_7702_(p_51504_);
         if (blockentity instanceof ChestBlockEntity) {
            ((ChestBlockEntity)blockentity).m_58638_(p_51507_.m_41786_());
         }
      }

   }

   public void m_6810_(BlockState p_51538_, Level p_51539_, BlockPos p_51540_, BlockState p_51541_, boolean p_51542_) {
      if (!p_51538_.m_60713_(p_51541_.m_60734_())) {
         BlockEntity blockentity = p_51539_.m_7702_(p_51540_);
         if (blockentity instanceof Container) {
            Containers.m_19002_(p_51539_, p_51540_, (Container)blockentity);
            p_51539_.m_46717_(p_51540_, this);
         }

         super.m_6810_(p_51538_, p_51539_, p_51540_, p_51541_, p_51542_);
      }
   }

   public InteractionResult m_6227_(BlockState p_51531_, Level p_51532_, BlockPos p_51533_, Player p_51534_, InteractionHand p_51535_, BlockHitResult p_51536_) {
      if (p_51532_.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         MenuProvider menuprovider = this.m_7246_(p_51531_, p_51532_, p_51533_);
         if (menuprovider != null) {
            p_51534_.m_5893_(menuprovider);
            p_51534_.m_36246_(this.m_7699_());
            PiglinAi.m_34873_(p_51534_, true);
         }

         return InteractionResult.CONSUME;
      }
   }

   protected Stat<ResourceLocation> m_7699_() {
      return Stats.f_12988_.m_12902_(Stats.f_12968_);
   }

   public BlockEntityType<? extends ChestBlockEntity> m_153066_() {
      return this.f_48675_.get();
   }

   @Nullable
   public static Container m_51511_(ChestBlock p_51512_, BlockState p_51513_, Level p_51514_, BlockPos p_51515_, boolean p_51516_) {
      return p_51512_.m_5641_(p_51513_, p_51514_, p_51515_, p_51516_).<Optional<Container>>m_5649_(f_51486_).orElse((Container)null);
   }

   public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> m_5641_(BlockState p_51544_, Level p_51545_, BlockPos p_51546_, boolean p_51547_) {
      BiPredicate<LevelAccessor, BlockPos> bipredicate;
      if (p_51547_) {
         bipredicate = (p_51578_, p_51579_) -> {
            return false;
         };
      } else {
         bipredicate = ChestBlock::m_51508_;
      }

      return DoubleBlockCombiner.m_52822_(this.f_48675_.get(), ChestBlock::m_51582_, ChestBlock::m_51584_, f_51478_, p_51544_, p_51545_, p_51546_, bipredicate);
   }

   @Nullable
   public MenuProvider m_7246_(BlockState p_51574_, Level p_51575_, BlockPos p_51576_) {
      return this.m_5641_(p_51574_, p_51575_, p_51576_, false).<Optional<MenuProvider>>m_5649_(f_51487_).orElse((MenuProvider)null);
   }

   public static DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction> m_51517_(final LidBlockEntity p_51518_) {
      return new DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction>() {
         public Float2FloatFunction m_6959_(ChestBlockEntity p_51633_, ChestBlockEntity p_51634_) {
            return (p_51638_) -> {
               return Math.max(p_51633_.m_6683_(p_51638_), p_51634_.m_6683_(p_51638_));
            };
         }

         public Float2FloatFunction m_7693_(ChestBlockEntity p_51631_) {
            return p_51631_::m_6683_;
         }

         public Float2FloatFunction m_6502_() {
            return p_51518_::m_6683_;
         }
      };
   }

   public BlockEntity m_142194_(BlockPos p_153064_, BlockState p_153065_) {
      return new ChestBlockEntity(p_153064_, p_153065_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153055_, BlockState p_153056_, BlockEntityType<T> p_153057_) {
      return p_153055_.f_46443_ ? m_152132_(p_153057_, this.m_153066_(), ChestBlockEntity::m_155343_) : null;
   }

   public static boolean m_51508_(LevelAccessor p_51509_, BlockPos p_51510_) {
      return m_51499_(p_51509_, p_51510_) || m_51563_(p_51509_, p_51510_);
   }

   private static boolean m_51499_(BlockGetter p_51500_, BlockPos p_51501_) {
      BlockPos blockpos = p_51501_.m_7494_();
      return p_51500_.m_8055_(blockpos).m_60796_(p_51500_, blockpos);
   }

   private static boolean m_51563_(LevelAccessor p_51564_, BlockPos p_51565_) {
      List<Cat> list = p_51564_.m_45976_(Cat.class, new AABB((double)p_51565_.m_123341_(), (double)(p_51565_.m_123342_() + 1), (double)p_51565_.m_123343_(), (double)(p_51565_.m_123341_() + 1), (double)(p_51565_.m_123342_() + 2), (double)(p_51565_.m_123343_() + 1)));
      if (!list.isEmpty()) {
         for(Cat cat : list) {
            if (cat.m_21825_()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_7278_(BlockState p_51520_) {
      return true;
   }

   public int m_6782_(BlockState p_51527_, Level p_51528_, BlockPos p_51529_) {
      return AbstractContainerMenu.m_38938_(m_51511_(this, p_51527_, p_51528_, p_51529_, false));
   }

   public BlockState m_6843_(BlockState p_51552_, Rotation p_51553_) {
      return p_51552_.m_61124_(f_51478_, p_51553_.m_55954_(p_51552_.m_61143_(f_51478_)));
   }

   public BlockState m_6943_(BlockState p_51549_, Mirror p_51550_) {
      BlockState rotated = p_51549_.m_60717_(p_51550_.m_54846_(p_51549_.m_61143_(f_51478_)));
      return p_51550_ == Mirror.NONE ? rotated : rotated.m_61124_(f_51479_, rotated.m_61143_(f_51479_).m_61486_());  // Forge: Fixed MC-134110 Structure mirroring breaking apart double chests
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51562_) {
      p_51562_.m_61104_(f_51478_, f_51479_, f_51480_);
   }

   public boolean m_7357_(BlockState p_51522_, BlockGetter p_51523_, BlockPos p_51524_, PathComputationType p_51525_) {
      return false;
   }

   public void m_7458_(BlockState p_153059_, ServerLevel p_153060_, BlockPos p_153061_, Random p_153062_) {
      BlockEntity blockentity = p_153060_.m_7702_(p_153061_);
      if (blockentity instanceof ChestBlockEntity) {
         ((ChestBlockEntity)blockentity).m_155350_();
      }

   }
}
