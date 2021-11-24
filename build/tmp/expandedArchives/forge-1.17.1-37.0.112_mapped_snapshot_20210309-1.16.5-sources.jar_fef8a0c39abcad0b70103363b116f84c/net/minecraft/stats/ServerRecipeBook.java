package net.minecraft.stats;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.ResourceLocationException;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipeBook extends RecipeBook {
   public static final String f_144248_ = "recipeBook";
   private static final Logger f_12786_ = LogManager.getLogger();

   public int m_12791_(Collection<Recipe<?>> p_12792_, ServerPlayer p_12793_) {
      List<ResourceLocation> list = Lists.newArrayList();
      int i = 0;

      for(Recipe<?> recipe : p_12792_) {
         ResourceLocation resourcelocation = recipe.m_6423_();
         if (!this.f_12680_.contains(resourcelocation) && !recipe.m_5598_()) {
            this.m_12702_(resourcelocation);
            this.m_12719_(resourcelocation);
            list.add(resourcelocation);
            CriteriaTriggers.f_10572_.m_63718_(p_12793_, recipe);
            ++i;
         }
      }

      this.m_12801_(ClientboundRecipePacket.State.ADD, p_12793_, list);
      return i;
   }

   public int m_12806_(Collection<Recipe<?>> p_12807_, ServerPlayer p_12808_) {
      List<ResourceLocation> list = Lists.newArrayList();
      int i = 0;

      for(Recipe<?> recipe : p_12807_) {
         ResourceLocation resourcelocation = recipe.m_6423_();
         if (this.f_12680_.contains(resourcelocation)) {
            this.m_12715_(resourcelocation);
            list.add(resourcelocation);
            ++i;
         }
      }

      this.m_12801_(ClientboundRecipePacket.State.REMOVE, p_12808_, list);
      return i;
   }

   private void m_12801_(ClientboundRecipePacket.State p_12802_, ServerPlayer p_12803_, List<ResourceLocation> p_12804_) {
      p_12803_.f_8906_.m_141995_(new ClientboundRecipePacket(p_12802_, p_12804_, Collections.emptyList(), this.m_12684_()));
   }

   public CompoundTag m_12805_() {
      CompoundTag compoundtag = new CompoundTag();
      this.m_12684_().m_12759_(compoundtag);
      ListTag listtag = new ListTag();

      for(ResourceLocation resourcelocation : this.f_12680_) {
         listtag.add(StringTag.m_129297_(resourcelocation.toString()));
      }

      compoundtag.m_128365_("recipes", listtag);
      ListTag listtag1 = new ListTag();

      for(ResourceLocation resourcelocation1 : this.f_12681_) {
         listtag1.add(StringTag.m_129297_(resourcelocation1.toString()));
      }

      compoundtag.m_128365_("toBeDisplayed", listtag1);
      return compoundtag;
   }

   public void m_12794_(CompoundTag p_12795_, RecipeManager p_12796_) {
      this.m_12687_(RecipeBookSettings.m_12741_(p_12795_));
      ListTag listtag = p_12795_.m_128437_("recipes", 8);
      this.m_12797_(listtag, this::m_12700_, p_12796_);
      ListTag listtag1 = p_12795_.m_128437_("toBeDisplayed", 8);
      this.m_12797_(listtag1, this::m_12723_, p_12796_);
   }

   private void m_12797_(ListTag p_12798_, Consumer<Recipe<?>> p_12799_, RecipeManager p_12800_) {
      for(int i = 0; i < p_12798_.size(); ++i) {
         String s = p_12798_.m_128778_(i);

         try {
            ResourceLocation resourcelocation = new ResourceLocation(s);
            Optional<? extends Recipe<?>> optional = p_12800_.m_44043_(resourcelocation);
            if (!optional.isPresent()) {
               f_12786_.error("Tried to load unrecognized recipe: {} removed now.", (Object)resourcelocation);
            } else {
               p_12799_.accept(optional.get());
            }
         } catch (ResourceLocationException resourcelocationexception) {
            f_12786_.error("Tried to load improperly formatted recipe: {} removed now.", (Object)s);
         }
      }

   }

   public void m_12789_(ServerPlayer p_12790_) {
      p_12790_.f_8906_.m_141995_(new ClientboundRecipePacket(ClientboundRecipePacket.State.INIT, this.f_12680_, this.f_12681_, this.m_12684_()));
   }
}