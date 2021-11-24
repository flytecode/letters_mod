package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BlockEntity extends net.minecraftforge.common.capabilities.CapabilityProvider<BlockEntity> implements net.minecraftforge.common.extensions.IForgeBlockEntity {
   private static final Logger f_58854_ = LogManager.getLogger();
   private final BlockEntityType<?> f_58855_;
   @Nullable
   protected Level f_58857_;
   protected final BlockPos f_58858_;
   protected boolean f_58859_;
   private BlockState f_58856_;
   private CompoundTag customTileData;

   public BlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
      super(BlockEntity.class);
      this.f_58855_ = p_155228_;
      this.f_58858_ = p_155229_.m_7949_();
      this.f_58856_ = p_155230_;
      this.gatherCapabilities();
   }

   @Nullable
   public Level m_58904_() {
      return this.f_58857_;
   }

   public void m_142339_(Level p_155231_) {
      this.f_58857_ = p_155231_;
   }

   public boolean m_58898_() {
      return this.f_58857_ != null;
   }

   public void m_142466_(CompoundTag p_155245_) {
      if (p_155245_.m_128441_("ForgeData")) this.customTileData = p_155245_.m_128469_("ForgeData");
      if (getCapabilities() != null && p_155245_.m_128441_("ForgeCaps")) deserializeCaps(p_155245_.m_128469_("ForgeCaps"));
   }

   public CompoundTag m_6945_(CompoundTag p_58888_) {
      return this.m_58894_(p_58888_);
   }

   private CompoundTag m_58894_(CompoundTag p_58895_) {
      ResourceLocation resourcelocation = BlockEntityType.m_58954_(this.m_58903_());
      if (resourcelocation == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         p_58895_.m_128359_("id", resourcelocation.toString());
         p_58895_.m_128405_("x", this.f_58858_.m_123341_());
         p_58895_.m_128405_("y", this.f_58858_.m_123342_());
         p_58895_.m_128405_("z", this.f_58858_.m_123343_());
         if (this.customTileData != null) p_58895_.m_128365_("ForgeData", this.customTileData.m_6426_());
         if (getCapabilities() != null) p_58895_.m_128365_("ForgeCaps", serializeCaps());
         return p_58895_;
      }
   }

   @Nullable
   public static BlockEntity m_155241_(BlockPos p_155242_, BlockState p_155243_, CompoundTag p_155244_) {
      String s = p_155244_.m_128461_("id");
      ResourceLocation resourcelocation = ResourceLocation.m_135820_(s);
      if (resourcelocation == null) {
         f_58854_.error("Block entity has invalid type: {}", (Object)s);
         return null;
      } else {
         return Registry.f_122830_.m_6612_(resourcelocation).map((p_155240_) -> {
            try {
               return p_155240_.m_155264_(p_155242_, p_155243_);
            } catch (Throwable throwable) {
               f_58854_.error("Failed to create block entity {}", s, throwable);
               return null;
            }
         }).map((p_155249_) -> {
            try {
               p_155249_.m_142466_(p_155244_);
               return p_155249_;
            } catch (Throwable throwable) {
               f_58854_.error("Failed to load data for block entity {}", s, throwable);
               return null;
            }
         }).orElseGet(() -> {
            f_58854_.warn("Skipping BlockEntity with id {}", (Object)s);
            return null;
         });
      }
   }

   public void m_6596_() {
      if (this.f_58857_ != null) {
         m_155232_(this.f_58857_, this.f_58858_, this.f_58856_);
      }

   }

   protected static void m_155232_(Level p_155233_, BlockPos p_155234_, BlockState p_155235_) {
      p_155233_.m_151543_(p_155234_);
      if (!p_155235_.m_60795_()) {
         p_155233_.m_46717_(p_155234_, p_155235_.m_60734_());
      }

   }

   public BlockPos m_58899_() {
      return this.f_58858_;
   }

   public BlockState m_58900_() {
      return this.f_58856_;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_7033_() {
      return null;
   }

   public CompoundTag m_5995_() {
      return this.m_58894_(new CompoundTag());
   }

   public boolean m_58901_() {
      return this.f_58859_;
   }

   public void m_7651_() {
      this.f_58859_ = true;
      this.invalidateCaps();
      requestModelDataUpdate();
   }

   @Override
   public void onChunkUnloaded() {
      this.invalidateCaps();
   }

   public void m_6339_() {
      this.f_58859_ = false;
   }

   public boolean m_7531_(int p_58889_, int p_58890_) {
      return false;
   }

   public void m_58886_(CrashReportCategory p_58887_) {
      p_58887_.m_128165_("Name", () -> {
         return Registry.f_122830_.m_7981_(this.m_58903_()) + " // " + this.getClass().getCanonicalName();
      });
      if (this.f_58857_ != null) {
         CrashReportCategory.m_178950_(p_58887_, this.f_58857_, this.f_58858_, this.m_58900_());
         CrashReportCategory.m_178950_(p_58887_, this.f_58857_, this.f_58858_, this.f_58857_.m_8055_(this.f_58858_));
      }
   }

   public boolean m_6326_() {
      return false;
   }

   public BlockEntityType<?> m_58903_() {
      return this.f_58855_;
   }

   @Override
   public CompoundTag getTileData() {
      if (this.customTileData == null)
         this.customTileData = new CompoundTag();
      return this.customTileData;
   }

   @Deprecated
   public void m_155250_(BlockState p_155251_) {
      this.f_58856_ = p_155251_;
   }
}
