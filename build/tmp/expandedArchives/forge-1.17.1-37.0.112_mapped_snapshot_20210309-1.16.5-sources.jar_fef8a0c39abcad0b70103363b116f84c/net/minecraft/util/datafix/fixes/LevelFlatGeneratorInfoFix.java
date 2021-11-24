package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.math.NumberUtils;

public class LevelFlatGeneratorInfoFix extends DataFix {
   private static final String f_145489_ = "generatorOptions";
   @VisibleForTesting
   static final String f_145488_ = "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
   private static final Splitter f_16337_ = Splitter.on(';').limit(5);
   private static final Splitter f_16338_ = Splitter.on(',');
   private static final Splitter f_16339_ = Splitter.on('x').limit(2);
   private static final Splitter f_16340_ = Splitter.on('*').limit(2);
   private static final Splitter f_16341_ = Splitter.on(':').limit(3);

   public LevelFlatGeneratorInfoFix(Schema p_16344_, boolean p_16345_) {
      super(p_16344_, p_16345_);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", this.getInputSchema().getType(References.f_16771_), (p_16351_) -> {
         return p_16351_.update(DSL.remainderFinder(), this::m_16352_);
      });
   }

   private Dynamic<?> m_16352_(Dynamic<?> p_16353_) {
      return p_16353_.get("generatorName").asString("").equalsIgnoreCase("flat") ? p_16353_.update("generatorOptions", (p_16357_) -> {
         return DataFixUtils.orElse(p_16357_.asString().map(this::m_16354_).map(s -> p_16357_.createString(s)).result(), p_16357_);
      }) : p_16353_;
   }

   @VisibleForTesting
   String m_16354_(String p_16355_) {
      if (p_16355_.isEmpty()) {
         return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
      } else {
         Iterator<String> iterator = f_16337_.split(p_16355_).iterator();
         String s = iterator.next();
         int i;
         String s1;
         if (iterator.hasNext()) {
            i = NumberUtils.toInt(s, 0);
            s1 = iterator.next();
         } else {
            i = 0;
            s1 = s;
         }

         if (i >= 0 && i <= 3) {
            StringBuilder stringbuilder = new StringBuilder();
            Splitter splitter = i < 3 ? f_16339_ : f_16340_;
            stringbuilder.append(StreamSupport.stream(f_16338_.split(s1).spliterator(), false).map((p_16349_) -> {
               List<String> list = splitter.splitToList(p_16349_);
               int j;
               String s2;
               if (list.size() == 2) {
                  j = NumberUtils.toInt(list.get(0));
                  s2 = list.get(1);
               } else {
                  j = 1;
                  s2 = list.get(0);
               }

               List<String> list1 = f_16341_.splitToList(s2);
               int k = list1.get(0).equals("minecraft") ? 1 : 0;
               String s3 = list1.get(k);
               int l = i == 3 ? EntityBlockStateFix.m_15365_("minecraft:" + s3) : NumberUtils.toInt(s3, 0);
               int i1 = k + 1;
               int j1 = list1.size() > i1 ? NumberUtils.toInt(list1.get(i1), 0) : 0;
               return (j == 1 ? "" : j + "*") + BlockStateData.m_14952_(l << 4 | j1).get("Name").asString("");
            }).collect(Collectors.joining(",")));

            while(iterator.hasNext()) {
               stringbuilder.append(';').append(iterator.next());
            }

            return stringbuilder.toString();
         } else {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
         }
      }
   }
}