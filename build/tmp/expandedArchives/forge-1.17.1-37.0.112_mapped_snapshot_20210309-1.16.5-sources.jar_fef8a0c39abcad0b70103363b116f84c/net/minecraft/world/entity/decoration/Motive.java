package net.minecraft.world.entity.decoration;

import net.minecraft.core.Registry;

public class Motive extends net.minecraftforge.registries.ForgeRegistryEntry<Motive> {
   public static final Motive f_31866_ = m_31897_("kebab", 16, 16);
   public static final Motive f_31867_ = m_31897_("aztec", 16, 16);
   public static final Motive f_31868_ = m_31897_("alban", 16, 16);
   public static final Motive f_31869_ = m_31897_("aztec2", 16, 16);
   public static final Motive f_31870_ = m_31897_("bomb", 16, 16);
   public static final Motive f_31871_ = m_31897_("plant", 16, 16);
   public static final Motive f_31872_ = m_31897_("wasteland", 16, 16);
   public static final Motive f_31873_ = m_31897_("pool", 32, 16);
   public static final Motive f_31874_ = m_31897_("courbet", 32, 16);
   public static final Motive f_31875_ = m_31897_("sea", 32, 16);
   public static final Motive f_31876_ = m_31897_("sunset", 32, 16);
   public static final Motive f_31877_ = m_31897_("creebet", 32, 16);
   public static final Motive f_31878_ = m_31897_("wanderer", 16, 32);
   public static final Motive f_31879_ = m_31897_("graham", 16, 32);
   public static final Motive f_31880_ = m_31897_("match", 32, 32);
   public static final Motive f_31881_ = m_31897_("bust", 32, 32);
   public static final Motive f_31882_ = m_31897_("stage", 32, 32);
   public static final Motive f_31883_ = m_31897_("void", 32, 32);
   public static final Motive f_31884_ = m_31897_("skull_and_roses", 32, 32);
   public static final Motive f_31885_ = m_31897_("wither", 32, 32);
   public static final Motive f_31886_ = m_31897_("fighters", 64, 32);
   public static final Motive f_31887_ = m_31897_("pointer", 64, 64);
   public static final Motive f_31888_ = m_31897_("pigscene", 64, 64);
   public static final Motive f_31889_ = m_31897_("burning_skull", 64, 64);
   public static final Motive f_31890_ = m_31897_("skeleton", 64, 48);
   public static final Motive f_31891_ = m_31897_("donkey_kong", 64, 48);
   private final int f_31864_;
   private final int f_31865_;

   private static Motive m_31897_(String p_31898_, int p_31899_, int p_31900_) {
      return Registry.m_122961_(Registry.f_122831_, p_31898_, new Motive(p_31899_, p_31900_));
   }

   public Motive(int p_31894_, int p_31895_) {
      this.f_31864_ = p_31894_;
      this.f_31865_ = p_31895_;
   }

   public int m_31896_() {
      return this.f_31864_;
   }

   public int m_31901_() {
      return this.f_31865_;
   }
}
