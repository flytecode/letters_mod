package net.minecraft.gametest.framework;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.io.IOUtils;

public class TestCommand {
   private static final int f_177786_ = 200;
   private static final int f_177787_ = 1024;
   private static final int f_177788_ = 15;
   private static final int f_177789_ = 200;
   private static final int f_177790_ = 3;
   private static final int f_177791_ = 10000;
   private static final int f_177792_ = 5;
   private static final int f_177793_ = 5;
   private static final int f_177794_ = 5;

   public static void m_127946_(CommandDispatcher<CommandSourceStack> p_127947_) {
      p_127947_.register(Commands.m_82127_("test").then(Commands.m_82127_("runthis").executes((p_128057_) -> {
         return m_127950_(p_128057_.getSource());
      })).then(Commands.m_82127_("runthese").executes((p_128055_) -> {
         return m_128001_(p_128055_.getSource());
      })).then(Commands.m_82127_("runfailed").executes((p_128053_) -> {
         return m_127982_(p_128053_.getSource(), false, 0, 8);
      }).then(Commands.m_82129_("onlyRequiredTests", BoolArgumentType.bool()).executes((p_128051_) -> {
         return m_127982_(p_128051_.getSource(), BoolArgumentType.getBool(p_128051_, "onlyRequiredTests"), 0, 8);
      }).then(Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes((p_128049_) -> {
         return m_127982_(p_128049_.getSource(), BoolArgumentType.getBool(p_128049_, "onlyRequiredTests"), IntegerArgumentType.getInteger(p_128049_, "rotationSteps"), 8);
      }).then(Commands.m_82129_("testsPerRow", IntegerArgumentType.integer()).executes((p_128047_) -> {
         return m_127982_(p_128047_.getSource(), BoolArgumentType.getBool(p_128047_, "onlyRequiredTests"), IntegerArgumentType.getInteger(p_128047_, "rotationSteps"), IntegerArgumentType.getInteger(p_128047_, "testsPerRow"));
      }))))).then(Commands.m_82127_("run").then(Commands.m_82129_("testName", TestFunctionArgument.m_128088_()).executes((p_128045_) -> {
         return m_127978_(p_128045_.getSource(), TestFunctionArgument.m_128091_(p_128045_, "testName"), 0);
      }).then(Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes((p_128043_) -> {
         return m_127978_(p_128043_.getSource(), TestFunctionArgument.m_128091_(p_128043_, "testName"), IntegerArgumentType.getInteger(p_128043_, "rotationSteps"));
      })))).then(Commands.m_82127_("runall").executes((p_128041_) -> {
         return m_127955_(p_128041_.getSource(), 0, 8);
      }).then(Commands.m_82129_("testClassName", TestClassNameArgument.m_127917_()).executes((p_128039_) -> {
         return m_127962_(p_128039_.getSource(), TestClassNameArgument.m_127920_(p_128039_, "testClassName"), 0, 8);
      }).then(Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes((p_128037_) -> {
         return m_127962_(p_128037_.getSource(), TestClassNameArgument.m_127920_(p_128037_, "testClassName"), IntegerArgumentType.getInteger(p_128037_, "rotationSteps"), 8);
      }).then(Commands.m_82129_("testsPerRow", IntegerArgumentType.integer()).executes((p_128035_) -> {
         return m_127962_(p_128035_.getSource(), TestClassNameArgument.m_127920_(p_128035_, "testClassName"), IntegerArgumentType.getInteger(p_128035_, "rotationSteps"), IntegerArgumentType.getInteger(p_128035_, "testsPerRow"));
      })))).then(Commands.m_82129_("rotationSteps", IntegerArgumentType.integer()).executes((p_128033_) -> {
         return m_127955_(p_128033_.getSource(), IntegerArgumentType.getInteger(p_128033_, "rotationSteps"), 8);
      }).then(Commands.m_82129_("testsPerRow", IntegerArgumentType.integer()).executes((p_128031_) -> {
         return m_127955_(p_128031_.getSource(), IntegerArgumentType.getInteger(p_128031_, "rotationSteps"), IntegerArgumentType.getInteger(p_128031_, "testsPerRow"));
      })))).then(Commands.m_82127_("export").then(Commands.m_82129_("testName", StringArgumentType.word()).executes((p_128029_) -> {
         return m_128010_(p_128029_.getSource(), StringArgumentType.getString(p_128029_, "testName"));
      }))).then(Commands.m_82127_("exportthis").executes((p_128027_) -> {
         return m_128008_(p_128027_.getSource());
      })).then(Commands.m_82127_("import").then(Commands.m_82129_("testName", StringArgumentType.word()).executes((p_128025_) -> {
         return m_128015_(p_128025_.getSource(), StringArgumentType.getString(p_128025_, "testName"));
      }))).then(Commands.m_82127_("pos").executes((p_128023_) -> {
         return m_127959_(p_128023_.getSource(), "pos");
      }).then(Commands.m_82129_("var", StringArgumentType.word()).executes((p_128021_) -> {
         return m_127959_(p_128021_.getSource(), StringArgumentType.getString(p_128021_, "var"));
      }))).then(Commands.m_82127_("create").then(Commands.m_82129_("testName", StringArgumentType.word()).executes((p_128019_) -> {
         return m_127967_(p_128019_.getSource(), StringArgumentType.getString(p_128019_, "testName"), 5, 5, 5);
      }).then(Commands.m_82129_("width", IntegerArgumentType.integer()).executes((p_128014_) -> {
         return m_127967_(p_128014_.getSource(), StringArgumentType.getString(p_128014_, "testName"), IntegerArgumentType.getInteger(p_128014_, "width"), IntegerArgumentType.getInteger(p_128014_, "width"), IntegerArgumentType.getInteger(p_128014_, "width"));
      }).then(Commands.m_82129_("height", IntegerArgumentType.integer()).then(Commands.m_82129_("depth", IntegerArgumentType.integer()).executes((p_128007_) -> {
         return m_127967_(p_128007_.getSource(), StringArgumentType.getString(p_128007_, "testName"), IntegerArgumentType.getInteger(p_128007_, "width"), IntegerArgumentType.getInteger(p_128007_, "height"), IntegerArgumentType.getInteger(p_128007_, "depth"));
      })))))).then(Commands.m_82127_("clearall").executes((p_128000_) -> {
         return m_127952_(p_128000_.getSource(), 200);
      }).then(Commands.m_82129_("radius", IntegerArgumentType.integer()).executes((p_127949_) -> {
         return m_127952_(p_127949_.getSource(), IntegerArgumentType.getInteger(p_127949_, "radius"));
      }))));
   }

