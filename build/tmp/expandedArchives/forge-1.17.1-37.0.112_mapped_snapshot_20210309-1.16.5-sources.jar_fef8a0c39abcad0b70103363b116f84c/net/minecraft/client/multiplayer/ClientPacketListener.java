package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.DebugQueryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.DemoIntroScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.achievement.StatsUpdateListener;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.debug.BeeDebugRenderer;
import net.minecraft.client.renderer.debug.BrainDebugRenderer;
import net.minecraft.client.renderer.debug.GoalSelectorDebugRenderer;
import net.minecraft.client.renderer.debug.NeighborsUpdateRenderer;
import net.minecraft.client.renderer.debug.WorldGenAttemptRenderer;
import net.minecraft.client.resources.sounds.BeeAggressiveSoundInstance;
import net.minecraft.client.resources.sounds.BeeFlyingSoundInstance;
import net.minecraft.client.resources.sounds.BeeSoundInstance;
import net.minecraft.client.resources.sounds.GuardianAttackSoundInstance;
import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.searchtree.MutableSearchTree;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundAddPaintingPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAddVibrationSignalPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockBreakAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.StaticTags;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientPacketListener implements ClientGamePacketListener {
   private static final Logger f_104883_ = LogManager.getLogger();
   private static final Component f_104884_ = new TranslatableComponent("disconnect.lost");
   private final Connection f_104885_;
   private final GameProfile f_104886_;
   private final Screen f_104887_;
   private final Minecraft f_104888_;
   private ClientLevel f_104889_;
   private ClientLevel.ClientLevelData f_104890_;
   private boolean f_104891_;
   private final Map<UUID, PlayerInfo> f_104892_ = Maps.newHashMap();
   private final ClientAdvancements f_104893_;
   private final ClientSuggestionProvider f_104894_;
   private TagContainer f_104895_ = TagContainer.f_13420_;
   private final DebugQueryHandler f_104896_ = new DebugQueryHandler(this);
   private int f_104897_ = 3;
   private final Random f_104898_ = new Random();
   private CommandDispatcher<SharedSuggestionProvider> f_104899_ = new CommandDispatcher<>();
   private final RecipeManager f_104900_ = new RecipeManager();
   private final UUID f_104901_ = UUID.randomUUID();
   private Set<ResourceKey<Level>> f_104902_;
   private RegistryAccess f_104903_ = RegistryAccess.m_123086_();

   public ClientPacketListener(Minecraft p_104906_, Screen p_104907_, Connection p_104908_, GameProfile p_104909_) {
      this.f_104888_ = p_104906_;
      this.f_104887_ = p_104907_;
      this.f_104885_ = p_104908_;
      this.f_104886_ = p_104909_;
      this.f_104893_ = new ClientAdvancements(p_104906_);
      this.f_104894_ = new ClientSuggestionProvider(this, p_104906_);
   }

   public ClientSuggestionProvider m_105137_() {
      return this.f_104894_;
   }

   public void m_105140_() {
      this.f_104889_ = null;
   }

   public RecipeManager m_105141_() {
      return this.f_104900_;
   }

   public void m_5998_(ClientboundLoginPacket p_105030_) {
      PacketUtils.m_131363_(p_105030_, this, this.f_104888_);
      this.f_104888_.f_91072_ = new MultiPlayerGameMode(this.f_104888_, this);
      if (!this.f_104885_.m_129531_()) {
         StaticTags.m_13266_();
      }

      List<ResourceKey<Level>> list = Lists.newArrayList(p_105030_.m_132405_());
      Collections.shuffle(list);
      this.f_104902_ = Sets.newLinkedHashSet(list);
      this.f_104903_ = p_105030_.m_132406_();
      ResourceKey<Level> resourcekey = p_105030_.m_132408_();
      DimensionType dimensiontype = p_105030_.m_132407_();
      this.f_104897_ = p_105030_.m_132409_();
      boolean flag = p_105030_.m_132412_();
      boolean flag1 = p_105030_.m_132413_();
      ClientLevel.ClientLevelData clientlevel$clientleveldata = new ClientLevel.ClientLevelData(Difficulty.NORMAL, p_105030_.m_132402_(), flag1);
      this.f_104890_ = clientlevel$clientleveldata;
      this.f_104889_ = new ClientLevel(this, clientlevel$clientleveldata, resourcekey, dimensiontype, this.f_104897_, this.f_104888_::m_91307_, this.f_104888_.f_91060_, flag, p_105030_.m_132401_());
      this.f_104888_.m_91156_(this.f_104889_);
      if (this.f_104888_.f_91074_ == null) {
         this.f_104888_.f_91074_ = this.f_104888_.f_91072_.m_105246_(this.f_104889_, new StatsCounter(), new ClientRecipeBook());
         this.f_104888_.f_91074_.m_146922_(-180.0F);
         if (this.f_104888_.m_91092_() != null) {
            this.f_104888_.m_91092_().m_120046_(this.f_104888_.f_91074_.m_142081_());
         }
      }

      this.f_104888_.f_91064_.m_113434_();
      this.f_104888_.f_91074_.m_172530_();
      net.minecraftforge.client.ForgeHooksClient.firePlayerLogin(this.f_104888_.f_91072_, this.f_104888_.f_91074_, this.f_104888_.m_91403_().m_6198_());
      int i = p_105030_.m_132398_();
      this.f_104888_.f_91074_.m_20234_(i);
      this.f_104889_.m_104630_(i, this.f_104888_.f_91074_);
      this.f_104888_.f_91074_.f_108618_ = new KeyboardInput(this.f_104888_.f_91066_);
      this.f_104888_.f_91072_.m_105221_(this.f_104888_.f_91074_);
      this.f_104888_.f_91075_ = this.f_104888_.f_91074_;
      this.f_104888_.m_91152_(new ReceivingLevelScreen());
      this.f_104888_.f_91074_.m_36393_(p_105030_.m_132410_());
      this.f_104888_.f_91074_.m_108711_(p_105030_.m_132411_());
      this.f_104888_.f_91072_.m_171805_(p_105030_.m_132403_(), p_105030_.m_132404_());
      net.minecraftforge.fmllegacy.network.NetworkHooks.sendMCRegistryPackets(f_104885_, "PLAY_TO_SERVER");
      this.f_104888_.f_91066_.m_92172_();
      this.f_104885_.m_129512_(new ServerboundCustomPayloadPacket(ServerboundCustomPayloadPacket.f_133979_, (new FriendlyByteBuf(Unpooled.buffer())).m_130070_(ClientBrandRetriever.m_129629_())));
      this.f_104888_.m_91309_().m_90739_();
   }

   public void m_6771_(ClientboundAddEntityPacket p_104958_) {
      PacketUtils.m_131363_(p_104958_, this, this.f_104888_);
      EntityType<?> entitytype = p_104958_.m_131508_();
      Entity entity = entitytype.m_20615_(this.f_104889_);
      if (entity != null) {
         entity.m_141965_(p_104958_);
         int i = p_104958_.m_131496_();
         this.f_104889_.m_104627_(i, entity);
         if (entity instanceof AbstractMinecart) {
            this.f_104888_.m_91106_().m_120367_(new MinecartSoundInstance((AbstractMinecart)entity));
         }
      }

   }

   public void m_7708_(ClientboundAddExperienceOrbPacket p_104960_) {
      PacketUtils.m_131363_(p_104960_, this, this.f_104888_);
      double d0 = p_104960_.m_131527_();
      double d1 = p_104960_.m_131528_();
      double d2 = p_104960_.m_131529_();
      Entity entity = new ExperienceOrb(this.f_104889_, d0, d1, d2, p_104960_.m_131530_());
      entity.m_20167_(d0, d1, d2);
      entity.m_146922_(0.0F);
      entity.m_146926_(0.0F);
      entity.m_20234_(p_104960_.m_131524_());
      this.f_104889_.m_104627_(p_104960_.m_131524_(), entity);
   }

   public void m_142024_(ClientboundAddVibrationSignalPacket p_171763_) {
      PacketUtils.m_131363_(p_171763_, this, this.f_104888_);
      VibrationPath vibrationpath = p_171763_.m_178582_();
      BlockPos blockpos = vibrationpath.m_157948_();
      this.f_104889_.m_6485_(new VibrationParticleOption(vibrationpath), true, (double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_() + 0.5D, (double)blockpos.m_123343_() + 0.5D, 0.0D, 0.0D, 0.0D);
   }

   public void m_6433_(ClientboundAddPaintingPacket p_104964_) {
      PacketUtils.m_131363_(p_104964_, this, this.f_104888_);
      Painting painting = new Painting(this.f_104889_, p_104964_.m_131584_(), p_104964_.m_131585_(), p_104964_.m_131586_());
      painting.m_20234_(p_104964_.m_131580_());
      painting.m_20084_(p_104964_.m_131583_());
      this.f_104889_.m_104627_(p_104964_.m_131580_(), painting);
   }

   public void m_8048_(ClientboundSetEntityMotionPacket p_105092_) {
      PacketUtils.m_131363_(p_105092_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105092_.m_133192_());
      if (entity != null) {
         entity.m_6001_((double)p_105092_.m_133195_() / 8000.0D, (double)p_105092_.m_133196_() / 8000.0D, (double)p_105092_.m_133197_() / 8000.0D);
      }
   }

   public void m_6455_(ClientboundSetEntityDataPacket p_105088_) {
      PacketUtils.m_131363_(p_105088_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105088_.m_133159_());
      if (entity != null && p_105088_.m_133156_() != null) {
         entity.m_20088_().m_135356_(p_105088_.m_133156_());
      }

   }

   public void m_6482_(ClientboundAddPlayerPacket p_104966_) {
      PacketUtils.m_131363_(p_104966_, this, this.f_104888_);
      double d0 = p_104966_.m_131607_();
      double d1 = p_104966_.m_131608_();
      double d2 = p_104966_.m_131609_();
      float f = (float)(p_104966_.m_131610_() * 360) / 256.0F;
      float f1 = (float)(p_104966_.m_131611_() * 360) / 256.0F;
      int i = p_104966_.m_131603_();
      RemotePlayer remoteplayer = new RemotePlayer(this.f_104888_.f_91073_, this.m_104949_(p_104966_.m_131606_()).m_105312_());
      remoteplayer.m_20234_(i);
      remoteplayer.m_20167_(d0, d1, d2);
      remoteplayer.m_19890_(d0, d1, d2, f, f1);
      remoteplayer.m_146867_();
      this.f_104889_.m_104630_(i, remoteplayer);
   }

   public void m_6435_(ClientboundTeleportEntityPacket p_105124_) {
      PacketUtils.m_131363_(p_105124_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105124_.m_133545_());
      if (entity != null) {
         double d0 = p_105124_.m_133548_();
         double d1 = p_105124_.m_133549_();
         double d2 = p_105124_.m_133550_();
         entity.m_20167_(d0, d1, d2);
         if (!entity.m_6109_()) {
            float f = (float)(p_105124_.m_133551_() * 360) / 256.0F;
            float f1 = (float)(p_105124_.m_133552_() * 360) / 256.0F;
            entity.m_6453_(d0, d1, d2, f, f1, 3, true);
            entity.m_6853_(p_105124_.m_133553_());
         }

      }
   }

   public void m_5612_(ClientboundSetCarriedItemPacket p_105078_) {
      PacketUtils.m_131363_(p_105078_, this, this.f_104888_);
      if (Inventory.m_36045_(p_105078_.m_133079_())) {
         this.f_104888_.f_91074_.m_150109_().f_35977_ = p_105078_.m_133079_();
      }

   }

   public void m_7865_(ClientboundMoveEntityPacket p_105036_) {
      PacketUtils.m_131363_(p_105036_, this, this.f_104888_);
      Entity entity = p_105036_.m_132519_(this.f_104889_);
      if (entity != null) {
         if (!entity.m_6109_()) {
            if (p_105036_.m_132534_()) {
               Vec3 vec3 = p_105036_.m_132521_(entity.m_19878_());
               entity.m_20013_(vec3);
               float f = p_105036_.m_132533_() ? (float)(p_105036_.m_132531_() * 360) / 256.0F : entity.m_146908_();
               float f1 = p_105036_.m_132533_() ? (float)(p_105036_.m_132532_() * 360) / 256.0F : entity.m_146909_();
               entity.m_6453_(vec3.m_7096_(), vec3.m_7098_(), vec3.m_7094_(), f, f1, 3, false);
            } else if (p_105036_.m_132533_()) {
               float f2 = (float)(p_105036_.m_132531_() * 360) / 256.0F;
               float f3 = (float)(p_105036_.m_132532_() * 360) / 256.0F;
               entity.m_6453_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), f2, f3, 3, false);
            }

            entity.m_6853_(p_105036_.m_132535_());
         }

      }
   }

   public void m_6176_(ClientboundRotateHeadPacket p_105068_) {
      PacketUtils.m_131363_(p_105068_, this, this.f_104888_);
      Entity entity = p_105068_.m_132969_(this.f_104889_);
      if (entity != null) {
         float f = (float)(p_105068_.m_132977_() * 360) / 256.0F;
         entity.m_6541_(f, 3);
      }
   }

   public void m_182047_(ClientboundRemoveEntitiesPacket p_182633_) {
      PacketUtils.m_131363_(p_182633_, this, this.f_104888_);
      p_182633_.m_182730_().forEach((int p_182600_) -> {
         this.f_104889_.m_171642_(p_182600_, Entity.RemovalReason.DISCARDED);
      });
   }

   public void m_5682_(ClientboundPlayerPositionPacket p_105056_) {
      PacketUtils.m_131363_(p_105056_, this, this.f_104888_);
      Player player = this.f_104888_.f_91074_;
      if (p_105056_.m_179159_()) {
         player.m_6038_();
      }

      Vec3 vec3 = player.m_20184_();
      boolean flag = p_105056_.m_132826_().contains(ClientboundPlayerPositionPacket.RelativeArgument.X);
      boolean flag1 = p_105056_.m_132826_().contains(ClientboundPlayerPositionPacket.RelativeArgument.Y);
      boolean flag2 = p_105056_.m_132826_().contains(ClientboundPlayerPositionPacket.RelativeArgument.Z);
      double d0;
      double d1;
      if (flag) {
         d0 = vec3.m_7096_();
         d1 = player.m_20185_() + p_105056_.m_132818_();
         player.f_19790_ += p_105056_.m_132818_();
      } else {
         d0 = 0.0D;
         d1 = p_105056_.m_132818_();
         player.f_19790_ = d1;
      }

      double d2;
      double d3;
      if (flag1) {
         d2 = vec3.m_7098_();
         d3 = player.m_20186_() + p_105056_.m_132821_();
         player.f_19791_ += p_105056_.m_132821_();
      } else {
         d2 = 0.0D;
         d3 = p_105056_.m_132821_();
         player.f_19791_ = d3;
      }

      double d4;
      double d5;
      if (flag2) {
         d4 = vec3.m_7094_();
         d5 = player.m_20189_() + p_105056_.m_132822_();
         player.f_19792_ += p_105056_.m_132822_();
      } else {
         d4 = 0.0D;
         d5 = p_105056_.m_132822_();
         player.f_19792_ = d5;
      }

      player.m_20343_(d1, d3, d5);
      player.f_19854_ = d1;
      player.f_19855_ = d3;
      player.f_19856_ = d5;
      player.m_20334_(d0, d2, d4);
      float f = p_105056_.m_132823_();
      float f1 = p_105056_.m_132824_();
      if (p_105056_.m_132826_().contains(ClientboundPlayerPositionPacket.RelativeArgument.X_ROT)) {
         f1 += player.m_146909_();
      }

      if (p_105056_.m_132826_().contains(ClientboundPlayerPositionPacket.RelativeArgument.Y_ROT)) {
         f += player.m_146908_();
      }

      player.m_19890_(d1, d3, d5, f, f1);
      this.f_104885_.m_129512_(new ServerboundAcceptTeleportationPacket(p_105056_.m_132825_()));
      this.f_104885_.m_129512_(new ServerboundMovePlayerPacket.PosRot(player.m_20185_(), player.m_20186_(), player.m_20189_(), player.m_146908_(), player.m_146909_(), false));
      if (!this.f_104891_) {
         this.f_104891_ = true;
         this.f_104888_.m_91152_((Screen)null);
      }

   }

   public void m_5771_(ClientboundSectionBlocksUpdatePacket p_105070_) {
      PacketUtils.m_131363_(p_105070_, this, this.f_104888_);
      int i = 19 | (p_105070_.m_133000_() ? 128 : 0);
      p_105070_.m_132992_((p_104922_, p_104923_) -> {
         this.f_104889_.m_7731_(p_104922_, p_104923_, i);
      });
   }

   public void m_5623_(ClientboundLevelChunkPacket p_105022_) {
      PacketUtils.m_131363_(p_105022_, this, this.f_104888_);
      int i = p_105022_.m_132250_();
      int j = p_105022_.m_132251_();
      ChunkBiomeContainer chunkbiomecontainer = new ChunkBiomeContainer(this.f_104903_.m_175515_(Registry.f_122885_), this.f_104889_, p_105022_.m_132256_());
      LevelChunk levelchunk = this.f_104889_.m_7726_().m_171615_(i, j, chunkbiomecontainer, p_105022_.m_132247_(), p_105022_.m_132254_(), p_105022_.m_178906_());

      for(int k = this.f_104889_.m_151560_(); k < this.f_104889_.m_151561_(); ++k) {
         this.f_104889_.m_104793_(i, k, j);
      }

      if (levelchunk != null) {
         for(CompoundTag compoundtag : p_105022_.m_132255_()) {
            BlockPos blockpos = new BlockPos(compoundtag.m_128451_("x"), compoundtag.m_128451_("y"), compoundtag.m_128451_("z"));
            BlockEntity blockentity = levelchunk.m_5685_(blockpos, LevelChunk.EntityCreationType.IMMEDIATE);
            if (blockentity != null) {
               blockentity.handleUpdateTag(compoundtag);
            }
         }
      }

   }

   public void m_5729_(ClientboundForgetLevelChunkPacket p_105014_) {
      PacketUtils.m_131363_(p_105014_, this, this.f_104888_);
      int i = p_105014_.m_132149_();
      int j = p_105014_.m_132152_();
      ClientChunkCache clientchunkcache = this.f_104889_.m_7726_();
      clientchunkcache.m_104455_(i, j);
      LevelLightEngine levellightengine = clientchunkcache.m_7827_();

      for(int k = this.f_104889_.m_151560_(); k < this.f_104889_.m_151561_(); ++k) {
         this.f_104889_.m_104793_(i, k, j);
         levellightengine.m_6191_(SectionPos.m_123173_(i, k, j), true);
      }

      levellightengine.m_141940_(new ChunkPos(i, j), false);
   }

   public void m_6773_(ClientboundBlockUpdatePacket p_104980_) {
      PacketUtils.m_131363_(p_104980_, this, this.f_104888_);
      this.f_104889_.m_104755_(p_104980_.m_131749_(), p_104980_.m_131746_());
   }

   public void m_6008_(ClientboundDisconnectPacket p_105008_) {
      this.f_104885_.m_129507_(p_105008_.m_132085_());
   }

   public void m_7026_(Component p_104954_) {
      this.f_104888_.m_91399_();
      if (this.f_104887_ != null) {
         if (this.f_104887_ instanceof RealmsScreen) {
            this.f_104888_.m_91152_(new DisconnectedRealmsScreen(this.f_104887_, f_104884_, p_104954_));
         } else {
            this.f_104888_.m_91152_(new DisconnectedScreen(this.f_104887_, f_104884_, p_104954_));
         }
      } else {
         this.f_104888_.m_91152_(new DisconnectedScreen(new JoinMultiplayerScreen(new TitleScreen()), f_104884_, p_104954_));
      }

   }

   public void m_104955_(Packet<?> p_104956_) {
      this.f_104885_.m_129512_(p_104956_);
   }

   public void m_8001_(ClientboundTakeItemEntityPacket p_105122_) {
      PacketUtils.m_131363_(p_105122_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105122_.m_133524_());
      LivingEntity livingentity = (LivingEntity)this.f_104889_.m_6815_(p_105122_.m_133527_());
      if (livingentity == null) {
         livingentity = this.f_104888_.f_91074_;
      }

      if (entity != null) {
         if (entity instanceof ExperienceOrb) {
            this.f_104889_.m_7785_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_11871_, SoundSource.PLAYERS, 0.1F, (this.f_104898_.nextFloat() - this.f_104898_.nextFloat()) * 0.35F + 0.9F, false);
         } else {
            this.f_104889_.m_7785_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2F, (this.f_104898_.nextFloat() - this.f_104898_.nextFloat()) * 1.4F + 2.0F, false);
         }

         this.f_104888_.f_91061_.m_107344_(new ItemPickupParticle(this.f_104888_.m_91290_(), this.f_104888_.m_91269_(), this.f_104889_, entity, livingentity));
         if (entity instanceof ItemEntity) {
            ItemEntity itementity = (ItemEntity)entity;
            ItemStack itemstack = itementity.m_32055_();
            itemstack.m_41774_(p_105122_.m_133528_());
            if (itemstack.m_41619_()) {
               this.f_104889_.m_171642_(p_105122_.m_133524_(), Entity.RemovalReason.DISCARDED);
            }
         } else if (!(entity instanceof ExperienceOrb)) {
            this.f_104889_.m_171642_(p_105122_.m_133524_(), Entity.RemovalReason.DISCARDED);
         }
      }

   }

   public void m_5784_(ClientboundChatPacket p_104986_) {
      PacketUtils.m_131363_(p_104986_, this, this.f_104888_);
      net.minecraft.network.chat.Component message = net.minecraftforge.event.ForgeEventFactory.onClientChat(p_104986_.m_131840_(), p_104986_.m_131836_(), p_104986_.m_131841_());
      if (message == null) return;
      this.f_104888_.f_91065_.m_93051_(p_104986_.m_131840_(), message, p_104986_.m_131841_());
   }

   public void m_7791_(ClientboundAnimatePacket p_104968_) {
      PacketUtils.m_131363_(p_104968_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_104968_.m_131624_());
      if (entity != null) {
         if (p_104968_.m_131627_() == 0) {
            LivingEntity livingentity = (LivingEntity)entity;
            livingentity.m_6674_(InteractionHand.MAIN_HAND);
         } else if (p_104968_.m_131627_() == 3) {
            LivingEntity livingentity1 = (LivingEntity)entity;
            livingentity1.m_6674_(InteractionHand.OFF_HAND);
         } else if (p_104968_.m_131627_() == 1) {
            entity.m_6053_();
         } else if (p_104968_.m_131627_() == 2) {
            Player player = (Player)entity;
            player.m_6145_(false, false);
         } else if (p_104968_.m_131627_() == 4) {
            this.f_104888_.f_91061_.m_107329_(entity, ParticleTypes.f_123797_);
         } else if (p_104968_.m_131627_() == 5) {
            this.f_104888_.f_91061_.m_107329_(entity, ParticleTypes.f_123808_);
         }

      }
   }

   public void m_6965_(ClientboundAddMobPacket p_104962_) {
      PacketUtils.m_131363_(p_104962_, this, this.f_104888_);
      LivingEntity livingentity = (LivingEntity)EntityType.m_20589_(p_104962_.m_131556_(), this.f_104889_);
      if (livingentity != null) {
         livingentity.m_142223_(p_104962_);
         this.f_104889_.m_104627_(p_104962_.m_131552_(), livingentity);
         if (livingentity instanceof Bee) {
            boolean flag = ((Bee)livingentity).m_21660_();
            BeeSoundInstance beesoundinstance;
            if (flag) {
               beesoundinstance = new BeeAggressiveSoundInstance((Bee)livingentity);
            } else {
               beesoundinstance = new BeeFlyingSoundInstance((Bee)livingentity);
            }

            this.f_104888_.m_91106_().m_120372_(beesoundinstance);
         }
      } else {
         f_104883_.warn("Skipping Entity with id {}", (int)p_104962_.m_131556_());
      }

   }

   public void m_7885_(ClientboundSetTimePacket p_105108_) {
      PacketUtils.m_131363_(p_105108_, this, this.f_104888_);
      this.f_104888_.f_91073_.m_104637_(p_105108_.m_133358_());
      this.f_104888_.f_91073_.m_104746_(p_105108_.m_133361_());
   }

   public void m_6571_(ClientboundSetDefaultSpawnPositionPacket p_105084_) {
      PacketUtils.m_131363_(p_105084_, this, this.f_104888_);
      this.f_104888_.f_91073_.m_104752_(p_105084_.m_133123_(), p_105084_.m_133126_());
   }

   public void m_6403_(ClientboundSetPassengersPacket p_105102_) {
      PacketUtils.m_131363_(p_105102_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105102_.m_133286_());
      if (entity == null) {
         f_104883_.warn("Received passengers for unknown entity");
      } else {
         boolean flag = entity.m_20367_(this.f_104888_.f_91074_);
         entity.m_20153_();

         for(int i : p_105102_.m_133283_()) {
            Entity entity1 = this.f_104889_.m_6815_(i);
            if (entity1 != null) {
               entity1.m_7998_(entity, true);
               if (entity1 == this.f_104888_.f_91074_ && !flag) {
                  this.f_104888_.f_91065_.m_93063_(new TranslatableComponent("mount.onboard", this.f_104888_.f_91066_.f_92090_.m_90863_()), false);
               }
            }
         }

      }
   }

   public void m_5599_(ClientboundSetEntityLinkPacket p_105090_) {
      PacketUtils.m_131363_(p_105090_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105090_.m_133172_());
      if (entity instanceof Mob) {
         ((Mob)entity).m_21506_(p_105090_.m_133175_());
      }

   }

   private static ItemStack m_104927_(Player p_104928_) {
      for(InteractionHand interactionhand : InteractionHand.values()) {
         ItemStack itemstack = p_104928_.m_21120_(interactionhand);
         if (itemstack.m_150930_(Items.f_42747_)) {
            return itemstack;
         }
      }

      return new ItemStack(Items.f_42747_);
   }

   public void m_7628_(ClientboundEntityEventPacket p_105010_) {
      PacketUtils.m_131363_(p_105010_, this, this.f_104888_);
      Entity entity = p_105010_.m_132094_(this.f_104889_);
      if (entity != null) {
         if (p_105010_.m_132102_() == 21) {
            this.f_104888_.m_91106_().m_120367_(new GuardianAttackSoundInstance((Guardian)entity));
         } else if (p_105010_.m_132102_() == 35) {
            int i = 40;
            this.f_104888_.f_91061_.m_107332_(entity, ParticleTypes.f_123767_, 30);
            this.f_104889_.m_7785_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12513_, entity.m_5720_(), 1.0F, 1.0F, false);
            if (entity == this.f_104888_.f_91074_) {
               this.f_104888_.f_91063_.m_109113_(m_104927_(this.f_104888_.f_91074_));
            }
         } else {
            entity.m_7822_(p_105010_.m_132102_());
         }
      }

   }

   public void m_5547_(ClientboundSetHealthPacket p_105098_) {
      PacketUtils.m_131363_(p_105098_, this, this.f_104888_);
      this.f_104888_.f_91074_.m_108760_(p_105098_.m_133247_());
      this.f_104888_.f_91074_.m_36324_().m_38705_(p_105098_.m_133250_());
      this.f_104888_.f_91074_.m_36324_().m_38717_(p_105098_.m_133251_());
   }

   public void m_6747_(ClientboundSetExperiencePacket p_105096_) {
      PacketUtils.m_131363_(p_105096_, this, this.f_104888_);
      this.f_104888_.f_91074_.m_108644_(p_105096_.m_133228_(), p_105096_.m_133231_(), p_105096_.m_133232_());
   }

   public void m_7992_(ClientboundRespawnPacket p_105066_) {
      PacketUtils.m_131363_(p_105066_, this, this.f_104888_);
      ResourceKey<Level> resourcekey = p_105066_.m_132955_();
      DimensionType dimensiontype = p_105066_.m_132952_();
      LocalPlayer localplayer = this.f_104888_.f_91074_;
      int i = localplayer.m_142049_();
      this.f_104891_ = false;
      if (resourcekey != localplayer.f_19853_.m_46472_()) {
         Scoreboard scoreboard = this.f_104889_.m_6188_();
         Map<String, MapItemSavedData> map = this.f_104889_.m_171684_();
         boolean flag = p_105066_.m_132959_();
         boolean flag1 = p_105066_.m_132960_();
         ClientLevel.ClientLevelData clientlevel$clientleveldata = new ClientLevel.ClientLevelData(this.f_104890_.m_5472_(), this.f_104890_.m_5466_(), flag1);
         this.f_104890_ = clientlevel$clientleveldata;
         this.f_104889_ = new ClientLevel(this, clientlevel$clientleveldata, resourcekey, dimensiontype, this.f_104897_, this.f_104888_::m_91307_, this.f_104888_.f_91060_, flag, p_105066_.m_132956_());
         this.f_104889_.m_104669_(scoreboard);
         this.f_104889_.m_171672_(map);
         this.f_104888_.m_91156_(this.f_104889_);
         this.f_104888_.m_91152_(new ReceivingLevelScreen());
      }

      String s = localplayer.m_108629_();
      this.f_104888_.f_91075_ = null;
      LocalPlayer localplayer1 = this.f_104888_.f_91072_.m_105250_(this.f_104889_, localplayer.m_108630_(), localplayer.m_108631_(), localplayer.m_6144_(), localplayer.m_20142_());
      localplayer1.m_20234_(i);
      this.f_104888_.f_91074_ = localplayer1;
      if (resourcekey != localplayer.f_19853_.m_46472_()) {
         this.f_104888_.m_91397_().m_120186_();
      }

      this.f_104888_.f_91075_ = localplayer1;
      localplayer1.m_20088_().m_135356_(localplayer.m_20088_().m_135384_());
      if (p_105066_.m_132961_()) {
         localplayer1.m_21204_().m_22159_(localplayer.m_21204_());
      }

      localplayer1.updateSyncFields(localplayer); // Forge: fix MC-10657
      localplayer1.m_172530_();
      localplayer1.m_108748_(s);
      net.minecraftforge.client.ForgeHooksClient.firePlayerRespawn(this.f_104888_.f_91072_, localplayer, localplayer1, localplayer1.f_108617_.m_6198_());
      this.f_104889_.m_104630_(i, localplayer1);
      localplayer1.m_146922_(-180.0F);
      localplayer1.f_108618_ = new KeyboardInput(this.f_104888_.f_91066_);
      this.f_104888_.f_91072_.m_105221_(localplayer1);
      localplayer1.m_36393_(localplayer.m_36330_());
      localplayer1.m_108711_(localplayer.m_108632_());
      if (this.f_104888_.f_91080_ instanceof DeathScreen) {
         this.f_104888_.m_91152_((Screen)null);
      }

      this.f_104888_.f_91072_.m_171805_(p_105066_.m_132957_(), p_105066_.m_132958_());
   }

   public void m_7345_(ClientboundExplodePacket p_105012_) {
      PacketUtils.m_131363_(p_105012_, this, this.f_104888_);
      Explosion explosion = new Explosion(this.f_104888_.f_91073_, (Entity)null, p_105012_.m_132132_(), p_105012_.m_132133_(), p_105012_.m_132134_(), p_105012_.m_132135_(), p_105012_.m_132136_());
      explosion.m_46075_(true);
      this.f_104888_.f_91074_.m_20256_(this.f_104888_.f_91074_.m_20184_().m_82520_((double)p_105012_.m_132127_(), (double)p_105012_.m_132130_(), (double)p_105012_.m_132131_()));
   }

   public void m_6905_(ClientboundHorseScreenOpenPacket p_105018_) {
      PacketUtils.m_131363_(p_105018_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105018_.m_132208_());
      if (entity instanceof AbstractHorse) {
         LocalPlayer localplayer = this.f_104888_.f_91074_;
         AbstractHorse abstracthorse = (AbstractHorse)entity;
         SimpleContainer simplecontainer = new SimpleContainer(p_105018_.m_132207_());
         HorseInventoryMenu horseinventorymenu = new HorseInventoryMenu(p_105018_.m_132204_(), localplayer.m_150109_(), simplecontainer, abstracthorse);
         localplayer.f_36096_ = horseinventorymenu;
         this.f_104888_.m_91152_(new HorseInventoryScreen(horseinventorymenu, localplayer.m_150109_(), abstracthorse));
      }

   }

   public void m_5980_(ClientboundOpenScreenPacket p_105042_) {
      PacketUtils.m_131363_(p_105042_, this, this.f_104888_);
      MenuScreens.m_96201_(p_105042_.m_132628_(), this.f_104888_, p_105042_.m_132625_(), p_105042_.m_132629_());
   }

   public void m_5735_(ClientboundContainerSetSlotPacket p_105000_) {
      PacketUtils.m_131363_(p_105000_, this, this.f_104888_);
      Player player = this.f_104888_.f_91074_;
      ItemStack itemstack = p_105000_.m_131995_();
      int i = p_105000_.m_131994_();
      this.f_104888_.m_91301_().m_120568_(itemstack);
      if (p_105000_.m_131991_() == -1) {
         if (!(this.f_104888_.f_91080_ instanceof CreativeModeInventoryScreen)) {
            player.f_36096_.m_142503_(itemstack);
         }
      } else if (p_105000_.m_131991_() == -2) {
         player.m_150109_().m_6836_(i, itemstack);
      } else {
         boolean flag = false;
         if (this.f_104888_.f_91080_ instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen creativemodeinventoryscreen = (CreativeModeInventoryScreen)this.f_104888_.f_91080_;
            flag = creativemodeinventoryscreen.m_98628_() != CreativeModeTab.f_40761_.m_40775_();
         }

         if (p_105000_.m_131991_() == 0 && InventoryMenu.m_150592_(i)) {
            if (!itemstack.m_41619_()) {
               ItemStack itemstack1 = player.f_36095_.m_38853_(i).m_7993_();
               if (itemstack1.m_41619_() || itemstack1.m_41613_() < itemstack.m_41613_()) {
                  itemstack.m_41754_(5);
               }
            }

            player.f_36095_.m_182406_(i, p_105000_.m_182716_(), itemstack);
         } else if (p_105000_.m_131991_() == player.f_36096_.f_38840_ && (p_105000_.m_131991_() != 0 || !flag)) {
            player.f_36096_.m_182406_(i, p_105000_.m_182716_(), itemstack);
         }
      }

   }

   public void m_6837_(ClientboundContainerSetContentPacket p_104996_) {
      PacketUtils.m_131363_(p_104996_, this, this.f_104888_);
      Player player = this.f_104888_.f_91074_;
      if (p_104996_.m_131954_() == 0) {
         player.f_36095_.m_182410_(p_104996_.m_182709_(), p_104996_.m_131957_(), p_104996_.m_182708_());
      } else if (p_104996_.m_131954_() == player.f_36096_.f_38840_) {
         player.f_36096_.m_182410_(p_104996_.m_182709_(), p_104996_.m_131957_(), p_104996_.m_182708_());
      }

   }

   public void m_8047_(ClientboundOpenSignEditorPacket p_105044_) {
      PacketUtils.m_131363_(p_105044_, this, this.f_104888_);
      BlockPos blockpos = p_105044_.m_132640_();
      BlockEntity blockentity = this.f_104889_.m_7702_(blockpos);
      if (!(blockentity instanceof SignBlockEntity)) {
         BlockState blockstate = this.f_104889_.m_8055_(blockpos);
         blockentity = new SignBlockEntity(blockpos, blockstate);
         blockentity.m_142339_(this.f_104889_);
      }

      this.f_104888_.f_91074_.m_7739_((SignBlockEntity)blockentity);
   }

   public void m_7545_(ClientboundBlockEntityDataPacket p_104976_) {
      PacketUtils.m_131363_(p_104976_, this, this.f_104888_);
      BlockPos blockpos = p_104976_.m_131704_();
      BlockEntity blockentity = this.f_104888_.f_91073_.m_7702_(blockpos);
      int i = p_104976_.m_131707_();
      boolean flag = i == 2 && blockentity instanceof CommandBlockEntity;
      if (i == 1 && blockentity instanceof SpawnerBlockEntity || flag || i == 3 && blockentity instanceof BeaconBlockEntity || i == 4 && blockentity instanceof SkullBlockEntity || i == 6 && blockentity instanceof BannerBlockEntity || i == 7 && blockentity instanceof StructureBlockEntity || i == 8 && blockentity instanceof TheEndGatewayBlockEntity || i == 9 && blockentity instanceof SignBlockEntity || i == 11 && blockentity instanceof BedBlockEntity || i == 5 && blockentity instanceof ConduitBlockEntity || i == 12 && blockentity instanceof JigsawBlockEntity || i == 13 && blockentity instanceof CampfireBlockEntity || i == 14 && blockentity instanceof BeehiveBlockEntity) {
         blockentity.m_142466_(p_104976_.m_131708_());
      }

      if (flag && this.f_104888_.f_91080_ instanceof CommandBlockEditScreen) {
         ((CommandBlockEditScreen)this.f_104888_.f_91080_).m_98398_();
      } else {
         if (blockentity == null) {
            f_104883_.error("Received invalid update packet for null tile entity at {} with data: {}", p_104976_.m_131704_(), p_104976_.m_131708_());
            return;
         }
         blockentity.onDataPacket(f_104885_, p_104976_);
      }

   }

   public void m_7257_(ClientboundContainerSetDataPacket p_104998_) {
      PacketUtils.m_131363_(p_104998_, this, this.f_104888_);
      Player player = this.f_104888_.f_91074_;
      if (player.f_36096_ != null && player.f_36096_.f_38840_ == p_104998_.m_131972_()) {
         player.f_36096_.m_7511_(p_104998_.m_131975_(), p_104998_.m_131976_());
      }

   }

   public void m_7277_(ClientboundSetEquipmentPacket p_105094_) {
      PacketUtils.m_131363_(p_105094_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105094_.m_133210_());
      if (entity != null) {
         p_105094_.m_133213_().forEach((p_104926_) -> {
            entity.m_8061_(p_104926_.getFirst(), p_104926_.getSecond());
         });
      }

   }

   public void m_7776_(ClientboundContainerClosePacket p_104994_) {
      PacketUtils.m_131363_(p_104994_, this, this.f_104888_);
      this.f_104888_.f_91074_.m_108763_();
   }

   public void m_7364_(ClientboundBlockEventPacket p_104978_) {
      PacketUtils.m_131363_(p_104978_, this, this.f_104888_);
      this.f_104888_.f_91073_.m_7696_(p_104978_.m_131725_(), p_104978_.m_131730_(), p_104978_.m_131728_(), p_104978_.m_131729_());
   }

   public void m_5943_(ClientboundBlockDestructionPacket p_104974_) {
      PacketUtils.m_131363_(p_104974_, this, this.f_104888_);
      this.f_104888_.f_91073_.m_6801_(p_104974_.m_131685_(), p_104974_.m_131688_(), p_104974_.m_131689_());
   }

   public void m_7616_(ClientboundGameEventPacket p_105016_) {
      PacketUtils.m_131363_(p_105016_, this, this.f_104888_);
      Player player = this.f_104888_.f_91074_;
      ClientboundGameEventPacket.Type clientboundgameeventpacket$type = p_105016_.m_132178_();
      float f = p_105016_.m_132181_();
      int i = Mth.m_14143_(f + 0.5F);
      if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132153_) {
         player.m_5661_(new TranslatableComponent("block.minecraft.spawn.not_valid"), false);
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132154_) {
         this.f_104889_.m_6106_().m_5565_(true);
         this.f_104889_.m_46734_(0.0F);
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132155_) {
         this.f_104889_.m_6106_().m_5565_(false);
         this.f_104889_.m_46734_(1.0F);
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132156_) {
         this.f_104888_.f_91072_.m_105279_(GameType.m_46393_(i));
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132157_) {
         if (i == 0) {
            this.f_104888_.f_91074_.f_108617_.m_104955_(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
            this.f_104888_.m_91152_(new ReceivingLevelScreen());
         } else if (i == 1) {
            this.f_104888_.m_91152_(new WinScreen(true, () -> {
               this.f_104888_.f_91074_.f_108617_.m_104955_(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
            }));
         }
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132158_) {
         Options options = this.f_104888_.f_91066_;
         if (f == 0.0F) {
            this.f_104888_.m_91152_(new DemoIntroScreen());
         } else if (f == 101.0F) {
            this.f_104888_.f_91065_.m_93076_().m_93785_(new TranslatableComponent("demo.help.movement", options.f_92085_.m_90863_(), options.f_92086_.m_90863_(), options.f_92087_.m_90863_(), options.f_92088_.m_90863_()));
         } else if (f == 102.0F) {
            this.f_104888_.f_91065_.m_93076_().m_93785_(new TranslatableComponent("demo.help.jump", options.f_92089_.m_90863_()));
         } else if (f == 103.0F) {
            this.f_104888_.f_91065_.m_93076_().m_93785_(new TranslatableComponent("demo.help.inventory", options.f_92092_.m_90863_()));
         } else if (f == 104.0F) {
            this.f_104888_.f_91065_.m_93076_().m_93785_(new TranslatableComponent("demo.day.6", options.f_92102_.m_90863_()));
         }
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132159_) {
         this.f_104889_.m_6263_(player, player.m_20185_(), player.m_20188_(), player.m_20189_(), SoundEvents.f_11686_, SoundSource.PLAYERS, 0.18F, 0.45F);
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132160_) {
         this.f_104889_.m_46734_(f);
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132161_) {
         this.f_104889_.m_46707_(f);
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132162_) {
         this.f_104889_.m_6263_(player, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12295_, SoundSource.NEUTRAL, 1.0F, 1.0F);
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132163_) {
         this.f_104889_.m_7106_(ParticleTypes.f_123807_, player.m_20185_(), player.m_20186_(), player.m_20189_(), 0.0D, 0.0D, 0.0D);
         if (i == 1) {
            this.f_104889_.m_6263_(player, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11880_, SoundSource.HOSTILE, 1.0F, 1.0F);
         }
      } else if (clientboundgameeventpacket$type == ClientboundGameEventPacket.f_132164_) {
         this.f_104888_.f_91074_.m_108711_(f == 0.0F);
      }

   }

   public void m_7633_(ClientboundMapItemDataPacket p_105032_) {
      PacketUtils.m_131363_(p_105032_, this, this.f_104888_);
      MapRenderer maprenderer = this.f_104888_.f_91063_.m_109151_();
      int i = p_105032_.m_132445_();
      String s = MapItem.m_42848_(i);
      MapItemSavedData mapitemsaveddata = this.f_104888_.f_91073_.m_7489_(s);
      if (mapitemsaveddata == null) {
         mapitemsaveddata = MapItemSavedData.m_164776_(p_105032_.m_178982_(), p_105032_.m_178983_(), this.f_104888_.f_91073_.m_46472_());
         this.f_104888_.f_91073_.m_142325_(s, mapitemsaveddata);
      }

      p_105032_.m_132437_(mapitemsaveddata);
      maprenderer.m_168765_(i, mapitemsaveddata);
   }

   public void m_7704_(ClientboundLevelEventPacket p_105024_) {
      PacketUtils.m_131363_(p_105024_, this, this.f_104888_);
      if (p_105024_.m_132274_()) {
         this.f_104888_.f_91073_.m_6798_(p_105024_.m_132277_(), p_105024_.m_132279_(), p_105024_.m_132278_());
      } else {
         this.f_104888_.f_91073_.m_46796_(p_105024_.m_132277_(), p_105024_.m_132279_(), p_105024_.m_132278_());
      }

   }

   public void m_5498_(ClientboundUpdateAdvancementsPacket p_105126_) {
      PacketUtils.m_131363_(p_105126_, this, this.f_104888_);
      this.f_104893_.m_104399_(p_105126_);
   }

   public void m_7553_(ClientboundSelectAdvancementsTabPacket p_105072_) {
      PacketUtils.m_131363_(p_105072_, this, this.f_104888_);
      ResourceLocation resourcelocation = p_105072_.m_133013_();
      if (resourcelocation == null) {
         this.f_104893_.m_104401_((Advancement)null, false);
      } else {
         Advancement advancement = this.f_104893_.m_104396_().m_139337_(resourcelocation);
         this.f_104893_.m_104401_(advancement, false);
      }

   }

   public void m_7443_(ClientboundCommandsPacket p_104990_) {
      PacketUtils.m_131363_(p_104990_, this, this.f_104888_);
      this.f_104899_ = new CommandDispatcher<>(p_104990_.m_131884_());
   }

   public void m_7183_(ClientboundStopSoundPacket p_105116_) {
      PacketUtils.m_131363_(p_105116_, this, this.f_104888_);
      this.f_104888_.m_91106_().m_120386_(p_105116_.m_133476_(), p_105116_.m_133479_());
   }

   public void m_7589_(ClientboundCommandSuggestionsPacket p_104988_) {
      PacketUtils.m_131363_(p_104988_, this, this.f_104888_);
      this.f_104894_.m_105171_(p_104988_.m_131854_(), p_104988_.m_131857_());
   }

   public void m_6327_(ClientboundUpdateRecipesPacket p_105132_) {
      PacketUtils.m_131363_(p_105132_, this, this.f_104888_);
      this.f_104900_.m_44024_(p_105132_.m_133644_());
      MutableSearchTree<RecipeCollection> mutablesearchtree = this.f_104888_.m_91171_(SearchRegistry.f_119943_);
      mutablesearchtree.m_7716_();
      ClientRecipeBook clientrecipebook = this.f_104888_.f_91074_.m_108631_();
      clientrecipebook.m_90625_(this.f_104900_.m_44051_());
      clientrecipebook.m_90639_().forEach(mutablesearchtree::m_8080_);
      mutablesearchtree.m_7729_();
      net.minecraftforge.client.ForgeHooksClient.onRecipesUpdated(this.f_104900_);
   }

   public void m_7244_(ClientboundPlayerLookAtPacket p_105054_) {
      PacketUtils.m_131363_(p_105054_, this, this.f_104888_);
      Vec3 vec3 = p_105054_.m_132785_(this.f_104889_);
      if (vec3 != null) {
         this.f_104888_.f_91074_.m_7618_(p_105054_.m_132793_(), vec3);
      }

   }

   public void m_6148_(ClientboundTagQueryPacket p_105120_) {
      PacketUtils.m_131363_(p_105120_, this, this.f_104888_);
      if (!this.f_104896_.m_90705_(p_105120_.m_133506_(), p_105120_.m_133509_())) {
         f_104883_.debug("Got unhandled response to tag query {}", (int)p_105120_.m_133506_());
      }

   }

   public void m_7271_(ClientboundAwardStatsPacket p_104970_) {
      PacketUtils.m_131363_(p_104970_, this, this.f_104888_);

      for(Entry<Stat<?>, Integer> entry : p_104970_.m_131643_().entrySet()) {
         Stat<?> stat = entry.getKey();
         int i = entry.getValue();
         this.f_104888_.f_91074_.m_108630_().m_6085_(this.f_104888_.f_91074_, stat, i);
      }

      if (this.f_104888_.f_91080_ instanceof StatsUpdateListener) {
         ((StatsUpdateListener)this.f_104888_.f_91080_).m_7819_();
      }

   }

   public void m_8076_(ClientboundRecipePacket p_105058_) {
      PacketUtils.m_131363_(p_105058_, this, this.f_104888_);
      ClientRecipeBook clientrecipebook = this.f_104888_.f_91074_.m_108631_();
      clientrecipebook.m_12687_(p_105058_.m_132869_());
      ClientboundRecipePacket.State clientboundrecipepacket$state = p_105058_.m_132870_();
      switch(clientboundrecipepacket$state) {
      case REMOVE:
         for(ResourceLocation resourcelocation3 : p_105058_.m_132865_()) {
            this.f_104900_.m_44043_(resourcelocation3).ifPresent(clientrecipebook::m_12713_);
         }
         break;
      case INIT:
         for(ResourceLocation resourcelocation1 : p_105058_.m_132865_()) {
            this.f_104900_.m_44043_(resourcelocation1).ifPresent(clientrecipebook::m_12700_);
         }

         for(ResourceLocation resourcelocation2 : p_105058_.m_132868_()) {
            this.f_104900_.m_44043_(resourcelocation2).ifPresent(clientrecipebook::m_12723_);
         }
         break;
      case ADD:
         for(ResourceLocation resourcelocation : p_105058_.m_132865_()) {
            this.f_104900_.m_44043_(resourcelocation).ifPresent((p_104934_) -> {
               clientrecipebook.m_12700_(p_104934_);
               clientrecipebook.m_12723_(p_104934_);
               RecipeToast.m_94817_(this.f_104888_.m_91300_(), p_104934_);
            });
         }
      }

      clientrecipebook.m_90639_().forEach((p_104937_) -> {
         p_104937_.m_100499_(clientrecipebook);
      });
      if (this.f_104888_.f_91080_ instanceof RecipeUpdateListener) {
         ((RecipeUpdateListener)this.f_104888_.f_91080_).m_6916_();
      }

   }

   public void m_7915_(ClientboundUpdateMobEffectPacket p_105130_) {
      PacketUtils.m_131363_(p_105130_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105130_.m_133622_());
      if (entity instanceof LivingEntity) {
         MobEffect mobeffect = MobEffect.m_19453_(p_105130_.m_133623_() & 0xFF);
         if (mobeffect != null) {
            MobEffectInstance mobeffectinstance = new MobEffectInstance(mobeffect, p_105130_.m_133625_(), p_105130_.m_133624_(), p_105130_.m_133627_(), p_105130_.m_133626_(), p_105130_.m_133628_());
            mobeffectinstance.m_19562_(p_105130_.m_133619_());
            ((LivingEntity)entity).m_147215_(mobeffectinstance, (Entity)null);
         }
      }
   }

   public void m_5859_(ClientboundUpdateTagsPacket p_105134_) {
      PacketUtils.m_131363_(p_105134_, this, this.f_104888_);
      TagContainer tagcontainer = TagContainer.m_144449_(this.f_104903_, p_105134_.m_179482_());
      Multimap<ResourceKey<? extends Registry<?>>, ResourceLocation> multimap = StaticTags.m_13283_(tagcontainer);
      if (!multimap.isEmpty()) {
         f_104883_.warn("Incomplete server tags, disconnecting. Missing: {}", (Object)multimap);
         this.f_104885_.m_129507_(new TranslatableComponent("multiplayer.disconnect.missing_tags"));
      } else {
         tagcontainer = net.minecraftforge.common.ForgeTagHandler.reinjectOptionalTags(tagcontainer);
         this.f_104895_ = tagcontainer;
         if (!this.f_104885_.m_129531_()) {
            tagcontainer.m_13431_();
         }

         this.f_104888_.m_91171_(SearchRegistry.f_119942_).m_7729_();
      }
   }

   public void m_142234_(ClientboundPlayerCombatEndPacket p_171771_) {
   }

   public void m_142058_(ClientboundPlayerCombatEnterPacket p_171773_) {
   }

   public void m_142747_(ClientboundPlayerCombatKillPacket p_171775_) {
      PacketUtils.m_131363_(p_171775_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_171775_.m_179078_());
      if (entity == this.f_104888_.f_91074_) {
         if (this.f_104888_.f_91074_.m_108632_()) {
            this.f_104888_.m_91152_(new DeathScreen(p_171775_.m_179079_(), this.f_104889_.m_6106_().m_5466_()));
         } else {
            this.f_104888_.f_91074_.m_7583_();
         }
      }

   }

   public void m_6664_(ClientboundChangeDifficultyPacket p_104984_) {
      PacketUtils.m_131363_(p_104984_, this, this.f_104888_);
      this.f_104890_.m_104851_(p_104984_.m_131820_());
      this.f_104890_.m_104858_(p_104984_.m_131817_());
   }

   public void m_6447_(ClientboundSetCameraPacket p_105076_) {
      PacketUtils.m_131363_(p_105076_, this, this.f_104888_);
      Entity entity = p_105076_.m_133059_(this.f_104889_);
      if (entity != null) {
         this.f_104888_.m_91118_(entity);
      }

   }

   public void m_142237_(ClientboundInitializeBorderPacket p_171767_) {
      PacketUtils.m_131363_(p_171767_, this, this.f_104888_);
      WorldBorder worldborder = this.f_104889_.m_6857_();
      worldborder.m_61949_(p_171767_.m_178886_(), p_171767_.m_178887_());
      long i = p_171767_.m_178890_();
      if (i > 0L) {
         worldborder.m_61919_(p_171767_.m_178889_(), p_171767_.m_178888_(), i);
      } else {
         worldborder.m_61917_(p_171767_.m_178888_());
      }

      worldborder.m_61923_(p_171767_.m_178891_());
      worldborder.m_61952_(p_171767_.m_178893_());
      worldborder.m_61944_(p_171767_.m_178892_());
   }

   public void m_142612_(ClientboundSetBorderCenterPacket p_171781_) {
      PacketUtils.m_131363_(p_171781_, this, this.f_104888_);
      this.f_104889_.m_6857_().m_61949_(p_171781_.m_179224_(), p_171781_.m_179223_());
   }

   public void m_142686_(ClientboundSetBorderLerpSizePacket p_171783_) {
      PacketUtils.m_131363_(p_171783_, this, this.f_104888_);
      this.f_104889_.m_6857_().m_61919_(p_171783_.m_179238_(), p_171783_.m_179239_(), p_171783_.m_179240_());
   }

   public void m_142238_(ClientboundSetBorderSizePacket p_171785_) {
      PacketUtils.m_131363_(p_171785_, this, this.f_104888_);
      this.f_104889_.m_6857_().m_61917_(p_171785_.m_179252_());
   }

   public void m_142696_(ClientboundSetBorderWarningDistancePacket p_171789_) {
      PacketUtils.m_131363_(p_171789_, this, this.f_104888_);
      this.f_104889_.m_6857_().m_61952_(p_171789_.m_179276_());
   }

   public void m_142056_(ClientboundSetBorderWarningDelayPacket p_171787_) {
      PacketUtils.m_131363_(p_171787_, this, this.f_104888_);
      this.f_104889_.m_6857_().m_61944_(p_171787_.m_179264_());
   }

   public void m_142766_(ClientboundClearTitlesPacket p_171765_) {
      PacketUtils.m_131363_(p_171765_, this, this.f_104888_);
      this.f_104888_.f_91065_.m_168713_();
      if (p_171765_.m_178788_()) {
         this.f_104888_.f_91065_.m_93006_();
      }

   }

   public void m_142456_(ClientboundSetActionBarTextPacket p_171779_) {
      PacketUtils.m_131363_(p_171779_, this, this.f_104888_);
      this.f_104888_.f_91065_.m_93063_(p_171779_.m_179210_(), false);
   }

   public void m_142442_(ClientboundSetTitleTextPacket p_171793_) {
      PacketUtils.m_131363_(p_171793_, this, this.f_104888_);
      this.f_104888_.f_91065_.m_168714_(p_171793_.m_179399_());
   }

   public void m_141913_(ClientboundSetSubtitleTextPacket p_171791_) {
      PacketUtils.m_131363_(p_171791_, this, this.f_104888_);
      this.f_104888_.f_91065_.m_168711_(p_171791_.m_179385_());
   }

   public void m_142185_(ClientboundSetTitlesAnimationPacket p_171795_) {
      PacketUtils.m_131363_(p_171795_, this, this.f_104888_);
      this.f_104888_.f_91065_.m_168684_(p_171795_.m_179415_(), p_171795_.m_179416_(), p_171795_.m_179417_());
   }

   public void m_6235_(ClientboundTabListPacket p_105118_) {
      PacketUtils.m_131363_(p_105118_, this, this.f_104888_);
      this.f_104888_.f_91065_.m_93088_().m_94558_(p_105118_.m_133489_().getString().isEmpty() ? null : p_105118_.m_133489_());
      this.f_104888_.f_91065_.m_93088_().m_94554_(p_105118_.m_133492_().getString().isEmpty() ? null : p_105118_.m_133492_());
   }

   public void m_6476_(ClientboundRemoveMobEffectPacket p_105062_) {
      PacketUtils.m_131363_(p_105062_, this, this.f_104888_);
      Entity entity = p_105062_.m_132901_(this.f_104889_);
      if (entity instanceof LivingEntity) {
         ((LivingEntity)entity).m_6234_(p_105062_.m_132909_());
      }

   }

   public void m_7039_(ClientboundPlayerInfoPacket p_105052_) {
      PacketUtils.m_131363_(p_105052_, this, this.f_104888_);

      for(ClientboundPlayerInfoPacket.PlayerUpdate clientboundplayerinfopacket$playerupdate : p_105052_.m_132732_()) {
         if (p_105052_.m_132735_() == ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER) {
            this.f_104888_.m_91266_().m_100690_(clientboundplayerinfopacket$playerupdate.m_132763_().getId());
            this.f_104892_.remove(clientboundplayerinfopacket$playerupdate.m_132763_().getId());
         } else {
            PlayerInfo playerinfo = this.f_104892_.get(clientboundplayerinfopacket$playerupdate.m_132763_().getId());
            if (p_105052_.m_132735_() == ClientboundPlayerInfoPacket.Action.ADD_PLAYER) {
               playerinfo = new PlayerInfo(clientboundplayerinfopacket$playerupdate);
               this.f_104892_.put(playerinfo.m_105312_().getId(), playerinfo);
               this.f_104888_.m_91266_().m_100676_(playerinfo);
            }

            if (playerinfo != null) {
               switch(p_105052_.m_132735_()) {
               case ADD_PLAYER:
                  playerinfo.m_105317_(clientboundplayerinfopacket$playerupdate.m_132765_());
                  playerinfo.m_105313_(clientboundplayerinfopacket$playerupdate.m_132764_());
                  playerinfo.m_105323_(clientboundplayerinfopacket$playerupdate.m_132766_());
                  break;
               case UPDATE_GAME_MODE:
                  playerinfo.m_105317_(clientboundplayerinfopacket$playerupdate.m_132765_());
                  break;
               case UPDATE_LATENCY:
                  playerinfo.m_105313_(clientboundplayerinfopacket$playerupdate.m_132764_());
                  break;
               case UPDATE_DISPLAY_NAME:
                  playerinfo.m_105323_(clientboundplayerinfopacket$playerupdate.m_132766_());
               }
            }
         }
      }

   }

   public void m_7231_(ClientboundKeepAlivePacket p_105020_) {
      this.m_104955_(new ServerboundKeepAlivePacket(p_105020_.m_132219_()));
   }

   public void m_5767_(ClientboundPlayerAbilitiesPacket p_105048_) {
      PacketUtils.m_131363_(p_105048_, this, this.f_104888_);
      Player player = this.f_104888_.f_91074_;
      player.m_150110_().f_35935_ = p_105048_.m_132677_();
      player.m_150110_().f_35937_ = p_105048_.m_132679_();
      player.m_150110_().f_35934_ = p_105048_.m_132674_();
      player.m_150110_().f_35936_ = p_105048_.m_132678_();
      player.m_150110_().m_35943_(p_105048_.m_132680_());
      player.m_150110_().m_35948_(p_105048_.m_132681_());
   }

   public void m_8068_(ClientboundSoundPacket p_105114_) {
      PacketUtils.m_131363_(p_105114_, this, this.f_104888_);
      this.f_104888_.f_91073_.m_6263_(this.f_104888_.f_91074_, p_105114_.m_133459_(), p_105114_.m_133460_(), p_105114_.m_133461_(), p_105114_.m_133455_(), p_105114_.m_133458_(), p_105114_.m_133462_(), p_105114_.m_133463_());
   }

   public void m_5863_(ClientboundSoundEntityPacket p_105112_) {
      PacketUtils.m_131363_(p_105112_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105112_.m_133430_());
      if (entity != null) {
         this.f_104888_.f_91073_.m_6269_(this.f_104888_.f_91074_, entity, p_105112_.m_133426_(), p_105112_.m_133429_(), p_105112_.m_133431_(), p_105112_.m_133432_());
      }
   }

   public void m_6490_(ClientboundCustomSoundPacket p_105006_) {
      PacketUtils.m_131363_(p_105006_, this, this.f_104888_);
      this.f_104888_.m_91106_().m_120367_(new SimpleSoundInstance(p_105006_.m_132066_(), p_105006_.m_132069_(), p_105006_.m_132073_(), p_105006_.m_132074_(), false, 0, SoundInstance.Attenuation.LINEAR, p_105006_.m_132070_(), p_105006_.m_132071_(), p_105006_.m_132072_(), false));
   }

   public void m_5587_(ClientboundResourcePackPacket p_105064_) {
      String s = p_105064_.m_132924_();
      String s1 = p_105064_.m_132927_();
      boolean flag = p_105064_.m_179188_();
      if (this.m_105138_(s)) {
         if (s.startsWith("level://")) {
            try {
               String s2 = URLDecoder.decode(s.substring("level://".length()), StandardCharsets.UTF_8.toString());
               File file1 = new File(this.f_104888_.f_91069_, "saves");
               File file2 = new File(file1, s2);
               if (file2.isFile()) {
                  this.m_105135_(ServerboundResourcePackPacket.Action.ACCEPTED);
                  CompletableFuture<?> completablefuture = this.f_104888_.m_91100_().m_118566_(file2, PackSource.f_10529_);
                  this.m_104951_(completablefuture);
                  return;
               }
            } catch (UnsupportedEncodingException unsupportedencodingexception) {
            }

            this.m_105135_(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
         } else {
            ServerData serverdata = this.f_104888_.m_91089_();
            if (serverdata != null && serverdata.m_105387_() == ServerData.ServerPackStatus.ENABLED) {
               this.m_105135_(ServerboundResourcePackPacket.Action.ACCEPTED);
               this.m_104951_(this.f_104888_.m_91100_().m_174813_(s, s1, true));
            } else if (serverdata != null && serverdata.m_105387_() != ServerData.ServerPackStatus.PROMPT && (!flag || serverdata.m_105387_() != ServerData.ServerPackStatus.DISABLED)) {
               this.m_105135_(ServerboundResourcePackPacket.Action.DECLINED);
               if (flag) {
                  this.f_104885_.m_129507_(new TranslatableComponent("multiplayer.requiredTexturePrompt.disconnect"));
               }
            } else {
               this.f_104888_.execute(() -> {
                  this.f_104888_.m_91152_(new ConfirmScreen((p_171758_) -> {
                     this.f_104888_.m_91152_((Screen)null);
                     ServerData serverdata1 = this.f_104888_.m_91089_();
                     if (p_171758_) {
                        if (serverdata1 != null) {
                           serverdata1.m_105379_(ServerData.ServerPackStatus.ENABLED);
                        }

                        this.m_105135_(ServerboundResourcePackPacket.Action.ACCEPTED);
                        this.m_104951_(this.f_104888_.m_91100_().m_174813_(s, s1, true));
                     } else {
                        this.m_105135_(ServerboundResourcePackPacket.Action.DECLINED);
                        if (flag) {
                           this.f_104885_.m_129507_(new TranslatableComponent("multiplayer.requiredTexturePrompt.disconnect"));
                        } else if (serverdata1 != null) {
                           serverdata1.m_105379_(ServerData.ServerPackStatus.DISABLED);
                        }
                     }

                     if (serverdata1 != null) {
                        ServerList.m_105446_(serverdata1);
                     }

                  }, flag ? new TranslatableComponent("multiplayer.requiredTexturePrompt.line1") : new TranslatableComponent("multiplayer.texturePrompt.line1"), m_171759_((Component)(flag ? (new TranslatableComponent("multiplayer.requiredTexturePrompt.line2")).m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.BOLD}) : new TranslatableComponent("multiplayer.texturePrompt.line2")), p_105064_.m_179189_()), flag ? CommonComponents.f_130659_ : CommonComponents.f_130657_, (Component)(flag ? new TranslatableComponent("menu.disconnect") : CommonComponents.f_130658_)));
               });
            }

         }
      }
   }

   private static Component m_171759_(Component p_171760_, @Nullable Component p_171761_) {
      return (Component)(p_171761_ == null ? p_171760_ : new TranslatableComponent("multiplayer.texturePrompt.serverPrompt", p_171760_, p_171761_));
   }

   private boolean m_105138_(String p_105139_) {
      try {
         URI uri = new URI(p_105139_);
         String s = uri.getScheme();
         boolean flag = "level".equals(s);
         if (!"http".equals(s) && !"https".equals(s) && !flag) {
            throw new URISyntaxException(p_105139_, "Wrong protocol");
         } else if (!flag || !p_105139_.contains("..") && p_105139_.endsWith("/resources.zip")) {
            return true;
         } else {
            throw new URISyntaxException(p_105139_, "Invalid levelstorage resourcepack path");
         }
      } catch (URISyntaxException urisyntaxexception) {
         this.m_105135_(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
         return false;
      }
   }

   private void m_104951_(CompletableFuture<?> p_104952_) {
      p_104952_.thenRun(() -> {
         this.m_105135_(ServerboundResourcePackPacket.Action.SUCCESSFULLY_LOADED);
      }).exceptionally((p_104948_) -> {
         this.m_105135_(ServerboundResourcePackPacket.Action.FAILED_DOWNLOAD);
         return null;
      });
   }

   private void m_105135_(ServerboundResourcePackPacket.Action p_105136_) {
      this.f_104885_.m_129512_(new ServerboundResourcePackPacket(p_105136_));
   }

   public void m_7685_(ClientboundBossEventPacket p_104982_) {
      PacketUtils.m_131363_(p_104982_, this, this.f_104888_);
      this.f_104888_.f_91065_.m_93090_().m_93711_(p_104982_);
   }

   public void m_7701_(ClientboundCooldownPacket p_105002_) {
      PacketUtils.m_131363_(p_105002_, this, this.f_104888_);
      if (p_105002_.m_132011_() == 0) {
         this.f_104888_.f_91074_.m_36335_().m_41527_(p_105002_.m_132008_());
      } else {
         this.f_104888_.f_91074_.m_36335_().m_41524_(p_105002_.m_132008_(), p_105002_.m_132011_());
      }

   }

   public void m_7410_(ClientboundMoveVehiclePacket p_105038_) {
      PacketUtils.m_131363_(p_105038_, this, this.f_104888_);
      Entity entity = this.f_104888_.f_91074_.m_20201_();
      if (entity != this.f_104888_.f_91074_ && entity.m_6109_()) {
         entity.m_19890_(p_105038_.m_132591_(), p_105038_.m_132594_(), p_105038_.m_132595_(), p_105038_.m_132596_(), p_105038_.m_132597_());
         this.f_104885_.m_129512_(new ServerboundMoveVehiclePacket(entity));
      }

   }

   public void m_6503_(ClientboundOpenBookPacket p_105040_) {
      PacketUtils.m_131363_(p_105040_, this, this.f_104888_);
      ItemStack itemstack = this.f_104888_.f_91074_.m_21120_(p_105040_.m_132608_());
      if (itemstack.m_150930_(Items.f_42615_)) {
         this.f_104888_.m_91152_(new BookViewScreen(new BookViewScreen.WrittenBookAccess(itemstack)));
      }

   }

   public void m_7413_(ClientboundCustomPayloadPacket p_105004_) {
      PacketUtils.m_131363_(p_105004_, this, this.f_104888_);
      ResourceLocation resourcelocation = p_105004_.m_132042_();
      FriendlyByteBuf friendlybytebuf = null;

      try {
         friendlybytebuf = p_105004_.m_132045_();
         if (ClientboundCustomPayloadPacket.f_132012_.equals(resourcelocation)) {
            this.f_104888_.f_91074_.m_108748_(friendlybytebuf.m_130277_());
         } else if (ClientboundCustomPayloadPacket.f_132013_.equals(resourcelocation)) {
            int i = friendlybytebuf.readInt();
            float f = friendlybytebuf.readFloat();
            Path path = Path.m_77390_(friendlybytebuf);
            this.f_104888_.f_91064_.f_113413_.m_113611_(i, path, f);
         } else if (ClientboundCustomPayloadPacket.f_132014_.equals(resourcelocation)) {
            long k1 = friendlybytebuf.m_130258_();
            BlockPos blockpos9 = friendlybytebuf.m_130135_();
            ((NeighborsUpdateRenderer)this.f_104888_.f_91064_.f_113418_).m_113596_(k1, blockpos9);
         } else if (ClientboundCustomPayloadPacket.f_132016_.equals(resourcelocation)) {
            DimensionType dimensiontype = this.f_104903_.m_175515_(Registry.f_122818_).m_7745_(friendlybytebuf.m_130281_());
            BoundingBox boundingbox = new BoundingBox(friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt());
            int k3 = friendlybytebuf.readInt();
            List<BoundingBox> list = Lists.newArrayList();
            List<Boolean> list1 = Lists.newArrayList();

            for(int j = 0; j < k3; ++j) {
               list.add(new BoundingBox(friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt(), friendlybytebuf.readInt()));
               list1.add(friendlybytebuf.readBoolean());
            }

            this.f_104888_.f_91064_.f_113420_.m_113682_(boundingbox, list, list1, dimensiontype);
         } else if (ClientboundCustomPayloadPacket.f_132017_.equals(resourcelocation)) {
            ((WorldGenAttemptRenderer)this.f_104888_.f_91064_.f_113422_).m_113737_(friendlybytebuf.m_130135_(), friendlybytebuf.readFloat(), friendlybytebuf.readFloat(), friendlybytebuf.readFloat(), friendlybytebuf.readFloat(), friendlybytebuf.readFloat());
         } else if (ClientboundCustomPayloadPacket.f_132021_.equals(resourcelocation)) {
            int l1 = friendlybytebuf.readInt();

            for(int j2 = 0; j2 < l1; ++j2) {
               this.f_104888_.f_91064_.f_113426_.m_113709_(friendlybytebuf.m_130157_());
            }

            int k2 = friendlybytebuf.readInt();

            for(int l3 = 0; l3 < k2; ++l3) {
               this.f_104888_.f_91064_.f_113426_.m_113711_(friendlybytebuf.m_130157_());
            }
         } else if (ClientboundCustomPayloadPacket.f_132019_.equals(resourcelocation)) {
            BlockPos blockpos2 = friendlybytebuf.m_130135_();
            String s8 = friendlybytebuf.m_130277_();
            int i4 = friendlybytebuf.readInt();
            BrainDebugRenderer.PoiInfo braindebugrenderer$poiinfo = new BrainDebugRenderer.PoiInfo(blockpos2, s8, i4);
            this.f_104888_.f_91064_.f_113425_.m_113226_(braindebugrenderer$poiinfo);
         } else if (ClientboundCustomPayloadPacket.f_132020_.equals(resourcelocation)) {
            BlockPos blockpos3 = friendlybytebuf.m_130135_();
            this.f_104888_.f_91064_.f_113425_.m_113228_(blockpos3);
         } else if (ClientboundCustomPayloadPacket.f_132018_.equals(resourcelocation)) {
            BlockPos blockpos4 = friendlybytebuf.m_130135_();
            int l2 = friendlybytebuf.readInt();
            this.f_104888_.f_91064_.f_113425_.m_113230_(blockpos4, l2);
         } else if (ClientboundCustomPayloadPacket.f_132022_.equals(resourcelocation)) {
            BlockPos blockpos5 = friendlybytebuf.m_130135_();
            int i3 = friendlybytebuf.readInt();
            int j4 = friendlybytebuf.readInt();
            List<GoalSelectorDebugRenderer.DebugGoal> list2 = Lists.newArrayList();

            for(int l5 = 0; l5 < j4; ++l5) {
               int i6 = friendlybytebuf.readInt();
               boolean flag = friendlybytebuf.readBoolean();
               String s = friendlybytebuf.m_130136_(255);
               list2.add(new GoalSelectorDebugRenderer.DebugGoal(blockpos5, i6, s, flag));
            }

            this.f_104888_.f_91064_.f_113429_.m_113548_(i3, list2);
         } else if (ClientboundCustomPayloadPacket.f_132028_.equals(resourcelocation)) {
            int i2 = friendlybytebuf.readInt();
            Collection<BlockPos> collection = Lists.newArrayList();

            for(int k4 = 0; k4 < i2; ++k4) {
               collection.add(friendlybytebuf.m_130135_());
            }

            this.f_104888_.f_91064_.f_113428_.m_113663_(collection);
         } else if (ClientboundCustomPayloadPacket.f_132023_.equals(resourcelocation)) {
            double d0 = friendlybytebuf.readDouble();
            double d2 = friendlybytebuf.readDouble();
            double d4 = friendlybytebuf.readDouble();
            Position position = new PositionImpl(d0, d2, d4);
            UUID uuid = friendlybytebuf.m_130259_();
            int k = friendlybytebuf.readInt();
            String s1 = friendlybytebuf.m_130277_();
            String s2 = friendlybytebuf.m_130277_();
            int l = friendlybytebuf.readInt();
            float f1 = friendlybytebuf.readFloat();
            float f2 = friendlybytebuf.readFloat();
            String s3 = friendlybytebuf.m_130277_();
            boolean flag1 = friendlybytebuf.readBoolean();
            Path path1;
            if (flag1) {
               path1 = Path.m_77390_(friendlybytebuf);
            } else {
               path1 = null;
            }

            boolean flag2 = friendlybytebuf.readBoolean();
            BrainDebugRenderer.BrainDump braindebugrenderer$braindump = new BrainDebugRenderer.BrainDump(uuid, k, s1, s2, l, f1, f2, position, s3, path1, flag2);
            int i1 = friendlybytebuf.m_130242_();

            for(int j1 = 0; j1 < i1; ++j1) {
               String s4 = friendlybytebuf.m_130277_();
               braindebugrenderer$braindump.f_113304_.add(s4);
            }

            int l7 = friendlybytebuf.m_130242_();

            for(int i8 = 0; i8 < l7; ++i8) {
               String s5 = friendlybytebuf.m_130277_();
               braindebugrenderer$braindump.f_113305_.add(s5);
            }

            int j8 = friendlybytebuf.m_130242_();

            for(int k8 = 0; k8 < j8; ++k8) {
               String s6 = friendlybytebuf.m_130277_();
               braindebugrenderer$braindump.f_113306_.add(s6);
            }

            int l8 = friendlybytebuf.m_130242_();

            for(int i9 = 0; i9 < l8; ++i9) {
               BlockPos blockpos = friendlybytebuf.m_130135_();
               braindebugrenderer$braindump.f_113308_.add(blockpos);
            }

            int j9 = friendlybytebuf.m_130242_();

            for(int k9 = 0; k9 < j9; ++k9) {
               BlockPos blockpos1 = friendlybytebuf.m_130135_();
               braindebugrenderer$braindump.f_113309_.add(blockpos1);
            }

            int l9 = friendlybytebuf.m_130242_();

            for(int i10 = 0; i10 < l9; ++i10) {
               String s7 = friendlybytebuf.m_130277_();
               braindebugrenderer$braindump.f_113307_.add(s7);
            }

            this.f_104888_.f_91064_.f_113425_.m_113219_(braindebugrenderer$braindump);
         } else if (ClientboundCustomPayloadPacket.f_132024_.equals(resourcelocation)) {
            double d1 = friendlybytebuf.readDouble();
            double d3 = friendlybytebuf.readDouble();
            double d5 = friendlybytebuf.readDouble();
            Position position1 = new PositionImpl(d1, d3, d5);
            UUID uuid1 = friendlybytebuf.m_130259_();
            int j6 = friendlybytebuf.readInt();
            boolean flag4 = friendlybytebuf.readBoolean();
            BlockPos blockpos10 = null;
            if (flag4) {
               blockpos10 = friendlybytebuf.m_130135_();
            }

            boolean flag5 = friendlybytebuf.readBoolean();
            BlockPos blockpos11 = null;
            if (flag5) {
               blockpos11 = friendlybytebuf.m_130135_();
            }

            int k6 = friendlybytebuf.readInt();
            boolean flag6 = friendlybytebuf.readBoolean();
            Path path2 = null;
            if (flag6) {
               path2 = Path.m_77390_(friendlybytebuf);
            }

            BeeDebugRenderer.BeeInfo beedebugrenderer$beeinfo = new BeeDebugRenderer.BeeInfo(uuid1, j6, position1, path2, blockpos10, blockpos11, k6);
            int l6 = friendlybytebuf.m_130242_();

            for(int i7 = 0; i7 < l6; ++i7) {
               String s11 = friendlybytebuf.m_130277_();
               beedebugrenderer$beeinfo.f_113164_.add(s11);
            }

            int j7 = friendlybytebuf.m_130242_();

            for(int k7 = 0; k7 < j7; ++k7) {
               BlockPos blockpos12 = friendlybytebuf.m_130135_();
               beedebugrenderer$beeinfo.f_113165_.add(blockpos12);
            }

            this.f_104888_.f_91064_.f_113427_.m_113066_(beedebugrenderer$beeinfo);
         } else if (ClientboundCustomPayloadPacket.f_132025_.equals(resourcelocation)) {
            BlockPos blockpos6 = friendlybytebuf.m_130135_();
            String s9 = friendlybytebuf.m_130277_();
            int l4 = friendlybytebuf.readInt();
            int j5 = friendlybytebuf.readInt();
            boolean flag3 = friendlybytebuf.readBoolean();
            BeeDebugRenderer.HiveInfo beedebugrenderer$hiveinfo = new BeeDebugRenderer.HiveInfo(blockpos6, s9, l4, j5, flag3, this.f_104889_.m_46467_());
            this.f_104888_.f_91064_.f_113427_.m_113071_(beedebugrenderer$hiveinfo);
         } else if (ClientboundCustomPayloadPacket.f_132027_.equals(resourcelocation)) {
            this.f_104888_.f_91064_.f_113430_.m_5630_();
         } else if (ClientboundCustomPayloadPacket.f_132026_.equals(resourcelocation)) {
            BlockPos blockpos7 = friendlybytebuf.m_130135_();
            int j3 = friendlybytebuf.readInt();
            String s10 = friendlybytebuf.m_130277_();
            int k5 = friendlybytebuf.readInt();
            this.f_104888_.f_91064_.f_113430_.m_113524_(blockpos7, j3, s10, k5);
         } else if (ClientboundCustomPayloadPacket.f_178832_.equals(resourcelocation)) {
            GameEvent gameevent = Registry.f_175412_.m_7745_(new ResourceLocation(friendlybytebuf.m_130277_()));
            BlockPos blockpos8 = friendlybytebuf.m_130135_();
            this.f_104888_.f_91064_.f_173815_.m_173827_(gameevent, blockpos8);
         } else if (ClientboundCustomPayloadPacket.f_178833_.equals(resourcelocation)) {
            ResourceLocation resourcelocation1 = friendlybytebuf.m_130281_();
            PositionSource positionsource = Registry.f_175420_.m_6612_(resourcelocation1).orElseThrow(() -> {
               return new IllegalArgumentException("Unknown position source type " + resourcelocation1);
            }).m_142281_(friendlybytebuf);
            int i5 = friendlybytebuf.m_130242_();
            this.f_104888_.f_91064_.f_173815_.m_173830_(positionsource, i5);
         } else {
            if (!net.minecraftforge.fmllegacy.network.NetworkHooks.onCustomPayload(p_105004_, this.f_104885_))
            f_104883_.warn("Unknown custom packet identifier: {}", (Object)resourcelocation);
         }
      } finally {
         if (friendlybytebuf != null) {
            friendlybytebuf.release();
         }

      }

   }

   public void m_7957_(ClientboundSetObjectivePacket p_105100_) {
      PacketUtils.m_131363_(p_105100_, this, this.f_104888_);
      Scoreboard scoreboard = this.f_104889_.m_6188_();
      String s = p_105100_.m_133266_();
      if (p_105100_.m_133270_() == 0) {
         scoreboard.m_83436_(s, ObjectiveCriteria.f_83588_, p_105100_.m_133269_(), p_105100_.m_133271_());
      } else if (scoreboard.m_83459_(s)) {
         Objective objective = scoreboard.m_83477_(s);
         if (p_105100_.m_133270_() == 1) {
            scoreboard.m_83502_(objective);
         } else if (p_105100_.m_133270_() == 2) {
            objective.m_83314_(p_105100_.m_133271_());
            objective.m_83316_(p_105100_.m_133269_());
         }
      }

   }

   public void m_7519_(ClientboundSetScorePacket p_105106_) {
      PacketUtils.m_131363_(p_105106_, this, this.f_104888_);
      Scoreboard scoreboard = this.f_104889_.m_6188_();
      String s = p_105106_.m_133342_();
      switch(p_105106_.m_133344_()) {
      case CHANGE:
         Objective objective = scoreboard.m_83469_(s);
         Score score = scoreboard.m_83471_(p_105106_.m_133339_(), objective);
         score.m_83402_(p_105106_.m_133343_());
         break;
      case REMOVE:
         scoreboard.m_83479_(p_105106_.m_133339_(), scoreboard.m_83477_(s));
      }

   }

   public void m_5556_(ClientboundSetDisplayObjectivePacket p_105086_) {
      PacketUtils.m_131363_(p_105086_, this, this.f_104888_);
      Scoreboard scoreboard = this.f_104889_.m_6188_();
      String s = p_105086_.m_133142_();
      Objective objective = s == null ? null : scoreboard.m_83469_(s);
      scoreboard.m_7136_(p_105086_.m_133139_(), objective);
   }

   public void m_5582_(ClientboundSetPlayerTeamPacket p_105104_) {
      PacketUtils.m_131363_(p_105104_, this, this.f_104888_);
      Scoreboard scoreboard = this.f_104889_.m_6188_();
      ClientboundSetPlayerTeamPacket.Action clientboundsetplayerteampacket$action = p_105104_.m_179338_();
      PlayerTeam playerteam;
      if (clientboundsetplayerteampacket$action == ClientboundSetPlayerTeamPacket.Action.ADD) {
         playerteam = scoreboard.m_83492_(p_105104_.m_133311_());
      } else {
         playerteam = scoreboard.m_83489_(p_105104_.m_133311_());
         if (playerteam == null) {
            f_104883_.warn("Received packet for unknown team {}: team action: {}, player action: {}", p_105104_.m_133311_(), p_105104_.m_179338_(), p_105104_.m_179335_());
            return;
         }
      }

      Optional<ClientboundSetPlayerTeamPacket.Parameters> optional = p_105104_.m_179339_();
      optional.ifPresent((p_171748_) -> {
         playerteam.m_83353_(p_171748_.m_179363_());
         playerteam.m_83351_(p_171748_.m_179367_());
         playerteam.m_83342_(p_171748_.m_179366_());
         Team.Visibility team$visibility = Team.Visibility.m_83579_(p_171748_.m_179368_());
         if (team$visibility != null) {
            playerteam.m_83346_(team$visibility);
         }

         Team.CollisionRule team$collisionrule = Team.CollisionRule.m_83555_(p_171748_.m_179369_());
         if (team$collisionrule != null) {
            playerteam.m_83344_(team$collisionrule);
         }

         playerteam.m_83360_(p_171748_.m_179370_());
         playerteam.m_83365_(p_171748_.m_179371_());
      });
      ClientboundSetPlayerTeamPacket.Action clientboundsetplayerteampacket$action1 = p_105104_.m_179335_();
      if (clientboundsetplayerteampacket$action1 == ClientboundSetPlayerTeamPacket.Action.ADD) {
         for(String s : p_105104_.m_133315_()) {
            scoreboard.m_6546_(s, playerteam);
         }
      } else if (clientboundsetplayerteampacket$action1 == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
         for(String s1 : p_105104_.m_133315_()) {
            scoreboard.m_6519_(s1, playerteam);
         }
      }

      if (clientboundsetplayerteampacket$action == ClientboundSetPlayerTeamPacket.Action.REMOVE) {
         scoreboard.m_83475_(playerteam);
      }

   }

   public void m_7406_(ClientboundLevelParticlesPacket p_105026_) {
      PacketUtils.m_131363_(p_105026_, this, this.f_104888_);
      if (p_105026_.m_132321_() == 0) {
         double d0 = (double)(p_105026_.m_132320_() * p_105026_.m_132317_());
         double d2 = (double)(p_105026_.m_132320_() * p_105026_.m_132318_());
         double d4 = (double)(p_105026_.m_132320_() * p_105026_.m_132319_());

         try {
            this.f_104889_.m_6493_(p_105026_.m_132322_(), p_105026_.m_132311_(), p_105026_.m_132314_(), p_105026_.m_132315_(), p_105026_.m_132316_(), d0, d2, d4);
         } catch (Throwable throwable1) {
            f_104883_.warn("Could not spawn particle effect {}", (Object)p_105026_.m_132322_());
         }
      } else {
         for(int i = 0; i < p_105026_.m_132321_(); ++i) {
            double d1 = this.f_104898_.nextGaussian() * (double)p_105026_.m_132317_();
            double d3 = this.f_104898_.nextGaussian() * (double)p_105026_.m_132318_();
            double d5 = this.f_104898_.nextGaussian() * (double)p_105026_.m_132319_();
            double d6 = this.f_104898_.nextGaussian() * (double)p_105026_.m_132320_();
            double d7 = this.f_104898_.nextGaussian() * (double)p_105026_.m_132320_();
            double d8 = this.f_104898_.nextGaussian() * (double)p_105026_.m_132320_();

            try {
               this.f_104889_.m_6493_(p_105026_.m_132322_(), p_105026_.m_132311_(), p_105026_.m_132314_() + d1, p_105026_.m_132315_() + d3, p_105026_.m_132316_() + d5, d6, d7, d8);
            } catch (Throwable throwable) {
               f_104883_.warn("Could not spawn particle effect {}", (Object)p_105026_.m_132322_());
               return;
            }
         }
      }

   }

   public void m_141955_(ClientboundPingPacket p_171769_) {
      PacketUtils.m_131363_(p_171769_, this, this.f_104888_);
      this.m_104955_(new ServerboundPongPacket(p_171769_.m_179025_()));
   }

   public void m_7710_(ClientboundUpdateAttributesPacket p_105128_) {
      PacketUtils.m_131363_(p_105128_, this, this.f_104888_);
      Entity entity = this.f_104889_.m_6815_(p_105128_.m_133588_());
      if (entity != null) {
         if (!(entity instanceof LivingEntity)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
         } else {
            AttributeMap attributemap = ((LivingEntity)entity).m_21204_();

            for(ClientboundUpdateAttributesPacket.AttributeSnapshot clientboundupdateattributespacket$attributesnapshot : p_105128_.m_133591_()) {
               AttributeInstance attributeinstance = attributemap.m_22146_(clientboundupdateattributespacket$attributesnapshot.m_133601_());
               if (attributeinstance == null) {
                  f_104883_.warn("Entity {} does not have attribute {}", entity, Registry.f_122866_.m_7981_(clientboundupdateattributespacket$attributesnapshot.m_133601_()));
               } else {
                  attributeinstance.m_22100_(clientboundupdateattributespacket$attributesnapshot.m_133602_());
                  attributeinstance.m_22132_();

                  for(AttributeModifier attributemodifier : clientboundupdateattributespacket$attributesnapshot.m_133603_()) {
                     attributeinstance.m_22118_(attributemodifier);
                  }
               }
            }

         }
      }
   }

   public void m_7339_(ClientboundPlaceGhostRecipePacket p_105046_) {
      PacketUtils.m_131363_(p_105046_, this, this.f_104888_);
      AbstractContainerMenu abstractcontainermenu = this.f_104888_.f_91074_.f_36096_;
      if (abstractcontainermenu.f_38840_ == p_105046_.m_132658_()) {
         this.f_104900_.m_44043_(p_105046_.m_132655_()).ifPresent((p_171745_) -> {
            if (this.f_104888_.f_91080_ instanceof RecipeUpdateListener) {
               RecipeBookComponent recipebookcomponent = ((RecipeUpdateListener)this.f_104888_.f_91080_).m_5564_();
               recipebookcomponent.m_7173_(p_171745_, abstractcontainermenu.f_38839_);
            }

         });
      }
   }

   public void m_6496_(ClientboundLightUpdatePacket p_105028_) {
      PacketUtils.m_131363_(p_105028_, this, this.f_104888_);
      int i = p_105028_.m_132349_();
      int j = p_105028_.m_132352_();
      LevelLightEngine levellightengine = this.f_104889_.m_7726_().m_7827_();
      BitSet bitset = p_105028_.m_178931_();
      BitSet bitset1 = p_105028_.m_178932_();
      Iterator<byte[]> iterator = p_105028_.m_132355_().iterator();
      this.m_171734_(i, j, levellightengine, LightLayer.SKY, bitset, bitset1, iterator, p_105028_.m_132359_());
      BitSet bitset2 = p_105028_.m_178933_();
      BitSet bitset3 = p_105028_.m_178934_();
      Iterator<byte[]> iterator1 = p_105028_.m_132358_().iterator();
      this.m_171734_(i, j, levellightengine, LightLayer.BLOCK, bitset2, bitset3, iterator1, p_105028_.m_132359_());
   }

   public void m_7330_(ClientboundMerchantOffersPacket p_105034_) {
      PacketUtils.m_131363_(p_105034_, this, this.f_104888_);
      AbstractContainerMenu abstractcontainermenu = this.f_104888_.f_91074_.f_36096_;
      if (p_105034_.m_132468_() == abstractcontainermenu.f_38840_ && abstractcontainermenu instanceof MerchantMenu) {
         MerchantMenu merchantmenu = (MerchantMenu)abstractcontainermenu;
         merchantmenu.m_40046_(new MerchantOffers(p_105034_.m_132471_().m_45388_()));
         merchantmenu.m_40066_(p_105034_.m_132473_());
         merchantmenu.m_40069_(p_105034_.m_132472_());
         merchantmenu.m_40048_(p_105034_.m_132474_());
         merchantmenu.m_40058_(p_105034_.m_132475_());
      }

   }

   public void m_7299_(ClientboundSetChunkCacheRadiusPacket p_105082_) {
      PacketUtils.m_131363_(p_105082_, this, this.f_104888_);
      this.f_104897_ = p_105082_.m_133108_();
      this.f_104889_.m_7726_().m_104416_(p_105082_.m_133108_());
   }

   public void m_8065_(ClientboundSetChunkCacheCenterPacket p_105080_) {
      PacketUtils.m_131363_(p_105080_, this, this.f_104888_);
      this.f_104889_.m_7726_().m_104459_(p_105080_.m_133094_(), p_105080_.m_133097_());
   }

   public void m_6695_(ClientboundBlockBreakAckPacket p_104972_) {
      PacketUtils.m_131363_(p_104972_, this, this.f_104888_);
      this.f_104888_.f_91072_.m_105256_(this.f_104889_, p_104972_.m_131668_(), p_104972_.m_131665_(), p_104972_.m_131670_(), p_104972_.m_131669_());
   }

   private void m_171734_(int p_171735_, int p_171736_, LevelLightEngine p_171737_, LightLayer p_171738_, BitSet p_171739_, BitSet p_171740_, Iterator<byte[]> p_171741_, boolean p_171742_) {
      for(int i = 0; i < p_171737_.m_164446_(); ++i) {
         int j = p_171737_.m_164447_() + i;
         boolean flag = p_171739_.get(i);
         boolean flag1 = p_171740_.get(i);
         if (flag || flag1) {
            p_171737_.m_5687_(p_171738_, SectionPos.m_123173_(p_171735_, j, p_171736_), flag ? new DataLayer((byte[])p_171741_.next().clone()) : new DataLayer(), p_171742_);
            this.f_104889_.m_104793_(p_171735_, j, p_171736_);
         }
      }

   }

   public Connection m_6198_() {
      return this.f_104885_;
   }

   public Collection<PlayerInfo> m_105142_() {
      return this.f_104892_.values();
   }

   public Collection<UUID> m_105143_() {
      return this.f_104892_.keySet();
   }

   @Nullable
   public PlayerInfo m_104949_(UUID p_104950_) {
      return this.f_104892_.get(p_104950_);
   }

   @Nullable
   public PlayerInfo m_104938_(String p_104939_) {
      for(PlayerInfo playerinfo : this.f_104892_.values()) {
         if (playerinfo.m_105312_().getName().equals(p_104939_)) {
            return playerinfo;
         }
      }

      return null;
   }

   public GameProfile m_105144_() {
      return this.f_104886_;
   }

   public ClientAdvancements m_105145_() {
      return this.f_104893_;
   }

   public CommandDispatcher<SharedSuggestionProvider> m_105146_() {
      return this.f_104899_;
   }

   public ClientLevel m_105147_() {
      return this.f_104889_;
   }

   public TagContainer m_105148_() {
      return this.f_104895_;
   }

   public DebugQueryHandler m_105149_() {
      return this.f_104896_;
   }

   public UUID m_105150_() {
      return this.f_104901_;
   }

   public Set<ResourceKey<Level>> m_105151_() {
      return this.f_104902_;
   }

   public RegistryAccess m_105152_() {
      return this.f_104903_;
   }
}
