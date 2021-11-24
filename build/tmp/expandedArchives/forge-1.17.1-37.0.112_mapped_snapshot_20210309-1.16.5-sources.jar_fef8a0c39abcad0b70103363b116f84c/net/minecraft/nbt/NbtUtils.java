package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SerializableUUID;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NbtUtils {
   private static final Comparator<ListTag> f_178008_ = Comparator.<ListTag>comparingInt((p_178074_) -> {
      return p_178074_.m_128763_(1);
   }).thenComparingInt((p_178070_) -> {
      return p_178070_.m_128763_(0);
   }).thenComparingInt((p_178066_) -> {
      return p_178066_.m_128763_(2);
   });
   private static final Comparator<ListTag> f_178009_ = Comparator.<ListTag>comparingDouble((p_178060_) -> {
      return p_178060_.m_128772_(1);
   }).thenComparingDouble((p_178056_) -> {
      return p_178056_.m_128772_(0);
   }).thenComparingDouble((p_178042_) -> {
      return p_178042_.m_128772_(2);
   });
   public static final String f_178007_ = "data";
   private static final char f_178010_ = '{';
   private static final char f_178011_ = '}';
   private static final String f_178012_ = ",";
   private static final char f_178013_ = ':';
   private static final Splitter f_178014_ = Splitter.on(",");
   private static final Splitter f_178015_ = Splitter.on(':').limit(2);
   private static final Logger f_129200_ = LogManager.getLogger();
   private static final int f_178016_ = 2;
   private static final int f_178017_ = -1;

   private NbtUtils() {
   }

   @Nullable
   public static GameProfile m_129228_(CompoundTag p_129229_) {
      String s = null;
      UUID uuid = null;
      if (p_129229_.m_128425_("Name", 8)) {
         s = p_129229_.m_128461_("Name");
      }

      if (p_129229_.m_128403_("Id")) {
         uuid = p_129229_.m_128342_("Id");
      }

      try {
         GameProfile gameprofile = new GameProfile(uuid, s);
         if (p_129229_.m_128425_("Properties", 10)) {
            CompoundTag compoundtag = p_129229_.m_128469_("Properties");

            for(String s1 : compoundtag.m_128431_()) {
               ListTag listtag = compoundtag.m_128437_(s1, 10);

               for(int i = 0; i < listtag.size(); ++i) {
                  CompoundTag compoundtag1 = listtag.m_128728_(i);
                  String s2 = compoundtag1.m_128461_("Value");
                  if (compoundtag1.m_128425_("Signature", 8)) {
                     gameprofile.getProperties().put(s1, new com.mojang.authlib.properties.Property(s1, s2, compoundtag1.m_128461_("Signature")));
                  } else {
                     gameprofile.getProperties().put(s1, new com.mojang.authlib.properties.Property(s1, s2));
                  }
               }
            }
         }

         return gameprofile;
      } catch (Throwable throwable) {
         return null;
      }
   }

   public static CompoundTag m_129230_(CompoundTag p_129231_, GameProfile p_129232_) {
      if (!StringUtil.m_14408_(p_129232_.getName())) {
         p_129231_.m_128359_("Name", p_129232_.getName());
      }

      if (p_129232_.getId() != null) {
         p_129231_.m_128362_("Id", p_129232_.getId());
      }

      if (!p_129232_.getProperties().isEmpty()) {
         CompoundTag compoundtag = new CompoundTag();

         for(String s : p_129232_.getProperties().keySet()) {
            ListTag listtag = new ListTag();

            for(com.mojang.authlib.properties.Property property : p_129232_.getProperties().get(s)) {
               CompoundTag compoundtag1 = new CompoundTag();
               compoundtag1.m_128359_("Value", property.getValue());
               if (property.hasSignature()) {
                  compoundtag1.m_128359_("Signature", property.getSignature());
               }

               listtag.add(compoundtag1);
            }

            compoundtag.m_128365_(s, listtag);
         }

         p_129231_.m_128365_("Properties", compoundtag);
      }

      return p_129231_;
   }

   @VisibleForTesting
   public static boolean m_129235_(@Nullable Tag p_129236_, @Nullable Tag p_129237_, boolean p_129238_) {
      if (p_129236_ == p_129237_) {
         return true;
      } else if (p_129236_ == null) {
         return true;
      } else if (p_129237_ == null) {
         return false;
      } else if (!p_129236_.getClass().equals(p_129237_.getClass())) {
         return false;
      } else if (p_129236_ instanceof CompoundTag) {
         CompoundTag compoundtag = (CompoundTag)p_129236_;
         CompoundTag compoundtag1 = (CompoundTag)p_129237_;

         for(String s : compoundtag.m_128431_()) {
            Tag tag1 = compoundtag.m_128423_(s);
            if (!m_129235_(tag1, compoundtag1.m_128423_(s), p_129238_)) {
               return false;
            }
         }

         return true;
      } else if (p_129236_ instanceof ListTag && p_129238_) {
         ListTag listtag = (ListTag)p_129236_;
         ListTag listtag1 = (ListTag)p_129237_;
         if (listtag.isEmpty()) {
            return listtag1.isEmpty();
         } else {
            for(int i = 0; i < listtag.size(); ++i) {
               Tag tag = listtag.get(i);
               boolean flag = false;

               for(int j = 0; j < listtag1.size(); ++j) {
                  if (m_129235_(tag, listtag1.get(j), p_129238_)) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return p_129236_.equals(p_129237_);
      }
   }

   public static IntArrayTag m_129226_(UUID p_129227_) {
      return new IntArrayTag(SerializableUUID.m_123277_(p_129227_));
   }

   public static UUID m_129233_(Tag p_129234_) {
      if (p_129234_.m_6458_() != IntArrayTag.f_128599_) {
         throw new IllegalArgumentException("Expected UUID-Tag to be of type " + IntArrayTag.f_128599_.m_5987_() + ", but found " + p_129234_.m_6458_().m_5987_() + ".");
      } else {
         int[] aint = ((IntArrayTag)p_129234_).m_128648_();
         if (aint.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + aint.length + ".");
         } else {
            return SerializableUUID.m_123281_(aint);
         }
      }
   }

   public static BlockPos m_129239_(CompoundTag p_129240_) {
      return new BlockPos(p_129240_.m_128451_("X"), p_129240_.m_128451_("Y"), p_129240_.m_128451_("Z"));
   }

   public static CompoundTag m_129224_(BlockPos p_129225_) {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128405_("X", p_129225_.m_123341_());
      compoundtag.m_128405_("Y", p_129225_.m_123342_());
      compoundtag.m_128405_("Z", p_129225_.m_123343_());
      return compoundtag;
   }

   public static BlockState m_129241_(CompoundTag p_129242_) {
      if (!p_129242_.m_128425_("Name", 8)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         Block block = Registry.f_122824_.m_7745_(new ResourceLocation(p_129242_.m_128461_("Name")));
         BlockState blockstate = block.m_49966_();
         if (p_129242_.m_128425_("Properties", 10)) {
            CompoundTag compoundtag = p_129242_.m_128469_("Properties");
            StateDefinition<Block, BlockState> statedefinition = block.m_49965_();

            for(String s : compoundtag.m_128431_()) {
               Property<?> property = statedefinition.m_61081_(s);
               if (property != null) {
                  blockstate = m_129204_(blockstate, property, s, compoundtag, p_129242_);
               }
            }
         }

         return blockstate;
      }
   }

   private static <S extends StateHolder<?, S>, T extends Comparable<T>> S m_129204_(S p_129205_, Property<T> p_129206_, String p_129207_, CompoundTag p_129208_, CompoundTag p_129209_) {
      Optional<T> optional = p_129206_.m_6215_(p_129208_.m_128461_(p_129207_));
      if (optional.isPresent()) {
         return p_129205_.m_61124_(p_129206_, optional.get());
      } else {
         f_129200_.warn("Unable to read property: {} with value: {} for blockstate: {}", p_129207_, p_129208_.m_128461_(p_129207_), p_129209_.toString());
         return p_129205_;
      }
   }

   public static CompoundTag m_129202_(BlockState p_129203_) {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("Name", Registry.f_122824_.m_7981_(p_129203_.m_60734_()).toString());
      ImmutableMap<Property<?>, Comparable<?>> immutablemap = p_129203_.m_61148_();
      if (!immutablemap.isEmpty()) {
         CompoundTag compoundtag1 = new CompoundTag();

         for(Entry<Property<?>, Comparable<?>> entry : immutablemap.entrySet()) {
            Property<?> property = entry.getKey();
            compoundtag1.m_128359_(property.m_61708_(), m_129210_(property, entry.getValue()));
         }

         compoundtag.m_128365_("Properties", compoundtag1);
      }

      return compoundtag;
   }

   public static CompoundTag m_178022_(FluidState p_178023_) {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("Name", Registry.f_122822_.m_7981_(p_178023_.m_76152_()).toString());
      ImmutableMap<Property<?>, Comparable<?>> immutablemap = p_178023_.m_61148_();
      if (!immutablemap.isEmpty()) {
         CompoundTag compoundtag1 = new CompoundTag();

         for(Entry<Property<?>, Comparable<?>> entry : immutablemap.entrySet()) {
            Property<?> property = entry.getKey();
            compoundtag1.m_128359_(property.m_61708_(), m_129210_(property, entry.getValue()));
         }

         compoundtag.m_128365_("Properties", compoundtag1);
      }

      return compoundtag;
   }

   private static <T extends Comparable<T>> String m_129210_(Property<T> p_129211_, Comparable<?> p_129212_) {
      return p_129211_.m_6940_((T)p_129212_);
   }

   public static String m_178057_(Tag p_178058_) {
      return m_178050_(p_178058_, false);
   }

   public static String m_178050_(Tag p_178051_, boolean p_178052_) {
      return m_178026_(new StringBuilder(), p_178051_, 0, p_178052_).toString();
   }

   public static StringBuilder m_178026_(StringBuilder p_178027_, Tag p_178028_, int p_178029_, boolean p_178030_) {
      switch(p_178028_.m_7060_()) {
      case 0:
         break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 8:
         p_178027_.append((Object)p_178028_);
         break;
      case 7:
         ByteArrayTag bytearraytag = (ByteArrayTag)p_178028_;
         byte[] abyte = bytearraytag.m_128227_();
         int k1 = abyte.length;
         m_178019_(p_178029_, p_178027_).append("byte[").append(k1).append("] {\n");
         if (!p_178030_) {
            m_178019_(p_178029_ + 1, p_178027_).append(" // Skipped, supply withBinaryBlobs true");
         } else {
            m_178019_(p_178029_ + 1, p_178027_);

            for(int i2 = 0; i2 < abyte.length; ++i2) {
               if (i2 != 0) {
                  p_178027_.append(',');
               }

               if (i2 % 16 == 0 && i2 / 16 > 0) {
                  p_178027_.append('\n');
                  if (i2 < abyte.length) {
                     m_178019_(p_178029_ + 1, p_178027_);
                  }
               } else if (i2 != 0) {
                  p_178027_.append(' ');
               }

               p_178027_.append(String.format("0x%02X", abyte[i2] & 255));
            }
         }

         p_178027_.append('\n');
         m_178019_(p_178029_, p_178027_).append('}');
         break;
      case 9:
         ListTag listtag = (ListTag)p_178028_;
         int k = listtag.size();
         int j1 = listtag.m_7264_();
         String s1 = j1 == 0 ? "undefined" : TagTypes.m_129397_(j1).m_5986_();
         m_178019_(p_178029_, p_178027_).append("list<").append(s1).append(">[").append(k).append("] [");
         if (k != 0) {
            p_178027_.append('\n');
         }

         for(int i3 = 0; i3 < k; ++i3) {
            if (i3 != 0) {
               p_178027_.append(",\n");
            }

            m_178019_(p_178029_ + 1, p_178027_);
            m_178026_(p_178027_, listtag.get(i3), p_178029_ + 1, p_178030_);
         }

         if (k != 0) {
            p_178027_.append('\n');
         }

         m_178019_(p_178029_, p_178027_).append(']');
         break;
      case 10:
         CompoundTag compoundtag = (CompoundTag)p_178028_;
         List<String> list = Lists.newArrayList(compoundtag.m_128431_());
         Collections.sort(list);
         m_178019_(p_178029_, p_178027_).append('{');
         if (p_178027_.length() - p_178027_.lastIndexOf("\n") > 2 * (p_178029_ + 1)) {
            p_178027_.append('\n');
            m_178019_(p_178029_ + 1, p_178027_);
         }

         int i1 = list.stream().mapToInt(String::length).max().orElse(0);
         String s = Strings.repeat(" ", i1);

         for(int l2 = 0; l2 < list.size(); ++l2) {
            if (l2 != 0) {
               p_178027_.append(",\n");
            }

            String s2 = list.get(l2);
            m_178019_(p_178029_ + 1, p_178027_).append('"').append(s2).append('"').append((CharSequence)s, 0, s.length() - s2.length()).append(": ");
            m_178026_(p_178027_, compoundtag.m_128423_(s2), p_178029_ + 1, p_178030_);
         }

         if (!list.isEmpty()) {
            p_178027_.append('\n');
         }

         m_178019_(p_178029_, p_178027_).append('}');
         break;
      case 11:
         IntArrayTag intarraytag = (IntArrayTag)p_178028_;
         int[] aint = intarraytag.m_128648_();
         int l = 0;

         for(int k3 : aint) {
            l = Math.max(l, String.format("%X", k3).length());
         }

         int l1 = aint.length;
         m_178019_(p_178029_, p_178027_).append("int[").append(l1).append("] {\n");
         if (!p_178030_) {
            m_178019_(p_178029_ + 1, p_178027_).append(" // Skipped, supply withBinaryBlobs true");
         } else {
            m_178019_(p_178029_ + 1, p_178027_);

            for(int k2 = 0; k2 < aint.length; ++k2) {
               if (k2 != 0) {
                  p_178027_.append(',');
               }

               if (k2 % 16 == 0 && k2 / 16 > 0) {
                  p_178027_.append('\n');
                  if (k2 < aint.length) {
                     m_178019_(p_178029_ + 1, p_178027_);
                  }
               } else if (k2 != 0) {
                  p_178027_.append(' ');
               }

               p_178027_.append(String.format("0x%0" + l + "X", aint[k2]));
            }
         }

         p_178027_.append('\n');
         m_178019_(p_178029_, p_178027_).append('}');
         break;
      case 12:
         LongArrayTag longarraytag = (LongArrayTag)p_178028_;
         long[] along = longarraytag.m_128851_();
         long i = 0L;

         for(long j : along) {
            i = Math.max(i, (long)String.format("%X", j).length());
         }

         long j2 = (long)along.length;
         m_178019_(p_178029_, p_178027_).append("long[").append(j2).append("] {\n");
         if (!p_178030_) {
            m_178019_(p_178029_ + 1, p_178027_).append(" // Skipped, supply withBinaryBlobs true");
         } else {
            m_178019_(p_178029_ + 1, p_178027_);

            for(int j3 = 0; j3 < along.length; ++j3) {
               if (j3 != 0) {
                  p_178027_.append(',');
               }

               if (j3 % 16 == 0 && j3 / 16 > 0) {
                  p_178027_.append('\n');
                  if (j3 < along.length) {
                     m_178019_(p_178029_ + 1, p_178027_);
                  }
               } else if (j3 != 0) {
                  p_178027_.append(' ');
               }

               p_178027_.append(String.format("0x%0" + i + "X", along[j3]));
            }
         }

         p_178027_.append('\n');
         m_178019_(p_178029_, p_178027_).append('}');
         break;
      default:
         p_178027_.append("<UNKNOWN :(>");
      }

      return p_178027_;
   }

   private static StringBuilder m_178019_(int p_178020_, StringBuilder p_178021_) {
      int i = p_178021_.lastIndexOf("\n") + 1;
      int j = p_178021_.length() - i;

      for(int k = 0; k < 2 * p_178020_ - j; ++k) {
         p_178021_.append(' ');
      }

      return p_178021_;
   }

   public static CompoundTag m_129213_(DataFixer p_129214_, DataFixTypes p_129215_, CompoundTag p_129216_, int p_129217_) {
      return m_129218_(p_129214_, p_129215_, p_129216_, p_129217_, SharedConstants.m_136187_().getWorldVersion());
   }

   public static CompoundTag m_129218_(DataFixer p_129219_, DataFixTypes p_129220_, CompoundTag p_129221_, int p_129222_, int p_129223_) {
      return (CompoundTag)p_129219_.update(p_129220_.m_14504_(), new Dynamic<>(NbtOps.f_128958_, p_129221_), p_129222_, p_129223_).getValue();
   }

   public static Component m_178061_(Tag p_178062_) {
      return (new TextComponentTagVisitor("", 0)).m_178281_(p_178062_);
   }

   public static String m_178063_(CompoundTag p_178064_) {
      return (new SnbtPrinterTagVisitor()).m_178141_(m_178067_(p_178064_));
   }

   public static CompoundTag m_178024_(String p_178025_) throws CommandSyntaxException {
      return m_178071_(TagParser.m_129359_(p_178025_));
   }

   @VisibleForTesting
   static CompoundTag m_178067_(CompoundTag p_178068_) {
      boolean flag = p_178068_.m_128425_("palettes", 9);
      ListTag listtag;
      if (flag) {
         listtag = p_178068_.m_128437_("palettes", 9).m_128744_(0);
      } else {
         listtag = p_178068_.m_128437_("palette", 10);
      }

      ListTag listtag1 = listtag.stream().map(CompoundTag.class::cast).map(NbtUtils::m_178075_).map(StringTag::m_129297_).collect(Collectors.toCollection(ListTag::new));
      p_178068_.m_128365_("palette", listtag1);
      if (flag) {
         ListTag listtag2 = new ListTag();
         ListTag listtag3 = p_178068_.m_128437_("palettes", 9);
         listtag3.stream().map(ListTag.class::cast).forEach((p_178049_) -> {
            CompoundTag compoundtag = new CompoundTag();

            for(int i = 0; i < p_178049_.size(); ++i) {
               compoundtag.m_128359_(listtag1.m_128778_(i), m_178075_(p_178049_.m_128728_(i)));
            }

            listtag2.add(compoundtag);
         });
         p_178068_.m_128365_("palettes", listtag2);
      }

      if (p_178068_.m_128425_("entities", 10)) {
         ListTag listtag4 = p_178068_.m_128437_("entities", 10);
         ListTag listtag6 = listtag4.stream().map(CompoundTag.class::cast).sorted(Comparator.comparing((p_178080_) -> {
            return p_178080_.m_128437_("pos", 6);
         }, f_178009_)).collect(Collectors.toCollection(ListTag::new));
         p_178068_.m_128365_("entities", listtag6);
      }

      ListTag listtag5 = p_178068_.m_128437_("blocks", 10).stream().map(CompoundTag.class::cast).sorted(Comparator.comparing((p_178078_) -> {
         return p_178078_.m_128437_("pos", 3);
      }, f_178008_)).peek((p_178045_) -> {
         p_178045_.m_128359_("state", listtag1.m_128778_(p_178045_.m_128451_("state")));
      }).collect(Collectors.toCollection(ListTag::new));
      p_178068_.m_128365_("data", listtag5);
      p_178068_.m_128473_("blocks");
      return p_178068_;
   }

   @VisibleForTesting
   static CompoundTag m_178071_(CompoundTag p_178072_) {
      ListTag listtag = p_178072_.m_128437_("palette", 8);
      Map<String, Tag> map = listtag.stream().map(StringTag.class::cast).map(StringTag::m_7916_).collect(ImmutableMap.toImmutableMap(Function.identity(), NbtUtils::m_178053_));
      if (p_178072_.m_128425_("palettes", 9)) {
         p_178072_.m_128365_("palettes", p_178072_.m_128437_("palettes", 10).stream().map(CompoundTag.class::cast).map((p_178033_) -> {
            return map.keySet().stream().map(p_178033_::m_128461_).map(NbtUtils::m_178053_).collect(Collectors.toCollection(ListTag::new));
         }).collect(Collectors.toCollection(ListTag::new)));
         p_178072_.m_128473_("palette");
      } else {
         p_178072_.m_128365_("palette", map.values().stream().collect(Collectors.toCollection(ListTag::new)));
      }

      if (p_178072_.m_128425_("data", 9)) {
         Object2IntMap<String> object2intmap = new Object2IntOpenHashMap<>();
         object2intmap.defaultReturnValue(-1);

         for(int i = 0; i < listtag.size(); ++i) {
            object2intmap.put(listtag.m_128778_(i), i);
         }

         ListTag listtag1 = p_178072_.m_128437_("data", 10);

         for(int j = 0; j < listtag1.size(); ++j) {
            CompoundTag compoundtag = listtag1.m_128728_(j);
            String s = compoundtag.m_128461_("state");
            int k = object2intmap.getInt(s);
            if (k == -1) {
               throw new IllegalStateException("Entry " + s + " missing from palette");
            }

            compoundtag.m_128405_("state", k);
         }

         p_178072_.m_128365_("blocks", listtag1);
         p_178072_.m_128473_("data");
      }

      return p_178072_;
   }

   @VisibleForTesting
   static String m_178075_(CompoundTag p_178076_) {
      StringBuilder stringbuilder = new StringBuilder(p_178076_.m_128461_("Name"));
      if (p_178076_.m_128425_("Properties", 10)) {
         CompoundTag compoundtag = p_178076_.m_128469_("Properties");
         String s = compoundtag.m_128431_().stream().sorted().map((p_178036_) -> {
            return p_178036_ + ":" + compoundtag.m_128423_(p_178036_).m_7916_();
         }).collect(Collectors.joining(","));
         stringbuilder.append('{').append(s).append('}');
      }

      return stringbuilder.toString();
   }

   @VisibleForTesting
   static CompoundTag m_178053_(String p_178054_) {
      CompoundTag compoundtag = new CompoundTag();
      int i = p_178054_.indexOf(123);
      String s;
      if (i >= 0) {
         s = p_178054_.substring(0, i);
         CompoundTag compoundtag1 = new CompoundTag();
         if (i + 2 <= p_178054_.length()) {
            String s1 = p_178054_.substring(i + 1, p_178054_.indexOf(125, i));
            f_178014_.split(s1).forEach((p_178040_) -> {
               List<String> list = f_178015_.splitToList(p_178040_);
               if (list.size() == 2) {
                  compoundtag1.m_128359_(list.get(0), list.get(1));
               } else {
                  f_129200_.error("Something went wrong parsing: '{}' -- incorrect gamedata!", (Object)p_178054_);
               }

            });
            compoundtag.m_128365_("Properties", compoundtag1);
         }
      } else {
         s = p_178054_;
      }

      compoundtag.m_128359_("Name", s);
      return compoundtag;
   }
}