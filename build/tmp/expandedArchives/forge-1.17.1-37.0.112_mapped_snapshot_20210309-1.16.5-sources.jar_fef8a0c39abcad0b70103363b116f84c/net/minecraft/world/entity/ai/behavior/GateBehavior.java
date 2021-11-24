package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class GateBehavior<E extends LivingEntity> extends Behavior<E> {
   private final Set<MemoryModuleType<?>> f_22868_;
   private final GateBehavior.OrderPolicy f_22869_;
   private final GateBehavior.RunningPolicy f_22870_;
   private final ShufflingList<Behavior<? super E>> f_22871_ = new ShufflingList<>();

   public GateBehavior(Map<MemoryModuleType<?>, MemoryStatus> p_22873_, Set<MemoryModuleType<?>> p_22874_, GateBehavior.OrderPolicy p_22875_, GateBehavior.RunningPolicy p_22876_, List<Pair<Behavior<? super E>, Integer>> p_22877_) {
      super(p_22873_);
      this.f_22868_ = p_22874_;
      this.f_22869_ = p_22875_;
      this.f_22870_ = p_22876_;
      p_22877_.forEach((p_22892_) -> {
         this.f_22871_.m_147929_(p_22892_.getFirst(), p_22892_.getSecond());
      });
   }

   protected boolean m_6737_(ServerLevel p_22894_, E p_22895_, long p_22896_) {
      return this.f_22871_.m_147932_().filter((p_22920_) -> {
         return p_22920_.m_22536_() == Behavior.Status.RUNNING;
      }).anyMatch((p_22912_) -> {
         return p_22912_.m_6737_(p_22894_, p_22895_, p_22896_);
      });
   }

   protected boolean m_7773_(long p_22879_) {
      return false;
   }

   protected void m_6735_(ServerLevel p_22881_, E p_22882_, long p_22883_) {
      this.f_22869_.m_147527_(this.f_22871_);
      this.f_22870_.m_142144_(this.f_22871_.m_147932_(), p_22881_, p_22882_, p_22883_);
   }

   protected void m_6725_(ServerLevel p_22916_, E p_22917_, long p_22918_) {
      this.f_22871_.m_147932_().filter((p_22914_) -> {
         return p_22914_.m_22536_() == Behavior.Status.RUNNING;
      }).forEach((p_22901_) -> {
         p_22901_.m_22558_(p_22916_, p_22917_, p_22918_);
      });
   }

   protected void m_6732_(ServerLevel p_22905_, E p_22906_, long p_22907_) {
      this.f_22871_.m_147932_().filter((p_22903_) -> {
         return p_22903_.m_22536_() == Behavior.Status.RUNNING;
      }).forEach((p_22888_) -> {
         p_22888_.m_22562_(p_22905_, p_22906_, p_22907_);
      });
      this.f_22868_.forEach(p_22906_.m_6274_()::m_21936_);
   }

   public String toString() {
      Set<? extends Behavior<? super E>> set = this.f_22871_.m_147932_().filter((p_22890_) -> {
         return p_22890_.m_22536_() == Behavior.Status.RUNNING;
      }).collect(Collectors.toSet());
      return "(" + this.getClass().getSimpleName() + "): " + set;
   }

   public static enum OrderPolicy {
      ORDERED((p_147530_) -> {
      }),
      SHUFFLED(ShufflingList::m_147922_);

      private final Consumer<ShufflingList<?>> f_22924_;

      private OrderPolicy(Consumer<ShufflingList<?>> p_22930_) {
         this.f_22924_ = p_22930_;
      }

      public void m_147527_(ShufflingList<?> p_147528_) {
         this.f_22924_.accept(p_147528_);
      }
   }

   public static enum RunningPolicy {
      RUN_ONE {
         public <E extends LivingEntity> void m_142144_(Stream<Behavior<? super E>> p_147537_, ServerLevel p_147538_, E p_147539_, long p_147540_) {
            p_147537_.filter((p_22965_) -> {
               return p_22965_.m_22536_() == Behavior.Status.STOPPED;
            }).filter((p_22963_) -> {
               return p_22963_.m_22554_(p_147538_, p_147539_, p_147540_);
            }).findFirst();
         }
      },
      TRY_ALL {
         public <E extends LivingEntity> void m_142144_(Stream<Behavior<? super E>> p_147542_, ServerLevel p_147543_, E p_147544_, long p_147545_) {
            p_147542_.filter((p_22980_) -> {
               return p_22980_.m_22536_() == Behavior.Status.STOPPED;
            }).forEach((p_22978_) -> {
               p_22978_.m_22554_(p_147543_, p_147544_, p_147545_);
            });
         }
      };

      public abstract <E extends LivingEntity> void m_142144_(Stream<Behavior<? super E>> p_147532_, ServerLevel p_147533_, E p_147534_, long p_147535_);
   }
}