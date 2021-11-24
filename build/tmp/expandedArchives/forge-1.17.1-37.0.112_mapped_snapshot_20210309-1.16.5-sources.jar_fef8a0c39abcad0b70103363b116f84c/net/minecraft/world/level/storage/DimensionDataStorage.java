package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionDataStorage {
   private static final Logger f_78143_ = LogManager.getLogger();
   private final Map<String, SavedData> f_78144_ = Maps.newHashMap();
   private final DataFixer f_78145_;
   private final File f_78146_;

   public DimensionDataStorage(File p_78149_, DataFixer p_78150_) {
      this.f_78145_ = p_78150_;
      this.f_78146_ = p_78149_;
   }

   private File m_78156_(String p_78157_) {
      return new File(this.f_78146_, p_78157_ + ".dat");
   }

   public <T extends SavedData> T m_164861_(Function<CompoundTag, T> p_164862_, Supplier<T> p_164863_, String p_164864_) {
      T t = this.m_164858_(p_164862_, p_164864_);
      if (t != null) {
         return t;
      } else {
         T t1 = p_164863_.get();
         this.m_164855_(p_164864_, t1);
         return t1;
      }
   }

   @Nullable
   public <T extends SavedData> T m_164858_(Function<CompoundTag, T> p_164859_, String p_164860_) {
      SavedData saveddata = this.f_78144_.get(p_164860_);
      if (saveddata == net.minecraftforge.common.util.DummySavedData.DUMMY) return null;
      if (saveddata == null && !this.f_78144_.containsKey(p_164860_)) {
         saveddata = this.m_164868_(p_164859_, p_164860_);
         this.f_78144_.put(p_164860_, saveddata);
      } else if (saveddata == null) {
         this.f_78144_.put(p_164860_, net.minecraftforge.common.util.DummySavedData.DUMMY);
         return null;
      }

      return (T)saveddata;
   }

   @Nullable
   private <T extends SavedData> T m_164868_(Function<CompoundTag, T> p_164869_, String p_164870_) {
      try {
         File file1 = this.m_78156_(p_164870_);
         if (file1.exists()) {
            CompoundTag compoundtag = this.m_78158_(p_164870_, SharedConstants.m_136187_().getWorldVersion());
            return p_164869_.apply(compoundtag.m_128469_("data"));
         }
      } catch (Exception exception) {
         f_78143_.error("Error loading saved data: {}", p_164870_, exception);
      }

      return (T)null;
   }

   public void m_164855_(String p_164856_, SavedData p_164857_) {
      this.f_78144_.put(p_164856_, p_164857_);
   }

   public CompoundTag m_78158_(String p_78159_, int p_78160_) throws IOException {
      File file1 = this.m_78156_(p_78159_);
      FileInputStream fileinputstream = new FileInputStream(file1);

      CompoundTag compoundtag1;
      try {
         PushbackInputStream pushbackinputstream = new PushbackInputStream(fileinputstream, 2);

         try {
            CompoundTag compoundtag;
            if (this.m_78154_(pushbackinputstream)) {
               compoundtag = NbtIo.m_128939_(pushbackinputstream);
            } else {
               DataInputStream datainputstream = new DataInputStream(pushbackinputstream);

               try {
                  compoundtag = NbtIo.m_128928_(datainputstream);
               } catch (Throwable throwable3) {
                  try {
                     datainputstream.close();
                  } catch (Throwable throwable2) {
                     throwable3.addSuppressed(throwable2);
                  }

                  throw throwable3;
               }

               datainputstream.close();
            }

            int i = compoundtag.m_128425_("DataVersion", 99) ? compoundtag.m_128451_("DataVersion") : 1343;
            compoundtag1 = NbtUtils.m_129218_(this.f_78145_, DataFixTypes.SAVED_DATA, compoundtag, i, p_78160_);
         } catch (Throwable throwable4) {
            try {
               pushbackinputstream.close();
            } catch (Throwable throwable1) {
               throwable4.addSuppressed(throwable1);
            }

            throw throwable4;
         }

         pushbackinputstream.close();
      } catch (Throwable throwable5) {
         try {
            fileinputstream.close();
         } catch (Throwable throwable) {
            throwable5.addSuppressed(throwable);
         }

         throw throwable5;
      }

      fileinputstream.close();
      return compoundtag1;
   }

   private boolean m_78154_(PushbackInputStream p_78155_) throws IOException {
      byte[] abyte = new byte[2];
      boolean flag = false;
      int i = p_78155_.read(abyte, 0, 2);
      if (i == 2) {
         int j = (abyte[1] & 255) << 8 | abyte[0] & 255;
         if (j == 35615) {
            flag = true;
         }
      }

      if (i != 0) {
         p_78155_.unread(abyte, 0, i);
      }

      return flag;
   }

   public void m_78151_() {
      this.f_78144_.forEach((p_164866_, p_164867_) -> {
         if (p_164867_ != null) {
            p_164867_.m_77757_(this.m_78156_(p_164866_));
         }

      });
   }
}
