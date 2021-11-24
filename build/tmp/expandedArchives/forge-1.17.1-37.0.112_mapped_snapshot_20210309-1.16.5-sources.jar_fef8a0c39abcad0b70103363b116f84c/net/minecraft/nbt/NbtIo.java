package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;

public class NbtIo {
   public static CompoundTag m_128937_(File p_128938_) throws IOException {
      InputStream inputstream = new FileInputStream(p_128938_);

      CompoundTag compoundtag;
      try {
         compoundtag = m_128939_(inputstream);
      } catch (Throwable throwable1) {
         try {
            inputstream.close();
         } catch (Throwable throwable) {
            throwable1.addSuppressed(throwable);
         }

         throw throwable1;
      }

      inputstream.close();
      return compoundtag;
   }

   public static CompoundTag m_128939_(InputStream p_128940_) throws IOException {
      DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(p_128940_)));

      CompoundTag compoundtag;
      try {
         compoundtag = m_128934_(datainputstream, NbtAccounter.f_128917_);
      } catch (Throwable throwable1) {
         try {
            datainputstream.close();
         } catch (Throwable throwable) {
            throwable1.addSuppressed(throwable);
         }

         throw throwable1;
      }

      datainputstream.close();
      return compoundtag;
   }

   public static void m_128944_(CompoundTag p_128945_, File p_128946_) throws IOException {
      OutputStream outputstream = new FileOutputStream(p_128946_);

      try {
         m_128947_(p_128945_, outputstream);
      } catch (Throwable throwable1) {
         try {
            outputstream.close();
         } catch (Throwable throwable) {
            throwable1.addSuppressed(throwable);
         }

         throw throwable1;
      }

      outputstream.close();
   }

   public static void m_128947_(CompoundTag p_128948_, OutputStream p_128949_) throws IOException {
      DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(p_128949_)));

      try {
         m_128941_(p_128948_, dataoutputstream);
      } catch (Throwable throwable1) {
         try {
            dataoutputstream.close();
         } catch (Throwable throwable) {
            throwable1.addSuppressed(throwable);
         }

         throw throwable1;
      }

      dataoutputstream.close();
   }

   public static void m_128955_(CompoundTag p_128956_, File p_128957_) throws IOException {
      FileOutputStream fileoutputstream = new FileOutputStream(p_128957_);

      try {
         DataOutputStream dataoutputstream = new DataOutputStream(fileoutputstream);

         try {
            m_128941_(p_128956_, dataoutputstream);
         } catch (Throwable throwable2) {
            try {
               dataoutputstream.close();
            } catch (Throwable throwable1) {
               throwable2.addSuppressed(throwable1);
            }

            throw throwable2;
         }

         dataoutputstream.close();
      } catch (Throwable throwable3) {
         try {
            fileoutputstream.close();
         } catch (Throwable throwable) {
            throwable3.addSuppressed(throwable);
         }

         throw throwable3;
      }

      fileoutputstream.close();
   }

   @Nullable
   public static CompoundTag m_128953_(File p_128954_) throws IOException {
      if (!p_128954_.exists()) {
         return null;
      } else {
         FileInputStream fileinputstream = new FileInputStream(p_128954_);

         CompoundTag compoundtag;
         try {
            DataInputStream datainputstream = new DataInputStream(fileinputstream);

            try {
               compoundtag = m_128934_(datainputstream, NbtAccounter.f_128917_);
            } catch (Throwable throwable2) {
               try {
                  datainputstream.close();
               } catch (Throwable throwable1) {
                  throwable2.addSuppressed(throwable1);
               }

               throw throwable2;
            }

            datainputstream.close();
         } catch (Throwable throwable3) {
            try {
               fileinputstream.close();
            } catch (Throwable throwable) {
               throwable3.addSuppressed(throwable);
            }

            throw throwable3;
         }

         fileinputstream.close();
         return compoundtag;
      }
   }

   public static CompoundTag m_128928_(DataInput p_128929_) throws IOException {
      return m_128934_(p_128929_, NbtAccounter.f_128917_);
   }

   public static CompoundTag m_128934_(DataInput p_128935_, NbtAccounter p_128936_) throws IOException {
      Tag tag = m_128930_(p_128935_, 0, p_128936_);
      if (tag instanceof CompoundTag) {
         return (CompoundTag)tag;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void m_128941_(CompoundTag p_128942_, DataOutput p_128943_) throws IOException {
      m_128950_(p_128942_, p_128943_);
   }

   private static void m_128950_(Tag p_128951_, DataOutput p_128952_) throws IOException {
      p_128952_.writeByte(p_128951_.m_7060_());
      if (p_128951_.m_7060_() != 0) {
         p_128952_.writeUTF("");
         p_128951_.m_6434_(p_128952_);
      }
   }

   private static Tag m_128930_(DataInput p_128931_, int p_128932_, NbtAccounter p_128933_) throws IOException {
      byte b0 = p_128931_.readByte();
      p_128933_.m_6800_(8); // Forge: Count everything!
      if (b0 == 0) {
         return EndTag.f_128534_;
      } else {
         p_128933_.readUTF(p_128931_.readUTF()); //Forge: Count this string.
         p_128933_.m_6800_(32); //Forge: 4 extra bytes for the object allocation.

         try {
            return TagTypes.m_129397_(b0).m_7300_(p_128931_, p_128932_, p_128933_);
         } catch (IOException ioexception) {
            CrashReport crashreport = CrashReport.m_127521_(ioexception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("NBT Tag");
            crashreportcategory.m_128159_("Tag type", b0);
            throw new ReportedException(crashreport);
         }
      }
   }
}