   private static int m_127967_(CommandSourceStack p_127968_, String p_127969_, int p_127970_, int p_127971_, int p_127972_) {
      if (p_127970_ <= 48 && p_127971_ <= 48 && p_127972_ <= 48) {
         ServerLevel serverlevel = p_127968_.m_81372_();
         BlockPos blockpos = new BlockPos(p_127968_.m_81371_());
         BlockPos blockpos1 = new BlockPos(blockpos.m_123341_(), p_127968_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, blockpos).m_123342_(), blockpos.m_123343_() + 3);
         StructureUtils.m_177764_(p_127969_.toLowerCase(), blockpos1, new Vec3i(p_127970_, p_127971_, p_127972_), Rotation.NONE, serverlevel);

         for(int i = 0; i < p_127970_; ++i) {
            for(int j = 0; j < p_127972_; ++j) {
               BlockPos blockpos2 = new BlockPos(blockpos1.m_123341_() + i, blockpos1.m_123342_() + 1, blockpos1.m_123343_() + j);
               Block block = Blocks.f_50387_;
               BlockInput blockinput = new BlockInput(block.m_49966_(), Collections.emptySet(), (CompoundTag)null);
               blockinput.m_114670_(serverlevel, blockpos2, 2);
            }
         }

         StructureUtils.m_127875_(blockpos1, new BlockPos(1, 0, -1), Rotation.NONE, serverlevel);
         return 0;
      } else {
         throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
      }
   }

   private static int m_127959_(CommandSourceStack p_127960_, String p_127961_) throws CommandSyntaxException {
      BlockHitResult blockhitresult = (BlockHitResult)p_127960_.m_81375_().m_19907_(10.0D, 1.0F, false);
      BlockPos blockpos = blockhitresult.m_82425_();
      ServerLevel serverlevel = p_127960_.m_81372_();
      Optional<BlockPos> optional = StructureUtils.m_127853_(blockpos, 15, serverlevel);
      if (!optional.isPresent()) {
         optional = StructureUtils.m_127853_(blockpos, 200, serverlevel);
      }

      if (!optional.isPresent()) {
         p_127960_.m_81352_(new TextComponent("Can't find a structure block that contains the targeted pos " + blockpos));
         return 0;
      } else {
         StructureBlockEntity structureblockentity = (StructureBlockEntity)serverlevel.m_7702_(optional.get());
         BlockPos blockpos1 = blockpos.m_141950_(optional.get());
         String s = blockpos1.m_123341_() + ", " + blockpos1.m_123342_() + ", " + blockpos1.m_123343_();
         String s1 = structureblockentity.m_59900_();
         Component component = (new TextComponent(s)).m_6270_(Style.f_131099_.m_131136_(true).m_131140_(ChatFormatting.GREEN).m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, new TextComponent("Click to copy to clipboard"))).m_131142_(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "final BlockPos " + p_127961_ + " = new BlockPos(" + s + ");")));
         p_127960_.m_81354_((new TextComponent("Position relative to " + s1 + ": ")).m_7220_(component), false);
         DebugPackets.m_133682_(serverlevel, new BlockPos(blockpos), s, -2147418368, 10000);
         return 1;
      }
   }

   private static int m_127950_(CommandSourceStack p_127951_) {
      BlockPos blockpos = new BlockPos(p_127951_.m_81371_());
      ServerLevel serverlevel = p_127951_.m_81372_();
      BlockPos blockpos1 = StructureUtils.m_127906_(blockpos, 15, serverlevel);
      if (blockpos1 == null) {
         m_127933_(serverlevel, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
         return 0;
      } else {
         GameTestRunner.m_127685_(serverlevel);
         m_127929_(serverlevel, blockpos1, (MultipleTestTracker)null);
         return 1;
      }
   }

   private static int m_128001_(CommandSourceStack p_128002_) {
      BlockPos blockpos = new BlockPos(p_128002_.m_81371_());
      ServerLevel serverlevel = p_128002_.m_81372_();
      Collection<BlockPos> collection = StructureUtils.m_127910_(blockpos, 200, serverlevel);
      if (collection.isEmpty()) {
         m_127933_(serverlevel, "Couldn't find any structure blocks within 200 block radius", ChatFormatting.RED);
         return 1;
      } else {
         GameTestRunner.m_127685_(serverlevel);
         m_128003_(p_128002_, "Running " + collection.size() + " tests...");
         MultipleTestTracker multipletesttracker = new MultipleTestTracker();
         collection.forEach((p_127943_) -> {
            m_127929_(serverlevel, p_127943_, multipletesttracker);
         });
         return 1;
      }
   }

   private static void m_127929_(ServerLevel p_127930_, BlockPos p_127931_, @Nullable MultipleTestTracker p_127932_) {
      StructureBlockEntity structureblockentity = (StructureBlockEntity)p_127930_.m_7702_(p_127931_);
      String s = structureblockentity.m_59900_();
      TestFunction testfunction = GameTestRegistry.m_127681_(s);
      GameTestInfo gametestinfo = new GameTestInfo(testfunction, structureblockentity.m_59906_(), p_127930_);
      if (p_127932_ != null) {
         p_127932_.m_127809_(gametestinfo);
         gametestinfo.m_127624_(new TestCommand.TestSummaryDisplayer(p_127930_, p_127932_));
      }

      m_127993_(testfunction, p_127930_);
      AABB aabb = StructureUtils.m_127847_(structureblockentity);
      BlockPos blockpos = new BlockPos(aabb.f_82288_, aabb.f_82289_, aabb.f_82290_);
      GameTestRunner.m_127742_(gametestinfo, blockpos, GameTestTicker.f_177648_);
   }

   static void m_127996_(ServerLevel p_127997_, MultipleTestTracker p_127998_) {
      if (p_127998_.m_127821_()) {
         m_127933_(p_127997_, "GameTest done! " + p_127998_.m_127820_() + " tests were run", ChatFormatting.WHITE);
         if (p_127998_.m_127818_()) {
            m_127933_(p_127997_, p_127998_.m_127803_() + " required tests failed :(", ChatFormatting.RED);
         } else {
            m_127933_(p_127997_, "All required tests passed :)", ChatFormatting.GREEN);
         }

         if (p_127998_.m_127819_()) {
            m_127933_(p_127997_, p_127998_.m_127816_() + " optional tests failed", ChatFormatting.GRAY);
         }
      }

   }

   private static int m_127952_(CommandSourceStack p_127953_, int p_127954_) {
      ServerLevel serverlevel = p_127953_.m_81372_();
      GameTestRunner.m_127685_(serverlevel);
      BlockPos blockpos = new BlockPos(p_127953_.m_81371_().f_82479_, (double)p_127953_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, new BlockPos(p_127953_.m_81371_())).m_123342_(), p_127953_.m_81371_().f_82481_);
      GameTestRunner.m_127694_(serverlevel, blockpos, GameTestTicker.f_177648_, Mth.m_14045_(p_127954_, 0, 1024));
      return 1;
   }

   private static int m_127978_(CommandSourceStack p_127979_, TestFunction p_127980_, int p_127981_) {
      ServerLevel serverlevel = p_127979_.m_81372_();
      BlockPos blockpos = new BlockPos(p_127979_.m_81371_());
      int i = p_127979_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, blockpos).m_123342_();
      BlockPos blockpos1 = new BlockPos(blockpos.m_123341_(), i, blockpos.m_123343_() + 3);
      GameTestRunner.m_127685_(serverlevel);
      m_127993_(p_127980_, serverlevel);
      Rotation rotation = StructureUtils.m_127835_(p_127981_);
      GameTestInfo gametestinfo = new GameTestInfo(p_127980_, rotation, serverlevel);
      GameTestRunner.m_127742_(gametestinfo, blockpos1, GameTestTicker.f_177648_);
      return 1;
   }

   private static void m_127993_(TestFunction p_127994_, ServerLevel p_127995_) {
      Consumer<ServerLevel> consumer = GameTestRegistry.m_127676_(p_127994_.m_128081_());
      if (consumer != null) {
         consumer.accept(p_127995_);
      }

   }

   private static int m_127955_(CommandSourceStack p_127956_, int p_127957_, int p_127958_) {
      GameTestRunner.m_127685_(p_127956_.m_81372_());
      Collection<TestFunction> collection = GameTestRegistry.m_127658_();
      m_128003_(p_127956_, "Running all " + collection.size() + " tests...");
      GameTestRegistry.m_127678_();
      m_127973_(p_127956_, collection, p_127957_, p_127958_);
      return 1;
   }

   private static int m_127962_(CommandSourceStack p_127963_, String p_127964_, int p_127965_, int p_127966_) {
      Collection<TestFunction> collection = GameTestRegistry.m_127659_(p_127964_);
      GameTestRunner.m_127685_(p_127963_.m_81372_());
      m_128003_(p_127963_, "Running " + collection.size() + " tests from " + p_127964_ + "...");
      GameTestRegistry.m_127678_();
      m_127973_(p_127963_, collection, p_127965_, p_127966_);
      return 1;
   }

   private static int m_127982_(CommandSourceStack p_127983_, boolean p_127984_, int p_127985_, int p_127986_) {
      Collection<TestFunction> collection;
      if (p_127984_) {
         collection = GameTestRegistry.m_127675_().stream().filter(TestFunction::m_128080_).collect(Collectors.toList());
      } else {
         collection = GameTestRegistry.m_127675_();
      }

      if (collection.isEmpty()) {
         m_128003_(p_127983_, "No failed tests to rerun");
         return 0;
      } else {
         GameTestRunner.m_127685_(p_127983_.m_81372_());
         m_128003_(p_127983_, "Rerunning " + collection.size() + " failed tests (" + (p_127984_ ? "only required tests" : "including optional tests") + ")");
         m_127973_(p_127983_, collection, p_127985_, p_127986_);
         return 1;
      }
   }

   private static void m_127973_(CommandSourceStack p_127974_, Collection<TestFunction> p_127975_, int p_127976_, int p_127977_) {
      BlockPos blockpos = new BlockPos(p_127974_.m_81371_());
      BlockPos blockpos1 = new BlockPos(blockpos.m_123341_(), p_127974_.m_81372_().m_5452_(Heightmap.Types.WORLD_SURFACE, blockpos).m_123342_(), blockpos.m_123343_() + 3);
      ServerLevel serverlevel = p_127974_.m_81372_();
      Rotation rotation = StructureUtils.m_127835_(p_127976_);
      Collection<GameTestInfo> collection = GameTestRunner.m_127752_(p_127975_, blockpos1, rotation, serverlevel, GameTestTicker.f_177648_, p_127977_);
      MultipleTestTracker multipletesttracker = new MultipleTestTracker(collection);
      multipletesttracker.m_127811_(new TestCommand.TestSummaryDisplayer(serverlevel, multipletesttracker));
      multipletesttracker.m_127807_((p_127992_) -> {
         GameTestRegistry.m_127664_(p_127992_.m_127648_());
      });
   }

   private static void m_128003_(CommandSourceStack p_128004_, String p_128005_) {
      p_128004_.m_81354_(new TextComponent(p_128005_), false);
   }

   private static int m_128008_(CommandSourceStack p_128009_) {
      BlockPos blockpos = new BlockPos(p_128009_.m_81371_());
      ServerLevel serverlevel = p_128009_.m_81372_();
      BlockPos blockpos1 = StructureUtils.m_127906_(blockpos, 15, serverlevel);
      if (blockpos1 == null) {
         m_127933_(serverlevel, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
         return 0;
      } else {
         StructureBlockEntity structureblockentity = (StructureBlockEntity)serverlevel.m_7702_(blockpos1);
         String s = structureblockentity.m_59900_();
         return m_128010_(p_128009_, s);
      }
   }

   private static int m_128010_(CommandSourceStack p_128011_, String p_128012_) {
      Path path = Paths.get(StructureUtils.f_127833_);
      ResourceLocation resourcelocation = new ResourceLocation("minecraft", p_128012_);
      Path path1 = p_128011_.m_81372_().m_8875_().m_74343_(resourcelocation, ".nbt");
      Path path2 = NbtToSnbt.m_126431_(path1, p_128012_, path);
      if (path2 == null) {
         m_128003_(p_128011_, "Failed to export " + path1);
         return 1;
      } else {
         try {
            Files.createDirectories(path2.getParent());
         } catch (IOException ioexception) {
            m_128003_(p_128011_, "Could not create folder " + path2.getParent());
            ioexception.printStackTrace();
            return 1;
         }

         m_128003_(p_128011_, "Exported " + p_128012_ + " to " + path2.toAbsolutePath());
         return 0;
      }
   }

   private static int m_128015_(CommandSourceStack p_128016_, String p_128017_) {
      Path path = Paths.get(StructureUtils.f_127833_, p_128017_ + ".snbt");
      ResourceLocation resourcelocation = new ResourceLocation("minecraft", p_128017_);
      Path path1 = p_128016_.m_81372_().m_8875_().m_74343_(resourcelocation, ".nbt");

      try {
         BufferedReader bufferedreader = Files.newBufferedReader(path);
         String s = IOUtils.toString((Reader)bufferedreader);
         Files.createDirectories(path1.getParent());
         OutputStream outputstream = Files.newOutputStream(path1);

         try {
            NbtIo.m_128947_(NbtUtils.m_178024_(s), outputstream);
         } catch (Throwable throwable1) {
            if (outputstream != null) {
               try {
                  outputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (outputstream != null) {
            outputstream.close();
         }

         m_128003_(p_128016_, "Imported to " + path1.toAbsolutePath());
         return 0;
      } catch (CommandSyntaxException | IOException ioexception) {
         System.err.println("Failed to load structure " + p_128017_);
         ioexception.printStackTrace();
         return 1;
      }
   }

   private static void m_127933_(ServerLevel p_127934_, String p_127935_, ChatFormatting p_127936_) {
      p_127934_.m_8795_((p_127945_) -> {
         return true;
      }).forEach((p_127990_) -> {
         p_127990_.m_6352_(new TextComponent(p_127936_ + p_127935_), Util.f_137441_);
      });
   }

   static class TestSummaryDisplayer implements GameTestListener {
      private final ServerLevel f_128058_;
      private final MultipleTestTracker f_128059_;

      public TestSummaryDisplayer(ServerLevel p_128061_, MultipleTestTracker p_128062_) {
         this.f_128058_ = p_128061_;
         this.f_128059_ = p_128062_;
      }

      public void m_8073_(GameTestInfo p_128064_) {
      }

      public void m_142378_(GameTestInfo p_177797_) {
         TestCommand.m_127996_(this.f_128058_, this.f_128059_);
      }

      public void m_8066_(GameTestInfo p_128066_) {
         TestCommand.m_127996_(this.f_128058_, this.f_128059_);
      }
   }
}