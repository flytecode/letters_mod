package net.minecraft.client.renderer.debug;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillageSectionsDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final int f_173904_ = 60;
   private final Set<SectionPos> f_113693_ = Sets.newHashSet();

   VillageSectionsDebugRenderer() {
   }

   public void m_5630_() {
      this.f_113693_.clear();
   }

   public void m_113709_(SectionPos p_113710_) {
      this.f_113693_.add(p_113710_);
   }

   public void m_113711_(SectionPos p_113712_) {
      this.f_113693_.remove(p_113712_);
   }

   public void m_7790_(PoseStack p_113701_, MultiBufferSource p_113702_, double p_113703_, double p_113704_, double p_113705_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69472_();
      this.m_113696_(p_113703_, p_113704_, p_113705_);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }

   private void m_113696_(double p_113697_, double p_113698_, double p_113699_) {
      BlockPos blockpos = new BlockPos(p_113697_, p_113698_, p_113699_);
      this.f_113693_.forEach((p_113708_) -> {
         if (blockpos.m_123314_(p_113708_.m_123250_(), 60.0D)) {
            m_113713_(p_113708_);
         }

      });
   }

   private static void m_113713_(SectionPos p_113714_) {
      float f = 1.0F;
      BlockPos blockpos = p_113714_.m_123250_();
      BlockPos blockpos1 = blockpos.m_142022_(-1.0D, -1.0D, -1.0D);
      BlockPos blockpos2 = blockpos.m_142022_(1.0D, 1.0D, 1.0D);
      DebugRenderer.m_113470_(blockpos1, blockpos2, 0.2F, 1.0F, 0.2F, 0.15F);
   }
}