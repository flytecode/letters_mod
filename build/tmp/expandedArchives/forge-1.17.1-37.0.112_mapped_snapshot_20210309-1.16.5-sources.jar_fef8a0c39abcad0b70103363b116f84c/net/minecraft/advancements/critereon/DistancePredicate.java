package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;

public class DistancePredicate {
   public static final DistancePredicate f_26241_ = new DistancePredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_);
   private final MinMaxBounds.Doubles f_26242_;
   private final MinMaxBounds.Doubles f_26243_;
   private final MinMaxBounds.Doubles f_26244_;
   private final MinMaxBounds.Doubles f_26245_;
   private final MinMaxBounds.Doubles f_26246_;

   public DistancePredicate(MinMaxBounds.Doubles p_26249_, MinMaxBounds.Doubles p_26250_, MinMaxBounds.Doubles p_26251_, MinMaxBounds.Doubles p_26252_, MinMaxBounds.Doubles p_26253_) {
      this.f_26242_ = p_26249_;
      this.f_26243_ = p_26250_;
      this.f_26244_ = p_26251_;
      this.f_26245_ = p_26252_;
      this.f_26246_ = p_26253_;
   }

   public static DistancePredicate m_148836_(MinMaxBounds.Doubles p_148837_) {
      return new DistancePredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, p_148837_, MinMaxBounds.Doubles.f_154779_);
   }

   public static DistancePredicate m_148838_(MinMaxBounds.Doubles p_148839_) {
      return new DistancePredicate(MinMaxBounds.Doubles.f_154779_, p_148839_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_);
   }

   public static DistancePredicate m_148840_(MinMaxBounds.Doubles p_148841_) {
      return new DistancePredicate(MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, MinMaxBounds.Doubles.f_154779_, p_148841_);
   }

   public boolean m_26255_(double p_26256_, double p_26257_, double p_26258_, double p_26259_, double p_26260_, double p_26261_) {
      float f = (float)(p_26256_ - p_26259_);
      float f1 = (float)(p_26257_ - p_26260_);
      float f2 = (float)(p_26258_ - p_26261_);
      if (this.f_26242_.m_154810_((double)Mth.m_14154_(f)) && this.f_26243_.m_154810_((double)Mth.m_14154_(f1)) && this.f_26244_.m_154810_((double)Mth.m_14154_(f2))) {
         if (!this.f_26245_.m_154812_((double)(f * f + f2 * f2))) {
            return false;
         } else {
            return this.f_26246_.m_154812_((double)(f * f + f1 * f1 + f2 * f2));
         }
      } else {
         return false;
      }
   }

   public static DistancePredicate m_26264_(@Nullable JsonElement p_26265_) {
      if (p_26265_ != null && !p_26265_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_26265_, "distance");
         MinMaxBounds.Doubles minmaxbounds$doubles = MinMaxBounds.Doubles.m_154791_(jsonobject.get("x"));
         MinMaxBounds.Doubles minmaxbounds$doubles1 = MinMaxBounds.Doubles.m_154791_(jsonobject.get("y"));
         MinMaxBounds.Doubles minmaxbounds$doubles2 = MinMaxBounds.Doubles.m_154791_(jsonobject.get("z"));
         MinMaxBounds.Doubles minmaxbounds$doubles3 = MinMaxBounds.Doubles.m_154791_(jsonobject.get("horizontal"));
         MinMaxBounds.Doubles minmaxbounds$doubles4 = MinMaxBounds.Doubles.m_154791_(jsonobject.get("absolute"));
         return new DistancePredicate(minmaxbounds$doubles, minmaxbounds$doubles1, minmaxbounds$doubles2, minmaxbounds$doubles3, minmaxbounds$doubles4);
      } else {
         return f_26241_;
      }
   }

   public JsonElement m_26254_() {
      if (this == f_26241_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("x", this.f_26242_.m_55328_());
         jsonobject.add("y", this.f_26243_.m_55328_());
         jsonobject.add("z", this.f_26244_.m_55328_());
         jsonobject.add("horizontal", this.f_26245_.m_55328_());
         jsonobject.add("absolute", this.f_26246_.m_55328_());
         return jsonobject;
      }
   }
}