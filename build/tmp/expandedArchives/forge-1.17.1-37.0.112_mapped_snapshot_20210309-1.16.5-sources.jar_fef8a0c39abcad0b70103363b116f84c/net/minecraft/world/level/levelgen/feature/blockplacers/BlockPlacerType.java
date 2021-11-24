package net.minecraft.world.level.levelgen.feature.blockplacers;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public class BlockPlacerType<P extends BlockPlacer> extends net.minecraftforge.registries.ForgeRegistryEntry<BlockPlacerType<?>> {
   public static final BlockPlacerType<SimpleBlockPlacer> f_67487_ = m_67495_("simple_block_placer", SimpleBlockPlacer.f_67528_);
   public static final BlockPlacerType<DoublePlantPlacer> f_67488_ = m_67495_("double_plant_placer", DoublePlantPlacer.f_67517_);
   public static final BlockPlacerType<ColumnPlacer> f_67489_ = m_67495_("column_placer", ColumnPlacer.f_67498_);
   private final Codec<P> f_67490_;

   private static <P extends BlockPlacer> BlockPlacerType<P> m_67495_(String p_67496_, Codec<P> p_67497_) {
      return Registry.m_122961_(Registry.f_122857_, p_67496_, new BlockPlacerType<>(p_67497_));
   }

   public BlockPlacerType(Codec<P> p_67493_) {
      this.f_67490_ = p_67493_;
   }

   public Codec<P> m_67494_() {
      return this.f_67490_;
   }
}
