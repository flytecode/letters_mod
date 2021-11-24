package net.minecraft.world.level.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerDataStorage {
   private static final Logger f_78426_ = LogManager.getLogger();
   private final File f_78427_;
   protected final DataFixer f_78425_;

   public PlayerDataStorage(LevelStorageSource.LevelStorageAccess p_78430_, DataFixer p_78431_) {
      this.f_78425_ = p_78431_;
      this.f_78427_ = p_78430_.m_78283_(LevelResource.f_78176_).toFile();
      this.f_78427_.mkdirs();
   }

   public void m_78433_(Player p_78434_) {
      try {
         CompoundTag compoundtag = p_78434_.m_20240_(new CompoundTag());
         File file1 = File.createTempFile(p_78434_.m_20149_() + "-", ".dat", this.f_78427_);
         NbtIo.m_128944_(compoundtag, file1);
         File file2 = new File(this.f_78427_, p_78434_.m_20149_() + ".dat");
         File file3 = new File(this.f_78427_, p_78434_.m_20149_() + ".dat_old");
         Util.m_137462_(file2, file1, file3);
         net.minecraftforge.event.ForgeEventFactory.firePlayerSavingEvent(p_78434_, f_78427_, p_78434_.m_20149_());
      } catch (Exception exception) {
         f_78426_.warn("Failed to save player data for {}", (Object)p_78434_.m_7755_().getString());
      }

   }

   @Nullable
   public CompoundTag m_78435_(Player p_78436_) {
      CompoundTag compoundtag = null;

      try {
         File file1 = new File(this.f_78427_, p_78436_.m_20149_() + ".dat");
         if (file1.exists() && file1.isFile()) {
            compoundtag = NbtIo.m_128937_(file1);
         }
      } catch (Exception exception) {
         f_78426_.warn("Failed to load player data for {}", (Object)p_78436_.m_7755_().getString());
      }

      if (compoundtag != null) {
         int i = compoundtag.m_128425_("DataVersion", 3) ? compoundtag.m_128451_("DataVersion") : -1;
         p_78436_.m_20258_(NbtUtils.m_129213_(this.f_78425_, DataFixTypes.PLAYER, compoundtag, i));
      }
      net.minecraftforge.event.ForgeEventFactory.firePlayerLoadingEvent(p_78436_, f_78427_, p_78436_.m_20149_());

      return compoundtag;
   }

   public String[] m_78432_() {
      String[] astring = this.f_78427_.list();
      if (astring == null) {
         astring = new String[0];
      }

      for(int i = 0; i < astring.length; ++i) {
         if (astring[i].endsWith(".dat")) {
            astring[i] = astring[i].substring(0, astring[i].length() - 4);
         }
      }

      return astring;
   }

   public File getPlayerDataFolder() {
      return f_78427_;
   }
}
