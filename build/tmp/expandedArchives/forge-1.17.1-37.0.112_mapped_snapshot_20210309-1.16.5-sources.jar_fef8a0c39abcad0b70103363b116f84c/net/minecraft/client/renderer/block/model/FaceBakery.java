package net.minecraft.client.renderer.block.model;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockMath;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FaceBakery {
   public static final int f_173433_ = 8;
   private static final float f_111569_ = 1.0F / (float)Math.cos((double)((float)Math.PI / 8F)) - 1.0F;
   private static final float f_111570_ = 1.0F / (float)Math.cos((double)((float)Math.PI / 4F)) - 1.0F;
   public static final int f_173434_ = 4;
   private static final int f_173436_ = 3;
   public static final int f_173435_ = 4;

   public BakedQuad m_111600_(Vector3f p_111601_, Vector3f p_111602_, BlockElementFace p_111603_, TextureAtlasSprite p_111604_, Direction p_111605_, ModelState p_111606_, @Nullable BlockElementRotation p_111607_, boolean p_111608_, ResourceLocation p_111609_) {
      BlockFaceUV blockfaceuv = p_111603_.f_111357_;
      if (p_111606_.m_7538_()) {
         blockfaceuv = m_111581_(p_111603_.f_111357_, p_111605_, p_111606_.m_6189_(), p_111609_);
      }

      float[] afloat = new float[blockfaceuv.f_111387_.length];
      System.arraycopy(blockfaceuv.f_111387_, 0, afloat, 0, afloat.length);
      float f = p_111604_.m_118417_();
      float f1 = (blockfaceuv.f_111387_[0] + blockfaceuv.f_111387_[0] + blockfaceuv.f_111387_[2] + blockfaceuv.f_111387_[2]) / 4.0F;
      float f2 = (blockfaceuv.f_111387_[1] + blockfaceuv.f_111387_[1] + blockfaceuv.f_111387_[3] + blockfaceuv.f_111387_[3]) / 4.0F;
      blockfaceuv.f_111387_[0] = Mth.m_14179_(f, blockfaceuv.f_111387_[0], f1);
      blockfaceuv.f_111387_[2] = Mth.m_14179_(f, blockfaceuv.f_111387_[2], f1);
      blockfaceuv.f_111387_[1] = Mth.m_14179_(f, blockfaceuv.f_111387_[1], f2);
      blockfaceuv.f_111387_[3] = Mth.m_14179_(f, blockfaceuv.f_111387_[3], f2);
      int[] aint = this.m_111573_(blockfaceuv, p_111604_, p_111605_, this.m_111592_(p_111601_, p_111602_), p_111606_.m_6189_(), p_111607_, p_111608_);
      Direction direction = m_111612_(aint);
      System.arraycopy(afloat, 0, blockfaceuv.f_111387_, 0, afloat.length);
      if (p_111607_ == null) {
         this.m_111630_(aint, direction);
      }

      net.minecraftforge.client.ForgeHooksClient.fillNormal(aint, direction);
      return new BakedQuad(aint, p_111603_.f_111355_, direction, p_111604_, p_111608_);
   }

   public static BlockFaceUV m_111581_(BlockFaceUV p_111582_, Direction p_111583_, Transformation p_111584_, ResourceLocation p_111585_) {
      Matrix4f matrix4f = BlockMath.m_121844_(p_111584_, p_111583_, () -> {
         return "Unable to resolve UVLock for model: " + p_111585_;
      }).m_121104_();
      float f = p_111582_.m_111392_(p_111582_.m_111398_(0));
      float f1 = p_111582_.m_111396_(p_111582_.m_111398_(0));
      Vector4f vector4f = new Vector4f(f / 16.0F, f1 / 16.0F, 0.0F, 1.0F);
      vector4f.m_123607_(matrix4f);
      float f2 = 16.0F * vector4f.m_123601_();
      float f3 = 16.0F * vector4f.m_123615_();
      float f4 = p_111582_.m_111392_(p_111582_.m_111398_(2));
      float f5 = p_111582_.m_111396_(p_111582_.m_111398_(2));
      Vector4f vector4f1 = new Vector4f(f4 / 16.0F, f5 / 16.0F, 0.0F, 1.0F);
      vector4f1.m_123607_(matrix4f);
      float f6 = 16.0F * vector4f1.m_123601_();
      float f7 = 16.0F * vector4f1.m_123615_();
      float f8;
      float f9;
      if (Math.signum(f4 - f) == Math.signum(f6 - f2)) {
         f8 = f2;
         f9 = f6;
      } else {
         f8 = f6;
         f9 = f2;
      }

      float f10;
      float f11;
      if (Math.signum(f5 - f1) == Math.signum(f7 - f3)) {
         f10 = f3;
         f11 = f7;
      } else {
         f10 = f7;
         f11 = f3;
      }

      float f12 = (float)Math.toRadians((double)p_111582_.f_111388_);
      Vector3f vector3f = new Vector3f(Mth.m_14089_(f12), Mth.m_14031_(f12), 0.0F);
      Matrix3f matrix3f = new Matrix3f(matrix4f);
      vector3f.m_122249_(matrix3f);
      int i = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2((double)vector3f.m_122260_(), (double)vector3f.m_122239_())) / 90.0D)) * 90, 360);
      return new BlockFaceUV(new float[]{f8, f10, f9, f11}, i);
   }

   private int[] m_111573_(BlockFaceUV p_111574_, TextureAtlasSprite p_111575_, Direction p_111576_, float[] p_111577_, Transformation p_111578_, @Nullable BlockElementRotation p_111579_, boolean p_111580_) {
      int[] aint = new int[32];

      for(int i = 0; i < 4; ++i) {
         this.m_111620_(aint, i, p_111576_, p_111574_, p_111577_, p_111575_, p_111578_, p_111579_, p_111580_);
      }

      return aint;
   }

   private float[] m_111592_(Vector3f p_111593_, Vector3f p_111594_) {
      float[] afloat = new float[Direction.values().length];
      afloat[FaceInfo.Constants.f_108996_] = p_111593_.m_122239_() / 16.0F;
      afloat[FaceInfo.Constants.f_108995_] = p_111593_.m_122260_() / 16.0F;
      afloat[FaceInfo.Constants.f_108994_] = p_111593_.m_122269_() / 16.0F;
      afloat[FaceInfo.Constants.f_108993_] = p_111594_.m_122239_() / 16.0F;
      afloat[FaceInfo.Constants.f_108992_] = p_111594_.m_122260_() / 16.0F;
      afloat[FaceInfo.Constants.f_108991_] = p_111594_.m_122269_() / 16.0F;
      return afloat;
   }

   private void m_111620_(int[] p_111621_, int p_111622_, Direction p_111623_, BlockFaceUV p_111624_, float[] p_111625_, TextureAtlasSprite p_111626_, Transformation p_111627_, @Nullable BlockElementRotation p_111628_, boolean p_111629_) {
      FaceInfo.VertexInfo faceinfo$vertexinfo = FaceInfo.m_108984_(p_111623_).m_108982_(p_111622_);
      Vector3f vector3f = new Vector3f(p_111625_[faceinfo$vertexinfo.f_108998_], p_111625_[faceinfo$vertexinfo.f_108999_], p_111625_[faceinfo$vertexinfo.f_109000_]);
      this.m_111586_(vector3f, p_111628_);
      this.m_111589_(vector3f, p_111627_);
      this.m_111614_(p_111621_, p_111622_, vector3f, p_111626_, p_111624_);
   }

   private void m_111614_(int[] p_111615_, int p_111616_, Vector3f p_111617_, TextureAtlasSprite p_111618_, BlockFaceUV p_111619_) {
      int i = p_111616_ * 8;
      p_111615_[i] = Float.floatToRawIntBits(p_111617_.m_122239_());
      p_111615_[i + 1] = Float.floatToRawIntBits(p_111617_.m_122260_());
      p_111615_[i + 2] = Float.floatToRawIntBits(p_111617_.m_122269_());
      p_111615_[i + 3] = -1;
      p_111615_[i + 4] = Float.floatToRawIntBits(p_111618_.m_118367_((double)p_111619_.m_111392_(p_111616_) * .999 + p_111619_.m_111392_((p_111616_ + 2) % 4) * .001));
      p_111615_[i + 4 + 1] = Float.floatToRawIntBits(p_111618_.m_118393_((double)p_111619_.m_111396_(p_111616_) * .999 + p_111619_.m_111396_((p_111616_ + 2) % 4) * .001));
   }

   private void m_111586_(Vector3f p_111587_, @Nullable BlockElementRotation p_111588_) {
      if (p_111588_ != null) {
         Vector3f vector3f;
         Vector3f vector3f1;
         switch(p_111588_.f_111379_) {
         case X:
            vector3f = Vector3f.f_122223_;
            vector3f1 = new Vector3f(0.0F, 1.0F, 1.0F);
            break;
         case Y:
            vector3f = Vector3f.f_122225_;
            vector3f1 = new Vector3f(1.0F, 0.0F, 1.0F);
            break;
         case Z:
            vector3f = Vector3f.f_122227_;
            vector3f1 = new Vector3f(1.0F, 1.0F, 0.0F);
            break;
         default:
            throw new IllegalArgumentException("There are only 3 axes");
         }

         Quaternion quaternion = vector3f.m_122240_(p_111588_.f_111380_);
         if (p_111588_.f_111381_) {
            if (Math.abs(p_111588_.f_111380_) == 22.5F) {
               vector3f1.m_122261_(f_111569_);
            } else {
               vector3f1.m_122261_(f_111570_);
            }

            vector3f1.m_122272_(1.0F, 1.0F, 1.0F);
         } else {
            vector3f1.m_122245_(1.0F, 1.0F, 1.0F);
         }

         this.m_111595_(p_111587_, p_111588_.f_111378_.m_122281_(), new Matrix4f(quaternion), vector3f1);
      }
   }

   public void m_111589_(Vector3f p_111590_, Transformation p_111591_) {
      if (p_111591_ != Transformation.m_121093_()) {
         this.m_111595_(p_111590_, new Vector3f(0.5F, 0.5F, 0.5F), p_111591_.m_121104_(), new Vector3f(1.0F, 1.0F, 1.0F));
      }
   }

   private void m_111595_(Vector3f p_111596_, Vector3f p_111597_, Matrix4f p_111598_, Vector3f p_111599_) {
      Vector4f vector4f = new Vector4f(p_111596_.m_122239_() - p_111597_.m_122239_(), p_111596_.m_122260_() - p_111597_.m_122260_(), p_111596_.m_122269_() - p_111597_.m_122269_(), 1.0F);
      vector4f.m_123607_(p_111598_);
      vector4f.m_123611_(p_111599_);
      p_111596_.m_122245_(vector4f.m_123601_() + p_111597_.m_122239_(), vector4f.m_123615_() + p_111597_.m_122260_(), vector4f.m_123616_() + p_111597_.m_122269_());
   }

   public static Direction m_111612_(int[] p_111613_) {
      Vector3f vector3f = new Vector3f(Float.intBitsToFloat(p_111613_[0]), Float.intBitsToFloat(p_111613_[1]), Float.intBitsToFloat(p_111613_[2]));
      Vector3f vector3f1 = new Vector3f(Float.intBitsToFloat(p_111613_[8]), Float.intBitsToFloat(p_111613_[9]), Float.intBitsToFloat(p_111613_[10]));
      Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(p_111613_[16]), Float.intBitsToFloat(p_111613_[17]), Float.intBitsToFloat(p_111613_[18]));
      Vector3f vector3f3 = vector3f.m_122281_();
      vector3f3.m_122267_(vector3f1);
      Vector3f vector3f4 = vector3f2.m_122281_();
      vector3f4.m_122267_(vector3f1);
      Vector3f vector3f5 = vector3f4.m_122281_();
      vector3f5.m_122279_(vector3f3);
      vector3f5.m_122278_();
      Direction direction = null;
      float f = 0.0F;

      for(Direction direction1 : Direction.values()) {
         Vec3i vec3i = direction1.m_122436_();
         Vector3f vector3f6 = new Vector3f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_());
         float f1 = vector3f5.m_122276_(vector3f6);
         if (f1 >= 0.0F && f1 > f) {
            f = f1;
            direction = direction1;
         }
      }

      return direction == null ? Direction.UP : direction;
   }

   private void m_111630_(int[] p_111631_, Direction p_111632_) {
      int[] aint = new int[p_111631_.length];
      System.arraycopy(p_111631_, 0, aint, 0, p_111631_.length);
      float[] afloat = new float[Direction.values().length];
      afloat[FaceInfo.Constants.f_108996_] = 999.0F;
      afloat[FaceInfo.Constants.f_108995_] = 999.0F;
      afloat[FaceInfo.Constants.f_108994_] = 999.0F;
      afloat[FaceInfo.Constants.f_108993_] = -999.0F;
      afloat[FaceInfo.Constants.f_108992_] = -999.0F;
      afloat[FaceInfo.Constants.f_108991_] = -999.0F;

      for(int i = 0; i < 4; ++i) {
         int j = 8 * i;
         float f = Float.intBitsToFloat(aint[j]);
         float f1 = Float.intBitsToFloat(aint[j + 1]);
         float f2 = Float.intBitsToFloat(aint[j + 2]);
         if (f < afloat[FaceInfo.Constants.f_108996_]) {
            afloat[FaceInfo.Constants.f_108996_] = f;
         }

         if (f1 < afloat[FaceInfo.Constants.f_108995_]) {
            afloat[FaceInfo.Constants.f_108995_] = f1;
         }

         if (f2 < afloat[FaceInfo.Constants.f_108994_]) {
            afloat[FaceInfo.Constants.f_108994_] = f2;
         }

         if (f > afloat[FaceInfo.Constants.f_108993_]) {
            afloat[FaceInfo.Constants.f_108993_] = f;
         }

         if (f1 > afloat[FaceInfo.Constants.f_108992_]) {
            afloat[FaceInfo.Constants.f_108992_] = f1;
         }

         if (f2 > afloat[FaceInfo.Constants.f_108991_]) {
            afloat[FaceInfo.Constants.f_108991_] = f2;
         }
      }

      FaceInfo faceinfo = FaceInfo.m_108984_(p_111632_);

      for(int i1 = 0; i1 < 4; ++i1) {
         int j1 = 8 * i1;
         FaceInfo.VertexInfo faceinfo$vertexinfo = faceinfo.m_108982_(i1);
         float f8 = afloat[faceinfo$vertexinfo.f_108998_];
         float f3 = afloat[faceinfo$vertexinfo.f_108999_];
         float f4 = afloat[faceinfo$vertexinfo.f_109000_];
         p_111631_[j1] = Float.floatToRawIntBits(f8);
         p_111631_[j1 + 1] = Float.floatToRawIntBits(f3);
         p_111631_[j1 + 2] = Float.floatToRawIntBits(f4);

         for(int k = 0; k < 4; ++k) {
            int l = 8 * k;
            float f5 = Float.intBitsToFloat(aint[l]);
            float f6 = Float.intBitsToFloat(aint[l + 1]);
            float f7 = Float.intBitsToFloat(aint[l + 2]);
            if (Mth.m_14033_(f8, f5) && Mth.m_14033_(f3, f6) && Mth.m_14033_(f4, f7)) {
               p_111631_[j1 + 4] = aint[l + 4];
               p_111631_[j1 + 4 + 1] = aint[l + 4 + 1];
            }
         }
      }

   }
}
