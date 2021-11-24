package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;

public class EntityProjectileOwnerFix extends DataFix {
   public EntityProjectileOwnerFix(Schema p_15558_) {
      super(p_15558_, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema schema = this.getInputSchema();
      return this.fixTypeEverywhereTyped("EntityProjectileOwner", schema.getType(References.f_16786_), this::m_15562_);
   }

   private Typed<?> m_15562_(Typed<?> p_15563_) {
      p_15563_ = this.m_15564_(p_15563_, "minecraft:egg", this::m_15581_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:ender_pearl", this::m_15581_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:experience_bottle", this::m_15581_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:snowball", this::m_15581_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:potion", this::m_15581_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:potion", this::m_15579_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:llama_spit", this::m_15577_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:arrow", this::m_15568_);
      p_15563_ = this.m_15564_(p_15563_, "minecraft:spectral_arrow", this::m_15568_);
      return this.m_15564_(p_15563_, "minecraft:trident", this::m_15568_);
   }

   private Dynamic<?> m_15568_(Dynamic<?> p_15569_) {
      long i = p_15569_.get("OwnerUUIDMost").asLong(0L);
      long j = p_15569_.get("OwnerUUIDLeast").asLong(0L);
      return this.m_15570_(p_15569_, i, j).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
   }

   private Dynamic<?> m_15577_(Dynamic<?> p_15578_) {
      OptionalDynamic<?> optionaldynamic = p_15578_.get("Owner");
      long i = optionaldynamic.get("OwnerUUIDMost").asLong(0L);
      long j = optionaldynamic.get("OwnerUUIDLeast").asLong(0L);
      return this.m_15570_(p_15578_, i, j).remove("Owner");
   }

   private Dynamic<?> m_15579_(Dynamic<?> p_15580_) {
      OptionalDynamic<?> optionaldynamic = p_15580_.get("Potion");
      return p_15580_.set("Item", optionaldynamic.orElseEmptyMap()).remove("Potion");
   }

   private Dynamic<?> m_15581_(Dynamic<?> p_15582_) {
      String s = "owner";
      OptionalDynamic<?> optionaldynamic = p_15582_.get("owner");
      long i = optionaldynamic.get("M").asLong(0L);
      long j = optionaldynamic.get("L").asLong(0L);
      return this.m_15570_(p_15582_, i, j).remove("owner");
   }

   private Dynamic<?> m_15570_(Dynamic<?> p_15571_, long p_15572_, long p_15573_) {
      String s = "OwnerUUID";
      return p_15572_ != 0L && p_15573_ != 0L ? p_15571_.set("OwnerUUID", p_15571_.createIntList(Arrays.stream(m_15559_(p_15572_, p_15573_)))) : p_15571_;
   }

   private static int[] m_15559_(long p_15560_, long p_15561_) {
      return new int[]{(int)(p_15560_ >> 32), (int)p_15560_, (int)(p_15561_ >> 32), (int)p_15561_};
   }

   private Typed<?> m_15564_(Typed<?> p_15565_, String p_15566_, Function<Dynamic<?>, Dynamic<?>> p_15567_) {
      Type<?> type = this.getInputSchema().getChoiceType(References.f_16786_, p_15566_);
      Type<?> type1 = this.getOutputSchema().getChoiceType(References.f_16786_, p_15566_);
      return p_15565_.updateTyped(DSL.namedChoice(p_15566_, type), type1, (p_15576_) -> {
         return p_15576_.update(DSL.remainderFinder(), p_15567_);
      });
   }
}