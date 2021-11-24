package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperSlabBlock extends SlabBlock implements WeatheringCopper {
   private final WeatheringCopper.WeatherState f_154936_;

   public WeatheringCopperSlabBlock(WeatheringCopper.WeatherState p_154938_, BlockBehaviour.Properties p_154939_) {
      super(p_154939_);
      this.f_154936_ = p_154938_;
   }

   public void m_7455_(BlockState p_154942_, ServerLevel p_154943_, BlockPos p_154944_, Random p_154945_) {
      this.m_153041_(p_154942_, p_154943_, p_154944_, p_154945_);
   }

   public boolean m_6724_(BlockState p_154947_) {
      return WeatheringCopper.m_154904_(p_154947_.m_60734_()).isPresent();
   }

   public WeatheringCopper.WeatherState m_142297_() {
      return this.f_154936_;
   }
}