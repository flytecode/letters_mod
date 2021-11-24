package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class TagEntry extends LootPoolSingletonContainer {
   final Tag<Item> f_79821_;
   final boolean f_79822_;

   TagEntry(Tag<Item> p_79824_, boolean p_79825_, int p_79826_, int p_79827_, LootItemCondition[] p_79828_, LootItemFunction[] p_79829_) {
      super(p_79826_, p_79827_, p_79828_, p_79829_);
      this.f_79821_ = p_79824_;
      this.f_79822_ = p_79825_;
   }

   public LootPoolEntryType m_6751_() {
      return LootPoolEntries.f_79623_;
   }

   public void m_6948_(Consumer<ItemStack> p_79854_, LootContext p_79855_) {
      this.f_79821_.m_6497_().forEach((p_79852_) -> {
         p_79854_.accept(new ItemStack(p_79852_));
      });
   }

   private boolean m_79845_(LootContext p_79846_, Consumer<LootPoolEntry> p_79847_) {
      if (!this.m_79639_(p_79846_)) {
         return false;
      } else {
         for(final Item item : this.f_79821_.m_6497_()) {
            p_79847_.accept(new LootPoolSingletonContainer.EntryBase() {
               public void m_6941_(Consumer<ItemStack> p_79869_, LootContext p_79870_) {
                  p_79869_.accept(new ItemStack(item));
               }
            });
         }

         return true;
      }
   }

   public boolean m_6562_(LootContext p_79861_, Consumer<LootPoolEntry> p_79862_) {
      return this.f_79822_ ? this.m_79845_(p_79861_, p_79862_) : super.m_6562_(p_79861_, p_79862_);
   }

   public static LootPoolSingletonContainer.Builder<?> m_165162_(Tag<Item> p_165163_) {
      return m_79687_((p_165166_, p_165167_, p_165168_, p_165169_) -> {
         return new TagEntry(p_165163_, false, p_165166_, p_165167_, p_165168_, p_165169_);
      });
   }

   public static LootPoolSingletonContainer.Builder<?> m_79856_(Tag<Item> p_79857_) {
      return m_79687_((p_79841_, p_79842_, p_79843_, p_79844_) -> {
         return new TagEntry(p_79857_, true, p_79841_, p_79842_, p_79843_, p_79844_);
      });
   }

   public static class Serializer extends LootPoolSingletonContainer.Serializer<TagEntry> {
      public void m_7219_(JsonObject p_79888_, TagEntry p_79889_, JsonSerializationContext p_79890_) {
         super.m_7219_(p_79888_, p_79889_, p_79890_);
         p_79888_.addProperty("name", SerializationTags.m_13199_().m_144454_(Registry.f_122904_, p_79889_.f_79821_, () -> {
            return new IllegalStateException("Unknown item tag");
         }).toString());
         p_79888_.addProperty("expand", p_79889_.f_79822_);
      }

      protected TagEntry m_7267_(JsonObject p_79873_, JsonDeserializationContext p_79874_, int p_79875_, int p_79876_, LootItemCondition[] p_79877_, LootItemFunction[] p_79878_) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_79873_, "name"));
         Tag<Item> tag = SerializationTags.m_13199_().m_144458_(Registry.f_122904_, resourcelocation, (p_165172_) -> {
            return new JsonParseException("Can't find tag: " + p_165172_);
         });
         boolean flag = GsonHelper.m_13912_(p_79873_, "expand");
         return new TagEntry(tag, flag, p_79875_, p_79876_, p_79877_, p_79878_);
      }
   }
}