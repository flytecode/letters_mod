package net.minecraft.data;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public interface DataProvider {
   HashFunction f_123918_ = Hashing.sha1();

   void m_6865_(HashCache p_123925_) throws IOException;

   String m_6055_();

   static void m_123920_(Gson p_123921_, HashCache p_123922_, JsonElement p_123923_, Path p_123924_) throws IOException {
      String s = p_123921_.toJson(p_123923_);
      String s1 = f_123918_.hashUnencodedChars(s).toString();
      if (!Objects.equals(p_123922_.m_123938_(p_123924_), s1) || !Files.exists(p_123924_)) {
         Files.createDirectories(p_123924_.getParent());
         BufferedWriter bufferedwriter = Files.newBufferedWriter(p_123924_);

         try {
            bufferedwriter.write(s);
         } catch (Throwable throwable1) {
            if (bufferedwriter != null) {
               try {
                  bufferedwriter.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (bufferedwriter != null) {
            bufferedwriter.close();
         }
      }

      p_123922_.m_123940_(p_123924_, s1);
   }
}