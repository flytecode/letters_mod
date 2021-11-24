package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeightMapRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113570_;
   private static final int f_173890_ = 2;
   private static final float f_173891_ = 0.09375F;

   public HeightMapRenderer(Minecraft p_113572_) {
      this.f_113570_ = p_113572_;
   }

   public void m_7790_(PoseStack p_113576_, MultiBufferSource p_113577_, double p_113578_, double p_113579_, double p_113580_) {
      LevelAccessor levelaccessor = this.f_113570_.f_91073_;
      RenderSystem.m_69461_();
      RenderSystem.m_69472_();
      RenderSystem.m_69482_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      BlockPos blockpos = new BlockPos(p_113578_, 0.0D, p_113580_);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);

      for(int i = -2; i <= 2; ++i) {
         for(int j = -2; j <= 2; ++j) {
            ChunkAccess chunkaccess = levelaccessor.m_46865_(blockpos.m_142082_(i * 16, 0, j * 16));

            for(Entry<Heightmap.Types, Heightmap> entry : chunkaccess.m_6890_()) {
               Heightmap.Types heightmap$types = entry.getKey();
               ChunkPos chunkpos = chunkaccess.m_7697_();
               Vector3f vector3f = this.m_113573_(heightmap$types);

               for(int k = 0; k < 16; ++k) {
                  for(int l = 0; l < 16; ++l) {
                     int i1 = SectionPos.m_175554_(chunkpos.f_45578_, k);
                     int j1 = SectionPos.m_175554_(chunkpos.f_45579_, l);
                     float f = (float)((double)((float)levelaccessor.m_6924_(heightmap$types, i1, j1) + (float)heightmap$types.ordinal() * 0.09375F) - p_113579_);
                     LevelRenderer.m_109556_(bufferbuilder, (double)((float)i1 + 0.25F) - p_113578_, (double)f, (double)((float)j1 + 0.25F) - p_113580_, (double)((float)i1 + 0.75F) - p_113578_, (double)(f + 0.09375F), (double)((float)j1 + 0.75F) - p_113580_, vector3f.m_122239_(), vector3f.m_122260_(), vector3f.m_122269_(), 1.0F);
                  }
               }
            }
         }
      }

      tesselator.m_85914_();
      RenderSystem.m_69493_();
   }

   private Vector3f m_113573_(Heightmap.Types p_113574_) {
      switch(p_113574_) {
      case WORLD_SURFACE_WG:
         return new Vector3f(1.0F, 1.0F, 0.0F);
      case OCEAN_FLOOR_WG:
         return new Vector3f(1.0F, 0.0F, 1.0F);
      case WORLD_SURFACE:
         return new Vector3f(0.0F, 0.7F, 0.0F);
      case OCEAN_FLOOR:
         return new Vector3f(0.0F, 0.0F, 0.5F);
      case MOTION_BLOCKING:
         return new Vector3f(0.0F, 0.3F, 0.3F);
      case MOTION_BLOCKING_NO_LEAVES:
         return new Vector3f(0.0F, 0.5F, 0.5F);
      default:
         return new Vector3f(0.0F, 0.0F, 0.0F);
      }
   }
}