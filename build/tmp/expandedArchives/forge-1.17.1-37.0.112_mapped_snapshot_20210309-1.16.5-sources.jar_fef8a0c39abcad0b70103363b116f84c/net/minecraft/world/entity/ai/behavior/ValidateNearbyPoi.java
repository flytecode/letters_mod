package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ValidateNearbyPoi extends Behavior<LivingEntity> {
   private static final int f_148036_ = 16;
   private final MemoryModuleType<GlobalPos> f_24515_;
   private final Predicate<PoiType> f_24516_;

   public ValidateNearbyPoi(PoiType p_24518_, MemoryModuleType<GlobalPos> p_24519_) {
      super(ImmutableMap.of(p_24519_, MemoryStatus.VALUE_PRESENT));
      this.f_24516_ = p_24518_.m_27392_();
      this.f_24515_ = p_24519_;
   }

   protected boolean m_6114_(ServerLevel p_24521_, LivingEntity p_24522_) {
      GlobalPos globalpos = p_24522_.m_6274_().m_21952_(this.f_24515_).get();
      return p_24521_.m_46472_() == globalpos.m_122640_() && globalpos.m_122646_().m_123306_(p_24522_.m_20182_(), 16.0D);
   }

   protected void m_6735_(ServerLevel p_24524_, LivingEntity p_24525_, long p_24526_) {
      Brain<?> brain = p_24525_.m_6274_();
      GlobalPos globalpos = brain.m_21952_(this.f_24515_).get();
      BlockPos blockpos = globalpos.m_122646_();
      ServerLevel serverlevel = p_24524_.m_142572_().m_129880_(globalpos.m_122640_());
      if (serverlevel != null && !this.m_24527_(serverlevel, blockpos)) {
         if (this.m_24530_(serverlevel, blockpos, p_24525_)) {
            brain.m_21936_(this.f_24515_);
            p_24524_.m_8904_().m_27154_(blockpos);
            DebugPackets.m_133719_(p_24524_, blockpos);
         }
      } else {
         brain.m_21936_(this.f_24515_);
      }

   }

   private boolean m_24530_(ServerLevel p_24531_, BlockPos p_24532_, LivingEntity p_24533_) {
      BlockState blockstate = p_24531_.m_8055_(p_24532_);
      return blockstate.m_60620_(BlockTags.f_13038_) && blockstate.m_61143_(BedBlock.f_49441_) && !p_24533_.m_5803_();
   }

   private boolean m_24527_(ServerLevel p_24528_, BlockPos p_24529_) {
      return !p_24528_.m_8904_().m_27091_(p_24529_, this.f_24516_);
   }
}