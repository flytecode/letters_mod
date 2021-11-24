package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureTemplatePool {
   private static final Logger f_69247_ = LogManager.getLogger();
   private static final int f_161679_ = Integer.MIN_VALUE;
   public static final Codec<StructureTemplatePool> f_69245_ = RecordCodecBuilder.create((p_69267_) -> {
      return p_69267_.group(ResourceLocation.f_135803_.fieldOf("name").forGetter(StructureTemplatePool::m_69275_), ResourceLocation.f_135803_.fieldOf("fallback").forGetter(StructureTemplatePool::m_69263_), Codec.mapPair(StructurePoolElement.f_69153_.fieldOf("element"), Codec.intRange(1, 150).fieldOf("weight")).codec().listOf().fieldOf("elements").forGetter((p_161683_) -> {
         return p_161683_.f_69249_;
      })).apply(p_69267_, StructureTemplatePool::new);
   });
   public static final Codec<Supplier<StructureTemplatePool>> f_69246_ = RegistryFileCodec.m_135589_(Registry.f_122884_, f_69245_);
   private final ResourceLocation f_69248_;
   private final List<Pair<StructurePoolElement, Integer>> f_69249_;
   private final List<StructurePoolElement> f_69250_;
   private final ResourceLocation f_69251_;
   private int f_69252_ = Integer.MIN_VALUE;

   public StructureTemplatePool(ResourceLocation p_69255_, ResourceLocation p_69256_, List<Pair<StructurePoolElement, Integer>> p_69257_) {
      this.f_69248_ = p_69255_;
      this.f_69249_ = p_69257_;
      this.f_69250_ = Lists.newArrayList();

      for(Pair<StructurePoolElement, Integer> pair : p_69257_) {
         StructurePoolElement structurepoolelement = pair.getFirst();

         for(int i = 0; i < pair.getSecond(); ++i) {
            this.f_69250_.add(structurepoolelement);
         }
      }

      this.f_69251_ = p_69256_;
   }

   public StructureTemplatePool(ResourceLocation p_69259_, ResourceLocation p_69260_, List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> p_69261_, StructureTemplatePool.Projection p_69262_) {
      this.f_69248_ = p_69259_;
      this.f_69249_ = Lists.newArrayList();
      this.f_69250_ = Lists.newArrayList();

      for(Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer> pair : p_69261_) {
         StructurePoolElement structurepoolelement = pair.getFirst().apply(p_69262_);
         this.f_69249_.add(Pair.of(structurepoolelement, pair.getSecond()));

         for(int i = 0; i < pair.getSecond(); ++i) {
            this.f_69250_.add(structurepoolelement);
         }
      }

      this.f_69251_ = p_69260_;
   }

   public int m_69268_(StructureManager p_69269_) {
      if (this.f_69252_ == Integer.MIN_VALUE) {
         this.f_69252_ = this.f_69250_.stream().filter((p_161681_) -> {
            return p_161681_ != EmptyPoolElement.f_68856_;
         }).mapToInt((p_161686_) -> {
            return p_161686_.m_6867_(p_69269_, BlockPos.f_121853_, Rotation.NONE).m_71057_();
         }).max().orElse(0);
      }

      return this.f_69252_;
   }

   public ResourceLocation m_69263_() {
      return this.f_69251_;
   }

   public StructurePoolElement m_69273_(Random p_69274_) {
      return this.f_69250_.get(p_69274_.nextInt(this.f_69250_.size()));
   }

   public List<StructurePoolElement> m_69276_(Random p_69277_) {
      return ImmutableList.copyOf(ObjectArrays.shuffle(this.f_69250_.toArray(new StructurePoolElement[0]), p_69277_));
   }

   public ResourceLocation m_69275_() {
      return this.f_69248_;
   }

   public int m_69278_() {
      return this.f_69250_.size();
   }

   public static enum Projection implements StringRepresentable, net.minecraftforge.common.IExtensibleEnum {
      TERRAIN_MATCHING("terrain_matching", ImmutableList.of(new GravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1))),
      RIGID("rigid", ImmutableList.of());

      public static final Codec<StructureTemplatePool.Projection> f_69281_ = net.minecraftforge.common.IExtensibleEnum.createCodecForExtensibleEnum(StructureTemplatePool.Projection::values, StructureTemplatePool.Projection::m_69295_);
      private static final Map<String, StructureTemplatePool.Projection> f_69282_ = Arrays.stream(values()).collect(Collectors.toMap(StructureTemplatePool.Projection::m_69297_, (p_69294_) -> {
         return p_69294_;
      }));
      private final String f_69283_;
      private final ImmutableList<StructureProcessor> f_69284_;

      private Projection(String p_69290_, ImmutableList<StructureProcessor> p_69291_) {
         this.f_69283_ = p_69290_;
         this.f_69284_ = p_69291_;
      }

      public String m_69297_() {
         return this.f_69283_;
      }

      public static StructureTemplatePool.Projection m_69295_(String p_69296_) {
         return f_69282_.get(p_69296_);
      }

      public ImmutableList<StructureProcessor> m_69298_() {
         return this.f_69284_;
      }

      public String m_7912_() {
         return this.f_69283_;
      }

      public static Projection create(String enumName, String nameIn, ImmutableList<StructureProcessor> structureProcessorsIn) {
         throw new IllegalStateException("Enum not extended");
      }

      @Override
      @Deprecated
      public void init() {
         f_69282_.put(m_69297_(), this);
      }
   }
}
