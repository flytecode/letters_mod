package net.minecraft.world.level.border;

public interface BorderChangeListener {
   void m_6312_(WorldBorder p_61847_, double p_61848_);

   void m_6689_(WorldBorder p_61852_, double p_61853_, double p_61854_, long p_61855_);

   void m_7721_(WorldBorder p_61849_, double p_61850_, double p_61851_);

   void m_5904_(WorldBorder p_61856_, int p_61857_);

   void m_5903_(WorldBorder p_61860_, int p_61861_);

   void m_6315_(WorldBorder p_61858_, double p_61859_);

   void m_6313_(WorldBorder p_61862_, double p_61863_);

   public static class DelegateBorderChangeListener implements BorderChangeListener {
      private final WorldBorder f_61864_;

      public DelegateBorderChangeListener(WorldBorder p_61866_) {
         this.f_61864_ = p_61866_;
      }

      public void m_6312_(WorldBorder p_61868_, double p_61869_) {
         this.f_61864_.m_61917_(p_61869_);
      }

      public void m_6689_(WorldBorder p_61875_, double p_61876_, double p_61877_, long p_61878_) {
         this.f_61864_.m_61919_(p_61876_, p_61877_, p_61878_);
      }

      public void m_7721_(WorldBorder p_61871_, double p_61872_, double p_61873_) {
         this.f_61864_.m_61949_(p_61872_, p_61873_);
      }

      public void m_5904_(WorldBorder p_61880_, int p_61881_) {
         this.f_61864_.m_61944_(p_61881_);
      }

      public void m_5903_(WorldBorder p_61886_, int p_61887_) {
         this.f_61864_.m_61952_(p_61887_);
      }

      public void m_6315_(WorldBorder p_61883_, double p_61884_) {
         this.f_61864_.m_61947_(p_61884_);
      }

      public void m_6313_(WorldBorder p_61889_, double p_61890_) {
         this.f_61864_.m_61939_(p_61890_);
      }
   }
}