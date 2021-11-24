package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class AssignProfessionFromJobSite extends Behavior<Villager> {
   public AssignProfessionFromJobSite() {
      super(ImmutableMap.of(MemoryModuleType.f_26361_, MemoryStatus.VALUE_PRESENT));
   }

   protected boolean m_6114_(ServerLevel p_22450_, Villager p_22451_) {
      BlockPos blockpos = p_22451_.m_6274_().m_21952_(MemoryModuleType.f_26361_).get().m_122646_();
      return blockpos.m_123306_(p_22451_.m_20182_(), 2.0D) || p_22451_.m_35504_();
   }

   protected void m_6735_(ServerLevel p_22453_, Villager p_22454_, long p_22455_) {
      GlobalPos globalpos = p_22454_.m_6274_().m_21952_(MemoryModuleType.f_26361_).get();
      p_22454_.m_6274_().m_21936_(MemoryModuleType.f_26361_);
      p_22454_.m_6274_().m_21879_(MemoryModuleType.f_26360_, globalpos);
      p_22453_.m_7605_(p_22454_, (byte)14);
      if (p_22454_.m_7141_().m_35571_() == VillagerProfession.f_35585_) {
         MinecraftServer minecraftserver = p_22453_.m_142572_();
         Optional.ofNullable(minecraftserver.m_129880_(globalpos.m_122640_())).flatMap((p_22467_) -> {
            return p_22467_.m_8904_().m_27177_(globalpos.m_122646_());
         }).flatMap((p_22457_) -> {
            return Registry.f_122869_.m_123024_().filter((p_147412_) -> {
               return p_147412_.m_35622_() == p_22457_;
            }).findFirst();
         }).ifPresent((p_22464_) -> {
            p_22454_.m_141967_(p_22454_.m_7141_().m_35565_(p_22464_));
            p_22454_.m_35483_(p_22453_);
         });
      }
   }
}