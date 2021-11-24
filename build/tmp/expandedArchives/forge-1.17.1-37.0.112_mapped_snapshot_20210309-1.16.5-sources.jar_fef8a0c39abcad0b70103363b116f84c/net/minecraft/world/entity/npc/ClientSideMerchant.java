package net.minecraft.world.entity.npc;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class ClientSideMerchant implements Merchant {
   private final Player f_35340_;
   private MerchantOffers f_35341_ = new MerchantOffers();
   private int f_35342_;

   public ClientSideMerchant(Player p_35344_) {
      this.f_35340_ = p_35344_;
   }

   public Player m_7962_() {
      return this.f_35340_;
   }

   public void m_7189_(@Nullable Player p_35356_) {
   }

   public MerchantOffers m_6616_() {
      return this.f_35341_;
   }

   public void m_6255_(MerchantOffers p_35348_) {
      this.f_35341_ = p_35348_;
   }

   public void m_6996_(MerchantOffer p_35346_) {
      p_35346_.m_45374_();
   }

   public void m_7713_(ItemStack p_35358_) {
   }

   public Level m_7133_() {
      return this.f_35340_.f_19853_;
   }

   public int m_7809_() {
      return this.f_35342_;
   }

   public void m_6621_(int p_35360_) {
      this.f_35342_ = p_35360_;
   }

   public boolean m_7826_() {
      return true;
   }

   public SoundEvent m_7596_() {
      return SoundEvents.f_12509_;
   }
}