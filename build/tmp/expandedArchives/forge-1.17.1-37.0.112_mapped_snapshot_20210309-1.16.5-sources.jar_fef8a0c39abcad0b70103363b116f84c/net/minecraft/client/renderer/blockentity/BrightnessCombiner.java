package net.minecraft.client.renderer.blockentity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BrightnessCombiner<S extends BlockEntity> implements DoubleBlockCombiner.Combiner<S, Int2IntFunction> {
   public Int2IntFunction m_6959_(S p_112320_, S p_112321_) {
      return (p_112325_) -> {
         int i = LevelRenderer.m_109541_(p_112320_.m_58904_(), p_112320_.m_58899_());
         int j = LevelRenderer.m_109541_(p_112321_.m_58904_(), p_112321_.m_58899_());
         int k = LightTexture.m_109883_(i);
         int l = LightTexture.m_109883_(j);
         int i1 = LightTexture.m_109894_(i);
         int j1 = LightTexture.m_109894_(j);
         return LightTexture.m_109885_(Math.max(k, l), Math.max(i1, j1));
      };
   }

   public Int2IntFunction m_7693_(S p_112318_) {
      return (p_112333_) -> {
         return p_112333_;
      };
   }

   public Int2IntFunction m_6502_() {
      return (p_112316_) -> {
         return p_112316_;
      };
   }
}