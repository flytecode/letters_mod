package net.minecraft.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
import net.minecraft.tags.TagContainer;
import net.minecraft.tags.TagManager;
import net.minecraft.util.Unit;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;

public class ServerResources implements AutoCloseable {
   private static final CompletableFuture<Unit> f_136144_ = CompletableFuture.completedFuture(Unit.INSTANCE);
   private final ReloadableResourceManager f_136145_ = new SimpleReloadableResourceManager(PackType.SERVER_DATA);
   private final Commands f_136146_;
   private final RecipeManager f_136147_ = new RecipeManager();
   private final TagManager f_136148_;
   private final PredicateManager f_136149_ = new PredicateManager();
   private final LootTables f_136150_ = new LootTables(this.f_136149_);
   private final ItemModifierManager f_180000_ = new ItemModifierManager(this.f_136149_, this.f_136150_);
   private final ServerAdvancementManager f_136151_ = new ServerAdvancementManager(this.f_136149_);
   private final ServerFunctionLibrary f_136152_;

   public ServerResources(RegistryAccess p_180002_, Commands.CommandSelection p_180003_, int p_180004_) {
      this.f_136148_ = new TagManager(p_180002_);
      this.f_136146_ = new Commands(p_180003_);
      this.f_136152_ = new ServerFunctionLibrary(p_180004_, this.f_136146_.m_82094_());
      this.f_136145_.m_7217_(this.f_136148_);
      this.f_136145_.m_7217_(this.f_136149_);
      this.f_136145_.m_7217_(this.f_136147_);
      this.f_136145_.m_7217_(this.f_136150_);
      this.f_136145_.m_7217_(this.f_180000_);
      this.f_136145_.m_7217_(this.f_136152_);
      this.f_136145_.m_7217_(this.f_136151_);
      net.minecraftforge.event.ForgeEventFactory.onResourceReload(this).forEach(f_136145_::m_7217_);
   }

   public ServerFunctionLibrary m_136157_() {
      return this.f_136152_;
   }

   public PredicateManager m_136171_() {
      return this.f_136149_;
   }

   public LootTables m_136172_() {
      return this.f_136150_;
   }

   public ItemModifierManager m_180012_() {
      return this.f_180000_;
   }

   public TagContainer m_136174_() {
      return this.f_136148_.m_13480_();
   }

   public RecipeManager m_136175_() {
      return this.f_136147_;
   }

   public Commands m_136176_() {
      return this.f_136146_;
   }

   public ServerAdvancementManager m_136177_() {
      return this.f_136151_;
   }

   public ResourceManager m_136178_() {
      return this.f_136145_;
   }

   public static CompletableFuture<ServerResources> m_180005_(List<PackResources> p_180006_, RegistryAccess p_180007_, Commands.CommandSelection p_180008_, int p_180009_, Executor p_180010_, Executor p_180011_) {
      ServerResources serverresources = new ServerResources(p_180007_, p_180008_, p_180009_);
      CompletableFuture<Unit> completablefuture = serverresources.f_136145_.m_10715_(p_180010_, p_180011_, p_180006_, f_136144_);
      return completablefuture.whenComplete((p_136169_, p_136170_) -> {
         if (p_136170_ != null) {
            serverresources.close();
         }

      }).thenApply((p_136166_) -> {
         return serverresources;
      });
   }

   public void m_136179_() {
      this.f_136148_.m_13480_().m_13431_();
   }

   public void close() {
      this.f_136145_.close();
   }
}
