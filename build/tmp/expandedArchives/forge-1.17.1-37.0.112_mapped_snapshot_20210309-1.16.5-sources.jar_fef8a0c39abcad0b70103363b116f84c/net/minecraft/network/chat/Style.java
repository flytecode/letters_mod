package net.minecraft.network.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Style {
   public static final Style f_131099_ = new Style((TextColor)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (Boolean)null, (ClickEvent)null, (HoverEvent)null, (String)null, (ResourceLocation)null);
   public static final ResourceLocation f_131100_ = new ResourceLocation("minecraft", "default");
   @Nullable
   final TextColor f_131101_;
   @Nullable
   final Boolean f_131102_;
   @Nullable
   final Boolean f_131103_;
   @Nullable
   final Boolean f_131104_;
   @Nullable
   final Boolean f_131105_;
   @Nullable
   final Boolean f_131106_;
   @Nullable
   final ClickEvent f_131107_;
   @Nullable
   final HoverEvent f_131108_;
   @Nullable
   final String f_131109_;
   @Nullable
   final ResourceLocation f_131110_;

   Style(@Nullable TextColor p_131113_, @Nullable Boolean p_131114_, @Nullable Boolean p_131115_, @Nullable Boolean p_131116_, @Nullable Boolean p_131117_, @Nullable Boolean p_131118_, @Nullable ClickEvent p_131119_, @Nullable HoverEvent p_131120_, @Nullable String p_131121_, @Nullable ResourceLocation p_131122_) {
      this.f_131101_ = p_131113_;
      this.f_131102_ = p_131114_;
      this.f_131103_ = p_131115_;
      this.f_131104_ = p_131116_;
      this.f_131105_ = p_131117_;
      this.f_131106_ = p_131118_;
      this.f_131107_ = p_131119_;
      this.f_131108_ = p_131120_;
      this.f_131109_ = p_131121_;
      this.f_131110_ = p_131122_;
   }

   @Nullable
   public TextColor m_131135_() {
      return this.f_131101_;
   }

   public boolean m_131154_() {
      return this.f_131102_ == Boolean.TRUE;
   }

   public boolean m_131161_() {
      return this.f_131103_ == Boolean.TRUE;
   }

   public boolean m_131168_() {
      return this.f_131105_ == Boolean.TRUE;
   }

   public boolean m_131171_() {
      return this.f_131104_ == Boolean.TRUE;
   }

   public boolean m_131176_() {
      return this.f_131106_ == Boolean.TRUE;
   }

   public boolean m_131179_() {
      return this == f_131099_;
   }

   @Nullable
   public ClickEvent m_131182_() {
      return this.f_131107_;
   }

   @Nullable
   public HoverEvent m_131186_() {
      return this.f_131108_;
   }

   @Nullable
   public String m_131189_() {
      return this.f_131109_;
   }

   public ResourceLocation m_131192_() {
      return this.f_131110_ != null ? this.f_131110_ : f_131100_;
   }

   public Style m_131148_(@Nullable TextColor p_131149_) {
      return new Style(p_131149_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131140_(@Nullable ChatFormatting p_131141_) {
      return this.m_131148_(p_131141_ != null ? TextColor.m_131270_(p_131141_) : null);
   }

   public Style m_178520_(int p_178521_) {
      return this.m_131148_(TextColor.m_131266_(p_178521_));
   }

   public Style m_131136_(@Nullable Boolean p_131137_) {
      return new Style(this.f_131101_, p_131137_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131155_(@Nullable Boolean p_131156_) {
      return new Style(this.f_131101_, this.f_131102_, p_131156_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131162_(@Nullable Boolean p_131163_) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, p_131163_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_178522_(@Nullable Boolean p_178523_) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, p_178523_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_178524_(@Nullable Boolean p_178525_) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, p_178525_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style setUnderlined(@Nullable Boolean underlined) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, underlined, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style setStrikethrough(@Nullable Boolean strikethrough) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, strikethrough, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style setObfuscated(@Nullable Boolean obfuscated) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, obfuscated, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131142_(@Nullable ClickEvent p_131143_) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, p_131143_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131144_(@Nullable HoverEvent p_131145_) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, p_131145_, this.f_131109_, this.f_131110_);
   }

   public Style m_131138_(@Nullable String p_131139_) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, p_131139_, this.f_131110_);
   }

   public Style m_131150_(@Nullable ResourceLocation p_131151_) {
      return new Style(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_, p_131151_);
   }

   public Style m_131157_(ChatFormatting p_131158_) {
      TextColor textcolor = this.f_131101_;
      Boolean obool = this.f_131102_;
      Boolean obool1 = this.f_131103_;
      Boolean obool2 = this.f_131105_;
      Boolean obool3 = this.f_131104_;
      Boolean obool4 = this.f_131106_;
      switch(p_131158_) {
      case OBFUSCATED:
         obool4 = true;
         break;
      case BOLD:
         obool = true;
         break;
      case STRIKETHROUGH:
         obool2 = true;
         break;
      case UNDERLINE:
         obool3 = true;
         break;
      case ITALIC:
         obool1 = true;
         break;
      case RESET:
         return f_131099_;
      default:
         textcolor = TextColor.m_131270_(p_131158_);
      }

      return new Style(textcolor, obool, obool1, obool3, obool2, obool4, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131164_(ChatFormatting p_131165_) {
      TextColor textcolor = this.f_131101_;
      Boolean obool = this.f_131102_;
      Boolean obool1 = this.f_131103_;
      Boolean obool2 = this.f_131105_;
      Boolean obool3 = this.f_131104_;
      Boolean obool4 = this.f_131106_;
      switch(p_131165_) {
      case OBFUSCATED:
         obool4 = true;
         break;
      case BOLD:
         obool = true;
         break;
      case STRIKETHROUGH:
         obool2 = true;
         break;
      case UNDERLINE:
         obool3 = true;
         break;
      case ITALIC:
         obool1 = true;
         break;
      case RESET:
         return f_131099_;
      default:
         obool4 = false;
         obool = false;
         obool2 = false;
         obool3 = false;
         obool1 = false;
         textcolor = TextColor.m_131270_(p_131165_);
      }

      return new Style(textcolor, obool, obool1, obool3, obool2, obool4, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131152_(ChatFormatting... p_131153_) {
      TextColor textcolor = this.f_131101_;
      Boolean obool = this.f_131102_;
      Boolean obool1 = this.f_131103_;
      Boolean obool2 = this.f_131105_;
      Boolean obool3 = this.f_131104_;
      Boolean obool4 = this.f_131106_;

      for(ChatFormatting chatformatting : p_131153_) {
         switch(chatformatting) {
         case OBFUSCATED:
            obool4 = true;
            break;
         case BOLD:
            obool = true;
            break;
         case STRIKETHROUGH:
            obool2 = true;
            break;
         case UNDERLINE:
            obool3 = true;
            break;
         case ITALIC:
            obool1 = true;
            break;
         case RESET:
            return f_131099_;
         default:
            textcolor = TextColor.m_131270_(chatformatting);
         }
      }

      return new Style(textcolor, obool, obool1, obool3, obool2, obool4, this.f_131107_, this.f_131108_, this.f_131109_, this.f_131110_);
   }

   public Style m_131146_(Style p_131147_) {
      if (this == f_131099_) {
         return p_131147_;
      } else {
         return p_131147_ == f_131099_ ? this : new Style(this.f_131101_ != null ? this.f_131101_ : p_131147_.f_131101_, this.f_131102_ != null ? this.f_131102_ : p_131147_.f_131102_, this.f_131103_ != null ? this.f_131103_ : p_131147_.f_131103_, this.f_131104_ != null ? this.f_131104_ : p_131147_.f_131104_, this.f_131105_ != null ? this.f_131105_ : p_131147_.f_131105_, this.f_131106_ != null ? this.f_131106_ : p_131147_.f_131106_, this.f_131107_ != null ? this.f_131107_ : p_131147_.f_131107_, this.f_131108_ != null ? this.f_131108_ : p_131147_.f_131108_, this.f_131109_ != null ? this.f_131109_ : p_131147_.f_131109_, this.f_131110_ != null ? this.f_131110_ : p_131147_.f_131110_);
      }
   }

   public String toString() {
      return "Style{ color=" + this.f_131101_ + ", bold=" + this.f_131102_ + ", italic=" + this.f_131103_ + ", underlined=" + this.f_131104_ + ", strikethrough=" + this.f_131105_ + ", obfuscated=" + this.f_131106_ + ", clickEvent=" + this.m_131182_() + ", hoverEvent=" + this.m_131186_() + ", insertion=" + this.m_131189_() + ", font=" + this.m_131192_() + "}";
   }

   public boolean equals(Object p_131175_) {
      if (this == p_131175_) {
         return true;
      } else if (!(p_131175_ instanceof Style)) {
         return false;
      } else {
         Style style = (Style)p_131175_;
         return this.m_131154_() == style.m_131154_() && Objects.equals(this.m_131135_(), style.m_131135_()) && this.m_131161_() == style.m_131161_() && this.m_131176_() == style.m_131176_() && this.m_131168_() == style.m_131168_() && this.m_131171_() == style.m_131171_() && Objects.equals(this.m_131182_(), style.m_131182_()) && Objects.equals(this.m_131186_(), style.m_131186_()) && Objects.equals(this.m_131189_(), style.m_131189_()) && Objects.equals(this.m_131192_(), style.m_131192_());
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_131101_, this.f_131102_, this.f_131103_, this.f_131104_, this.f_131105_, this.f_131106_, this.f_131107_, this.f_131108_, this.f_131109_);
   }

   public static class Serializer implements JsonDeserializer<Style>, JsonSerializer<Style> {
      @Nullable
      public Style deserialize(JsonElement p_131200_, Type p_131201_, JsonDeserializationContext p_131202_) throws JsonParseException {
         if (p_131200_.isJsonObject()) {
            JsonObject jsonobject = p_131200_.getAsJsonObject();
            if (jsonobject == null) {
               return null;
            } else {
               Boolean obool = m_131205_(jsonobject, "bold");
               Boolean obool1 = m_131205_(jsonobject, "italic");
               Boolean obool2 = m_131205_(jsonobject, "underlined");
               Boolean obool3 = m_131205_(jsonobject, "strikethrough");
               Boolean obool4 = m_131205_(jsonobject, "obfuscated");
               TextColor textcolor = m_131222_(jsonobject);
               String s = m_131216_(jsonobject);
               ClickEvent clickevent = m_131214_(jsonobject);
               HoverEvent hoverevent = m_131212_(jsonobject);
               ResourceLocation resourcelocation = m_131203_(jsonobject);
               return new Style(textcolor, obool, obool1, obool2, obool3, obool4, clickevent, hoverevent, s, resourcelocation);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static ResourceLocation m_131203_(JsonObject p_131204_) {
         if (p_131204_.has("font")) {
            String s = GsonHelper.m_13906_(p_131204_, "font");

            try {
               return new ResourceLocation(s);
            } catch (ResourceLocationException resourcelocationexception) {
               throw new JsonSyntaxException("Invalid font name: " + s);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static HoverEvent m_131212_(JsonObject p_131213_) {
         if (p_131213_.has("hoverEvent")) {
            JsonObject jsonobject = GsonHelper.m_13930_(p_131213_, "hoverEvent");
            HoverEvent hoverevent = HoverEvent.m_130821_(jsonobject);
            if (hoverevent != null && hoverevent.m_130820_().m_130847_()) {
               return hoverevent;
            }
         }

         return null;
      }

      @Nullable
      private static ClickEvent m_131214_(JsonObject p_131215_) {
         if (p_131215_.has("clickEvent")) {
            JsonObject jsonobject = GsonHelper.m_13930_(p_131215_, "clickEvent");
            String s = GsonHelper.m_13851_(jsonobject, "action", (String)null);
            ClickEvent.Action clickevent$action = s == null ? null : ClickEvent.Action.m_130645_(s);
            String s1 = GsonHelper.m_13851_(jsonobject, "value", (String)null);
            if (clickevent$action != null && s1 != null && clickevent$action.m_130644_()) {
               return new ClickEvent(clickevent$action, s1);
            }
         }

         return null;
      }

      @Nullable
      private static String m_131216_(JsonObject p_131217_) {
         return GsonHelper.m_13851_(p_131217_, "insertion", (String)null);
      }

      @Nullable
      private static TextColor m_131222_(JsonObject p_131223_) {
         if (p_131223_.has("color")) {
            String s = GsonHelper.m_13906_(p_131223_, "color");
            return TextColor.m_131268_(s);
         } else {
            return null;
         }
      }

      @Nullable
      private static Boolean m_131205_(JsonObject p_131206_, String p_131207_) {
         return p_131206_.has(p_131207_) ? p_131206_.get(p_131207_).getAsBoolean() : null;
      }

      @Nullable
      public JsonElement serialize(Style p_131209_, Type p_131210_, JsonSerializationContext p_131211_) {
         if (p_131209_.m_131179_()) {
            return null;
         } else {
            JsonObject jsonobject = new JsonObject();
            if (p_131209_.f_131102_ != null) {
               jsonobject.addProperty("bold", p_131209_.f_131102_);
            }

            if (p_131209_.f_131103_ != null) {
               jsonobject.addProperty("italic", p_131209_.f_131103_);
            }

            if (p_131209_.f_131104_ != null) {
               jsonobject.addProperty("underlined", p_131209_.f_131104_);
            }

            if (p_131209_.f_131105_ != null) {
               jsonobject.addProperty("strikethrough", p_131209_.f_131105_);
            }

            if (p_131209_.f_131106_ != null) {
               jsonobject.addProperty("obfuscated", p_131209_.f_131106_);
            }

            if (p_131209_.f_131101_ != null) {
               jsonobject.addProperty("color", p_131209_.f_131101_.m_131274_());
            }

            if (p_131209_.f_131109_ != null) {
               jsonobject.add("insertion", p_131211_.serialize(p_131209_.f_131109_));
            }

            if (p_131209_.f_131107_ != null) {
               JsonObject jsonobject1 = new JsonObject();
               jsonobject1.addProperty("action", p_131209_.f_131107_.m_130622_().m_130649_());
               jsonobject1.addProperty("value", p_131209_.f_131107_.m_130623_());
               jsonobject.add("clickEvent", jsonobject1);
            }

            if (p_131209_.f_131108_ != null) {
               jsonobject.add("hoverEvent", p_131209_.f_131108_.m_130825_());
            }

            if (p_131209_.f_131110_ != null) {
               jsonobject.addProperty("font", p_131209_.f_131110_.toString());
            }

            return jsonobject;
         }
      }
   }
}
