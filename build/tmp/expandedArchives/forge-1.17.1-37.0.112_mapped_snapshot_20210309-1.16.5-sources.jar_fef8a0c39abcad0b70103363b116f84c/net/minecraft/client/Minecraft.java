package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.OfflineSocialInteractions;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.GlDebug;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Function4;
import com.mojang.math.Matrix4f;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.FileUtil;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DatapackLoadFailureScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.OutOfMemoryScreen;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.profiling.ClientMetricsSamplersProvider;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.VirtualScreen;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.LegacyPackResourcesAdapter;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.client.resources.PackResourcesAdapterV4;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.searchtree.MutableSearchTree;
import net.minecraft.client.searchtree.ReloadableIdSearchTree;
import net.minecraft.client.searchtree.ReloadableSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.KeybindComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.level.progress.ProcessorChunkProgressListener;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FileZipper;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.Unit;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.profiling.ContinuousProfiler;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Snooper;
import net.minecraft.world.SnooperPopulator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

@OnlyIn(Dist.CLIENT)
public class Minecraft extends ReentrantBlockableEventLoop<Runnable> implements SnooperPopulator, WindowEventHandler, net.minecraftforge.client.extensions.IForgeMinecraft {
   private static Minecraft f_90981_;
   private static final Logger f_90982_ = LogManager.getLogger();
   public static final boolean f_91002_ = Util.m_137581_() == Util.OS.OSX;
   private static final int f_167843_ = 10;
   public static final ResourceLocation f_91055_ = new ResourceLocation("default");
   public static final ResourceLocation f_91058_ = new ResourceLocation("uniform");
   public static final ResourceLocation f_91059_ = new ResourceLocation("alt");
   private static final CompletableFuture<Unit> f_90983_ = CompletableFuture.completedFuture(Unit.INSTANCE);
   private static final Component f_90984_ = new TranslatableComponent("multiplayer.socialInteractions.not_available");
   public static final String f_167848_ = "Please make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).";
   private final File f_90985_;
   private final PropertyMap f_90986_;
   public final TextureManager f_90987_;
   private final DataFixer f_90988_;
   private final VirtualScreen f_90989_;
   private final Window f_90990_;
   private final Timer f_90991_ = new Timer(20.0F, 0L);
   private final Snooper f_90992_ = new Snooper("client", this, Util.m_137550_());
   private final RenderBuffers f_90993_;
   public final LevelRenderer f_91060_;
   private final EntityRenderDispatcher f_90994_;
   private final ItemRenderer f_90995_;
   private final ItemInHandRenderer f_90996_;
   public final ParticleEngine f_91061_;
   private final SearchRegistry f_90997_ = new SearchRegistry();
   private final User f_90998_;
   public final Font f_91062_;
   public final GameRenderer f_91063_;
   public final DebugRenderer f_91064_;
   private final AtomicReference<StoringChunkProgressListener> f_90999_ = new AtomicReference<>();
   public final Gui f_91065_;
   public final Options f_91066_;
   private final HotbarManager f_91000_;
   public final MouseHandler f_91067_;
   public final KeyboardHandler f_91068_;
   public final File f_91069_;
   private final String f_91001_;
   private final String f_91029_;
   private final Proxy f_91030_;
   private final LevelStorageSource f_91031_;
   public final FrameTimer f_91070_ = new FrameTimer();
   private final boolean f_91032_;
   private final boolean f_91033_;
   private final boolean f_91034_;
   private final boolean f_91035_;
   private final ReloadableResourceManager f_91036_;
   private final ClientPackSource f_91037_;
   private final PackRepository f_91038_;
   private final LanguageManager f_91039_;
   private final BlockColors f_91040_;
   private final ItemColors f_91041_;
   private final RenderTarget f_91042_;
   private final SoundManager f_91043_;
   private final MusicManager f_91044_;
   private final FontManager f_91045_;
   private final SplashManager f_91046_;
   private final GpuWarnlistManager f_91047_;
   private final MinecraftSessionService f_91048_;
   private final SocialInteractionsService f_91049_;
   private final SkinManager f_91050_;
   private final ModelManager f_91051_;
   private final BlockRenderDispatcher f_91052_;
   private final PaintingTextureManager f_91053_;
   private final MobEffectTextureManager f_91054_;
   private final ToastComponent f_91003_;
   private final Game f_91004_ = new Game(this);
   private final Tutorial f_91005_;
   private final PlayerSocialManager f_91006_;
   private final EntityModelSet f_167844_;
   private final BlockEntityRenderDispatcher f_167845_;
   @Nullable
   public MultiPlayerGameMode f_91072_;
   @Nullable
   public ClientLevel f_91073_;
   @Nullable
   public LocalPlayer f_91074_;
   @Nullable
   private IntegratedServer f_91007_;
   @Nullable
   private ServerData f_91008_;
   @Nullable
   private Connection f_91009_;
   private boolean f_91010_;
   @Nullable
   public Entity f_91075_;
   @Nullable
   public Entity f_91076_;
   @Nullable
   public HitResult f_91077_;
   private int f_91011_;
   protected int f_91078_;
   private boolean f_91012_;
   private float f_91013_;
   private long f_91014_ = Util.m_137569_();
   private long f_91015_;
   private int f_91016_;
   public boolean f_91079_;
   @Nullable
   public Screen f_91080_;
   @Nullable
   private Overlay f_91081_;
   private boolean f_91017_;
   private Thread f_91018_;
   private volatile boolean f_91019_ = true;
   @Nullable
   private CrashReport f_91020_;
   private static int f_91021_;
   public String f_90977_ = "";
   public boolean f_167842_;
   public boolean f_90978_;
   public boolean f_90979_;
   public boolean f_90980_ = true;
   private boolean f_91022_;
   private final Queue<Runnable> f_91023_ = Queues.newConcurrentLinkedQueue();
   @Nullable
   private CompletableFuture<Void> f_91024_;
   @Nullable
   private TutorialToast f_91025_;
   private ProfilerFiller f_91026_ = InactiveProfiler.f_18554_;
   private int f_91027_;
   private final ContinuousProfiler f_91028_ = new ContinuousProfiler(Util.f_137440_, () -> {
      return this.f_91027_;
   });
   @Nullable
   private ProfileResults f_91056_;
   private MetricsRecorder f_167846_ = InactiveMetricsRecorder.f_146153_;
   private final ResourceLoadStateTracker f_167847_ = new ResourceLoadStateTracker();
   private String f_91057_ = "root";

