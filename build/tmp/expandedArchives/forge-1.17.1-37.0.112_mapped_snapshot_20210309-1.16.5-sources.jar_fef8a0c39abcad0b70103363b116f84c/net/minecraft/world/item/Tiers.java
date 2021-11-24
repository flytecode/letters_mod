package net.minecraft.world.item;

import java.util.function.Supplier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.crafting.Ingredient;

public enum Tiers implements Tier {
   WOOD(0, 59, 2.0F, 0.0F, 15, () -> {
      return Ingredient.m_43911_(ItemTags.f_13168_);
   }),
   STONE(1, 131, 4.0F, 1.0F, 5, () -> {
      return Ingredient.m_43911_(ItemTags.f_13165_);
   }),
   IRON(2, 250, 6.0F, 2.0F, 14, () -> {
      return Ingredient.m_43929_(Items.f_42416_);
   }),
   DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {
      return Ingredient.m_43929_(Items.f_42415_);
   }),
   GOLD(0, 32, 12.0F, 0.0F, 22, () -> {
      return Ingredient.m_43929_(Items.f_42417_);
   }),
   NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> {
      return Ingredient.m_43929_(Items.f_42418_);
   });

   private final int f_43321_;
   private final int f_43322_;
   private final float f_43323_;
   private final float f_43324_;
   private final int f_43325_;
   private final LazyLoadedValue<Ingredient> f_43326_;

   private Tiers(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_, Supplier<Ingredient> p_43337_) {
      this.f_43321_ = p_43332_;
      this.f_43322_ = p_43333_;
      this.f_43323_ = p_43334_;
      this.f_43324_ = p_43335_;
      this.f_43325_ = p_43336_;
      this.f_43326_ = new LazyLoadedValue<>(p_43337_);
   }

   public int m_6609_() {
      return this.f_43322_;
   }

   public float m_6624_() {
      return this.f_43323_;
   }

   public float m_6631_() {
      return this.f_43324_;
   }

   public int m_6604_() {
      return this.f_43321_;
   }

   public int m_6601_() {
      return this.f_43325_;
   }

   public Ingredient m_6282_() {
      return this.f_43326_.m_13971_();
   }

   @javax.annotation.Nullable public net.minecraft.tags.Tag<net.minecraft.world.level.block.Block> getTag() { return net.minecraftforge.common.ForgeHooks.getTagFromVanillaTier(this); }
}
