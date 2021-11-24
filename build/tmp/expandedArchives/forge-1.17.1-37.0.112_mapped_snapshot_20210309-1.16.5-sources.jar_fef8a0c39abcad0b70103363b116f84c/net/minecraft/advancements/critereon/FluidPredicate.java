package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class FluidPredicate {
   public static final FluidPredicate f_41094_ = new FluidPredicate((Tag<Fluid>)null, (Fluid)null, StatePropertiesPredicate.f_67658_);
   @Nullable
   private final Tag<Fluid> f_41095_;
   @Nullable
   private final Fluid f_41096_;
   private final StatePropertiesPredicate f_41097_;

   public FluidPredicate(@Nullable Tag<Fluid> p_41100_, @Nullable Fluid p_41101_, StatePropertiesPredicate p_41102_) {
      this.f_41095_ = p_41100_;
      this.f_41096_ = p_41101_;
      this.f_41097_ = p_41102_;
   }

   public boolean m_41104_(ServerLevel p_41105_, BlockPos p_41106_) {
      if (this == f_41094_) {
         return true;
      } else if (!p_41105_.m_46749_(p_41106_)) {
         return false;
      } else {
         FluidState fluidstate = p_41105_.m_6425_(p_41106_);
         Fluid fluid = fluidstate.m_76152_();
         if (this.f_41095_ != null && !fluid.m_76108_(this.f_41095_)) {
            return false;
         } else if (this.f_41096_ != null && fluid != this.f_41096_) {
            return false;
         } else {
            return this.f_41097_.m_67684_(fluidstate);
         }
      }
   }

   public static FluidPredicate m_41107_(@Nullable JsonElement p_41108_) {
      if (p_41108_ != null && !p_41108_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_41108_, "fluid");
         Fluid fluid = null;
         if (jsonobject.has("fluid")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "fluid"));
            fluid = Registry.f_122822_.m_7745_(resourcelocation);
         }

         Tag<Fluid> tag = null;
         if (jsonobject.has("tag")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "tag"));
            tag = SerializationTags.m_13199_().m_144458_(Registry.f_122899_, resourcelocation1, (p_151160_) -> {
               return new JsonSyntaxException("Unknown fluid tag '" + p_151160_ + "'");
            });
         }

         StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.m_67679_(jsonobject.get("state"));
         return new FluidPredicate(tag, fluid, statepropertiespredicate);
      } else {
         return f_41094_;
      }
   }

   public JsonElement m_41103_() {
      if (this == f_41094_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.f_41096_ != null) {
            jsonobject.addProperty("fluid", Registry.f_122822_.m_7981_(this.f_41096_).toString());
         }

         if (this.f_41095_ != null) {
            jsonobject.addProperty("tag", SerializationTags.m_13199_().m_144454_(Registry.f_122899_, this.f_41095_, () -> {
               return new IllegalStateException("Unknown fluid tag");
            }).toString());
         }

         jsonobject.add("state", this.f_41097_.m_67666_());
         return jsonobject;
      }
   }

   public static class Builder {
      @Nullable
      private Fluid f_151162_;
      @Nullable
      private Tag<Fluid> f_151163_;
      private StatePropertiesPredicate f_151164_ = StatePropertiesPredicate.f_67658_;

      private Builder() {
      }

      public static FluidPredicate.Builder m_151166_() {
         return new FluidPredicate.Builder();
      }

      public FluidPredicate.Builder m_151171_(Fluid p_151172_) {
         this.f_151162_ = p_151172_;
         return this;
      }

      public FluidPredicate.Builder m_151167_(Tag<Fluid> p_151168_) {
         this.f_151163_ = p_151168_;
         return this;
      }

      public FluidPredicate.Builder m_151169_(StatePropertiesPredicate p_151170_) {
         this.f_151164_ = p_151170_;
         return this;
      }

      public FluidPredicate m_151173_() {
         return new FluidPredicate(this.f_151163_, this.f_151162_, this.f_151164_);
      }
   }
}