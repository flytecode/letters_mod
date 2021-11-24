package net.minecraft.resources;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;

public class ResourceLocation implements Comparable<ResourceLocation> {
   public static final Codec<ResourceLocation> f_135803_ = Codec.STRING.comapFlatMap(ResourceLocation::m_135837_, ResourceLocation::toString).stable();
   private static final SimpleCommandExceptionType f_135806_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.id.invalid"));
   public static final char f_179907_ = ':';
   public static final String f_179908_ = "minecraft";
   public static final String f_179909_ = "realms";
   protected final String f_135804_;
   protected final String f_135805_;

   protected ResourceLocation(String[] p_135814_) {
      this.f_135804_ = StringUtils.isEmpty(p_135814_[0]) ? "minecraft" : p_135814_[0];
      this.f_135805_ = p_135814_[1];
      if (!m_135843_(this.f_135804_)) {
         throw new ResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + this.f_135804_ + ":" + this.f_135805_);
      } else if (!m_135841_(this.f_135805_)) {
         throw new ResourceLocationException("Non [a-z0-9/._-] character in path of location: " + this.f_135804_ + ":" + this.f_135805_);
      }
   }

   public ResourceLocation(String p_135809_) {
      this(m_135832_(p_135809_, ':'));
   }

   public ResourceLocation(String p_135811_, String p_135812_) {
      this(new String[]{p_135811_, p_135812_});
   }

   public static ResourceLocation m_135822_(String p_135823_, char p_135824_) {
      return new ResourceLocation(m_135832_(p_135823_, p_135824_));
   }

   @Nullable
   public static ResourceLocation m_135820_(String p_135821_) {
      try {
         return new ResourceLocation(p_135821_);
      } catch (ResourceLocationException resourcelocationexception) {
         return null;
      }
   }

   protected static String[] m_135832_(String p_135833_, char p_135834_) {
      String[] astring = new String[]{"minecraft", p_135833_};
      int i = p_135833_.indexOf(p_135834_);
      if (i >= 0) {
         astring[1] = p_135833_.substring(i + 1, p_135833_.length());
         if (i >= 1) {
            astring[0] = p_135833_.substring(0, i);
         }
      }

      return astring;
   }

   private static DataResult<ResourceLocation> m_135837_(String p_135838_) {
      try {
         return DataResult.success(new ResourceLocation(p_135838_));
      } catch (ResourceLocationException resourcelocationexception) {
         return DataResult.error("Not a valid resource location: " + p_135838_ + " " + resourcelocationexception.getMessage());
      }
   }

   public String m_135815_() {
      return this.f_135805_;
   }

   public String m_135827_() {
      return this.f_135804_;
   }

   public String toString() {
      return this.f_135804_ + ":" + this.f_135805_;
   }

   public boolean equals(Object p_135846_) {
      if (this == p_135846_) {
         return true;
      } else if (!(p_135846_ instanceof ResourceLocation)) {
         return false;
      } else {
         ResourceLocation resourcelocation = (ResourceLocation)p_135846_;
         return this.f_135804_.equals(resourcelocation.f_135804_) && this.f_135805_.equals(resourcelocation.f_135805_);
      }
   }

   public int hashCode() {
      return 31 * this.f_135804_.hashCode() + this.f_135805_.hashCode();
   }

   public int compareTo(ResourceLocation p_135826_) {
      int i = this.f_135805_.compareTo(p_135826_.f_135805_);
      if (i == 0) {
         i = this.f_135804_.compareTo(p_135826_.f_135804_);
      }

      return i;
   }

   // Normal compare sorts by path first, this compares namespace first.
   public int compareNamespaced(ResourceLocation o) {
      int ret = this.f_135804_.compareTo(o.f_135804_);
      return ret != 0 ? ret : this.f_135805_.compareTo(o.f_135805_);
   }

   public String m_179910_() {
      return this.toString().replace('/', '_').replace(':', '_');
   }

   public static ResourceLocation m_135818_(StringReader p_135819_) throws CommandSyntaxException {
      int i = p_135819_.getCursor();

      while(p_135819_.canRead() && m_135816_(p_135819_.peek())) {
         p_135819_.skip();
      }

      String s = p_135819_.getString().substring(i, p_135819_.getCursor());

      try {
         return new ResourceLocation(s);
      } catch (ResourceLocationException resourcelocationexception) {
         p_135819_.setCursor(i);
         throw f_135806_.createWithContext(p_135819_);
      }
   }

   public static boolean m_135816_(char p_135817_) {
      return p_135817_ >= '0' && p_135817_ <= '9' || p_135817_ >= 'a' && p_135817_ <= 'z' || p_135817_ == '_' || p_135817_ == ':' || p_135817_ == '/' || p_135817_ == '.' || p_135817_ == '-';
   }

   private static boolean m_135841_(String p_135842_) {
      for(int i = 0; i < p_135842_.length(); ++i) {
         if (!m_135828_(p_135842_.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   private static boolean m_135843_(String p_135844_) {
      for(int i = 0; i < p_135844_.length(); ++i) {
         if (!m_135835_(p_135844_.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public static boolean m_135828_(char p_135829_) {
      return p_135829_ == '_' || p_135829_ == '-' || p_135829_ >= 'a' && p_135829_ <= 'z' || p_135829_ >= '0' && p_135829_ <= '9' || p_135829_ == '/' || p_135829_ == '.';
   }

   private static boolean m_135835_(char p_135836_) {
      return p_135836_ == '_' || p_135836_ == '-' || p_135836_ >= 'a' && p_135836_ <= 'z' || p_135836_ >= '0' && p_135836_ <= '9' || p_135836_ == '.';
   }

   public static boolean m_135830_(String p_135831_) {
      String[] astring = m_135832_(p_135831_, ':');
      return m_135843_(StringUtils.isEmpty(astring[0]) ? "minecraft" : astring[0]) && m_135841_(astring[1]);
   }

   public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation> {
      public ResourceLocation deserialize(JsonElement p_135851_, Type p_135852_, JsonDeserializationContext p_135853_) throws JsonParseException {
         return new ResourceLocation(GsonHelper.m_13805_(p_135851_, "location"));
      }

      public JsonElement serialize(ResourceLocation p_135855_, Type p_135856_, JsonSerializationContext p_135857_) {
         return new JsonPrimitive(p_135855_.toString());
      }
   }
}
