package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raids;

public class RaidCommand {
   public static void m_180468_(CommandDispatcher<CommandSourceStack> p_180469_) {
      p_180469_.register(Commands.m_82127_("raid").requires((p_180498_) -> {
         return p_180498_.m_6761_(3);
      }).then(Commands.m_82127_("start").then(Commands.m_82129_("omenlvl", IntegerArgumentType.integer(0)).executes((p_180502_) -> {
         return m_180484_(p_180502_.getSource(), IntegerArgumentType.getInteger(p_180502_, "omenlvl"));
      }))).then(Commands.m_82127_("stop").executes((p_180500_) -> {
         return m_180489_(p_180500_.getSource());
      })).then(Commands.m_82127_("check").executes((p_180496_) -> {
         return m_180493_(p_180496_.getSource());
      })).then(Commands.m_82127_("sound").then(Commands.m_82129_("type", ComponentArgument.m_87114_()).executes((p_180492_) -> {
         return m_180477_(p_180492_.getSource(), ComponentArgument.m_87117_(p_180492_, "type"));
      }))).then(Commands.m_82127_("spawnleader").executes((p_180488_) -> {
         return m_180482_(p_180488_.getSource());
      })).then(Commands.m_82127_("setomen").then(Commands.m_82129_("level", IntegerArgumentType.integer(0)).executes((p_180481_) -> {
         return m_180474_(p_180481_.getSource(), IntegerArgumentType.getInteger(p_180481_, "level"));
      }))).then(Commands.m_82127_("glow").executes((p_180471_) -> {
         return m_180472_(p_180471_.getSource());
      })));
   }

   private static int m_180472_(CommandSourceStack p_180473_) throws CommandSyntaxException {
      Raid raid = m_180466_(p_180473_.m_81375_());
      if (raid != null) {
         for(Raider raider : raid.m_150221_()) {
            raider.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 1000, 1));
         }
      }

      return 1;
   }

   private static int m_180474_(CommandSourceStack p_180475_, int p_180476_) throws CommandSyntaxException {
      Raid raid = m_180466_(p_180475_.m_81375_());
      if (raid != null) {
         int i = raid.m_37772_();
         if (p_180476_ > i) {
            p_180475_.m_81352_(new TextComponent("Sorry, the max bad omen level you can set is " + i));
         } else {
            int j = raid.m_37773_();
            raid.m_150218_(p_180476_);
            p_180475_.m_81354_(new TextComponent("Changed village's bad omen level from " + j + " to " + p_180476_), false);
         }
      } else {
         p_180475_.m_81352_(new TextComponent("No raid found here"));
      }

      return 1;
   }

   private static int m_180482_(CommandSourceStack p_180483_) {
      p_180483_.m_81354_(new TextComponent("Spawned a raid captain"), false);
      Raider raider = EntityType.f_20513_.m_20615_(p_180483_.m_81372_());
      raider.m_33075_(true);
      raider.m_8061_(EquipmentSlot.HEAD, Raid.m_37779_());
      raider.m_6034_(p_180483_.m_81371_().f_82479_, p_180483_.m_81371_().f_82480_, p_180483_.m_81371_().f_82481_);
      raider.m_6518_(p_180483_.m_81372_(), p_180483_.m_81372_().m_6436_(new BlockPos(p_180483_.m_81371_())), MobSpawnType.COMMAND, (SpawnGroupData)null, (CompoundTag)null);
      p_180483_.m_81372_().m_47205_(raider);
      return 1;
   }

   private static int m_180477_(CommandSourceStack p_180478_, Component p_180479_) {
      if (p_180479_ != null && p_180479_.getString().equals("local")) {
         p_180478_.m_81372_().m_5594_((Player)null, new BlockPos(p_180478_.m_81371_().m_82520_(5.0D, 0.0D, 0.0D)), SoundEvents.f_12355_, SoundSource.NEUTRAL, 2.0F, 1.0F);
      }

      return 1;
   }

   private static int m_180484_(CommandSourceStack p_180485_, int p_180486_) throws CommandSyntaxException {
      ServerPlayer serverplayer = p_180485_.m_81375_();
      BlockPos blockpos = serverplayer.m_142538_();
      if (serverplayer.m_9236_().m_8843_(blockpos)) {
         p_180485_.m_81352_(new TextComponent("Raid already started close by"));
         return -1;
      } else {
         Raids raids = serverplayer.m_9236_().m_8905_();
         Raid raid = raids.m_37963_(serverplayer);
         if (raid != null) {
            raid.m_150218_(p_180486_);
            raids.m_77762_();
            p_180485_.m_81354_(new TextComponent("Created a raid in your local village"), false);
         } else {
            p_180485_.m_81352_(new TextComponent("Failed to create a raid in your local village"));
         }

         return 1;
      }
   }

   private static int m_180489_(CommandSourceStack p_180490_) throws CommandSyntaxException {
      ServerPlayer serverplayer = p_180490_.m_81375_();
      BlockPos blockpos = serverplayer.m_142538_();
      Raid raid = serverplayer.m_9236_().m_8832_(blockpos);
      if (raid != null) {
         raid.m_37774_();
         p_180490_.m_81354_(new TextComponent("Stopped raid"), false);
         return 1;
      } else {
         p_180490_.m_81352_(new TextComponent("No raid here"));
         return -1;
      }
   }

   private static int m_180493_(CommandSourceStack p_180494_) throws CommandSyntaxException {
      Raid raid = m_180466_(p_180494_.m_81375_());
      if (raid != null) {
         StringBuilder stringbuilder = new StringBuilder();
         stringbuilder.append("Found a started raid! ");
         p_180494_.m_81354_(new TextComponent(stringbuilder.toString()), false);
         stringbuilder = new StringBuilder();
         stringbuilder.append("Num groups spawned: ");
         stringbuilder.append(raid.m_37771_());
         stringbuilder.append(" Bad omen level: ");
         stringbuilder.append(raid.m_37773_());
         stringbuilder.append(" Num mobs: ");
         stringbuilder.append(raid.m_37778_());
         stringbuilder.append(" Raid health: ");
         stringbuilder.append(raid.m_37777_());
         stringbuilder.append(" / ");
         stringbuilder.append(raid.m_150220_());
         p_180494_.m_81354_(new TextComponent(stringbuilder.toString()), false);
         return 1;
      } else {
         p_180494_.m_81352_(new TextComponent("Found no started raids"));
         return 0;
      }
   }

   @Nullable
   private static Raid m_180466_(ServerPlayer p_180467_) {
      return p_180467_.m_9236_().m_8832_(p_180467_.m_142538_());
   }
}