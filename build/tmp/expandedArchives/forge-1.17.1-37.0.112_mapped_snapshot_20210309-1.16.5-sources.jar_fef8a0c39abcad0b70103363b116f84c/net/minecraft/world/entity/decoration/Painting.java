package net.minecraft.world.entity.decoration;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddPaintingPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class Painting extends HangingEntity {
   public Motive f_31902_;

   public Painting(EntityType<? extends Painting> p_31904_, Level p_31905_) {
      super(p_31904_, p_31905_);
   }

   public Painting(Level p_31907_, BlockPos p_31908_, Direction p_31909_) {
      super(EntityType.f_20506_, p_31907_, p_31908_);
      List<Motive> list = Lists.newArrayList();
      int i = 0;

      for(Motive motive : Registry.f_122831_) {
         this.f_31902_ = motive;
         this.m_6022_(p_31909_);
         if (this.m_7088_()) {
            list.add(motive);
            int j = motive.m_31896_() * motive.m_31901_();
            if (j > i) {
               i = j;
            }
         }
      }

      if (!list.isEmpty()) {
         Iterator<Motive> iterator = list.iterator();

         while(iterator.hasNext()) {
            Motive motive1 = iterator.next();
            if (motive1.m_31896_() * motive1.m_31901_() < i) {
               iterator.remove();
            }
         }

         this.f_31902_ = list.get(this.f_19796_.nextInt(list.size()));
      }

      this.m_6022_(p_31909_);
   }

   public Painting(Level p_31911_, BlockPos p_31912_, Direction p_31913_, Motive p_31914_) {
      this(p_31911_, p_31912_, p_31913_);
      this.f_31902_ = p_31914_;
      this.m_6022_(p_31913_);
   }

   public void m_7380_(CompoundTag p_31935_) {
      p_31935_.m_128359_("Motive", Registry.f_122831_.m_7981_(this.f_31902_).toString());
      p_31935_.m_128344_("Facing", (byte)this.f_31699_.m_122416_());
      super.m_7380_(p_31935_);
   }

   public void m_7378_(CompoundTag p_31927_) {
      this.f_31902_ = Registry.f_122831_.m_7745_(ResourceLocation.m_135820_(p_31927_.m_128461_("Motive")));
      this.f_31699_ = Direction.m_122407_(p_31927_.m_128445_("Facing"));
      super.m_7378_(p_31927_);
      this.m_6022_(this.f_31699_);
   }

   public int m_7076_() {
      return this.f_31902_ == null ? 1 : this.f_31902_.m_31896_();
   }

   public int m_7068_() {
      return this.f_31902_ == null ? 1 : this.f_31902_.m_31901_();
   }

   public void m_5553_(@Nullable Entity p_31925_) {
      if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
         this.m_5496_(SoundEvents.f_12175_, 1.0F, 1.0F);
         if (p_31925_ instanceof Player) {
            Player player = (Player)p_31925_;
            if (player.m_150110_().f_35937_) {
               return;
            }
         }

         this.m_19998_(Items.f_42487_);
      }
   }

   public void m_7084_() {
      this.m_5496_(SoundEvents.f_12176_, 1.0F, 1.0F);
   }

   public void m_7678_(double p_31929_, double p_31930_, double p_31931_, float p_31932_, float p_31933_) {
      this.m_6034_(p_31929_, p_31930_, p_31931_);
   }

   public void m_6453_(double p_31917_, double p_31918_, double p_31919_, float p_31920_, float p_31921_, int p_31922_, boolean p_31923_) {
      BlockPos blockpos = this.f_31698_.m_142022_(p_31917_ - this.m_20185_(), p_31918_ - this.m_20186_(), p_31919_ - this.m_20189_());
      this.m_6034_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_());
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddPaintingPacket(this);
   }

   public ItemStack m_142340_() {
      return new ItemStack(Items.f_42487_);
   }
}