package net.minecraft.data.models.blockstates;

import com.google.gson.JsonPrimitive;
import net.minecraft.resources.ResourceLocation;

public class VariantProperties {
   public static final VariantProperty<VariantProperties.Rotation> f_125518_ = new VariantProperty<>("x", (p_125529_) -> {
      return new JsonPrimitive(p_125529_.f_125534_);
   });
   public static final VariantProperty<VariantProperties.Rotation> f_125519_ = new VariantProperty<>("y", (p_125525_) -> {
      return new JsonPrimitive(p_125525_.f_125534_);
   });
   public static final VariantProperty<ResourceLocation> f_125520_ = new VariantProperty<>("model", (p_125527_) -> {
      return new JsonPrimitive(p_125527_.toString());
   });
   public static final VariantProperty<Boolean> f_125521_ = new VariantProperty<>("uvlock", JsonPrimitive::new);
   public static final VariantProperty<Integer> f_125522_ = new VariantProperty<>("weight", JsonPrimitive::new);

   public static enum Rotation {
      R0(0),
      R90(90),
      R180(180),
      R270(270);

      final int f_125534_;

      private Rotation(int p_125540_) {
         this.f_125534_ = p_125540_;
      }
   }
}