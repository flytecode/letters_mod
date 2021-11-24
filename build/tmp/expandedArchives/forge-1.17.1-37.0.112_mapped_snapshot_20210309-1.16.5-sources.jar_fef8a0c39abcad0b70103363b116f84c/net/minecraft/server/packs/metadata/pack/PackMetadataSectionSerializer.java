package net.minecraft.server.packs.metadata.pack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;

public class PackMetadataSectionSerializer implements MetadataSectionSerializer<PackMetadataSection> {
   public PackMetadataSection m_6322_(JsonObject p_10380_) {
      Component component = Component.Serializer.m_130691_(p_10380_.get("description"));
      if (component == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int i = GsonHelper.m_13927_(p_10380_, "pack_format");
         return new PackMetadataSection(component, i);
      }
   }

   public String m_7991_() {
      return "pack";
   }
}