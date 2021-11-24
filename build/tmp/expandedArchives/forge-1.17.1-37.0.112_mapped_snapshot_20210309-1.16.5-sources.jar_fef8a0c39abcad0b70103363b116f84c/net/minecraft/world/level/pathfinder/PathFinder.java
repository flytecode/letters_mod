package net.minecraft.world.level.pathfinder;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;

public class PathFinder {
   private static final float f_164714_ = 1.5F;
   private final Node[] f_77420_ = new Node[32];
   private final int f_77421_;
   private final NodeEvaluator f_77422_;
   private static final boolean f_164715_ = false;
   private final BinaryHeap f_77423_ = new BinaryHeap();

   public PathFinder(NodeEvaluator p_77425_, int p_77426_) {
      this.f_77422_ = p_77425_;
      this.f_77421_ = p_77426_;
   }

   @Nullable
   public Path m_77427_(PathNavigationRegion p_77428_, Mob p_77429_, Set<BlockPos> p_77430_, float p_77431_, int p_77432_, float p_77433_) {
      this.f_77423_.m_77081_();
      this.f_77422_.m_6028_(p_77428_, p_77429_);
      Node node = this.f_77422_.m_7171_();
      Map<Target, BlockPos> map = p_77430_.stream().collect(Collectors.toMap((p_77448_) -> {
         return this.f_77422_.m_7568_((double)p_77448_.m_123341_(), (double)p_77448_.m_123342_(), (double)p_77448_.m_123343_());
      }, Function.identity()));
      Path path = this.m_164716_(p_77428_.m_151625_(), node, map, p_77431_, p_77432_, p_77433_);
      this.f_77422_.m_6802_();
      return path;
   }

   @Nullable
   private Path m_164716_(ProfilerFiller p_164717_, Node p_164718_, Map<Target, BlockPos> p_164719_, float p_164720_, int p_164721_, float p_164722_) {
      p_164717_.m_6180_("find_path");
      p_164717_.m_142259_(MetricCategory.PATH_FINDING);
      Set<Target> set = p_164719_.keySet();
      p_164718_.f_77275_ = 0.0F;
      p_164718_.f_77276_ = this.m_77444_(p_164718_, set);
      p_164718_.f_77277_ = p_164718_.f_77276_;
      this.f_77423_.m_77081_();
      this.f_77423_.m_77084_(p_164718_);
      Set<Node> set1 = ImmutableSet.of();
      int i = 0;
      Set<Target> set2 = Sets.newHashSetWithExpectedSize(set.size());
      int j = (int)((float)this.f_77421_ * p_164722_);

      while(!this.f_77423_.m_77092_()) {
         ++i;
         if (i >= j) {
            break;
         }

         Node node = this.f_77423_.m_77091_();
         node.f_77279_ = true;

         for(Target target : set) {
            if (node.m_77304_(target) <= (float)p_164721_) {
               target.m_77509_();
               set2.add(target);
            }
         }

         if (!set2.isEmpty()) {
            break;
         }

         if (!(node.m_77293_(p_164718_) >= p_164720_)) {
            int k = this.f_77422_.m_6065_(this.f_77420_, node);

            for(int l = 0; l < k; ++l) {
               Node node1 = this.f_77420_[l];
               float f = node.m_77293_(node1);
               node1.f_77280_ = node.f_77280_ + f;
               float f1 = node.f_77275_ + f + node1.f_77281_;
               if (node1.f_77280_ < p_164720_ && (!node1.m_77303_() || f1 < node1.f_77275_)) {
                  node1.f_77278_ = node;
                  node1.f_77275_ = f1;
                  node1.f_77276_ = this.m_77444_(node1, set) * 1.5F;
                  if (node1.m_77303_()) {
                     this.f_77423_.m_77086_(node1, node1.f_77275_ + node1.f_77276_);
                  } else {
                     node1.f_77277_ = node1.f_77275_ + node1.f_77276_;
                     this.f_77423_.m_77084_(node1);
                  }
               }
            }
         }
      }

      Optional<Path> optional = !set2.isEmpty() ? set2.stream().map((p_77454_) -> {
         return this.m_77434_(p_77454_.m_77508_(), p_164719_.get(p_77454_), true);
      }).min(Comparator.comparingInt(Path::m_77398_)) : set.stream().map((p_77451_) -> {
         return this.m_77434_(p_77451_.m_77508_(), p_164719_.get(p_77451_), false);
      }).min(Comparator.comparingDouble(Path::m_77407_).thenComparingInt(Path::m_77398_));
      p_164717_.m_7238_();
      return !optional.isPresent() ? null : optional.get();
   }

   private float m_77444_(Node p_77445_, Set<Target> p_77446_) {
      float f = Float.MAX_VALUE;

      for(Target target : p_77446_) {
         float f1 = p_77445_.m_77293_(target);
         target.m_77503_(f1, p_77445_);
         f = Math.min(f1, f);
      }

      return f;
   }

   private Path m_77434_(Node p_77435_, BlockPos p_77436_, boolean p_77437_) {
      List<Node> list = Lists.newArrayList();
      Node node = p_77435_;
      list.add(0, p_77435_);

      while(node.f_77278_ != null) {
         node = node.f_77278_;
         list.add(0, node);
      }

      return new Path(list, p_77436_, p_77437_);
   }
}