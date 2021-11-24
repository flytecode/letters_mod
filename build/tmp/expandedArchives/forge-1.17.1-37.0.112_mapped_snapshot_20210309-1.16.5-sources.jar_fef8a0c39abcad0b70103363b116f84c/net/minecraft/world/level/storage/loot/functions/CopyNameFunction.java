package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class CopyNameFunction extends LootItemConditionalFunction {
   final CopyNameFunction.NameSource f_80175_;

   CopyNameFunction(LootItemCondition[] p_80177_, CopyNameFunction.NameSource p_80178_) {
      super(p_80177_);
      this.f_80175_ = p_80178_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80747_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(this.f_80175_.f_80200_);
   }

   public ItemStack m_7372_(ItemStack p_80185_, LootContext p_80186_) {
      Object object = p_80186_.m_78953_(this.f_80175_.f_80200_);
      if (object instanceof Nameable) {
         Nameable nameable = (Nameable)object;
         if (nameable.m_8077_()) {
            p_80185_.m_41714_(nameable.m_5446_());
         }
      }

      return p_80185_;
   }

   public static LootItemConditionalFunction.Builder<?> m_80187_(CopyNameFunction.NameSource p_80188_) {
      return m_80683_((p_80191_) -> {
         return new CopyNameFunction(p_80191_, p_80188_);
      });
   }

   public static enum NameSource {
      THIS("this", LootContextParams.f_81455_),
      KILLER("killer", LootContextParams.f_81458_),
      KILLER_PLAYER("killer_player", LootContextParams.f_81456_),
      BLOCK_ENTITY("block_entity", LootContextParams.f_81462_);

      public final String f_80199_;
      public final LootContextParam<?> f_80200_;

      private NameSource(String p_80206_, LootContextParam<?> p_80207_) {
         this.f_80199_ = p_80206_;
         this.f_80200_ = p_80207_;
      }

      public static CopyNameFunction.NameSource m_80208_(String p_80209_) {
         for(CopyNameFunction.NameSource copynamefunction$namesource : values()) {
            if (copynamefunction$namesource.f_80199_.equals(p_80209_)) {
               return copynamefunction$namesource;
            }
         }

         throw new IllegalArgumentException("Invalid name source " + p_80209_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<CopyNameFunction> {
      public void m_6170_(JsonObject p_80219_, CopyNameFunction p_80220_, JsonSerializationContext p_80221_) {
         super.m_6170_(p_80219_, p_80220_, p_80221_);
         p_80219_.addProperty("source", p_80220_.f_80175_.f_80199_);
      }

      public CopyNameFunction m_6821_(JsonObject p_80215_, JsonDeserializationContext p_80216_, LootItemCondition[] p_80217_) {
         CopyNameFunction.NameSource copynamefunction$namesource = CopyNameFunction.NameSource.m_80208_(GsonHelper.m_13906_(p_80215_, "source"));
         return new CopyNameFunction(p_80217_, copynamefunction$namesource);
      }
   }
}