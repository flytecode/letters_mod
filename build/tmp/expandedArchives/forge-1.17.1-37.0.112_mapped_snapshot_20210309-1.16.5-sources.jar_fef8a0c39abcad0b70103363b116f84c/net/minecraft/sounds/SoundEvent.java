package net.minecraft.sounds;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public class SoundEvent extends net.minecraftforge.registries.ForgeRegistryEntry<SoundEvent> {
   public static final Codec<SoundEvent> f_11655_ = ResourceLocation.f_135803_.xmap(SoundEvent::new, (p_11662_) -> {
      return p_11662_.f_11656_;
   });
   private final ResourceLocation f_11656_;

   public SoundEvent(ResourceLocation p_11659_) {
      this.f_11656_ = p_11659_;
   }

   public ResourceLocation m_11660_() {
      return this.f_11656_;
   }
}
