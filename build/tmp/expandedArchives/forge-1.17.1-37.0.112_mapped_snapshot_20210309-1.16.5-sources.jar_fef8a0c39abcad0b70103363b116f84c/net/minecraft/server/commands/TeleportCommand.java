package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class TeleportCommand {
   private static final SimpleCommandExceptionType f_139006_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.teleport.invalidPosition"));

   public static void m_139008_(CommandDispatcher<CommandSourceStack> p_139009_) {
      LiteralCommandNode<CommandSourceStack> literalcommandnode = p_139009_.register(Commands.m_82127_("teleport").requires((p_139039_) -> {
         return p_139039_.m_6761_(2);
      }).then(Commands.m_82129_("location", Vec3Argument.m_120841_()).executes((p_139051_) -> {
         return m_139025_(p_139051_.getSource(), Collections.singleton(p_139051_.getSource().m_81374_()), p_139051_.getSource().m_81372_(), Vec3Argument.m_120849_(p_139051_, "location"), WorldCoordinates.m_120898_(), (TeleportCommand.LookAt)null);
      })).then(Commands.m_82129_("destination", EntityArgument.m_91449_()).executes((p_139049_) -> {
         return m_139032_(p_139049_.getSource(), Collections.singleton(p_139049_.getSource().m_81374_()), EntityArgument.m_91452_(p_139049_, "destination"));
      })).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82129_("location", Vec3Argument.m_120841_()).executes((p_139047_) -> {
         return m_139025_(p_139047_.getSource(), EntityArgument.m_91461_(p_139047_, "targets"), p_139047_.getSource().m_81372_(), Vec3Argument.m_120849_(p_139047_, "location"), (Coordinates)null, (TeleportCommand.LookAt)null);
      }).then(Commands.m_82129_("rotation", RotationArgument.m_120479_()).executes((p_139045_) -> {
         return m_139025_(p_139045_.getSource(), EntityArgument.m_91461_(p_139045_, "targets"), p_139045_.getSource().m_81372_(), Vec3Argument.m_120849_(p_139045_, "location"), RotationArgument.m_120482_(p_139045_, "rotation"), (TeleportCommand.LookAt)null);
      })).then(Commands.m_82127_("facing").then(Commands.m_82127_("entity").then(Commands.m_82129_("facingEntity", EntityArgument.m_91449_()).executes((p_139043_) -> {
         return m_139025_(p_139043_.getSource(), EntityArgument.m_91461_(p_139043_, "targets"), p_139043_.getSource().m_81372_(), Vec3Argument.m_120849_(p_139043_, "location"), (Coordinates)null, new TeleportCommand.LookAt(EntityArgument.m_91452_(p_139043_, "facingEntity"), EntityAnchorArgument.Anchor.FEET));
      }).then(Commands.m_82129_("facingAnchor", EntityAnchorArgument.m_90350_()).executes((p_139041_) -> {
         return m_139025_(p_139041_.getSource(), EntityArgument.m_91461_(p_139041_, "targets"), p_139041_.getSource().m_81372_(), Vec3Argument.m_120849_(p_139041_, "location"), (Coordinates)null, new TeleportCommand.LookAt(EntityArgument.m_91452_(p_139041_, "facingEntity"), EntityAnchorArgument.m_90353_(p_139041_, "facingAnchor")));
      })))).then(Commands.m_82129_("facingLocation", Vec3Argument.m_120841_()).executes((p_139037_) -> {
         return m_139025_(p_139037_.getSource(), EntityArgument.m_91461_(p_139037_, "targets"), p_139037_.getSource().m_81372_(), Vec3Argument.m_120849_(p_139037_, "location"), (Coordinates)null, new TeleportCommand.LookAt(Vec3Argument.m_120844_(p_139037_, "facingLocation")));
      })))).then(Commands.m_82129_("destination", EntityArgument.m_91449_()).executes((p_139011_) -> {
         return m_139032_(p_139011_.getSource(), EntityArgument.m_91461_(p_139011_, "targets"), EntityArgument.m_91452_(p_139011_, "destination"));
      }))));
      p_139009_.register(Commands.m_82127_("tp").requires((p_139013_) -> {
         return p_139013_.m_6761_(2);
      }).redirect(literalcommandnode));
   }

   private static int m_139032_(CommandSourceStack p_139033_, Collection<? extends Entity> p_139034_, Entity p_139035_) throws CommandSyntaxException {
      for(Entity entity : p_139034_) {
         m_139014_(p_139033_, entity, (ServerLevel)p_139035_.f_19853_, p_139035_.m_20185_(), p_139035_.m_20186_(), p_139035_.m_20189_(), EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class), p_139035_.m_146908_(), p_139035_.m_146909_(), (TeleportCommand.LookAt)null);
      }

      if (p_139034_.size() == 1) {
         p_139033_.m_81354_(new TranslatableComponent("commands.teleport.success.entity.single", p_139034_.iterator().next().m_5446_(), p_139035_.m_5446_()), true);
      } else {
         p_139033_.m_81354_(new TranslatableComponent("commands.teleport.success.entity.multiple", p_139034_.size(), p_139035_.m_5446_()), true);
      }

      return p_139034_.size();
   }

   private static int m_139025_(CommandSourceStack p_139026_, Collection<? extends Entity> p_139027_, ServerLevel p_139028_, Coordinates p_139029_, @Nullable Coordinates p_139030_, @Nullable TeleportCommand.LookAt p_139031_) throws CommandSyntaxException {
      Vec3 vec3 = p_139029_.m_6955_(p_139026_);
      Vec2 vec2 = p_139030_ == null ? null : p_139030_.m_6970_(p_139026_);
      Set<ClientboundPlayerPositionPacket.RelativeArgument> set = EnumSet.noneOf(ClientboundPlayerPositionPacket.RelativeArgument.class);
      if (p_139029_.m_6888_()) {
         set.add(ClientboundPlayerPositionPacket.RelativeArgument.X);
      }

      if (p_139029_.m_6892_()) {
         set.add(ClientboundPlayerPositionPacket.RelativeArgument.Y);
      }

      if (p_139029_.m_6900_()) {
         set.add(ClientboundPlayerPositionPacket.RelativeArgument.Z);
      }

      if (p_139030_ == null) {
         set.add(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT);
         set.add(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT);
      } else {
         if (p_139030_.m_6888_()) {
            set.add(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT);
         }

         if (p_139030_.m_6892_()) {
            set.add(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT);
         }
      }

      for(Entity entity : p_139027_) {
         if (p_139030_ == null) {
            m_139014_(p_139026_, entity, p_139028_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, set, entity.m_146908_(), entity.m_146909_(), p_139031_);
         } else {
            m_139014_(p_139026_, entity, p_139028_, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, set, vec2.f_82471_, vec2.f_82470_, p_139031_);
         }
      }

      if (p_139027_.size() == 1) {
         p_139026_.m_81354_(new TranslatableComponent("commands.teleport.success.location.single", p_139027_.iterator().next().m_5446_(), m_142775_(vec3.f_82479_), m_142775_(vec3.f_82480_), m_142775_(vec3.f_82481_)), true);
      } else {
         p_139026_.m_81354_(new TranslatableComponent("commands.teleport.success.location.multiple", p_139027_.size(), m_142775_(vec3.f_82479_), m_142775_(vec3.f_82480_), m_142775_(vec3.f_82481_)), true);
      }

      return p_139027_.size();
   }

   private static String m_142775_(double p_142776_) {
      return String.format(Locale.ROOT, "%f", p_142776_);
   }

   private static void m_139014_(CommandSourceStack p_139015_, Entity p_139016_, ServerLevel p_139017_, double p_139018_, double p_139019_, double p_139020_, Set<ClientboundPlayerPositionPacket.RelativeArgument> p_139021_, float p_139022_, float p_139023_, @Nullable TeleportCommand.LookAt p_139024_) throws CommandSyntaxException {
      net.minecraftforge.event.entity.EntityTeleportEvent.TeleportCommand event = net.minecraftforge.event.ForgeEventFactory.onEntityTeleportCommand(p_139016_, p_139018_, p_139019_, p_139020_);
      if (event.isCanceled()) return;
      p_139018_ = event.getTargetX(); p_139019_ = event.getTargetY(); p_139020_ = event.getTargetZ();
      BlockPos blockpos = new BlockPos(p_139018_, p_139019_, p_139020_);
      if (!Level.m_46741_(blockpos)) {
         throw f_139006_.create();
      } else {
         float f = Mth.m_14177_(p_139022_);
         float f1 = Mth.m_14177_(p_139023_);
         if (p_139016_ instanceof ServerPlayer) {
            ChunkPos chunkpos = new ChunkPos(new BlockPos(p_139018_, p_139019_, p_139020_));
            p_139017_.m_7726_().m_8387_(TicketType.f_9448_, chunkpos, 1, p_139016_.m_142049_());
            p_139016_.m_8127_();
            if (((ServerPlayer)p_139016_).m_5803_()) {
               ((ServerPlayer)p_139016_).m_6145_(true, true);
            }

            if (p_139017_ == p_139016_.f_19853_) {
               ((ServerPlayer)p_139016_).f_8906_.m_9780_(p_139018_, p_139019_, p_139020_, f, f1, p_139021_);
            } else {
               ((ServerPlayer)p_139016_).m_8999_(p_139017_, p_139018_, p_139019_, p_139020_, f, f1);
            }

            p_139016_.m_5616_(f);
         } else {
            float f2 = Mth.m_14036_(f1, -90.0F, 90.0F);
            if (p_139017_ == p_139016_.f_19853_) {
               p_139016_.m_7678_(p_139018_, p_139019_, p_139020_, f, f2);
               p_139016_.m_5616_(f);
            } else {
               p_139016_.m_19877_();
               Entity entity = p_139016_;
               p_139016_ = p_139016_.m_6095_().m_20615_(p_139017_);
               if (p_139016_ == null) {
                  return;
               }

               p_139016_.m_20361_(entity);
               p_139016_.m_7678_(p_139018_, p_139019_, p_139020_, f, f2);
               p_139016_.m_5616_(f);
               entity.m_142467_(Entity.RemovalReason.CHANGED_DIMENSION);
               p_139017_.m_143334_(p_139016_);
            }
         }

         if (p_139024_ != null) {
            p_139024_.m_139060_(p_139015_, p_139016_);
         }

         if (!(p_139016_ instanceof LivingEntity) || !((LivingEntity)p_139016_).m_21255_()) {
            p_139016_.m_20256_(p_139016_.m_20184_().m_82542_(1.0D, 0.0D, 1.0D));
            p_139016_.m_6853_(true);
         }

         if (p_139016_ instanceof PathfinderMob) {
            ((PathfinderMob)p_139016_).m_21573_().m_26573_();
         }

      }
   }

   static class LookAt {
      private final Vec3 f_139052_;
      private final Entity f_139053_;
      private final EntityAnchorArgument.Anchor f_139054_;

      public LookAt(Entity p_139056_, EntityAnchorArgument.Anchor p_139057_) {
         this.f_139053_ = p_139056_;
         this.f_139054_ = p_139057_;
         this.f_139052_ = p_139057_.m_90377_(p_139056_);
      }

      public LookAt(Vec3 p_139059_) {
         this.f_139053_ = null;
         this.f_139052_ = p_139059_;
         this.f_139054_ = null;
      }

      public void m_139060_(CommandSourceStack p_139061_, Entity p_139062_) {
         if (this.f_139053_ != null) {
            if (p_139062_ instanceof ServerPlayer) {
               ((ServerPlayer)p_139062_).m_9107_(p_139061_.m_81378_(), this.f_139053_, this.f_139054_);
            } else {
               p_139062_.m_7618_(p_139061_.m_81378_(), this.f_139052_);
            }
         } else {
            p_139062_.m_7618_(p_139061_.m_81378_(), this.f_139052_);
         }

      }
   }
}
