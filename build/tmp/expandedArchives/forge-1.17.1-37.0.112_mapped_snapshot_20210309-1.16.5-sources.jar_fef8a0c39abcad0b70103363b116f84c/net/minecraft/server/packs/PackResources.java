package net.minecraft.server.packs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;

public interface PackResources extends AutoCloseable, net.minecraftforge.common.extensions.IForgePackResources {
   String f_143748_ = ".mcmeta";
   String f_143749_ = "pack.mcmeta";

   @Nullable
   InputStream m_5542_(String p_10294_) throws IOException;

   InputStream m_8031_(PackType p_10289_, ResourceLocation p_10290_) throws IOException;

   Collection<ResourceLocation> m_7466_(PackType p_10284_, String p_10285_, String p_10286_, int p_10287_, Predicate<String> p_10288_);

   boolean m_7211_(PackType p_10292_, ResourceLocation p_10293_);

   Set<String> m_5698_(PackType p_10283_);

   @Nullable
   <T> T m_5550_(MetadataSectionSerializer<T> p_10291_) throws IOException;

   String m_8017_();

   void close();
}
