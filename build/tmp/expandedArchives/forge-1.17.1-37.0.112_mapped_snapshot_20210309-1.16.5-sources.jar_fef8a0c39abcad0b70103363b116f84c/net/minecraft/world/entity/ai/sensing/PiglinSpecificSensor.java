package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PiglinSpecificSensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_148205_, MemoryModuleType.f_148204_, MemoryModuleType.f_26333_, MemoryModuleType.f_26345_, MemoryModuleType.f_26354_, MemoryModuleType.f_26343_, MemoryModuleType.f_26344_, MemoryModuleType.f_26347_, MemoryModuleType.f_26346_, MemoryModuleType.f_26352_, MemoryModuleType.f_26353_, MemoryModuleType.f_26356_);
   }

   protected void m_5578_(ServerLevel p_26726_, LivingEntity p_26727_) {
      Brain<?> brain = p_26727_.m_6274_();
      brain.m_21886_(MemoryModuleType.f_26356_, m_26734_(p_26726_, p_26727_));
      Optional<Mob> optional = Optional.empty();
      Optional<Hoglin> optional1 = Optional.empty();
      Optional<Hoglin> optional2 = Optional.empty();
      Optional<Piglin> optional3 = Optional.empty();
      Optional<LivingEntity> optional4 = Optional.empty();
      Optional<Player> optional5 = Optional.empty();
      Optional<Player> optional6 = Optional.empty();
      int i = 0;
      List<AbstractPiglin> list = Lists.newArrayList();
      List<AbstractPiglin> list1 = Lists.newArrayList();

      for(LivingEntity livingentity : brain.m_21952_(MemoryModuleType.f_148205_).orElse(ImmutableList.of())) {
         if (livingentity instanceof Hoglin) {
            Hoglin hoglin = (Hoglin)livingentity;
            if (hoglin.m_6162_() && !optional2.isPresent()) {
               optional2 = Optional.of(hoglin);
            } else if (hoglin.m_34552_()) {
               ++i;
               if (!optional1.isPresent() && hoglin.m_34555_()) {
                  optional1 = Optional.of(hoglin);
               }
            }
         } else if (livingentity instanceof PiglinBrute) {
            list.add((PiglinBrute)livingentity);
         } else if (livingentity instanceof Piglin) {
            Piglin piglin = (Piglin)livingentity;
            if (piglin.m_6162_() && !optional3.isPresent()) {
               optional3 = Optional.of(piglin);
            } else if (piglin.m_34667_()) {
               list.add(piglin);
            }
         } else if (livingentity instanceof Player) {
            Player player = (Player)livingentity;
            if (!optional5.isPresent() && p_26727_.m_6779_(livingentity) && !PiglinAi.m_34808_(player)) {
               optional5 = Optional.of(player);
            }

            if (!optional6.isPresent() && !player.m_5833_() && PiglinAi.m_34883_(player)) {
               optional6 = Optional.of(player);
            }
         } else if (optional.isPresent() || !(livingentity instanceof WitherSkeleton) && !(livingentity instanceof WitherBoss)) {
            if (!optional4.isPresent() && PiglinAi.m_34806_(livingentity.m_6095_())) {
               optional4 = Optional.of(livingentity);
            }
         } else {
            optional = Optional.of((Mob)livingentity);
         }
      }

      for(LivingEntity livingentity1 : brain.m_21952_(MemoryModuleType.f_148204_).orElse(ImmutableList.of())) {
         if (livingentity1 instanceof AbstractPiglin && ((AbstractPiglin)livingentity1).m_34667_()) {
            list1.add((AbstractPiglin)livingentity1);
         }
      }

      brain.m_21886_(MemoryModuleType.f_26333_, optional);
      brain.m_21886_(MemoryModuleType.f_26343_, optional1);
      brain.m_21886_(MemoryModuleType.f_26344_, optional2);
      brain.m_21886_(MemoryModuleType.f_26351_, optional4);
      brain.m_21886_(MemoryModuleType.f_26345_, optional5);
      brain.m_21886_(MemoryModuleType.f_26354_, optional6);
      brain.m_21879_(MemoryModuleType.f_26346_, list1);
      brain.m_21879_(MemoryModuleType.f_26347_, list);
      brain.m_21879_(MemoryModuleType.f_26352_, list.size());
      brain.m_21879_(MemoryModuleType.f_26353_, i);
   }

   private static Optional<BlockPos> m_26734_(ServerLevel p_26735_, LivingEntity p_26736_) {
      return BlockPos.m_121930_(p_26736_.m_142538_(), 8, 4, (p_26733_) -> {
         return m_26728_(p_26735_, p_26733_);
      });
   }

   private static boolean m_26728_(ServerLevel p_26729_, BlockPos p_26730_) {
      BlockState blockstate = p_26729_.m_8055_(p_26730_);
      boolean flag = blockstate.m_60620_(BlockTags.f_13042_);
      return flag && blockstate.m_60713_(Blocks.f_50684_) ? CampfireBlock.m_51319_(blockstate) : flag;
   }
}