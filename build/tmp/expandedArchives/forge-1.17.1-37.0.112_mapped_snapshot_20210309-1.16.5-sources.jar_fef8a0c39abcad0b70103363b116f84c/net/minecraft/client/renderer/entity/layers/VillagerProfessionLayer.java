package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.metadata.animation.VillagerMetaDataSection;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillagerProfessionLayer<T extends LivingEntity & VillagerDataHolder, M extends EntityModel<T> & VillagerHeadModel> extends RenderLayer<T, M> {
   private static final Int2ObjectMap<ResourceLocation> f_117622_ = Util.m_137469_(new Int2ObjectOpenHashMap<>(), (p_117657_) -> {
      p_117657_.put(1, new ResourceLocation("stone"));
      p_117657_.put(2, new ResourceLocation("iron"));
      p_117657_.put(3, new ResourceLocation("gold"));
      p_117657_.put(4, new ResourceLocation("emerald"));
      p_117657_.put(5, new ResourceLocation("diamond"));
   });
   private final Object2ObjectMap<VillagerType, VillagerMetaDataSection.Hat> f_117623_ = new Object2ObjectOpenHashMap<>();
   private final Object2ObjectMap<VillagerProfession, VillagerMetaDataSection.Hat> f_117624_ = new Object2ObjectOpenHashMap<>();
   private final ResourceManager f_117625_;
   private final String f_117626_;

   public VillagerProfessionLayer(RenderLayerParent<T, M> p_174550_, ResourceManager p_174551_, String p_174552_) {
      super(p_174550_);
      this.f_117625_ = p_174551_;
      this.f_117626_ = p_174552_;
   }

   public void m_6494_(PoseStack p_117646_, MultiBufferSource p_117647_, int p_117648_, T p_117649_, float p_117650_, float p_117651_, float p_117652_, float p_117653_, float p_117654_, float p_117655_) {
      if (!p_117649_.m_20145_()) {
         VillagerData villagerdata = p_117649_.m_7141_();
         VillagerType villagertype = villagerdata.m_35560_();
         VillagerProfession villagerprofession = villagerdata.m_35571_();
         VillagerMetaDataSection.Hat villagermetadatasection$hat = this.m_117658_(this.f_117623_, "type", Registry.f_122868_, villagertype);
         VillagerMetaDataSection.Hat villagermetadatasection$hat1 = this.m_117658_(this.f_117624_, "profession", Registry.f_122869_, villagerprofession);
         M m = this.m_117386_();
         m.m_7491_(villagermetadatasection$hat1 == VillagerMetaDataSection.Hat.NONE || villagermetadatasection$hat1 == VillagerMetaDataSection.Hat.PARTIAL && villagermetadatasection$hat != VillagerMetaDataSection.Hat.FULL);
         ResourceLocation resourcelocation = this.m_117668_("type", Registry.f_122868_.m_7981_(villagertype));
         m_117376_(m, resourcelocation, p_117646_, p_117647_, p_117648_, p_117649_, 1.0F, 1.0F, 1.0F);
         m.m_7491_(true);
         if (villagerprofession != VillagerProfession.f_35585_ && !p_117649_.m_6162_()) {
            ResourceLocation resourcelocation1 = this.m_117668_("profession", Registry.f_122869_.m_7981_(villagerprofession));
            m_117376_(m, resourcelocation1, p_117646_, p_117647_, p_117648_, p_117649_, 1.0F, 1.0F, 1.0F);
            if (villagerprofession != VillagerProfession.f_35596_) {
               ResourceLocation resourcelocation2 = this.m_117668_("profession_level", f_117622_.get(Mth.m_14045_(villagerdata.m_35576_(), 1, f_117622_.size())));
               m_117376_(m, resourcelocation2, p_117646_, p_117647_, p_117648_, p_117649_, 1.0F, 1.0F, 1.0F);
            }
         }

      }
   }

   private ResourceLocation m_117668_(String p_117669_, ResourceLocation p_117670_) {
      return new ResourceLocation(p_117670_.m_135827_(), "textures/entity/" + this.f_117626_ + "/" + p_117669_ + "/" + p_117670_.m_135815_() + ".png");
   }

   public <K> VillagerMetaDataSection.Hat m_117658_(Object2ObjectMap<K, VillagerMetaDataSection.Hat> p_117659_, String p_117660_, DefaultedRegistry<K> p_117661_, K p_117662_) {
      return p_117659_.computeIfAbsent(p_117662_, (p_117667_) -> {
         try {
            Resource resource = this.f_117625_.m_142591_(this.m_117668_(p_117660_, p_117661_.m_7981_(p_117662_)));

            VillagerMetaDataSection.Hat villagermetadatasection$hat;
            label50: {
               try {
                  VillagerMetaDataSection villagermetadatasection = resource.m_5507_(VillagerMetaDataSection.f_119065_);
                  if (villagermetadatasection != null) {
                     villagermetadatasection$hat = villagermetadatasection.m_119070_();
                     break label50;
                  }
               } catch (Throwable throwable1) {
                  if (resource != null) {
                     try {
                        resource.close();
                     } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                     }
                  }

                  throw throwable1;
               }

               if (resource != null) {
                  resource.close();
               }

               return VillagerMetaDataSection.Hat.NONE;
            }

            if (resource != null) {
               resource.close();
            }

            return villagermetadatasection$hat;
         } catch (IOException ioexception) {
            return VillagerMetaDataSection.Hat.NONE;
         }
      });
   }
}