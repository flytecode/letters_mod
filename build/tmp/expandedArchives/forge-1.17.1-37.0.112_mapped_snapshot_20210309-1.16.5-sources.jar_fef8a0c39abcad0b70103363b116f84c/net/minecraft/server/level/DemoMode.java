package net.minecraft.server.level;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class DemoMode extends ServerPlayerGameMode {
   public static final int f_143201_ = 5;
   public static final int f_143202_ = 120500;
   private boolean f_140734_;
   private boolean f_140735_;
   private int f_140736_;
   private int f_140737_;

   public DemoMode(ServerPlayer p_143204_) {
      super(p_143204_);
   }

   public void m_7712_() {
      super.m_7712_();
      ++this.f_140737_;
      long i = this.f_9244_.m_46467_();
      long j = i / 24000L + 1L;
      if (!this.f_140734_ && this.f_140737_ > 20) {
         this.f_140734_ = true;
         this.f_9245_.f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 0.0F));
      }

      this.f_140735_ = i > 120500L;
      if (this.f_140735_) {
         ++this.f_140736_;
      }

      if (i % 24000L == 500L) {
         if (j <= 6L) {
            if (j == 6L) {
               this.f_9245_.f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 104.0F));
            } else {
               this.f_9245_.m_6352_(new TranslatableComponent("demo.day." + j), Util.f_137441_);
            }
         }
      } else if (j == 1L) {
         if (i == 100L) {
            this.f_9245_.f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 101.0F));
         } else if (i == 175L) {
            this.f_9245_.f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 102.0F));
         } else if (i == 250L) {
            this.f_9245_.f_8906_.m_141995_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132158_, 103.0F));
         }
      } else if (j == 5L && i % 24000L == 22000L) {
         this.f_9245_.m_6352_(new TranslatableComponent("demo.day.warning"), Util.f_137441_);
      }

   }

   private void m_140757_() {
      if (this.f_140736_ > 100) {
         this.f_9245_.m_6352_(new TranslatableComponent("demo.reminder"), Util.f_137441_);
         this.f_140736_ = 0;
      }

   }

   public void m_7391_(BlockPos p_140753_, ServerboundPlayerActionPacket.Action p_140754_, Direction p_140755_, int p_140756_) {
      if (this.f_140735_) {
         this.m_140757_();
      } else {
         super.m_7391_(p_140753_, p_140754_, p_140755_, p_140756_);
      }
   }

   public InteractionResult m_6261_(ServerPlayer p_140742_, Level p_140743_, ItemStack p_140744_, InteractionHand p_140745_) {
      if (this.f_140735_) {
         this.m_140757_();
         return InteractionResult.PASS;
      } else {
         return super.m_6261_(p_140742_, p_140743_, p_140744_, p_140745_);
      }
   }

   public InteractionResult m_7179_(ServerPlayer p_140747_, Level p_140748_, ItemStack p_140749_, InteractionHand p_140750_, BlockHitResult p_140751_) {
      if (this.f_140735_) {
         this.m_140757_();
         return InteractionResult.PASS;
      } else {
         return super.m_7179_(p_140747_, p_140748_, p_140749_, p_140750_, p_140751_);
      }
   }
}