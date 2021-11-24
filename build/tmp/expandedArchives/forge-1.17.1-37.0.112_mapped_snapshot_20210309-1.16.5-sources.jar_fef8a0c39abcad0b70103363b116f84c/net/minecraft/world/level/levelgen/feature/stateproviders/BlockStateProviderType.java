package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public class BlockStateProviderType<P extends BlockStateProvider> extends net.minecraftforge.registries.ForgeRegistryEntry<BlockStateProviderType<?>> {
   public static final BlockStateProviderType<SimpleStateProvider> f_68752_ = m_68762_("simple_state_provider", SimpleStateProvider.f_68797_);
   public static final BlockStateProviderType<WeightedStateProvider> f_68753_ = m_68762_("weighted_state_provider", WeightedStateProvider.f_68808_);
   public static final BlockStateProviderType<PlainFlowerProvider> f_68754_ = m_68762_("plain_flower_provider", PlainFlowerProvider.f_68775_);
   public static final BlockStateProviderType<ForestFlowerProvider> f_68755_ = m_68762_("forest_flower_provider", ForestFlowerProvider.f_68765_);
   public static final BlockStateProviderType<RotatedBlockProvider> f_68756_ = m_68762_("rotated_block_provider", RotatedBlockProvider.f_68786_);
   public static final BlockStateProviderType<RandomizedIntStateProvider> f_161554_ = m_68762_("randomized_int_state_provider", RandomizedIntStateProvider.f_161555_);
   private final Codec<P> f_68757_;

   private static <P extends BlockStateProvider> BlockStateProviderType<P> m_68762_(String p_68763_, Codec<P> p_68764_) {
      return Registry.m_122961_(Registry.f_122856_, p_68763_, new BlockStateProviderType<>(p_68764_));
   }

   public BlockStateProviderType(Codec<P> p_68760_) {
      this.f_68757_ = p_68760_;
   }

   public Codec<P> m_68761_() {
      return this.f_68757_;
   }
}
