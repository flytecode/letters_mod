package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;

public class ComponentUtils {
   public static final String f_178419_ = ", ";
   public static final Component f_178420_ = (new TextComponent(", ")).m_130940_(ChatFormatting.GRAY);
   public static final Component f_178421_ = new TextComponent(", ");

   public static MutableComponent m_130750_(MutableComponent p_130751_, Style p_130752_) {
      if (p_130752_.m_131179_()) {
         return p_130751_;
      } else {
         Style style = p_130751_.m_7383_();
         if (style.m_131179_()) {
            return p_130751_.m_6270_(p_130752_);
         } else {
            return style.equals(p_130752_) ? p_130751_ : p_130751_.m_6270_(style.m_131146_(p_130752_));
         }
      }
   }

   public static Optional<MutableComponent> m_178424_(@Nullable CommandSourceStack p_178425_, Optional<Component> p_178426_, @Nullable Entity p_178427_, int p_178428_) throws CommandSyntaxException {
      return p_178426_.isPresent() ? Optional.of(m_130731_(p_178425_, p_178426_.get(), p_178427_, p_178428_)) : Optional.empty();
   }

   public static MutableComponent m_130731_(@Nullable CommandSourceStack p_130732_, Component p_130733_, @Nullable Entity p_130734_, int p_130735_) throws CommandSyntaxException {
      if (p_130735_ > 100) {
         return p_130733_.m_6881_();
      } else {
         MutableComponent mutablecomponent = p_130733_ instanceof ContextAwareComponent ? ((ContextAwareComponent)p_130733_).m_5638_(p_130732_, p_130734_, p_130735_ + 1) : p_130733_.m_6879_();

         for(Component component : p_130733_.m_7360_()) {
            mutablecomponent.m_7220_(m_130731_(p_130732_, component, p_130734_, p_130735_ + 1));
         }

         return mutablecomponent.m_130948_(m_130736_(p_130732_, p_130733_.m_7383_(), p_130734_, p_130735_));
      }
   }

   private static Style m_130736_(@Nullable CommandSourceStack p_130737_, Style p_130738_, @Nullable Entity p_130739_, int p_130740_) throws CommandSyntaxException {
      HoverEvent hoverevent = p_130738_.m_131186_();
      if (hoverevent != null) {
         Component component = hoverevent.m_130823_(HoverEvent.Action.f_130831_);
         if (component != null) {
            HoverEvent hoverevent1 = new HoverEvent(HoverEvent.Action.f_130831_, m_130731_(p_130737_, component, p_130739_, p_130740_ + 1));
            return p_130738_.m_131144_(hoverevent1);
         }
      }

      return p_130738_;
   }

   public static Component m_130727_(GameProfile p_130728_) {
      if (p_130728_.getName() != null) {
         return new TextComponent(p_130728_.getName());
      } else {
         return p_130728_.getId() != null ? new TextComponent(p_130728_.getId().toString()) : new TextComponent("(unknown)");
      }
   }

   public static Component m_130743_(Collection<String> p_130744_) {
      return m_130745_(p_130744_, (p_130742_) -> {
         return (new TextComponent(p_130742_)).m_130940_(ChatFormatting.GREEN);
      });
   }

   public static <T extends Comparable<T>> Component m_130745_(Collection<T> p_130746_, Function<T, Component> p_130747_) {
      if (p_130746_.isEmpty()) {
         return TextComponent.f_131282_;
      } else if (p_130746_.size() == 1) {
         return p_130747_.apply(p_130746_.iterator().next());
      } else {
         List<T> list = Lists.newArrayList(p_130746_);
         list.sort(Comparable::compareTo);
         return m_178440_(list, p_130747_);
      }
   }

   public static <T> Component m_178440_(Collection<? extends T> p_178441_, Function<T, Component> p_178442_) {
      return m_178436_(p_178441_, f_178420_, p_178442_);
   }

   public static <T> MutableComponent m_178429_(Collection<? extends T> p_178430_, Optional<? extends Component> p_178431_, Function<T, Component> p_178432_) {
      return m_178436_(p_178430_, DataFixUtils.orElse(p_178431_, f_178420_), p_178432_);
   }

   public static Component m_178433_(Collection<? extends Component> p_178434_, Component p_178435_) {
      return m_178436_(p_178434_, p_178435_, Function.identity());
   }

   public static <T> MutableComponent m_178436_(Collection<? extends T> p_178437_, Component p_178438_, Function<T, Component> p_178439_) {
      if (p_178437_.isEmpty()) {
         return new TextComponent("");
      } else if (p_178437_.size() == 1) {
         return p_178439_.apply(p_178437_.iterator().next()).m_6881_();
      } else {
         MutableComponent mutablecomponent = new TextComponent("");
         boolean flag = true;

         for(T t : p_178437_) {
            if (!flag) {
               mutablecomponent.m_7220_(p_178438_);
            }

            mutablecomponent.m_7220_(p_178439_.apply(t));
            flag = false;
         }

         return mutablecomponent;
      }
   }

   public static MutableComponent m_130748_(Component p_130749_) {
      return new TranslatableComponent("chat.square_brackets", p_130749_);
   }

   public static Component m_130729_(Message p_130730_) {
      return (Component)(p_130730_ instanceof Component ? (Component)p_130730_ : new TextComponent(p_130730_.getString()));
   }
}