package net.minecraft.client.renderer.culling;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Frustum {
   private final Vector4f[] f_112995_ = new Vector4f[6];
   private double f_112996_;
   private double f_112997_;
   private double f_112998_;

   public Frustum(Matrix4f p_113000_, Matrix4f p_113001_) {
      this.m_113026_(p_113000_, p_113001_);
   }

   public void m_113002_(double p_113003_, double p_113004_, double p_113005_) {
      this.f_112996_ = p_113003_;
      this.f_112997_ = p_113004_;
      this.f_112998_ = p_113005_;
   }

   private void m_113026_(Matrix4f p_113027_, Matrix4f p_113028_) {
      Matrix4f matrix4f = p_113028_.m_27658_();
      matrix4f.m_27644_(p_113027_);
      matrix4f.m_27659_();
      this.m_113020_(matrix4f, -1, 0, 0, 0);
      this.m_113020_(matrix4f, 1, 0, 0, 1);
      this.m_113020_(matrix4f, 0, -1, 0, 2);
      this.m_113020_(matrix4f, 0, 1, 0, 3);
      this.m_113020_(matrix4f, 0, 0, -1, 4);
      this.m_113020_(matrix4f, 0, 0, 1, 5);
   }

   private void m_113020_(Matrix4f p_113021_, int p_113022_, int p_113023_, int p_113024_, int p_113025_) {
      Vector4f vector4f = new Vector4f((float)p_113022_, (float)p_113023_, (float)p_113024_, 1.0F);
      vector4f.m_123607_(p_113021_);
      vector4f.m_123618_();
      this.f_112995_[p_113025_] = vector4f;
   }

   public boolean m_113029_(AABB p_113030_) {
      return this.m_113006_(p_113030_.f_82288_, p_113030_.f_82289_, p_113030_.f_82290_, p_113030_.f_82291_, p_113030_.f_82292_, p_113030_.f_82293_);
   }

   private boolean m_113006_(double p_113007_, double p_113008_, double p_113009_, double p_113010_, double p_113011_, double p_113012_) {
      float f = (float)(p_113007_ - this.f_112996_);
      float f1 = (float)(p_113008_ - this.f_112997_);
      float f2 = (float)(p_113009_ - this.f_112998_);
      float f3 = (float)(p_113010_ - this.f_112996_);
      float f4 = (float)(p_113011_ - this.f_112997_);
      float f5 = (float)(p_113012_ - this.f_112998_);
      return this.m_113013_(f, f1, f2, f3, f4, f5);
   }

   private boolean m_113013_(float p_113014_, float p_113015_, float p_113016_, float p_113017_, float p_113018_, float p_113019_) {
      for(int i = 0; i < 6; ++i) {
         Vector4f vector4f = this.f_112995_[i];
         if (!(vector4f.m_123613_(new Vector4f(p_113014_, p_113015_, p_113016_, 1.0F)) > 0.0F) && !(vector4f.m_123613_(new Vector4f(p_113017_, p_113015_, p_113016_, 1.0F)) > 0.0F) && !(vector4f.m_123613_(new Vector4f(p_113014_, p_113018_, p_113016_, 1.0F)) > 0.0F) && !(vector4f.m_123613_(new Vector4f(p_113017_, p_113018_, p_113016_, 1.0F)) > 0.0F) && !(vector4f.m_123613_(new Vector4f(p_113014_, p_113015_, p_113019_, 1.0F)) > 0.0F) && !(vector4f.m_123613_(new Vector4f(p_113017_, p_113015_, p_113019_, 1.0F)) > 0.0F) && !(vector4f.m_123613_(new Vector4f(p_113014_, p_113018_, p_113019_, 1.0F)) > 0.0F) && !(vector4f.m_123613_(new Vector4f(p_113017_, p_113018_, p_113019_, 1.0F)) > 0.0F)) {
            return false;
         }
      }

      return true;
   }
}