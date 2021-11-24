package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class UpgradeRecipe implements Recipe<Container> {
   final Ingredient f_44518_;
   final Ingredient f_44519_;
   final ItemStack f_44520_;
   private final ResourceLocation f_44521_;

   public UpgradeRecipe(ResourceLocation p_44523_, Ingredient p_44524_, Ingredient p_44525_, ItemStack p_44526_) {
      this.f_44521_ = p_44523_;
      this.f_44518_ = p_44524_;
      this.f_44519_ = p_44525_;
      this.f_44520_ = p_44526_;
   }

   public boolean m_5818_(Container p_44533_, Level p_44534_) {
      return this.f_44518_.test(p_44533_.m_8020_(0)) && this.f_44519_.test(p_44533_.m_8020_(1));
   }

   public ItemStack m_5874_(Container p_44531_) {
      ItemStack itemstack = this.f_44520_.m_41777_();
      CompoundTag compoundtag = p_44531_.m_8020_(0).m_41783_();
      if (compoundtag != null) {
         itemstack.m_41751_(compoundtag.m_6426_());
      }

      return itemstack;
   }

   public boolean m_8004_(int p_44528_, int p_44529_) {
      return p_44528_ * p_44529_ >= 2;
   }

   public ItemStack m_8043_() {
      return this.f_44520_;
   }

   public boolean m_44535_(ItemStack p_44536_) {
      return this.f_44519_.test(p_44536_);
   }

   public ItemStack m_8042_() {
      return new ItemStack(Blocks.f_50625_);
   }

   public ResourceLocation m_6423_() {
      return this.f_44521_;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44096_;
   }

   public RecipeType<?> m_6671_() {
      return RecipeType.f_44113_;
   }

   public boolean m_142505_() {
      return Stream.of(this.f_44518_, this.f_44519_).anyMatch((p_151284_) -> {
         return p_151284_.m_43908_().length == 0;
      });
   }

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<UpgradeRecipe> {
      public UpgradeRecipe m_6729_(ResourceLocation p_44562_, JsonObject p_44563_) {
         Ingredient ingredient = Ingredient.m_43917_(GsonHelper.m_13930_(p_44563_, "base"));
         Ingredient ingredient1 = Ingredient.m_43917_(GsonHelper.m_13930_(p_44563_, "addition"));
         ItemStack itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44563_, "result"));
         return new UpgradeRecipe(p_44562_, ingredient, ingredient1, itemstack);
      }

      public UpgradeRecipe m_8005_(ResourceLocation p_44565_, FriendlyByteBuf p_44566_) {
         Ingredient ingredient = Ingredient.m_43940_(p_44566_);
         Ingredient ingredient1 = Ingredient.m_43940_(p_44566_);
         ItemStack itemstack = p_44566_.m_130267_();
         return new UpgradeRecipe(p_44565_, ingredient, ingredient1, itemstack);
      }

      public void m_6178_(FriendlyByteBuf p_44553_, UpgradeRecipe p_44554_) {
         p_44554_.f_44518_.m_43923_(p_44553_);
         p_44554_.f_44519_.m_43923_(p_44553_);
         p_44553_.m_130055_(p_44554_.f_44520_);
      }
   }
}
