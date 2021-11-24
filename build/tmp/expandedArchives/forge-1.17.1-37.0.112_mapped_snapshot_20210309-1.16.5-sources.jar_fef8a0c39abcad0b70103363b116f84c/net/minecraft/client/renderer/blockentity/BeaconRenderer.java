package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeaconRenderer implements BlockEntityRenderer<BeaconBlockEntity> {
   public static final ResourceLocation f_112102_ = new ResourceLocation("textures/entity/beacon_beam.png");
   public static final int f_173527_ = 1024;

   public BeaconRenderer(BlockEntityRendererProvider.Context p_173529_) {
   }

   public void m_6922_(BeaconBlockEntity p_112140_, float p_112141_, PoseStack p_112142_, MultiBufferSource p_112143_, int p_112144_, int p_112145_) {
      long i = p_112140_.m_58904_().m_46467_();
      List<BeaconBlockEntity.BeaconBeamSection> list = p_112140_.m_58702_();
      int j = 0;

      for(int k = 0; k < list.size(); ++k) {
         BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
         m_112176_(p_112142_, p_112143_, p_112141_, i, j, k == list.size() - 1 ? 1024 : beaconblockentity$beaconbeamsection.m_58723_(), beaconblockentity$beaconbeamsection.m_58722_());
         j += beaconblockentity$beaconbeamsection.m_58723_();
      }

   }

   private static void m_112176_(PoseStack p_112177_, MultiBufferSource p_112178_, float p_112179_, long p_112180_, int p_112181_, int p_112182_, float[] p_112183_) {
      m_112184_(p_112177_, p_112178_, f_112102_, p_112179_, 1.0F, p_112180_, p_112181_, p_112182_, p_112183_, 0.2F, 0.25F);
   }

   public static void m_112184_(PoseStack p_112185_, MultiBufferSource p_112186_, ResourceLocation p_112187_, float p_112188_, float p_112189_, long p_112190_, int p_112191_, int p_112192_, float[] p_112193_, float p_112194_, float p_112195_) {
      int i = p_112191_ + p_112192_;
      p_112185_.m_85836_();
      p_112185_.m_85837_(0.5D, 0.0D, 0.5D);
      float f = (float)Math.floorMod(p_112190_, 40) + p_112188_;
      float f1 = p_112192_ < 0 ? f : -f;
      float f2 = Mth.m_14187_(f1 * 0.2F - (float)Mth.m_14143_(f1 * 0.1F));
      float f3 = p_112193_[0];
      float f4 = p_112193_[1];
      float f5 = p_112193_[2];
      p_112185_.m_85836_();
      p_112185_.m_85845_(Vector3f.f_122225_.m_122240_(f * 2.25F - 45.0F));
      float f6 = 0.0F;
      float f8 = 0.0F;
      float f9 = -p_112194_;
      float f10 = 0.0F;
      float f11 = 0.0F;
      float f12 = -p_112194_;
      float f13 = 0.0F;
      float f14 = 1.0F;
      float f15 = -1.0F + f2;
      float f16 = (float)p_112192_ * p_112189_ * (0.5F / p_112194_) + f15;
      m_112155_(p_112185_, p_112186_.m_6299_(RenderType.m_110460_(p_112187_, false)), f3, f4, f5, 1.0F, p_112191_, i, 0.0F, p_112194_, p_112194_, 0.0F, f9, 0.0F, 0.0F, f12, 0.0F, 1.0F, f16, f15);
      p_112185_.m_85849_();
      f6 = -p_112195_;
      float f7 = -p_112195_;
      f8 = -p_112195_;
      f9 = -p_112195_;
      f13 = 0.0F;
      f14 = 1.0F;
      f15 = -1.0F + f2;
      f16 = (float)p_112192_ * p_112189_ + f15;
      m_112155_(p_112185_, p_112186_.m_6299_(RenderType.m_110460_(p_112187_, true)), f3, f4, f5, 0.125F, p_112191_, i, f6, f7, p_112195_, f8, f9, p_112195_, p_112195_, p_112195_, 0.0F, 1.0F, f16, f15);
      p_112185_.m_85849_();
   }

   private static void m_112155_(PoseStack p_112156_, VertexConsumer p_112157_, float p_112158_, float p_112159_, float p_112160_, float p_112161_, int p_112162_, int p_112163_, float p_112164_, float p_112165_, float p_112166_, float p_112167_, float p_112168_, float p_112169_, float p_112170_, float p_112171_, float p_112172_, float p_112173_, float p_112174_, float p_112175_) {
      PoseStack.Pose posestack$pose = p_112156_.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      m_112119_(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112164_, p_112165_, p_112166_, p_112167_, p_112172_, p_112173_, p_112174_, p_112175_);
      m_112119_(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112170_, p_112171_, p_112168_, p_112169_, p_112172_, p_112173_, p_112174_, p_112175_);
      m_112119_(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112166_, p_112167_, p_112170_, p_112171_, p_112172_, p_112173_, p_112174_, p_112175_);
      m_112119_(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112168_, p_112169_, p_112164_, p_112165_, p_112172_, p_112173_, p_112174_, p_112175_);
   }

   private static void m_112119_(Matrix4f p_112120_, Matrix3f p_112121_, VertexConsumer p_112122_, float p_112123_, float p_112124_, float p_112125_, float p_112126_, int p_112127_, int p_112128_, float p_112129_, float p_112130_, float p_112131_, float p_112132_, float p_112133_, float p_112134_, float p_112135_, float p_112136_) {
      m_112106_(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112128_, p_112129_, p_112130_, p_112134_, p_112135_);
      m_112106_(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112127_, p_112129_, p_112130_, p_112134_, p_112136_);
      m_112106_(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112127_, p_112131_, p_112132_, p_112133_, p_112136_);
      m_112106_(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112128_, p_112131_, p_112132_, p_112133_, p_112135_);
   }

   private static void m_112106_(Matrix4f p_112107_, Matrix3f p_112108_, VertexConsumer p_112109_, float p_112110_, float p_112111_, float p_112112_, float p_112113_, int p_112114_, float p_112115_, float p_112116_, float p_112117_, float p_112118_) {
      p_112109_.m_85982_(p_112107_, p_112115_, (float)p_112114_, p_112116_).m_85950_(p_112110_, p_112111_, p_112112_, p_112113_).m_7421_(p_112117_, p_112118_).m_86008_(OverlayTexture.f_118083_).m_85969_(15728880).m_85977_(p_112108_, 0.0F, 1.0F, 0.0F).m_5752_();
   }

   public boolean m_5932_(BeaconBlockEntity p_112138_) {
      return true;
   }

   public int m_142163_() {
      return 256;
   }

   public boolean m_142756_(BeaconBlockEntity p_173531_, Vec3 p_173532_) {
      return Vec3.m_82512_(p_173531_.m_58899_()).m_82542_(1.0D, 0.0D, 1.0D).m_82509_(p_173532_.m_82542_(1.0D, 0.0D, 1.0D), (double)this.m_142163_());
   }
}