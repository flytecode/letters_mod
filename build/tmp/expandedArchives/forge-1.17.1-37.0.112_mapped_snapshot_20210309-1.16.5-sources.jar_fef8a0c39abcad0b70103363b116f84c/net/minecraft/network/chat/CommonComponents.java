package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Collection;

public class CommonComponents {
   public static final Component f_130653_ = new TranslatableComponent("options.on");
   public static final Component f_130654_ = new TranslatableComponent("options.off");
   public static final Component f_130655_ = new TranslatableComponent("gui.done");
   public static final Component f_130656_ = new TranslatableComponent("gui.cancel");
   public static final Component f_130657_ = new TranslatableComponent("gui.yes");
   public static final Component f_130658_ = new TranslatableComponent("gui.no");
   public static final Component f_130659_ = new TranslatableComponent("gui.proceed");
   public static final Component f_130660_ = new TranslatableComponent("gui.back");
   public static final Component f_130661_ = new TranslatableComponent("connect.failed");
   public static final Component f_178388_ = new TextComponent("\n");
   public static final Component f_178389_ = new TextComponent(". ");

   public static Component m_130666_(boolean p_130667_) {
      return p_130667_ ? f_130653_ : f_130654_;
   }

   public static MutableComponent m_130663_(Component p_130664_, boolean p_130665_) {
      return new TranslatableComponent(p_130665_ ? "options.on.composed" : "options.off.composed", p_130664_);
   }

   public static MutableComponent m_178393_(Component p_178394_, Component p_178395_) {
      return new TranslatableComponent("options.generic_value", p_178394_, p_178395_);
   }

   public static MutableComponent m_178398_(Component p_178399_, Component p_178400_) {
      return (new TextComponent("")).m_7220_(p_178399_).m_7220_(f_178389_).m_7220_(p_178400_);
   }

   public static Component m_178396_(Component... p_178397_) {
      return m_178391_(Arrays.asList(p_178397_));
   }

   public static Component m_178391_(Collection<? extends Component> p_178392_) {
      return ComponentUtils.m_178433_(p_178392_, f_178388_);
   }
}