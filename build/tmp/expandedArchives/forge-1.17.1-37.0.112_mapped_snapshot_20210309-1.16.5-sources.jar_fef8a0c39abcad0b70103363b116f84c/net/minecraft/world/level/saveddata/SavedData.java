package net.minecraft.world.level.saveddata;

import java.io.File;
import java.io.IOException;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SavedData {
   private static final Logger f_77751_ = LogManager.getLogger();
   private boolean f_77753_;

   public abstract CompoundTag m_7176_(CompoundTag p_77763_);

   public void m_77762_() {
      this.m_77760_(true);
   }

   public void m_77760_(boolean p_77761_) {
      this.f_77753_ = p_77761_;
   }

   public boolean m_77764_() {
      return this.f_77753_;
   }

   public void m_77757_(File p_77758_) {
      if (this.m_77764_()) {
         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128365_("data", this.m_7176_(new CompoundTag()));
         compoundtag.m_128405_("DataVersion", SharedConstants.m_136187_().getWorldVersion());

         try {
            NbtIo.m_128944_(compoundtag, p_77758_);
         } catch (IOException ioexception) {
            f_77751_.error("Could not save data {}", this, ioexception);
         }

         this.m_77760_(false);
      }
   }
}