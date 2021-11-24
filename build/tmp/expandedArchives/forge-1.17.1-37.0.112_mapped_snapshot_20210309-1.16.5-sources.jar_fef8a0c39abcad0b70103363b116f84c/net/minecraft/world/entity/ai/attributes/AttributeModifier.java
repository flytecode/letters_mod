package net.minecraft.world.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeModifier {
   private static final Logger f_22189_ = LogManager.getLogger();
   private final double f_22190_;
   private final AttributeModifier.Operation f_22191_;
   private final Supplier<String> f_22192_;
   private final UUID f_22193_;

   public AttributeModifier(String p_22196_, double p_22197_, AttributeModifier.Operation p_22198_) {
      this(Mth.m_14062_(ThreadLocalRandom.current()), () -> {
         return p_22196_;
      }, p_22197_, p_22198_);
   }

   public AttributeModifier(UUID p_22200_, String p_22201_, double p_22202_, AttributeModifier.Operation p_22203_) {
      this(p_22200_, () -> {
         return p_22201_;
      }, p_22202_, p_22203_);
   }

   public AttributeModifier(UUID p_22205_, Supplier<String> p_22206_, double p_22207_, AttributeModifier.Operation p_22208_) {
      this.f_22193_ = p_22205_;
      this.f_22192_ = p_22206_;
      this.f_22190_ = p_22207_;
      this.f_22191_ = p_22208_;
   }

   public UUID m_22209_() {
      return this.f_22193_;
   }

   public String m_22214_() {
      return this.f_22192_.get();
   }

   public AttributeModifier.Operation m_22217_() {
      return this.f_22191_;
   }

   public double m_22218_() {
      return this.f_22190_;
   }

   public boolean equals(Object p_22221_) {
      if (this == p_22221_) {
         return true;
      } else if (p_22221_ != null && this.getClass() == p_22221_.getClass()) {
         AttributeModifier attributemodifier = (AttributeModifier)p_22221_;
         return Objects.equals(this.f_22193_, attributemodifier.f_22193_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.f_22193_.hashCode();
   }

   public String toString() {
      return "AttributeModifier{amount=" + this.f_22190_ + ", operation=" + this.f_22191_ + ", name='" + (String)this.f_22192_.get() + "', id=" + this.f_22193_ + "}";
   }

   public CompoundTag m_22219_() {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("Name", this.m_22214_());
      compoundtag.m_128347_("Amount", this.f_22190_);
      compoundtag.m_128405_("Operation", this.f_22191_.m_22235_());
      compoundtag.m_128362_("UUID", this.f_22193_);
      return compoundtag;
   }

   @Nullable
   public static AttributeModifier m_22212_(CompoundTag p_22213_) {
      try {
         UUID uuid = p_22213_.m_128342_("UUID");
         AttributeModifier.Operation attributemodifier$operation = AttributeModifier.Operation.m_22236_(p_22213_.m_128451_("Operation"));
         return new AttributeModifier(uuid, p_22213_.m_128461_("Name"), p_22213_.m_128459_("Amount"), attributemodifier$operation);
      } catch (Exception exception) {
         f_22189_.warn("Unable to create attribute: {}", (Object)exception.getMessage());
         return null;
      }
   }

   public static enum Operation {
      ADDITION(0),
      MULTIPLY_BASE(1),
      MULTIPLY_TOTAL(2);

      private static final AttributeModifier.Operation[] f_22227_ = new AttributeModifier.Operation[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
      private final int f_22228_;

      private Operation(int p_22234_) {
         this.f_22228_ = p_22234_;
      }

      public int m_22235_() {
         return this.f_22228_;
      }

      public static AttributeModifier.Operation m_22236_(int p_22237_) {
         if (p_22237_ >= 0 && p_22237_ < f_22227_.length) {
            return f_22227_[p_22237_];
         } else {
            throw new IllegalArgumentException("No operation with value " + p_22237_);
         }
      }
   }
}