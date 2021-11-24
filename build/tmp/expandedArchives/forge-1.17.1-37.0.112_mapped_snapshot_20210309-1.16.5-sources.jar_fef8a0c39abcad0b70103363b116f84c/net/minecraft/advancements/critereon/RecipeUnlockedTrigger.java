package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeUnlockedTrigger extends SimpleCriterionTrigger<RecipeUnlockedTrigger.TriggerInstance> {
   static final ResourceLocation f_63714_ = new ResourceLocation("recipe_unlocked");

   public ResourceLocation m_7295_() {
      return f_63714_;
   }

   public RecipeUnlockedTrigger.TriggerInstance m_7214_(JsonObject p_63725_, EntityPredicate.Composite p_63726_, DeserializationContext p_63727_) {
      ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_63725_, "recipe"));
      return new RecipeUnlockedTrigger.TriggerInstance(p_63726_, resourcelocation);
   }

   public void m_63718_(ServerPlayer p_63719_, Recipe<?> p_63720_) {
      this.m_66234_(p_63719_, (p_63723_) -> {
         return p_63723_.m_63739_(p_63720_);
      });
   }

   public static RecipeUnlockedTrigger.TriggerInstance m_63728_(ResourceLocation p_63729_) {
      return new RecipeUnlockedTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_63729_);
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ResourceLocation f_63735_;

      public TriggerInstance(EntityPredicate.Composite p_63737_, ResourceLocation p_63738_) {
         super(RecipeUnlockedTrigger.f_63714_, p_63737_);
         this.f_63735_ = p_63738_;
      }

      public JsonObject m_7683_(SerializationContext p_63742_) {
         JsonObject jsonobject = super.m_7683_(p_63742_);
         jsonobject.addProperty("recipe", this.f_63735_.toString());
         return jsonobject;
      }

      public boolean m_63739_(Recipe<?> p_63740_) {
         return this.f_63735_.equals(p_63740_.m_6423_());
      }
   }
}