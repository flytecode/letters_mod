package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class ElytraItem extends Item implements Wearable {
   public ElytraItem(Item.Properties p_41132_) {
      super(p_41132_);
      DispenserBlock.m_52672_(this, ArmorItem.f_40376_);
   }

   public static boolean m_41140_(ItemStack p_41141_) {
      return p_41141_.m_41773_() < p_41141_.m_41776_() - 1;
   }

   public boolean m_6832_(ItemStack p_41134_, ItemStack p_41135_) {
      return p_41135_.m_150930_(Items.f_42714_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_41137_, Player p_41138_, InteractionHand p_41139_) {
      ItemStack itemstack = p_41138_.m_21120_(p_41139_);
      EquipmentSlot equipmentslot = Mob.m_147233_(itemstack);
      ItemStack itemstack1 = p_41138_.m_6844_(equipmentslot);
      if (itemstack1.m_41619_()) {
         p_41138_.m_8061_(equipmentslot, itemstack.m_41777_());
         if (!p_41137_.m_5776_()) {
            p_41138_.m_36246_(Stats.f_12982_.m_12902_(this));
         }

         itemstack.m_41764_(0);
         return InteractionResultHolder.m_19092_(itemstack, p_41137_.m_5776_());
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   @Override
   public boolean canElytraFly(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
      return ElytraItem.m_41140_(stack);
   }

   @Override
   public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
      if (!entity.f_19853_.f_46443_ && (flightTicks + 1) % 20 == 0) {
         stack.m_41622_(1, entity, e -> e.m_21166_(net.minecraft.world.entity.EquipmentSlot.CHEST));
      }
      return true;
   }

   @Nullable
   public SoundEvent m_142602_() {
      return SoundEvents.f_11674_;
   }
}
