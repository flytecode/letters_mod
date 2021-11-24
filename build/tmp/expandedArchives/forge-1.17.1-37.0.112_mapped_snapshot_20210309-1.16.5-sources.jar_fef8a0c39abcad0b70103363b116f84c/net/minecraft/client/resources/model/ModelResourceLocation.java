package net.minecraft.client.resources.model;

import com.google.common.annotations.VisibleForTesting;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;

public class ModelResourceLocation extends ResourceLocation {
   @VisibleForTesting
   static final char f_174906_ = '#';
   private final String f_119435_;

   protected ModelResourceLocation(String[] p_119445_) {
      super(p_119445_);
      this.f_119435_ = p_119445_[2].toLowerCase(Locale.ROOT);
   }

   public ModelResourceLocation(String p_174908_, String p_174909_, String p_174910_) {
      this(new String[]{p_174908_, p_174909_, p_174910_});
   }

   public ModelResourceLocation(String p_119437_) {
      this(m_119446_(p_119437_));
   }

   public ModelResourceLocation(ResourceLocation p_119442_, String p_119443_) {
      this(p_119442_.toString(), p_119443_);
   }

   public ModelResourceLocation(String p_119439_, String p_119440_) {
      this(m_119446_(p_119439_ + "#" + p_119440_));
   }

   protected static String[] m_119446_(String p_119447_) {
      String[] astring = new String[]{null, p_119447_, ""};
      int i = p_119447_.indexOf(35);
      String s = p_119447_;
      if (i >= 0) {
         astring[2] = p_119447_.substring(i + 1, p_119447_.length());
         if (i > 1) {
            s = p_119447_.substring(0, i);
         }
      }

      System.arraycopy(ResourceLocation.m_135832_(s, ':'), 0, astring, 0, 2);
      return astring;
   }

   public String m_119448_() {
      return this.f_119435_;
   }

   public boolean equals(Object p_119450_) {
      if (this == p_119450_) {
         return true;
      } else if (p_119450_ instanceof ModelResourceLocation && super.equals(p_119450_)) {
         ModelResourceLocation modelresourcelocation = (ModelResourceLocation)p_119450_;
         return this.f_119435_.equals(modelresourcelocation.f_119435_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 31 * super.hashCode() + this.f_119435_.hashCode();
   }

   public String toString() {
      return super.toString() + "#" + this.f_119435_;
   }
}