package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.Unit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements ReloadableResourceManager {
   private static final Logger f_10869_ = LogManager.getLogger();
   private final Map<String, FallbackResourceManager> f_10870_ = Maps.newHashMap();
   private final List<PreparableReloadListener> f_10871_ = Lists.newArrayList();
   private final Set<String> f_10873_ = Sets.newLinkedHashSet();
   private final List<PackResources> f_10874_ = Lists.newArrayList();
   private final PackType f_10875_;

   public SimpleReloadableResourceManager(PackType p_10878_) {
      this.f_10875_ = p_10878_;
   }

   public void m_10880_(PackResources p_10881_) {
      this.f_10874_.add(p_10881_);

      for(String s : p_10881_.m_5698_(this.f_10875_)) {
         this.f_10873_.add(s);
         FallbackResourceManager fallbackresourcemanager = this.f_10870_.get(s);
         if (fallbackresourcemanager == null) {
            fallbackresourcemanager = new FallbackResourceManager(this.f_10875_, s);
            this.f_10870_.put(s, fallbackresourcemanager);
         }

         fallbackresourcemanager.m_10608_(p_10881_);
      }

   }

   public Set<String> m_7187_() {
      return this.f_10873_;
   }

   public Resource m_142591_(ResourceLocation p_10895_) throws IOException {
      ResourceManager resourcemanager = this.f_10870_.get(p_10895_.m_135827_());
      if (resourcemanager != null) {
         return resourcemanager.m_142591_(p_10895_);
      } else {
         throw new FileNotFoundException(p_10895_.toString());
      }
   }

   public boolean m_7165_(ResourceLocation p_10903_) {
      ResourceManager resourcemanager = this.f_10870_.get(p_10903_.m_135827_());
      return resourcemanager != null ? resourcemanager.m_7165_(p_10903_) : false;
   }

   public List<Resource> m_7396_(ResourceLocation p_10906_) throws IOException {
      ResourceManager resourcemanager = this.f_10870_.get(p_10906_.m_135827_());
      if (resourcemanager != null) {
         return resourcemanager.m_7396_(p_10906_);
      } else {
         throw new FileNotFoundException(p_10906_.toString());
      }
   }

   public Collection<ResourceLocation> m_6540_(String p_10885_, Predicate<String> p_10886_) {
      Set<ResourceLocation> set = Sets.newHashSet();

      for(FallbackResourceManager fallbackresourcemanager : this.f_10870_.values()) {
         set.addAll(fallbackresourcemanager.m_6540_(p_10885_, p_10886_));
      }

      List<ResourceLocation> list = Lists.newArrayList(set);
      Collections.sort(list);
      return list;
   }

   private void m_10904_() {
      this.f_10870_.clear();
      this.f_10873_.clear();
      this.f_10874_.forEach(PackResources::close);
      this.f_10874_.clear();
   }

   public void close() {
      this.m_10904_();
   }

   public void m_7217_(PreparableReloadListener p_10883_) {
      this.f_10871_.add(p_10883_);
   }

   public ReloadInstance m_142463_(Executor p_143947_, Executor p_143948_, CompletableFuture<Unit> p_143949_, List<PackResources> p_143950_) {
      f_10869_.info("Reloading ResourceManager: {}", () -> {
         return p_143950_.stream().map(PackResources::m_8017_).collect(Collectors.joining(", "));
      });
      this.m_10904_();

      for(PackResources packresources : p_143950_) {
         try {
            this.m_10880_(packresources);
         } catch (Exception exception) {
            f_10869_.error("Failed to add resource pack {}", packresources.m_8017_(), exception);
            return new SimpleReloadableResourceManager.FailingReloadInstance(new SimpleReloadableResourceManager.ResourcePackLoadingFailure(packresources, exception));
         }
      }

      return (ReloadInstance)(f_10869_.isDebugEnabled() ? new ProfiledReloadInstance(this, Lists.newArrayList(this.f_10871_), p_143947_, p_143948_, p_143949_) : SimpleReloadInstance.m_10815_(this, Lists.newArrayList(this.f_10871_), p_143947_, p_143948_, p_143949_));
   }

   public Stream<PackResources> m_7536_() {
      return this.f_10874_.stream();
   }

   static class FailingReloadInstance implements ReloadInstance {
      private final SimpleReloadableResourceManager.ResourcePackLoadingFailure f_10908_;
      private final CompletableFuture<Unit> f_10909_;

      public FailingReloadInstance(SimpleReloadableResourceManager.ResourcePackLoadingFailure p_10911_) {
         this.f_10908_ = p_10911_;
         this.f_10909_ = new CompletableFuture<>();
         this.f_10909_.completeExceptionally(p_10911_);
      }

      public CompletableFuture<Unit> m_7237_() {
         return this.f_10909_;
      }

      public float m_7750_() {
         return 0.0F;
      }

      public boolean m_7746_() {
         return true;
      }

      public void m_7748_() {
         throw this.f_10908_;
      }
   }

   public static class ResourcePackLoadingFailure extends RuntimeException {
      private final PackResources f_10917_;

      public ResourcePackLoadingFailure(PackResources p_10919_, Throwable p_10920_) {
         super(p_10919_.m_8017_(), p_10920_);
         this.f_10917_ = p_10919_;
      }

      public PackResources m_10921_() {
         return this.f_10917_;
      }
   }
}