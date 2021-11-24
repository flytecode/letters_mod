package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableSet;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.material.FluidState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructurePiece {
   private static final Logger f_163536_ = LogManager.getLogger();
   protected static final BlockState f_73382_ = Blocks.f_50627_.m_49966_();
   protected BoundingBox f_73383_;
   @Nullable
   private Direction f_73377_;
   private Mirror f_73378_;
   private Rotation f_73379_;
   protected int f_73384_;
   private final StructurePieceType f_73380_;
   private static final Set<Block> f_73381_ = ImmutableSet.<Block>builder().add(Blocks.f_50198_).add(Blocks.f_50081_).add(Blocks.f_50082_).add(Blocks.f_50132_).add(Blocks.f_50479_).add(Blocks.f_50483_).add(Blocks.f_50482_).add(Blocks.f_50480_).add(Blocks.f_50481_).add(Blocks.f_50155_).add(Blocks.f_50183_).build();

   protected StructurePiece(StructurePieceType p_163538_, int p_163539_, BoundingBox p_163540_) {
      this.f_73380_ = p_163538_;
      this.f_73384_ = p_163539_;
      this.f_73383_ = p_163540_;
   }

   public StructurePiece(StructurePieceType p_73390_, CompoundTag p_73391_) {
      this(p_73390_, p_73391_.m_128451_("GD"), BoundingBox.f_162354_.parse(NbtOps.f_128958_, p_73391_.m_128423_("BB")).resultOrPartial(f_163536_::error).orElseThrow(() -> {
         return new IllegalArgumentException("Invalid boundingbox");
      }));
      int i = p_73391_.m_128451_("O");
      this.m_73519_(i == -1 ? null : Direction.m_122407_(i));
   }

   protected static BoundingBox m_163541_(int p_163542_, int p_163543_, int p_163544_, Direction p_163545_, int p_163546_, int p_163547_, int p_163548_) {
      return p_163545_.m_122434_() == Direction.Axis.Z ? new BoundingBox(p_163542_, p_163543_, p_163544_, p_163542_ + p_163546_ - 1, p_163543_ + p_163547_ - 1, p_163544_ + p_163548_ - 1) : new BoundingBox(p_163542_, p_163543_, p_163544_, p_163542_ + p_163548_ - 1, p_163543_ + p_163547_ - 1, p_163544_ + p_163546_ - 1);
   }

   protected static Direction m_163580_(Random p_163581_) {
      return Direction.Plane.HORIZONTAL.m_122560_(p_163581_);
   }

   public final CompoundTag m_163549_(ServerLevel p_163550_) {
      if (Registry.f_122843_.m_7981_(this.m_73550_()) == null) { // FORGE: Friendlier error then the Null String error below.
         throw new RuntimeException("StructurePiece \"" + this.getClass().getName() + "\": \"" + this.m_73550_() + "\" missing ID Mapping, Modder see MapGenStructureIO");
      }
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("id", Registry.f_122843_.m_7981_(this.m_73550_()).toString());
      BoundingBox.f_162354_.encodeStart(NbtOps.f_128958_, this.f_73383_).resultOrPartial(f_163536_::error).ifPresent((p_163579_) -> {
         compoundtag.m_128365_("BB", p_163579_);
      });
      Direction direction = this.m_73549_();
      compoundtag.m_128405_("O", direction == null ? -1 : direction.m_122416_());
      compoundtag.m_128405_("GD", this.f_73384_);
      this.m_142347_(p_163550_, compoundtag);
      return compoundtag;
   }

   protected abstract void m_142347_(ServerLevel p_163551_, CompoundTag p_163552_);

   public NoiseEffect m_142318_() {
      return NoiseEffect.BEARD;
   }

   public void m_142537_(StructurePiece p_163574_, StructurePieceAccessor p_163575_, Random p_163576_) {
   }

   public abstract boolean m_7832_(WorldGenLevel p_73427_, StructureFeatureManager p_73428_, ChunkGenerator p_73429_, Random p_73430_, BoundingBox p_73431_, ChunkPos p_73432_, BlockPos p_73433_);

   public BoundingBox m_73547_() {
      return this.f_73383_;
   }

   public int m_73548_() {
      return this.f_73384_;
   }

   public boolean m_73411_(ChunkPos p_73412_, int p_73413_) {
      int i = p_73412_.m_45604_();
      int j = p_73412_.m_45605_();
      return this.f_73383_.m_71019_(i - p_73413_, j - p_73413_, i + 15 + p_73413_, j + 15 + p_73413_);
   }

   public BlockPos m_142171_() {
      return new BlockPos(this.f_73383_.m_162394_());
   }

   protected BlockPos.MutableBlockPos m_163582_(int p_163583_, int p_163584_, int p_163585_) {
      return new BlockPos.MutableBlockPos(this.m_73392_(p_163583_, p_163585_), this.m_73544_(p_163584_), this.m_73525_(p_163583_, p_163585_));
   }

   protected int m_73392_(int p_73393_, int p_73394_) {
      Direction direction = this.m_73549_();
      if (direction == null) {
         return p_73393_;
      } else {
         switch(direction) {
         case NORTH:
         case SOUTH:
            return this.f_73383_.m_162395_() + p_73393_;
         case WEST:
            return this.f_73383_.m_162399_() - p_73394_;
         case EAST:
            return this.f_73383_.m_162395_() + p_73394_;
         default:
            return p_73393_;
         }
      }
   }

   protected int m_73544_(int p_73545_) {
      return this.m_73549_() == null ? p_73545_ : p_73545_ + this.f_73383_.m_162396_();
   }

   protected int m_73525_(int p_73526_, int p_73527_) {
      Direction direction = this.m_73549_();
      if (direction == null) {
         return p_73527_;
      } else {
         switch(direction) {
         case NORTH:
            return this.f_73383_.m_162401_() - p_73527_;
         case SOUTH:
            return this.f_73383_.m_162398_() + p_73527_;
         case WEST:
         case EAST:
            return this.f_73383_.m_162398_() + p_73526_;
         default:
            return p_73527_;
         }
      }
   }

   protected void m_73434_(WorldGenLevel p_73435_, BlockState p_73436_, int p_73437_, int p_73438_, int p_73439_, BoundingBox p_73440_) {
      BlockPos blockpos = this.m_163582_(p_73437_, p_73438_, p_73439_);
      if (p_73440_.m_71051_(blockpos)) {
         if (this.m_142085_(p_73435_, p_73437_, p_73438_, p_73439_, p_73440_)) {
            if (this.f_73378_ != Mirror.NONE) {
               p_73436_ = p_73436_.m_60715_(this.f_73378_);
            }

            if (this.f_73379_ != Rotation.NONE) {
               p_73436_ = p_73436_.m_60717_(this.f_73379_);
            }

            p_73435_.m_7731_(blockpos, p_73436_, 2);
            FluidState fluidstate = p_73435_.m_6425_(blockpos);
            if (!fluidstate.m_76178_()) {
               p_73435_.m_6217_().m_5945_(blockpos, fluidstate.m_76152_(), 0);
            }

            if (f_73381_.contains(p_73436_.m_60734_())) {
               p_73435_.m_46865_(blockpos).m_8113_(blockpos);
            }

         }
      }
   }

   protected boolean m_142085_(LevelReader p_163553_, int p_163554_, int p_163555_, int p_163556_, BoundingBox p_163557_) {
      return true;
   }

   protected BlockState m_73398_(BlockGetter p_73399_, int p_73400_, int p_73401_, int p_73402_, BoundingBox p_73403_) {
      BlockPos blockpos = this.m_163582_(p_73400_, p_73401_, p_73402_);
      return !p_73403_.m_71051_(blockpos) ? Blocks.f_50016_.m_49966_() : p_73399_.m_8055_(blockpos);
   }

   protected boolean m_73414_(LevelReader p_73415_, int p_73416_, int p_73417_, int p_73418_, BoundingBox p_73419_) {
      BlockPos blockpos = this.m_163582_(p_73416_, p_73417_ + 1, p_73418_);
      if (!p_73419_.m_71051_(blockpos)) {
         return false;
      } else {
         return blockpos.m_123342_() < p_73415_.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, blockpos.m_123341_(), blockpos.m_123343_());
      }
   }

   protected void m_73535_(WorldGenLevel p_73536_, BoundingBox p_73537_, int p_73538_, int p_73539_, int p_73540_, int p_73541_, int p_73542_, int p_73543_) {
      for(int i = p_73539_; i <= p_73542_; ++i) {
         for(int j = p_73538_; j <= p_73541_; ++j) {
            for(int k = p_73540_; k <= p_73543_; ++k) {
               this.m_73434_(p_73536_, Blocks.f_50016_.m_49966_(), j, i, k, p_73537_);
            }
         }
      }

   }

   protected void m_73441_(WorldGenLevel p_73442_, BoundingBox p_73443_, int p_73444_, int p_73445_, int p_73446_, int p_73447_, int p_73448_, int p_73449_, BlockState p_73450_, BlockState p_73451_, boolean p_73452_) {
      for(int i = p_73445_; i <= p_73448_; ++i) {
         for(int j = p_73444_; j <= p_73447_; ++j) {
            for(int k = p_73446_; k <= p_73449_; ++k) {
               if (!p_73452_ || !this.m_73398_(p_73442_, j, i, k, p_73443_).m_60795_()) {
                  if (i != p_73445_ && i != p_73448_ && j != p_73444_ && j != p_73447_ && k != p_73446_ && k != p_73449_) {
                     this.m_73434_(p_73442_, p_73451_, j, i, k, p_73443_);
                  } else {
                     this.m_73434_(p_73442_, p_73450_, j, i, k, p_73443_);
                  }
               }
            }
         }
      }

   }

   protected void m_163558_(WorldGenLevel p_163559_, BoundingBox p_163560_, BoundingBox p_163561_, BlockState p_163562_, BlockState p_163563_, boolean p_163564_) {
      this.m_73441_(p_163559_, p_163560_, p_163561_.m_162395_(), p_163561_.m_162396_(), p_163561_.m_162398_(), p_163561_.m_162399_(), p_163561_.m_162400_(), p_163561_.m_162401_(), p_163562_, p_163563_, p_163564_);
   }

   protected void m_73464_(WorldGenLevel p_73465_, BoundingBox p_73466_, int p_73467_, int p_73468_, int p_73469_, int p_73470_, int p_73471_, int p_73472_, boolean p_73473_, Random p_73474_, StructurePiece.BlockSelector p_73475_) {
      for(int i = p_73468_; i <= p_73471_; ++i) {
         for(int j = p_73467_; j <= p_73470_; ++j) {
            for(int k = p_73469_; k <= p_73472_; ++k) {
               if (!p_73473_ || !this.m_73398_(p_73465_, j, i, k, p_73466_).m_60795_()) {
                  p_73475_.m_7889_(p_73474_, j, i, k, i == p_73468_ || i == p_73471_ || j == p_73467_ || j == p_73470_ || k == p_73469_ || k == p_73472_);
                  this.m_73434_(p_73465_, p_73475_.m_73555_(), j, i, k, p_73466_);
               }
            }
         }
      }

   }

   protected void m_163565_(WorldGenLevel p_163566_, BoundingBox p_163567_, BoundingBox p_163568_, boolean p_163569_, Random p_163570_, StructurePiece.BlockSelector p_163571_) {
      this.m_73464_(p_163566_, p_163567_, p_163568_.m_162395_(), p_163568_.m_162396_(), p_163568_.m_162398_(), p_163568_.m_162399_(), p_163568_.m_162400_(), p_163568_.m_162401_(), p_163569_, p_163570_, p_163571_);
   }

   protected void m_73476_(WorldGenLevel p_73477_, BoundingBox p_73478_, Random p_73479_, float p_73480_, int p_73481_, int p_73482_, int p_73483_, int p_73484_, int p_73485_, int p_73486_, BlockState p_73487_, BlockState p_73488_, boolean p_73489_, boolean p_73490_) {
      for(int i = p_73482_; i <= p_73485_; ++i) {
         for(int j = p_73481_; j <= p_73484_; ++j) {
            for(int k = p_73483_; k <= p_73486_; ++k) {
               if (!(p_73479_.nextFloat() > p_73480_) && (!p_73489_ || !this.m_73398_(p_73477_, j, i, k, p_73478_).m_60795_()) && (!p_73490_ || this.m_73414_(p_73477_, j, i, k, p_73478_))) {
                  if (i != p_73482_ && i != p_73485_ && j != p_73481_ && j != p_73484_ && k != p_73483_ && k != p_73486_) {
                     this.m_73434_(p_73477_, p_73488_, j, i, k, p_73478_);
                  } else {
                     this.m_73434_(p_73477_, p_73487_, j, i, k, p_73478_);
                  }
               }
            }
         }
      }

   }

   protected void m_73491_(WorldGenLevel p_73492_, BoundingBox p_73493_, Random p_73494_, float p_73495_, int p_73496_, int p_73497_, int p_73498_, BlockState p_73499_) {
      if (p_73494_.nextFloat() < p_73495_) {
         this.m_73434_(p_73492_, p_73499_, p_73496_, p_73497_, p_73498_, p_73493_);
      }

   }

   protected void m_73453_(WorldGenLevel p_73454_, BoundingBox p_73455_, int p_73456_, int p_73457_, int p_73458_, int p_73459_, int p_73460_, int p_73461_, BlockState p_73462_, boolean p_73463_) {
      float f = (float)(p_73459_ - p_73456_ + 1);
      float f1 = (float)(p_73460_ - p_73457_ + 1);
      float f2 = (float)(p_73461_ - p_73458_ + 1);
      float f3 = (float)p_73456_ + f / 2.0F;
      float f4 = (float)p_73458_ + f2 / 2.0F;

      for(int i = p_73457_; i <= p_73460_; ++i) {
         float f5 = (float)(i - p_73457_) / f1;

         for(int j = p_73456_; j <= p_73459_; ++j) {
            float f6 = ((float)j - f3) / (f * 0.5F);

            for(int k = p_73458_; k <= p_73461_; ++k) {
               float f7 = ((float)k - f4) / (f2 * 0.5F);
               if (!p_73463_ || !this.m_73398_(p_73454_, j, i, k, p_73455_).m_60795_()) {
                  float f8 = f6 * f6 + f5 * f5 + f7 * f7;
                  if (f8 <= 1.05F) {
                     this.m_73434_(p_73454_, p_73462_, j, i, k, p_73455_);
                  }
               }
            }
         }
      }

   }

   protected void m_73528_(WorldGenLevel p_73529_, BlockState p_73530_, int p_73531_, int p_73532_, int p_73533_, BoundingBox p_73534_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = this.m_163582_(p_73531_, p_73532_, p_73533_);
      if (p_73534_.m_71051_(blockpos$mutableblockpos)) {
         while(this.m_163572_(p_73529_.m_8055_(blockpos$mutableblockpos)) && blockpos$mutableblockpos.m_123342_() > p_73529_.m_141937_() + 1) {
            p_73529_.m_7731_(blockpos$mutableblockpos, p_73530_, 2);
            blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         }

      }
   }

   protected boolean m_163572_(BlockState p_163573_) {
      return p_163573_.m_60795_() || p_163573_.m_60767_().m_76332_() || p_163573_.m_60713_(Blocks.f_152475_) || p_163573_.m_60713_(Blocks.f_50037_) || p_163573_.m_60713_(Blocks.f_50038_);
   }

   protected boolean m_5606_(WorldGenLevel p_73509_, BoundingBox p_73510_, Random p_73511_, int p_73512_, int p_73513_, int p_73514_, ResourceLocation p_73515_) {
      return this.m_73420_(p_73509_, p_73510_, p_73511_, this.m_163582_(p_73512_, p_73513_, p_73514_), p_73515_, (BlockState)null);
   }

   public static BlockState m_73407_(BlockGetter p_73408_, BlockPos p_73409_, BlockState p_73410_) {
      Direction direction = null;

      for(Direction direction1 : Direction.Plane.HORIZONTAL) {
         BlockPos blockpos = p_73409_.m_142300_(direction1);
         BlockState blockstate = p_73408_.m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50087_)) {
            return p_73410_;
         }

         if (blockstate.m_60804_(p_73408_, blockpos)) {
            if (direction != null) {
               direction = null;
               break;
            }

            direction = direction1;
         }
      }

      if (direction != null) {
         return p_73410_.m_61124_(HorizontalDirectionalBlock.f_54117_, direction.m_122424_());
      } else {
         Direction direction2 = p_73410_.m_61143_(HorizontalDirectionalBlock.f_54117_);
         BlockPos blockpos1 = p_73409_.m_142300_(direction2);
         if (p_73408_.m_8055_(blockpos1).m_60804_(p_73408_, blockpos1)) {
            direction2 = direction2.m_122424_();
            blockpos1 = p_73409_.m_142300_(direction2);
         }

         if (p_73408_.m_8055_(blockpos1).m_60804_(p_73408_, blockpos1)) {
            direction2 = direction2.m_122427_();
            blockpos1 = p_73409_.m_142300_(direction2);
         }

         if (p_73408_.m_8055_(blockpos1).m_60804_(p_73408_, blockpos1)) {
            direction2 = direction2.m_122424_();
            p_73409_.m_142300_(direction2);
         }

         return p_73410_.m_61124_(HorizontalDirectionalBlock.f_54117_, direction2);
      }
   }

   protected boolean m_73420_(ServerLevelAccessor p_73421_, BoundingBox p_73422_, Random p_73423_, BlockPos p_73424_, ResourceLocation p_73425_, @Nullable BlockState p_73426_) {
      if (p_73422_.m_71051_(p_73424_) && !p_73421_.m_8055_(p_73424_).m_60713_(Blocks.f_50087_)) {
         if (p_73426_ == null) {
            p_73426_ = m_73407_(p_73421_, p_73424_, Blocks.f_50087_.m_49966_());
         }

         p_73421_.m_7731_(p_73424_, p_73426_, 2);
         BlockEntity blockentity = p_73421_.m_7702_(p_73424_);
         if (blockentity instanceof ChestBlockEntity) {
            ((ChestBlockEntity)blockentity).m_59626_(p_73425_, p_73423_.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean m_73500_(WorldGenLevel p_73501_, BoundingBox p_73502_, Random p_73503_, int p_73504_, int p_73505_, int p_73506_, Direction p_73507_, ResourceLocation p_73508_) {
      BlockPos blockpos = this.m_163582_(p_73504_, p_73505_, p_73506_);
      if (p_73502_.m_71051_(blockpos) && !p_73501_.m_8055_(blockpos).m_60713_(Blocks.f_50061_)) {
         this.m_73434_(p_73501_, Blocks.f_50061_.m_49966_().m_61124_(DispenserBlock.f_52659_, p_73507_), p_73504_, p_73505_, p_73506_, p_73502_);
         BlockEntity blockentity = p_73501_.m_7702_(blockpos);
         if (blockentity instanceof DispenserBlockEntity) {
            ((DispenserBlockEntity)blockentity).m_59626_(p_73508_, p_73503_.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   public void m_6324_(int p_73395_, int p_73396_, int p_73397_) {
      this.f_73383_.m_162367_(p_73395_, p_73396_, p_73397_);
   }

   @Nullable
   public Direction m_73549_() {
      return this.f_73377_;
   }

   public void m_73519_(@Nullable Direction p_73520_) {
      this.f_73377_ = p_73520_;
      if (p_73520_ == null) {
         this.f_73379_ = Rotation.NONE;
         this.f_73378_ = Mirror.NONE;
      } else {
         switch(p_73520_) {
         case SOUTH:
            this.f_73378_ = Mirror.LEFT_RIGHT;
            this.f_73379_ = Rotation.NONE;
            break;
         case WEST:
            this.f_73378_ = Mirror.LEFT_RIGHT;
            this.f_73379_ = Rotation.CLOCKWISE_90;
            break;
         case EAST:
            this.f_73378_ = Mirror.NONE;
            this.f_73379_ = Rotation.CLOCKWISE_90;
            break;
         default:
            this.f_73378_ = Mirror.NONE;
            this.f_73379_ = Rotation.NONE;
         }
      }

   }

   public Rotation m_6830_() {
      return this.f_73379_;
   }

   public Mirror m_163587_() {
      return this.f_73378_;
   }

   public StructurePieceType m_73550_() {
      return this.f_73380_;
   }

   protected abstract static class BlockSelector {
      protected BlockState f_73553_ = Blocks.f_50016_.m_49966_();

      public abstract void m_7889_(Random p_73556_, int p_73557_, int p_73558_, int p_73559_, boolean p_73560_);

      public BlockState m_73555_() {
         return this.f_73553_;
      }
   }
}
