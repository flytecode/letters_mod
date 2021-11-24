package net.minecraft.util.datafix;

import com.mojang.datafixers.DSL.TypeReference;
import net.minecraft.util.datafix.fixes.References;

public enum DataFixTypes {
   LEVEL(References.f_16771_),
   PLAYER(References.f_16772_),
   CHUNK(References.f_16773_),
   HOTBAR(References.f_16774_),
   OPTIONS(References.f_16775_),
   STRUCTURE(References.f_16776_),
   STATS(References.f_16777_),
   SAVED_DATA(References.f_16778_),
   ADVANCEMENTS(References.f_16779_),
   POI_CHUNK(References.f_16780_),
   WORLD_GEN_SETTINGS(References.f_16795_),
   ENTITY_CHUNK(References.f_145628_);

   private final TypeReference f_14497_;

   private DataFixTypes(TypeReference p_14503_) {
      this.f_14497_ = p_14503_;
   }

   public TypeReference m_14504_() {
      return this.f_14497_;
   }
}