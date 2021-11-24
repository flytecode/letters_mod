package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class SetBlockCommand {
   private static final SimpleCommandExceptionType f_138597_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.setblock.failed"));

   public static void m_138601_(CommandDispatcher<CommandSourceStack> p_138602_) {
      p_138602_.register(Commands.m_82127_("setblock").requires((p_138606_) -> {
         return p_138606_.m_6761_(2);
      }).then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).then(Commands.m_82129_("block", BlockStateArgument.m_116120_()).executes((p_138618_) -> {
         return m_138607_(p_138618_.getSource(), BlockPosArgument.m_118242_(p_138618_, "pos"), BlockStateArgument.m_116123_(p_138618_, "block"), SetBlockCommand.Mode.REPLACE, (Predicate<BlockInWorld>)null);
      }).then(Commands.m_82127_("destroy").executes((p_138616_) -> {
         return m_138607_(p_138616_.getSource(), BlockPosArgument.m_118242_(p_138616_, "pos"), BlockStateArgument.m_116123_(p_138616_, "block"), SetBlockCommand.Mode.DESTROY, (Predicate<BlockInWorld>)null);
      })).then(Commands.m_82127_("keep").executes((p_138614_) -> {
         return m_138607_(p_138614_.getSource(), BlockPosArgument.m_118242_(p_138614_, "pos"), BlockStateArgument.m_116123_(p_138614_, "block"), SetBlockCommand.Mode.REPLACE, (p_180517_) -> {
            return p_180517_.m_61175_().m_46859_(p_180517_.m_61176_());
         });
      })).then(Commands.m_82127_("replace").executes((p_138604_) -> {
         return m_138607_(p_138604_.getSource(), BlockPosArgument.m_118242_(p_138604_, "pos"), BlockStateArgument.m_116123_(p_138604_, "block"), SetBlockCommand.Mode.REPLACE, (Predicate<BlockInWorld>)null);
      })))));
   }

   private static int m_138607_(CommandSourceStack p_138608_, BlockPos p_138609_, BlockInput p_138610_, SetBlockCommand.Mode p_138611_, @Nullable Predicate<BlockInWorld> p_138612_) throws CommandSyntaxException {
      ServerLevel serverlevel = p_138608_.m_81372_();
      if (p_138612_ != null && !p_138612_.test(new BlockInWorld(serverlevel, p_138609_, true))) {
         throw f_138597_.create();
      } else {
         boolean flag;
         if (p_138611_ == SetBlockCommand.Mode.DESTROY) {
            serverlevel.m_46961_(p_138609_, true);
            flag = !p_138610_.m_114669_().m_60795_() || !serverlevel.m_8055_(p_138609_).m_60795_();
         } else {
            BlockEntity blockentity = serverlevel.m_7702_(p_138609_);
            Clearable.m_18908_(blockentity);
            flag = true;
         }

         if (flag && !p_138610_.m_114670_(serverlevel, p_138609_, 2)) {
            throw f_138597_.create();
         } else {
            serverlevel.m_6289_(p_138609_, p_138610_.m_114669_().m_60734_());
            p_138608_.m_81354_(new TranslatableComponent("commands.setblock.success", p_138609_.m_123341_(), p_138609_.m_123342_(), p_138609_.m_123343_()), true);
            return 1;
         }
      }
   }

   public interface Filter {
      @Nullable
      BlockInput m_138619_(BoundingBox p_138620_, BlockPos p_138621_, BlockInput p_138622_, ServerLevel p_138623_);
   }

   public static enum Mode {
      REPLACE,
      DESTROY;
   }
}