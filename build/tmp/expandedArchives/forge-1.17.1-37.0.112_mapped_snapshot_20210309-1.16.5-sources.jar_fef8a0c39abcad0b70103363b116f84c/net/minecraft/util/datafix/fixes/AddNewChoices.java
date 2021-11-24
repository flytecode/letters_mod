package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;

public class AddNewChoices extends DataFix {
   private final String f_14625_;
   private final TypeReference f_14626_;

   public AddNewChoices(Schema p_14628_, String p_14629_, TypeReference p_14630_) {
      super(p_14628_, true);
      this.f_14625_ = p_14629_;
      this.f_14626_ = p_14630_;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<?> taggedchoicetype = this.getInputSchema().findChoiceType(this.f_14626_);
      TaggedChoiceType<?> taggedchoicetype1 = this.getOutputSchema().findChoiceType(this.f_14626_);
      return this.m_14637_(this.f_14625_, taggedchoicetype, taggedchoicetype1);
   }

   protected final <K> TypeRewriteRule m_14637_(String p_14638_, TaggedChoiceType<K> p_14639_, TaggedChoiceType<?> p_14640_) {
      if (p_14639_.getKeyType() != p_14640_.getKeyType()) {
         throw new IllegalStateException("Could not inject: key type is not the same");
      } else {
         return this.fixTypeEverywhere(p_14638_, p_14639_, (TaggedChoiceType<K>)p_14640_, (p_14636_) -> {
            return (p_145061_) -> {
               if (!((TaggedChoiceType<K>)p_14640_).hasType(p_145061_.getFirst())) {
                  throw new IllegalArgumentException(String.format("Unknown type %s in %s ", p_145061_.getFirst(), this.f_14626_));
               } else {
                  return p_145061_;
               }
            };
         });
      }
   }
}