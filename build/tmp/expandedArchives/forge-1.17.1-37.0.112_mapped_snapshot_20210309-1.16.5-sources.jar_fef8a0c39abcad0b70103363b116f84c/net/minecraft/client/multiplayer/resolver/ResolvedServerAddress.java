package net.minecraft.client.multiplayer.resolver;

import java.net.InetSocketAddress;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ResolvedServerAddress {
   String m_142727_();

   String m_142728_();

   int m_142599_();

   InetSocketAddress m_142641_();

   static ResolvedServerAddress m_171845_(final InetSocketAddress p_171846_) {
      return new ResolvedServerAddress() {
         public String m_142727_() {
            return p_171846_.getAddress().getHostName();
         }

         public String m_142728_() {
            return p_171846_.getAddress().getHostAddress();
         }

         public int m_142599_() {
            return p_171846_.getPort();
         }

         public InetSocketAddress m_142641_() {
            return p_171846_;
         }
      };
   }
}