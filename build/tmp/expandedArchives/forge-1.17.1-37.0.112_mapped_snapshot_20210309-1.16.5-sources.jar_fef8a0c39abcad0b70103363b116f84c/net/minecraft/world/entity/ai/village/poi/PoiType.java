package net.minecraft.world.entity.ai.village.poi;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class PoiType extends net.minecraftforge.registries.ForgeRegistryEntry<PoiType> {
   private static final Supplier<Set<PoiType>> f_27353_ = Suppliers.memoize(() -> {
      return Registry.f_122869_.m_123024_().map(VillagerProfession::m_35622_).collect(Collectors.toSet());
   });
   public static final Predicate<PoiType> f_27329_ = (p_27399_) -> {
      return f_27353_.get().contains(p_27399_);
   };
   public static final Predicate<PoiType> f_27330_ = (p_27394_) -> {
      return true;
   };
   private static final Set<BlockState> f_27354_ = ImmutableList.of(Blocks.f_50028_, Blocks.f_50029_, Blocks.f_50025_, Blocks.f_50026_, Blocks.f_50023_, Blocks.f_50021_, Blocks.f_50027_, Blocks.f_50017_, Blocks.f_50022_, Blocks.f_50019_, Blocks.f_50068_, Blocks.f_50067_, Blocks.f_50020_, Blocks.f_50024_, Blocks.f_50066_, Blocks.f_50018_).stream().flatMap((p_27389_) -> {
      return p_27389_.m_49965_().m_61056_().stream();
   }).filter((p_27396_) -> {
      return p_27396_.m_61143_(BedBlock.f_49440_) == BedPart.HEAD;
   }).collect(ImmutableSet.toImmutableSet());
   private static final Set<BlockState> f_148686_ = ImmutableList.of(Blocks.f_50256_, Blocks.f_152477_, Blocks.f_152476_, Blocks.f_152478_).stream().flatMap((p_148697_) -> {
      return p_148697_.m_49965_().m_61056_().stream();
   }).collect(ImmutableSet.toImmutableSet());
   private static final Map<BlockState, PoiType> f_27323_ = net.minecraftforge.registries.GameData.getBlockStatePointOfInterestTypeMap();
   public static final PoiType f_27331_ = m_27379_("unemployed", ImmutableSet.of(), 1, f_27329_, 1);
   public static final PoiType f_27332_ = m_27374_("armorer", m_27372_(Blocks.f_50620_), 1, 1);
   public static final PoiType f_27333_ = m_27374_("butcher", m_27372_(Blocks.f_50619_), 1, 1);
   public static final PoiType f_27334_ = m_27374_("cartographer", m_27372_(Blocks.f_50621_), 1, 1);
   public static final PoiType f_27335_ = m_27374_("cleric", m_27372_(Blocks.f_50255_), 1, 1);
   public static final PoiType f_27336_ = m_27374_("farmer", m_27372_(Blocks.f_50715_), 1, 1);
   public static final PoiType f_27337_ = m_27374_("fisherman", m_27372_(Blocks.f_50618_), 1, 1);
   public static final PoiType f_27338_ = m_27374_("fletcher", m_27372_(Blocks.f_50622_), 1, 1);
   public static final PoiType f_27339_ = m_27374_("leatherworker", f_148686_, 1, 1);
   public static final PoiType f_27340_ = m_27374_("librarian", m_27372_(Blocks.f_50624_), 1, 1);
   public static final PoiType f_27341_ = m_27374_("mason", m_27372_(Blocks.f_50679_), 1, 1);
   public static final PoiType f_27342_ = m_27374_("nitwit", ImmutableSet.of(), 1, 1);
   public static final PoiType f_27343_ = m_27374_("shepherd", m_27372_(Blocks.f_50617_), 1, 1);
   public static final PoiType f_27344_ = m_27374_("toolsmith", m_27372_(Blocks.f_50625_), 1, 1);
   public static final PoiType f_27345_ = m_27374_("weaponsmith", m_27372_(Blocks.f_50623_), 1, 1);
   public static final PoiType f_27346_ = m_27374_("home", f_27354_, 1, 1);
   public static final PoiType f_27347_ = m_27374_("meeting", m_27372_(Blocks.f_50680_), 32, 6);
   public static final PoiType f_27348_ = m_27374_("beehive", m_27372_(Blocks.f_50718_), 0, 1);
   public static final PoiType f_27349_ = m_27374_("bee_nest", m_27372_(Blocks.f_50717_), 0, 1);
   public static final PoiType f_27350_ = m_27374_("nether_portal", m_27372_(Blocks.f_50142_), 0, 1);
   public static final PoiType f_27351_ = m_27374_("lodestone", m_27372_(Blocks.f_50729_), 0, 1);
   public static final PoiType f_148687_ = m_27374_("lightning_rod", m_27372_(Blocks.f_152587_), 0, 1);
   protected static final Set<BlockState> f_27352_ = new ObjectOpenHashSet<>(f_27323_.keySet());
   private final String f_27324_;
   private final Set<BlockState> f_27325_;
   private final int f_27326_;
   private final Predicate<PoiType> f_27327_;
   private final int f_27328_;

   public static Set<BlockState> m_27372_(Block p_27373_) {
      return ImmutableSet.copyOf(p_27373_.m_49965_().m_61056_());
   }

   public PoiType(String p_27362_, Set<BlockState> p_27363_, int p_27364_, Predicate<PoiType> p_27365_, int p_27366_) {
      this.f_27324_ = p_27362_;
      this.f_27325_ = ImmutableSet.copyOf(p_27363_);
      this.f_27326_ = p_27364_;
      this.f_27327_ = p_27365_;
      this.f_27328_ = p_27366_;
   }

   public PoiType(String p_27357_, Set<BlockState> p_27358_, int p_27359_, int p_27360_) {
      this.f_27324_ = p_27357_;
      this.f_27325_ = ImmutableSet.copyOf(p_27358_);
      this.f_27326_ = p_27359_;
      this.f_27327_ = (p_148695_) -> {
         return p_148695_ == this;
      };
      this.f_27328_ = p_27360_;
   }

   public String m_148688_() {
      return this.f_27324_;
   }

   public int m_27385_() {
      return this.f_27326_;
   }

   public Predicate<PoiType> m_27392_() {
      return this.f_27327_;
   }

   public boolean m_148692_(BlockState p_148693_) {
      return this.f_27325_.contains(p_148693_);
   }

   public int m_27397_() {
      return this.f_27328_;
   }

   public String toString() {
      return this.f_27324_;
   }

   private static PoiType m_27374_(String p_27375_, Set<BlockState> p_27376_, int p_27377_, int p_27378_) {
      return m_27367_(Registry.m_122965_(Registry.f_122870_, new ResourceLocation(p_27375_), new PoiType(p_27375_, p_27376_, p_27377_, p_27378_)));
   }

   private static PoiType m_27379_(String p_27380_, Set<BlockState> p_27381_, int p_27382_, Predicate<PoiType> p_27383_, int p_27384_) {
      return m_27367_(Registry.m_122965_(Registry.f_122870_, new ResourceLocation(p_27380_), new PoiType(p_27380_, p_27381_, p_27382_, p_27383_, p_27384_)));
   }

   private static PoiType m_27367_(PoiType p_27368_) {
      return p_27368_;
   }

   public static Optional<PoiType> m_27390_(BlockState p_27391_) {
      return Optional.ofNullable(f_27323_.get(p_27391_));
   }
   
   public ImmutableSet<BlockState> getBlockStates() {
      return ImmutableSet.copyOf(this.f_27325_);
   }
}
