package net.minecraft.world.level.material;

import net.minecraft.core.Registry;

public class Fluids {
   public static final Fluid f_76191_ = m_76197_("empty", new EmptyFluid());
   public static final FlowingFluid f_76192_ = m_76197_("flowing_water", new WaterFluid.Flowing());
   public static final FlowingFluid f_76193_ = m_76197_("water", new WaterFluid.Source());
   public static final FlowingFluid f_76194_ = m_76197_("flowing_lava", new LavaFluid.Flowing());
   public static final FlowingFluid f_76195_ = m_76197_("lava", new LavaFluid.Source());

   private static <T extends Fluid> T m_76197_(String p_76198_, T p_76199_) {
      return Registry.m_122961_(Registry.f_122822_, p_76198_, p_76199_);
   }

   static {
      for(Fluid fluid : Registry.f_122822_) {
         for(FluidState fluidstate : fluid.m_76144_().m_61056_()) {
            Fluid.f_76104_.m_122667_(fluidstate);
         }
      }

   }
}