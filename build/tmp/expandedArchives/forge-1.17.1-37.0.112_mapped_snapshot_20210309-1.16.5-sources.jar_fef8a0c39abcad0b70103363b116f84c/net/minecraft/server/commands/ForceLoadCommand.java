package net.minecraft.server.commands;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class ForceLoadCommand {
   private static final int f_180227_ = 256;
   private static final Dynamic2CommandExceptionType f_137668_ = new Dynamic2CommandExceptionType((p_137698_, p_137699_) -> {
      return new TranslatableComponent("commands.forceload.toobig", p_137698_, p_137699_);
   });
   private static final Dynamic2CommandExceptionType f_137669_ = new Dynamic2CommandExceptionType((p_137691_, p_137692_) -> {
      return new TranslatableComponent("commands.forceload.query.failure", p_137691_, p_137692_);
   });
   private static final SimpleCommandExceptionType f_137670_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.forceload.added.failure"));
   private static final SimpleCommandExceptionType f_137671_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.forceload.removed.failure"));

   public static void m_137676_(CommandDispatcher<CommandSourceStack> p_137677_) {
      p_137677_.register(Commands.m_82127_("forceload").requires((p_137703_) -> {
         return p_137703_.m_6761_(2);
      }).then(Commands.m_82127_("add").then(Commands.m_82129_("from", ColumnPosArgument.m_118989_()).executes((p_137711_) -> {
         return m_137685_(p_137711_.getSource(), ColumnPosArgument.m_118992_(p_137711_, "from"), ColumnPosArgument.m_118992_(p_137711_, "from"), true);
      }).then(Commands.m_82129_("to", ColumnPosArgument.m_118989_()).executes((p_137709_) -> {
         return m_137685_(p_137709_.getSource(), ColumnPosArgument.m_118992_(p_137709_, "from"), ColumnPosArgument.m_118992_(p_137709_, "to"), true);
      })))).then(Commands.m_82127_("remove").then(Commands.m_82129_("from", ColumnPosArgument.m_118989_()).executes((p_137707_) -> {
         return m_137685_(p_137707_.getSource(), ColumnPosArgument.m_118992_(p_137707_, "from"), ColumnPosArgument.m_118992_(p_137707_, "from"), false);
      }).then(Commands.m_82129_("to", ColumnPosArgument.m_118989_()).executes((p_137705_) -> {
         return m_137685_(p_137705_.getSource(), ColumnPosArgument.m_118992_(p_137705_, "from"), ColumnPosArgument.m_118992_(p_137705_, "to"), false);
      }))).then(Commands.m_82127_("all").executes((p_137701_) -> {
         return m_137695_(p_137701_.getSource());
      }))).then(Commands.m_82127_("query").executes((p_137694_) -> {
         return m_137680_(p_137694_.getSource());
      }).then(Commands.m_82129_("pos", ColumnPosArgument.m_118989_()).executes((p_137679_) -> {
         return m_137682_(p_137679_.getSource(), ColumnPosArgument.m_118992_(p_137679_, "pos"));
      }))));
   }

   private static int m_137682_(CommandSourceStack p_137683_, ColumnPos p_137684_) throws CommandSyntaxException {
      ChunkPos chunkpos = new ChunkPos(SectionPos.m_123171_(p_137684_.f_140723_), SectionPos.m_123171_(p_137684_.f_140724_));
      ServerLevel serverlevel = p_137683_.m_81372_();
      ResourceKey<Level> resourcekey = serverlevel.m_46472_();
      boolean flag = serverlevel.m_8902_().contains(chunkpos.m_45588_());
      if (flag) {
         p_137683_.m_81354_(new TranslatableComponent("commands.forceload.query.success", chunkpos, resourcekey.m_135782_()), false);
         return 1;
      } else {
         throw f_137669_.create(chunkpos, resourcekey.m_135782_());
      }
   }

   private static int m_137680_(CommandSourceStack p_137681_) {
      ServerLevel serverlevel = p_137681_.m_81372_();
      ResourceKey<Level> resourcekey = serverlevel.m_46472_();
      LongSet longset = serverlevel.m_8902_();
      int i = longset.size();
      if (i > 0) {
         String s = Joiner.on(", ").join(longset.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
         if (i == 1) {
            p_137681_.m_81354_(new TranslatableComponent("commands.forceload.list.single", resourcekey.m_135782_(), s), false);
         } else {
            p_137681_.m_81354_(new TranslatableComponent("commands.forceload.list.multiple", i, resourcekey.m_135782_(), s), false);
         }
      } else {
         p_137681_.m_81352_(new TranslatableComponent("commands.forceload.added.none", resourcekey.m_135782_()));
      }

      return i;
   }

   private static int m_137695_(CommandSourceStack p_137696_) {
      ServerLevel serverlevel = p_137696_.m_81372_();
      ResourceKey<Level> resourcekey = serverlevel.m_46472_();
      LongSet longset = serverlevel.m_8902_();
      longset.forEach((long p_137675_) -> {
         serverlevel.m_8602_(ChunkPos.m_45592_(p_137675_), ChunkPos.m_45602_(p_137675_), false);
      });
      p_137696_.m_81354_(new TranslatableComponent("commands.forceload.removed.all", resourcekey.m_135782_()), true);
      return 0;
   }

   private static int m_137685_(CommandSourceStack p_137686_, ColumnPos p_137687_, ColumnPos p_137688_, boolean p_137689_) throws CommandSyntaxException {
      int i = Math.min(p_137687_.f_140723_, p_137688_.f_140723_);
      int j = Math.min(p_137687_.f_140724_, p_137688_.f_140724_);
      int k = Math.max(p_137687_.f_140723_, p_137688_.f_140723_);
      int l = Math.max(p_137687_.f_140724_, p_137688_.f_140724_);
      if (i >= -30000000 && j >= -30000000 && k < 30000000 && l < 30000000) {
         int i1 = SectionPos.m_123171_(i);
         int j1 = SectionPos.m_123171_(j);
         int k1 = SectionPos.m_123171_(k);
         int l1 = SectionPos.m_123171_(l);
         long i2 = ((long)(k1 - i1) + 1L) * ((long)(l1 - j1) + 1L);
         if (i2 > 256L) {
            throw f_137668_.create(256, i2);
         } else {
            ServerLevel serverlevel = p_137686_.m_81372_();
            ResourceKey<Level> resourcekey = serverlevel.m_46472_();
            ChunkPos chunkpos = null;
            int j2 = 0;

            for(int k2 = i1; k2 <= k1; ++k2) {
               for(int l2 = j1; l2 <= l1; ++l2) {
                  boolean flag = serverlevel.m_8602_(k2, l2, p_137689_);
                  if (flag) {
                     ++j2;
                     if (chunkpos == null) {
                        chunkpos = new ChunkPos(k2, l2);
                     }
                  }
               }
            }

            if (j2 == 0) {
               throw (p_137689_ ? f_137670_ : f_137671_).create();
            } else {
               if (j2 == 1) {
                  p_137686_.m_81354_(new TranslatableComponent("commands.forceload." + (p_137689_ ? "added" : "removed") + ".single", chunkpos, resourcekey.m_135782_()), true);
               } else {
                  ChunkPos chunkpos1 = new ChunkPos(i1, j1);
                  ChunkPos chunkpos2 = new ChunkPos(k1, l1);
                  p_137686_.m_81354_(new TranslatableComponent("commands.forceload." + (p_137689_ ? "added" : "removed") + ".multiple", j2, resourcekey.m_135782_(), chunkpos1, chunkpos2), true);
               }

               return j2;
            }
         }
      } else {
         throw BlockPosArgument.f_118235_.create();
      }
   }
}