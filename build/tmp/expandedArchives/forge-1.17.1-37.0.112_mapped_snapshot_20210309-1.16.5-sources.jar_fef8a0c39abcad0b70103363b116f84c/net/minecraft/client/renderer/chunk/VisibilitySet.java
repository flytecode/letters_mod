package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VisibilitySet {
   private static final int f_112979_ = Direction.values().length;
   private final BitSet f_112980_ = new BitSet(f_112979_ * f_112979_);

   public void m_112990_(Set<Direction> p_112991_) {
      for(Direction direction : p_112991_) {
         for(Direction direction1 : p_112991_) {
            this.m_112986_(direction, direction1, true);
         }
      }

   }

   public void m_112986_(Direction p_112987_, Direction p_112988_, boolean p_112989_) {
      this.f_112980_.set(p_112987_.ordinal() + p_112988_.ordinal() * f_112979_, p_112989_);
      this.f_112980_.set(p_112988_.ordinal() + p_112987_.ordinal() * f_112979_, p_112989_);
   }

   public void m_112992_(boolean p_112993_) {
      this.f_112980_.set(0, this.f_112980_.size(), p_112993_);
   }

   public boolean m_112983_(Direction p_112984_, Direction p_112985_) {
      return this.f_112980_.get(p_112984_.ordinal() + p_112985_.ordinal() * f_112979_);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(' ');

      for(Direction direction : Direction.values()) {
         stringbuilder.append(' ').append(direction.toString().toUpperCase().charAt(0));
      }

      stringbuilder.append('\n');

      for(Direction direction2 : Direction.values()) {
         stringbuilder.append(direction2.toString().toUpperCase().charAt(0));

         for(Direction direction1 : Direction.values()) {
            if (direction2 == direction1) {
               stringbuilder.append("  ");
            } else {
               boolean flag = this.m_112983_(direction2, direction1);
               stringbuilder.append(' ').append((char)(flag ? 'Y' : 'n'));
            }
         }

         stringbuilder.append('\n');
      }

      return stringbuilder.toString();
   }
}