package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpgradeData {
   private static final Logger f_63321_ = LogManager.getLogger();
   public static final UpgradeData f_63320_ = new UpgradeData(EmptyBlockGetter.INSTANCE);
   private static final String f_156504_ = "Indices";
   private static final Direction8[] f_63322_ = Direction8.values();
   private final EnumSet<Direction8> f_63323_ = EnumSet.noneOf(Direction8.class);
   private final int[][] f_63324_;
   static final Map<Block, UpgradeData.BlockFixer> f_63325_ = new IdentityHashMap<>();
   static final Set<UpgradeData.BlockFixer> f_63326_ = Sets.newHashSet();

   private UpgradeData(LevelHeightAccessor p_156506_) {
      this.f_63324_ = new int[p_156506_.m_151559_()][];
   }

   public UpgradeData(CompoundTag p_156508_, LevelHeightAccessor p_156509_) {
      this(p_156509_);
      if (p_156508_.m_128425_("Indices", 10)) {
         CompoundTag compoundtag = p_156508_.m_128469_("Indices");

         for(int i = 0; i < this.f_63324_.length; ++i) {
            String s = String.valueOf(i);
            if (compoundtag.m_128425_(s, 11)) {
               this.f_63324_[i] = compoundtag.m_128465_(s);
            }
         }
      }

      int j = p_156508_.m_128451_("Sides");

      for(Direction8 direction8 : Direction8.values()) {
         if ((j & 1 << direction8.ordinal()) != 0) {
            this.f_63323_.add(direction8);
         }
      }

   }

   public void m_63341_(LevelChunk p_63342_) {
      this.m_63347_(p_63342_);

      for(Direction8 direction8 : f_63322_) {
         m_63343_(p_63342_, direction8);
      }

      Level level = p_63342_.m_62953_();
      f_63326_.forEach((p_63334_) -> {
         p_63334_.m_5870_(level);
      });
   }

   private static void m_63343_(LevelChunk p_63344_, Direction8 p_63345_) {
      Level level = p_63344_.m_62953_();
      if (p_63344_.m_7387_().f_63323_.remove(p_63345_)) {
         Set<Direction> set = p_63345_.m_122593_();
         int i = 0;
         int j = 15;
         boolean flag = set.contains(Direction.EAST);
         boolean flag1 = set.contains(Direction.WEST);
         boolean flag2 = set.contains(Direction.SOUTH);
         boolean flag3 = set.contains(Direction.NORTH);
         boolean flag4 = set.size() == 1;
         ChunkPos chunkpos = p_63344_.m_7697_();
         int k = chunkpos.m_45604_() + (!flag4 || !flag3 && !flag2 ? (flag1 ? 0 : 15) : 1);
         int l = chunkpos.m_45604_() + (!flag4 || !flag3 && !flag2 ? (flag1 ? 0 : 15) : 14);
         int i1 = chunkpos.m_45605_() + (!flag4 || !flag && !flag1 ? (flag3 ? 0 : 15) : 1);
         int j1 = chunkpos.m_45605_() + (!flag4 || !flag && !flag1 ? (flag3 ? 0 : 15) : 14);
         Direction[] adirection = Direction.values();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(BlockPos blockpos : BlockPos.m_121976_(k, level.m_141937_(), i1, l, level.m_151558_() - 1, j1)) {
            BlockState blockstate = level.m_8055_(blockpos);
            BlockState blockstate1 = blockstate;

            for(Direction direction : adirection) {
               blockpos$mutableblockpos.m_122159_(blockpos, direction);
               blockstate1 = m_63335_(blockstate1, direction, level, blockpos, blockpos$mutableblockpos);
            }

            Block.m_49902_(blockstate, blockstate1, level, blockpos, 18);
         }

      }
   }

   private static BlockState m_63335_(BlockState p_63336_, Direction p_63337_, LevelAccessor p_63338_, BlockPos p_63339_, BlockPos p_63340_) {
      return f_63325_.getOrDefault(p_63336_.m_60734_(), UpgradeData.BlockFixers.DEFAULT).m_5731_(p_63336_, p_63337_, p_63338_.m_8055_(p_63340_), p_63338_, p_63339_, p_63340_);
   }

   private void m_63347_(LevelChunk p_63348_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
      ChunkPos chunkpos = p_63348_.m_7697_();
      LevelAccessor levelaccessor = p_63348_.m_62953_();

      for(int i = 0; i < this.f_63324_.length; ++i) {
         LevelChunkSection levelchunksection = p_63348_.m_7103_()[i];
         int[] aint = this.f_63324_[i];
         this.f_63324_[i] = null;
         if (levelchunksection != null && aint != null && aint.length > 0) {
            Direction[] adirection = Direction.values();
            PalettedContainer<BlockState> palettedcontainer = levelchunksection.m_63019_();

            for(int j : aint) {
               int k = j & 15;
               int l = j >> 8 & 15;
               int i1 = j >> 4 & 15;
               blockpos$mutableblockpos.m_122178_(chunkpos.m_45604_() + k, levelchunksection.m_63017_() + l, chunkpos.m_45605_() + i1);
               BlockState blockstate = palettedcontainer.m_63085_(j);
               BlockState blockstate1 = blockstate;

               for(Direction direction : adirection) {
                  blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, direction);
                  if (SectionPos.m_123171_(blockpos$mutableblockpos.m_123341_()) == chunkpos.f_45578_ && SectionPos.m_123171_(blockpos$mutableblockpos.m_123343_()) == chunkpos.f_45579_) {
                     blockstate1 = m_63335_(blockstate1, direction, levelaccessor, blockpos$mutableblockpos, blockpos$mutableblockpos1);
                  }
               }

               Block.m_49902_(blockstate, blockstate1, levelaccessor, blockpos$mutableblockpos, 18);
            }
         }
      }

      for(int j1 = 0; j1 < this.f_63324_.length; ++j1) {
         if (this.f_63324_[j1] != null) {
            f_63321_.warn("Discarding update data for section {} for chunk ({} {})", levelaccessor.m_151568_(j1), chunkpos.f_45578_, chunkpos.f_45579_);
         }

         this.f_63324_[j1] = null;
      }

   }

   public boolean m_63331_() {
      for(int[] aint : this.f_63324_) {
         if (aint != null) {
            return false;
         }
      }

      return this.f_63323_.isEmpty();
   }

   public CompoundTag m_63346_() {
      CompoundTag compoundtag = new CompoundTag();
      CompoundTag compoundtag1 = new CompoundTag();

      for(int i = 0; i < this.f_63324_.length; ++i) {
         String s = String.valueOf(i);
         if (this.f_63324_[i] != null && this.f_63324_[i].length != 0) {
            compoundtag1.m_128385_(s, this.f_63324_[i]);
         }
      }

      if (!compoundtag1.m_128456_()) {
         compoundtag.m_128365_("Indices", compoundtag1);
      }

      int j = 0;

      for(Direction8 direction8 : this.f_63323_) {
         j |= 1 << direction8.ordinal();
      }

      compoundtag.m_128344_("Sides", (byte)j);
      return compoundtag;
   }

   public interface BlockFixer {
      BlockState m_5731_(BlockState p_63352_, Direction p_63353_, BlockState p_63354_, LevelAccessor p_63355_, BlockPos p_63356_, BlockPos p_63357_);

      default void m_5870_(LevelAccessor p_63351_) {
      }
   }

   static enum BlockFixers implements UpgradeData.BlockFixer {
      BLACKLIST(Blocks.f_50455_, Blocks.f_50142_, Blocks.f_50506_, Blocks.f_50507_, Blocks.f_50508_, Blocks.f_50509_, Blocks.f_50510_, Blocks.f_50511_, Blocks.f_50512_, Blocks.f_50513_, Blocks.f_50514_, Blocks.f_50515_, Blocks.f_50516_, Blocks.f_50517_, Blocks.f_50518_, Blocks.f_50519_, Blocks.f_50573_, Blocks.f_50574_, Blocks.f_50322_, Blocks.f_50323_, Blocks.f_50324_, Blocks.f_50260_, Blocks.f_49994_, Blocks.f_49992_, Blocks.f_49993_, Blocks.f_50095_, Blocks.f_50149_, Blocks.f_50150_, Blocks.f_50151_, Blocks.f_50152_, Blocks.f_50153_, Blocks.f_50158_, Blocks.f_50159_, Blocks.f_50160_, Blocks.f_50161_, Blocks.f_50162_, Blocks.f_50163_) {
         public BlockState m_5731_(BlockState p_63394_, Direction p_63395_, BlockState p_63396_, LevelAccessor p_63397_, BlockPos p_63398_, BlockPos p_63399_) {
            return p_63394_;
         }
      },
      DEFAULT {
         public BlockState m_5731_(BlockState p_63405_, Direction p_63406_, BlockState p_63407_, LevelAccessor p_63408_, BlockPos p_63409_, BlockPos p_63410_) {
            return p_63405_.m_60728_(p_63406_, p_63408_.m_8055_(p_63410_), p_63408_, p_63409_, p_63410_);
         }
      },
      CHEST(Blocks.f_50087_, Blocks.f_50325_) {
         public BlockState m_5731_(BlockState p_63416_, Direction p_63417_, BlockState p_63418_, LevelAccessor p_63419_, BlockPos p_63420_, BlockPos p_63421_) {
            if (p_63418_.m_60713_(p_63416_.m_60734_()) && p_63417_.m_122434_().m_122479_() && p_63416_.m_61143_(ChestBlock.f_51479_) == ChestType.SINGLE && p_63418_.m_61143_(ChestBlock.f_51479_) == ChestType.SINGLE) {
               Direction direction = p_63416_.m_61143_(ChestBlock.f_51478_);
               if (p_63417_.m_122434_() != direction.m_122434_() && direction == p_63418_.m_61143_(ChestBlock.f_51478_)) {
                  ChestType chesttype = p_63417_ == direction.m_122427_() ? ChestType.LEFT : ChestType.RIGHT;
                  p_63419_.m_7731_(p_63421_, p_63418_.m_61124_(ChestBlock.f_51479_, chesttype.m_61486_()), 18);
                  if (direction == Direction.NORTH || direction == Direction.EAST) {
                     BlockEntity blockentity = p_63419_.m_7702_(p_63420_);
                     BlockEntity blockentity1 = p_63419_.m_7702_(p_63421_);
                     if (blockentity instanceof ChestBlockEntity && blockentity1 instanceof ChestBlockEntity) {
                        ChestBlockEntity.m_59103_((ChestBlockEntity)blockentity, (ChestBlockEntity)blockentity1);
                     }
                  }

                  return p_63416_.m_61124_(ChestBlock.f_51479_, chesttype);
               }
            }

            return p_63416_;
         }
      },
      LEAVES(true, Blocks.f_50054_, Blocks.f_50052_, Blocks.f_50055_, Blocks.f_50053_, Blocks.f_50050_, Blocks.f_50051_) {
         private final ThreadLocal<List<ObjectSet<BlockPos>>> f_63422_ = ThreadLocal.withInitial(() -> {
            return Lists.newArrayListWithCapacity(7);
         });

         public BlockState m_5731_(BlockState p_63432_, Direction p_63433_, BlockState p_63434_, LevelAccessor p_63435_, BlockPos p_63436_, BlockPos p_63437_) {
            BlockState blockstate = p_63432_.m_60728_(p_63433_, p_63435_.m_8055_(p_63437_), p_63435_, p_63436_, p_63437_);
            if (p_63432_ != blockstate) {
               int i = blockstate.m_61143_(BlockStateProperties.f_61414_);
               List<ObjectSet<BlockPos>> list = this.f_63422_.get();
               if (list.isEmpty()) {
                  for(int j = 0; j < 7; ++j) {
                     list.add(new ObjectOpenHashSet<>());
                  }
               }

               list.get(i).add(p_63436_.m_7949_());
            }

            return p_63432_;
         }

         public void m_5870_(LevelAccessor p_63430_) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            List<ObjectSet<BlockPos>> list = this.f_63422_.get();

            for(int i = 2; i < list.size(); ++i) {
               int j = i - 1;
               ObjectSet<BlockPos> objectset = list.get(j);
               ObjectSet<BlockPos> objectset1 = list.get(i);

               for(BlockPos blockpos : objectset) {
                  BlockState blockstate = p_63430_.m_8055_(blockpos);
                  if (blockstate.m_61143_(BlockStateProperties.f_61414_) >= j) {
                     p_63430_.m_7731_(blockpos, blockstate.m_61124_(BlockStateProperties.f_61414_, Integer.valueOf(j)), 18);
                     if (i != 7) {
                        for(Direction direction : f_63363_) {
                           blockpos$mutableblockpos.m_122159_(blockpos, direction);
                           BlockState blockstate1 = p_63430_.m_8055_(blockpos$mutableblockpos);
                           if (blockstate1.m_61138_(BlockStateProperties.f_61414_) && blockstate.m_61143_(BlockStateProperties.f_61414_) > i) {
                              objectset1.add(blockpos$mutableblockpos.m_7949_());
                           }
                        }
                     }
                  }
               }
            }

            list.clear();
         }
      },
      STEM_BLOCK(Blocks.f_50190_, Blocks.f_50189_) {
         public BlockState m_5731_(BlockState p_63443_, Direction p_63444_, BlockState p_63445_, LevelAccessor p_63446_, BlockPos p_63447_, BlockPos p_63448_) {
            if (p_63443_.m_61143_(StemBlock.f_57013_) == 7) {
               StemGrownBlock stemgrownblock = ((StemBlock)p_63443_.m_60734_()).m_57056_();
               if (p_63445_.m_60713_(stemgrownblock)) {
                  return stemgrownblock.m_7810_().m_49966_().m_61124_(HorizontalDirectionalBlock.f_54117_, p_63444_);
               }
            }

            return p_63443_;
         }
      };

      public static final Direction[] f_63363_ = Direction.values();

      BlockFixers(Block... p_63380_) {
         this(false, p_63380_);
      }

      BlockFixers(boolean p_63369_, Block... p_63370_) {
         for(Block block : p_63370_) {
            UpgradeData.f_63325_.put(block, this);
         }

         if (p_63369_) {
            UpgradeData.f_63326_.add(this);
         }

      }
   }
}