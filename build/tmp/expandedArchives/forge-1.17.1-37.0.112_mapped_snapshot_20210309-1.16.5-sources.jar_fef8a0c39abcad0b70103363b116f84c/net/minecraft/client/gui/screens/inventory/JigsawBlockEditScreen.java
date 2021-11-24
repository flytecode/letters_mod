package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JigsawBlockEditScreen extends Screen {
   private static final int f_169762_ = 7;
   private static final Component f_98933_ = new TranslatableComponent("jigsaw_block.joint_label");
   private static final Component f_98934_ = new TranslatableComponent("jigsaw_block.pool");
   private static final Component f_98935_ = new TranslatableComponent("jigsaw_block.name");
   private static final Component f_98936_ = new TranslatableComponent("jigsaw_block.target");
   private static final Component f_98937_ = new TranslatableComponent("jigsaw_block.final_state");
   private final JigsawBlockEntity f_98938_;
   private EditBox f_98939_;
   private EditBox f_98940_;
   private EditBox f_98941_;
   private EditBox f_98942_;
   int f_98943_;
   private boolean f_98944_ = true;
   private CycleButton<JigsawBlockEntity.JointType> f_98945_;
   private Button f_98946_;
   private Button f_169763_;
   private JigsawBlockEntity.JointType f_98932_;

   public JigsawBlockEditScreen(JigsawBlockEntity p_98949_) {
      super(NarratorChatListener.f_93310_);
      this.f_98938_ = p_98949_;
   }

   public void m_96624_() {
      this.f_98939_.m_94120_();
      this.f_98940_.m_94120_();
      this.f_98941_.m_94120_();
      this.f_98942_.m_94120_();
   }

   private void m_98990_() {
      this.m_98992_();
      this.f_96541_.m_91152_((Screen)null);
   }

   private void m_98991_() {
      this.f_96541_.m_91152_((Screen)null);
   }

   private void m_98992_() {
      this.f_96541_.m_91403_().m_104955_(new ServerboundSetJigsawBlockPacket(this.f_98938_.m_58899_(), new ResourceLocation(this.f_98939_.m_94155_()), new ResourceLocation(this.f_98940_.m_94155_()), new ResourceLocation(this.f_98941_.m_94155_()), this.f_98942_.m_94155_(), this.f_98932_));
   }

   private void m_98993_() {
      this.f_96541_.m_91403_().m_104955_(new ServerboundJigsawGeneratePacket(this.f_98938_.m_58899_(), this.f_98943_, this.f_98944_));
   }

   public void m_7379_() {
      this.m_98991_();
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_98941_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 20, 300, 20, new TranslatableComponent("jigsaw_block.pool"));
      this.f_98941_.m_94199_(128);
      this.f_98941_.m_94144_(this.f_98938_.m_59444_().toString());
      this.f_98941_.m_94151_((p_98986_) -> {
         this.m_98994_();
      });
      this.m_7787_(this.f_98941_);
      this.f_98939_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 55, 300, 20, new TranslatableComponent("jigsaw_block.name"));
      this.f_98939_.m_94199_(128);
      this.f_98939_.m_94144_(this.f_98938_.m_59442_().toString());
      this.f_98939_.m_94151_((p_98981_) -> {
         this.m_98994_();
      });
      this.m_7787_(this.f_98939_);
      this.f_98940_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 90, 300, 20, new TranslatableComponent("jigsaw_block.target"));
      this.f_98940_.m_94199_(128);
      this.f_98940_.m_94144_(this.f_98938_.m_59443_().toString());
      this.f_98940_.m_94151_((p_98977_) -> {
         this.m_98994_();
      });
      this.m_7787_(this.f_98940_);
      this.f_98942_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 152, 125, 300, 20, new TranslatableComponent("jigsaw_block.final_state"));
      this.f_98942_.m_94199_(256);
      this.f_98942_.m_94144_(this.f_98938_.m_59445_());
      this.m_7787_(this.f_98942_);
      this.f_98932_ = this.f_98938_.m_59446_();
      int i = this.f_96547_.m_92852_(f_98933_) + 10;
      this.f_98945_ = this.m_142416_(CycleButton.m_168894_(JigsawBlockEntity.JointType::m_155610_).m_168961_(JigsawBlockEntity.JointType.values()).m_168948_(this.f_98932_).m_168929_().m_168936_(this.f_96543_ / 2 - 152 + i, 150, 300 - i, 20, f_98933_, (p_169765_, p_169766_) -> {
         this.f_98932_ = p_169766_;
      }));
      boolean flag = JigsawBlock.m_54250_(this.f_98938_.m_58900_()).m_122434_().m_122478_();
      this.f_98945_.f_93623_ = flag;
      this.f_98945_.f_93624_ = flag;
      this.m_142416_(new AbstractSliderButton(this.f_96543_ / 2 - 154, 180, 100, 20, TextComponent.f_131282_, 0.0D) {
         {
            this.m_5695_();
         }

         protected void m_5695_() {
            this.m_93666_(new TranslatableComponent("jigsaw_block.levels", JigsawBlockEditScreen.this.f_98943_));
         }

         protected void m_5697_() {
            JigsawBlockEditScreen.this.f_98943_ = Mth.m_14107_(Mth.m_14085_(0.0D, 7.0D, this.f_93577_));
         }
      });
      this.m_142416_(CycleButton.m_168916_(this.f_98944_).m_168936_(this.f_96543_ / 2 - 50, 180, 100, 20, new TranslatableComponent("jigsaw_block.keep_jigsaws"), (p_169768_, p_169769_) -> {
         this.f_98944_ = p_169769_;
      }));
      this.f_169763_ = this.m_142416_(new Button(this.f_96543_ / 2 + 54, 180, 100, 20, new TranslatableComponent("jigsaw_block.generate"), (p_98979_) -> {
         this.m_98990_();
         this.m_98993_();
      }));
      this.f_98946_ = this.m_142416_(new Button(this.f_96543_ / 2 - 4 - 150, 210, 150, 20, CommonComponents.f_130655_, (p_98973_) -> {
         this.m_98990_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 4, 210, 150, 20, CommonComponents.f_130656_, (p_98964_) -> {
         this.m_98991_();
      }));
      this.m_94718_(this.f_98941_);
      this.m_98994_();
   }

   private void m_98994_() {
      boolean flag = ResourceLocation.m_135830_(this.f_98939_.m_94155_()) && ResourceLocation.m_135830_(this.f_98940_.m_94155_()) && ResourceLocation.m_135830_(this.f_98941_.m_94155_());
      this.f_98946_.f_93623_ = flag;
      this.f_169763_.f_93623_ = flag;
   }

   public void m_6574_(Minecraft p_98960_, int p_98961_, int p_98962_) {
      String s = this.f_98939_.m_94155_();
      String s1 = this.f_98940_.m_94155_();
      String s2 = this.f_98941_.m_94155_();
      String s3 = this.f_98942_.m_94155_();
      int i = this.f_98943_;
      JigsawBlockEntity.JointType jigsawblockentity$jointtype = this.f_98932_;
      this.m_6575_(p_98960_, p_98961_, p_98962_);
      this.f_98939_.m_94144_(s);
      this.f_98940_.m_94144_(s1);
      this.f_98941_.m_94144_(s2);
      this.f_98942_.m_94144_(s3);
      this.f_98943_ = i;
      this.f_98932_ = jigsawblockentity$jointtype;
      this.f_98945_.m_168892_(jigsawblockentity$jointtype);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public boolean m_7933_(int p_98951_, int p_98952_, int p_98953_) {
      if (super.m_7933_(p_98951_, p_98952_, p_98953_)) {
         return true;
      } else if (!this.f_98946_.f_93623_ || p_98951_ != 257 && p_98951_ != 335) {
         return false;
      } else {
         this.m_98990_();
         return true;
      }
   }

   public void m_6305_(PoseStack p_98955_, int p_98956_, int p_98957_, float p_98958_) {
      this.m_7333_(p_98955_);
      m_93243_(p_98955_, this.f_96547_, f_98934_, this.f_96543_ / 2 - 153, 10, 10526880);
      this.f_98941_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
      m_93243_(p_98955_, this.f_96547_, f_98935_, this.f_96543_ / 2 - 153, 45, 10526880);
      this.f_98939_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
      m_93243_(p_98955_, this.f_96547_, f_98936_, this.f_96543_ / 2 - 153, 80, 10526880);
      this.f_98940_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
      m_93243_(p_98955_, this.f_96547_, f_98937_, this.f_96543_ / 2 - 153, 115, 10526880);
      this.f_98942_.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
      if (JigsawBlock.m_54250_(this.f_98938_.m_58900_()).m_122434_().m_122478_()) {
         m_93243_(p_98955_, this.f_96547_, f_98933_, this.f_96543_ / 2 - 153, 156, 16777215);
      }

      super.m_6305_(p_98955_, p_98956_, p_98957_, p_98958_);
   }
}