package net.minecraft.server.packs.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;

public interface ResourceManager extends ResourceProvider {
   Set<String> m_7187_();

   boolean m_7165_(ResourceLocation p_10729_);

   List<Resource> m_7396_(ResourceLocation p_10730_) throws IOException;

   Collection<ResourceLocation> m_6540_(String p_10726_, Predicate<String> p_10727_);

   Stream<PackResources> m_7536_();

   public static enum Empty implements ResourceManager {
      INSTANCE;

      public Set<String> m_7187_() {
         return ImmutableSet.of();
      }

      public Resource m_142591_(ResourceLocation p_10742_) throws IOException {
         throw new FileNotFoundException(p_10742_.toString());
      }

      public boolean m_7165_(ResourceLocation p_10745_) {
         return false;
      }

      public List<Resource> m_7396_(ResourceLocation p_10747_) {
         return ImmutableList.of();
      }

      public Collection<ResourceLocation> m_6540_(String p_10739_, Predicate<String> p_10740_) {
         return ImmutableSet.of();
      }

      public Stream<PackResources> m_7536_() {
         return Stream.of();
      }
   }
}