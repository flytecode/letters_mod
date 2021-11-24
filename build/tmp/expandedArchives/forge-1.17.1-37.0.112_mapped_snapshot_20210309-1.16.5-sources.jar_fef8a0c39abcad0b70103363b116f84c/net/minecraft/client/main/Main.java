package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.CrashReport;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.User;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Main {
   static final Logger f_129630_ = LogManager.getLogger();

   @DontObfuscate
   public static void main(String[] p_129642_) {
      SharedConstants.m_142977_();
      OptionParser optionparser = new OptionParser();
      optionparser.allowsUnrecognizedOptions();
      optionparser.accepts("demo");
      optionparser.accepts("disableMultiplayer");
      optionparser.accepts("disableChat");
      optionparser.accepts("fullscreen");
      optionparser.accepts("checkGlErrors");
      OptionSpec<String> optionspec = optionparser.accepts("server").withRequiredArg();
      OptionSpec<Integer> optionspec1 = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
      OptionSpec<File> optionspec2 = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
      OptionSpec<File> optionspec3 = optionparser.accepts("assetsDir").withRequiredArg().ofType(File.class);
      OptionSpec<File> optionspec4 = optionparser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
      OptionSpec<String> optionspec5 = optionparser.accepts("proxyHost").withRequiredArg();
      OptionSpec<Integer> optionspec6 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080").ofType(Integer.class);
      OptionSpec<String> optionspec7 = optionparser.accepts("proxyUser").withRequiredArg();
      OptionSpec<String> optionspec8 = optionparser.accepts("proxyPass").withRequiredArg();
      OptionSpec<String> optionspec9 = optionparser.accepts("username").withRequiredArg().defaultsTo("Player" + Util.m_137550_() % 1000L);
      OptionSpec<String> optionspec10 = optionparser.accepts("uuid").withRequiredArg();
      OptionSpec<String> optionspec11 = optionparser.accepts("accessToken").withRequiredArg().required();
      OptionSpec<String> optionspec12 = optionparser.accepts("version").withRequiredArg().required();
      OptionSpec<Integer> optionspec13 = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
      OptionSpec<Integer> optionspec14 = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
      OptionSpec<Integer> optionspec15 = optionparser.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
      OptionSpec<Integer> optionspec16 = optionparser.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
      OptionSpec<String> optionspec17 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> optionspec18 = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
      OptionSpec<String> optionspec19 = optionparser.accepts("assetIndex").withRequiredArg();
      OptionSpec<String> optionspec20 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy");
      OptionSpec<String> optionspec21 = optionparser.accepts("versionType").withRequiredArg().defaultsTo("release");
      OptionSpec<String> optionspec22 = optionparser.nonOptions();
      OptionSet optionset = optionparser.parse(p_129642_);
      List<String> list = optionset.valuesOf(optionspec22);
      if (!list.isEmpty()) {
         System.out.println("Completely ignored arguments: " + list);
      }

      String s = m_129638_(optionset, optionspec5);
      Proxy proxy = Proxy.NO_PROXY;
      if (s != null) {
         try {
            proxy = new Proxy(Type.SOCKS, new InetSocketAddress(s, m_129638_(optionset, optionspec6)));
         } catch (Exception exception) {
         }
      }

      final String s1 = m_129638_(optionset, optionspec7);
      final String s2 = m_129638_(optionset, optionspec8);
      if (!proxy.equals(Proxy.NO_PROXY) && m_129636_(s1) && m_129636_(s2)) {
         Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(s1, s2.toCharArray());
            }
         });
      }

      int i = m_129638_(optionset, optionspec13);
      int j = m_129638_(optionset, optionspec14);
      OptionalInt optionalint = m_129634_(m_129638_(optionset, optionspec15));
      OptionalInt optionalint1 = m_129634_(m_129638_(optionset, optionspec16));
      boolean flag = optionset.has("fullscreen");
      boolean flag1 = optionset.has("demo");
      boolean flag2 = optionset.has("disableMultiplayer");
      boolean flag3 = optionset.has("disableChat");
      String s3 = m_129638_(optionset, optionspec12);
      Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap propertymap = GsonHelper.m_13794_(gson, m_129638_(optionset, optionspec17), PropertyMap.class);
      PropertyMap propertymap1 = GsonHelper.m_13794_(gson, m_129638_(optionset, optionspec18), PropertyMap.class);
      String s4 = m_129638_(optionset, optionspec21);
      File file1 = m_129638_(optionset, optionspec2);
      File file2 = optionset.has(optionspec3) ? m_129638_(optionset, optionspec3) : new File(file1, "assets/");
      File file3 = optionset.has(optionspec4) ? m_129638_(optionset, optionspec4) : new File(file1, "resourcepacks/");
      String s5 = optionset.has(optionspec10) ? optionspec10.value(optionset) : Player.m_36283_(optionspec9.value(optionset)).toString();
      String s6 = optionset.has(optionspec19) ? optionspec19.value(optionset) : null;
      String s7 = m_129638_(optionset, optionspec);
      Integer integer = m_129638_(optionset, optionspec1);
      CrashReport.m_127529_();
      net.minecraftforge.fml.loading.BackgroundWaiter.runAndTick(()->Bootstrap.m_135870_(), net.minecraftforge.fml.loading.FMLLoader.progressWindowTick);
      Bootstrap.m_135889_();
      Util.m_137584_();
      User user = new User(optionspec9.value(optionset), s5, optionspec11.value(optionset), optionspec20.value(optionset));
      GameConfig gameconfig = new GameConfig(new GameConfig.UserData(user, propertymap, propertymap1, proxy), new DisplayData(i, j, optionalint, optionalint1, flag), new GameConfig.FolderData(file1, file3, file2, s6), new GameConfig.GameData(flag1, s3, s4, flag2, flag3), new GameConfig.ServerData(s7, integer));
      Thread thread = new Thread("Client Shutdown Thread") {
         public void run() {
            Minecraft minecraft1 = Minecraft.m_91087_();
            if (minecraft1 != null) {
               IntegratedServer integratedserver = minecraft1.m_91092_();
               if (integratedserver != null) {
                  integratedserver.m_7570_(true);
               }

            }
         }
      };
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_129630_));
      Runtime.getRuntime().addShutdownHook(thread);
      new RenderPipeline();

      final Minecraft minecraft;
      try {
         Thread.currentThread().setName("Render thread");
         RenderSystem.m_69579_();
         RenderSystem.m_69395_();
         minecraft = new Minecraft(gameconfig);
         RenderSystem.m_69494_();
      } catch (SilentInitException silentinitexception) {
         f_129630_.warn("Failed to create window: ", (Throwable)silentinitexception);
         return;
      } catch (Throwable throwable1) {
         CrashReport crashreport = CrashReport.m_127521_(throwable1, "Initializing game");
         crashreport.m_127514_("Initialization");
         Minecraft.m_167872_((Minecraft)null, (LanguageManager)null, gameconfig.f_101908_.f_101927_, (Options)null, crashreport);
         Minecraft.m_91332_(crashreport);
         return;
      }

      Thread thread1;
      if (minecraft.m_91267_()) {
         thread1 = new Thread("Game thread") {
            public void run() {
               try {
                  RenderSystem.m_69577_(true);
                  minecraft.m_91374_();
               } catch (Throwable throwable2) {
                  Main.f_129630_.error("Exception in client thread", throwable2);
               }

            }
         };
         thread1.start();

         while(minecraft.m_91396_()) {
         }
      } else {
         thread1 = null;

         try {
            RenderSystem.m_69577_(false);
            minecraft.m_91374_();
         } catch (Throwable throwable) {
            f_129630_.error("Unhandled game exception", throwable);
         }
      }

      BufferUploader.m_166835_();

      try {
         minecraft.m_91395_();
         if (thread1 != null) {
            thread1.join();
         }
      } catch (InterruptedException interruptedexception) {
         f_129630_.error("Exception during client thread shutdown", (Throwable)interruptedexception);
      } finally {
         minecraft.m_91393_();
      }

   }

   private static OptionalInt m_129634_(@Nullable Integer p_129635_) {
      return p_129635_ != null ? OptionalInt.of(p_129635_) : OptionalInt.empty();
   }

   @Nullable
   private static <T> T m_129638_(OptionSet p_129639_, OptionSpec<T> p_129640_) {
      try {
         return p_129639_.valueOf(p_129640_);
      } catch (Throwable throwable) {
         if (p_129640_ instanceof ArgumentAcceptingOptionSpec) {
            ArgumentAcceptingOptionSpec<T> argumentacceptingoptionspec = (ArgumentAcceptingOptionSpec)p_129640_;
            List<T> list = argumentacceptingoptionspec.defaultValues();
            if (!list.isEmpty()) {
               return list.get(0);
            }
         }

         throw throwable;
      }
   }

   private static boolean m_129636_(@Nullable String p_129637_) {
      return p_129637_ != null && !p_129637_.isEmpty();
   }

   static {
      System.setProperty("java.awt.headless", "true");
   }
}
