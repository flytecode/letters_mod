package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class CloneCommands {
   private static final int f_180030_ = 32768;
   private static final SimpleCommandExceptionType f_136723_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.clone.overlap"));
   private static final Dynamic2CommandExceptionType f_136724_ = new Dynamic2CommandExceptionType((p_136743_, p_136744_) -> {
      return new TranslatableComponent("commands.clone.toobig", p_136743_, p_136744_);
   });
   private static final SimpleCommandExceptionType f_136725_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.clone.failed"));
   public static final Predicate<BlockInWorld> f_136722_ = (p_136762_) -> {
      return !p_136762_.m_61168_().m_60795_();
   };

   public static void m_136729_(CommandDispatcher<CommandSourceStack> p_136730_) {
      p_136730_.register(Commands.m_82127_("clone").requires((p_136734_) -> {
         return p_136734_.m_6761_(2);
      }).then(Commands.m_82129_("begin", BlockPosArgument.m_118239_()).then(Commands.m_82129_("end", BlockPosArgument.m_118239_()).then(Commands.m_82129_("destination", BlockPosArgument.m_118239_()).executes((p_136778_) -> {
         return m_136735_(p_136778_.getSource(), BlockPosArgument.m_118242_(p_136778_, "begin"), BlockPosArgument.m_118242_(p_136778_, "end"), BlockPosArgument.m_118242_(p_136778_, "destination"), (p_180041_) -> {
            return true;
         }, CloneCommands.Mode.NORMAL);
      }).then(Commands.m_82127_("replace").executes((p_136776_) -> {
         return m_136735_(p_136776_.getSource(), BlockPosArgument.m_118242_(p_136776_, "begin"), BlockPosArgument.m_118242_(p_136776_, "end"), BlockPosArgument.m_118242_(p_136776_, "destination"), (p_180039_) -> {
            return true;
         }, CloneCommands.Mode.NORMAL);
      }).then(Commands.m_82127_("force").executes((p_136774_) -> {
         return m_136735_(p_136774_.getSource(), BlockPosArgument.m_118242_(p_136774_, "begin"), BlockPosArgument.m_118242_(p_136774_, "end"), BlockPosArgument.m_118242_(p_136774_, "destination"), (p_180037_) -> {
            return true;
         }, CloneCommands.Mode.FORCE);
      })).then(Commands.m_82127_("move").executes((p_136772_) -> {
         return m_136735_(p_136772_.getSource(), BlockPosArgument.m_118242_(p_136772_, "begin"), BlockPosArgument.m_118242_(p_136772_, "end"), BlockPosArgument.m_118242_(p_136772_, "destination"), (p_180035_) -> {
            return true;
         }, CloneCommands.Mode.MOVE);
      })).then(Commands.m_82127_("normal").executes((p_136770_) -> {
         return m_136735_(p_136770_.getSource(), BlockPosArgument.m_118242_(p_136770_, "begin"), BlockPosArgument.m_118242_(p_136770_, "end"), BlockPosArgument.m_118242_(p_136770_, "destination"), (p_180033_) -> {
            return true;
         }, CloneCommands.Mode.NORMAL);
      }))).then(Commands.m_82127_("masked").executes((p_136768_) -> {
         return m_136735_(p_136768_.getSource(), BlockPosArgument.m_118242_(p_136768_, "begin"), BlockPosArgument.m_118242_(p_136768_, "end"), BlockPosArgument.m_118242_(p_136768_, "destination"), f_136722_, CloneCommands.Mode.NORMAL);
      }).then(Commands.m_82127_("force").executes((p_136766_) -> {
         return m_136735_(p_136766_.getSource(), BlockPosArgument.m_118242_(p_136766_, "begin"), BlockPosArgument.m_118242_(p_136766_, "end"), BlockPosArgument.m_118242_(p_136766_, "destination"), f_136722_, CloneCommands.Mode.FORCE);
      })).then(Commands.m_82127_("move").executes((p_136764_) -> {
         return m_136735_(p_136764_.getSource(), BlockPosArgument.m_118242_(p_136764_, "begin"), BlockPosArgument.m_118242_(p_136764_, "end"), BlockPosArgument.m_118242_(p_136764_, "destination"), f_136722_, CloneCommands.Mode.MOVE);
      })).then(Commands.m_82127_("normal").executes((p_136760_) -> {
         return m_136735_(p_136760_.getSource(), BlockPosArgument.m_118242_(p_136760_, "begin"), BlockPosArgument.m_118242_(p_136760_, "end"), BlockPosArgument.m_118242_(p_136760_, "destination"), f_136722_, CloneCommands.Mode.NORMAL);
      }))).then(Commands.m_82127_("filtered").then(Commands.m_82129_("filter", BlockPredicateArgument.m_115570_()).executes((p_136756_) -> {
         return m_136735_(p_136756_.getSource(), BlockPosArgument.m_118242_(p_136756_, "begin"), BlockPosArgument.m_118242_(p_136756_, "end"), BlockPosArgument.m_118242_(p_136756_, "destination"), BlockPredicateArgument.m_115573_(p_136756_, "filter"), CloneCommands.Mode.NORMAL);
      }).then(Commands.m_82127_("force").executes((p_136752_) -> {
         return m_136735_(p_136752_.getSource(), BlockPosArgument.m_118242_(p_136752_, "begin"), BlockPosArgument.m_118242_(p_136752_, "end"), BlockPosArgument.m_118242_(p_136752_, "destination"), BlockPredicateArgument.m_115573_(p_136752_, "filter"), CloneCommands.Mode.FORCE);
      })).then(Commands.m_82127_("move").executes((p_136748_) -> {
         return m_136735_(p_136748_.getSource(), BlockPosArgument.m_118242_(p_136748_, "begin"), BlockPosArgument.m_118242_(p_136748_, "end"), BlockPosArgument.m_118242_(p_136748_, "destination"), BlockPredicateArgument.m_115573_(p_136748_, "filter"), CloneCommands.Mode.MOVE);
      })).then(Commands.m_82127_("normal").executes((p_136732_) -> {
         return m_136735_(p_136732_.getSource(), BlockPosArgument.m_118242_(p_136732_, "begin"), BlockPosArgument.m_118242_(p_136732_, "end"), BlockPosArgument.m_118242_(p_136732_, "destination"), BlockPredicateArgument.m_115573_(p_136732_, "filter"), CloneCommands.Mode.NORMAL);
      }))))))));
   }

   private static int m_136735_(CommandSourceStack p_136736_, BlockPos p_136737_, BlockPos p_136738_, BlockPos p_136739_, Predicate<BlockInWorld> p_136740_, CloneCommands.Mode p_136741_) throws CommandSyntaxException {
      BoundingBox boundingbox = BoundingBox.m_162375_(p_136737_, p_136738_);
      BlockPos blockpos = p_136739_.m_141952_(boundingbox.m_71053_());
      BoundingBox boundingbox1 = BoundingBox.m_162375_(p_136739_, blockpos);
      if (!p_136741_.m_136796_() && boundingbox1.m_71049_(boundingbox)) {
         throw f_136723_.create();
      } else {
         int i = boundingbox.m_71056_() * boundingbox.m_71057_() * boundingbox.m_71058_();
         if (i > 32768) {
            throw f_136724_.create(32768, i);
         } else {
            ServerLevel serverlevel = p_136736_.m_81372_();
            if (serverlevel.m_46832_(p_136737_, p_136738_) && serverlevel.m_46832_(p_136739_, blockpos)) {
               List<CloneCommands.CloneBlockInfo> list = Lists.newArrayList();
               List<CloneCommands.CloneBlockInfo> list1 = Lists.newArrayList();
               List<CloneCommands.CloneBlockInfo> list2 = Lists.newArrayList();
               Deque<BlockPos> deque = Lists.newLinkedList();
               BlockPos blockpos1 = new BlockPos(boundingbox1.m_162395_() - boundingbox.m_162395_(), boundingbox1.m_162396_() - boundingbox.m_162396_(), boundingbox1.m_162398_() - boundingbox.m_162398_());

               for(int j = boundingbox.m_162398_(); j <= boundingbox.m_162401_(); ++j) {
                  for(int k = boundingbox.m_162396_(); k <= boundingbox.m_162400_(); ++k) {
                     for(int l = boundingbox.m_162395_(); l <= boundingbox.m_162399_(); ++l) {
                        BlockPos blockpos2 = new BlockPos(l, k, j);
                        BlockPos blockpos3 = blockpos2.m_141952_(blockpos1);
                        BlockInWorld blockinworld = new BlockInWorld(serverlevel, blockpos2, false);
                        BlockState blockstate = blockinworld.m_61168_();
                        if (p_136740_.test(blockinworld)) {
                           BlockEntity blockentity = serverlevel.m_7702_(blockpos2);
                           if (blockentity != null) {
                              CompoundTag compoundtag = blockentity.m_6945_(new CompoundTag());
                              list1.add(new CloneCommands.CloneBlockInfo(blockpos3, blockstate, compoundtag));
                              deque.addLast(blockpos2);
                           } else if (!blockstate.m_60804_(serverlevel, blockpos2) && !blockstate.m_60838_(serverlevel, blockpos2)) {
                              list2.add(new CloneCommands.CloneBlockInfo(blockpos3, blockstate, (CompoundTag)null));
                              deque.addFirst(blockpos2);
                           } else {
                              list.add(new CloneCommands.CloneBlockInfo(blockpos3, blockstate, (CompoundTag)null));
                              deque.addLast(blockpos2);
                           }
                        }
                     }
                  }
               }

               if (p_136741_ == CloneCommands.Mode.MOVE) {
                  for(BlockPos blockpos4 : deque) {
                     BlockEntity blockentity1 = serverlevel.m_7702_(blockpos4);
                     Clearable.m_18908_(blockentity1);
                     serverlevel.m_7731_(blockpos4, Blocks.f_50375_.m_49966_(), 2);
                  }

                  for(BlockPos blockpos5 : deque) {
                     serverlevel.m_7731_(blockpos5, Blocks.f_50016_.m_49966_(), 3);
                  }
               }

               List<CloneCommands.CloneBlockInfo> list3 = Lists.newArrayList();
               list3.addAll(list);
               list3.addAll(list1);
               list3.addAll(list2);
               List<CloneCommands.CloneBlockInfo> list4 = Lists.reverse(list3);

               for(CloneCommands.CloneBlockInfo clonecommands$cloneblockinfo : list4) {
                  BlockEntity blockentity2 = serverlevel.m_7702_(clonecommands$cloneblockinfo.f_136779_);
                  Clearable.m_18908_(blockentity2);
                  serverlevel.m_7731_(clonecommands$cloneblockinfo.f_136779_, Blocks.f_50375_.m_49966_(), 2);
               }

               int i1 = 0;

               for(CloneCommands.CloneBlockInfo clonecommands$cloneblockinfo1 : list3) {
                  if (serverlevel.m_7731_(clonecommands$cloneblockinfo1.f_136779_, clonecommands$cloneblockinfo1.f_136780_, 2)) {
                     ++i1;
                  }
               }

               for(CloneCommands.CloneBlockInfo clonecommands$cloneblockinfo2 : list1) {
                  BlockEntity blockentity3 = serverlevel.m_7702_(clonecommands$cloneblockinfo2.f_136779_);
                  if (clonecommands$cloneblockinfo2.f_136781_ != null && blockentity3 != null) {
                     clonecommands$cloneblockinfo2.f_136781_.m_128405_("x", clonecommands$cloneblockinfo2.f_136779_.m_123341_());
                     clonecommands$cloneblockinfo2.f_136781_.m_128405_("y", clonecommands$cloneblockinfo2.f_136779_.m_123342_());
                     clonecommands$cloneblockinfo2.f_136781_.m_128405_("z", clonecommands$cloneblockinfo2.f_136779_.m_123343_());
                     blockentity3.m_142466_(clonecommands$cloneblockinfo2.f_136781_);
                     blockentity3.m_6596_();
                  }

                  serverlevel.m_7731_(clonecommands$cloneblockinfo2.f_136779_, clonecommands$cloneblockinfo2.f_136780_, 2);
               }

               for(CloneCommands.CloneBlockInfo clonecommands$cloneblockinfo3 : list4) {
                  serverlevel.m_6289_(clonecommands$cloneblockinfo3.f_136779_, clonecommands$cloneblockinfo3.f_136780_.m_60734_());
               }

               serverlevel.m_6219_().m_47229_(boundingbox, blockpos1);
               if (i1 == 0) {
                  throw f_136725_.create();
               } else {
                  p_136736_.m_81354_(new TranslatableComponent("commands.clone.success", i1), true);
                  return i1;
               }
            } else {
               throw BlockPosArgument.f_118234_.create();
            }
         }
      }
   }

   static class CloneBlockInfo {
      public final BlockPos f_136779_;
      public final BlockState f_136780_;
      @Nullable
      public final CompoundTag f_136781_;

      public CloneBlockInfo(BlockPos p_136783_, BlockState p_136784_, @Nullable CompoundTag p_136785_) {
         this.f_136779_ = p_136783_;
         this.f_136780_ = p_136784_;
         this.f_136781_ = p_136785_;
      }
   }

   static enum Mode {
      FORCE(true),
      MOVE(true),
      NORMAL(false);

      private final boolean f_136789_;

      private Mode(boolean p_136795_) {
         this.f_136789_ = p_136795_;
      }

      public boolean m_136796_() {
         return this.f_136789_;
      }
   }
}