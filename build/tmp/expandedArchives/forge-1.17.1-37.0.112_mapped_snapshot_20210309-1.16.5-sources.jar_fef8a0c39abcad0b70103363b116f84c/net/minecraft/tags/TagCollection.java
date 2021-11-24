package net.minecraft.tags;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface TagCollection<T> {
   Map<ResourceLocation, Tag<T>> m_5643_();

   @Nullable
   default Tag<T> m_13404_(ResourceLocation p_13405_) {
      return this.m_5643_().get(p_13405_);
   }

   Tag<T> m_7689_(ResourceLocation p_13409_);

   @Nullable
   default ResourceLocation m_144406_(Tag.Named<T> p_144407_) {
      return p_144407_.m_6979_();
   }

   @Nullable
   ResourceLocation m_7473_(Tag<T> p_13393_);

   default boolean m_144423_(ResourceLocation p_144424_) {
      return this.m_5643_().containsKey(p_144424_);
   }

   default Collection<ResourceLocation> m_13406_() {
      return this.m_5643_().keySet();
   }

   default Collection<ResourceLocation> m_13394_(T p_13395_) {
      List<ResourceLocation> list = Lists.newArrayList();

      for(Entry<ResourceLocation, Tag<T>> entry : this.m_5643_().entrySet()) {
         if (entry.getValue().m_8110_(p_13395_)) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   default TagCollection.NetworkPayload m_144411_(Registry<T> p_144412_) {
      Map<ResourceLocation, Tag<T>> map = this.m_5643_();
      Map<ResourceLocation, IntList> map1 = Maps.newHashMapWithExpectedSize(map.size());
      map.forEach((p_144416_, p_144417_) -> {
         List<T> list = p_144417_.m_6497_();
         IntList intlist = new IntArrayList(list.size());

         for(T t : list) {
            intlist.add(p_144412_.m_7447_(t));
         }

         map1.put(p_144416_, intlist);
      });
      return new TagCollection.NetworkPayload(map1);
   }

   static <T> TagCollection<T> m_144408_(TagCollection.NetworkPayload p_144409_, Registry<? extends T> p_144410_) {
      Map<ResourceLocation, Tag<T>> map = Maps.newHashMapWithExpectedSize(p_144409_.f_144425_.size());
      p_144409_.f_144425_.forEach((p_144421_, p_144422_) -> {
         Builder<T> builder = ImmutableSet.builder();

         for(int i : p_144422_) {
            builder.add(p_144410_.m_7942_(i));
         }

         map.put(p_144421_, Tag.m_13300_(builder.build()));
      });
      return m_13396_(map);
   }

   static <T> TagCollection<T> m_13410_() {
      return m_13396_(ImmutableBiMap.of());
   }

   static <T> TagCollection<T> m_13396_(Map<ResourceLocation, Tag<T>> p_13397_) {
      final BiMap<ResourceLocation, Tag<T>> bimap = ImmutableBiMap.copyOf(p_13397_);
      return new TagCollection<T>() {
         private final Tag<T> f_13412_ = SetTag.m_13216_();

         public Tag<T> m_7689_(ResourceLocation p_13419_) {
            return bimap.getOrDefault(p_13419_, this.f_13412_);
         }

         @Nullable
         public ResourceLocation m_7473_(Tag<T> p_13417_) {
            return p_13417_ instanceof Tag.Named ? ((Tag.Named)p_13417_).m_6979_() : bimap.inverse().get(p_13417_);
         }

         public Map<ResourceLocation, Tag<T>> m_5643_() {
            return bimap;
         }
      };
   }

   public static class NetworkPayload {
      final Map<ResourceLocation, IntList> f_144425_;

      NetworkPayload(Map<ResourceLocation, IntList> p_144427_) {
         this.f_144425_ = p_144427_;
      }

      public void m_144428_(FriendlyByteBuf p_144429_) {
         p_144429_.m_178355_(this.f_144425_, FriendlyByteBuf::m_130085_, FriendlyByteBuf::m_178345_);
      }

      public static TagCollection.NetworkPayload m_144430_(FriendlyByteBuf p_144431_) {
         return new TagCollection.NetworkPayload(p_144431_.m_178368_(FriendlyByteBuf::m_130281_, FriendlyByteBuf::m_178338_));
      }
   }
}