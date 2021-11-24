package net.minecraft.world.item.enchantment;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;

public abstract class Enchantment extends net.minecraftforge.registries.ForgeRegistryEntry<Enchantment> {
   private final net.minecraftforge.common.util.ReverseTagWrapper<Enchantment> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, () -> net.minecraft.tags.SerializationTags.m_13199_().m_144452_(net.minecraftforge.registries.ForgeRegistries.Keys.ENCHANTMENTS));
   private final EquipmentSlot[] f_44671_;
   private final Enchantment.Rarity f_44674_;
   public final EnchantmentCategory f_44672_;
   @Nullable
   protected String f_44673_;

   @Nullable
   public static Enchantment m_44697_(int p_44698_) {
      return Registry.f_122825_.m_7942_(p_44698_);
   }

   protected Enchantment(Enchantment.Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
      this.f_44674_ = p_44676_;
      this.f_44672_ = p_44677_;
      this.f_44671_ = p_44678_;
   }

   public java.util.Set<net.minecraft.resources.ResourceLocation> getTags() {
      return reverseTags.getTagNames();
   }

   public boolean is(net.minecraft.tags.Tag<Enchantment> tag) {
      return tag.m_8110_(this);
   }

   public Map<EquipmentSlot, ItemStack> m_44684_(LivingEntity p_44685_) {
      Map<EquipmentSlot, ItemStack> map = Maps.newEnumMap(EquipmentSlot.class);

      for(EquipmentSlot equipmentslot : this.f_44671_) {
         ItemStack itemstack = p_44685_.m_6844_(equipmentslot);
         if (!itemstack.m_41619_()) {
            map.put(equipmentslot, itemstack);
         }
      }

      return map;
   }

   public Enchantment.Rarity m_44699_() {
      return this.f_44674_;
   }

   public int m_44702_() {
      return 1;
   }

   public int m_6586_() {
      return 1;
   }

   public int m_6183_(int p_44679_) {
      return 1 + p_44679_ * 10;
   }

   public int m_6175_(int p_44691_) {
      return this.m_6183_(p_44691_) + 5;
   }

   public int m_7205_(int p_44680_, DamageSource p_44681_) {
      return 0;
   }

   public float m_7335_(int p_44682_, MobType p_44683_) {
      return 0.0F;
   }

   public final boolean m_44695_(Enchantment p_44696_) {
      return this.m_5975_(p_44696_) && p_44696_.m_5975_(this);
   }

   protected boolean m_5975_(Enchantment p_44690_) {
      return this != p_44690_;
   }

   protected String m_44703_() {
      if (this.f_44673_ == null) {
         this.f_44673_ = Util.m_137492_("enchantment", Registry.f_122825_.m_7981_(this));
      }

      return this.f_44673_;
   }

   public String m_44704_() {
      return this.m_44703_();
   }

   public Component m_44700_(int p_44701_) {
      MutableComponent mutablecomponent = new TranslatableComponent(this.m_44704_());
      if (this.m_6589_()) {
         mutablecomponent.m_130940_(ChatFormatting.RED);
      } else {
         mutablecomponent.m_130940_(ChatFormatting.GRAY);
      }

      if (p_44701_ != 1 || this.m_6586_() != 1) {
         mutablecomponent.m_130946_(" ").m_7220_(new TranslatableComponent("enchantment.level." + p_44701_));
      }

      return mutablecomponent;
   }

   public boolean m_6081_(ItemStack p_44689_) {
      return canApplyAtEnchantingTable(p_44689_);
   }

   public void m_7677_(LivingEntity p_44686_, Entity p_44687_, int p_44688_) {
   }

   public void m_7675_(LivingEntity p_44692_, Entity p_44693_, int p_44694_) {
   }

   public boolean m_6591_() {
      return false;
   }

   public boolean m_6589_() {
      return false;
   }

   public boolean m_6594_() {
      return true;
   }

   public boolean m_6592_() {
      return true;
   }

   /**
    * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
    * applies for <i>all possible</i> enchantments.
    * @param stack
    * @return
    */
   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return stack.canApplyAtEnchantingTable(this);
   }

   /**
    * Is this enchantment allowed to be enchanted on books via Enchantment Table
    * @return false to disable the vanilla feature
    */
   public boolean isAllowedOnBooks() {
      return true;
   }

   public static enum Rarity {
      COMMON(10),
      UNCOMMON(5),
      RARE(2),
      VERY_RARE(1);

      private final int f_44709_;

      private Rarity(int p_44715_) {
         this.f_44709_ = p_44715_;
      }

      public int m_44716_() {
         return this.f_44709_;
      }
   }
}
