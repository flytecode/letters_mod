package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreateBuffetWorldScreen extends Screen {
   private static final Component f_95742_ = new TranslatableComponent("createWorld.customize.buffet.biome");
   private final Screen f_95743_;
   private final Consumer<Biome> f_95744_;
   final Registry<Biome> f_95745_;
   private CreateBuffetWorldScreen.BiomeList f_95746_;
   Biome f_95747_;
   private Button f_95748_;

   public CreateBuffetWorldScreen(Screen p_95751_, RegistryAccess p_95752_, Consumer<Biome> p_95753_, Biome p_95754_) {
      super(new TranslatableComponent("createWorld.customize.buffet.title"));
      this.f_95743_ = p_95751_;
      this.f_95744_ = p_95753_;
      this.f_95747_ = p_95754_;
      this.f_95745_ = p_95752_.m_175515_(Registry.f_122885_);
   }

   public void m_7379_() {
      this.f_96541_.m_91152_(this.f_95743_);
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_95746_ = new CreateBuffetWorldScreen.BiomeList();
      this.m_7787_(this.f_95746_);
      this.f_95748_ = this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 28, 150, 20, CommonComponents.f_130655_, (p_95772_) -> {
         this.f_95744_.accept(this.f_95747_);
         this.f_96541_.m_91152_(this.f_95743_);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, (p_95761_) -> {
         this.f_96541_.m_91152_(this.f_95743_);
      }));
      this.f_95746_.m_6987_(this.f_95746_.m_6702_().stream().filter((p_95763_) -> {
         return Objects.equals(p_95763_.f_95792_, this.f_95747_);
      }).findFirst().orElse((CreateBuffetWorldScreen.BiomeList.Entry)null));
   }

   void m_95775_() {
      this.f_95748_.f_93623_ = this.f_95746_.m_93511_() != null;
   }

   public void m_6305_(PoseStack p_95756_, int p_95757_, int p_95758_, float p_95759_) {
      this.m_96626_(0);
      this.f_95746_.m_6305_(p_95756_, p_95757_, p_95758_, p_95759_);
      m_93215_(p_95756_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 16777215);
      m_93215_(p_95756_, this.f_96547_, f_95742_, this.f_96543_ / 2, 28, 10526880);
      super.m_6305_(p_95756_, p_95757_, p_95758_, p_95759_);
   }

   @OnlyIn(Dist.CLIENT)
   class BiomeList extends ObjectSelectionList<CreateBuffetWorldScreen.BiomeList.Entry> {
      BiomeList() {
         super(CreateBuffetWorldScreen.this.f_96541_, CreateBuffetWorldScreen.this.f_96543_, CreateBuffetWorldScreen.this.f_96544_, 40, CreateBuffetWorldScreen.this.f_96544_ - 37, 16);
         CreateBuffetWorldScreen.this.f_95745_.m_6579_().stream().sorted(Comparator.comparing((p_95790_) -> {
            return p_95790_.getKey().m_135782_().toString();
         })).forEach((p_95787_) -> {
            this.m_7085_(new CreateBuffetWorldScreen.BiomeList.Entry(p_95787_.getValue()));
         });
      }

      protected boolean m_5694_() {
         return CreateBuffetWorldScreen.this.m_7222_() == this;
      }

      public void m_6987_(@Nullable CreateBuffetWorldScreen.BiomeList.Entry p_95785_) {
         super.m_6987_(p_95785_);
         if (p_95785_ != null) {
            CreateBuffetWorldScreen.this.f_95747_ = p_95785_.f_95792_;
         }

         CreateBuffetWorldScreen.this.m_95775_();
      }

      @OnlyIn(Dist.CLIENT)
      class Entry extends ObjectSelectionList.Entry<CreateBuffetWorldScreen.BiomeList.Entry> {
         final Biome f_95792_;
         private final Component f_95793_;

         public Entry(Biome p_95796_) {
            this.f_95792_ = p_95796_;
            ResourceLocation resourcelocation = CreateBuffetWorldScreen.this.f_95745_.m_7981_(p_95796_);
            String s = "biome." + resourcelocation.m_135827_() + "." + resourcelocation.m_135815_();
            if (Language.m_128107_().m_6722_(s)) {
               this.f_95793_ = new TranslatableComponent(s);
            } else {
               this.f_95793_ = new TextComponent(resourcelocation.toString());
            }

         }

         public Component m_142172_() {
            return new TranslatableComponent("narrator.select", this.f_95793_);
         }

         public void m_6311_(PoseStack p_95802_, int p_95803_, int p_95804_, int p_95805_, int p_95806_, int p_95807_, int p_95808_, int p_95809_, boolean p_95810_, float p_95811_) {
            GuiComponent.m_93243_(p_95802_, CreateBuffetWorldScreen.this.f_96547_, this.f_95793_, p_95805_ + 5, p_95804_ + 2, 16777215);
         }

         public boolean m_6375_(double p_95798_, double p_95799_, int p_95800_) {
            if (p_95800_ == 0) {
               BiomeList.this.m_6987_(this);
               return true;
            } else {
               return false;
            }
         }
      }
   }
}