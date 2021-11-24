package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

public class MultiVariantGenerator implements BlockStateGenerator {
   private final Block f_125246_;
   private final List<Variant> f_125247_;
   private final Set<Property<?>> f_125248_ = Sets.newHashSet();
   private final List<PropertyDispatch> f_125249_ = Lists.newArrayList();

   private MultiVariantGenerator(Block p_125251_, List<Variant> p_125252_) {
      this.f_125246_ = p_125251_;
      this.f_125247_ = p_125252_;
   }

   public MultiVariantGenerator m_125271_(PropertyDispatch p_125272_) {
      p_125272_.m_7336_().forEach((p_125263_) -> {
         if (this.f_125246_.m_49965_().m_61081_(p_125263_.m_61708_()) != p_125263_) {
            throw new IllegalStateException("Property " + p_125263_ + " is not defined for block " + this.f_125246_);
         } else if (!this.f_125248_.add(p_125263_)) {
            throw new IllegalStateException("Values of property " + p_125263_ + " already defined for block " + this.f_125246_);
         }
      });
      this.f_125249_.add(p_125272_);
      return this;
   }

   public JsonElement get() {
      Stream<Pair<Selector, List<Variant>>> stream = Stream.of(Pair.of(Selector.m_125485_(), this.f_125247_));

      for(PropertyDispatch propertydispatch : this.f_125249_) {
         Map<Selector, List<Variant>> map = propertydispatch.m_125293_();
         stream = stream.flatMap((p_125289_) -> {
            return map.entrySet().stream().map((p_176309_) -> {
               Selector selector = ((Selector)p_125289_.getFirst()).m_125488_(p_176309_.getKey());
               List<Variant> list = m_125277_((List)p_125289_.getSecond(), p_176309_.getValue());
               return Pair.of(selector, list);
            });
         });
      }

      Map<String, JsonElement> map1 = new TreeMap<>();
      stream.forEach((p_125285_) -> {
         map1.put(p_125285_.getFirst().m_125492_(), Variant.m_125514_(p_125285_.getSecond()));
      });
      JsonObject jsonobject = new JsonObject();
      jsonobject.add("variants", Util.m_137469_(new JsonObject(), (p_125282_) -> {
         map1.forEach(p_125282_::add);
      }));
      return jsonobject;
   }

   private static List<Variant> m_125277_(List<Variant> p_125278_, List<Variant> p_125279_) {
      Builder<Variant> builder = ImmutableList.builder();
      p_125278_.forEach((p_125276_) -> {
         p_125279_.forEach((p_176306_) -> {
            builder.add(Variant.m_125508_(p_125276_, p_176306_));
         });
      });
      return builder.build();
   }

   public Block m_6968_() {
      return this.f_125246_;
   }

   public static MultiVariantGenerator m_125254_(Block p_125255_) {
      return new MultiVariantGenerator(p_125255_, ImmutableList.of(Variant.m_125501_()));
   }

   public static MultiVariantGenerator m_125256_(Block p_125257_, Variant p_125258_) {
      return new MultiVariantGenerator(p_125257_, ImmutableList.of(p_125258_));
   }

   public static MultiVariantGenerator m_125259_(Block p_125260_, Variant... p_125261_) {
      return new MultiVariantGenerator(p_125260_, ImmutableList.copyOf(p_125261_));
   }
}