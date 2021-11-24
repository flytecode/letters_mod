package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Blocks;

public abstract class CreativeModeTab {
   public static CreativeModeTab[] f_40748_ = new CreativeModeTab[12];
   public static final CreativeModeTab f_40749_ = (new CreativeModeTab(0, "buildingBlocks") {
      public ItemStack m_6976_() {
         return new ItemStack(Blocks.f_50076_);
      }
   }).m_40784_("building_blocks");
   public static final CreativeModeTab f_40750_ = new CreativeModeTab(1, "decorations") {
      public ItemStack m_6976_() {
         return new ItemStack(Blocks.f_50358_);
      }
   };
   public static final CreativeModeTab f_40751_ = new CreativeModeTab(2, "redstone") {
      public ItemStack m_6976_() {
         return new ItemStack(Items.f_42451_);
      }
   };
   public static final CreativeModeTab f_40752_ = new CreativeModeTab(3, "transportation") {
      public ItemStack m_6976_() {
         return new ItemStack(Blocks.f_50030_);
      }
   };
   public static final CreativeModeTab f_40753_ = new CreativeModeTab(6, "misc") {
      public ItemStack m_6976_() {
         return new ItemStack(Items.f_42448_);
      }
   };
   public static final CreativeModeTab f_40754_ = (new CreativeModeTab(5, "search") {
      public ItemStack m_6976_() {
         return new ItemStack(Items.f_42522_);
      }
   }).m_40779_("item_search.png");
   public static final CreativeModeTab f_40755_ = new CreativeModeTab(7, "food") {
      public ItemStack m_6976_() {
         return new ItemStack(Items.f_42410_);
      }
   };
   public static final CreativeModeTab f_40756_ = (new CreativeModeTab(8, "tools") {
      public ItemStack m_6976_() {
         return new ItemStack(Items.f_42386_);
      }
   }).m_40781_(new EnchantmentCategory[]{EnchantmentCategory.VANISHABLE, EnchantmentCategory.DIGGER, EnchantmentCategory.FISHING_ROD, EnchantmentCategory.BREAKABLE});
   public static final CreativeModeTab f_40757_ = (new CreativeModeTab(9, "combat") {
      public ItemStack m_6976_() {
         return new ItemStack(Items.f_42430_);
      }
   }).m_40781_(new EnchantmentCategory[]{EnchantmentCategory.VANISHABLE, EnchantmentCategory.ARMOR, EnchantmentCategory.ARMOR_FEET, EnchantmentCategory.ARMOR_HEAD, EnchantmentCategory.ARMOR_LEGS, EnchantmentCategory.ARMOR_CHEST, EnchantmentCategory.BOW, EnchantmentCategory.WEAPON, EnchantmentCategory.WEARABLE, EnchantmentCategory.BREAKABLE, EnchantmentCategory.TRIDENT, EnchantmentCategory.CROSSBOW});
   public static final CreativeModeTab f_40758_ = new CreativeModeTab(10, "brewing") {
      public ItemStack m_6976_() {
         return PotionUtils.m_43549_(new ItemStack(Items.f_42589_), Potions.f_43599_);
      }
   };
   public static final CreativeModeTab f_40759_ = f_40753_;
   public static final CreativeModeTab f_40760_ = new CreativeModeTab(4, "hotbar") {
      public ItemStack m_6976_() {
         return new ItemStack(Blocks.f_50078_);
      }

      public void m_6151_(NonNullList<ItemStack> p_40820_) {
         throw new RuntimeException("Implement exception client-side.");
      }

      public boolean m_6563_() {
         return true;
      }
   };
   public static final CreativeModeTab f_40761_ = (new CreativeModeTab(11, "inventory") {
      public ItemStack m_6976_() {
         return new ItemStack(Blocks.f_50087_);
      }
   }).m_40779_("inventory.png").m_40792_().m_40790_();
   private final int f_40762_;
   private final String f_40763_;
   private final Component f_40764_;
   private String f_40765_;
   @Deprecated
   private String f_40766_ = "items.png";
   private net.minecraft.resources.ResourceLocation backgroundLocation;
   private boolean f_40767_ = true;
   private boolean f_40768_ = true;
   private EnchantmentCategory[] f_40769_ = new EnchantmentCategory[0];
   private ItemStack f_40770_;

   public CreativeModeTab(String label) {
       this(-1, label);
   }

   public CreativeModeTab(int p_40773_, String p_40774_) {
      this.f_40763_ = p_40774_;
      this.f_40764_ = new TranslatableComponent("itemGroup." + p_40774_);
      this.f_40770_ = ItemStack.f_41583_;
      this.f_40762_ = addGroupSafe(p_40773_, this);
   }

