package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class GoToClosestVillage extends Behavior<Villager> {
   private final float f_23074_;
   private final int f_23075_;

   public GoToClosestVillage(float p_23077_, int p_23078_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT));
      this.f_23074_ = p_23077_;
      this.f_23075_ = p_23078_;
   }

   protected boolean m_6114_(ServerLevel p_23087_, Villager p_23088_) {
      return !p_23087_.m_8802_(p_23088_.m_142538_());
   }

   protected void m_6735_(ServerLevel p_23090_, Villager p_23091_, long p_23092_) {
      PoiManager poimanager = p_23090_.m_8904_();
      int i = poimanager.m_27098_(SectionPos.m_123199_(p_23091_.m_142538_()));
      Vec3 vec3 = null;

      for(int j = 0; j < 5; ++j) {
         Vec3 vec31 = LandRandomPos.m_148503_(p_23091_, 15, 7, (p_147554_) -> {
            return (double)(-poimanager.m_27098_(SectionPos.m_123199_(p_147554_)));
         });
         if (vec31 != null) {
            int k = poimanager.m_27098_(SectionPos.m_123199_(new BlockPos(vec31)));
            if (k < i) {
               vec3 = vec31;
               break;
            }

            if (k == i) {
               vec3 = vec31;
            }
         }
      }

      if (vec3 != null) {
         p_23091_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(vec3, this.f_23074_, this.f_23075_));
      }

   }
}