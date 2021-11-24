package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.phys.Vec3;

public class PathfindToRaidGoal<T extends Raider> extends Goal {
   private static final float f_148132_ = 1.0F;
   private final T f_25704_;

   public PathfindToRaidGoal(T p_25706_) {
      this.f_25704_ = p_25706_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      return this.f_25704_.m_5448_() == null && !this.f_25704_.m_20160_() && this.f_25704_.m_37886_() && !this.f_25704_.m_37885_().m_37706_() && !((ServerLevel)this.f_25704_.f_19853_).m_8802_(this.f_25704_.m_142538_());
   }

   public boolean m_8045_() {
      return this.f_25704_.m_37886_() && !this.f_25704_.m_37885_().m_37706_() && this.f_25704_.f_19853_ instanceof ServerLevel && !((ServerLevel)this.f_25704_.f_19853_).m_8802_(this.f_25704_.m_142538_());
   }

   public void m_8037_() {
      if (this.f_25704_.m_37886_()) {
         Raid raid = this.f_25704_.m_37885_();
         if (this.f_25704_.f_19797_ % 20 == 0) {
            this.m_25708_(raid);
         }

         if (!this.f_25704_.m_21691_()) {
            Vec3 vec3 = DefaultRandomPos.m_148412_(this.f_25704_, 15, 4, Vec3.m_82539_(raid.m_37780_()), (double)((float)Math.PI / 2F));
            if (vec3 != null) {
               this.f_25704_.m_21573_().m_26519_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 1.0D);
            }
         }
      }

   }

   private void m_25708_(Raid p_25709_) {
      if (p_25709_.m_37782_()) {
         Set<Raider> set = Sets.newHashSet();
         List<Raider> list = this.f_25704_.f_19853_.m_6443_(Raider.class, this.f_25704_.m_142469_().m_82400_(16.0D), (p_25712_) -> {
            return !p_25712_.m_37886_() && Raids.m_37965_(p_25712_, p_25709_);
         });
         set.addAll(list);

         for(Raider raider : set) {
            p_25709_.m_37713_(p_25709_.m_37771_(), raider, (BlockPos)null, true);
         }
      }

   }
}