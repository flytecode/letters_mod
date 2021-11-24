package net.minecraft.server;

import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.locale.Language;
import net.minecraft.tags.StaticTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap {
   public static final PrintStream f_135866_ = System.out;
   private static volatile boolean f_135867_;
   private static final Logger f_135868_ = LogManager.getLogger();

   public static void m_135870_() {
      if (!f_135867_) {
         f_135867_ = true;
         if (Registry.f_122897_.m_6566_().isEmpty()) {
            throw new IllegalStateException("Unable to load registries");
         } else {
            FireBlock.m_53484_();
            ComposterBlock.m_51988_();
            if (EntityType.m_20613_(EntityType.f_20532_) == null) {
               throw new IllegalStateException("Failed loading EntityTypes");
            } else {
               PotionBrewing.m_43499_();
               EntitySelectorOptions.m_121426_();
               DispenseItemBehavior.m_123402_();
               CauldronInteraction.m_175649_();
               ArgumentTypes.m_121586_();
               StaticTags.m_13282_();
               net.minecraftforge.registries.GameData.vanillaSnapshot();
               if (false) // skip redirectOutputToLog, Forge already redirects stdout and stderr output to log so that they print with more context
               m_135890_();
            }
         }
      }
   }

   private static <T> void m_135871_(Iterable<T> p_135872_, Function<T, String> p_135873_, Set<String> p_135874_) {
      Language language = Language.m_128107_();
      p_135872_.forEach((p_135883_) -> {
         String s = p_135873_.apply(p_135883_);
         if (!language.m_6722_(s)) {
            p_135874_.add(s);
         }

      });
   }

   private static void m_135877_(final Set<String> p_135878_) {
      final Language language = Language.m_128107_();
      GameRules.m_46164_(new GameRules.GameRuleTypeVisitor() {
         public <T extends GameRules.Value<T>> void m_6889_(GameRules.Key<T> p_135897_, GameRules.Type<T> p_135898_) {
            if (!language.m_6722_(p_135897_.m_46331_())) {
               p_135878_.add(p_135897_.m_46328_());
            }

         }
      });
   }

   public static Set<String> m_135886_() {
      Set<String> set = new TreeSet<>();
      m_135871_(Registry.f_122866_, Attribute::m_22087_, set);
      m_135871_(Registry.f_122826_, EntityType::m_20675_, set);
      m_135871_(Registry.f_122823_, MobEffect::m_19481_, set);
      m_135871_(Registry.f_122827_, Item::m_5524_, set);
      m_135871_(Registry.f_122825_, Enchantment::m_44704_, set);
      m_135871_(Registry.f_122824_, Block::m_7705_, set);
      m_135871_(Registry.f_122832_, (p_135885_) -> {
         return "stat." + p_135885_.toString().replace(':', '.');
      }, set);
      m_135877_(set);
      return set;
   }

   public static void m_179912_(Supplier<String> p_179913_) {
      if (!f_135867_) {
         throw m_179916_(p_179913_);
      }
   }

   private static RuntimeException m_179916_(Supplier<String> p_179917_) {
      try {
         String s = p_179917_.get();
         return new IllegalArgumentException("Not bootstrapped (called from " + s + ")");
      } catch (Exception exception) {
         RuntimeException runtimeexception = new IllegalArgumentException("Not bootstrapped (failed to resolve location)");
         runtimeexception.addSuppressed(exception);
         return runtimeexception;
      }
   }

   public static void m_135889_() {
      m_179912_(() -> {
         return "validate";
      });
      if (SharedConstants.f_136183_) {
         m_135886_().forEach((p_179915_) -> {
            f_135868_.error("Missing translations: {}", (Object)p_179915_);
         });
         Commands.m_82138_();
      }

   }

   private static void m_135890_() {
      if (f_135868_.isDebugEnabled()) {
         System.setErr(new DebugLoggedPrintStream("STDERR", System.err));
         System.setOut(new DebugLoggedPrintStream("STDOUT", f_135866_));
      } else {
         System.setErr(new LoggedPrintStream("STDERR", System.err));
         System.setOut(new LoggedPrintStream("STDOUT", f_135866_));
      }

   }

   public static void m_135875_(String p_135876_) {
      f_135866_.println(p_135876_);
   }
}