   public int m_40775_() {
      return this.f_40762_;
   }

   public String m_40783_() {
      return this.f_40765_ == null ? this.f_40763_ : this.f_40765_;
   }

   public Component m_40786_() {
      return this.f_40764_;
   }

   public ItemStack m_40787_() {
      if (this.f_40770_.m_41619_()) {
         this.f_40770_ = this.m_6976_();
      }

      return this.f_40770_;
   }

   public abstract ItemStack m_6976_();

   /**
    * @deprecated Forge use {@link #getBackgroundImage()} instead
    */
   @Deprecated
   public String m_40788_() {
      return this.f_40766_;
   }

   /**
    * @deprecated Forge: use {@link #setBackgroundImage(net.minecraft.resources.ResourceLocation)} instead
    */
   @Deprecated
   public CreativeModeTab m_40779_(String p_40780_) {
      this.f_40766_ = p_40780_;
      return this;
   }

   public CreativeModeTab setBackgroundImage(net.minecraft.resources.ResourceLocation texture) {
      this.backgroundLocation = texture;
      return this;
   }

   public CreativeModeTab m_40784_(String p_40785_) {
      this.f_40765_ = p_40785_;
      return this;
   }

   public boolean m_40789_() {
      return this.f_40768_;
   }

   public CreativeModeTab m_40790_() {
      this.f_40768_ = false;
      return this;
   }

   public boolean m_40791_() {
      return this.f_40767_;
   }

   public CreativeModeTab m_40792_() {
      this.f_40767_ = false;
      return this;
   }

   public int m_40793_() {
      if (f_40762_ > 11) return ((f_40762_ - 12) % 10) % 5;
      return this.f_40762_ % 6;
   }

   public boolean m_40794_() {
      if (f_40762_ > 11) return ((f_40762_ - 12) % 10) < 5;
      return this.f_40762_ < 6;
   }

   public boolean m_6563_() {
      return this.m_40793_() == 5;
   }

   public EnchantmentCategory[] m_40795_() {
      return this.f_40769_;
   }

   public CreativeModeTab m_40781_(EnchantmentCategory... p_40782_) {
      this.f_40769_ = p_40782_;
      return this;
   }

   public boolean m_40776_(@Nullable EnchantmentCategory p_40777_) {
      if (p_40777_ != null) {
         for(EnchantmentCategory enchantmentcategory : this.f_40769_) {
            if (enchantmentcategory == p_40777_) {
               return true;
            }
         }
      }

      return false;
   }

   public void m_6151_(NonNullList<ItemStack> p_40778_) {
      for(Item item : Registry.f_122827_) {
         item.m_6787_(this, p_40778_);
      }

   }

   public int getTabPage() {
      return f_40762_ < 12 ? 0 : ((f_40762_ - 12) / 10) + 1;
   }

   public boolean hasSearchBar() {
      return f_40762_ == f_40754_.f_40762_;
   }

   /**
    * Gets the width of the search bar of the creative tab, use this if your
    * creative tab name overflows together with a custom texture.
    *
    * @return The width of the search bar, 89 by default
    */
   public int getSearchbarWidth() {
      return 89;
   }

   public net.minecraft.resources.ResourceLocation getBackgroundImage() {
      if (backgroundLocation != null) return backgroundLocation; //FORGE: allow custom namespace
      return new net.minecraft.resources.ResourceLocation("textures/gui/container/creative_inventory/tab_" + this.m_40788_());
   }

   private static final net.minecraft.resources.ResourceLocation CREATIVE_INVENTORY_TABS = new net.minecraft.resources.ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   public net.minecraft.resources.ResourceLocation getTabsImage() {
      return CREATIVE_INVENTORY_TABS;
   }

   public int getLabelColor() {
      return 4210752;
   }

   public int getSlotColor() {
      return -2130706433;
   }

   public static synchronized int getGroupCountSafe() {
      return CreativeModeTab.f_40748_.length;
   }

   private static synchronized int addGroupSafe(int index, CreativeModeTab newGroup) {
      if(index == -1) {
         index = f_40748_.length;
      }
      if (index >= f_40748_.length) {
         CreativeModeTab[] tmp = new CreativeModeTab[index + 1];
         System.arraycopy(f_40748_, 0, tmp, 0, f_40748_.length);
         f_40748_ = tmp;
      }
      f_40748_[index] = newGroup;
      return index;
   }
}
