package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.lang.reflect.Type;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @deprecated use {@link net.minecraft.util.math.vector.TransformationMatrix} through {@link net.minecraftforge.client.extensions.IForgeBakedModel#handlePerspective}
 */
@OnlyIn(Dist.CLIENT)
@Deprecated
public class ItemTransform {
   public static final ItemTransform f_111754_ = new ItemTransform(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
   public final Vector3f f_111755_;
   public final Vector3f f_111756_;
   public final Vector3f f_111757_;

   public ItemTransform(Vector3f p_111760_, Vector3f p_111761_, Vector3f p_111762_) {
      this.f_111755_ = p_111760_.m_122281_();
      this.f_111756_ = p_111761_.m_122281_();
      this.f_111757_ = p_111762_.m_122281_();
   }

   public void m_111763_(boolean p_111764_, PoseStack p_111765_) {
      if (this != f_111754_) {
         float f = this.f_111755_.m_122239_();
         float f1 = this.f_111755_.m_122260_();
         float f2 = this.f_111755_.m_122269_();
         if (p_111764_) {
            f1 = -f1;
            f2 = -f2;
         }

         int i = p_111764_ ? -1 : 1;
         p_111765_.m_85837_((double)((float)i * this.f_111756_.m_122239_()), (double)this.f_111756_.m_122260_(), (double)this.f_111756_.m_122269_());
         p_111765_.m_85845_(new Quaternion(f, f1, f2, true));
         p_111765_.m_85841_(this.f_111757_.m_122239_(), this.f_111757_.m_122260_(), this.f_111757_.m_122269_());
      }
   }

   public boolean equals(Object p_111767_) {
      if (this == p_111767_) {
         return true;
      } else if (this.getClass() != p_111767_.getClass()) {
         return false;
      } else {
         ItemTransform itemtransform = (ItemTransform)p_111767_;
         return this.f_111755_.equals(itemtransform.f_111755_) && this.f_111757_.equals(itemtransform.f_111757_) && this.f_111756_.equals(itemtransform.f_111756_);
      }
   }

   public int hashCode() {
      int i = this.f_111755_.hashCode();
      i = 31 * i + this.f_111756_.hashCode();
      return 31 * i + this.f_111757_.hashCode();
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<ItemTransform> {
      public static final Vector3f f_111769_ = new Vector3f(0.0F, 0.0F, 0.0F);
      public static final Vector3f f_111770_ = new Vector3f(0.0F, 0.0F, 0.0F);
      public static final Vector3f f_111771_ = new Vector3f(1.0F, 1.0F, 1.0F);
      public static final float f_173492_ = 5.0F;
      public static final float f_173493_ = 4.0F;

      public ItemTransform deserialize(JsonElement p_111775_, Type p_111776_, JsonDeserializationContext p_111777_) throws JsonParseException {
         JsonObject jsonobject = p_111775_.getAsJsonObject();
         Vector3f vector3f = this.m_111778_(jsonobject, "rotation", f_111769_);
         Vector3f vector3f1 = this.m_111778_(jsonobject, "translation", f_111770_);
         vector3f1.m_122261_(0.0625F);
         vector3f1.m_122242_(-5.0F, 5.0F);
         Vector3f vector3f2 = this.m_111778_(jsonobject, "scale", f_111771_);
         vector3f2.m_122242_(-4.0F, 4.0F);
         return new ItemTransform(vector3f, vector3f1, vector3f2);
      }

      private Vector3f m_111778_(JsonObject p_111779_, String p_111780_, Vector3f p_111781_) {
         if (!p_111779_.has(p_111780_)) {
            return p_111781_;
         } else {
            JsonArray jsonarray = GsonHelper.m_13933_(p_111779_, p_111780_);
            if (jsonarray.size() != 3) {
               throw new JsonParseException("Expected 3 " + p_111780_ + " values, found: " + jsonarray.size());
            } else {
               float[] afloat = new float[3];

               for(int i = 0; i < afloat.length; ++i) {
                  afloat[i] = GsonHelper.m_13888_(jsonarray.get(i), p_111780_ + "[" + i + "]");
               }

               return new Vector3f(afloat[0], afloat[1], afloat[2]);
            }
         }
      }
   }
}
