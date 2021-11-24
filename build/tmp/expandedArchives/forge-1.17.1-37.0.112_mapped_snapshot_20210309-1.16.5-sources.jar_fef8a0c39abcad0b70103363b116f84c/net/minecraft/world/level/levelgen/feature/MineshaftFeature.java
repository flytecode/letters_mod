package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.MineshaftConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.MineShaftPieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class MineshaftFeature extends StructureFeature<MineshaftConfiguration> {
   public MineshaftFeature(Codec<MineshaftConfiguration> p_66273_) {
      super(p_66273_);
   }

   protected boolean m_142290_(ChunkGenerator p_160021_, BiomeSource p_160022_, long p_160023_, WorldgenRandom p_160024_, ChunkPos p_160025_, Biome p_160026_, ChunkPos p_160027_, MineshaftConfiguration p_160028_, LevelHeightAccessor p_160029_) {
      p_160024_.m_64703_(p_160023_, p_160025_.f_45578_, p_160025_.f_45579_);
      double d0 = (double)p_160028_.f_67781_;
      return p_160024_.nextDouble() < d0;
   }

   public StructureFeature.StructureStartFactory<MineshaftConfiguration> m_6258_() {
      return MineshaftFeature.MineShaftStart::new;
   }

   public static class MineShaftStart extends StructureStart<MineshaftConfiguration> {
      public MineShaftStart(StructureFeature<MineshaftConfiguration> p_160031_, ChunkPos p_160032_, int p_160033_, long p_160034_) {
         super(p_160031_, p_160032_, p_160033_, p_160034_);
      }

      public void m_142743_(RegistryAccess p_160044_, ChunkGenerator p_160045_, StructureManager p_160046_, ChunkPos p_160047_, Biome p_160048_, MineshaftConfiguration p_160049_, LevelHeightAccessor p_160050_) {
         MineShaftPieces.MineShaftRoom mineshaftpieces$mineshaftroom = new MineShaftPieces.MineShaftRoom(0, this.f_73564_, p_160047_.m_151382_(2), p_160047_.m_151391_(2), p_160049_.f_67782_);
         this.m_142679_(mineshaftpieces$mineshaftroom);
         mineshaftpieces$mineshaftroom.m_142537_(mineshaftpieces$mineshaftroom, this, this.f_73564_);
         if (p_160049_.f_67782_ == MineshaftFeature.Type.MESA) {
            int i = -5;
            BoundingBox boundingbox = this.m_73601_();
            int j = p_160045_.m_6337_() - boundingbox.m_162400_() + boundingbox.m_71057_() / 2 - -5;
            this.m_163599_(j);
         } else {
            this.m_163601_(p_160045_.m_6337_(), p_160045_.m_142062_(), this.f_73564_, 10);
         }

      }
   }

   public static enum Type implements StringRepresentable {
      NORMAL("normal", Blocks.f_49999_, Blocks.f_50705_, Blocks.f_50132_),
      MESA("mesa", Blocks.f_50004_, Blocks.f_50745_, Blocks.f_50483_);

      public static final Codec<MineshaftFeature.Type> f_66320_ = StringRepresentable.m_14350_(MineshaftFeature.Type::values, MineshaftFeature.Type::m_66334_);
      private static final Map<String, MineshaftFeature.Type> f_66321_ = Arrays.stream(values()).collect(Collectors.toMap(MineshaftFeature.Type::m_66336_, (p_66333_) -> {
         return p_66333_;
      }));
      private final String f_66322_;
      private final BlockState f_160051_;
      private final BlockState f_160052_;
      private final BlockState f_160053_;

      private Type(String p_160057_, Block p_160058_, Block p_160059_, Block p_160060_) {
         this.f_66322_ = p_160057_;
         this.f_160051_ = p_160058_.m_49966_();
         this.f_160052_ = p_160059_.m_49966_();
         this.f_160053_ = p_160060_.m_49966_();
      }

      public String m_66336_() {
         return this.f_66322_;
      }

      private static MineshaftFeature.Type m_66334_(String p_66335_) {
         return f_66321_.get(p_66335_);
      }

      public static MineshaftFeature.Type m_66330_(int p_66331_) {
         return p_66331_ >= 0 && p_66331_ < values().length ? values()[p_66331_] : NORMAL;
      }

      public BlockState m_160061_() {
         return this.f_160051_;
      }

      public BlockState m_160062_() {
         return this.f_160052_;
      }

      public BlockState m_160063_() {
         return this.f_160053_;
      }

      public String m_7912_() {
         return this.f_66322_;
      }
   }
}