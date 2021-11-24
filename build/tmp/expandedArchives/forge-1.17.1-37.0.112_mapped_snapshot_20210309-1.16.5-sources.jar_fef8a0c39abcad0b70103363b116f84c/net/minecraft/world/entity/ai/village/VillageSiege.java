package net.minecraft.world.entity.ai.village;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillageSiege implements CustomSpawner {
   private static final Logger f_26997_ = LogManager.getLogger();
   private boolean f_26998_;
   private VillageSiege.State f_26999_ = VillageSiege.State.SIEGE_DONE;
   private int f_27000_;
   private int f_27001_;
   private int f_27002_;
   private int f_27003_;
   private int f_27004_;

   public int m_7995_(ServerLevel p_27013_, boolean p_27014_, boolean p_27015_) {
      if (!p_27013_.m_46461_() && p_27014_) {
         float f = p_27013_.m_46942_(0.0F);
         if ((double)f == 0.5D) {
            this.f_26999_ = p_27013_.f_46441_.nextInt(10) == 0 ? VillageSiege.State.SIEGE_TONIGHT : VillageSiege.State.SIEGE_DONE;
         }

         if (this.f_26999_ == VillageSiege.State.SIEGE_DONE) {
            return 0;
         } else {
            if (!this.f_26998_) {
               if (!this.m_27007_(p_27013_)) {
                  return 0;
               }

               this.f_26998_ = true;
            }

            if (this.f_27001_ > 0) {
               --this.f_27001_;
               return 0;
            } else {
               this.f_27001_ = 2;
               if (this.f_27000_ > 0) {
                  this.m_27016_(p_27013_);
                  --this.f_27000_;
               } else {
                  this.f_26999_ = VillageSiege.State.SIEGE_DONE;
               }

               return 1;
            }
         }
      } else {
         this.f_26999_ = VillageSiege.State.SIEGE_DONE;
         this.f_26998_ = false;
         return 0;
      }
   }

   private boolean m_27007_(ServerLevel p_27008_) {
      for(Player player : p_27008_.m_6907_()) {
         if (!player.m_5833_()) {
            BlockPos blockpos = player.m_142538_();
            if (p_27008_.m_8802_(blockpos) && p_27008_.m_46857_(blockpos).m_47567_() != Biome.BiomeCategory.MUSHROOM) {
               for(int i = 0; i < 10; ++i) {
                  float f = p_27008_.f_46441_.nextFloat() * ((float)Math.PI * 2F);
                  this.f_27002_ = blockpos.m_123341_() + Mth.m_14143_(Mth.m_14089_(f) * 32.0F);
                  this.f_27003_ = blockpos.m_123342_();
                  this.f_27004_ = blockpos.m_123343_() + Mth.m_14143_(Mth.m_14031_(f) * 32.0F);
                  Vec3 siegeLocation = this.m_27009_(p_27008_, new BlockPos(this.f_27002_, this.f_27003_, this.f_27004_));
                  if (siegeLocation != null) {
                     if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.village.VillageSiegeEvent(this, p_27008_, player, siegeLocation))) return false;
                     this.f_27001_ = 0;
                     this.f_27000_ = 20;
                     break;
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   private void m_27016_(ServerLevel p_27017_) {
      Vec3 vec3 = this.m_27009_(p_27017_, new BlockPos(this.f_27002_, this.f_27003_, this.f_27004_));
      if (vec3 != null) {
         Zombie zombie;
         try {
            zombie = EntityType.f_20501_.m_20615_(p_27017_); //Forge: Direct Initialization is deprecated, use EntityType.
            zombie.m_6518_(p_27017_, p_27017_.m_6436_(zombie.m_142538_()), MobSpawnType.EVENT, (SpawnGroupData)null, (CompoundTag)null);
         } catch (Exception exception) {
            f_26997_.warn("Failed to create zombie for village siege at {}", vec3, exception);
            return;
         }

         zombie.m_7678_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, p_27017_.f_46441_.nextFloat() * 360.0F, 0.0F);
         p_27017_.m_47205_(zombie);
      }
   }

   @Nullable
   private Vec3 m_27009_(ServerLevel p_27010_, BlockPos p_27011_) {
      for(int i = 0; i < 10; ++i) {
         int j = p_27011_.m_123341_() + p_27010_.f_46441_.nextInt(16) - 8;
         int k = p_27011_.m_123343_() + p_27010_.f_46441_.nextInt(16) - 8;
         int l = p_27010_.m_6924_(Heightmap.Types.WORLD_SURFACE, j, k);
         BlockPos blockpos = new BlockPos(j, l, k);
         if (p_27010_.m_8802_(blockpos) && Monster.m_33017_(EntityType.f_20501_, p_27010_, MobSpawnType.EVENT, blockpos, p_27010_.f_46441_)) {
            return Vec3.m_82539_(blockpos);
         }
      }

      return null;
   }

   static enum State {
      SIEGE_CAN_ACTIVATE,
      SIEGE_TONIGHT,
      SIEGE_DONE;
   }
}
