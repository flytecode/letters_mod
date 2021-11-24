package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.tags.TagLoader;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerFunctionLibrary implements PreparableReloadListener {
   private static final Logger f_136043_ = LogManager.getLogger();
   private static final String f_179927_ = ".mcfunction";
   private static final int f_136044_ = "functions/".length();
   private static final int f_136045_ = ".mcfunction".length();
   private volatile Map<ResourceLocation, CommandFunction> f_136046_ = ImmutableMap.of();
   private final TagLoader<CommandFunction> f_136047_ = new TagLoader<>(this::m_136089_, "tags/functions");
   private volatile TagCollection<CommandFunction> f_136048_ = TagCollection.m_13410_();
   private final int f_136049_;
   private final CommandDispatcher<CommandSourceStack> f_136050_;

   public Optional<CommandFunction> m_136089_(ResourceLocation p_136090_) {
      return Optional.ofNullable(this.f_136046_.get(p_136090_));
   }

   public Map<ResourceLocation, CommandFunction> m_136055_() {
      return this.f_136046_;
   }

   public TagCollection<CommandFunction> m_136096_() {
      return this.f_136048_;
   }

   public Tag<CommandFunction> m_136097_(ResourceLocation p_136098_) {
      return this.f_136048_.m_7689_(p_136098_);
   }

   public ServerFunctionLibrary(int p_136053_, CommandDispatcher<CommandSourceStack> p_136054_) {
      this.f_136049_ = p_136053_;
      this.f_136050_ = p_136054_;
   }

   public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_136057_, ResourceManager p_136058_, ProfilerFiller p_136059_, ProfilerFiller p_136060_, Executor p_136061_, Executor p_136062_) {
      CompletableFuture<Map<ResourceLocation, Tag.Builder>> completablefuture = CompletableFuture.supplyAsync(() -> {
         return this.f_136047_.m_144495_(p_136058_);
      }, p_136061_);
      CompletableFuture<Map<ResourceLocation, CompletableFuture<CommandFunction>>> completablefuture1 = CompletableFuture.supplyAsync(() -> {
         return p_136058_.m_6540_("functions", (p_179946_) -> {
            return p_179946_.endsWith(".mcfunction");
         });
      }, p_136061_).thenCompose((p_179933_) -> {
         Map<ResourceLocation, CompletableFuture<CommandFunction>> map = Maps.newHashMap();
         CommandSourceStack commandsourcestack = new CommandSourceStack(CommandSource.f_80164_, Vec3.f_82478_, Vec2.f_82462_, (ServerLevel)null, this.f_136049_, "", TextComponent.f_131282_, (MinecraftServer)null, (Entity)null);

         for(ResourceLocation resourcelocation : p_179933_) {
            String s = resourcelocation.m_135815_();
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.m_135827_(), s.substring(f_136044_, s.length() - f_136045_));
            map.put(resourcelocation1, CompletableFuture.supplyAsync(() -> {
               List<String> list = m_136069_(p_136058_, resourcelocation);
               return CommandFunction.m_77984_(resourcelocation1, this.f_136050_, commandsourcestack, list);
            }, p_136061_));
         }

         CompletableFuture<?>[] completablefuture2 = map.values().toArray(new CompletableFuture[0]);
         return CompletableFuture.allOf(completablefuture2).handle((p_179949_, p_179950_) -> {
            return map;
         });
      });
      return completablefuture.thenCombine(completablefuture1, Pair::of).thenCompose(p_136057_::m_6769_).thenAcceptAsync((p_179944_) -> {
         Map<ResourceLocation, CompletableFuture<CommandFunction>> map = (Map)p_179944_.getSecond();
         Builder<ResourceLocation, CommandFunction> builder = ImmutableMap.builder();
         map.forEach((p_179941_, p_179942_) -> {
            p_179942_.handle((p_179954_, p_179955_) -> {
               if (p_179955_ != null) {
                  f_136043_.error("Failed to load function {}", p_179941_, p_179955_);
               } else {
                  builder.put(p_179941_, p_179954_);
               }

               return null;
            }).join();
         });
         this.f_136046_ = builder.build();
         this.f_136048_ = this.f_136047_.m_144507_((Map)p_179944_.getFirst());
      }, p_136062_);
   }

   private static List<String> m_136069_(ResourceManager p_136070_, ResourceLocation p_136071_) {
      try {
         Resource resource = p_136070_.m_142591_(p_136071_);

         List list;
         try {
            list = IOUtils.readLines(resource.m_6679_(), StandardCharsets.UTF_8);
         } catch (Throwable throwable1) {
            if (resource != null) {
               try {
                  resource.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (resource != null) {
            resource.close();
         }

         return list;
      } catch (IOException ioexception) {
         throw new CompletionException(ioexception);
      }
   }
}