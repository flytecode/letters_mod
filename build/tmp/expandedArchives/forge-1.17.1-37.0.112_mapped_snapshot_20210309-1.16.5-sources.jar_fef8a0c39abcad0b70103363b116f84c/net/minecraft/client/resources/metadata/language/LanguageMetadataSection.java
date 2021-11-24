package net.minecraft.client.resources.metadata.language;

import java.util.Collection;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LanguageMetadataSection {
   public static final LanguageMetadataSectionSerializer f_119096_ = new LanguageMetadataSectionSerializer();
   public static final boolean f_174868_ = false;
   private final Collection<LanguageInfo> f_119097_;

   public LanguageMetadataSection(Collection<LanguageInfo> p_119100_) {
      this.f_119097_ = p_119100_;
   }

   public Collection<LanguageInfo> m_119101_() {
      return this.f_119097_;
   }
}