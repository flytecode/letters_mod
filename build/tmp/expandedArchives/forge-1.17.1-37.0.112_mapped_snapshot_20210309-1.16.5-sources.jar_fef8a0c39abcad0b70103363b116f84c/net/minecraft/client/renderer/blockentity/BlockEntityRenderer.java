package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface BlockEntityRenderer<T extends BlockEntity> {
   void m_6922_(T p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_);

   default boolean m_5932_(T p_112306_) {
      return false;
   }

   default int m_142163_() {
      return 64;
   }

   default boolean m_142756_(T p_173568_, Vec3 p_173569_) {
      return Vec3.m_82512_(p_173568_.m_58899_()).m_82509_(p_173569_, (double)this.m_142163_());
   }
}