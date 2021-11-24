package net.minecraft.world.level.block;

import com.mojang.math.OctahedralGroup;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum Mirror {
   NONE(new TranslatableComponent("mirror.none"), OctahedralGroup.IDENTITY),
   LEFT_RIGHT(new TranslatableComponent("mirror.left_right"), OctahedralGroup.INVERT_Z),
   FRONT_BACK(new TranslatableComponent("mirror.front_back"), OctahedralGroup.INVERT_X);

   private final Component f_153781_;
   private final OctahedralGroup f_54835_;

   private Mirror(Component p_153785_, OctahedralGroup p_153786_) {
      this.f_153781_ = p_153785_;
      this.f_54835_ = p_153786_;
   }

   public int m_54843_(int p_54844_, int p_54845_) {
      int i = p_54845_ / 2;
      int j = p_54844_ > i ? p_54844_ - p_54845_ : p_54844_;
      switch(this) {
      case FRONT_BACK:
         return (p_54845_ - j) % p_54845_;
      case LEFT_RIGHT:
         return (i - j + p_54845_) % p_54845_;
      default:
         return p_54844_;
      }
   }

   public Rotation m_54846_(Direction p_54847_) {
      Direction.Axis direction$axis = p_54847_.m_122434_();
      return (this != LEFT_RIGHT || direction$axis != Direction.Axis.Z) && (this != FRONT_BACK || direction$axis != Direction.Axis.X) ? Rotation.NONE : Rotation.CLOCKWISE_180;
   }

   public Direction m_54848_(Direction p_54849_) {
      if (this == FRONT_BACK && p_54849_.m_122434_() == Direction.Axis.X) {
         return p_54849_.m_122424_();
      } else {
         return this == LEFT_RIGHT && p_54849_.m_122434_() == Direction.Axis.Z ? p_54849_.m_122424_() : p_54849_;
      }
   }

   public OctahedralGroup m_54842_() {
      return this.f_54835_;
   }

   public Component m_153787_() {
      return this.f_153781_;
   }
}