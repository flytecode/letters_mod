package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class LocateCommand {
   private static final SimpleCommandExceptionType f_137851_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.locate.failed"));

   public static void m_137858_(CommandDispatcher<CommandSourceStack> p_137859_) {
      LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.m_82127_("locate").requires((p_137861_) -> {
         return p_137861_.m_6761_(2);
      });

      if (false)
      for(Entry<String, StructureFeature<?>> entry : StructureFeature.f_67012_.entrySet()) {
         literalargumentbuilder = literalargumentbuilder.then(Commands.m_82127_(entry.getKey()).executes((p_137876_) -> {
            return m_137862_(p_137876_.getSource(), entry.getValue());
         }));
      }
      else {
      for (StructureFeature<?> structurefeatureFeature : net.minecraftforge.registries.ForgeRegistries.STRUCTURE_FEATURES) {
         String name = structurefeatureFeature.getRegistryName().toString().replace("minecraft:", "");
         literalargumentbuilder = literalargumentbuilder.then(Commands.m_82127_(name)
               .executes(ctx -> m_137862_(ctx.getSource(), structurefeatureFeature)));
      }}

      p_137859_.register(literalargumentbuilder);
   }

   private static int m_137862_(CommandSourceStack p_137863_, StructureFeature<?> p_137864_) throws CommandSyntaxException {
      BlockPos blockpos = new BlockPos(p_137863_.m_81371_());
      BlockPos blockpos1 = p_137863_.m_81372_().m_8717_(p_137864_, blockpos, 100, false);
      if (blockpos1 == null) {
         throw f_137851_.create();
      } else {
         return m_137865_(p_137863_, p_137864_.m_67098_(), blockpos, blockpos1, "commands.locate.success");
      }
   }

   public static int m_137865_(CommandSourceStack p_137866_, String p_137867_, BlockPos p_137868_, BlockPos p_137869_, String p_137870_) {
      int i = Mth.m_14143_(m_137853_(p_137868_.m_123341_(), p_137868_.m_123343_(), p_137869_.m_123341_(), p_137869_.m_123343_()));
      Component component = ComponentUtils.m_130748_(new TranslatableComponent("chat.coordinates", p_137869_.m_123341_(), "~", p_137869_.m_123343_())).m_130938_((p_137873_) -> {
         return p_137873_.m_131140_(ChatFormatting.GREEN).m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + p_137869_.m_123341_() + " ~ " + p_137869_.m_123343_())).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, new TranslatableComponent("chat.coordinates.tooltip")));
      });
      p_137866_.m_81354_(new TranslatableComponent(p_137870_, p_137867_, component, i), false);
      return i;
   }

   private static float m_137853_(int p_137854_, int p_137855_, int p_137856_, int p_137857_) {
      int i = p_137856_ - p_137854_;
      int j = p_137857_ - p_137855_;
      return Mth.m_14116_((float)(i * i + j * j));
   }
}
