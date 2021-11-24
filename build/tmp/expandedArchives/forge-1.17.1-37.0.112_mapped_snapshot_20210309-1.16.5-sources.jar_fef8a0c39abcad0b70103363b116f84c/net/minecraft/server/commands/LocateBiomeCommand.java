package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class LocateBiomeCommand {
   public static final DynamicCommandExceptionType f_137833_ = new DynamicCommandExceptionType((p_137850_) -> {
      return new TranslatableComponent("commands.locatebiome.invalid", p_137850_);
   });
   private static final DynamicCommandExceptionType f_137834_ = new DynamicCommandExceptionType((p_137846_) -> {
      return new TranslatableComponent("commands.locatebiome.notFound", p_137846_);
   });
   private static final int f_180387_ = 6400;
   private static final int f_180388_ = 8;

   public static void m_137836_(CommandDispatcher<CommandSourceStack> p_137837_) {
      p_137837_.register(Commands.m_82127_("locatebiome").requires((p_137841_) -> {
         return p_137841_.m_6761_(2);
      }).then(Commands.m_82129_("biome", ResourceLocationArgument.m_106984_()).suggests(SuggestionProviders.f_121644_).executes((p_137839_) -> {
         return m_137842_(p_137839_.getSource(), p_137839_.getArgument("biome", ResourceLocation.class));
      })));
   }

   private static int m_137842_(CommandSourceStack p_137843_, ResourceLocation p_137844_) throws CommandSyntaxException {
      Biome biome = p_137843_.m_81377_().m_129911_().m_175515_(Registry.f_122885_).m_6612_(p_137844_).orElseThrow(() -> {
         return f_137833_.create(p_137844_);
      });
      BlockPos blockpos = new BlockPos(p_137843_.m_81371_());
      BlockPos blockpos1 = p_137843_.m_81372_().m_8705_(biome, blockpos, 6400, 8);
      String s = p_137844_.toString();
      if (blockpos1 == null) {
         throw f_137834_.create(s);
      } else {
         return LocateCommand.m_137865_(p_137843_, s, blockpos, blockpos1, "commands.locatebiome.success");
      }
   }
}