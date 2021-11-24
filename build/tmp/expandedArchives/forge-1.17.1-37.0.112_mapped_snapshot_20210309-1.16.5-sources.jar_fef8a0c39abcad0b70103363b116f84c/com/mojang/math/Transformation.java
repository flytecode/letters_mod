package com.mojang.math;

import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.Util;
import org.apache.commons.lang3.tuple.Triple;

public final class Transformation implements net.minecraftforge.client.extensions.IForgeTransformation {
   private final Matrix4f f_121078_;
   private boolean f_121079_;
   @Nullable
   private Vector3f f_121080_;
   @Nullable
   private Quaternion f_121081_;
   @Nullable
   private Vector3f f_121082_;
   @Nullable
   private Quaternion f_121083_;
   private static final Transformation f_121084_ = Util.m_137537_(() -> {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.m_27624_();
      Transformation transformation = new Transformation(matrix4f);
      transformation.m_121105_();
      return transformation;
   });

   public Transformation(@Nullable Matrix4f p_121087_) {
      if (p_121087_ == null) {
         this.f_121078_ = f_121084_.f_121078_;
      } else {
         this.f_121078_ = p_121087_;
      }

   }

   public Transformation(@Nullable Vector3f p_121089_, @Nullable Quaternion p_121090_, @Nullable Vector3f p_121091_, @Nullable Quaternion p_121092_) {
      this.f_121078_ = m_121098_(p_121089_, p_121090_, p_121091_, p_121092_);
      this.f_121080_ = p_121089_ != null ? p_121089_ : new Vector3f();
      this.f_121081_ = p_121090_ != null ? p_121090_ : Quaternion.f_80118_.m_80161_();
      this.f_121082_ = p_121091_ != null ? p_121091_ : new Vector3f(1.0F, 1.0F, 1.0F);
      this.f_121083_ = p_121092_ != null ? p_121092_ : Quaternion.f_80118_.m_80161_();
      this.f_121079_ = true;
   }

   public static Transformation m_121093_() {
      return f_121084_;
   }

   public Transformation m_121096_(Transformation p_121097_) {
      Matrix4f matrix4f = this.m_121104_();
      matrix4f.m_27644_(p_121097_.m_121104_());
      return new Transformation(matrix4f);
   }

   @Nullable
   public Transformation m_121103_() {
      if (this == f_121084_) {
         return this;
      } else {
         Matrix4f matrix4f = this.m_121104_();
         return matrix4f.m_27657_() ? new Transformation(matrix4f) : null;
      }
   }

   private void m_121106_() {
      if (!this.f_121079_) {
         Pair<Matrix3f, Vector3f> pair = m_121094_(this.f_121078_);
         Triple<Quaternion, Vector3f, Quaternion> triple = pair.getFirst().m_8173_();
         this.f_121080_ = pair.getSecond();
         this.f_121081_ = triple.getLeft();
         this.f_121082_ = triple.getMiddle();
         this.f_121083_ = triple.getRight();
         this.f_121079_ = true;
      }

   }

   private static Matrix4f m_121098_(@Nullable Vector3f p_121099_, @Nullable Quaternion p_121100_, @Nullable Vector3f p_121101_, @Nullable Quaternion p_121102_) {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.m_27624_();
      if (p_121100_ != null) {
         matrix4f.m_27644_(new Matrix4f(p_121100_));
      }

      if (p_121101_ != null) {
         matrix4f.m_27644_(Matrix4f.m_27632_(p_121101_.m_122239_(), p_121101_.m_122260_(), p_121101_.m_122269_()));
      }

      if (p_121102_ != null) {
         matrix4f.m_27644_(new Matrix4f(p_121102_));
      }

      if (p_121099_ != null) {
         matrix4f.f_27606_ = p_121099_.m_122239_();
         matrix4f.f_27610_ = p_121099_.m_122260_();
         matrix4f.f_27614_ = p_121099_.m_122269_();
      }

      return matrix4f;
   }

   public static Pair<Matrix3f, Vector3f> m_121094_(Matrix4f p_121095_) {
      p_121095_.m_27630_(1.0F / p_121095_.f_27618_);
      Vector3f vector3f = new Vector3f(p_121095_.f_27606_, p_121095_.f_27610_, p_121095_.f_27614_);
      Matrix3f matrix3f = new Matrix3f(p_121095_);
      return Pair.of(matrix3f, vector3f);
   }

   public Matrix4f m_121104_() {
      return this.f_121078_.m_27658_();
   }

   public Vector3f m_175940_() {
      this.m_121106_();
      return this.f_121080_.m_122281_();
   }

   public Quaternion m_121105_() {
      this.m_121106_();
      return this.f_121081_.m_80161_();
   }

   public Vector3f m_175941_() {
      this.m_121106_();
      return this.f_121082_.m_122281_();
   }

   public Quaternion m_175942_() {
      this.m_121106_();
      return this.f_121083_.m_80161_();
   }

   public boolean equals(Object p_121108_) {
      if (this == p_121108_) {
         return true;
      } else if (p_121108_ != null && this.getClass() == p_121108_.getClass()) {
         Transformation transformation = (Transformation)p_121108_;
         return Objects.equals(this.f_121078_, transformation.f_121078_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_121078_);
   }

    private Matrix3f normalTransform = null;
    public Matrix3f getNormalMatrix() {
        checkNormalTransform();
        return normalTransform;
    }
    private void checkNormalTransform() {
        if (normalTransform == null) {
            normalTransform = new Matrix3f(f_121078_);
            normalTransform.m_8187_();
            normalTransform.m_8155_();
        }
    }

   public Transformation m_175937_(Transformation p_175938_, float p_175939_) {
      Vector3f vector3f = this.m_175940_();
      Quaternion quaternion = this.m_121105_();
      Vector3f vector3f1 = this.m_175941_();
      Quaternion quaternion1 = this.m_175942_();
      vector3f.m_122255_(p_175938_.m_175940_(), p_175939_);
      quaternion.m_175222_(p_175938_.m_121105_(), p_175939_);
      vector3f1.m_122255_(p_175938_.m_175941_(), p_175939_);
      quaternion1.m_175222_(p_175938_.m_175942_(), p_175939_);
      return new Transformation(vector3f, quaternion, vector3f1, quaternion1);
   }
}
