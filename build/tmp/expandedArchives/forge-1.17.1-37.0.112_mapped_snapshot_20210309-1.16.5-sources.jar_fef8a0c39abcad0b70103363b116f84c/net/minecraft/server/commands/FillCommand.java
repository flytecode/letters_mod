package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class FillCommand {
   private static final int f_180222_ = 32768;
   private static final Dynamic2CommandExceptionType f_137372_ = new Dynamic2CommandExceptionType((p_137392_, p_137393_) -> {
      return new TranslatableComponent("commands.fill.toobig", p_137392_, p_137393_);
   });
   static final BlockInput f_137373_ = new BlockInput(Blocks.f_50016_.m_49966_(), Collections.emptySet(), (CompoundTag)null);
   private static final SimpleCommandExceptionType f_137374_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.fill.failed"));

   public static void m_137379_(CommandDispatcher<CommandSourceStack> p_137380_) {
      p_137380_.register(Commands.m_82127_("fill").requires((p_137384_) -> {
         return p_137384_.m_6761_(2);
      }).then(Commands.m_82129_("from", BlockPosArgument.m_118239_()).then(Commands.m_82129_("to", BlockPosArgument.m_118239_()).then(Commands.m_82129_("block", BlockStateArgument.m_116120_()).executes((p_137405_) -> {
         return m_137385_(p_137405_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_(p_137405_, "from"), BlockPosArgument.m_118242_(p_137405_, "to")), BlockStateArgument.m_116123_(p_137405_, "block"), FillCommand.Mode.REPLACE, (Predicate<BlockInWorld>)null);
      }).then(Commands.m_82127_("replace").executes((p_137403_) -> {
         return m_137385_(p_137403_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_(p_137403_, "from"), BlockPosArgument.m_118242_(p_137403_, "to")), BlockStateArgument.m_116123_(p_137403_, "block"), FillCommand.Mode.REPLACE, (Predicate<BlockInWorld>)null);
      }).then(Commands.m_82129_("filter", BlockPredicateArgument.m_115570_()).executes((p_137401_) -> {
         return m_137385_(p_137401_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_(p_137401_, "from"), BlockPosArgument.m_118242_(p_137401_, "to")), BlockStateArgument.m_116123_(p_137401_, "block"), FillCommand.Mode.REPLACE, BlockPredicateArgument.m_115573_(p_137401_, "filter"));
      }))).then(Commands.m_82127_("keep").executes((p_137399_) -> {
         return m_137385_(p_137399_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_(p_137399_, "from"), BlockPosArgument.m_118242_(p_137399_, "to")), BlockStateArgument.m_116123_(p_137399_, "block"), FillCommand.Mode.REPLACE, (p_180225_) -> {
            return p_180225_.m_61175_().m_46859_(p_180225_.m_61176_());
         });
      })).then(Commands.m_82127_("outline").executes((p_137397_) -> {
         return m_137385_(p_137397_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_(p_137397_, "from"), BlockPosArgument.m_118242_(p_137397_, "to")), BlockStateArgument.m_116123_(p_137397_, "block"), FillCommand.Mode.OUTLINE, (Predicate<BlockInWorld>)null);
      })).then(Commands.m_82127_("hollow").executes((p_137395_) -> {
         return m_137385_(p_137395_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_(p_137395_, "from"), BlockPosArgument.m_118242_(p_137395_, "to")), BlockStateArgument.m_116123_(p_137395_, "block"), FillCommand.Mode.HOLLOW, (Predicate<BlockInWorld>)null);
      })).then(Commands.m_82127_("destroy").executes((p_137382_) -> {
         return m_137385_(p_137382_.getSource(), BoundingBox.m_162375_(BlockPosArgument.m_118242_(p_137382_, "from"), BlockPosArgument.m_118242_(p_137382_, "to")), BlockStateArgument.m_116123_(p_137382_, "block"), FillCommand.Mode.DESTROY, (Predicate<BlockInWorld>)null);
      }))))));
   }

   private static int m_137385_(CommandSourceStack p_137386_, BoundingBox p_137387_, BlockInput p_137388_, FillCommand.Mode p_137389_, @Nullable Predicate<BlockInWorld> p_137390_) throws CommandSyntaxException {
      int i = p_137387_.m_71056_() * p_137387_.m_71057_() * p_137387_.m_71058_();
      if (i > 32768) {
         throw f_137372_.create(32768, i);
      } else {
         List<BlockPos> list = Lists.newArrayList();
         ServerLevel serverlevel = p_137386_.m_81372_();
         int j = 0;

         for(BlockPos blockpos : BlockPos.m_121976_(p_137387_.m_162395_(), p_137387_.m_162396_(), p_137387_.m_162398_(), p_137387_.m_162399_(), p_137387_.m_162400_(), p_137387_.m_162401_())) {
            if (p_137390_ == null || p_137390_.test(new BlockInWorld(serverlevel, blockpos, true))) {
               BlockInput blockinput = p_137389_.f_137410_.m_138619_(p_137387_, blockpos, p_137388_, serverlevel);
               if (blockinput != null) {
                  BlockEntity blockentity = serverlevel.m_7702_(blockpos);
                  Clearable.m_18908_(blockentity);
                  if (blockinput.m_114670_(serverlevel, blockpos, 2)) {
                     list.add(blockpos.m_7949_());
                     ++j;
                  }
               }
            }
         }

         for(BlockPos blockpos1 : list) {
            Block block = serverlevel.m_8055_(blockpos1).m_60734_();
            serverlevel.m_6289_(blockpos1, block);
         }

         if (j == 0) {
            throw f_137374_.create();
         } else {
            p_137386_.m_81354_(new TranslatableComponent("commands.fill.success", j), true);
            return j;
         }
      }
   }

   static enum Mode {
      REPLACE((p_137433_, p_137434_, p_137435_, p_137436_) -> {
         return p_137435_;
      }),
      OUTLINE((p_137428_, p_137429_, p_137430_, p_137431_) -> {
         return p_137429_.m_123341_() != p_137428_.m_162395_() && p_137429_.m_123341_() != p_137428_.m_162399_() && p_137429_.m_123342_() != p_137428_.m_162396_() && p_137429_.m_123342_() != p_137428_.m_162400_() && p_137429_.m_123343_() != p_137428_.m_162398_() && p_137429_.m_123343_() != p_137428_.m_162401_() ? null : p_137430_;
      }),
      HOLLOW((p_137423_, p_137424_, p_137425_, p_137426_) -> {
         return p_137424_.m_123341_() != p_137423_.m_162395_() && p_137424_.m_123341_() != p_137423_.m_162399_() && p_137424_.m_123342_() != p_137423_.m_162396_() && p_137424_.m_123342_() != p_137423_.m_162400_() && p_137424_.m_123343_() != p_137423_.m_162398_() && p_137424_.m_123343_() != p_137423_.m_162401_() ? FillCommand.f_137373_ : p_137425_;
      }),
      DESTROY((p_137418_, p_137419_, p_137420_, p_137421_) -> {
         p_137421_.m_46961_(p_137419_, true);
         return p_137420_;
      });

      public final SetBlockCommand.Filter f_137410_;

      private Mode(SetBlockCommand.Filter p_137416_) {
         this.f_137410_ = p_137416_;
      }
   }
}