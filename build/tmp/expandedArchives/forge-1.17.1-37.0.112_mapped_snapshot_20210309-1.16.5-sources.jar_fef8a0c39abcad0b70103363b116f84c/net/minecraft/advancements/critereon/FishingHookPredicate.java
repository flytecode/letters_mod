package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;

public class FishingHookPredicate {
   public static final FishingHookPredicate f_39756_ = new FishingHookPredicate(false);
   private static final String f_150706_ = "in_open_water";
   private final boolean f_39757_;

   private FishingHookPredicate(boolean p_39760_) {
      this.f_39757_ = p_39760_;
   }

   public static FishingHookPredicate m_39766_(boolean p_39767_) {
      return new FishingHookPredicate(p_39767_);
   }

   public static FishingHookPredicate m_39764_(@Nullable JsonElement p_39765_) {
      if (p_39765_ != null && !p_39765_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_39765_, "fishing_hook");
         JsonElement jsonelement = jsonobject.get("in_open_water");
         return jsonelement != null ? new FishingHookPredicate(GsonHelper.m_13877_(jsonelement, "in_open_water")) : f_39756_;
      } else {
         return f_39756_;
      }
   }

   public JsonElement m_39761_() {
      if (this == f_39756_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("in_open_water", new JsonPrimitive(this.f_39757_));
         return jsonobject;
      }
   }

   public boolean m_39762_(Entity p_39763_) {
      if (this == f_39756_) {
         return true;
      } else if (!(p_39763_ instanceof FishingHook)) {
         return false;
      } else {
         FishingHook fishinghook = (FishingHook)p_39763_;
         return this.f_39757_ == fishinghook.m_37166_();
      }
   }
}