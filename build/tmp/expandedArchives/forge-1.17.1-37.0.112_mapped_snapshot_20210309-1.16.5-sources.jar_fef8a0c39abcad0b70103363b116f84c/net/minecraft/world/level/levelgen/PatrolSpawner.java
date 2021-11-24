package net.minecraft.world.level.levelgen;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class PatrolSpawner implements CustomSpawner {
   private int f_64562_;

   public int m_7995_(ServerLevel p_64570_, boolean p_64571_, boolean p_64572_) {
      if (!p_64571_) {
         return 0;
      } else if (!p_64570_.m_46469_().m_46207_(GameRules.f_46124_)) {
         return 0;
      } else {
         Random random = p_64570_.f_46441_;
         --this.f_64562_;
         if (this.f_64562_ > 0) {
            return 0;
         } else {
            this.f_64562_ += 12000 + random.nextInt(1200);
            long i = p_64570_.m_46468_() / 24000L;
            if (i >= 5L && p_64570_.m_46461_()) {
               if (random.nextInt(5) != 0) {
                  return 0;
               } else {
                  int j = p_64570_.m_6907_().size();
                  if (j < 1) {
                     return 0;
                  } else {
                     Player player = p_64570_.m_6907_().get(random.nextInt(j));
                     if (player.m_5833_()) {
                        return 0;
                     } else if (p_64570_.m_8736_(player.m_142538_(), 2)) {
                        return 0;
                     } else {
                        int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                        int l = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                        BlockPos.MutableBlockPos blockpos$mutableblockpos = player.m_142538_().m_122032_().m_122184_(k, 0, l);
                        int i1 = 10;
                        if (!p_64570_.m_151572_(blockpos$mutableblockpos.m_123341_() - 10, blockpos$mutableblockpos.m_123343_() - 10, blockpos$mutableblockpos.m_123341_() + 10, blockpos$mutableblockpos.m_123343_() + 10)) {
                           return 0;
                        } else {
                           Biome biome = p_64570_.m_46857_(blockpos$mutableblockpos);
                           Biome.BiomeCategory biome$biomecategory = biome.m_47567_();
                           if (biome$biomecategory == Biome.BiomeCategory.MUSHROOM) {
                              return 0;
                           } else {
                              int j1 = 0;
                              int k1 = (int)Math.ceil((double)p_64570_.m_6436_(blockpos$mutableblockpos).m_19056_()) + 1;

                              for(int l1 = 0; l1 < k1; ++l1) {
                                 ++j1;
                                 blockpos$mutableblockpos.m_142448_(p_64570_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos$mutableblockpos).m_123342_());
                                 if (l1 == 0) {
                                    if (!this.m_64564_(p_64570_, blockpos$mutableblockpos, random, true)) {
                                       break;
                                    }
                                 } else {
                                    this.m_64564_(p_64570_, blockpos$mutableblockpos, random, false);
                                 }

                                 blockpos$mutableblockpos.m_142451_(blockpos$mutableblockpos.m_123341_() + random.nextInt(5) - random.nextInt(5));
                                 blockpos$mutableblockpos.m_142443_(blockpos$mutableblockpos.m_123343_() + random.nextInt(5) - random.nextInt(5));
                              }

                              return j1;
                           }
                        }
                     }
                  }
               }
            } else {
               return 0;
            }
         }
      }
   }

   private boolean m_64564_(ServerLevel p_64565_, BlockPos p_64566_, Random p_64567_, boolean p_64568_) {
      BlockState blockstate = p_64565_.m_8055_(p_64566_);
      if (!NaturalSpawner.m_47056_(p_64565_, p_64566_, blockstate, blockstate.m_60819_(), EntityType.f_20513_)) {
         return false;
      } else if (!PatrollingMonster.m_33056_(EntityType.f_20513_, p_64565_, MobSpawnType.PATROL, p_64566_, p_64567_)) {
         return false;
      } else {
         PatrollingMonster patrollingmonster = EntityType.f_20513_.m_20615_(p_64565_);
         if (patrollingmonster != null) {
            if (p_64568_) {
               patrollingmonster.m_33075_(true);
               patrollingmonster.m_33068_();
            }

            patrollingmonster.m_6034_((double)p_64566_.m_123341_(), (double)p_64566_.m_123342_(), (double)p_64566_.m_123343_());
            if(net.minecraftforge.common.ForgeHooks.canEntitySpawn(patrollingmonster, p_64565_, p_64566_.m_123341_(), p_64566_.m_123342_(), p_64566_.m_123343_(), null, MobSpawnType.PATROL) == -1) return false;
            patrollingmonster.m_6518_(p_64565_, p_64565_.m_6436_(p_64566_), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
            p_64565_.m_47205_(patrollingmonster);
            return true;
         } else {
            return false;
         }
      }
   }
}
