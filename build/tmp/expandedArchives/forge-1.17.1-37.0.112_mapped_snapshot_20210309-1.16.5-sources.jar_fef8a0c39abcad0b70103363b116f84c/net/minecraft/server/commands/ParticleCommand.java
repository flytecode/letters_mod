package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ParticleCommand {
   private static final SimpleCommandExceptionType f_138120_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.particle.failed"));

   public static void m_138122_(CommandDispatcher<CommandSourceStack> p_138123_) {
      p_138123_.register(Commands.m_82127_("particle").requires((p_138127_) -> {
         return p_138127_.m_6761_(2);
      }).then(Commands.m_82129_("name", ParticleArgument.m_103931_()).executes((p_138148_) -> {
         return m_138128_(p_138148_.getSource(), ParticleArgument.m_103937_(p_138148_, "name"), p_138148_.getSource().m_81371_(), Vec3.f_82478_, 0.0F, 0, false, p_138148_.getSource().m_81377_().m_6846_().m_11314_());
      }).then(Commands.m_82129_("pos", Vec3Argument.m_120841_()).executes((p_138146_) -> {
         return m_138128_(p_138146_.getSource(), ParticleArgument.m_103937_(p_138146_, "name"), Vec3Argument.m_120844_(p_138146_, "pos"), Vec3.f_82478_, 0.0F, 0, false, p_138146_.getSource().m_81377_().m_6846_().m_11314_());
      }).then(Commands.m_82129_("delta", Vec3Argument.m_120847_(false)).then(Commands.m_82129_("speed", FloatArgumentType.floatArg(0.0F)).then(Commands.m_82129_("count", IntegerArgumentType.integer(0)).executes((p_138144_) -> {
         return m_138128_(p_138144_.getSource(), ParticleArgument.m_103937_(p_138144_, "name"), Vec3Argument.m_120844_(p_138144_, "pos"), Vec3Argument.m_120844_(p_138144_, "delta"), FloatArgumentType.getFloat(p_138144_, "speed"), IntegerArgumentType.getInteger(p_138144_, "count"), false, p_138144_.getSource().m_81377_().m_6846_().m_11314_());
      }).then(Commands.m_82127_("force").executes((p_138142_) -> {
         return m_138128_(p_138142_.getSource(), ParticleArgument.m_103937_(p_138142_, "name"), Vec3Argument.m_120844_(p_138142_, "pos"), Vec3Argument.m_120844_(p_138142_, "delta"), FloatArgumentType.getFloat(p_138142_, "speed"), IntegerArgumentType.getInteger(p_138142_, "count"), true, p_138142_.getSource().m_81377_().m_6846_().m_11314_());
      }).then(Commands.m_82129_("viewers", EntityArgument.m_91470_()).executes((p_138140_) -> {
         return m_138128_(p_138140_.getSource(), ParticleArgument.m_103937_(p_138140_, "name"), Vec3Argument.m_120844_(p_138140_, "pos"), Vec3Argument.m_120844_(p_138140_, "delta"), FloatArgumentType.getFloat(p_138140_, "speed"), IntegerArgumentType.getInteger(p_138140_, "count"), true, EntityArgument.m_91477_(p_138140_, "viewers"));
      }))).then(Commands.m_82127_("normal").executes((p_138138_) -> {
         return m_138128_(p_138138_.getSource(), ParticleArgument.m_103937_(p_138138_, "name"), Vec3Argument.m_120844_(p_138138_, "pos"), Vec3Argument.m_120844_(p_138138_, "delta"), FloatArgumentType.getFloat(p_138138_, "speed"), IntegerArgumentType.getInteger(p_138138_, "count"), false, p_138138_.getSource().m_81377_().m_6846_().m_11314_());
      }).then(Commands.m_82129_("viewers", EntityArgument.m_91470_()).executes((p_138125_) -> {
         return m_138128_(p_138125_.getSource(), ParticleArgument.m_103937_(p_138125_, "name"), Vec3Argument.m_120844_(p_138125_, "pos"), Vec3Argument.m_120844_(p_138125_, "delta"), FloatArgumentType.getFloat(p_138125_, "speed"), IntegerArgumentType.getInteger(p_138125_, "count"), false, EntityArgument.m_91477_(p_138125_, "viewers"));
      })))))))));
   }

   private static int m_138128_(CommandSourceStack p_138129_, ParticleOptions p_138130_, Vec3 p_138131_, Vec3 p_138132_, float p_138133_, int p_138134_, boolean p_138135_, Collection<ServerPlayer> p_138136_) throws CommandSyntaxException {
      int i = 0;

      for(ServerPlayer serverplayer : p_138136_) {
         if (p_138129_.m_81372_().m_8624_(serverplayer, p_138130_, p_138135_, p_138131_.f_82479_, p_138131_.f_82480_, p_138131_.f_82481_, p_138134_, p_138132_.f_82479_, p_138132_.f_82480_, p_138132_.f_82481_, (double)p_138133_)) {
            ++i;
         }
      }

      if (i == 0) {
         throw f_138120_.create();
      } else {
         p_138129_.m_81354_(new TranslatableComponent("commands.particle.success", Registry.f_122829_.m_7981_(p_138130_.m_6012_()).toString()), true);
         return i;
      }
   }
}