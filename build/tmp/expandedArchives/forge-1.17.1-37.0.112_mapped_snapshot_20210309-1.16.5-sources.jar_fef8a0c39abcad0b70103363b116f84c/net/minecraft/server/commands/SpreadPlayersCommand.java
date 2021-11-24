package net.minecraft.server.commands;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.scores.Team;

public class SpreadPlayersCommand {
   private static final int f_180523_ = 10000;
   private static final Dynamic4CommandExceptionType f_138693_ = new Dynamic4CommandExceptionType((p_138745_, p_138746_, p_138747_, p_138748_) -> {
      return new TranslatableComponent("commands.spreadplayers.failed.teams", p_138745_, p_138746_, p_138747_, p_138748_);
   });
   private static final Dynamic4CommandExceptionType f_138694_ = new Dynamic4CommandExceptionType((p_138723_, p_138724_, p_138725_, p_138726_) -> {
      return new TranslatableComponent("commands.spreadplayers.failed.entities", p_138723_, p_138724_, p_138725_, p_138726_);
   });

   public static void m_138696_(CommandDispatcher<CommandSourceStack> p_138697_) {
      p_138697_.register(Commands.m_82127_("spreadplayers").requires((p_138701_) -> {
         return p_138701_.m_6761_(2);
      }).then(Commands.m_82129_("center", Vec2Argument.m_120822_()).then(Commands.m_82129_("spreadDistance", FloatArgumentType.floatArg(0.0F)).then(Commands.m_82129_("maxRange", FloatArgumentType.floatArg(1.0F)).then(Commands.m_82129_("respectTeams", BoolArgumentType.bool()).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).executes((p_138743_) -> {
         return m_138702_(p_138743_.getSource(), Vec2Argument.m_120825_(p_138743_, "center"), FloatArgumentType.getFloat(p_138743_, "spreadDistance"), FloatArgumentType.getFloat(p_138743_, "maxRange"), p_138743_.getSource().m_81372_().m_151558_(), BoolArgumentType.getBool(p_138743_, "respectTeams"), EntityArgument.m_91461_(p_138743_, "targets"));
      }))).then(Commands.m_82127_("under").then(Commands.m_82129_("maxHeight", IntegerArgumentType.integer(0)).then(Commands.m_82129_("respectTeams", BoolArgumentType.bool()).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).executes((p_138699_) -> {
         return m_138702_(p_138699_.getSource(), Vec2Argument.m_120825_(p_138699_, "center"), FloatArgumentType.getFloat(p_138699_, "spreadDistance"), FloatArgumentType.getFloat(p_138699_, "maxRange"), IntegerArgumentType.getInteger(p_138699_, "maxHeight"), BoolArgumentType.getBool(p_138699_, "respectTeams"), EntityArgument.m_91461_(p_138699_, "targets"));
      })))))))));
   }

   private static int m_138702_(CommandSourceStack p_138703_, Vec2 p_138704_, float p_138705_, float p_138706_, int p_138707_, boolean p_138708_, Collection<? extends Entity> p_138709_) throws CommandSyntaxException {
      Random random = new Random();
      double d0 = (double)(p_138704_.f_82470_ - p_138706_);
      double d1 = (double)(p_138704_.f_82471_ - p_138706_);
      double d2 = (double)(p_138704_.f_82470_ + p_138706_);
      double d3 = (double)(p_138704_.f_82471_ + p_138706_);
      SpreadPlayersCommand.Position[] aspreadplayerscommand$position = m_138735_(random, p_138708_ ? m_138727_(p_138709_) : p_138709_.size(), d0, d1, d2, d3);
      m_138710_(p_138704_, (double)p_138705_, p_138703_.m_81372_(), random, d0, d1, d2, d3, p_138707_, aspreadplayerscommand$position, p_138708_);
      double d4 = m_138729_(p_138709_, p_138703_.m_81372_(), aspreadplayerscommand$position, p_138707_, p_138708_);
      p_138703_.m_81354_(new TranslatableComponent("commands.spreadplayers.success." + (p_138708_ ? "teams" : "entities"), aspreadplayerscommand$position.length, p_138704_.f_82470_, p_138704_.f_82471_, String.format(Locale.ROOT, "%.2f", d4)), true);
      return aspreadplayerscommand$position.length;
   }

   private static int m_138727_(Collection<? extends Entity> p_138728_) {
      Set<Team> set = Sets.newHashSet();

      for(Entity entity : p_138728_) {
         if (entity instanceof Player) {
            set.add(entity.m_5647_());
         } else {
            set.add((Team)null);
         }
      }

      return set.size();
   }

   private static void m_138710_(Vec2 p_138711_, double p_138712_, ServerLevel p_138713_, Random p_138714_, double p_138715_, double p_138716_, double p_138717_, double p_138718_, int p_138719_, SpreadPlayersCommand.Position[] p_138720_, boolean p_138721_) throws CommandSyntaxException {
      boolean flag = true;
      double d0 = (double)Float.MAX_VALUE;

      int i;
      for(i = 0; i < 10000 && flag; ++i) {
         flag = false;
         d0 = (double)Float.MAX_VALUE;

         for(int j = 0; j < p_138720_.length; ++j) {
            SpreadPlayersCommand.Position spreadplayerscommand$position = p_138720_[j];
            int k = 0;
            SpreadPlayersCommand.Position spreadplayerscommand$position1 = new SpreadPlayersCommand.Position();

            for(int l = 0; l < p_138720_.length; ++l) {
               if (j != l) {
                  SpreadPlayersCommand.Position spreadplayerscommand$position2 = p_138720_[l];
                  double d1 = spreadplayerscommand$position.m_138767_(spreadplayerscommand$position2);
                  d0 = Math.min(d1, d0);
                  if (d1 < p_138712_) {
                     ++k;
                     spreadplayerscommand$position1.f_138749_ += spreadplayerscommand$position2.f_138749_ - spreadplayerscommand$position.f_138749_;
                     spreadplayerscommand$position1.f_138750_ += spreadplayerscommand$position2.f_138750_ - spreadplayerscommand$position.f_138750_;
                  }
               }
            }

            if (k > 0) {
               spreadplayerscommand$position1.f_138749_ /= (double)k;
               spreadplayerscommand$position1.f_138750_ /= (double)k;
               double d2 = spreadplayerscommand$position1.m_180525_();
               if (d2 > 0.0D) {
                  spreadplayerscommand$position1.m_138752_();
                  spreadplayerscommand$position.m_138776_(spreadplayerscommand$position1);
               } else {
                  spreadplayerscommand$position.m_138761_(p_138714_, p_138715_, p_138716_, p_138717_, p_138718_);
               }

               flag = true;
            }

            if (spreadplayerscommand$position.m_138753_(p_138715_, p_138716_, p_138717_, p_138718_)) {
               flag = true;
            }
         }

         if (!flag) {
            for(SpreadPlayersCommand.Position spreadplayerscommand$position3 : p_138720_) {
               if (!spreadplayerscommand$position3.m_138773_(p_138713_, p_138719_)) {
                  spreadplayerscommand$position3.m_138761_(p_138714_, p_138715_, p_138716_, p_138717_, p_138718_);
                  flag = true;
               }
            }
         }
      }

      if (d0 == (double)Float.MAX_VALUE) {
         d0 = 0.0D;
      }

      if (i >= 10000) {
         if (p_138721_) {
            throw f_138693_.create(p_138720_.length, p_138711_.f_82470_, p_138711_.f_82471_, String.format(Locale.ROOT, "%.2f", d0));
         } else {
            throw f_138694_.create(p_138720_.length, p_138711_.f_82470_, p_138711_.f_82471_, String.format(Locale.ROOT, "%.2f", d0));
         }
      }
   }

   private static double m_138729_(Collection<? extends Entity> p_138730_, ServerLevel p_138731_, SpreadPlayersCommand.Position[] p_138732_, int p_138733_, boolean p_138734_) {
      double d0 = 0.0D;
      int i = 0;
      Map<Team, SpreadPlayersCommand.Position> map = Maps.newHashMap();

      for(Entity entity : p_138730_) {
         SpreadPlayersCommand.Position spreadplayerscommand$position;
         if (p_138734_) {
            Team team = entity instanceof Player ? entity.m_5647_() : null;
            if (!map.containsKey(team)) {
               map.put(team, p_138732_[i++]);
            }

            spreadplayerscommand$position = map.get(team);
         } else {
            spreadplayerscommand$position = p_138732_[i++];
         }

         net.minecraftforge.event.entity.EntityTeleportEvent.SpreadPlayersCommand event = net.minecraftforge.event.ForgeEventFactory.onEntityTeleportSpreadPlayersCommand(entity, (double)Mth.m_14107_(spreadplayerscommand$position.f_138749_) + 0.5D, (double)spreadplayerscommand$position.m_138758_(p_138731_, p_138733_), (double)Mth.m_14107_(spreadplayerscommand$position.f_138750_) + 0.5D);
         if (!event.isCanceled()) entity.m_20324_(event.getTargetX(), event.getTargetY(), event.getTargetZ());
         double d2 = Double.MAX_VALUE;

         for(SpreadPlayersCommand.Position spreadplayerscommand$position1 : p_138732_) {
            if (spreadplayerscommand$position != spreadplayerscommand$position1) {
               double d1 = spreadplayerscommand$position.m_138767_(spreadplayerscommand$position1);
               d2 = Math.min(d1, d2);
            }
         }

         d0 += d2;
      }

      return p_138730_.size() < 2 ? 0.0D : d0 / (double)p_138730_.size();
   }

   private static SpreadPlayersCommand.Position[] m_138735_(Random p_138736_, int p_138737_, double p_138738_, double p_138739_, double p_138740_, double p_138741_) {
      SpreadPlayersCommand.Position[] aspreadplayerscommand$position = new SpreadPlayersCommand.Position[p_138737_];

      for(int i = 0; i < aspreadplayerscommand$position.length; ++i) {
         SpreadPlayersCommand.Position spreadplayerscommand$position = new SpreadPlayersCommand.Position();
         spreadplayerscommand$position.m_138761_(p_138736_, p_138738_, p_138739_, p_138740_, p_138741_);
         aspreadplayerscommand$position[i] = spreadplayerscommand$position;
      }

      return aspreadplayerscommand$position;
   }

   static class Position {
      double f_138749_;
      double f_138750_;

      double m_138767_(SpreadPlayersCommand.Position p_138768_) {
         double d0 = this.f_138749_ - p_138768_.f_138749_;
         double d1 = this.f_138750_ - p_138768_.f_138750_;
         return Math.sqrt(d0 * d0 + d1 * d1);
      }

      void m_138752_() {
         double d0 = this.m_180525_();
         this.f_138749_ /= d0;
         this.f_138750_ /= d0;
      }

      double m_180525_() {
         return Math.sqrt(this.f_138749_ * this.f_138749_ + this.f_138750_ * this.f_138750_);
      }

      public void m_138776_(SpreadPlayersCommand.Position p_138777_) {
         this.f_138749_ -= p_138777_.f_138749_;
         this.f_138750_ -= p_138777_.f_138750_;
      }

      public boolean m_138753_(double p_138754_, double p_138755_, double p_138756_, double p_138757_) {
         boolean flag = false;
         if (this.f_138749_ < p_138754_) {
            this.f_138749_ = p_138754_;
            flag = true;
         } else if (this.f_138749_ > p_138756_) {
            this.f_138749_ = p_138756_;
            flag = true;
         }

         if (this.f_138750_ < p_138755_) {
            this.f_138750_ = p_138755_;
            flag = true;
         } else if (this.f_138750_ > p_138757_) {
            this.f_138750_ = p_138757_;
            flag = true;
         }

         return flag;
      }

      public int m_138758_(BlockGetter p_138759_, int p_138760_) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(this.f_138749_, (double)(p_138760_ + 1), this.f_138750_);
         boolean flag = p_138759_.m_8055_(blockpos$mutableblockpos).m_60795_();
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);

         boolean flag2;
         for(boolean flag1 = p_138759_.m_8055_(blockpos$mutableblockpos).m_60795_(); blockpos$mutableblockpos.m_123342_() > p_138759_.m_141937_(); flag1 = flag2) {
            blockpos$mutableblockpos.m_122173_(Direction.DOWN);
            flag2 = p_138759_.m_8055_(blockpos$mutableblockpos).m_60795_();
            if (!flag2 && flag1 && flag) {
               return blockpos$mutableblockpos.m_123342_() + 1;
            }

            flag = flag1;
         }

         return p_138760_ + 1;
      }

      public boolean m_138773_(BlockGetter p_138774_, int p_138775_) {
         BlockPos blockpos = new BlockPos(this.f_138749_, (double)(this.m_138758_(p_138774_, p_138775_) - 1), this.f_138750_);
         BlockState blockstate = p_138774_.m_8055_(blockpos);
         Material material = blockstate.m_60767_();
         return blockpos.m_123342_() < p_138775_ && !material.m_76332_() && material != Material.f_76309_;
      }

      public void m_138761_(Random p_138762_, double p_138763_, double p_138764_, double p_138765_, double p_138766_) {
         this.f_138749_ = Mth.m_14064_(p_138762_, p_138763_, p_138765_);
         this.f_138750_ = Mth.m_14064_(p_138762_, p_138764_, p_138766_);
      }
   }
}
