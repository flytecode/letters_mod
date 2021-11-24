package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class ShearsDispenseItemBehavior extends OptionalDispenseItemBehavior {
   protected ItemStack m_7498_(BlockSource p_123580_, ItemStack p_123581_) {
      Level level = p_123580_.m_7727_();
      if (!level.m_5776_()) {
         BlockPos blockpos = p_123580_.m_7961_().m_142300_(p_123580_.m_6414_().m_61143_(DispenserBlock.f_52659_));
         this.m_123573_(m_123576_((ServerLevel)level, blockpos) || m_123582_((ServerLevel)level, blockpos));
         if (this.m_123570_() && p_123581_.m_41629_(1, level.m_5822_(), (ServerPlayer)null)) {
            p_123581_.m_41764_(0);
         }
      }

      return p_123581_;
   }

   private static boolean m_123576_(ServerLevel p_123577_, BlockPos p_123578_) {
      BlockState blockstate = p_123577_.m_8055_(p_123578_);
      if (blockstate.m_60620_(BlockTags.f_13072_)) {
         int i = blockstate.m_61143_(BeehiveBlock.f_49564_);
         if (i >= 5) {
            p_123577_.m_5594_((Player)null, p_123578_, SoundEvents.f_11697_, SoundSource.BLOCKS, 1.0F, 1.0F);
            BeehiveBlock.m_49600_(p_123577_, p_123578_);
            ((BeehiveBlock)blockstate.m_60734_()).m_49594_(p_123577_, blockstate, p_123578_, (Player)null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
            p_123577_.m_142346_((Entity)null, GameEvent.f_157781_, p_123578_);
            return true;
         }
      }

      return false;
   }

   private static boolean m_123582_(ServerLevel p_123583_, BlockPos p_123584_) {
      for(LivingEntity livingentity : p_123583_.m_6443_(LivingEntity.class, new AABB(p_123584_), EntitySelector.f_20408_)) {
         if (livingentity instanceof Shearable) {
            Shearable shearable = (Shearable)livingentity;
            if (shearable.m_6220_()) {
               shearable.m_5851_(SoundSource.BLOCKS);
               p_123583_.m_142346_((Entity)null, GameEvent.f_157781_, p_123584_);
               return true;
            }
         }
      }

      return false;
   }
}