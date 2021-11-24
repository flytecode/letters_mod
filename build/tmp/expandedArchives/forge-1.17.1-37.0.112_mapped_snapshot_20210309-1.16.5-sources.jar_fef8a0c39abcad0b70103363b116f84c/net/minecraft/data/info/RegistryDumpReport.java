package net.minecraft.data.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

public class RegistryDumpReport implements DataProvider {
   private static final Gson f_124049_ = (new GsonBuilder()).setPrettyPrinting().create();
   private final DataGenerator f_124050_;

   public RegistryDumpReport(DataGenerator p_124053_) {
      this.f_124050_ = p_124053_;
   }

   public void m_6865_(HashCache p_124061_) throws IOException {
      JsonObject jsonobject = new JsonObject();
      Registry.f_122897_.m_6566_().forEach((p_124057_) -> {
         jsonobject.add(p_124057_.toString(), m_124058_(Registry.f_122897_.m_7745_(p_124057_)));
      });
      Path path = this.f_124050_.m_123916_().resolve("reports/registries.json");
      DataProvider.m_123920_(f_124049_, p_124061_, jsonobject, path);
   }

   private static <T> JsonElement m_124058_(Registry<T> p_124059_) {
      JsonObject jsonobject = new JsonObject();
      if (p_124059_ instanceof DefaultedRegistry) {
         ResourceLocation resourcelocation = ((DefaultedRegistry)p_124059_).m_122315_();
         jsonobject.addProperty("default", resourcelocation.toString());
      }

      int j = ((Registry)Registry.f_122897_).m_7447_(p_124059_);
      jsonobject.addProperty("protocol_id", j);
      JsonObject jsonobject1 = new JsonObject();

      for(ResourceLocation resourcelocation1 : p_124059_.m_6566_()) {
         T t = p_124059_.m_7745_(resourcelocation1);
         int i = p_124059_.m_7447_(t);
         JsonObject jsonobject2 = new JsonObject();
         jsonobject2.addProperty("protocol_id", i);
         jsonobject1.add(resourcelocation1.toString(), jsonobject2);
      }

      jsonobject.add("entries", jsonobject1);
      return jsonobject;
   }

   public String m_6055_() {
      return "Registry Dump";
   }
}