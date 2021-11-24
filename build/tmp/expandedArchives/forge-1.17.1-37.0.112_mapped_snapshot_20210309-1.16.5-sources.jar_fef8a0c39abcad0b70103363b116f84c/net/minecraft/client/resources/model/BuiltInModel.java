package net.minecraft.client.resources.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuiltInModel implements BakedModel {
   private final ItemTransforms f_119167_;
   private final ItemOverrides f_119168_;
   private final TextureAtlasSprite f_119169_;
   private final boolean f_119170_;

   public BuiltInModel(ItemTransforms p_119172_, ItemOverrides p_119173_, TextureAtlasSprite p_119174_, boolean p_119175_) {
      this.f_119167_ = p_119172_;
      this.f_119168_ = p_119173_;
      this.f_119169_ = p_119174_;
      this.f_119170_ = p_119175_;
   }

   public List<BakedQuad> m_6840_(@Nullable BlockState p_119178_, @Nullable Direction p_119179_, Random p_119180_) {
      return Collections.emptyList();
   }

   public boolean m_7541_() {
      return false;
   }

   public boolean m_7539_() {
      return true;
   }

   public boolean m_7547_() {
      return this.f_119170_;
   }

   public boolean m_7521_() {
      return true;
   }

   public TextureAtlasSprite m_6160_() {
      return this.f_119169_;
   }

   public ItemTransforms m_7442_() {
      return this.f_119167_;
   }

   public ItemOverrides m_7343_() {
      return this.f_119168_;
   }
}