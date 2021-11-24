package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;

public class PosAlwaysTrueTest extends PosRuleTest {
   public static final Codec<PosAlwaysTrueTest> f_74187_;
   public static final PosAlwaysTrueTest f_74188_ = new PosAlwaysTrueTest();

   private PosAlwaysTrueTest() {
   }

   public boolean m_7124_(BlockPos p_74193_, BlockPos p_74194_, BlockPos p_74195_, Random p_74196_) {
      return true;
   }

   protected PosRuleTestType<?> m_6158_() {
      return PosRuleTestType.f_74205_;
   }

   static {
      f_74187_ = Codec.unit(() -> {
         return f_74188_;
      });
   }
}