package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.ExceptionCollector;
import net.minecraft.world.level.ChunkPos;

public final class RegionFileStorage implements AutoCloseable {
   public static final String f_156615_ = ".mca";
   private static final int f_156616_ = 256;
   private final Long2ObjectLinkedOpenHashMap<RegionFile> f_63699_ = new Long2ObjectLinkedOpenHashMap<>();
   private final File f_63700_;
   private final boolean f_63701_;

   RegionFileStorage(File p_63703_, boolean p_63704_) {
      this.f_63700_ = p_63703_;
      this.f_63701_ = p_63704_;
   }

   private RegionFile m_63711_(ChunkPos p_63712_) throws IOException {
      long i = ChunkPos.m_45589_(p_63712_.m_45610_(), p_63712_.m_45612_());
      RegionFile regionfile = this.f_63699_.getAndMoveToFirst(i);
      if (regionfile != null) {
         return regionfile;
      } else {
         if (this.f_63699_.size() >= 256) {
            this.f_63699_.removeLast().close();
         }

         if (!this.f_63700_.exists()) {
            this.f_63700_.mkdirs();
         }

         File file1 = new File(this.f_63700_, "r." + p_63712_.m_45610_() + "." + p_63712_.m_45612_() + ".mca");
         RegionFile regionfile1 = new RegionFile(file1, this.f_63700_, this.f_63701_);
         this.f_63699_.putAndMoveToFirst(i, regionfile1);
         return regionfile1;
      }
   }

   @Nullable
   public CompoundTag m_63706_(ChunkPos p_63707_) throws IOException {
      RegionFile regionfile = this.m_63711_(p_63707_);
      DataInputStream datainputstream = regionfile.m_63645_(p_63707_);

      CompoundTag compoundtag;
      label43: {
         try {
            if (datainputstream == null) {
               compoundtag = null;
               break label43;
            }

            compoundtag = NbtIo.m_128928_(datainputstream);
         } catch (Throwable throwable1) {
            if (datainputstream != null) {
               try {
                  datainputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (datainputstream != null) {
            datainputstream.close();
         }

         return compoundtag;
      }

      if (datainputstream != null) {
         datainputstream.close();
      }

      return compoundtag;
   }

   protected void m_63708_(ChunkPos p_63709_, @Nullable CompoundTag p_63710_) throws IOException {
      RegionFile regionfile = this.m_63711_(p_63709_);
      if (p_63710_ == null) {
         regionfile.m_156613_(p_63709_);
      } else {
         DataOutputStream dataoutputstream = regionfile.m_63678_(p_63709_);

         try {
            NbtIo.m_128941_(p_63710_, dataoutputstream);
         } catch (Throwable throwable1) {
            if (dataoutputstream != null) {
               try {
                  dataoutputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (dataoutputstream != null) {
            dataoutputstream.close();
         }
      }

   }

   public void close() throws IOException {
      ExceptionCollector<IOException> exceptioncollector = new ExceptionCollector<>();

      for(RegionFile regionfile : this.f_63699_.values()) {
         try {
            regionfile.close();
         } catch (IOException ioexception) {
            exceptioncollector.m_13653_(ioexception);
         }
      }

      exceptioncollector.m_13652_();
   }

   public void m_63705_() throws IOException {
      for(RegionFile regionfile : this.f_63699_.values()) {
         regionfile.m_63637_();
      }

   }
}