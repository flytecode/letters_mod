package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IndirectEntityDamageSource extends EntityDamageSource {
   @Nullable
   private final Entity f_19404_;

   public IndirectEntityDamageSource(String p_19406_, Entity p_19407_, @Nullable Entity p_19408_) {
      super(p_19406_, p_19407_);
      this.f_19404_ = p_19408_;
   }

   @Nullable
   public Entity m_7640_() {
      return this.f_19391_;
   }

   @Nullable
   public Entity m_7639_() {
      return this.f_19404_;
   }

   public Component m_6157_(LivingEntity p_19410_) {
      Component component = this.f_19404_ == null ? this.f_19391_.m_5446_() : this.f_19404_.m_5446_();
      ItemStack itemstack = this.f_19404_ instanceof LivingEntity ? ((LivingEntity)this.f_19404_).m_21205_() : ItemStack.f_41583_;
      String s = "death.attack." + this.f_19326_;
      String s1 = s + ".item";
      return !itemstack.m_41619_() && itemstack.m_41788_() ? new TranslatableComponent(s1, p_19410_.m_5446_(), component, itemstack.m_41611_()) : new TranslatableComponent(s, p_19410_.m_5446_(), component);
   }
}