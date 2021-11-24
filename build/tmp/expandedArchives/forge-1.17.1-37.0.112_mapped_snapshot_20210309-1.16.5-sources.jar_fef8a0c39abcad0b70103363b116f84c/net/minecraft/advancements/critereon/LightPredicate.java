package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;

public class LightPredicate {
   public static final LightPredicate f_51335_ = new LightPredicate(MinMaxBounds.Ints.f_55364_);
   private final MinMaxBounds.Ints f_51336_;

   LightPredicate(MinMaxBounds.Ints p_51339_) {
      this.f_51336_ = p_51339_;
   }

   public boolean m_51341_(ServerLevel p_51342_, BlockPos p_51343_) {
      if (this == f_51335_) {
         return true;
      } else if (!p_51342_.m_46749_(p_51343_)) {
         return false;
      } else {
         return this.f_51336_.m_55390_(p_51342_.m_46803_(p_51343_));
      }
   }

   public JsonElement m_51340_() {
      if (this == f_51335_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("light", this.f_51336_.m_55328_());
         return jsonobject;
      }
   }

   public static LightPredicate m_51344_(@Nullable JsonElement p_51345_) {
      if (p_51345_ != null && !p_51345_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_51345_, "light");
         MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(jsonobject.get("light"));
         return new LightPredicate(minmaxbounds$ints);
      } else {
         return f_51335_;
      }
   }

   public static class Builder {
      private MinMaxBounds.Ints f_153101_ = MinMaxBounds.Ints.f_55364_;

      public static LightPredicate.Builder m_153103_() {
         return new LightPredicate.Builder();
      }

      public LightPredicate.Builder m_153104_(MinMaxBounds.Ints p_153105_) {
         this.f_153101_ = p_153105_;
         return this;
      }

      public LightPredicate m_153106_() {
         return new LightPredicate(this.f_153101_);
      }
   }
}