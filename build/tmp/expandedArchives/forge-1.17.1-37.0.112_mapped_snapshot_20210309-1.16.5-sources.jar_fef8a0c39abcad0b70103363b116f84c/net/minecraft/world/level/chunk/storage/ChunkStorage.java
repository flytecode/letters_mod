package net.minecraft.world.level.chunk.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.LegacyStructureDataHandler;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ChunkStorage implements AutoCloseable {
   private final IOWorker f_63495_;
   protected final DataFixer f_63496_;
   @Nullable
   private LegacyStructureDataHandler f_63497_;

   public ChunkStorage(File p_63499_, DataFixer p_63500_, boolean p_63501_) {
      this.f_63496_ = p_63500_;
      this.f_63495_ = new IOWorker(p_63499_, p_63501_, "chunk");
   }

   public CompoundTag m_63507_(ResourceKey<Level> p_63508_, Supplier<DimensionDataStorage> p_63509_, CompoundTag p_63510_) {
      int i = m_63505_(p_63510_);
      int j = 1493;
      if (i < 1493) {
         p_63510_ = NbtUtils.m_129218_(this.f_63496_, DataFixTypes.CHUNK, p_63510_, i, 1493);
         if (p_63510_.m_128469_("Level").m_128471_("hasLegacyStructureData")) {
            if (this.f_63497_ == null) {
               this.f_63497_ = LegacyStructureDataHandler.m_71331_(p_63508_, p_63509_.get());
            }

            p_63510_ = this.f_63497_.m_71326_(p_63510_);
         }
      }

      p_63510_ = NbtUtils.m_129213_(this.f_63496_, DataFixTypes.CHUNK, p_63510_, Math.max(1493, i));
      if (i < SharedConstants.m_136187_().getWorldVersion()) {
         p_63510_.m_128405_("DataVersion", SharedConstants.m_136187_().getWorldVersion());
      }

      return p_63510_;
   }

   public static int m_63505_(CompoundTag p_63506_) {
      return p_63506_.m_128425_("DataVersion", 99) ? p_63506_.m_128451_("DataVersion") : -1;
   }

   @Nullable
   public CompoundTag m_63512_(ChunkPos p_63513_) throws IOException {
      return this.f_63495_.m_63533_(p_63513_);
   }

   public void m_63502_(ChunkPos p_63503_, CompoundTag p_63504_) {
      this.f_63495_.m_63538_(p_63503_, p_63504_);
      if (this.f_63497_ != null) {
         this.f_63497_.m_71318_(p_63503_.m_45588_());
      }

   }

   public void m_63514_() {
      this.f_63495_.m_182498_(true).join();
   }

   public void close() throws IOException {
      this.f_63495_.close();
   }
}