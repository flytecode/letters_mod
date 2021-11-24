package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemTransforms {
   public static final ItemTransforms f_111786_ = new ItemTransforms();
   public final ItemTransform f_111787_;
   public final ItemTransform f_111788_;
   public final ItemTransform f_111789_;
   public final ItemTransform f_111790_;
   public final ItemTransform f_111791_;
   public final ItemTransform f_111792_;
   public final ItemTransform f_111793_;
   public final ItemTransform f_111794_;

   private ItemTransforms() {
      this(ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_, ItemTransform.f_111754_);
   }

   @Deprecated
   public ItemTransforms(ItemTransforms p_111807_) {
      this.f_111787_ = p_111807_.f_111787_;
      this.f_111788_ = p_111807_.f_111788_;
      this.f_111789_ = p_111807_.f_111789_;
      this.f_111790_ = p_111807_.f_111790_;
      this.f_111791_ = p_111807_.f_111791_;
      this.f_111792_ = p_111807_.f_111792_;
      this.f_111793_ = p_111807_.f_111793_;
      this.f_111794_ = p_111807_.f_111794_;
   }

   @Deprecated
   public ItemTransforms(ItemTransform p_111798_, ItemTransform p_111799_, ItemTransform p_111800_, ItemTransform p_111801_, ItemTransform p_111802_, ItemTransform p_111803_, ItemTransform p_111804_, ItemTransform p_111805_) {
      this.f_111787_ = p_111798_;
      this.f_111788_ = p_111799_;
      this.f_111789_ = p_111800_;
      this.f_111790_ = p_111801_;
      this.f_111791_ = p_111802_;
      this.f_111792_ = p_111803_;
      this.f_111793_ = p_111804_;
      this.f_111794_ = p_111805_;
   }

   @Deprecated
   public ItemTransform m_111808_(ItemTransforms.TransformType p_111809_) {
      switch(p_111809_) {
      case THIRD_PERSON_LEFT_HAND:
         return this.f_111787_;
      case THIRD_PERSON_RIGHT_HAND:
         return this.f_111788_;
      case FIRST_PERSON_LEFT_HAND:
         return this.f_111789_;
      case FIRST_PERSON_RIGHT_HAND:
         return this.f_111790_;
      case HEAD:
         return this.f_111791_;
      case GUI:
         return this.f_111792_;
      case GROUND:
         return this.f_111793_;
      case FIXED:
         return this.f_111794_;
      default:
         return ItemTransform.f_111754_;
      }
   }

   public boolean m_111810_(ItemTransforms.TransformType p_111811_) {
      return this.m_111808_(p_111811_) != ItemTransform.f_111754_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Deserializer implements JsonDeserializer<ItemTransforms> {
      public ItemTransforms deserialize(JsonElement p_111820_, Type p_111821_, JsonDeserializationContext p_111822_) throws JsonParseException {
         JsonObject jsonobject = p_111820_.getAsJsonObject();
         ItemTransform itemtransform = this.m_111815_(p_111822_, jsonobject, "thirdperson_righthand");
         ItemTransform itemtransform1 = this.m_111815_(p_111822_, jsonobject, "thirdperson_lefthand");
         if (itemtransform1 == ItemTransform.f_111754_) {
            itemtransform1 = itemtransform;
         }

         ItemTransform itemtransform2 = this.m_111815_(p_111822_, jsonobject, "firstperson_righthand");
         ItemTransform itemtransform3 = this.m_111815_(p_111822_, jsonobject, "firstperson_lefthand");
         if (itemtransform3 == ItemTransform.f_111754_) {
            itemtransform3 = itemtransform2;
         }

         ItemTransform itemtransform4 = this.m_111815_(p_111822_, jsonobject, "head");
         ItemTransform itemtransform5 = this.m_111815_(p_111822_, jsonobject, "gui");
         ItemTransform itemtransform6 = this.m_111815_(p_111822_, jsonobject, "ground");
         ItemTransform itemtransform7 = this.m_111815_(p_111822_, jsonobject, "fixed");
         return new ItemTransforms(itemtransform1, itemtransform, itemtransform3, itemtransform2, itemtransform4, itemtransform5, itemtransform6, itemtransform7);
      }

      private ItemTransform m_111815_(JsonDeserializationContext p_111816_, JsonObject p_111817_, String p_111818_) {
         return p_111817_.has(p_111818_) ? p_111816_.deserialize(p_111817_.get(p_111818_), ItemTransform.class) : ItemTransform.f_111754_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum TransformType {
      NONE,
      THIRD_PERSON_LEFT_HAND,
      THIRD_PERSON_RIGHT_HAND,
      FIRST_PERSON_LEFT_HAND,
      FIRST_PERSON_RIGHT_HAND,
      HEAD,
      GUI,
      GROUND,
      FIXED;

      public boolean m_111841_() {
         return this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND;
      }
   }
}
