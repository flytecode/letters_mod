package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoalSelectorDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final int f_173887_ = 160;
   private final Minecraft f_113543_;
   private final Map<Integer, List<GoalSelectorDebugRenderer.DebugGoal>> f_113544_ = Maps.newHashMap();

   public void m_5630_() {
      this.f_113544_.clear();
   }

   public void m_113548_(int p_113549_, List<GoalSelectorDebugRenderer.DebugGoal> p_113550_) {
      this.f_113544_.put(p_113549_, p_113550_);
   }

   public void m_173888_(int p_173889_) {
      this.f_113544_.remove(p_173889_);
   }

   public GoalSelectorDebugRenderer(Minecraft p_113546_) {
      this.f_113543_ = p_113546_;
   }

   public void m_7790_(PoseStack p_113552_, MultiBufferSource p_113553_, double p_113554_, double p_113555_, double p_113556_) {
      Camera camera = this.f_113543_.f_91063_.m_109153_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69472_();
      BlockPos blockpos = new BlockPos(camera.m_90583_().f_82479_, 0.0D, camera.m_90583_().f_82481_);
      this.f_113544_.forEach((p_113559_, p_113560_) -> {
         for(int i = 0; i < p_113560_.size(); ++i) {
            GoalSelectorDebugRenderer.DebugGoal goalselectordebugrenderer$debuggoal = p_113560_.get(i);
            if (blockpos.m_123314_(goalselectordebugrenderer$debuggoal.f_113561_, 160.0D)) {
               double d0 = (double)goalselectordebugrenderer$debuggoal.f_113561_.m_123341_() + 0.5D;
               double d1 = (double)goalselectordebugrenderer$debuggoal.f_113561_.m_123342_() + 2.0D + (double)i * 0.25D;
               double d2 = (double)goalselectordebugrenderer$debuggoal.f_113561_.m_123343_() + 0.5D;
               int j = goalselectordebugrenderer$debuggoal.f_113564_ ? -16711936 : -3355444;
               DebugRenderer.m_113477_(goalselectordebugrenderer$debuggoal.f_113563_, d0, d1, d2, j);
            }
         }

      });
      RenderSystem.m_69482_();
      RenderSystem.m_69493_();
   }

   @OnlyIn(Dist.CLIENT)
   public static class DebugGoal {
      public final BlockPos f_113561_;
      public final int f_113562_;
      public final String f_113563_;
      public final boolean f_113564_;

      public DebugGoal(BlockPos p_113566_, int p_113567_, String p_113568_, boolean p_113569_) {
         this.f_113561_ = p_113566_;
         this.f_113562_ = p_113567_;
         this.f_113563_ = p_113568_;
         this.f_113564_ = p_113569_;
      }
   }
}