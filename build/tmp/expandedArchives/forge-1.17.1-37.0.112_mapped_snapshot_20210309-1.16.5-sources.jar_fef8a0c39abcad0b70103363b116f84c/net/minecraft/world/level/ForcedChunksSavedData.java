package net.minecraft.world.level;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class ForcedChunksSavedData extends SavedData {
   public static final String f_151479_ = "chunks";
   private static final String f_151480_ = "Forced";
   private final LongSet f_46114_;

   private ForcedChunksSavedData(LongSet p_151482_) {
      this.f_46114_ = p_151482_;
   }

   public ForcedChunksSavedData() {
      this(new LongOpenHashSet());
   }

   public static ForcedChunksSavedData m_151483_(CompoundTag p_151484_) {
      ForcedChunksSavedData savedData = new ForcedChunksSavedData(new LongOpenHashSet(p_151484_.m_128467_("Forced")));
      net.minecraftforge.common.world.ForgeChunkManager.readForgeForcedChunks(p_151484_, savedData.blockForcedChunks, savedData.entityForcedChunks);
      return savedData;
   }

   public CompoundTag m_7176_(CompoundTag p_46120_) {
      p_46120_.m_128388_("Forced", this.f_46114_.toLongArray());
      net.minecraftforge.common.world.ForgeChunkManager.writeForgeForcedChunks(p_46120_, this.blockForcedChunks, this.entityForcedChunks);
      return p_46120_;
   }

   public LongSet m_46116_() {
      return this.f_46114_;
   }

   /* ======================================== FORGE START =====================================*/
   // TODO: not sure if these are being written correctly. load used to refer to these directly.
   private net.minecraftforge.common.world.ForgeChunkManager.TicketTracker<net.minecraft.core.BlockPos> blockForcedChunks = new net.minecraftforge.common.world.ForgeChunkManager.TicketTracker<>();
   private net.minecraftforge.common.world.ForgeChunkManager.TicketTracker<java.util.UUID> entityForcedChunks = new net.minecraftforge.common.world.ForgeChunkManager.TicketTracker<>();

   public net.minecraftforge.common.world.ForgeChunkManager.TicketTracker<net.minecraft.core.BlockPos> getBlockForcedChunks() {
      return this.blockForcedChunks;
   }

   public net.minecraftforge.common.world.ForgeChunkManager.TicketTracker<java.util.UUID> getEntityForcedChunks() {
      return this.entityForcedChunks;
   }
}
