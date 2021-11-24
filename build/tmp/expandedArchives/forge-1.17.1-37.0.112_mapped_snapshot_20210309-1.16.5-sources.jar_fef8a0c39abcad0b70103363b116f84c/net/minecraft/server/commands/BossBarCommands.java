package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class BossBarCommands {
   private static final DynamicCommandExceptionType f_136571_ = new DynamicCommandExceptionType((p_136636_) -> {
      return new TranslatableComponent("commands.bossbar.create.failed", p_136636_);
   });
   private static final DynamicCommandExceptionType f_136572_ = new DynamicCommandExceptionType((p_136623_) -> {
      return new TranslatableComponent("commands.bossbar.unknown", p_136623_);
   });
   private static final SimpleCommandExceptionType f_136573_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.players.unchanged"));
   private static final SimpleCommandExceptionType f_136574_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.name.unchanged"));
   private static final SimpleCommandExceptionType f_136575_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.color.unchanged"));
   private static final SimpleCommandExceptionType f_136576_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.style.unchanged"));
   private static final SimpleCommandExceptionType f_136577_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.value.unchanged"));
   private static final SimpleCommandExceptionType f_136578_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.max.unchanged"));
   private static final SimpleCommandExceptionType f_136579_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.visibility.unchanged.hidden"));
   private static final SimpleCommandExceptionType f_136580_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.bossbar.set.visibility.unchanged.visible"));
   public static final SuggestionProvider<CommandSourceStack> f_136570_ = (p_136587_, p_136588_) -> {
      return SharedSuggestionProvider.m_82926_(p_136587_.getSource().m_81377_().m_129901_().m_136292_(), p_136588_);
   };

   public static void m_136582_(CommandDispatcher<CommandSourceStack> p_136583_) {
      p_136583_.register(Commands.m_82127_("bossbar").requires((p_136627_) -> {
         return p_136627_.m_6761_(2);
      }).then(Commands.m_82127_("add").then(Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).then(Commands.m_82129_("name", ComponentArgument.m_87114_()).executes((p_136693_) -> {
         return m_136591_(p_136693_.getSource(), ResourceLocationArgument.m_107011_(p_136693_, "id"), ComponentArgument.m_87117_(p_136693_, "name"));
      })))).then(Commands.m_82127_("remove").then(Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(f_136570_).executes((p_136691_) -> {
         return m_136649_(p_136691_.getSource(), m_136584_(p_136691_));
      }))).then(Commands.m_82127_("list").executes((p_136689_) -> {
         return m_136589_(p_136689_.getSource());
      })).then(Commands.m_82127_("set").then(Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(f_136570_).then(Commands.m_82127_("name").then(Commands.m_82129_("name", ComponentArgument.m_87114_()).executes((p_136687_) -> {
         return m_136614_(p_136687_.getSource(), m_136584_(p_136687_), ComponentArgument.m_87117_(p_136687_, "name"));
      }))).then(Commands.m_82127_("color").then(Commands.m_82127_("pink").executes((p_136685_) -> {
         return m_136602_(p_136685_.getSource(), m_136584_(p_136685_), BossEvent.BossBarColor.PINK);
      })).then(Commands.m_82127_("blue").executes((p_136683_) -> {
         return m_136602_(p_136683_.getSource(), m_136584_(p_136683_), BossEvent.BossBarColor.BLUE);
      })).then(Commands.m_82127_("red").executes((p_136681_) -> {
         return m_136602_(p_136681_.getSource(), m_136584_(p_136681_), BossEvent.BossBarColor.RED);
      })).then(Commands.m_82127_("green").executes((p_136679_) -> {
         return m_136602_(p_136679_.getSource(), m_136584_(p_136679_), BossEvent.BossBarColor.GREEN);
      })).then(Commands.m_82127_("yellow").executes((p_136677_) -> {
         return m_136602_(p_136677_.getSource(), m_136584_(p_136677_), BossEvent.BossBarColor.YELLOW);
      })).then(Commands.m_82127_("purple").executes((p_136675_) -> {
         return m_136602_(p_136675_.getSource(), m_136584_(p_136675_), BossEvent.BossBarColor.PURPLE);
      })).then(Commands.m_82127_("white").executes((p_136673_) -> {
         return m_136602_(p_136673_.getSource(), m_136584_(p_136673_), BossEvent.BossBarColor.WHITE);
      }))).then(Commands.m_82127_("style").then(Commands.m_82127_("progress").executes((p_136671_) -> {
         return m_136606_(p_136671_.getSource(), m_136584_(p_136671_), BossEvent.BossBarOverlay.PROGRESS);
      })).then(Commands.m_82127_("notched_6").executes((p_136669_) -> {
         return m_136606_(p_136669_.getSource(), m_136584_(p_136669_), BossEvent.BossBarOverlay.NOTCHED_6);
      })).then(Commands.m_82127_("notched_10").executes((p_136667_) -> {
         return m_136606_(p_136667_.getSource(), m_136584_(p_136667_), BossEvent.BossBarOverlay.NOTCHED_10);
      })).then(Commands.m_82127_("notched_12").executes((p_136665_) -> {
         return m_136606_(p_136665_.getSource(), m_136584_(p_136665_), BossEvent.BossBarOverlay.NOTCHED_12);
      })).then(Commands.m_82127_("notched_20").executes((p_136663_) -> {
         return m_136606_(p_136663_.getSource(), m_136584_(p_136663_), BossEvent.BossBarOverlay.NOTCHED_20);
      }))).then(Commands.m_82127_("value").then(Commands.m_82129_("value", IntegerArgumentType.integer(0)).executes((p_136661_) -> {
         return m_136598_(p_136661_.getSource(), m_136584_(p_136661_), IntegerArgumentType.getInteger(p_136661_, "value"));
      }))).then(Commands.m_82127_("max").then(Commands.m_82129_("max", IntegerArgumentType.integer(1)).executes((p_136659_) -> {
         return m_136631_(p_136659_.getSource(), m_136584_(p_136659_), IntegerArgumentType.getInteger(p_136659_, "max"));
      }))).then(Commands.m_82127_("visible").then(Commands.m_82129_("visible", BoolArgumentType.bool()).executes((p_136657_) -> {
         return m_136618_(p_136657_.getSource(), m_136584_(p_136657_), BoolArgumentType.getBool(p_136657_, "visible"));
      }))).then(Commands.m_82127_("players").executes((p_136655_) -> {
         return m_136610_(p_136655_.getSource(), m_136584_(p_136655_), Collections.emptyList());
      }).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((p_136653_) -> {
         return m_136610_(p_136653_.getSource(), m_136584_(p_136653_), EntityArgument.m_91471_(p_136653_, "targets"));
      }))))).then(Commands.m_82127_("get").then(Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(f_136570_).then(Commands.m_82127_("value").executes((p_136648_) -> {
         return m_136595_(p_136648_.getSource(), m_136584_(p_136648_));
      })).then(Commands.m_82127_("max").executes((p_136643_) -> {
         return m_136628_(p_136643_.getSource(), m_136584_(p_136643_));
      })).then(Commands.m_82127_("visible").executes((p_136638_) -> {
         return m_136639_(p_136638_.getSource(), m_136584_(p_136638_));
      })).then(Commands.m_82127_("players").executes((p_136625_) -> {
         return m_136644_(p_136625_.getSource(), m_136584_(p_136625_));
      })))));
   }

   private static int m_136595_(CommandSourceStack p_136596_, CustomBossEvent p_136597_) {
      p_136596_.m_81354_(new TranslatableComponent("commands.bossbar.get.value", p_136597_.m_136288_(), p_136597_.m_136282_()), true);
      return p_136597_.m_136282_();
   }

   private static int m_136628_(CommandSourceStack p_136629_, CustomBossEvent p_136630_) {
      p_136629_.m_81354_(new TranslatableComponent("commands.bossbar.get.max", p_136630_.m_136288_(), p_136630_.m_136285_()), true);
      return p_136630_.m_136285_();
   }

   private static int m_136639_(CommandSourceStack p_136640_, CustomBossEvent p_136641_) {
      if (p_136641_.m_8323_()) {
         p_136640_.m_81354_(new TranslatableComponent("commands.bossbar.get.visible.visible", p_136641_.m_136288_()), true);
         return 1;
      } else {
         p_136640_.m_81354_(new TranslatableComponent("commands.bossbar.get.visible.hidden", p_136641_.m_136288_()), true);
         return 0;
      }
   }

   private static int m_136644_(CommandSourceStack p_136645_, CustomBossEvent p_136646_) {
      if (p_136646_.m_8324_().isEmpty()) {
         p_136645_.m_81354_(new TranslatableComponent("commands.bossbar.get.players.none", p_136646_.m_136288_()), true);
      } else {
         p_136645_.m_81354_(new TranslatableComponent("commands.bossbar.get.players.some", p_136646_.m_136288_(), p_136646_.m_8324_().size(), ComponentUtils.m_178440_(p_136646_.m_8324_(), Player::m_5446_)), true);
      }

      return p_136646_.m_8324_().size();
   }

   private static int m_136618_(CommandSourceStack p_136619_, CustomBossEvent p_136620_, boolean p_136621_) throws CommandSyntaxException {
      if (p_136620_.m_8323_() == p_136621_) {
         if (p_136621_) {
            throw f_136580_.create();
         } else {
            throw f_136579_.create();
         }
      } else {
         p_136620_.m_8321_(p_136621_);
         if (p_136621_) {
            p_136619_.m_81354_(new TranslatableComponent("commands.bossbar.set.visible.success.visible", p_136620_.m_136288_()), true);
         } else {
            p_136619_.m_81354_(new TranslatableComponent("commands.bossbar.set.visible.success.hidden", p_136620_.m_136288_()), true);
         }

         return 0;
      }
   }

   private static int m_136598_(CommandSourceStack p_136599_, CustomBossEvent p_136600_, int p_136601_) throws CommandSyntaxException {
      if (p_136600_.m_136282_() == p_136601_) {
         throw f_136577_.create();
      } else {
         p_136600_.m_136264_(p_136601_);
         p_136599_.m_81354_(new TranslatableComponent("commands.bossbar.set.value.success", p_136600_.m_136288_(), p_136601_), true);
         return p_136601_;
      }
   }

   private static int m_136631_(CommandSourceStack p_136632_, CustomBossEvent p_136633_, int p_136634_) throws CommandSyntaxException {
      if (p_136633_.m_136285_() == p_136634_) {
         throw f_136578_.create();
      } else {
         p_136633_.m_136278_(p_136634_);
         p_136632_.m_81354_(new TranslatableComponent("commands.bossbar.set.max.success", p_136633_.m_136288_(), p_136634_), true);
         return p_136634_;
      }
   }

   private static int m_136602_(CommandSourceStack p_136603_, CustomBossEvent p_136604_, BossEvent.BossBarColor p_136605_) throws CommandSyntaxException {
      if (p_136604_.m_18862_().equals(p_136605_)) {
         throw f_136575_.create();
      } else {
         p_136604_.m_6451_(p_136605_);
         p_136603_.m_81354_(new TranslatableComponent("commands.bossbar.set.color.success", p_136604_.m_136288_()), true);
         return 0;
      }
   }

   private static int m_136606_(CommandSourceStack p_136607_, CustomBossEvent p_136608_, BossEvent.BossBarOverlay p_136609_) throws CommandSyntaxException {
      if (p_136608_.m_18863_().equals(p_136609_)) {
         throw f_136576_.create();
      } else {
         p_136608_.m_5648_(p_136609_);
         p_136607_.m_81354_(new TranslatableComponent("commands.bossbar.set.style.success", p_136608_.m_136288_()), true);
         return 0;
      }
   }

   private static int m_136614_(CommandSourceStack p_136615_, CustomBossEvent p_136616_, Component p_136617_) throws CommandSyntaxException {
      Component component = ComponentUtils.m_130731_(p_136615_, p_136617_, (Entity)null, 0);
      if (p_136616_.m_18861_().equals(component)) {
         throw f_136574_.create();
      } else {
         p_136616_.m_6456_(component);
         p_136615_.m_81354_(new TranslatableComponent("commands.bossbar.set.name.success", p_136616_.m_136288_()), true);
         return 0;
      }
   }

   private static int m_136610_(CommandSourceStack p_136611_, CustomBossEvent p_136612_, Collection<ServerPlayer> p_136613_) throws CommandSyntaxException {
      boolean flag = p_136612_.m_136268_(p_136613_);
      if (!flag) {
         throw f_136573_.create();
      } else {
         if (p_136612_.m_8324_().isEmpty()) {
            p_136611_.m_81354_(new TranslatableComponent("commands.bossbar.set.players.success.none", p_136612_.m_136288_()), true);
         } else {
            p_136611_.m_81354_(new TranslatableComponent("commands.bossbar.set.players.success.some", p_136612_.m_136288_(), p_136613_.size(), ComponentUtils.m_178440_(p_136613_, Player::m_5446_)), true);
         }

         return p_136612_.m_8324_().size();
      }
   }

   private static int m_136589_(CommandSourceStack p_136590_) {
      Collection<CustomBossEvent> collection = p_136590_.m_81377_().m_129901_().m_136304_();
      if (collection.isEmpty()) {
         p_136590_.m_81354_(new TranslatableComponent("commands.bossbar.list.bars.none"), false);
      } else {
         p_136590_.m_81354_(new TranslatableComponent("commands.bossbar.list.bars.some", collection.size(), ComponentUtils.m_178440_(collection, CustomBossEvent::m_136288_)), false);
      }

      return collection.size();
   }

   private static int m_136591_(CommandSourceStack p_136592_, ResourceLocation p_136593_, Component p_136594_) throws CommandSyntaxException {
      CustomBossEvents custombossevents = p_136592_.m_81377_().m_129901_();
      if (custombossevents.m_136297_(p_136593_) != null) {
         throw f_136571_.create(p_136593_.toString());
      } else {
         CustomBossEvent custombossevent = custombossevents.m_136299_(p_136593_, ComponentUtils.m_130731_(p_136592_, p_136594_, (Entity)null, 0));
         p_136592_.m_81354_(new TranslatableComponent("commands.bossbar.create.success", custombossevent.m_136288_()), true);
         return custombossevents.m_136304_().size();
      }
   }

   private static int m_136649_(CommandSourceStack p_136650_, CustomBossEvent p_136651_) {
      CustomBossEvents custombossevents = p_136650_.m_81377_().m_129901_();
      p_136651_.m_7706_();
      custombossevents.m_136302_(p_136651_);
      p_136650_.m_81354_(new TranslatableComponent("commands.bossbar.remove.success", p_136651_.m_136288_()), true);
      return custombossevents.m_136304_().size();
   }

   public static CustomBossEvent m_136584_(CommandContext<CommandSourceStack> p_136585_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = ResourceLocationArgument.m_107011_(p_136585_, "id");
      CustomBossEvent custombossevent = p_136585_.getSource().m_81377_().m_129901_().m_136297_(resourcelocation);
      if (custombossevent == null) {
         throw f_136572_.create(resourcelocation.toString());
      } else {
         return custombossevent;
      }
   }
}