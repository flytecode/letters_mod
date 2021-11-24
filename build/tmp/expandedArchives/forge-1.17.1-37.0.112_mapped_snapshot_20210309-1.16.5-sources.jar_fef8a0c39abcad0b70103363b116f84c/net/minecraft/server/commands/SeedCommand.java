package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class SeedCommand {
   public static void m_138589_(CommandDispatcher<CommandSourceStack> p_138590_, boolean p_138591_) {
      p_138590_.register(Commands.m_82127_("seed").requires((p_138596_) -> {
         return !p_138591_ || p_138596_.m_6761_(2);
      }).executes((p_138593_) -> {
         long i = p_138593_.getSource().m_81372_().m_7328_();
         Component component = ComponentUtils.m_130748_((new TextComponent(String.valueOf(i))).m_130938_((p_180514_) -> {
            return p_180514_.m_131140_(ChatFormatting.GREEN).m_131142_(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(i))).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, new TranslatableComponent("chat.copy.click"))).m_131138_(String.valueOf(i));
         }));
         p_138593_.getSource().m_81354_(new TranslatableComponent("commands.seed.success", component), false);
         return (int)i;
      }));
   }
}