package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public class InteractWithDoor extends Behavior<LivingEntity> {
   private static final int f_147585_ = 20;
   private static final double f_147586_ = 2.0D;
   private static final double f_147587_ = 2.0D;
   @Nullable
   private Node f_23288_;
   private int f_23289_;

   public InteractWithDoor() {
      super(ImmutableMap.of(MemoryModuleType.f_26377_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26379_, MemoryStatus.REGISTERED));
   }

   protected boolean m_6114_(ServerLevel p_23292_, LivingEntity p_23293_) {
      Path path = p_23293_.m_6274_().m_21952_(MemoryModuleType.f_26377_).get();
      if (!path.m_77387_() && !path.m_77392_()) {
         if (!Objects.equals(this.f_23288_, path.m_77401_())) {
            this.f_23289_ = 20;
            return true;
         } else {
            if (this.f_23289_ > 0) {
               --this.f_23289_;
            }

            return this.f_23289_ == 0;
         }
      } else {
         return false;
      }
   }

   protected void m_6735_(ServerLevel p_23295_, LivingEntity p_23296_, long p_23297_) {
      Path path = p_23296_.m_6274_().m_21952_(MemoryModuleType.f_26377_).get();
      this.f_23288_ = path.m_77401_();
      Node node = path.m_77402_();
      Node node1 = path.m_77401_();
      BlockPos blockpos = node.m_77288_();
      BlockState blockstate = p_23295_.m_8055_(blockpos);
      if (blockstate.m_60620_(BlockTags.f_13095_)) {
         DoorBlock doorblock = (DoorBlock)blockstate.m_60734_();
         if (!doorblock.m_52815_(blockstate)) {
            doorblock.m_153165_(p_23296_, p_23295_, blockstate, blockpos, true);
         }

         this.m_23325_(p_23295_, p_23296_, blockpos);
      }

      BlockPos blockpos1 = node1.m_77288_();
      BlockState blockstate1 = p_23295_.m_8055_(blockpos1);
      if (blockstate1.m_60620_(BlockTags.f_13095_)) {
         DoorBlock doorblock1 = (DoorBlock)blockstate1.m_60734_();
         if (!doorblock1.m_52815_(blockstate1)) {
            doorblock1.m_153165_(p_23296_, p_23295_, blockstate1, blockpos1, true);
            this.m_23325_(p_23295_, p_23296_, blockpos1);
         }
      }

      m_23298_(p_23295_, p_23296_, node, node1);
   }

   public static void m_23298_(ServerLevel p_23299_, LivingEntity p_23300_, @Nullable Node p_23301_, @Nullable Node p_23302_) {
      Brain<?> brain = p_23300_.m_6274_();
      if (brain.m_21874_(MemoryModuleType.f_26379_)) {
         Iterator<GlobalPos> iterator = brain.m_21952_(MemoryModuleType.f_26379_).get().iterator();

         while(iterator.hasNext()) {
            GlobalPos globalpos = iterator.next();
            BlockPos blockpos = globalpos.m_122646_();
            if ((p_23301_ == null || !p_23301_.m_77288_().equals(blockpos)) && (p_23302_ == null || !p_23302_.m_77288_().equals(blockpos))) {
               if (m_23307_(p_23299_, p_23300_, globalpos)) {
                  iterator.remove();
               } else {
                  BlockState blockstate = p_23299_.m_8055_(blockpos);
                  if (!blockstate.m_60620_(BlockTags.f_13095_)) {
                     iterator.remove();
                  } else {
                     DoorBlock doorblock = (DoorBlock)blockstate.m_60734_();
                     if (!doorblock.m_52815_(blockstate)) {
                        iterator.remove();
                     } else if (m_23303_(p_23299_, p_23300_, blockpos)) {
                        iterator.remove();
                     } else {
                        doorblock.m_153165_(p_23300_, p_23299_, blockstate, blockpos, false);
                        iterator.remove();
                     }
                  }
               }
            }
         }
      }

   }

   private static boolean m_23303_(ServerLevel p_23304_, LivingEntity p_23305_, BlockPos p_23306_) {
      Brain<?> brain = p_23305_.m_6274_();
      return !brain.m_21874_(MemoryModuleType.f_148204_) ? false : brain.m_21952_(MemoryModuleType.f_148204_).get().stream().filter((p_23317_) -> {
         return p_23317_.m_6095_() == p_23305_.m_6095_();
      }).filter((p_23320_) -> {
         return p_23306_.m_123306_(p_23320_.m_20182_(), 2.0D);
      }).anyMatch((p_23314_) -> {
         return m_23321_(p_23304_, p_23314_, p_23306_);
      });
   }

   private static boolean m_23321_(ServerLevel p_23322_, LivingEntity p_23323_, BlockPos p_23324_) {
      if (!p_23323_.m_6274_().m_21874_(MemoryModuleType.f_26377_)) {
         return false;
      } else {
         Path path = p_23323_.m_6274_().m_21952_(MemoryModuleType.f_26377_).get();
         if (path.m_77392_()) {
            return false;
         } else {
            Node node = path.m_77402_();
            if (node == null) {
               return false;
            } else {
               Node node1 = path.m_77401_();
               return p_23324_.equals(node.m_77288_()) || p_23324_.equals(node1.m_77288_());
            }
         }
      }
   }

   private static boolean m_23307_(ServerLevel p_23308_, LivingEntity p_23309_, GlobalPos p_23310_) {
      return p_23310_.m_122640_() != p_23308_.m_46472_() || !p_23310_.m_122646_().m_123306_(p_23309_.m_20182_(), 2.0D);
   }

   private void m_23325_(ServerLevel p_23326_, LivingEntity p_23327_, BlockPos p_23328_) {
      Brain<?> brain = p_23327_.m_6274_();
      GlobalPos globalpos = GlobalPos.m_122643_(p_23326_.m_46472_(), p_23328_);
      if (brain.m_21952_(MemoryModuleType.f_26379_).isPresent()) {
         brain.m_21952_(MemoryModuleType.f_26379_).get().add(globalpos);
      } else {
         brain.m_21879_(MemoryModuleType.f_26379_, Sets.newHashSet(globalpos));
      }

   }
}