package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class VegetationPatchFeature extends Feature<VegetationPatchConfiguration> {
   public VegetationPatchFeature(Codec<VegetationPatchConfiguration> p_160588_) {
      super(p_160588_);
   }

   public boolean m_142674_(FeaturePlaceContext<VegetationPatchConfiguration> p_160612_) {
      WorldGenLevel worldgenlevel = p_160612_.m_159774_();
      VegetationPatchConfiguration vegetationpatchconfiguration = p_160612_.m_159778_();
      Random random = p_160612_.m_159776_();
      BlockPos blockpos = p_160612_.m_159777_();
      Predicate<BlockState> predicate = m_160621_(vegetationpatchconfiguration);
      int i = vegetationpatchconfiguration.f_161289_.m_142270_(random) + 1;
      int j = vegetationpatchconfiguration.f_161289_.m_142270_(random) + 1;
      Set<BlockPos> set = this.m_142619_(worldgenlevel, vegetationpatchconfiguration, random, blockpos, predicate, i, j);
      this.m_160613_(p_160612_, worldgenlevel, vegetationpatchconfiguration, random, set, i, j);
      return !set.isEmpty();
   }

   protected Set<BlockPos> m_142619_(WorldGenLevel p_160597_, VegetationPatchConfiguration p_160598_, Random p_160599_, BlockPos p_160600_, Predicate<BlockState> p_160601_, int p_160602_, int p_160603_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_160600_.m_122032_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos$mutableblockpos.m_122032_();
      Direction direction = p_160598_.f_161284_.m_162107_();
      Direction direction1 = direction.m_122424_();
      Set<BlockPos> set = new HashSet<>();

      for(int i = -p_160602_; i <= p_160602_; ++i) {
         boolean flag = i == -p_160602_ || i == p_160602_;

         for(int j = -p_160603_; j <= p_160603_; ++j) {
            boolean flag1 = j == -p_160603_ || j == p_160603_;
            boolean flag2 = flag || flag1;
            boolean flag3 = flag && flag1;
            boolean flag4 = flag2 && !flag3;
            if (!flag3 && (!flag4 || p_160598_.f_161290_ != 0.0F && !(p_160599_.nextFloat() > p_160598_.f_161290_))) {
               blockpos$mutableblockpos.m_122154_(p_160600_, i, 0, j);

               for(int k = 0; p_160597_.m_7433_(blockpos$mutableblockpos, BlockBehaviour.BlockStateBase::m_60795_) && k < p_160598_.f_161287_; ++k) {
                  blockpos$mutableblockpos.m_122173_(direction);
               }

               for(int i1 = 0; p_160597_.m_7433_(blockpos$mutableblockpos, (p_160626_) -> {
                  return !p_160626_.m_60795_();
               }) && i1 < p_160598_.f_161287_; ++i1) {
                  blockpos$mutableblockpos.m_122173_(direction1);
               }

               blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, p_160598_.f_161284_.m_162107_());
               BlockState blockstate = p_160597_.m_8055_(blockpos$mutableblockpos1);
               if (p_160597_.m_46859_(blockpos$mutableblockpos) && blockstate.m_60783_(p_160597_, blockpos$mutableblockpos1, p_160598_.f_161284_.m_162107_().m_122424_())) {
                  int l = p_160598_.f_161285_.m_142270_(p_160599_) + (p_160598_.f_161286_ > 0.0F && p_160599_.nextFloat() < p_160598_.f_161286_ ? 1 : 0);
                  BlockPos blockpos = blockpos$mutableblockpos1.m_7949_();
                  boolean flag5 = this.m_160604_(p_160597_, p_160598_, p_160601_, p_160599_, blockpos$mutableblockpos1, l);
                  if (flag5) {
                     set.add(blockpos);
                  }
               }
            }
         }
      }

      return set;
   }

   protected void m_160613_(FeaturePlaceContext<VegetationPatchConfiguration> p_160614_, WorldGenLevel p_160615_, VegetationPatchConfiguration p_160616_, Random p_160617_, Set<BlockPos> p_160618_, int p_160619_, int p_160620_) {
      for(BlockPos blockpos : p_160618_) {
         if (p_160616_.f_161288_ > 0.0F && p_160617_.nextFloat() < p_160616_.f_161288_) {
            this.m_142229_(p_160615_, p_160616_, p_160614_.m_159775_(), p_160617_, blockpos);
         }
      }

   }

   protected boolean m_142229_(WorldGenLevel p_160592_, VegetationPatchConfiguration p_160593_, ChunkGenerator p_160594_, Random p_160595_, BlockPos p_160596_) {
      return p_160593_.f_161283_.get().m_65385_(p_160592_, p_160594_, p_160595_, p_160596_.m_142300_(p_160593_.f_161284_.m_162107_().m_122424_()));
   }

   protected boolean m_160604_(WorldGenLevel p_160605_, VegetationPatchConfiguration p_160606_, Predicate<BlockState> p_160607_, Random p_160608_, BlockPos.MutableBlockPos p_160609_, int p_160610_) {
      for(int i = 0; i < p_160610_; ++i) {
         BlockState blockstate = p_160606_.f_161282_.m_7112_(p_160608_, p_160609_);
         BlockState blockstate1 = p_160605_.m_8055_(p_160609_);
         if (!blockstate.m_60713_(blockstate1.m_60734_())) {
            if (!p_160607_.test(blockstate1)) {
               return i != 0;
            }

            p_160605_.m_7731_(p_160609_, blockstate, 2);
            p_160609_.m_122173_(p_160606_.f_161284_.m_162107_());
         }
      }

      return true;
   }

   private static Predicate<BlockState> m_160621_(VegetationPatchConfiguration p_160622_) {
      Tag<Block> tag = BlockTags.m_13115_().m_13404_(p_160622_.f_161281_);
      return tag == null ? (p_160624_) -> {
         return true;
      } : (p_160591_) -> {
         return p_160591_.m_60620_(tag);
      };
   }
}