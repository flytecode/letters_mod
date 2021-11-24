package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CollisionBoxRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113400_;
   private double f_113401_ = Double.MIN_VALUE;
   private List<VoxelShape> f_113402_ = Collections.emptyList();

   public CollisionBoxRenderer(Minecraft p_113404_) {
      this.f_113400_ = p_113404_;
   }

   public void m_7790_(PoseStack p_113408_, MultiBufferSource p_113409_, double p_113410_, double p_113411_, double p_113412_) {
      double d0 = (double)Util.m_137569_();
      if (d0 - this.f_113401_ > 1.0E8D) {
         this.f_113401_ = d0;
         Entity entity = this.f_113400_.f_91063_.m_109153_().m_90592_();
         this.f_113402_ = entity.f_19853_.m_7786_(entity, entity.m_142469_().m_82400_(6.0D), (p_113406_) -> {
            return true;
         }).collect(Collectors.toList());
      }

      VertexConsumer vertexconsumer = p_113409_.m_6299_(RenderType.m_110504_());

      for(VoxelShape voxelshape : this.f_113402_) {
         LevelRenderer.m_109654_(p_113408_, vertexconsumer, voxelshape, -p_113410_, -p_113411_, -p_113412_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

   }
}