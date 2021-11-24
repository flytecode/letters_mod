package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class GeodeBlockSettings {
   public final BlockStateProvider f_158287_;
   public final BlockStateProvider f_158288_;
   public final BlockStateProvider f_158289_;
   public final BlockStateProvider f_158290_;
   public final BlockStateProvider f_158291_;
   public final List<BlockState> f_158292_;
   public final ResourceLocation f_158293_;
   public final ResourceLocation f_158294_;
   public static final Codec<GeodeBlockSettings> f_158295_ = RecordCodecBuilder.create((p_158307_) -> {
      return p_158307_.group(BlockStateProvider.f_68747_.fieldOf("filling_provider").forGetter((p_158323_) -> {
         return p_158323_.f_158287_;
      }), BlockStateProvider.f_68747_.fieldOf("inner_layer_provider").forGetter((p_158321_) -> {
         return p_158321_.f_158288_;
      }), BlockStateProvider.f_68747_.fieldOf("alternate_inner_layer_provider").forGetter((p_158319_) -> {
         return p_158319_.f_158289_;
      }), BlockStateProvider.f_68747_.fieldOf("middle_layer_provider").forGetter((p_158317_) -> {
         return p_158317_.f_158290_;
      }), BlockStateProvider.f_68747_.fieldOf("outer_layer_provider").forGetter((p_158315_) -> {
         return p_158315_.f_158291_;
      }), ExtraCodecs.m_144637_(BlockState.f_61039_.listOf()).fieldOf("inner_placements").forGetter((p_158313_) -> {
         return p_158313_.f_158292_;
      }), ResourceLocation.f_135803_.fieldOf("cannot_replace").forGetter((p_158311_) -> {
         return p_158311_.f_158293_;
      }), ResourceLocation.f_135803_.fieldOf("invalid_blocks").forGetter((p_158309_) -> {
         return p_158309_.f_158294_;
      })).apply(p_158307_, GeodeBlockSettings::new);
   });

   public GeodeBlockSettings(BlockStateProvider p_158298_, BlockStateProvider p_158299_, BlockStateProvider p_158300_, BlockStateProvider p_158301_, BlockStateProvider p_158302_, List<BlockState> p_158303_, ResourceLocation p_158304_, ResourceLocation p_158305_) {
      this.f_158287_ = p_158298_;
      this.f_158288_ = p_158299_;
      this.f_158289_ = p_158300_;
      this.f_158290_ = p_158301_;
      this.f_158291_ = p_158302_;
      this.f_158292_ = p_158303_;
      this.f_158293_ = p_158304_;
      this.f_158294_ = p_158305_;
   }
}