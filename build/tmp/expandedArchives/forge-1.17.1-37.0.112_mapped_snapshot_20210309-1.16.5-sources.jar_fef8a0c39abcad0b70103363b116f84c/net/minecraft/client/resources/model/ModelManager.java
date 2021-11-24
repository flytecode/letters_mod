package net.minecraft.client.resources.model;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.AtlasSet;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelManager extends SimplePreparableReloadListener<ModelBakery> implements AutoCloseable {
   private Map<ResourceLocation, BakedModel> f_119397_ = new java.util.HashMap<>();
   @Nullable
   private AtlasSet f_119398_;
   private final BlockModelShaper f_119399_;
   private final TextureManager f_119400_;
   private final BlockColors f_119401_;
   private int f_119402_;
   private BakedModel f_119403_;
   private Object2IntMap<BlockState> f_119404_;

   public ModelManager(TextureManager p_119406_, BlockColors p_119407_, int p_119408_) {
      this.f_119400_ = p_119406_;
      this.f_119401_ = p_119407_;
      this.f_119402_ = p_119408_;
      this.f_119399_ = new BlockModelShaper(this);
   }

   public BakedModel getModel(ResourceLocation modelLocation) {
      return this.f_119397_.getOrDefault(modelLocation, this.f_119403_);
   }

   public BakedModel m_119422_(ModelResourceLocation p_119423_) {
      return this.f_119397_.getOrDefault(p_119423_, this.f_119403_);
   }

   public BakedModel m_119409_() {
      return this.f_119403_;
   }

   public BlockModelShaper m_119430_() {
      return this.f_119399_;
   }

   protected ModelBakery m_5944_(ResourceManager p_119413_, ProfilerFiller p_119414_) {
      p_119414_.m_7242_();
      net.minecraftforge.client.model.ModelLoader modelbakery = new net.minecraftforge.client.model.ModelLoader(p_119413_, this.f_119401_, p_119414_, this.f_119402_);
      p_119414_.m_7241_();
      return modelbakery;
   }

   protected void m_5787_(ModelBakery p_119419_, ResourceManager p_119420_, ProfilerFiller p_119421_) {
      p_119421_.m_7242_();
      p_119421_.m_6180_("upload");
      if (this.f_119398_ != null) {
         this.f_119398_.close();
      }

      this.f_119398_ = p_119419_.m_119298_(this.f_119400_, p_119421_);
      this.f_119397_ = p_119419_.m_119251_();
      this.f_119404_ = p_119419_.m_119355_();
      this.f_119403_ = this.f_119397_.get(ModelBakery.f_119230_);
      net.minecraftforge.client.ForgeHooksClient.onModelBake(this, this.f_119397_, (net.minecraftforge.client.model.ModelLoader) p_119419_);
      p_119421_.m_6182_("cache");
      this.f_119399_.m_110892_();
      p_119421_.m_7238_();
      p_119421_.m_7241_();
   }

   public boolean m_119415_(BlockState p_119416_, BlockState p_119417_) {
      if (p_119416_ == p_119417_) {
         return false;
      } else {
         int i = this.f_119404_.getInt(p_119416_);
         if (i != -1) {
            int j = this.f_119404_.getInt(p_119417_);
            if (i == j) {
               FluidState fluidstate = p_119416_.m_60819_();
               FluidState fluidstate1 = p_119417_.m_60819_();
               return fluidstate != fluidstate1;
            }
         }

         return true;
      }
   }

   public TextureAtlas m_119428_(ResourceLocation p_119429_) {
      if (this.f_119398_ == null) throw new RuntimeException("getAtlasTexture called too early!");
      return this.f_119398_.m_117973_(p_119429_);
   }

   public void close() {
      if (this.f_119398_ != null) {
         this.f_119398_.close();
      }

   }

   public void m_119410_(int p_119411_) {
      this.f_119402_ = p_119411_;
   }
}
