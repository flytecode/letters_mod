package net.minecraft.world.item;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BoatItem extends Item {
   private static final Predicate<Entity> f_40615_ = EntitySelector.f_20408_.and(Entity::m_6087_);
   private final Boat.Type f_40616_;

   public BoatItem(Boat.Type p_40619_, Item.Properties p_40620_) {
      super(p_40620_);
      this.f_40616_ = p_40619_;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_40622_, Player p_40623_, InteractionHand p_40624_) {
      ItemStack itemstack = p_40623_.m_21120_(p_40624_);
      HitResult hitresult = m_41435_(p_40622_, p_40623_, ClipContext.Fluid.ANY);
      if (hitresult.m_6662_() == HitResult.Type.MISS) {
         return InteractionResultHolder.m_19098_(itemstack);
      } else {
         Vec3 vec3 = p_40623_.m_20252_(1.0F);
         double d0 = 5.0D;
         List<Entity> list = p_40622_.m_6249_(p_40623_, p_40623_.m_142469_().m_82369_(vec3.m_82490_(5.0D)).m_82400_(1.0D), f_40615_);
         if (!list.isEmpty()) {
            Vec3 vec31 = p_40623_.m_146892_();

            for(Entity entity : list) {
               AABB aabb = entity.m_142469_().m_82400_((double)entity.m_6143_());
               if (aabb.m_82390_(vec31)) {
                  return InteractionResultHolder.m_19098_(itemstack);
               }
            }
         }

         if (hitresult.m_6662_() == HitResult.Type.BLOCK) {
            Boat boat = new Boat(p_40622_, hitresult.m_82450_().f_82479_, hitresult.m_82450_().f_82480_, hitresult.m_82450_().f_82481_);
            boat.m_38332_(this.f_40616_);
            boat.m_146922_(p_40623_.m_146908_());
            if (!p_40622_.m_45756_(boat, boat.m_142469_().m_82400_(-0.1D))) {
               return InteractionResultHolder.m_19100_(itemstack);
            } else {
               if (!p_40622_.f_46443_) {
                  p_40622_.m_7967_(boat);
                  p_40622_.m_142346_(p_40623_, GameEvent.f_157810_, new BlockPos(hitresult.m_82450_()));
                  if (!p_40623_.m_150110_().f_35937_) {
                     itemstack.m_41774_(1);
                  }
               }

               p_40623_.m_36246_(Stats.f_12982_.m_12902_(this));
               return InteractionResultHolder.m_19092_(itemstack, p_40622_.m_5776_());
            }
         } else {
            return InteractionResultHolder.m_19098_(itemstack);
         }
      }
   }
}