package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class PotionItem extends Item {
   private static final int f_151180_ = 32;

   public PotionItem(Item.Properties p_42979_) {
      super(p_42979_);
   }

   public ItemStack m_7968_() {
      return PotionUtils.m_43549_(super.m_7968_(), Potions.f_43599_);
   }

   public ItemStack m_5922_(ItemStack p_42984_, Level p_42985_, LivingEntity p_42986_) {
      Player player = p_42986_ instanceof Player ? (Player)p_42986_ : null;
      if (player instanceof ServerPlayer) {
         CriteriaTriggers.f_10592_.m_23682_((ServerPlayer)player, p_42984_);
      }

      if (!p_42985_.f_46443_) {
         for(MobEffectInstance mobeffectinstance : PotionUtils.m_43547_(p_42984_)) {
            if (mobeffectinstance.m_19544_().m_8093_()) {
               mobeffectinstance.m_19544_().m_19461_(player, player, p_42986_, mobeffectinstance.m_19564_(), 1.0D);
            } else {
               p_42986_.m_7292_(new MobEffectInstance(mobeffectinstance));
            }
         }
      }

      if (player != null) {
         player.m_36246_(Stats.f_12982_.m_12902_(this));
         if (!player.m_150110_().f_35937_) {
            p_42984_.m_41774_(1);
         }
      }

      if (player == null || !player.m_150110_().f_35937_) {
         if (p_42984_.m_41619_()) {
            return new ItemStack(Items.f_42590_);
         }

         if (player != null) {
            player.m_150109_().m_36054_(new ItemStack(Items.f_42590_));
         }
      }

      p_42985_.m_142346_(p_42986_, GameEvent.f_157805_, p_42986_.m_146901_());
      return p_42984_;
   }

   public int m_8105_(ItemStack p_43001_) {
      return 32;
   }

   public UseAnim m_6164_(ItemStack p_42997_) {
      return UseAnim.DRINK;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
      return ItemUtils.m_150959_(p_42993_, p_42994_, p_42995_);
   }

   public String m_5671_(ItemStack p_43003_) {
      return PotionUtils.m_43579_(p_43003_).m_43492_(this.m_5524_() + ".effect.");
   }

   public void m_7373_(ItemStack p_42988_, @Nullable Level p_42989_, List<Component> p_42990_, TooltipFlag p_42991_) {
      PotionUtils.m_43555_(p_42988_, p_42990_, 1.0F);
   }

   public boolean m_5812_(ItemStack p_42999_) {
      return super.m_5812_(p_42999_) || !PotionUtils.m_43547_(p_42999_).isEmpty();
   }

   public void m_6787_(CreativeModeTab p_42981_, NonNullList<ItemStack> p_42982_) {
      if (this.m_41389_(p_42981_)) {
         for(Potion potion : Registry.f_122828_) {
            if (potion != Potions.f_43598_) {
               p_42982_.add(PotionUtils.m_43549_(new ItemStack(this), potion));
            }
         }
      }

   }
}