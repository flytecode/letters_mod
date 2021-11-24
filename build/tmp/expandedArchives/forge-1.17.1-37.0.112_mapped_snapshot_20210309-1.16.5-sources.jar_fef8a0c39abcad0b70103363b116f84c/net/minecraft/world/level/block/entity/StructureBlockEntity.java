package net.minecraft.world.level.block.entity;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StructureBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class StructureBlockEntity extends BlockEntity {
   private static final int f_155777_ = 5;
   public static final int f_155774_ = 48;
   public static final int f_155775_ = 48;
   public static final String f_155776_ = "author";
   private ResourceLocation f_59812_;
   private String f_59813_ = "";
   private String f_59814_ = "";
   private BlockPos f_59815_ = new BlockPos(0, 1, 0);
   private Vec3i f_59816_ = Vec3i.f_123288_;
   private Mirror f_59817_ = Mirror.NONE;
   private Rotation f_59818_ = Rotation.NONE;
   private StructureMode f_59819_;
   private boolean f_59820_ = true;
   private boolean f_59821_;
   private boolean f_59822_;
   private boolean f_59823_ = true;
   private float f_59824_ = 1.0F;
   private long f_59825_;

   public StructureBlockEntity(BlockPos p_155779_, BlockState p_155780_) {
      super(BlockEntityType.f_58936_, p_155779_, p_155780_);
      this.f_59819_ = p_155780_.m_61143_(StructureBlock.f_57110_);
   }

   public CompoundTag m_6945_(CompoundTag p_59873_) {
      super.m_6945_(p_59873_);
      p_59873_.m_128359_("name", this.m_59895_());
      p_59873_.m_128359_("author", this.f_59813_);
      p_59873_.m_128359_("metadata", this.f_59814_);
      p_59873_.m_128405_("posX", this.f_59815_.m_123341_());
      p_59873_.m_128405_("posY", this.f_59815_.m_123342_());
      p_59873_.m_128405_("posZ", this.f_59815_.m_123343_());
      p_59873_.m_128405_("sizeX", this.f_59816_.m_123341_());
      p_59873_.m_128405_("sizeY", this.f_59816_.m_123342_());
      p_59873_.m_128405_("sizeZ", this.f_59816_.m_123343_());
      p_59873_.m_128359_("rotation", this.f_59818_.toString());
      p_59873_.m_128359_("mirror", this.f_59817_.toString());
      p_59873_.m_128359_("mode", this.f_59819_.toString());
      p_59873_.m_128379_("ignoreEntities", this.f_59820_);
      p_59873_.m_128379_("powered", this.f_59821_);
      p_59873_.m_128379_("showair", this.f_59822_);
      p_59873_.m_128379_("showboundingbox", this.f_59823_);
      p_59873_.m_128350_("integrity", this.f_59824_);
      p_59873_.m_128356_("seed", this.f_59825_);
      return p_59873_;
   }

   public void m_142466_(CompoundTag p_155800_) {
      super.m_142466_(p_155800_);
      this.m_59868_(p_155800_.m_128461_("name"));
      this.f_59813_ = p_155800_.m_128461_("author");
      this.f_59814_ = p_155800_.m_128461_("metadata");
      int i = Mth.m_14045_(p_155800_.m_128451_("posX"), -48, 48);
      int j = Mth.m_14045_(p_155800_.m_128451_("posY"), -48, 48);
      int k = Mth.m_14045_(p_155800_.m_128451_("posZ"), -48, 48);
      this.f_59815_ = new BlockPos(i, j, k);
      int l = Mth.m_14045_(p_155800_.m_128451_("sizeX"), 0, 48);
      int i1 = Mth.m_14045_(p_155800_.m_128451_("sizeY"), 0, 48);
      int j1 = Mth.m_14045_(p_155800_.m_128451_("sizeZ"), 0, 48);
      this.f_59816_ = new Vec3i(l, i1, j1);

      try {
         this.f_59818_ = Rotation.valueOf(p_155800_.m_128461_("rotation"));
      } catch (IllegalArgumentException illegalargumentexception2) {
         this.f_59818_ = Rotation.NONE;
      }

      try {
         this.f_59817_ = Mirror.valueOf(p_155800_.m_128461_("mirror"));
      } catch (IllegalArgumentException illegalargumentexception1) {
         this.f_59817_ = Mirror.NONE;
      }

      try {
         this.f_59819_ = StructureMode.valueOf(p_155800_.m_128461_("mode"));
      } catch (IllegalArgumentException illegalargumentexception) {
         this.f_59819_ = StructureMode.DATA;
      }

      this.f_59820_ = p_155800_.m_128471_("ignoreEntities");
      this.f_59821_ = p_155800_.m_128471_("powered");
      this.f_59822_ = p_155800_.m_128471_("showair");
      this.f_59823_ = p_155800_.m_128471_("showboundingbox");
      if (p_155800_.m_128441_("integrity")) {
         this.f_59824_ = p_155800_.m_128457_("integrity");
      } else {
         this.f_59824_ = 1.0F;
      }

      this.f_59825_ = p_155800_.m_128454_("seed");
      this.m_59836_();
   }

   private void m_59836_() {
      if (this.f_58857_ != null) {
         BlockPos blockpos = this.m_58899_();
         BlockState blockstate = this.f_58857_.m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50677_)) {
            this.f_58857_.m_7731_(blockpos, blockstate.m_61124_(StructureBlock.f_57110_, this.f_59819_), 2);
         }

      }
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 7, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   public boolean m_59853_(Player p_59854_) {
      if (!p_59854_.m_36337_()) {
         return false;
      } else {
         if (p_59854_.m_20193_().f_46443_) {
            p_59854_.m_5966_(this);
         }

         return true;
      }
   }

   public String m_59895_() {
      return this.f_59812_ == null ? "" : this.f_59812_.toString();
   }

   public String m_59900_() {
      return this.f_59812_ == null ? "" : this.f_59812_.m_135815_();
   }

   public boolean m_59901_() {
      return this.f_59812_ != null;
   }

   public void m_59868_(@Nullable String p_59869_) {
      this.m_59874_(StringUtil.m_14408_(p_59869_) ? null : ResourceLocation.m_135820_(p_59869_));
   }

   public void m_59874_(@Nullable ResourceLocation p_59875_) {
      this.f_59812_ = p_59875_;
   }

   public void m_59851_(LivingEntity p_59852_) {
      this.f_59813_ = p_59852_.m_7755_().getString();
   }

   public BlockPos m_59902_() {
      return this.f_59815_;
   }

   public void m_59885_(BlockPos p_59886_) {
      this.f_59815_ = p_59886_;
   }

   public Vec3i m_155805_() {
      return this.f_59816_;
   }

   public void m_155797_(Vec3i p_155798_) {
      this.f_59816_ = p_155798_;
   }

   public Mirror m_59905_() {
      return this.f_59817_;
   }

   public void m_59881_(Mirror p_59882_) {
      this.f_59817_ = p_59882_;
   }

   public Rotation m_59906_() {
      return this.f_59818_;
   }

   public void m_59883_(Rotation p_59884_) {
      this.f_59818_ = p_59884_;
   }

   public String m_59907_() {
      return this.f_59814_;
   }

   public void m_59887_(String p_59888_) {
      this.f_59814_ = p_59888_;
   }

   public StructureMode m_59908_() {
      return this.f_59819_;
   }

   public void m_59860_(StructureMode p_59861_) {
      this.f_59819_ = p_59861_;
      BlockState blockstate = this.f_58857_.m_8055_(this.m_58899_());
      if (blockstate.m_60713_(Blocks.f_50677_)) {
         this.f_58857_.m_7731_(this.m_58899_(), blockstate.m_61124_(StructureBlock.f_57110_, p_59861_), 2);
      }

   }

   public boolean m_59910_() {
      return this.f_59820_;
   }

   public void m_59876_(boolean p_59877_) {
      this.f_59820_ = p_59877_;
   }

   public float m_59827_() {
      return this.f_59824_;
   }

   public void m_59838_(float p_59839_) {
      this.f_59824_ = p_59839_;
   }

   public long m_59828_() {
      return this.f_59825_;
   }

   public void m_59840_(long p_59841_) {
      this.f_59825_ = p_59841_;
   }

   public boolean m_59829_() {
      if (this.f_59819_ != StructureMode.SAVE) {
         return false;
      } else {
         BlockPos blockpos = this.m_58899_();
         int i = 80;
         BlockPos blockpos1 = new BlockPos(blockpos.m_123341_() - 80, this.f_58857_.m_141937_(), blockpos.m_123343_() - 80);
         BlockPos blockpos2 = new BlockPos(blockpos.m_123341_() + 80, this.f_58857_.m_151558_() - 1, blockpos.m_123343_() + 80);
         Stream<BlockPos> stream = this.m_155791_(blockpos1, blockpos2);
         return m_155794_(blockpos, stream).filter((p_155790_) -> {
            int j = p_155790_.m_162399_() - p_155790_.m_162395_();
            int k = p_155790_.m_162400_() - p_155790_.m_162396_();
            int l = p_155790_.m_162401_() - p_155790_.m_162398_();
            if (j > 1 && k > 1 && l > 1) {
               this.f_59815_ = new BlockPos(p_155790_.m_162395_() - blockpos.m_123341_() + 1, p_155790_.m_162396_() - blockpos.m_123342_() + 1, p_155790_.m_162398_() - blockpos.m_123343_() + 1);
               this.f_59816_ = new Vec3i(j - 1, k - 1, l - 1);
               this.m_6596_();
               BlockState blockstate = this.f_58857_.m_8055_(blockpos);
               this.f_58857_.m_7260_(blockpos, blockstate, blockstate, 3);
               return true;
            } else {
               return false;
            }
         }).isPresent();
      }
   }

   private Stream<BlockPos> m_155791_(BlockPos p_155792_, BlockPos p_155793_) {
      return BlockPos.m_121990_(p_155792_, p_155793_).filter((p_155804_) -> {
         return this.f_58857_.m_8055_(p_155804_).m_60713_(Blocks.f_50677_);
      }).map(this.f_58857_::m_7702_).filter((p_155802_) -> {
         return p_155802_ instanceof StructureBlockEntity;
      }).map((p_155785_) -> {
         return (StructureBlockEntity)p_155785_;
      }).filter((p_155787_) -> {
         return p_155787_.f_59819_ == StructureMode.CORNER && Objects.equals(this.f_59812_, p_155787_.f_59812_);
      }).map(BlockEntity::m_58899_);
   }

   private static Optional<BoundingBox> m_155794_(BlockPos p_155795_, Stream<BlockPos> p_155796_) {
      Iterator<BlockPos> iterator = p_155796_.iterator();
      if (!iterator.hasNext()) {
         return Optional.empty();
      } else {
         BlockPos blockpos = iterator.next();
         BoundingBox boundingbox = new BoundingBox(blockpos);
         if (iterator.hasNext()) {
            iterator.forEachRemaining(boundingbox::m_162371_);
         } else {
            boundingbox.m_162371_(p_155795_);
         }

         return Optional.of(boundingbox);
      }
   }

   public boolean m_59830_() {
      return this.m_59889_(true);
   }

   public boolean m_59889_(boolean p_59890_) {
      if (this.f_59819_ == StructureMode.SAVE && !this.f_58857_.f_46443_ && this.f_59812_ != null) {
         BlockPos blockpos = this.m_58899_().m_141952_(this.f_59815_);
         ServerLevel serverlevel = (ServerLevel)this.f_58857_;
         StructureManager structuremanager = serverlevel.m_8875_();

         StructureTemplate structuretemplate;
         try {
            structuretemplate = structuremanager.m_74341_(this.f_59812_);
         } catch (ResourceLocationException resourcelocationexception1) {
            return false;
         }

         structuretemplate.m_163802_(this.f_58857_, blockpos, this.f_59816_, !this.f_59820_, Blocks.f_50454_);
         structuretemplate.m_74612_(this.f_59813_);
         if (p_59890_) {
            try {
               return structuremanager.m_74351_(this.f_59812_);
            } catch (ResourceLocationException resourcelocationexception) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean m_59842_(ServerLevel p_59843_) {
      return this.m_59844_(p_59843_, true);
   }

   private static Random m_59879_(long p_59880_) {
      return p_59880_ == 0L ? new Random(Util.m_137550_()) : new Random(p_59880_);
   }

   public boolean m_59844_(ServerLevel p_59845_, boolean p_59846_) {
      if (this.f_59819_ == StructureMode.LOAD && this.f_59812_ != null) {
         StructureManager structuremanager = p_59845_.m_8875_();

         Optional<StructureTemplate> optional;
         try {
            optional = structuremanager.m_163774_(this.f_59812_);
         } catch (ResourceLocationException resourcelocationexception) {
            return false;
         }

         return !optional.isPresent() ? false : this.m_59847_(p_59845_, p_59846_, optional.get());
      } else {
         return false;
      }
   }

   public boolean m_59847_(ServerLevel p_59848_, boolean p_59849_, StructureTemplate p_59850_) {
      BlockPos blockpos = this.m_58899_();
      if (!StringUtil.m_14408_(p_59850_.m_74627_())) {
         this.f_59813_ = p_59850_.m_74627_();
      }

      Vec3i vec3i = p_59850_.m_163801_();
      boolean flag = this.f_59816_.equals(vec3i);
      if (!flag) {
         this.f_59816_ = vec3i;
         this.m_6596_();
         BlockState blockstate = p_59848_.m_8055_(blockpos);
         p_59848_.m_7260_(blockpos, blockstate, blockstate, 3);
      }

      if (p_59849_ && !flag) {
         return false;
      } else {
         StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).m_74377_(this.f_59817_).m_74379_(this.f_59818_).m_74392_(this.f_59820_);
         if (this.f_59824_ < 1.0F) {
            structureplacesettings.m_74394_().m_74383_(new BlockRotProcessor(Mth.m_14036_(this.f_59824_, 0.0F, 1.0F))).m_74390_(m_59879_(this.f_59825_));
         }

         BlockPos blockpos1 = blockpos.m_141952_(this.f_59815_);
         p_59850_.m_74536_(p_59848_, blockpos1, blockpos1, structureplacesettings, m_59879_(this.f_59825_), 2);
         return true;
      }
   }

   public void m_59831_() {
      if (this.f_59812_ != null) {
         ServerLevel serverlevel = (ServerLevel)this.f_58857_;
         StructureManager structuremanager = serverlevel.m_8875_();
         structuremanager.m_74353_(this.f_59812_);
      }
   }

   public boolean m_59832_() {
      if (this.f_59819_ == StructureMode.LOAD && !this.f_58857_.f_46443_ && this.f_59812_ != null) {
         ServerLevel serverlevel = (ServerLevel)this.f_58857_;
         StructureManager structuremanager = serverlevel.m_8875_();

         try {
            return structuremanager.m_163774_(this.f_59812_).isPresent();
         } catch (ResourceLocationException resourcelocationexception) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_59833_() {
      return this.f_59821_;
   }

   public void m_59893_(boolean p_59894_) {
      this.f_59821_ = p_59894_;
   }

   public boolean m_59834_() {
      return this.f_59822_;
   }

   public void m_59896_(boolean p_59897_) {
      this.f_59822_ = p_59897_;
   }

   public boolean m_59835_() {
      return this.f_59823_;
   }

   public void m_59898_(boolean p_59899_) {
      this.f_59823_ = p_59899_;
   }

   public static enum UpdateType {
      UPDATE_DATA,
      SAVE_AREA,
      LOAD_AREA,
      SCAN_AREA;
   }
}