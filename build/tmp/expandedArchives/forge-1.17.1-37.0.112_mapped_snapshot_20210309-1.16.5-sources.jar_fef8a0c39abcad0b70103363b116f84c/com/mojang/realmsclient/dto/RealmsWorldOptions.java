package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsWorldOptions extends ValueObject {
   public final boolean f_87598_;
   public final boolean f_87599_;
   public final boolean f_87600_;
   public final boolean f_87601_;
   public final int f_87602_;
   public final boolean f_87603_;
   public final boolean f_87604_;
   public final int f_87605_;
   public final int f_87606_;
   @Nullable
   private final String f_87607_;
   public long f_87608_;
   @Nullable
   public String f_87609_;
   public boolean f_87611_;
   private static final boolean f_167290_ = false;
   private static final boolean f_167291_ = true;
   private static final boolean f_167292_ = true;
   private static final boolean f_167293_ = true;
   private static final boolean f_167294_ = true;
   private static final int f_167295_ = 0;
   private static final boolean f_167296_ = false;
   private static final int f_167297_ = 2;
   private static final int f_167298_ = 0;
   private static final String f_167299_ = "";
   private static final long f_167300_ = -1L;
   private static final String f_87612_ = null;

   public RealmsWorldOptions(boolean p_167302_, boolean p_167303_, boolean p_167304_, boolean p_167305_, int p_167306_, boolean p_167307_, int p_167308_, int p_167309_, boolean p_167310_, @Nullable String p_167311_) {
      this.f_87598_ = p_167302_;
      this.f_87599_ = p_167303_;
      this.f_87600_ = p_167304_;
      this.f_87601_ = p_167305_;
      this.f_87602_ = p_167306_;
      this.f_87603_ = p_167307_;
      this.f_87605_ = p_167308_;
      this.f_87606_ = p_167309_;
      this.f_87604_ = p_167310_;
      this.f_87607_ = p_167311_;
   }

   public static RealmsWorldOptions m_87625_() {
      return new RealmsWorldOptions(true, true, true, true, 0, false, 2, 0, false, "");
   }

   public static RealmsWorldOptions m_87632_() {
      RealmsWorldOptions realmsworldoptions = m_87625_();
      realmsworldoptions.m_87630_(true);
      return realmsworldoptions;
   }

   public void m_87630_(boolean p_87631_) {
      this.f_87611_ = p_87631_;
   }

   public static RealmsWorldOptions m_87628_(JsonObject p_87629_) {
      RealmsWorldOptions realmsworldoptions = new RealmsWorldOptions(JsonUtils.m_90165_("pvp", p_87629_, true), JsonUtils.m_90165_("spawnAnimals", p_87629_, true), JsonUtils.m_90165_("spawnMonsters", p_87629_, true), JsonUtils.m_90165_("spawnNPCs", p_87629_, true), JsonUtils.m_90153_("spawnProtection", p_87629_, 0), JsonUtils.m_90165_("commandBlocks", p_87629_, false), JsonUtils.m_90153_("difficulty", p_87629_, 2), JsonUtils.m_90153_("gameMode", p_87629_, 0), JsonUtils.m_90165_("forceGameMode", p_87629_, false), JsonUtils.m_90161_("slotName", p_87629_, ""));
      realmsworldoptions.f_87608_ = JsonUtils.m_90157_("worldTemplateId", p_87629_, -1L);
      realmsworldoptions.f_87609_ = JsonUtils.m_90161_("worldTemplateImage", p_87629_, f_87612_);
      return realmsworldoptions;
   }

   public String m_87626_(int p_87627_) {
      if (this.f_87607_ != null && !this.f_87607_.isEmpty()) {
         return this.f_87607_;
      } else {
         return this.f_87611_ ? I18n.m_118938_("mco.configure.world.slot.empty") : this.m_87633_(p_87627_);
      }
   }

   public String m_87633_(int p_87634_) {
      return I18n.m_118938_("mco.configure.world.slot", p_87634_);
   }

   public String m_87635_() {
      JsonObject jsonobject = new JsonObject();
      if (!this.f_87598_) {
         jsonobject.addProperty("pvp", this.f_87598_);
      }

      if (!this.f_87599_) {
         jsonobject.addProperty("spawnAnimals", this.f_87599_);
      }

      if (!this.f_87600_) {
         jsonobject.addProperty("spawnMonsters", this.f_87600_);
      }

      if (!this.f_87601_) {
         jsonobject.addProperty("spawnNPCs", this.f_87601_);
      }

      if (this.f_87602_ != 0) {
         jsonobject.addProperty("spawnProtection", this.f_87602_);
      }

      if (this.f_87603_) {
         jsonobject.addProperty("commandBlocks", this.f_87603_);
      }

      if (this.f_87605_ != 2) {
         jsonobject.addProperty("difficulty", this.f_87605_);
      }

      if (this.f_87606_ != 0) {
         jsonobject.addProperty("gameMode", this.f_87606_);
      }

      if (this.f_87604_) {
         jsonobject.addProperty("forceGameMode", this.f_87604_);
      }

      if (!Objects.equals(this.f_87607_, "")) {
         jsonobject.addProperty("slotName", this.f_87607_);
      }

      return jsonobject.toString();
   }

   public RealmsWorldOptions clone() {
      return new RealmsWorldOptions(this.f_87598_, this.f_87599_, this.f_87600_, this.f_87601_, this.f_87602_, this.f_87603_, this.f_87605_, this.f_87606_, this.f_87604_, this.f_87607_);
   }
}