package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetBannerPatternFunction extends LootItemConditionalFunction {
   final List<Pair<BannerPattern, DyeColor>> f_165272_;
   final boolean f_165273_;

   SetBannerPatternFunction(LootItemCondition[] p_165275_, List<Pair<BannerPattern, DyeColor>> p_165276_, boolean p_165277_) {
      super(p_165275_);
      this.f_165272_ = p_165276_;
      this.f_165273_ = p_165277_;
   }

   protected ItemStack m_7372_(ItemStack p_165280_, LootContext p_165281_) {
      CompoundTag compoundtag = p_165280_.m_41698_("BlockEntityTag");
      BannerPattern.Builder bannerpattern$builder = new BannerPattern.Builder();
      this.f_165272_.forEach(bannerpattern$builder::m_155048_);
      ListTag listtag = bannerpattern$builder.m_58587_();
      ListTag listtag1;
      if (this.f_165273_) {
         listtag1 = compoundtag.m_128437_("Patterns", 10).m_6426_();
         listtag1.addAll(listtag);
      } else {
         listtag1 = listtag;
      }

      compoundtag.m_128365_("Patterns", listtag1);
      return p_165280_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_165222_;
   }

   public static SetBannerPatternFunction.Builder m_165282_(boolean p_165283_) {
      return new SetBannerPatternFunction.Builder(p_165283_);
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetBannerPatternFunction.Builder> {
      private final ImmutableList.Builder<Pair<BannerPattern, DyeColor>> f_165284_ = ImmutableList.builder();
      private final boolean f_165285_;

      Builder(boolean p_165287_) {
         this.f_165285_ = p_165287_;
      }

      protected SetBannerPatternFunction.Builder m_6477_() {
         return this;
      }

      public LootItemFunction m_7453_() {
         return new SetBannerPatternFunction(this.m_80699_(), this.f_165284_.build(), this.f_165285_);
      }

      public SetBannerPatternFunction.Builder m_165289_(BannerPattern p_165290_, DyeColor p_165291_) {
         this.f_165284_.add(Pair.of(p_165290_, p_165291_));
         return this;
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetBannerPatternFunction> {
      public void m_6170_(JsonObject p_165307_, SetBannerPatternFunction p_165308_, JsonSerializationContext p_165309_) {
         super.m_6170_(p_165307_, p_165308_, p_165309_);
         JsonArray jsonarray = new JsonArray();
         p_165308_.f_165272_.forEach((p_165297_) -> {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("pattern", p_165297_.getFirst().m_58572_());
            jsonobject.addProperty("color", p_165297_.getSecond().m_41065_());
            jsonarray.add(jsonobject);
         });
         p_165307_.add("patterns", jsonarray);
         p_165307_.addProperty("append", p_165308_.f_165273_);
      }

      public SetBannerPatternFunction m_6821_(JsonObject p_165299_, JsonDeserializationContext p_165300_, LootItemCondition[] p_165301_) {
         ImmutableList.Builder<Pair<BannerPattern, DyeColor>> builder = ImmutableList.builder();
         JsonArray jsonarray = GsonHelper.m_13933_(p_165299_, "patterns");

         for(int i = 0; i < jsonarray.size(); ++i) {
            JsonObject jsonobject = GsonHelper.m_13918_(jsonarray.get(i), "pattern[" + i + "]");
            String s = GsonHelper.m_13906_(jsonobject, "pattern");
            BannerPattern bannerpattern = BannerPattern.m_155045_(s);
            if (bannerpattern == null) {
               throw new JsonSyntaxException("Unknown pattern: " + s);
            }

            String s1 = GsonHelper.m_13906_(jsonobject, "color");
            DyeColor dyecolor = DyeColor.m_41057_(s1, (DyeColor)null);
            if (dyecolor == null) {
               throw new JsonSyntaxException("Unknown color: " + s1);
            }

            builder.add(Pair.of(bannerpattern, dyecolor));
         }

         boolean flag = GsonHelper.m_13912_(p_165299_, "append");
         return new SetBannerPatternFunction(p_165301_, builder.build(), flag);
      }
   }
}