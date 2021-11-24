package net.minecraft.data.models.model;

import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class TexturedModel {
   public static final TexturedModel.Provider f_125905_ = m_125942_(TextureMapping::m_125748_, ModelTemplates.f_125692_);
   public static final TexturedModel.Provider f_125906_ = m_125942_(TextureMapping::m_125748_, ModelTemplates.f_125693_);
   public static final TexturedModel.Provider f_125907_ = m_125942_(TextureMapping::m_125818_, ModelTemplates.f_125694_);
   public static final TexturedModel.Provider f_125908_ = m_125942_(TextureMapping::m_125818_, ModelTemplates.f_125695_);
   public static final TexturedModel.Provider f_125909_ = m_125942_(TextureMapping::m_125826_, ModelTemplates.f_125697_);
   public static final TexturedModel.Provider f_125910_ = m_125942_(TextureMapping::m_125822_, ModelTemplates.f_125696_);
   public static final TexturedModel.Provider f_125911_ = m_125942_(TextureMapping::m_125848_, ModelTemplates.f_125698_);
   public static final TexturedModel.Provider f_125912_ = m_125942_(TextureMapping::m_125846_, ModelTemplates.f_125699_);
   public static final TexturedModel.Provider f_125913_ = m_125942_(TextureMapping::m_125804_, ModelTemplates.f_125665_);
   public static final TexturedModel.Provider f_125914_ = m_125942_(TextureMapping::m_125810_, ModelTemplates.f_125668_);
   public static final TexturedModel.Provider f_125915_ = m_125942_(TextureMapping::m_125814_, ModelTemplates.f_125666_);
   public static final TexturedModel.Provider f_125916_ = m_125942_(TextureMapping::m_125834_, ModelTemplates.f_125626_);
   public static final TexturedModel.Provider f_125917_ = m_125942_(TextureMapping::m_125852_, ModelTemplates.f_125677_);
   public static final TexturedModel.Provider f_125918_ = m_125942_(TextureMapping::m_125748_, ModelTemplates.f_125629_);
   public static final TexturedModel.Provider f_125919_ = m_125942_(TextureMapping::m_125840_, ModelTemplates.f_125688_);
   public static final TexturedModel.Provider f_125920_ = m_125942_(TextureMapping::m_125840_, ModelTemplates.f_125689_);
   public static final TexturedModel.Provider f_125921_ = m_125942_(TextureMapping::m_125768_, ModelTemplates.f_125652_);
   public static final TexturedModel.Provider f_125922_ = m_125942_(TextureMapping::m_125824_, ModelTemplates.f_125694_);
   public static final TexturedModel.Provider f_125923_ = m_125942_(TextureMapping::m_125824_, ModelTemplates.f_125695_);
   public static final TexturedModel.Provider f_125924_ = m_125942_(TextureMapping::m_125828_, ModelTemplates.f_125697_);
   public static final TexturedModel.Provider f_125925_ = m_125942_(TextureMapping::m_125830_, ModelTemplates.f_125694_);
   private final TextureMapping f_125926_;
   private final ModelTemplate f_125927_;

   private TexturedModel(TextureMapping p_125930_, ModelTemplate p_125931_) {
      this.f_125926_ = p_125930_;
      this.f_125927_ = p_125931_;
   }

   public ModelTemplate m_125932_() {
      return this.f_125927_;
   }

   public TextureMapping m_125951_() {
      return this.f_125926_;
   }

   public TexturedModel m_125940_(Consumer<TextureMapping> p_125941_) {
      p_125941_.accept(this.f_125926_);
      return this;
   }

   public ResourceLocation m_125937_(Block p_125938_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125939_) {
      return this.f_125927_.m_125592_(p_125938_, this.f_125926_, p_125939_);
   }

   public ResourceLocation m_125933_(Block p_125934_, String p_125935_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125936_) {
      return this.f_125927_.m_125596_(p_125934_, p_125935_, this.f_125926_, p_125936_);
   }

   private static TexturedModel.Provider m_125942_(Function<Block, TextureMapping> p_125943_, ModelTemplate p_125944_) {
      return (p_125948_) -> {
         return new TexturedModel(p_125943_.apply(p_125948_), p_125944_);
      };
   }

   public static TexturedModel m_125949_(ResourceLocation p_125950_) {
      return new TexturedModel(TextureMapping.m_125776_(p_125950_), ModelTemplates.f_125692_);
   }

   @FunctionalInterface
   public interface Provider {
      TexturedModel m_125964_(Block p_125965_);

      default ResourceLocation m_125956_(Block p_125957_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125958_) {
         return this.m_125964_(p_125957_).m_125937_(p_125957_, p_125958_);
      }

      default ResourceLocation m_125952_(Block p_125953_, String p_125954_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_125955_) {
         return this.m_125964_(p_125953_).m_125933_(p_125953_, p_125954_, p_125955_);
      }

      default TexturedModel.Provider m_125959_(Consumer<TextureMapping> p_125960_) {
         return (p_125963_) -> {
            return this.m_125964_(p_125963_).m_125940_(p_125960_);
         };
      }
   }
}