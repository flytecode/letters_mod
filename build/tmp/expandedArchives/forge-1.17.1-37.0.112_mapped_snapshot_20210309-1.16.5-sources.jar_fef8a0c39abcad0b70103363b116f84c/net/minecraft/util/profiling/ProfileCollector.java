package net.minecraft.util.profiling;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;

public interface ProfileCollector extends ProfilerFiller {
   ProfileResults m_5948_();

   @Nullable
   ActiveProfiler.PathEntry m_142431_(String p_145955_);

   Set<Pair<String, MetricCategory>> m_142579_();
}