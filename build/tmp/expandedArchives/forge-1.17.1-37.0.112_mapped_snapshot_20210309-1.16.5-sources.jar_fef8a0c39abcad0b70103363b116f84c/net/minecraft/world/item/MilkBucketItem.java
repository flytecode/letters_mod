package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MilkBucketItem extends Item {
   private static final int f_151133_ = 32;

   public MilkBucketItem(Item.Properties p_42921_) {
      super(p_42921_);
   }

   public ItemStack m_5922_(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_) {
      if (!p_42924_.f_46443_) p_42925_.curePotionEffects(p_42923_); // FORGE - move up so stack.shrink does not turn stack into air
      if (p_42925_ instanceof ServerPlayer) {
         ServerPlayer serverplayer = (ServerPlayer)p_42925_;
         CriteriaTriggers.f_10592_.m_23682_(serverplayer, p_42923_);
         serverplayer.m_36246_(Stats.f_12982_.m_12902_(this));
      }

      if (p_42925_ instanceof Player && !((Player)p_42925_).m_150110_().f_35937_) {
         p_42923_.m_41774_(1);
      }

      return p_42923_.m_41619_() ? new ItemStack(Items.f_42446_) : p_42923_;
   }

   public int m_8105_(ItemStack p_42933_) {
      return 32;
   }

   public UseAnim m_6164_(ItemStack p_42931_) {
      return UseAnim.DRINK;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_42927_, Player p_42928_, InteractionHand p_42929_) {
      return ItemUtils.m_150959_(p_42927_, p_42928_, p_42929_);
   }

   @Override
   public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @javax.annotation.Nullable net.minecraft.nbt.CompoundTag nbt) {
      return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
   }
}
