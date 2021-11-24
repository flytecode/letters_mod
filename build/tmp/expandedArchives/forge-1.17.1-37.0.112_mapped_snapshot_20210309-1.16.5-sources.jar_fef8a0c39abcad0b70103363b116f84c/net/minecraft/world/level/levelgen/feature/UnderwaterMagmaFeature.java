package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.phys.AABB;

public class UnderwaterMagmaFeature extends Feature<UnderwaterMagmaConfiguration> {
   public UnderwaterMagmaFeature(Codec<UnderwaterMagmaConfiguration> p_160560_) {
      super(p_160560_);
   }

   public boolean m_142674_(FeaturePlaceContext<UnderwaterMagmaConfiguration> p_160569_) {
      WorldGenLevel worldgenlevel = p_160569_.m_159774_();
      BlockPos blockpos = p_160569_.m_159777_();
      UnderwaterMagmaConfiguration underwatermagmaconfiguration = p_160569_.m_159778_();
      Random random = p_160569_.m_159776_();
      OptionalInt optionalint = m_160564_(worldgenlevel, blockpos, underwatermagmaconfiguration);
      if (!optionalint.isPresent()) {
         return false;
      } else {
         BlockPos blockpos1 = blockpos.m_175288_(optionalint.getAsInt());
         Vec3i vec3i = new Vec3i(underwatermagmaconfiguration.f_161265_, underwatermagmaconfiguration.f_161265_, underwatermagmaconfiguration.f_161265_);
         AABB aabb = new AABB(blockpos1.m_141950_(vec3i), blockpos1.m_141952_(vec3i));
         return BlockPos.m_121921_(aabb).filter((p_160573_) -> {
            return random.nextFloat() < underwatermagmaconfiguration.f_161266_;
         }).filter((p_160584_) -> {
            return this.m_160574_(worldgenlevel, p_160584_);
         }).mapToInt((p_160579_) -> {
            worldgenlevel.m_7731_(p_160579_, Blocks.f_50450_.m_49966_(), 2);
            return 1;
         }).sum() > 0;
      }
   }

   private static OptionalInt m_160564_(WorldGenLevel p_160565_, BlockPos p_160566_, UnderwaterMagmaConfiguration p_160567_) {
      Predicate<BlockState> predicate = (p_160586_) -> {
         return p_160586_.m_60713_(Blocks.f_49990_);
      };
      Predicate<BlockState> predicate1 = (p_160581_) -> {
         return !p_160581_.m_60713_(Blocks.f_49990_);
      };
      Optional<Column> optional = Column.m_158175_(p_160565_, p_160566_, p_160567_.f_161264_, predicate, predicate1);
      return optional.map(Column::m_142009_).orElseGet(OptionalInt::empty);
   }

   private boolean m_160574_(WorldGenLevel p_160575_, BlockPos p_160576_) {
      if (!this.m_160561_(p_160575_, p_160576_) && !this.m_160561_(p_160575_, p_160576_.m_7495_())) {
         for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (this.m_160561_(p_160575_, p_160576_.m_142300_(direction))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean m_160561_(LevelAccessor p_160562_, BlockPos p_160563_) {
      BlockState blockstate = p_160562_.m_8055_(p_160563_);
      return blockstate.m_60713_(Blocks.f_49990_) || blockstate.m_60795_();
   }
}