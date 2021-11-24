package net.minecraft.advancements.critereon;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;

public abstract class EntityTypePredicate {
   public static final EntityTypePredicate f_37636_ = new EntityTypePredicate() {
      public boolean m_7484_(EntityType<?> p_37652_) {
         return true;
      }

      public JsonElement m_5908_() {
         return JsonNull.INSTANCE;
      }
   };
   private static final Joiner f_37637_ = Joiner.on(", ");

   public abstract boolean m_7484_(EntityType<?> p_37642_);

   public abstract JsonElement m_5908_();

   public static EntityTypePredicate m_37643_(@Nullable JsonElement p_37644_) {
      if (p_37644_ != null && !p_37644_.isJsonNull()) {
         String s = GsonHelper.m_13805_(p_37644_, "type");
         if (s.startsWith("#")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(s.substring(1));
            return new EntityTypePredicate.TagPredicate(SerializationTags.m_13199_().m_144458_(Registry.f_122903_, resourcelocation1, (p_37646_) -> {
               return new JsonSyntaxException("Unknown entity tag '" + p_37646_ + "'");
            }));
         } else {
            ResourceLocation resourcelocation = new ResourceLocation(s);
            EntityType<?> entitytype = Registry.f_122826_.m_6612_(resourcelocation).orElseThrow(() -> {
               return new JsonSyntaxException("Unknown entity type '" + resourcelocation + "', valid types are: " + f_37637_.join(Registry.f_122826_.m_6566_()));
            });
            return new EntityTypePredicate.TypePredicate(entitytype);
         }
      } else {
         return f_37636_;
      }
   }

   public static EntityTypePredicate m_37647_(EntityType<?> p_37648_) {
      return new EntityTypePredicate.TypePredicate(p_37648_);
   }

   public static EntityTypePredicate m_37640_(Tag<EntityType<?>> p_37641_) {
      return new EntityTypePredicate.TagPredicate(p_37641_);
   }

   static class TagPredicate extends EntityTypePredicate {
      private final Tag<EntityType<?>> f_37653_;

      public TagPredicate(Tag<EntityType<?>> p_37655_) {
         this.f_37653_ = p_37655_;
      }

      public boolean m_7484_(EntityType<?> p_37658_) {
         return p_37658_.m_20609_(this.f_37653_);
      }

      public JsonElement m_5908_() {
         return new JsonPrimitive("#" + SerializationTags.m_13199_().<EntityType<?>, IllegalStateException>m_144454_(Registry.f_122903_, this.f_37653_, () -> {
            return new IllegalStateException("Unknown entity type tag");
         }));
      }
   }

   static class TypePredicate extends EntityTypePredicate {
      private final EntityType<?> f_37659_;

      public TypePredicate(EntityType<?> p_37661_) {
         this.f_37659_ = p_37661_;
      }

      public boolean m_7484_(EntityType<?> p_37664_) {
         return this.f_37659_ == p_37664_;
      }

      public JsonElement m_5908_() {
         return new JsonPrimitive(Registry.f_122826_.m_7981_(this.f_37659_).toString());
      }
   }
}