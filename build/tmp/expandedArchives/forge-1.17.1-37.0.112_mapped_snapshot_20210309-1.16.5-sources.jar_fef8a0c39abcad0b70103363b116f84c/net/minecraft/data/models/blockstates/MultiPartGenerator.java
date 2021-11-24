package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class MultiPartGenerator implements BlockStateGenerator {
   private final Block f_125199_;
   private final List<MultiPartGenerator.Entry> f_125200_ = Lists.newArrayList();

   private MultiPartGenerator(Block p_125202_) {
      this.f_125199_ = p_125202_;
   }

   public Block m_6968_() {
      return this.f_125199_;
   }

   public static MultiPartGenerator m_125204_(Block p_125205_) {
      return new MultiPartGenerator(p_125205_);
   }

   public MultiPartGenerator m_125220_(List<Variant> p_125221_) {
      this.f_125200_.add(new MultiPartGenerator.Entry(p_125221_));
      return this;
   }

   public MultiPartGenerator m_125218_(Variant p_125219_) {
      return this.m_125220_(ImmutableList.of(p_125219_));
   }

   public MultiPartGenerator m_125212_(Condition p_125213_, List<Variant> p_125214_) {
      this.f_125200_.add(new MultiPartGenerator.ConditionalEntry(p_125213_, p_125214_));
      return this;
   }

   public MultiPartGenerator m_125215_(Condition p_125216_, Variant... p_125217_) {
      return this.m_125212_(p_125216_, ImmutableList.copyOf(p_125217_));
   }

   public MultiPartGenerator m_125209_(Condition p_125210_, Variant p_125211_) {
      return this.m_125212_(p_125210_, ImmutableList.of(p_125211_));
   }

   public JsonElement get() {
      StateDefinition<Block, BlockState> statedefinition = this.f_125199_.m_49965_();
      this.f_125200_.forEach((p_125208_) -> {
         p_125208_.m_5848_(statedefinition);
      });
      JsonArray jsonarray = new JsonArray();
      this.f_125200_.stream().map(MultiPartGenerator.Entry::get).forEach(jsonarray::add);
      JsonObject jsonobject = new JsonObject();
      jsonobject.add("multipart", jsonarray);
      return jsonobject;
   }

   static class ConditionalEntry extends MultiPartGenerator.Entry {
      private final Condition f_125224_;

      ConditionalEntry(Condition p_125226_, List<Variant> p_125227_) {
         super(p_125227_);
         this.f_125224_ = p_125226_;
      }

      public void m_5848_(StateDefinition<?, ?> p_125233_) {
         this.f_125224_.m_7619_(p_125233_);
      }

      public void m_8000_(JsonObject p_125235_) {
         p_125235_.add("when", this.f_125224_.get());
      }
   }

   static class Entry implements Supplier<JsonElement> {
      private final List<Variant> f_125236_;

      Entry(List<Variant> p_125238_) {
         this.f_125236_ = p_125238_;
      }

      public void m_5848_(StateDefinition<?, ?> p_125243_) {
      }

      public void m_8000_(JsonObject p_125244_) {
      }

      public JsonElement get() {
         JsonObject jsonobject = new JsonObject();
         this.m_8000_(jsonobject);
         jsonobject.add("apply", Variant.m_125514_(this.f_125236_));
         return jsonobject;
      }
   }
}