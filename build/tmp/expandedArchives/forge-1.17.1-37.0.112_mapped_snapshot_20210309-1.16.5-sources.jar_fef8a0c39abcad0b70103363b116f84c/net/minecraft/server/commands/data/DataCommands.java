package net.minecraft.server.commands.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class DataCommands {
   private static final SimpleCommandExceptionType f_139352_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.merge.failed"));
   private static final DynamicCommandExceptionType f_139353_ = new DynamicCommandExceptionType((p_139491_) -> {
      return new TranslatableComponent("commands.data.get.invalid", p_139491_);
   });
   private static final DynamicCommandExceptionType f_139354_ = new DynamicCommandExceptionType((p_139481_) -> {
      return new TranslatableComponent("commands.data.get.unknown", p_139481_);
   });
   private static final SimpleCommandExceptionType f_139355_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.get.multiple"));
   private static final DynamicCommandExceptionType f_139356_ = new DynamicCommandExceptionType((p_139468_) -> {
      return new TranslatableComponent("commands.data.modify.expected_list", p_139468_);
   });
   private static final DynamicCommandExceptionType f_139357_ = new DynamicCommandExceptionType((p_139448_) -> {
      return new TranslatableComponent("commands.data.modify.expected_object", p_139448_);
   });
   private static final DynamicCommandExceptionType f_139358_ = new DynamicCommandExceptionType((p_139402_) -> {
      return new TranslatableComponent("commands.data.modify.invalid_index", p_139402_);
   });
   public static final List<Function<String, DataCommands.DataProvider>> f_139349_ = ImmutableList.of(EntityDataAccessor.f_139505_, BlockDataAccessor.f_139291_, StorageDataAccessor.f_139531_);
   public static final List<DataCommands.DataProvider> f_139350_ = f_139349_.stream().map((p_139450_) -> {
      return p_139450_.apply("target");
   }).collect(ImmutableList.toImmutableList());
   public static final List<DataCommands.DataProvider> f_139351_ = f_139349_.stream().map((p_139410_) -> {
      return p_139410_.apply("source");
   }).collect(ImmutableList.toImmutableList());

   public static void m_139365_(CommandDispatcher<CommandSourceStack> p_139366_) {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("data").requires((p_139381_) -> {
         return p_139381_.m_6761_(2);
      });

      for(DataCommands.DataProvider datacommands$dataprovider : f_139350_) {
         literalargumentbuilder.then(datacommands$dataprovider.m_7621_(Commands.m_82127_("merge"), (p_139471_) -> {
            return p_139471_.then(Commands.m_82129_("nbt", CompoundTagArgument.m_87657_()).executes((p_142857_) -> {
               return m_139394_(p_142857_.getSource(), datacommands$dataprovider.m_7018_(p_142857_), CompoundTagArgument.m_87660_(p_142857_, "nbt"));
            }));
         })).then(datacommands$dataprovider.m_7621_(Commands.m_82127_("get"), (p_139453_) -> {
            return p_139453_.executes((p_142849_) -> {
               return m_139382_(p_142849_.getSource(), datacommands$dataprovider.m_7018_(p_142849_));
            }).then(Commands.m_82129_("path", NbtPathArgument.m_99487_()).executes((p_142841_) -> {
               return m_139443_(p_142841_.getSource(), datacommands$dataprovider.m_7018_(p_142841_), NbtPathArgument.m_99498_(p_142841_, "path"));
            }).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes((p_142833_) -> {
               return m_139389_(p_142833_.getSource(), datacommands$dataprovider.m_7018_(p_142833_), NbtPathArgument.m_99498_(p_142833_, "path"), DoubleArgumentType.getDouble(p_142833_, "scale"));
            })));
         })).then(datacommands$dataprovider.m_7621_(Commands.m_82127_("remove"), (p_139413_) -> {
            return p_139413_.then(Commands.m_82129_("path", NbtPathArgument.m_99487_()).executes((p_142820_) -> {
               return m_139385_(p_142820_.getSource(), datacommands$dataprovider.m_7018_(p_142820_), NbtPathArgument.m_99498_(p_142820_, "path"));
            }));
         })).then(m_139403_((p_139368_, p_139369_) -> {
            p_139368_.then(Commands.m_82127_("insert").then(Commands.m_82129_("index", IntegerArgumentType.integer()).then(p_139369_.m_139500_((p_142859_, p_142860_, p_142861_, p_142862_) -> {
               int i = IntegerArgumentType.getInteger(p_142859_, "index");
               return m_139360_(i, p_142860_, p_142861_, p_142862_);
            })))).then(Commands.m_82127_("prepend").then(p_139369_.m_139500_((p_142851_, p_142852_, p_142853_, p_142854_) -> {
               return m_139360_(0, p_142852_, p_142853_, p_142854_);
            }))).then(Commands.m_82127_("append").then(p_139369_.m_139500_((p_142843_, p_142844_, p_142845_, p_142846_) -> {
               return m_139360_(-1, p_142844_, p_142845_, p_142846_);
            }))).then(Commands.m_82127_("set").then(p_139369_.m_139500_((p_142835_, p_142836_, p_142837_, p_142838_) -> {
               return p_142837_.m_99645_(p_142836_, Iterables.getLast(p_142838_)::m_6426_);
            }))).then(Commands.m_82127_("merge").then(p_139369_.m_139500_((p_142822_, p_142823_, p_142824_, p_142825_) -> {
               Collection<Tag> collection = p_142824_.m_99640_(p_142823_, CompoundTag::new);
               int i = 0;

               for(Tag tag : collection) {
                  if (!(tag instanceof CompoundTag)) {
                     throw f_139357_.create(tag);
                  }

                  CompoundTag compoundtag = (CompoundTag)tag;
                  CompoundTag compoundtag1 = compoundtag.m_6426_();

                  for(Tag tag1 : p_142825_) {
                     if (!(tag1 instanceof CompoundTag)) {
                        throw f_139357_.create(tag1);
                     }

                     compoundtag.m_128391_((CompoundTag)tag1);
                  }

                  i += compoundtag1.equals(compoundtag) ? 0 : 1;
               }

               return i;
            })));
         }));
      }

      p_139366_.register(literalargumentbuilder);
   }

   private static int m_139360_(int p_139361_, CompoundTag p_139362_, NbtPathArgument.NbtPath p_139363_, List<Tag> p_139364_) throws CommandSyntaxException {
      Collection<Tag> collection = p_139363_.m_99640_(p_139362_, ListTag::new);
      int i = 0;

      for(Tag tag : collection) {
         if (!(tag instanceof CollectionTag)) {
            throw f_139356_.create(tag);
         }

         boolean flag = false;
         CollectionTag<?> collectiontag = (CollectionTag)tag;
         int j = p_139361_ < 0 ? collectiontag.size() + p_139361_ + 1 : p_139361_;

         for(Tag tag1 : p_139364_) {
            try {
               if (collectiontag.m_7614_(j, tag1.m_6426_())) {
                  ++j;
                  flag = true;
               }
            } catch (IndexOutOfBoundsException indexoutofboundsexception) {
               throw f_139358_.create(j);
            }
         }

         i += flag ? 1 : 0;
      }

      return i;
   }

   private static ArgumentBuilder<CommandSourceStack, ?> m_139403_(BiConsumer<ArgumentBuilder<CommandSourceStack, ?>, DataCommands.DataManipulatorDecorator> p_139404_) {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("modify");

      for(DataCommands.DataProvider datacommands$dataprovider : f_139350_) {
         datacommands$dataprovider.m_7621_(literalargumentbuilder, (p_139408_) -> {
            ArgumentBuilder<CommandSourceStack, ?> argumentbuilder = Commands.m_82129_("targetPath", NbtPathArgument.m_99487_());

            for(DataCommands.DataProvider datacommands$dataprovider1 : f_139351_) {
               p_139404_.accept(argumentbuilder, (p_142807_) -> {
                  return datacommands$dataprovider1.m_7621_(Commands.m_82127_("from"), (p_142812_) -> {
                     return p_142812_.executes((p_142830_) -> {
                        List<Tag> list = Collections.singletonList(datacommands$dataprovider1.m_7018_(p_142830_).m_6184_());
                        return m_139375_(p_142830_, datacommands$dataprovider, p_142807_, list);
                     }).then(Commands.m_82129_("sourcePath", NbtPathArgument.m_99487_()).executes((p_142817_) -> {
                        DataAccessor dataaccessor = datacommands$dataprovider1.m_7018_(p_142817_);
                        NbtPathArgument.NbtPath nbtpathargument$nbtpath = NbtPathArgument.m_99498_(p_142817_, "sourcePath");
                        List<Tag> list = nbtpathargument$nbtpath.m_99638_(dataaccessor.m_6184_());
                        return m_139375_(p_142817_, datacommands$dataprovider, p_142807_, list);
                     }));
                  });
               });
            }

            p_139404_.accept(argumentbuilder, (p_142799_) -> {
               return Commands.m_82127_("value").then(Commands.m_82129_("value", NbtTagArgument.m_100659_()).executes((p_142803_) -> {
                  List<Tag> list = Collections.singletonList(NbtTagArgument.m_100662_(p_142803_, "value"));
                  return m_139375_(p_142803_, datacommands$dataprovider, p_142799_, list);
               }));
            });
            return p_139408_.then(argumentbuilder);
         });
      }

      return literalargumentbuilder;
   }

   private static int m_139375_(CommandContext<CommandSourceStack> p_139376_, DataCommands.DataProvider p_139377_, DataCommands.DataManipulator p_139378_, List<Tag> p_139379_) throws CommandSyntaxException {
      DataAccessor dataaccessor = p_139377_.m_7018_(p_139376_);
      NbtPathArgument.NbtPath nbtpathargument$nbtpath = NbtPathArgument.m_99498_(p_139376_, "targetPath");
      CompoundTag compoundtag = dataaccessor.m_6184_();
      int i = p_139378_.m_139495_(p_139376_, compoundtag, nbtpathargument$nbtpath, p_139379_);
      if (i == 0) {
         throw f_139352_.create();
      } else {
         dataaccessor.m_7603_(compoundtag);
         p_139376_.getSource().m_81354_(dataaccessor.m_6934_(), true);
         return i;
      }
   }

   private static int m_139385_(CommandSourceStack p_139386_, DataAccessor p_139387_, NbtPathArgument.NbtPath p_139388_) throws CommandSyntaxException {
      CompoundTag compoundtag = p_139387_.m_6184_();
      int i = p_139388_.m_99648_(compoundtag);
      if (i == 0) {
         throw f_139352_.create();
      } else {
         p_139387_.m_7603_(compoundtag);
         p_139386_.m_81354_(p_139387_.m_6934_(), true);
         return i;
      }
   }

   private static Tag m_139398_(NbtPathArgument.NbtPath p_139399_, DataAccessor p_139400_) throws CommandSyntaxException {
      Collection<Tag> collection = p_139399_.m_99638_(p_139400_.m_6184_());
      Iterator<Tag> iterator = collection.iterator();
      Tag tag = iterator.next();
      if (iterator.hasNext()) {
         throw f_139355_.create();
      } else {
         return tag;
      }
   }

   private static int m_139443_(CommandSourceStack p_139444_, DataAccessor p_139445_, NbtPathArgument.NbtPath p_139446_) throws CommandSyntaxException {
      Tag tag = m_139398_(p_139446_, p_139445_);
      int i;
      if (tag instanceof NumericTag) {
         i = Mth.m_14107_(((NumericTag)tag).m_7061_());
      } else if (tag instanceof CollectionTag) {
         i = ((CollectionTag)tag).size();
      } else if (tag instanceof CompoundTag) {
         i = ((CompoundTag)tag).m_128440_();
      } else {
         if (!(tag instanceof StringTag)) {
            throw f_139354_.create(p_139446_.toString());
         }

         i = tag.m_7916_().length();
      }

      p_139444_.m_81354_(p_139445_.m_7624_(tag), false);
      return i;
   }

   private static int m_139389_(CommandSourceStack p_139390_, DataAccessor p_139391_, NbtPathArgument.NbtPath p_139392_, double p_139393_) throws CommandSyntaxException {
      Tag tag = m_139398_(p_139392_, p_139391_);
      if (!(tag instanceof NumericTag)) {
         throw f_139353_.create(p_139392_.toString());
      } else {
         int i = Mth.m_14107_(((NumericTag)tag).m_7061_() * p_139393_);
         p_139390_.m_81354_(p_139391_.m_6066_(p_139392_, p_139393_, i), false);
         return i;
      }
   }

   private static int m_139382_(CommandSourceStack p_139383_, DataAccessor p_139384_) throws CommandSyntaxException {
      p_139383_.m_81354_(p_139384_.m_7624_(p_139384_.m_6184_()), false);
      return 1;
   }

   private static int m_139394_(CommandSourceStack p_139395_, DataAccessor p_139396_, CompoundTag p_139397_) throws CommandSyntaxException {
      CompoundTag compoundtag = p_139396_.m_6184_();
      CompoundTag compoundtag1 = compoundtag.m_6426_().m_128391_(p_139397_);
      if (compoundtag.equals(compoundtag1)) {
         throw f_139352_.create();
      } else {
         p_139396_.m_7603_(compoundtag1);
         p_139395_.m_81354_(p_139396_.m_6934_(), true);
         return 1;
      }
   }

   interface DataManipulator {
      int m_139495_(CommandContext<CommandSourceStack> p_139496_, CompoundTag p_139497_, NbtPathArgument.NbtPath p_139498_, List<Tag> p_139499_) throws CommandSyntaxException;
   }

   interface DataManipulatorDecorator {
      ArgumentBuilder<CommandSourceStack, ?> m_139500_(DataCommands.DataManipulator p_139501_);
   }

   public interface DataProvider {
      DataAccessor m_7018_(CommandContext<CommandSourceStack> p_139504_) throws CommandSyntaxException;

      ArgumentBuilder<CommandSourceStack, ?> m_7621_(ArgumentBuilder<CommandSourceStack, ?> p_139502_, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> p_139503_);
   }
}