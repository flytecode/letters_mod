package net.minecraft.server.packs.resources;

import java.io.Closeable;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;

public interface Resource extends Closeable {
   ResourceLocation m_7843_();

   InputStream m_6679_();

   boolean m_142564_();

   @Nullable
   <T> T m_5507_(MetadataSectionSerializer<T> p_10725_);

   String m_7816_();
}