package net.minecraft.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

@OnlyIn(Dist.CLIENT)
public class BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
   private static final ShulkerBoxBlockEntity[] f_108815_ = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::m_41060_)).map((p_172557_) -> {
      return new ShulkerBoxBlockEntity(p_172557_, BlockPos.f_121853_, Blocks.f_50456_.m_49966_());
   }).toArray((p_172553_) -> {
      return new ShulkerBoxBlockEntity[p_172553_];
   });
   private static final ShulkerBoxBlockEntity f_108816_ = new ShulkerBoxBlockEntity(BlockPos.f_121853_, Blocks.f_50456_.m_49966_());
   private final ChestBlockEntity f_108817_ = new ChestBlockEntity(BlockPos.f_121853_, Blocks.f_50087_.m_49966_());
   private final ChestBlockEntity f_108818_ = new TrappedChestBlockEntity(BlockPos.f_121853_, Blocks.f_50325_.m_49966_());
   private final EnderChestBlockEntity f_108819_ = new EnderChestBlockEntity(BlockPos.f_121853_, Blocks.f_50265_.m_49966_());
   private final BannerBlockEntity f_108820_ = new BannerBlockEntity(BlockPos.f_121853_, Blocks.f_50414_.m_49966_());
   private final BedBlockEntity f_108821_ = new BedBlockEntity(BlockPos.f_121853_, Blocks.f_50028_.m_49966_());
   private final ConduitBlockEntity f_108822_ = new ConduitBlockEntity(BlockPos.f_121853_, Blocks.f_50569_.m_49966_());
   private ShieldModel f_108823_;
   private TridentModel f_108824_;
   private Map<SkullBlock.Type, SkullModelBase> f_172546_;
   private final BlockEntityRenderDispatcher f_172547_;
   private final EntityModelSet f_172548_;

   public BlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
      this.f_172547_ = p_172550_;
      this.f_172548_ = p_172551_;
   }

   public void m_6213_(ResourceManager p_172555_) {
      this.f_108823_ = new ShieldModel(this.f_172548_.m_171103_(ModelLayers.f_171179_));
      this.f_108824_ = new TridentModel(this.f_172548_.m_171103_(ModelLayers.f_171255_));
      this.f_172546_ = SkullBlockRenderer.m_173661_(this.f_172548_);
   }

   public void m_108829_(ItemStack p_108830_, ItemTransforms.TransformType p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_) {
      Item item = p_108830_.m_41720_();
      if (item instanceof BlockItem) {
         Block block = ((BlockItem)item).m_40614_();
         if (block instanceof AbstractSkullBlock) {
            GameProfile gameprofile = null;
            if (p_108830_.m_41782_()) {
               CompoundTag compoundtag = p_108830_.m_41783_();
               if (compoundtag.m_128425_("SkullOwner", 10)) {
                  gameprofile = NbtUtils.m_129228_(compoundtag.m_128469_("SkullOwner"));
               } else if (compoundtag.m_128425_("SkullOwner", 8) && !StringUtils.isBlank(compoundtag.m_128461_("SkullOwner"))) {
                  gameprofile = new GameProfile((UUID)null, compoundtag.m_128461_("SkullOwner"));
                  compoundtag.m_128473_("SkullOwner");
                  SkullBlockEntity.m_155738_(gameprofile, (p_172560_) -> {
                     compoundtag.m_128365_("SkullOwner", NbtUtils.m_129230_(new CompoundTag(), p_172560_));
                  });
               }
            }

            SkullBlock.Type skullblock$type = ((AbstractSkullBlock)block).m_48754_();
            SkullModelBase skullmodelbase = this.f_172546_.get(skullblock$type);
            RenderType rendertype = SkullBlockRenderer.m_112523_(skullblock$type, gameprofile);
            SkullBlockRenderer.m_173663_((Direction)null, 180.0F, 0.0F, p_108832_, p_108833_, p_108834_, skullmodelbase, rendertype);
         } else {
            BlockState blockstate = block.m_49966_();
            BlockEntity blockentity;
            if (block instanceof AbstractBannerBlock) {
               this.f_108820_.m_58489_(p_108830_, ((AbstractBannerBlock)block).m_48674_());
               blockentity = this.f_108820_;
            } else if (block instanceof BedBlock) {
               this.f_108821_.m_58729_(((BedBlock)block).m_49554_());
               blockentity = this.f_108821_;
            } else if (blockstate.m_60713_(Blocks.f_50569_)) {
               blockentity = this.f_108822_;
            } else if (blockstate.m_60713_(Blocks.f_50087_)) {
               blockentity = this.f_108817_;
            } else if (blockstate.m_60713_(Blocks.f_50265_)) {
               blockentity = this.f_108819_;
            } else if (blockstate.m_60713_(Blocks.f_50325_)) {
               blockentity = this.f_108818_;
            } else {
               if (!(block instanceof ShulkerBoxBlock)) {
                  return;
               }

               DyeColor dyecolor = ShulkerBoxBlock.m_56252_(item);
               if (dyecolor == null) {
                  blockentity = f_108816_;
               } else {
                  blockentity = f_108815_[dyecolor.m_41060_()];
               }
            }

            this.f_172547_.m_112272_(blockentity, p_108832_, p_108833_, p_108834_, p_108835_);
         }
      } else {
         if (p_108830_.m_150930_(Items.f_42740_)) {
            boolean flag = p_108830_.m_41737_("BlockEntityTag") != null;
            p_108832_.m_85836_();
            p_108832_.m_85841_(1.0F, -1.0F, -1.0F);
            Material material = flag ? ModelBakery.f_119225_ : ModelBakery.f_119226_;
            VertexConsumer vertexconsumer = material.m_119204_().m_118381_(ItemRenderer.m_115222_(p_108833_, this.f_108823_.m_103119_(material.m_119193_()), true, p_108830_.m_41790_()));
            this.f_108823_.m_103711_().m_104306_(p_108832_, vertexconsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
            if (flag) {
               List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.m_58484_(ShieldItem.m_43102_(p_108830_), BannerBlockEntity.m_58487_(p_108830_));
               BannerRenderer.m_112074_(p_108832_, p_108833_, p_108834_, p_108835_, this.f_108823_.m_103701_(), material, false, list, p_108830_.m_41790_());
            } else {
               this.f_108823_.m_103701_().m_104306_(p_108832_, vertexconsumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            p_108832_.m_85849_();
         } else if (p_108830_.m_150930_(Items.f_42713_)) {
            p_108832_.m_85836_();
            p_108832_.m_85841_(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexconsumer1 = ItemRenderer.m_115222_(p_108833_, this.f_108824_.m_103119_(TridentModel.f_103914_), false, p_108830_.m_41790_());
            this.f_108824_.m_7695_(p_108832_, vertexconsumer1, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
            p_108832_.m_85849_();
         }

      }
   }
}