package net.minecraft.server.packs.repository;

import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class ServerPacksSource implements RepositorySource {
   public static final PackMetadataSection f_143904_ = new PackMetadataSection(new TranslatableComponent("dataPack.vanilla.description"), PackType.SERVER_DATA.m_143756_(SharedConstants.m_136187_()));
   public static final String f_143905_ = "vanilla";
   private final VanillaPackResources f_10544_ = new VanillaPackResources(f_143904_, "minecraft");

   public void m_7686_(Consumer<Pack> p_10548_, Pack.PackConstructor p_10549_) {
      Pack pack = Pack.m_10430_("vanilla", false, () -> {
         return this.f_10544_;
      }, p_10549_, Pack.Position.BOTTOM, PackSource.f_10528_);
      if (pack != null) {
         p_10548_.accept(pack);
      }

   }
}