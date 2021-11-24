package net.minecraft.world.damagesource;

import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;

public class BadRespawnPointDamage extends DamageSource {
   protected BadRespawnPointDamage() {
      super("badRespawnPoint");
      this.m_19386_();
      this.m_19375_();
   }

   public Component m_6157_(LivingEntity p_19247_) {
      Component component = ComponentUtils.m_130748_(new TranslatableComponent("death.attack.badRespawnPoint.link")).m_130938_((p_19249_) -> {
         return p_19249_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bugs.mojang.com/browse/MCPE-28723")).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, new TextComponent("MCPE-28723")));
      });
      return new TranslatableComponent("death.attack.badRespawnPoint.message", p_19247_.m_5446_(), component);
   }
}