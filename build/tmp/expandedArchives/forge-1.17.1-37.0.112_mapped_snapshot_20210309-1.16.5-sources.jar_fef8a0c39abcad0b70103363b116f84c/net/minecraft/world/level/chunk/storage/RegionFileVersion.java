package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;

public class RegionFileVersion {
   private static final Int2ObjectMap<RegionFileVersion> f_63746_ = new Int2ObjectOpenHashMap<>();
   public static final RegionFileVersion f_63743_ = m_63758_(new RegionFileVersion(1, GZIPInputStream::new, GZIPOutputStream::new));
   public static final RegionFileVersion f_63744_ = m_63758_(new RegionFileVersion(2, InflaterInputStream::new, DeflaterOutputStream::new));
   public static final RegionFileVersion f_63745_ = m_63758_(new RegionFileVersion(3, (p_63767_) -> {
      return p_63767_;
   }, (p_63769_) -> {
      return p_63769_;
   }));
   private final int f_63747_;
   private final RegionFileVersion.StreamWrapper<InputStream> f_63748_;
   private final RegionFileVersion.StreamWrapper<OutputStream> f_63749_;

   private RegionFileVersion(int p_63752_, RegionFileVersion.StreamWrapper<InputStream> p_63753_, RegionFileVersion.StreamWrapper<OutputStream> p_63754_) {
      this.f_63747_ = p_63752_;
      this.f_63748_ = p_63753_;
      this.f_63749_ = p_63754_;
   }

   private static RegionFileVersion m_63758_(RegionFileVersion p_63759_) {
      f_63746_.put(p_63759_.f_63747_, p_63759_);
      return p_63759_;
   }

   @Nullable
   public static RegionFileVersion m_63756_(int p_63757_) {
      return f_63746_.get(p_63757_);
   }

   public static boolean m_63764_(int p_63765_) {
      return f_63746_.containsKey(p_63765_);
   }

   public int m_63755_() {
      return this.f_63747_;
   }

   public OutputStream m_63762_(OutputStream p_63763_) throws IOException {
      return this.f_63749_.m_63770_(p_63763_);
   }

   public InputStream m_63760_(InputStream p_63761_) throws IOException {
      return this.f_63748_.m_63770_(p_63761_);
   }

   @FunctionalInterface
   interface StreamWrapper<O> {
      O m_63770_(O p_63771_) throws IOException;
   }
}