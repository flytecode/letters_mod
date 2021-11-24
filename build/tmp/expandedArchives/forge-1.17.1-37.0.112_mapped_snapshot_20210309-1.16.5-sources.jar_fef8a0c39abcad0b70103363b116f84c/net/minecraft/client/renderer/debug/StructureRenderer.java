package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StructureRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113675_;
   private final Map<DimensionType, Map<String, BoundingBox>> f_113676_ = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, BoundingBox>> f_113677_ = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, Boolean>> f_113678_ = Maps.newIdentityHashMap();
   private static final int f_173903_ = 500;

   public StructureRenderer(Minecraft p_113680_) {
      this.f_113675_ = p_113680_;
   }

   public void m_7790_(PoseStack p_113688_, MultiBufferSource p_113689_, double p_113690_, double p_113691_, double p_113692_) {
      Camera camera = this.f_113675_.f_91063_.m_109153_();
      LevelAccessor levelaccessor = this.f_113675_.f_91073_;
      DimensionType dimensiontype = levelaccessor.m_6042_();
      BlockPos blockpos = new BlockPos(camera.m_90583_().f_82479_, 0.0D, camera.m_90583_().f_82481_);
      VertexConsumer vertexconsumer = p_113689_.m_6299_(RenderType.m_110504_());
      if (this.f_113676_.containsKey(dimensiontype)) {
         for(BoundingBox boundingbox : this.f_113676_.get(dimensiontype).values()) {
            if (blockpos.m_123314_(boundingbox.m_162394_(), 500.0D)) {
               LevelRenderer.m_109621_(p_113688_, vertexconsumer, (double)boundingbox.m_162395_() - p_113690_, (double)boundingbox.m_162396_() - p_113691_, (double)boundingbox.m_162398_() - p_113692_, (double)(boundingbox.m_162399_() + 1) - p_113690_, (double)(boundingbox.m_162400_() + 1) - p_113691_, (double)(boundingbox.m_162401_() + 1) - p_113692_, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      }

      if (this.f_113677_.containsKey(dimensiontype)) {
         for(Entry<String, BoundingBox> entry : this.f_113677_.get(dimensiontype).entrySet()) {
            String s = entry.getKey();
            BoundingBox boundingbox1 = entry.getValue();
            Boolean obool = this.f_113678_.get(dimensiontype).get(s);
            if (blockpos.m_123314_(boundingbox1.m_162394_(), 500.0D)) {
               if (obool) {
                  LevelRenderer.m_109621_(p_113688_, vertexconsumer, (double)boundingbox1.m_162395_() - p_113690_, (double)boundingbox1.m_162396_() - p_113691_, (double)boundingbox1.m_162398_() - p_113692_, (double)(boundingbox1.m_162399_() + 1) - p_113690_, (double)(boundingbox1.m_162400_() + 1) - p_113691_, (double)(boundingbox1.m_162401_() + 1) - p_113692_, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F);
               } else {
                  LevelRenderer.m_109621_(p_113688_, vertexconsumer, (double)boundingbox1.m_162395_() - p_113690_, (double)boundingbox1.m_162396_() - p_113691_, (double)boundingbox1.m_162398_() - p_113692_, (double)(boundingbox1.m_162399_() + 1) - p_113690_, (double)(boundingbox1.m_162400_() + 1) - p_113691_, (double)(boundingbox1.m_162401_() + 1) - p_113692_, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
               }
            }
         }
      }

   }

   public void m_113682_(BoundingBox p_113683_, List<BoundingBox> p_113684_, List<Boolean> p_113685_, DimensionType p_113686_) {
      if (!this.f_113676_.containsKey(p_113686_)) {
         this.f_113676_.put(p_113686_, Maps.newHashMap());
      }

      if (!this.f_113677_.containsKey(p_113686_)) {
         this.f_113677_.put(p_113686_, Maps.newHashMap());
         this.f_113678_.put(p_113686_, Maps.newHashMap());
      }

      this.f_113676_.get(p_113686_).put(p_113683_.toString(), p_113683_);

      for(int i = 0; i < p_113684_.size(); ++i) {
         BoundingBox boundingbox = p_113684_.get(i);
         Boolean obool = p_113685_.get(i);
         this.f_113677_.get(p_113686_).put(boundingbox.toString(), boundingbox);
         this.f_113678_.get(p_113686_).put(boundingbox.toString(), obool);
      }

   }

   public void m_5630_() {
      this.f_113676_.clear();
      this.f_113677_.clear();
      this.f_113678_.clear();
   }
}