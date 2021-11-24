package net.minecraft.world.level.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityLookup<T extends EntityAccess> {
   private static final Logger f_156806_ = LogManager.getLogger();
   private final Int2ObjectMap<T> f_156807_ = new Int2ObjectLinkedOpenHashMap<>();
   private final Map<UUID, T> f_156808_ = Maps.newHashMap();

   public <U extends T> void m_156816_(EntityTypeTest<T, U> p_156817_, Consumer<U> p_156818_) {
      for(T t : this.f_156807_.values()) {
         U u = (U)((EntityAccess)p_156817_.m_141992_(t));
         if (u != null) {
            p_156818_.accept(u);
         }
      }

   }

   public Iterable<T> m_156811_() {
      return Iterables.unmodifiableIterable(this.f_156807_.values());
   }

   public void m_156814_(T p_156815_) {
      UUID uuid = p_156815_.m_142081_();
      if (this.f_156808_.containsKey(uuid)) {
         f_156806_.warn("Duplicate entity UUID {}: {}", uuid, p_156815_);
      } else {
         this.f_156808_.put(uuid, p_156815_);
         this.f_156807_.put(p_156815_.m_142049_(), p_156815_);
      }
   }

   public void m_156822_(T p_156823_) {
      this.f_156808_.remove(p_156823_.m_142081_());
      this.f_156807_.remove(p_156823_.m_142049_());
   }

   @Nullable
   public T m_156812_(int p_156813_) {
      return this.f_156807_.get(p_156813_);
   }

   @Nullable
   public T m_156819_(UUID p_156820_) {
      return this.f_156808_.get(p_156820_);
   }

   public int m_156821_() {
      return this.f_156808_.size();
   }
}