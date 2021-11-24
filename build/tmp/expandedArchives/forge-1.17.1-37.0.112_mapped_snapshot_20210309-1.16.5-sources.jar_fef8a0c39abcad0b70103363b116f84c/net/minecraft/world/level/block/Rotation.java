package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import com.mojang.math.OctahedralGroup;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.Direction;

public enum Rotation {
   NONE(OctahedralGroup.IDENTITY),
   CLOCKWISE_90(OctahedralGroup.ROT_90_Y_NEG),
   CLOCKWISE_180(OctahedralGroup.ROT_180_FACE_XZ),
   COUNTERCLOCKWISE_90(OctahedralGroup.ROT_90_Y_POS);

   private final OctahedralGroup f_55941_;

   private Rotation(OctahedralGroup p_55947_) {
      this.f_55941_ = p_55947_;
   }

   public Rotation m_55952_(Rotation p_55953_) {
      switch(p_55953_) {
      case CLOCKWISE_180:
         switch(this) {
         case NONE:
            return CLOCKWISE_180;
         case CLOCKWISE_90:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_180:
            return NONE;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_90;
         }
      case COUNTERCLOCKWISE_90:
         switch(this) {
         case NONE:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_90:
            return NONE;
         case CLOCKWISE_180:
            return CLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_180;
         }
      case CLOCKWISE_90:
         switch(this) {
         case NONE:
            return CLOCKWISE_90;
         case CLOCKWISE_90:
            return CLOCKWISE_180;
         case CLOCKWISE_180:
            return COUNTERCLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return NONE;
         }
      default:
         return this;
      }
   }

   public OctahedralGroup m_55948_() {
      return this.f_55941_;
   }

   public Direction m_55954_(Direction p_55955_) {
      if (p_55955_.m_122434_() == Direction.Axis.Y) {
         return p_55955_;
      } else {
         switch(this) {
         case CLOCKWISE_90:
            return p_55955_.m_122427_();
         case CLOCKWISE_180:
            return p_55955_.m_122424_();
         case COUNTERCLOCKWISE_90:
            return p_55955_.m_122428_();
         default:
            return p_55955_;
         }
      }
   }

   public int m_55949_(int p_55950_, int p_55951_) {
      switch(this) {
      case CLOCKWISE_90:
         return (p_55950_ + p_55951_ / 4) % p_55951_;
      case CLOCKWISE_180:
         return (p_55950_ + p_55951_ / 2) % p_55951_;
      case COUNTERCLOCKWISE_90:
         return (p_55950_ + p_55951_ * 3 / 4) % p_55951_;
      default:
         return p_55950_;
      }
   }

   public static Rotation m_55956_(Random p_55957_) {
      return Util.m_137545_(values(), p_55957_);
   }

   public static List<Rotation> m_55958_(Random p_55959_) {
      List<Rotation> list = Lists.newArrayList(values());
      Collections.shuffle(list, p_55959_);
      return list;
   }
}