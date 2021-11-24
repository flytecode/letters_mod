package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Vector3f;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockElement {
   private static final boolean f_173411_ = false;
   private static final float f_173412_ = -16.0F;
   private static final float f_173413_ = 32.0F;
   public final Vector3f f_111308_;
   public final Vector3f f_111309_;
   public final Map<Direction, BlockElementFace> f_111310_;
   public final BlockElementRotation f_111311_;
   public final boolean f_111312_;

   public BlockElement(Vector3f p_111314_, Vector3f p_111315_, Map<Direction, BlockElementFace> p_111316_, @Nullable BlockElementRotation p_111317_, boolean p_111318_) {
      this.f_111308_ = p_111314_;
      this.f_111309_ = p_111315_;
      this.f_111310_ = p_111316_;
      this.f_111311_ = p_111317_;
      this.f_111312_ = p_111318_;
      this.m_111319_();
   }

   private void m_111319_() {
      for(Entry<Direction, BlockElementFace> entry : this.f_111310_.entrySet()) {
         float[] afloat = this.m_111320_(entry.getKey());
         (entry.getValue()).f_111357_.m_111394_(afloat);
      }

   }

   public float[] m_111320_(Direction p_111321_) {
      switch(p_111321_) {
      case DOWN:
         return new float[]{this.f_111308_.m_122239_(), 16.0F - this.f_111309_.m_122269_(), this.f_111309_.m_122239_(), 16.0F - this.f_111308_.m_122269_()};
      case UP:
         return new float[]{this.f_111308_.m_122239_(), this.f_111308_.m_122269_(), this.f_111309_.m_122239_(), this.f_111309_.m_122269_()};
      case NORTH:
      default:
         return new float[]{16.0F - this.f_111309_.m_122239_(), 16.0F - this.f_111309_.m_122260_(), 16.0F - this.f_111308_.m_122239_(), 16.0F - this.f_111308_.m_122260_()};
      case SOUTH:
         return new float[]{this.f_111308_.m_122239_(), 16.0F - this.f_111309_.m_122260_(), this.f_111309_.m_122239_(), 16.0F - this.f_111308_.m_122260_()};
      case WEST:
         return new float[]{this.f_111308_.m_122269_(), 16.0F - this.f_111309_.m_122260_(), this.f_111309_.m_122269_(), 16.0F - this.f_111308_.m_122260_()};
      case EAST:
         return new float[]{16.0F - this.f_111309_.m_122269_(), 16.0F - this.f_111309_.m_122260_(), 16.0F - this.f_111308_.m_122269_(), 16.0F - this.f_111308_.m_122260_()};
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<BlockElement> {
      private static final boolean f_173414_ = true;

      public BlockElement deserialize(JsonElement p_111329_, Type p_111330_, JsonDeserializationContext p_111331_) throws JsonParseException {
         JsonObject jsonobject = p_111329_.getAsJsonObject();
         Vector3f vector3f = this.m_111352_(jsonobject);
         Vector3f vector3f1 = this.m_111346_(jsonobject);
         BlockElementRotation blockelementrotation = this.m_111332_(jsonobject);
         Map<Direction, BlockElementFace> map = this.m_111325_(p_111331_, jsonobject);
         if (jsonobject.has("shade") && !GsonHelper.m_13880_(jsonobject, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean flag = GsonHelper.m_13855_(jsonobject, "shade", true);
            return new BlockElement(vector3f, vector3f1, map, blockelementrotation, flag);
         }
      }

      @Nullable
      private BlockElementRotation m_111332_(JsonObject p_111333_) {
         BlockElementRotation blockelementrotation = null;
         if (p_111333_.has("rotation")) {
            JsonObject jsonobject = GsonHelper.m_13930_(p_111333_, "rotation");
            Vector3f vector3f = this.m_111334_(jsonobject, "origin");
            vector3f.m_122261_(0.0625F);
            Direction.Axis direction$axis = this.m_111344_(jsonobject);
            float f = this.m_111342_(jsonobject);
            boolean flag = GsonHelper.m_13855_(jsonobject, "rescale", false);
            blockelementrotation = new BlockElementRotation(vector3f, direction$axis, f, flag);
         }

         return blockelementrotation;
      }

      private float m_111342_(JsonObject p_111343_) {
         float f = GsonHelper.m_13915_(p_111343_, "angle");
         if (f != 0.0F && Mth.m_14154_(f) != 22.5F && Mth.m_14154_(f) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return f;
         }
      }

      private Direction.Axis m_111344_(JsonObject p_111345_) {
         String s = GsonHelper.m_13906_(p_111345_, "axis");
         Direction.Axis direction$axis = Direction.Axis.m_122473_(s.toLowerCase(Locale.ROOT));
         if (direction$axis == null) {
            throw new JsonParseException("Invalid rotation axis: " + s);
         } else {
            return direction$axis;
         }
      }

      private Map<Direction, BlockElementFace> m_111325_(JsonDeserializationContext p_111326_, JsonObject p_111327_) {
         Map<Direction, BlockElementFace> map = this.m_111339_(p_111326_, p_111327_);
         if (map.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return map;
         }
      }

      private Map<Direction, BlockElementFace> m_111339_(JsonDeserializationContext p_111340_, JsonObject p_111341_) {
         Map<Direction, BlockElementFace> map = Maps.newEnumMap(Direction.class);
         JsonObject jsonobject = GsonHelper.m_13930_(p_111341_, "faces");

         for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
            Direction direction = this.m_111337_(entry.getKey());
            map.put(direction, p_111340_.deserialize(entry.getValue(), BlockElementFace.class));
         }

         return map;
      }

      private Direction m_111337_(String p_111338_) {
         Direction direction = Direction.m_122402_(p_111338_);
         if (direction == null) {
            throw new JsonParseException("Unknown facing: " + p_111338_);
         } else {
            return direction;
         }
      }

      private Vector3f m_111346_(JsonObject p_111347_) {
         Vector3f vector3f = this.m_111334_(p_111347_, "to");
         if (!(vector3f.m_122239_() < -16.0F) && !(vector3f.m_122260_() < -16.0F) && !(vector3f.m_122269_() < -16.0F) && !(vector3f.m_122239_() > 32.0F) && !(vector3f.m_122260_() > 32.0F) && !(vector3f.m_122269_() > 32.0F)) {
            return vector3f;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
         }
      }

      private Vector3f m_111352_(JsonObject p_111353_) {
         Vector3f vector3f = this.m_111334_(p_111353_, "from");
         if (!(vector3f.m_122239_() < -16.0F) && !(vector3f.m_122260_() < -16.0F) && !(vector3f.m_122269_() < -16.0F) && !(vector3f.m_122239_() > 32.0F) && !(vector3f.m_122260_() > 32.0F) && !(vector3f.m_122269_() > 32.0F)) {
            return vector3f;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
         }
      }

      private Vector3f m_111334_(JsonObject p_111335_, String p_111336_) {
         JsonArray jsonarray = GsonHelper.m_13933_(p_111335_, p_111336_);
         if (jsonarray.size() != 3) {
            throw new JsonParseException("Expected 3 " + p_111336_ + " values, found: " + jsonarray.size());
         } else {
            float[] afloat = new float[3];

            for(int i = 0; i < afloat.length; ++i) {
               afloat[i] = GsonHelper.m_13888_(jsonarray.get(i), p_111336_ + "[" + i + "]");
            }

            return new Vector3f(afloat[0], afloat[1], afloat[2]);
         }
      }
   }
}