   public Minecraft(GameConfig p_91084_) {
      super("Client");
      f_90981_ = this;
      this.f_91069_ = p_91084_.f_101907_.f_101916_;
      File file1 = p_91084_.f_101907_.f_101918_;
      this.f_90985_ = p_91084_.f_101907_.f_101917_;
      this.f_91001_ = p_91084_.f_101908_.f_101927_;
      this.f_91029_ = p_91084_.f_101908_.f_101928_;
      this.f_90986_ = p_91084_.f_101905_.f_101944_;
      this.f_91037_ = new ClientPackSource(new File(this.f_91069_, "server-resource-packs"), p_91084_.f_101907_.m_101925_());
      this.f_91038_ = new PackRepository(Minecraft::createClientPackAdapter, this.f_91037_, new FolderRepositorySource(this.f_90985_, PackSource.f_10527_));
      this.f_91030_ = p_91084_.f_101905_.f_101945_;
      YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(this.f_91030_);
      this.f_91048_ = yggdrasilauthenticationservice.createMinecraftSessionService();
      this.f_91049_ = this.m_91130_(yggdrasilauthenticationservice, p_91084_);
      this.f_90998_ = p_91084_.f_101905_.f_101942_;
      f_90982_.info("Setting user: {}", (Object)this.f_90998_.m_92546_());
      this.f_91033_ = p_91084_.f_101908_.f_101926_;
      this.f_91034_ = !p_91084_.f_101908_.f_101929_;
      this.f_91035_ = !p_91084_.f_101908_.f_101930_;
      this.f_91032_ = m_91272_();
      this.f_91007_ = null;
      String s;
      int i;
      if (this.m_91400_() && p_91084_.f_101909_.f_101937_ != null) {
         s = p_91084_.f_101909_.f_101937_;
         i = p_91084_.f_101909_.f_101938_;
      } else {
         s = null;
         i = 0;
      }

      KeybindComponent.m_130919_(KeyMapping::m_90842_);
      this.f_90988_ = DataFixers.m_14512_();
      this.f_91003_ = new ToastComponent(this);
      this.f_91018_ = Thread.currentThread();
      this.f_91066_ = new Options(this, this.f_91069_);
      this.f_91005_ = new Tutorial(this, this.f_91066_);
      this.f_91000_ = new HotbarManager(this.f_91069_, this.f_90988_);
      f_90982_.info("Backend library: {}", (Object)RenderSystem.m_69517_());
      DisplayData displaydata;
      if (this.f_91066_.f_92129_ > 0 && this.f_91066_.f_92128_ > 0) {
         displaydata = new DisplayData(this.f_91066_.f_92128_, this.f_91066_.f_92129_, p_91084_.f_101906_.f_84007_, p_91084_.f_101906_.f_84008_, p_91084_.f_101906_.f_84009_);
      } else {
         displaydata = p_91084_.f_101906_;
      }

      Util.f_137440_ = RenderSystem.m_69576_();
      if (net.minecraftforge.common.ForgeConfig.CLIENT.forceSystemNanoTime.get()) Util.f_137440_ = System::nanoTime;
      this.f_90989_ = new VirtualScreen(this);
      this.f_90990_ = this.f_90989_.m_110872_(displaydata, this.f_91066_.f_92123_, this.m_91270_());
      this.m_7440_(true);

      try {
         InputStream inputstream = this.m_91100_().m_118555_().m_8031_(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_16x16.png"));
         InputStream inputstream1 = this.m_91100_().m_118555_().m_8031_(PackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_32x32.png"));
         this.f_90990_.m_85395_(inputstream, inputstream1);
      } catch (IOException ioexception) {
         f_90982_.error("Couldn't set icon", (Throwable)ioexception);
      }

      this.f_90990_.m_85380_(this.f_91066_.f_92113_);
      this.f_91067_ = new MouseHandler(this);
      this.f_91068_ = new KeyboardHandler(this);
      this.f_91068_.m_90887_(this.f_90990_.m_85439_());
      RenderSystem.m_69580_(this.f_91066_.f_92035_, false);
      this.f_91042_ = new MainTarget(this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
      this.f_91042_.m_83931_(0.0F, 0.0F, 0.0F, 0.0F);
      this.f_91042_.m_83954_(f_91002_);
      this.f_91036_ = new SimpleReloadableResourceManager(PackType.CLIENT_RESOURCES);
      net.minecraftforge.fmlclient.ClientModLoader.begin(this, this.f_91038_, this.f_91036_, this.f_91037_);
      this.f_91038_.m_10506_();
      this.f_91066_.m_92145_(this.f_91038_);
      this.f_91039_ = new LanguageManager(this.f_91066_.f_92075_);
      this.f_91036_.m_7217_(this.f_91039_);
      this.f_90987_ = new TextureManager(this.f_91036_);
      this.f_91036_.m_7217_(this.f_90987_);
      this.f_91050_ = new SkinManager(this.f_90987_, new File(file1, "skins"), this.f_91048_);
      this.f_91031_ = new LevelStorageSource(this.f_91069_.toPath().resolve("saves"), this.f_91069_.toPath().resolve("backups"), this.f_90988_);
      this.f_91043_ = new SoundManager(this.f_91036_, this.f_91066_);
      this.f_91036_.m_7217_(this.f_91043_);
      this.f_91046_ = new SplashManager(this.f_90998_);
      this.f_91036_.m_7217_(this.f_91046_);
      this.f_91044_ = new MusicManager(this);
      this.f_91045_ = new FontManager(this.f_90987_);
      this.f_91062_ = this.f_91045_.m_95006_();
      this.f_91036_.m_7217_(this.f_91045_.m_95015_());
      this.m_91336_(this.m_91390_());
      this.f_91036_.m_7217_(new GrassColorReloadListener());
      this.f_91036_.m_7217_(new FoliageColorReloadListener());
      this.f_90990_.m_85403_("Startup");
      RenderSystem.m_69902_(0, 0, this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
      this.f_90990_.m_85403_("Post startup");
      this.f_91040_ = BlockColors.m_92574_();
      this.f_91041_ = ItemColors.m_92683_(this.f_91040_);
      this.f_91051_ = new ModelManager(this.f_90987_, this.f_91040_, this.f_91066_.f_92027_);
      this.f_91036_.m_7217_(this.f_91051_);
      this.f_167844_ = new EntityModelSet();
      this.f_91036_.m_7217_(this.f_167844_);
      this.f_167845_ = new BlockEntityRenderDispatcher(this.f_91062_, this.f_167844_, this::m_91289_);
      this.f_91036_.m_7217_(this.f_167845_);
      BlockEntityWithoutLevelRenderer blockentitywithoutlevelrenderer = new BlockEntityWithoutLevelRenderer(this.f_167845_, this.f_167844_);
      this.f_91036_.m_7217_(blockentitywithoutlevelrenderer);
      this.f_90995_ = new ItemRenderer(this.f_90987_, this.f_91051_, this.f_91041_, blockentitywithoutlevelrenderer);
      this.f_90994_ = new EntityRenderDispatcher(this.f_90987_, this.f_90995_, this.f_91062_, this.f_91066_, this.f_167844_);
      this.f_91036_.m_7217_(this.f_90994_);
      this.f_90996_ = new ItemInHandRenderer(this);
      this.f_91036_.m_7217_(this.f_90995_);
      this.f_90993_ = new RenderBuffers();
      this.f_91063_ = new GameRenderer(this, this.f_91036_, this.f_90993_);
      this.f_91036_.m_7217_(this.f_91063_);
      this.f_91006_ = new PlayerSocialManager(this, this.f_91049_);
      this.f_91052_ = new BlockRenderDispatcher(this.f_91051_.m_119430_(), blockentitywithoutlevelrenderer, this.f_91040_);
      this.f_91036_.m_7217_(this.f_91052_);
      this.f_91060_ = new LevelRenderer(this, this.f_90993_);
      this.f_91036_.m_7217_(this.f_91060_);
      this.m_91271_();
      this.f_91036_.m_7217_(this.f_90997_);
      this.f_91061_ = new ParticleEngine(this.f_91073_, this.f_90987_);
      net.minecraftforge.fml.ModLoader.get().postEvent(new net.minecraftforge.client.event.ParticleFactoryRegisterEvent());
      this.f_91036_.m_7217_(this.f_91061_);
      this.f_91053_ = new PaintingTextureManager(this.f_90987_);
      this.f_91036_.m_7217_(this.f_91053_);
      this.f_91054_ = new MobEffectTextureManager(this.f_90987_);
      this.f_91036_.m_7217_(this.f_91054_);
      this.f_91047_ = new GpuWarnlistManager();
      this.f_91036_.m_7217_(this.f_91047_);
      this.f_91065_ = new net.minecraftforge.client.gui.ForgeIngameGui(this);
      this.f_91067_.m_91524_(this.f_90990_.m_85439_()); //Forge: Moved below ingameGUI setting to prevent NPEs in handeler.
      this.f_91064_ = new DebugRenderer(this);
      RenderSystem.m_69900_(this::m_91113_);
      if (this.f_91042_.f_83915_ == this.f_90990_.m_85441_() && this.f_91042_.f_83916_ == this.f_90990_.m_85442_()) {
         if (this.f_91066_.f_92052_ && !this.f_90990_.m_85440_()) {
            this.f_90990_.m_85438_();
            this.f_91066_.f_92052_ = this.f_90990_.m_85440_();
         }
      } else {
         StringBuilder stringbuilder = new StringBuilder("Recovering from unsupported resolution (" + this.f_90990_.m_85441_() + "x" + this.f_90990_.m_85442_() + ").\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).");
         if (GlDebug.m_166226_()) {
            stringbuilder.append("\n\nReported GL debug messages:\n").append(String.join("\n", GlDebug.m_166225_()));
         }

         this.f_90990_.m_166447_(this.f_91042_.f_83915_, this.f_91042_.f_83916_);
         TinyFileDialogs.tinyfd_messageBox("Minecraft", stringbuilder.toString(), "ok", "error", false);
      }

      net.minecraftforge.fml.ModLoader.get().postEvent(new net.minecraftforge.client.event.RegisterClientReloadListenersEvent(this.f_91036_));
      net.minecraftforge.fml.ModLoader.get().postEvent(new net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions());
      net.minecraftforge.fml.ModLoader.get().postEvent(new net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers());

      this.f_90990_.m_85409_(this.f_91066_.f_92041_);
      this.f_90990_.m_85424_(this.f_91066_.f_92034_);
      this.f_90990_.m_85426_();
      this.m_5741_();
      this.f_91063_.m_172722_(this.m_91100_().m_118555_());
      LoadingOverlay.m_96189_(this);
      List<PackResources> list = this.f_91038_.m_10525_();
      this.f_167847_.m_168557_(ResourceLoadStateTracker.ReloadReason.INITIAL, list);
      this.m_91150_(new LoadingOverlay(this, this.f_91036_.m_142463_(Util.m_137578_(), this, f_90983_, list), (p_91245_) -> {
         Util.m_137521_(p_91245_, this::m_91239_, () -> {
            if (SharedConstants.f_136183_) {
               this.m_91273_();
            }

            this.f_167847_.m_168556_();
            if (net.minecraftforge.fmlclient.ClientModLoader.completeModLoading()) return; // Do not overwrite the error screen
            // FORGE: Move opening initial screen to after startup and events are enabled.
            // Also Fixes MC-145102
            if (s != null) {
                ConnectScreen.m_169267_(new TitleScreen(), this, new ServerAddress(s, i), (ServerData)null);
            } else {
                this.m_91152_(new TitleScreen(true));
            }
         });
      }, false));

   }

   public void m_91341_() {
      this.f_90990_.m_85422_(this.m_91270_());
   }

   private String m_91270_() {
      StringBuilder stringbuilder = new StringBuilder("Minecraft");
      if (this.m_91361_()) {
         stringbuilder.append("*");
      }

      stringbuilder.append(" ");
      stringbuilder.append(SharedConstants.m_136187_().getName());
      ClientPacketListener clientpacketlistener = this.m_91403_();
      if (clientpacketlistener != null && clientpacketlistener.m_6198_().m_129536_()) {
         stringbuilder.append(" - ");
         if (this.f_91007_ != null && !this.f_91007_.m_6992_()) {
            stringbuilder.append(I18n.m_118938_("title.singleplayer"));
         } else if (this.m_91294_()) {
            stringbuilder.append(I18n.m_118938_("title.multiplayer.realms"));
         } else if (this.f_91007_ == null && (this.f_91008_ == null || !this.f_91008_.m_105389_())) {
            stringbuilder.append(I18n.m_118938_("title.multiplayer.other"));
         } else {
            stringbuilder.append(I18n.m_118938_("title.multiplayer.lan"));
         }
      }

      return stringbuilder.toString();
   }

   private SocialInteractionsService m_91130_(YggdrasilAuthenticationService p_91131_, GameConfig p_91132_) {
      try {
         if ("0".equals(p_91132_.f_101905_.f_101942_.m_92547_())) // Forge: We use "0" in dev. Short circuit to stop exception spam.
            return new OfflineSocialInteractions();
         return p_91131_.createSocialInteractionsService(p_91132_.f_101905_.f_101942_.m_92547_());
      } catch (AuthenticationException authenticationexception) {
         f_90982_.error("Failed to verify authentication", (Throwable)authenticationexception);
         return new OfflineSocialInteractions();
      }
   }

   public boolean m_91361_() {
      return !"vanilla".equals(ClientBrandRetriever.m_129629_()) || Minecraft.class.getSigners() == null;
   }

   private void m_91239_(Throwable p_91240_) {
      if (this.f_91038_.m_10524_().stream().anyMatch(e -> !e.m_10449_())) { //Forge: This caused infinite loop if any resource packs are forced. Such as mod resources. So check if we can disable any.
         Component component;
         if (p_91240_ instanceof SimpleReloadableResourceManager.ResourcePackLoadingFailure) {
            component = new TextComponent(((SimpleReloadableResourceManager.ResourcePackLoadingFailure)p_91240_).m_10921_().m_8017_());
         } else {
            component = null;
         }

         this.m_91241_(p_91240_, component);
      } else {
         Util.m_137559_(p_91240_);
      }

   }

   public void m_91241_(Throwable p_91242_, @Nullable Component p_91243_) {
      f_90982_.info("Caught error loading resourcepacks, removing all selected resourcepacks", p_91242_);
      this.f_167847_.m_168560_(p_91242_);
      this.f_91038_.m_10509_(Collections.emptyList());
      this.f_91066_.f_92117_.clear();
      this.f_91066_.f_92118_.clear();
      this.f_91066_.m_92169_();
      this.m_168019_(true).thenRun(() -> {
         ToastComponent toastcomponent = this.m_91300_();
         SystemToast.m_94869_(toastcomponent, SystemToast.SystemToastIds.PACK_LOAD_FAILURE, new TranslatableComponent("resourcePack.load_fail"), p_91243_);
      });
   }

   public void m_91374_() {
      this.f_91018_ = Thread.currentThread();

      try {
         boolean flag = false;

         while(this.f_91019_) {
            if (this.f_91020_ != null) {
               m_91332_(this.f_91020_);
               return;
            }

            try {
               SingleTickProfiler singletickprofiler = SingleTickProfiler.m_18632_("Renderer");
               boolean flag1 = this.m_91274_();
               this.f_91026_ = this.m_167970_(flag1, singletickprofiler);
               this.f_91026_.m_7242_();
               this.f_167846_.m_142759_();
               this.m_91383_(!flag);
               this.f_167846_.m_142758_();
               this.f_91026_.m_7241_();
               this.m_91338_(flag1, singletickprofiler);
            } catch (OutOfMemoryError outofmemoryerror) {
               if (flag) {
                  throw outofmemoryerror;
               }

               this.m_91394_();
               this.m_91152_(new OutOfMemoryScreen());
               System.gc();
               f_90982_.fatal("Out of memory", (Throwable)outofmemoryerror);
               flag = true;
            }
         }
      } catch (ReportedException reportedexception) {
         this.m_91354_(reportedexception.m_134761_());
         this.m_91394_();
         f_90982_.fatal("Reported exception thrown!", (Throwable)reportedexception);
         m_91332_(reportedexception.m_134761_());
      } catch (Throwable throwable) {
         CrashReport crashreport = this.m_91354_(new CrashReport("Unexpected error", throwable));
         f_90982_.fatal("Unreported exception thrown!", throwable);
         this.m_91394_();
         m_91332_(crashreport);
      }

   }

   void m_91336_(boolean p_91337_) {
      this.f_91045_.m_95011_(p_91337_ ? ImmutableMap.of(f_91055_, f_91058_) : ImmutableMap.of());
   }

   public void m_91271_() {
      ReloadableSearchTree<ItemStack> reloadablesearchtree = new ReloadableSearchTree<>((p_91345_) -> {
         return p_91345_.m_41651_((Player)null, TooltipFlag.Default.NORMAL).stream().map((p_168014_) -> {
            return ChatFormatting.m_126649_(p_168014_.getString()).trim();
         }).filter((p_168016_) -> {
            return !p_168016_.isEmpty();
         });
      }, (p_91317_) -> {
         return Stream.of(Registry.f_122827_.m_7981_(p_91317_.m_41720_()));
      });
      ReloadableIdSearchTree<ItemStack> reloadableidsearchtree = new ReloadableIdSearchTree<>((p_91121_) -> {
         return p_91121_.m_41720_().getTags().stream();
      });
      NonNullList<ItemStack> nonnulllist = NonNullList.m_122779_();

      for(Item item : Registry.f_122827_) {
         item.m_6787_(CreativeModeTab.f_40754_, nonnulllist);
      }

      nonnulllist.forEach((p_91170_) -> {
         reloadablesearchtree.m_8080_(p_91170_);
         reloadableidsearchtree.m_8080_(p_91170_);
      });
      ReloadableSearchTree<RecipeCollection> reloadablesearchtree1 = new ReloadableSearchTree<>((p_91323_) -> {
         return p_91323_.m_100516_().stream().flatMap((p_167987_) -> {
            return p_167987_.m_8043_().m_41651_((Player)null, TooltipFlag.Default.NORMAL).stream();
         }).map((p_168008_) -> {
            return ChatFormatting.m_126649_(p_168008_.getString()).trim();
         }).filter((p_168010_) -> {
            return !p_168010_.isEmpty();
         });
      }, (p_91155_) -> {
         return p_91155_.m_100516_().stream().map((p_167866_) -> {
            return Registry.f_122827_.m_7981_(p_167866_.m_8043_().m_41720_());
         });
      });
      this.f_90997_.m_119951_(SearchRegistry.f_119941_, reloadablesearchtree);
      this.f_90997_.m_119951_(SearchRegistry.f_119942_, reloadableidsearchtree);
      this.f_90997_.m_119951_(SearchRegistry.f_119943_, reloadablesearchtree1);
   }

   private void m_91113_(int p_91114_, long p_91115_) {
      this.f_91066_.f_92041_ = false;
      this.f_91066_.m_92169_();
   }

   private static boolean m_91272_() {
      String[] astring = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for(String s : astring) {
         String s1 = System.getProperty(s);
         if (s1 != null && s1.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public RenderTarget m_91385_() {
      return this.f_91042_;
   }

   public String m_91388_() {
      return this.f_91001_;
   }

   public String m_91389_() {
      return this.f_91029_;
   }

   public void m_91253_(CrashReport p_91254_) {
      this.f_91020_ = p_91254_;
   }

   public static void m_91332_(CrashReport p_91333_) {
      File file1 = new File(m_91087_().f_91069_, "crash-reports");
      File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
      Bootstrap.m_135875_(p_91333_.m_127526_());
      if (p_91333_.m_127527_() != null) {
         Bootstrap.m_135875_("#@!@# Game crashed! Crash report saved to: #@!@# " + p_91333_.m_127527_());
         net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleExit(-1);
      } else if (p_91333_.m_127512_(file2)) {
         Bootstrap.m_135875_("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
         net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleExit(-1);
      } else {
         Bootstrap.m_135875_("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleExit(-2);
      }

   }

   public boolean m_91390_() {
      return this.f_91066_.f_92043_;
   }

   public CompletableFuture<Void> m_91391_() {
      return this.m_168019_(false);
   }

   private CompletableFuture<Void> m_168019_(boolean p_168020_) {
      if (this.f_91024_ != null) {
         return this.f_91024_;
      } else {
         CompletableFuture<Void> completablefuture = new CompletableFuture<>();
         if (!p_168020_ && this.f_91081_ instanceof LoadingOverlay) {
            this.f_91024_ = completablefuture;
            return completablefuture;
         } else {
            this.f_91038_.m_10506_();
            List<PackResources> list = this.f_91038_.m_10525_();
            if (!p_168020_) {
               this.f_167847_.m_168557_(ResourceLoadStateTracker.ReloadReason.MANUAL, list);
            }

            this.m_91150_(new LoadingOverlay(this, this.f_91036_.m_142463_(Util.m_137578_(), this, f_90983_, list), (p_91252_) -> {
               Util.m_137521_(p_91252_, this::m_91239_, () -> {
                  this.f_91060_.m_109818_();
                  this.f_167847_.m_168556_();
                  completablefuture.complete((Void)null);
               });
            }, true));
            return completablefuture;
         }
      }
   }

   private void m_91273_() {
      boolean flag = false;
      BlockModelShaper blockmodelshaper = this.m_91289_().m_110907_();
      BakedModel bakedmodel = blockmodelshaper.m_110881_().m_119409_();

      for(Block block : Registry.f_122824_) {
         for(BlockState blockstate : block.m_49965_().m_61056_()) {
            if (blockstate.m_60799_() == RenderShape.MODEL) {
               BakedModel bakedmodel1 = blockmodelshaper.m_110893_(blockstate);
               if (bakedmodel1 == bakedmodel) {
                  f_90982_.debug("Missing model for: {}", (Object)blockstate);
                  flag = true;
               }
            }
         }
      }

      TextureAtlasSprite textureatlassprite1 = bakedmodel.m_6160_();

      for(Block block1 : Registry.f_122824_) {
         for(BlockState blockstate1 : block1.m_49965_().m_61056_()) {
            TextureAtlasSprite textureatlassprite = blockmodelshaper.m_110882_(blockstate1);
            if (!blockstate1.m_60795_() && textureatlassprite == textureatlassprite1) {
               f_90982_.debug("Missing particle icon for: {}", (Object)blockstate1);
               flag = true;
            }
         }
      }

      NonNullList<ItemStack> nonnulllist = NonNullList.m_122779_();

      for(Item item : Registry.f_122827_) {
         nonnulllist.clear();
         item.m_6787_(CreativeModeTab.f_40754_, nonnulllist);

         for(ItemStack itemstack : nonnulllist) {
            String s = itemstack.m_41778_();
            String s1 = (new TranslatableComponent(s)).getString();
            if (s1.toLowerCase(Locale.ROOT).equals(item.m_5524_())) {
               f_90982_.debug("Missing translation for: {} {} {}", itemstack, s, itemstack.m_41720_());
            }
         }
      }

      flag = flag | MenuScreens.m_96198_();
      flag = flag | EntityRenderers.m_174035_();
      if (flag) {
         throw new IllegalStateException("Your game data is foobar, fix the errors above!");
      }
   }

   public LevelStorageSource m_91392_() {
      return this.f_91031_;
   }

   private void m_91326_(String p_91327_) {
      Minecraft.ChatStatus minecraft$chatstatus = this.m_168022_();
      if (!minecraft$chatstatus.m_142594_(this.m_91090_())) {
         this.f_91065_.m_93063_(minecraft$chatstatus.m_168034_(), false);
      } else {
         this.m_91152_(new ChatScreen(p_91327_));
      }

   }

   public void m_91152_(@Nullable Screen p_91153_) {
      if (SharedConstants.f_136183_ && Thread.currentThread() != this.f_91018_) {
         f_90982_.error("setScreen called from non-game thread");
      }

      if (p_91153_ == null && this.f_91073_ == null) {
         p_91153_ = new TitleScreen();
      } else if (p_91153_ == null && this.f_91074_.m_21224_()) {
         if (this.f_91074_.m_108632_()) {
            p_91153_ = new DeathScreen((Component)null, this.f_91073_.m_6106_().m_5466_());
         } else {
            this.f_91074_.m_7583_();
         }
      }

      net.minecraftforge.client.ForgeHooksClient.clearGuiLayers(this);
      Screen old = this.f_91080_;
      net.minecraftforge.client.event.GuiOpenEvent event = new net.minecraftforge.client.event.GuiOpenEvent(p_91153_);
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;

      p_91153_ = event.getGui();
      if (old != null && p_91153_ != old)
         old.m_7861_();

      this.f_91080_ = p_91153_;
      BufferUploader.m_166835_();
      if (p_91153_ != null) {
         this.f_91067_.m_91602_();
         KeyMapping.m_90847_();
         p_91153_.m_6575_(this, this.f_90990_.m_85445_(), this.f_90990_.m_85446_());
         this.f_91079_ = false;
      } else {
         this.f_91043_.m_120407_();
         this.f_91067_.m_91601_();
      }

      this.m_91341_();
   }

   public void m_91150_(@Nullable Overlay p_91151_) {
      this.f_91081_ = p_91151_;
   }

   public void m_91393_() {
      try {
         f_90982_.info("Stopping!");

         try {
            NarratorChatListener.f_93311_.m_93329_();
         } catch (Throwable throwable1) {
         }

         try {
            if (this.f_91073_ != null) {
               this.f_91073_.m_7462_();
            }

            this.m_91399_();
         } catch (Throwable throwable) {
         }

         if (this.f_91080_ != null) {
            this.f_91080_.m_7861_();
         }

         this.close();
      } finally {
         Util.f_137440_ = System::nanoTime;
         if (this.f_91020_ == null) {
            System.exit(0);
         }

      }

   }

   public void close() {
      try {
         this.f_91051_.close();
         this.f_91045_.close();
         this.f_91063_.close();
         this.f_91060_.close();
         this.f_91043_.m_120406_();
         this.f_91038_.close();
         this.f_91061_.m_107301_();
         this.f_91054_.close();
         this.f_91053_.close();
         this.f_90987_.close();
         this.f_91036_.close();
         Util.m_137580_();
      } catch (Throwable throwable) {
         f_90982_.error("Shutdown failure!", throwable);
         throw throwable;
      } finally {
         this.f_90989_.close();
         this.f_90990_.close();
      }

   }

   private void m_91383_(boolean p_91384_) {
      this.f_90990_.m_85403_("Pre render");
      long i = Util.m_137569_();
      if (this.f_90990_.m_85411_()) {
         this.m_91395_();
      }

      if (this.f_91024_ != null && !(this.f_91081_ instanceof LoadingOverlay)) {
         CompletableFuture<Void> completablefuture = this.f_91024_;
         this.f_91024_ = null;
         this.m_91391_().thenRun(() -> {
            completablefuture.complete((Void)null);
         });
      }

      Runnable runnable;
      while((runnable = this.f_91023_.poll()) != null) {
         runnable.run();
      }

      if (p_91384_) {
         int j = this.f_90991_.m_92525_(Util.m_137550_());
         this.f_91026_.m_6180_("scheduledExecutables");
         this.m_18699_();
         this.f_91026_.m_7238_();
         this.f_91026_.m_6180_("tick");

         for(int k = 0; k < Math.min(10, j); ++k) {
            this.f_91026_.m_6174_("clientTick");
            this.m_91398_();
         }

         this.f_91026_.m_7238_();
      }

      this.f_91067_.m_91523_();
      this.f_90990_.m_85403_("Render");
      this.f_91026_.m_6180_("sound");
      this.f_91043_.m_120361_(this.f_91063_.m_109153_());
      this.f_91026_.m_7238_();
      this.f_91026_.m_6180_("render");
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      RenderSystem.m_157182_();
      RenderSystem.m_69421_(16640, f_91002_);
      this.f_91042_.m_83947_(true);
      FogRenderer.m_109017_();
      this.f_91026_.m_6180_("display");
      RenderSystem.m_69493_();
      RenderSystem.m_69481_();
      this.f_91026_.m_7238_();
      if (!this.f_91079_) {
         net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onRenderTickStart(this.f_91012_ ? this.f_91013_ : this.f_90991_.f_92518_);
         this.f_91026_.m_6182_("gameRenderer");
         this.f_91063_.m_109093_(this.f_91012_ ? this.f_91013_ : this.f_90991_.f_92518_, i, p_91384_);
         this.f_91026_.m_6182_("toasts");
         this.f_91003_.m_94920_(new PoseStack());
         this.f_91026_.m_7238_();
         net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onRenderTickEnd(this.f_91012_ ? this.f_91013_ : this.f_90991_.f_92518_);
      }

      if (this.f_91056_ != null) {
         this.f_91026_.m_6180_("fpsPie");
         this.m_91140_(new PoseStack(), this.f_91056_);
         this.f_91026_.m_7238_();
      }

      this.f_91026_.m_6180_("blit");
      this.f_91042_.m_83970_();
      posestack.m_85849_();
      posestack.m_85836_();
      RenderSystem.m_157182_();
      this.f_91042_.m_83938_(this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
      posestack.m_85849_();
      RenderSystem.m_157182_();
      this.f_91026_.m_6182_("updateDisplay");
      this.f_90990_.m_85435_();
      int i1 = this.m_91275_();
      if ((double)i1 < Option.f_91670_.m_92235_()) {
         RenderSystem.m_69830_(i1);
      }

      this.f_91026_.m_6182_("yield");
      Thread.yield();
      this.f_91026_.m_7238_();
      this.f_90990_.m_85403_("Post render");
      ++this.f_91016_;
      boolean flag = this.m_91091_() && (this.f_91080_ != null && this.f_91080_.m_7043_() || this.f_91081_ != null && this.f_91081_.m_7859_()) && !this.f_91007_.m_6992_();
      if (this.f_91012_ != flag) {
         if (this.f_91012_) {
            this.f_91013_ = this.f_90991_.f_92518_;
         } else {
            this.f_90991_.f_92518_ = this.f_91013_;
         }

         this.f_91012_ = flag;
      }

      long l = Util.m_137569_();
      this.f_91070_.m_13755_(l - this.f_91014_);
      this.f_91014_ = l;
      this.f_91026_.m_6180_("fpsUpdate");

      while(Util.m_137550_() >= this.f_91015_ + 1000L) {
         f_91021_ = this.f_91016_;
         this.f_90977_ = String.format("%d fps T: %s%s%s%s B: %d", f_91021_, (double)this.f_91066_.f_92113_ == Option.f_91670_.m_92235_() ? "inf" : this.f_91066_.f_92113_, this.f_91066_.f_92041_ ? " vsync" : "", this.f_91066_.f_92115_.toString(), this.f_91066_.f_92114_ == CloudStatus.OFF ? "" : (this.f_91066_.f_92114_ == CloudStatus.FAST ? " fast-clouds" : " fancy-clouds"), this.f_91066_.f_92032_);
         this.f_91015_ += 1000L;
         this.f_91016_ = 0;
         this.f_90992_.m_19226_();
         if (!this.f_90992_.m_19230_()) {
            this.f_90992_.m_19222_();
         }
      }

      this.f_91026_.m_7238_();
   }

   private boolean m_91274_() {
      return this.f_91066_.f_92063_ && this.f_91066_.f_92064_ && !this.f_91066_.f_92062_;
   }

   private ProfilerFiller m_167970_(boolean p_167971_, @Nullable SingleTickProfiler p_167972_) {
      if (!p_167971_) {
         this.f_91028_.m_18437_();
         if (!this.f_167846_.m_142763_() && p_167972_ == null) {
            return InactiveProfiler.f_18554_;
         }
      }

      ProfilerFiller profilerfiller;
      if (p_167971_) {
         if (!this.f_91028_.m_18436_()) {
            this.f_91027_ = 0;
            this.f_91028_.m_18438_();
         }

         ++this.f_91027_;
         profilerfiller = this.f_91028_.m_18439_();
      } else {
         profilerfiller = InactiveProfiler.f_18554_;
      }

      if (this.f_167846_.m_142763_()) {
         profilerfiller = ProfilerFiller.m_18578_(profilerfiller, this.f_167846_.m_142610_());
      }

      return SingleTickProfiler.m_18629_(profilerfiller, p_167972_);
   }

   private void m_91338_(boolean p_91339_, @Nullable SingleTickProfiler p_91340_) {
      if (p_91340_ != null) {
         p_91340_.m_18634_();
      }

      if (p_91339_) {
         this.f_91056_ = this.f_91028_.m_18440_();
      } else {
         this.f_91056_ = null;
      }

      this.f_91026_ = this.f_91028_.m_18439_();
   }

   public void m_5741_() {
      int i = this.f_90990_.m_85385_(this.f_91066_.f_92072_, this.m_91390_());
      this.f_90990_.m_85378_((double)i);
      if (this.f_91080_ != null) {
         this.f_91080_.m_6574_(this, this.f_90990_.m_85445_(), this.f_90990_.m_85446_());
         net.minecraftforge.client.ForgeHooksClient.resizeGuiLayers(this, this.f_90990_.m_85445_(), this.f_90990_.m_85446_());
      }

      RenderTarget rendertarget = this.m_91385_();
      rendertarget.m_83941_(this.f_90990_.m_85441_(), this.f_90990_.m_85442_(), f_91002_);
      if (this.f_91063_ != null)
      this.f_91063_.m_109097_(this.f_90990_.m_85441_(), this.f_90990_.m_85442_());
      this.f_91067_.m_91599_();
   }

   public void m_5740_() {
      this.f_91067_.m_91603_();
   }

   private int m_91275_() {
      return this.f_91073_ != null || this.f_91080_ == null && this.f_91081_ == null ? this.f_90990_.m_85434_() : 60;
   }

   public void m_91394_() {
      try {
         MemoryReserve.m_182328_();
         this.f_91060_.m_109824_();
      } catch (Throwable throwable1) {
      }

      try {
         System.gc();
         if (this.f_91010_ && this.f_91007_ != null) {
            this.f_91007_.m_7570_(true);
         }

         this.m_91320_(new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")));
      } catch (Throwable throwable) {
      }

      System.gc();
   }

   public boolean m_167946_(Consumer<TranslatableComponent> p_167947_) {
      if (this.f_167846_.m_142763_()) {
         this.m_167975_();
         return false;
      } else {
         Consumer<ProfileResults> consumer = (p_167993_) -> {
            int i = p_167993_.m_7315_();
            double d0 = (double)p_167993_.m_18577_() / (double)TimeUtil.f_145016_;
            this.execute(() -> {
               p_167947_.accept(new TranslatableComponent("commands.debug.stopped", String.format(Locale.ROOT, "%.2f", d0), i, String.format(Locale.ROOT, "%.2f", (double)i / d0)));
            });
         };
         Consumer<Path> consumer1 = (p_167996_) -> {
            Component component = (new TextComponent(p_167996_.toString())).m_130940_(ChatFormatting.UNDERLINE).m_130938_((p_167943_) -> {
               return p_167943_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, p_167996_.toFile().getParent()));
            });
            this.execute(() -> {
               p_167947_.accept(new TranslatableComponent("debug.profiling.stop", component));
            });
         };
         SystemReport systemreport = m_167850_(new SystemReport(), this, this.f_91039_, this.f_91001_, this.f_91066_);
         Consumer<List<Path>> consumer2 = (p_167862_) -> {
            Path path = this.m_167856_(systemreport, p_167862_);
            consumer1.accept(path);
         };
         Consumer<Path> consumer3;
         if (this.f_91007_ == null) {
            consumer3 = (p_167957_) -> {
               consumer2.accept(ImmutableList.of(p_167957_));
            };
         } else {
            this.f_91007_.m_177935_(systemreport);
            CompletableFuture<Path> completablefuture = new CompletableFuture<>();
            CompletableFuture<Path> completablefuture1 = new CompletableFuture<>();
            CompletableFuture.allOf(completablefuture, completablefuture1).thenRunAsync(() -> {
               consumer2.accept(ImmutableList.of(completablefuture.join(), completablefuture1.join()));
            }, Util.m_137579_());
            this.f_91007_.m_177923_((p_167864_) -> {
            }, completablefuture1::complete);
            consumer3 = completablefuture::complete;
         }

         this.f_167846_ = ActiveMetricsRecorder.m_146132_(new ClientMetricsSamplersProvider(Util.f_137440_, this.f_91060_), Util.f_137440_, Util.m_137579_(), new MetricsPersister("client"), (p_167954_) -> {
            this.f_167846_ = InactiveMetricsRecorder.f_146153_;
            consumer.accept(p_167954_);
         }, consumer3);
         return true;
      }
   }

   private void m_167975_() {
      this.f_167846_.m_142760_();
      if (this.f_91007_ != null) {
         this.f_91007_.m_177929_();
      }

   }

   private Path m_167856_(SystemReport p_167857_, List<Path> p_167858_) {
      String s;
      if (this.m_91090_()) {
         s = this.m_91092_().m_129910_().m_5462_();
      } else {
         s = this.m_91089_().f_105362_;
      }

      Path path;
      try {
         String s1 = String.format("%s-%s-%s", (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()), s, SharedConstants.m_136187_().getId());
         String s2 = FileUtil.m_133730_(MetricsPersister.f_146209_, s1, ".zip");
         path = MetricsPersister.f_146209_.resolve(s2);
      } catch (IOException ioexception1) {
         throw new UncheckedIOException(ioexception1);
      }

      try {
         FileZipper filezipper = new FileZipper(path);

         try {
            filezipper.m_144703_(Paths.get("system.txt"), p_167857_.m_143515_());
            filezipper.m_144703_(Paths.get("client").resolve(this.f_91066_.m_168450_().getName()), this.f_91066_.m_168451_());
            p_167858_.forEach(filezipper::m_144698_);
         } catch (Throwable throwable1) {
            try {
               filezipper.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }

            throw throwable1;
         }

         filezipper.close();
      } finally {
         for(Path path1 : p_167858_) {
            try {
               FileUtils.forceDelete(path1.toFile());
            } catch (IOException ioexception) {
               f_90982_.warn("Failed to delete temporary profiling result {}", path1, ioexception);
            }
         }

      }

      return path;
   }

   public void m_91111_(int p_91112_) {
      if (this.f_91056_ != null) {
         List<ResultField> list = this.f_91056_.m_6412_(this.f_91057_);
         if (!list.isEmpty()) {
            ResultField resultfield = list.remove(0);
            if (p_91112_ == 0) {
               if (!resultfield.f_18610_.isEmpty()) {
                  int i = this.f_91057_.lastIndexOf(30);
                  if (i >= 0) {
                     this.f_91057_ = this.f_91057_.substring(0, i);
                  }
               }
            } else {
               --p_91112_;
               if (p_91112_ < list.size() && !"unspecified".equals((list.get(p_91112_)).f_18610_)) {
                  if (!this.f_91057_.isEmpty()) {
                     this.f_91057_ = this.f_91057_ + "\u001e";
                  }

                  this.f_91057_ = this.f_91057_ + (list.get(p_91112_)).f_18610_;
               }
            }

         }
      }
   }

   private void m_91140_(PoseStack p_91141_, ProfileResults p_91142_) {
      List<ResultField> list = p_91142_.m_6412_(this.f_91057_);
      ResultField resultfield = list.remove(0);
      RenderSystem.m_69421_(256, f_91002_);
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      Matrix4f matrix4f = Matrix4f.m_162203_(0.0F, (float)this.f_90990_.m_85441_(), 0.0F, (float)this.f_90990_.m_85442_(), 1000.0F, 3000.0F);
      RenderSystem.m_157425_(matrix4f);
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_166856_();
      posestack.m_85837_(0.0D, 0.0D, -2000.0D);
      RenderSystem.m_157182_();
      RenderSystem.m_69832_(1.0F);
      RenderSystem.m_69472_();
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      int i = 160;
      int j = this.f_90990_.m_85441_() - 160 - 10;
      int k = this.f_90990_.m_85442_() - 320;
      RenderSystem.m_69478_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
      bufferbuilder.m_5483_((double)((float)j - 176.0F), (double)((float)k - 96.0F - 16.0F), 0.0D).m_6122_(200, 0, 0, 0).m_5752_();
      bufferbuilder.m_5483_((double)((float)j - 176.0F), (double)(k + 320), 0.0D).m_6122_(200, 0, 0, 0).m_5752_();
      bufferbuilder.m_5483_((double)((float)j + 176.0F), (double)(k + 320), 0.0D).m_6122_(200, 0, 0, 0).m_5752_();
      bufferbuilder.m_5483_((double)((float)j + 176.0F), (double)((float)k - 96.0F - 16.0F), 0.0D).m_6122_(200, 0, 0, 0).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69461_();
      double d0 = 0.0D;

      for(ResultField resultfield1 : list) {
         int l = Mth.m_14107_(resultfield1.f_18607_ / 4.0D) + 1;
         bufferbuilder.m_166779_(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.f_85815_);
         int i1 = resultfield1.m_18616_();
         int j1 = i1 >> 16 & 255;
         int k1 = i1 >> 8 & 255;
         int l1 = i1 & 255;
         bufferbuilder.m_5483_((double)j, (double)k, 0.0D).m_6122_(j1, k1, l1, 255).m_5752_();

         for(int i2 = l; i2 >= 0; --i2) {
            float f = (float)((d0 + resultfield1.f_18607_ * (double)i2 / (double)l) * (double)((float)Math.PI * 2F) / 100.0D);
            float f1 = Mth.m_14031_(f) * 160.0F;
            float f2 = Mth.m_14089_(f) * 160.0F * 0.5F;
            bufferbuilder.m_5483_((double)((float)j + f1), (double)((float)k - f2), 0.0D).m_6122_(j1, k1, l1, 255).m_5752_();
         }

         tesselator.m_85914_();
         bufferbuilder.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);

         for(int l2 = l; l2 >= 0; --l2) {
            float f3 = (float)((d0 + resultfield1.f_18607_ * (double)l2 / (double)l) * (double)((float)Math.PI * 2F) / 100.0D);
            float f4 = Mth.m_14031_(f3) * 160.0F;
            float f5 = Mth.m_14089_(f3) * 160.0F * 0.5F;
            if (!(f5 > 0.0F)) {
               bufferbuilder.m_5483_((double)((float)j + f4), (double)((float)k - f5), 0.0D).m_6122_(j1 >> 1, k1 >> 1, l1 >> 1, 255).m_5752_();
               bufferbuilder.m_5483_((double)((float)j + f4), (double)((float)k - f5 + 10.0F), 0.0D).m_6122_(j1 >> 1, k1 >> 1, l1 >> 1, 255).m_5752_();
            }
         }

         tesselator.m_85914_();
         d0 += resultfield1.f_18607_;
      }

      DecimalFormat decimalformat = new DecimalFormat("##0.00");
      decimalformat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
      RenderSystem.m_69493_();
      String s = ProfileResults.m_18575_(resultfield.f_18610_);
      String s1 = "";
      if (!"unspecified".equals(s)) {
         s1 = s1 + "[0] ";
      }

      if (s.isEmpty()) {
         s1 = s1 + "ROOT ";
      } else {
         s1 = s1 + s + " ";
      }

      int k2 = 16777215;
      this.f_91062_.m_92750_(p_91141_, s1, (float)(j - 160), (float)(k - 80 - 16), 16777215);
      s1 = decimalformat.format(resultfield.f_18608_) + "%";
      this.f_91062_.m_92750_(p_91141_, s1, (float)(j + 160 - this.f_91062_.m_92895_(s1)), (float)(k - 80 - 16), 16777215);

      for(int j2 = 0; j2 < list.size(); ++j2) {
         ResultField resultfield2 = list.get(j2);
         StringBuilder stringbuilder = new StringBuilder();
         if ("unspecified".equals(resultfield2.f_18610_)) {
            stringbuilder.append("[?] ");
         } else {
            stringbuilder.append("[").append(j2 + 1).append("] ");
         }

         String s2 = stringbuilder.append(resultfield2.f_18610_).toString();
         this.f_91062_.m_92750_(p_91141_, s2, (float)(j - 160), (float)(k + 80 + j2 * 8 + 20), resultfield2.m_18616_());
         s2 = decimalformat.format(resultfield2.f_18607_) + "%";
         this.f_91062_.m_92750_(p_91141_, s2, (float)(j + 160 - 50 - this.f_91062_.m_92895_(s2)), (float)(k + 80 + j2 * 8 + 20), resultfield2.m_18616_());
         s2 = decimalformat.format(resultfield2.f_18608_) + "%";
         this.f_91062_.m_92750_(p_91141_, s2, (float)(j + 160 - this.f_91062_.m_92895_(s2)), (float)(k + 80 + j2 * 8 + 20), resultfield2.m_18616_());
      }

   }

   public void m_91395_() {
      this.f_91019_ = false;
   }

   public boolean m_91396_() {
      return this.f_91019_;
   }

   public void m_91358_(boolean p_91359_) {
      if (this.f_91080_ == null) {
         boolean flag = this.m_91091_() && !this.f_91007_.m_6992_();
         if (flag) {
            this.m_91152_(new PauseScreen(!p_91359_));
            this.f_91043_.m_120391_();
         } else {
            this.m_91152_(new PauseScreen(true));
         }

      }
   }

   private void m_91386_(boolean p_91387_) {
      if (!p_91387_) {
         this.f_91078_ = 0;
      }

      if (this.f_91078_ <= 0 && !this.f_91074_.m_6117_()) {
         if (p_91387_ && this.f_91077_ != null && this.f_91077_.m_6662_() == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)this.f_91077_;
            BlockPos blockpos = blockhitresult.m_82425_();
            if (!this.f_91073_.m_46859_(blockpos)) {
               net.minecraftforge.client.event.InputEvent.ClickInputEvent inputEvent = net.minecraftforge.client.ForgeHooksClient.onClickInput(0, this.f_91066_.f_92096_, InteractionHand.MAIN_HAND);
               if (inputEvent.isCanceled()) {
                  if (inputEvent.shouldSwingHand()) {
                     this.f_91061_.addBlockHitEffects(blockpos, blockhitresult);
                     this.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
                  }
                  return;
               }
               Direction direction = blockhitresult.m_82434_();
               if (this.f_91072_.m_105283_(blockpos, direction)) {
                  if (inputEvent.shouldSwingHand()) {
                  this.f_91061_.addBlockHitEffects(blockpos, blockhitresult);
                  this.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
                  }
               }
            }

         } else {
            this.f_91072_.m_105276_();
         }
      }
   }

   private void m_91276_() {
      if (this.f_91078_ <= 0) {
         if (this.f_91077_ == null) {
            f_90982_.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.f_91072_.m_105289_()) {
               this.f_91078_ = 10;
            }

         } else if (!this.f_91074_.m_108637_()) {
            net.minecraftforge.client.event.InputEvent.ClickInputEvent inputEvent = net.minecraftforge.client.ForgeHooksClient.onClickInput(0, this.f_91066_.f_92096_, InteractionHand.MAIN_HAND);
            if (!inputEvent.isCanceled())
            switch(this.f_91077_.m_6662_()) {
            case ENTITY:
               this.f_91072_.m_105223_(this.f_91074_, ((EntityHitResult)this.f_91077_).m_82443_());
               break;
            case BLOCK:
               BlockHitResult blockhitresult = (BlockHitResult)this.f_91077_;
               BlockPos blockpos = blockhitresult.m_82425_();
               if (!this.f_91073_.m_46859_(blockpos)) {
                  this.f_91072_.m_105269_(blockpos, blockhitresult.m_82434_());
                  break;
               }
            case MISS:
               if (this.f_91072_.m_105289_()) {
                  this.f_91078_ = 10;
               }

               this.f_91074_.m_36334_();
               net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(this.f_91074_);
            }

            if (inputEvent.shouldSwingHand())
            this.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
         }
      }
   }

   private void m_91277_() {
      if (!this.f_91072_.m_105296_()) {
         this.f_91011_ = 4;
         if (!this.f_91074_.m_108637_()) {
            if (this.f_91077_ == null) {
               f_90982_.warn("Null returned as 'hitResult', this shouldn't happen!");
            }

            for(InteractionHand interactionhand : InteractionHand.values()) {
               net.minecraftforge.client.event.InputEvent.ClickInputEvent inputEvent = net.minecraftforge.client.ForgeHooksClient.onClickInput(1, this.f_91066_.f_92095_, interactionhand);
               if (inputEvent.isCanceled()) {
                  if (inputEvent.shouldSwingHand()) this.f_91074_.m_6674_(interactionhand);
                  return;
               }
               ItemStack itemstack = this.f_91074_.m_21120_(interactionhand);
               if (this.f_91077_ != null) {
                  switch(this.f_91077_.m_6662_()) {
                  case ENTITY:
                     EntityHitResult entityhitresult = (EntityHitResult)this.f_91077_;
                     Entity entity = entityhitresult.m_82443_();
                     InteractionResult interactionresult = this.f_91072_.m_105230_(this.f_91074_, entity, entityhitresult, interactionhand);
                     if (!interactionresult.m_19077_()) {
                        interactionresult = this.f_91072_.m_105226_(this.f_91074_, entity, interactionhand);
                     }

                     if (interactionresult.m_19077_()) {
                        if (interactionresult.m_19080_()) {
                           if (inputEvent.shouldSwingHand())
                           this.f_91074_.m_6674_(interactionhand);
                        }

                        return;
                     }
                     break;
                  case BLOCK:
                     BlockHitResult blockhitresult = (BlockHitResult)this.f_91077_;
                     int i = itemstack.m_41613_();
                     InteractionResult interactionresult1 = this.f_91072_.m_105262_(this.f_91074_, this.f_91073_, interactionhand, blockhitresult);
                     if (interactionresult1.m_19077_()) {
                        if (interactionresult1.m_19080_()) {
                           if (inputEvent.shouldSwingHand())
                           this.f_91074_.m_6674_(interactionhand);
                           if (!itemstack.m_41619_() && (itemstack.m_41613_() != i || this.f_91072_.m_105290_())) {
                              this.f_91063_.f_109055_.m_109320_(interactionhand);
                           }
                        }

                        return;
                     }

                     if (interactionresult1 == InteractionResult.FAIL) {
                        return;
                     }
                  }
               }

               if (itemstack.m_41619_() && (this.f_91077_ == null || this.f_91077_.m_6662_() == HitResult.Type.MISS))
                  net.minecraftforge.common.ForgeHooks.onEmptyClick(this.f_91074_, interactionhand);

               if (!itemstack.m_41619_()) {
                  InteractionResult interactionresult2 = this.f_91072_.m_105235_(this.f_91074_, this.f_91073_, interactionhand);
                  if (interactionresult2.m_19077_()) {
                     if (interactionresult2.m_19080_()) {
                        this.f_91074_.m_6674_(interactionhand);
                     }

                     this.f_91063_.f_109055_.m_109320_(interactionhand);
                     return;
                  }
               }
            }

         }
      }
   }

   public MusicManager m_91397_() {
      return this.f_91044_;
   }

   public void m_91398_() {
      if (this.f_91011_ > 0) {
         --this.f_91011_;
      }

      net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPreClientTick();

      this.f_91026_.m_6180_("gui");
      if (!this.f_91012_) {
         this.f_91065_.m_93066_();
      }

      this.f_91026_.m_7238_();
      this.f_91063_.m_109087_(1.0F);
      this.f_91005_.m_120578_(this.f_91073_, this.f_91077_);
      this.f_91026_.m_6180_("gameMode");
      if (!this.f_91012_ && this.f_91073_ != null) {
         this.f_91072_.m_105287_();
      }

      this.f_91026_.m_6182_("textures");
      if (this.f_91073_ != null) {
         this.f_90987_.m_7673_();
      }

      if (this.f_91080_ == null && this.f_91074_ != null) {
         if (this.f_91074_.m_21224_() && !(this.f_91080_ instanceof DeathScreen)) {
            this.m_91152_((Screen)null);
         } else if (this.f_91074_.m_5803_() && this.f_91073_ != null) {
            this.m_91152_(new InBedChatScreen());
         }
      } else if (this.f_91080_ != null && this.f_91080_ instanceof InBedChatScreen && !this.f_91074_.m_5803_()) {
         this.m_91152_((Screen)null);
      }

      if (this.f_91080_ != null) {
         this.f_91078_ = 10000;
      }

      if (this.f_91080_ != null) {
         Screen.m_96579_(() -> {
            this.f_91080_.m_96624_();
         }, "Ticking screen", this.f_91080_.getClass().getCanonicalName());
      }

      if (!this.f_91066_.f_92063_) {
         this.f_91065_.m_93091_();
      }

      if (this.f_91081_ == null && (this.f_91080_ == null || this.f_91080_.f_96546_)) {
         this.f_91026_.m_6182_("Keybindings");
         this.m_91279_();
         if (this.f_91078_ > 0) {
            --this.f_91078_;
         }
      }

      if (this.f_91073_ != null) {
         this.f_91026_.m_6182_("gameRenderer");
         if (!this.f_91012_) {
            this.f_91063_.m_109148_();
         }

         this.f_91026_.m_6182_("levelRenderer");
         if (!this.f_91012_) {
            this.f_91060_.m_109823_();
         }

         this.f_91026_.m_6182_("level");
         if (!this.f_91012_) {
            if (this.f_91073_.m_104819_() > 0) {
               this.f_91073_.m_6580_(this.f_91073_.m_104819_() - 1);
            }

            this.f_91073_.m_104804_();
         }
      } else if (this.f_91063_.m_109149_() != null) {
         this.f_91063_.m_109086_();
      }

      if (!this.f_91012_) {
         this.f_91044_.m_120183_();
      }

      this.f_91043_.m_120389_(this.f_91012_);
      if (this.f_91073_ != null) {
         if (!this.f_91012_) {
            if (!this.f_91066_.f_92031_ && this.m_91278_()) {
               Component component = new TranslatableComponent("tutorial.socialInteractions.title");
               Component component1 = new TranslatableComponent("tutorial.socialInteractions.description", Tutorial.m_120592_("socialInteractions"));
               this.f_91025_ = new TutorialToast(TutorialToast.Icons.SOCIAL_INTERACTIONS, component, component1, true);
               this.f_91005_.m_120572_(this.f_91025_, 160);
               this.f_91066_.f_92031_ = true;
               this.f_91066_.m_92169_();
            }

            this.f_91005_.m_120596_();

            try {
               this.f_91073_.m_104726_(() -> {
                  return true;
               });
            } catch (Throwable throwable) {
               CrashReport crashreport = CrashReport.m_127521_(throwable, "Exception in world tick");
               if (this.f_91073_ == null) {
                  CrashReportCategory crashreportcategory = crashreport.m_127514_("Affected level");
                  crashreportcategory.m_128159_("Problem", "Level is null!");
               } else {
                  this.f_91073_.m_6026_(crashreport);
               }

               throw new ReportedException(crashreport);
            }
         }

         this.f_91026_.m_6182_("animateTick");
         if (!this.f_91012_ && this.f_91073_ != null) {
            this.f_91073_.m_104784_(this.f_91074_.m_146903_(), this.f_91074_.m_146904_(), this.f_91074_.m_146907_());
         }

         this.f_91026_.m_6182_("particles");
         if (!this.f_91012_) {
            this.f_91061_.m_107388_();
         }
      } else if (this.f_91009_ != null) {
         this.f_91026_.m_6182_("pendingConnection");
         this.f_91009_.m_129483_();
      }

      this.f_91026_.m_6182_("keyboard");
      this.f_91068_.m_90931_();
      this.f_91026_.m_7238_();

      net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPostClientTick();
   }

   private boolean m_91278_() {
      return !this.f_91010_ || this.f_91007_ != null && this.f_91007_.m_6992_();
   }

   private void m_91279_() {
      for(; this.f_91066_.f_92103_.m_90859_(); this.f_91060_.m_109826_()) {
         CameraType cameratype = this.f_91066_.m_92176_();
         this.f_91066_.m_92157_(this.f_91066_.m_92176_().m_90614_());
         if (cameratype.m_90612_() != this.f_91066_.m_92176_().m_90612_()) {
            this.f_91063_.m_109106_(this.f_91066_.m_92176_().m_90612_() ? this.m_91288_() : null);
         }
      }

      while(this.f_91066_.f_92104_.m_90859_()) {
         this.f_91066_.f_92067_ = !this.f_91066_.f_92067_;
      }

      for(int i = 0; i < 9; ++i) {
         boolean flag = this.f_91066_.f_92057_.m_90857_();
         boolean flag1 = this.f_91066_.f_92058_.m_90857_();
         if (this.f_91066_.f_92056_[i].m_90859_()) {
            if (this.f_91074_.m_5833_()) {
               this.f_91065_.m_93085_().m_94771_(i);
            } else if (!this.f_91074_.m_7500_() || this.f_91080_ != null || !flag1 && !flag) {
               this.f_91074_.m_150109_().f_35977_ = i;
            } else {
               CreativeModeInventoryScreen.m_98598_(this, i, flag1, flag);
            }
         }
      }

      while(this.f_91066_.f_92101_.m_90859_()) {
         if (!this.m_91278_()) {
            this.f_91074_.m_5661_(f_90984_, true);
            NarratorChatListener.f_93311_.m_168785_(f_90984_);
         } else {
            if (this.f_91025_ != null) {
               this.f_91005_.m_120570_(this.f_91025_);
               this.f_91025_ = null;
            }

            this.m_91152_(new SocialInteractionsScreen());
         }
      }

      while(this.f_91066_.f_92092_.m_90859_()) {
         if (this.f_91072_.m_105292_()) {
            this.f_91074_.m_108628_();
         } else {
            this.f_91005_.m_120564_();
            this.m_91152_(new InventoryScreen(this.f_91074_));
         }
      }

      while(this.f_91066_.f_92055_.m_90859_()) {
         this.m_91152_(new AdvancementsScreen(this.f_91074_.f_108617_.m_105145_()));
      }

      while(this.f_91066_.f_92093_.m_90859_()) {
         if (!this.f_91074_.m_5833_()) {
            this.m_91403_().m_104955_(new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.f_121853_, Direction.DOWN));
         }
      }

      while(this.f_91066_.f_92094_.m_90859_()) {
         if (!this.f_91074_.m_5833_() && this.f_91074_.m_108700_(Screen.m_96637_())) {
            this.f_91074_.m_6674_(InteractionHand.MAIN_HAND);
         }
      }

      while(this.f_91066_.f_92098_.m_90859_()) {
         this.m_91326_("");
      }

      if (this.f_91080_ == null && this.f_91081_ == null && this.f_91066_.f_92100_.m_90859_()) {
         this.m_91326_("/");
      }

      if (this.f_91074_.m_6117_()) {
         if (!this.f_91066_.f_92095_.m_90857_()) {
            this.f_91072_.m_105277_(this.f_91074_);
         }

         while(this.f_91066_.f_92096_.m_90859_()) {
         }

         while(this.f_91066_.f_92095_.m_90859_()) {
         }

         while(this.f_91066_.f_92097_.m_90859_()) {
         }
      } else {
         while(this.f_91066_.f_92096_.m_90859_()) {
            this.m_91276_();
         }

         while(this.f_91066_.f_92095_.m_90859_()) {
            this.m_91277_();
         }

         while(this.f_91066_.f_92097_.m_90859_()) {
            this.m_91280_();
         }
      }

      if (this.f_91066_.f_92095_.m_90857_() && this.f_91011_ == 0 && !this.f_91074_.m_6117_()) {
         this.m_91277_();
      }

      this.m_91386_(this.f_91080_ == null && this.f_91066_.f_92096_.m_90857_() && this.f_91067_.m_91600_());
   }

   public static DataPackConfig m_91133_(LevelStorageSource.LevelStorageAccess p_91134_) {
      MinecraftServer.m_129845_(p_91134_);
      DataPackConfig datapackconfig = p_91134_.m_78309_();
      if (datapackconfig == null) {
         throw new IllegalStateException("Failed to load data pack config");
      } else {
         return datapackconfig;
      }
   }

   public static WorldData m_91135_(LevelStorageSource.LevelStorageAccess p_91136_, RegistryAccess.RegistryHolder p_91137_, ResourceManager p_91138_, DataPackConfig p_91139_) {
      RegistryReadOps<Tag> registryreadops = RegistryReadOps.m_179866_(NbtOps.f_128958_, p_91138_, p_91137_);
      WorldData worlddata = p_91136_.m_78280_(registryreadops, p_91139_);
      if (worlddata == null) {
         throw new IllegalStateException("Failed to load world");
      } else {
         return worlddata;
      }
   }

   public void m_91200_(String p_91201_) {
      this.doLoadLevel(p_91201_, RegistryAccess.m_123086_(), Minecraft::m_91133_, Minecraft::m_91135_, false, Minecraft.ExperimentalDialogType.BACKUP, false);
   }

   public void m_91202_(String p_91203_, LevelSettings p_91204_, RegistryAccess.RegistryHolder p_91205_, WorldGenSettings p_91206_) {
      this.doLoadLevel(p_91203_, p_91205_, (p_91129_) -> {
         return p_91204_.m_46934_();
      }, (p_167886_, p_167887_, p_167888_, p_167889_) -> {
         RegistryWriteOps<JsonElement> registrywriteops = RegistryWriteOps.m_135767_(JsonOps.INSTANCE, p_91205_);
         RegistryReadOps<JsonElement> registryreadops = RegistryReadOps.m_179866_(JsonOps.INSTANCE, p_167888_, p_91205_);
         DataResult<WorldGenSettings> dataresult = WorldGenSettings.f_64600_.encodeStart(registrywriteops, p_91206_).setLifecycle(Lifecycle.stable()).flatMap((p_167969_) -> {
            return WorldGenSettings.f_64600_.parse(registryreadops, p_167969_);
         });
         WorldGenSettings worldgensettings = dataresult.resultOrPartial(Util.m_137489_("Error reading worldgen settings after loading data packs: ", f_90982_::error)).orElse(p_91206_);
         return new PrimaryLevelData(p_91204_, worldgensettings, dataresult.lifecycle());
      }, false, Minecraft.ExperimentalDialogType.CREATE, true);
   }

   private void doLoadLevel(String p_91220_, RegistryAccess.RegistryHolder p_91221_, Function<LevelStorageSource.LevelStorageAccess, DataPackConfig> p_91222_, Function4<LevelStorageSource.LevelStorageAccess, RegistryAccess.RegistryHolder, ResourceManager, DataPackConfig, WorldData> p_91223_, boolean p_91224_, Minecraft.ExperimentalDialogType p_91225_, boolean creating) {
      LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess;
      try {
         levelstoragesource$levelstorageaccess = this.f_91031_.m_78260_(p_91220_);
      } catch (IOException ioexception2) {
         f_90982_.warn("Failed to read level {} data", p_91220_, ioexception2);
         SystemToast.m_94852_(this, p_91220_);
         this.m_91152_((Screen)null);
         return;
      }

      Minecraft.ServerStem minecraft$serverstem;
      final RegistryAccess.RegistryHolder dyn_f;
      try {
         Minecraft.ServerStem mgr = this.m_91190_(p_91221_, p_91222_, p_91223_, p_91224_, levelstoragesource$levelstorageaccess);
         // We need to rebuild this if we're loading world, so that it gets all the information from AFTER we inject our mappings from the save.
         dyn_f = creating ? p_91221_ : RegistryAccess.m_123086_();
         minecraft$serverstem = creating ? mgr : this.m_91190_(dyn_f, p_91222_, p_91223_, p_91224_, levelstoragesource$levelstorageaccess); //Note this runs the injection again... which isn't good.. but hey.. we need a better spot to hook in.

      } catch (Exception exception) {
         f_90982_.warn("Failed to load datapacks, can't proceed with server load", (Throwable)exception);
         this.m_91152_(new DatapackLoadFailureScreen(() -> {
            this.doLoadLevel(p_91220_, p_91221_, p_91222_, p_91223_, true, p_91225_, creating);
         }));

         try {
            levelstoragesource$levelstorageaccess.close();
         } catch (IOException ioexception) {
            f_90982_.warn("Failed to unlock access to level {}", p_91220_, ioexception);
         }

         return;
      }

      WorldData worlddata = minecraft$serverstem.m_91434_();
      boolean flag = worlddata.m_5961_().m_64670_();
      boolean flag1 = worlddata.m_5754_() != Lifecycle.stable();
      if (p_91225_ == Minecraft.ExperimentalDialogType.NONE || !flag && !flag1) {
         this.m_91399_();
         this.f_90999_.set((StoringChunkProgressListener)null);

         try {
            levelstoragesource$levelstorageaccess.m_78287_(dyn_f, worlddata);
            minecraft$serverstem.m_91433_().m_136179_();
            YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(this.f_91030_);
            MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
            GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
            GameProfileCache gameprofilecache = new GameProfileCache(gameprofilerepository, new File(this.f_91069_, MinecraftServer.f_129742_.getName()));
            gameprofilecache.m_143974_(this);
            SkullBlockEntity.m_59764_(gameprofilecache);
            SkullBlockEntity.m_59771_(minecraftsessionservice);
            SkullBlockEntity.m_182462_(this);
            GameProfileCache.m_11004_(false);
            this.f_91007_ = MinecraftServer.m_129872_((p_167898_) -> {
               return new IntegratedServer(p_167898_, this, dyn_f, levelstoragesource$levelstorageaccess, minecraft$serverstem.m_91432_(), minecraft$serverstem.m_91433_(), worlddata, minecraftsessionservice, gameprofilerepository, gameprofilecache, (p_168000_) -> {
                  StoringChunkProgressListener storingchunkprogresslistener = new StoringChunkProgressListener(p_168000_ + 0);
                  this.f_90999_.set(storingchunkprogresslistener);
                  return ProcessorChunkProgressListener.m_143583_(storingchunkprogresslistener, this.f_91023_::add);
               });
            });
            this.f_91010_ = true;
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.m_127521_(throwable, "Starting integrated server");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Starting integrated server");
            crashreportcategory.m_128159_("Level ID", p_91220_);
            crashreportcategory.m_128159_("Level Name", worlddata.m_5462_());
            throw new ReportedException(crashreport);
         }

         while(this.f_90999_.get() == null) {
            Thread.yield();
         }

         LevelLoadingScreen levelloadingscreen = new LevelLoadingScreen(this.f_90999_.get());
         this.m_91152_(levelloadingscreen);
         this.f_91026_.m_6180_("waitForServer");

         while(!this.f_91007_.m_129920_()) {
            levelloadingscreen.m_96624_();
            this.m_91383_(false);

            try {
               Thread.sleep(16L);
            } catch (InterruptedException interruptedexception) {
            }

            if (this.f_91020_ != null) {
               m_91332_(this.f_91020_);
               return;
            }
         }

         this.f_91026_.m_7238_();
         SocketAddress socketaddress = this.f_91007_.m_129919_().m_9708_();
         Connection connection = Connection.m_129493_(socketaddress);
         connection.m_129505_(new ClientHandshakePacketListenerImpl(connection, this, (Screen)null, (p_167998_) -> {
         }));
         connection.m_129512_(new ClientIntentionPacket(socketaddress.toString(), 0, ConnectionProtocol.LOGIN));
         com.mojang.authlib.GameProfile gameProfile = this.m_91094_().m_92548_();
         if (!this.m_91094_().hasCachedProperties()) {
            gameProfile = f_91048_.fillProfileProperties(gameProfile, true); //Forge: Fill profile properties upon game load. Fixes MC-52974.
            this.m_91094_().setProperties(gameProfile.getProperties());
         }
         connection.m_129512_(new ServerboundHelloPacket(gameProfile));
         this.f_91009_ = connection;
      } else {
         this.m_91143_(p_91225_, p_91220_, flag, () -> {
            this.doLoadLevel(p_91220_, dyn_f, p_91222_, p_91223_, p_91224_, Minecraft.ExperimentalDialogType.NONE, creating);
         });
         minecraft$serverstem.close();

         try {
            levelstoragesource$levelstorageaccess.close();
         } catch (IOException ioexception1) {
            f_90982_.warn("Failed to unlock access to level {}", p_91220_, ioexception1);
         }

      }
   }

   private void m_91143_(Minecraft.ExperimentalDialogType p_91144_, String p_91145_, boolean p_91146_, Runnable p_91147_) {
      if (p_91144_ == Minecraft.ExperimentalDialogType.BACKUP) {
         Component component;
         Component component1;
         if (p_91146_) {
            component = new TranslatableComponent("selectWorld.backupQuestion.customized");
            component1 = new TranslatableComponent("selectWorld.backupWarning.customized");
         } else {
            component = new TranslatableComponent("selectWorld.backupQuestion.experimental");
            component1 = new TranslatableComponent("selectWorld.backupWarning.experimental");
         }

         this.m_91152_(new BackupConfirmScreen((Screen)null, (p_167931_, p_167932_) -> {
            if (p_167931_) {
               EditWorldScreen.m_101260_(this.f_91031_, p_91145_);
            }

            p_91147_.run();
         }, component, component1, false));
      } else {
         this.m_91152_(new ConfirmScreen((p_167915_) -> {
            if (p_167915_) {
               p_91147_.run();
            } else {
               this.m_91152_((Screen)null);

               try {
                  LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_91031_.m_78260_(p_91145_);

                  try {
                     levelstoragesource$levelstorageaccess.m_78311_();
                  } catch (Throwable throwable1) {
                     if (levelstoragesource$levelstorageaccess != null) {
                        try {
                           levelstoragesource$levelstorageaccess.close();
                        } catch (Throwable throwable) {
                           throwable1.addSuppressed(throwable);
                        }
                     }

                     throw throwable1;
                  }

                  if (levelstoragesource$levelstorageaccess != null) {
                     levelstoragesource$levelstorageaccess.close();
                  }
               } catch (IOException ioexception) {
                  SystemToast.m_94866_(this, p_91145_);
                  f_90982_.error("Failed to delete world {}", p_91145_, ioexception);
               }
            }

         }, new TranslatableComponent("selectWorld.backupQuestion.experimental"), new TranslatableComponent("selectWorld.backupWarning.experimental"), CommonComponents.f_130659_, CommonComponents.f_130656_));
      }

   }

   public Minecraft.ServerStem m_91190_(RegistryAccess.RegistryHolder p_91191_, Function<LevelStorageSource.LevelStorageAccess, DataPackConfig> p_91192_, Function4<LevelStorageSource.LevelStorageAccess, RegistryAccess.RegistryHolder, ResourceManager, DataPackConfig, WorldData> p_91193_, boolean p_91194_, LevelStorageSource.LevelStorageAccess p_91195_) throws InterruptedException, ExecutionException {
      DataPackConfig datapackconfig = p_91192_.apply(p_91195_);
      PackRepository packrepository = new PackRepository(PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(p_91195_.m_78283_(LevelResource.f_78180_).toFile(), PackSource.f_10529_));

      try {
         DataPackConfig datapackconfig1 = MinecraftServer.m_129819_(packrepository, datapackconfig, p_91194_);
         CompletableFuture<ServerResources> completablefuture = ServerResources.m_180005_(packrepository.m_10525_(), p_91191_, Commands.CommandSelection.INTEGRATED, 2, Util.m_137578_(), this);
         this.m_18701_(completablefuture::isDone);
         ServerResources serverresources = completablefuture.get();
         WorldData worlddata = p_91193_.apply(p_91195_, p_91191_, serverresources.m_136178_(), datapackconfig1);
         return new Minecraft.ServerStem(packrepository, serverresources, worlddata);
      } catch (ExecutionException | InterruptedException interruptedexception) {
         packrepository.close();
         throw interruptedexception;
      }
   }

   public void m_91156_(ClientLevel p_91157_) {
      if (f_91073_ != null) net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Unload(f_91073_));
      ProgressScreen progressscreen = new ProgressScreen(true);
      progressscreen.m_6309_(new TranslatableComponent("connect.joining"));
      this.m_91362_(progressscreen);
      this.f_91073_ = p_91157_;
      this.m_91324_(p_91157_);
      if (!this.f_91010_) {
         AuthenticationService authenticationservice = new YggdrasilAuthenticationService(this.f_91030_);
         MinecraftSessionService minecraftsessionservice = authenticationservice.createMinecraftSessionService();
         GameProfileRepository gameprofilerepository = authenticationservice.createProfileRepository();
         GameProfileCache gameprofilecache = new GameProfileCache(gameprofilerepository, new File(this.f_91069_, MinecraftServer.f_129742_.getName()));
         gameprofilecache.m_143974_(this);
         SkullBlockEntity.m_59764_(gameprofilecache);
         SkullBlockEntity.m_59771_(minecraftsessionservice);
         SkullBlockEntity.m_182462_(this);
         GameProfileCache.m_11004_(false);
      }

   }

   public void m_91399_() {
      this.m_91320_(new ProgressScreen(true));
   }

   public void m_91320_(Screen p_91321_) {
      ClientPacketListener clientpacketlistener = this.m_91403_();
      if (clientpacketlistener != null) {
         this.m_18698_();
         clientpacketlistener.m_105140_();
      }

      IntegratedServer integratedserver = this.f_91007_;
      this.f_91007_ = null;
      this.f_91063_.m_109150_();
      net.minecraftforge.client.ForgeHooksClient.firePlayerLogout(this.f_91072_, this.f_91074_);
      this.f_91072_ = null;
      NarratorChatListener.f_93311_.m_93328_();
      this.m_91362_(p_91321_);
      if (this.f_91073_ != null) {
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Unload(f_91073_));
         if (integratedserver != null) {
            this.f_91026_.m_6180_("waitForServer");

            while(!integratedserver.m_129782_()) {
               this.m_91383_(false);
            }

            this.f_91026_.m_7238_();
         }

         this.f_91037_.m_118586_();
         this.f_91065_.m_93089_();
         this.f_91008_ = null;
         this.f_91010_ = false;
         net.minecraftforge.client.ForgeHooksClient.handleClientWorldClosing(f_91073_);
         this.f_91004_.m_90740_();
      }

      this.f_91073_ = null;
      this.m_91324_((ClientLevel)null);
      this.f_91074_ = null;
   }

   private void m_91362_(Screen p_91363_) {
      this.f_91026_.m_6180_("forcedTick");
      this.f_91043_.m_120405_();
      this.f_91075_ = null;
      this.f_91009_ = null;
      this.m_91152_(p_91363_);
      this.m_91383_(false);
      this.f_91026_.m_7238_();
   }

   public void m_91346_(Screen p_91347_) {
      this.f_91026_.m_6180_("forcedTick");
      this.m_91152_(p_91347_);
      this.m_91383_(false);
      this.f_91026_.m_7238_();
   }

   private void m_91324_(@Nullable ClientLevel p_91325_) {
      this.f_91060_.m_109701_(p_91325_);
      this.f_91061_.m_107342_(p_91325_);
      this.f_167845_.m_112257_(p_91325_);
      this.m_91341_();
      net.minecraftforge.client.MinecraftForgeClient.clearRenderCache();
   }

   public boolean m_91400_() {
      return this.f_91034_ && this.f_91049_.serversAllowed();
   }

   public boolean m_168021_() {
      return this.f_91049_.realmsAllowed();
   }

   public boolean m_91246_(UUID p_91247_) {
      if (this.m_168022_().m_142594_(false)) {
         return this.f_91006_.m_100684_(p_91247_);
      } else {
         return (this.f_91074_ == null || !p_91247_.equals(this.f_91074_.m_142081_())) && !p_91247_.equals(Util.f_137441_);
      }
   }

   public Minecraft.ChatStatus m_168022_() {
      if (this.f_91066_.f_92119_ == ChatVisiblity.HIDDEN) {
         return Minecraft.ChatStatus.DISABLED_BY_OPTIONS;
      } else if (!this.f_91035_) {
         return Minecraft.ChatStatus.DISABLED_BY_LAUNCHER;
      } else {
         return !this.f_91049_.chatAllowed() ? Minecraft.ChatStatus.DISABLED_BY_PROFILE : Minecraft.ChatStatus.ENABLED;
      }
   }

   public final boolean m_91402_() {
      return this.f_91033_;
   }

   @Nullable
   public ClientPacketListener m_91403_() {
      return this.f_91074_ == null ? null : this.f_91074_.f_108617_;
   }

   public static boolean m_91404_() {
      return !f_90981_.f_91066_.f_92062_;
   }

   public static boolean m_91405_() {
      return f_90981_.f_91066_.f_92115_.m_90773_() >= GraphicsStatus.FANCY.m_90773_();
   }

   public static boolean m_91085_() {
      return !f_90981_.f_91063_.m_172715_() && f_90981_.f_91066_.f_92115_.m_90773_() >= GraphicsStatus.FABULOUS.m_90773_();
   }

   public static boolean m_91086_() {
      return f_90981_.f_91066_.f_92116_ != AmbientOcclusionStatus.OFF;
   }

   private void m_91280_() {
      if (this.f_91077_ != null && this.f_91077_.m_6662_() != HitResult.Type.MISS) {
          if (!net.minecraftforge.client.ForgeHooksClient.onClickInput(2, this.f_91066_.f_92097_, InteractionHand.MAIN_HAND).isCanceled())
              net.minecraftforge.common.ForgeHooks.onPickBlock(this.f_91077_, this.f_91074_, this.f_91073_);
          // We delete this code wholly instead of commenting it out, to make sure we detect changes in it between MC versions
      }
   }

   public ItemStack m_91122_(ItemStack p_91123_, BlockEntity p_91124_) {
      CompoundTag compoundtag = p_91124_.m_6945_(new CompoundTag());
      if (p_91123_.m_41720_() instanceof PlayerHeadItem && compoundtag.m_128441_("SkullOwner")) {
         CompoundTag compoundtag2 = compoundtag.m_128469_("SkullOwner");
         p_91123_.m_41784_().m_128365_("SkullOwner", compoundtag2);
         return p_91123_;
      } else {
         p_91123_.m_41700_("BlockEntityTag", compoundtag);
         CompoundTag compoundtag1 = new CompoundTag();
         ListTag listtag = new ListTag();
         listtag.add(StringTag.m_129297_("\"(+NBT)\""));
         compoundtag1.m_128365_("Lore", listtag);
         p_91123_.m_41700_("display", compoundtag1);
         return p_91123_;
      }
   }

   public CrashReport m_91354_(CrashReport p_91355_) {
      SystemReport systemreport = p_91355_.m_178626_();
      m_167850_(systemreport, this, this.f_91039_, this.f_91001_, this.f_91066_);
      if (this.f_91073_ != null) {
         this.f_91073_.m_6026_(p_91355_);
      }

      if (this.f_91007_ != null) {
         this.f_91007_.m_177935_(systemreport);
      }

      this.f_167847_.m_168562_(p_91355_);
      return p_91355_;
   }

   public static void m_167872_(@Nullable Minecraft p_167873_, @Nullable LanguageManager p_167874_, String p_167875_, @Nullable Options p_167876_, CrashReport p_167877_) {
      SystemReport systemreport = p_167877_.m_178626_();
      m_167850_(systemreport, p_167873_, p_167874_, p_167875_, p_167876_);
   }

   private static SystemReport m_167850_(SystemReport p_167851_, @Nullable Minecraft p_167852_, @Nullable LanguageManager p_167853_, String p_167854_, Options p_167855_) {
      p_167851_.m_143522_("Launched Version", () -> {
         return p_167854_;
      });
      p_167851_.m_143522_("Backend library", RenderSystem::m_69517_);
      p_167851_.m_143522_("Backend API", RenderSystem::m_69516_);
      p_167851_.m_143522_("Window size", () -> {
         return p_167852_ != null ? p_167852_.f_90990_.m_85441_() + "x" + p_167852_.f_90990_.m_85442_() : "<not initialized>";
      });
      p_167851_.m_143522_("GL Caps", RenderSystem::m_69518_);
      p_167851_.m_143522_("GL debug messages", () -> {
         return GlDebug.m_166226_() ? String.join("\n", GlDebug.m_166225_()) : "<disabled>";
      });
      p_167851_.m_143522_("Using VBOs", () -> {
         return "Yes";
      });
      p_167851_.m_143522_("Is Modded", () -> {
         String s1 = ClientBrandRetriever.m_129629_();
         if (!"vanilla".equals(s1)) {
            return "Definitely; Client brand changed to '" + s1 + "'";
         } else {
            return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
         }
      });
      p_167851_.m_143519_("Type", "Client (map_client.txt)");
      if (p_167855_ != null) {
         if (f_90981_ != null) {
            String s = f_90981_.m_91105_().m_109256_();
            if (s != null) {
               p_167851_.m_143519_("GPU Warnings", s);
            }
         }

         p_167851_.m_143519_("Graphics mode", p_167855_.f_92115_.toString());
         p_167851_.m_143522_("Resource Packs", () -> {
            StringBuilder stringbuilder = new StringBuilder();

            for(String s1 : p_167855_.f_92117_) {
               if (stringbuilder.length() > 0) {
                  stringbuilder.append(", ");
               }

               stringbuilder.append(s1);
               if (p_167855_.f_92118_.contains(s1)) {
                  stringbuilder.append(" (incompatible)");
               }
            }

            return stringbuilder.toString();
         });
      }

      if (p_167853_ != null) {
         p_167851_.m_143522_("Current Language", () -> {
            return p_167853_.m_118983_().toString();
         });
      }

      p_167851_.m_143522_("CPU", GlUtil::m_84819_);
      return p_167851_;
   }

   public static Minecraft m_91087_() {
      return f_90981_;
   }

   public CompletableFuture<Void> m_91088_() {
      return this.m_18691_(() -> this.m_91391_()).thenCompose((p_167945_) -> {
         return p_167945_;
      });
   }

   public void m_7108_(Snooper p_91117_) {
      p_91117_.m_19223_("fps", f_91021_);
      p_91117_.m_19223_("vsync_enabled", this.f_91066_.f_92041_);
      p_91117_.m_19223_("display_frequency", this.f_90990_.m_85377_());
      p_91117_.m_19223_("display_type", this.f_90990_.m_85440_() ? "fullscreen" : "windowed");
      p_91117_.m_19223_("run_time", (Util.m_137550_() - p_91117_.m_19233_()) / 60L * 1000L);
      p_91117_.m_19223_("current_action", this.m_91281_());
      p_91117_.m_19223_("language", this.f_91066_.f_92075_ == null ? "en_us" : this.f_91066_.f_92075_);
      String s = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      p_91117_.m_19223_("endianness", s);
      p_91117_.m_19223_("subtitles", this.f_91066_.f_92049_);
      p_91117_.m_19223_("touch", this.f_91066_.f_92051_ ? "touch" : "mouse");
      int i = 0;

      for(Pack pack : this.f_91038_.m_10524_()) {
         if (!pack.m_10449_() && !pack.m_10450_()) {
            p_91117_.m_19223_("resource_pack[" + i++ + "]", pack.m_10446_());
         }
      }

      p_91117_.m_19223_("resource_packs", i);
      if (this.f_91007_ != null) {
         p_91117_.m_19223_("snooper_partner", this.f_91007_.m_129922_().m_19232_());
      }

   }

   private String m_91281_() {
      if (this.f_91007_ != null) {
         return this.f_91007_.m_6992_() ? "hosting_lan" : "singleplayer";
      } else if (this.f_91008_ != null) {
         return this.f_91008_.m_105389_() ? "playing_lan" : "multiplayer";
      } else {
         return "out_of_game";
      }
   }

   public void m_142423_(Snooper p_167985_) {
      p_167985_.m_19227_("client_brand", ClientBrandRetriever.m_129629_());
      p_167985_.m_19227_("launched_version", this.f_91001_);
      m_168001_(p_167985_);
      p_167985_.m_19227_("gl_max_texture_size", RenderSystem.m_69839_());
      GameProfile gameprofile = this.f_90998_.m_92548_();
      if (gameprofile.getId() != null) {
         p_167985_.m_19227_("uuid", Hashing.sha1().hashBytes(gameprofile.getId().toString().getBytes(Charsets.ISO_8859_1)).toString());
      }

   }

   private static void m_168001_(Snooper p_168002_) {
      GlUtil.m_166249_(p_168002_::m_19227_);
   }

   public boolean m_142725_() {
      return this.f_91066_.f_92048_;
   }

   public void m_91158_(@Nullable ServerData p_91159_) {
      this.f_91008_ = p_91159_;
   }

   @Nullable
   public ServerData m_91089_() {
      return this.f_91008_;
   }

   public boolean m_91090_() {
      return this.f_91010_;
   }

   public boolean m_91091_() {
      return this.f_91010_ && this.f_91007_ != null;
   }

   @Nullable
   public IntegratedServer m_91092_() {
      return this.f_91007_;
   }

   public Snooper m_91093_() {
      return this.f_90992_;
   }

   public User m_91094_() {
      return this.f_90998_;
   }

   public PropertyMap m_91095_() {
      if (this.f_90986_.isEmpty()) {
         GameProfile gameprofile = this.m_91108_().fillProfileProperties(this.f_90998_.m_92548_(), false);
         this.f_90986_.putAll(gameprofile.getProperties());
      }

      return this.f_90986_;
   }

   public Proxy m_91096_() {
      return this.f_91030_;
   }

   public TextureManager m_91097_() {
      return this.f_90987_;
   }

   public ResourceManager m_91098_() {
      return this.f_91036_;
   }

   public PackRepository m_91099_() {
      return this.f_91038_;
   }

   public ClientPackSource m_91100_() {
      return this.f_91037_;
   }

   public File m_91101_() {
      return this.f_90985_;
   }

   public LanguageManager m_91102_() {
      return this.f_91039_;
   }

   public Function<ResourceLocation, TextureAtlasSprite> m_91258_(ResourceLocation p_91259_) {
      return this.f_91051_.m_119428_(p_91259_)::m_118316_;
   }

   public boolean m_91103_() {
      return this.f_91032_;
   }

   public boolean m_91104_() {
      return this.f_91012_;
   }

   public GpuWarnlistManager m_91105_() {
      return this.f_91047_;
   }

   public SoundManager m_91106_() {
      return this.f_91043_;
   }

   public Music m_91107_() {
      if (this.f_91080_ instanceof WinScreen) {
         return Musics.f_11647_;
      } else if (this.f_91074_ != null) {
         if (this.f_91074_.f_19853_.m_46472_() == Level.f_46430_) {
            return this.f_91065_.m_93090_().m_93713_() ? Musics.f_11648_ : Musics.f_11649_;
         } else {
            Biome.BiomeCategory biome$biomecategory = this.f_91074_.f_19853_.m_46857_(this.f_91074_.m_142538_()).m_47567_();
            if (!this.f_91044_.m_120187_(Musics.f_11650_) && (!this.f_91074_.m_5842_() || biome$biomecategory != Biome.BiomeCategory.OCEAN && biome$biomecategory != Biome.BiomeCategory.RIVER)) {
               return this.f_91074_.f_19853_.m_46472_() != Level.f_46429_ && this.f_91074_.m_150110_().f_35937_ && this.f_91074_.m_150110_().f_35936_ ? Musics.f_11646_ : this.f_91073_.m_7062_().m_47883_(this.f_91074_.m_142538_()).m_47566_().orElse(Musics.f_11651_);
            } else {
               return Musics.f_11650_;
            }
         }
      } else {
         return Musics.f_11645_;
      }
   }

   public MinecraftSessionService m_91108_() {
      return this.f_91048_;
   }

   public SkinManager m_91109_() {
      return this.f_91050_;
   }

   @Nullable
   public Entity m_91288_() {
      return this.f_91075_;
   }

   public void m_91118_(Entity p_91119_) {
      this.f_91075_ = p_91119_;
      this.f_91063_.m_109106_(p_91119_);
   }

   public boolean m_91314_(Entity p_91315_) {
      return p_91315_.m_142038_() || this.f_91074_ != null && this.f_91074_.m_5833_() && this.f_91066_.f_92054_.m_90857_() && p_91315_.m_6095_() == EntityType.f_20532_;
   }

   protected Thread m_6304_() {
      return this.f_91018_;
   }

   protected Runnable m_6681_(Runnable p_91376_) {
      return p_91376_;
   }

   protected boolean m_6362_(Runnable p_91365_) {
      return true;
   }

   public BlockRenderDispatcher m_91289_() {
      return this.f_91052_;
   }

   public EntityRenderDispatcher m_91290_() {
      return this.f_90994_;
   }

   public BlockEntityRenderDispatcher m_167982_() {
      return this.f_167845_;
   }

   public ItemRenderer m_91291_() {
      return this.f_90995_;
   }

   public ItemInHandRenderer m_91292_() {
      return this.f_90996_;
   }

   public <T> MutableSearchTree<T> m_91171_(SearchRegistry.Key<T> p_91172_) {
      return this.f_90997_.m_119949_(p_91172_);
   }

   public FrameTimer m_91293_() {
      return this.f_91070_;
   }

   public boolean m_91294_() {
      return this.f_91017_;
   }

   public void m_91372_(boolean p_91373_) {
      this.f_91017_ = p_91373_;
   }

   public DataFixer m_91295_() {
      return this.f_90988_;
   }

   public float m_91296_() {
      return this.f_90991_.f_92518_;
   }

   public float m_91297_() {
      return this.f_90991_.f_92519_;
   }

   public BlockColors m_91298_() {
      return this.f_91040_;
   }

   public boolean m_91299_() {
      return this.f_91074_ != null && this.f_91074_.m_36330_() || this.f_91066_.f_92047_;
   }

   public ToastComponent m_91300_() {
      return this.f_91003_;
   }

   public Tutorial m_91301_() {
      return this.f_91005_;
   }

   public boolean m_91302_() {
      return this.f_91022_;
   }

   public HotbarManager m_91303_() {
      return this.f_91000_;
   }

   public ModelManager m_91304_() {
      return this.f_91051_;
   }

   public PaintingTextureManager m_91305_() {
      return this.f_91053_;
   }

   public MobEffectTextureManager m_91306_() {
      return this.f_91054_;
   }

   public void m_7440_(boolean p_91261_) {
      this.f_91022_ = p_91261_;
   }

   public Component m_167899_(File p_167900_, int p_167901_, int p_167902_) {
      int i = this.f_90990_.m_85441_();
      int j = this.f_90990_.m_85442_();
      RenderTarget rendertarget = new TextureTarget(p_167901_, p_167902_, true, f_91002_);
      float f = this.f_91074_.m_146909_();
      float f1 = this.f_91074_.m_146908_();
      float f2 = this.f_91074_.f_19860_;
      float f3 = this.f_91074_.f_19859_;
      this.f_91063_.m_172775_(false);

      TranslatableComponent translatablecomponent;
      try {
         this.f_91063_.m_172779_(true);
         this.f_91060_.m_173014_();
         this.f_90990_.m_166450_(p_167901_);
         this.f_90990_.m_166452_(p_167902_);

         for(int k = 0; k < 6; ++k) {
            switch(k) {
            case 0:
               this.f_91074_.m_146922_(f1);
               this.f_91074_.m_146926_(0.0F);
               break;
            case 1:
               this.f_91074_.m_146922_((f1 + 90.0F) % 360.0F);
               this.f_91074_.m_146926_(0.0F);
               break;
            case 2:
               this.f_91074_.m_146922_((f1 + 180.0F) % 360.0F);
               this.f_91074_.m_146926_(0.0F);
               break;
            case 3:
               this.f_91074_.m_146922_((f1 - 90.0F) % 360.0F);
               this.f_91074_.m_146926_(0.0F);
               break;
            case 4:
               this.f_91074_.m_146922_(f1);
               this.f_91074_.m_146926_(-90.0F);
               break;
            case 5:
            default:
               this.f_91074_.m_146922_(f1);
               this.f_91074_.m_146926_(90.0F);
            }

            this.f_91074_.f_19859_ = this.f_91074_.m_146908_();
            this.f_91074_.f_19860_ = this.f_91074_.m_146909_();
            rendertarget.m_83947_(true);
            this.f_91063_.m_109089_(1.0F, 0L, new PoseStack());

            try {
               Thread.sleep(10L);
            } catch (InterruptedException interruptedexception) {
            }

            Screenshot.m_92295_(p_167900_, "panorama_" + k + ".png", rendertarget, (p_167966_) -> {
            });
         }

         Component component = (new TextComponent(p_167900_.getName())).m_130940_(ChatFormatting.UNDERLINE).m_130938_((p_167990_) -> {
            return p_167990_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, p_167900_.getAbsolutePath()));
         });
         return new TranslatableComponent("screenshot.success", component);
      } catch (Exception exception) {
         f_90982_.error("Couldn't save image", (Throwable)exception);
         translatablecomponent = new TranslatableComponent("screenshot.failure", exception.getMessage());
      } finally {
         this.f_91074_.m_146926_(f);
         this.f_91074_.m_146922_(f1);
         this.f_91074_.f_19860_ = f2;
         this.f_91074_.f_19859_ = f3;
         this.f_91063_.m_172775_(true);
         this.f_90990_.m_166450_(i);
         this.f_90990_.m_166452_(j);
         rendertarget.m_83930_();
         this.f_91063_.m_172779_(false);
         this.f_91060_.m_173014_();
         this.m_91385_().m_83947_(true);
      }

      return translatablecomponent;
   }

   private Component m_167903_(File p_167904_, int p_167905_, int p_167906_, int p_167907_, int p_167908_) {
      try {
         ByteBuffer bytebuffer = GlUtil.m_166247_(p_167905_ * p_167906_ * 3);
         Screenshot screenshot = new Screenshot(p_167904_, p_167907_, p_167908_, p_167906_);
         float f = (float)p_167907_ / (float)p_167905_;
         float f1 = (float)p_167908_ / (float)p_167906_;
         float f2 = f > f1 ? f : f1;

         for(int i = (p_167908_ - 1) / p_167906_ * p_167906_; i >= 0; i -= p_167906_) {
            for(int j = 0; j < p_167907_; j += p_167905_) {
               RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
               float f3 = (float)(p_167907_ - p_167905_) / 2.0F * 2.0F - (float)(j * 2);
               float f4 = (float)(p_167908_ - p_167906_) / 2.0F * 2.0F - (float)(i * 2);
               f3 = f3 / (float)p_167905_;
               f4 = f4 / (float)p_167906_;
               this.f_91063_.m_172718_(f2, f3, f4);
               bytebuffer.clear();
               RenderSystem.m_69854_(3333, 1);
               RenderSystem.m_69854_(3317, 1);
               RenderSystem.m_69871_(0, 0, p_167905_, p_167906_, 32992, 5121, bytebuffer);
               screenshot.m_168609_(bytebuffer, j, i, p_167905_, p_167906_);
            }

            screenshot.m_168605_();
         }

         File file1 = screenshot.m_168615_();
         GlUtil.m_166251_(bytebuffer);
         Component component = (new TextComponent(file1.getName())).m_130940_(ChatFormatting.UNDERLINE).m_130938_((p_167911_) -> {
            return p_167911_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, file1.getAbsolutePath()));
         });
         return new TranslatableComponent("screenshot.success", component);
      } catch (Exception exception) {
         f_90982_.warn("Couldn't save screenshot", (Throwable)exception);
         return new TranslatableComponent("screenshot.failure", exception.getMessage());
      }
   }

   public ProfilerFiller m_91307_() {
      return this.f_91026_;
   }

   public Game m_91309_() {
      return this.f_91004_;
   }

   @Nullable
   public StoringChunkProgressListener m_167983_() {
      return this.f_90999_.get();
   }

   public SplashManager m_91310_() {
      return this.f_91046_;
   }

   @Nullable
   public Overlay m_91265_() {
      return this.f_91081_;
   }

   public PlayerSocialManager m_91266_() {
      return this.f_91006_;
   }

   public boolean m_91267_() {
      return false;
   }

   public Window m_91268_() {
      return this.f_90990_;
   }

   public RenderBuffers m_91269_() {
      return this.f_90993_;
   }

   private static Pack createClientPackAdapter(String p_167934_, Component p_167935_, boolean p_167936_, Supplier<PackResources> p_167937_, PackMetadataSection p_167938_, Pack.Position p_167939_, PackSource p_167940_, boolean hidden) {
      int i = p_167938_.m_10374_();
      Supplier<PackResources> supplier = p_167937_;
      if (i <= 3) {
         supplier = m_91330_(p_167937_);
      }

      if (i <= 4) {
         supplier = m_91352_(supplier);
      }

      return new Pack(p_167934_, p_167935_, p_167936_, supplier, p_167938_, PackType.CLIENT_RESOURCES, p_167939_, p_167940_, hidden);
   }

   private static Supplier<PackResources> m_91330_(Supplier<PackResources> p_91331_) {
      return () -> {
         return new LegacyPackResourcesAdapter(p_91331_.get(), LegacyPackResourcesAdapter.f_118690_);
      };
   }

   private static Supplier<PackResources> m_91352_(Supplier<PackResources> p_91353_) {
      return () -> {
         return new PackResourcesAdapterV4(p_91353_.get());
      };
   }

   public void m_91312_(int p_91313_) {
      this.f_91051_.m_119410_(p_91313_);
   }

   public ItemColors getItemColors() {
      return this.f_91041_;
   }

   public SearchRegistry getSearchTreeManager() {
      return this.f_90997_;
   }

   public EntityModelSet m_167973_() {
      return this.f_167844_;
   }

   public boolean m_167974_() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum ChatStatus {
      ENABLED(TextComponent.f_131282_) {
         public boolean m_142594_(boolean p_168045_) {
            return true;
         }
      },
      DISABLED_BY_OPTIONS((new TranslatableComponent("chat.disabled.options")).m_130940_(ChatFormatting.RED)) {
         public boolean m_142594_(boolean p_168051_) {
            return false;
         }
      },
      DISABLED_BY_LAUNCHER((new TranslatableComponent("chat.disabled.launcher")).m_130940_(ChatFormatting.RED)) {
         public boolean m_142594_(boolean p_168057_) {
            return p_168057_;
         }
      },
      DISABLED_BY_PROFILE((new TranslatableComponent("chat.disabled.profile")).m_130940_(ChatFormatting.RED)) {
         public boolean m_142594_(boolean p_168063_) {
            return p_168063_;
         }
      };

      private final Component f_168027_;

      ChatStatus(Component p_168033_) {
         this.f_168027_ = p_168033_;
      }

      public Component m_168034_() {
         return this.f_168027_;
      }

      public abstract boolean m_142594_(boolean p_168035_);
   }

   @OnlyIn(Dist.CLIENT)
   static enum ExperimentalDialogType {
      NONE,
      CREATE,
      BACKUP;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class ServerStem implements AutoCloseable {
      private final PackRepository f_91420_;
      private final ServerResources f_91421_;
      private final WorldData f_91422_;

      ServerStem(PackRepository p_91424_, ServerResources p_91425_, WorldData p_91426_) {
         this.f_91420_ = p_91424_;
         this.f_91421_ = p_91425_;
         this.f_91422_ = p_91426_;
      }

      public PackRepository m_91432_() {
         return this.f_91420_;
      }

      public ServerResources m_91433_() {
         return this.f_91421_;
      }

      public WorldData m_91434_() {
         return this.f_91422_;
      }

      public void close() {
         this.f_91420_.close();
         this.f_91421_.close();
      }
   }
}
