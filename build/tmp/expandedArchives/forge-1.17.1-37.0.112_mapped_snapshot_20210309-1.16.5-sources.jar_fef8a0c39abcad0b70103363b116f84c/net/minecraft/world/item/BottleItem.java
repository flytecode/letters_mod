package net.minecraft.world.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BottleItem extends Item {
   public BottleItem(Item.Properties p_40648_) {
      super(p_40648_);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_40656_, Player p_40657_, InteractionHand p_40658_) {
      List<AreaEffectCloud> list = p_40656_.m_6443_(AreaEffectCloud.class, p_40657_.m_142469_().m_82400_(2.0D), (p_40650_) -> {
         return p_40650_ != null && p_40650_.m_6084_() && p_40650_.m_19749_() instanceof EnderDragon;
      });
      ItemStack itemstack = p_40657_.m_21120_(p_40658_);
      if (!list.isEmpty()) {
         AreaEffectCloud areaeffectcloud = list.get(0);
         areaeffectcloud.m_19712_(areaeffectcloud.m_19743_() - 0.5F);
         p_40656_.m_6263_((Player)null, p_40657_.m_20185_(), p_40657_.m_20186_(), p_40657_.m_20189_(), SoundEvents.f_11771_, SoundSource.NEUTRAL, 1.0F, 1.0F);
         p_40656_.m_142346_(p_40657_, GameEvent.f_157816_, p_40657_.m_142538_());
         return InteractionResultHolder.m_19092_(this.m_40651_(itemstack, p_40657_, new ItemStack(Items.f_42735_)), p_40656_.m_5776_());
      } else {
         HitResult hitresult = m_41435_(p_40656_, p_40657_, ClipContext.Fluid.SOURCE_ONLY);
         if (hitresult.m_6662_() == HitResult.Type.MISS) {
            return InteractionResultHolder.m_19098_(itemstack);
         } else {
            if (hitresult.m_6662_() == HitResult.Type.BLOCK) {
               BlockPos blockpos = ((BlockHitResult)hitresult).m_82425_();
               if (!p_40656_.m_7966_(p_40657_, blockpos)) {
                  return InteractionResultHolder.m_19098_(itemstack);
               }

               if (p_40656_.m_6425_(blockpos).m_76153_(FluidTags.f_13131_)) {
                  p_40656_.m_6263_(p_40657_, p_40657_.m_20185_(), p_40657_.m_20186_(), p_40657_.m_20189_(), SoundEvents.f_11770_, SoundSource.NEUTRAL, 1.0F, 1.0F);
                  p_40656_.m_142346_(p_40657_, GameEvent.f_157816_, blockpos);
                  return InteractionResultHolder.m_19092_(this.m_40651_(itemstack, p_40657_, PotionUtils.m_43549_(new ItemStack(Items.f_42589_), Potions.f_43599_)), p_40656_.m_5776_());
               }
            }

            return InteractionResultHolder.m_19098_(itemstack);
         }
      }
   }

   protected ItemStack m_40651_(ItemStack p_40652_, Player p_40653_, ItemStack p_40654_) {
      p_40653_.m_36246_(Stats.f_12982_.m_12902_(this));
      return ItemUtils.m_41813_(p_40652_, p_40653_, p_40654_);
   }
}