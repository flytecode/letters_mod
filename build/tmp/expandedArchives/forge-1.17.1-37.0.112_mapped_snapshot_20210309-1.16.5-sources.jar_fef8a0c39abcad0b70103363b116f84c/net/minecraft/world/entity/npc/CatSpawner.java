package net.minecraft.world.entity.npc;

import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.AABB;

public class CatSpawner implements CustomSpawner {
   private static final int f_149996_ = 1200;
   private int f_35324_;

   public int m_7995_(ServerLevel p_35330_, boolean p_35331_, boolean p_35332_) {
      if (p_35332_ && p_35330_.m_46469_().m_46207_(GameRules.f_46134_)) {
         --this.f_35324_;
         if (this.f_35324_ > 0) {
            return 0;
         } else {
            this.f_35324_ = 1200;
            Player player = p_35330_.m_8890_();
            if (player == null) {
               return 0;
            } else {
               Random random = p_35330_.f_46441_;
               int i = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
               int j = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
               BlockPos blockpos = player.m_142538_().m_142082_(i, 0, j);
               int k = 10;
               if (!p_35330_.m_151572_(blockpos.m_123341_() - 10, blockpos.m_123343_() - 10, blockpos.m_123341_() + 10, blockpos.m_123343_() + 10)) {
                  return 0;
               } else {
                  if (NaturalSpawner.m_47051_(SpawnPlacements.Type.ON_GROUND, p_35330_, blockpos, EntityType.f_20553_)) {
                     if (p_35330_.m_8736_(blockpos, 2)) {
                        return this.m_35326_(p_35330_, blockpos);
                     }

                     if (p_35330_.m_8595_().m_47285_(blockpos, true, StructureFeature.f_67021_).m_73603_()) {
                        return this.m_35336_(p_35330_, blockpos);
                     }
                  }

                  return 0;
               }
            }
         }
      } else {
         return 0;
      }
   }

   private int m_35326_(ServerLevel p_35327_, BlockPos p_35328_) {
      int i = 48;
      if (p_35327_.m_8904_().m_27121_(PoiType.f_27346_.m_27392_(), p_35328_, 48, PoiManager.Occupancy.IS_OCCUPIED) > 4L) {
         List<Cat> list = p_35327_.m_45976_(Cat.class, (new AABB(p_35328_)).m_82377_(48.0D, 8.0D, 48.0D));
         if (list.size() < 5) {
            return this.m_35333_(p_35328_, p_35327_);
         }
      }

      return 0;
   }

   private int m_35336_(ServerLevel p_35337_, BlockPos p_35338_) {
      int i = 16;
      List<Cat> list = p_35337_.m_45976_(Cat.class, (new AABB(p_35338_)).m_82377_(16.0D, 8.0D, 16.0D));
      return list.size() < 1 ? this.m_35333_(p_35338_, p_35337_) : 0;
   }

   private int m_35333_(BlockPos p_35334_, ServerLevel p_35335_) {
      Cat cat = EntityType.f_20553_.m_20615_(p_35335_);
      if (cat == null) {
         return 0;
      } else {
         cat.m_20035_(p_35334_, 0.0F, 0.0F); // Fix MC-147659: Some witch huts spawn the incorrect cat
         if(net.minecraftforge.common.ForgeHooks.canEntitySpawn(cat, p_35335_, p_35334_.m_123341_(), p_35334_.m_123342_(), p_35334_.m_123343_(), null, MobSpawnType.NATURAL) == -1) return 0;
         cat.m_6518_(p_35335_, p_35335_.m_6436_(p_35334_), MobSpawnType.NATURAL, (SpawnGroupData)null, (CompoundTag)null);
         p_35335_.m_47205_(cat);
         return 1;
      }
   }
}
