package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;

public class PoweredRailBlock extends BaseRailBlock {
   public static final EnumProperty<RailShape> f_55214_ = BlockStateProperties.f_61404_;
   public static final BooleanProperty f_55215_ = BlockStateProperties.f_61448_;
   private final boolean isActivator;  // TRUE for an Activator Rail, FALSE for Powered Rail

   public PoweredRailBlock(BlockBehaviour.Properties p_55218_) {
      this(p_55218_, false);
   }

   protected PoweredRailBlock(BlockBehaviour.Properties builder, boolean isPoweredRail) {
      super(true, builder);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_55214_, RailShape.NORTH_SOUTH).m_61124_(f_55215_, Boolean.valueOf(false)).m_61124_(f_152149_, Boolean.valueOf(false)));
      this.isActivator = !isPoweredRail;
   }

   protected boolean m_55219_(Level p_55220_, BlockPos p_55221_, BlockState p_55222_, boolean p_55223_, int p_55224_) {
      if (p_55224_ >= 8) {
         return false;
      } else {
         int i = p_55221_.m_123341_();
         int j = p_55221_.m_123342_();
         int k = p_55221_.m_123343_();
         boolean flag = true;
         RailShape railshape = p_55222_.m_61143_(f_55214_);
         switch(railshape) {
         case NORTH_SOUTH:
            if (p_55223_) {
               ++k;
            } else {
               --k;
            }
            break;
         case EAST_WEST:
            if (p_55223_) {
               --i;
            } else {
               ++i;
            }
            break;
         case ASCENDING_EAST:
            if (p_55223_) {
               --i;
            } else {
               ++i;
               ++j;
               flag = false;
            }

            railshape = RailShape.EAST_WEST;
            break;
         case ASCENDING_WEST:
            if (p_55223_) {
               --i;
               ++j;
               flag = false;
            } else {
               ++i;
            }

            railshape = RailShape.EAST_WEST;
            break;
         case ASCENDING_NORTH:
            if (p_55223_) {
               ++k;
            } else {
               --k;
               ++j;
               flag = false;
            }

            railshape = RailShape.NORTH_SOUTH;
            break;
         case ASCENDING_SOUTH:
            if (p_55223_) {
               ++k;
               ++j;
               flag = false;
            } else {
               --k;
            }

            railshape = RailShape.NORTH_SOUTH;
         }

         if (this.m_55225_(p_55220_, new BlockPos(i, j, k), p_55223_, p_55224_, railshape)) {
            return true;
         } else {
            return flag && this.m_55225_(p_55220_, new BlockPos(i, j - 1, k), p_55223_, p_55224_, railshape);
         }
      }
   }

   protected boolean m_55225_(Level p_55226_, BlockPos p_55227_, boolean p_55228_, int p_55229_, RailShape p_55230_) {
      BlockState blockstate = p_55226_.m_8055_(p_55227_);
      if (!(blockstate.m_60734_() instanceof PoweredRailBlock)) {
         return false;
      } else {
         RailShape railshape = getRailDirection(blockstate, p_55226_, p_55227_, null);
         if (p_55230_ != RailShape.EAST_WEST || railshape != RailShape.NORTH_SOUTH && railshape != RailShape.ASCENDING_NORTH && railshape != RailShape.ASCENDING_SOUTH) {
            if (p_55230_ != RailShape.NORTH_SOUTH || railshape != RailShape.EAST_WEST && railshape != RailShape.ASCENDING_EAST && railshape != RailShape.ASCENDING_WEST) {
               if (isActivator == (((PoweredRailBlock) blockstate.m_60734_()).isActivator)) {
                  return p_55226_.m_46753_(p_55227_) ? true : this.m_55219_(p_55226_, p_55227_, blockstate, p_55228_, p_55229_ + 1);
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected void m_6360_(BlockState p_55232_, Level p_55233_, BlockPos p_55234_, Block p_55235_) {
      boolean flag = p_55232_.m_61143_(f_55215_);
      boolean flag1 = p_55233_.m_46753_(p_55234_) || this.m_55219_(p_55233_, p_55234_, p_55232_, true, 0) || this.m_55219_(p_55233_, p_55234_, p_55232_, false, 0);
      if (flag1 != flag) {
         p_55233_.m_7731_(p_55234_, p_55232_.m_61124_(f_55215_, Boolean.valueOf(flag1)), 3);
         p_55233_.m_46672_(p_55234_.m_7495_(), this);
         if (p_55232_.m_61143_(f_55214_).m_61745_()) {
            p_55233_.m_46672_(p_55234_.m_7494_(), this);
         }
      }

   }

   public Property<RailShape> m_7978_() {
      return f_55214_;
   }

   public BlockState m_6843_(BlockState p_55240_, Rotation p_55241_) {
      switch(p_55241_) {
      case CLOCKWISE_180:
         switch((RailShape)p_55240_.m_61143_(f_55214_)) {
         case ASCENDING_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
         case ASCENDING_NORTH:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_WEST);
         case SOUTH_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_EAST);
         case NORTH_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
         case NORTH_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
         case NORTH_SOUTH: //Forge fix: MC-196102
         case EAST_WEST:
            return p_55240_;
         }
      case COUNTERCLOCKWISE_90:
         switch((RailShape)p_55240_.m_61143_(f_55214_)) {
         case NORTH_SOUTH:
            return p_55240_.m_61124_(f_55214_, RailShape.EAST_WEST);
         case EAST_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_SOUTH);
         case ASCENDING_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
         case ASCENDING_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_NORTH:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
         case ASCENDING_SOUTH:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
         case SOUTH_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_EAST);
         case SOUTH_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
         case NORTH_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
         case NORTH_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_WEST);
         }
      case CLOCKWISE_90:
         switch((RailShape)p_55240_.m_61143_(f_55214_)) {
         case NORTH_SOUTH:
            return p_55240_.m_61124_(f_55214_, RailShape.EAST_WEST);
         case EAST_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_SOUTH);
         case ASCENDING_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
         case ASCENDING_NORTH:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
         case ASCENDING_SOUTH:
            return p_55240_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
         case SOUTH_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
         case SOUTH_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_WEST);
         case NORTH_WEST:
            return p_55240_.m_61124_(f_55214_, RailShape.NORTH_EAST);
         case NORTH_EAST:
            return p_55240_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
         }
      default:
         return p_55240_;
      }
   }

   public BlockState m_6943_(BlockState p_55237_, Mirror p_55238_) {
      RailShape railshape = p_55237_.m_61143_(f_55214_);
      switch(p_55238_) {
      case LEFT_RIGHT:
         switch(railshape) {
         case ASCENDING_NORTH:
            return p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_55237_.m_61124_(f_55214_, RailShape.NORTH_EAST);
         case SOUTH_WEST:
            return p_55237_.m_61124_(f_55214_, RailShape.NORTH_WEST);
         case NORTH_WEST:
            return p_55237_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
         case NORTH_EAST:
            return p_55237_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
         default:
            return super.m_6943_(p_55237_, p_55238_);
         }
      case FRONT_BACK:
         switch(railshape) {
         case ASCENDING_EAST:
            return p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_55237_.m_61124_(f_55214_, RailShape.ASCENDING_EAST);
         case ASCENDING_NORTH:
         case ASCENDING_SOUTH:
         default:
            break;
         case SOUTH_EAST:
            return p_55237_.m_61124_(f_55214_, RailShape.SOUTH_WEST);
         case SOUTH_WEST:
            return p_55237_.m_61124_(f_55214_, RailShape.SOUTH_EAST);
         case NORTH_WEST:
            return p_55237_.m_61124_(f_55214_, RailShape.NORTH_EAST);
         case NORTH_EAST:
            return p_55237_.m_61124_(f_55214_, RailShape.NORTH_WEST);
         }
      }

      return super.m_6943_(p_55237_, p_55238_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55243_) {
      p_55243_.m_61104_(f_55214_, f_55215_, f_152149_);
   }

   public boolean isActivatorRail() {
      return isActivator;
   }
}
