package net.minecraft.world.entity.ai.goal;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class GolemRandomStrollInVillageGoal extends RandomStrollGoal {
   private static final int f_148106_ = 2;
   private static final int f_148107_ = 32;
   private static final int f_148108_ = 10;
   private static final int f_148109_ = 7;

   public GolemRandomStrollInVillageGoal(PathfinderMob p_25398_, double p_25399_) {
      super(p_25398_, p_25399_, 240, false);
   }

   @Nullable
   protected Vec3 m_7037_() {
      float f = this.f_25725_.f_19853_.f_46441_.nextFloat();
      if (this.f_25725_.f_19853_.f_46441_.nextFloat() < 0.3F) {
         return this.m_25410_();
      } else {
         Vec3 vec3;
         if (f < 0.7F) {
            vec3 = this.m_25411_();
            if (vec3 == null) {
               vec3 = this.m_25412_();
            }
         } else {
            vec3 = this.m_25412_();
            if (vec3 == null) {
               vec3 = this.m_25411_();
            }
         }

         return vec3 == null ? this.m_25410_() : vec3;
      }
   }

   @Nullable
   private Vec3 m_25410_() {
      return LandRandomPos.m_148488_(this.f_25725_, 10, 7);
   }

   @Nullable
   private Vec3 m_25411_() {
      ServerLevel serverlevel = (ServerLevel)this.f_25725_.f_19853_;
      List<Villager> list = serverlevel.m_142425_(EntityType.f_20492_, this.f_25725_.m_142469_().m_82400_(32.0D), this::m_25405_);
      if (list.isEmpty()) {
         return null;
      } else {
         Villager villager = list.get(this.f_25725_.f_19853_.f_46441_.nextInt(list.size()));
         Vec3 vec3 = villager.m_20182_();
         return LandRandomPos.m_148492_(this.f_25725_, 10, 7, vec3);
      }
   }

   @Nullable
   private Vec3 m_25412_() {
      SectionPos sectionpos = this.m_25413_();
      if (sectionpos == null) {
         return null;
      } else {
         BlockPos blockpos = this.m_25407_(sectionpos);
         return blockpos == null ? null : LandRandomPos.m_148492_(this.f_25725_, 10, 7, Vec3.m_82539_(blockpos));
      }
   }

   @Nullable
   private SectionPos m_25413_() {
      ServerLevel serverlevel = (ServerLevel)this.f_25725_.f_19853_;
      List<SectionPos> list = SectionPos.m_123201_(SectionPos.m_123194_(this.f_25725_), 2).filter((p_25402_) -> {
         return serverlevel.m_8828_(p_25402_) == 0;
      }).collect(Collectors.toList());
      return list.isEmpty() ? null : list.get(serverlevel.f_46441_.nextInt(list.size()));
   }

   @Nullable
   private BlockPos m_25407_(SectionPos p_25408_) {
      ServerLevel serverlevel = (ServerLevel)this.f_25725_.f_19853_;
      PoiManager poimanager = serverlevel.m_8904_();
      List<BlockPos> list = poimanager.m_27181_((p_25404_) -> {
         return true;
      }, p_25408_.m_123250_(), 8, PoiManager.Occupancy.IS_OCCUPIED).map(PoiRecord::m_27257_).collect(Collectors.toList());
      return list.isEmpty() ? null : list.get(serverlevel.f_46441_.nextInt(list.size()));
   }

   private boolean m_25405_(Villager p_25406_) {
      return p_25406_.m_35392_(this.f_25725_.f_19853_.m_46467_());
   }
}