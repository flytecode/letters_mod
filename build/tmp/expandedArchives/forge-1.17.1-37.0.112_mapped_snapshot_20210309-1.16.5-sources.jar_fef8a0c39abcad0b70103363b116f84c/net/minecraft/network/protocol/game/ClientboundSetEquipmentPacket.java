package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ClientboundSetEquipmentPacket implements Packet<ClientGamePacketListener> {
   private static final byte f_179295_ = -128;
   private final int f_133198_;
   private final List<Pair<EquipmentSlot, ItemStack>> f_133199_;

   public ClientboundSetEquipmentPacket(int p_133202_, List<Pair<EquipmentSlot, ItemStack>> p_133203_) {
      this.f_133198_ = p_133202_;
      this.f_133199_ = p_133203_;
   }

   public ClientboundSetEquipmentPacket(FriendlyByteBuf p_179297_) {
      this.f_133198_ = p_179297_.m_130242_();
      EquipmentSlot[] aequipmentslot = EquipmentSlot.values();
      this.f_133199_ = Lists.newArrayList();

      int i;
      do {
         i = p_179297_.readByte();
         EquipmentSlot equipmentslot = aequipmentslot[i & 127];
         ItemStack itemstack = p_179297_.m_130267_();
         this.f_133199_.add(Pair.of(equipmentslot, itemstack));
      } while((i & -128) != 0);

   }

   public void m_5779_(FriendlyByteBuf p_133212_) {
      p_133212_.m_130130_(this.f_133198_);
      int i = this.f_133199_.size();

      for(int j = 0; j < i; ++j) {
         Pair<EquipmentSlot, ItemStack> pair = this.f_133199_.get(j);
         EquipmentSlot equipmentslot = pair.getFirst();
         boolean flag = j != i - 1;
         int k = equipmentslot.ordinal();
         p_133212_.writeByte(flag ? k | -128 : k);
         p_133212_.m_130055_(pair.getSecond());
      }

   }

   public void m_5797_(ClientGamePacketListener p_133209_) {
      p_133209_.m_7277_(this);
   }

   public int m_133210_() {
      return this.f_133198_;
   }

   public List<Pair<EquipmentSlot, ItemStack>> m_133213_() {
      return this.f_133199_;
   }
}