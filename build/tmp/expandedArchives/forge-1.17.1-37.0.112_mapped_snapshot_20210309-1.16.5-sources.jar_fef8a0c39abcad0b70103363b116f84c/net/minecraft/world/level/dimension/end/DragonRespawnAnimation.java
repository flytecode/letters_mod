package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;

public enum DragonRespawnAnimation {
   START {
      public void m_6363_(ServerLevel p_64017_, EndDragonFight p_64018_, List<EndCrystal> p_64019_, int p_64020_, BlockPos p_64021_) {
         BlockPos blockpos = new BlockPos(0, 128, 0);

         for(EndCrystal endcrystal : p_64019_) {
            endcrystal.m_31052_(blockpos);
         }

         p_64018_.m_64087_(PREPARING_TO_SUMMON_PILLARS);
      }
   },
   PREPARING_TO_SUMMON_PILLARS {
      public void m_6363_(ServerLevel p_64026_, EndDragonFight p_64027_, List<EndCrystal> p_64028_, int p_64029_, BlockPos p_64030_) {
         if (p_64029_ < 100) {
            if (p_64029_ == 0 || p_64029_ == 50 || p_64029_ == 51 || p_64029_ == 52 || p_64029_ >= 95) {
               p_64026_.m_46796_(3001, new BlockPos(0, 128, 0), 0);
            }
         } else {
            p_64027_.m_64087_(SUMMONING_PILLARS);
         }

      }
   },
   SUMMONING_PILLARS {
      public void m_6363_(ServerLevel p_64035_, EndDragonFight p_64036_, List<EndCrystal> p_64037_, int p_64038_, BlockPos p_64039_) {
         int i = 40;
         boolean flag = p_64038_ % 40 == 0;
         boolean flag1 = p_64038_ % 40 == 39;
         if (flag || flag1) {
            List<SpikeFeature.EndSpike> list = SpikeFeature.m_66858_(p_64035_);
            int j = p_64038_ / 40;
            if (j < list.size()) {
               SpikeFeature.EndSpike spikefeature$endspike = list.get(j);
               if (flag) {
                  for(EndCrystal endcrystal : p_64037_) {
                     endcrystal.m_31052_(new BlockPos(spikefeature$endspike.m_66886_(), spikefeature$endspike.m_66899_() + 1, spikefeature$endspike.m_66893_()));
                  }
               } else {
                  int k = 10;

                  for(BlockPos blockpos : BlockPos.m_121940_(new BlockPos(spikefeature$endspike.m_66886_() - 10, spikefeature$endspike.m_66899_() - 10, spikefeature$endspike.m_66893_() - 10), new BlockPos(spikefeature$endspike.m_66886_() + 10, spikefeature$endspike.m_66899_() + 10, spikefeature$endspike.m_66893_() + 10))) {
                     p_64035_.m_7471_(blockpos, false);
                  }

                  p_64035_.m_46511_((Entity)null, (double)((float)spikefeature$endspike.m_66886_() + 0.5F), (double)spikefeature$endspike.m_66899_(), (double)((float)spikefeature$endspike.m_66893_() + 0.5F), 5.0F, Explosion.BlockInteraction.DESTROY);
                  SpikeConfiguration spikeconfiguration = new SpikeConfiguration(true, ImmutableList.of(spikefeature$endspike), new BlockPos(0, 128, 0));
                  Feature.f_65732_.m_65815_(spikeconfiguration).m_65385_(p_64035_, p_64035_.m_7726_().m_8481_(), new Random(), new BlockPos(spikefeature$endspike.m_66886_(), 45, spikefeature$endspike.m_66893_()));
               }
            } else if (flag) {
               p_64036_.m_64087_(SUMMONING_DRAGON);
            }
         }

      }
   },
   SUMMONING_DRAGON {
      public void m_6363_(ServerLevel p_64044_, EndDragonFight p_64045_, List<EndCrystal> p_64046_, int p_64047_, BlockPos p_64048_) {
         if (p_64047_ >= 100) {
            p_64045_.m_64087_(END);
            p_64045_.m_64101_();

            for(EndCrystal endcrystal : p_64046_) {
               endcrystal.m_31052_((BlockPos)null);
               p_64044_.m_46511_(endcrystal, endcrystal.m_20185_(), endcrystal.m_20186_(), endcrystal.m_20189_(), 6.0F, Explosion.BlockInteraction.NONE);
               endcrystal.m_146870_();
            }
         } else if (p_64047_ >= 80) {
            p_64044_.m_46796_(3001, new BlockPos(0, 128, 0), 0);
         } else if (p_64047_ == 0) {
            for(EndCrystal endcrystal1 : p_64046_) {
               endcrystal1.m_31052_(new BlockPos(0, 128, 0));
            }
         } else if (p_64047_ < 5) {
            p_64044_.m_46796_(3001, new BlockPos(0, 128, 0), 0);
         }

      }
   },
   END {
      public void m_6363_(ServerLevel p_64053_, EndDragonFight p_64054_, List<EndCrystal> p_64055_, int p_64056_, BlockPos p_64057_) {
      }
   };

   public abstract void m_6363_(ServerLevel p_64005_, EndDragonFight p_64006_, List<EndCrystal> p_64007_, int p_64008_, BlockPos p_64009_);
}