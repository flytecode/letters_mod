package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.phys.AABB;

public interface LevelEntityGetter<T extends EntityAccess> {
   @Nullable
   T m_142597_(int p_156931_);

   @Nullable
   T m_142694_(UUID p_156939_);

   Iterable<T> m_142273_();

   <U extends T> void m_142690_(EntityTypeTest<T, U> p_156935_, Consumer<U> p_156936_);

   void m_142232_(AABB p_156937_, Consumer<T> p_156938_);

   <U extends T> void m_142137_(EntityTypeTest<T, U> p_156932_, AABB p_156933_, Consumer<U> p_156934_);
}