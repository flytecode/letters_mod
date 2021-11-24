package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpawnerRenderer implements BlockEntityRenderer<SpawnerBlockEntity> {
   public SpawnerRenderer(BlockEntityRendererProvider.Context p_173673_) {
   }

   public void m_6922_(SpawnerBlockEntity p_112563_, float p_112564_, PoseStack p_112565_, MultiBufferSource p_112566_, int p_112567_, int p_112568_) {
      p_112565_.m_85836_();
      p_112565_.m_85837_(0.5D, 0.0D, 0.5D);
      BaseSpawner basespawner = p_112563_.m_59801_();
      Entity entity = basespawner.m_151314_(p_112563_.m_58904_());
      if (entity != null) {
         float f = 0.53125F;
         float f1 = Math.max(entity.m_20205_(), entity.m_20206_());
         if ((double)f1 > 1.0D) {
            f /= f1;
         }

         p_112565_.m_85837_(0.0D, (double)0.4F, 0.0D);
         p_112565_.m_85845_(Vector3f.f_122225_.m_122240_((float)Mth.m_14139_((double)p_112564_, basespawner.m_45474_(), basespawner.m_45473_()) * 10.0F));
         p_112565_.m_85837_(0.0D, (double)-0.2F, 0.0D);
         p_112565_.m_85845_(Vector3f.f_122223_.m_122240_(-30.0F));
         p_112565_.m_85841_(f, f, f);
         Minecraft.m_91087_().m_91290_().m_114384_(entity, 0.0D, 0.0D, 0.0D, 0.0F, p_112564_, p_112565_, p_112566_, p_112567_);
      }

      p_112565_.m_85849_();
   }
}