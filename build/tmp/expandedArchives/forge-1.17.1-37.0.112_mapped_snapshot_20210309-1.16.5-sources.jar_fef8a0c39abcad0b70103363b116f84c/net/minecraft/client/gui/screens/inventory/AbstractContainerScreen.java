package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractContainerScreen<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {
   public static final ResourceLocation f_97725_ = new ResourceLocation("textures/gui/container/inventory.png");
   private static final float f_169605_ = 100.0F;
   private static final int f_169600_ = 500;
   private static final int f_169601_ = 250;
   public static final int f_169603_ = 100;
   private static final int f_169602_ = 200;
   protected int f_97726_ = 176;
   protected int f_97727_ = 166;
   protected int f_97728_;
   protected int f_97729_;
   protected int f_97730_;
   protected int f_97731_;
   protected final T f_97732_;
   protected final Component f_169604_;
   @Nullable
   protected Slot f_97734_;
   @Nullable
   private Slot f_97706_;
   @Nullable
   private Slot f_97707_;
   @Nullable
   private Slot f_97708_;
   @Nullable
   private Slot f_97709_;
   protected int f_97735_;
   protected int f_97736_;
   private boolean f_97710_;
   private ItemStack f_97711_ = ItemStack.f_41583_;
   private int f_97712_;
   private int f_97713_;
   private long f_97714_;
   private ItemStack f_97715_ = ItemStack.f_41583_;
   private long f_97716_;
   protected final Set<Slot> f_97737_ = Sets.newHashSet();
   protected boolean f_97738_;
   private int f_97717_;
   private int f_97718_;
   private boolean f_97719_;
   private int f_97720_;
   private long f_97721_;
   private int f_97722_;
   private boolean f_97723_;
   private ItemStack f_97724_ = ItemStack.f_41583_;

   public AbstractContainerScreen(T p_97741_, Inventory p_97742_, Component p_97743_) {
      super(p_97743_);
      this.f_97732_ = p_97741_;
      this.f_169604_ = p_97742_.m_5446_();
      this.f_97719_ = true;
      this.f_97728_ = 8;
      this.f_97729_ = 6;
      this.f_97730_ = 8;
      this.f_97731_ = this.f_97727_ - 94;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
      this.f_97736_ = (this.f_96544_ - this.f_97727_) / 2;
   }

   public void m_6305_(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_7286_(p_97795_, p_97798_, p_97796_, p_97797_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, p_97795_, p_97796_, p_97797_));
      RenderSystem.m_69465_();
      super.m_6305_(p_97795_, p_97796_, p_97797_, p_97798_);
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      posestack.m_85837_((double)i, (double)j, 0.0D);
      RenderSystem.m_157182_();
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      this.f_97734_ = null;
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);

      for(int k = 0; k < this.f_97732_.f_38839_.size(); ++k) {
         Slot slot = this.f_97732_.f_38839_.get(k);
         if (slot.m_6659_()) {
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            this.m_97799_(p_97795_, slot);
         }

         if (this.m_97774_(slot, (double)p_97796_, (double)p_97797_) && slot.m_6659_()) {
            this.f_97734_ = slot;
            int l = slot.f_40220_;
            int i1 = slot.f_40221_;
            renderSlotHighlight(p_97795_, l, i1, this.m_93252_(), this.getSlotColor(k));
         }
      }

      this.m_7027_(p_97795_, p_97796_, p_97797_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawForeground(this, p_97795_, p_97796_, p_97797_));
      ItemStack itemstack = this.f_97711_.m_41619_() ? this.f_97732_.m_142621_() : this.f_97711_;
      if (!itemstack.m_41619_()) {
         int l1 = 8;
         int i2 = this.f_97711_.m_41619_() ? 8 : 16;
         String s = null;
         if (!this.f_97711_.m_41619_() && this.f_97710_) {
            itemstack = itemstack.m_41777_();
            itemstack.m_41764_(Mth.m_14167_((float)itemstack.m_41613_() / 2.0F));
         } else if (this.f_97738_ && this.f_97737_.size() > 1) {
            itemstack = itemstack.m_41777_();
            itemstack.m_41764_(this.f_97720_);
            if (itemstack.m_41619_()) {
               s = ChatFormatting.YELLOW + "0";
            }
         }

         this.m_97782_(itemstack, p_97796_ - i - 8, p_97797_ - j - i2, s);
      }

      if (!this.f_97715_.m_41619_()) {
         float f = (float)(Util.m_137550_() - this.f_97714_) / 100.0F;
         if (f >= 1.0F) {
            f = 1.0F;
            this.f_97715_ = ItemStack.f_41583_;
         }

         int j2 = this.f_97707_.f_40220_ - this.f_97712_;
         int k2 = this.f_97707_.f_40221_ - this.f_97713_;
         int j1 = this.f_97712_ + (int)((float)j2 * f);
         int k1 = this.f_97713_ + (int)((float)k2 * f);
         this.m_97782_(this.f_97715_, j1, k1, (String)null);
      }

      posestack.m_85849_();
      RenderSystem.m_157182_();
      RenderSystem.m_69482_();
   }

   public static void m_169606_(PoseStack p_169607_, int p_169608_, int p_169609_, int p_169610_) {
       renderSlotHighlight(p_169607_, p_169608_, p_169609_, p_169610_, -2130706433);
   }

   public static void renderSlotHighlight(PoseStack p_169607_, int p_169608_, int p_169609_, int p_169610_, int slotColor) {
      RenderSystem.m_69465_();
      RenderSystem.m_69444_(true, true, true, false);
      m_168740_(p_169607_, p_169608_, p_169609_, p_169608_ + 16, p_169609_ + 16, slotColor, slotColor, p_169610_);
      RenderSystem.m_69444_(true, true, true, true);
      RenderSystem.m_69482_();
   }

   protected void m_7025_(PoseStack p_97791_, int p_97792_, int p_97793_) {
      if (this.f_97732_.m_142621_().m_41619_() && this.f_97734_ != null && this.f_97734_.m_6657_()) {
         this.m_6057_(p_97791_, this.f_97734_.m_7993_(), p_97792_, p_97793_);
      }

   }

   private void m_97782_(ItemStack p_97783_, int p_97784_, int p_97785_, String p_97786_) {
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85837_(0.0D, 0.0D, 32.0D);
      RenderSystem.m_157182_();
      this.m_93250_(200);
      this.f_96542_.f_115093_ = 200.0F;
      net.minecraft.client.gui.Font font = net.minecraftforge.client.RenderProperties.get(p_97783_).getFont(p_97783_);
      if (font == null) font = this.f_96547_;
      this.f_96542_.m_115203_(p_97783_, p_97784_, p_97785_);
      this.f_96542_.m_115174_(font, p_97783_, p_97784_, p_97785_ - (this.f_97711_.m_41619_() ? 0 : 8), p_97786_);
      this.m_93250_(0);
      this.f_96542_.f_115093_ = 0.0F;
   }

   protected void m_7027_(PoseStack p_97808_, int p_97809_, int p_97810_) {
      this.f_96547_.m_92889_(p_97808_, this.f_96539_, (float)this.f_97728_, (float)this.f_97729_, 4210752);
      this.f_96547_.m_92889_(p_97808_, this.f_169604_, (float)this.f_97730_, (float)this.f_97731_, 4210752);
   }

   protected abstract void m_7286_(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_);

   private void m_97799_(PoseStack p_97800_, Slot p_97801_) {
      int i = p_97801_.f_40220_;
      int j = p_97801_.f_40221_;
      ItemStack itemstack = p_97801_.m_7993_();
      boolean flag = false;
      boolean flag1 = p_97801_ == this.f_97706_ && !this.f_97711_.m_41619_() && !this.f_97710_;
      ItemStack itemstack1 = this.f_97732_.m_142621_();
      String s = null;
      if (p_97801_ == this.f_97706_ && !this.f_97711_.m_41619_() && this.f_97710_ && !itemstack.m_41619_()) {
         itemstack = itemstack.m_41777_();
         itemstack.m_41764_(itemstack.m_41613_() / 2);
      } else if (this.f_97738_ && this.f_97737_.contains(p_97801_) && !itemstack1.m_41619_()) {
         if (this.f_97737_.size() == 1) {
            return;
         }

         if (AbstractContainerMenu.m_38899_(p_97801_, itemstack1, true) && this.f_97732_.m_5622_(p_97801_)) {
            itemstack = itemstack1.m_41777_();
            flag = true;
            AbstractContainerMenu.m_38922_(this.f_97737_, this.f_97717_, itemstack, p_97801_.m_7993_().m_41619_() ? 0 : p_97801_.m_7993_().m_41613_());
            int k = Math.min(itemstack.m_41741_(), p_97801_.m_5866_(itemstack));
            if (itemstack.m_41613_() > k) {
               s = ChatFormatting.YELLOW.toString() + k;
               itemstack.m_41764_(k);
            }
         } else {
            this.f_97737_.remove(p_97801_);
            this.m_97818_();
         }
      }

      this.m_93250_(100);
      this.f_96542_.f_115093_ = 100.0F;
      if (itemstack.m_41619_() && p_97801_.m_6659_()) {
         Pair<ResourceLocation, ResourceLocation> pair = p_97801_.m_7543_();
         if (pair != null) {
            TextureAtlasSprite textureatlassprite = this.f_96541_.m_91258_(pair.getFirst()).apply(pair.getSecond());
            RenderSystem.m_157456_(0, textureatlassprite.m_118414_().m_118330_());
            m_93200_(p_97800_, i, j, this.m_93252_(), 16, 16, textureatlassprite);
            flag1 = true;
         }
      }

      if (!flag1) {
         if (flag) {
            m_93172_(p_97800_, i, j, i + 16, j + 16, -2130706433);
         }

         RenderSystem.m_69482_();
         this.f_96542_.m_174229_(this.f_96541_.f_91074_, itemstack, i, j, p_97801_.f_40220_ + p_97801_.f_40221_ * this.f_97726_);
         this.f_96542_.m_115174_(this.f_96547_, itemstack, i, j, s);
      }

      this.f_96542_.f_115093_ = 0.0F;
      this.m_93250_(0);
   }

   private void m_97818_() {
      ItemStack itemstack = this.f_97732_.m_142621_();
      if (!itemstack.m_41619_() && this.f_97738_) {
         if (this.f_97717_ == 2) {
            this.f_97720_ = itemstack.m_41741_();
         } else {
            this.f_97720_ = itemstack.m_41613_();

            for(Slot slot : this.f_97737_) {
               ItemStack itemstack1 = itemstack.m_41777_();
               ItemStack itemstack2 = slot.m_7993_();
               int i = itemstack2.m_41619_() ? 0 : itemstack2.m_41613_();
               AbstractContainerMenu.m_38922_(this.f_97737_, this.f_97717_, itemstack1, i);
               int j = Math.min(itemstack1.m_41741_(), slot.m_5866_(itemstack1));
               if (itemstack1.m_41613_() > j) {
                  itemstack1.m_41764_(j);
               }

               this.f_97720_ -= itemstack1.m_41613_() - i;
            }

         }
      }
   }

   @Nullable
   private Slot m_97744_(double p_97745_, double p_97746_) {
      for(int i = 0; i < this.f_97732_.f_38839_.size(); ++i) {
         Slot slot = this.f_97732_.f_38839_.get(i);
         if (this.m_97774_(slot, p_97745_, p_97746_) && slot.m_6659_()) {
            return slot;
         }
      }

      return null;
   }

   public boolean m_6375_(double p_97748_, double p_97749_, int p_97750_) {
      if (super.m_6375_(p_97748_, p_97749_, p_97750_)) {
         return true;
      } else {
         InputConstants.Key mouseKey = InputConstants.Type.MOUSE.m_84895_(p_97750_);
         boolean flag = this.f_96541_.f_91066_.f_92097_.isActiveAndMatches(mouseKey);
         Slot slot = this.m_97744_(p_97748_, p_97749_);
         long i = Util.m_137550_();
         this.f_97723_ = this.f_97709_ == slot && i - this.f_97721_ < 250L && this.f_97722_ == p_97750_;
         this.f_97719_ = false;
         if (p_97750_ != 0 && p_97750_ != 1 && !flag) {
            this.m_97762_(p_97750_);
         } else {
            int j = this.f_97735_;
            int k = this.f_97736_;
            boolean flag1 = this.m_7467_(p_97748_, p_97749_, j, k, p_97750_);
            if (slot != null) flag1 = false; // Forge, prevent dropping of items through slots outside of GUI boundaries
            int l = -1;
            if (slot != null) {
               l = slot.f_40219_;
            }

            if (flag1) {
               l = -999;
            }

            if (this.f_96541_.f_91066_.f_92051_ && flag1 && this.f_97732_.m_142621_().m_41619_()) {
               this.f_96541_.m_91152_((Screen)null);
               return true;
            }

            if (l != -1) {
               if (this.f_96541_.f_91066_.f_92051_) {
                  if (slot != null && slot.m_6657_()) {
                     this.f_97706_ = slot;
                     this.f_97711_ = ItemStack.f_41583_;
                     this.f_97710_ = p_97750_ == 1;
                  } else {
                     this.f_97706_ = null;
                  }
               } else if (!this.f_97738_) {
                  if (this.f_97732_.m_142621_().m_41619_()) {
                     if (this.f_96541_.f_91066_.f_92097_.isActiveAndMatches(mouseKey)) {
                        this.m_6597_(slot, l, p_97750_, ClickType.CLONE);
                     } else {
                        boolean flag2 = l != -999 && (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 340) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 344));
                        ClickType clicktype = ClickType.PICKUP;
                        if (flag2) {
                           this.f_97724_ = slot != null && slot.m_6657_() ? slot.m_7993_().m_41777_() : ItemStack.f_41583_;
                           clicktype = ClickType.QUICK_MOVE;
                        } else if (l == -999) {
                           clicktype = ClickType.THROW;
                        }

                        this.m_6597_(slot, l, p_97750_, clicktype);
                     }

                     this.f_97719_ = true;
                  } else {
                     this.f_97738_ = true;
                     this.f_97718_ = p_97750_;
                     this.f_97737_.clear();
                     if (p_97750_ == 0) {
                        this.f_97717_ = 0;
                     } else if (p_97750_ == 1) {
                        this.f_97717_ = 1;
                     } else if (this.f_96541_.f_91066_.f_92097_.isActiveAndMatches(mouseKey)) {
                        this.f_97717_ = 2;
                     }
                  }
               }
            }
         }

         this.f_97709_ = slot;
         this.f_97721_ = i;
         this.f_97722_ = p_97750_;
         return true;
      }
   }

   private void m_97762_(int p_97763_) {
      if (this.f_97734_ != null && this.f_97732_.m_142621_().m_41619_()) {
         if (this.f_96541_.f_91066_.f_92093_.m_90830_(p_97763_)) {
            this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, 40, ClickType.SWAP);
            return;
         }

         for(int i = 0; i < 9; ++i) {
            if (this.f_96541_.f_91066_.f_92056_[i].m_90830_(p_97763_)) {
               this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, i, ClickType.SWAP);
            }
         }
      }

   }

   protected boolean m_7467_(double p_97757_, double p_97758_, int p_97759_, int p_97760_, int p_97761_) {
      return p_97757_ < (double)p_97759_ || p_97758_ < (double)p_97760_ || p_97757_ >= (double)(p_97759_ + this.f_97726_) || p_97758_ >= (double)(p_97760_ + this.f_97727_);
   }

   public boolean m_7979_(double p_97752_, double p_97753_, int p_97754_, double p_97755_, double p_97756_) {
      Slot slot = this.m_97744_(p_97752_, p_97753_);
      ItemStack itemstack = this.f_97732_.m_142621_();
      if (this.f_97706_ != null && this.f_96541_.f_91066_.f_92051_) {
         if (p_97754_ == 0 || p_97754_ == 1) {
            if (this.f_97711_.m_41619_()) {
               if (slot != this.f_97706_ && !this.f_97706_.m_7993_().m_41619_()) {
                  this.f_97711_ = this.f_97706_.m_7993_().m_41777_();
               }
            } else if (this.f_97711_.m_41613_() > 1 && slot != null && AbstractContainerMenu.m_38899_(slot, this.f_97711_, false)) {
               long i = Util.m_137550_();
               if (this.f_97708_ == slot) {
                  if (i - this.f_97716_ > 500L) {
                     this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, 0, ClickType.PICKUP);
                     this.m_6597_(slot, slot.f_40219_, 1, ClickType.PICKUP);
                     this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, 0, ClickType.PICKUP);
                     this.f_97716_ = i + 750L;
                     this.f_97711_.m_41774_(1);
                  }
               } else {
                  this.f_97708_ = slot;
                  this.f_97716_ = i;
               }
            }
         }
      } else if (this.f_97738_ && slot != null && !itemstack.m_41619_() && (itemstack.m_41613_() > this.f_97737_.size() || this.f_97717_ == 2) && AbstractContainerMenu.m_38899_(slot, itemstack, true) && slot.m_5857_(itemstack) && this.f_97732_.m_5622_(slot)) {
         this.f_97737_.add(slot);
         this.m_97818_();
      }

      return true;
   }

   public boolean m_6348_(double p_97812_, double p_97813_, int p_97814_) {
      super.m_6348_(p_97812_, p_97813_, p_97814_); //Forge, Call parent to release buttons
      Slot slot = this.m_97744_(p_97812_, p_97813_);
      int i = this.f_97735_;
      int j = this.f_97736_;
      boolean flag = this.m_7467_(p_97812_, p_97813_, i, j, p_97814_);
      if (slot != null) flag = false; // Forge, prevent dropping of items through slots outside of GUI boundaries
      InputConstants.Key mouseKey = InputConstants.Type.MOUSE.m_84895_(p_97814_);
      int k = -1;
      if (slot != null) {
         k = slot.f_40219_;
      }

      if (flag) {
         k = -999;
      }

      if (this.f_97723_ && slot != null && p_97814_ == 0 && this.f_97732_.m_5882_(ItemStack.f_41583_, slot)) {
         if (m_96638_()) {
            if (!this.f_97724_.m_41619_()) {
               for(Slot slot2 : this.f_97732_.f_38839_) {
                  if (slot2 != null && slot2.m_8010_(this.f_96541_.f_91074_) && slot2.m_6657_() && slot2.isSameInventory(slot) && AbstractContainerMenu.m_38899_(slot2, this.f_97724_, true)) {
                     this.m_6597_(slot2, slot2.f_40219_, p_97814_, ClickType.QUICK_MOVE);
                  }
               }
            }
         } else {
            this.m_6597_(slot, k, p_97814_, ClickType.PICKUP_ALL);
         }

         this.f_97723_ = false;
         this.f_97721_ = 0L;
      } else {
         if (this.f_97738_ && this.f_97718_ != p_97814_) {
            this.f_97738_ = false;
            this.f_97737_.clear();
            this.f_97719_ = true;
            return true;
         }

         if (this.f_97719_) {
            this.f_97719_ = false;
            return true;
         }

         if (this.f_97706_ != null && this.f_96541_.f_91066_.f_92051_) {
            if (p_97814_ == 0 || p_97814_ == 1) {
               if (this.f_97711_.m_41619_() && slot != this.f_97706_) {
                  this.f_97711_ = this.f_97706_.m_7993_();
               }

               boolean flag2 = AbstractContainerMenu.m_38899_(slot, this.f_97711_, false);
               if (k != -1 && !this.f_97711_.m_41619_() && flag2) {
                  this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, p_97814_, ClickType.PICKUP);
                  this.m_6597_(slot, k, 0, ClickType.PICKUP);
                  if (this.f_97732_.m_142621_().m_41619_()) {
                     this.f_97715_ = ItemStack.f_41583_;
                  } else {
                     this.m_6597_(this.f_97706_, this.f_97706_.f_40219_, p_97814_, ClickType.PICKUP);
                     this.f_97712_ = Mth.m_14107_(p_97812_ - (double)i);
                     this.f_97713_ = Mth.m_14107_(p_97813_ - (double)j);
                     this.f_97707_ = this.f_97706_;
                     this.f_97715_ = this.f_97711_;
                     this.f_97714_ = Util.m_137550_();
                  }
               } else if (!this.f_97711_.m_41619_()) {
                  this.f_97712_ = Mth.m_14107_(p_97812_ - (double)i);
                  this.f_97713_ = Mth.m_14107_(p_97813_ - (double)j);
                  this.f_97707_ = this.f_97706_;
                  this.f_97715_ = this.f_97711_;
                  this.f_97714_ = Util.m_137550_();
               }

               this.f_97711_ = ItemStack.f_41583_;
               this.f_97706_ = null;
            }
         } else if (this.f_97738_ && !this.f_97737_.isEmpty()) {
            this.m_6597_((Slot)null, -999, AbstractContainerMenu.m_38930_(0, this.f_97717_), ClickType.QUICK_CRAFT);

            for(Slot slot1 : this.f_97737_) {
               this.m_6597_(slot1, slot1.f_40219_, AbstractContainerMenu.m_38930_(1, this.f_97717_), ClickType.QUICK_CRAFT);
            }

            this.m_6597_((Slot)null, -999, AbstractContainerMenu.m_38930_(2, this.f_97717_), ClickType.QUICK_CRAFT);
         } else if (!this.f_97732_.m_142621_().m_41619_()) {
            if (this.f_96541_.f_91066_.f_92097_.isActiveAndMatches(mouseKey)) {
               this.m_6597_(slot, k, p_97814_, ClickType.CLONE);
            } else {
               boolean flag1 = k != -999 && (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 340) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 344));
               if (flag1) {
                  this.f_97724_ = slot != null && slot.m_6657_() ? slot.m_7993_().m_41777_() : ItemStack.f_41583_;
               }

               this.m_6597_(slot, k, p_97814_, flag1 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
            }
         }
      }

      if (this.f_97732_.m_142621_().m_41619_()) {
         this.f_97721_ = 0L;
      }

      this.f_97738_ = false;
      return true;
   }

   private boolean m_97774_(Slot p_97775_, double p_97776_, double p_97777_) {
      return this.m_6774_(p_97775_.f_40220_, p_97775_.f_40221_, 16, 16, p_97776_, p_97777_);
   }

   protected boolean m_6774_(int p_97768_, int p_97769_, int p_97770_, int p_97771_, double p_97772_, double p_97773_) {
      int i = this.f_97735_;
      int j = this.f_97736_;
      p_97772_ = p_97772_ - (double)i;
      p_97773_ = p_97773_ - (double)j;
      return p_97772_ >= (double)(p_97768_ - 1) && p_97772_ < (double)(p_97768_ + p_97770_ + 1) && p_97773_ >= (double)(p_97769_ - 1) && p_97773_ < (double)(p_97769_ + p_97771_ + 1);
   }

   protected void m_6597_(Slot p_97778_, int p_97779_, int p_97780_, ClickType p_97781_) {
      if (p_97778_ != null) {
         p_97779_ = p_97778_.f_40219_;
      }

      this.f_96541_.f_91072_.m_171799_(this.f_97732_.f_38840_, p_97779_, p_97780_, p_97781_, this.f_96541_.f_91074_);
   }

   public boolean m_7933_(int p_97765_, int p_97766_, int p_97767_) {
      InputConstants.Key mouseKey = InputConstants.m_84827_(p_97765_, p_97766_);
      if (super.m_7933_(p_97765_, p_97766_, p_97767_)) {
         return true;
      } else if (this.f_96541_.f_91066_.f_92092_.isActiveAndMatches(mouseKey)) {
         this.m_7379_();
         return true;
      } else {
         boolean handled = this.m_97805_(p_97765_, p_97766_);// Forge MC-146650: Needs to return true when the key is handled
         if (this.f_97734_ != null && this.f_97734_.m_6657_()) {
            if (this.f_96541_.f_91066_.f_92097_.isActiveAndMatches(mouseKey)) {
               this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, 0, ClickType.CLONE);
               handled = true;
            } else if (this.f_96541_.f_91066_.f_92094_.isActiveAndMatches(mouseKey)) {
               this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, m_96637_() ? 1 : 0, ClickType.THROW);
               handled = true;
            }
         } else if (this.f_96541_.f_91066_.f_92094_.isActiveAndMatches(mouseKey)) {
             handled = true; // Forge MC-146650: Emulate MC bug, so we don't drop from hotbar when pressing drop without hovering over a item.
         }

         return handled;
      }
   }

   protected boolean m_97805_(int p_97806_, int p_97807_) {
      if (this.f_97732_.m_142621_().m_41619_() && this.f_97734_ != null) {
         if (this.f_96541_.f_91066_.f_92093_.isActiveAndMatches(InputConstants.m_84827_(p_97806_, p_97807_))) {
            this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, 40, ClickType.SWAP);
            return true;
         }

         for(int i = 0; i < 9; ++i) {
            if (this.f_96541_.f_91066_.f_92056_[i].isActiveAndMatches(InputConstants.m_84827_(p_97806_, p_97807_))) {
               this.m_6597_(this.f_97734_, this.f_97734_.f_40219_, i, ClickType.SWAP);
               return true;
            }
         }
      }

      return false;
   }

   public void m_7861_() {
      if (this.f_96541_.f_91074_ != null) {
         this.f_97732_.m_6877_(this.f_96541_.f_91074_);
      }
   }

   public boolean m_7043_() {
      return false;
   }

   public final void m_96624_() {
      super.m_96624_();
      if (this.f_96541_.f_91074_.m_6084_() && !this.f_96541_.f_91074_.m_146910_()) {
         this.m_181908_();
      } else {
         this.f_96541_.f_91074_.m_6915_();
      }

   }

   protected void m_181908_() {
   }

   public T m_6262_() {
      return this.f_97732_;
   }

   @javax.annotation.Nullable
   public Slot getSlotUnderMouse() { return this.f_97734_; }
   public int getGuiLeft() { return f_97735_; }
   public int getGuiTop() { return f_97736_; }
   public int getXSize() { return f_97726_; }
   public int getYSize() { return f_97727_; }

   protected int slotColor = -2130706433;
   public int getSlotColor(int index) {
      return slotColor;
   }

   public void m_7379_() {
      this.f_96541_.f_91074_.m_6915_();
      super.m_7379_();
   }
}
