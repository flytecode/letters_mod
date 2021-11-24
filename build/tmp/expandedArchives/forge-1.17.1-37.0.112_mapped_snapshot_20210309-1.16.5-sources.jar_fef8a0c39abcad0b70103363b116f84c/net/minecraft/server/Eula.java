package net.minecraft.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Eula {
   private static final Logger f_135938_ = LogManager.getLogger();
   private final Path f_135939_;
   private final boolean f_135940_;

   public Eula(Path p_135943_) {
      this.f_135939_ = p_135943_;
      this.f_135940_ = SharedConstants.f_136183_ || this.m_135945_();
   }

   private boolean m_135945_() {
      try {
         InputStream inputstream = Files.newInputStream(this.f_135939_);

         boolean flag;
         try {
            Properties properties = new Properties();
            properties.load(inputstream);
            flag = Boolean.parseBoolean(properties.getProperty("eula", "false"));
         } catch (Throwable throwable1) {
            if (inputstream != null) {
               try {
                  inputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (inputstream != null) {
            inputstream.close();
         }

         return flag;
      } catch (Exception exception) {
         f_135938_.warn("Failed to load {}", (Object)this.f_135939_);
         this.m_135946_();
         return false;
      }
   }

   public boolean m_135944_() {
      return this.f_135940_;
   }

   private void m_135946_() {
      if (!SharedConstants.f_136183_) {
         try {
            OutputStream outputstream = Files.newOutputStream(this.f_135939_);

            try {
               Properties properties = new Properties();
               properties.setProperty("eula", "false");
               properties.store(outputstream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
            } catch (Throwable throwable1) {
               if (outputstream != null) {
                  try {
                     outputstream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (outputstream != null) {
               outputstream.close();
            }
         } catch (Exception exception) {
            f_135938_.warn("Failed to save {}", this.f_135939_, exception);
         }

      }
   }
}