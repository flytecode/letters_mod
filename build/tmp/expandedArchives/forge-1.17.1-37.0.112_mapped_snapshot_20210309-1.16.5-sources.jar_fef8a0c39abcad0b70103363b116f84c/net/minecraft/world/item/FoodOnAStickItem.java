package net.minecraft.world.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FoodOnAStickItem<T extends Entity & ItemSteerable> extends Item {
   private final EntityType<T> f_41304_;
   private final int f_41305_;

   public FoodOnAStickItem(Item.Properties p_41307_, EntityType<T> p_41308_, int p_41309_) {
      super(p_41307_);
      this.f_41304_ = p_41308_;
      this.f_41305_ = p_41309_;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41314_, Player p_41315_, InteractionHand p_41316_) {
      ItemStack itemstack = p_41315_.m_21120_(p_41316_);
      if (p_41314_.f_46443_) {
         return InteractionResultHolder.m_19098_(itemstack);
      } else {
         Entity entity = p_41315_.m_20202_();
         if (p_41315_.m_20159_() && entity instanceof ItemSteerable && entity.m_6095_() == this.f_41304_) {
            ItemSteerable itemsteerable = (ItemSteerable)entity;
            if (itemsteerable.m_6746_()) {
               itemstack.m_41622_(this.f_41305_, p_41315_, (p_41312_) -> {
                  p_41312_.m_21190_(p_41316_);
               });
               if (itemstack.m_41619_()) {
                  ItemStack itemstack1 = new ItemStack(Items.f_42523_);
                  itemstack1.m_41751_(itemstack.m_41783_());
                  return InteractionResultHolder.m_19090_(itemstack1);
               }

               return InteractionResultHolder.m_19090_(itemstack);
            }
         }

         p_41315_.m_36246_(Stats.f_12982_.m_12902_(this));
         return InteractionResultHolder.m_19098_(itemstack);
      }
   }
}