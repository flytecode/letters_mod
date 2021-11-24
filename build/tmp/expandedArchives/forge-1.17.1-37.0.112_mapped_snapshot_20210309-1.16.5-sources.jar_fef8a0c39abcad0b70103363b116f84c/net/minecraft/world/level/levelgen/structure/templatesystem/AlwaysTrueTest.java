package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;

public class AlwaysTrueTest extends RuleTest {
   public static final Codec<AlwaysTrueTest> f_73953_;
   public static final AlwaysTrueTest f_73954_ = new AlwaysTrueTest();

   private AlwaysTrueTest() {
   }

   public boolean m_7715_(BlockState p_73959_, Random p_73960_) {
      return true;
   }

   protected RuleTestType<?> m_7319_() {
      return RuleTestType.f_74312_;
   }

   static {
      f_73953_ = Codec.unit(() -> {
         return f_73954_;
      });
   }
}