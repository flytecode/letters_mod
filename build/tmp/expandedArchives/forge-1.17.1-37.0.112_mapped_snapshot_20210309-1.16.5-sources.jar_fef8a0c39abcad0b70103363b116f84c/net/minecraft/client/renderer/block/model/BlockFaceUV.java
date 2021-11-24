package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockFaceUV {
   public float[] f_111387_;
   public final int f_111388_;

   public BlockFaceUV(@Nullable float[] p_111390_, int p_111391_) {
      this.f_111387_ = p_111390_;
      this.f_111388_ = p_111391_;
   }

   public float m_111392_(int p_111393_) {
      if (this.f_111387_ == null) {
         throw new NullPointerException("uvs");
      } else {
         int i = this.m_111400_(p_111393_);
         return this.f_111387_[i != 0 && i != 1 ? 2 : 0];
      }
   }

   public float m_111396_(int p_111397_) {
      if (this.f_111387_ == null) {
         throw new NullPointerException("uvs");
      } else {
         int i = this.m_111400_(p_111397_);
         return this.f_111387_[i != 0 && i != 3 ? 3 : 1];
      }
   }

   private int m_111400_(int p_111401_) {
      return (p_111401_ + this.f_111388_ / 90) % 4;
   }

   public int m_111398_(int p_111399_) {
      return (p_111399_ + 4 - this.f_111388_ / 90) % 4;
   }

   public void m_111394_(float[] p_111395_) {
      if (this.f_111387_ == null) {
         this.f_111387_ = p_111395_;
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<BlockFaceUV> {
      private static final int f_173417_ = 0;

      public BlockFaceUV deserialize(JsonElement p_111404_, Type p_111405_, JsonDeserializationContext p_111406_) throws JsonParseException {
         JsonObject jsonobject = p_111404_.getAsJsonObject();
         float[] afloat = this.m_111409_(jsonobject);
         int i = this.m_111407_(jsonobject);
         return new BlockFaceUV(afloat, i);
      }

      protected int m_111407_(JsonObject p_111408_) {
         int i = GsonHelper.m_13824_(p_111408_, "rotation", 0);
         if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
            return i;
         } else {
            throw new JsonParseException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
         }
      }

      @Nullable
      private float[] m_111409_(JsonObject p_111410_) {
         if (!p_111410_.has("uv")) {
            return null;
         } else {
            JsonArray jsonarray = GsonHelper.m_13933_(p_111410_, "uv");
            if (jsonarray.size() != 4) {
               throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
            } else {
               float[] afloat = new float[4];

               for(int i = 0; i < afloat.length; ++i) {
                  afloat[i] = GsonHelper.m_13888_(jsonarray.get(i), "uv[" + i + "]");
               }

               return afloat;
            }
         }
      }
   }
}