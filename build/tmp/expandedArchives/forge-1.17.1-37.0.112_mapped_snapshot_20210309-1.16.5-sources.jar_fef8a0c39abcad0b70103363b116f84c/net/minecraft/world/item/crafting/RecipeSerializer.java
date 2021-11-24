package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface RecipeSerializer<T extends Recipe<?>> extends net.minecraftforge.registries.IForgeRegistryEntry<RecipeSerializer<?>> {
   RecipeSerializer<ShapedRecipe> f_44076_ = m_44098_("crafting_shaped", new ShapedRecipe.Serializer());
   RecipeSerializer<ShapelessRecipe> f_44077_ = m_44098_("crafting_shapeless", new ShapelessRecipe.Serializer());
   SimpleRecipeSerializer<ArmorDyeRecipe> f_44078_ = m_44098_("crafting_special_armordye", new SimpleRecipeSerializer<>(ArmorDyeRecipe::new));
   SimpleRecipeSerializer<BookCloningRecipe> f_44079_ = m_44098_("crafting_special_bookcloning", new SimpleRecipeSerializer<>(BookCloningRecipe::new));
   SimpleRecipeSerializer<MapCloningRecipe> f_44080_ = m_44098_("crafting_special_mapcloning", new SimpleRecipeSerializer<>(MapCloningRecipe::new));
   SimpleRecipeSerializer<MapExtendingRecipe> f_44081_ = m_44098_("crafting_special_mapextending", new SimpleRecipeSerializer<>(MapExtendingRecipe::new));
   SimpleRecipeSerializer<FireworkRocketRecipe> f_44082_ = m_44098_("crafting_special_firework_rocket", new SimpleRecipeSerializer<>(FireworkRocketRecipe::new));
   SimpleRecipeSerializer<FireworkStarRecipe> f_44083_ = m_44098_("crafting_special_firework_star", new SimpleRecipeSerializer<>(FireworkStarRecipe::new));
   SimpleRecipeSerializer<FireworkStarFadeRecipe> f_44084_ = m_44098_("crafting_special_firework_star_fade", new SimpleRecipeSerializer<>(FireworkStarFadeRecipe::new));
   SimpleRecipeSerializer<TippedArrowRecipe> f_44085_ = m_44098_("crafting_special_tippedarrow", new SimpleRecipeSerializer<>(TippedArrowRecipe::new));
   SimpleRecipeSerializer<BannerDuplicateRecipe> f_44086_ = m_44098_("crafting_special_bannerduplicate", new SimpleRecipeSerializer<>(BannerDuplicateRecipe::new));
   SimpleRecipeSerializer<ShieldDecorationRecipe> f_44087_ = m_44098_("crafting_special_shielddecoration", new SimpleRecipeSerializer<>(ShieldDecorationRecipe::new));
   SimpleRecipeSerializer<ShulkerBoxColoring> f_44088_ = m_44098_("crafting_special_shulkerboxcoloring", new SimpleRecipeSerializer<>(ShulkerBoxColoring::new));
   SimpleRecipeSerializer<SuspiciousStewRecipe> f_44089_ = m_44098_("crafting_special_suspiciousstew", new SimpleRecipeSerializer<>(SuspiciousStewRecipe::new));
   SimpleRecipeSerializer<RepairItemRecipe> f_44090_ = m_44098_("crafting_special_repairitem", new SimpleRecipeSerializer<>(RepairItemRecipe::new));
   SimpleCookingSerializer<SmeltingRecipe> f_44091_ = m_44098_("smelting", new SimpleCookingSerializer<>(SmeltingRecipe::new, 200));
   SimpleCookingSerializer<BlastingRecipe> f_44092_ = m_44098_("blasting", new SimpleCookingSerializer<>(BlastingRecipe::new, 100));
   SimpleCookingSerializer<SmokingRecipe> f_44093_ = m_44098_("smoking", new SimpleCookingSerializer<>(SmokingRecipe::new, 100));
   SimpleCookingSerializer<CampfireCookingRecipe> f_44094_ = m_44098_("campfire_cooking", new SimpleCookingSerializer<>(CampfireCookingRecipe::new, 100));
   RecipeSerializer<StonecutterRecipe> f_44095_ = m_44098_("stonecutting", new SingleItemRecipe.Serializer<>(StonecutterRecipe::new));
   RecipeSerializer<UpgradeRecipe> f_44096_ = m_44098_("smithing", new UpgradeRecipe.Serializer());

   T m_6729_(ResourceLocation p_44103_, JsonObject p_44104_);

   @javax.annotation.Nullable
   T m_8005_(ResourceLocation p_44105_, FriendlyByteBuf p_44106_);

   void m_6178_(FriendlyByteBuf p_44101_, T p_44102_);

   static <S extends RecipeSerializer<T>, T extends Recipe<?>> S m_44098_(String p_44099_, S p_44100_) {
      return Registry.m_122961_(Registry.f_122865_, p_44099_, p_44100_);
   }
}
