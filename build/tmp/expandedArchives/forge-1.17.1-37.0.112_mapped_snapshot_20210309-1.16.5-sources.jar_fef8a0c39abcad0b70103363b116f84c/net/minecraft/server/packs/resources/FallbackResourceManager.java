package net.minecraft.server.packs.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements ResourceManager {
   static final Logger f_10600_ = LogManager.getLogger();
   public final List<PackResources> f_10599_ = Lists.newArrayList();
   private final PackType f_10601_;
   private final String f_10602_;

   public FallbackResourceManager(PackType p_10605_, String p_10606_) {
      this.f_10601_ = p_10605_;
      this.f_10602_ = p_10606_;
   }

   public void m_10608_(PackResources p_10609_) {
      this.f_10599_.add(p_10609_);
   }

   public Set<String> m_7187_() {
      return ImmutableSet.of(this.f_10602_);
   }

   public Resource m_142591_(ResourceLocation p_10614_) throws IOException {
      this.m_10626_(p_10614_);
      PackResources packresources = null;
      ResourceLocation resourcelocation = m_10624_(p_10614_);

      for(int i = this.f_10599_.size() - 1; i >= 0; --i) {
         PackResources packresources1 = this.f_10599_.get(i);
         if (packresources == null && packresources1.m_7211_(this.f_10601_, resourcelocation)) {
            packresources = packresources1;
         }

         if (packresources1.m_7211_(this.f_10601_, p_10614_)) {
            InputStream inputstream = null;
            if (packresources != null) {
               inputstream = this.m_10615_(resourcelocation, packresources);
            }

            return new SimpleResource(packresources1.m_8017_(), p_10614_, this.m_10615_(p_10614_, packresources1), inputstream);
         }
      }

      throw new FileNotFoundException(p_10614_.toString());
   }

   public boolean m_7165_(ResourceLocation p_10620_) {
      if (!this.m_10628_(p_10620_)) {
         return false;
      } else {
         for(int i = this.f_10599_.size() - 1; i >= 0; --i) {
            PackResources packresources = this.f_10599_.get(i);
            if (packresources.m_7211_(this.f_10601_, p_10620_)) {
               return true;
            }
         }

         return false;
      }
   }

   protected InputStream m_10615_(ResourceLocation p_10616_, PackResources p_10617_) throws IOException {
      InputStream inputstream = p_10617_.m_8031_(this.f_10601_, p_10616_);
      return (InputStream)(f_10600_.isDebugEnabled() ? new FallbackResourceManager.LeakedResourceWarningInputStream(inputstream, p_10616_, p_10617_.m_8017_()) : inputstream);
   }

   private void m_10626_(ResourceLocation p_10627_) throws IOException {
      if (!this.m_10628_(p_10627_)) {
         throw new IOException("Invalid relative path to resource: " + p_10627_);
      }
   }

   private boolean m_10628_(ResourceLocation p_10629_) {
      return !p_10629_.m_135815_().contains("..");
   }

   public List<Resource> m_7396_(ResourceLocation p_10623_) throws IOException {
      this.m_10626_(p_10623_);
      List<Resource> list = Lists.newArrayList();
      ResourceLocation resourcelocation = m_10624_(p_10623_);

      for(PackResources packresources : this.f_10599_) {
         if (packresources.m_7211_(this.f_10601_, p_10623_)) {
            InputStream inputstream = packresources.m_7211_(this.f_10601_, resourcelocation) ? this.m_10615_(resourcelocation, packresources) : null;
            list.add(new SimpleResource(packresources.m_8017_(), p_10623_, this.m_10615_(p_10623_, packresources), inputstream));
         }
      }

      if (list.isEmpty()) {
         throw new FileNotFoundException(p_10623_.toString());
      } else {
         return list;
      }
   }

   public Collection<ResourceLocation> m_6540_(String p_10611_, Predicate<String> p_10612_) {
      List<ResourceLocation> list = Lists.newArrayList();

      for(PackResources packresources : this.f_10599_) {
         list.addAll(packresources.m_7466_(this.f_10601_, this.f_10602_, p_10611_, Integer.MAX_VALUE, p_10612_));
      }

      Collections.sort(list);
      return list;
   }

   public Stream<PackResources> m_7536_() {
      return this.f_10599_.stream();
   }

   static ResourceLocation m_10624_(ResourceLocation p_10625_) {
      return new ResourceLocation(p_10625_.m_135827_(), p_10625_.m_135815_() + ".mcmeta");
   }

   static class LeakedResourceWarningInputStream extends FilterInputStream {
      private final String f_10630_;
      private boolean f_10631_;

      public LeakedResourceWarningInputStream(InputStream p_10633_, ResourceLocation p_10634_, String p_10635_) {
         super(p_10633_);
         ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
         (new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
         this.f_10630_ = "Leaked resource: '" + p_10634_ + "' loaded from pack: '" + p_10635_ + "'\n" + bytearrayoutputstream;
      }

      public void close() throws IOException {
         super.close();
         this.f_10631_ = true;
      }

      protected void finalize() throws Throwable {
         if (!this.f_10631_) {
            FallbackResourceManager.f_10600_.warn(this.f_10630_);
         }

         super.finalize();
      }
   }
}