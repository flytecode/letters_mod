package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class PotionUtils {
   public static final String f_151254_ = "CustomPotionEffects";
   public static final String f_151255_ = "CustomPotionColor";
   public static final String f_151256_ = "Potion";
   private static final int f_151257_ = 16253176;
   private static final Component f_43545_ = (new TranslatableComponent("effect.none")).m_130940_(ChatFormatting.GRAY);

   public static List<MobEffectInstance> m_43547_(ItemStack p_43548_) {
      return m_43566_(p_43548_.m_41783_());
   }

   public static List<MobEffectInstance> m_43561_(Potion p_43562_, Collection<MobEffectInstance> p_43563_) {
      List<MobEffectInstance> list = Lists.newArrayList();
      list.addAll(p_43562_.m_43488_());
      list.addAll(p_43563_);
      return list;
   }

   public static List<MobEffectInstance> m_43566_(@Nullable CompoundTag p_43567_) {
      List<MobEffectInstance> list = Lists.newArrayList();
      list.addAll(m_43577_(p_43567_).m_43488_());
      m_43568_(p_43567_, list);
      return list;
   }

   public static List<MobEffectInstance> m_43571_(ItemStack p_43572_) {
      return m_43573_(p_43572_.m_41783_());
   }

   public static List<MobEffectInstance> m_43573_(@Nullable CompoundTag p_43574_) {
      List<MobEffectInstance> list = Lists.newArrayList();
      m_43568_(p_43574_, list);
      return list;
   }

   public static void m_43568_(@Nullable CompoundTag p_43569_, List<MobEffectInstance> p_43570_) {
      if (p_43569_ != null && p_43569_.m_128425_("CustomPotionEffects", 9)) {
         ListTag listtag = p_43569_.m_128437_("CustomPotionEffects", 10);

         for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.m_128728_(i);
            MobEffectInstance mobeffectinstance = MobEffectInstance.m_19560_(compoundtag);
            if (mobeffectinstance != null) {
               p_43570_.add(mobeffectinstance);
            }
         }
      }

   }

   public static int m_43575_(ItemStack p_43576_) {
      CompoundTag compoundtag = p_43576_.m_41783_();
      if (compoundtag != null && compoundtag.m_128425_("CustomPotionColor", 99)) {
         return compoundtag.m_128451_("CustomPotionColor");
      } else {
         return m_43579_(p_43576_) == Potions.f_43598_ ? 16253176 : m_43564_(m_43547_(p_43576_));
      }
   }

   public static int m_43559_(Potion p_43560_) {
      return p_43560_ == Potions.f_43598_ ? 16253176 : m_43564_(p_43560_.m_43488_());
   }

   public static int m_43564_(Collection<MobEffectInstance> p_43565_) {
      int i = 3694022;
      if (p_43565_.isEmpty()) {
         return 3694022;
      } else {
         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         int j = 0;

         for(MobEffectInstance mobeffectinstance : p_43565_) {
            if (mobeffectinstance.m_19572_()) {
               int k = mobeffectinstance.m_19544_().m_19484_();
               int l = mobeffectinstance.m_19564_() + 1;
               f += (float)(l * (k >> 16 & 255)) / 255.0F;
               f1 += (float)(l * (k >> 8 & 255)) / 255.0F;
               f2 += (float)(l * (k >> 0 & 255)) / 255.0F;
               j += l;
            }
         }

         if (j == 0) {
            return 0;
         } else {
            f = f / (float)j * 255.0F;
            f1 = f1 / (float)j * 255.0F;
            f2 = f2 / (float)j * 255.0F;
            return (int)f << 16 | (int)f1 << 8 | (int)f2;
         }
      }
   }

   public static Potion m_43579_(ItemStack p_43580_) {
      return m_43577_(p_43580_.m_41783_());
   }

   public static Potion m_43577_(@Nullable CompoundTag p_43578_) {
      return p_43578_ == null ? Potions.f_43598_ : Potion.m_43489_(p_43578_.m_128461_("Potion"));
   }

   public static ItemStack m_43549_(ItemStack p_43550_, Potion p_43551_) {
      ResourceLocation resourcelocation = Registry.f_122828_.m_7981_(p_43551_);
      if (p_43551_ == Potions.f_43598_) {
         p_43550_.m_41749_("Potion");
      } else {
         p_43550_.m_41784_().m_128359_("Potion", resourcelocation.toString());
      }

      return p_43550_;
   }

   public static ItemStack m_43552_(ItemStack p_43553_, Collection<MobEffectInstance> p_43554_) {
      if (p_43554_.isEmpty()) {
         return p_43553_;
      } else {
         CompoundTag compoundtag = p_43553_.m_41784_();
         ListTag listtag = compoundtag.m_128437_("CustomPotionEffects", 9);

         for(MobEffectInstance mobeffectinstance : p_43554_) {
            listtag.add(mobeffectinstance.m_19555_(new CompoundTag()));
         }

         compoundtag.m_128365_("CustomPotionEffects", listtag);
         return p_43553_;
      }
   }

   public static void m_43555_(ItemStack p_43556_, List<Component> p_43557_, float p_43558_) {
      List<MobEffectInstance> list = m_43547_(p_43556_);
      List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
      if (list.isEmpty()) {
         p_43557_.add(f_43545_);
      } else {
         for(MobEffectInstance mobeffectinstance : list) {
            MutableComponent mutablecomponent = new TranslatableComponent(mobeffectinstance.m_19576_());
            MobEffect mobeffect = mobeffectinstance.m_19544_();
            Map<Attribute, AttributeModifier> map = mobeffect.m_19485_();
            if (!map.isEmpty()) {
               for(Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                  AttributeModifier attributemodifier = entry.getValue();
                  AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.m_22214_(), mobeffect.m_7048_(mobeffectinstance.m_19564_(), attributemodifier), attributemodifier.m_22217_());
                  list1.add(new Pair<>(entry.getKey(), attributemodifier1));
               }
            }

            if (mobeffectinstance.m_19564_() > 0) {
               mutablecomponent = new TranslatableComponent("potion.withAmplifier", mutablecomponent, new TranslatableComponent("potion.potency." + mobeffectinstance.m_19564_()));
            }

            if (mobeffectinstance.m_19557_() > 20) {
               mutablecomponent = new TranslatableComponent("potion.withDuration", mutablecomponent, MobEffectUtil.m_19581_(mobeffectinstance, p_43558_));
            }

            p_43557_.add(mutablecomponent.m_130940_(mobeffect.m_19483_().m_19497_()));
         }
      }

      if (!list1.isEmpty()) {
         p_43557_.add(TextComponent.f_131282_);
         p_43557_.add((new TranslatableComponent("potion.whenDrank")).m_130940_(ChatFormatting.DARK_PURPLE));

         for(Pair<Attribute, AttributeModifier> pair : list1) {
            AttributeModifier attributemodifier2 = pair.getSecond();
            double d0 = attributemodifier2.m_22218_();
            double d1;
            if (attributemodifier2.m_22217_() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.m_22217_() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
               d1 = attributemodifier2.m_22218_();
            } else {
               d1 = attributemodifier2.m_22218_() * 100.0D;
            }

            if (d0 > 0.0D) {
               p_43557_.add((new TranslatableComponent("attribute.modifier.plus." + attributemodifier2.m_22217_().m_22235_(), ItemStack.f_41584_.format(d1), new TranslatableComponent(pair.getFirst().m_22087_()))).m_130940_(ChatFormatting.BLUE));
            } else if (d0 < 0.0D) {
               d1 = d1 * -1.0D;
               p_43557_.add((new TranslatableComponent("attribute.modifier.take." + attributemodifier2.m_22217_().m_22235_(), ItemStack.f_41584_.format(d1), new TranslatableComponent(pair.getFirst().m_22087_()))).m_130940_(ChatFormatting.RED));
            }
         }
      }

   }
}