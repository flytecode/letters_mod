package net.minecraft.client.searchtree;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SearchRegistry implements ResourceManagerReloadListener {
   public static final SearchRegistry.Key<ItemStack> f_119941_ = new SearchRegistry.Key<>();
   public static final SearchRegistry.Key<ItemStack> f_119942_ = new SearchRegistry.Key<>();
   public static final SearchRegistry.Key<RecipeCollection> f_119943_ = new SearchRegistry.Key<>();
   private final Map<SearchRegistry.Key<?>, MutableSearchTree<?>> f_119944_ = Maps.newHashMap();

   public void m_6213_(ResourceManager p_119948_) {
      for(MutableSearchTree<?> mutablesearchtree : this.f_119944_.values()) {
         mutablesearchtree.m_7729_();
      }

   }

   public <T> void m_119951_(SearchRegistry.Key<T> p_119952_, MutableSearchTree<T> p_119953_) {
      this.f_119944_.put(p_119952_, p_119953_);
   }

   public <T> MutableSearchTree<T> m_119949_(SearchRegistry.Key<T> p_119950_) {
      return (MutableSearchTree<T>)this.f_119944_.get(p_119950_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Key<T> {
   }
}