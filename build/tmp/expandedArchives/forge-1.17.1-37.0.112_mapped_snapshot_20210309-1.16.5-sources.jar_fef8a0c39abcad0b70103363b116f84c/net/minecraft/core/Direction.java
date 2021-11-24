package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;

public enum Direction implements StringRepresentable {
   DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, new Vec3i(0, -1, 0)),
   UP(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, new Vec3i(0, 1, 0)),
   NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, new Vec3i(0, 0, -1)),
   SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, new Vec3i(0, 0, 1)),
   WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, new Vec3i(-1, 0, 0)),
   EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, new Vec3i(1, 0, 0));

   public static final Codec<Direction> f_175356_ = StringRepresentable.m_14350_(Direction::values, Direction::m_122402_);
   private final int f_122339_;
   private final int f_122340_;
   private final int f_122341_;
   private final String f_122342_;
   private final Direction.Axis f_122343_;
   private final Direction.AxisDirection f_122344_;
   private final Vec3i f_122345_;
   private static final Direction[] f_122346_ = values();
   private static final Map<String, Direction> f_122347_ = Arrays.stream(f_122346_).collect(Collectors.toMap(Direction::m_122433_, (p_122426_) -> {
      return p_122426_;
   }));
   private static final Direction[] f_122348_ = Arrays.stream(f_122346_).sorted(Comparator.comparingInt((p_122423_) -> {
      return p_122423_.f_122339_;
   })).toArray((p_122418_) -> {
      return new Direction[p_122418_];
   });
   private static final Direction[] f_122349_ = Arrays.stream(f_122346_).filter((p_122420_) -> {
      return p_122420_.m_122434_().m_122479_();
   }).sorted(Comparator.comparingInt((p_122415_) -> {
      return p_122415_.f_122341_;
   })).toArray((p_122413_) -> {
      return new Direction[p_122413_];
   });
   private static final Long2ObjectMap<Direction> f_122350_ = Arrays.stream(f_122346_).collect(Collectors.toMap((p_122410_) -> {
      return (new BlockPos(p_122410_.m_122436_())).m_121878_();
   }, (p_122394_) -> {
      return p_122394_;
   }, (p_122396_, p_122397_) -> {
      throw new IllegalArgumentException("Duplicate keys");
   }, Long2ObjectOpenHashMap::new));

   private Direction(int p_122356_, int p_122357_, int p_122358_, String p_122359_, Direction.AxisDirection p_122360_, Direction.Axis p_122361_, Vec3i p_122362_) {
      this.f_122339_ = p_122356_;
      this.f_122341_ = p_122358_;
      this.f_122340_ = p_122357_;
      this.f_122342_ = p_122359_;
      this.f_122343_ = p_122361_;
      this.f_122344_ = p_122360_;
      this.f_122345_ = p_122362_;
   }

   public static Direction[] m_122382_(Entity p_122383_) {
      float f = p_122383_.m_5686_(1.0F) * ((float)Math.PI / 180F);
      float f1 = -p_122383_.m_5675_(1.0F) * ((float)Math.PI / 180F);
      float f2 = Mth.m_14031_(f);
      float f3 = Mth.m_14089_(f);
      float f4 = Mth.m_14031_(f1);
      float f5 = Mth.m_14089_(f1);
      boolean flag = f4 > 0.0F;
      boolean flag1 = f2 < 0.0F;
      boolean flag2 = f5 > 0.0F;
      float f6 = flag ? f4 : -f4;
      float f7 = flag1 ? -f2 : f2;
      float f8 = flag2 ? f5 : -f5;
      float f9 = f6 * f3;
      float f10 = f8 * f3;
      Direction direction = flag ? EAST : WEST;
      Direction direction1 = flag1 ? UP : DOWN;
      Direction direction2 = flag2 ? SOUTH : NORTH;
      if (f6 > f8) {
         if (f7 > f9) {
            return m_122398_(direction1, direction, direction2);
         } else {
            return f10 > f7 ? m_122398_(direction, direction2, direction1) : m_122398_(direction, direction1, direction2);
         }
      } else if (f7 > f10) {
         return m_122398_(direction1, direction2, direction);
      } else {
         return f9 > f7 ? m_122398_(direction2, direction, direction1) : m_122398_(direction2, direction1, direction);
      }
   }

   private static Direction[] m_122398_(Direction p_122399_, Direction p_122400_, Direction p_122401_) {
      return new Direction[]{p_122399_, p_122400_, p_122401_, p_122401_.m_122424_(), p_122400_.m_122424_(), p_122399_.m_122424_()};
   }

   public static Direction m_122384_(Matrix4f p_122385_, Direction p_122386_) {
      Vec3i vec3i = p_122386_.m_122436_();
      Vector4f vector4f = new Vector4f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_(), 0.0F);
      vector4f.m_123607_(p_122385_);
      return m_122372_(vector4f.m_123601_(), vector4f.m_123615_(), vector4f.m_123616_());
   }

   public Quaternion m_122406_() {
      Quaternion quaternion = Vector3f.f_122223_.m_122240_(90.0F);
      switch(this) {
      case DOWN:
         return Vector3f.f_122223_.m_122240_(180.0F);
      case UP:
         return Quaternion.f_80118_.m_80161_();
      case NORTH:
         quaternion.m_80148_(Vector3f.f_122227_.m_122240_(180.0F));
         return quaternion;
      case SOUTH:
         return quaternion;
      case WEST:
         quaternion.m_80148_(Vector3f.f_122227_.m_122240_(90.0F));
         return quaternion;
      case EAST:
      default:
         quaternion.m_80148_(Vector3f.f_122227_.m_122240_(-90.0F));
         return quaternion;
      }
   }

   public int m_122411_() {
      return this.f_122339_;
   }

   public int m_122416_() {
      return this.f_122341_;
   }

   public Direction.AxisDirection m_122421_() {
      return this.f_122344_;
   }

   public static Direction m_175357_(Entity p_175358_, Direction.Axis p_175359_) {
      switch(p_175359_) {
      case X:
         return EAST.m_122370_(p_175358_.m_5675_(1.0F)) ? EAST : WEST;
      case Z:
         return SOUTH.m_122370_(p_175358_.m_5675_(1.0F)) ? SOUTH : NORTH;
      case Y:
      default:
         return p_175358_.m_5686_(1.0F) < 0.0F ? UP : DOWN;
      }
   }

   public Direction m_122424_() {
      return m_122376_(this.f_122340_);
   }

   public Direction m_175362_(Direction.Axis p_175363_) {
      switch(p_175363_) {
      case X:
         if (this != WEST && this != EAST) {
            return this.m_175366_();
         }

         return this;
      case Z:
         if (this != NORTH && this != SOUTH) {
            return this.m_175368_();
         }

         return this;
      case Y:
         if (this != UP && this != DOWN) {
            return this.m_122427_();
         }

         return this;
      default:
         throw new IllegalStateException("Unable to get CW facing for axis " + p_175363_);
      }
   }

   public Direction m_175364_(Direction.Axis p_175365_) {
      switch(p_175365_) {
      case X:
         if (this != WEST && this != EAST) {
            return this.m_175367_();
         }

         return this;
      case Z:
         if (this != NORTH && this != SOUTH) {
            return this.m_175369_();
         }

         return this;
      case Y:
         if (this != UP && this != DOWN) {
            return this.m_122428_();
         }

         return this;
      default:
         throw new IllegalStateException("Unable to get CW facing for axis " + p_175365_);
      }
   }

   public Direction m_122427_() {
      switch(this) {
      case NORTH:
         return EAST;
      case SOUTH:
         return WEST;
      case WEST:
         return NORTH;
      case EAST:
         return SOUTH;
      default:
         throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   private Direction m_175366_() {
      switch(this) {
      case DOWN:
         return SOUTH;
      case UP:
         return NORTH;
      case NORTH:
         return DOWN;
      case SOUTH:
         return UP;
      default:
         throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      }
   }

   private Direction m_175367_() {
      switch(this) {
      case DOWN:
         return NORTH;
      case UP:
         return SOUTH;
      case NORTH:
         return UP;
      case SOUTH:
         return DOWN;
      default:
         throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      }
   }

   private Direction m_175368_() {
      switch(this) {
      case DOWN:
         return WEST;
      case UP:
         return EAST;
      case NORTH:
      case SOUTH:
      default:
         throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
      case WEST:
         return UP;
      case EAST:
         return DOWN;
      }
   }

   private Direction m_175369_() {
      switch(this) {
      case DOWN:
         return EAST;
      case UP:
         return WEST;
      case NORTH:
      case SOUTH:
      default:
         throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
      case WEST:
         return DOWN;
      case EAST:
         return UP;
      }
   }

   public Direction m_122428_() {
      switch(this) {
      case NORTH:
         return WEST;
      case SOUTH:
         return EAST;
      case WEST:
         return SOUTH;
      case EAST:
         return NORTH;
      default:
         throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int m_122429_() {
      return this.f_122345_.m_123341_();
   }

   public int m_122430_() {
      return this.f_122345_.m_123342_();
   }

   public int m_122431_() {
      return this.f_122345_.m_123343_();
   }

   public Vector3f m_122432_() {
      return new Vector3f((float)this.m_122429_(), (float)this.m_122430_(), (float)this.m_122431_());
   }

   public String m_122433_() {
      return this.f_122342_;
   }

   public Direction.Axis m_122434_() {
      return this.f_122343_;
   }

   @Nullable
   public static Direction m_122402_(@Nullable String p_122403_) {
      return p_122403_ == null ? null : f_122347_.get(p_122403_.toLowerCase(Locale.ROOT));
   }

   public static Direction m_122376_(int p_122377_) {
      return f_122348_[Mth.m_14040_(p_122377_ % f_122348_.length)];
   }

   public static Direction m_122407_(int p_122408_) {
      return f_122349_[Mth.m_14040_(p_122408_ % f_122349_.length)];
   }

   @Nullable
   public static Direction m_175360_(BlockPos p_175361_) {
      return f_122350_.get(p_175361_.m_121878_());
   }

   @Nullable
   public static Direction m_122378_(int p_122379_, int p_122380_, int p_122381_) {
      return f_122350_.get(BlockPos.m_121882_(p_122379_, p_122380_, p_122381_));
   }

   public static Direction m_122364_(double p_122365_) {
      return m_122407_(Mth.m_14107_(p_122365_ / 90.0D + 0.5D) & 3);
   }

   public static Direction m_122387_(Direction.Axis p_122388_, Direction.AxisDirection p_122389_) {
      switch(p_122388_) {
      case X:
         return p_122389_ == Direction.AxisDirection.POSITIVE ? EAST : WEST;
      case Z:
      default:
         return p_122389_ == Direction.AxisDirection.POSITIVE ? SOUTH : NORTH;
      case Y:
         return p_122389_ == Direction.AxisDirection.POSITIVE ? UP : DOWN;
      }
   }

   public float m_122435_() {
      return (float)((this.f_122341_ & 3) * 90);
   }

   public static Direction m_122404_(Random p_122405_) {
      return Util.m_137545_(f_122346_, p_122405_);
   }

   public static Direction m_122366_(double p_122367_, double p_122368_, double p_122369_) {
      return m_122372_((float)p_122367_, (float)p_122368_, (float)p_122369_);
   }

   public static Direction m_122372_(float p_122373_, float p_122374_, float p_122375_) {
      Direction direction = NORTH;
      float f = Float.MIN_VALUE;

      for(Direction direction1 : f_122346_) {
         float f1 = p_122373_ * (float)direction1.f_122345_.m_123341_() + p_122374_ * (float)direction1.f_122345_.m_123342_() + p_122375_ * (float)direction1.f_122345_.m_123343_();
         if (f1 > f) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   public String toString() {
      return this.f_122342_;
   }

   public String m_7912_() {
      return this.f_122342_;
   }

   public static Direction m_122390_(Direction.AxisDirection p_122391_, Direction.Axis p_122392_) {
      for(Direction direction : f_122346_) {
         if (direction.m_122421_() == p_122391_ && direction.m_122434_() == p_122392_) {
            return direction;
         }
      }

      throw new IllegalArgumentException("No such direction: " + p_122391_ + " " + p_122392_);
   }

   public Vec3i m_122436_() {
      return this.f_122345_;
   }

   public boolean m_122370_(float p_122371_) {
      float f = p_122371_ * ((float)Math.PI / 180F);
      float f1 = -Mth.m_14031_(f);
      float f2 = Mth.m_14089_(f);
      return (float)this.f_122345_.m_123341_() * f1 + (float)this.f_122345_.m_123343_() * f2 > 0.0F;
   }

   public static enum Axis implements StringRepresentable, Predicate<Direction> {
      X("x") {
         public int m_7863_(int p_122496_, int p_122497_, int p_122498_) {
            return p_122496_;
         }

         public double m_6150_(double p_122492_, double p_122493_, double p_122494_) {
            return p_122492_;
         }
      },
      Y("y") {
         public int m_7863_(int p_122510_, int p_122511_, int p_122512_) {
            return p_122511_;
         }

         public double m_6150_(double p_122506_, double p_122507_, double p_122508_) {
            return p_122507_;
         }
      },
      Z("z") {
         public int m_7863_(int p_122524_, int p_122525_, int p_122526_) {
            return p_122526_;
         }

         public double m_6150_(double p_122520_, double p_122521_, double p_122522_) {
            return p_122522_;
         }
      };

      public static final Direction.Axis[] f_122448_ = values();
      public static final Codec<Direction.Axis> f_122447_ = StringRepresentable.m_14350_(Direction.Axis::values, Direction.Axis::m_122473_);
      private static final Map<String, Direction.Axis> f_122449_ = Arrays.stream(f_122448_).collect(Collectors.toMap(Direction.Axis::m_122477_, (p_122470_) -> {
         return p_122470_;
      }));
      private final String f_122450_;

      Axis(String p_122456_) {
         this.f_122450_ = p_122456_;
      }

      @Nullable
      public static Direction.Axis m_122473_(String p_122474_) {
         return f_122449_.get(p_122474_.toLowerCase(Locale.ROOT));
      }

      public String m_122477_() {
         return this.f_122450_;
      }

      public boolean m_122478_() {
         return this == Y;
      }

      public boolean m_122479_() {
         return this == X || this == Z;
      }

      public String toString() {
         return this.f_122450_;
      }

      public static Direction.Axis m_122475_(Random p_122476_) {
         return Util.m_137545_(f_122448_, p_122476_);
      }

      public boolean test(@Nullable Direction p_122472_) {
         return p_122472_ != null && p_122472_.m_122434_() == this;
      }

      public Direction.Plane m_122480_() {
         switch(this) {
         case X:
         case Z:
            return Direction.Plane.HORIZONTAL;
         case Y:
            return Direction.Plane.VERTICAL;
         default:
            throw new Error("Someone's been tampering with the universe!");
         }
      }

      public String m_7912_() {
         return this.f_122450_;
      }

      public abstract int m_7863_(int p_122466_, int p_122467_, int p_122468_);

      public abstract double m_6150_(double p_122463_, double p_122464_, double p_122465_);
   }

   public static enum AxisDirection {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int f_122531_;
      private final String f_122532_;

      private AxisDirection(int p_122538_, String p_122539_) {
         this.f_122531_ = p_122538_;
         this.f_122532_ = p_122539_;
      }

      public int m_122540_() {
         return this.f_122531_;
      }

      public String m_175372_() {
         return this.f_122532_;
      }

      public String toString() {
         return this.f_122532_;
      }

      public Direction.AxisDirection m_122541_() {
         return this == POSITIVE ? NEGATIVE : POSITIVE;
      }
   }

   public static enum Plane implements Iterable<Direction>, Predicate<Direction> {
      HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}),
      VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Direction.Axis[]{Direction.Axis.Y});

      private final Direction[] f_122548_;
      private final Direction.Axis[] f_122549_;

      private Plane(Direction[] p_122555_, Direction.Axis[] p_122556_) {
         this.f_122548_ = p_122555_;
         this.f_122549_ = p_122556_;
      }

      public Direction m_122560_(Random p_122561_) {
         return Util.m_137545_(this.f_122548_, p_122561_);
      }

      public Direction.Axis m_122562_(Random p_122563_) {
         return Util.m_137545_(this.f_122549_, p_122563_);
      }

      public boolean test(@Nullable Direction p_122559_) {
         return p_122559_ != null && p_122559_.m_122434_().m_122480_() == this;
      }

      public Iterator<Direction> iterator() {
         return Iterators.forArray(this.f_122548_);
      }

      public Stream<Direction> m_122557_() {
         return Arrays.stream(this.f_122548_);
      }
   }
}