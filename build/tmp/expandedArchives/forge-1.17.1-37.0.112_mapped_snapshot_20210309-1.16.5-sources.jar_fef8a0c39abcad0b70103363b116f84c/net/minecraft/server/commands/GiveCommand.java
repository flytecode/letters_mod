package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GiveCommand {
   public static final int f_180233_ = 100;

   public static void m_137772_(CommandDispatcher<CommandSourceStack> p_137773_) {
      p_137773_.register(Commands.m_82127_("give").requires((p_137777_) -> {
         return p_137777_.m_6761_(2);
      }).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("item", ItemArgument.m_120960_()).executes((p_137784_) -> {
         return m_137778_(p_137784_.getSource(), ItemArgument.m_120963_(p_137784_, "item"), EntityArgument.m_91477_(p_137784_, "targets"), 1);
      }).then(Commands.m_82129_("count", IntegerArgumentType.integer(1)).executes((p_137775_) -> {
         return m_137778_(p_137775_.getSource(), ItemArgument.m_120963_(p_137775_, "item"), EntityArgument.m_91477_(p_137775_, "targets"), IntegerArgumentType.getInteger(p_137775_, "count"));
      })))));
   }

   private static int m_137778_(CommandSourceStack p_137779_, ItemInput p_137780_, Collection<ServerPlayer> p_137781_, int p_137782_) throws CommandSyntaxException {
      int i = p_137780_.m_120979_().m_41459_();
      int j = i * 100;
      if (p_137782_ > j) {
         p_137779_.m_81352_(new TranslatableComponent("commands.give.failed.toomanyitems", j, p_137780_.m_120980_(p_137782_, false).m_41611_()));
         return 0;
      } else {
         for(ServerPlayer serverplayer : p_137781_) {
            int k = p_137782_;

            while(k > 0) {
               int l = Math.min(i, k);
               k -= l;
               ItemStack itemstack = p_137780_.m_120980_(l, false);
               boolean flag = serverplayer.m_150109_().m_36054_(itemstack);
               if (flag && itemstack.m_41619_()) {
                  itemstack.m_41764_(1);
                  ItemEntity itementity1 = serverplayer.m_36176_(itemstack, false);
                  if (itementity1 != null) {
                     itementity1.m_32065_();
                  }

                  serverplayer.f_19853_.m_6263_((Player)null, serverplayer.m_20185_(), serverplayer.m_20186_(), serverplayer.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2F, ((serverplayer.m_21187_().nextFloat() - serverplayer.m_21187_().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                  serverplayer.f_36096_.m_38946_();
               } else {
                  ItemEntity itementity = serverplayer.m_36176_(itemstack, false);
                  if (itementity != null) {
                     itementity.m_32061_();
                     itementity.m_32047_(serverplayer.m_142081_());
                  }
               }
            }
         }

         if (p_137781_.size() == 1) {
            p_137779_.m_81354_(new TranslatableComponent("commands.give.success.single", p_137782_, p_137780_.m_120980_(p_137782_, false).m_41611_(), p_137781_.iterator().next().m_5446_()), true);
         } else {
            p_137779_.m_81354_(new TranslatableComponent("commands.give.success.single", p_137782_, p_137780_.m_120980_(p_137782_, false).m_41611_(), p_137781_.size()), true);
         }

         return p_137781_.size();
      }
   }
}