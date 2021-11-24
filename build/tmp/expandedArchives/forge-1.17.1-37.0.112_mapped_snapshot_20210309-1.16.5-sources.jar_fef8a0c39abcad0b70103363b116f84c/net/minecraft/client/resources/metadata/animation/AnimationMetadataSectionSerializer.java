package net.minecraft.client.resources.metadata.animation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import javax.annotation.Nullable;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;

@OnlyIn(Dist.CLIENT)
public class AnimationMetadataSectionSerializer implements MetadataSectionSerializer<AnimationMetadataSection> {
   public AnimationMetadataSection m_6322_(JsonObject p_119064_) {
      Builder<AnimationFrame> builder = ImmutableList.builder();
      int i = GsonHelper.m_13824_(p_119064_, "frametime", 1);
      if (i != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)i, "Invalid default frame time");
      }

      if (p_119064_.has("frames")) {
         try {
            JsonArray jsonarray = GsonHelper.m_13933_(p_119064_, "frames");

            for(int j = 0; j < jsonarray.size(); ++j) {
               JsonElement jsonelement = jsonarray.get(j);
               AnimationFrame animationframe = this.m_119058_(j, jsonelement);
               if (animationframe != null) {
                  builder.add(animationframe);
               }
            }
         } catch (ClassCastException classcastexception) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + p_119064_.get("frames"), classcastexception);
         }
      }

      int k = GsonHelper.m_13824_(p_119064_, "width", -1);
      int l = GsonHelper.m_13824_(p_119064_, "height", -1);
      if (k != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)k, "Invalid width");
      }

      if (l != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)l, "Invalid height");
      }

      boolean flag = GsonHelper.m_13855_(p_119064_, "interpolate", false);
      return new AnimationMetadataSection(builder.build(), k, l, i, flag);
   }

   @Nullable
   private AnimationFrame m_119058_(int p_119059_, JsonElement p_119060_) {
      if (p_119060_.isJsonPrimitive()) {
         return new AnimationFrame(GsonHelper.m_13897_(p_119060_, "frames[" + p_119059_ + "]"));
      } else if (p_119060_.isJsonObject()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_119060_, "frames[" + p_119059_ + "]");
         int i = GsonHelper.m_13824_(jsonobject, "time", -1);
         if (jsonobject.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)i, "Invalid frame time");
         }

         int j = GsonHelper.m_13927_(jsonobject, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)j, "Invalid frame index");
         return new AnimationFrame(j, i);
      } else {
         return null;
      }
   }

   public String m_7991_() {
      return "animation";
   }
}