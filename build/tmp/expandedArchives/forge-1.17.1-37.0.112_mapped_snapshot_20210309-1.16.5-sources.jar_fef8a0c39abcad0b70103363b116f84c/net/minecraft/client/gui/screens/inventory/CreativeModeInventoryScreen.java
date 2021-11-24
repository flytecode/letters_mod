package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.HotbarManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.client.searchtree.SearchTree;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreativeModeInventoryScreen extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
   private static final ResourceLocation f_98504_ = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   private static final String f_169735_ = "textures/gui/container/creative_inventory/tab_";
   private static final String f_169736_ = "CustomCreativeLock";
   private static final int f_169737_ = 5;
   private static final int f_169738_ = 9;
   private static final int f_169739_ = 28;
   private static final int f_169740_ = 32;
   private static final int f_169741_ = 12;
   private static final int f_169742_ = 15;
   static final SimpleContainer f_98505_ = new SimpleContainer(45);
   private static final Component f_98506_ = new TranslatableComponent("inventory.binSlot");
   private static final int f_169743_ = 16777215;
   private static int f_98507_ = CreativeModeTab.f_40749_.m_40775_();
   private float f_98508_;
   private boolean f_98509_;
   private EditBox f_98510_;
   @Nullable
   private List<Slot> f_98511_;
   @Nullable
   private Slot f_98512_;
   private CreativeInventoryListener f_98513_;
   private boolean f_98514_;
   private static int tabPage = 0;
   private int maxPages = 0;
   private boolean f_98515_;
   private final Map<ResourceLocation, Tag<Item>> f_98516_ = Maps.newTreeMap();

   public CreativeModeInventoryScreen(Player p_98519_) {
      super(new CreativeModeInventoryScreen.ItemPickerMenu(p_98519_), p_98519_.m_150109_(), TextComponent.f_131282_);
      p_98519_.f_36096_ = this.f_97732_;
      this.f_96546_ = true;
      this.f_97727_ = 136;
      this.f_97726_ = 195;
   }

   public void m_181908_() {
      super.m_181908_();
      if (!this.f_96541_.f_91072_.m_105290_()) {
         this.f_96541_.m_91152_(new InventoryScreen(this.f_96541_.f_91074_));
      } else if (this.f_98510_ != null) {
         this.f_98510_.m_94120_();
      }

   }

   protected void m_6597_(@Nullable Slot p_98556_, int p_98557_, int p_98558_, ClickType p_98559_) {
      if (this.m_98553_(p_98556_)) {
         this.f_98510_.m_94201_();
         this.f_98510_.m_94208_(0);
      }

      boolean flag = p_98559_ == ClickType.QUICK_MOVE;
      p_98559_ = p_98557_ == -999 && p_98559_ == ClickType.PICKUP ? ClickType.THROW : p_98559_;
      if (p_98556_ == null && f_98507_ != CreativeModeTab.f_40761_.m_40775_() && p_98559_ != ClickType.QUICK_CRAFT) {
         if (!this.f_97732_.m_142621_().m_41619_() && this.f_98515_) {
            if (p_98558_ == 0) {
               this.f_96541_.f_91074_.m_36176_(this.f_97732_.m_142621_(), true);
               this.f_96541_.f_91072_.m_105239_(this.f_97732_.m_142621_());
               this.f_97732_.m_142503_(ItemStack.f_41583_);
            }

            if (p_98558_ == 1) {
               ItemStack itemstack5 = this.f_97732_.m_142621_().m_41620_(1);
               this.f_96541_.f_91074_.m_36176_(itemstack5, true);
               this.f_96541_.f_91072_.m_105239_(itemstack5);
            }
         }
      } else {
         if (p_98556_ != null && !p_98556_.m_8010_(this.f_96541_.f_91074_)) {
            return;
         }

         if (p_98556_ == this.f_98512_ && flag) {
            for(int j = 0; j < this.f_96541_.f_91074_.f_36095_.m_38927_().size(); ++j) {
               this.f_96541_.f_91072_.m_105241_(ItemStack.f_41583_, j);
            }
         } else if (f_98507_ == CreativeModeTab.f_40761_.m_40775_()) {
            if (p_98556_ == this.f_98512_) {
               this.f_97732_.m_142503_(ItemStack.f_41583_);
            } else if (p_98559_ == ClickType.THROW && p_98556_ != null && p_98556_.m_6657_()) {
               ItemStack itemstack = p_98556_.m_6201_(p_98558_ == 0 ? 1 : p_98556_.m_7993_().m_41741_());
               ItemStack itemstack1 = p_98556_.m_7993_();
               this.f_96541_.f_91074_.m_36176_(itemstack, true);
               this.f_96541_.f_91072_.m_105239_(itemstack);
               this.f_96541_.f_91072_.m_105241_(itemstack1, ((CreativeModeInventoryScreen.SlotWrapper)p_98556_).f_98655_.f_40219_);
            } else if (p_98559_ == ClickType.THROW && !this.f_97732_.m_142621_().m_41619_()) {
               this.f_96541_.f_91074_.m_36176_(this.f_97732_.m_142621_(), true);
               this.f_96541_.f_91072_.m_105239_(this.f_97732_.m_142621_());
               this.f_97732_.m_142503_(ItemStack.f_41583_);
            } else {
               this.f_96541_.f_91074_.f_36095_.m_150399_(p_98556_ == null ? p_98557_ : ((CreativeModeInventoryScreen.SlotWrapper)p_98556_).f_98655_.f_40219_, p_98558_, p_98559_, this.f_96541_.f_91074_);
               this.f_96541_.f_91074_.f_36095_.m_38946_();
            }
         } else if (p_98559_ != ClickType.QUICK_CRAFT && p_98556_.f_40218_ == f_98505_) {
            ItemStack itemstack4 = this.f_97732_.m_142621_();
            ItemStack itemstack7 = p_98556_.m_7993_();
            if (p_98559_ == ClickType.SWAP) {
               if (!itemstack7.m_41619_()) {
                  ItemStack itemstack10 = itemstack7.m_41777_();
                  itemstack10.m_41764_(itemstack10.m_41741_());
                  this.f_96541_.f_91074_.m_150109_().m_6836_(p_98558_, itemstack10);
                  this.f_96541_.f_91074_.f_36095_.m_38946_();
               }

               return;
            }

            if (p_98559_ == ClickType.CLONE) {
               if (this.f_97732_.m_142621_().m_41619_() && p_98556_.m_6657_()) {
                  ItemStack itemstack9 = p_98556_.m_7993_().m_41777_();
                  itemstack9.m_41764_(itemstack9.m_41741_());
                  this.f_97732_.m_142503_(itemstack9);
               }

               return;
            }

            if (p_98559_ == ClickType.THROW) {
               if (!itemstack7.m_41619_()) {
                  ItemStack itemstack8 = itemstack7.m_41777_();
                  itemstack8.m_41764_(p_98558_ == 0 ? 1 : itemstack8.m_41741_());
                  this.f_96541_.f_91074_.m_36176_(itemstack8, true);
                  this.f_96541_.f_91072_.m_105239_(itemstack8);
               }

               return;
            }

            if (!itemstack4.m_41619_() && !itemstack7.m_41619_() && itemstack4.m_41656_(itemstack7) && ItemStack.m_41658_(itemstack4, itemstack7)) {
               if (p_98558_ == 0) {
                  if (flag) {
                     itemstack4.m_41764_(itemstack4.m_41741_());
                  } else if (itemstack4.m_41613_() < itemstack4.m_41741_()) {
                     itemstack4.m_41769_(1);
                  }
               } else {
                  itemstack4.m_41774_(1);
               }
            } else if (!itemstack7.m_41619_() && itemstack4.m_41619_()) {
               this.f_97732_.m_142503_(itemstack7.m_41777_());
               itemstack4 = this.f_97732_.m_142621_();
               if (flag) {
                  itemstack4.m_41764_(itemstack4.m_41741_());
               }
            } else if (p_98558_ == 0) {
               this.f_97732_.m_142503_(ItemStack.f_41583_);
            } else {
               this.f_97732_.m_142621_().m_41774_(1);
            }
         } else if (this.f_97732_ != null) {
            ItemStack itemstack3 = p_98556_ == null ? ItemStack.f_41583_ : this.f_97732_.m_38853_(p_98556_.f_40219_).m_7993_();
            this.f_97732_.m_150399_(p_98556_ == null ? p_98557_ : p_98556_.f_40219_, p_98558_, p_98559_, this.f_96541_.f_91074_);
            if (AbstractContainerMenu.m_38947_(p_98558_) == 2) {
               for(int k = 0; k < 9; ++k) {
                  this.f_96541_.f_91072_.m_105241_(this.f_97732_.m_38853_(45 + k).m_7993_(), 36 + k);
               }
            } else if (p_98556_ != null) {
               ItemStack itemstack6 = this.f_97732_.m_38853_(p_98556_.f_40219_).m_7993_();
               this.f_96541_.f_91072_.m_105241_(itemstack6, p_98556_.f_40219_ - (this.f_97732_).f_38839_.size() + 9 + 36);
               int i = 45 + p_98558_;
               if (p_98559_ == ClickType.SWAP) {
                  this.f_96541_.f_91072_.m_105241_(itemstack3, i - (this.f_97732_).f_38839_.size() + 9 + 36);
               } else if (p_98559_ == ClickType.THROW && !itemstack3.m_41619_()) {
                  ItemStack itemstack2 = itemstack3.m_41777_();
                  itemstack2.m_41764_(p_98558_ == 0 ? 1 : itemstack2.m_41741_());
                  this.f_96541_.f_91074_.m_36176_(itemstack2, true);
                  this.f_96541_.f_91072_.m_105239_(itemstack2);
               }

               this.f_96541_.f_91074_.f_36095_.m_38946_();
            }
         }
      }

   }

   private boolean m_98553_(@Nullable Slot p_98554_) {
      return p_98554_ != null && p_98554_.f_40218_ == f_98505_;
   }

   protected void m_7824_() {
      int i = this.f_97735_;
      super.m_7824_();
      if (this.f_98510_ != null && this.f_97735_ != i) {
         this.f_98510_.m_94214_(this.f_97735_ + 82);
      }

   }

   protected void m_7856_() {
      if (this.f_96541_.f_91072_.m_105290_()) {
         super.m_7856_();
         int tabCount = CreativeModeTab.f_40748_.length;
         if (tabCount > 12) {
            m_142416_(new net.minecraft.client.gui.components.Button(f_97735_,              f_97736_ - 50, 20, 20, new TextComponent("<"), b -> tabPage = Math.max(tabPage - 1, 0       )));
            m_142416_(new net.minecraft.client.gui.components.Button(f_97735_ + f_97726_ - 20, f_97736_ - 50, 20, 20, new TextComponent(">"), b -> tabPage = Math.min(tabPage + 1, maxPages)));
            maxPages = (int) Math.ceil((tabCount - 12) / 10D);
         }
         this.f_96541_.f_91068_.m_90926_(true);
         this.f_98510_ = new EditBox(this.f_96547_, this.f_97735_ + 82, this.f_97736_ + 6, 80, 9, new TranslatableComponent("itemGroup.search"));
         this.f_98510_.m_94199_(50);
         this.f_98510_.m_94182_(false);
         this.f_98510_.m_94194_(false);
         this.f_98510_.m_94202_(16777215);
         this.m_7787_(this.f_98510_);
         int i = f_98507_;
         f_98507_ = -1;
         this.m_98560_(CreativeModeTab.f_40748_[i]);
         this.f_96541_.f_91074_.f_36095_.m_38943_(this.f_98513_);
         this.f_98513_ = new CreativeInventoryListener(this.f_96541_);
         this.f_96541_.f_91074_.f_36095_.m_38893_(this.f_98513_);
      } else {
         this.f_96541_.m_91152_(new InventoryScreen(this.f_96541_.f_91074_));
      }

   }

   public void m_6574_(Minecraft p_98595_, int p_98596_, int p_98597_) {
      String s = this.f_98510_.m_94155_();
      this.m_6575_(p_98595_, p_98596_, p_98597_);
      this.f_98510_.m_94144_(s);
      if (!this.f_98510_.m_94155_().isEmpty()) {
         this.m_98630_();
      }

   }

   public void m_7861_() {
      super.m_7861_();
      if (this.f_96541_.f_91074_ != null && this.f_96541_.f_91074_.m_150109_() != null) {
         this.f_96541_.f_91074_.f_36095_.m_38943_(this.f_98513_);
      }

      this.f_96541_.f_91068_.m_90926_(false);
   }

   public boolean m_5534_(char p_98521_, int p_98522_) {
      if (this.f_98514_) {
         return false;
      } else if (!CreativeModeTab.f_40748_[f_98507_].hasSearchBar()) {
         return false;
      } else {
         String s = this.f_98510_.m_94155_();
         if (this.f_98510_.m_5534_(p_98521_, p_98522_)) {
            if (!Objects.equals(s, this.f_98510_.m_94155_())) {
               this.m_98630_();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean m_7933_(int p_98547_, int p_98548_, int p_98549_) {
      this.f_98514_ = false;
      if (!CreativeModeTab.f_40748_[f_98507_].hasSearchBar()) {
         if (this.f_96541_.f_91066_.f_92098_.m_90832_(p_98547_, p_98548_)) {
            this.f_98514_ = true;
            this.m_98560_(CreativeModeTab.f_40754_);
            return true;
         } else {
            return super.m_7933_(p_98547_, p_98548_, p_98549_);
         }
      } else {
         boolean flag = !this.m_98553_(this.f_97734_) || this.f_97734_.m_6657_();
         boolean flag1 = InputConstants.m_84827_(p_98547_, p_98548_).m_84876_().isPresent();
         if (flag && flag1 && this.m_97805_(p_98547_, p_98548_)) {
            this.f_98514_ = true;
            return true;
         } else {
            String s = this.f_98510_.m_94155_();
            if (this.f_98510_.m_7933_(p_98547_, p_98548_, p_98549_)) {
               if (!Objects.equals(s, this.f_98510_.m_94155_())) {
                  this.m_98630_();
               }

               return true;
            } else {
               return this.f_98510_.m_93696_() && this.f_98510_.m_94213_() && p_98547_ != 256 ? true : super.m_7933_(p_98547_, p_98548_, p_98549_);
            }
         }
      }
   }

   public boolean m_7920_(int p_98612_, int p_98613_, int p_98614_) {
      this.f_98514_ = false;
      return super.m_7920_(p_98612_, p_98613_, p_98614_);
   }

   private void m_98630_() {
      (this.f_97732_).f_98639_.clear();
      this.f_98516_.clear();

      CreativeModeTab tab = CreativeModeTab.f_40748_[f_98507_];
      if (tab.hasSearchBar() && tab != CreativeModeTab.f_40754_) {
         tab.m_6151_(f_97732_.f_98639_);
         if (!this.f_98510_.m_94155_().isEmpty()) {
            //TODO: Make this a SearchTree not a manual search
            String search = this.f_98510_.m_94155_().toLowerCase(Locale.ROOT);
            java.util.Iterator<ItemStack> itr = f_97732_.f_98639_.iterator();
            while (itr.hasNext()) {
               ItemStack stack = itr.next();
               boolean matches = false;
               for (Component line : stack.m_41651_(this.f_96541_.f_91074_, this.f_96541_.f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL)) {
                  if (ChatFormatting.m_126649_(line.getString()).toLowerCase(Locale.ROOT).contains(search)) {
                     matches = true;
                     break;
                  }
               }
               if (!matches)
                  itr.remove();
            }
         }
         this.f_98508_ = 0.0F;
         f_97732_.m_98642_(0.0F);
         return;
      }

      String s = this.f_98510_.m_94155_();
      if (s.isEmpty()) {
         for(Item item : Registry.f_122827_) {
            item.m_6787_(CreativeModeTab.f_40754_, (this.f_97732_).f_98639_);
         }
      } else {
         SearchTree<ItemStack> searchtree;
         if (s.startsWith("#")) {
            s = s.substring(1);
            searchtree = this.f_96541_.m_91171_(SearchRegistry.f_119942_);
            this.m_98619_(s);
         } else {
            searchtree = this.f_96541_.m_91171_(SearchRegistry.f_119941_);
         }

         (this.f_97732_).f_98639_.addAll(searchtree.m_6293_(s.toLowerCase(Locale.ROOT)));
      }

      this.f_98508_ = 0.0F;
      this.f_97732_.m_98642_(0.0F);
   }

   private void m_98619_(String p_98620_) {
      int i = p_98620_.indexOf(58);
      Predicate<ResourceLocation> predicate;
      if (i == -1) {
         predicate = (p_98609_) -> {
            return p_98609_.m_135815_().contains(p_98620_);
         };
      } else {
         String s = p_98620_.substring(0, i).trim();
         String s1 = p_98620_.substring(i + 1).trim();
         predicate = (p_98606_) -> {
            return p_98606_.m_135827_().contains(s) && p_98606_.m_135815_().contains(s1);
         };
      }

      TagCollection<Item> tagcollection = ItemTags.m_13193_();
      tagcollection.m_13406_().stream().filter(predicate).forEach((p_98552_) -> {
         this.f_98516_.put(p_98552_, tagcollection.m_13404_(p_98552_));
      });
   }

   protected void m_7027_(PoseStack p_98616_, int p_98617_, int p_98618_) {
      CreativeModeTab creativemodetab = CreativeModeTab.f_40748_[f_98507_];
      if (creativemodetab != null && creativemodetab.m_40789_()) {
         RenderSystem.m_69461_();
         this.f_96547_.m_92889_(p_98616_, creativemodetab.m_40786_(), 8.0F, 6.0F, creativemodetab.getLabelColor());
      }

   }

   public boolean m_6375_(double p_98531_, double p_98532_, int p_98533_) {
      if (p_98533_ == 0) {
         double d0 = p_98531_ - (double)this.f_97735_;
         double d1 = p_98532_ - (double)this.f_97736_;

         for(CreativeModeTab creativemodetab : CreativeModeTab.f_40748_) {
            if (creativemodetab != null && this.m_98562_(creativemodetab, d0, d1)) {
               return true;
            }
         }

         if (f_98507_ != CreativeModeTab.f_40761_.m_40775_() && this.m_98523_(p_98531_, p_98532_)) {
            this.f_98509_ = this.m_98631_();
            return true;
         }
      }

      return super.m_6375_(p_98531_, p_98532_, p_98533_);
   }

   public boolean m_6348_(double p_98622_, double p_98623_, int p_98624_) {
      if (p_98624_ == 0) {
         double d0 = p_98622_ - (double)this.f_97735_;
         double d1 = p_98623_ - (double)this.f_97736_;
         this.f_98509_ = false;

         for(CreativeModeTab creativemodetab : CreativeModeTab.f_40748_) {
            if (creativemodetab != null && this.m_98562_(creativemodetab, d0, d1)) {
               this.m_98560_(creativemodetab);
               return true;
            }
         }
      }

      return super.m_6348_(p_98622_, p_98623_, p_98624_);
   }

   private boolean m_98631_() {
      if (CreativeModeTab.f_40748_[f_98507_] == null) return false;
      return f_98507_ != CreativeModeTab.f_40761_.m_40775_() && CreativeModeTab.f_40748_[f_98507_].m_40791_() && this.f_97732_.m_98654_();
   }

   private void m_98560_(CreativeModeTab p_98561_) {
      if (p_98561_ == null) return;
      int i = f_98507_;
      f_98507_ = p_98561_.m_40775_();
      slotColor = p_98561_.getSlotColor();
      this.f_97737_.clear();
      (this.f_97732_).f_98639_.clear();
      if (p_98561_ == CreativeModeTab.f_40760_) {
         HotbarManager hotbarmanager = this.f_96541_.m_91303_();

         for(int j = 0; j < 9; ++j) {
            Hotbar hotbar = hotbarmanager.m_90806_(j);
            if (hotbar.isEmpty()) {
               for(int k = 0; k < 9; ++k) {
                  if (k == j) {
                     ItemStack itemstack = new ItemStack(Items.f_42516_);
                     itemstack.m_41698_("CustomCreativeLock");
                     Component component = this.f_96541_.f_91066_.f_92056_[j].m_90863_();
                     Component component1 = this.f_96541_.f_91066_.f_92057_.m_90863_();
                     itemstack.m_41714_(new TranslatableComponent("inventory.hotbarInfo", component1, component));
                     (this.f_97732_).f_98639_.add(itemstack);
                  } else {
                     (this.f_97732_).f_98639_.add(ItemStack.f_41583_);
                  }
               }
            } else {
               (this.f_97732_).f_98639_.addAll(hotbar);
            }
         }
      } else if (p_98561_ != CreativeModeTab.f_40754_) {
         p_98561_.m_6151_((this.f_97732_).f_98639_);
      }

      if (p_98561_ == CreativeModeTab.f_40761_) {
         AbstractContainerMenu abstractcontainermenu = this.f_96541_.f_91074_.f_36095_;
         if (this.f_98511_ == null) {
            this.f_98511_ = ImmutableList.copyOf((this.f_97732_).f_38839_);
         }

         (this.f_97732_).f_38839_.clear();

         for(int l = 0; l < abstractcontainermenu.f_38839_.size(); ++l) {
            int i1;
            int j1;
            if (l >= 5 && l < 9) {
               int l1 = l - 5;
               int j2 = l1 / 2;
               int l2 = l1 % 2;
               i1 = 54 + j2 * 54;
               j1 = 6 + l2 * 27;
            } else if (l >= 0 && l < 5) {
               i1 = -2000;
               j1 = -2000;
            } else if (l == 45) {
               i1 = 35;
               j1 = 20;
            } else {
               int k1 = l - 9;
               int i2 = k1 % 9;
               int k2 = k1 / 9;
               i1 = 9 + i2 * 18;
               if (l >= 36) {
                  j1 = 112;
               } else {
                  j1 = 54 + k2 * 18;
               }
            }

            Slot slot = new CreativeModeInventoryScreen.SlotWrapper(abstractcontainermenu.f_38839_.get(l), l, i1, j1);
            (this.f_97732_).f_38839_.add(slot);
         }

         this.f_98512_ = new Slot(f_98505_, 0, 173, 112);
         (this.f_97732_).f_38839_.add(this.f_98512_);
      } else if (i == CreativeModeTab.f_40761_.m_40775_()) {
         (this.f_97732_).f_38839_.clear();
         (this.f_97732_).f_38839_.addAll(this.f_98511_);
         this.f_98511_ = null;
      }

      if (this.f_98510_ != null) {
         if (p_98561_.hasSearchBar()) {
            this.f_98510_.m_94194_(true);
            this.f_98510_.m_94190_(false);
            this.f_98510_.m_94178_(true);
            if (i != p_98561_.m_40775_()) {
               this.f_98510_.m_94144_("");
            }
            this.f_98510_.m_93674_(p_98561_.getSearchbarWidth());
            this.f_98510_.f_93620_ = this.f_97735_ + (82 /*default left*/ + 89 /*default width*/) - this.f_98510_.m_5711_();

            this.m_98630_();
         } else {
            this.f_98510_.m_94194_(false);
            this.f_98510_.m_94190_(true);
            this.f_98510_.m_94178_(false);
            this.f_98510_.m_94144_("");
         }
      }

      this.f_98508_ = 0.0F;
      this.f_97732_.m_98642_(0.0F);
   }

   public boolean m_6050_(double p_98527_, double p_98528_, double p_98529_) {
      if (!this.m_98631_()) {
         return false;
      } else {
         int i = ((this.f_97732_).f_98639_.size() + 9 - 1) / 9 - 5;
         this.f_98508_ = (float)((double)this.f_98508_ - p_98529_ / (double)i);
         this.f_98508_ = Mth.m_14036_(this.f_98508_, 0.0F, 1.0F);
         this.f_97732_.m_98642_(this.f_98508_);
         return true;
      }
   }

   protected boolean m_7467_(double p_98541_, double p_98542_, int p_98543_, int p_98544_, int p_98545_) {
      boolean flag = p_98541_ < (double)p_98543_ || p_98542_ < (double)p_98544_ || p_98541_ >= (double)(p_98543_ + this.f_97726_) || p_98542_ >= (double)(p_98544_ + this.f_97727_);
      this.f_98515_ = flag && !this.m_98562_(CreativeModeTab.f_40748_[f_98507_], p_98541_, p_98542_);
      return this.f_98515_;
   }

   protected boolean m_98523_(double p_98524_, double p_98525_) {
      int i = this.f_97735_;
      int j = this.f_97736_;
      int k = i + 175;
      int l = j + 18;
      int i1 = k + 14;
      int j1 = l + 112;
      return p_98524_ >= (double)k && p_98525_ >= (double)l && p_98524_ < (double)i1 && p_98525_ < (double)j1;
   }

   public boolean m_7979_(double p_98535_, double p_98536_, int p_98537_, double p_98538_, double p_98539_) {
      if (this.f_98509_) {
         int i = this.f_97736_ + 18;
         int j = i + 112;
         this.f_98508_ = ((float)p_98536_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
         this.f_98508_ = Mth.m_14036_(this.f_98508_, 0.0F, 1.0F);
         this.f_97732_.m_98642_(this.f_98508_);
         return true;
      } else {
         return super.m_7979_(p_98535_, p_98536_, p_98537_, p_98538_, p_98539_);
      }
   }

   public void m_6305_(PoseStack p_98577_, int p_98578_, int p_98579_, float p_98580_) {
      this.m_7333_(p_98577_);
      super.m_6305_(p_98577_, p_98578_, p_98579_, p_98580_);

      int start = tabPage * 10;
      int end = Math.min(CreativeModeTab.f_40748_.length, ((tabPage + 1) * 10) + 2);
      if (tabPage != 0) start += 2;
      boolean rendered = false;

      for (int x = start; x < end; x++) {
         CreativeModeTab creativemodetab = CreativeModeTab.f_40748_[x];
         if (creativemodetab != null && this.m_98584_(p_98577_, creativemodetab, p_98578_, p_98579_)) {
            rendered = true;
            break;
         }
      }
      if (!rendered && !this.m_98584_(p_98577_, CreativeModeTab.f_40754_, p_98578_, p_98579_))
         this.m_98584_(p_98577_, CreativeModeTab.f_40761_, p_98578_, p_98579_);

      if (this.f_98512_ != null && f_98507_ == CreativeModeTab.f_40761_.m_40775_() && this.m_6774_(this.f_98512_.f_40220_, this.f_98512_.f_40221_, 16, 16, (double)p_98578_, (double)p_98579_)) {
         this.m_96602_(p_98577_, f_98506_, p_98578_, p_98579_);
      }

      if (maxPages != 0) {
          Component page = new TextComponent(String.format("%d / %d", tabPage + 1, maxPages + 1));
          this.m_93250_(300);
          this.f_96542_.f_115093_ = 300.0F;
          f_96547_.m_92744_(p_98577_, page.m_7532_(), f_97735_ + (f_97726_ / 2) - (f_96547_.m_92852_(page) / 2), f_97736_ - 44, -1);
          this.m_93250_(0);
          this.f_96542_.f_115093_ = 0.0F;
      }

      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      this.m_7025_(p_98577_, p_98578_, p_98579_);
   }

   protected void m_6057_(PoseStack p_98590_, ItemStack p_98591_, int p_98592_, int p_98593_) {
      if (f_98507_ == CreativeModeTab.f_40754_.m_40775_()) {
         List<Component> list = p_98591_.m_41651_(this.f_96541_.f_91074_, this.f_96541_.f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
         List<Component> list1 = Lists.newArrayList(list);
         Item item = p_98591_.m_41720_();
         CreativeModeTab creativemodetab = item.m_41471_();
         if (creativemodetab == null && p_98591_.m_150930_(Items.f_42690_)) {
            Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(p_98591_);
            if (map.size() == 1) {
               Enchantment enchantment = map.keySet().iterator().next();

               for(CreativeModeTab creativemodetab1 : CreativeModeTab.f_40748_) {
                  if (creativemodetab1.m_40776_(enchantment.f_44672_)) {
                     creativemodetab = creativemodetab1;
                     break;
                  }
               }
            }
         }

         this.f_98516_.forEach((p_169747_, p_169748_) -> {
            if (p_98591_.m_150922_(p_169748_)) {
               list1.add(1, (new TextComponent("#" + p_169747_)).m_130940_(ChatFormatting.DARK_PURPLE));
            }

         });
         if (creativemodetab != null) {
            list1.add(1, creativemodetab.m_40786_().m_6881_().m_130940_(ChatFormatting.BLUE));
         }

         this.renderTooltip(p_98590_, list1, p_98591_.m_150921_(), p_98592_, p_98593_, p_98591_);
      } else {
         super.m_6057_(p_98590_, p_98591_, p_98592_, p_98593_);
      }

   }

   protected void m_7286_(PoseStack p_98572_, float p_98573_, int p_98574_, int p_98575_) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      CreativeModeTab creativemodetab = CreativeModeTab.f_40748_[f_98507_];

      int start = tabPage * 10;
      int end = Math.min(CreativeModeTab.f_40748_.length, ((tabPage + 1) * 10 + 2));
      if (tabPage != 0) start += 2;

      for (int idx = start; idx < end; idx++) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         CreativeModeTab creativemodetab1 = CreativeModeTab.f_40748_[idx];
         if (creativemodetab1 != null && creativemodetab1.m_40775_() != f_98507_) {
            RenderSystem.m_157456_(0, creativemodetab1.getTabsImage());
            this.m_98581_(p_98572_, creativemodetab1);
         }
      }

      RenderSystem.m_157427_(GameRenderer::m_172817_);
      if (tabPage != 0) {
         if (creativemodetab != CreativeModeTab.f_40754_) {
            RenderSystem.m_157456_(0, CreativeModeTab.f_40754_.getTabsImage());
            m_98581_(p_98572_, CreativeModeTab.f_40754_);
         }
         if (creativemodetab != CreativeModeTab.f_40761_) {
            RenderSystem.m_157456_(0, CreativeModeTab.f_40761_.getTabsImage());
            m_98581_(p_98572_, CreativeModeTab.f_40761_);
         }
      }

      RenderSystem.m_157456_(0, creativemodetab.getBackgroundImage());
      this.m_93228_(p_98572_, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
      this.f_98510_.m_6305_(p_98572_, p_98574_, p_98575_, p_98573_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      int i = this.f_97735_ + 175;
      int j = this.f_97736_ + 18;
      int k = j + 112;
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, creativemodetab.getTabsImage());
      if (creativemodetab.m_40791_()) {
         this.m_93228_(p_98572_, i, j + (int)((float)(k - j - 17) * this.f_98508_), 232 + (this.m_98631_() ? 0 : 12), 0, 12, 15);
      }

      if ((creativemodetab == null || creativemodetab.getTabPage() != tabPage) && (creativemodetab != CreativeModeTab.f_40754_ && creativemodetab != CreativeModeTab.f_40761_))
         return;

      this.m_98581_(p_98572_, creativemodetab);
      if (creativemodetab == CreativeModeTab.f_40761_) {
         InventoryScreen.m_98850_(this.f_97735_ + 88, this.f_97736_ + 45, 20, (float)(this.f_97735_ + 88 - p_98574_), (float)(this.f_97736_ + 45 - 30 - p_98575_), this.f_96541_.f_91074_);
      }

   }

   protected boolean m_98562_(CreativeModeTab p_98563_, double p_98564_, double p_98565_) {
      if (p_98563_.getTabPage() != tabPage && p_98563_ != CreativeModeTab.f_40754_ && p_98563_ != CreativeModeTab.f_40761_) return false;
      int i = p_98563_.m_40793_();
      int j = 28 * i;
      int k = 0;
      if (p_98563_.m_6563_()) {
         j = this.f_97726_ - 28 * (6 - i) + 2;
      } else if (i > 0) {
         j += i;
      }

      if (p_98563_.m_40794_()) {
         k = k - 32;
      } else {
         k = k + this.f_97727_;
      }

      return p_98564_ >= (double)j && p_98564_ <= (double)(j + 28) && p_98565_ >= (double)k && p_98565_ <= (double)(k + 32);
   }

   protected boolean m_98584_(PoseStack p_98585_, CreativeModeTab p_98586_, int p_98587_, int p_98588_) {
      int i = p_98586_.m_40793_();
      int j = 28 * i;
      int k = 0;
      if (p_98586_.m_6563_()) {
         j = this.f_97726_ - 28 * (6 - i) + 2;
      } else if (i > 0) {
         j += i;
      }

      if (p_98586_.m_40794_()) {
         k = k - 32;
      } else {
         k = k + this.f_97727_;
      }

      if (this.m_6774_(j + 3, k + 3, 23, 27, (double)p_98587_, (double)p_98588_)) {
         this.m_96602_(p_98585_, p_98586_.m_40786_(), p_98587_, p_98588_);
         return true;
      } else {
         return false;
      }
   }

   protected void m_98581_(PoseStack p_98582_, CreativeModeTab p_98583_) {
      boolean flag = p_98583_.m_40775_() == f_98507_;
      boolean flag1 = p_98583_.m_40794_();
      int i = p_98583_.m_40793_();
      int j = i * 28;
      int k = 0;
      int l = this.f_97735_ + 28 * i;
      int i1 = this.f_97736_;
      int j1 = 32;
      if (flag) {
         k += 32;
      }

      if (p_98583_.m_6563_()) {
         l = this.f_97735_ + this.f_97726_ - 28 * (6 - i);
      } else if (i > 0) {
         l += i;
      }

      if (flag1) {
         i1 = i1 - 28;
      } else {
         k += 64;
         i1 = i1 + (this.f_97727_ - 4);
      }

      RenderSystem.m_69478_(); //Forge: Make sure blend is enabled else tabs show a white border.
      this.m_93228_(p_98582_, l, i1, j, k, 28, 32);
      this.f_96542_.f_115093_ = 100.0F;
      l = l + 6;
      i1 = i1 + 8 + (flag1 ? 1 : -1);
      ItemStack itemstack = p_98583_.m_40787_();
      this.f_96542_.m_115203_(itemstack, l, i1);
      this.f_96542_.m_115169_(this.f_96547_, itemstack, l, i1);
      this.f_96542_.f_115093_ = 0.0F;
   }

   public int m_98628_() {
      return f_98507_;
   }

   public static void m_98598_(Minecraft p_98599_, int p_98600_, boolean p_98601_, boolean p_98602_) {
      LocalPlayer localplayer = p_98599_.f_91074_;
      HotbarManager hotbarmanager = p_98599_.m_91303_();
      Hotbar hotbar = hotbarmanager.m_90806_(p_98600_);
      if (p_98601_) {
         for(int i = 0; i < Inventory.m_36059_(); ++i) {
            ItemStack itemstack = hotbar.get(i).m_41777_();
            localplayer.m_150109_().m_6836_(i, itemstack);
            p_98599_.f_91072_.m_105241_(itemstack, 36 + i);
         }

         localplayer.f_36095_.m_38946_();
      } else if (p_98602_) {
         for(int j = 0; j < Inventory.m_36059_(); ++j) {
            hotbar.set(j, localplayer.m_150109_().m_8020_(j).m_41777_());
         }

         Component component = p_98599_.f_91066_.f_92056_[p_98600_].m_90863_();
         Component component1 = p_98599_.f_91066_.f_92058_.m_90863_();
         p_98599_.f_91065_.m_93063_(new TranslatableComponent("inventory.hotbarSaved", component1, component), false);
         hotbarmanager.m_90805_();
      }

   }

   @OnlyIn(Dist.CLIENT)
   static class CustomCreativeSlot extends Slot {
      public CustomCreativeSlot(Container p_98633_, int p_98634_, int p_98635_, int p_98636_) {
         super(p_98633_, p_98634_, p_98635_, p_98636_);
      }

      public boolean m_8010_(Player p_98638_) {
         if (super.m_8010_(p_98638_) && this.m_6657_()) {
            return this.m_7993_().m_41737_("CustomCreativeLock") == null;
         } else {
            return !this.m_6657_();
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class ItemPickerMenu extends AbstractContainerMenu {
      public final NonNullList<ItemStack> f_98639_ = NonNullList.m_122779_();
      private final AbstractContainerMenu f_169749_;

      public ItemPickerMenu(Player p_98641_) {
         super((MenuType<?>)null, 0);
         this.f_169749_ = p_98641_.f_36095_;
         Inventory inventory = p_98641_.m_150109_();

         for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 9; ++j) {
               this.m_38897_(new CreativeModeInventoryScreen.CustomCreativeSlot(CreativeModeInventoryScreen.f_98505_, i * 9 + j, 9 + j * 18, 18 + i * 18));
            }
         }

         for(int k = 0; k < 9; ++k) {
            this.m_38897_(new Slot(inventory, k, 9 + k * 18, 112));
         }

         this.m_98642_(0.0F);
      }

      public boolean m_6875_(Player p_98645_) {
         return true;
      }

      public void m_98642_(float p_98643_) {
         int i = (this.f_98639_.size() + 9 - 1) / 9 - 5;
         int j = (int)((double)(p_98643_ * (float)i) + 0.5D);
         if (j < 0) {
            j = 0;
         }

         for(int k = 0; k < 5; ++k) {
            for(int l = 0; l < 9; ++l) {
               int i1 = l + (k + j) * 9;
               if (i1 >= 0 && i1 < this.f_98639_.size()) {
                  CreativeModeInventoryScreen.f_98505_.m_6836_(l + k * 9, this.f_98639_.get(i1));
               } else {
                  CreativeModeInventoryScreen.f_98505_.m_6836_(l + k * 9, ItemStack.f_41583_);
               }
            }
         }

      }

      public boolean m_98654_() {
         return this.f_98639_.size() > 45;
      }

      public ItemStack m_7648_(Player p_98650_, int p_98651_) {
         if (p_98651_ >= this.f_38839_.size() - 9 && p_98651_ < this.f_38839_.size()) {
            Slot slot = this.f_38839_.get(p_98651_);
            if (slot != null && slot.m_6657_()) {
               slot.m_5852_(ItemStack.f_41583_);
            }
         }

         return ItemStack.f_41583_;
      }

      public boolean m_5882_(ItemStack p_98647_, Slot p_98648_) {
         return p_98648_.f_40218_ != CreativeModeInventoryScreen.f_98505_;
      }

      public boolean m_5622_(Slot p_98653_) {
         return p_98653_.f_40218_ != CreativeModeInventoryScreen.f_98505_;
      }

      public ItemStack m_142621_() {
         return this.f_169749_.m_142621_();
      }

      public void m_142503_(ItemStack p_169751_) {
         this.f_169749_.m_142503_(p_169751_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class SlotWrapper extends Slot {
      final Slot f_98655_;

      public SlotWrapper(Slot p_98657_, int p_98658_, int p_98659_, int p_98660_) {
         super(p_98657_.f_40218_, p_98658_, p_98659_, p_98660_);
         this.f_98655_ = p_98657_;
      }

      public void m_142406_(Player p_169754_, ItemStack p_169755_) {
         this.f_98655_.m_142406_(p_169754_, p_169755_);
      }

      public boolean m_5857_(ItemStack p_98670_) {
         return this.f_98655_.m_5857_(p_98670_);
      }

      public ItemStack m_7993_() {
         return this.f_98655_.m_7993_();
      }

      public boolean m_6657_() {
         return this.f_98655_.m_6657_();
      }

      public void m_5852_(ItemStack p_98679_) {
         this.f_98655_.m_5852_(p_98679_);
      }

      public void m_6654_() {
         this.f_98655_.m_6654_();
      }

      public int m_6641_() {
         return this.f_98655_.m_6641_();
      }

      public int m_5866_(ItemStack p_98675_) {
         return this.f_98655_.m_5866_(p_98675_);
      }

      @Nullable
      public Pair<ResourceLocation, ResourceLocation> m_7543_() {
         return this.f_98655_.m_7543_();
      }

      public ItemStack m_6201_(int p_98663_) {
         return this.f_98655_.m_6201_(p_98663_);
      }

      public boolean m_6659_() {
         return this.f_98655_.m_6659_();
      }

      public boolean m_8010_(Player p_98665_) {
         return this.f_98655_.m_8010_(p_98665_);
      }

      @Override
      public int getSlotIndex() {
         return this.f_98655_.getSlotIndex();
      }

      @Override
      public boolean isSameInventory(Slot other) {
         return this.f_98655_.isSameInventory(other);
      }

      @Override
      public Slot setBackground(ResourceLocation atlas, ResourceLocation sprite) {
         this.f_98655_.setBackground(atlas, sprite);
         return this;
      }
   }
}
