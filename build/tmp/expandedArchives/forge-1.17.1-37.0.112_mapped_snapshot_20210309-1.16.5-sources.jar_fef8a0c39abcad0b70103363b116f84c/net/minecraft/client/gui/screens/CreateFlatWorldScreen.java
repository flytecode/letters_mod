package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreateFlatWorldScreen extends Screen {
   private static final int f_169285_ = 128;
   private static final int f_169286_ = 18;
   private static final int f_169287_ = 20;
   private static final int f_169288_ = 1;
   private static final int f_169289_ = 1;
   private static final int f_169290_ = 2;
   private static final int f_169291_ = 2;
   protected final CreateWorldScreen f_95814_;
   private final Consumer<FlatLevelGeneratorSettings> f_95815_;
   FlatLevelGeneratorSettings f_95816_;
   private Component f_95817_;
   private Component f_95818_;
   private CreateFlatWorldScreen.DetailsList f_95819_;
   private Button f_95820_;

   public CreateFlatWorldScreen(CreateWorldScreen p_95822_, Consumer<FlatLevelGeneratorSettings> p_95823_, FlatLevelGeneratorSettings p_95824_) {
      super(new TranslatableComponent("createWorld.customize.flat.title"));
      this.f_95814_ = p_95822_;
      this.f_95815_ = p_95823_;
      this.f_95816_ = p_95824_;
   }

   public FlatLevelGeneratorSettings m_95846_() {
      return this.f_95816_;
   }

   public void m_95825_(FlatLevelGeneratorSettings p_95826_) {
      this.f_95816_ = p_95826_;
   }

   protected void m_7856_() {
      this.f_95817_ = new TranslatableComponent("createWorld.customize.flat.tile");
      this.f_95818_ = new TranslatableComponent("createWorld.customize.flat.height");
      this.f_95819_ = new CreateFlatWorldScreen.DetailsList();
      this.m_7787_(this.f_95819_);
      this.f_95820_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 52, 150, 20, new TranslatableComponent("createWorld.customize.flat.removeLayer"), (p_95845_) -> {
         if (this.m_95848_()) {
            List<FlatLayerInfo> list = this.f_95816_.m_70401_();
            int i = this.f_95819_.m_6702_().indexOf(this.f_95819_.m_93511_());
            int j = list.size() - i - 1;
            list.remove(j);
            this.f_95819_.m_6987_(list.isEmpty() ? null : this.f_95819_.m_6702_().get(Math.min(i, list.size() - 1)));
            this.f_95816_.m_70403_();
            this.f_95819_.m_95860_();
            this.m_95847_();
         }
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 52, 150, 20, new TranslatableComponent("createWorld.customize.presets"), (p_95843_) -> {
         this.f_96541_.m_91152_(new PresetFlatWorldScreen(this));
         this.f_95816_.m_70403_();
         this.m_95847_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 28, 150, 20, CommonComponents.f_130655_, (p_95839_) -> {
         this.f_95815_.accept(this.f_95816_);
         this.f_96541_.m_91152_(this.f_95814_);
         this.f_95816_.m_70403_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, (p_95833_) -> {
         this.f_96541_.m_91152_(this.f_95814_);
         this.f_95816_.m_70403_();
      }));
      this.f_95816_.m_70403_();
      this.m_95847_();
   }

   void m_95847_() {
      this.f_95820_.f_93623_ = this.m_95848_();
   }

   private boolean m_95848_() {
      return this.f_95819_.m_93511_() != null;
   }

   public void m_7379_() {
      this.f_96541_.m_91152_(this.f_95814_);
   }

   public void m_6305_(PoseStack p_95828_, int p_95829_, int p_95830_, float p_95831_) {
      this.m_7333_(p_95828_);
      this.f_95819_.m_6305_(p_95828_, p_95829_, p_95830_, p_95831_);
      m_93215_(p_95828_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 16777215);
      int i = this.f_96543_ / 2 - 92 - 16;
      m_93243_(p_95828_, this.f_96547_, this.f_95817_, i, 32, 16777215);
      m_93243_(p_95828_, this.f_96547_, this.f_95818_, i + 2 + 213 - this.f_96547_.m_92852_(this.f_95818_), 32, 16777215);
      super.m_6305_(p_95828_, p_95829_, p_95830_, p_95831_);
   }

   @OnlyIn(Dist.CLIENT)
   class DetailsList extends ObjectSelectionList<CreateFlatWorldScreen.DetailsList.Entry> {
      public DetailsList() {
         super(CreateFlatWorldScreen.this.f_96541_, CreateFlatWorldScreen.this.f_96543_, CreateFlatWorldScreen.this.f_96544_, 43, CreateFlatWorldScreen.this.f_96544_ - 60, 24);

         for(int i = 0; i < CreateFlatWorldScreen.this.f_95816_.m_70401_().size(); ++i) {
            this.m_7085_(new CreateFlatWorldScreen.DetailsList.Entry());
         }

      }

      public void m_6987_(@Nullable CreateFlatWorldScreen.DetailsList.Entry p_95855_) {
         super.m_6987_(p_95855_);
         CreateFlatWorldScreen.this.m_95847_();
      }

      protected boolean m_5694_() {
         return CreateFlatWorldScreen.this.m_7222_() == this;
      }

      protected int m_5756_() {
         return this.f_93388_ - 70;
      }

      public void m_95860_() {
         int i = this.m_6702_().indexOf(this.m_93511_());
         this.m_93516_();

         for(int j = 0; j < CreateFlatWorldScreen.this.f_95816_.m_70401_().size(); ++j) {
            this.m_7085_(new CreateFlatWorldScreen.DetailsList.Entry());
         }

         List<CreateFlatWorldScreen.DetailsList.Entry> list = this.m_6702_();
         if (i >= 0 && i < list.size()) {
            this.m_6987_(list.get(i));
         }

      }

      @OnlyIn(Dist.CLIENT)
      class Entry extends ObjectSelectionList.Entry<CreateFlatWorldScreen.DetailsList.Entry> {
         public void m_6311_(PoseStack p_95876_, int p_95877_, int p_95878_, int p_95879_, int p_95880_, int p_95881_, int p_95882_, int p_95883_, boolean p_95884_, float p_95885_) {
            FlatLayerInfo flatlayerinfo = CreateFlatWorldScreen.this.f_95816_.m_70401_().get(CreateFlatWorldScreen.this.f_95816_.m_70401_().size() - p_95877_ - 1);
            BlockState blockstate = flatlayerinfo.m_70344_();
            ItemStack itemstack = this.m_169293_(blockstate);
            this.m_95886_(p_95876_, p_95879_, p_95878_, itemstack);
            CreateFlatWorldScreen.this.f_96547_.m_92889_(p_95876_, itemstack.m_41786_(), (float)(p_95879_ + 18 + 5), (float)(p_95878_ + 3), 16777215);
            Component component;
            if (p_95877_ == 0) {
               component = new TranslatableComponent("createWorld.customize.flat.layer.top", flatlayerinfo.m_70337_());
            } else if (p_95877_ == CreateFlatWorldScreen.this.f_95816_.m_70401_().size() - 1) {
               component = new TranslatableComponent("createWorld.customize.flat.layer.bottom", flatlayerinfo.m_70337_());
            } else {
               component = new TranslatableComponent("createWorld.customize.flat.layer", flatlayerinfo.m_70337_());
            }

            CreateFlatWorldScreen.this.f_96547_.m_92889_(p_95876_, component, (float)(p_95879_ + 2 + 213 - CreateFlatWorldScreen.this.f_96547_.m_92852_(component)), (float)(p_95878_ + 3), 16777215);
         }

         private ItemStack m_169293_(BlockState p_169294_) {
            Item item = p_169294_.m_60734_().m_5456_();
            if (item == Items.f_41852_) {
               if (p_169294_.m_60713_(Blocks.f_49990_)) {
                  item = Items.f_42447_;
               } else if (p_169294_.m_60713_(Blocks.f_49991_)) {
                  item = Items.f_42448_;
               }
            }

            return new ItemStack(item);
         }

         public Component m_142172_() {
            FlatLayerInfo flatlayerinfo = CreateFlatWorldScreen.this.f_95816_.m_70401_().get(CreateFlatWorldScreen.this.f_95816_.m_70401_().size() - DetailsList.this.m_6702_().indexOf(this) - 1);
            ItemStack itemstack = this.m_169293_(flatlayerinfo.m_70344_());
            return (Component)(!itemstack.m_41619_() ? new TranslatableComponent("narrator.select", itemstack.m_41786_()) : TextComponent.f_131282_);
         }

         public boolean m_6375_(double p_95868_, double p_95869_, int p_95870_) {
            if (p_95870_ == 0) {
               DetailsList.this.m_6987_(this);
               return true;
            } else {
               return false;
            }
         }

         private void m_95886_(PoseStack p_95887_, int p_95888_, int p_95889_, ItemStack p_95890_) {
            this.m_95871_(p_95887_, p_95888_ + 1, p_95889_ + 1);
            if (!p_95890_.m_41619_()) {
               CreateFlatWorldScreen.this.f_96542_.m_115123_(p_95890_, p_95888_ + 2, p_95889_ + 2);
            }

         }

         private void m_95871_(PoseStack p_95872_, int p_95873_, int p_95874_) {
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.m_157456_(0, GuiComponent.f_93097_);
            GuiComponent.m_93143_(p_95872_, p_95873_, p_95874_, CreateFlatWorldScreen.this.m_93252_(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}