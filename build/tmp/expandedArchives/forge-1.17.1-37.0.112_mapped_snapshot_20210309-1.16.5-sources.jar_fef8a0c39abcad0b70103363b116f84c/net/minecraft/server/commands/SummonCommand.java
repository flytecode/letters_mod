package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SummonCommand {
   private static final SimpleCommandExceptionType f_138810_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.summon.failed"));
   private static final SimpleCommandExceptionType f_138811_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.summon.failed.uuid"));
   private static final SimpleCommandExceptionType f_138812_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.summon.invalidPosition"));

   public static void m_138814_(CommandDispatcher<CommandSourceStack> p_138815_) {
      p_138815_.register(Commands.m_82127_("summon").requires((p_138819_) -> {
         return p_138819_.m_6761_(2);
      }).then(Commands.m_82129_("entity", EntitySummonArgument.m_93335_()).suggests(SuggestionProviders.f_121645_).executes((p_138832_) -> {
         return m_138820_(p_138832_.getSource(), EntitySummonArgument.m_93338_(p_138832_, "entity"), p_138832_.getSource().m_81371_(), new CompoundTag(), true);
      }).then(Commands.m_82129_("pos", Vec3Argument.m_120841_()).executes((p_138830_) -> {
         return m_138820_(p_138830_.getSource(), EntitySummonArgument.m_93338_(p_138830_, "entity"), Vec3Argument.m_120844_(p_138830_, "pos"), new CompoundTag(), true);
      }).then(Commands.m_82129_("nbt", CompoundTagArgument.m_87657_()).executes((p_138817_) -> {
         return m_138820_(p_138817_.getSource(), EntitySummonArgument.m_93338_(p_138817_, "entity"), Vec3Argument.m_120844_(p_138817_, "pos"), CompoundTagArgument.m_87660_(p_138817_, "nbt"), false);
      })))));
   }

   private static int m_138820_(CommandSourceStack p_138821_, ResourceLocation p_138822_, Vec3 p_138823_, CompoundTag p_138824_, boolean p_138825_) throws CommandSyntaxException {
      BlockPos blockpos = new BlockPos(p_138823_);
      if (!Level.m_46741_(blockpos)) {
         throw f_138812_.create();
      } else {
         CompoundTag compoundtag = p_138824_.m_6426_();
         compoundtag.m_128359_("id", p_138822_.toString());
         ServerLevel serverlevel = p_138821_.m_81372_();
         Entity entity = EntityType.m_20645_(compoundtag, serverlevel, (p_138828_) -> {
            p_138828_.m_7678_(p_138823_.f_82479_, p_138823_.f_82480_, p_138823_.f_82481_, p_138828_.m_146908_(), p_138828_.m_146909_());
            return p_138828_;
         });
         if (entity == null) {
            throw f_138810_.create();
         } else {
            if (p_138825_ && entity instanceof Mob) {
               ((Mob)entity).m_6518_(p_138821_.m_81372_(), p_138821_.m_81372_().m_6436_(entity.m_142538_()), MobSpawnType.COMMAND, (SpawnGroupData)null, (CompoundTag)null);
            }

            if (!serverlevel.m_8860_(entity)) {
               throw f_138811_.create();
            } else {
               p_138821_.m_81354_(new TranslatableComponent("commands.summon.success", entity.m_5446_()), true);
               return 1;
            }
         }
      }
   }
}