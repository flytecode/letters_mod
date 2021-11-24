package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;

public class StopSoundCommand {
   public static void m_138794_(CommandDispatcher<CommandSourceStack> p_138795_) {
      RequiredArgumentBuilder<CommandSourceStack, EntitySelector> requiredargumentbuilder = Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((p_138809_) -> {
         return m_138800_(p_138809_.getSource(), EntityArgument.m_91477_(p_138809_, "targets"), (SoundSource)null, (ResourceLocation)null);
      }).then(Commands.m_82127_("*").then(Commands.m_82129_("sound", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121643_).executes((p_138797_) -> {
         return m_138800_(p_138797_.getSource(), EntityArgument.m_91477_(p_138797_, "targets"), (SoundSource)null, ResourceLocationArgument.m_107011_(p_138797_, "sound"));
      })));

      for(SoundSource soundsource : SoundSource.values()) {
         requiredargumentbuilder.then(Commands.m_82127_(soundsource.m_12676_()).executes((p_138807_) -> {
            return m_138800_(p_138807_.getSource(), EntityArgument.m_91477_(p_138807_, "targets"), soundsource, (ResourceLocation)null);
         }).then(Commands.m_82129_("sound", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121643_).executes((p_138793_) -> {
            return m_138800_(p_138793_.getSource(), EntityArgument.m_91477_(p_138793_, "targets"), soundsource, ResourceLocationArgument.m_107011_(p_138793_, "sound"));
         })));
      }

      p_138795_.register(Commands.m_82127_("stopsound").requires((p_138799_) -> {
         return p_138799_.m_6761_(2);
      }).then(requiredargumentbuilder));
   }

   private static int m_138800_(CommandSourceStack p_138801_, Collection<ServerPlayer> p_138802_, @Nullable SoundSource p_138803_, @Nullable ResourceLocation p_138804_) {
      ClientboundStopSoundPacket clientboundstopsoundpacket = new ClientboundStopSoundPacket(p_138804_, p_138803_);

      for(ServerPlayer serverplayer : p_138802_) {
         serverplayer.f_8906_.m_141995_(clientboundstopsoundpacket);
      }

      if (p_138803_ != null) {
         if (p_138804_ != null) {
            p_138801_.m_81354_(new TranslatableComponent("commands.stopsound.success.source.sound", p_138804_, p_138803_.m_12676_()), true);
         } else {
            p_138801_.m_81354_(new TranslatableComponent("commands.stopsound.success.source.any", p_138803_.m_12676_()), true);
         }
      } else if (p_138804_ != null) {
         p_138801_.m_81354_(new TranslatableComponent("commands.stopsound.success.sourceless.sound", p_138804_), true);
      } else {
         p_138801_.m_81354_(new TranslatableComponent("commands.stopsound.success.sourceless.any"), true);
      }

      return p_138802_.size();
   }
}