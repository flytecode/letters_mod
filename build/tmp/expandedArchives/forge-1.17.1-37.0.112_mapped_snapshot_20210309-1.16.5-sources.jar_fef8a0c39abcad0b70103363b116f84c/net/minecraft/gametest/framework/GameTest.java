package net.minecraft.gametest.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GameTest {
   int m_177042_() default 100;

   String m_177043_() default "defaultBatch";

   int m_177044_() default 0;

   boolean m_177045_() default true;

   String m_177046_() default "";

   long m_177047_() default 0L;

   int m_177048_() default 1;

   int m_177049_() default 1;
}