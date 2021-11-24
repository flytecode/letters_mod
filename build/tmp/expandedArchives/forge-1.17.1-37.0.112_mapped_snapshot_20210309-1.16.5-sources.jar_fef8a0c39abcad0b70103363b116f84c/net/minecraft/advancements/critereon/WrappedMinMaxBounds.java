package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;

public class WrappedMinMaxBounds {
   public static final WrappedMinMaxBounds f_75350_ = new WrappedMinMaxBounds((Float)null, (Float)null);
   public static final SimpleCommandExceptionType f_75351_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.range.ints"));
   private final Float f_75352_;
   private final Float f_75353_;

   public WrappedMinMaxBounds(@Nullable Float p_75356_, @Nullable Float p_75357_) {
      this.f_75352_ = p_75356_;
      this.f_75353_ = p_75357_;
   }

   public static WrappedMinMaxBounds m_164402_(float p_164403_) {
      return new WrappedMinMaxBounds(p_164403_, p_164403_);
   }

   public static WrappedMinMaxBounds m_164404_(float p_164405_, float p_164406_) {
      return new WrappedMinMaxBounds(p_164405_, p_164406_);
   }

   public static WrappedMinMaxBounds m_164414_(float p_164415_) {
      return new WrappedMinMaxBounds(p_164415_, (Float)null);
   }

   public static WrappedMinMaxBounds m_164417_(float p_164418_) {
      return new WrappedMinMaxBounds((Float)null, p_164418_);
   }

   public boolean m_164419_(float p_164420_) {
      if (this.f_75352_ != null && this.f_75353_ != null && this.f_75352_ > this.f_75353_ && this.f_75352_ > p_164420_ && this.f_75353_ < p_164420_) {
         return false;
      } else if (this.f_75352_ != null && this.f_75352_ > p_164420_) {
         return false;
      } else {
         return this.f_75353_ == null || !(this.f_75353_ < p_164420_);
      }
   }

   public boolean m_164400_(double p_164401_) {
      if (this.f_75352_ != null && this.f_75353_ != null && this.f_75352_ > this.f_75353_ && (double)(this.f_75352_ * this.f_75352_) > p_164401_ && (double)(this.f_75353_ * this.f_75353_) < p_164401_) {
         return false;
      } else if (this.f_75352_ != null && (double)(this.f_75352_ * this.f_75352_) > p_164401_) {
         return false;
      } else {
         return this.f_75353_ == null || !((double)(this.f_75353_ * this.f_75353_) < p_164401_);
      }
   }

   @Nullable
   public Float m_75358_() {
      return this.f_75352_;
   }

   @Nullable
   public Float m_75366_() {
      return this.f_75353_;
   }

   public JsonElement m_164416_() {
      if (this == f_75350_) {
         return JsonNull.INSTANCE;
      } else if (this.f_75352_ != null && this.f_75353_ != null && this.f_75352_.equals(this.f_75353_)) {
         return new JsonPrimitive(this.f_75352_);
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.f_75352_ != null) {
            jsonobject.addProperty("min", this.f_75352_);
         }

         if (this.f_75353_ != null) {
            jsonobject.addProperty("max", this.f_75352_);
         }

         return jsonobject;
      }
   }

   public static WrappedMinMaxBounds m_164407_(@Nullable JsonElement p_164408_) {
      if (p_164408_ != null && !p_164408_.isJsonNull()) {
         if (GsonHelper.m_13872_(p_164408_)) {
            float f2 = GsonHelper.m_13888_(p_164408_, "value");
            return new WrappedMinMaxBounds(f2, f2);
         } else {
            JsonObject jsonobject = GsonHelper.m_13918_(p_164408_, "value");
            Float f = jsonobject.has("min") ? GsonHelper.m_13915_(jsonobject, "min") : null;
            Float f1 = jsonobject.has("max") ? GsonHelper.m_13915_(jsonobject, "max") : null;
            return new WrappedMinMaxBounds(f, f1);
         }
      } else {
         return f_75350_;
      }
   }

   public static WrappedMinMaxBounds m_164409_(StringReader p_164410_, boolean p_164411_) throws CommandSyntaxException {
      return m_75359_(p_164410_, p_164411_, (p_164413_) -> {
         return p_164413_;
      });
   }

   public static WrappedMinMaxBounds m_75359_(StringReader p_75360_, boolean p_75361_, Function<Float, Float> p_75362_) throws CommandSyntaxException {
      if (!p_75360_.canRead()) {
         throw MinMaxBounds.f_55297_.createWithContext(p_75360_);
      } else {
         int i = p_75360_.getCursor();
         Float f = m_75363_(m_75367_(p_75360_, p_75361_), p_75362_);
         Float f1;
         if (p_75360_.canRead(2) && p_75360_.peek() == '.' && p_75360_.peek(1) == '.') {
            p_75360_.skip();
            p_75360_.skip();
            f1 = m_75363_(m_75367_(p_75360_, p_75361_), p_75362_);
            if (f == null && f1 == null) {
               p_75360_.setCursor(i);
               throw MinMaxBounds.f_55297_.createWithContext(p_75360_);
            }
         } else {
            if (!p_75361_ && p_75360_.canRead() && p_75360_.peek() == '.') {
               p_75360_.setCursor(i);
               throw f_75351_.createWithContext(p_75360_);
            }

            f1 = f;
         }

         if (f == null && f1 == null) {
            p_75360_.setCursor(i);
            throw MinMaxBounds.f_55297_.createWithContext(p_75360_);
         } else {
            return new WrappedMinMaxBounds(f, f1);
         }
      }
   }

   @Nullable
   private static Float m_75367_(StringReader p_75368_, boolean p_75369_) throws CommandSyntaxException {
      int i = p_75368_.getCursor();

      while(p_75368_.canRead() && m_75370_(p_75368_, p_75369_)) {
         p_75368_.skip();
      }

      String s = p_75368_.getString().substring(i, p_75368_.getCursor());
      if (s.isEmpty()) {
         return null;
      } else {
         try {
            return Float.parseFloat(s);
         } catch (NumberFormatException numberformatexception) {
            if (p_75369_) {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(p_75368_, s);
            } else {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(p_75368_, s);
            }
         }
      }
   }

   private static boolean m_75370_(StringReader p_75371_, boolean p_75372_) {
      char c0 = p_75371_.peek();
      if ((c0 < '0' || c0 > '9') && c0 != '-') {
         if (p_75372_ && c0 == '.') {
            return !p_75371_.canRead(2) || p_75371_.peek(1) != '.';
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   private static Float m_75363_(@Nullable Float p_75364_, Function<Float, Float> p_75365_) {
      return p_75364_ == null ? null : p_75365_.apply(p_75364_);
   }
}