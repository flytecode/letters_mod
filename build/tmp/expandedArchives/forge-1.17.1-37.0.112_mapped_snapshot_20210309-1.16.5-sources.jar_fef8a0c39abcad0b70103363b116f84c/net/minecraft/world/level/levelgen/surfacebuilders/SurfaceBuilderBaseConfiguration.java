package net.minecraft.world.level.levelgen.surfacebuilders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;

public class SurfaceBuilderBaseConfiguration implements SurfaceBuilderConfiguration {
   public static final Codec<SurfaceBuilderBaseConfiguration> f_75241_ = RecordCodecBuilder.create((p_75252_) -> {
      return p_75252_.group(BlockState.f_61039_.fieldOf("top_material").forGetter((p_164231_) -> {
         return p_164231_.f_75242_;
      }), BlockState.f_61039_.fieldOf("under_material").forGetter((p_164229_) -> {
         return p_164229_.f_75243_;
      }), BlockState.f_61039_.fieldOf("underwater_material").forGetter((p_164227_) -> {
         return p_164227_.f_75244_;
      })).apply(p_75252_, SurfaceBuilderBaseConfiguration::new);
   });
   private final BlockState f_75242_;
   private final BlockState f_75243_;
   private final BlockState f_75244_;

   public SurfaceBuilderBaseConfiguration(BlockState p_75247_, BlockState p_75248_, BlockState p_75249_) {
      this.f_75242_ = p_75247_;
      this.f_75243_ = p_75248_;
      this.f_75244_ = p_75249_;
   }

   public BlockState m_6743_() {
      return this.f_75242_;
   }

   public BlockState m_6744_() {
      return this.f_75243_;
   }

   public BlockState m_142434_() {
      return this.f_75244_;
   }
}