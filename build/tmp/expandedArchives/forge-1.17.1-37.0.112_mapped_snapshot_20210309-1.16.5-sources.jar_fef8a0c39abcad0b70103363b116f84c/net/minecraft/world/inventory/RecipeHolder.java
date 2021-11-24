package net.minecraft.world.inventory;

import java.util.Collections;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public interface RecipeHolder {
   void m_6029_(@Nullable Recipe<?> p_40134_);

   @Nullable
   Recipe<?> m_7928_();

   default void m_8015_(Player p_40139_) {
      Recipe<?> recipe = this.m_7928_();
      if (recipe != null && !recipe.m_5598_()) {
         p_40139_.m_7281_(Collections.singleton(recipe));
         this.m_6029_((Recipe<?>)null);
      }

   }

   default boolean m_40135_(Level p_40136_, ServerPlayer p_40137_, Recipe<?> p_40138_) {
      if (!p_40138_.m_5598_() && p_40136_.m_46469_().m_46207_(GameRules.f_46151_) && !p_40137_.m_8952_().m_12709_(p_40138_)) {
         return false;
      } else {
         this.m_6029_(p_40138_);
         return true;
      }
   }
}