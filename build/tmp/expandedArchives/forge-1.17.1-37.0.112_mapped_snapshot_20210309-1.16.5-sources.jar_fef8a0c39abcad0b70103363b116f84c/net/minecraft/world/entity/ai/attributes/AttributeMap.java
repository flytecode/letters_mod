package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeMap {
   private static final Logger f_22138_ = LogManager.getLogger();
   private final Map<Attribute, AttributeInstance> f_22139_ = Maps.newHashMap();
   private final Set<AttributeInstance> f_22140_ = Sets.newHashSet();
   private final AttributeSupplier f_22141_;

   public AttributeMap(AttributeSupplier p_22144_) {
      this.f_22141_ = p_22144_;
   }

   private void m_22157_(AttributeInstance p_22158_) {
      if (p_22158_.m_22099_().m_22086_()) {
         this.f_22140_.add(p_22158_);
      }

   }

   public Set<AttributeInstance> m_22145_() {
      return this.f_22140_;
   }

   public Collection<AttributeInstance> m_22170_() {
      return this.f_22139_.values().stream().filter((p_22184_) -> {
         return p_22184_.m_22099_().m_22086_();
      }).collect(Collectors.toList());
   }

   @Nullable
   public AttributeInstance m_22146_(Attribute p_22147_) {
      return this.f_22139_.computeIfAbsent(p_22147_, (p_22188_) -> {
         return this.f_22141_.m_22250_(this::m_22157_, p_22188_);
      });
   }

   public boolean m_22171_(Attribute p_22172_) {
      return this.f_22139_.get(p_22172_) != null || this.f_22141_.m_22258_(p_22172_);
   }

   public boolean m_22154_(Attribute p_22155_, UUID p_22156_) {
      AttributeInstance attributeinstance = this.f_22139_.get(p_22155_);
      return attributeinstance != null ? attributeinstance.m_22111_(p_22156_) != null : this.f_22141_.m_22255_(p_22155_, p_22156_);
   }

   public double m_22181_(Attribute p_22182_) {
      AttributeInstance attributeinstance = this.f_22139_.get(p_22182_);
      return attributeinstance != null ? attributeinstance.m_22135_() : this.f_22141_.m_22245_(p_22182_);
   }

   public double m_22185_(Attribute p_22186_) {
      AttributeInstance attributeinstance = this.f_22139_.get(p_22186_);
      return attributeinstance != null ? attributeinstance.m_22115_() : this.f_22141_.m_22253_(p_22186_);
   }

   public double m_22173_(Attribute p_22174_, UUID p_22175_) {
      AttributeInstance attributeinstance = this.f_22139_.get(p_22174_);
      return attributeinstance != null ? attributeinstance.m_22111_(p_22175_).m_22218_() : this.f_22141_.m_22247_(p_22174_, p_22175_);
   }

   public void m_22161_(Multimap<Attribute, AttributeModifier> p_22162_) {
      p_22162_.asMap().forEach((p_22152_, p_22153_) -> {
         AttributeInstance attributeinstance = this.f_22139_.get(p_22152_);
         if (attributeinstance != null) {
            p_22153_.forEach(attributeinstance::m_22130_);
         }

      });
   }

   public void m_22178_(Multimap<Attribute, AttributeModifier> p_22179_) {
      p_22179_.forEach((p_22149_, p_22150_) -> {
         AttributeInstance attributeinstance = this.m_22146_(p_22149_);
         if (attributeinstance != null) {
            attributeinstance.m_22130_(p_22150_);
            attributeinstance.m_22118_(p_22150_);
         }

      });
   }

   public void m_22159_(AttributeMap p_22160_) {
      p_22160_.f_22139_.values().forEach((p_22177_) -> {
         AttributeInstance attributeinstance = this.m_22146_(p_22177_.m_22099_());
         if (attributeinstance != null) {
            attributeinstance.m_22102_(p_22177_);
         }

      });
   }

   public ListTag m_22180_() {
      ListTag listtag = new ListTag();

      for(AttributeInstance attributeinstance : this.f_22139_.values()) {
         listtag.add(attributeinstance.m_22136_());
      }

      return listtag;
   }

   public void m_22168_(ListTag p_22169_) {
      for(int i = 0; i < p_22169_.size(); ++i) {
         CompoundTag compoundtag = p_22169_.m_128728_(i);
         String s = compoundtag.m_128461_("Name");
         Util.m_137521_(Registry.f_122866_.m_6612_(ResourceLocation.m_135820_(s)), (p_22167_) -> {
            AttributeInstance attributeinstance = this.m_22146_(p_22167_);
            if (attributeinstance != null) {
               attributeinstance.m_22113_(compoundtag);
            }

         }, () -> {
            f_22138_.warn("Ignoring unknown attribute '{}'", (Object)s);
         });
      }

   }
}