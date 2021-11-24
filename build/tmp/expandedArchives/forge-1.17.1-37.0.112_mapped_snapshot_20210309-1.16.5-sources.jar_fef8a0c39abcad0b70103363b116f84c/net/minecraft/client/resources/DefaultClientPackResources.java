package net.minecraft.client.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefaultClientPackResources extends VanillaPackResources {
   private final AssetIndex f_118606_;

   public DefaultClientPackResources(PackMetadataSection p_174827_, AssetIndex p_174828_) {
      super(p_174827_, "minecraft", "realms");
      this.f_118606_ = p_174828_;
   }

   @Nullable
   protected InputStream m_8033_(PackType p_118621_, ResourceLocation p_118622_) {
      if (p_118621_ == PackType.CLIENT_RESOURCES) {
         File file1 = this.f_118606_.m_7879_(p_118622_);
         if (file1 != null && file1.exists()) {
            try {
               return new FileInputStream(file1);
            } catch (FileNotFoundException filenotfoundexception) {
            }
         }
      }

      return super.m_8033_(p_118621_, p_118622_);
   }

   public boolean m_7211_(PackType p_118618_, ResourceLocation p_118619_) {
      if (p_118618_ == PackType.CLIENT_RESOURCES) {
         File file1 = this.f_118606_.m_7879_(p_118619_);
         if (file1 != null && file1.exists()) {
            return true;
         }
      }

      return super.m_7211_(p_118618_, p_118619_);
   }

   @Nullable
   protected InputStream m_5539_(String p_118616_) {
      File file1 = this.f_118606_.m_7974_(p_118616_);
      if (file1 != null && file1.exists()) {
         try {
            return new FileInputStream(file1);
         } catch (FileNotFoundException filenotfoundexception) {
         }
      }

      return super.m_5539_(p_118616_);
   }

   public Collection<ResourceLocation> m_7466_(PackType p_118610_, String p_118611_, String p_118612_, int p_118613_, Predicate<String> p_118614_) {
      Collection<ResourceLocation> collection = super.m_7466_(p_118610_, p_118611_, p_118612_, p_118613_, p_118614_);
      collection.addAll(this.f_118606_.m_6487_(p_118612_, p_118611_, p_118613_, p_118614_));
      return collection;
   }
}