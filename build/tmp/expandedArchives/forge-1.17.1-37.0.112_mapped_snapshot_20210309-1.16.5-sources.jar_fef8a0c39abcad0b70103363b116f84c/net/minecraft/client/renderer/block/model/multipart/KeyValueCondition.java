package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KeyValueCondition implements Condition {
   private static final Splitter f_111934_ = Splitter.on('|').omitEmptyStrings();
   private final String f_111935_;
   private final String f_111936_;

   public KeyValueCondition(String p_111939_, String p_111940_) {
      this.f_111935_ = p_111939_;
      this.f_111936_ = p_111940_;
   }

   public Predicate<BlockState> m_7289_(StateDefinition<Block, BlockState> p_111960_) {
      Property<?> property = p_111960_.m_61081_(this.f_111935_);
      if (property == null) {
         throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.f_111935_, p_111960_.m_61091_()));
      } else {
         String s = this.f_111936_;
         boolean flag = !s.isEmpty() && s.charAt(0) == '!';
         if (flag) {
            s = s.substring(1);
         }

         List<String> list = f_111934_.splitToList(s);
         if (list.isEmpty()) {
            throw new RuntimeException(String.format("Empty value '%s' for property '%s' on '%s'", this.f_111936_, this.f_111935_, p_111960_.m_61091_()));
         } else {
            Predicate<BlockState> predicate;
            if (list.size() == 1) {
               predicate = this.m_111944_(p_111960_, property, s);
            } else {
               List<Predicate<BlockState>> list1 = list.stream().map((p_111958_) -> {
                  return this.m_111944_(p_111960_, property, p_111958_);
               }).collect(Collectors.toList());
               predicate = (p_111954_) -> {
                  return list1.stream().anyMatch((p_173509_) -> {
                     return p_173509_.test(p_111954_);
                  });
               };
            }

            return flag ? predicate.negate() : predicate;
         }
      }
   }

   private Predicate<BlockState> m_111944_(StateDefinition<Block, BlockState> p_111945_, Property<?> p_111946_, String p_111947_) {
      Optional<?> optional = p_111946_.m_6215_(p_111947_);
      if (!optional.isPresent()) {
         throw new RuntimeException(String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", p_111947_, this.f_111935_, p_111945_.m_61091_(), this.f_111936_));
      } else {
         return (p_111951_) -> {
            return p_111951_.m_61143_(p_111946_).equals(optional.get());
         };
      }
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("key", this.f_111935_).add("value", this.f_111936_).toString();
   }
}