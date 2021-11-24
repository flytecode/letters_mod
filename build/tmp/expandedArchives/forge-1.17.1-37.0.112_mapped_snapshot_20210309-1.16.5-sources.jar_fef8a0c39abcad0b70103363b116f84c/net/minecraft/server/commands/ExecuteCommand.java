package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.commands.data.DataAccessor;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class ExecuteCommand {
   private static final int f_180148_ = 32768;
   private static final Dynamic2CommandExceptionType f_137030_ = new Dynamic2CommandExceptionType((p_137129_, p_137130_) -> {
      return new TranslatableComponent("commands.execute.blocks.toobig", p_137129_, p_137130_);
   });
   private static final SimpleCommandExceptionType f_137031_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.execute.conditional.fail"));
   private static final DynamicCommandExceptionType f_137032_ = new DynamicCommandExceptionType((p_137127_) -> {
      return new TranslatableComponent("commands.execute.conditional.fail_count", p_137127_);
   });
   private static final BinaryOperator<ResultConsumer<CommandSourceStack>> f_137033_ = (p_137045_, p_137046_) -> {
      return (p_180160_, p_180161_, p_180162_) -> {
         p_137045_.onCommandComplete(p_180160_, p_180161_, p_180162_);
         p_137046_.onCommandComplete(p_180160_, p_180161_, p_180162_);
      };
   };
   private static final SuggestionProvider<CommandSourceStack> f_137034_ = (p_137062_, p_137063_) -> {
      PredicateManager predicatemanager = p_137062_.getSource().m_81377_().m_129899_();
      return SharedSuggestionProvider.m_82926_(predicatemanager.m_79232_(), p_137063_);
   };

   public static void m_137042_(CommandDispatcher<CommandSourceStack> p_137043_) {
      LiteralCommandNode<CommandSourceStack> literalcommandnode = p_137043_.register(Commands.m_82127_("execute").requires((p_137197_) -> {
         return p_137197_.m_6761_(2);
      }));
      p_137043_.register(Commands.m_82127_("execute").requires((p_137103_) -> {
         return p_137103_.m_6761_(2);
      }).then(Commands.m_82127_("run").redirect(p_137043_.getRoot())).then(m_137084_(literalcommandnode, Commands.m_82127_("if"), true)).then(m_137084_(literalcommandnode, Commands.m_82127_("unless"), false)).then(Commands.m_82127_("as").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork(literalcommandnode, (p_137299_) -> {
         List<CommandSourceStack> list = Lists.newArrayList();

         for(Entity entity : EntityArgument.m_91467_(p_137299_, "targets")) {
            list.add(p_137299_.getSource().m_81329_(entity));
         }

         return list;
      }))).then(Commands.m_82127_("at").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork(literalcommandnode, (p_137297_) -> {
         List<CommandSourceStack> list = Lists.newArrayList();

         for(Entity entity : EntityArgument.m_91467_(p_137297_, "targets")) {
            list.add(p_137297_.getSource().m_81327_((ServerLevel)entity.f_19853_).m_81348_(entity.m_20182_()).m_81346_(entity.m_20155_()));
         }

         return list;
      }))).then(Commands.m_82127_("store").then(m_137093_(literalcommandnode, Commands.m_82127_("result"), true)).then(m_137093_(literalcommandnode, Commands.m_82127_("success"), false))).then(Commands.m_82127_("positioned").then(Commands.m_82129_("pos", Vec3Argument.m_120841_()).redirect(literalcommandnode, (p_137295_) -> {
         return p_137295_.getSource().m_81348_(Vec3Argument.m_120844_(p_137295_, "pos")).m_81350_(EntityAnchorArgument.Anchor.FEET);
      })).then(Commands.m_82127_("as").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork(literalcommandnode, (p_137293_) -> {
         List<CommandSourceStack> list = Lists.newArrayList();

         for(Entity entity : EntityArgument.m_91467_(p_137293_, "targets")) {
            list.add(p_137293_.getSource().m_81348_(entity.m_20182_()));
         }

         return list;
      })))).then(Commands.m_82127_("rotated").then(Commands.m_82129_("rot", RotationArgument.m_120479_()).redirect(literalcommandnode, (p_137291_) -> {
         return p_137291_.getSource().m_81346_(RotationArgument.m_120482_(p_137291_, "rot").m_6970_(p_137291_.getSource()));
      })).then(Commands.m_82127_("as").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).fork(literalcommandnode, (p_137289_) -> {
         List<CommandSourceStack> list = Lists.newArrayList();

         for(Entity entity : EntityArgument.m_91467_(p_137289_, "targets")) {
            list.add(p_137289_.getSource().m_81346_(entity.m_20155_()));
         }

         return list;
      })))).then(Commands.m_82127_("facing").then(Commands.m_82127_("entity").then(Commands.m_82129_("targets", EntityArgument.m_91460_()).then(Commands.m_82129_("anchor", EntityAnchorArgument.m_90350_()).fork(literalcommandnode, (p_137287_) -> {
         List<CommandSourceStack> list = Lists.newArrayList();
         EntityAnchorArgument.Anchor entityanchorargument$anchor = EntityAnchorArgument.m_90353_(p_137287_, "anchor");

         for(Entity entity : EntityArgument.m_91467_(p_137287_, "targets")) {
            list.add(p_137287_.getSource().m_81331_(entity, entityanchorargument$anchor));
         }

         return list;
      })))).then(Commands.m_82129_("pos", Vec3Argument.m_120841_()).redirect(literalcommandnode, (p_137285_) -> {
         return p_137285_.getSource().m_81364_(Vec3Argument.m_120844_(p_137285_, "pos"));
      }))).then(Commands.m_82127_("align").then(Commands.m_82129_("axes", SwizzleArgument.m_120807_()).redirect(literalcommandnode, (p_137283_) -> {
         return p_137283_.getSource().m_81348_(p_137283_.getSource().m_81371_().m_82517_(SwizzleArgument.m_120810_(p_137283_, "axes")));
      }))).then(Commands.m_82127_("anchored").then(Commands.m_82129_("anchor", EntityAnchorArgument.m_90350_()).redirect(literalcommandnode, (p_137281_) -> {
         return p_137281_.getSource().m_81350_(EntityAnchorArgument.m_90353_(p_137281_, "anchor"));
      }))).then(Commands.m_82127_("in").then(Commands.m_82129_("dimension", DimensionArgument.m_88805_()).redirect(literalcommandnode, (p_137279_) -> {
         return p_137279_.getSource().m_81327_(DimensionArgument.m_88808_(p_137279_, "dimension"));
      }))));
   }

   private static ArgumentBuilder<CommandSourceStack, ?> m_137093_(LiteralCommandNode<CommandSourceStack> p_137094_, LiteralArgumentBuilder<CommandSourceStack> p_137095_, boolean p_137096_) {
      p_137095_.then(Commands.m_82127_("score").then(Commands.m_82129_("targets", ScoreHolderArgument.m_108239_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).redirect(p_137094_, (p_137271_) -> {
         return m_137107_(p_137271_.getSource(), ScoreHolderArgument.m_108246_(p_137271_, "targets"), ObjectiveArgument.m_101960_(p_137271_, "objective"), p_137096_);
      }))));
      p_137095_.then(Commands.m_82127_("bossbar").then(Commands.m_82129_("id", ResourceLocationArgument.m_106984_()).suggests(BossBarCommands.f_136570_).then(Commands.m_82127_("value").redirect(p_137094_, (p_137259_) -> {
         return m_137112_(p_137259_.getSource(), BossBarCommands.m_136584_(p_137259_), true, p_137096_);
      })).then(Commands.m_82127_("max").redirect(p_137094_, (p_137247_) -> {
         return m_137112_(p_137247_.getSource(), BossBarCommands.m_136584_(p_137247_), false, p_137096_);
      }))));

      for(DataCommands.DataProvider datacommands$dataprovider : DataCommands.f_139350_) {
         datacommands$dataprovider.m_7621_(p_137095_, (p_137101_) -> {
            return p_137101_.then(Commands.m_82129_("path", NbtPathArgument.m_99487_()).then(Commands.m_82127_("int").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect(p_137094_, (p_180216_) -> {
               return m_137117_(p_180216_.getSource(), datacommands$dataprovider.m_7018_(p_180216_), NbtPathArgument.m_99498_(p_180216_, "path"), (p_180219_) -> {
                  return IntTag.m_128679_((int)((double)p_180219_ * DoubleArgumentType.getDouble(p_180216_, "scale")));
               }, p_137096_);
            }))).then(Commands.m_82127_("float").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect(p_137094_, (p_180209_) -> {
               return m_137117_(p_180209_.getSource(), datacommands$dataprovider.m_7018_(p_180209_), NbtPathArgument.m_99498_(p_180209_, "path"), (p_180212_) -> {
                  return FloatTag.m_128566_((float)((double)p_180212_ * DoubleArgumentType.getDouble(p_180209_, "scale")));
               }, p_137096_);
            }))).then(Commands.m_82127_("short").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect(p_137094_, (p_180199_) -> {
               return m_137117_(p_180199_.getSource(), datacommands$dataprovider.m_7018_(p_180199_), NbtPathArgument.m_99498_(p_180199_, "path"), (p_180202_) -> {
                  return ShortTag.m_129258_((short)((int)((double)p_180202_ * DoubleArgumentType.getDouble(p_180199_, "scale"))));
               }, p_137096_);
            }))).then(Commands.m_82127_("long").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect(p_137094_, (p_180189_) -> {
               return m_137117_(p_180189_.getSource(), datacommands$dataprovider.m_7018_(p_180189_), NbtPathArgument.m_99498_(p_180189_, "path"), (p_180192_) -> {
                  return LongTag.m_128882_((long)((double)p_180192_ * DoubleArgumentType.getDouble(p_180189_, "scale")));
               }, p_137096_);
            }))).then(Commands.m_82127_("double").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect(p_137094_, (p_180179_) -> {
               return m_137117_(p_180179_.getSource(), datacommands$dataprovider.m_7018_(p_180179_), NbtPathArgument.m_99498_(p_180179_, "path"), (p_180182_) -> {
                  return DoubleTag.m_128500_((double)p_180182_ * DoubleArgumentType.getDouble(p_180179_, "scale"));
               }, p_137096_);
            }))).then(Commands.m_82127_("byte").then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).redirect(p_137094_, (p_180156_) -> {
               return m_137117_(p_180156_.getSource(), datacommands$dataprovider.m_7018_(p_180156_), NbtPathArgument.m_99498_(p_180156_, "path"), (p_180165_) -> {
                  return ByteTag.m_128266_((byte)((int)((double)p_180165_ * DoubleArgumentType.getDouble(p_180156_, "scale"))));
               }, p_137096_);
            }))));
         });
      }

      return p_137095_;
   }

   private static CommandSourceStack m_137107_(CommandSourceStack p_137108_, Collection<String> p_137109_, Objective p_137110_, boolean p_137111_) {
      Scoreboard scoreboard = p_137108_.m_81377_().m_129896_();
      return p_137108_.m_81336_((p_137136_, p_137137_, p_137138_) -> {
         for(String s : p_137109_) {
            Score score = scoreboard.m_83471_(s, p_137110_);
            int i = p_137111_ ? p_137138_ : (p_137137_ ? 1 : 0);
            score.m_83402_(i);
         }

      }, f_137033_);
   }

   private static CommandSourceStack m_137112_(CommandSourceStack p_137113_, CustomBossEvent p_137114_, boolean p_137115_, boolean p_137116_) {
      return p_137113_.m_81336_((p_137185_, p_137186_, p_137187_) -> {
         int i = p_137116_ ? p_137187_ : (p_137186_ ? 1 : 0);
         if (p_137115_) {
            p_137114_.m_136264_(i);
         } else {
            p_137114_.m_136278_(i);
         }

      }, f_137033_);
   }

   private static CommandSourceStack m_137117_(CommandSourceStack p_137118_, DataAccessor p_137119_, NbtPathArgument.NbtPath p_137120_, IntFunction<Tag> p_137121_, boolean p_137122_) {
      return p_137118_.m_81336_((p_137153_, p_137154_, p_137155_) -> {
         try {
            CompoundTag compoundtag = p_137119_.m_6184_();
            int i = p_137122_ ? p_137155_ : (p_137154_ ? 1 : 0);
            p_137120_.m_99645_(compoundtag, () -> {
               return p_137121_.apply(i);
            });
            p_137119_.m_7603_(compoundtag);
         } catch (CommandSyntaxException commandsyntaxexception) {
         }

      }, f_137033_);
   }

   private static ArgumentBuilder<CommandSourceStack, ?> m_137084_(CommandNode<CommandSourceStack> p_137085_, LiteralArgumentBuilder<CommandSourceStack> p_137086_, boolean p_137087_) {
      p_137086_.then(Commands.m_82127_("block").then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).then(m_137074_(p_137085_, Commands.m_82129_("block", BlockPredicateArgument.m_115570_()), p_137087_, (p_137277_) -> {
         return BlockPredicateArgument.m_115573_(p_137277_, "block").test(new BlockInWorld(p_137277_.getSource().m_81372_(), BlockPosArgument.m_118242_(p_137277_, "pos"), true));
      })))).then(Commands.m_82127_("score").then(Commands.m_82129_("target", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(Commands.m_82129_("targetObjective", ObjectiveArgument.m_101957_()).then(Commands.m_82127_("=").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(m_137074_(p_137085_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_137087_, (p_137275_) -> {
         return m_137064_(p_137275_, Integer::equals);
      })))).then(Commands.m_82127_("<").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(m_137074_(p_137085_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_137087_, (p_137273_) -> {
         return m_137064_(p_137273_, (p_180204_, p_180205_) -> {
            return p_180204_ < p_180205_;
         });
      })))).then(Commands.m_82127_("<=").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(m_137074_(p_137085_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_137087_, (p_137261_) -> {
         return m_137064_(p_137261_, (p_180194_, p_180195_) -> {
            return p_180194_ <= p_180195_;
         });
      })))).then(Commands.m_82127_(">").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(m_137074_(p_137085_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_137087_, (p_137249_) -> {
         return m_137064_(p_137249_, (p_180184_, p_180185_) -> {
            return p_180184_ > p_180185_;
         });
      })))).then(Commands.m_82127_(">=").then(Commands.m_82129_("source", ScoreHolderArgument.m_108217_()).suggests(ScoreHolderArgument.f_108210_).then(m_137074_(p_137085_, Commands.m_82129_("sourceObjective", ObjectiveArgument.m_101957_()), p_137087_, (p_137234_) -> {
         return m_137064_(p_137234_, (p_180167_, p_180168_) -> {
            return p_180167_ >= p_180168_;
         });
      })))).then(Commands.m_82127_("matches").then(m_137074_(p_137085_, Commands.m_82129_("range", RangeArgument.m_105404_()), p_137087_, (p_137216_) -> {
         return m_137058_(p_137216_, RangeArgument.Ints.m_105419_(p_137216_, "range"));
      })))))).then(Commands.m_82127_("blocks").then(Commands.m_82129_("start", BlockPosArgument.m_118239_()).then(Commands.m_82129_("end", BlockPosArgument.m_118239_()).then(Commands.m_82129_("destination", BlockPosArgument.m_118239_()).then(m_137079_(p_137085_, Commands.m_82127_("all"), p_137087_, false)).then(m_137079_(p_137085_, Commands.m_82127_("masked"), p_137087_, true)))))).then(Commands.m_82127_("entity").then(Commands.m_82129_("entities", EntityArgument.m_91460_()).fork(p_137085_, (p_137232_) -> {
         return m_137070_(p_137232_, p_137087_, !EntityArgument.m_91467_(p_137232_, "entities").isEmpty());
      }).executes(m_137166_(p_137087_, (p_137189_) -> {
         return EntityArgument.m_91467_(p_137189_, "entities").size();
      })))).then(Commands.m_82127_("predicate").then(m_137074_(p_137085_, Commands.m_82129_("predicate", ResourceLocationArgument.m_106984_()).suggests(f_137034_), p_137087_, (p_137054_) -> {
         return m_137104_(p_137054_.getSource(), ResourceLocationArgument.m_107001_(p_137054_, "predicate"));
      })));

      for(DataCommands.DataProvider datacommands$dataprovider : DataCommands.f_139351_) {
         p_137086_.then(datacommands$dataprovider.m_7621_(Commands.m_82127_("data"), (p_137092_) -> {
            return p_137092_.then(Commands.m_82129_("path", NbtPathArgument.m_99487_()).fork(p_137085_, (p_180175_) -> {
               return m_137070_(p_180175_, p_137087_, m_137145_(datacommands$dataprovider.m_7018_(p_180175_), NbtPathArgument.m_99498_(p_180175_, "path")) > 0);
            }).executes(m_137166_(p_137087_, (p_180152_) -> {
               return m_137145_(datacommands$dataprovider.m_7018_(p_180152_), NbtPathArgument.m_99498_(p_180152_, "path"));
            })));
         }));
      }

      return p_137086_;
   }

   private static Command<CommandSourceStack> m_137166_(boolean p_137167_, ExecuteCommand.CommandNumericPredicate p_137168_) {
      return p_137167_ ? (p_137203_) -> {
         int i = p_137168_.m_137300_(p_137203_);
         if (i > 0) {
            p_137203_.getSource().m_81354_(new TranslatableComponent("commands.execute.conditional.pass_count", i), false);
            return i;
         } else {
            throw f_137031_.create();
         }
      } : (p_137144_) -> {
         int i = p_137168_.m_137300_(p_137144_);
         if (i == 0) {
            p_137144_.getSource().m_81354_(new TranslatableComponent("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw f_137032_.create(i);
         }
      };
   }

   private static int m_137145_(DataAccessor p_137146_, NbtPathArgument.NbtPath p_137147_) throws CommandSyntaxException {
      return p_137147_.m_99643_(p_137146_.m_6184_());
   }

   private static boolean m_137064_(CommandContext<CommandSourceStack> p_137065_, BiPredicate<Integer, Integer> p_137066_) throws CommandSyntaxException {
      String s = ScoreHolderArgument.m_108223_(p_137065_, "target");
      Objective objective = ObjectiveArgument.m_101960_(p_137065_, "targetObjective");
      String s1 = ScoreHolderArgument.m_108223_(p_137065_, "source");
      Objective objective1 = ObjectiveArgument.m_101960_(p_137065_, "sourceObjective");
      Scoreboard scoreboard = p_137065_.getSource().m_81377_().m_129896_();
      if (scoreboard.m_83461_(s, objective) && scoreboard.m_83461_(s1, objective1)) {
         Score score = scoreboard.m_83471_(s, objective);
         Score score1 = scoreboard.m_83471_(s1, objective1);
         return p_137066_.test(score.m_83400_(), score1.m_83400_());
      } else {
         return false;
      }
   }

   private static boolean m_137058_(CommandContext<CommandSourceStack> p_137059_, MinMaxBounds.Ints p_137060_) throws CommandSyntaxException {
      String s = ScoreHolderArgument.m_108223_(p_137059_, "target");
      Objective objective = ObjectiveArgument.m_101960_(p_137059_, "targetObjective");
      Scoreboard scoreboard = p_137059_.getSource().m_81377_().m_129896_();
      return !scoreboard.m_83461_(s, objective) ? false : p_137060_.m_55390_(scoreboard.m_83471_(s, objective).m_83400_());
   }

   private static boolean m_137104_(CommandSourceStack p_137105_, LootItemCondition p_137106_) {
      ServerLevel serverlevel = p_137105_.m_81372_();
      LootContext.Builder lootcontext$builder = (new LootContext.Builder(serverlevel)).m_78972_(LootContextParams.f_81460_, p_137105_.m_81371_()).m_78984_(LootContextParams.f_81455_, p_137105_.m_81373_());
      return p_137106_.test(lootcontext$builder.m_78975_(LootContextParamSets.f_81412_));
   }

   private static Collection<CommandSourceStack> m_137070_(CommandContext<CommandSourceStack> p_137071_, boolean p_137072_, boolean p_137073_) {
      return (Collection<CommandSourceStack>)(p_137073_ == p_137072_ ? Collections.singleton(p_137071_.getSource()) : Collections.emptyList());
   }

   private static ArgumentBuilder<CommandSourceStack, ?> m_137074_(CommandNode<CommandSourceStack> p_137075_, ArgumentBuilder<CommandSourceStack, ?> p_137076_, boolean p_137077_, ExecuteCommand.CommandPredicate p_137078_) {
      return p_137076_.fork(p_137075_, (p_137214_) -> {
         return m_137070_(p_137214_, p_137077_, p_137078_.m_137302_(p_137214_));
      }).executes((p_137172_) -> {
         if (p_137077_ == p_137078_.m_137302_(p_137172_)) {
            p_137172_.getSource().m_81354_(new TranslatableComponent("commands.execute.conditional.pass"), false);
            return 1;
         } else {
            throw f_137031_.create();
         }
      });
   }

   private static ArgumentBuilder<CommandSourceStack, ?> m_137079_(CommandNode<CommandSourceStack> p_137080_, ArgumentBuilder<CommandSourceStack, ?> p_137081_, boolean p_137082_, boolean p_137083_) {
      return p_137081_.fork(p_137080_, (p_137180_) -> {
         return m_137070_(p_137180_, p_137082_, m_137220_(p_137180_, p_137083_).isPresent());
      }).executes(p_137082_ ? (p_137210_) -> {
         return m_137067_(p_137210_, p_137083_);
      } : (p_137165_) -> {
         return m_137193_(p_137165_, p_137083_);
      });
   }

   private static int m_137067_(CommandContext<CommandSourceStack> p_137068_, boolean p_137069_) throws CommandSyntaxException {
      OptionalInt optionalint = m_137220_(p_137068_, p_137069_);
      if (optionalint.isPresent()) {
         p_137068_.getSource().m_81354_(new TranslatableComponent("commands.execute.conditional.pass_count", optionalint.getAsInt()), false);
         return optionalint.getAsInt();
      } else {
         throw f_137031_.create();
      }
   }

   private static int m_137193_(CommandContext<CommandSourceStack> p_137194_, boolean p_137195_) throws CommandSyntaxException {
      OptionalInt optionalint = m_137220_(p_137194_, p_137195_);
      if (optionalint.isPresent()) {
         throw f_137032_.create(optionalint.getAsInt());
      } else {
         p_137194_.getSource().m_81354_(new TranslatableComponent("commands.execute.conditional.pass"), false);
         return 1;
      }
   }

   private static OptionalInt m_137220_(CommandContext<CommandSourceStack> p_137221_, boolean p_137222_) throws CommandSyntaxException {
      return m_137036_(p_137221_.getSource().m_81372_(), BlockPosArgument.m_118242_(p_137221_, "start"), BlockPosArgument.m_118242_(p_137221_, "end"), BlockPosArgument.m_118242_(p_137221_, "destination"), p_137222_);
   }

   private static OptionalInt m_137036_(ServerLevel p_137037_, BlockPos p_137038_, BlockPos p_137039_, BlockPos p_137040_, boolean p_137041_) throws CommandSyntaxException {
      BoundingBox boundingbox = BoundingBox.m_162375_(p_137038_, p_137039_);
      BoundingBox boundingbox1 = BoundingBox.m_162375_(p_137040_, p_137040_.m_141952_(boundingbox.m_71053_()));
      BlockPos blockpos = new BlockPos(boundingbox1.m_162395_() - boundingbox.m_162395_(), boundingbox1.m_162396_() - boundingbox.m_162396_(), boundingbox1.m_162398_() - boundingbox.m_162398_());
      int i = boundingbox.m_71056_() * boundingbox.m_71057_() * boundingbox.m_71058_();
      if (i > 32768) {
         throw f_137030_.create(32768, i);
      } else {
         int j = 0;

         for(int k = boundingbox.m_162398_(); k <= boundingbox.m_162401_(); ++k) {
            for(int l = boundingbox.m_162396_(); l <= boundingbox.m_162400_(); ++l) {
               for(int i1 = boundingbox.m_162395_(); i1 <= boundingbox.m_162399_(); ++i1) {
                  BlockPos blockpos1 = new BlockPos(i1, l, k);
                  BlockPos blockpos2 = blockpos1.m_141952_(blockpos);
                  BlockState blockstate = p_137037_.m_8055_(blockpos1);
                  if (!p_137041_ || !blockstate.m_60713_(Blocks.f_50016_)) {
                     if (blockstate != p_137037_.m_8055_(blockpos2)) {
                        return OptionalInt.empty();
                     }

                     BlockEntity blockentity = p_137037_.m_7702_(blockpos1);
                     BlockEntity blockentity1 = p_137037_.m_7702_(blockpos2);
                     if (blockentity != null) {
                        if (blockentity1 == null) {
                           return OptionalInt.empty();
                        }

                        CompoundTag compoundtag = blockentity.m_6945_(new CompoundTag());
                        compoundtag.m_128473_("x");
                        compoundtag.m_128473_("y");
                        compoundtag.m_128473_("z");
                        CompoundTag compoundtag1 = blockentity1.m_6945_(new CompoundTag());
                        compoundtag1.m_128473_("x");
                        compoundtag1.m_128473_("y");
                        compoundtag1.m_128473_("z");
                        if (!compoundtag.equals(compoundtag1)) {
                           return OptionalInt.empty();
                        }
                     }

                     ++j;
                  }
               }
            }
         }

         return OptionalInt.of(j);
      }
   }

   @FunctionalInterface
   interface CommandNumericPredicate {
      int m_137300_(CommandContext<CommandSourceStack> p_137301_) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface CommandPredicate {
      boolean m_137302_(CommandContext<CommandSourceStack> p_137303_) throws CommandSyntaxException;
   }
}