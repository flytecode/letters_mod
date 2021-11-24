package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Random;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class StuckInBodyLayer<T extends LivingEntity, M extends PlayerModel<T>> extends RenderLayer<T, M> {
   public StuckInBodyLayer(LivingEntityRenderer<T, M> p_117564_) {
      super(p_117564_);
   }

   protected abstract int m_7040_(T p_117565_);

   protected abstract void m_5558_(PoseStack p_117566_, MultiBufferSource p_117567_, int p_117568_, Entity p_117569_, float p_117570_, float p_117571_, float p_117572_, float p_117573_);

   public void m_6494_(PoseStack p_117586_, MultiBufferSource p_117587_, int p_117588_, T p_117589_, float p_117590_, float p_117591_, float p_117592_, float p_117593_, float p_117594_, float p_117595_) {
      int i = this.m_7040_(p_117589_);
      Random random = new Random((long)p_117589_.m_142049_());
      if (i > 0) {
         for(int j = 0; j < i; ++j) {
            p_117586_.m_85836_();
            ModelPart modelpart = this.m_117386_().m_103406_(random);
            ModelPart.Cube modelpart$cube = modelpart.m_104328_(random);
            modelpart.m_104299_(p_117586_);
            float f = random.nextFloat();
            float f1 = random.nextFloat();
            float f2 = random.nextFloat();
            float f3 = Mth.m_14179_(f, modelpart$cube.f_104335_, modelpart$cube.f_104338_) / 16.0F;
            float f4 = Mth.m_14179_(f1, modelpart$cube.f_104336_, modelpart$cube.f_104339_) / 16.0F;
            float f5 = Mth.m_14179_(f2, modelpart$cube.f_104337_, modelpart$cube.f_104340_) / 16.0F;
            p_117586_.m_85837_((double)f3, (double)f4, (double)f5);
            f = -1.0F * (f * 2.0F - 1.0F);
            f1 = -1.0F * (f1 * 2.0F - 1.0F);
            f2 = -1.0F * (f2 * 2.0F - 1.0F);
            this.m_5558_(p_117586_, p_117587_, p_117588_, p_117589_, f, f1, f2, p_117592_);
            p_117586_.m_85849_();
         }

      }
   }
}