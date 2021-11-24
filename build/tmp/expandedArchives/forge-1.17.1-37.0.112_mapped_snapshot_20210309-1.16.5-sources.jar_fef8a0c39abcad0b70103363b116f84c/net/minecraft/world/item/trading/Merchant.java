package net.minecraft.world.item.trading;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface Merchant {
   void m_7189_(@Nullable Player p_45307_);

   @Nullable
   Player m_7962_();

   MerchantOffers m_6616_();

   void m_6255_(MerchantOffers p_45306_);

   void m_6996_(MerchantOffer p_45305_);

   void m_7713_(ItemStack p_45308_);

   Level m_7133_();

   int m_7809_();

   void m_6621_(int p_45309_);

   boolean m_7826_();

   SoundEvent m_7596_();

   default boolean m_7862_() {
      return false;
   }

   default void m_45301_(Player p_45302_, Component p_45303_, int p_45304_) {
      OptionalInt optionalint = p_45302_.m_5893_(new SimpleMenuProvider((p_45298_, p_45299_, p_45300_) -> {
         return new MerchantMenu(p_45298_, p_45299_, this);
      }, p_45303_));
      if (optionalint.isPresent()) {
         MerchantOffers merchantoffers = this.m_6616_();
         if (!merchantoffers.isEmpty()) {
            p_45302_.m_7662_(optionalint.getAsInt(), merchantoffers, p_45304_, this.m_7809_(), this.m_7826_(), this.m_7862_());
         }
      }

   }
}