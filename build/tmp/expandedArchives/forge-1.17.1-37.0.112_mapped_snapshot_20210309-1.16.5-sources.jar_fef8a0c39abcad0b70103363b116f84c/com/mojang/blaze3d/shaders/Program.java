package com.mojang.blaze3d.shaders;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Program {
   private static final Logger f_166597_ = LogManager.getLogger();
   private static final int f_166598_ = 32768;
   private final Program.Type f_85535_;
   private final String f_85536_;
   private int f_85537_;

   protected Program(Program.Type p_85540_, int p_85541_, String p_85542_) {
      this.f_85535_ = p_85540_;
      this.f_85537_ = p_85541_;
      this.f_85536_ = p_85542_;
   }

   public void m_166610_(Shader p_166611_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      GlStateManager.m_84423_(p_166611_.m_142658_(), this.m_166618_());
   }

   public void m_85543_() {
      if (this.f_85537_ != -1) {
         RenderSystem.m_69393_(RenderSystem::m_69586_);
         GlStateManager.m_84421_(this.f_85537_);
         this.f_85537_ = -1;
         this.f_85535_.m_85570_().remove(this.f_85536_);
      }
   }

   public String m_85551_() {
      return this.f_85536_;
   }

   public static Program m_166604_(Program.Type p_166605_, String p_166606_, InputStream p_166607_, String p_166608_, GlslPreprocessor p_166609_) throws IOException {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      int i = m_166612_(p_166605_, p_166606_, p_166607_, p_166608_, p_166609_);
      Program program = new Program(p_166605_, i, p_166606_);
      p_166605_.m_85570_().put(p_166606_, program);
      return program;
   }

   protected static int m_166612_(Program.Type p_166613_, String p_166614_, InputStream p_166615_, String p_166616_, GlslPreprocessor p_166617_) throws IOException {
      String s = TextureUtil.m_85311_(p_166615_);
      if (s == null) {
         throw new IOException("Could not load program " + p_166613_.m_85566_());
      } else {
         int i = GlStateManager.m_84447_(p_166613_.m_85571_());
         GlStateManager.m_157116_(i, p_166617_.m_166461_(s));
         GlStateManager.m_84465_(i);
         if (GlStateManager.m_84449_(i, 35713) == 0) {
            String s1 = StringUtils.trim(GlStateManager.m_84492_(i, 32768));
            throw new IOException("Couldn't compile " + p_166613_.m_85566_() + " program (" + p_166616_ + ", " + p_166614_ + ") : " + s1);
         } else {
            return i;
         }
      }
   }

   private static Program m_166600_(Program.Type p_166601_, String p_166602_, int p_166603_) {
      return new Program(p_166601_, p_166603_, p_166602_);
   }

   protected int m_166618_() {
      return this.f_85537_;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Type {
      VERTEX("vertex", ".vsh", 35633),
      FRAGMENT("fragment", ".fsh", 35632);

      private final String f_85554_;
      private final String f_85555_;
      private final int f_85556_;
      private final Map<String, Program> f_85557_ = Maps.newHashMap();

      private Type(String p_85563_, String p_85564_, int p_85565_) {
         this.f_85554_ = p_85563_;
         this.f_85555_ = p_85564_;
         this.f_85556_ = p_85565_;
      }

      public String m_85566_() {
         return this.f_85554_;
      }

      public String m_85569_() {
         return this.f_85555_;
      }

      int m_85571_() {
         return this.f_85556_;
      }

      public Map<String, Program> m_85570_() {
         return this.f_85557_;
      }
   }
}