package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperStairBlock extends StairBlock implements WeatheringCopper {
   private final WeatheringCopper.WeatherState f_154949_;

   public WeatheringCopperStairBlock(WeatheringCopper.WeatherState p_154951_, BlockState p_154952_, BlockBehaviour.Properties p_154953_) {
      super(p_154952_, p_154953_);
      this.f_154949_ = p_154951_;
   }

   public void m_7455_(BlockState p_154956_, ServerLevel p_154957_, BlockPos p_154958_, Random p_154959_) {
      this.m_153041_(p_154956_, p_154957_, p_154958_, p_154959_);
   }

   public boolean m_6724_(BlockState p_154961_) {
      return WeatheringCopper.m_154904_(p_154961_.m_60734_()).isPresent();
   }

   public WeatheringCopper.WeatherState m_142297_() {
      return this.f_154949_;
   }
}