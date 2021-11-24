package net.minecraft.client.renderer.debug;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChunkDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   final Minecraft f_113363_;
   private double f_113364_ = Double.MIN_VALUE;
   private final int f_113365_ = 12;
   @Nullable
   private ChunkDebugRenderer.ChunkData f_113366_;

   public ChunkDebugRenderer(Minecraft p_113368_) {
      this.f_113363_ = p_113368_;
   }

   public void m_7790_(PoseStack p_113370_, MultiBufferSource p_113371_, double p_113372_, double p_113373_, double p_113374_) {
      double d0 = (double)Util.m_137569_();
      if (d0 - this.f_113364_ > 3.0E9D) {
         this.f_113364_ = d0;
         IntegratedServer integratedserver = this.f_113363_.m_91092_();
         if (integratedserver != null) {
            this.f_113366_ = new ChunkDebugRenderer.ChunkData(integratedserver, p_113372_, p_113374_);
         } else {
            this.f_113366_ = null;
         }
      }

      if (this.f_113366_ != null) {
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         RenderSystem.m_69832_(2.0F);
         RenderSystem.m_69472_();
         RenderSystem.m_69458_(false);
         Map<ChunkPos, String> map = this.f_113366_.f_113379_.getNow((Map<ChunkPos, String>)null);
         double d1 = this.f_113363_.f_91063_.m_109153_().m_90583_().f_82480_ * 0.85D;

         for(Entry<ChunkPos, String> entry : this.f_113366_.f_113378_.entrySet()) {
            ChunkPos chunkpos = entry.getKey();
            String s = entry.getValue();
            if (map != null) {
               s = s + (String)map.get(chunkpos);
            }

            String[] astring = s.split("\n");
            int i = 0;

            for(String s1 : astring) {
               DebugRenderer.m_113483_(s1, (double)SectionPos.m_175554_(chunkpos.f_45578_, 8), d1 + (double)i, (double)SectionPos.m_175554_(chunkpos.f_45579_, 8), -1, 0.15F);
               i -= 2;
            }
         }

         RenderSystem.m_69458_(true);
         RenderSystem.m_69493_();
         RenderSystem.m_69461_();
      }

   }

   @OnlyIn(Dist.CLIENT)
   final class ChunkData {
      final Map<ChunkPos, String> f_113378_;
      final CompletableFuture<Map<ChunkPos, String>> f_113379_;

      ChunkData(IntegratedServer p_113382_, double p_113383_, double p_113384_) {
         ClientLevel clientlevel = ChunkDebugRenderer.this.f_113363_.f_91073_;
         ResourceKey<Level> resourcekey = clientlevel.m_46472_();
         int i = SectionPos.m_175552_(p_113383_);
         int j = SectionPos.m_175552_(p_113384_);
         Builder<ChunkPos, String> builder = ImmutableMap.builder();
         ClientChunkCache clientchunkcache = clientlevel.m_7726_();

         for(int k = i - 12; k <= i + 12; ++k) {
            for(int l = j - 12; l <= j + 12; ++l) {
               ChunkPos chunkpos = new ChunkPos(k, l);
               String s = "";
               LevelChunk levelchunk = clientchunkcache.m_62227_(k, l, false);
               s = s + "Client: ";
               if (levelchunk == null) {
                  s = s + "0n/a\n";
               } else {
                  s = s + (levelchunk.m_6430_() ? " E" : "");
                  s = s + "\n";
               }

               builder.put(chunkpos, s);
            }
         }

         this.f_113378_ = builder.build();
         this.f_113379_ = p_113382_.m_18691_(() -> {
            ServerLevel serverlevel = p_113382_.m_129880_(resourcekey);
            if (serverlevel == null) {
               return ImmutableMap.of();
            } else {
               Builder<ChunkPos, String> builder1 = ImmutableMap.builder();
               ServerChunkCache serverchunkcache = serverlevel.m_7726_();

               for(int i1 = i - 12; i1 <= i + 12; ++i1) {
                  for(int j1 = j - 12; j1 <= j + 12; ++j1) {
                     ChunkPos chunkpos1 = new ChunkPos(i1, j1);
                     builder1.put(chunkpos1, "Server: " + serverchunkcache.m_8448_(chunkpos1));
                  }
               }

               return builder1.build();
            }
         });
      }
   }
}