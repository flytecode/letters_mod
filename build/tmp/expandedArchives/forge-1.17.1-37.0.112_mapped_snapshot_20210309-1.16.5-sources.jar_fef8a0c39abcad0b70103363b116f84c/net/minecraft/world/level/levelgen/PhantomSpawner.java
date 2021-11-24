package net.minecraft.world.level.levelgen;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class PhantomSpawner implements CustomSpawner {
   private int f_64573_;

   public int m_7995_(ServerLevel p_64576_, boolean p_64577_, boolean p_64578_) {
      if (!p_64577_) {
         return 0;
      } else if (!p_64576_.m_46469_().m_46207_(GameRules.f_46155_)) {
         return 0;
      } else {
         Random random = p_64576_.f_46441_;
         --this.f_64573_;
         if (this.f_64573_ > 0) {
            return 0;
         } else {
            this.f_64573_ += (60 + random.nextInt(60)) * 20;
            if (p_64576_.m_7445_() < 5 && p_64576_.m_6042_().m_63935_()) {
               return 0;
            } else {
               int i = 0;

               for(Player player : p_64576_.m_6907_()) {
                  if (!player.m_5833_()) {
                     BlockPos blockpos = player.m_142538_();
                     if (!p_64576_.m_6042_().m_63935_() || blockpos.m_123342_() >= p_64576_.m_5736_() && p_64576_.m_45527_(blockpos)) {
                        DifficultyInstance difficultyinstance = p_64576_.m_6436_(blockpos);
                        if (difficultyinstance.m_19049_(random.nextFloat() * 3.0F)) {
                           ServerStatsCounter serverstatscounter = ((ServerPlayer)player).m_8951_();
                           int j = Mth.m_14045_(serverstatscounter.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12992_)), 1, Integer.MAX_VALUE);
                           int k = 24000;
                           if (random.nextInt(j) >= 72000) {
                              BlockPos blockpos1 = blockpos.m_6630_(20 + random.nextInt(15)).m_142385_(-10 + random.nextInt(21)).m_142383_(-10 + random.nextInt(21));
                              BlockState blockstate = p_64576_.m_8055_(blockpos1);
                              FluidState fluidstate = p_64576_.m_6425_(blockpos1);
                              if (NaturalSpawner.m_47056_(p_64576_, blockpos1, blockstate, fluidstate, EntityType.f_20509_)) {
                                 SpawnGroupData spawngroupdata = null;
                                 int l = 1 + random.nextInt(difficultyinstance.m_19048_().m_19028_() + 1);

                                 for(int i1 = 0; i1 < l; ++i1) {
                                    Phantom phantom = EntityType.f_20509_.m_20615_(p_64576_);
                                    phantom.m_20035_(blockpos1, 0.0F, 0.0F);
                                    if(net.minecraftforge.common.ForgeHooks.canEntitySpawn(phantom, p_64576_, blockpos1.m_123341_(), blockpos1.m_123342_(), blockpos1.m_123343_(), null, MobSpawnType.NATURAL) == -1) return 0;
                                    spawngroupdata = phantom.m_6518_(p_64576_, difficultyinstance, MobSpawnType.NATURAL, spawngroupdata, (CompoundTag)null);
                                    p_64576_.m_47205_(phantom);
                                 }

                                 i += l;
                              }
                           }
                        }
                     }
                  }
               }

               return i;
            }
         }
      }
   }
}
