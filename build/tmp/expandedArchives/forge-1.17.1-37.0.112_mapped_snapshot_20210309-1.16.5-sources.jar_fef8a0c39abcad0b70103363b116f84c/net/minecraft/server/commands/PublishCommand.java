package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.level.GameType;

public class PublishCommand {
   private static final SimpleCommandExceptionType f_138181_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.publish.failed"));
   private static final DynamicCommandExceptionType f_138182_ = new DynamicCommandExceptionType((p_138194_) -> {
      return new TranslatableComponent("commands.publish.alreadyPublished", p_138194_);
   });

   public static void m_138184_(CommandDispatcher<CommandSourceStack> p_138185_) {
      p_138185_.register(Commands.m_82127_("publish").requires((p_138189_) -> {
         return p_138189_.m_6761_(4);
      }).executes((p_138196_) -> {
         return m_138190_(p_138196_.getSource(), HttpUtil.m_13939_());
      }).then(Commands.m_82129_("port", IntegerArgumentType.integer(0, 65535)).executes((p_138187_) -> {
         return m_138190_(p_138187_.getSource(), IntegerArgumentType.getInteger(p_138187_, "port"));
      })));
   }

   private static int m_138190_(CommandSourceStack p_138191_, int p_138192_) throws CommandSyntaxException {
      if (p_138191_.m_81377_().m_6992_()) {
         throw f_138182_.create(p_138191_.m_81377_().m_7010_());
      } else if (!p_138191_.m_81377_().m_7386_((GameType)null, false, p_138192_)) {
         throw f_138181_.create();
      } else {
         p_138191_.m_81354_(new TranslatableComponent("commands.publish.success", p_138192_), true);
         return p_138192_;
      }
   }
}