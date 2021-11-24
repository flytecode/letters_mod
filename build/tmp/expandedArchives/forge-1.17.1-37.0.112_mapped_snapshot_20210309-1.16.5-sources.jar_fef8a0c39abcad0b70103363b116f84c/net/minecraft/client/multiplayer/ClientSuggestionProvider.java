package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientSuggestionProvider implements SharedSuggestionProvider {
   private final ClientPacketListener f_105160_;
   private final Minecraft f_105161_;
   private int f_105162_ = -1;
   private CompletableFuture<Suggestions> f_105163_;

   public ClientSuggestionProvider(ClientPacketListener p_105165_, Minecraft p_105166_) {
      this.f_105160_ = p_105165_;
      this.f_105161_ = p_105166_;
   }

   public Collection<String> m_5982_() {
      List<String> list = Lists.newArrayList();

      for(PlayerInfo playerinfo : this.f_105160_.m_105142_()) {
         list.add(playerinfo.m_105312_().getName());
      }

      return list;
   }

   public Collection<String> m_6264_() {
      return (Collection<String>)(this.f_105161_.f_91077_ != null && this.f_105161_.f_91077_.m_6662_() == HitResult.Type.ENTITY ? Collections.singleton(((EntityHitResult)this.f_105161_.f_91077_).m_82443_().m_20149_()) : Collections.emptyList());
   }

   public Collection<String> m_5983_() {
      return this.f_105160_.m_105147_().m_6188_().m_83488_();
   }

   public Collection<ResourceLocation> m_5984_() {
      return this.f_105161_.m_91106_().m_120354_();
   }

   public Stream<ResourceLocation> m_6860_() {
      return this.f_105160_.m_105141_().m_44073_();
   }

   public boolean m_6761_(int p_105178_) {
      LocalPlayer localplayer = this.f_105161_.f_91074_;
      return localplayer != null ? localplayer.m_20310_(p_105178_) : p_105178_ == 0;
   }

   public CompletableFuture<Suggestions> m_5497_(CommandContext<SharedSuggestionProvider> p_105175_, SuggestionsBuilder p_105176_) {
      if (this.f_105163_ != null) {
         this.f_105163_.cancel(false);
      }

      this.f_105163_ = new CompletableFuture<>();
      int i = ++this.f_105162_;
      this.f_105160_.m_104955_(new ServerboundCommandSuggestionPacket(i, p_105175_.getInput()));
      return this.f_105163_;
   }

   private static String m_105167_(double p_105168_) {
      return String.format(Locale.ROOT, "%.2f", p_105168_);
   }

   private static String m_105169_(int p_105170_) {
      return Integer.toString(p_105170_);
   }

   public Collection<SharedSuggestionProvider.TextCoordinates> m_6265_() {
      HitResult hitresult = this.f_105161_.f_91077_;
      if (hitresult != null && hitresult.m_6662_() == HitResult.Type.BLOCK) {
         BlockPos blockpos = ((BlockHitResult)hitresult).m_82425_();
         return Collections.singleton(new SharedSuggestionProvider.TextCoordinates(m_105169_(blockpos.m_123341_()), m_105169_(blockpos.m_123342_()), m_105169_(blockpos.m_123343_())));
      } else {
         return SharedSuggestionProvider.super.m_6265_();
      }
   }

   public Collection<SharedSuggestionProvider.TextCoordinates> m_6284_() {
      HitResult hitresult = this.f_105161_.f_91077_;
      if (hitresult != null && hitresult.m_6662_() == HitResult.Type.BLOCK) {
         Vec3 vec3 = hitresult.m_82450_();
         return Collections.singleton(new SharedSuggestionProvider.TextCoordinates(m_105167_(vec3.f_82479_), m_105167_(vec3.f_82480_), m_105167_(vec3.f_82481_)));
      } else {
         return SharedSuggestionProvider.super.m_6284_();
      }
   }

   public Set<ResourceKey<Level>> m_6553_() {
      return this.f_105160_.m_105151_();
   }

   public RegistryAccess m_5894_() {
      return this.f_105160_.m_105152_();
   }

   public void m_105171_(int p_105172_, Suggestions p_105173_) {
      if (p_105172_ == this.f_105162_) {
         this.f_105163_.complete(p_105173_);
         this.f_105163_ = null;
         this.f_105162_ = -1;
      }

   }
}