package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapItem extends ComplexItem {
   public static final int f_151104_ = 128;
   public static final int f_151105_ = 128;
   private static final int f_151106_ = -12173266;
   private static final String f_151107_ = "map";

   public MapItem(Item.Properties p_42847_) {
      super(p_42847_);
   }

   public static ItemStack m_42886_(Level p_42887_, int p_42888_, int p_42889_, byte p_42890_, boolean p_42891_, boolean p_42892_) {
      ItemStack itemstack = new ItemStack(Items.f_42573_);
      m_151111_(itemstack, p_42887_, p_42888_, p_42889_, p_42890_, p_42891_, p_42892_, p_42887_.m_46472_());
      return itemstack;
   }

   @Nullable
   public static MapItemSavedData m_151128_(@Nullable Integer p_151129_, Level p_151130_) {
      return p_151129_ == null ? null : p_151130_.m_7489_(m_42848_(p_151129_));
   }

   @Nullable
   public static MapItemSavedData m_42853_(ItemStack p_42854_, Level p_42855_) {
      // Forge: Add instance method so that mods can override
      Item map = p_42854_.m_41720_();
      if(map instanceof MapItem) {
         return ((MapItem)map).getCustomMapData(p_42854_, p_42855_);
      }
      return null;
   }

   @Nullable
   protected MapItemSavedData getCustomMapData(ItemStack p_42910_, Level p_42911_) {
      Integer integer = m_151131_(p_42910_);
      return m_151128_(integer, p_42911_);
   }

   @Nullable
   public static Integer m_151131_(ItemStack p_151132_) {
      CompoundTag compoundtag = p_151132_.m_41783_();
      return compoundtag != null && compoundtag.m_128425_("map", 99) ? compoundtag.m_128451_("map") : null;
   }

   private static int m_151120_(Level p_151121_, int p_151122_, int p_151123_, int p_151124_, boolean p_151125_, boolean p_151126_, ResourceKey<Level> p_151127_) {
      MapItemSavedData mapitemsaveddata = MapItemSavedData.m_164780_((double)p_151122_, (double)p_151123_, (byte)p_151124_, p_151125_, p_151126_, p_151127_);
      int i = p_151121_.m_7354_();
      p_151121_.m_142325_(m_42848_(i), mapitemsaveddata);
      return i;
   }

   private static void m_151108_(ItemStack p_151109_, int p_151110_) {
      p_151109_.m_41784_().m_128405_("map", p_151110_);
   }

   private static void m_151111_(ItemStack p_151112_, Level p_151113_, int p_151114_, int p_151115_, int p_151116_, boolean p_151117_, boolean p_151118_, ResourceKey<Level> p_151119_) {
      int i = m_151120_(p_151113_, p_151114_, p_151115_, p_151116_, p_151117_, p_151118_, p_151119_);
      m_151108_(p_151112_, i);
   }

   public static String m_42848_(int p_42849_) {
      return "map_" + p_42849_;
   }

   public void m_42893_(Level p_42894_, Entity p_42895_, MapItemSavedData p_42896_) {
      if (p_42894_.m_46472_() == p_42896_.f_77887_ && p_42895_ instanceof Player) {
         int i = 1 << p_42896_.f_77890_;
         int j = p_42896_.f_77885_;
         int k = p_42896_.f_77886_;
         int l = Mth.m_14107_(p_42895_.m_20185_() - (double)j) / i + 64;
         int i1 = Mth.m_14107_(p_42895_.m_20189_() - (double)k) / i + 64;
         int j1 = 128 / i;
         if (p_42894_.m_6042_().m_63946_()) {
            j1 /= 2;
         }

         MapItemSavedData.HoldingPlayer mapitemsaveddata$holdingplayer = p_42896_.m_77916_((Player)p_42895_);
         ++mapitemsaveddata$holdingplayer.f_77960_;
         boolean flag = false;

         for(int k1 = l - j1 + 1; k1 < l + j1; ++k1) {
            if ((k1 & 15) == (mapitemsaveddata$holdingplayer.f_77960_ & 15) || flag) {
               flag = false;
               double d0 = 0.0D;

               for(int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1) {
                  if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
                     int i2 = k1 - l;
                     int j2 = l1 - i1;
                     boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
                     int k2 = (j / i + k1 - 64) * i;
                     int l2 = (k / i + l1 - 64) * i;
                     Multiset<MaterialColor> multiset = LinkedHashMultiset.create();
                     LevelChunk levelchunk = p_42894_.m_46745_(new BlockPos(k2, 0, l2));
                     if (!levelchunk.m_6430_()) {
                        ChunkPos chunkpos = levelchunk.m_7697_();
                        int i3 = k2 & 15;
                        int j3 = l2 & 15;
                        int k3 = 0;
                        double d1 = 0.0D;
                        if (p_42894_.m_6042_().m_63946_()) {
                           int l3 = k2 + l2 * 231871;
                           l3 = l3 * l3 * 31287121 + l3 * 11;
                           if ((l3 >> 20 & 1) == 0) {
                              multiset.add(Blocks.f_50493_.m_49966_().m_60780_(p_42894_, BlockPos.f_121853_), 10);
                           } else {
                              multiset.add(Blocks.f_50069_.m_49966_().m_60780_(p_42894_, BlockPos.f_121853_), 100);
                           }

                           d1 = 100.0D;
                        } else {
                           BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
                           BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                           for(int i4 = 0; i4 < i; ++i4) {
                              for(int j4 = 0; j4 < i; ++j4) {
                                 int k4 = levelchunk.m_5885_(Heightmap.Types.WORLD_SURFACE, i4 + i3, j4 + j3) + 1;
                                 BlockState blockstate;
                                 if (k4 <= p_42894_.m_141937_() + 1) {
                                    blockstate = Blocks.f_50752_.m_49966_();
                                 } else {
                                    do {
                                       --k4;
                                       blockpos$mutableblockpos1.m_122178_(chunkpos.m_45604_() + i4 + i3, k4, chunkpos.m_45605_() + j4 + j3);
                                       blockstate = levelchunk.m_8055_(blockpos$mutableblockpos1);
                                    } while(blockstate.m_60780_(p_42894_, blockpos$mutableblockpos1) == MaterialColor.f_76398_ && k4 > p_42894_.m_141937_());

                                    if (k4 > p_42894_.m_141937_() && !blockstate.m_60819_().m_76178_()) {
                                       int l4 = k4 - 1;
                                       blockpos$mutableblockpos.m_122190_(blockpos$mutableblockpos1);

                                       BlockState blockstate1;
                                       do {
                                          blockpos$mutableblockpos.m_142448_(l4--);
                                          blockstate1 = levelchunk.m_8055_(blockpos$mutableblockpos);
                                          ++k3;
                                       } while(l4 > p_42894_.m_141937_() && !blockstate1.m_60819_().m_76178_());

                                       blockstate = this.m_42900_(p_42894_, blockstate, blockpos$mutableblockpos1);
                                    }
                                 }

                                 p_42896_.m_77930_(p_42894_, chunkpos.m_45604_() + i4 + i3, chunkpos.m_45605_() + j4 + j3);
                                 d1 += (double)k4 / (double)(i * i);
                                 multiset.add(blockstate.m_60780_(p_42894_, blockpos$mutableblockpos1));
                              }
                           }
                        }

                        k3 = k3 / (i * i);
                        double d2 = (d1 - d0) * 4.0D / (double)(i + 4) + ((double)(k1 + l1 & 1) - 0.5D) * 0.4D;
                        int i5 = 1;
                        if (d2 > 0.6D) {
                           i5 = 2;
                        }

                        if (d2 < -0.6D) {
                           i5 = 0;
                        }

                        MaterialColor materialcolor = Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MaterialColor.f_76398_);
                        if (materialcolor == MaterialColor.f_76410_) {
                           d2 = (double)k3 * 0.1D + (double)(k1 + l1 & 1) * 0.2D;
                           i5 = 1;
                           if (d2 < 0.5D) {
                              i5 = 2;
                           }

                           if (d2 > 0.9D) {
                              i5 = 0;
                           }
                        }

                        d0 = d1;
                        if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0)) {
                           flag |= p_42896_.m_164792_(k1, l1, (byte)(materialcolor.f_76397_ * 4 + i5));
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private BlockState m_42900_(Level p_42901_, BlockState p_42902_, BlockPos p_42903_) {
      FluidState fluidstate = p_42902_.m_60819_();
      return !fluidstate.m_76178_() && !p_42902_.m_60783_(p_42901_, p_42903_, Direction.UP) ? fluidstate.m_76188_() : p_42902_;
   }

   private static boolean m_42904_(Biome[] p_42905_, int p_42906_, int p_42907_, int p_42908_) {
      return p_42905_[p_42907_ * p_42906_ + p_42908_ * p_42906_ * 128 * p_42906_].m_47545_() >= 0.0F;
   }

   public static void m_42850_(ServerLevel p_42851_, ItemStack p_42852_) {
      MapItemSavedData mapitemsaveddata = m_42853_(p_42852_, p_42851_);
      if (mapitemsaveddata != null) {
         if (p_42851_.m_46472_() == mapitemsaveddata.f_77887_) {
            int i = 1 << mapitemsaveddata.f_77890_;
            int j = mapitemsaveddata.f_77885_;
            int k = mapitemsaveddata.f_77886_;
            Biome[] abiome = new Biome[128 * i * 128 * i];

            for(int l = 0; l < 128 * i; ++l) {
               for(int i1 = 0; i1 < 128 * i; ++i1) {
                  abiome[l * 128 * i + i1] = p_42851_.m_46857_(new BlockPos((j / i - 64) * i + i1, 0, (k / i - 64) * i + l));
               }
            }

            for(int l1 = 0; l1 < 128; ++l1) {
               for(int i2 = 0; i2 < 128; ++i2) {
                  if (l1 > 0 && i2 > 0 && l1 < 127 && i2 < 127) {
                     Biome biome = abiome[l1 * i + i2 * i * 128 * i];
                     int j1 = 8;
                     if (m_42904_(abiome, i, l1 - 1, i2 - 1)) {
                        --j1;
                     }

                     if (m_42904_(abiome, i, l1 - 1, i2 + 1)) {
                        --j1;
                     }

                     if (m_42904_(abiome, i, l1 - 1, i2)) {
                        --j1;
                     }

                     if (m_42904_(abiome, i, l1 + 1, i2 - 1)) {
                        --j1;
                     }

                     if (m_42904_(abiome, i, l1 + 1, i2 + 1)) {
                        --j1;
                     }

                     if (m_42904_(abiome, i, l1 + 1, i2)) {
                        --j1;
                     }

                     if (m_42904_(abiome, i, l1, i2 - 1)) {
                        --j1;
                     }

                     if (m_42904_(abiome, i, l1, i2 + 1)) {
                        --j1;
                     }

                     int k1 = 3;
                     MaterialColor materialcolor = MaterialColor.f_76398_;
                     if (biome.m_47545_() < 0.0F) {
                        materialcolor = MaterialColor.f_76413_;
                        if (j1 > 7 && i2 % 2 == 0) {
                           k1 = (l1 + (int)(Mth.m_14031_((float)i2 + 0.0F) * 7.0F)) / 8 % 5;
                           if (k1 == 3) {
                              k1 = 1;
                           } else if (k1 == 4) {
                              k1 = 0;
                           }
                        } else if (j1 > 7) {
                           materialcolor = MaterialColor.f_76398_;
                        } else if (j1 > 5) {
                           k1 = 1;
                        } else if (j1 > 3) {
                           k1 = 0;
                        } else if (j1 > 1) {
                           k1 = 0;
                        }
                     } else if (j1 > 0) {
                        materialcolor = MaterialColor.f_76362_;
                        if (j1 > 3) {
                           k1 = 1;
                        } else {
                           k1 = 3;
                        }
                     }

                     if (materialcolor != MaterialColor.f_76398_) {
                        mapitemsaveddata.m_164803_(l1, i2, (byte)(materialcolor.f_76397_ * 4 + k1));
                     }
                  }
               }
            }

         }
      }
   }

   public void m_6883_(ItemStack p_42870_, Level p_42871_, Entity p_42872_, int p_42873_, boolean p_42874_) {
      if (!p_42871_.f_46443_) {
         MapItemSavedData mapitemsaveddata = m_42853_(p_42870_, p_42871_);
         if (mapitemsaveddata != null) {
            if (p_42872_ instanceof Player) {
               Player player = (Player)p_42872_;
               mapitemsaveddata.m_77918_(player, p_42870_);
            }

            if (!mapitemsaveddata.f_77892_ && (p_42874_ || p_42872_ instanceof Player && ((Player)p_42872_).m_21206_() == p_42870_)) {
               this.m_42893_(p_42871_, p_42872_, mapitemsaveddata);
            }

         }
      }
   }

   @Nullable
   public Packet<?> m_7233_(ItemStack p_42876_, Level p_42877_, Player p_42878_) {
      Integer integer = m_151131_(p_42876_);
      MapItemSavedData mapitemsaveddata = m_151128_(integer, p_42877_);
      return mapitemsaveddata != null ? mapitemsaveddata.m_164796_(integer, p_42878_) : null;
   }

   public void m_7836_(ItemStack p_42913_, Level p_42914_, Player p_42915_) {
      CompoundTag compoundtag = p_42913_.m_41783_();
      if (compoundtag != null && compoundtag.m_128425_("map_scale_direction", 99)) {
         m_42856_(p_42913_, p_42914_, compoundtag.m_128451_("map_scale_direction"));
         compoundtag.m_128473_("map_scale_direction");
      } else if (compoundtag != null && compoundtag.m_128425_("map_to_lock", 1) && compoundtag.m_128471_("map_to_lock")) {
         m_42897_(p_42914_, p_42913_);
         compoundtag.m_128473_("map_to_lock");
      }

   }

   private static void m_42856_(ItemStack p_42857_, Level p_42858_, int p_42859_) {
      MapItemSavedData mapitemsaveddata = m_42853_(p_42857_, p_42858_);
      if (mapitemsaveddata != null) {
         int i = p_42858_.m_7354_();
         p_42858_.m_142325_(m_42848_(i), mapitemsaveddata.m_164787_(p_42859_));
         m_151108_(p_42857_, i);
      }

   }

   public static void m_42897_(Level p_42898_, ItemStack p_42899_) {
      MapItemSavedData mapitemsaveddata = m_42853_(p_42899_, p_42898_);
      if (mapitemsaveddata != null) {
         int i = p_42898_.m_7354_();
         String s = m_42848_(i);
         MapItemSavedData mapitemsaveddata1 = mapitemsaveddata.m_164775_();
         p_42898_.m_142325_(s, mapitemsaveddata1);
         m_151108_(p_42899_, i);
      }

   }

   public void m_7373_(ItemStack p_42880_, @Nullable Level p_42881_, List<Component> p_42882_, TooltipFlag p_42883_) {
      Integer integer = m_151131_(p_42880_);
      MapItemSavedData mapitemsaveddata = p_42881_ == null ? null : m_151128_(integer, p_42881_);
      if (mapitemsaveddata != null && mapitemsaveddata.f_77892_) {
         p_42882_.add((new TranslatableComponent("filled_map.locked", integer)).m_130940_(ChatFormatting.GRAY));
      }

      if (p_42883_.m_7050_()) {
         if (mapitemsaveddata != null) {
            p_42882_.add((new TranslatableComponent("filled_map.id", integer)).m_130940_(ChatFormatting.GRAY));
            p_42882_.add((new TranslatableComponent("filled_map.scale", 1 << mapitemsaveddata.f_77890_)).m_130940_(ChatFormatting.GRAY));
            p_42882_.add((new TranslatableComponent("filled_map.level", mapitemsaveddata.f_77890_, 4)).m_130940_(ChatFormatting.GRAY));
         } else {
            p_42882_.add((new TranslatableComponent("filled_map.unknown")).m_130940_(ChatFormatting.GRAY));
         }
      }

   }

   public static int m_42918_(ItemStack p_42919_) {
      CompoundTag compoundtag = p_42919_.m_41737_("display");
      if (compoundtag != null && compoundtag.m_128425_("MapColor", 99)) {
         int i = compoundtag.m_128451_("MapColor");
         return -16777216 | i & 16777215;
      } else {
         return -12173266;
      }
   }

   public InteractionResult m_6225_(UseOnContext p_42885_) {
      BlockState blockstate = p_42885_.m_43725_().m_8055_(p_42885_.m_8083_());
      if (blockstate.m_60620_(BlockTags.f_13028_)) {
         if (!p_42885_.m_43725_().f_46443_) {
            MapItemSavedData mapitemsaveddata = m_42853_(p_42885_.m_43722_(), p_42885_.m_43725_());
            if (mapitemsaveddata != null && !mapitemsaveddata.m_77934_(p_42885_.m_43725_(), p_42885_.m_8083_())) {
               return InteractionResult.FAIL;
            }
         }

         return InteractionResult.m_19078_(p_42885_.m_43725_().f_46443_);
      } else {
         return super.m_6225_(p_42885_);
      }
   }
}
