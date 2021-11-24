package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public class StructureTemplate {
   public static final String f_163789_ = "palette";
   public static final String f_163790_ = "palettes";
   public static final String f_163791_ = "entities";
   public static final String f_163792_ = "blocks";
   public static final String f_163793_ = "pos";
   public static final String f_163794_ = "state";
   public static final String f_163795_ = "nbt";
   public static final String f_163796_ = "pos";
   public static final String f_163797_ = "blockPos";
   public static final String f_163798_ = "nbt";
   public static final String f_163799_ = "size";
   static final int f_163800_ = 16;
   private final List<StructureTemplate.Palette> f_74482_ = Lists.newArrayList();
   private final List<StructureTemplate.StructureEntityInfo> f_74483_ = Lists.newArrayList();
   private Vec3i f_74484_ = Vec3i.f_123288_;
   private String f_74485_ = "?";

   public Vec3i m_163801_() {
      return this.f_74484_;
   }

   public void m_74612_(String p_74613_) {
      this.f_74485_ = p_74613_;
   }

   public String m_74627_() {
      return this.f_74485_;
   }

   public void m_163802_(Level p_163803_, BlockPos p_163804_, Vec3i p_163805_, boolean p_163806_, @Nullable Block p_163807_) {
      if (p_163805_.m_123341_() >= 1 && p_163805_.m_123342_() >= 1 && p_163805_.m_123343_() >= 1) {
         BlockPos blockpos = p_163804_.m_141952_(p_163805_).m_142082_(-1, -1, -1);
         List<StructureTemplate.StructureBlockInfo> list = Lists.newArrayList();
         List<StructureTemplate.StructureBlockInfo> list1 = Lists.newArrayList();
         List<StructureTemplate.StructureBlockInfo> list2 = Lists.newArrayList();
         BlockPos blockpos1 = new BlockPos(Math.min(p_163804_.m_123341_(), blockpos.m_123341_()), Math.min(p_163804_.m_123342_(), blockpos.m_123342_()), Math.min(p_163804_.m_123343_(), blockpos.m_123343_()));
         BlockPos blockpos2 = new BlockPos(Math.max(p_163804_.m_123341_(), blockpos.m_123341_()), Math.max(p_163804_.m_123342_(), blockpos.m_123342_()), Math.max(p_163804_.m_123343_(), blockpos.m_123343_()));
         this.f_74484_ = p_163805_;

         for(BlockPos blockpos3 : BlockPos.m_121940_(blockpos1, blockpos2)) {
            BlockPos blockpos4 = blockpos3.m_141950_(blockpos1);
            BlockState blockstate = p_163803_.m_8055_(blockpos3);
            if (p_163807_ == null || !blockstate.m_60713_(p_163807_)) {
               BlockEntity blockentity = p_163803_.m_7702_(blockpos3);
               StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo;
               if (blockentity != null) {
                  CompoundTag compoundtag = blockentity.m_6945_(new CompoundTag());
                  compoundtag.m_128473_("x");
                  compoundtag.m_128473_("y");
                  compoundtag.m_128473_("z");
                  structuretemplate$structureblockinfo = new StructureTemplate.StructureBlockInfo(blockpos4, blockstate, compoundtag.m_6426_());
               } else {
                  structuretemplate$structureblockinfo = new StructureTemplate.StructureBlockInfo(blockpos4, blockstate, (CompoundTag)null);
               }

               m_74573_(structuretemplate$structureblockinfo, list, list1, list2);
            }
         }

         List<StructureTemplate.StructureBlockInfo> list3 = m_74614_(list, list1, list2);
         this.f_74482_.clear();
         this.f_74482_.add(new StructureTemplate.Palette(list3));
         if (p_163806_) {
            this.m_74500_(p_163803_, blockpos1, blockpos2.m_142082_(1, 1, 1));
         } else {
            this.f_74483_.clear();
         }

      }
   }

   private static void m_74573_(StructureTemplate.StructureBlockInfo p_74574_, List<StructureTemplate.StructureBlockInfo> p_74575_, List<StructureTemplate.StructureBlockInfo> p_74576_, List<StructureTemplate.StructureBlockInfo> p_74577_) {
      if (p_74574_.f_74677_ != null) {
         p_74576_.add(p_74574_);
      } else if (!p_74574_.f_74676_.m_60734_().m_49967_() && p_74574_.f_74676_.m_60838_(EmptyBlockGetter.INSTANCE, BlockPos.f_121853_)) {
         p_74575_.add(p_74574_);
      } else {
         p_74577_.add(p_74574_);
      }

   }

   private static List<StructureTemplate.StructureBlockInfo> m_74614_(List<StructureTemplate.StructureBlockInfo> p_74615_, List<StructureTemplate.StructureBlockInfo> p_74616_, List<StructureTemplate.StructureBlockInfo> p_74617_) {
      Comparator<StructureTemplate.StructureBlockInfo> comparator = Comparator.<StructureTemplate.StructureBlockInfo>comparingInt((p_74641_) -> {
         return p_74641_.f_74675_.m_123342_();
      }).thenComparingInt((p_74637_) -> {
         return p_74637_.f_74675_.m_123341_();
      }).thenComparingInt((p_74572_) -> {
         return p_74572_.f_74675_.m_123343_();
      });
      p_74615_.sort(comparator);
      p_74617_.sort(comparator);
      p_74616_.sort(comparator);
      List<StructureTemplate.StructureBlockInfo> list = Lists.newArrayList();
      list.addAll(p_74615_);
      list.addAll(p_74617_);
      list.addAll(p_74616_);
      return list;
   }

   private void m_74500_(Level p_74501_, BlockPos p_74502_, BlockPos p_74503_) {
      List<Entity> list = p_74501_.m_6443_(Entity.class, new AABB(p_74502_, p_74503_), (p_74499_) -> {
         return !(p_74499_ instanceof Player);
      });
      this.f_74483_.clear();

      for(Entity entity : list) {
         Vec3 vec3 = new Vec3(entity.m_20185_() - (double)p_74502_.m_123341_(), entity.m_20186_() - (double)p_74502_.m_123342_(), entity.m_20189_() - (double)p_74502_.m_123343_());
         CompoundTag compoundtag = new CompoundTag();
         entity.m_20223_(compoundtag);
         BlockPos blockpos;
         if (entity instanceof Painting) {
            blockpos = ((Painting)entity).m_31748_().m_141950_(p_74502_);
         } else {
            blockpos = new BlockPos(vec3);
         }

         this.f_74483_.add(new StructureTemplate.StructureEntityInfo(vec3, blockpos, compoundtag.m_6426_()));
      }

   }

   public List<StructureTemplate.StructureBlockInfo> m_74603_(BlockPos p_74604_, StructurePlaceSettings p_74605_, Block p_74606_) {
      return this.m_74607_(p_74604_, p_74605_, p_74606_, true);
   }

   public List<StructureTemplate.StructureBlockInfo> m_74607_(BlockPos p_74608_, StructurePlaceSettings p_74609_, Block p_74610_, boolean p_74611_) {
      List<StructureTemplate.StructureBlockInfo> list = Lists.newArrayList();
      BoundingBox boundingbox = p_74609_.m_74409_();
      if (this.f_74482_.isEmpty()) {
         return Collections.emptyList();
      } else {
         for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : p_74609_.m_74387_(this.f_74482_, p_74608_).m_74653_(p_74610_)) {
            BlockPos blockpos = p_74611_ ? m_74563_(p_74609_, structuretemplate$structureblockinfo.f_74675_).m_141952_(p_74608_) : structuretemplate$structureblockinfo.f_74675_;
            if (boundingbox == null || boundingbox.m_71051_(blockpos)) {
               list.add(new StructureTemplate.StructureBlockInfo(blockpos, structuretemplate$structureblockinfo.f_74676_.m_60717_(p_74609_.m_74404_()), structuretemplate$structureblockinfo.f_74677_));
            }
         }

         return list;
      }
   }

   public BlockPos m_74566_(StructurePlaceSettings p_74567_, BlockPos p_74568_, StructurePlaceSettings p_74569_, BlockPos p_74570_) {
      BlockPos blockpos = m_74563_(p_74567_, p_74568_);
      BlockPos blockpos1 = m_74563_(p_74569_, p_74570_);
      return blockpos.m_141950_(blockpos1);
   }

   public static BlockPos m_74563_(StructurePlaceSettings p_74564_, BlockPos p_74565_) {
      return m_74593_(p_74565_, p_74564_.m_74401_(), p_74564_.m_74404_(), p_74564_.m_74407_());
   }

   public static Vec3 transformedVec3d(StructurePlaceSettings placementIn, Vec3 pos) {
      return m_74578_(pos, placementIn.m_74401_(), placementIn.m_74404_(), placementIn.m_74407_());
   }

   public boolean m_74536_(ServerLevelAccessor p_74537_, BlockPos p_74538_, BlockPos p_74539_, StructurePlaceSettings p_74540_, Random p_74541_, int p_74542_) {
      if (this.f_74482_.isEmpty()) {
         return false;
      } else {
         List<StructureTemplate.StructureBlockInfo> list = p_74540_.m_74387_(this.f_74482_, p_74538_).m_74652_();
         if ((!list.isEmpty() || !p_74540_.m_74408_() && !this.f_74483_.isEmpty()) && this.f_74484_.m_123341_() >= 1 && this.f_74484_.m_123342_() >= 1 && this.f_74484_.m_123343_() >= 1) {
            BoundingBox boundingbox = p_74540_.m_74409_();
            List<BlockPos> list1 = Lists.newArrayListWithCapacity(p_74540_.m_74413_() ? list.size() : 0);
            List<BlockPos> list2 = Lists.newArrayListWithCapacity(p_74540_.m_74413_() ? list.size() : 0);
            List<Pair<BlockPos, CompoundTag>> list3 = Lists.newArrayListWithCapacity(list.size());
            int i = Integer.MAX_VALUE;
            int j = Integer.MAX_VALUE;
            int k = Integer.MAX_VALUE;
            int l = Integer.MIN_VALUE;
            int i1 = Integer.MIN_VALUE;
            int j1 = Integer.MIN_VALUE;

            for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : processBlockInfos(p_74537_, p_74538_, p_74539_, p_74540_, list, this)) {
               BlockPos blockpos = structuretemplate$structureblockinfo.f_74675_;
               if (boundingbox == null || boundingbox.m_71051_(blockpos)) {
                  FluidState fluidstate = p_74540_.m_74413_() ? p_74537_.m_6425_(blockpos) : null;
                  BlockState blockstate = structuretemplate$structureblockinfo.f_74676_.m_60715_(p_74540_.m_74401_()).m_60717_(p_74540_.m_74404_());
                  if (structuretemplate$structureblockinfo.f_74677_ != null) {
                     BlockEntity blockentity = p_74537_.m_7702_(blockpos);
                     Clearable.m_18908_(blockentity);
                     p_74537_.m_7731_(blockpos, Blocks.f_50375_.m_49966_(), 20);
                  }

                  if (p_74537_.m_7731_(blockpos, blockstate, p_74542_)) {
                     i = Math.min(i, blockpos.m_123341_());
                     j = Math.min(j, blockpos.m_123342_());
                     k = Math.min(k, blockpos.m_123343_());
                     l = Math.max(l, blockpos.m_123341_());
                     i1 = Math.max(i1, blockpos.m_123342_());
                     j1 = Math.max(j1, blockpos.m_123343_());
                     list3.add(Pair.of(blockpos, structuretemplate$structureblockinfo.f_74677_));
                     if (structuretemplate$structureblockinfo.f_74677_ != null) {
                        BlockEntity blockentity1 = p_74537_.m_7702_(blockpos);
                        if (blockentity1 != null) {
                           structuretemplate$structureblockinfo.f_74677_.m_128405_("x", blockpos.m_123341_());
                           structuretemplate$structureblockinfo.f_74677_.m_128405_("y", blockpos.m_123342_());
                           structuretemplate$structureblockinfo.f_74677_.m_128405_("z", blockpos.m_123343_());
                           if (blockentity1 instanceof RandomizableContainerBlockEntity) {
                              structuretemplate$structureblockinfo.f_74677_.m_128356_("LootTableSeed", p_74541_.nextLong());
                           }

                           blockentity1.m_142466_(structuretemplate$structureblockinfo.f_74677_);
                        }
                     }

                     if (fluidstate != null) {
                        if (blockstate.m_60819_().m_76170_()) {
                           list2.add(blockpos);
                        } else if (blockstate.m_60734_() instanceof LiquidBlockContainer) {
                           ((LiquidBlockContainer)blockstate.m_60734_()).m_7361_(p_74537_, blockpos, blockstate, fluidstate);
                           if (!fluidstate.m_76170_()) {
                              list1.add(blockpos);
                           }
                        }
                     }
                  }
               }
            }

            boolean flag = true;
            Direction[] adirection = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

            while(flag && !list1.isEmpty()) {
               flag = false;
               Iterator<BlockPos> iterator = list1.iterator();

               while(iterator.hasNext()) {
                  BlockPos blockpos3 = iterator.next();
                  FluidState fluidstate2 = p_74537_.m_6425_(blockpos3);

                  for(int i2 = 0; i2 < adirection.length && !fluidstate2.m_76170_(); ++i2) {
                     BlockPos blockpos1 = blockpos3.m_142300_(adirection[i2]);
                     FluidState fluidstate1 = p_74537_.m_6425_(blockpos1);
                     if (fluidstate1.m_76170_() && !list2.contains(blockpos1)) {
                        fluidstate2 = fluidstate1;
                     }
                  }

                  if (fluidstate2.m_76170_()) {
                     BlockState blockstate1 = p_74537_.m_8055_(blockpos3);
                     Block block = blockstate1.m_60734_();
                     if (block instanceof LiquidBlockContainer) {
                        ((LiquidBlockContainer)block).m_7361_(p_74537_, blockpos3, blockstate1, fluidstate2);
                        flag = true;
                        iterator.remove();
                     }
                  }
               }
            }

            if (i <= l) {
               if (!p_74540_.m_74410_()) {
                  DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(l - i + 1, i1 - j + 1, j1 - k + 1);
                  int k1 = i;
                  int l1 = j;
                  int j2 = k;

                  for(Pair<BlockPos, CompoundTag> pair1 : list3) {
                     BlockPos blockpos2 = pair1.getFirst();
                     discretevoxelshape.m_142703_(blockpos2.m_123341_() - k1, blockpos2.m_123342_() - l1, blockpos2.m_123343_() - j2);
                  }

                  m_74510_(p_74537_, p_74542_, discretevoxelshape, k1, l1, j2);
               }

               for(Pair<BlockPos, CompoundTag> pair : list3) {
                  BlockPos blockpos4 = pair.getFirst();
                  if (!p_74540_.m_74410_()) {
                     BlockState blockstate2 = p_74537_.m_8055_(blockpos4);
                     BlockState blockstate3 = Block.m_49931_(blockstate2, p_74537_, blockpos4);
                     if (blockstate2 != blockstate3) {
                        p_74537_.m_7731_(blockpos4, blockstate3, p_74542_ & -2 | 16);
                     }

                     p_74537_.m_6289_(blockpos4, blockstate3.m_60734_());
                  }

                  if (pair.getSecond() != null) {
                     BlockEntity blockentity2 = p_74537_.m_7702_(blockpos4);
                     if (blockentity2 != null) {
                        blockentity2.m_6596_();
                     }
                  }
               }
            }

            if (!p_74540_.m_74408_()) {
               this.addEntitiesToWorld(p_74537_, p_74538_, p_74540_);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static void m_74510_(LevelAccessor p_74511_, int p_74512_, DiscreteVoxelShape p_74513_, int p_74514_, int p_74515_, int p_74516_) {
      p_74513_.m_82810_((p_74494_, p_74495_, p_74496_, p_74497_) -> {
         BlockPos blockpos = new BlockPos(p_74514_ + p_74495_, p_74515_ + p_74496_, p_74516_ + p_74497_);
         BlockPos blockpos1 = blockpos.m_142300_(p_74494_);
         BlockState blockstate = p_74511_.m_8055_(blockpos);
         BlockState blockstate1 = p_74511_.m_8055_(blockpos1);
         BlockState blockstate2 = blockstate.m_60728_(p_74494_, blockstate1, p_74511_, blockpos, blockpos1);
         if (blockstate != blockstate2) {
            p_74511_.m_7731_(blockpos, blockstate2, p_74512_ & -2);
         }

         BlockState blockstate3 = blockstate1.m_60728_(p_74494_.m_122424_(), blockstate2, p_74511_, blockpos1, blockpos);
         if (blockstate1 != blockstate3) {
            p_74511_.m_7731_(blockpos1, blockstate3, p_74512_ & -2);
         }

      });
   }

   @Deprecated //Use Forge version
   public static List<StructureTemplate.StructureBlockInfo> m_74517_(LevelAccessor p_74518_, BlockPos p_74519_, BlockPos p_74520_, StructurePlaceSettings p_74521_, List<StructureTemplate.StructureBlockInfo> p_74522_) {
      return processBlockInfos(p_74518_, p_74519_, p_74520_, p_74521_, p_74522_, null);
   }

   public static List<StructureTemplate.StructureBlockInfo> processBlockInfos(LevelAccessor p_74518_, BlockPos p_74519_, BlockPos p_74520_, StructurePlaceSettings p_74521_, List<StructureTemplate.StructureBlockInfo> p_74522_, @Nullable StructureTemplate template) {
      List<StructureTemplate.StructureBlockInfo> list = Lists.newArrayList();

      for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : p_74522_) {
         BlockPos blockpos = m_74563_(p_74521_, structuretemplate$structureblockinfo.f_74675_).m_141952_(p_74519_);
         StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo1 = new StructureTemplate.StructureBlockInfo(blockpos, structuretemplate$structureblockinfo.f_74676_, structuretemplate$structureblockinfo.f_74677_ != null ? structuretemplate$structureblockinfo.f_74677_.m_6426_() : null);

         for(Iterator<StructureProcessor> iterator = p_74521_.m_74411_().iterator(); structuretemplate$structureblockinfo1 != null && iterator.hasNext(); structuretemplate$structureblockinfo1 = iterator.next().process(p_74518_, p_74519_, p_74520_, structuretemplate$structureblockinfo, structuretemplate$structureblockinfo1, p_74521_, template)) {
         }

         if (structuretemplate$structureblockinfo1 != null) {
            list.add(structuretemplate$structureblockinfo1);
         }
      }

      return list;
   }

   public static List<StructureTemplate.StructureEntityInfo> processEntityInfos(@Nullable StructureTemplate template, LevelAccessor worldIn, BlockPos offsetPos, StructurePlaceSettings placementSettingsIn, List<StructureTemplate.StructureEntityInfo> blockInfos) {
      List<StructureTemplate.StructureEntityInfo> list = Lists.newArrayList();
      for(StructureTemplate.StructureEntityInfo entityInfo : blockInfos) {
         Vec3 pos = transformedVec3d(placementSettingsIn, entityInfo.f_74683_).m_82549_(Vec3.m_82528_(offsetPos));
         BlockPos blockpos = m_74563_(placementSettingsIn, entityInfo.f_74684_).m_141952_(offsetPos);
         StructureTemplate.StructureEntityInfo info = new StructureTemplate.StructureEntityInfo(pos, blockpos, entityInfo.f_74685_);
         for (StructureProcessor proc : placementSettingsIn.m_74411_()) {
            info = proc.processEntity(worldIn, offsetPos, entityInfo, info, placementSettingsIn, template);
            if (info == null)
               break;
         }
         if (info != null)
            list.add(info);
      }
      return list;
   }

   private void addEntitiesToWorld(ServerLevelAccessor p_74524_, BlockPos p_74525_, StructurePlaceSettings placementIn) {
      for(StructureTemplate.StructureEntityInfo structuretemplate$structureentityinfo : processEntityInfos(this, p_74524_, p_74525_, placementIn, this.f_74483_)) {
         BlockPos blockpos = m_74593_(structuretemplate$structureentityinfo.f_74684_, placementIn.m_74401_(), placementIn.m_74404_(), placementIn.m_74407_()).m_141952_(p_74525_);
         blockpos = structuretemplate$structureentityinfo.f_74684_; // FORGE: Position will have already been transformed by processEntityInfos
         if (placementIn.m_74409_() == null || placementIn.m_74409_().m_71051_(blockpos)) {
            CompoundTag compoundtag = structuretemplate$structureentityinfo.f_74685_.m_6426_();
            Vec3 vec31 = structuretemplate$structureentityinfo.f_74683_; // FORGE: Position will have already been transformed by processEntityInfos
            ListTag listtag = new ListTag();
            listtag.add(DoubleTag.m_128500_(vec31.f_82479_));
            listtag.add(DoubleTag.m_128500_(vec31.f_82480_));
            listtag.add(DoubleTag.m_128500_(vec31.f_82481_));
            compoundtag.m_128365_("Pos", listtag);
            compoundtag.m_128473_("UUID");
            m_74543_(p_74524_, compoundtag).ifPresent((p_74553_) -> {
               float f = p_74553_.m_6961_(placementIn.m_74401_());
               f = f + (p_74553_.m_146908_() - p_74553_.m_7890_(placementIn.m_74404_()));
               p_74553_.m_7678_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, f, p_74553_.m_146909_());
               if (placementIn.m_74414_() && p_74553_ instanceof Mob) {
                  ((Mob)p_74553_).m_6518_(p_74524_, p_74524_.m_6436_(new BlockPos(vec31)), MobSpawnType.STRUCTURE, (SpawnGroupData)null, compoundtag);
               }

               p_74524_.m_47205_(p_74553_);
            });
         }
      }

   }

   private static Optional<Entity> m_74543_(ServerLevelAccessor p_74544_, CompoundTag p_74545_) {
      try {
         return EntityType.m_20642_(p_74545_, p_74544_.m_6018_());
      } catch (Exception exception) {
         return Optional.empty();
      }
   }

   public Vec3i m_163808_(Rotation p_163809_) {
      switch(p_163809_) {
      case COUNTERCLOCKWISE_90:
      case CLOCKWISE_90:
         return new Vec3i(this.f_74484_.m_123343_(), this.f_74484_.m_123342_(), this.f_74484_.m_123341_());
      default:
         return this.f_74484_;
      }
   }

   public static BlockPos m_74593_(BlockPos p_74594_, Mirror p_74595_, Rotation p_74596_, BlockPos p_74597_) {
      int i = p_74594_.m_123341_();
      int j = p_74594_.m_123342_();
      int k = p_74594_.m_123343_();
      boolean flag = true;
      switch(p_74595_) {
      case LEFT_RIGHT:
         k = -k;
         break;
      case FRONT_BACK:
         i = -i;
         break;
      default:
         flag = false;
      }

      int l = p_74597_.m_123341_();
      int i1 = p_74597_.m_123343_();
      switch(p_74596_) {
      case COUNTERCLOCKWISE_90:
         return new BlockPos(l - i1 + k, j, l + i1 - i);
      case CLOCKWISE_90:
         return new BlockPos(l + i1 - k, j, i1 - l + i);
      case CLOCKWISE_180:
         return new BlockPos(l + l - i, j, i1 + i1 - k);
      default:
         return flag ? new BlockPos(i, j, k) : p_74594_;
      }
   }

   public static Vec3 m_74578_(Vec3 p_74579_, Mirror p_74580_, Rotation p_74581_, BlockPos p_74582_) {
      double d0 = p_74579_.f_82479_;
      double d1 = p_74579_.f_82480_;
      double d2 = p_74579_.f_82481_;
      boolean flag = true;
      switch(p_74580_) {
      case LEFT_RIGHT:
         d2 = 1.0D - d2;
         break;
      case FRONT_BACK:
         d0 = 1.0D - d0;
         break;
      default:
         flag = false;
      }

      int i = p_74582_.m_123341_();
      int j = p_74582_.m_123343_();
      switch(p_74581_) {
      case COUNTERCLOCKWISE_90:
         return new Vec3((double)(i - j) + d2, d1, (double)(i + j + 1) - d0);
      case CLOCKWISE_90:
         return new Vec3((double)(i + j + 1) - d2, d1, (double)(j - i) + d0);
      case CLOCKWISE_180:
         return new Vec3((double)(i + i + 1) - d0, d1, (double)(j + j + 1) - d2);
      default:
         return flag ? new Vec3(d0, d1, d2) : p_74579_;
      }
   }

   public BlockPos m_74583_(BlockPos p_74584_, Mirror p_74585_, Rotation p_74586_) {
      return m_74587_(p_74584_, p_74585_, p_74586_, this.m_163801_().m_123341_(), this.m_163801_().m_123343_());
   }

   public static BlockPos m_74587_(BlockPos p_74588_, Mirror p_74589_, Rotation p_74590_, int p_74591_, int p_74592_) {
      --p_74591_;
      --p_74592_;
      int i = p_74589_ == Mirror.FRONT_BACK ? p_74591_ : 0;
      int j = p_74589_ == Mirror.LEFT_RIGHT ? p_74592_ : 0;
      BlockPos blockpos = p_74588_;
      switch(p_74590_) {
      case COUNTERCLOCKWISE_90:
         blockpos = p_74588_.m_142082_(j, 0, p_74591_ - i);
         break;
      case CLOCKWISE_90:
         blockpos = p_74588_.m_142082_(p_74592_ - j, 0, i);
         break;
      case CLOCKWISE_180:
         blockpos = p_74588_.m_142082_(p_74591_ - i, 0, p_74592_ - j);
         break;
      case NONE:
         blockpos = p_74588_.m_142082_(i, 0, j);
      }

      return blockpos;
   }

   public BoundingBox m_74633_(StructurePlaceSettings p_74634_, BlockPos p_74635_) {
      return this.m_74598_(p_74635_, p_74634_.m_74404_(), p_74634_.m_74407_(), p_74634_.m_74401_());
   }

   public BoundingBox m_74598_(BlockPos p_74599_, Rotation p_74600_, BlockPos p_74601_, Mirror p_74602_) {
      return m_163810_(p_74599_, p_74600_, p_74601_, p_74602_, this.f_74484_);
   }

   @VisibleForTesting
   protected static BoundingBox m_163810_(BlockPos p_163811_, Rotation p_163812_, BlockPos p_163813_, Mirror p_163814_, Vec3i p_163815_) {
      Vec3i vec3i = p_163815_.m_142082_(-1, -1, -1);
      BlockPos blockpos = m_74593_(BlockPos.f_121853_, p_163814_, p_163812_, p_163813_);
      BlockPos blockpos1 = m_74593_(BlockPos.f_121853_.m_141952_(vec3i), p_163814_, p_163812_, p_163813_);
      return BoundingBox.m_162375_(blockpos, blockpos1).m_162373_(p_163811_);
   }

   public CompoundTag m_74618_(CompoundTag p_74619_) {
      if (this.f_74482_.isEmpty()) {
         p_74619_.m_128365_("blocks", new ListTag());
         p_74619_.m_128365_("palette", new ListTag());
      } else {
         List<StructureTemplate.SimplePalette> list = Lists.newArrayList();
         StructureTemplate.SimplePalette structuretemplate$simplepalette = new StructureTemplate.SimplePalette();
         list.add(structuretemplate$simplepalette);

         for(int i = 1; i < this.f_74482_.size(); ++i) {
            list.add(new StructureTemplate.SimplePalette());
         }

         ListTag listtag1 = new ListTag();
         List<StructureTemplate.StructureBlockInfo> list1 = this.f_74482_.get(0).m_74652_();

         for(int j = 0; j < list1.size(); ++j) {
            StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo = list1.get(j);
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128365_("pos", this.m_74625_(structuretemplate$structureblockinfo.f_74675_.m_123341_(), structuretemplate$structureblockinfo.f_74675_.m_123342_(), structuretemplate$structureblockinfo.f_74675_.m_123343_()));
            int k = structuretemplate$simplepalette.m_74669_(structuretemplate$structureblockinfo.f_74676_);
            compoundtag.m_128405_("state", k);
            if (structuretemplate$structureblockinfo.f_74677_ != null) {
               compoundtag.m_128365_("nbt", structuretemplate$structureblockinfo.f_74677_);
            }

            listtag1.add(compoundtag);

            for(int l = 1; l < this.f_74482_.size(); ++l) {
               StructureTemplate.SimplePalette structuretemplate$simplepalette1 = list.get(l);
               structuretemplate$simplepalette1.m_74671_((this.f_74482_.get(l).m_74652_().get(j)).f_74676_, k);
            }
         }

         p_74619_.m_128365_("blocks", listtag1);
         if (list.size() == 1) {
            ListTag listtag2 = new ListTag();

            for(BlockState blockstate : structuretemplate$simplepalette) {
               listtag2.add(NbtUtils.m_129202_(blockstate));
            }

            p_74619_.m_128365_("palette", listtag2);
         } else {
            ListTag listtag3 = new ListTag();

            for(StructureTemplate.SimplePalette structuretemplate$simplepalette2 : list) {
               ListTag listtag4 = new ListTag();

               for(BlockState blockstate1 : structuretemplate$simplepalette2) {
                  listtag4.add(NbtUtils.m_129202_(blockstate1));
               }

               listtag3.add(listtag4);
            }

            p_74619_.m_128365_("palettes", listtag3);
         }
      }

      ListTag listtag = new ListTag();

      for(StructureTemplate.StructureEntityInfo structuretemplate$structureentityinfo : this.f_74483_) {
         CompoundTag compoundtag1 = new CompoundTag();
         compoundtag1.m_128365_("pos", this.m_74623_(structuretemplate$structureentityinfo.f_74683_.f_82479_, structuretemplate$structureentityinfo.f_74683_.f_82480_, structuretemplate$structureentityinfo.f_74683_.f_82481_));
         compoundtag1.m_128365_("blockPos", this.m_74625_(structuretemplate$structureentityinfo.f_74684_.m_123341_(), structuretemplate$structureentityinfo.f_74684_.m_123342_(), structuretemplate$structureentityinfo.f_74684_.m_123343_()));
         if (structuretemplate$structureentityinfo.f_74685_ != null) {
            compoundtag1.m_128365_("nbt", structuretemplate$structureentityinfo.f_74685_);
         }

         listtag.add(compoundtag1);
      }

      p_74619_.m_128365_("entities", listtag);
      p_74619_.m_128365_("size", this.m_74625_(this.f_74484_.m_123341_(), this.f_74484_.m_123342_(), this.f_74484_.m_123343_()));
      p_74619_.m_128405_("DataVersion", SharedConstants.m_136187_().getWorldVersion());
      return p_74619_;
   }

   public void m_74638_(CompoundTag p_74639_) {
      this.f_74482_.clear();
      this.f_74483_.clear();
      ListTag listtag = p_74639_.m_128437_("size", 3);
      this.f_74484_ = new Vec3i(listtag.m_128763_(0), listtag.m_128763_(1), listtag.m_128763_(2));
      ListTag listtag1 = p_74639_.m_128437_("blocks", 10);
      if (p_74639_.m_128425_("palettes", 9)) {
         ListTag listtag2 = p_74639_.m_128437_("palettes", 9);

         for(int i = 0; i < listtag2.size(); ++i) {
            this.m_74620_(listtag2.m_128744_(i), listtag1);
         }
      } else {
         this.m_74620_(p_74639_.m_128437_("palette", 10), listtag1);
      }

      ListTag listtag5 = p_74639_.m_128437_("entities", 10);

      for(int j = 0; j < listtag5.size(); ++j) {
         CompoundTag compoundtag = listtag5.m_128728_(j);
         ListTag listtag3 = compoundtag.m_128437_("pos", 6);
         Vec3 vec3 = new Vec3(listtag3.m_128772_(0), listtag3.m_128772_(1), listtag3.m_128772_(2));
         ListTag listtag4 = compoundtag.m_128437_("blockPos", 3);
         BlockPos blockpos = new BlockPos(listtag4.m_128763_(0), listtag4.m_128763_(1), listtag4.m_128763_(2));
         if (compoundtag.m_128441_("nbt")) {
            CompoundTag compoundtag1 = compoundtag.m_128469_("nbt");
            this.f_74483_.add(new StructureTemplate.StructureEntityInfo(vec3, blockpos, compoundtag1));
         }
      }

   }

   private void m_74620_(ListTag p_74621_, ListTag p_74622_) {
      StructureTemplate.SimplePalette structuretemplate$simplepalette = new StructureTemplate.SimplePalette();

      for(int i = 0; i < p_74621_.size(); ++i) {
         structuretemplate$simplepalette.m_74671_(NbtUtils.m_129241_(p_74621_.m_128728_(i)), i);
      }

      List<StructureTemplate.StructureBlockInfo> list2 = Lists.newArrayList();
      List<StructureTemplate.StructureBlockInfo> list = Lists.newArrayList();
      List<StructureTemplate.StructureBlockInfo> list1 = Lists.newArrayList();

      for(int j = 0; j < p_74622_.size(); ++j) {
         CompoundTag compoundtag = p_74622_.m_128728_(j);
         ListTag listtag = compoundtag.m_128437_("pos", 3);
         BlockPos blockpos = new BlockPos(listtag.m_128763_(0), listtag.m_128763_(1), listtag.m_128763_(2));
         BlockState blockstate = structuretemplate$simplepalette.m_74667_(compoundtag.m_128451_("state"));
         CompoundTag compoundtag1;
         if (compoundtag.m_128441_("nbt")) {
            compoundtag1 = compoundtag.m_128469_("nbt");
         } else {
            compoundtag1 = null;
         }

         StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo = new StructureTemplate.StructureBlockInfo(blockpos, blockstate, compoundtag1);
         m_74573_(structuretemplate$structureblockinfo, list2, list, list1);
      }

      List<StructureTemplate.StructureBlockInfo> list3 = m_74614_(list2, list, list1);
      this.f_74482_.add(new StructureTemplate.Palette(list3));
   }

   private ListTag m_74625_(int... p_74626_) {
      ListTag listtag = new ListTag();

      for(int i : p_74626_) {
         listtag.add(IntTag.m_128679_(i));
      }

      return listtag;
   }

   private ListTag m_74623_(double... p_74624_) {
      ListTag listtag = new ListTag();

      for(double d0 : p_74624_) {
         listtag.add(DoubleTag.m_128500_(d0));
      }

      return listtag;
   }

   public static final class Palette {
      private final List<StructureTemplate.StructureBlockInfo> f_74645_;
      private final Map<Block, List<StructureTemplate.StructureBlockInfo>> f_74646_ = Maps.newHashMap();

      Palette(List<StructureTemplate.StructureBlockInfo> p_74648_) {
         this.f_74645_ = p_74648_;
      }

      public List<StructureTemplate.StructureBlockInfo> m_74652_() {
         return this.f_74645_;
      }

      public List<StructureTemplate.StructureBlockInfo> m_74653_(Block p_74654_) {
         return this.f_74646_.computeIfAbsent(p_74654_, (p_74659_) -> {
            return this.f_74645_.stream().filter((p_163818_) -> {
               return p_163818_.f_74676_.m_60713_(p_74659_);
            }).collect(Collectors.toList());
         });
      }
   }

   static class SimplePalette implements Iterable<BlockState> {
      public static final BlockState f_74660_ = Blocks.f_50016_.m_49966_();
      private final IdMapper<BlockState> f_74661_ = new IdMapper<>(16);
      private int f_74662_;

      public int m_74669_(BlockState p_74670_) {
         int i = this.f_74661_.m_7447_(p_74670_);
         if (i == -1) {
            i = this.f_74662_++;
            this.f_74661_.m_122664_(p_74670_, i);
         }

         return i;
      }

      @Nullable
      public BlockState m_74667_(int p_74668_) {
         BlockState blockstate = this.f_74661_.m_7942_(p_74668_);
         return blockstate == null ? f_74660_ : blockstate;
      }

      public Iterator<BlockState> iterator() {
         return this.f_74661_.iterator();
      }

      public void m_74671_(BlockState p_74672_, int p_74673_) {
         this.f_74661_.m_122664_(p_74672_, p_74673_);
      }
   }

   public static class StructureBlockInfo {
      public final BlockPos f_74675_;
      public final BlockState f_74676_;
      public final CompoundTag f_74677_;

      public StructureBlockInfo(BlockPos p_74679_, BlockState p_74680_, @Nullable CompoundTag p_74681_) {
         this.f_74675_ = p_74679_;
         this.f_74676_ = p_74680_;
         this.f_74677_ = p_74681_;
      }

      public String toString() {
         return String.format("<StructureBlockInfo | %s | %s | %s>", this.f_74675_, this.f_74676_, this.f_74677_);
      }
   }

   public static class StructureEntityInfo {
      public final Vec3 f_74683_;
      public final BlockPos f_74684_;
      public final CompoundTag f_74685_;

      public StructureEntityInfo(Vec3 p_74687_, BlockPos p_74688_, CompoundTag p_74689_) {
         this.f_74683_ = p_74687_;
         this.f_74684_ = p_74688_;
         this.f_74685_ = p_74689_;
      }
   }
}
