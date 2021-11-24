package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class ProcessorRule {
   public static final Codec<ProcessorRule> f_74215_ = RecordCodecBuilder.create((p_74246_) -> {
      return p_74246_.group(RuleTest.f_74307_.fieldOf("input_predicate").forGetter((p_163747_) -> {
         return p_163747_.f_74216_;
      }), RuleTest.f_74307_.fieldOf("location_predicate").forGetter((p_163745_) -> {
         return p_163745_.f_74217_;
      }), PosRuleTest.f_74198_.optionalFieldOf("position_predicate", PosAlwaysTrueTest.f_74188_).forGetter((p_163743_) -> {
         return p_163743_.f_74218_;
      }), BlockState.f_61039_.fieldOf("output_state").forGetter((p_163741_) -> {
         return p_163741_.f_74219_;
      }), CompoundTag.f_128325_.optionalFieldOf("output_nbt").forGetter((p_163739_) -> {
         return Optional.ofNullable(p_163739_.f_74220_);
      })).apply(p_74246_, ProcessorRule::new);
   });
   private final RuleTest f_74216_;
   private final RuleTest f_74217_;
   private final PosRuleTest f_74218_;
   private final BlockState f_74219_;
   @Nullable
   private final CompoundTag f_74220_;

   public ProcessorRule(RuleTest p_74223_, RuleTest p_74224_, BlockState p_74225_) {
      this(p_74223_, p_74224_, PosAlwaysTrueTest.f_74188_, p_74225_, Optional.empty());
   }

   public ProcessorRule(RuleTest p_74227_, RuleTest p_74228_, PosRuleTest p_74229_, BlockState p_74230_) {
      this(p_74227_, p_74228_, p_74229_, p_74230_, Optional.empty());
   }

   public ProcessorRule(RuleTest p_74232_, RuleTest p_74233_, PosRuleTest p_74234_, BlockState p_74235_, Optional<CompoundTag> p_74236_) {
      this.f_74216_ = p_74232_;
      this.f_74217_ = p_74233_;
      this.f_74218_ = p_74234_;
      this.f_74219_ = p_74235_;
      this.f_74220_ = p_74236_.orElse((CompoundTag)null);
   }

   public boolean m_74238_(BlockState p_74239_, BlockState p_74240_, BlockPos p_74241_, BlockPos p_74242_, BlockPos p_74243_, Random p_74244_) {
      return this.f_74216_.m_7715_(p_74239_, p_74244_) && this.f_74217_.m_7715_(p_74240_, p_74244_) && this.f_74218_.m_7124_(p_74241_, p_74242_, p_74243_, p_74244_);
   }

   public BlockState m_74237_() {
      return this.f_74219_;
   }

   @Nullable
   public CompoundTag m_74249_() {
      return this.f_74220_;
   }
}