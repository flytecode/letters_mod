package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;

public class InventoryChangeTrigger extends SimpleCriterionTrigger<InventoryChangeTrigger.TriggerInstance> {
   static final ResourceLocation f_43145_ = new ResourceLocation("inventory_changed");

   public ResourceLocation m_7295_() {
      return f_43145_;
   }

   public InventoryChangeTrigger.TriggerInstance m_7214_(JsonObject p_43168_, EntityPredicate.Composite p_43169_, DeserializationContext p_43170_) {
      JsonObject jsonobject = GsonHelper.m_13841_(p_43168_, "slots", new JsonObject());
      MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(jsonobject.get("occupied"));
      MinMaxBounds.Ints minmaxbounds$ints1 = MinMaxBounds.Ints.m_55373_(jsonobject.get("full"));
      MinMaxBounds.Ints minmaxbounds$ints2 = MinMaxBounds.Ints.m_55373_(jsonobject.get("empty"));
      ItemPredicate[] aitempredicate = ItemPredicate.m_45055_(p_43168_.get("items"));
      return new InventoryChangeTrigger.TriggerInstance(p_43169_, minmaxbounds$ints, minmaxbounds$ints1, minmaxbounds$ints2, aitempredicate);
   }

   public void m_43149_(ServerPlayer p_43150_, Inventory p_43151_, ItemStack p_43152_) {
      int i = 0;
      int j = 0;
      int k = 0;

      for(int l = 0; l < p_43151_.m_6643_(); ++l) {
         ItemStack itemstack = p_43151_.m_8020_(l);
         if (itemstack.m_41619_()) {
            ++j;
         } else {
            ++k;
            if (itemstack.m_41613_() >= itemstack.m_41741_()) {
               ++i;
            }
         }
      }

      this.m_43153_(p_43150_, p_43151_, p_43152_, i, j, k);
   }

   private void m_43153_(ServerPlayer p_43154_, Inventory p_43155_, ItemStack p_43156_, int p_43157_, int p_43158_, int p_43159_) {
      this.m_66234_(p_43154_, (p_43166_) -> {
         return p_43166_.m_43186_(p_43155_, p_43156_, p_43157_, p_43158_, p_43159_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Ints f_43176_;
      private final MinMaxBounds.Ints f_43177_;
      private final MinMaxBounds.Ints f_43178_;
      private final ItemPredicate[] f_43179_;

      public TriggerInstance(EntityPredicate.Composite p_43181_, MinMaxBounds.Ints p_43182_, MinMaxBounds.Ints p_43183_, MinMaxBounds.Ints p_43184_, ItemPredicate[] p_43185_) {
         super(InventoryChangeTrigger.f_43145_, p_43181_);
         this.f_43176_ = p_43182_;
         this.f_43177_ = p_43183_;
         this.f_43178_ = p_43184_;
         this.f_43179_ = p_43185_;
      }

      public static InventoryChangeTrigger.TriggerInstance m_43197_(ItemPredicate... p_43198_) {
         return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, p_43198_);
      }

      public static InventoryChangeTrigger.TriggerInstance m_43199_(ItemLike... p_43200_) {
         ItemPredicate[] aitempredicate = new ItemPredicate[p_43200_.length];

         for(int i = 0; i < p_43200_.length; ++i) {
            aitempredicate[i] = new ItemPredicate((Tag<Item>)null, ImmutableSet.of(p_43200_[i].m_5456_()), MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, EnchantmentPredicate.f_30465_, EnchantmentPredicate.f_30465_, (Potion)null, NbtPredicate.f_57471_);
         }

         return m_43197_(aitempredicate);
      }

      public JsonObject m_7683_(SerializationContext p_43196_) {
         JsonObject jsonobject = super.m_7683_(p_43196_);
         if (!this.f_43176_.m_55327_() || !this.f_43177_.m_55327_() || !this.f_43178_.m_55327_()) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.add("occupied", this.f_43176_.m_55328_());
            jsonobject1.add("full", this.f_43177_.m_55328_());
            jsonobject1.add("empty", this.f_43178_.m_55328_());
            jsonobject.add("slots", jsonobject1);
         }

         if (this.f_43179_.length > 0) {
            JsonArray jsonarray = new JsonArray();

            for(ItemPredicate itempredicate : this.f_43179_) {
               jsonarray.add(itempredicate.m_45048_());
            }

            jsonobject.add("items", jsonarray);
         }

         return jsonobject;
      }

      public boolean m_43186_(Inventory p_43187_, ItemStack p_43188_, int p_43189_, int p_43190_, int p_43191_) {
         if (!this.f_43177_.m_55390_(p_43189_)) {
            return false;
         } else if (!this.f_43178_.m_55390_(p_43190_)) {
            return false;
         } else if (!this.f_43176_.m_55390_(p_43191_)) {
            return false;
         } else {
            int i = this.f_43179_.length;
            if (i == 0) {
               return true;
            } else if (i != 1) {
               List<ItemPredicate> list = new ObjectArrayList<>(this.f_43179_);
               int j = p_43187_.m_6643_();

               for(int k = 0; k < j; ++k) {
                  if (list.isEmpty()) {
                     return true;
                  }

                  ItemStack itemstack = p_43187_.m_8020_(k);
                  if (!itemstack.m_41619_()) {
                     list.removeIf((p_43194_) -> {
                        return p_43194_.m_45049_(itemstack);
                     });
                  }
               }

               return list.isEmpty();
            } else {
               return !p_43188_.m_41619_() && this.f_43179_[0].m_45049_(p_43188_);
            }
         }
      }
   }
}