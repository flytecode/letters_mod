package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ObserverBlock extends DirectionalBlock {
   public static final BooleanProperty f_55082_ = BlockStateProperties.f_61448_;

   public ObserverBlock(BlockBehaviour.Properties p_55085_) {
      super(p_55085_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52588_, Direction.SOUTH).m_61124_(f_55082_, Boolean.valueOf(false)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55125_) {
      p_55125_.m_61104_(f_52588_, f_55082_);
   }

   public BlockState m_6843_(BlockState p_55115_, Rotation p_55116_) {
      return p_55115_.m_61124_(f_52588_, p_55116_.m_55954_(p_55115_.m_61143_(f_52588_)));
   }

   public BlockState m_6943_(BlockState p_55112_, Mirror p_55113_) {
      return p_55112_.m_60717_(p_55113_.m_54846_(p_55112_.m_61143_(f_52588_)));
   }

   public void m_7458_(BlockState p_55096_, ServerLevel p_55097_, BlockPos p_55098_, Random p_55099_) {
      if (p_55096_.m_61143_(f_55082_)) {
         p_55097_.m_7731_(p_55098_, p_55096_.m_61124_(f_55082_, Boolean.valueOf(false)), 2);
      } else {
         p_55097_.m_7731_(p_55098_, p_55096_.m_61124_(f_55082_, Boolean.valueOf(true)), 2);
         p_55097_.m_6219_().m_5945_(p_55098_, this, 2);
      }

      this.m_55088_(p_55097_, p_55098_, p_55096_);
   }

   public BlockState m_7417_(BlockState p_55118_, Direction p_55119_, BlockState p_55120_, LevelAccessor p_55121_, BlockPos p_55122_, BlockPos p_55123_) {
      if (p_55118_.m_61143_(f_52588_) == p_55119_ && !p_55118_.m_61143_(f_55082_)) {
         this.m_55092_(p_55121_, p_55122_);
      }

      return super.m_7417_(p_55118_, p_55119_, p_55120_, p_55121_, p_55122_, p_55123_);
   }

   private void m_55092_(LevelAccessor p_55093_, BlockPos p_55094_) {
      if (!p_55093_.m_5776_() && !p_55093_.m_6219_().m_5916_(p_55094_, this)) {
         p_55093_.m_6219_().m_5945_(p_55094_, this, 2);
      }

   }

   protected void m_55088_(Level p_55089_, BlockPos p_55090_, BlockState p_55091_) {
      Direction direction = p_55091_.m_61143_(f_52588_);
      BlockPos blockpos = p_55090_.m_142300_(direction.m_122424_());
      p_55089_.m_46586_(blockpos, this, p_55090_);
      p_55089_.m_46590_(blockpos, this, direction);
   }

   public boolean m_7899_(BlockState p_55138_) {
      return true;
   }

   public int m_6376_(BlockState p_55127_, BlockGetter p_55128_, BlockPos p_55129_, Direction p_55130_) {
      return p_55127_.m_60746_(p_55128_, p_55129_, p_55130_);
   }

   public int m_6378_(BlockState p_55101_, BlockGetter p_55102_, BlockPos p_55103_, Direction p_55104_) {
      return p_55101_.m_61143_(f_55082_) && p_55101_.m_61143_(f_52588_) == p_55104_ ? 15 : 0;
   }

   public void m_6807_(BlockState p_55132_, Level p_55133_, BlockPos p_55134_, BlockState p_55135_, boolean p_55136_) {
      if (!p_55132_.m_60713_(p_55135_.m_60734_())) {
         if (!p_55133_.m_5776_() && p_55132_.m_61143_(f_55082_) && !p_55133_.m_6219_().m_5916_(p_55134_, this)) {
            BlockState blockstate = p_55132_.m_61124_(f_55082_, Boolean.valueOf(false));
            p_55133_.m_7731_(p_55134_, blockstate, 18);
            this.m_55088_(p_55133_, p_55134_, blockstate);
         }

      }
   }

   public void m_6810_(BlockState p_55106_, Level p_55107_, BlockPos p_55108_, BlockState p_55109_, boolean p_55110_) {
      if (!p_55106_.m_60713_(p_55109_.m_60734_())) {
         if (!p_55107_.f_46443_ && p_55106_.m_61143_(f_55082_) && p_55107_.m_6219_().m_5916_(p_55108_, this)) {
            this.m_55088_(p_55107_, p_55108_, p_55106_.m_61124_(f_55082_, Boolean.valueOf(false)));
         }

      }
   }

   public BlockState m_5573_(BlockPlaceContext p_55087_) {
      return this.m_49966_().m_61124_(f_52588_, p_55087_.m_7820_().m_122424_().m_122424_());
   }
}