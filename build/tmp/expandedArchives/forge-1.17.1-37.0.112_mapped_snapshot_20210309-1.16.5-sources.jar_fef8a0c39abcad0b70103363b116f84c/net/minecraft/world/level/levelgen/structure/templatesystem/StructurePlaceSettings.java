package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class StructurePlaceSettings {
   private Mirror f_74361_ = Mirror.NONE;
   private Rotation f_74362_ = Rotation.NONE;
   private BlockPos f_74363_ = BlockPos.f_121853_;
   private boolean f_74364_;
   @Nullable
   private BoundingBox f_74366_;
   private boolean f_74367_ = true;
   @Nullable
   private Random f_74368_;
   @Nullable
   private int f_74369_;
   private final List<StructureProcessor> f_74370_ = Lists.newArrayList();
   private boolean f_74371_;
   private boolean f_74372_;

   public StructurePlaceSettings m_74374_() {
      StructurePlaceSettings structureplacesettings = new StructurePlaceSettings();
      structureplacesettings.f_74361_ = this.f_74361_;
      structureplacesettings.f_74362_ = this.f_74362_;
      structureplacesettings.f_74363_ = this.f_74363_;
      structureplacesettings.f_74364_ = this.f_74364_;
      structureplacesettings.f_74366_ = this.f_74366_;
      structureplacesettings.f_74367_ = this.f_74367_;
      structureplacesettings.f_74368_ = this.f_74368_;
      structureplacesettings.f_74369_ = this.f_74369_;
      structureplacesettings.f_74370_.addAll(this.f_74370_);
      structureplacesettings.f_74371_ = this.f_74371_;
      structureplacesettings.f_74372_ = this.f_74372_;
      return structureplacesettings;
   }

   public StructurePlaceSettings m_74377_(Mirror p_74378_) {
      this.f_74361_ = p_74378_;
      return this;
   }

   public StructurePlaceSettings m_74379_(Rotation p_74380_) {
      this.f_74362_ = p_74380_;
      return this;
   }

   public StructurePlaceSettings m_74385_(BlockPos p_74386_) {
      this.f_74363_ = p_74386_;
      return this;
   }

   public StructurePlaceSettings m_74392_(boolean p_74393_) {
      this.f_74364_ = p_74393_;
      return this;
   }

   public StructurePlaceSettings m_74381_(BoundingBox p_74382_) {
      this.f_74366_ = p_74382_;
      return this;
   }

   public StructurePlaceSettings m_74390_(@Nullable Random p_74391_) {
      this.f_74368_ = p_74391_;
      return this;
   }

   public StructurePlaceSettings m_163782_(boolean p_163783_) {
      this.f_74367_ = p_163783_;
      return this;
   }

   public StructurePlaceSettings m_74402_(boolean p_74403_) {
      this.f_74371_ = p_74403_;
      return this;
   }

   public StructurePlaceSettings m_74394_() {
      this.f_74370_.clear();
      return this;
   }

   public StructurePlaceSettings m_74383_(StructureProcessor p_74384_) {
      this.f_74370_.add(p_74384_);
      return this;
   }

   public StructurePlaceSettings m_74397_(StructureProcessor p_74398_) {
      this.f_74370_.remove(p_74398_);
      return this;
   }

   public Mirror m_74401_() {
      return this.f_74361_;
   }

   public Rotation m_74404_() {
      return this.f_74362_;
   }

   public BlockPos m_74407_() {
      return this.f_74363_;
   }

   public Random m_74399_(@Nullable BlockPos p_74400_) {
      if (this.f_74368_ != null) {
         return this.f_74368_;
      } else {
         return p_74400_ == null ? new Random(Util.m_137550_()) : new Random(Mth.m_14057_(p_74400_));
      }
   }

   public boolean m_74408_() {
      return this.f_74364_;
   }

   @Nullable
   public BoundingBox m_74409_() {
      return this.f_74366_;
   }

   public boolean m_74410_() {
      return this.f_74371_;
   }

   public List<StructureProcessor> m_74411_() {
      return this.f_74370_;
   }

   public boolean m_74413_() {
      return this.f_74367_;
   }

   public StructureTemplate.Palette m_74387_(List<StructureTemplate.Palette> p_74388_, @Nullable BlockPos p_74389_) {
      int i = p_74388_.size();
      if (i == 0) {
         throw new IllegalStateException("No palettes");
      } else {
         return p_74388_.get(this.m_74399_(p_74389_).nextInt(i));
      }
   }

   public StructurePlaceSettings m_74405_(boolean p_74406_) {
      this.f_74372_ = p_74406_;
      return this;
   }

   public boolean m_74414_() {
      return this.f_74372_;
   }
}