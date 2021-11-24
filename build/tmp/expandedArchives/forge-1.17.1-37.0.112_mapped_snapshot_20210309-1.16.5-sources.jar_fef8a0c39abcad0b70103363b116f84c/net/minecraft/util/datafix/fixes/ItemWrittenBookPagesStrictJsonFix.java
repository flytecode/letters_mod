package net.minecraft.util.datafix.fixes;

import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;

public class ItemWrittenBookPagesStrictJsonFix extends DataFix {
   public ItemWrittenBookPagesStrictJsonFix(Schema p_16164_, boolean p_16165_) {
      super(p_16164_, p_16165_);
   }

   public Dynamic<?> m_16171_(Dynamic<?> p_16172_) {
      return p_16172_.update("pages", (p_16175_) -> {
         return DataFixUtils.orElse(p_16175_.asStreamOpt().map((p_145441_) -> {
            return p_145441_.map((p_145443_) -> {
               if (!p_145443_.asString().result().isPresent()) {
                  return p_145443_;
               } else {
                  String s = p_145443_.asString("");
                  Component component = null;
                  if (!"null".equals(s) && !StringUtils.isEmpty(s)) {
                     if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"' || s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') {
                        try {
                           component = GsonHelper.m_13798_(BlockEntitySignTextStrictJsonFix.f_14861_, s, Component.class, true);
                           if (component == null) {
                              component = TextComponent.f_131282_;
                           }
                        } catch (JsonParseException jsonparseexception2) {
                        }

                        if (component == null) {
                           try {
                              component = Component.Serializer.m_130701_(s);
                           } catch (JsonParseException jsonparseexception1) {
                           }
                        }

                        if (component == null) {
                           try {
                              component = Component.Serializer.m_130714_(s);
                           } catch (JsonParseException jsonparseexception) {
                           }
                        }

                        if (component == null) {
                           component = new TextComponent(s);
                        }
                     } else {
                        component = new TextComponent(s);
                     }
                  } else {
                     component = TextComponent.f_131282_;
                  }

                  return p_145443_.createString(Component.Serializer.m_130703_(component));
               }
            });
         }).map(p_16172_::createList).result(), p_16172_.emptyList());
      });
   }

   public TypeRewriteRule makeRule() {
      Type<?> type = this.getInputSchema().getType(References.f_16782_);
      OpticFinder<?> opticfinder = type.findField("tag");
      return this.fixTypeEverywhereTyped("ItemWrittenBookPagesStrictJsonFix", type, (p_16168_) -> {
         return p_16168_.updateTyped(opticfinder, (p_145439_) -> {
            return p_145439_.update(DSL.remainderFinder(), this::m_16171_);
         });
      });
   }
}