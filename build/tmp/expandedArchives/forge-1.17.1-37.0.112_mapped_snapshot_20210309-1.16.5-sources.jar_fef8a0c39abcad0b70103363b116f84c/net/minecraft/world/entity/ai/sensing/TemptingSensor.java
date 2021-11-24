package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class TemptingSensor extends Sensor<PathfinderMob> {
   public static final int f_148320_ = 10;
   private static final TargetingConditions f_148321_ = TargetingConditions.m_148353_().m_26883_(10.0D).m_148355_();
   private final Ingredient f_148322_;

   public TemptingSensor(Ingredient p_148325_) {
      this.f_148322_ = p_148325_;
   }

   protected void m_5578_(ServerLevel p_148331_, PathfinderMob p_148332_) {
      Brain<?> brain = p_148332_.m_6274_();
      List<Player> list = p_148331_.m_6907_().stream().filter(EntitySelector.f_20408_).filter((p_148342_) -> {
         return f_148321_.m_26885_(p_148332_, p_148342_);
      }).filter((p_148335_) -> {
         return p_148332_.m_19950_(p_148335_, 10.0D);
      }).filter(this::m_148336_).sorted(Comparator.comparingDouble(p_148332_::m_20280_)).collect(Collectors.toList());
      if (!list.isEmpty()) {
         Player player = list.get(0);
         brain.m_21879_(MemoryModuleType.f_148196_, player);
      } else {
         brain.m_21936_(MemoryModuleType.f_148196_);
      }

   }

   private boolean m_148336_(Player p_148337_) {
      return this.m_148338_(p_148337_.m_21205_()) || this.m_148338_(p_148337_.m_21206_());
   }

   private boolean m_148338_(ItemStack p_148339_) {
      return this.f_148322_.test(p_148339_);
   }

   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_148196_);
   }
}