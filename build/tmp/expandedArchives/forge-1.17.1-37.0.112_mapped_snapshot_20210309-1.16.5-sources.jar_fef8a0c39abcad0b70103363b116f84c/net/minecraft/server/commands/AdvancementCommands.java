package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementCommands {
   private static final SuggestionProvider<CommandSourceStack> f_136308_ = (p_136344_, p_136345_) -> {
      Collection<Advancement> collection = p_136344_.getSource().m_81377_().m_129889_().m_136028_();
      return SharedSuggestionProvider.m_82957_(collection.stream().map(Advancement::m_138327_), p_136345_);
   };

   public static void m_136310_(CommandDispatcher<CommandSourceStack> p_136311_) {
      p_136311_.register(Commands.m_82127_("advancement").requires((p_136318_) -> {
         return p_136318_.m_6761_(2);
      }).then(Commands.m_82127_("grant").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82127_("only").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136363_) -> {
         return m_136319_(p_136363_.getSource(), EntityArgument.m_91477_(p_136363_, "targets"), AdvancementCommands.Action.GRANT, m_136333_(ResourceLocationArgument.m_106987_(p_136363_, "advancement"), AdvancementCommands.Mode.ONLY));
      }).then(Commands.m_82129_("criterion", StringArgumentType.greedyString()).suggests((p_136339_, p_136340_) -> {
         return SharedSuggestionProvider.m_82970_(ResourceLocationArgument.m_106987_(p_136339_, "advancement").m_138325_().keySet(), p_136340_);
      }).executes((p_136361_) -> {
         return m_136324_(p_136361_.getSource(), EntityArgument.m_91477_(p_136361_, "targets"), AdvancementCommands.Action.GRANT, ResourceLocationArgument.m_106987_(p_136361_, "advancement"), StringArgumentType.getString(p_136361_, "criterion"));
      })))).then(Commands.m_82127_("from").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136359_) -> {
         return m_136319_(p_136359_.getSource(), EntityArgument.m_91477_(p_136359_, "targets"), AdvancementCommands.Action.GRANT, m_136333_(ResourceLocationArgument.m_106987_(p_136359_, "advancement"), AdvancementCommands.Mode.FROM));
      }))).then(Commands.m_82127_("until").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136357_) -> {
         return m_136319_(p_136357_.getSource(), EntityArgument.m_91477_(p_136357_, "targets"), AdvancementCommands.Action.GRANT, m_136333_(ResourceLocationArgument.m_106987_(p_136357_, "advancement"), AdvancementCommands.Mode.UNTIL));
      }))).then(Commands.m_82127_("through").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136355_) -> {
         return m_136319_(p_136355_.getSource(), EntityArgument.m_91477_(p_136355_, "targets"), AdvancementCommands.Action.GRANT, m_136333_(ResourceLocationArgument.m_106987_(p_136355_, "advancement"), AdvancementCommands.Mode.THROUGH));
      }))).then(Commands.m_82127_("everything").executes((p_136353_) -> {
         return m_136319_(p_136353_.getSource(), EntityArgument.m_91477_(p_136353_, "targets"), AdvancementCommands.Action.GRANT, p_136353_.getSource().m_81377_().m_129889_().m_136028_());
      })))).then(Commands.m_82127_("revoke").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82127_("only").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136351_) -> {
         return m_136319_(p_136351_.getSource(), EntityArgument.m_91477_(p_136351_, "targets"), AdvancementCommands.Action.REVOKE, m_136333_(ResourceLocationArgument.m_106987_(p_136351_, "advancement"), AdvancementCommands.Mode.ONLY));
      }).then(Commands.m_82129_("criterion", StringArgumentType.greedyString()).suggests((p_136315_, p_136316_) -> {
         return SharedSuggestionProvider.m_82970_(ResourceLocationArgument.m_106987_(p_136315_, "advancement").m_138325_().keySet(), p_136316_);
      }).executes((p_136349_) -> {
         return m_136324_(p_136349_.getSource(), EntityArgument.m_91477_(p_136349_, "targets"), AdvancementCommands.Action.REVOKE, ResourceLocationArgument.m_106987_(p_136349_, "advancement"), StringArgumentType.getString(p_136349_, "criterion"));
      })))).then(Commands.m_82127_("from").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136347_) -> {
         return m_136319_(p_136347_.getSource(), EntityArgument.m_91477_(p_136347_, "targets"), AdvancementCommands.Action.REVOKE, m_136333_(ResourceLocationArgument.m_106987_(p_136347_, "advancement"), AdvancementCommands.Mode.FROM));
      }))).then(Commands.m_82127_("until").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136342_) -> {
         return m_136319_(p_136342_.getSource(), EntityArgument.m_91477_(p_136342_, "targets"), AdvancementCommands.Action.REVOKE, m_136333_(ResourceLocationArgument.m_106987_(p_136342_, "advancement"), AdvancementCommands.Mode.UNTIL));
      }))).then(Commands.m_82127_("through").then(Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_()).suggests(f_136308_).executes((p_136337_) -> {
         return m_136319_(p_136337_.getSource(), EntityArgument.m_91477_(p_136337_, "targets"), AdvancementCommands.Action.REVOKE, m_136333_(ResourceLocationArgument.m_106987_(p_136337_, "advancement"), AdvancementCommands.Mode.THROUGH));
      }))).then(Commands.m_82127_("everything").executes((p_136313_) -> {
         return m_136319_(p_136313_.getSource(), EntityArgument.m_91477_(p_136313_, "targets"), AdvancementCommands.Action.REVOKE, p_136313_.getSource().m_81377_().m_129889_().m_136028_());
      })))));
   }

   private static int m_136319_(CommandSourceStack p_136320_, Collection<ServerPlayer> p_136321_, AdvancementCommands.Action p_136322_, Collection<Advancement> p_136323_) {
      int i = 0;

      for(ServerPlayer serverplayer : p_136321_) {
         i += p_136322_.m_136379_(serverplayer, p_136323_);
      }

      if (i == 0) {
         if (p_136323_.size() == 1) {
            if (p_136321_.size() == 1) {
               throw new CommandRuntimeException(new TranslatableComponent(p_136322_.m_136378_() + ".one.to.one.failure", p_136323_.iterator().next().m_138330_(), p_136321_.iterator().next().m_5446_()));
            } else {
               throw new CommandRuntimeException(new TranslatableComponent(p_136322_.m_136378_() + ".one.to.many.failure", p_136323_.iterator().next().m_138330_(), p_136321_.size()));
            }
         } else if (p_136321_.size() == 1) {
            throw new CommandRuntimeException(new TranslatableComponent(p_136322_.m_136378_() + ".many.to.one.failure", p_136323_.size(), p_136321_.iterator().next().m_5446_()));
         } else {
            throw new CommandRuntimeException(new TranslatableComponent(p_136322_.m_136378_() + ".many.to.many.failure", p_136323_.size(), p_136321_.size()));
         }
      } else {
         if (p_136323_.size() == 1) {
            if (p_136321_.size() == 1) {
               p_136320_.m_81354_(new TranslatableComponent(p_136322_.m_136378_() + ".one.to.one.success", p_136323_.iterator().next().m_138330_(), p_136321_.iterator().next().m_5446_()), true);
            } else {
               p_136320_.m_81354_(new TranslatableComponent(p_136322_.m_136378_() + ".one.to.many.success", p_136323_.iterator().next().m_138330_(), p_136321_.size()), true);
            }
         } else if (p_136321_.size() == 1) {
            p_136320_.m_81354_(new TranslatableComponent(p_136322_.m_136378_() + ".many.to.one.success", p_136323_.size(), p_136321_.iterator().next().m_5446_()), true);
         } else {
            p_136320_.m_81354_(new TranslatableComponent(p_136322_.m_136378_() + ".many.to.many.success", p_136323_.size(), p_136321_.size()), true);
         }

         return i;
      }
   }

   private static int m_136324_(CommandSourceStack p_136325_, Collection<ServerPlayer> p_136326_, AdvancementCommands.Action p_136327_, Advancement p_136328_, String p_136329_) {
      int i = 0;
      if (!p_136328_.m_138325_().containsKey(p_136329_)) {
         throw new CommandRuntimeException(new TranslatableComponent("commands.advancement.criterionNotFound", p_136328_.m_138330_(), p_136329_));
      } else {
         for(ServerPlayer serverplayer : p_136326_) {
            if (p_136327_.m_5753_(serverplayer, p_136328_, p_136329_)) {
               ++i;
            }
         }

         if (i == 0) {
            if (p_136326_.size() == 1) {
               throw new CommandRuntimeException(new TranslatableComponent(p_136327_.m_136378_() + ".criterion.to.one.failure", p_136329_, p_136328_.m_138330_(), p_136326_.iterator().next().m_5446_()));
            } else {
               throw new CommandRuntimeException(new TranslatableComponent(p_136327_.m_136378_() + ".criterion.to.many.failure", p_136329_, p_136328_.m_138330_(), p_136326_.size()));
            }
         } else {
            if (p_136326_.size() == 1) {
               p_136325_.m_81354_(new TranslatableComponent(p_136327_.m_136378_() + ".criterion.to.one.success", p_136329_, p_136328_.m_138330_(), p_136326_.iterator().next().m_5446_()), true);
            } else {
               p_136325_.m_81354_(new TranslatableComponent(p_136327_.m_136378_() + ".criterion.to.many.success", p_136329_, p_136328_.m_138330_(), p_136326_.size()), true);
            }

            return i;
         }
      }
   }

   private static List<Advancement> m_136333_(Advancement p_136334_, AdvancementCommands.Mode p_136335_) {
      List<Advancement> list = Lists.newArrayList();
      if (p_136335_.f_136417_) {
         for(Advancement advancement = p_136334_.m_138319_(); advancement != null; advancement = advancement.m_138319_()) {
            list.add(advancement);
         }
      }

      list.add(p_136334_);
      if (p_136335_.f_136418_) {
         m_136330_(p_136334_, list);
      }

      return list;
   }

   private static void m_136330_(Advancement p_136331_, List<Advancement> p_136332_) {
      for(Advancement advancement : p_136331_.m_138322_()) {
         p_136332_.add(advancement);
         m_136330_(advancement, p_136332_);
      }

   }

   static enum Action {
      GRANT("grant") {
         protected boolean m_6070_(ServerPlayer p_136395_, Advancement p_136396_) {
            AdvancementProgress advancementprogress = p_136395_.m_8960_().m_135996_(p_136396_);
            if (advancementprogress.m_8193_()) {
               return false;
            } else {
               for(String s : advancementprogress.m_8219_()) {
                  p_136395_.m_8960_().m_135988_(p_136396_, s);
               }

               return true;
            }
         }

         protected boolean m_5753_(ServerPlayer p_136398_, Advancement p_136399_, String p_136400_) {
            return p_136398_.m_8960_().m_135988_(p_136399_, p_136400_);
         }
      },
      REVOKE("revoke") {
         protected boolean m_6070_(ServerPlayer p_136406_, Advancement p_136407_) {
            AdvancementProgress advancementprogress = p_136406_.m_8960_().m_135996_(p_136407_);
            if (!advancementprogress.m_8206_()) {
               return false;
            } else {
               for(String s : advancementprogress.m_8220_()) {
                  p_136406_.m_8960_().m_135998_(p_136407_, s);
               }

               return true;
            }
         }

         protected boolean m_5753_(ServerPlayer p_136409_, Advancement p_136410_, String p_136411_) {
            return p_136409_.m_8960_().m_135998_(p_136410_, p_136411_);
         }
      };

      private final String f_136366_;

      Action(String p_136372_) {
         this.f_136366_ = "commands.advancement." + p_136372_;
      }

      public int m_136379_(ServerPlayer p_136380_, Iterable<Advancement> p_136381_) {
         int i = 0;

         for(Advancement advancement : p_136381_) {
            if (this.m_6070_(p_136380_, advancement)) {
               ++i;
            }
         }

         return i;
      }

      protected abstract boolean m_6070_(ServerPlayer p_136382_, Advancement p_136383_);

      protected abstract boolean m_5753_(ServerPlayer p_136384_, Advancement p_136385_, String p_136386_);

      protected String m_136378_() {
         return this.f_136366_;
      }
   }

   static enum Mode {
      ONLY(false, false),
      THROUGH(true, true),
      FROM(false, true),
      UNTIL(true, false),
      EVERYTHING(true, true);

      final boolean f_136417_;
      final boolean f_136418_;

      private Mode(boolean p_136424_, boolean p_136425_) {
         this.f_136417_ = p_136424_;
         this.f_136418_ = p_136425_;
      }
   }
}