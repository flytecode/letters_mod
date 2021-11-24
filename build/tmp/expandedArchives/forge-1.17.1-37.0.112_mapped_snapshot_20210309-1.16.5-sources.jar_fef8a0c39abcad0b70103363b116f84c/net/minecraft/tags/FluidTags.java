package net.minecraft.tags;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;

public final class FluidTags {
   protected static final StaticTagHelper<Fluid> f_13130_ = StaticTags.m_144351_(Registry.f_122899_, "tags/fluids");
   private static final List<Tag<Fluid>> f_144297_ = Lists.newArrayList();
   public static final Tag.Named<Fluid> f_13131_ = m_13134_("water");
   public static final Tag.Named<Fluid> f_13132_ = m_13134_("lava");

   private FluidTags() {
   }

   public static Tag.Named<Fluid> m_13134_(String p_13135_) {
      Tag.Named<Fluid> named = f_13130_.m_13244_(p_13135_);
      f_144297_.add(named);
      return named;
   }

   public static TagCollection<Fluid> m_144299_() {
      return f_13130_.m_13246_();
   }

   @Deprecated
   public static List<Tag<Fluid>> m_144300_() {
      return f_144297_;
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Fluid> createOptional(net.minecraft.resources.ResourceLocation name) {
      return createOptional(name, null);
   }

   public static net.minecraftforge.common.Tags.IOptionalNamedTag<Fluid> createOptional(net.minecraft.resources.ResourceLocation name, @javax.annotation.Nullable java.util.Set<java.util.function.Supplier<Fluid>> defaults) {
      return f_13130_.createOptional(name, defaults);
   }
}
