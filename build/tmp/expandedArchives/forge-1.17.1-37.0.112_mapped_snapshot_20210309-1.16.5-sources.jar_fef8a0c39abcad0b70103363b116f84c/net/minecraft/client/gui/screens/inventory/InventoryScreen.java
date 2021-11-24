package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InventoryScreen extends EffectRenderingInventoryScreen<InventoryMenu> implements RecipeUpdateListener {
   private static final ResourceLocation f_98830_ = new ResourceLocation("textures/gui/recipe_button.png");
   private float f_98831_;
   private float f_98832_;
   private final RecipeBookComponent f_98833_ = new RecipeBookComponent();
   private boolean f_98834_;
   private boolean f_98835_;
   private boolean f_98836_;

   public InventoryScreen(Player p_98839_) {
      super(p_98839_.f_36095_, p_98839_.m_150109_(), new TranslatableComponent("container.crafting"));
      this.f_96546_ = true;
      this.f_97728_ = 97;
   }

   public void m_181908_() {
      if (this.f_96541_.f_91072_.m_105290_()) {
         this.f_96541_.m_91152_(new CreativeModeInventoryScreen(this.f_96541_.f_91074_));
      } else {
         this.f_98833_.m_100386_();
      }
   }

   protected void m_7856_() {
      if (this.f_96541_.f_91072_.m_105290_()) {
         this.f_96541_.m_91152_(new CreativeModeInventoryScreen(this.f_96541_.f_91074_));
      } else {
         super.m_7856_();
         this.f_98835_ = this.f_96543_ < 379;
         this.f_98833_.m_100309_(this.f_96543_, this.f_96544_, this.f_96541_, this.f_98835_, this.f_97732_);
         this.f_98834_ = true;
         this.f_97735_ = this.f_98833_.m_181401_(this.f_96543_, this.f_97726_);
         this.m_142416_(new ImageButton(this.f_97735_ + 104, this.f_96544_ / 2 - 22, 20, 18, 0, 0, 19, f_98830_, (p_98880_) -> {
            this.f_98833_.m_100384_();
            this.f_97735_ = this.f_98833_.m_181401_(this.f_96543_, this.f_97726_);
            ((ImageButton)p_98880_).m_94278_(this.f_97735_ + 104, this.f_96544_ / 2 - 22);
            this.f_98836_ = true;
         }));
         this.m_7787_(this.f_98833_);
         this.m_94718_(this.f_98833_);
      }
   }

   protected void m_7027_(PoseStack p_98889_, int p_98890_, int p_98891_) {
      this.f_96547_.m_92889_(p_98889_, this.f_96539_, (float)this.f_97728_, (float)this.f_97729_, 4210752);
   }

   public void m_6305_(PoseStack p_98875_, int p_98876_, int p_98877_, float p_98878_) {
      this.m_7333_(p_98875_);
      this.f_98699_ = !this.f_98833_.m_100385_();
      if (this.f_98833_.m_100385_() && this.f_98835_) {
         this.m_7286_(p_98875_, p_98878_, p_98876_, p_98877_);
         this.f_98833_.m_6305_(p_98875_, p_98876_, p_98877_, p_98878_);
      } else {
         this.f_98833_.m_6305_(p_98875_, p_98876_, p_98877_, p_98878_);
         super.m_6305_(p_98875_, p_98876_, p_98877_, p_98878_);
         this.f_98833_.m_6545_(p_98875_, this.f_97735_, this.f_97736_, false, p_98878_);
      }

      this.m_7025_(p_98875_, p_98876_, p_98877_);
      this.f_98833_.m_100361_(p_98875_, this.f_97735_, this.f_97736_, p_98876_, p_98877_);
      this.f_98831_ = (float)p_98876_;
      this.f_98832_ = (float)p_98877_;
   }

   protected void m_7286_(PoseStack p_98870_, float p_98871_, int p_98872_, int p_98873_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_97725_);
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_93228_(p_98870_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      m_98850_(i + 51, j + 75, 30, (float)(i + 51) - this.f_98831_, (float)(j + 75 - 50) - this.f_98832_, this.f_96541_.f_91074_);
   }

   public static void m_98850_(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_) {
      float f = (float)Math.atan((double)(p_98854_ / 40.0F));
      float f1 = (float)Math.atan((double)(p_98855_ / 40.0F));
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      posestack.m_85837_((double)p_98851_, (double)p_98852_, 1050.0D);
      posestack.m_85841_(1.0F, 1.0F, -1.0F);
      RenderSystem.m_157182_();
      PoseStack posestack1 = new PoseStack();
      posestack1.m_85837_(0.0D, 0.0D, 1000.0D);
      posestack1.m_85841_((float)p_98853_, (float)p_98853_, (float)p_98853_);
      Quaternion quaternion = Vector3f.f_122227_.m_122240_(180.0F);
      Quaternion quaternion1 = Vector3f.f_122223_.m_122240_(f1 * 20.0F);
      quaternion.m_80148_(quaternion1);
      posestack1.m_85845_(quaternion);
      float f2 = p_98856_.f_20883_;
      float f3 = p_98856_.m_146908_();
      float f4 = p_98856_.m_146909_();
      float f5 = p_98856_.f_20886_;
      float f6 = p_98856_.f_20885_;
      p_98856_.f_20883_ = 180.0F + f * 20.0F;
      p_98856_.m_146922_(180.0F + f * 40.0F);
      p_98856_.m_146926_(-f1 * 20.0F);
      p_98856_.f_20885_ = p_98856_.m_146908_();
      p_98856_.f_20886_ = p_98856_.m_146908_();
      Lighting.m_166384_();
      EntityRenderDispatcher entityrenderdispatcher = Minecraft.m_91087_().m_91290_();
      quaternion1.m_80157_();
      entityrenderdispatcher.m_114412_(quaternion1);
      entityrenderdispatcher.m_114468_(false);
      MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      RenderSystem.m_69890_(() -> {
         entityrenderdispatcher.m_114384_(p_98856_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
      });
      multibuffersource$buffersource.m_109911_();
      entityrenderdispatcher.m_114468_(true);
      p_98856_.f_20883_ = f2;
      p_98856_.m_146922_(f3);
      p_98856_.m_146926_(f4);
      p_98856_.f_20886_ = f5;
      p_98856_.f_20885_ = f6;
      posestack.m_85849_();
      RenderSystem.m_157182_();
      Lighting.m_84931_();
   }

   protected boolean m_6774_(int p_98858_, int p_98859_, int p_98860_, int p_98861_, double p_98862_, double p_98863_) {
      return (!this.f_98835_ || !this.f_98833_.m_100385_()) && super.m_6774_(p_98858_, p_98859_, p_98860_, p_98861_, p_98862_, p_98863_);
   }

   public boolean m_6375_(double p_98841_, double p_98842_, int p_98843_) {
      if (this.f_98833_.m_6375_(p_98841_, p_98842_, p_98843_)) {
         this.m_7522_(this.f_98833_);
         return true;
      } else {
         return this.f_98835_ && this.f_98833_.m_100385_() ? false : super.m_6375_(p_98841_, p_98842_, p_98843_);
      }
   }

   public boolean m_6348_(double p_98893_, double p_98894_, int p_98895_) {
      if (this.f_98836_) {
         this.f_98836_ = false;
         return true;
      } else {
         return super.m_6348_(p_98893_, p_98894_, p_98895_);
      }
   }

   protected boolean m_7467_(double p_98845_, double p_98846_, int p_98847_, int p_98848_, int p_98849_) {
      boolean flag = p_98845_ < (double)p_98847_ || p_98846_ < (double)p_98848_ || p_98845_ >= (double)(p_98847_ + this.f_97726_) || p_98846_ >= (double)(p_98848_ + this.f_97727_);
      return this.f_98833_.m_100297_(p_98845_, p_98846_, this.f_97735_, this.f_97736_, this.f_97726_, this.f_97727_, p_98849_) && flag;
   }

   protected void m_6597_(Slot p_98865_, int p_98866_, int p_98867_, ClickType p_98868_) {
      super.m_6597_(p_98865_, p_98866_, p_98867_, p_98868_);
      this.f_98833_.m_6904_(p_98865_);
   }

   public void m_6916_() {
      this.f_98833_.m_100387_();
   }

   public void m_7861_() {
      if (this.f_98834_) {
         this.f_98833_.m_100373_();
      }

      super.m_7861_();
   }

   public RecipeBookComponent m_5564_() {
      return this.f_98833_;
   }
}