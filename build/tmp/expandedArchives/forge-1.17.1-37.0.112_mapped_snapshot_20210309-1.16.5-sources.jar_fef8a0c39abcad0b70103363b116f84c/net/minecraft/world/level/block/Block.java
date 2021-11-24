package net.minecraft.world.level.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.IdMapper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block extends BlockBehaviour implements ItemLike, net.minecraftforge.common.extensions.IForgeBlock {
   protected static final Logger f_49790_ = LogManager.getLogger();
   @Deprecated //Forge: Do not use, use GameRegistry
   public static final IdMapper<BlockState> f_49791_ = net.minecraftforge.registries.GameData.getBlockStateIDMap();
   private static final LoadingCache<VoxelShape, Boolean> f_49785_ = CacheBuilder.newBuilder().maximumSize(512L).weakKeys().build(new CacheLoader<VoxelShape, Boolean>() {
      public Boolean load(VoxelShape p_49972_) {
         return !Shapes.m_83157_(Shapes.m_83144_(), p_49972_, BooleanOp.f_82687_);
      }
   });
   public static final int f_152393_ = 1;
   public static final int f_152394_ = 2;
   public static final int f_152395_ = 4;
   public static final int f_152396_ = 8;
   public static final int f_152397_ = 16;
   public static final int f_152398_ = 32;
   public static final int f_152399_ = 64;
   public static final int f_152400_ = 128;
   public static final int f_152401_ = 4;
   public static final int f_152402_ = 3;
   public static final int f_152388_ = 11;
   public static final float f_152389_ = -1.0F;
   public static final float f_152390_ = 0.0F;
   public static final int f_152391_ = 512;
   protected final StateDefinition<Block, BlockState> f_49792_;
   private BlockState f_49786_;
   @Nullable
   private String f_49787_;
   @Nullable
   private Item f_49788_;
   private static final int f_152392_ = 2048;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>> f_49789_ = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> object2bytelinkedopenhashmap = new Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>(2048, 0.25F) {
         protected void rehash(int p_49979_) {
         }
      };
      object2bytelinkedopenhashmap.defaultReturnValue((byte)127);
      return object2bytelinkedopenhashmap;
   });

   public static int m_49956_(@Nullable BlockState p_49957_) {
      if (p_49957_ == null) {
         return 0;
      } else {
         int i = f_49791_.m_7447_(p_49957_);
         return i == -1 ? 0 : i;
      }
   }

   public static BlockState m_49803_(int p_49804_) {
      BlockState blockstate = f_49791_.m_7942_(p_49804_);
      return blockstate == null ? Blocks.f_50016_.m_49966_() : blockstate;
   }

   public static Block m_49814_(@Nullable Item p_49815_) {
      return p_49815_ instanceof BlockItem ? ((BlockItem)p_49815_).m_40614_() : Blocks.f_50016_;
   }

   public static BlockState m_49897_(BlockState p_49898_, BlockState p_49899_, Level p_49900_, BlockPos p_49901_) {
      VoxelShape voxelshape = Shapes.m_83148_(p_49898_.m_60812_(p_49900_, p_49901_), p_49899_.m_60812_(p_49900_, p_49901_), BooleanOp.f_82683_).m_83216_((double)p_49901_.m_123341_(), (double)p_49901_.m_123342_(), (double)p_49901_.m_123343_());
      if (voxelshape.m_83281_()) {
         return p_49899_;
      } else {
         for(Entity entity : p_49900_.m_45933_((Entity)null, voxelshape.m_83215_())) {
            double d0 = Shapes.m_83134_(Direction.Axis.Y, entity.m_142469_().m_82386_(0.0D, 1.0D, 0.0D), Stream.of(voxelshape), -1.0D);
            entity.m_6021_(entity.m_20185_(), entity.m_20186_() + 1.0D + d0, entity.m_20189_());
         }

         return p_49899_;
      }
   }

   public static VoxelShape m_49796_(double p_49797_, double p_49798_, double p_49799_, double p_49800_, double p_49801_, double p_49802_) {
      return Shapes.m_83048_(p_49797_ / 16.0D, p_49798_ / 16.0D, p_49799_ / 16.0D, p_49800_ / 16.0D, p_49801_ / 16.0D, p_49802_ / 16.0D);
   }

   public static BlockState m_49931_(BlockState p_49932_, LevelAccessor p_49933_, BlockPos p_49934_) {
      BlockState blockstate = p_49932_;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Direction direction : f_60441_) {
         blockpos$mutableblockpos.m_122159_(p_49934_, direction);
         blockstate = blockstate.m_60728_(direction, p_49933_.m_8055_(blockpos$mutableblockpos), p_49933_, p_49934_, blockpos$mutableblockpos);
      }

      return blockstate;
   }

   public static void m_49902_(BlockState p_49903_, BlockState p_49904_, LevelAccessor p_49905_, BlockPos p_49906_, int p_49907_) {
      m_49908_(p_49903_, p_49904_, p_49905_, p_49906_, p_49907_, 512);
   }

   public static void m_49908_(BlockState p_49909_, BlockState p_49910_, LevelAccessor p_49911_, BlockPos p_49912_, int p_49913_, int p_49914_) {
      if (p_49910_ != p_49909_) {
         if (p_49910_.m_60795_()) {
            if (!p_49911_.m_5776_()) {
               p_49911_.m_7740_(p_49912_, (p_49913_ & 32) == 0, (Entity)null, p_49914_);
            }
         } else {
            p_49911_.m_6933_(p_49912_, p_49910_, p_49913_ & -33, p_49914_);
         }
      }

   }

   public Block(BlockBehaviour.Properties p_49795_) {
      super(p_49795_);
      StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
      this.m_7926_(builder);
      this.f_49792_ = builder.m_61101_(Block::m_49966_, BlockState::new);
      this.m_49959_(this.f_49792_.m_61090_());
      if (SharedConstants.f_136183_) {
         String s = this.getClass().getSimpleName();
         if (!s.endsWith("Block")) {
            f_49790_.error("Block classes should end with Block and {} doesn't.", (Object)s);
         }
      }
      initClient();
   }

   public static boolean m_152463_(BlockState p_152464_) {
      return p_152464_.m_60734_() instanceof LeavesBlock || p_152464_.m_60713_(Blocks.f_50375_) || p_152464_.m_60713_(Blocks.f_50143_) || p_152464_.m_60713_(Blocks.f_50144_) || p_152464_.m_60713_(Blocks.f_50186_) || p_152464_.m_60713_(Blocks.f_50133_) || p_152464_.m_60620_(BlockTags.f_13083_);
   }

   public boolean m_6724_(BlockState p_49921_) {
      return this.f_60445_;
   }

   public static boolean m_152444_(BlockState p_152445_, BlockGetter p_152446_, BlockPos p_152447_, Direction p_152448_, BlockPos p_152449_) {
      BlockState blockstate = p_152446_.m_8055_(p_152449_);
      if (p_152445_.m_60719_(blockstate, p_152448_)) {
         return false;
      } else if (blockstate.m_60815_()) {
         Block.BlockStatePairKey block$blockstatepairkey = new Block.BlockStatePairKey(p_152445_, blockstate, p_152448_);
         Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> object2bytelinkedopenhashmap = f_49789_.get();
         byte b0 = object2bytelinkedopenhashmap.getAndMoveToFirst(block$blockstatepairkey);
         if (b0 != 127) {
            return b0 != 0;
         } else {
            VoxelShape voxelshape = p_152445_.m_60655_(p_152446_, p_152447_, p_152448_);
            if (voxelshape.m_83281_()) {
               return true;
            } else {
               VoxelShape voxelshape1 = blockstate.m_60655_(p_152446_, p_152449_, p_152448_.m_122424_());
               boolean flag = Shapes.m_83157_(voxelshape, voxelshape1, BooleanOp.f_82685_);
               if (object2bytelinkedopenhashmap.size() == 2048) {
                  object2bytelinkedopenhashmap.removeLastByte();
               }

               object2bytelinkedopenhashmap.putAndMoveToFirst(block$blockstatepairkey, (byte)(flag ? 1 : 0));
               return flag;
            }
         }
      } else {
         return true;
      }
   }

   public static boolean m_49936_(BlockGetter p_49937_, BlockPos p_49938_) {
      return p_49937_.m_8055_(p_49938_).m_60659_(p_49937_, p_49938_, Direction.UP, SupportType.RIGID);
   }

   public static boolean m_49863_(LevelReader p_49864_, BlockPos p_49865_, Direction p_49866_) {
      BlockState blockstate = p_49864_.m_8055_(p_49865_);
      return p_49866_ == Direction.DOWN && blockstate.m_60620_(BlockTags.f_13056_) ? false : blockstate.m_60659_(p_49864_, p_49865_, p_49866_, SupportType.CENTER);
   }

   public static boolean m_49918_(VoxelShape p_49919_, Direction p_49920_) {
      VoxelShape voxelshape = p_49919_.m_83263_(p_49920_);
      return m_49916_(voxelshape);
   }

   public static boolean m_49916_(VoxelShape p_49917_) {
      return f_49785_.getUnchecked(p_49917_);
   }

   public boolean m_7420_(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
      return !m_49916_(p_49928_.m_60808_(p_49929_, p_49930_)) && p_49928_.m_60819_().m_76178_();
   }

   public void m_7100_(BlockState p_49888_, Level p_49889_, BlockPos p_49890_, Random p_49891_) {
   }

   public void m_6786_(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
   }

   public static List<ItemStack> m_49869_(BlockState p_49870_, ServerLevel p_49871_, BlockPos p_49872_, @Nullable BlockEntity p_49873_) {
      LootContext.Builder lootcontext$builder = (new LootContext.Builder(p_49871_)).m_78977_(p_49871_.f_46441_).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_49872_)).m_78972_(LootContextParams.f_81463_, ItemStack.f_41583_).m_78984_(LootContextParams.f_81462_, p_49873_);
      return p_49870_.m_60724_(lootcontext$builder);
   }

   public static List<ItemStack> m_49874_(BlockState p_49875_, ServerLevel p_49876_, BlockPos p_49877_, @Nullable BlockEntity p_49878_, @Nullable Entity p_49879_, ItemStack p_49880_) {
      LootContext.Builder lootcontext$builder = (new LootContext.Builder(p_49876_)).m_78977_(p_49876_.f_46441_).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(p_49877_)).m_78972_(LootContextParams.f_81463_, p_49880_).m_78984_(LootContextParams.f_81455_, p_49879_).m_78984_(LootContextParams.f_81462_, p_49878_);
      return p_49875_.m_60724_(lootcontext$builder);
   }

   public static void m_152460_(BlockState p_152461_, LootContext.Builder p_152462_) {
      ServerLevel serverlevel = p_152462_.m_78962_();
      BlockPos blockpos = new BlockPos(p_152462_.m_78970_(LootContextParams.f_81460_));
      p_152461_.m_60724_(p_152462_).forEach((p_152406_) -> {
         m_49840_(serverlevel, blockpos, p_152406_);
      });
      p_152461_.m_60612_(serverlevel, blockpos, ItemStack.f_41583_);
   }

   public static void m_49950_(BlockState p_49951_, Level p_49952_, BlockPos p_49953_) {
      if (p_49952_ instanceof ServerLevel) {
         m_49869_(p_49951_, (ServerLevel)p_49952_, p_49953_, (BlockEntity)null).forEach((p_49944_) -> {
            m_49840_(p_49952_, p_49953_, p_49944_);
         });
         p_49951_.m_60612_((ServerLevel)p_49952_, p_49953_, ItemStack.f_41583_);
      }

   }

   public static void m_49892_(BlockState p_49893_, LevelAccessor p_49894_, BlockPos p_49895_, @Nullable BlockEntity p_49896_) {
      if (p_49894_ instanceof ServerLevel) {
         m_49869_(p_49893_, (ServerLevel)p_49894_, p_49895_, p_49896_).forEach((p_49859_) -> {
            m_49840_((ServerLevel)p_49894_, p_49895_, p_49859_);
         });
         p_49893_.m_60612_((ServerLevel)p_49894_, p_49895_, ItemStack.f_41583_);
      }

   }

   public static void m_49881_(BlockState p_49882_, Level p_49883_, BlockPos p_49884_, @Nullable BlockEntity p_49885_, Entity p_49886_, ItemStack p_49887_) {
      if (p_49883_ instanceof ServerLevel) {
         m_49874_(p_49882_, (ServerLevel)p_49883_, p_49884_, p_49885_, p_49886_, p_49887_).forEach((p_49925_) -> {
            m_49840_(p_49883_, p_49884_, p_49925_);
         });
         p_49882_.m_60612_((ServerLevel)p_49883_, p_49884_, p_49887_);
      }

   }

   public static void m_49840_(Level p_49841_, BlockPos p_49842_, ItemStack p_49843_) {
      float f = EntityType.f_20461_.m_20679_() / 2.0F;
      double d0 = (double)((float)p_49842_.m_123341_() + 0.5F) + Mth.m_14064_(p_49841_.f_46441_, -0.25D, 0.25D);
      double d1 = (double)((float)p_49842_.m_123342_() + 0.5F) + Mth.m_14064_(p_49841_.f_46441_, -0.25D, 0.25D) - (double)f;
      double d2 = (double)((float)p_49842_.m_123343_() + 0.5F) + Mth.m_14064_(p_49841_.f_46441_, -0.25D, 0.25D);
      m_152440_(p_49841_, () -> {
         return new ItemEntity(p_49841_, d0, d1, d2, p_49843_);
      }, p_49843_);
   }

   public static void m_152435_(Level p_152436_, BlockPos p_152437_, Direction p_152438_, ItemStack p_152439_) {
      int i = p_152438_.m_122429_();
      int j = p_152438_.m_122430_();
      int k = p_152438_.m_122431_();
      float f = EntityType.f_20461_.m_20678_() / 2.0F;
      float f1 = EntityType.f_20461_.m_20679_() / 2.0F;
      double d0 = (double)((float)p_152437_.m_123341_() + 0.5F) + (i == 0 ? Mth.m_14064_(p_152436_.f_46441_, -0.25D, 0.25D) : (double)((float)i * (0.5F + f)));
      double d1 = (double)((float)p_152437_.m_123342_() + 0.5F) + (j == 0 ? Mth.m_14064_(p_152436_.f_46441_, -0.25D, 0.25D) : (double)((float)j * (0.5F + f1))) - (double)f1;
      double d2 = (double)((float)p_152437_.m_123343_() + 0.5F) + (k == 0 ? Mth.m_14064_(p_152436_.f_46441_, -0.25D, 0.25D) : (double)((float)k * (0.5F + f)));
      double d3 = i == 0 ? Mth.m_14064_(p_152436_.f_46441_, -0.1D, 0.1D) : (double)i * 0.1D;
      double d4 = j == 0 ? Mth.m_14064_(p_152436_.f_46441_, 0.0D, 0.1D) : (double)j * 0.1D + 0.1D;
      double d5 = k == 0 ? Mth.m_14064_(p_152436_.f_46441_, -0.1D, 0.1D) : (double)k * 0.1D;
      m_152440_(p_152436_, () -> {
         return new ItemEntity(p_152436_, d0, d1, d2, p_152439_, d3, d4, d5);
      }, p_152439_);
   }

   private static void m_152440_(Level p_152441_, Supplier<ItemEntity> p_152442_, ItemStack p_152443_) {
      if (!p_152441_.f_46443_ && !p_152443_.m_41619_() && p_152441_.m_46469_().m_46207_(GameRules.f_46136_) && !p_152441_.restoringBlockSnapshots) {
         ItemEntity itementity = p_152442_.get();
         itementity.m_32060_();
         p_152441_.m_7967_(itementity);
      }
   }

   public void m_49805_(ServerLevel p_49806_, BlockPos p_49807_, int p_49808_) {
      if (p_49806_.m_46469_().m_46207_(GameRules.f_46136_) && !p_49806_.restoringBlockSnapshots) {
         ExperienceOrb.m_147082_(p_49806_, Vec3.m_82512_(p_49807_), p_49808_);
      }

   }

   @Deprecated //Forge: Use more sensitive version
   public float m_7325_() {
      return this.f_60444_;
   }

   public void m_7592_(Level p_49844_, BlockPos p_49845_, Explosion p_49846_) {
   }

   public void m_141947_(Level p_152431_, BlockPos p_152432_, BlockState p_152433_, Entity p_152434_) {
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_49820_) {
      return this.m_49966_();
   }

   public void m_6240_(Level p_49827_, Player p_49828_, BlockPos p_49829_, BlockState p_49830_, @Nullable BlockEntity p_49831_, ItemStack p_49832_) {
      p_49828_.m_36246_(Stats.f_12949_.m_12902_(this));
      p_49828_.m_36399_(0.005F);
      m_49881_(p_49830_, p_49827_, p_49829_, p_49831_, p_49828_, p_49832_);
   }

   public void m_6402_(Level p_49847_, BlockPos p_49848_, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
   }

   public boolean m_5568_() {
      return !this.f_60442_.m_76333_() && !this.f_60442_.m_76332_();
   }

   public MutableComponent m_49954_() {
      return new TranslatableComponent(this.m_7705_());
   }

   public String m_7705_() {
      if (this.f_49787_ == null) {
         this.f_49787_ = Util.m_137492_("block", Registry.f_122824_.m_7981_(this));
      }

      return this.f_49787_;
   }

   public void m_142072_(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
      p_152429_.m_142535_(p_152430_, 1.0F, DamageSource.f_19315_);
   }

   public void m_5548_(BlockGetter p_49821_, Entity p_49822_) {
      p_49822_.m_20256_(p_49822_.m_20184_().m_82542_(1.0D, 0.0D, 1.0D));
      p_49822_.m_20256_(p_49822_.m_20184_().m_82542_(1.0D, 0.0D, 1.0D));
   }

   @Deprecated //Forge: Use more sensitive version
   public ItemStack m_7397_(BlockGetter p_49823_, BlockPos p_49824_, BlockState p_49825_) {
      return new ItemStack(this);
   }

   public void m_49811_(CreativeModeTab p_49812_, NonNullList<ItemStack> p_49813_) {
      p_49813_.add(new ItemStack(this));
   }

   public float m_49958_() {
      return this.f_60447_;
   }

   public float m_49961_() {
      return this.f_60448_;
   }

   public float m_49964_() {
      return this.f_60449_;
   }

   protected void m_142387_(Level p_152422_, Player p_152423_, BlockPos p_152424_, BlockState p_152425_) {
      p_152422_.m_5898_(p_152423_, 2001, p_152424_, m_49956_(p_152425_));
   }

   public void m_5707_(Level p_49852_, BlockPos p_49853_, BlockState p_49854_, Player p_49855_) {
      this.m_142387_(p_49852_, p_49855_, p_49853_, p_49854_);
      if (p_49854_.m_60620_(BlockTags.f_13088_)) {
         PiglinAi.m_34873_(p_49855_, false);
      }

      p_49852_.m_142346_(p_49855_, GameEvent.f_157794_, p_49853_);
   }

   public void m_141997_(BlockState p_152450_, Level p_152451_, BlockPos p_152452_, Biome.Precipitation p_152453_) {
   }

   @Deprecated //Forge: Use more sensitive version
   public boolean m_6903_(Explosion p_49826_) {
      return true;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49915_) {
   }

   public StateDefinition<Block, BlockState> m_49965_() {
      return this.f_49792_;
   }

   protected final void m_49959_(BlockState p_49960_) {
      this.f_49786_ = p_49960_;
   }

   public final BlockState m_49966_() {
      return this.f_49786_;
   }

   public final BlockState m_152465_(BlockState p_152466_) {
      BlockState blockstate = this.m_49966_();

      for(Property<?> property : p_152466_.m_60734_().m_49965_().m_61092_()) {
         if (blockstate.m_61138_(property)) {
            blockstate = m_152454_(p_152466_, blockstate, property);
         }
      }

      return blockstate;
   }

   private static <T extends Comparable<T>> BlockState m_152454_(BlockState p_152455_, BlockState p_152456_, Property<T> p_152457_) {
      return p_152456_.m_61124_(p_152457_, p_152455_.m_61143_(p_152457_));
   }

   @Deprecated //Forge: Use more sensitive version {@link IForgeBlockState#getSoundType(IWorldReader, BlockPos, Entity) }
   public SoundType m_49962_(BlockState p_49963_) {
      return this.f_60446_;
   }

   public Item m_5456_() {
      if (this.f_49788_ == null) {
         this.f_49788_ = Item.m_41439_(this);
      }

      return this.f_49788_.delegate.get(); //Forge: Vanilla caches the items, update with registry replacements.
   }

   public boolean m_49967_() {
      return this.f_60438_;
   }

   public String toString() {
      return "Block{" + getRegistryName() + "}";
   }

   public void m_5871_(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> p_49818_, TooltipFlag p_49819_) {
   }

   protected Block m_7374_() {
      return this;
   }

   protected ImmutableMap<BlockState, VoxelShape> m_152458_(Function<BlockState, VoxelShape> p_152459_) {
      return this.f_49792_.m_61056_().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), p_152459_));
   }

   /* ======================================== FORGE START =====================================*/
   protected Random RANDOM = new Random();
   private final net.minecraftforge.common.util.ReverseTagWrapper<Block> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, BlockTags::m_13115_);
   private Object renderProperties;

   /*
      DO NOT CALL, IT WILL DISAPPEAR IN THE FUTURE
      Call RenderProperties.get instead
    */
   public Object getRenderPropertiesInternal() {
      return renderProperties;
   }

   private void initClient() {
      // Minecraft instance isn't available in datagen, so don't call initializeClient if in datagen
      if (net.minecraftforge.fml.loading.FMLEnvironment.dist == net.minecraftforge.api.distmarker.Dist.CLIENT && !net.minecraftforge.fml.loading.FMLLoader.getLaunchHandler().isData()) {
         initializeClient(properties -> {
            if (properties == this)
               throw new IllegalStateException("Don't extend IBlockRenderProperties in your block, use an anonymous class instead.");
            this.renderProperties = properties;
         });
      }
   }

   public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IBlockRenderProperties> consumer) {
   }

   @Override
   public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
      BlockState plant = plantable.getPlant(world, pos.m_142300_(facing));
      net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.m_142300_(facing));

      if (plant.m_60734_() == Blocks.f_50128_)
         return state.m_60713_(Blocks.f_50128_) || state.m_60713_(Blocks.f_49992_) || state.m_60713_(Blocks.f_49993_);

      if (plant.m_60734_() == Blocks.f_50130_ && this == Blocks.f_50130_)
         return true;

      if (plantable instanceof BushBlock && ((BushBlock)plantable).m_6266_(state, world, pos))
         return true;

      if (net.minecraftforge.common.PlantType.DESERT.equals(type)) {
         return this == Blocks.f_49992_ || this == Blocks.f_50352_ || this instanceof GlazedTerracottaBlock;
      } else if (net.minecraftforge.common.PlantType.NETHER.equals(type)) {
         return this == Blocks.f_50135_;
      } else if (net.minecraftforge.common.PlantType.CROP.equals(type)) {
         return state.m_60713_(Blocks.f_50093_);
      } else if (net.minecraftforge.common.PlantType.CAVE.equals(type)) {
         return state.m_60783_(world, pos, Direction.UP);
      } else if (net.minecraftforge.common.PlantType.PLAINS.equals(type)) {
         return this == Blocks.f_50440_ || net.minecraftforge.common.Tags.Blocks.DIRT.m_8110_(this) || this == Blocks.f_50093_;
      } else if (net.minecraftforge.common.PlantType.WATER.equals(type)) {
         return state.m_60767_() == net.minecraft.world.level.material.Material.f_76305_; //&& state.getValue(BlockLiquidWrapper)
      } else if (net.minecraftforge.common.PlantType.BEACH.equals(type)) {
         boolean isBeach = state.m_60713_(Blocks.f_50440_) || net.minecraftforge.common.Tags.Blocks.DIRT.m_8110_(this) || state.m_60713_(Blocks.f_49992_) || state.m_60713_(Blocks.f_49993_);
         boolean hasWater = false;
         for (Direction face : Direction.Plane.HORIZONTAL) {
            BlockState blockState = world.m_8055_(pos.m_142300_(face));
            net.minecraft.world.level.material.FluidState fluidState = world.m_6425_(pos.m_142300_(face));
            hasWater |= blockState.m_60713_(Blocks.f_50449_);
            hasWater |= fluidState.m_76153_(net.minecraft.tags.FluidTags.f_13131_);
            if (hasWater)
               break; //No point continuing.
         }
         return isBeach && hasWater;
      }
      return false;
   }

   @Override
   public final java.util.Set<net.minecraft.resources.ResourceLocation> getTags() {
      return reverseTags.getTagNames();
   }

   /* ========================================= FORGE END ======================================*/

   public static final class BlockStatePairKey {
      private final BlockState f_49980_;
      private final BlockState f_49981_;
      private final Direction f_49982_;

      public BlockStatePairKey(BlockState p_49984_, BlockState p_49985_, Direction p_49986_) {
         this.f_49980_ = p_49984_;
         this.f_49981_ = p_49985_;
         this.f_49982_ = p_49986_;
      }

      public boolean equals(Object p_49988_) {
         if (this == p_49988_) {
            return true;
         } else if (!(p_49988_ instanceof Block.BlockStatePairKey)) {
            return false;
         } else {
            Block.BlockStatePairKey block$blockstatepairkey = (Block.BlockStatePairKey)p_49988_;
            return this.f_49980_ == block$blockstatepairkey.f_49980_ && this.f_49981_ == block$blockstatepairkey.f_49981_ && this.f_49982_ == block$blockstatepairkey.f_49982_;
         }
      }

      public int hashCode() {
         int i = this.f_49980_.hashCode();
         i = 31 * i + this.f_49981_.hashCode();
         return 31 * i + this.f_49982_.hashCode();
      }
   }
}
