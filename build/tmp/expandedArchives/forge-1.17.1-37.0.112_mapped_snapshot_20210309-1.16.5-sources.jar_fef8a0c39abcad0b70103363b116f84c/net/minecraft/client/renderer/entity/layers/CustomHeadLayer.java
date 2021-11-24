package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomHeadLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
   private final float f_116709_;
   private final float f_116710_;
   private final float f_116711_;
   private final Map<SkullBlock.Type, SkullModelBase> f_174473_;

   public CustomHeadLayer(RenderLayerParent<T, M> p_174475_, EntityModelSet p_174476_) {
      this(p_174475_, p_174476_, 1.0F, 1.0F, 1.0F);
   }

   public CustomHeadLayer(RenderLayerParent<T, M> p_174478_, EntityModelSet p_174479_, float p_174480_, float p_174481_, float p_174482_) {
      super(p_174478_);
      this.f_116709_ = p_174480_;
      this.f_116710_ = p_174481_;
      this.f_116711_ = p_174482_;
      this.f_174473_ = SkullBlockRenderer.m_173661_(p_174479_);
   }

   public void m_6494_(PoseStack p_116731_, MultiBufferSource p_116732_, int p_116733_, T p_116734_, float p_116735_, float p_116736_, float p_116737_, float p_116738_, float p_116739_, float p_116740_) {
      ItemStack itemstack = p_116734_.m_6844_(EquipmentSlot.HEAD);
      if (!itemstack.m_41619_()) {
         Item item = itemstack.m_41720_();
         p_116731_.m_85836_();
         p_116731_.m_85841_(this.f_116709_, this.f_116710_, this.f_116711_);
         boolean flag = p_116734_ instanceof Villager || p_116734_ instanceof ZombieVillager;
         if (p_116734_.m_6162_() && !(p_116734_ instanceof Villager)) {
            float f = 2.0F;
            float f1 = 1.4F;
            p_116731_.m_85837_(0.0D, 0.03125D, 0.0D);
            p_116731_.m_85841_(0.7F, 0.7F, 0.7F);
            p_116731_.m_85837_(0.0D, 1.0D, 0.0D);
         }

         this.m_117386_().m_5585_().m_104299_(p_116731_);
         if (item instanceof BlockItem && ((BlockItem)item).m_40614_() instanceof AbstractSkullBlock) {
            float f2 = 1.1875F;
            p_116731_.m_85841_(1.1875F, -1.1875F, -1.1875F);
            if (flag) {
               p_116731_.m_85837_(0.0D, 0.0625D, 0.0D);
            }

            GameProfile gameprofile = null;
            if (itemstack.m_41782_()) {
               CompoundTag compoundtag = itemstack.m_41783_();
               if (compoundtag.m_128425_("SkullOwner", 10)) {
                  gameprofile = NbtUtils.m_129228_(compoundtag.m_128469_("SkullOwner"));
               }
            }

            p_116731_.m_85837_(-0.5D, 0.0D, -0.5D);
            SkullBlock.Type skullblock$type = ((AbstractSkullBlock)((BlockItem)item).m_40614_()).m_48754_();
            SkullModelBase skullmodelbase = this.f_174473_.get(skullblock$type);
            RenderType rendertype = SkullBlockRenderer.m_112523_(skullblock$type, gameprofile);
            SkullBlockRenderer.m_173663_((Direction)null, 180.0F, p_116735_, p_116731_, p_116732_, p_116733_, skullmodelbase, rendertype);
         } else if (!(item instanceof ArmorItem) || ((ArmorItem)item).m_40402_() != EquipmentSlot.HEAD) {
            m_174483_(p_116731_, flag);
            Minecraft.m_91087_().m_91292_().m_109322_(p_116734_, itemstack, ItemTransforms.TransformType.HEAD, false, p_116731_, p_116732_, p_116733_);
         }

         p_116731_.m_85849_();
      }
   }

   public static void m_174483_(PoseStack p_174484_, boolean p_174485_) {
      float f = 0.625F;
      p_174484_.m_85837_(0.0D, -0.25D, 0.0D);
      p_174484_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
      p_174484_.m_85841_(0.625F, -0.625F, -0.625F);
      if (p_174485_) {
         p_174484_.m_85837_(0.0D, 0.1875D, 0.0D);
      }

   }
}