package net.minecraft.world.level.entity;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.ClassInstanceMultiMap;
import net.minecraft.util.VisibleForDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntitySection<T> {
   protected static final Logger f_156826_ = LogManager.getLogger();
   private final ClassInstanceMultiMap<T> f_156827_;
   private Visibility f_156828_;

   public EntitySection(Class<T> p_156831_, Visibility p_156832_) {
      this.f_156828_ = p_156832_;
      this.f_156827_ = new ClassInstanceMultiMap<>(p_156831_);
   }

   public void m_156840_(T p_156841_) {
      this.f_156827_.add(p_156841_);
   }

   public boolean m_156846_(T p_156847_) {
      return this.f_156827_.remove(p_156847_);
   }

   public void m_156842_(Predicate<? super T> p_156843_, Consumer<T> p_156844_) {
      for(T t : this.f_156827_) {
         if (p_156843_.test(t)) {
            p_156844_.accept(t);
         }
      }

   }

   public <U extends T> void m_156834_(EntityTypeTest<T, U> p_156835_, Predicate<? super U> p_156836_, Consumer<? super U> p_156837_) {
      for(T t : this.f_156827_.m_13533_(p_156835_.m_142225_())) {
         U u = (U)p_156835_.m_141992_(t);
         if (u != null && p_156836_.test(u)) {
            p_156837_.accept(u);
         }
      }

   }

   public boolean m_156833_() {
      return this.f_156827_.isEmpty();
   }

   public Stream<T> m_156845_() {
      return this.f_156827_.stream();
   }

   public Visibility m_156848_() {
      return this.f_156828_;
   }

   public Visibility m_156838_(Visibility p_156839_) {
      Visibility visibility = this.f_156828_;
      this.f_156828_ = p_156839_;
      return visibility;
   }

   @VisibleForDebug
   public int m_156849_() {
      return this.f_156827_.size();
   }
}