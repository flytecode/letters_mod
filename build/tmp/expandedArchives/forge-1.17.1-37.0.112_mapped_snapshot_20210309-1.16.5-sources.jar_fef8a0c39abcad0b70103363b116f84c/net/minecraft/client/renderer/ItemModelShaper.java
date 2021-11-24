package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemModelShaper {
   public final Int2ObjectMap<ModelResourceLocation> f_109388_ = new Int2ObjectOpenHashMap<>(256);
   private final Int2ObjectMap<BakedModel> f_109389_ = new Int2ObjectOpenHashMap<>(256);
   private final ModelManager f_109390_;

   public ItemModelShaper(ModelManager p_109392_) {
      this.f_109390_ = p_109392_;
   }

   public TextureAtlasSprite m_109401_(ItemLike p_109402_) {
      return this.m_109399_(new ItemStack(p_109402_));
   }

   public TextureAtlasSprite m_109399_(ItemStack p_109400_) {
      BakedModel bakedmodel = this.m_109406_(p_109400_);
      // FORGE: Make sure to call the item overrides
      return bakedmodel == this.f_109390_.m_119409_() && p_109400_.m_41720_() instanceof BlockItem ? this.f_109390_.m_119430_().m_110882_(((BlockItem)p_109400_.m_41720_()).m_40614_().m_49966_()) : bakedmodel.m_7343_().m_173464_(bakedmodel, p_109400_, null, null, 0).getParticleIcon(net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
   }

   public BakedModel m_109406_(ItemStack p_109407_) {
      BakedModel bakedmodel = this.m_109394_(p_109407_.m_41720_());
      return bakedmodel == null ? this.f_109390_.m_119409_() : bakedmodel;
   }

   @Nullable
   public BakedModel m_109394_(Item p_109395_) {
      return this.f_109389_.get(m_109404_(p_109395_));
   }

   private static int m_109404_(Item p_109405_) {
      return Item.m_41393_(p_109405_);
   }

   public void m_109396_(Item p_109397_, ModelResourceLocation p_109398_) {
      this.f_109388_.put(m_109404_(p_109397_), p_109398_);
   }

   public ModelManager m_109393_() {
      return this.f_109390_;
   }

   public void m_109403_() {
      this.f_109389_.clear();

      for(Entry<Integer, ModelResourceLocation> entry : this.f_109388_.entrySet()) {
         this.f_109389_.put(entry.getKey(), this.f_109390_.m_119422_(entry.getValue()));
      }

   }
}
