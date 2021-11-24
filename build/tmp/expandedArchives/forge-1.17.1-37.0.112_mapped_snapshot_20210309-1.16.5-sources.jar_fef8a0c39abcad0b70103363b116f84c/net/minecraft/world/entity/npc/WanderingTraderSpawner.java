package net.minecraft.world.entity.npc;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ServerLevelData;

public class WanderingTraderSpawner implements CustomSpawner {
   private static final int f_150051_ = 1200;
   public static final int f_150050_ = 24000;
   private static final int f_150052_ = 25;
   private static final int f_150053_ = 75;
   private static final int f_150054_ = 25;
   private static final int f_150055_ = 10;
   private static final int f_150056_ = 10;
   private final Random f_35908_ = new Random();
   private final ServerLevelData f_35909_;
   private int f_35910_;
   private int f_35911_;
   private int f_35912_;

   public WanderingTraderSpawner(ServerLevelData p_35914_) {
      this.f_35909_ = p_35914_;
      this.f_35910_ = 1200;
      this.f_35911_ = p_35914_.m_6530_();
      this.f_35912_ = p_35914_.m_6528_();
      if (this.f_35911_ == 0 && this.f_35912_ == 0) {
         this.f_35911_ = 24000;
         p_35914_.m_6391_(this.f_35911_);
         this.f_35912_ = 25;
         p_35914_.m_6387_(this.f_35912_);
      }

   }

   public int m_7995_(ServerLevel p_35922_, boolean p_35923_, boolean p_35924_) {
      if (!p_35922_.m_46469_().m_46207_(GameRules.f_46125_)) {
         return 0;
      } else if (--this.f_35910_ > 0) {
         return 0;
      } else {
         this.f_35910_ = 1200;
         this.f_35911_ -= 1200;
         this.f_35909_.m_6391_(this.f_35911_);
         if (this.f_35911_ > 0) {
            return 0;
         } else {
            this.f_35911_ = 24000;
            if (!p_35922_.m_46469_().m_46207_(GameRules.f_46134_)) {
               return 0;
            } else {
               int i = this.f_35912_;
               this.f_35912_ = Mth.m_14045_(this.f_35912_ + 25, 25, 75);
               this.f_35909_.m_6387_(this.f_35912_);
               if (this.f_35908_.nextInt(100) > i) {
                  return 0;
               } else if (this.m_35915_(p_35922_)) {
                  this.f_35912_ = 25;
                  return 1;
               } else {
                  return 0;
               }
            }
         }
      }
   }

   private boolean m_35915_(ServerLevel p_35916_) {
      Player player = p_35916_.m_8890_();
      if (player == null) {
         return true;
      } else if (this.f_35908_.nextInt(10) != 0) {
         return false;
      } else {
         BlockPos blockpos = player.m_142538_();
         int i = 48;
         PoiManager poimanager = p_35916_.m_8904_();
         Optional<BlockPos> optional = poimanager.m_27186_(PoiType.f_27347_.m_27392_(), (p_35933_) -> {
            return true;
         }, blockpos, 48, PoiManager.Occupancy.ANY);
         BlockPos blockpos1 = optional.orElse(blockpos);
         BlockPos blockpos2 = this.m_35928_(p_35916_, blockpos1, 48);
         if (blockpos2 != null && this.m_35925_(p_35916_, blockpos2)) {
            if (p_35916_.m_45837_(blockpos2).equals(Optional.of(Biomes.f_48173_))) {
               return false;
            }

            WanderingTrader wanderingtrader = EntityType.f_20494_.m_20600_(p_35916_, (CompoundTag)null, (Component)null, (Player)null, blockpos2, MobSpawnType.EVENT, false, false);
            if (wanderingtrader != null) {
               for(int j = 0; j < 2; ++j) {
                  this.m_35917_(p_35916_, wanderingtrader, 4);
               }

               this.f_35909_.m_8115_(wanderingtrader.m_142081_());
               wanderingtrader.m_35891_(48000);
               wanderingtrader.m_35883_(blockpos1);
               wanderingtrader.m_21446_(blockpos1, 16);
               return true;
            }
         }

         return false;
      }
   }

   private void m_35917_(ServerLevel p_35918_, WanderingTrader p_35919_, int p_35920_) {
      BlockPos blockpos = this.m_35928_(p_35918_, p_35919_.m_142538_(), p_35920_);
      if (blockpos != null) {
         TraderLlama traderllama = EntityType.f_20488_.m_20600_(p_35918_, (CompoundTag)null, (Component)null, (Player)null, blockpos, MobSpawnType.EVENT, false, false);
         if (traderllama != null) {
            traderllama.m_21463_(p_35919_, true);
         }
      }
   }

   @Nullable
   private BlockPos m_35928_(LevelReader p_35929_, BlockPos p_35930_, int p_35931_) {
      BlockPos blockpos = null;

      for(int i = 0; i < 10; ++i) {
         int j = p_35930_.m_123341_() + this.f_35908_.nextInt(p_35931_ * 2) - p_35931_;
         int k = p_35930_.m_123343_() + this.f_35908_.nextInt(p_35931_ * 2) - p_35931_;
         int l = p_35929_.m_6924_(Heightmap.Types.WORLD_SURFACE, j, k);
         BlockPos blockpos1 = new BlockPos(j, l, k);
         if (NaturalSpawner.m_47051_(SpawnPlacements.Type.ON_GROUND, p_35929_, blockpos1, EntityType.f_20494_)) {
            blockpos = blockpos1;
            break;
         }
      }

      return blockpos;
   }

   private boolean m_35925_(BlockGetter p_35926_, BlockPos p_35927_) {
      for(BlockPos blockpos : BlockPos.m_121940_(p_35927_, p_35927_.m_142082_(1, 2, 1))) {
         if (!p_35926_.m_8055_(blockpos).m_60812_(p_35926_, blockpos).m_83281_()) {
            return false;
         }
      }

      return true;
   }
}