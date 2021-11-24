package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class EntityDamageSource extends DamageSource {
   protected final Entity f_19391_;
   private boolean f_19392_;

   public EntityDamageSource(String p_19394_, Entity p_19395_) {
      super(p_19394_);
      this.f_19391_ = p_19395_;
   }

   public EntityDamageSource m_19402_() {
      this.f_19392_ = true;
      return this;
   }

   public boolean m_19403_() {
      return this.f_19392_;
   }

   public Entity m_7639_() {
      return this.f_19391_;
   }

   public Component m_6157_(LivingEntity p_19397_) {
      ItemStack itemstack = this.f_19391_ instanceof LivingEntity ? ((LivingEntity)this.f_19391_).m_21205_() : ItemStack.f_41583_;
      String s = "death.attack." + this.f_19326_;
      return !itemstack.m_41619_() && itemstack.m_41788_() ? new TranslatableComponent(s + ".item", p_19397_.m_5446_(), this.f_19391_.m_5446_(), itemstack.m_41611_()) : new TranslatableComponent(s, p_19397_.m_5446_(), this.f_19391_.m_5446_());
   }

   public boolean m_7986_() {
      return this.f_19391_ instanceof LivingEntity && !(this.f_19391_ instanceof Player);
   }

   @Nullable
   public Vec3 m_7270_() {
      return this.f_19391_.m_20182_();
   }

   public String toString() {
      return "EntityDamageSource (" + this.f_19391_ + ")";
   }
}