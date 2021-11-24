package net.minecraft.world.item.trading;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class MerchantOffers extends ArrayList<MerchantOffer> {
   public MerchantOffers() {
   }

   public MerchantOffers(CompoundTag p_45387_) {
      ListTag listtag = p_45387_.m_128437_("Recipes", 10);

      for(int i = 0; i < listtag.size(); ++i) {
         this.add(new MerchantOffer(listtag.m_128728_(i)));
      }

   }

   @Nullable
   public MerchantOffer m_45389_(ItemStack p_45390_, ItemStack p_45391_, int p_45392_) {
      if (p_45392_ > 0 && p_45392_ < this.size()) {
         MerchantOffer merchantoffer1 = this.get(p_45392_);
         return merchantoffer1.m_45355_(p_45390_, p_45391_) ? merchantoffer1 : null;
      } else {
         for(int i = 0; i < this.size(); ++i) {
            MerchantOffer merchantoffer = this.get(i);
            if (merchantoffer.m_45355_(p_45390_, p_45391_)) {
               return merchantoffer;
            }
         }

         return null;
      }
   }

   public void m_45393_(FriendlyByteBuf p_45394_) {
      p_45394_.writeByte((byte)(this.size() & 255));

      for(int i = 0; i < this.size(); ++i) {
         MerchantOffer merchantoffer = this.get(i);
         p_45394_.m_130055_(merchantoffer.m_45352_());
         p_45394_.m_130055_(merchantoffer.m_45368_());
         ItemStack itemstack = merchantoffer.m_45364_();
         p_45394_.writeBoolean(!itemstack.m_41619_());
         if (!itemstack.m_41619_()) {
            p_45394_.m_130055_(itemstack);
         }

         p_45394_.writeBoolean(merchantoffer.m_45380_());
         p_45394_.writeInt(merchantoffer.m_45371_());
         p_45394_.writeInt(merchantoffer.m_45373_());
         p_45394_.writeInt(merchantoffer.m_45379_());
         p_45394_.writeInt(merchantoffer.m_45377_());
         p_45394_.writeFloat(merchantoffer.m_45378_());
         p_45394_.writeInt(merchantoffer.m_45375_());
      }

   }

   public static MerchantOffers m_45395_(FriendlyByteBuf p_45396_) {
      MerchantOffers merchantoffers = new MerchantOffers();
      int i = p_45396_.readByte() & 255;

      for(int j = 0; j < i; ++j) {
         ItemStack itemstack = p_45396_.m_130267_();
         ItemStack itemstack1 = p_45396_.m_130267_();
         ItemStack itemstack2 = ItemStack.f_41583_;
         if (p_45396_.readBoolean()) {
            itemstack2 = p_45396_.m_130267_();
         }

         boolean flag = p_45396_.readBoolean();
         int k = p_45396_.readInt();
         int l = p_45396_.readInt();
         int i1 = p_45396_.readInt();
         int j1 = p_45396_.readInt();
         float f = p_45396_.readFloat();
         int k1 = p_45396_.readInt();
         MerchantOffer merchantoffer = new MerchantOffer(itemstack, itemstack2, itemstack1, k, l, i1, f, k1);
         if (flag) {
            merchantoffer.m_45381_();
         }

         merchantoffer.m_45359_(j1);
         merchantoffers.add(merchantoffer);
      }

      return merchantoffers;
   }

   public CompoundTag m_45388_() {
      CompoundTag compoundtag = new CompoundTag();
      ListTag listtag = new ListTag();

      for(int i = 0; i < this.size(); ++i) {
         MerchantOffer merchantoffer = this.get(i);
         listtag.add(merchantoffer.m_45384_());
      }

      compoundtag.m_128365_("Recipes", listtag);
      return compoundtag;
   }
}