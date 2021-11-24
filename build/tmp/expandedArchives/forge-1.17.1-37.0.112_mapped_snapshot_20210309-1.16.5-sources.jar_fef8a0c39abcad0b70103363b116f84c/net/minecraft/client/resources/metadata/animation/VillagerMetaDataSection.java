package net.minecraft.client.resources.metadata.animation;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillagerMetaDataSection {
   public static final VillagerMetadataSectionSerializer f_119065_ = new VillagerMetadataSectionSerializer();
   public static final String f_174866_ = "villager";
   private final VillagerMetaDataSection.Hat f_119066_;

   public VillagerMetaDataSection(VillagerMetaDataSection.Hat p_119069_) {
      this.f_119066_ = p_119069_;
   }

   public VillagerMetaDataSection.Hat m_119070_() {
      return this.f_119066_;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Hat {
      NONE("none"),
      PARTIAL("partial"),
      FULL("full");

      private static final Map<String, VillagerMetaDataSection.Hat> f_119074_ = Arrays.stream(values()).collect(Collectors.toMap(VillagerMetaDataSection.Hat::m_119082_, (p_119084_) -> {
         return p_119084_;
      }));
      private final String f_119075_;

      private Hat(String p_119081_) {
         this.f_119075_ = p_119081_;
      }

      public String m_119082_() {
         return this.f_119075_;
      }

      public static VillagerMetaDataSection.Hat m_119085_(String p_119086_) {
         return f_119074_.getOrDefault(p_119086_, NONE);
      }
   }
}