package net.minecraft.world.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public class UseItemGoal<T extends Mob> extends Goal {
   private final T f_25967_;
   private final ItemStack f_25968_;
   private final Predicate<? super T> f_25969_;
   private final SoundEvent f_25970_;

   public UseItemGoal(T p_25972_, ItemStack p_25973_, @Nullable SoundEvent p_25974_, Predicate<? super T> p_25975_) {
      this.f_25967_ = p_25972_;
      this.f_25968_ = p_25973_;
      this.f_25970_ = p_25974_;
      this.f_25969_ = p_25975_;
   }

   public boolean m_8036_() {
      return this.f_25969_.test(this.f_25967_);
   }

   public boolean m_8045_() {
      return this.f_25967_.m_6117_();
   }

   public void m_8056_() {
      this.f_25967_.m_8061_(EquipmentSlot.MAINHAND, this.f_25968_.m_41777_());
      this.f_25967_.m_6672_(InteractionHand.MAIN_HAND);
   }

   public void m_8041_() {
      this.f_25967_.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
      if (this.f_25970_ != null) {
         this.f_25967_.m_5496_(this.f_25970_, 1.0F, this.f_25967_.m_21187_().nextFloat() * 0.2F + 0.9F);
      }

   }
}