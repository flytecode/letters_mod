package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperFullBlock extends Block implements WeatheringCopper {
   private final WeatheringCopper.WeatherState f_154923_;

   public WeatheringCopperFullBlock(WeatheringCopper.WeatherState p_154925_, BlockBehaviour.Properties p_154926_) {
      super(p_154926_);
      this.f_154923_ = p_154925_;
   }

   public void m_7455_(BlockState p_154929_, ServerLevel p_154930_, BlockPos p_154931_, Random p_154932_) {
      this.m_153041_(p_154929_, p_154930_, p_154931_, p_154932_);
   }

   public boolean m_6724_(BlockState p_154935_) {
      return WeatheringCopper.m_154904_(p_154935_.m_60734_()).isPresent();
   }

   public WeatheringCopper.WeatherState m_142297_() {
      return this.f_154923_;
   }
}