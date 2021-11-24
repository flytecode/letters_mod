package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.PackedBitStorage;

public class LeavesFix extends DataFix {
   private static final int f_145445_ = 128;
   private static final int f_145446_ = 64;
   private static final int f_145447_ = 32;
   private static final int f_145448_ = 16;
   private static final int f_145449_ = 8;
   private static final int f_145450_ = 4;
   private static final int f_145451_ = 2;
   private static final int f_145452_ = 1;
   private static final int[][] f_16200_ = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
   private static final int f_145453_ = 7;
   private static final int f_145454_ = 12;
   private static final int f_145455_ = 4096;
   static final Object2IntMap<String> f_16201_ = DataFixUtils.make(new Object2IntOpenHashMap<>(), (p_16235_) -> {
      p_16235_.put("minecraft:acacia_leaves", 0);
      p_16235_.put("minecraft:birch_leaves", 1);
      p_16235_.put("minecraft:dark_oak_leaves", 2);
      p_16235_.put("minecraft:jungle_leaves", 3);
      p_16235_.put("minecraft:oak_leaves", 4);
      p_16235_.put("minecraft:spruce_leaves", 5);
   });
   static final Set<String> f_16202_ = ImmutableSet.of("minecraft:acacia_bark", "minecraft:birch_bark", "minecraft:dark_oak_bark", "minecraft:jungle_bark", "minecraft:oak_bark", "minecraft:spruce_bark", "minecraft:acacia_log", "minecraft:birch_log", "minecraft:dark_oak_log", "minecraft:jungle_log", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:stripped_acacia_log", "minecraft:stripped_birch_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_jungle_log", "minecraft:stripped_oak_log", "minecraft:stripped_spruce_log");

   public LeavesFix(Schema p_16205_, boolean p_16206_) {
      super(p_16205_, p_16206_);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> type = this.getInputSchema().getType(References.f_16773_);
      OpticFinder<?> opticfinder = type.findField("Level");
      OpticFinder<?> opticfinder1 = opticfinder.type().findField("Sections");
      Type<?> type1 = opticfinder1.type();
      if (!(type1 instanceof ListType)) {
         throw new IllegalStateException("Expecting sections to be a list.");
      } else {
         Type<?> type2 = ((ListType)type1).getElement();
         OpticFinder<?> opticfinder2 = DSL.typeFinder(type2);
         return this.fixTypeEverywhereTyped("Leaves fix", type, (p_16220_) -> {
            return p_16220_.updateTyped(opticfinder, (p_145461_) -> {
               int[] aint = new int[]{0};
               Typed<?> typed = p_145461_.updateTyped(opticfinder1, (p_145465_) -> {
                  Int2ObjectMap<LeavesFix.LeavesSection> int2objectmap = new Int2ObjectOpenHashMap<>(p_145465_.getAllTyped(opticfinder2).stream().map((p_145467_) -> {
                     return new LeavesFix.LeavesSection(p_145467_, this.getInputSchema());
                  }).collect(Collectors.toMap(LeavesFix.Section::m_16301_, (p_145457_) -> {
                     return p_145457_;
                  })));
                  if (int2objectmap.values().stream().allMatch(LeavesFix.Section::m_16298_)) {
                     return p_145465_;
                  } else {
                     List<IntSet> list = Lists.newArrayList();

                     for(int i = 0; i < 7; ++i) {
                        list.add(new IntOpenHashSet());
                     }

                     for(LeavesFix.LeavesSection leavesfix$leavessection : int2objectmap.values()) {
                        if (!leavesfix$leavessection.m_16298_()) {
                           for(int j = 0; j < 4096; ++j) {
                              int k = leavesfix$leavessection.m_16302_(j);
                              if (leavesfix$leavessection.m_16257_(k)) {
                                 list.get(0).add(leavesfix$leavessection.m_16301_() << 12 | j);
                              } else if (leavesfix$leavessection.m_16276_(k)) {
                                 int l = this.m_16208_(j);
                                 int i1 = this.m_16247_(j);
                                 aint[0] |= m_16236_(l == 0, l == 15, i1 == 0, i1 == 15);
                              }
                           }
                        }
                     }

                     for(int j3 = 1; j3 < 7; ++j3) {
                        IntSet intset = list.get(j3 - 1);
                        IntSet intset1 = list.get(j3);
                        IntIterator intiterator = intset.iterator();

                        while(intiterator.hasNext()) {
                           int k3 = intiterator.nextInt();
                           int l3 = this.m_16208_(k3);
                           int j1 = this.m_16245_(k3);
                           int k1 = this.m_16247_(k3);

                           for(int[] aint1 : f_16200_) {
                              int l1 = l3 + aint1[0];
                              int i2 = j1 + aint1[1];
                              int j2 = k1 + aint1[2];
                              if (l1 >= 0 && l1 <= 15 && j2 >= 0 && j2 <= 15 && i2 >= 0 && i2 <= 255) {
                                 LeavesFix.LeavesSection leavesfix$leavessection1 = int2objectmap.get(i2 >> 4);
                                 if (leavesfix$leavessection1 != null && !leavesfix$leavessection1.m_16298_()) {
                                    int k2 = m_16210_(l1, i2 & 15, j2);
                                    int l2 = leavesfix$leavessection1.m_16302_(k2);
                                    if (leavesfix$leavessection1.m_16276_(l2)) {
                                       int i3 = leavesfix$leavessection1.m_16278_(l2);
                                       if (i3 > j3) {
                                          leavesfix$leavessection1.m_16259_(k2, l2, j3);
                                          intset1.add(m_16210_(l1, i2, j2));
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }

                     return p_145465_.updateTyped(opticfinder2, (p_145470_) -> {
                        return int2objectmap.get(p_145470_.get(DSL.remainderFinder()).get("Y").asInt(0)).m_16288_(p_145470_);
                     });
                  }
               });
               if (aint[0] != 0) {
                  typed = typed.update(DSL.remainderFinder(), (p_145473_) -> {
                     Dynamic<?> dynamic = DataFixUtils.orElse(p_145473_.get("UpgradeData").result(), p_145473_.emptyMap());
                     return p_145473_.set("UpgradeData", dynamic.set("Sides", p_145473_.createByte((byte)(dynamic.get("Sides").asByte((byte)0) | aint[0]))));
                  });
               }

               return typed;
            });
         });
      }
   }

   public static int m_16210_(int p_16211_, int p_16212_, int p_16213_) {
      return p_16212_ << 8 | p_16213_ << 4 | p_16211_;
   }

   private int m_16208_(int p_16209_) {
      return p_16209_ & 15;
   }

   private int m_16245_(int p_16246_) {
      return p_16246_ >> 8 & 255;
   }

   private int m_16247_(int p_16248_) {
      return p_16248_ >> 4 & 15;
   }

   public static int m_16236_(boolean p_16237_, boolean p_16238_, boolean p_16239_, boolean p_16240_) {
      int i = 0;
      if (p_16239_) {
         if (p_16238_) {
            i |= 2;
         } else if (p_16237_) {
            i |= 128;
         } else {
            i |= 1;
         }
      } else if (p_16240_) {
         if (p_16237_) {
            i |= 32;
         } else if (p_16238_) {
            i |= 8;
         } else {
            i |= 16;
         }
      } else if (p_16238_) {
         i |= 4;
      } else if (p_16237_) {
         i |= 64;
      }

      return i;
   }

   public static final class LeavesSection extends LeavesFix.Section {
      private static final String f_145474_ = "persistent";
      private static final String f_145475_ = "decayable";
      private static final String f_145476_ = "distance";
      @Nullable
      private IntSet f_16250_;
      @Nullable
      private IntSet f_16251_;
      @Nullable
      private Int2IntMap f_16252_;

      public LeavesSection(Typed<?> p_16254_, Schema p_16255_) {
         super(p_16254_, p_16255_);
      }

      protected boolean m_7969_() {
         this.f_16250_ = new IntOpenHashSet();
         this.f_16251_ = new IntOpenHashSet();
         this.f_16252_ = new Int2IntOpenHashMap();

         for(int i = 0; i < this.f_16281_.size(); ++i) {
            Dynamic<?> dynamic = this.f_16281_.get(i);
            String s = dynamic.get("Name").asString("");
            if (LeavesFix.f_16201_.containsKey(s)) {
               boolean flag = Objects.equals(dynamic.get("Properties").get("decayable").asString(""), "false");
               this.f_16250_.add(i);
               this.f_16252_.put(this.m_16292_(s, flag, 7), i);
               this.f_16281_.set(i, this.m_16271_(dynamic, s, flag, 7));
            }

            if (LeavesFix.f_16202_.contains(s)) {
               this.f_16251_.add(i);
            }
         }

         return this.f_16250_.isEmpty() && this.f_16251_.isEmpty();
      }

      private Dynamic<?> m_16271_(Dynamic<?> p_16272_, String p_16273_, boolean p_16274_, int p_16275_) {
         Dynamic<?> dynamic = p_16272_.emptyMap();
         dynamic = dynamic.set("persistent", dynamic.createString(p_16274_ ? "true" : "false"));
         dynamic = dynamic.set("distance", dynamic.createString(Integer.toString(p_16275_)));
         Dynamic<?> dynamic1 = p_16272_.emptyMap();
         dynamic1 = dynamic1.set("Properties", dynamic);
         return dynamic1.set("Name", dynamic1.createString(p_16273_));
      }

      public boolean m_16257_(int p_16258_) {
         return this.f_16251_.contains(p_16258_);
      }

      public boolean m_16276_(int p_16277_) {
         return this.f_16250_.contains(p_16277_);
      }

      int m_16278_(int p_16279_) {
         return this.m_16257_(p_16279_) ? 0 : Integer.parseInt(this.f_16281_.get(p_16279_).get("Properties").get("distance").asString(""));
      }

      void m_16259_(int p_16260_, int p_16261_, int p_16262_) {
         Dynamic<?> dynamic = this.f_16281_.get(p_16261_);
         String s = dynamic.get("Name").asString("");
         boolean flag = Objects.equals(dynamic.get("Properties").get("persistent").asString(""), "true");
         int i = this.m_16292_(s, flag, p_16262_);
         if (!this.f_16252_.containsKey(i)) {
            int j = this.f_16281_.size();
            this.f_16250_.add(j);
            this.f_16252_.put(i, j);
            this.f_16281_.add(this.m_16271_(dynamic, s, flag, p_16262_));
         }

         int l = this.f_16252_.get(i);
         if (1 << this.f_16283_.m_14567_() <= l) {
            PackedBitStorage packedbitstorage = new PackedBitStorage(this.f_16283_.m_14567_() + 1, 4096);

            for(int k = 0; k < 4096; ++k) {
               packedbitstorage.m_14564_(k, this.f_16283_.m_14562_(k));
            }

            this.f_16283_ = packedbitstorage;
         }

         this.f_16283_.m_14564_(p_16260_, l);
      }
   }

   public abstract static class Section {
      protected static final String f_145477_ = "BlockStates";
      protected static final String f_145478_ = "Name";
      protected static final String f_145479_ = "Properties";
      private final Type<Pair<String, Dynamic<?>>> f_16284_ = DSL.named(References.f_16783_.typeName(), DSL.remainderType());
      protected final OpticFinder<List<Pair<String, Dynamic<?>>>> f_16280_ = DSL.fieldFinder("Palette", DSL.list(this.f_16284_));
      protected final List<Dynamic<?>> f_16281_;
      protected final int f_16282_;
      @Nullable
      protected PackedBitStorage f_16283_;

      public Section(Typed<?> p_16286_, Schema p_16287_) {
         if (!Objects.equals(p_16287_.getType(References.f_16783_), this.f_16284_)) {
            throw new IllegalStateException("Block state type is not what was expected.");
         } else {
            Optional<List<Pair<String, Dynamic<?>>>> optional = p_16286_.getOptional(this.f_16280_);
            this.f_16281_ = optional.<List<Dynamic<?>>>map((p_16297_) -> {
               return p_16297_.stream().map(Pair::getSecond).collect(Collectors.toList());
            }).orElse(ImmutableList.of());
            Dynamic<?> dynamic = p_16286_.get(DSL.remainderFinder());
            this.f_16282_ = dynamic.get("Y").asInt(0);
            this.m_16290_(dynamic);
         }
      }

      protected void m_16290_(Dynamic<?> p_16291_) {
         if (this.m_7969_()) {
            this.f_16283_ = null;
         } else {
            long[] along = p_16291_.get("BlockStates").asLongStream().toArray();
            int i = Math.max(4, DataFixUtils.ceillog2(this.f_16281_.size()));
            this.f_16283_ = new PackedBitStorage(i, 4096, along);
         }

      }

      public Typed<?> m_16288_(Typed<?> p_16289_) {
         return this.m_16298_() ? p_16289_ : p_16289_.update(DSL.remainderFinder(), (p_16305_) -> {
            return p_16305_.set("BlockStates", p_16305_.createLongList(Arrays.stream(this.f_16283_.m_14561_())));
         }).set(this.f_16280_, this.f_16281_.stream().<Pair<String, Dynamic<?>>>map((p_16300_) -> {
            return Pair.of(References.f_16783_.typeName(), p_16300_);
         }).collect(Collectors.toList()));
      }

      public boolean m_16298_() {
         return this.f_16283_ == null;
      }

      public int m_16302_(int p_16303_) {
         return this.f_16283_.m_14562_(p_16303_);
      }

      protected int m_16292_(String p_16293_, boolean p_16294_, int p_16295_) {
         return LeavesFix.f_16201_.get(p_16293_) << 5 | (p_16294_ ? 16 : 0) | p_16295_;
      }

      int m_16301_() {
         return this.f_16282_;
      }

      protected abstract boolean m_7969_();
   }
}