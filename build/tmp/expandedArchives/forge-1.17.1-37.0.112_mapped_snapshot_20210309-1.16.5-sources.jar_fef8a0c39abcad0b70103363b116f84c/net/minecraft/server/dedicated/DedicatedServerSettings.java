package net.minecraft.server.dedicated;

import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class DedicatedServerSettings {
   private final Path f_139772_;
   private DedicatedServerProperties f_139773_;

   public DedicatedServerSettings(Path p_180932_) {
      this.f_139772_ = p_180932_;
      this.f_139773_ = DedicatedServerProperties.m_180929_(p_180932_);
   }

   public DedicatedServerProperties m_139777_() {
      return this.f_139773_;
   }

   public void m_139780_() {
      this.f_139773_.m_139876_(this.f_139772_);
   }

   public DedicatedServerSettings m_139778_(UnaryOperator<DedicatedServerProperties> p_139779_) {
      (this.f_139773_ = p_139779_.apply(this.f_139773_)).m_139876_(this.f_139772_);
      return this;
   }
}