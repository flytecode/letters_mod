package net.minecraft.world.level.block.state.properties;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public enum NoteBlockInstrument implements StringRepresentable {
   HARP("harp", SoundEvents.f_12214_),
   BASEDRUM("basedrum", SoundEvents.f_12208_),
   SNARE("snare", SoundEvents.f_12217_),
   HAT("hat", SoundEvents.f_12215_),
   BASS("bass", SoundEvents.f_12209_),
   FLUTE("flute", SoundEvents.f_12212_),
   BELL("bell", SoundEvents.f_12210_),
   GUITAR("guitar", SoundEvents.f_12213_),
   CHIME("chime", SoundEvents.f_12211_),
   XYLOPHONE("xylophone", SoundEvents.f_12218_),
   IRON_XYLOPHONE("iron_xylophone", SoundEvents.f_12167_),
   COW_BELL("cow_bell", SoundEvents.f_12168_),
   DIDGERIDOO("didgeridoo", SoundEvents.f_12169_),
   BIT("bit", SoundEvents.f_12170_),
   BANJO("banjo", SoundEvents.f_12171_),
   PLING("pling", SoundEvents.f_12216_);

   private final String f_61656_;
   private final SoundEvent f_61657_;

   private NoteBlockInstrument(String p_61663_, SoundEvent p_61664_) {
      this.f_61656_ = p_61663_;
      this.f_61657_ = p_61664_;
   }

   public String m_7912_() {
      return this.f_61656_;
   }

   public SoundEvent m_61668_() {
      return this.f_61657_;
   }

   public static NoteBlockInstrument m_61666_(BlockState p_61667_) {
      if (p_61667_.m_60713_(Blocks.f_50129_)) {
         return FLUTE;
      } else if (p_61667_.m_60713_(Blocks.f_50074_)) {
         return BELL;
      } else if (p_61667_.m_60620_(BlockTags.f_13089_)) {
         return GUITAR;
      } else if (p_61667_.m_60713_(Blocks.f_50354_)) {
         return CHIME;
      } else if (p_61667_.m_60713_(Blocks.f_50453_)) {
         return XYLOPHONE;
      } else if (p_61667_.m_60713_(Blocks.f_50075_)) {
         return IRON_XYLOPHONE;
      } else if (p_61667_.m_60713_(Blocks.f_50135_)) {
         return COW_BELL;
      } else if (p_61667_.m_60713_(Blocks.f_50133_)) {
         return DIDGERIDOO;
      } else if (p_61667_.m_60713_(Blocks.f_50268_)) {
         return BIT;
      } else if (p_61667_.m_60713_(Blocks.f_50335_)) {
         return BANJO;
      } else if (p_61667_.m_60713_(Blocks.f_50141_)) {
         return PLING;
      } else {
         Material material = p_61667_.m_60767_();
         if (material == Material.f_76278_) {
            return BASEDRUM;
         } else if (material == Material.f_76317_) {
            return SNARE;
         } else if (material == Material.f_76275_) {
            return HAT;
         } else {
            return material != Material.f_76320_ && material != Material.f_76321_ ? HARP : BASS;
         }
      }
   }
}