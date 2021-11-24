package net.minecraft.world.entity;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityType<T extends Entity> extends net.minecraftforge.registries.ForgeRegistryEntry<EntityType<?>> implements EntityTypeTest<Entity, T> {
   private static final Logger f_20534_ = LogManager.getLogger();
   public static final String f_147037_ = "EntityTag";
   private static final float f_147038_ = 1.3964844F;
   public static final EntityType<AreaEffectCloud> f_20476_ = m_20634_("area_effect_cloud", EntityType.Builder.<AreaEffectCloud>m_20704_(AreaEffectCloud::new, MobCategory.MISC).m_20719_().m_20699_(6.0F, 0.5F).m_20702_(10).m_20717_(Integer.MAX_VALUE));
   public static final EntityType<ArmorStand> f_20529_ = m_20634_("armor_stand", EntityType.Builder.<ArmorStand>m_20704_(ArmorStand::new, MobCategory.MISC).m_20699_(0.5F, 1.975F).m_20702_(10));
   public static final EntityType<Arrow> f_20548_ = m_20634_("arrow", EntityType.Builder.<Arrow>m_20704_(Arrow::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(4).m_20717_(20));
   public static final EntityType<Axolotl> f_147039_ = m_20634_("axolotl", EntityType.Builder.<Axolotl>m_20704_(Axolotl::new, MobCategory.UNDERGROUND_WATER_CREATURE).m_20699_(0.75F, 0.42F).m_20702_(10));
   public static final EntityType<Bat> f_20549_ = m_20634_("bat", EntityType.Builder.<Bat>m_20704_(Bat::new, MobCategory.AMBIENT).m_20699_(0.5F, 0.9F).m_20702_(5));
   public static final EntityType<Bee> f_20550_ = m_20634_("bee", EntityType.Builder.<Bee>m_20704_(Bee::new, MobCategory.CREATURE).m_20699_(0.7F, 0.6F).m_20702_(8));
   public static final EntityType<Blaze> f_20551_ = m_20634_("blaze", EntityType.Builder.<Blaze>m_20704_(Blaze::new, MobCategory.MONSTER).m_20719_().m_20699_(0.6F, 1.8F).m_20702_(8));
   public static final EntityType<Boat> f_20552_ = m_20634_("boat", EntityType.Builder.<Boat>m_20704_(Boat::new, MobCategory.MISC).m_20699_(1.375F, 0.5625F).m_20702_(10));
   public static final EntityType<Cat> f_20553_ = m_20634_("cat", EntityType.Builder.<Cat>m_20704_(Cat::new, MobCategory.CREATURE).m_20699_(0.6F, 0.7F).m_20702_(8));
   public static final EntityType<CaveSpider> f_20554_ = m_20634_("cave_spider", EntityType.Builder.<CaveSpider>m_20704_(CaveSpider::new, MobCategory.MONSTER).m_20699_(0.7F, 0.5F).m_20702_(8));
   public static final EntityType<Chicken> f_20555_ = m_20634_("chicken", EntityType.Builder.<Chicken>m_20704_(Chicken::new, MobCategory.CREATURE).m_20699_(0.4F, 0.7F).m_20702_(10));
   public static final EntityType<Cod> f_20556_ = m_20634_("cod", EntityType.Builder.<Cod>m_20704_(Cod::new, MobCategory.WATER_AMBIENT).m_20699_(0.5F, 0.3F).m_20702_(4));
   public static final EntityType<Cow> f_20557_ = m_20634_("cow", EntityType.Builder.<Cow>m_20704_(Cow::new, MobCategory.CREATURE).m_20699_(0.9F, 1.4F).m_20702_(10));
   public static final EntityType<Creeper> f_20558_ = m_20634_("creeper", EntityType.Builder.<Creeper>m_20704_(Creeper::new, MobCategory.MONSTER).m_20699_(0.6F, 1.7F).m_20702_(8));
   public static final EntityType<Dolphin> f_20559_ = m_20634_("dolphin", EntityType.Builder.<Dolphin>m_20704_(Dolphin::new, MobCategory.WATER_CREATURE).m_20699_(0.9F, 0.6F));
   public static final EntityType<Donkey> f_20560_ = m_20634_("donkey", EntityType.Builder.<Donkey>m_20704_(Donkey::new, MobCategory.CREATURE).m_20699_(1.3964844F, 1.5F).m_20702_(10));
   public static final EntityType<DragonFireball> f_20561_ = m_20634_("dragon_fireball", EntityType.Builder.<DragonFireball>m_20704_(DragonFireball::new, MobCategory.MISC).m_20699_(1.0F, 1.0F).m_20702_(4).m_20717_(10));
   public static final EntityType<Drowned> f_20562_ = m_20634_("drowned", EntityType.Builder.<Drowned>m_20704_(Drowned::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<ElderGuardian> f_20563_ = m_20634_("elder_guardian", EntityType.Builder.<ElderGuardian>m_20704_(ElderGuardian::new, MobCategory.MONSTER).m_20699_(1.9975F, 1.9975F).m_20702_(10));
   public static final EntityType<EndCrystal> f_20564_ = m_20634_("end_crystal", EntityType.Builder.<EndCrystal>m_20704_(EndCrystal::new, MobCategory.MISC).m_20699_(2.0F, 2.0F).m_20702_(16).m_20717_(Integer.MAX_VALUE));
   public static final EntityType<EnderDragon> f_20565_ = m_20634_("ender_dragon", EntityType.Builder.<EnderDragon>m_20704_(EnderDragon::new, MobCategory.MONSTER).m_20719_().m_20699_(16.0F, 8.0F).m_20702_(10));
   public static final EntityType<EnderMan> f_20566_ = m_20634_("enderman", EntityType.Builder.<EnderMan>m_20704_(EnderMan::new, MobCategory.MONSTER).m_20699_(0.6F, 2.9F).m_20702_(8));
   public static final EntityType<Endermite> f_20567_ = m_20634_("endermite", EntityType.Builder.<Endermite>m_20704_(Endermite::new, MobCategory.MONSTER).m_20699_(0.4F, 0.3F).m_20702_(8));
   public static final EntityType<Evoker> f_20568_ = m_20634_("evoker", EntityType.Builder.<Evoker>m_20704_(Evoker::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<EvokerFangs> f_20569_ = m_20634_("evoker_fangs", EntityType.Builder.<EvokerFangs>m_20704_(EvokerFangs::new, MobCategory.MISC).m_20699_(0.5F, 0.8F).m_20702_(6).m_20717_(2));
   public static final EntityType<ExperienceOrb> f_20570_ = m_20634_("experience_orb", EntityType.Builder.<ExperienceOrb>m_20704_(ExperienceOrb::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(6).m_20717_(20));
   public static final EntityType<EyeOfEnder> f_20571_ = m_20634_("eye_of_ender", EntityType.Builder.<EyeOfEnder>m_20704_(EyeOfEnder::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(4));
   public static final EntityType<FallingBlockEntity> f_20450_ = m_20634_("falling_block", EntityType.Builder.<FallingBlockEntity>m_20704_(FallingBlockEntity::new, MobCategory.MISC).m_20699_(0.98F, 0.98F).m_20702_(10).m_20717_(20));
   public static final EntityType<FireworkRocketEntity> f_20451_ = m_20634_("firework_rocket", EntityType.Builder.<FireworkRocketEntity>m_20704_(FireworkRocketEntity::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10));
   public static final EntityType<Fox> f_20452_ = m_20634_("fox", EntityType.Builder.<Fox>m_20704_(Fox::new, MobCategory.CREATURE).m_20699_(0.6F, 0.7F).m_20702_(8).m_20714_(Blocks.f_50685_));
   public static final EntityType<Ghast> f_20453_ = m_20634_("ghast", EntityType.Builder.<Ghast>m_20704_(Ghast::new, MobCategory.MONSTER).m_20719_().m_20699_(4.0F, 4.0F).m_20702_(10));
   public static final EntityType<Giant> f_20454_ = m_20634_("giant", EntityType.Builder.<Giant>m_20704_(Giant::new, MobCategory.MONSTER).m_20699_(3.6F, 12.0F).m_20702_(10));
   public static final EntityType<GlowItemFrame> f_147033_ = m_20634_("glow_item_frame", EntityType.Builder.<GlowItemFrame>m_20704_(GlowItemFrame::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(10).m_20717_(Integer.MAX_VALUE));
   public static final EntityType<GlowSquid> f_147034_ = m_20634_("glow_squid", EntityType.Builder.<GlowSquid>m_20704_(GlowSquid::new, MobCategory.UNDERGROUND_WATER_CREATURE).m_20699_(0.8F, 0.8F).m_20702_(10));
   public static final EntityType<Goat> f_147035_ = m_20634_("goat", EntityType.Builder.<Goat>m_20704_(Goat::new, MobCategory.CREATURE).m_20699_(0.9F, 1.3F).m_20702_(10));
   public static final EntityType<Guardian> f_20455_ = m_20634_("guardian", EntityType.Builder.<Guardian>m_20704_(Guardian::new, MobCategory.MONSTER).m_20699_(0.85F, 0.85F).m_20702_(8));
   public static final EntityType<Hoglin> f_20456_ = m_20634_("hoglin", EntityType.Builder.<Hoglin>m_20704_(Hoglin::new, MobCategory.MONSTER).m_20699_(1.3964844F, 1.4F).m_20702_(8));
   public static final EntityType<Horse> f_20457_ = m_20634_("horse", EntityType.Builder.<Horse>m_20704_(Horse::new, MobCategory.CREATURE).m_20699_(1.3964844F, 1.6F).m_20702_(10));
   public static final EntityType<Husk> f_20458_ = m_20634_("husk", EntityType.Builder.<Husk>m_20704_(Husk::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<Illusioner> f_20459_ = m_20634_("illusioner", EntityType.Builder.<Illusioner>m_20704_(Illusioner::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<IronGolem> f_20460_ = m_20634_("iron_golem", EntityType.Builder.<IronGolem>m_20704_(IronGolem::new, MobCategory.MISC).m_20699_(1.4F, 2.7F).m_20702_(10));
   public static final EntityType<ItemEntity> f_20461_ = m_20634_("item", EntityType.Builder.<ItemEntity>m_20704_(ItemEntity::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(6).m_20717_(20));
   public static final EntityType<ItemFrame> f_20462_ = m_20634_("item_frame", EntityType.Builder.<ItemFrame>m_20704_(ItemFrame::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(10).m_20717_(Integer.MAX_VALUE));
   public static final EntityType<LargeFireball> f_20463_ = m_20634_("fireball", EntityType.Builder.<LargeFireball>m_20704_(LargeFireball::new, MobCategory.MISC).m_20699_(1.0F, 1.0F).m_20702_(4).m_20717_(10));
   public static final EntityType<LeashFenceKnotEntity> f_20464_ = m_20634_("leash_knot", EntityType.Builder.<LeashFenceKnotEntity>m_20704_(LeashFenceKnotEntity::new, MobCategory.MISC).m_20716_().m_20699_(0.375F, 0.5F).m_20702_(10).m_20717_(Integer.MAX_VALUE));
   public static final EntityType<LightningBolt> f_20465_ = m_20634_("lightning_bolt", EntityType.Builder.<LightningBolt>m_20704_(LightningBolt::new, MobCategory.MISC).m_20716_().m_20699_(0.0F, 0.0F).m_20702_(16).m_20717_(Integer.MAX_VALUE));
   public static final EntityType<Llama> f_20466_ = m_20634_("llama", EntityType.Builder.<Llama>m_20704_(Llama::new, MobCategory.CREATURE).m_20699_(0.9F, 1.87F).m_20702_(10));
   public static final EntityType<LlamaSpit> f_20467_ = m_20634_("llama_spit", EntityType.Builder.<LlamaSpit>m_20704_(LlamaSpit::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10));
   public static final EntityType<MagmaCube> f_20468_ = m_20634_("magma_cube", EntityType.Builder.<MagmaCube>m_20704_(MagmaCube::new, MobCategory.MONSTER).m_20719_().m_20699_(2.04F, 2.04F).m_20702_(8));
   public static final EntityType<Marker> f_147036_ = m_20634_("marker", EntityType.Builder.<Marker>m_20704_(Marker::new, MobCategory.MISC).m_20699_(0.0F, 0.0F).m_20702_(0));
   public static final EntityType<Minecart> f_20469_ = m_20634_("minecart", EntityType.Builder.<Minecart>m_20704_(Minecart::new, MobCategory.MISC).m_20699_(0.98F, 0.7F).m_20702_(8));
   public static final EntityType<MinecartChest> f_20470_ = m_20634_("chest_minecart", EntityType.Builder.<MinecartChest>m_20704_(MinecartChest::new, MobCategory.MISC).m_20699_(0.98F, 0.7F).m_20702_(8));
   public static final EntityType<MinecartCommandBlock> f_20471_ = m_20634_("command_block_minecart", EntityType.Builder.<MinecartCommandBlock>m_20704_(MinecartCommandBlock::new, MobCategory.MISC).m_20699_(0.98F, 0.7F).m_20702_(8));
   public static final EntityType<MinecartFurnace> f_20472_ = m_20634_("furnace_minecart", EntityType.Builder.<MinecartFurnace>m_20704_(MinecartFurnace::new, MobCategory.MISC).m_20699_(0.98F, 0.7F).m_20702_(8));
   public static final EntityType<MinecartHopper> f_20473_ = m_20634_("hopper_minecart", EntityType.Builder.<MinecartHopper>m_20704_(MinecartHopper::new, MobCategory.MISC).m_20699_(0.98F, 0.7F).m_20702_(8));
   public static final EntityType<MinecartSpawner> f_20474_ = m_20634_("spawner_minecart", EntityType.Builder.<MinecartSpawner>m_20704_(MinecartSpawner::new, MobCategory.MISC).m_20699_(0.98F, 0.7F).m_20702_(8));
   public static final EntityType<MinecartTNT> f_20475_ = m_20634_("tnt_minecart", EntityType.Builder.<MinecartTNT>m_20704_(MinecartTNT::new, MobCategory.MISC).m_20699_(0.98F, 0.7F).m_20702_(8));
   public static final EntityType<Mule> f_20503_ = m_20634_("mule", EntityType.Builder.<Mule>m_20704_(Mule::new, MobCategory.CREATURE).m_20699_(1.3964844F, 1.6F).m_20702_(8));
   public static final EntityType<MushroomCow> f_20504_ = m_20634_("mooshroom", EntityType.Builder.<MushroomCow>m_20704_(MushroomCow::new, MobCategory.CREATURE).m_20699_(0.9F, 1.4F).m_20702_(10));
   public static final EntityType<Ocelot> f_20505_ = m_20634_("ocelot", EntityType.Builder.<Ocelot>m_20704_(Ocelot::new, MobCategory.CREATURE).m_20699_(0.6F, 0.7F).m_20702_(10));
   public static final EntityType<Painting> f_20506_ = m_20634_("painting", EntityType.Builder.<Painting>m_20704_(Painting::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(10).m_20717_(Integer.MAX_VALUE));
   public static final EntityType<Panda> f_20507_ = m_20634_("panda", EntityType.Builder.<Panda>m_20704_(Panda::new, MobCategory.CREATURE).m_20699_(1.3F, 1.25F).m_20702_(10));
   public static final EntityType<Parrot> f_20508_ = m_20634_("parrot", EntityType.Builder.<Parrot>m_20704_(Parrot::new, MobCategory.CREATURE).m_20699_(0.5F, 0.9F).m_20702_(8));
   public static final EntityType<Phantom> f_20509_ = m_20634_("phantom", EntityType.Builder.<Phantom>m_20704_(Phantom::new, MobCategory.MONSTER).m_20699_(0.9F, 0.5F).m_20702_(8));
   public static final EntityType<Pig> f_20510_ = m_20634_("pig", EntityType.Builder.<Pig>m_20704_(Pig::new, MobCategory.CREATURE).m_20699_(0.9F, 0.9F).m_20702_(10));
   public static final EntityType<Piglin> f_20511_ = m_20634_("piglin", EntityType.Builder.<Piglin>m_20704_(Piglin::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<PiglinBrute> f_20512_ = m_20634_("piglin_brute", EntityType.Builder.<PiglinBrute>m_20704_(PiglinBrute::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<Pillager> f_20513_ = m_20634_("pillager", EntityType.Builder.<Pillager>m_20704_(Pillager::new, MobCategory.MONSTER).m_20720_().m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<PolarBear> f_20514_ = m_20634_("polar_bear", EntityType.Builder.<PolarBear>m_20704_(PolarBear::new, MobCategory.CREATURE).m_20714_(Blocks.f_152499_).m_20699_(1.4F, 1.4F).m_20702_(10));
   public static final EntityType<PrimedTnt> f_20515_ = m_20634_("tnt", EntityType.Builder.<PrimedTnt>m_20704_(PrimedTnt::new, MobCategory.MISC).m_20719_().m_20699_(0.98F, 0.98F).m_20702_(10).m_20717_(10));
   public static final EntityType<Pufferfish> f_20516_ = m_20634_("pufferfish", EntityType.Builder.<Pufferfish>m_20704_(Pufferfish::new, MobCategory.WATER_AMBIENT).m_20699_(0.7F, 0.7F).m_20702_(4));
   public static final EntityType<Rabbit> f_20517_ = m_20634_("rabbit", EntityType.Builder.<Rabbit>m_20704_(Rabbit::new, MobCategory.CREATURE).m_20699_(0.4F, 0.5F).m_20702_(8));
   public static final EntityType<Ravager> f_20518_ = m_20634_("ravager", EntityType.Builder.<Ravager>m_20704_(Ravager::new, MobCategory.MONSTER).m_20699_(1.95F, 2.2F).m_20702_(10));
   public static final EntityType<Salmon> f_20519_ = m_20634_("salmon", EntityType.Builder.<Salmon>m_20704_(Salmon::new, MobCategory.WATER_AMBIENT).m_20699_(0.7F, 0.4F).m_20702_(4));
   public static final EntityType<Sheep> f_20520_ = m_20634_("sheep", EntityType.Builder.<Sheep>m_20704_(Sheep::new, MobCategory.CREATURE).m_20699_(0.9F, 1.3F).m_20702_(10));
   public static final EntityType<Shulker> f_20521_ = m_20634_("shulker", EntityType.Builder.<Shulker>m_20704_(Shulker::new, MobCategory.MONSTER).m_20719_().m_20720_().m_20699_(1.0F, 1.0F).m_20702_(10));
   public static final EntityType<ShulkerBullet> f_20522_ = m_20634_("shulker_bullet", EntityType.Builder.<ShulkerBullet>m_20704_(ShulkerBullet::new, MobCategory.MISC).m_20699_(0.3125F, 0.3125F).m_20702_(8));
   public static final EntityType<Silverfish> f_20523_ = m_20634_("silverfish", EntityType.Builder.<Silverfish>m_20704_(Silverfish::new, MobCategory.MONSTER).m_20699_(0.4F, 0.3F).m_20702_(8));
   public static final EntityType<Skeleton> f_20524_ = m_20634_("skeleton", EntityType.Builder.<Skeleton>m_20704_(Skeleton::new, MobCategory.MONSTER).m_20699_(0.6F, 1.99F).m_20702_(8));
   public static final EntityType<SkeletonHorse> f_20525_ = m_20634_("skeleton_horse", EntityType.Builder.<SkeletonHorse>m_20704_(SkeletonHorse::new, MobCategory.CREATURE).m_20699_(1.3964844F, 1.6F).m_20702_(10));
   public static final EntityType<Slime> f_20526_ = m_20634_("slime", EntityType.Builder.<Slime>m_20704_(Slime::new, MobCategory.MONSTER).m_20699_(2.04F, 2.04F).m_20702_(10));
   public static final EntityType<SmallFireball> f_20527_ = m_20634_("small_fireball", EntityType.Builder.<SmallFireball>m_20704_(SmallFireball::new, MobCategory.MISC).m_20699_(0.3125F, 0.3125F).m_20702_(4).m_20717_(10));
   public static final EntityType<SnowGolem> f_20528_ = m_20634_("snow_golem", EntityType.Builder.<SnowGolem>m_20704_(SnowGolem::new, MobCategory.MISC).m_20714_(Blocks.f_152499_).m_20699_(0.7F, 1.9F).m_20702_(8));
   public static final EntityType<Snowball> f_20477_ = m_20634_("snowball", EntityType.Builder.<Snowball>m_20704_(Snowball::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10));
   public static final EntityType<SpectralArrow> f_20478_ = m_20634_("spectral_arrow", EntityType.Builder.<SpectralArrow>m_20704_(SpectralArrow::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(4).m_20717_(20));
   public static final EntityType<Spider> f_20479_ = m_20634_("spider", EntityType.Builder.<Spider>m_20704_(Spider::new, MobCategory.MONSTER).m_20699_(1.4F, 0.9F).m_20702_(8));
   public static final EntityType<Squid> f_20480_ = m_20634_("squid", EntityType.Builder.<Squid>m_20704_(Squid::new, MobCategory.WATER_CREATURE).m_20699_(0.8F, 0.8F).m_20702_(8));
   public static final EntityType<Stray> f_20481_ = m_20634_("stray", EntityType.Builder.<Stray>m_20704_(Stray::new, MobCategory.MONSTER).m_20699_(0.6F, 1.99F).m_20714_(Blocks.f_152499_).m_20702_(8));
   public static final EntityType<Strider> f_20482_ = m_20634_("strider", EntityType.Builder.<Strider>m_20704_(Strider::new, MobCategory.CREATURE).m_20719_().m_20699_(0.9F, 1.7F).m_20702_(10));
   public static final EntityType<ThrownEgg> f_20483_ = m_20634_("egg", EntityType.Builder.<ThrownEgg>m_20704_(ThrownEgg::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10));
   public static final EntityType<ThrownEnderpearl> f_20484_ = m_20634_("ender_pearl", EntityType.Builder.<ThrownEnderpearl>m_20704_(ThrownEnderpearl::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10));
   public static final EntityType<ThrownExperienceBottle> f_20485_ = m_20634_("experience_bottle", EntityType.Builder.<ThrownExperienceBottle>m_20704_(ThrownExperienceBottle::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10));
   public static final EntityType<ThrownPotion> f_20486_ = m_20634_("potion", EntityType.Builder.<ThrownPotion>m_20704_(ThrownPotion::new, MobCategory.MISC).m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(10));
   public static final EntityType<ThrownTrident> f_20487_ = m_20634_("trident", EntityType.Builder.<ThrownTrident>m_20704_(ThrownTrident::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20702_(4).m_20717_(20));
   public static final EntityType<TraderLlama> f_20488_ = m_20634_("trader_llama", EntityType.Builder.<TraderLlama>m_20704_(TraderLlama::new, MobCategory.CREATURE).m_20699_(0.9F, 1.87F).m_20702_(10));
   public static final EntityType<TropicalFish> f_20489_ = m_20634_("tropical_fish", EntityType.Builder.<TropicalFish>m_20704_(TropicalFish::new, MobCategory.WATER_AMBIENT).m_20699_(0.5F, 0.4F).m_20702_(4));
   public static final EntityType<Turtle> f_20490_ = m_20634_("turtle", EntityType.Builder.<Turtle>m_20704_(Turtle::new, MobCategory.CREATURE).m_20699_(1.2F, 0.4F).m_20702_(10));
   public static final EntityType<Vex> f_20491_ = m_20634_("vex", EntityType.Builder.<Vex>m_20704_(Vex::new, MobCategory.MONSTER).m_20719_().m_20699_(0.4F, 0.8F).m_20702_(8));
   public static final EntityType<Villager> f_20492_ = m_20634_("villager", EntityType.Builder.<Villager>m_20704_(Villager::new, MobCategory.MISC).m_20699_(0.6F, 1.95F).m_20702_(10));
   public static final EntityType<Vindicator> f_20493_ = m_20634_("vindicator", EntityType.Builder.<Vindicator>m_20704_(Vindicator::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<WanderingTrader> f_20494_ = m_20634_("wandering_trader", EntityType.Builder.<WanderingTrader>m_20704_(WanderingTrader::new, MobCategory.CREATURE).m_20699_(0.6F, 1.95F).m_20702_(10));
   public static final EntityType<Witch> f_20495_ = m_20634_("witch", EntityType.Builder.<Witch>m_20704_(Witch::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<WitherBoss> f_20496_ = m_20634_("wither", EntityType.Builder.<WitherBoss>m_20704_(WitherBoss::new, MobCategory.MONSTER).m_20719_().m_20714_(Blocks.f_50070_).m_20699_(0.9F, 3.5F).m_20702_(10));
   public static final EntityType<WitherSkeleton> f_20497_ = m_20634_("wither_skeleton", EntityType.Builder.<WitherSkeleton>m_20704_(WitherSkeleton::new, MobCategory.MONSTER).m_20719_().m_20714_(Blocks.f_50070_).m_20699_(0.7F, 2.4F).m_20702_(8));
   public static final EntityType<WitherSkull> f_20498_ = m_20634_("wither_skull", EntityType.Builder.<WitherSkull>m_20704_(WitherSkull::new, MobCategory.MISC).m_20699_(0.3125F, 0.3125F).m_20702_(4).m_20717_(10));
   public static final EntityType<Wolf> f_20499_ = m_20634_("wolf", EntityType.Builder.<Wolf>m_20704_(Wolf::new, MobCategory.CREATURE).m_20699_(0.6F, 0.85F).m_20702_(10));
   public static final EntityType<Zoglin> f_20500_ = m_20634_("zoglin", EntityType.Builder.<Zoglin>m_20704_(Zoglin::new, MobCategory.MONSTER).m_20719_().m_20699_(1.3964844F, 1.4F).m_20702_(8));
   public static final EntityType<Zombie> f_20501_ = m_20634_("zombie", EntityType.Builder.<Zombie>m_20704_(Zombie::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<ZombieHorse> f_20502_ = m_20634_("zombie_horse", EntityType.Builder.<ZombieHorse>m_20704_(ZombieHorse::new, MobCategory.CREATURE).m_20699_(1.3964844F, 1.6F).m_20702_(10));
   public static final EntityType<ZombieVillager> f_20530_ = m_20634_("zombie_villager", EntityType.Builder.<ZombieVillager>m_20704_(ZombieVillager::new, MobCategory.MONSTER).m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<ZombifiedPiglin> f_20531_ = m_20634_("zombified_piglin", EntityType.Builder.<ZombifiedPiglin>m_20704_(ZombifiedPiglin::new, MobCategory.MONSTER).m_20719_().m_20699_(0.6F, 1.95F).m_20702_(8));
   public static final EntityType<Player> f_20532_ = m_20634_("player", EntityType.Builder.<Player>m_20710_(MobCategory.MISC).m_20716_().m_20698_().m_20699_(0.6F, 1.8F).m_20702_(32).m_20717_(2));
   public static final EntityType<FishingHook> f_20533_ = m_20634_("fishing_bobber", EntityType.Builder.<FishingHook>m_20704_(FishingHook::new, MobCategory.MISC).m_20716_().m_20698_().m_20699_(0.25F, 0.25F).m_20702_(4).m_20717_(5));
   private final EntityType.EntityFactory<T> f_20535_;
   private final MobCategory f_20536_;
   private final ImmutableSet<Block> f_20537_;
   private final boolean f_20538_;
   private final boolean f_20539_;
   private final boolean f_20540_;
   private final boolean f_20541_;
   private final int f_20542_;
   private final int f_20543_;
   @Nullable
   private String f_20544_;
   @Nullable
   private Component f_20545_;
   @Nullable
   private ResourceLocation f_20546_;
   private final EntityDimensions f_20547_;

   private final java.util.function.Predicate<EntityType<?>> velocityUpdateSupplier;
   private final java.util.function.ToIntFunction<EntityType<?>> trackingRangeSupplier;
   private final java.util.function.ToIntFunction<EntityType<?>> updateIntervalSupplier;
   private final java.util.function.BiFunction<net.minecraftforge.fmllegacy.network.FMLPlayMessages.SpawnEntity, Level, T> customClientFactory;
   private final net.minecraftforge.common.util.ReverseTagWrapper<EntityType<?>> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, net.minecraft.tags.EntityTypeTags::m_13126_);

   private static <T extends Entity> EntityType<T> m_20634_(String p_20635_, EntityType.Builder<T> p_20636_) {
      return Registry.m_122961_(Registry.f_122826_, p_20635_, p_20636_.m_20712_(p_20635_));
   }

   public static ResourceLocation m_20613_(EntityType<?> p_20614_) {
      return Registry.f_122826_.m_7981_(p_20614_);
   }

   public static Optional<EntityType<?>> m_20632_(String p_20633_) {
      return Registry.f_122826_.m_6612_(ResourceLocation.m_135820_(p_20633_));
   }

   public EntityType(EntityType.EntityFactory<T> p_20574_, MobCategory p_20575_, boolean p_20576_, boolean p_20577_, boolean p_20578_, boolean p_20579_, ImmutableSet<Block> p_20580_, EntityDimensions p_20581_, int p_20582_, int p_20583_) {
       this(p_20574_, p_20575_, p_20576_, p_20577_, p_20578_, p_20579_, p_20580_, p_20581_, p_20582_, p_20583_, EntityType::defaultVelocitySupplier, EntityType::defaultTrackingRangeSupplier, EntityType::defaultUpdateIntervalSupplier, null);
   }
   public EntityType(EntityType.EntityFactory<T> p_20574_, MobCategory p_20575_, boolean p_20576_, boolean p_20577_, boolean p_20578_, boolean p_20579_, ImmutableSet<Block> p_20580_, EntityDimensions p_20581_, int p_20582_, int p_20583_, final java.util.function.Predicate<EntityType<?>> velocityUpdateSupplier, final java.util.function.ToIntFunction<EntityType<?>> trackingRangeSupplier, final java.util.function.ToIntFunction<EntityType<?>> updateIntervalSupplier, final java.util.function.BiFunction<net.minecraftforge.fmllegacy.network.FMLPlayMessages.SpawnEntity, Level, T> customClientFactory) {
      this.f_20535_ = p_20574_;
      this.f_20536_ = p_20575_;
      this.f_20541_ = p_20579_;
      this.f_20538_ = p_20576_;
      this.f_20539_ = p_20577_;
      this.f_20540_ = p_20578_;
      this.f_20537_ = p_20580_;
      this.f_20547_ = p_20581_;
      this.f_20542_ = p_20582_;
      this.f_20543_ = p_20583_;
      this.velocityUpdateSupplier = velocityUpdateSupplier;
      this.trackingRangeSupplier = trackingRangeSupplier;
      this.updateIntervalSupplier = updateIntervalSupplier;
      this.customClientFactory = customClientFactory;
   }

   @Nullable
   public Entity m_20592_(ServerLevel p_20593_, @Nullable ItemStack p_20594_, @Nullable Player p_20595_, BlockPos p_20596_, MobSpawnType p_20597_, boolean p_20598_, boolean p_20599_) {
      return this.m_20600_(p_20593_, p_20594_ == null ? null : p_20594_.m_41783_(), p_20594_ != null && p_20594_.m_41788_() ? p_20594_.m_41786_() : null, p_20595_, p_20596_, p_20597_, p_20598_, p_20599_);
   }

   @Nullable
   public T m_20600_(ServerLevel p_20601_, @Nullable CompoundTag p_20602_, @Nullable Component p_20603_, @Nullable Player p_20604_, BlockPos p_20605_, MobSpawnType p_20606_, boolean p_20607_, boolean p_20608_) {
      T t = this.m_20655_(p_20601_, p_20602_, p_20603_, p_20604_, p_20605_, p_20606_, p_20607_, p_20608_);
      if (t != null) {
         if (t instanceof net.minecraft.world.entity.Mob && net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn((net.minecraft.world.entity.Mob) t, p_20601_, p_20605_.m_123341_(), p_20605_.m_123342_(), p_20605_.m_123343_(), null, p_20606_)) return null;
         p_20601_.m_47205_(t);
      }

      return t;
   }

   @Nullable
   public T m_20655_(ServerLevel p_20656_, @Nullable CompoundTag p_20657_, @Nullable Component p_20658_, @Nullable Player p_20659_, BlockPos p_20660_, MobSpawnType p_20661_, boolean p_20662_, boolean p_20663_) {
      T t = this.m_20615_(p_20656_);
      if (t == null) {
         return (T)null;
      } else {
         double d0;
         if (p_20662_) {
            t.m_6034_((double)p_20660_.m_123341_() + 0.5D, (double)(p_20660_.m_123342_() + 1), (double)p_20660_.m_123343_() + 0.5D);
            d0 = m_20625_(p_20656_, p_20660_, p_20663_, t.m_142469_());
         } else {
            d0 = 0.0D;
         }

         t.m_7678_((double)p_20660_.m_123341_() + 0.5D, (double)p_20660_.m_123342_() + d0, (double)p_20660_.m_123343_() + 0.5D, Mth.m_14177_(p_20656_.f_46441_.nextFloat() * 360.0F), 0.0F);
         if (t instanceof Mob) {
            Mob mob = (Mob)t;
            mob.f_20885_ = mob.m_146908_();
            mob.f_20883_ = mob.m_146908_();
            mob.m_6518_(p_20656_, p_20656_.m_6436_(mob.m_142538_()), p_20661_, (SpawnGroupData)null, p_20657_);
            mob.m_8032_();
         }

         if (p_20658_ != null && t instanceof LivingEntity) {
            t.m_6593_(p_20658_);
         }

         m_20620_(p_20656_, p_20659_, t, p_20657_);
         return t;
      }
   }

   protected static double m_20625_(LevelReader p_20626_, BlockPos p_20627_, boolean p_20628_, AABB p_20629_) {
      AABB aabb = new AABB(p_20627_);
      if (p_20628_) {
         aabb = aabb.m_82363_(0.0D, -1.0D, 0.0D);
      }

      Stream<VoxelShape> stream = p_20626_.m_7786_((Entity)null, aabb, (p_20612_) -> {
         return true;
      });
      return 1.0D + Shapes.m_83134_(Direction.Axis.Y, p_20629_, stream, p_20628_ ? -2.0D : -1.0D);
   }

   public static void m_20620_(Level p_20621_, @Nullable Player p_20622_, @Nullable Entity p_20623_, @Nullable CompoundTag p_20624_) {
      if (p_20624_ != null && p_20624_.m_128425_("EntityTag", 10)) {
         MinecraftServer minecraftserver = p_20621_.m_142572_();
         if (minecraftserver != null && p_20623_ != null) {
            if (p_20621_.f_46443_ || !p_20623_.m_6127_() || p_20622_ != null && minecraftserver.m_6846_().m_11303_(p_20622_.m_36316_())) {
               CompoundTag compoundtag = p_20623_.m_20240_(new CompoundTag());
               UUID uuid = p_20623_.m_142081_();
               compoundtag.m_128391_(p_20624_.m_128469_("EntityTag"));
               p_20623_.m_20084_(uuid);
               p_20623_.m_20258_(compoundtag);
            }
         }
      }
   }

   public boolean m_20584_() {
      return this.f_20538_;
   }

   public boolean m_20654_() {
      return this.f_20539_;
   }

   public boolean m_20672_() {
      return this.f_20540_;
   }

   public boolean m_20673_() {
      return this.f_20541_;
   }

   public MobCategory m_20674_() {
      return this.f_20536_;
   }

   public String m_20675_() {
      if (this.f_20544_ == null) {
         this.f_20544_ = Util.m_137492_("entity", Registry.f_122826_.m_7981_(this));
      }

      return this.f_20544_;
   }

   public Component m_20676_() {
      if (this.f_20545_ == null) {
         this.f_20545_ = new TranslatableComponent(this.m_20675_());
      }

      return this.f_20545_;
   }

   public String toString() {
      return this.m_20675_();
   }

   public String m_147048_() {
      int i = this.m_20675_().lastIndexOf(46);
      return i == -1 ? this.m_20675_() : this.m_20675_().substring(i + 1);
   }

   public ResourceLocation m_20677_() {
      if (this.f_20546_ == null) {
         ResourceLocation resourcelocation = Registry.f_122826_.m_7981_(this);
         this.f_20546_ = new ResourceLocation(resourcelocation.m_135827_(), "entities/" + resourcelocation.m_135815_());
      }

      return this.f_20546_;
   }

   public float m_20678_() {
      return this.f_20547_.f_20377_;
   }

   public float m_20679_() {
      return this.f_20547_.f_20378_;
   }

   @Nullable
   public T m_20615_(Level p_20616_) {
      return this.f_20535_.m_20721_(this, p_20616_);
   }

   @Nullable
   public static Entity m_20589_(int p_20590_, Level p_20591_) {
      return m_20617_(p_20591_, Registry.f_122826_.m_7942_(p_20590_));
   }

   public static Optional<Entity> m_20642_(CompoundTag p_20643_, Level p_20644_) {
      return Util.m_137521_(m_20637_(p_20643_).map((p_20666_) -> {
         return p_20666_.m_20615_(p_20644_);
      }), (p_20641_) -> {
         p_20641_.m_20258_(p_20643_);
      }, () -> {
         f_20534_.warn("Skipping Entity with id {}", (Object)p_20643_.m_128461_("id"));
      });
   }

   @Nullable
   private static Entity m_20617_(Level p_20618_, @Nullable EntityType<?> p_20619_) {
      return p_20619_ == null ? null : p_20619_.m_20615_(p_20618_);
   }

   public AABB m_20585_(double p_20586_, double p_20587_, double p_20588_) {
      float f = this.m_20678_() / 2.0F;
      return new AABB(p_20586_ - (double)f, p_20587_, p_20588_ - (double)f, p_20586_ + (double)f, p_20587_ + (double)this.m_20679_(), p_20588_ + (double)f);
   }

   public boolean m_20630_(BlockState p_20631_) {
      if (this.f_20537_.contains(p_20631_.m_60734_())) {
         return false;
      } else if (!this.f_20540_ && WalkNodeEvaluator.m_77622_(p_20631_)) {
         return true;
      } else {
         return p_20631_.m_60713_(Blocks.f_50070_) || p_20631_.m_60713_(Blocks.f_50685_) || p_20631_.m_60713_(Blocks.f_50128_) || p_20631_.m_60713_(Blocks.f_152499_);
      }
   }

   public EntityDimensions m_20680_() {
      return this.f_20547_;
   }

   public static Optional<EntityType<?>> m_20637_(CompoundTag p_20638_) {
      return Registry.f_122826_.m_6612_(new ResourceLocation(p_20638_.m_128461_("id")));
   }

   @Nullable
   public static Entity m_20645_(CompoundTag p_20646_, Level p_20647_, Function<Entity, Entity> p_20648_) {
      return m_20669_(p_20646_, p_20647_).map(p_20648_).map((p_20653_) -> {
         if (p_20646_.m_128425_("Passengers", 9)) {
            ListTag listtag = p_20646_.m_128437_("Passengers", 10);

            for(int i = 0; i < listtag.size(); ++i) {
               Entity entity = m_20645_(listtag.m_128728_(i), p_20647_, p_20648_);
               if (entity != null) {
                  entity.m_7998_(p_20653_, true);
               }
            }
         }

         return p_20653_;
      }).orElse((Entity)null);
   }

   public static Stream<Entity> m_147045_(final List<? extends Tag> p_147046_, final Level p_147047_) {
      final Spliterator<? extends Tag> spliterator = p_147046_.spliterator();
      return StreamSupport.stream(new Spliterator<Entity>() {
         public boolean tryAdvance(Consumer<? super Entity> p_147066_) {
            return spliterator.tryAdvance((p_147059_) -> {
               EntityType.m_20645_((CompoundTag)p_147059_, p_147047_, (p_147062_) -> {
                  p_147066_.accept(p_147062_);
                  return p_147062_;
               });
            });
         }

         public Spliterator<Entity> trySplit() {
            return null;
         }

         public long estimateSize() {
            return (long)p_147046_.size();
         }

         public int characteristics() {
            return 1297;
         }
      }, false);
   }

   private static Optional<Entity> m_20669_(CompoundTag p_20670_, Level p_20671_) {
      try {
         return m_20642_(p_20670_, p_20671_);
      } catch (RuntimeException runtimeexception) {
         f_20534_.warn("Exception loading entity: ", (Throwable)runtimeexception);
         return Optional.empty();
      }
   }

   public int m_20681_() {
      return trackingRangeSupplier.applyAsInt(this);
   }
   private int defaultTrackingRangeSupplier() {
      return this.f_20542_;
   }

   public int m_20682_() {
      return updateIntervalSupplier.applyAsInt(this);
   }
   private int defaultUpdateIntervalSupplier() {
      return this.f_20543_;
   }

   public boolean m_20683_() {
      return velocityUpdateSupplier.test(this);
   }
   private boolean defaultVelocitySupplier() {
      return this != f_20532_ && this != f_20467_ && this != f_20496_ && this != f_20549_ && this != f_20462_ && this != f_147033_ && this != f_20464_ && this != f_20506_ && this != f_20564_ && this != f_20569_;
   }

   public boolean m_20609_(net.minecraft.tags.Tag<EntityType<?>> p_20610_) {
      return p_20610_.m_8110_(this);
   }

   @Nullable
   public T m_141992_(Entity p_147042_) {
      return (T)(p_147042_.m_6095_() == this ? p_147042_ : null);
   }

   public Class<? extends Entity> m_142225_() {
      return Entity.class;
   }

   public T customClientSpawn(net.minecraftforge.fmllegacy.network.FMLPlayMessages.SpawnEntity packet, Level world) {
       if (customClientFactory == null) return this.m_20615_(world);
       return customClientFactory.apply(packet, world);
   }

   /**
    * Retrieves a list of tags names this is known to be associated with.
    * This should be used in favor of TagCollection.getOwningTags, as this caches the result and automatically updates when the TagCollection changes.
    */
   public java.util.Set<ResourceLocation> getTags() {
       return reverseTags.getTagNames();
   }

   public static class Builder<T extends Entity> {
      private final EntityType.EntityFactory<T> f_20685_;
      private final MobCategory f_20686_;
      private ImmutableSet<Block> f_20687_ = ImmutableSet.of();
      private boolean f_20688_ = true;
      private boolean f_20689_ = true;
      private boolean f_20690_;
      private boolean f_20691_;
      private int f_20692_ = 5;
      private int f_20693_ = 3;
      private EntityDimensions f_20694_ = EntityDimensions.m_20395_(0.6F, 1.8F);

      private java.util.function.Predicate<EntityType<?>> velocityUpdateSupplier = EntityType::defaultVelocitySupplier;
      private java.util.function.ToIntFunction<EntityType<?>> trackingRangeSupplier = EntityType::defaultTrackingRangeSupplier;
      private java.util.function.ToIntFunction<EntityType<?>> updateIntervalSupplier = EntityType::defaultUpdateIntervalSupplier;
      private java.util.function.BiFunction<net.minecraftforge.fmllegacy.network.FMLPlayMessages.SpawnEntity, Level, T> customClientFactory;

      private Builder(EntityType.EntityFactory<T> p_20696_, MobCategory p_20697_) {
         this.f_20685_ = p_20696_;
         this.f_20686_ = p_20697_;
         this.f_20691_ = p_20697_ == MobCategory.CREATURE || p_20697_ == MobCategory.MISC;
      }

      public static <T extends Entity> EntityType.Builder<T> m_20704_(EntityType.EntityFactory<T> p_20705_, MobCategory p_20706_) {
         return new EntityType.Builder<>(p_20705_, p_20706_);
      }

      public static <T extends Entity> EntityType.Builder<T> m_20710_(MobCategory p_20711_) {
         return new EntityType.Builder<>((p_20708_, p_20709_) -> {
            return (T)null;
         }, p_20711_);
      }

      public EntityType.Builder<T> m_20699_(float p_20700_, float p_20701_) {
         this.f_20694_ = EntityDimensions.m_20395_(p_20700_, p_20701_);
         return this;
      }

      public EntityType.Builder<T> m_20698_() {
         this.f_20689_ = false;
         return this;
      }

      public EntityType.Builder<T> m_20716_() {
         this.f_20688_ = false;
         return this;
      }

      public EntityType.Builder<T> m_20719_() {
         this.f_20690_ = true;
         return this;
      }

      public EntityType.Builder<T> m_20714_(Block... p_20715_) {
         this.f_20687_ = ImmutableSet.copyOf(p_20715_);
         return this;
      }

      public EntityType.Builder<T> m_20720_() {
         this.f_20691_ = true;
         return this;
      }

      public EntityType.Builder<T> m_20702_(int p_20703_) {
         this.f_20692_ = p_20703_;
         return this;
      }

      public EntityType.Builder<T> m_20717_(int p_20718_) {
         this.f_20693_ = p_20718_;
         return this;
      }

      public EntityType.Builder<T> setUpdateInterval(int interval) {
          this.updateIntervalSupplier = t->interval;
          return this;
      }

      public EntityType.Builder<T> setTrackingRange(int range) {
          this.trackingRangeSupplier = t->range;
          return this;
      }

      public EntityType.Builder<T> setShouldReceiveVelocityUpdates(boolean value) {
          this.velocityUpdateSupplier = t->value;
          return this;
      }

      /**
       * By default, entities are spawned clientside via {@link EntityType#create(Level)}}.
       * If you need finer control over the spawning process, use this to get read access to the spawn packet.
       */
      public EntityType.Builder<T> setCustomClientFactory(java.util.function.BiFunction<net.minecraftforge.fmllegacy.network.FMLPlayMessages.SpawnEntity, Level, T> customClientFactory) {
          this.customClientFactory = customClientFactory;
          return this;
      }

      public EntityType<T> m_20712_(String p_20713_) {
         if (this.f_20688_) {
            Util.m_137456_(References.f_16785_, p_20713_);
         }

         return new EntityType<>(this.f_20685_, this.f_20686_, this.f_20688_, this.f_20689_, this.f_20690_, this.f_20691_, this.f_20687_, this.f_20694_, this.f_20692_, this.f_20693_, velocityUpdateSupplier, trackingRangeSupplier, updateIntervalSupplier, customClientFactory);
      }
   }

   public interface EntityFactory<T extends Entity> {
      T m_20721_(EntityType<T> p_20722_, Level p_20723_);
   }
}
