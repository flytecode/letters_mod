package net.minecraft.server.level;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerEntity {
   private static final Logger f_8508_ = LogManager.getLogger();
   private static final int f_143241_ = 1;
   private final ServerLevel f_8509_;
   private final Entity f_8510_;
   private final int f_8511_;
   private final boolean f_8512_;
   private final Consumer<Packet<?>> f_8513_;
   private long f_8514_;
   private long f_8515_;
   private long f_8516_;
   private int f_8517_;
   private int f_8518_;
   private int f_8519_;
   private Vec3 f_8520_ = Vec3.f_82478_;
   private int f_8521_;
   private int f_8522_;
   private List<Entity> f_8523_ = Collections.emptyList();
   private boolean f_8524_;
   private boolean f_8525_;

   public ServerEntity(ServerLevel p_8528_, Entity p_8529_, int p_8530_, boolean p_8531_, Consumer<Packet<?>> p_8532_) {
      this.f_8509_ = p_8528_;
      this.f_8513_ = p_8532_;
      this.f_8510_ = p_8529_;
      this.f_8511_ = p_8530_;
      this.f_8512_ = p_8531_;
      this.m_8544_();
      this.f_8517_ = Mth.m_14143_(p_8529_.m_146908_() * 256.0F / 360.0F);
      this.f_8518_ = Mth.m_14143_(p_8529_.m_146909_() * 256.0F / 360.0F);
      this.f_8519_ = Mth.m_14143_(p_8529_.m_6080_() * 256.0F / 360.0F);
      this.f_8525_ = p_8529_.m_20096_();
   }

   public void m_8533_() {
      List<Entity> list = this.f_8510_.m_20197_();
      if (!list.equals(this.f_8523_)) {
         this.f_8523_ = list;
         this.f_8513_.accept(new ClientboundSetPassengersPacket(this.f_8510_));
      }

      if (this.f_8510_ instanceof ItemFrame && this.f_8521_ % 10 == 0) {
         ItemFrame itemframe = (ItemFrame)this.f_8510_;
         ItemStack itemstack = itemframe.m_31822_();
         Integer integer = MapItem.m_151131_(itemstack);
         MapItemSavedData mapitemsaveddata = MapItem.m_42853_(itemstack, this.f_8509_);
         if (mapitemsaveddata != null) {
               for(ServerPlayer serverplayer : this.f_8509_.m_6907_()) {
                  mapitemsaveddata.m_77918_(serverplayer, itemstack);
                  Packet<?> packet = mapitemsaveddata.m_164796_(integer, serverplayer);
                  if (packet != null) {
                     serverplayer.f_8906_.m_141995_(packet);
                  }
               }
         }

         this.m_8543_();
      }



      if (this.f_8521_ % this.f_8511_ == 0 || this.f_8510_.f_19812_ || this.f_8510_.m_20088_().m_135352_()) {
         if (this.f_8510_.m_20159_()) {
            int i1 = Mth.m_14143_(this.f_8510_.m_146908_() * 256.0F / 360.0F);
            int l1 = Mth.m_14143_(this.f_8510_.m_146909_() * 256.0F / 360.0F);
            boolean flag1 = Math.abs(i1 - this.f_8517_) >= 1 || Math.abs(l1 - this.f_8518_) >= 1;
            if (flag1) {
               this.f_8513_.accept(new ClientboundMoveEntityPacket.Rot(this.f_8510_.m_142049_(), (byte)i1, (byte)l1, this.f_8510_.m_20096_()));
               this.f_8517_ = i1;
               this.f_8518_ = l1;
            }

            this.m_8544_();
            this.m_8543_();
            this.f_8524_ = true;
         } else {
            ++this.f_8522_;
            int l = Mth.m_14143_(this.f_8510_.m_146908_() * 256.0F / 360.0F);
            int k1 = Mth.m_14143_(this.f_8510_.m_146909_() * 256.0F / 360.0F);
            Vec3 vec3 = this.f_8510_.m_20182_().m_82546_(ClientboundMoveEntityPacket.m_132515_(this.f_8514_, this.f_8515_, this.f_8516_));
            boolean flag2 = vec3.m_82556_() >= (double)7.6293945E-6F;
            Packet<?> packet1 = null;
            boolean flag3 = flag2 || this.f_8521_ % 60 == 0;
            boolean flag4 = Math.abs(l - this.f_8517_) >= 1 || Math.abs(k1 - this.f_8518_) >= 1;
            if (this.f_8521_ > 0 || this.f_8510_ instanceof AbstractArrow) {
               long i = ClientboundMoveEntityPacket.m_132511_(vec3.f_82479_);
               long j = ClientboundMoveEntityPacket.m_132511_(vec3.f_82480_);
               long k = ClientboundMoveEntityPacket.m_132511_(vec3.f_82481_);
               boolean flag = i < -32768L || i > 32767L || j < -32768L || j > 32767L || k < -32768L || k > 32767L;
               if (!flag && this.f_8522_ <= 400 && !this.f_8524_ && this.f_8525_ == this.f_8510_.m_20096_()) {
                  if ((!flag3 || !flag4) && !(this.f_8510_ instanceof AbstractArrow)) {
                     if (flag3) {
                        packet1 = new ClientboundMoveEntityPacket.Pos(this.f_8510_.m_142049_(), (short)((int)i), (short)((int)j), (short)((int)k), this.f_8510_.m_20096_());
                     } else if (flag4) {
                        packet1 = new ClientboundMoveEntityPacket.Rot(this.f_8510_.m_142049_(), (byte)l, (byte)k1, this.f_8510_.m_20096_());
                     }
                  } else {
                     packet1 = new ClientboundMoveEntityPacket.PosRot(this.f_8510_.m_142049_(), (short)((int)i), (short)((int)j), (short)((int)k), (byte)l, (byte)k1, this.f_8510_.m_20096_());
                  }
               } else {
                  this.f_8525_ = this.f_8510_.m_20096_();
                  this.f_8522_ = 0;
                  packet1 = new ClientboundTeleportEntityPacket(this.f_8510_);
               }
            }

            if ((this.f_8512_ || this.f_8510_.f_19812_ || this.f_8510_ instanceof LivingEntity && ((LivingEntity)this.f_8510_).m_21255_()) && this.f_8521_ > 0) {
               Vec3 vec31 = this.f_8510_.m_20184_();
               double d0 = vec31.m_82557_(this.f_8520_);
               if (d0 > 1.0E-7D || d0 > 0.0D && vec31.m_82556_() == 0.0D) {
                  this.f_8520_ = vec31;
                  this.f_8513_.accept(new ClientboundSetEntityMotionPacket(this.f_8510_.m_142049_(), this.f_8520_));
               }
            }

            if (packet1 != null) {
               this.f_8513_.accept(packet1);
            }

            this.m_8543_();
            if (flag3) {
               this.m_8544_();
            }

            if (flag4) {
               this.f_8517_ = l;
               this.f_8518_ = k1;
            }

            this.f_8524_ = false;
         }

         int j1 = Mth.m_14143_(this.f_8510_.m_6080_() * 256.0F / 360.0F);
         if (Math.abs(j1 - this.f_8519_) >= 1) {
            this.f_8513_.accept(new ClientboundRotateHeadPacket(this.f_8510_, (byte)j1));
            this.f_8519_ = j1;
         }

         this.f_8510_.f_19812_ = false;
      }

      ++this.f_8521_;
      if (this.f_8510_.f_19864_) {
         this.m_8538_(new ClientboundSetEntityMotionPacket(this.f_8510_));
         this.f_8510_.f_19864_ = false;
      }

   }

   public void m_8534_(ServerPlayer p_8535_) {
      this.f_8510_.m_6452_(p_8535_);
      p_8535_.f_8906_.m_141995_(new ClientboundRemoveEntitiesPacket(this.f_8510_.m_142049_()));
      net.minecraftforge.event.ForgeEventFactory.onStopEntityTracking(this.f_8510_, p_8535_);
   }

   public void m_8541_(ServerPlayer p_8542_) {
      this.m_8536_(p_8542_.f_8906_::m_141995_);
      this.f_8510_.m_6457_(p_8542_);
      net.minecraftforge.event.ForgeEventFactory.onStartEntityTracking(this.f_8510_, p_8542_);
   }

   public void m_8536_(Consumer<Packet<?>> p_8537_) {
      if (this.f_8510_.m_146910_()) {
         f_8508_.warn("Fetching packet for removed entity {}", (Object)this.f_8510_);
      }

      Packet<?> packet = this.f_8510_.m_5654_();
      this.f_8519_ = Mth.m_14143_(this.f_8510_.m_6080_() * 256.0F / 360.0F);
      p_8537_.accept(packet);
      if (!this.f_8510_.m_20088_().m_135388_()) {
         p_8537_.accept(new ClientboundSetEntityDataPacket(this.f_8510_.m_142049_(), this.f_8510_.m_20088_(), true));
      }

      boolean flag = this.f_8512_;
      if (this.f_8510_ instanceof LivingEntity) {
         Collection<AttributeInstance> collection = ((LivingEntity)this.f_8510_).m_21204_().m_22170_();
         if (!collection.isEmpty()) {
            p_8537_.accept(new ClientboundUpdateAttributesPacket(this.f_8510_.m_142049_(), collection));
         }

         if (((LivingEntity)this.f_8510_).m_21255_()) {
            flag = true;
         }
      }

      this.f_8520_ = this.f_8510_.m_20184_();
      if (flag && !(packet instanceof ClientboundAddMobPacket)) {
         p_8537_.accept(new ClientboundSetEntityMotionPacket(this.f_8510_.m_142049_(), this.f_8520_));
      }

      if (this.f_8510_ instanceof LivingEntity) {
         List<Pair<EquipmentSlot, ItemStack>> list = Lists.newArrayList();

         for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            ItemStack itemstack = ((LivingEntity)this.f_8510_).m_6844_(equipmentslot);
            if (!itemstack.m_41619_()) {
               list.add(Pair.of(equipmentslot, itemstack.m_41777_()));
            }
         }

         if (!list.isEmpty()) {
            p_8537_.accept(new ClientboundSetEquipmentPacket(this.f_8510_.m_142049_(), list));
         }
      }

      if (this.f_8510_ instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)this.f_8510_;

         for(MobEffectInstance mobeffectinstance : livingentity.m_21220_()) {
            p_8537_.accept(new ClientboundUpdateMobEffectPacket(this.f_8510_.m_142049_(), mobeffectinstance));
         }
      }

      if (!this.f_8510_.m_20197_().isEmpty()) {
         p_8537_.accept(new ClientboundSetPassengersPacket(this.f_8510_));
      }

      if (this.f_8510_.m_20159_()) {
         p_8537_.accept(new ClientboundSetPassengersPacket(this.f_8510_.m_20202_()));
      }

      if (this.f_8510_ instanceof Mob) {
         Mob mob = (Mob)this.f_8510_;
         if (mob.m_21523_()) {
            p_8537_.accept(new ClientboundSetEntityLinkPacket(mob, mob.m_21524_()));
         }
      }

   }

   private void m_8543_() {
      SynchedEntityData synchedentitydata = this.f_8510_.m_20088_();
      if (synchedentitydata.m_135352_()) {
         this.m_8538_(new ClientboundSetEntityDataPacket(this.f_8510_.m_142049_(), synchedentitydata, false));
      }

      if (this.f_8510_ instanceof LivingEntity) {
         Set<AttributeInstance> set = ((LivingEntity)this.f_8510_).m_21204_().m_22145_();
         if (!set.isEmpty()) {
            this.m_8538_(new ClientboundUpdateAttributesPacket(this.f_8510_.m_142049_(), set));
         }

         set.clear();
      }

   }

   private void m_8544_() {
      this.f_8514_ = ClientboundMoveEntityPacket.m_132511_(this.f_8510_.m_20185_());
      this.f_8515_ = ClientboundMoveEntityPacket.m_132511_(this.f_8510_.m_20186_());
      this.f_8516_ = ClientboundMoveEntityPacket.m_132511_(this.f_8510_.m_20189_());
   }

   public Vec3 m_8540_() {
      return ClientboundMoveEntityPacket.m_132515_(this.f_8514_, this.f_8515_, this.f_8516_);
   }

   private void m_8538_(Packet<?> p_8539_) {
      this.f_8513_.accept(p_8539_);
      if (this.f_8510_ instanceof ServerPlayer) {
         ((ServerPlayer)this.f_8510_).f_8906_.m_141995_(p_8539_);
      }

   }
}
