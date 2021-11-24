package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CommonLevelAccessor extends EntityGetter, LevelReader, LevelSimulatedRW {
   default <T extends BlockEntity> Optional<T> m_141902_(BlockPos p_151452_, BlockEntityType<T> p_151453_) {
      return LevelReader.super.m_141902_(p_151452_, p_151453_);
   }

   default Stream<VoxelShape> m_5454_(@Nullable Entity p_45834_, AABB p_45835_, Predicate<Entity> p_45836_) {
      return EntityGetter.super.m_5454_(p_45834_, p_45835_, p_45836_);
   }

   default boolean m_5450_(@Nullable Entity p_45828_, VoxelShape p_45829_) {
      return EntityGetter.super.m_5450_(p_45828_, p_45829_);
   }

   default BlockPos m_5452_(Heightmap.Types p_45831_, BlockPos p_45832_) {
      return LevelReader.super.m_5452_(p_45831_, p_45832_);
   }

   RegistryAccess m_5962_();

   default Optional<ResourceKey<Biome>> m_45837_(BlockPos p_45838_) {
      return this.m_5962_().m_175515_(Registry.f_122885_).m_7854_(this.m_46857_(p_45838_));
   }
}