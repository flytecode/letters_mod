package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;

public class DataPackCommand {
   private static final DynamicCommandExceptionType f_136800_ = new DynamicCommandExceptionType((p_136868_) -> {
      return new TranslatableComponent("commands.datapack.unknown", p_136868_);
   });
   private static final DynamicCommandExceptionType f_136801_ = new DynamicCommandExceptionType((p_136857_) -> {
      return new TranslatableComponent("commands.datapack.enable.failed", p_136857_);
   });
   private static final DynamicCommandExceptionType f_136802_ = new DynamicCommandExceptionType((p_136833_) -> {
      return new TranslatableComponent("commands.datapack.disable.failed", p_136833_);
   });
   private static final SuggestionProvider<CommandSourceStack> f_136803_ = (p_136848_, p_136849_) -> {
      return SharedSuggestionProvider.m_82981_(p_136848_.getSource().m_81377_().m_129891_().m_10523_().stream().map(StringArgumentType::escapeIfRequired), p_136849_);
   };
   private static final SuggestionProvider<CommandSourceStack> f_136804_ = (p_136813_, p_136814_) -> {
      PackRepository packrepository = p_136813_.getSource().m_81377_().m_129891_();
      Collection<String> collection = packrepository.m_10523_();
      return SharedSuggestionProvider.m_82981_(packrepository.m_10514_().stream().filter((p_180050_) -> {
         return !collection.contains(p_180050_);
      }).map(StringArgumentType::escapeIfRequired), p_136814_);
   };

   public static void m_136808_(CommandDispatcher<CommandSourceStack> p_136809_) {
      p_136809_.register(Commands.m_82127_("datapack").requires((p_136872_) -> {
         return p_136872_.m_6761_(2);
      }).then(Commands.m_82127_("enable").then(Commands.m_82129_("name", StringArgumentType.string()).suggests(f_136804_).executes((p_136882_) -> {
         return m_136828_(p_136882_.getSource(), m_136815_(p_136882_, "name", true), (p_180059_, p_180060_) -> {
            p_180060_.m_10451_().m_10470_(p_180059_, p_180060_, (p_180062_) -> {
               return p_180062_;
            }, false);
         });
      }).then(Commands.m_82127_("after").then(Commands.m_82129_("existing", StringArgumentType.string()).suggests(f_136803_).executes((p_136880_) -> {
         return m_136828_(p_136880_.getSource(), m_136815_(p_136880_, "name", true), (p_180056_, p_180057_) -> {
            p_180056_.add(p_180056_.indexOf(m_136815_(p_136880_, "existing", false)) + 1, p_180057_);
         });
      }))).then(Commands.m_82127_("before").then(Commands.m_82129_("existing", StringArgumentType.string()).suggests(f_136803_).executes((p_136878_) -> {
         return m_136828_(p_136878_.getSource(), m_136815_(p_136878_, "name", true), (p_180046_, p_180047_) -> {
            p_180046_.add(p_180046_.indexOf(m_136815_(p_136878_, "existing", false)), p_180047_);
         });
      }))).then(Commands.m_82127_("last").executes((p_136876_) -> {
         return m_136828_(p_136876_.getSource(), m_136815_(p_136876_, "name", true), List::add);
      })).then(Commands.m_82127_("first").executes((p_136874_) -> {
         return m_136828_(p_136874_.getSource(), m_136815_(p_136874_, "name", true), (p_180052_, p_180053_) -> {
            p_180052_.add(0, p_180053_);
         });
      })))).then(Commands.m_82127_("disable").then(Commands.m_82129_("name", StringArgumentType.string()).suggests(f_136803_).executes((p_136870_) -> {
         return m_136825_(p_136870_.getSource(), m_136815_(p_136870_, "name", false));
      }))).then(Commands.m_82127_("list").executes((p_136864_) -> {
         return m_136823_(p_136864_.getSource());
      }).then(Commands.m_82127_("available").executes((p_136846_) -> {
         return m_136854_(p_136846_.getSource());
      })).then(Commands.m_82127_("enabled").executes((p_136811_) -> {
         return m_136865_(p_136811_.getSource());
      }))));
   }

