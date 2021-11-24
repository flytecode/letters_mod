package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.Features;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheEndGatewayBlockEntity extends TheEndPortalBlockEntity {
   private static final Logger f_59925_ = LogManager.getLogger();
   private static final int f_155807_ = 200;
   private static final int f_155808_ = 40;
   private static final int f_155809_ = 2400;
   private static final int f_155810_ = 1;
   private static final int f_155811_ = 10;
   private long f_59926_;
   private int f_59927_;
   @Nullable
   private BlockPos f_59928_;
   private boolean f_59929_;

   public TheEndGatewayBlockEntity(BlockPos p_155813_, BlockState p_155814_) {
      super(BlockEntityType.f_58937_, p_155813_, p_155814_);
   }

   public CompoundTag m_6945_(CompoundTag p_59961_) {
      super.m_6945_(p_59961_);
      p_59961_.m_128356_("Age", this.f_59926_);
      if (this.f_59928_ != null) {
         p_59961_.m_128365_("ExitPortal", NbtUtils.m_129224_(this.f_59928_));
      }

      if (this.f_59929_) {
         p_59961_.m_128379_("ExactTeleport", this.f_59929_);
      }

      return p_59961_;
   }

   public void m_142466_(CompoundTag p_155840_) {
      super.m_142466_(p_155840_);
      this.f_59926_ = p_155840_.m_128454_("Age");
      if (p_155840_.m_128425_("ExitPortal", 10)) {
         BlockPos blockpos = NbtUtils.m_129239_(p_155840_.m_128469_("ExitPortal"));
         if (Level.m_46741_(blockpos)) {
            this.f_59928_ = blockpos;
         }
      }

      this.f_59929_ = p_155840_.m_128471_("ExactTeleport");
   }

   public static void m_155834_(Level p_155835_, BlockPos p_155836_, BlockState p_155837_, TheEndGatewayBlockEntity p_155838_) {
      ++p_155838_.f_59926_;
      if (p_155838_.m_59972_()) {
         --p_155838_.f_59927_;
      }

   }

   public static void m_155844_(Level p_155845_, BlockPos p_155846_, BlockState p_155847_, TheEndGatewayBlockEntity p_155848_) {
      boolean flag = p_155848_.m_59971_();
      boolean flag1 = p_155848_.m_59972_();
      ++p_155848_.f_59926_;
      if (flag1) {
         --p_155848_.f_59927_;
      } else {
         List<Entity> list = p_155845_.m_6443_(Entity.class, new AABB(p_155846_), TheEndGatewayBlockEntity::m_59940_);
         if (!list.isEmpty()) {
            m_155828_(p_155845_, p_155846_, p_155847_, list.get(p_155845_.f_46441_.nextInt(list.size())), p_155848_);
         }

         if (p_155848_.f_59926_ % 2400L == 0L) {
            m_155849_(p_155845_, p_155846_, p_155847_, p_155848_);
         }
      }

      if (flag != p_155848_.m_59971_() || flag1 != p_155848_.m_59972_()) {
         m_155232_(p_155845_, p_155846_, p_155847_);
      }

   }

   public static boolean m_59940_(Entity p_59941_) {
      return EntitySelector.f_20408_.test(p_59941_) && !p_59941_.m_20201_().m_20092_();
   }

   public boolean m_59971_() {
      return this.f_59926_ < 200L;
   }

   public boolean m_59972_() {
      return this.f_59927_ > 0;
   }

   public float m_59933_(float p_59934_) {
      return Mth.m_14036_(((float)this.f_59926_ + p_59934_) / 200.0F, 0.0F, 1.0F);
   }

   public float m_59967_(float p_59968_) {
      return 1.0F - Mth.m_14036_(((float)this.f_59927_ - p_59968_) / 40.0F, 0.0F, 1.0F);
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return new ClientboundBlockEntityDataPacket(this.f_58858_, 8, this.m_5995_());
   }

   public CompoundTag m_5995_() {
      return this.m_6945_(new CompoundTag());
   }

   private static void m_155849_(Level p_155850_, BlockPos p_155851_, BlockState p_155852_, TheEndGatewayBlockEntity p_155853_) {
      if (!p_155850_.f_46443_) {
         p_155853_.f_59927_ = 40;
         p_155850_.m_7696_(p_155851_, p_155852_.m_60734_(), 1, 0);
         m_155232_(p_155850_, p_155851_, p_155852_);
      }

   }

   public boolean m_7531_(int p_59963_, int p_59964_) {
      if (p_59963_ == 1) {
         this.f_59927_ = 40;
         return true;
      } else {
         return super.m_7531_(p_59963_, p_59964_);
      }
   }

   public static void m_155828_(Level p_155829_, BlockPos p_155830_, BlockState p_155831_, Entity p_155832_, TheEndGatewayBlockEntity p_155833_) {
      if (p_155829_ instanceof ServerLevel && !p_155833_.m_59972_()) {
         ServerLevel serverlevel = (ServerLevel)p_155829_;
         p_155833_.f_59927_ = 100;
         if (p_155833_.f_59928_ == null && p_155829_.m_46472_() == Level.f_46430_) {
            BlockPos blockpos = m_155818_(serverlevel, p_155830_);
            blockpos = blockpos.m_6630_(10);
            f_59925_.debug("Creating portal at {}", (Object)blockpos);
            m_155821_(serverlevel, blockpos, EndGatewayConfiguration.m_67650_(p_155830_, false));
            p_155833_.f_59928_ = blockpos;
         }

         if (p_155833_.f_59928_ != null) {
            BlockPos blockpos1 = p_155833_.f_59929_ ? p_155833_.f_59928_ : m_155825_(p_155829_, p_155833_.f_59928_);
            Entity entity;
            if (p_155832_ instanceof ThrownEnderpearl) {
               Entity entity1 = ((ThrownEnderpearl)p_155832_).m_37282_();
               if (entity1 instanceof ServerPlayer) {
                  CriteriaTriggers.f_10570_.m_31269_((ServerPlayer)entity1, p_155831_);
               }

               if (entity1 != null) {
                  entity = entity1;
                  p_155832_.m_146870_();
               } else {
                  entity = p_155832_;
               }
            } else {
               entity = p_155832_.m_20201_();
            }

            entity.m_20091_();
            entity.m_20324_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_() + 0.5D);
         }

         m_155849_(p_155829_, p_155830_, p_155831_, p_155833_);
      }
   }

   private static BlockPos m_155825_(Level p_155826_, BlockPos p_155827_) {
      BlockPos blockpos = m_59942_(p_155826_, p_155827_.m_142082_(0, 2, 0), 5, false);
      f_59925_.debug("Best exit position for portal at {} is {}", p_155827_, blockpos);
      return blockpos.m_7494_();
   }

   private static BlockPos m_155818_(ServerLevel p_155819_, BlockPos p_155820_) {
      Vec3 vec3 = m_155841_(p_155819_, p_155820_);
      LevelChunk levelchunk = m_59947_(p_155819_, vec3);
      BlockPos blockpos = m_59953_(levelchunk);
      if (blockpos == null) {
         blockpos = new BlockPos(vec3.f_82479_ + 0.5D, 75.0D, vec3.f_82481_ + 0.5D);
         f_59925_.debug("Failed to find a suitable block to teleport to, spawning an island on {}", (Object)blockpos);
         Features.f_127031_.m_65385_(p_155819_, p_155819_.m_7726_().m_8481_(), new Random(blockpos.m_121878_()), blockpos);
      } else {
         f_59925_.debug("Found suitable block to teleport to: {}", (Object)blockpos);
      }

      return m_59942_(p_155819_, blockpos, 16, true);
   }

   private static Vec3 m_155841_(ServerLevel p_155842_, BlockPos p_155843_) {
      Vec3 vec3 = (new Vec3((double)p_155843_.m_123341_(), 0.0D, (double)p_155843_.m_123343_())).m_82541_();
      int i = 1024;
      Vec3 vec31 = vec3.m_82490_(1024.0D);

      for(int j = 16; !m_155815_(p_155842_, vec31) && j-- > 0; vec31 = vec31.m_82549_(vec3.m_82490_(-16.0D))) {
         f_59925_.debug("Skipping backwards past nonempty chunk at {}", (Object)vec31);
      }

      for(int k = 16; m_155815_(p_155842_, vec31) && k-- > 0; vec31 = vec31.m_82549_(vec3.m_82490_(16.0D))) {
         f_59925_.debug("Skipping forward past empty chunk at {}", (Object)vec31);
      }

      f_59925_.debug("Found chunk at {}", (Object)vec31);
      return vec31;
   }

   private static boolean m_155815_(ServerLevel p_155816_, Vec3 p_155817_) {
      return m_59947_(p_155816_, p_155817_).m_62098_() <= p_155816_.m_141937_();
   }

   private static BlockPos m_59942_(BlockGetter p_59943_, BlockPos p_59944_, int p_59945_, boolean p_59946_) {
      BlockPos blockpos = null;

      for(int i = -p_59945_; i <= p_59945_; ++i) {
         for(int j = -p_59945_; j <= p_59945_; ++j) {
            if (i != 0 || j != 0 || p_59946_) {
               for(int k = p_59943_.m_151558_() - 1; k > (blockpos == null ? p_59943_.m_141937_() : blockpos.m_123342_()); --k) {
                  BlockPos blockpos1 = new BlockPos(p_59944_.m_123341_() + i, k, p_59944_.m_123343_() + j);
                  BlockState blockstate = p_59943_.m_8055_(blockpos1);
                  if (blockstate.m_60838_(p_59943_, blockpos1) && (p_59946_ || !blockstate.m_60713_(Blocks.f_50752_))) {
                     blockpos = blockpos1;
                     break;
                  }
               }
            }
         }
      }

      return blockpos == null ? p_59944_ : blockpos;
   }

   private static LevelChunk m_59947_(Level p_59948_, Vec3 p_59949_) {
      return p_59948_.m_6325_(Mth.m_14107_(p_59949_.f_82479_ / 16.0D), Mth.m_14107_(p_59949_.f_82481_ / 16.0D));
   }

   @Nullable
   private static BlockPos m_59953_(LevelChunk p_59954_) {
      ChunkPos chunkpos = p_59954_.m_7697_();
      BlockPos blockpos = new BlockPos(chunkpos.m_45604_(), 30, chunkpos.m_45605_());
      int i = p_59954_.m_62098_() + 16 - 1;
      BlockPos blockpos1 = new BlockPos(chunkpos.m_45608_(), i, chunkpos.m_45609_());
      BlockPos blockpos2 = null;
      double d0 = 0.0D;

      for(BlockPos blockpos3 : BlockPos.m_121940_(blockpos, blockpos1)) {
         BlockState blockstate = p_59954_.m_8055_(blockpos3);
         BlockPos blockpos4 = blockpos3.m_7494_();
         BlockPos blockpos5 = blockpos3.m_6630_(2);
         if (blockstate.m_60713_(Blocks.f_50259_) && !p_59954_.m_8055_(blockpos4).m_60838_(p_59954_, blockpos4) && !p_59954_.m_8055_(blockpos5).m_60838_(p_59954_, blockpos5)) {
            double d1 = blockpos3.m_123299_(0.0D, 0.0D, 0.0D, true);
            if (blockpos2 == null || d1 < d0) {
               blockpos2 = blockpos3;
               d0 = d1;
            }
         }
      }

      return blockpos2;
   }

   private static void m_155821_(ServerLevel p_155822_, BlockPos p_155823_, EndGatewayConfiguration p_155824_) {
      Feature.f_65734_.m_65815_(p_155824_).m_65385_(p_155822_, p_155822_.m_7726_().m_8481_(), new Random(), p_155823_);
   }

   public boolean m_6665_(Direction p_59959_) {
      return Block.m_152444_(this.m_58900_(), this.f_58857_, this.m_58899_(), p_59959_, this.m_58899_().m_142300_(p_59959_));
   }

   public int m_59975_() {
      int i = 0;

      for(Direction direction : Direction.values()) {
         i += this.m_6665_(direction) ? 1 : 0;
      }

      return i;
   }

   public void m_59955_(BlockPos p_59956_, boolean p_59957_) {
      this.f_59929_ = p_59957_;
      this.f_59928_ = p_59956_;
   }
}