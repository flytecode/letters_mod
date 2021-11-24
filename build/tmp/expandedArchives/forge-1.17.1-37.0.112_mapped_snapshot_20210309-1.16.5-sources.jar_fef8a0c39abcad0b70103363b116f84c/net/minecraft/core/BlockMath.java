package net.minecraft.core;

import com.google.common.collect.Maps;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockMath {
   private static final Logger f_121840_ = LogManager.getLogger();
   public static final Map<Direction, Transformation> f_175256_ = Util.m_137469_(Maps.newEnumMap(Direction.class), (p_121851_) -> {
      p_121851_.put(Direction.SOUTH, Transformation.m_121093_());
      p_121851_.put(Direction.EAST, new Transformation((Vector3f)null, Vector3f.f_122225_.m_122240_(90.0F), (Vector3f)null, (Quaternion)null));
      p_121851_.put(Direction.WEST, new Transformation((Vector3f)null, Vector3f.f_122225_.m_122240_(-90.0F), (Vector3f)null, (Quaternion)null));
      p_121851_.put(Direction.NORTH, new Transformation((Vector3f)null, Vector3f.f_122225_.m_122240_(180.0F), (Vector3f)null, (Quaternion)null));
      p_121851_.put(Direction.UP, new Transformation((Vector3f)null, Vector3f.f_122223_.m_122240_(-90.0F), (Vector3f)null, (Quaternion)null));
      p_121851_.put(Direction.DOWN, new Transformation((Vector3f)null, Vector3f.f_122223_.m_122240_(90.0F), (Vector3f)null, (Quaternion)null));
   });
   public static final Map<Direction, Transformation> f_175257_ = Util.m_137469_(Maps.newEnumMap(Direction.class), (p_121849_) -> {
      for(Direction direction : Direction.values()) {
         p_121849_.put(direction, f_175256_.get(direction).m_121103_());
      }

   });

   public static Transformation m_121842_(Transformation p_121843_) {
      Matrix4f matrix4f = Matrix4f.m_27653_(0.5F, 0.5F, 0.5F);
      matrix4f.m_27644_(p_121843_.m_121104_());
      matrix4f.m_27644_(Matrix4f.m_27653_(-0.5F, -0.5F, -0.5F));
      return new Transformation(matrix4f);
   }

   public static Transformation m_175259_(Transformation p_175260_) {
      Matrix4f matrix4f = Matrix4f.m_27653_(-0.5F, -0.5F, -0.5F);
      matrix4f.m_27644_(p_175260_.m_121104_());
      matrix4f.m_27644_(Matrix4f.m_27653_(0.5F, 0.5F, 0.5F));
      return new Transformation(matrix4f);
   }

   public static Transformation m_121844_(Transformation p_121845_, Direction p_121846_, Supplier<String> p_121847_) {
      Direction direction = Direction.m_122384_(p_121845_.m_121104_(), p_121846_);
      Transformation transformation = p_121845_.m_121103_();
      if (transformation == null) {
         f_121840_.warn(p_121847_.get());
         return new Transformation((Vector3f)null, (Quaternion)null, new Vector3f(0.0F, 0.0F, 0.0F), (Quaternion)null);
      } else {
         Transformation transformation1 = f_175257_.get(p_121846_).m_121096_(transformation).m_121096_(f_175256_.get(direction));
         return m_121842_(transformation1);
      }
   }
}