package net.minecraft.world.level.block.state.properties;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;
import java.util.stream.Stream;

public class WoodType {
   private static final Set<WoodType> f_61838_ = new ObjectArraySet<>();
   public static final WoodType f_61830_ = m_61844_(new WoodType("oak"));
   public static final WoodType f_61831_ = m_61844_(new WoodType("spruce"));
   public static final WoodType f_61832_ = m_61844_(new WoodType("birch"));
   public static final WoodType f_61833_ = m_61844_(new WoodType("acacia"));
   public static final WoodType f_61834_ = m_61844_(new WoodType("jungle"));
   public static final WoodType f_61835_ = m_61844_(new WoodType("dark_oak"));
   public static final WoodType f_61836_ = m_61844_(new WoodType("crimson"));
   public static final WoodType f_61837_ = m_61844_(new WoodType("warped"));
   private final String f_61839_;

   protected WoodType(String p_61842_) {
      this.f_61839_ = p_61842_;
   }

   public static WoodType m_61844_(WoodType p_61845_) {
      f_61838_.add(p_61845_);
      return p_61845_;
   }

   public static Stream<WoodType> m_61843_() {
      return f_61838_.stream();
   }

   public String m_61846_() {
      return this.f_61839_;
   }

   /**
    * Use this to create a new {@link WoodType}. Make sure to register its rendering by enqueuing Atlases.addWoodType(...) during client setup..
    */
   public static WoodType create(String name) {
      return new WoodType(name);
   }
}
