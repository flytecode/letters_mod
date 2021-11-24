package net.minecraft.world.entity.ai.gossip;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum GossipType {
   MAJOR_NEGATIVE("major_negative", -5, 100, 10, 10),
   MINOR_NEGATIVE("minor_negative", -1, 200, 20, 20),
   MINOR_POSITIVE("minor_positive", 1, 200, 1, 5),
   MAJOR_POSITIVE("major_positive", 5, 100, 0, 100),
   TRADING("trading", 1, 25, 2, 20);

   public static final int f_148182_ = 25;
   public static final int f_148183_ = 20;
   public static final int f_148184_ = 2;
   public final String f_26273_;
   public final int f_26274_;
   public final int f_26275_;
   public final int f_26276_;
   public final int f_26277_;
   private static final Map<String, GossipType> f_26278_ = Stream.of(values()).collect(ImmutableMap.toImmutableMap((p_26290_) -> {
      return p_26290_.f_26273_;
   }, Function.identity()));

   private GossipType(String p_26284_, int p_26285_, int p_26286_, int p_26287_, int p_26288_) {
      this.f_26273_ = p_26284_;
      this.f_26274_ = p_26285_;
      this.f_26275_ = p_26286_;
      this.f_26276_ = p_26287_;
      this.f_26277_ = p_26288_;
   }

   @Nullable
   public static GossipType m_26291_(String p_26292_) {
      return f_26278_.get(p_26292_);
   }
}