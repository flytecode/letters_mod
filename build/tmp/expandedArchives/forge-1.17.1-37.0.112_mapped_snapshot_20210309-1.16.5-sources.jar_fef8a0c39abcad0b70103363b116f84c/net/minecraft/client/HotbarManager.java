package net.minecraft.client;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import net.minecraft.SharedConstants;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class HotbarManager {
   private static final Logger f_90796_ = LogManager.getLogger();
   public static final int f_167804_ = 9;
   private final File f_90797_;
   private final DataFixer f_90798_;
   private final Hotbar[] f_90799_ = new Hotbar[9];
   private boolean f_90800_;

   public HotbarManager(File p_90803_, DataFixer p_90804_) {
      this.f_90797_ = new File(p_90803_, "hotbar.nbt");
      this.f_90798_ = p_90804_;

      for(int i = 0; i < 9; ++i) {
         this.f_90799_[i] = new Hotbar();
      }

   }

   private void m_90808_() {
      try {
         CompoundTag compoundtag = NbtIo.m_128953_(this.f_90797_);
         if (compoundtag == null) {
            return;
         }

         if (!compoundtag.m_128425_("DataVersion", 99)) {
            compoundtag.m_128405_("DataVersion", 1343);
         }

         compoundtag = NbtUtils.m_129213_(this.f_90798_, DataFixTypes.HOTBAR, compoundtag, compoundtag.m_128451_("DataVersion"));

         for(int i = 0; i < 9; ++i) {
            this.f_90799_[i].m_108783_(compoundtag.m_128437_(String.valueOf(i), 10));
         }
      } catch (Exception exception) {
         f_90796_.error("Failed to load creative mode options", (Throwable)exception);
      }

   }

   public void m_90805_() {
      try {
         CompoundTag compoundtag = new CompoundTag();
         compoundtag.m_128405_("DataVersion", SharedConstants.m_136187_().getWorldVersion());

         for(int i = 0; i < 9; ++i) {
            compoundtag.m_128365_(String.valueOf(i), this.m_90806_(i).m_108782_());
         }

         NbtIo.m_128955_(compoundtag, this.f_90797_);
      } catch (Exception exception) {
         f_90796_.error("Failed to save creative mode options", (Throwable)exception);
      }

   }

   public Hotbar m_90806_(int p_90807_) {
      if (!this.f_90800_) {
         this.m_90808_();
         this.f_90800_ = true;
      }

      return this.f_90799_[p_90807_];
   }
}