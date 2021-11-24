package net.minecraft.world;

import com.google.common.collect.Maps;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.Util;

public class Snooper {
   private static final String f_146669_ = "http://snoop.minecraft.net/";
   private static final long f_146670_ = 900000L;
   private static final int f_146671_ = 2;
   final Map<String, Object> f_19209_ = Maps.newHashMap();
   final Map<String, Object> f_19210_ = Maps.newHashMap();
   final String f_19211_ = UUID.randomUUID().toString();
   final URL f_19212_;
   final SnooperPopulator f_19213_;
   private final Timer f_19214_ = new Timer("Snooper Timer", true);
   final Object f_19215_ = new Object();
   private final long f_19216_;
   private boolean f_19217_;
   int f_146672_;

   public Snooper(String p_19219_, SnooperPopulator p_19220_, long p_19221_) {
      try {
         this.f_19212_ = new URL("http://snoop.minecraft.net/" + p_19219_ + "?version=2");
      } catch (MalformedURLException malformedurlexception) {
         throw new IllegalArgumentException();
      }

      this.f_19213_ = p_19220_;
      this.f_19216_ = p_19221_;
   }

   public void m_19222_() {
      if (!this.f_19217_) {
      }

   }

   private void m_146677_() {
      this.m_146678_();
      this.m_19223_("snooper_token", this.f_19211_);
      this.m_19227_("snooper_token", this.f_19211_);
      this.m_19227_("os_name", System.getProperty("os.name"));
      this.m_19227_("os_version", System.getProperty("os.version"));
      this.m_19227_("os_architecture", System.getProperty("os.arch"));
      this.m_19227_("java_version", System.getProperty("java.version"));
      this.m_19223_("version", SharedConstants.m_136187_().getId());
      this.f_19213_.m_142423_(this);
   }

   private void m_146678_() {
      int[] aint = new int[]{0};
      Util.m_137582_().forEach((p_146675_) -> {
         int j = aint[0];
         int i = aint[0];
         aint[0] = j + 1;
         this.m_19223_("jvm_arg[" + i + "]", p_146675_);
      });
      this.m_19223_("jvm_args", aint[0]);
   }

   public void m_19226_() {
      this.m_19227_("memory_total", Runtime.getRuntime().totalMemory());
      this.m_19227_("memory_max", Runtime.getRuntime().maxMemory());
      this.m_19227_("memory_free", Runtime.getRuntime().freeMemory());
      this.m_19227_("cpu_cores", Runtime.getRuntime().availableProcessors());
      this.f_19213_.m_7108_(this);
   }

   public void m_19223_(String p_19224_, Object p_19225_) {
      synchronized(this.f_19215_) {
         this.f_19210_.put(p_19224_, p_19225_);
      }
   }

   public void m_19227_(String p_19228_, Object p_19229_) {
      synchronized(this.f_19215_) {
         this.f_19209_.put(p_19228_, p_19229_);
      }
   }

   public Map<String, String> m_146676_() {
      Map<String, String> map = Maps.newLinkedHashMap();
      synchronized(this.f_19215_) {
         this.m_19226_();

         for(Entry<String, Object> entry : this.f_19209_.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
         }

         for(Entry<String, Object> entry1 : this.f_19210_.entrySet()) {
            map.put(entry1.getKey(), entry1.getValue().toString());
         }

         return map;
      }
   }

   public boolean m_19230_() {
      return this.f_19217_;
   }

   public void m_19231_() {
      this.f_19214_.cancel();
   }

   public String m_19232_() {
      return this.f_19211_;
   }

   public long m_19233_() {
      return this.f_19216_;
   }
}