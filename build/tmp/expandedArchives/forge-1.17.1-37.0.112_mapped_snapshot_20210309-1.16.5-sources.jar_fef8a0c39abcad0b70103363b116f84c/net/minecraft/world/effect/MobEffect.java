package net.minecraft.world.effect;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class MobEffect extends net.minecraftforge.registries.ForgeRegistryEntry<MobEffect> implements net.minecraftforge.common.extensions.IForgeMobEffect {
   private final Map<Attribute, AttributeModifier> f_19446_ = Maps.newHashMap();
   private final MobEffectCategory f_19447_;
   private final int f_19448_;
   @Nullable
   private String f_19449_;

   @Nullable
   public static MobEffect m_19453_(int p_19454_) {
      return Registry.f_122823_.m_7942_(p_19454_);
   }

   public static int m_19459_(MobEffect p_19460_) {
      return Registry.f_122823_.m_7447_(p_19460_);
   }

   protected MobEffect(MobEffectCategory p_19451_, int p_19452_) {
      this.f_19447_ = p_19451_;
      this.f_19448_ = p_19452_;
      initClient();
   }

   public void m_6742_(LivingEntity p_19467_, int p_19468_) {
      if (this == MobEffects.f_19605_) {
         if (p_19467_.m_21223_() < p_19467_.m_21233_()) {
            p_19467_.m_5634_(1.0F);
         }
      } else if (this == MobEffects.f_19614_) {
         if (p_19467_.m_21223_() > 1.0F) {
            p_19467_.m_6469_(DamageSource.f_19319_, 1.0F);
         }
      } else if (this == MobEffects.f_19615_) {
         p_19467_.m_6469_(DamageSource.f_19320_, 1.0F);
      } else if (this == MobEffects.f_19612_ && p_19467_ instanceof Player) {
         ((Player)p_19467_).m_36399_(0.005F * (float)(p_19468_ + 1));
      } else if (this == MobEffects.f_19618_ && p_19467_ instanceof Player) {
         if (!p_19467_.f_19853_.f_46443_) {
            ((Player)p_19467_).m_36324_().m_38707_(p_19468_ + 1, 1.0F);
         }
      } else if ((this != MobEffects.f_19601_ || p_19467_.m_21222_()) && (this != MobEffects.f_19602_ || !p_19467_.m_21222_())) {
         if (this == MobEffects.f_19602_ && !p_19467_.m_21222_() || this == MobEffects.f_19601_ && p_19467_.m_21222_()) {
            p_19467_.m_6469_(DamageSource.f_19319_, (float)(6 << p_19468_));
         }
      } else {
         p_19467_.m_5634_((float)Math.max(4 << p_19468_, 0));
      }

   }

   public void m_19461_(@Nullable Entity p_19462_, @Nullable Entity p_19463_, LivingEntity p_19464_, int p_19465_, double p_19466_) {
      if ((this != MobEffects.f_19601_ || p_19464_.m_21222_()) && (this != MobEffects.f_19602_ || !p_19464_.m_21222_())) {
         if (this == MobEffects.f_19602_ && !p_19464_.m_21222_() || this == MobEffects.f_19601_ && p_19464_.m_21222_()) {
            int j = (int)(p_19466_ * (double)(6 << p_19465_) + 0.5D);
            if (p_19462_ == null) {
               p_19464_.m_6469_(DamageSource.f_19319_, (float)j);
            } else {
               p_19464_.m_6469_(DamageSource.m_19367_(p_19462_, p_19463_), (float)j);
            }
         } else {
            this.m_6742_(p_19464_, p_19465_);
         }
      } else {
         int i = (int)(p_19466_ * (double)(4 << p_19465_) + 0.5D);
         p_19464_.m_5634_((float)i);
      }

   }

   public boolean m_6584_(int p_19455_, int p_19456_) {
      if (this == MobEffects.f_19605_) {
         int k = 50 >> p_19456_;
         if (k > 0) {
            return p_19455_ % k == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.f_19614_) {
         int j = 25 >> p_19456_;
         if (j > 0) {
            return p_19455_ % j == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.f_19615_) {
         int i = 40 >> p_19456_;
         if (i > 0) {
            return p_19455_ % i == 0;
         } else {
            return true;
         }
      } else {
         return this == MobEffects.f_19612_;
      }
   }

   public boolean m_8093_() {
      return false;
   }

   protected String m_19477_() {
      if (this.f_19449_ == null) {
         this.f_19449_ = Util.m_137492_("effect", Registry.f_122823_.m_7981_(this));
      }

      return this.f_19449_;
   }

   public String m_19481_() {
      return this.m_19477_();
   }

   public Component m_19482_() {
      return new TranslatableComponent(this.m_19481_());
   }

   public MobEffectCategory m_19483_() {
      return this.f_19447_;
   }

   public int m_19484_() {
      return this.f_19448_;
   }

   public MobEffect m_19472_(Attribute p_19473_, String p_19474_, double p_19475_, AttributeModifier.Operation p_19476_) {
      AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(p_19474_), this::m_19481_, p_19475_, p_19476_);
      this.f_19446_.put(p_19473_, attributemodifier);
      return this;
   }

   public Map<Attribute, AttributeModifier> m_19485_() {
      return this.f_19446_;
   }

   public void m_6386_(LivingEntity p_19469_, AttributeMap p_19470_, int p_19471_) {
      for(Entry<Attribute, AttributeModifier> entry : this.f_19446_.entrySet()) {
         AttributeInstance attributeinstance = p_19470_.m_22146_(entry.getKey());
         if (attributeinstance != null) {
            attributeinstance.m_22130_(entry.getValue());
         }
      }

   }

   public void m_6385_(LivingEntity p_19478_, AttributeMap p_19479_, int p_19480_) {
      for(Entry<Attribute, AttributeModifier> entry : this.f_19446_.entrySet()) {
         AttributeInstance attributeinstance = p_19479_.m_22146_(entry.getKey());
         if (attributeinstance != null) {
            AttributeModifier attributemodifier = entry.getValue();
            attributeinstance.m_22130_(attributemodifier);
            attributeinstance.m_22125_(new AttributeModifier(attributemodifier.m_22209_(), this.m_19481_() + " " + p_19480_, this.m_7048_(p_19480_, attributemodifier), attributemodifier.m_22217_()));
         }
      }

   }

   public double m_7048_(int p_19457_, AttributeModifier p_19458_) {
      return p_19458_.m_22218_() * (double)(p_19457_ + 1);
   }

   public boolean m_19486_() {
      return this.f_19447_ == MobEffectCategory.BENEFICIAL;
   }

   // FORGE START
   private Object effectRenderer;

   /*
      DO NOT CALL, IT WILL DISAPPEAR IN THE FUTURE
      Call RenderProperties.getEffectRenderer instead
    */
   public Object getEffectRendererInternal() {
      return effectRenderer;
   }

   private void initClient() {
      // Minecraft instance isn't available in datagen, so don't call initializeClient if in datagen
      if (net.minecraftforge.fml.loading.FMLEnvironment.dist == net.minecraftforge.api.distmarker.Dist.CLIENT && !net.minecraftforge.fml.loading.FMLLoader.getLaunchHandler().isData()) {
         initializeClient(properties -> {
            this.effectRenderer = properties;
         });
      }
   }

   public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.EffectRenderer> consumer) {
   }
   // END FORGE

}
