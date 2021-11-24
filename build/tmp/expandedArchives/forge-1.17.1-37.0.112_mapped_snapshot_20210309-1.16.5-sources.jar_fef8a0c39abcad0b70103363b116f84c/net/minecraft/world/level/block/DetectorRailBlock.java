package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;

public class DetectorRailBlock extends BaseRailBlock {
   public static final EnumProperty<RailShape> f_52427_ = BlockStateProperties.f_61404_;
   public static final BooleanProperty f_52428_ = BlockStateProperties.f_61448_;
   private static final int f_153121_ = 20;

   public DetectorRailBlock(BlockBehaviour.Properties p_52431_) {
      super(true, p_52431_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52428_, Boolean.valueOf(false)).m_61124_(f_52427_, RailShape.NORTH_SOUTH).m_61124_(f_152149_, Boolean.valueOf(false)));
   }

   public boolean m_7899_(BlockState p_52489_) {
      return true;
   }

   public void m_7892_(BlockState p_52458_, Level p_52459_, BlockPos p_52460_, Entity p_52461_) {
      if (!p_52459_.f_46443_) {
         if (!p_52458_.m_61143_(f_52428_)) {
            this.m_52432_(p_52459_, p_52460_, p_52458_);
         }
      }
   }

   public void m_7458_(BlockState p_52444_, ServerLevel p_52445_, BlockPos p_52446_, Random p_52447_) {
      if (p_52444_.m_61143_(f_52428_)) {
         this.m_52432_(p_52445_, p_52446_, p_52444_);
      }
   }

   public int m_6378_(BlockState p_52449_, BlockGetter p_52450_, BlockPos p_52451_, Direction p_52452_) {
      return p_52449_.m_61143_(f_52428_) ? 15 : 0;
   }

   public int m_6376_(BlockState p_52478_, BlockGetter p_52479_, BlockPos p_52480_, Direction p_52481_) {
      if (!p_52478_.m_61143_(f_52428_)) {
         return 0;
      } else {
         return p_52481_ == Direction.UP ? 15 : 0;
      }
   }

   private void m_52432_(Level p_52433_, BlockPos p_52434_, BlockState p_52435_) {
      if (this.m_7898_(p_52435_, p_52433_, p_52434_)) {
         boolean flag = p_52435_.m_61143_(f_52428_);
         boolean flag1 = false;
         List<AbstractMinecart> list = this.m_52436_(p_52433_, p_52434_, AbstractMinecart.class, (p_153125_) -> {
            return true;
         });
         if (!list.isEmpty()) {
            flag1 = true;
         }

         if (flag1 && !flag) {
            BlockState blockstate = p_52435_.m_61124_(f_52428_, Boolean.valueOf(true));
            p_52433_.m_7731_(p_52434_, blockstate, 3);
            this.m_52472_(p_52433_, p_52434_, blockstate, true);
            p_52433_.m_46672_(p_52434_, this);
            p_52433_.m_46672_(p_52434_.m_7495_(), this);
            p_52433_.m_6550_(p_52434_, p_52435_, blockstate);
         }

         if (!flag1 && flag) {
            BlockState blockstate1 = p_52435_.m_61124_(f_52428_, Boolean.valueOf(false));
            p_52433_.m_7731_(p_52434_, blockstate1, 3);
            this.m_52472_(p_52433_, p_52434_, blockstate1, false);
            p_52433_.m_46672_(p_52434_, this);
            p_52433_.m_46672_(p_52434_.m_7495_(), this);
            p_52433_.m_6550_(p_52434_, p_52435_, blockstate1);
         }

         if (flag1) {
            p_52433_.m_6219_().m_5945_(p_52434_, this, 20);
         }

         p_52433_.m_46717_(p_52434_, this);
      }
   }

   protected void m_52472_(Level p_52473_, BlockPos p_52474_, BlockState p_52475_, boolean p_52476_) {
      RailState railstate = new RailState(p_52473_, p_52474_, p_52475_);

      for(BlockPos blockpos : railstate.m_55424_()) {
         BlockState blockstate = p_52473_.m_8055_(blockpos);
         blockstate.m_60690_(p_52473_, blockpos, blockstate.m_60734_(), p_52474_, false);
      }

   }

   public void m_6807_(BlockState p_52483_, Level p_52484_, BlockPos p_52485_, BlockState p_52486_, boolean p_52487_) {
      if (!p_52486_.m_60713_(p_52483_.m_60734_())) {
         BlockState blockstate = this.m_49389_(p_52483_, p_52484_, p_52485_, p_52487_);
         this.m_52432_(p_52484_, p_52485_, blockstate);
      }
   }

   public Property<RailShape> m_7978_() {
      return f_52427_;
   }

   public boolean m_7278_(BlockState p_52442_) {
      return true;
   }

   public int m_6782_(BlockState p_52454_, Level p_52455_, BlockPos p_52456_) {
      if (p_52454_.m_61143_(f_52428_)) {
         List<MinecartCommandBlock> list = this.m_52436_(p_52455_, p_52456_, MinecartCommandBlock.class, (p_153123_) -> {
            return true;
         });
         if (!list.isEmpty()) {
            return list.get(0).m_38534_().m_45436_();
         }

         List<AbstractMinecart> carts = this.m_52436_(p_52455_, p_52456_, AbstractMinecart.class, e -> e.m_6084_());
         if (!carts.isEmpty() && carts.get(0).getComparatorLevel() > -1) return carts.get(0).getComparatorLevel();
         List<AbstractMinecart> list1 = carts.stream().filter(EntitySelector.f_20405_).collect(java.util.stream.Collectors.toList());
         if (!list1.isEmpty()) {
            return AbstractContainerMenu.m_38938_((Container)list1.get(0));
         }
      }

      return 0;
   }

   private <T extends AbstractMinecart> List<T> m_52436_(Level p_52437_, BlockPos p_52438_, Class<T> p_52439_, Predicate<Entity> p_52440_) {
      return p_52437_.m_6443_(p_52439_, this.m_52470_(p_52438_), p_52440_);
   }

   private AABB m_52470_(BlockPos p_52471_) {
      double d0 = 0.2D;
      return new AABB((double)p_52471_.m_123341_() + 0.2D, (double)p_52471_.m_123342_(), (double)p_52471_.m_123343_() + 0.2D, (double)(p_52471_.m_123341_() + 1) - 0.2D, (double)(p_52471_.m_123342_() + 1) - 0.2D, (double)(p_52471_.m_123343_() + 1) - 0.2D);
   }

   public BlockState m_6843_(BlockState p_52466_, Rotation p_52467_) {
      switch(p_52467_) {
      case CLOCKWISE_180:
         switch((RailShape)p_52466_.m_61143_(f_52427_)) {
         case ASCENDING_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
         case ASCENDING_NORTH:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_WEST);
         case SOUTH_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_EAST);
         case NORTH_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
         case NORTH_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
         }
      case COUNTERCLOCKWISE_90:
         switch((RailShape)p_52466_.m_61143_(f_52427_)) {
         case ASCENDING_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
         case ASCENDING_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_NORTH:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
         case ASCENDING_SOUTH:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
         case SOUTH_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_EAST);
         case SOUTH_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
         case NORTH_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
         case NORTH_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_WEST);
         case NORTH_SOUTH:
            return p_52466_.m_61124_(f_52427_, RailShape.EAST_WEST);
         case EAST_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_SOUTH);
         }
      case CLOCKWISE_90:
         switch((RailShape)p_52466_.m_61143_(f_52427_)) {
         case ASCENDING_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
         case ASCENDING_NORTH:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
         case ASCENDING_SOUTH:
            return p_52466_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
         case SOUTH_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
         case SOUTH_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_WEST);
         case NORTH_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_EAST);
         case NORTH_EAST:
            return p_52466_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
         case NORTH_SOUTH:
            return p_52466_.m_61124_(f_52427_, RailShape.EAST_WEST);
         case EAST_WEST:
            return p_52466_.m_61124_(f_52427_, RailShape.NORTH_SOUTH);
         }
      default:
         return p_52466_;
      }
   }

   public BlockState m_6943_(BlockState p_52463_, Mirror p_52464_) {
      RailShape railshape = p_52463_.m_61143_(f_52427_);
      switch(p_52464_) {
      case LEFT_RIGHT:
         switch(railshape) {
         case ASCENDING_NORTH:
            return p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_SOUTH);
         case ASCENDING_SOUTH:
            return p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_NORTH);
         case SOUTH_EAST:
            return p_52463_.m_61124_(f_52427_, RailShape.NORTH_EAST);
         case SOUTH_WEST:
            return p_52463_.m_61124_(f_52427_, RailShape.NORTH_WEST);
         case NORTH_WEST:
            return p_52463_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
         case NORTH_EAST:
            return p_52463_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
         default:
            return super.m_6943_(p_52463_, p_52464_);
         }
      case FRONT_BACK:
         switch(railshape) {
         case ASCENDING_EAST:
            return p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_WEST);
         case ASCENDING_WEST:
            return p_52463_.m_61124_(f_52427_, RailShape.ASCENDING_EAST);
         case ASCENDING_NORTH:
         case ASCENDING_SOUTH:
         default:
            break;
         case SOUTH_EAST:
            return p_52463_.m_61124_(f_52427_, RailShape.SOUTH_WEST);
         case SOUTH_WEST:
            return p_52463_.m_61124_(f_52427_, RailShape.SOUTH_EAST);
         case NORTH_WEST:
            return p_52463_.m_61124_(f_52427_, RailShape.NORTH_EAST);
         case NORTH_EAST:
            return p_52463_.m_61124_(f_52427_, RailShape.NORTH_WEST);
         }
      }

      return super.m_6943_(p_52463_, p_52464_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52469_) {
      p_52469_.m_61104_(f_52427_, f_52428_, f_152149_);
   }
}
