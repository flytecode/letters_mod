package net.minecraft.gametest.framework;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.exception.ExceptionUtils;

class ReportGameListener implements GameTestListener {
   private final GameTestInfo f_177688_;
   private final GameTestTicker f_177689_;
   private final BlockPos f_177690_;
   int f_177686_;
   int f_177687_;

   public ReportGameListener(GameTestInfo p_177692_, GameTestTicker p_177693_, BlockPos p_177694_) {
      this.f_177688_ = p_177692_;
      this.f_177689_ = p_177693_;
      this.f_177690_ = p_177694_;
      this.f_177686_ = 0;
      this.f_177687_ = 0;
   }

   public void m_8073_(GameTestInfo p_177718_) {
      m_177719_(this.f_177688_, Blocks.f_50208_);
      ++this.f_177686_;
   }

   public void m_142378_(GameTestInfo p_177729_) {
      ++this.f_177687_;
      if (!p_177729_.m_177491_()) {
         m_177722_(p_177729_, p_177729_.m_127633_() + " passed!");
      } else {
         if (this.f_177687_ >= p_177729_.m_177493_()) {
            m_177722_(p_177729_, p_177729_ + " passed " + this.f_177687_ + " times of " + this.f_177686_ + " attempts.");
         } else {
            m_177700_(this.f_177688_.m_127637_(), ChatFormatting.GREEN, "Flaky test " + this.f_177688_ + " succeeded, attempt: " + this.f_177686_ + " successes: " + this.f_177687_);
            this.m_177695_();
         }

      }
   }

   public void m_8066_(GameTestInfo p_177737_) {
      if (!p_177737_.m_177491_()) {
         m_177725_(p_177737_, p_177737_.m_127642_());
      } else {
         TestFunction testfunction = this.f_177688_.m_127648_();
         String s = "Flaky test " + this.f_177688_ + " failed, attempt: " + this.f_177686_ + "/" + testfunction.m_177829_();
         if (testfunction.m_177830_() > 1) {
            s = s + ", successes: " + this.f_177687_ + " (" + testfunction.m_177830_() + " required)";
         }

         m_177700_(this.f_177688_.m_127637_(), ChatFormatting.YELLOW, s);
         if (p_177737_.m_177492_() - this.f_177686_ + this.f_177687_ >= p_177737_.m_177493_()) {
            this.m_177695_();
         } else {
            m_177725_(p_177737_, new ExhaustedAttemptsException(this.f_177686_, this.f_177687_, p_177737_));
         }

      }
   }

   public static void m_177722_(GameTestInfo p_177723_, String p_177724_) {
      m_177719_(p_177723_, Blocks.f_50205_);
      m_177730_(p_177723_, p_177724_);
   }

   private static void m_177730_(GameTestInfo p_177731_, String p_177732_) {
      m_177700_(p_177731_.m_127637_(), ChatFormatting.GREEN, p_177732_);
      GlobalTestReporter.m_177657_(p_177731_);
   }

   protected static void m_177725_(GameTestInfo p_177726_, Throwable p_177727_) {
      m_177719_(p_177726_, p_177726_.m_127643_() ? Blocks.f_50214_ : Blocks.f_50148_);
      m_177738_(p_177726_, Util.m_137575_(p_177727_));
      m_177733_(p_177726_, p_177727_);
   }

   protected static void m_177733_(GameTestInfo p_177734_, Throwable p_177735_) {
      String s = p_177735_.getMessage() + (p_177735_.getCause() == null ? "" : " cause: " + Util.m_137575_(p_177735_.getCause()));
      String s1 = (p_177734_.m_127643_() ? "" : "(optional) ") + p_177734_.m_127633_() + " failed! " + s;
      m_177700_(p_177734_.m_127637_(), p_177734_.m_127643_() ? ChatFormatting.RED : ChatFormatting.YELLOW, s1);
      Throwable throwable = MoreObjects.firstNonNull(ExceptionUtils.getRootCause(p_177735_), p_177735_);
      if (throwable instanceof GameTestAssertPosException) {
         GameTestAssertPosException gametestassertposexception = (GameTestAssertPosException)throwable;
         m_177696_(p_177734_.m_127637_(), gametestassertposexception.m_127497_(), gametestassertposexception.m_127496_());
      }

      GlobalTestReporter.m_177653_(p_177734_);
   }

