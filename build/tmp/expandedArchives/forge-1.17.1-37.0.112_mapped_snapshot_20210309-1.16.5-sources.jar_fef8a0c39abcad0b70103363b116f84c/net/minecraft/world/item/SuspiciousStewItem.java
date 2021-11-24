package net.minecraft.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SuspiciousStewItem extends Item {
   public static final String f_151225_ = "Effects";
   public static final String f_151226_ = "EffectId";
   public static final String f_151227_ = "EffectDuration";

   public SuspiciousStewItem(Item.Properties p_43257_) {
      super(p_43257_);
   }

   public static void m_43258_(ItemStack p_43259_, MobEffect p_43260_, int p_43261_) {
      CompoundTag compoundtag = p_43259_.m_41784_();
      ListTag listtag = compoundtag.m_128437_("Effects", 9);
      CompoundTag compoundtag1 = new CompoundTag();
      compoundtag1.m_128344_("EffectId", (byte)MobEffect.m_19459_(p_43260_));
      compoundtag1.m_128405_("EffectDuration", p_43261_);
      listtag.add(compoundtag1);
      compoundtag.m_128365_("Effects", listtag);
   }

   public ItemStack m_5922_(ItemStack p_43263_, Level p_43264_, LivingEntity p_43265_) {
      ItemStack itemstack = super.m_5922_(p_43263_, p_43264_, p_43265_);
      CompoundTag compoundtag = p_43263_.m_41783_();
      if (compoundtag != null && compoundtag.m_128425_("Effects", 9)) {
         ListTag listtag = compoundtag.m_128437_("Effects", 10);

         for(int i = 0; i < listtag.size(); ++i) {
            int j = 160;
            CompoundTag compoundtag1 = listtag.m_128728_(i);
            if (compoundtag1.m_128425_("EffectDuration", 3)) {
               j = compoundtag1.m_128451_("EffectDuration");
            }

            MobEffect mobeffect = MobEffect.m_19453_(compoundtag1.m_128445_("EffectId"));
            if (mobeffect != null) {
               p_43265_.m_7292_(new MobEffectInstance(mobeffect, j));
            }
         }
      }

      return p_43265_ instanceof Player && ((Player)p_43265_).m_150110_().f_35937_ ? itemstack : new ItemStack(Items.f_42399_);
   }
}