package net.minecraft.world.item.enchantment;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;

@net.minecraftforge.registries.ObjectHolder("minecraft")
public class Enchantments {
   private static final EquipmentSlot[] f_44964_ = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   public static final Enchantment f_44965_ = m_44992_("protection", new ProtectionEnchantment(Enchantment.Rarity.COMMON, ProtectionEnchantment.Type.ALL, f_44964_));
   public static final Enchantment f_44966_ = m_44992_("fire_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FIRE, f_44964_));
   public static final Enchantment f_44967_ = m_44992_("feather_falling", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FALL, f_44964_));
   public static final Enchantment f_44968_ = m_44992_("blast_protection", new ProtectionEnchantment(Enchantment.Rarity.RARE, ProtectionEnchantment.Type.EXPLOSION, f_44964_));
   public static final Enchantment f_44969_ = m_44992_("projectile_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.PROJECTILE, f_44964_));
   public static final Enchantment f_44970_ = m_44992_("respiration", new OxygenEnchantment(Enchantment.Rarity.RARE, f_44964_));
   public static final Enchantment f_44971_ = m_44992_("aqua_affinity", new WaterWorkerEnchantment(Enchantment.Rarity.RARE, f_44964_));
   public static final Enchantment f_44972_ = m_44992_("thorns", new ThornsEnchantment(Enchantment.Rarity.VERY_RARE, f_44964_));
   public static final Enchantment f_44973_ = m_44992_("depth_strider", new WaterWalkerEnchantment(Enchantment.Rarity.RARE, f_44964_));
   public static final Enchantment f_44974_ = m_44992_("frost_walker", new FrostWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.FEET));
   public static final Enchantment f_44975_ = m_44992_("binding_curse", new BindingCurseEnchantment(Enchantment.Rarity.VERY_RARE, f_44964_));
   public static final Enchantment f_44976_ = m_44992_("soul_speed", new SoulSpeedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.FEET));
   public static final Enchantment f_44977_ = m_44992_("sharpness", new DamageEnchantment(Enchantment.Rarity.COMMON, 0, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44978_ = m_44992_("smite", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44979_ = m_44992_("bane_of_arthropods", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 2, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44980_ = m_44992_("knockback", new KnockbackEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44981_ = m_44992_("fire_aspect", new FireAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44982_ = m_44992_("looting", new LootBonusEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44983_ = m_44992_("sweeping", new SweepingEdgeEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44984_ = m_44992_("efficiency", new DiggingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44985_ = m_44992_("silk_touch", new UntouchingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44986_ = m_44992_("unbreaking", new DigDurabilityEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44987_ = m_44992_("fortune", new LootBonusEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44988_ = m_44992_("power", new ArrowDamageEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44989_ = m_44992_("punch", new ArrowKnockbackEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44990_ = m_44992_("flame", new ArrowFireEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44952_ = m_44992_("infinity", new ArrowInfiniteEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44953_ = m_44992_("luck_of_the_sea", new LootBonusEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.FISHING_ROD, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44954_ = m_44992_("lure", new FishingSpeedEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.FISHING_ROD, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44955_ = m_44992_("loyalty", new TridentLoyaltyEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44956_ = m_44992_("impaling", new TridentImpalerEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44957_ = m_44992_("riptide", new TridentRiptideEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44958_ = m_44992_("channeling", new TridentChannelingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44959_ = m_44992_("multishot", new MultiShotEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44960_ = m_44992_("quick_charge", new QuickChargeEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44961_ = m_44992_("piercing", new ArrowPiercingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND));
   public static final Enchantment f_44962_ = m_44992_("mending", new MendingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.values()));
   public static final Enchantment f_44963_ = m_44992_("vanishing_curse", new VanishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.values()));

   private static Enchantment m_44992_(String p_44993_, Enchantment p_44994_) {
      return Registry.m_122961_(Registry.f_122825_, p_44993_, p_44994_);
   }
}
