package net.minecraft.tags;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagManager implements PreparableReloadListener {
   private static final Logger f_144568_ = LogManager.getLogger();
   private final RegistryAccess f_144569_;
   protected java.util.Map<ResourceLocation, TagLoader<?>> customTagTypeReaders = net.minecraftforge.common.ForgeTagHandler.createCustomTagTypeReaders();
   private TagContainer f_13478_ = TagContainer.f_13420_;

   public TagManager(RegistryAccess p_144572_) {
      this.f_144569_ = p_144572_;
   }

   public TagContainer m_13480_() {
      return this.f_13478_;
   }

   public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_13482_, ResourceManager p_13483_, ProfilerFiller p_13484_, ProfilerFiller p_13485_, Executor p_13486_, Executor p_13487_) {
      List<TagManager.LoaderInfo<?>> list = Lists.newArrayList();
      StaticTags.m_144349_((p_144583_) -> {
         TagManager.LoaderInfo<?> loaderinfo = this.m_144575_(p_13483_, p_13486_, p_144583_);
         if (loaderinfo != null) {
            list.add(loaderinfo);
         }

      });
      return CompletableFuture.allOf(list.stream().map((p_144591_) -> {
         return p_144591_.f_144598_;
      }).toArray((p_144574_) -> {
         return new CompletableFuture[p_144574_];
      })).thenCompose(p_13482_::m_6769_).thenAcceptAsync((p_144594_) -> {
         TagContainer.Builder tagcontainer$builder = new TagContainer.Builder();
         list.forEach((p_144586_) -> {
            p_144586_.m_144602_(tagcontainer$builder);
         });
         TagContainer tagcontainer = tagcontainer$builder.m_144485_();
         Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> multimap = StaticTags.m_13283_(tagcontainer);
         if (!multimap.isEmpty()) {
            throw new IllegalStateException("Missing required tags: " + (String)multimap.entries().stream().map((p_144596_) -> {
               return p_144596_.getKey() + ":" + p_144596_.getValue();
            }).sorted().collect(Collectors.joining(",")));
         } else {
            tagcontainer = net.minecraftforge.common.ForgeTagHandler.reinjectOptionalTags(tagcontainer);
            SerializationTags.m_13202_(tagcontainer);
            this.f_13478_ = tagcontainer;
         }
      }, p_13487_);
   }

   @Nullable
   private <T> TagManager.LoaderInfo<T> m_144575_(ResourceManager p_144576_, Executor p_144577_, StaticTagHelper<T> p_144578_) {
      Optional<? extends Registry<T>> optional = this.f_144569_.m_6632_(p_144578_.m_144338_());
      if (optional.isPresent()) {
         Registry<T> registry = optional.get();
         TagLoader<T> tagloader = new TagLoader<>(registry::m_6612_, p_144578_.m_144339_());
         CompletableFuture<? extends TagCollection<T>> completablefuture = CompletableFuture.supplyAsync(() -> {
            return tagloader.m_144544_(p_144576_);
         }, p_144577_);
         return new TagManager.LoaderInfo<>(p_144578_, completablefuture);
      } else {
         if (net.minecraftforge.common.ForgeTagHandler.getCustomTagTypeNames().contains(p_144578_.m_144338_().m_135782_()))
            return new TagManager.LoaderInfo<>(p_144578_, CompletableFuture.supplyAsync(() -> ((TagLoader<T>) customTagTypeReaders.get(p_144578_.m_144338_().m_135782_())).m_144544_(p_144576_), p_144577_));
         f_144568_.warn("Can't find registry for {}", (Object)p_144578_.m_144338_());
         return null;
      }
   }

   static class LoaderInfo<T> {
      private final StaticTagHelper<T> f_144597_;
      final CompletableFuture<? extends TagCollection<T>> f_144598_;

      LoaderInfo(StaticTagHelper<T> p_144600_, CompletableFuture<? extends TagCollection<T>> p_144601_) {
         this.f_144597_ = p_144600_;
         this.f_144598_ = p_144601_;
      }

      public void m_144602_(TagContainer.Builder p_144603_) {
         p_144603_.m_144486_(this.f_144597_.m_144338_(), this.f_144598_.join());
      }
   }
}
