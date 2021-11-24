package net.minecraft.world.item;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ArmorStandItem extends Item {
   public ArmorStandItem(Item.Properties p_40503_) {
      super(p_40503_);
   }

   public InteractionResult m_6225_(UseOnContext p_40510_) {
      Direction direction = p_40510_.m_43719_();
      if (direction == Direction.DOWN) {
         return InteractionResult.FAIL;
      } else {
         Level level = p_40510_.m_43725_();
         BlockPlaceContext blockplacecontext = new BlockPlaceContext(p_40510_);
         BlockPos blockpos = blockplacecontext.m_8083_();
         ItemStack itemstack = p_40510_.m_43722_();
         Vec3 vec3 = Vec3.m_82539_(blockpos);
         AABB aabb = EntityType.f_20529_.m_20680_().m_20384_(vec3.m_7096_(), vec3.m_7098_(), vec3.m_7094_());
         if (level.m_45768_((Entity)null, aabb, (p_40505_) -> {
            return true;
         }) && level.m_45933_((Entity)null, aabb).isEmpty()) {
            if (level instanceof ServerLevel) {
               ServerLevel serverlevel = (ServerLevel)level;
               ArmorStand armorstand = EntityType.f_20529_.m_20655_(serverlevel, itemstack.m_41783_(), (Component)null, p_40510_.m_43723_(), blockpos, MobSpawnType.SPAWN_EGG, true, true);
               if (armorstand == null) {
                  return InteractionResult.FAIL;
               }

               float f = (float)Mth.m_14143_((Mth.m_14177_(p_40510_.m_7074_() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
               armorstand.m_7678_(armorstand.m_20185_(), armorstand.m_20186_(), armorstand.m_20189_(), f, 0.0F);
               this.m_40506_(armorstand, level.f_46441_);
               serverlevel.m_47205_(armorstand);
               level.m_6263_((Player)null, armorstand.m_20185_(), armorstand.m_20186_(), armorstand.m_20189_(), SoundEvents.f_11684_, SoundSource.BLOCKS, 0.75F, 0.8F);
               level.m_151545_(p_40510_.m_43723_(), GameEvent.f_157810_, armorstand);
            }

            itemstack.m_41774_(1);
            return InteractionResult.m_19078_(level.f_46443_);
         } else {
            return InteractionResult.FAIL;
         }
      }
   }

   private void m_40506_(ArmorStand p_40507_, Random p_40508_) {
      Rotations rotations = p_40507_.m_31680_();
      float f = p_40508_.nextFloat() * 5.0F;
      float f1 = p_40508_.nextFloat() * 20.0F - 10.0F;
      Rotations rotations1 = new Rotations(rotations.m_123156_() + f, rotations.m_123157_() + f1, rotations.m_123158_());
      p_40507_.m_31597_(rotations1);
      rotations = p_40507_.m_31685_();
      f = p_40508_.nextFloat() * 10.0F - 5.0F;
      rotations1 = new Rotations(rotations.m_123156_(), rotations.m_123157_() + f, rotations.m_123158_());
      p_40507_.m_31616_(rotations1);
   }
}