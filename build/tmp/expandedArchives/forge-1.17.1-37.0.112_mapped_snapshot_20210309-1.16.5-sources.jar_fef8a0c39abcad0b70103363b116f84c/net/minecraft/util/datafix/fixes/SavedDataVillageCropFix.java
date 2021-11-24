package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;

public class SavedDataVillageCropFix extends DataFix {
   public SavedDataVillageCropFix(Schema p_16882_, boolean p_16883_) {
      super(p_16882_, p_16883_);
   }

   public TypeRewriteRule makeRule() {
      return this.writeFixAndRead("SavedDataVillageCropFix", this.getInputSchema().getType(References.f_16790_), this.getOutputSchema().getType(References.f_16790_), this::m_16884_);
   }

   private <T> Dynamic<T> m_16884_(Dynamic<T> p_16885_) {
      return p_16885_.update("Children", SavedDataVillageCropFix::m_16891_);
   }

   private static <T> Dynamic<T> m_16891_(Dynamic<T> p_16892_) {
      return p_16892_.asStreamOpt().map(SavedDataVillageCropFix::m_16889_).map(p_16892_::createList).result().orElse(p_16892_);
   }

   private static Stream<? extends Dynamic<?>> m_16889_(Stream<? extends Dynamic<?>> p_16890_) {
      return p_16890_.map((Dynamic<?> p_16898_) -> {
         String s = p_16898_.get("id").asString("");
         if ("ViF".equals(s)) {
            return m_16893_(p_16898_);
         } else {
            return "ViDF".equals(s) ? m_16895_(p_16898_) : p_16898_;
         }
      });
   }

   private static <T> Dynamic<T> m_16893_(Dynamic<T> p_16894_) {
      p_16894_ = m_16886_(p_16894_, "CA");
      return m_16886_(p_16894_, "CB");
   }

   private static <T> Dynamic<T> m_16895_(Dynamic<T> p_16896_) {
      p_16896_ = m_16886_(p_16896_, "CA");
      p_16896_ = m_16886_(p_16896_, "CB");
      p_16896_ = m_16886_(p_16896_, "CC");
      return m_16886_(p_16896_, "CD");
   }

   private static <T> Dynamic<T> m_16886_(Dynamic<T> p_16887_, String p_16888_) {
      return p_16887_.get(p_16888_).asNumber().result().isPresent() ? p_16887_.set(p_16888_, BlockStateData.m_14952_(p_16887_.get(p_16888_).asInt(0) << 4)) : p_16887_;
   }
}