package net.minecraft.world.item.alchemy;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

public class Potion extends net.minecraftforge.registries.ForgeRegistryEntry<Potion> {
   private final net.minecraftforge.common.util.ReverseTagWrapper<Potion> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, () -> net.minecraft.tags.SerializationTags.m_13199_().m_144452_(Registry.f_122905_));
   private final String f_43481_;
   private final ImmutableList<MobEffectInstance> f_43482_;

   public static Potion m_43489_(String p_43490_) {
      return Registry.f_122828_.m_7745_(ResourceLocation.m_135820_(p_43490_));
   }

   public Potion(MobEffectInstance... p_43487_) {
      this((String)null, p_43487_);
   }

   public Potion(@Nullable String p_43484_, MobEffectInstance... p_43485_) {
      this.f_43481_ = p_43484_;
      this.f_43482_ = ImmutableList.copyOf(p_43485_);
   }

   public java.util.Set<net.minecraft.resources.ResourceLocation> getTags() {
      return reverseTags.getTagNames();
   }

   public boolean is(net.minecraft.tags.Tag<Potion> tag) {
      return tag.m_8110_(this);
   }

   public String m_43492_(String p_43493_) {
      return p_43493_ + (this.f_43481_ == null ? Registry.f_122828_.m_7981_(this).m_135815_() : this.f_43481_);
   }

   public List<MobEffectInstance> m_43488_() {
      return this.f_43482_;
   }

   public boolean m_43491_() {
      if (!this.f_43482_.isEmpty()) {
         for(MobEffectInstance mobeffectinstance : this.f_43482_) {
            if (mobeffectinstance.m_19544_().m_8093_()) {
               return true;
            }
         }
      }

      return false;
   }
}
