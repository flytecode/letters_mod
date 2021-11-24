package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public class AxisAlignedLinearPosTest extends PosRuleTest {
   public static final Codec<AxisAlignedLinearPosTest> f_73962_ = RecordCodecBuilder.create((p_73977_) -> {
      return p_73977_.group(Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter((p_163719_) -> {
         return p_163719_.f_73963_;
      }), Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter((p_163717_) -> {
         return p_163717_.f_73964_;
      }), Codec.INT.fieldOf("min_dist").orElse(0).forGetter((p_163715_) -> {
         return p_163715_.f_73965_;
      }), Codec.INT.fieldOf("max_dist").orElse(0).forGetter((p_163713_) -> {
         return p_163713_.f_73966_;
      }), Direction.Axis.f_122447_.fieldOf("axis").orElse(Direction.Axis.Y).forGetter((p_163711_) -> {
         return p_163711_.f_73967_;
      })).apply(p_73977_, AxisAlignedLinearPosTest::new);
   });
   private final float f_73963_;
   private final float f_73964_;
   private final int f_73965_;
   private final int f_73966_;
   private final Direction.Axis f_73967_;

   public AxisAlignedLinearPosTest(float p_73970_, float p_73971_, int p_73972_, int p_73973_, Direction.Axis p_73974_) {
      if (p_73972_ >= p_73973_) {
         throw new IllegalArgumentException("Invalid range: [" + p_73972_ + "," + p_73973_ + "]");
      } else {
         this.f_73963_ = p_73970_;
         this.f_73964_ = p_73971_;
         this.f_73965_ = p_73972_;
         this.f_73966_ = p_73973_;
         this.f_73967_ = p_73974_;
      }
   }

   public boolean m_7124_(BlockPos p_73981_, BlockPos p_73982_, BlockPos p_73983_, Random p_73984_) {
      Direction direction = Direction.m_122390_(Direction.AxisDirection.POSITIVE, this.f_73967_);
      float f = (float)Math.abs((p_73982_.m_123341_() - p_73983_.m_123341_()) * direction.m_122429_());
      float f1 = (float)Math.abs((p_73982_.m_123342_() - p_73983_.m_123342_()) * direction.m_122430_());
      float f2 = (float)Math.abs((p_73982_.m_123343_() - p_73983_.m_123343_()) * direction.m_122431_());
      int i = (int)(f + f1 + f2);
      float f3 = p_73984_.nextFloat();
      return (double)f3 <= Mth.m_14085_((double)this.f_73963_, (double)this.f_73964_, Mth.m_14112_((double)i, (double)this.f_73965_, (double)this.f_73966_));
   }

   protected PosRuleTestType<?> m_6158_() {
      return PosRuleTestType.f_74207_;
   }
}