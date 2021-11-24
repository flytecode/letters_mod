package net.minecraft.data.structures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureUpdater implements SnbtToNbt.Filter {
   private static final Logger f_126499_ = LogManager.getLogger();

   public CompoundTag m_6392_(String p_126503_, CompoundTag p_126504_) {
      return p_126503_.startsWith("data/minecraft/structures/") ? m_176822_(p_126503_, p_126504_) : p_126504_;
   }

   public static CompoundTag m_176822_(String p_176823_, CompoundTag p_176824_) {
      return m_126507_(p_176823_, m_126505_(p_176824_));
   }

   private static CompoundTag m_126505_(CompoundTag p_126506_) {
      if (!p_126506_.m_128425_("DataVersion", 99)) {
         p_126506_.m_128405_("DataVersion", 500);
      }

      return p_126506_;
   }

   private static CompoundTag m_126507_(String p_126508_, CompoundTag p_126509_) {
      StructureTemplate structuretemplate = new StructureTemplate();
      int i = p_126509_.m_128451_("DataVersion");
      int j = 2678;
      if (i < 2678) {
         f_126499_.warn("SNBT Too old, do not forget to update: {} < {}: {}", i, 2678, p_126508_);
      }

      CompoundTag compoundtag = NbtUtils.m_129213_(DataFixers.m_14512_(), DataFixTypes.STRUCTURE, p_126509_, i);
      structuretemplate.m_74638_(compoundtag);
      return structuretemplate.m_74618_(new CompoundTag());
   }
}