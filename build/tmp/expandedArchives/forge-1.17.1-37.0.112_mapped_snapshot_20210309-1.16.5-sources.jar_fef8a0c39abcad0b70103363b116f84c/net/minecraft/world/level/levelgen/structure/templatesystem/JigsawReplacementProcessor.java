package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class JigsawReplacementProcessor extends StructureProcessor {
   public static final Codec<JigsawReplacementProcessor> f_74121_;
   public static final JigsawReplacementProcessor f_74122_ = new JigsawReplacementProcessor();

   private JigsawReplacementProcessor() {
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74127_, BlockPos p_74128_, BlockPos p_74129_, StructureTemplate.StructureBlockInfo p_74130_, StructureTemplate.StructureBlockInfo p_74131_, StructurePlaceSettings p_74132_) {
      BlockState blockstate = p_74131_.f_74676_;
      if (blockstate.m_60713_(Blocks.f_50678_)) {
         String s = p_74131_.f_74677_.m_128461_("final_state");
         BlockStateParser blockstateparser = new BlockStateParser(new StringReader(s), false);

         try {
            blockstateparser.m_116806_(true);
         } catch (CommandSyntaxException commandsyntaxexception) {
            throw new RuntimeException(commandsyntaxexception);
         }

         return blockstateparser.m_116808_().m_60713_(Blocks.f_50454_) ? null : new StructureTemplate.StructureBlockInfo(p_74131_.f_74675_, blockstateparser.m_116808_(), (CompoundTag)null);
      } else {
         return p_74131_;
      }
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74459_;
   }

   static {
      f_74121_ = Codec.unit(() -> {
         return f_74122_;
      });
   }
}