package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NeighborsUpdateRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113592_;
   private final Map<Long, Map<BlockPos, Integer>> f_113593_ = Maps.newTreeMap(Ordering.natural().reverse());

   NeighborsUpdateRenderer(Minecraft p_113595_) {
      this.f_113592_ = p_113595_;
   }

   public void m_113596_(long p_113597_, BlockPos p_113598_) {
      Map<BlockPos, Integer> map = this.f_113593_.computeIfAbsent(p_113597_, (p_113606_) -> {
         return Maps.newHashMap();
      });
      int i = map.getOrDefault(p_113598_, 0);
      map.put(p_113598_, i + 1);
   }

   public void m_7790_(PoseStack p_113600_, MultiBufferSource p_113601_, double p_113602_, double p_113603_, double p_113604_) {
      long i = this.f_113592_.f_91073_.m_46467_();
      int j = 200;
      double d0 = 0.0025D;
      Set<BlockPos> set = Sets.newHashSet();
      Map<BlockPos, Integer> map = Maps.newHashMap();
      VertexConsumer vertexconsumer = p_113601_.m_6299_(RenderType.m_110504_());
      Iterator<Entry<Long, Map<BlockPos, Integer>>> iterator = this.f_113593_.entrySet().iterator();

      while(iterator.hasNext()) {
         Entry<Long, Map<BlockPos, Integer>> entry = iterator.next();
         Long olong = entry.getKey();
         Map<BlockPos, Integer> map1 = entry.getValue();
         long k = i - olong;
         if (k > 200L) {
            iterator.remove();
         } else {
            for(Entry<BlockPos, Integer> entry1 : map1.entrySet()) {
               BlockPos blockpos = entry1.getKey();
               Integer integer = entry1.getValue();
               if (set.add(blockpos)) {
                  AABB aabb = (new AABB(BlockPos.f_121853_)).m_82400_(0.002D).m_82406_(0.0025D * (double)k).m_82386_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_()).m_82386_(-p_113602_, -p_113603_, -p_113604_);
                  LevelRenderer.m_109608_(p_113600_, vertexconsumer, aabb.f_82288_, aabb.f_82289_, aabb.f_82290_, aabb.f_82291_, aabb.f_82292_, aabb.f_82293_, 1.0F, 1.0F, 1.0F, 1.0F);
                  map.put(blockpos, integer);
               }
            }
         }
      }

      for(Entry<BlockPos, Integer> entry2 : map.entrySet()) {
         BlockPos blockpos1 = entry2.getKey();
         Integer integer1 = entry2.getValue();
         DebugRenderer.m_113500_(String.valueOf((Object)integer1), blockpos1.m_123341_(), blockpos1.m_123342_(), blockpos1.m_123343_(), -1);
      }

   }
}