package net.minecraft.network.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.world.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectorComponent extends BaseComponent implements ContextAwareComponent {
   private static final Logger f_131081_ = LogManager.getLogger();
   private final String f_131082_;
   @Nullable
   private final EntitySelector f_131083_;
   protected final Optional<Component> f_178514_;

   public SelectorComponent(String p_178516_, Optional<Component> p_178517_) {
      this.f_131082_ = p_178516_;
      this.f_178514_ = p_178517_;
      EntitySelector entityselector = null;

      try {
         EntitySelectorParser entityselectorparser = new EntitySelectorParser(new StringReader(p_178516_));
         entityselector = entityselectorparser.m_121377_();
      } catch (CommandSyntaxException commandsyntaxexception) {
         f_131081_.warn("Invalid selector component: {}: {}", p_178516_, commandsyntaxexception.getMessage());
      }

      this.f_131083_ = entityselector;
   }

   public String m_131096_() {
      return this.f_131082_;
   }

   @Nullable
   public EntitySelector m_178518_() {
      return this.f_131083_;
   }

   public Optional<Component> m_178519_() {
      return this.f_178514_;
   }

   public MutableComponent m_5638_(@Nullable CommandSourceStack p_131089_, @Nullable Entity p_131090_, int p_131091_) throws CommandSyntaxException {
      if (p_131089_ != null && this.f_131083_ != null) {
         Optional<? extends Component> optional = ComponentUtils.m_178424_(p_131089_, this.f_178514_, p_131090_, p_131091_);
         return ComponentUtils.m_178429_(this.f_131083_.m_121160_(p_131089_), optional, Entity::m_5446_);
      } else {
         return new TextComponent("");
      }
   }

   public String m_6111_() {
      return this.f_131082_;
   }

   public SelectorComponent m_6879_() {
      return new SelectorComponent(this.f_131082_, this.f_178514_);
   }

   public boolean equals(Object p_131094_) {
      if (this == p_131094_) {
         return true;
      } else if (!(p_131094_ instanceof SelectorComponent)) {
         return false;
      } else {
         SelectorComponent selectorcomponent = (SelectorComponent)p_131094_;
         return this.f_131082_.equals(selectorcomponent.f_131082_) && super.equals(p_131094_);
      }
   }

   public String toString() {
      return "SelectorComponent{pattern='" + this.f_131082_ + "', siblings=" + this.f_130578_ + ", style=" + this.m_7383_() + "}";
   }
}