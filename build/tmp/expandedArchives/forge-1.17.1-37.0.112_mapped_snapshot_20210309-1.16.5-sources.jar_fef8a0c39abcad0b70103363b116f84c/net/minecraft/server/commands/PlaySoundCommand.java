package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class PlaySoundCommand {
   private static final SimpleCommandExceptionType f_138149_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.playsound.failed"));

   public static void m_138156_(CommandDispatcher<CommandSourceStack> p_138157_) {
      RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> requiredargumentbuilder = Commands.m_82129_("sound", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121643_);

      for(SoundSource soundsource : SoundSource.values()) {
         requiredargumentbuilder.then(m_138151_(soundsource));
      }

      p_138157_.register(Commands.m_82127_("playsound").requires((p_138159_) -> {
         return p_138159_.m_6761_(2);
      }).then(requiredargumentbuilder));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> m_138151_(SoundSource p_138152_) {
      return Commands.m_82127_(p_138152_.m_12676_()).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((p_138180_) -> {
         return m_138160_(p_138180_.getSource(), EntityArgument.m_91477_(p_138180_, "targets"), ResourceLocationArgument.m_107011_(p_138180_, "sound"), p_138152_, p_138180_.getSource().m_81371_(), 1.0F, 1.0F, 0.0F);
      }).then(Commands.m_82129_("pos", Vec3Argument.m_120841_()).executes((p_138177_) -> {
         return m_138160_(p_138177_.getSource(), EntityArgument.m_91477_(p_138177_, "targets"), ResourceLocationArgument.m_107011_(p_138177_, "sound"), p_138152_, Vec3Argument.m_120844_(p_138177_, "pos"), 1.0F, 1.0F, 0.0F);
      }).then(Commands.m_82129_("volume", FloatArgumentType.floatArg(0.0F)).executes((p_138174_) -> {
         return m_138160_(p_138174_.getSource(), EntityArgument.m_91477_(p_138174_, "targets"), ResourceLocationArgument.m_107011_(p_138174_, "sound"), p_138152_, Vec3Argument.m_120844_(p_138174_, "pos"), p_138174_.getArgument("volume", Float.class), 1.0F, 0.0F);
      }).then(Commands.m_82129_("pitch", FloatArgumentType.floatArg(0.0F, 2.0F)).executes((p_138171_) -> {
         return m_138160_(p_138171_.getSource(), EntityArgument.m_91477_(p_138171_, "targets"), ResourceLocationArgument.m_107011_(p_138171_, "sound"), p_138152_, Vec3Argument.m_120844_(p_138171_, "pos"), p_138171_.getArgument("volume", Float.class), p_138171_.getArgument("pitch", Float.class), 0.0F);
      }).then(Commands.m_82129_("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F)).executes((p_138155_) -> {
         return m_138160_(p_138155_.getSource(), EntityArgument.m_91477_(p_138155_, "targets"), ResourceLocationArgument.m_107011_(p_138155_, "sound"), p_138152_, Vec3Argument.m_120844_(p_138155_, "pos"), p_138155_.getArgument("volume", Float.class), p_138155_.getArgument("pitch", Float.class), p_138155_.getArgument("minVolume", Float.class));
      }))))));
   }

   private static int m_138160_(CommandSourceStack p_138161_, Collection<ServerPlayer> p_138162_, ResourceLocation p_138163_, SoundSource p_138164_, Vec3 p_138165_, float p_138166_, float p_138167_, float p_138168_) throws CommandSyntaxException {
      double d0 = Math.pow(p_138166_ > 1.0F ? (double)(p_138166_ * 16.0F) : 16.0D, 2.0D);
      int i = 0;
      Iterator iterator = p_138162_.iterator();

      while(true) {
         ServerPlayer serverplayer;
         Vec3 vec3;
         float f;
         while(true) {
            if (!iterator.hasNext()) {
               if (i == 0) {
                  throw f_138149_.create();
               }

               if (p_138162_.size() == 1) {
                  p_138161_.m_81354_(new TranslatableComponent("commands.playsound.success.single", p_138163_, p_138162_.iterator().next().m_5446_()), true);
               } else {
                  p_138161_.m_81354_(new TranslatableComponent("commands.playsound.success.multiple", p_138163_, p_138162_.size()), true);
               }

               return i;
            }

            serverplayer = (ServerPlayer)iterator.next();
            double d1 = p_138165_.f_82479_ - serverplayer.m_20185_();
            double d2 = p_138165_.f_82480_ - serverplayer.m_20186_();
            double d3 = p_138165_.f_82481_ - serverplayer.m_20189_();
            double d4 = d1 * d1 + d2 * d2 + d3 * d3;
            vec3 = p_138165_;
            f = p_138166_;
            if (!(d4 > d0)) {
               break;
            }

            if (!(p_138168_ <= 0.0F)) {
               double d5 = Math.sqrt(d4);
               vec3 = new Vec3(serverplayer.m_20185_() + d1 / d5 * 2.0D, serverplayer.m_20186_() + d2 / d5 * 2.0D, serverplayer.m_20189_() + d3 / d5 * 2.0D);
               f = p_138168_;
               break;
            }
         }

         serverplayer.f_8906_.m_141995_(new ClientboundCustomSoundPacket(p_138163_, p_138164_, vec3, f, p_138167_));
         ++i;
      }
   }
}