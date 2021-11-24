package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;

public class ReplaceBlobsFeature extends Feature<ReplaceSphereConfiguration> {
   public ReplaceBlobsFeature(Codec<ReplaceSphereConfiguration> p_66633_) {
      super(p_66633_);
   }

   public boolean m_142674_(FeaturePlaceContext<ReplaceSphereConfiguration> p_160214_) {
      ReplaceSphereConfiguration replacesphereconfiguration = p_160214_.m_159778_();
      WorldGenLevel worldgenlevel = p_160214_.m_159774_();
      Random random = p_160214_.m_159776_();
      Block block = replacesphereconfiguration.f_68037_.m_60734_();
      BlockPos blockpos = m_66634_(worldgenlevel, p_160214_.m_159777_().m_122032_().m_122147_(Direction.Axis.Y, worldgenlevel.m_141937_() + 1, worldgenlevel.m_151558_() - 1), block);
      if (blockpos == null) {
         return false;
      } else {
         int i = replacesphereconfiguration.m_161096_().m_142270_(random);
         int j = replacesphereconfiguration.m_161096_().m_142270_(random);
         int k = replacesphereconfiguration.m_161096_().m_142270_(random);
         int l = Math.max(i, Math.max(j, k));
         boolean flag = false;

         for(BlockPos blockpos1 : BlockPos.m_121925_(blockpos, i, j, k)) {
            if (blockpos1.m_123333_(blockpos) > l) {
               break;
            }

            BlockState blockstate = worldgenlevel.m_8055_(blockpos1);
            if (blockstate.m_60713_(block)) {
               this.m_5974_(worldgenlevel, blockpos1, replacesphereconfiguration.f_68038_);
               flag = true;
            }
         }

         return flag;
      }
   }

   @Nullable
   private static BlockPos m_66634_(LevelAccessor p_66635_, BlockPos.MutableBlockPos p_66636_, Block p_66637_) {
      while(p_66636_.m_123342_() > p_66635_.m_141937_() + 1) {
         BlockState blockstate = p_66635_.m_8055_(p_66636_);
         if (blockstate.m_60713_(p_66637_)) {
            return p_66636_;
         }

         p_66636_.m_122173_(Direction.DOWN);
      }

      return null;
   }
}