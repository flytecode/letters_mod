package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SplashManager extends SimplePreparableReloadListener<List<String>> {
   private static final ResourceLocation f_118860_ = new ResourceLocation("texts/splashes.txt");
   private static final Random f_118861_ = new Random();
   private final List<String> f_118862_ = Lists.newArrayList();
   private final User f_118863_;

   public SplashManager(User p_118866_) {
      this.f_118863_ = p_118866_;
   }

   protected List<String> m_5944_(ResourceManager p_118869_, ProfilerFiller p_118870_) {
      try {
         Resource resource = Minecraft.m_91087_().m_91098_().m_142591_(f_118860_);

         List list;
         try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(resource.m_6679_(), StandardCharsets.UTF_8));

            try {
               list = bufferedreader.lines().map(String::trim).filter((p_118876_) -> {
                  return p_118876_.hashCode() != 125780783;
               }).collect(Collectors.toList());
            } catch (Throwable throwable2) {
               try {
                  bufferedreader.close();
               } catch (Throwable throwable1) {
                  throwable2.addSuppressed(throwable1);
               }

               throw throwable2;
            }

            bufferedreader.close();
         } catch (Throwable throwable3) {
            if (resource != null) {
               try {
                  resource.close();
               } catch (Throwable throwable) {
                  throwable3.addSuppressed(throwable);
               }
            }

            throw throwable3;
         }

         if (resource != null) {
            resource.close();
         }

         return list;
      } catch (IOException ioexception) {
         return Collections.emptyList();
      }
   }

   protected void m_5787_(List<String> p_118878_, ResourceManager p_118879_, ProfilerFiller p_118880_) {
      this.f_118862_.clear();
      this.f_118862_.addAll(p_118878_);
   }

   @Nullable
   public String m_118867_() {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
         return "Merry X-mas!";
      } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
         return "Happy new year!";
      } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
         return "OOoooOOOoooo! Spooky!";
      } else if (this.f_118862_.isEmpty()) {
         return null;
      } else {
         return this.f_118863_ != null && f_118861_.nextInt(this.f_118862_.size()) == 42 ? this.f_118863_.m_92546_().toUpperCase(Locale.ROOT) + " IS YOU" : this.f_118862_.get(f_118861_.nextInt(this.f_118862_.size()));
      }
   }
}