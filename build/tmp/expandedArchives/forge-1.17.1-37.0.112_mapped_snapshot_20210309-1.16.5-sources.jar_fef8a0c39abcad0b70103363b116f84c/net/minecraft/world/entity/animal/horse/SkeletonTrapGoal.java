package net.minecraft.world.entity.animal.horse;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class SkeletonTrapGoal extends Goal {
   private final SkeletonHorse f_30925_;

   public SkeletonTrapGoal(SkeletonHorse p_30927_) {
      this.f_30925_ = p_30927_;
   }

   public boolean m_8036_() {
      return this.f_30925_.f_19853_.m_45914_(this.f_30925_.m_20185_(), this.f_30925_.m_20186_(), this.f_30925_.m_20189_(), 10.0D);
   }

   public void m_8037_() {
      ServerLevel serverlevel = (ServerLevel)this.f_30925_.f_19853_;
      // Forge: Trigger the trap in a tick task to avoid crashes when mods add goals to skeleton horses
      // (MC-206338/Forge PR #7509)
      serverlevel.m_142572_().m_6937_(new net.minecraft.server.TickTask(serverlevel.m_142572_().m_129921_(), () -> {
      if (!this.f_30925_.m_6084_()) return;
      DifficultyInstance difficultyinstance = serverlevel.m_6436_(this.f_30925_.m_142538_());
      this.f_30925_.m_30923_(false);
      this.f_30925_.m_30651_(true);
      this.f_30925_.m_146762_(0);
      LightningBolt lightningbolt = EntityType.f_20465_.m_20615_(serverlevel);
      lightningbolt.m_6027_(this.f_30925_.m_20185_(), this.f_30925_.m_20186_(), this.f_30925_.m_20189_());
      lightningbolt.m_20874_(true);
      serverlevel.m_7967_(lightningbolt);
      Skeleton skeleton = this.m_30931_(difficultyinstance, this.f_30925_);
      skeleton.m_20329_(this.f_30925_);
      serverlevel.m_47205_(skeleton);

      for(int i = 0; i < 3; ++i) {
         AbstractHorse abstracthorse = this.m_30929_(difficultyinstance);
         Skeleton skeleton1 = this.m_30931_(difficultyinstance, abstracthorse);
         skeleton1.m_20329_(abstracthorse);
         abstracthorse.m_5997_(this.f_30925_.m_21187_().nextGaussian() * 0.5D, 0.0D, this.f_30925_.m_21187_().nextGaussian() * 0.5D);
         serverlevel.m_47205_(abstracthorse);
      }
      }));
   }

   private AbstractHorse m_30929_(DifficultyInstance p_30930_) {
      SkeletonHorse skeletonhorse = EntityType.f_20525_.m_20615_(this.f_30925_.f_19853_);
      skeletonhorse.m_6518_((ServerLevel)this.f_30925_.f_19853_, p_30930_, MobSpawnType.TRIGGERED, (SpawnGroupData)null, (CompoundTag)null);
      skeletonhorse.m_6034_(this.f_30925_.m_20185_(), this.f_30925_.m_20186_(), this.f_30925_.m_20189_());
      skeletonhorse.f_19802_ = 60;
      skeletonhorse.m_21530_();
      skeletonhorse.m_30651_(true);
      skeletonhorse.m_146762_(0);
      return skeletonhorse;
   }

   private Skeleton m_30931_(DifficultyInstance p_30932_, AbstractHorse p_30933_) {
      Skeleton skeleton = EntityType.f_20524_.m_20615_(p_30933_.f_19853_);
      skeleton.m_6518_((ServerLevel)p_30933_.f_19853_, p_30932_, MobSpawnType.TRIGGERED, (SpawnGroupData)null, (CompoundTag)null);
      skeleton.m_6034_(p_30933_.m_20185_(), p_30933_.m_20186_(), p_30933_.m_20189_());
      skeleton.f_19802_ = 60;
      skeleton.m_21530_();
      if (skeleton.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
         skeleton.m_8061_(EquipmentSlot.HEAD, new ItemStack(Items.f_42468_));
      }

      skeleton.m_8061_(EquipmentSlot.MAINHAND, EnchantmentHelper.m_44877_(skeleton.m_21187_(), this.m_30934_(skeleton.m_21205_()), (int)(5.0F + p_30932_.m_19057_() * (float)skeleton.m_21187_().nextInt(18)), false));
      skeleton.m_8061_(EquipmentSlot.HEAD, EnchantmentHelper.m_44877_(skeleton.m_21187_(), this.m_30934_(skeleton.m_6844_(EquipmentSlot.HEAD)), (int)(5.0F + p_30932_.m_19057_() * (float)skeleton.m_21187_().nextInt(18)), false));
      return skeleton;
   }

   private ItemStack m_30934_(ItemStack p_30935_) {
      p_30935_.m_41749_("Enchantments");
      return p_30935_;
   }
}
