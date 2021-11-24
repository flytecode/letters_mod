package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class RandomizedIntStateProvider extends BlockStateProvider {
   public static final Codec<RandomizedIntStateProvider> f_161555_ = RecordCodecBuilder.create((p_161576_) -> {
      return p_161576_.group(BlockStateProvider.f_68747_.fieldOf("source").forGetter((p_161592_) -> {
         return p_161592_.f_161556_;
      }), Codec.STRING.fieldOf("property").forGetter((p_161590_) -> {
         return p_161590_.f_161557_;
      }), IntProvider.f_146531_.fieldOf("values").forGetter((p_161578_) -> {
         return p_161578_.f_161559_;
      })).apply(p_161576_, RandomizedIntStateProvider::new);
   });
   private final BlockStateProvider f_161556_;
   private final String f_161557_;
   @Nullable
   private IntegerProperty f_161558_;
   private final IntProvider f_161559_;

   public RandomizedIntStateProvider(BlockStateProvider p_161562_, IntegerProperty p_161563_, IntProvider p_161564_) {
      this.f_161556_ = p_161562_;
      this.f_161558_ = p_161563_;
      this.f_161557_ = p_161563_.m_61708_();
      this.f_161559_ = p_161564_;
      Collection<Integer> collection = p_161563_.m_6908_();

      for(int i = p_161564_.m_142739_(); i <= p_161564_.m_142737_(); ++i) {
         if (!collection.contains(i)) {
            throw new IllegalArgumentException("Property value out of range: " + p_161563_.m_61708_() + ": " + i);
         }
      }

   }

   public RandomizedIntStateProvider(BlockStateProvider p_161566_, String p_161567_, IntProvider p_161568_) {
      this.f_161556_ = p_161566_;
      this.f_161557_ = p_161567_;
      this.f_161559_ = p_161568_;
   }

   protected BlockStateProviderType<?> m_5923_() {
      return BlockStateProviderType.f_161554_;
   }

   public BlockState m_7112_(Random p_161585_, BlockPos p_161586_) {
      BlockState blockstate = this.f_161556_.m_7112_(p_161585_, p_161586_);
      if (this.f_161558_ == null || !blockstate.m_61138_(this.f_161558_)) {
         this.f_161558_ = m_161570_(blockstate, this.f_161557_);
      }

      return blockstate.m_61124_(this.f_161558_, Integer.valueOf(this.f_161559_.m_142270_(p_161585_)));
   }

   private static IntegerProperty m_161570_(BlockState p_161571_, String p_161572_) {
      Collection<Property<?>> collection = p_161571_.m_61147_();
      Optional<IntegerProperty> optional = collection.stream().filter((p_161583_) -> {
         return p_161583_.m_61708_().equals(p_161572_);
      }).filter((p_161588_) -> {
         return p_161588_ instanceof IntegerProperty;
      }).map((p_161574_) -> {
         return (IntegerProperty)p_161574_;
      }).findAny();
      return optional.orElseThrow(() -> {
         return new IllegalArgumentException("Illegal property: " + p_161572_);
      });
   }
}