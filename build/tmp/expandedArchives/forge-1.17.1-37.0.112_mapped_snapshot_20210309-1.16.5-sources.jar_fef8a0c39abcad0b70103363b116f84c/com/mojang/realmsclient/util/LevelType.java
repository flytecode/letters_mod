package com.mojang.realmsclient.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum LevelType {
   DEFAULT(0, new TranslatableComponent("generator.default")),
   FLAT(1, new TranslatableComponent("generator.flat")),
   LARGE_BIOMES(2, new TranslatableComponent("generator.large_biomes")),
   AMPLIFIED(3, new TranslatableComponent("generator.amplified"));

   private final int f_167598_;
   private final Component f_167599_;

   private LevelType(int p_167605_, Component p_167606_) {
      this.f_167598_ = p_167605_;
      this.f_167599_ = p_167606_;
   }

   public Component m_167607_() {
      return this.f_167599_;
   }

   public int m_167608_() {
      return this.f_167598_;
   }
}