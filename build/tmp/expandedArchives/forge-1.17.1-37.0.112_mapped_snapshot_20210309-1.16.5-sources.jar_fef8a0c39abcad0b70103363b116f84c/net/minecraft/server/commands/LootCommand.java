package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LootCommand {
   public static final SuggestionProvider<CommandSourceStack> f_137877_ = (p_137910_, p_137911_) -> {
      LootTables loottables = p_137910_.getSource().m_81377_().m_129898_();
      return SharedSuggestionProvider.m_82926_(loottables.m_79195_(), p_137911_);
   };
   private static final DynamicCommandExceptionType f_137878_ = new DynamicCommandExceptionType((p_137999_) -> {
      return new TranslatableComponent("commands.drop.no_held_items", p_137999_);
   });
   private static final DynamicCommandExceptionType f_137879_ = new DynamicCommandExceptionType((p_137977_) -> {
      return new TranslatableComponent("commands.drop.no_loot_table", p_137977_);
   });

   public static void m_137897_(CommandDispatcher<CommandSourceStack> p_137898_) {
      p_137898_.register(m_137902_(Commands.m_82127_("loot").requires((p_137937_) -> {
         return p_137937_.m_6761_(2);
      }), (p_137900_, p_137901_) -> {
         return p_137900_.then(Commands.m_82127_("fish").then(Commands.m_82129_("loot_table", ResourceLocationArgument.m_106984_()).suggests(f_137877_).then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes((p_180421_) -> {
            return m_137926_(p_180421_, ResourceLocationArgument.m_107011_(p_180421_, "loot_table"), BlockPosArgument.m_118242_(p_180421_, "pos"), ItemStack.f_41583_, p_137901_);
         }).then(Commands.m_82129_("tool", ItemArgument.m_120960_()).executes((p_180418_) -> {
            return m_137926_(p_180418_, ResourceLocationArgument.m_107011_(p_180418_, "loot_table"), BlockPosArgument.m_118242_(p_180418_, "pos"), ItemArgument.m_120963_(p_180418_, "tool").m_120980_(1, false), p_137901_);
         })).then(Commands.m_82127_("mainhand").executes((p_180415_) -> {
            return m_137926_(p_180415_, ResourceLocationArgument.m_107011_(p_180415_, "loot_table"), BlockPosArgument.m_118242_(p_180415_, "pos"), m_137938_(p_180415_.getSource(), EquipmentSlot.MAINHAND), p_137901_);
         })).then(Commands.m_82127_("offhand").executes((p_180412_) -> {
            return m_137926_(p_180412_, ResourceLocationArgument.m_107011_(p_180412_, "loot_table"), BlockPosArgument.m_118242_(p_180412_, "pos"), m_137938_(p_180412_.getSource(), EquipmentSlot.OFFHAND), p_137901_);
         }))))).then(Commands.m_82127_("loot").then(Commands.m_82129_("loot_table", ResourceLocationArgument.m_106984_()).suggests(f_137877_).executes((p_180409_) -> {
            return m_137932_(p_180409_, ResourceLocationArgument.m_107011_(p_180409_, "loot_table"), p_137901_);
         }))).then(Commands.m_82127_("kill").then(Commands.m_82129_("target", EntityArgument.m_91449_()).executes((p_180406_) -> {
            return m_137905_(p_180406_, EntityArgument.m_91452_(p_180406_, "target"), p_137901_);
         }))).then(Commands.m_82127_("mine").then(Commands.m_82129_("pos", BlockPosArgument.m_118239_()).executes((p_180403_) -> {
            return m_137912_(p_180403_, BlockPosArgument.m_118242_(p_180403_, "pos"), ItemStack.f_41583_, p_137901_);
         }).then(Commands.m_82129_("tool", ItemArgument.m_120960_()).executes((p_180400_) -> {
            return m_137912_(p_180400_, BlockPosArgument.m_118242_(p_180400_, "pos"), ItemArgument.m_120963_(p_180400_, "tool").m_120980_(1, false), p_137901_);
         })).then(Commands.m_82127_("mainhand").executes((p_180397_) -> {
            return m_137912_(p_180397_, BlockPosArgument.m_118242_(p_180397_, "pos"), m_137938_(p_180397_.getSource(), EquipmentSlot.MAINHAND), p_137901_);
         })).then(Commands.m_82127_("offhand").executes((p_180394_) -> {
            return m_137912_(p_180394_, BlockPosArgument.m_118242_(p_180394_, "pos"), m_137938_(p_180394_.getSource(), EquipmentSlot.OFFHAND), p_137901_);
         }))));
      }));
   }

   private static <T extends ArgumentBuilder<CommandSourceStack, T>> T m_137902_(T p_137903_, LootCommand.TailProvider p_137904_) {
      return p_137903_.then(Commands.m_82127_("replace").then(Commands.m_82127_("entity").then(Commands.m_82129_("entities", EntityArgument.m_91460_()).then(p_137904_.m_138053_(Commands.m_82129_("slot", SlotArgument.m_111276_()), (p_138032_, p_138033_, p_138034_) -> {
         return m_137978_(EntityArgument.m_91461_(p_138032_, "entities"), SlotArgument.m_111279_(p_138032_, "slot"), p_138033_.size(), p_138033_, p_138034_);
      }).then(p_137904_.m_138053_(Commands.m_82129_("count", IntegerArgumentType.integer(0)), (p_138025_, p_138026_, p_138027_) -> {
         return m_137978_(EntityArgument.m_91461_(p_138025_, "entities"), SlotArgument.m_111279_(p_138025_, "slot"), IntegerArgumentType.getInteger(p_138025_, "count"), p_138026_, p_138027_);
      }))))).then(Commands.m_82127_("block").then(Commands.m_82129_("targetPos", BlockPosArgument.m_118239_()).then(p_137904_.m_138053_(Commands.m_82129_("slot", SlotArgument.m_111276_()), (p_138018_, p_138019_, p_138020_) -> {
         return m_137953_(p_138018_.getSource(), BlockPosArgument.m_118242_(p_138018_, "targetPos"), SlotArgument.m_111279_(p_138018_, "slot"), p_138019_.size(), p_138019_, p_138020_);
      }).then(p_137904_.m_138053_(Commands.m_82129_("count", IntegerArgumentType.integer(0)), (p_138011_, p_138012_, p_138013_) -> {
         return m_137953_(p_138011_.getSource(), BlockPosArgument.m_118242_(p_138011_, "targetPos"), IntegerArgumentType.getInteger(p_138011_, "slot"), IntegerArgumentType.getInteger(p_138011_, "count"), p_138012_, p_138013_);
      })))))).then(Commands.m_82127_("insert").then(p_137904_.m_138053_(Commands.m_82129_("targetPos", BlockPosArgument.m_118239_()), (p_138004_, p_138005_, p_138006_) -> {
         return m_137960_(p_138004_.getSource(), BlockPosArgument.m_118242_(p_138004_, "targetPos"), p_138005_, p_138006_);
      }))).then(Commands.m_82127_("give").then(p_137904_.m_138053_(Commands.m_82129_("players", EntityArgument.m_91470_()), (p_137992_, p_137993_, p_137994_) -> {
         return m_137984_(EntityArgument.m_91477_(p_137992_, "players"), p_137993_, p_137994_);
      }))).then(Commands.m_82127_("spawn").then(p_137904_.m_138053_(Commands.m_82129_("targetPos", Vec3Argument.m_120841_()), (p_137918_, p_137919_, p_137920_) -> {
         return m_137945_(p_137918_.getSource(), Vec3Argument.m_120844_(p_137918_, "targetPos"), p_137919_, p_137920_);
      })));
   }

   private static Container m_137950_(CommandSourceStack p_137951_, BlockPos p_137952_) throws CommandSyntaxException {
      BlockEntity blockentity = p_137951_.m_81372_().m_7702_(p_137952_);
      if (!(blockentity instanceof Container)) {
         throw ItemCommands.f_180236_.create(p_137952_.m_123341_(), p_137952_.m_123342_(), p_137952_.m_123343_());
      } else {
         return (Container)blockentity;
      }
   }

   private static int m_137960_(CommandSourceStack p_137961_, BlockPos p_137962_, List<ItemStack> p_137963_, LootCommand.Callback p_137964_) throws CommandSyntaxException {
      Container container = m_137950_(p_137961_, p_137962_);
      List<ItemStack> list = Lists.newArrayListWithCapacity(p_137963_.size());

      for(ItemStack itemstack : p_137963_) {
         if (m_137885_(container, itemstack.m_41777_())) {
            container.m_6596_();
            list.add(itemstack);
         }
      }

      p_137964_.m_138047_(list);
      return list.size();
   }

   private static boolean m_137885_(Container p_137886_, ItemStack p_137887_) {
      boolean flag = false;

      for(int i = 0; i < p_137886_.m_6643_() && !p_137887_.m_41619_(); ++i) {
         ItemStack itemstack = p_137886_.m_8020_(i);
         if (p_137886_.m_7013_(i, p_137887_)) {
            if (itemstack.m_41619_()) {
               p_137886_.m_6836_(i, p_137887_);
               flag = true;
               break;
            }

            if (m_137894_(itemstack, p_137887_)) {
               int j = p_137887_.m_41741_() - itemstack.m_41613_();
               int k = Math.min(p_137887_.m_41613_(), j);
               p_137887_.m_41774_(k);
               itemstack.m_41769_(k);
               flag = true;
            }
         }
      }

      return flag;
   }

   private static int m_137953_(CommandSourceStack p_137954_, BlockPos p_137955_, int p_137956_, int p_137957_, List<ItemStack> p_137958_, LootCommand.Callback p_137959_) throws CommandSyntaxException {
      Container container = m_137950_(p_137954_, p_137955_);
      int i = container.m_6643_();
      if (p_137956_ >= 0 && p_137956_ < i) {
         List<ItemStack> list = Lists.newArrayListWithCapacity(p_137958_.size());

         for(int j = 0; j < p_137957_; ++j) {
            int k = p_137956_ + j;
            ItemStack itemstack = j < p_137958_.size() ? p_137958_.get(j) : ItemStack.f_41583_;
            if (container.m_7013_(k, itemstack)) {
               container.m_6836_(k, itemstack);
               list.add(itemstack);
            }
         }

         p_137959_.m_138047_(list);
         return list.size();
      } else {
         throw ItemCommands.f_180237_.create(p_137956_);
      }
   }

   private static boolean m_137894_(ItemStack p_137895_, ItemStack p_137896_) {
      return p_137895_.m_150930_(p_137896_.m_41720_()) && p_137895_.m_41773_() == p_137896_.m_41773_() && p_137895_.m_41613_() <= p_137895_.m_41741_() && Objects.equals(p_137895_.m_41783_(), p_137896_.m_41783_());
   }

   private static int m_137984_(Collection<ServerPlayer> p_137985_, List<ItemStack> p_137986_, LootCommand.Callback p_137987_) throws CommandSyntaxException {
      List<ItemStack> list = Lists.newArrayListWithCapacity(p_137986_.size());

      for(ItemStack itemstack : p_137986_) {
         for(ServerPlayer serverplayer : p_137985_) {
            if (serverplayer.m_150109_().m_36054_(itemstack.m_41777_())) {
               list.add(itemstack);
            }
         }
      }

      p_137987_.m_138047_(list);
      return list.size();
   }

   private static void m_137888_(Entity p_137889_, List<ItemStack> p_137890_, int p_137891_, int p_137892_, List<ItemStack> p_137893_) {
      for(int i = 0; i < p_137892_; ++i) {
         ItemStack itemstack = i < p_137890_.size() ? p_137890_.get(i) : ItemStack.f_41583_;
         SlotAccess slotaccess = p_137889_.m_141942_(p_137891_ + i);
         if (slotaccess != SlotAccess.f_147290_ && slotaccess.m_142104_(itemstack.m_41777_())) {
            p_137893_.add(itemstack);
         }
      }

   }

   private static int m_137978_(Collection<? extends Entity> p_137979_, int p_137980_, int p_137981_, List<ItemStack> p_137982_, LootCommand.Callback p_137983_) throws CommandSyntaxException {
      List<ItemStack> list = Lists.newArrayListWithCapacity(p_137982_.size());

      for(Entity entity : p_137979_) {
         if (entity instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer)entity;
            m_137888_(entity, p_137982_, p_137980_, p_137981_, list);
            serverplayer.f_36096_.m_38946_();
         } else {
            m_137888_(entity, p_137982_, p_137980_, p_137981_, list);
         }
      }

      p_137983_.m_138047_(list);
      return list.size();
   }

   private static int m_137945_(CommandSourceStack p_137946_, Vec3 p_137947_, List<ItemStack> p_137948_, LootCommand.Callback p_137949_) throws CommandSyntaxException {
      ServerLevel serverlevel = p_137946_.m_81372_();
      p_137948_.forEach((p_137884_) -> {
         ItemEntity itementity = new ItemEntity(serverlevel, p_137947_.f_82479_, p_137947_.f_82480_, p_137947_.f_82481_, p_137884_.m_41777_());
         itementity.m_32060_();
         serverlevel.m_7967_(itementity);
      });
      p_137949_.m_138047_(p_137948_);
      return p_137948_.size();
   }

   private static void m_137965_(CommandSourceStack p_137966_, List<ItemStack> p_137967_) {
      if (p_137967_.size() == 1) {
         ItemStack itemstack = p_137967_.get(0);
         p_137966_.m_81354_(new TranslatableComponent("commands.drop.success.single", itemstack.m_41613_(), itemstack.m_41611_()), false);
      } else {
         p_137966_.m_81354_(new TranslatableComponent("commands.drop.success.multiple", p_137967_.size()), false);
      }

   }

   private static void m_137968_(CommandSourceStack p_137969_, List<ItemStack> p_137970_, ResourceLocation p_137971_) {
      if (p_137970_.size() == 1) {
         ItemStack itemstack = p_137970_.get(0);
         p_137969_.m_81354_(new TranslatableComponent("commands.drop.success.single_with_table", itemstack.m_41613_(), itemstack.m_41611_(), p_137971_), false);
      } else {
         p_137969_.m_81354_(new TranslatableComponent("commands.drop.success.multiple_with_table", p_137970_.size(), p_137971_), false);
      }

   }

   private static ItemStack m_137938_(CommandSourceStack p_137939_, EquipmentSlot p_137940_) throws CommandSyntaxException {
      Entity entity = p_137939_.m_81374_();
      if (entity instanceof LivingEntity) {
         return ((LivingEntity)entity).m_6844_(p_137940_);
      } else {
         throw f_137878_.create(entity.m_5446_());
      }
   }

   private static int m_137912_(CommandContext<CommandSourceStack> p_137913_, BlockPos p_137914_, ItemStack p_137915_, LootCommand.DropConsumer p_137916_) throws CommandSyntaxException {
      CommandSourceStack commandsourcestack = p_137913_.getSource();
      ServerLevel serverlevel = commandsourcestack.m_81372_();
      BlockState blockstate = serverlevel.m_8055_(p_137914_);
      BlockEntity blockentity = serverlevel.m_7702_(p_137914_);
      LootContext.Builder lootcontext$builder = (new LootContext.Builder(serverlevel)).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_137914_)).m_78972_(LootContextParams.f_81461_, blockstate).m_78984_(LootContextParams.f_81462_, blockentity).m_78984_(LootContextParams.f_81455_, commandsourcestack.m_81373_()).m_78972_(LootContextParams.f_81463_, p_137915_);
      List<ItemStack> list = blockstate.m_60724_(lootcontext$builder);
      return p_137916_.m_138049_(p_137913_, list, (p_137944_) -> {
         m_137968_(commandsourcestack, p_137944_, blockstate.m_60734_().m_60589_());
      });
   }

   private static int m_137905_(CommandContext<CommandSourceStack> p_137906_, Entity p_137907_, LootCommand.DropConsumer p_137908_) throws CommandSyntaxException {
      if (!(p_137907_ instanceof LivingEntity)) {
         throw f_137879_.create(p_137907_.m_5446_());
      } else {
         ResourceLocation resourcelocation = ((LivingEntity)p_137907_).m_5743_();
         CommandSourceStack commandsourcestack = p_137906_.getSource();
         LootContext.Builder lootcontext$builder = new LootContext.Builder(commandsourcestack.m_81372_());
         Entity entity = commandsourcestack.m_81373_();
         if (entity instanceof Player) {
            lootcontext$builder.m_78972_(LootContextParams.f_81456_, (Player)entity);
         }

         lootcontext$builder.m_78972_(LootContextParams.f_81457_, DamageSource.f_19319_);
         lootcontext$builder.m_78984_(LootContextParams.f_81459_, entity);
         lootcontext$builder.m_78984_(LootContextParams.f_81458_, entity);
         lootcontext$builder.m_78972_(LootContextParams.f_81455_, p_137907_);
         lootcontext$builder.m_78972_(LootContextParams.f_81460_, commandsourcestack.m_81371_());
         LootTable loottable = commandsourcestack.m_81377_().m_129898_().m_79217_(resourcelocation);
         List<ItemStack> list = loottable.m_79129_(lootcontext$builder.m_78975_(LootContextParamSets.f_81415_));
         return p_137908_.m_138049_(p_137906_, list, (p_137975_) -> {
            m_137968_(commandsourcestack, p_137975_, resourcelocation);
         });
      }
   }

   private static int m_137932_(CommandContext<CommandSourceStack> p_137933_, ResourceLocation p_137934_, LootCommand.DropConsumer p_137935_) throws CommandSyntaxException {
      CommandSourceStack commandsourcestack = p_137933_.getSource();
      LootContext.Builder lootcontext$builder = (new LootContext.Builder(commandsourcestack.m_81372_())).m_78984_(LootContextParams.f_81455_, commandsourcestack.m_81373_()).m_78972_(LootContextParams.f_81460_, commandsourcestack.m_81371_());
      return m_137921_(p_137933_, p_137934_, lootcontext$builder.m_78975_(LootContextParamSets.f_81411_), p_137935_);
   }

   private static int m_137926_(CommandContext<CommandSourceStack> p_137927_, ResourceLocation p_137928_, BlockPos p_137929_, ItemStack p_137930_, LootCommand.DropConsumer p_137931_) throws CommandSyntaxException {
      CommandSourceStack commandsourcestack = p_137927_.getSource();
      LootContext lootcontext = (new LootContext.Builder(commandsourcestack.m_81372_())).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_137929_)).m_78972_(LootContextParams.f_81463_, p_137930_).m_78984_(LootContextParams.f_81455_, commandsourcestack.m_81373_()).m_78975_(LootContextParamSets.f_81414_);
      return m_137921_(p_137927_, p_137928_, lootcontext, p_137931_);
   }

   private static int m_137921_(CommandContext<CommandSourceStack> p_137922_, ResourceLocation p_137923_, LootContext p_137924_, LootCommand.DropConsumer p_137925_) throws CommandSyntaxException {
      CommandSourceStack commandsourcestack = p_137922_.getSource();
      LootTable loottable = commandsourcestack.m_81377_().m_129898_().m_79217_(p_137923_);
      List<ItemStack> list = loottable.m_79129_(p_137924_);
      return p_137925_.m_138049_(p_137922_, list, (p_137997_) -> {
         m_137965_(commandsourcestack, p_137997_);
      });
   }

   @FunctionalInterface
   interface Callback {
      void m_138047_(List<ItemStack> p_138048_) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface DropConsumer {
      int m_138049_(CommandContext<CommandSourceStack> p_138050_, List<ItemStack> p_138051_, LootCommand.Callback p_138052_) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface TailProvider {
      ArgumentBuilder<CommandSourceStack, ?> m_138053_(ArgumentBuilder<CommandSourceStack, ?> p_138054_, LootCommand.DropConsumer p_138055_);
   }
}