   private void m_177695_() {
      this.f_177688_.m_177487_();
      GameTestInfo gametestinfo = new GameTestInfo(this.f_177688_.m_127648_(), this.f_177688_.m_127646_(), this.f_177688_.m_127637_());
      gametestinfo.m_127616_();
      this.f_177689_.m_127788_(gametestinfo);
      gametestinfo.m_127624_(this);
      gametestinfo.m_127619_(this.f_177690_, 2);
   }

   protected static void m_177719_(GameTestInfo p_177720_, Block p_177721_) {
      ServerLevel serverlevel = p_177720_.m_127637_();
      BlockPos blockpos = p_177720_.m_127636_();
      BlockPos blockpos1 = new BlockPos(-1, -1, -1);
      BlockPos blockpos2 = StructureTemplate.m_74593_(blockpos.m_141952_(blockpos1), Mirror.NONE, p_177720_.m_127646_(), blockpos);
      serverlevel.m_46597_(blockpos2, Blocks.f_50273_.m_49966_().m_60717_(p_177720_.m_127646_()));
      BlockPos blockpos3 = blockpos2.m_142082_(0, 1, 0);
      serverlevel.m_46597_(blockpos3, p_177721_.m_49966_());

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            BlockPos blockpos4 = blockpos2.m_142082_(i, -1, j);
            serverlevel.m_46597_(blockpos4, Blocks.f_50075_.m_49966_());
         }
      }

   }

   private static void m_177738_(GameTestInfo p_177739_, String p_177740_) {
      ServerLevel serverlevel = p_177739_.m_127637_();
      BlockPos blockpos = p_177739_.m_127636_();
      BlockPos blockpos1 = new BlockPos(-1, 1, -1);
      BlockPos blockpos2 = StructureTemplate.m_74593_(blockpos.m_141952_(blockpos1), Mirror.NONE, p_177739_.m_127646_(), blockpos);
      serverlevel.m_46597_(blockpos2, Blocks.f_50624_.m_49966_().m_60717_(p_177739_.m_127646_()));
      BlockState blockstate = serverlevel.m_8055_(blockpos2);
      ItemStack itemstack = m_177710_(p_177739_.m_127633_(), p_177739_.m_127643_(), p_177740_);
      LecternBlock.m_153566_((Player)null, serverlevel, blockpos2, blockstate, itemstack);
   }

   private static ItemStack m_177710_(String p_177711_, boolean p_177712_, String p_177713_) {
      ItemStack itemstack = new ItemStack(Items.f_42614_);
      ListTag listtag = new ListTag();
      StringBuffer stringbuffer = new StringBuffer();
      Arrays.stream(p_177711_.split("\\.")).forEach((p_177716_) -> {
         stringbuffer.append(p_177716_).append('\n');
      });
      if (!p_177712_) {
         stringbuffer.append("(optional)\n");
      }

      stringbuffer.append("-------------------\n");
      listtag.add(StringTag.m_129297_(stringbuffer + p_177713_));
      itemstack.m_41700_("pages", listtag);
      return itemstack;
   }

   protected static void m_177700_(ServerLevel p_177701_, ChatFormatting p_177702_, String p_177703_) {
      p_177701_.m_8795_((p_177705_) -> {
         return true;
      }).forEach((p_177709_) -> {
         p_177709_.m_6352_((new TextComponent(p_177703_)).m_130940_(p_177702_), Util.f_137441_);
      });
   }

   private static void m_177696_(ServerLevel p_177697_, BlockPos p_177698_, String p_177699_) {
      DebugPackets.m_133682_(p_177697_, p_177698_, p_177699_, -2130771968, Integer.MAX_VALUE);
   }
}