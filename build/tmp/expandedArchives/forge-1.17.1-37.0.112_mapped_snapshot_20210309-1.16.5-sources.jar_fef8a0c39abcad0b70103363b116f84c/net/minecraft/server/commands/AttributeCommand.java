package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeCommand {
   private static final SuggestionProvider<CommandSourceStack> f_136433_ = (p_136449_, p_136450_) -> {
      return SharedSuggestionProvider.m_82926_(Registry.f_122866_.m_6566_(), p_136450_);
   };
   private static final DynamicCommandExceptionType f_136434_ = new DynamicCommandExceptionType((p_136478_) -> {
      return new TranslatableComponent("commands.attribute.failed.entity", p_136478_);
   });
   private static final Dynamic2CommandExceptionType f_136435_ = new Dynamic2CommandExceptionType((p_136480_, p_136481_) -> {
      return new TranslatableComponent("commands.attribute.failed.no_attribute", p_136480_, p_136481_);
   });
   private static final Dynamic3CommandExceptionType f_136436_ = new Dynamic3CommandExceptionType((p_136497_, p_136498_, p_136499_) -> {
      return new TranslatableComponent("commands.attribute.failed.no_modifier", p_136498_, p_136497_, p_136499_);
   });
   private static final Dynamic3CommandExceptionType f_136437_ = new Dynamic3CommandExceptionType((p_136483_, p_136484_, p_136485_) -> {
      return new TranslatableComponent("commands.attribute.failed.modifier_already_present", p_136485_, p_136484_, p_136483_);
   });

   public static void m_136444_(CommandDispatcher<CommandSourceStack> p_136445_) {
      p_136445_.register(Commands.m_82127_("attribute").requires((p_136452_) -> {
         return p_136452_.m_6761_(2);
      }).then(Commands.m_82129_("target", EntityArgument.m_91449_()).then(Commands.m_82129_("attribute", ResourceLocationArgument.m_106984_()).suggests(f_136433_).then(Commands.m_82127_("get").executes((p_136522_) -> {
         return m_136453_(p_136522_.getSource(), EntityArgument.m_91452_(p_136522_, "target"), ResourceLocationArgument.m_107006_(p_136522_, "attribute"), 1.0D);
      }).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes((p_136520_) -> {
         return m_136453_(p_136520_.getSource(), EntityArgument.m_91452_(p_136520_, "target"), ResourceLocationArgument.m_107006_(p_136520_, "attribute"), DoubleArgumentType.getDouble(p_136520_, "scale"));
      }))).then(Commands.m_82127_("base").then(Commands.m_82127_("set").then(Commands.m_82129_("value", DoubleArgumentType.doubleArg()).executes((p_136518_) -> {
         return m_136502_(p_136518_.getSource(), EntityArgument.m_91452_(p_136518_, "target"), ResourceLocationArgument.m_107006_(p_136518_, "attribute"), DoubleArgumentType.getDouble(p_136518_, "value"));
      }))).then(Commands.m_82127_("get").executes((p_136516_) -> {
         return m_136491_(p_136516_.getSource(), EntityArgument.m_91452_(p_136516_, "target"), ResourceLocationArgument.m_107006_(p_136516_, "attribute"), 1.0D);
      }).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes((p_136514_) -> {
         return m_136491_(p_136514_.getSource(), EntityArgument.m_91452_(p_136514_, "target"), ResourceLocationArgument.m_107006_(p_136514_, "attribute"), DoubleArgumentType.getDouble(p_136514_, "scale"));
      })))).then(Commands.m_82127_("modifier").then(Commands.m_82127_("add").then(Commands.m_82129_("uuid", UuidArgument.m_113850_()).then(Commands.m_82129_("name", StringArgumentType.string()).then(Commands.m_82129_("value", DoubleArgumentType.doubleArg()).then(Commands.m_82127_("add").executes((p_136512_) -> {
         return m_136469_(p_136512_.getSource(), EntityArgument.m_91452_(p_136512_, "target"), ResourceLocationArgument.m_107006_(p_136512_, "attribute"), UuidArgument.m_113853_(p_136512_, "uuid"), StringArgumentType.getString(p_136512_, "name"), DoubleArgumentType.getDouble(p_136512_, "value"), AttributeModifier.Operation.ADDITION);
      })).then(Commands.m_82127_("multiply").executes((p_136510_) -> {
         return m_136469_(p_136510_.getSource(), EntityArgument.m_91452_(p_136510_, "target"), ResourceLocationArgument.m_107006_(p_136510_, "attribute"), UuidArgument.m_113853_(p_136510_, "uuid"), StringArgumentType.getString(p_136510_, "name"), DoubleArgumentType.getDouble(p_136510_, "value"), AttributeModifier.Operation.MULTIPLY_TOTAL);
      })).then(Commands.m_82127_("multiply_base").executes((p_136508_) -> {
         return m_136469_(p_136508_.getSource(), EntityArgument.m_91452_(p_136508_, "target"), ResourceLocationArgument.m_107006_(p_136508_, "attribute"), UuidArgument.m_113853_(p_136508_, "uuid"), StringArgumentType.getString(p_136508_, "name"), DoubleArgumentType.getDouble(p_136508_, "value"), AttributeModifier.Operation.MULTIPLY_BASE);
      })))))).then(Commands.m_82127_("remove").then(Commands.m_82129_("uuid", UuidArgument.m_113850_()).executes((p_136501_) -> {
         return m_136458_(p_136501_.getSource(), EntityArgument.m_91452_(p_136501_, "target"), ResourceLocationArgument.m_107006_(p_136501_, "attribute"), UuidArgument.m_113853_(p_136501_, "uuid"));
      }))).then(Commands.m_82127_("value").then(Commands.m_82127_("get").then(Commands.m_82129_("uuid", UuidArgument.m_113850_()).executes((p_136490_) -> {
         return m_136463_(p_136490_.getSource(), EntityArgument.m_91452_(p_136490_, "target"), ResourceLocationArgument.m_107006_(p_136490_, "attribute"), UuidArgument.m_113853_(p_136490_, "uuid"), 1.0D);
      }).then(Commands.m_82129_("scale", DoubleArgumentType.doubleArg()).executes((p_136447_) -> {
         return m_136463_(p_136447_.getSource(), EntityArgument.m_91452_(p_136447_, "target"), ResourceLocationArgument.m_107006_(p_136447_, "attribute"), UuidArgument.m_113853_(p_136447_, "uuid"), DoubleArgumentType.getDouble(p_136447_, "scale"));
      })))))))));
   }

   private static AttributeInstance m_136441_(Entity p_136442_, Attribute p_136443_) throws CommandSyntaxException {
      AttributeInstance attributeinstance = m_136439_(p_136442_).m_21204_().m_22146_(p_136443_);
      if (attributeinstance == null) {
         throw f_136435_.create(p_136442_.m_7755_(), new TranslatableComponent(p_136443_.m_22087_()));
      } else {
         return attributeinstance;
      }
   }

   private static LivingEntity m_136439_(Entity p_136440_) throws CommandSyntaxException {
      if (!(p_136440_ instanceof LivingEntity)) {
         throw f_136434_.create(p_136440_.m_7755_());
      } else {
         return (LivingEntity)p_136440_;
      }
   }

   private static LivingEntity m_136486_(Entity p_136487_, Attribute p_136488_) throws CommandSyntaxException {
      LivingEntity livingentity = m_136439_(p_136487_);
      if (!livingentity.m_21204_().m_22171_(p_136488_)) {
         throw f_136435_.create(p_136487_.m_7755_(), new TranslatableComponent(p_136488_.m_22087_()));
      } else {
         return livingentity;
      }
   }

   private static int m_136453_(CommandSourceStack p_136454_, Entity p_136455_, Attribute p_136456_, double p_136457_) throws CommandSyntaxException {
      LivingEntity livingentity = m_136486_(p_136455_, p_136456_);
      double d0 = livingentity.m_21133_(p_136456_);
      p_136454_.m_81354_(new TranslatableComponent("commands.attribute.value.get.success", new TranslatableComponent(p_136456_.m_22087_()), p_136455_.m_7755_(), d0), false);
      return (int)(d0 * p_136457_);
   }

   private static int m_136491_(CommandSourceStack p_136492_, Entity p_136493_, Attribute p_136494_, double p_136495_) throws CommandSyntaxException {
      LivingEntity livingentity = m_136486_(p_136493_, p_136494_);
      double d0 = livingentity.m_21172_(p_136494_);
      p_136492_.m_81354_(new TranslatableComponent("commands.attribute.base_value.get.success", new TranslatableComponent(p_136494_.m_22087_()), p_136493_.m_7755_(), d0), false);
      return (int)(d0 * p_136495_);
   }

   private static int m_136463_(CommandSourceStack p_136464_, Entity p_136465_, Attribute p_136466_, UUID p_136467_, double p_136468_) throws CommandSyntaxException {
      LivingEntity livingentity = m_136486_(p_136465_, p_136466_);
      AttributeMap attributemap = livingentity.m_21204_();
      if (!attributemap.m_22154_(p_136466_, p_136467_)) {
         throw f_136436_.create(p_136465_.m_7755_(), new TranslatableComponent(p_136466_.m_22087_()), p_136467_);
      } else {
         double d0 = attributemap.m_22173_(p_136466_, p_136467_);
         p_136464_.m_81354_(new TranslatableComponent("commands.attribute.modifier.value.get.success", p_136467_, new TranslatableComponent(p_136466_.m_22087_()), p_136465_.m_7755_(), d0), false);
         return (int)(d0 * p_136468_);
      }
   }

   private static int m_136502_(CommandSourceStack p_136503_, Entity p_136504_, Attribute p_136505_, double p_136506_) throws CommandSyntaxException {
      m_136441_(p_136504_, p_136505_).m_22100_(p_136506_);
      p_136503_.m_81354_(new TranslatableComponent("commands.attribute.base_value.set.success", new TranslatableComponent(p_136505_.m_22087_()), p_136504_.m_7755_(), p_136506_), false);
      return 1;
   }

   private static int m_136469_(CommandSourceStack p_136470_, Entity p_136471_, Attribute p_136472_, UUID p_136473_, String p_136474_, double p_136475_, AttributeModifier.Operation p_136476_) throws CommandSyntaxException {
      AttributeInstance attributeinstance = m_136441_(p_136471_, p_136472_);
      AttributeModifier attributemodifier = new AttributeModifier(p_136473_, p_136474_, p_136475_, p_136476_);
      if (attributeinstance.m_22109_(attributemodifier)) {
         throw f_136437_.create(p_136471_.m_7755_(), new TranslatableComponent(p_136472_.m_22087_()), p_136473_);
      } else {
         attributeinstance.m_22125_(attributemodifier);
         p_136470_.m_81354_(new TranslatableComponent("commands.attribute.modifier.add.success", p_136473_, new TranslatableComponent(p_136472_.m_22087_()), p_136471_.m_7755_()), false);
         return 1;
      }
   }

   private static int m_136458_(CommandSourceStack p_136459_, Entity p_136460_, Attribute p_136461_, UUID p_136462_) throws CommandSyntaxException {
      AttributeInstance attributeinstance = m_136441_(p_136460_, p_136461_);
      if (attributeinstance.m_22127_(p_136462_)) {
         p_136459_.m_81354_(new TranslatableComponent("commands.attribute.modifier.remove.success", p_136462_, new TranslatableComponent(p_136461_.m_22087_()), p_136460_.m_7755_()), false);
         return 1;
      } else {
         throw f_136436_.create(p_136460_.m_7755_(), new TranslatableComponent(p_136461_.m_22087_()), p_136462_);
      }
   }
}