package com.mojang.blaze3d.vertex;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheetedDecalTextureGenerator extends DefaultedVertexConsumer {
   private final VertexConsumer f_85867_;
   private final Matrix4f f_85868_;
   private final Matrix3f f_85869_;
   private float f_85870_;
   private float f_85871_;
   private float f_85872_;
   private int f_85873_;
   private int f_85874_;
   private int f_85875_;
   private float f_85876_;
   private float f_85877_;
   private float f_85878_;

   public SheetedDecalTextureGenerator(VertexConsumer p_85880_, Matrix4f p_85881_, Matrix3f p_85882_) {
      this.f_85867_ = p_85880_;
      this.f_85868_ = p_85881_.m_27658_();
      this.f_85868_.m_27657_();
      this.f_85869_ = p_85882_.m_8183_();
      this.f_85869_.m_8187_();
      this.m_85883_();
   }

   private void m_85883_() {
      this.f_85870_ = 0.0F;
      this.f_85871_ = 0.0F;
      this.f_85872_ = 0.0F;
      this.f_85873_ = 0;
      this.f_85874_ = 10;
      this.f_85875_ = 15728880;
      this.f_85876_ = 0.0F;
      this.f_85877_ = 1.0F;
      this.f_85878_ = 0.0F;
   }

   public void m_5752_() {
      Vector3f vector3f = new Vector3f(this.f_85876_, this.f_85877_, this.f_85878_);
      vector3f.m_122249_(this.f_85869_);
      Direction direction = Direction.m_122372_(vector3f.m_122239_(), vector3f.m_122260_(), vector3f.m_122269_());
      Vector4f vector4f = new Vector4f(this.f_85870_, this.f_85871_, this.f_85872_, 1.0F);
      vector4f.m_123607_(this.f_85868_);
      vector4f.m_123609_(Vector3f.f_122225_.m_122240_(180.0F));
      vector4f.m_123609_(Vector3f.f_122223_.m_122240_(-90.0F));
      vector4f.m_123609_(direction.m_122406_());
      float f = -vector4f.m_123601_();
      float f1 = -vector4f.m_123615_();
      this.f_85867_.m_5483_((double)this.f_85870_, (double)this.f_85871_, (double)this.f_85872_).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_7421_(f, f1).m_7122_(this.f_85873_, this.f_85874_).m_85969_(this.f_85875_).m_5601_(this.f_85876_, this.f_85877_, this.f_85878_).m_5752_();
      this.m_85883_();
   }

   public VertexConsumer m_5483_(double p_85885_, double p_85886_, double p_85887_) {
      this.f_85870_ = (float)p_85885_;
      this.f_85871_ = (float)p_85886_;
      this.f_85872_ = (float)p_85887_;
      return this;
   }

   public VertexConsumer m_6122_(int p_85895_, int p_85896_, int p_85897_, int p_85898_) {
      return this;
   }

   public VertexConsumer m_7421_(float p_85889_, float p_85890_) {
      return this;
   }

   public VertexConsumer m_7122_(int p_85892_, int p_85893_) {
      this.f_85873_ = p_85892_;
      this.f_85874_ = p_85893_;
      return this;
   }

   public VertexConsumer m_7120_(int p_85904_, int p_85905_) {
      this.f_85875_ = p_85904_ | p_85905_ << 16;
      return this;
   }

   public VertexConsumer m_5601_(float p_85900_, float p_85901_, float p_85902_) {
      this.f_85876_ = p_85900_;
      this.f_85877_ = p_85901_;
      this.f_85878_ = p_85902_;
      return this;
   }
}