   private static int m_136828_(CommandSourceStack p_136829_, Pack p_136830_, DataPackCommand.Inserter p_136831_) throws CommandSyntaxException {
      PackRepository packrepository = p_136829_.m_81377_().m_129891_();
      List<Pack> list = Lists.newArrayList(packrepository.m_10524_());
      p_136831_.m_136883_(list, p_136830_);
      p_136829_.m_81354_(new TranslatableComponent("commands.datapack.modify.enable", p_136830_.m_10437_(true)), true);
      ReloadCommand.m_138235_(list.stream().map(Pack::m_10446_).collect(Collectors.toList()), p_136829_);
      return list.size();
   }

   private static int m_136825_(CommandSourceStack p_136826_, Pack p_136827_) {
      PackRepository packrepository = p_136826_.m_81377_().m_129891_();
      List<Pack> list = Lists.newArrayList(packrepository.m_10524_());
      list.remove(p_136827_);
      p_136826_.m_81354_(new TranslatableComponent("commands.datapack.modify.disable", p_136827_.m_10437_(true)), true);
      ReloadCommand.m_138235_(list.stream().map(Pack::m_10446_).collect(Collectors.toList()), p_136826_);
      return list.size();
   }

   private static int m_136823_(CommandSourceStack p_136824_) {
      return m_136865_(p_136824_) + m_136854_(p_136824_);
   }

   private static int m_136854_(CommandSourceStack p_136855_) {
      PackRepository packrepository = p_136855_.m_81377_().m_129891_();
      packrepository.m_10506_();
      Collection<? extends Pack> collection = packrepository.m_10524_();
      Collection<? extends Pack> collection1 = packrepository.m_10519_();
      List<Pack> list = collection1.stream().filter((p_136836_) -> {
         return !collection.contains(p_136836_);
      }).collect(Collectors.toList());
      if (list.isEmpty()) {
         p_136855_.m_81354_(new TranslatableComponent("commands.datapack.list.available.none"), false);
      } else {
         p_136855_.m_81354_(new TranslatableComponent("commands.datapack.list.available.success", list.size(), ComponentUtils.m_178440_(list, (p_136844_) -> {
            return p_136844_.m_10437_(false);
         })), false);
      }

      return list.size();
   }

   private static int m_136865_(CommandSourceStack p_136866_) {
      PackRepository packrepository = p_136866_.m_81377_().m_129891_();
      packrepository.m_10506_();
      Collection<? extends Pack> collection = packrepository.m_10524_();
      if (collection.isEmpty()) {
         p_136866_.m_81354_(new TranslatableComponent("commands.datapack.list.enabled.none"), false);
      } else {
         p_136866_.m_81354_(new TranslatableComponent("commands.datapack.list.enabled.success", collection.size(), ComponentUtils.m_178440_(collection, (p_136807_) -> {
            return p_136807_.m_10437_(true);
         })), false);
      }

      return collection.size();
   }

   private static Pack m_136815_(CommandContext<CommandSourceStack> p_136816_, String p_136817_, boolean p_136818_) throws CommandSyntaxException {
      String s = StringArgumentType.getString(p_136816_, p_136817_);
      PackRepository packrepository = p_136816_.getSource().m_81377_().m_129891_();
      Pack pack = packrepository.m_10507_(s);
      if (pack == null) {
         throw f_136800_.create(s);
      } else {
         boolean flag = packrepository.m_10524_().contains(pack);
         if (p_136818_ && flag) {
            throw f_136801_.create(s);
         } else if (!p_136818_ && !flag) {
            throw f_136802_.create(s);
         } else {
            return pack;
         }
      }
   }

   interface Inserter {
      void m_136883_(List<Pack> p_136884_, Pack p_136885_) throws CommandSyntaxException;
   }
}