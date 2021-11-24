package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class AssetIndex {
   protected static final Logger f_118524_ = LogManager.getLogger();
   private final Map<String, File> f_118525_ = Maps.newHashMap();
   private final Map<ResourceLocation, File> f_118526_ = Maps.newHashMap();

   protected AssetIndex() {
   }

   public AssetIndex(File p_118530_, String p_118531_) {
      File file1 = new File(p_118530_, "objects");
      File file2 = new File(p_118530_, "indexes/" + p_118531_ + ".json");
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = Files.newReader(file2, StandardCharsets.UTF_8);
         JsonObject jsonobject = GsonHelper.m_13859_(bufferedreader);
         JsonObject jsonobject1 = GsonHelper.m_13841_(jsonobject, "objects", (JsonObject)null);
         if (jsonobject1 != null) {
            for(Entry<String, JsonElement> entry : jsonobject1.entrySet()) {
               JsonObject jsonobject2 = (JsonObject)entry.getValue();
               String s = entry.getKey();
               String[] astring = s.split("/", 2);
               String s1 = GsonHelper.m_13906_(jsonobject2, "hash");
               File file3 = new File(file1, s1.substring(0, 2) + "/" + s1);
               if (astring.length == 1) {
                  this.f_118525_.put(astring[0], file3);
               } else {
                  this.f_118526_.put(new ResourceLocation(astring[0], astring[1]), file3);
               }
            }
         }
      } catch (JsonParseException jsonparseexception) {
         f_118524_.error("Unable to parse resource index file: {}", (Object)file2);
      } catch (FileNotFoundException filenotfoundexception) {
         f_118524_.error("Can't find the resource index file: {}", (Object)file2);
      } finally {
         IOUtils.closeQuietly((Reader)bufferedreader);
      }

   }

   @Nullable
   public File m_7879_(ResourceLocation p_118542_) {
      return this.f_118526_.get(p_118542_);
   }

   @Nullable
   public File m_7974_(String p_118532_) {
      return this.f_118525_.get(p_118532_);
   }

   public Collection<ResourceLocation> m_6487_(String p_118533_, String p_118534_, int p_118535_, Predicate<String> p_118536_) {
      return this.f_118526_.keySet().stream().filter((p_118541_) -> {
         String s = p_118541_.m_135815_();
         return p_118541_.m_135827_().equals(p_118534_) && !s.endsWith(".mcmeta") && s.startsWith(p_118533_ + "/") && p_118536_.test(s);
      }).collect(Collectors.toList());
   }
}