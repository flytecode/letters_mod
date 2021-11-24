package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;

public class GameTestRegistry {
   private static final Collection<TestFunction> f_177495_ = Lists.newArrayList();
   private static final Set<String> f_177496_ = Sets.newHashSet();
   private static final Map<String, Consumer<ServerLevel>> f_177497_ = Maps.newHashMap();
   private static final Map<String, Consumer<ServerLevel>> f_177498_ = Maps.newHashMap();
   private static final Collection<TestFunction> f_177499_ = Sets.newHashSet();

   public static void m_177501_(Class<?> p_177502_) {
      Arrays.stream(p_177502_.getDeclaredMethods()).forEach(GameTestRegistry::m_177503_);
   }

   public static void m_177503_(Method p_177504_) {
      String s = p_177504_.getDeclaringClass().getSimpleName();
      GameTest gametest = p_177504_.getAnnotation(GameTest.class);
      if (gametest != null) {
         f_177495_.add(m_177515_(p_177504_));
         f_177496_.add(s);
      }

      GameTestGenerator gametestgenerator = p_177504_.getAnnotation(GameTestGenerator.class);
      if (gametestgenerator != null) {
         f_177495_.addAll(m_177513_(p_177504_));
         f_177496_.add(s);
      }

      m_177505_(p_177504_, BeforeBatch.class, BeforeBatch::m_177037_, f_177497_);
      m_177505_(p_177504_, AfterBatch.class, AfterBatch::m_177036_, f_177498_);
   }

   private static <T extends Annotation> void m_177505_(Method p_177506_, Class<T> p_177507_, Function<T, String> p_177508_, Map<String, Consumer<ServerLevel>> p_177509_) {
      T t = p_177506_.getAnnotation(p_177507_);
      if (t != null) {
         String s = p_177508_.apply(t);
         Consumer<ServerLevel> consumer = p_177509_.putIfAbsent(s, (Consumer<ServerLevel>)m_177519_(p_177506_));
         if (consumer != null) {
            throw new RuntimeException("Hey, there should only be one " + p_177507_ + " method per batch. Batch '" + s + "' has more than one!");
         }
      }

   }

   public static Collection<TestFunction> m_127659_(String p_127660_) {
      return f_177495_.stream().filter((p_127674_) -> {
         return m_127666_(p_127674_, p_127660_);
      }).collect(Collectors.toList());
   }

   public static Collection<TestFunction> m_127658_() {
      return f_177495_;
   }

   public static Collection<String> m_127669_() {
      return f_177496_;
   }

   public static boolean m_127670_(String p_127671_) {
      return f_177496_.contains(p_127671_);
   }

   @Nullable
   public static Consumer<ServerLevel> m_127676_(String p_127677_) {
      return f_177497_.get(p_127677_);
   }

   @Nullable
   public static Consumer<ServerLevel> m_177517_(String p_177518_) {
      return f_177498_.get(p_177518_);
   }

   public static Optional<TestFunction> m_127679_(String p_127680_) {
      return m_127658_().stream().filter((p_127663_) -> {
         return p_127663_.m_128075_().equalsIgnoreCase(p_127680_);
      }).findFirst();
   }

   public static TestFunction m_127681_(String p_127682_) {
      Optional<TestFunction> optional = m_127679_(p_127682_);
      if (!optional.isPresent()) {
         throw new IllegalArgumentException("Can't find the test function for " + p_127682_);
      } else {
         return optional.get();
      }
   }

   private static Collection<TestFunction> m_177513_(Method p_177514_) {
      try {
         Object object = p_177514_.getDeclaringClass().newInstance();
         return (Collection)p_177514_.invoke(object);
      } catch (ReflectiveOperationException reflectiveoperationexception) {
         throw new RuntimeException(reflectiveoperationexception);
      }
   }

   private static TestFunction m_177515_(Method p_177516_) {
      GameTest gametest = p_177516_.getAnnotation(GameTest.class);
      String s = p_177516_.getDeclaringClass().getSimpleName();
      String s1 = s.toLowerCase();
      String s2 = s1 + "." + p_177516_.getName().toLowerCase();
      String s3 = gametest.m_177046_().isEmpty() ? s2 : s1 + "." + gametest.m_177046_();
      String s4 = gametest.m_177043_();
      Rotation rotation = StructureUtils.m_127835_(gametest.m_177044_());
      return new TestFunction(s4, s2, s3, rotation, gametest.m_177042_(), gametest.m_177047_(), gametest.m_177045_(), gametest.m_177049_(), gametest.m_177048_(), (Consumer<net.minecraft.gametest.framework.GameTestHelper>)m_177519_(p_177516_));
   }

   private static Consumer<?> m_177519_(Method p_177520_) {
      return (p_177512_) -> {
         try {
            Object object = p_177520_.getDeclaringClass().newInstance();
            p_177520_.invoke(object, p_177512_);
         } catch (InvocationTargetException invocationtargetexception) {
            if (invocationtargetexception.getCause() instanceof RuntimeException) {
               throw (RuntimeException)invocationtargetexception.getCause();
            } else {
               throw new RuntimeException(invocationtargetexception.getCause());
            }
         } catch (ReflectiveOperationException reflectiveoperationexception) {
            throw new RuntimeException(reflectiveoperationexception);
         }
      };
   }

   private static boolean m_127666_(TestFunction p_127667_, String p_127668_) {
      return p_127667_.m_128075_().toLowerCase().startsWith(p_127668_.toLowerCase() + ".");
   }

   public static Collection<TestFunction> m_127675_() {
      return f_177499_;
   }

   public static void m_127664_(TestFunction p_127665_) {
      f_177499_.add(p_127665_);
   }

   public static void m_127678_() {
      f_177499_.clear();
   }
}