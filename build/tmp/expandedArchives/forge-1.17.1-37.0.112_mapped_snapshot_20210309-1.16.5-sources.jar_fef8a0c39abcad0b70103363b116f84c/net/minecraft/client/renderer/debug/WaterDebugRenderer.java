package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WaterDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113715_;

   public WaterDebugRenderer(Minecraft p_113717_) {
      this.f_113715_ = p_113717_;
   }

   public void m_7790_(PoseStack p_113719_, MultiBufferSource p_113720_, double p_113721_, double p_113722_, double p_113723_) {
      BlockPos blockpos = this.f_113715_.f_91074_.m_142538_();
      LevelReader levelreader = this.f_113715_.f_91074_.f_19853_;
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157429_(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.m_69472_();
      RenderSystem.m_69832_(6.0F);

      for(BlockPos blockpos1 : BlockPos.m_121940_(blockpos.m_142082_(-10, -10, -10), blockpos.m_142082_(10, 10, 10))) {
         FluidState fluidstate = levelreader.m_6425_(blockpos1);
         if (fluidstate.m_76153_(FluidTags.f_13131_)) {
            double d0 = (double)((float)blockpos1.m_123342_() + fluidstate.m_76155_(levelreader, blockpos1));
            DebugRenderer.m_113451_((new AABB((double)((float)blockpos1.m_123341_() + 0.01F), (double)((float)blockpos1.m_123342_() + 0.01F), (double)((float)blockpos1.m_123343_() + 0.01F), (double)((float)blockpos1.m_123341_() + 0.99F), d0, (double)((float)blockpos1.m_123343_() + 0.99F))).m_82386_(-p_113721_, -p_113722_, -p_113723_), 1.0F, 1.0F, 1.0F, 0.2F);
         }
      }

      for(BlockPos blockpos2 : BlockPos.m_121940_(blockpos.m_142082_(-10, -10, -10), blockpos.m_142082_(10, 10, 10))) {
         FluidState fluidstate1 = levelreader.m_6425_(blockpos2);
         if (fluidstate1.m_76153_(FluidTags.f_13131_)) {
            DebugRenderer.m_113477_(String.valueOf(fluidstate1.m_76186_()), (double)blockpos2.m_123341_() + 0.5D, (double)((float)blockpos2.m_123342_() + fluidstate1.m_76155_(levelreader, blockpos2)), (double)blockpos2.m_123343_() + 0.5D, -16777216);
         }
      }

      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }
}