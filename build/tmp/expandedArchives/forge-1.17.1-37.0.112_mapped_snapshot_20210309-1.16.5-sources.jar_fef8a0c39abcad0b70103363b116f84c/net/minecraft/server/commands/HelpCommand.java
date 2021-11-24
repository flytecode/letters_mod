package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class HelpCommand {
   private static final SimpleCommandExceptionType f_137785_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.help.failed"));

   public static void m_137787_(CommandDispatcher<CommandSourceStack> p_137788_) {
      p_137788_.register(Commands.m_82127_("help").executes((p_137794_) -> {
         Map<CommandNode<CommandSourceStack>, String> map = p_137788_.getSmartUsage(p_137788_.getRoot(), p_137794_.getSource());

         for(String s : map.values()) {
            p_137794_.getSource().m_81354_(new TextComponent("/" + s), false);
         }

         return map.size();
      }).then(Commands.m_82129_("command", StringArgumentType.greedyString()).executes((p_137791_) -> {
         ParseResults<CommandSourceStack> parseresults = p_137788_.parse(StringArgumentType.getString(p_137791_, "command"), p_137791_.getSource());
         if (parseresults.getContext().getNodes().isEmpty()) {
            throw f_137785_.create();
         } else {
            Map<CommandNode<CommandSourceStack>, String> map = p_137788_.getSmartUsage(Iterables.getLast(parseresults.getContext().getNodes()).getNode(), p_137791_.getSource());

            for(String s : map.values()) {
               p_137791_.getSource().m_81354_(new TextComponent("/" + parseresults.getReader().getString() + " " + s), false);
            }

            return map.size();
         }
      })));
   }
}