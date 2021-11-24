package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
   protected static final int f_154980_ = 0;
   protected static final int f_154981_ = 1;
   protected static final int f_154982_ = 2;
   public static final int f_154983_ = 0;
   private static final int[] f_58313_ = new int[]{0};
   private static final int[] f_58314_ = new int[]{2, 1};
   private static final int[] f_58315_ = new int[]{1};
   public static final int f_154984_ = 1;
   public static final int f_154985_ = 2;
   public static final int f_154986_ = 3;
   public static final int f_154987_ = 4;
   public static final int f_154988_ = 200;
   public static final int f_154989_ = 2;
   protected NonNullList<ItemStack> f_58310_ = NonNullList.m_122780_(3, ItemStack.f_41583_);
   int f_58316_;
   int f_58317_;
   int f_58318_;
   int f_58319_;
   protected final ContainerData f_58311_ = new ContainerData() {
      public int m_6413_(int p_58431_) {
         switch(p_58431_) {
         case 0:
            return AbstractFurnaceBlockEntity.this.f_58316_;
         case 1:
            return AbstractFurnaceBlockEntity.this.f_58317_;
         case 2:
            return AbstractFurnaceBlockEntity.this.f_58318_;
         case 3:
            return AbstractFurnaceBlockEntity.this.f_58319_;
         default:
            return 0;
         }
      }

      public void m_8050_(int p_58433_, int p_58434_) {
         switch(p_58433_) {
         case 0:
            AbstractFurnaceBlockEntity.this.f_58316_ = p_58434_;
            break;
         case 1:
            AbstractFurnaceBlockEntity.this.f_58317_ = p_58434_;
            break;
         case 2:
            AbstractFurnaceBlockEntity.this.f_58318_ = p_58434_;
            break;
         case 3:
            AbstractFurnaceBlockEntity.this.f_58319_ = p_58434_;
         }

      }

      public int m_6499_() {
         return 4;
      }
   };
   private final Object2IntOpenHashMap<ResourceLocation> f_58320_ = new Object2IntOpenHashMap<>();
   private final RecipeType<? extends AbstractCookingRecipe> f_58312_;

   protected AbstractFurnaceBlockEntity(BlockEntityType<?> p_154991_, BlockPos p_154992_, BlockState p_154993_, RecipeType<? extends AbstractCookingRecipe> p_154994_) {
      super(p_154991_, p_154992_, p_154993_);
      this.f_58312_ = p_154994_;
   }

   /**@deprecated Forge: get burn times by calling ForgeHooks#getBurnTime(ItemStack)*/ @Deprecated
   public static Map<Item, Integer> m_58423_() {
      Map<Item, Integer> map = Maps.newLinkedHashMap();
      m_58374_(map, Items.f_42448_, 20000);
      m_58374_(map, Blocks.f_50353_, 16000);
      m_58374_(map, Items.f_42585_, 2400);
      m_58374_(map, Items.f_42413_, 1600);
      m_58374_(map, Items.f_42414_, 1600);
      m_58370_(map, ItemTags.f_13182_, 300);
      m_58370_(map, ItemTags.f_13168_, 300);
      m_58370_(map, ItemTags.f_13174_, 300);
      m_58370_(map, ItemTags.f_13175_, 150);
      m_58370_(map, ItemTags.f_13178_, 300);
      m_58370_(map, ItemTags.f_13177_, 300);
      m_58374_(map, Blocks.f_50132_, 300);
      m_58374_(map, Blocks.f_50480_, 300);
      m_58374_(map, Blocks.f_50479_, 300);
      m_58374_(map, Blocks.f_50481_, 300);
      m_58374_(map, Blocks.f_50483_, 300);
      m_58374_(map, Blocks.f_50482_, 300);
      m_58374_(map, Blocks.f_50192_, 300);
      m_58374_(map, Blocks.f_50475_, 300);
      m_58374_(map, Blocks.f_50474_, 300);
      m_58374_(map, Blocks.f_50476_, 300);
      m_58374_(map, Blocks.f_50478_, 300);
      m_58374_(map, Blocks.f_50477_, 300);
      m_58374_(map, Blocks.f_50065_, 300);
      m_58374_(map, Blocks.f_50078_, 300);
      m_58374_(map, Blocks.f_50624_, 300);
      m_58374_(map, Blocks.f_50131_, 300);
      m_58374_(map, Blocks.f_50087_, 300);
      m_58374_(map, Blocks.f_50325_, 300);
      m_58374_(map, Blocks.f_50091_, 300);
      m_58374_(map, Blocks.f_50329_, 300);
      m_58370_(map, ItemTags.f_13191_, 300);
      m_58374_(map, Items.f_42411_, 300);
      m_58374_(map, Items.f_42523_, 300);
      m_58374_(map, Blocks.f_50155_, 300);
      m_58370_(map, ItemTags.f_13157_, 200);
      m_58374_(map, Items.f_42421_, 200);
      m_58374_(map, Items.f_42420_, 200);
      m_58374_(map, Items.f_42424_, 200);
      m_58374_(map, Items.f_42423_, 200);
      m_58374_(map, Items.f_42422_, 200);
      m_58370_(map, ItemTags.f_13173_, 200);
      m_58370_(map, ItemTags.f_13155_, 1200);
      m_58370_(map, ItemTags.f_13167_, 100);
      m_58370_(map, ItemTags.f_13170_, 100);
      m_58374_(map, Items.f_42398_, 100);
      m_58370_(map, ItemTags.f_13180_, 100);
      m_58374_(map, Items.f_42399_, 100);
      m_58370_(map, ItemTags.f_13172_, 67);
      m_58374_(map, Blocks.f_50577_, 4001);
      m_58374_(map, Items.f_42717_, 300);
      m_58374_(map, Blocks.f_50571_, 50);
      m_58374_(map, Blocks.f_50036_, 100);
      m_58374_(map, Blocks.f_50616_, 400);
      m_58374_(map, Blocks.f_50617_, 300);
      m_58374_(map, Blocks.f_50618_, 300);
      m_58374_(map, Blocks.f_50621_, 300);
      m_58374_(map, Blocks.f_50622_, 300);
      m_58374_(map, Blocks.f_50625_, 300);
      m_58374_(map, Blocks.f_50715_, 300);
      m_58374_(map, Blocks.f_152541_, 100);
      m_58374_(map, Blocks.f_152542_, 100);
      return map;
   }

   private static boolean m_58397_(Item p_58398_) {
      return ItemTags.f_13153_.m_8110_(p_58398_);
   }

   private static void m_58370_(Map<Item, Integer> p_58371_, Tag<Item> p_58372_, int p_58373_) {
      for(Item item : p_58372_.m_6497_()) {
         if (!m_58397_(item)) {
            p_58371_.put(item, p_58373_);
         }
      }

   }

   private static void m_58374_(Map<Item, Integer> p_58375_, ItemLike p_58376_, int p_58377_) {
      Item item = p_58376_.m_5456_();
      if (m_58397_(item)) {
         if (SharedConstants.f_136183_) {
            throw (IllegalStateException)Util.m_137570_(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item.m_7626_((ItemStack)null).getString() + " a furnace fuel. That will not work!"));
         }
      } else {
         p_58375_.put(item, p_58377_);
      }
   }

   private boolean m_58425_() {
      return this.f_58316_ > 0;
   }

   public void m_142466_(CompoundTag p_155025_) {
      super.m_142466_(p_155025_);
      this.f_58310_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      ContainerHelper.m_18980_(p_155025_, this.f_58310_);
      this.f_58316_ = p_155025_.m_128451_("BurnTime");
      this.f_58318_ = p_155025_.m_128451_("CookTime");
      this.f_58319_ = p_155025_.m_128451_("CookTimeTotal");
      this.f_58317_ = this.m_7743_(this.f_58310_.get(1));
      CompoundTag compoundtag = p_155025_.m_128469_("RecipesUsed");

      for(String s : compoundtag.m_128431_()) {
         this.f_58320_.put(new ResourceLocation(s), compoundtag.m_128451_(s));
      }

   }

   public CompoundTag m_6945_(CompoundTag p_58379_) {
      super.m_6945_(p_58379_);
      p_58379_.m_128405_("BurnTime", this.f_58316_);
      p_58379_.m_128405_("CookTime", this.f_58318_);
      p_58379_.m_128405_("CookTimeTotal", this.f_58319_);
      ContainerHelper.m_18973_(p_58379_, this.f_58310_);
      CompoundTag compoundtag = new CompoundTag();
      this.f_58320_.forEach((p_58382_, p_58383_) -> {
         compoundtag.m_128405_(p_58382_.toString(), p_58383_);
      });
      p_58379_.m_128365_("RecipesUsed", compoundtag);
      return p_58379_;
   }

   public static void m_155013_(Level p_155014_, BlockPos p_155015_, BlockState p_155016_, AbstractFurnaceBlockEntity p_155017_) {
      boolean flag = p_155017_.m_58425_();
      boolean flag1 = false;
      if (p_155017_.m_58425_()) {
         --p_155017_.f_58316_;
      }

      ItemStack itemstack = p_155017_.f_58310_.get(1);
      if (p_155017_.m_58425_() || !itemstack.m_41619_() && !p_155017_.f_58310_.get(0).m_41619_()) {
         Recipe<?> recipe = p_155014_.m_7465_().m_44015_((RecipeType<AbstractCookingRecipe>)p_155017_.f_58312_, p_155017_, p_155014_).orElse(null);
         int i = p_155017_.m_6893_();
         if (!p_155017_.m_58425_() && p_155017_.m_155005_(recipe, p_155017_.f_58310_, i)) {
            p_155017_.f_58316_ = p_155017_.m_7743_(itemstack);
            p_155017_.f_58317_ = p_155017_.f_58316_;
            if (p_155017_.m_58425_()) {
               flag1 = true;
               if (itemstack.hasContainerItem())
                  p_155017_.f_58310_.set(1, itemstack.getContainerItem());
               else
               if (!itemstack.m_41619_()) {
                  Item item = itemstack.m_41720_();
                  itemstack.m_41774_(1);
                  if (itemstack.m_41619_()) {
                     p_155017_.f_58310_.set(1, itemstack.getContainerItem());
                  }
               }
            }
         }

         if (p_155017_.m_58425_() && p_155017_.m_155005_(recipe, p_155017_.f_58310_, i)) {
            ++p_155017_.f_58318_;
            if (p_155017_.f_58318_ == p_155017_.f_58319_) {
               p_155017_.f_58318_ = 0;
               p_155017_.f_58319_ = m_155009_(p_155014_, p_155017_.f_58312_, p_155017_);
               if (p_155017_.m_155026_(recipe, p_155017_.f_58310_, i)) {
                  p_155017_.m_6029_(recipe);
               }

               flag1 = true;
            }
         } else {
            p_155017_.f_58318_ = 0;
         }
      } else if (!p_155017_.m_58425_() && p_155017_.f_58318_ > 0) {
         p_155017_.f_58318_ = Mth.m_14045_(p_155017_.f_58318_ - 2, 0, p_155017_.f_58319_);
      }

      if (flag != p_155017_.m_58425_()) {
         flag1 = true;
         p_155016_ = p_155016_.m_61124_(AbstractFurnaceBlock.f_48684_, Boolean.valueOf(p_155017_.m_58425_()));
         p_155014_.m_7731_(p_155015_, p_155016_, 3);
      }

      if (flag1) {
         m_155232_(p_155014_, p_155015_, p_155016_);
      }

   }

   private boolean m_155005_(@Nullable Recipe<?> p_155006_, NonNullList<ItemStack> p_155007_, int p_155008_) {
      if (!p_155007_.get(0).m_41619_() && p_155006_ != null) {
         ItemStack itemstack = ((Recipe<WorldlyContainer>) p_155006_).m_5874_(this);
         if (itemstack.m_41619_()) {
            return false;
         } else {
            ItemStack itemstack1 = p_155007_.get(2);
            if (itemstack1.m_41619_()) {
               return true;
            } else if (!itemstack1.m_41656_(itemstack)) {
               return false;
            } else if (itemstack1.m_41613_() + itemstack.m_41613_() <= p_155008_ && itemstack1.m_41613_() + itemstack.m_41613_() <= itemstack1.m_41741_()) { // Forge fix: make furnace respect stack sizes in furnace recipes
               return true;
            } else {
               return itemstack1.m_41613_() + itemstack.m_41613_() <= itemstack.m_41741_(); // Forge fix: make furnace respect stack sizes in furnace recipes
            }
         }
      } else {
         return false;
      }
   }

   private boolean m_155026_(@Nullable Recipe<?> p_155027_, NonNullList<ItemStack> p_155028_, int p_155029_) {
      if (p_155027_ != null && this.m_155005_(p_155027_, p_155028_, p_155029_)) {
         ItemStack itemstack = p_155028_.get(0);
         ItemStack itemstack1 = ((Recipe<WorldlyContainer>) p_155027_).m_5874_(this);
         ItemStack itemstack2 = p_155028_.get(2);
         if (itemstack2.m_41619_()) {
            p_155028_.set(2, itemstack1.m_41777_());
         } else if (itemstack2.m_150930_(itemstack1.m_41720_())) {
            itemstack2.m_41769_(itemstack1.m_41613_());
         }

         if (itemstack.m_150930_(Blocks.f_50057_.m_5456_()) && !p_155028_.get(1).m_41619_() && p_155028_.get(1).m_150930_(Items.f_42446_)) {
            p_155028_.set(1, new ItemStack(Items.f_42447_));
         }

         itemstack.m_41774_(1);
         return true;
      } else {
         return false;
      }
   }

   protected int m_7743_(ItemStack p_58343_) {
      if (p_58343_.m_41619_()) {
         return 0;
      } else {
         Item item = p_58343_.m_41720_();
         return net.minecraftforge.common.ForgeHooks.getBurnTime(p_58343_, this.f_58312_);
      }
   }

   private static int m_155009_(Level p_155010_, RecipeType<? extends AbstractCookingRecipe> p_155011_, Container p_155012_) {
      return p_155010_.m_7465_().m_44015_((RecipeType<AbstractCookingRecipe>)p_155011_, p_155012_, p_155010_).map(AbstractCookingRecipe::m_43753_).orElse(200);
   }

   public static boolean m_58399_(ItemStack p_58400_) {
      return net.minecraftforge.common.ForgeHooks.getBurnTime(p_58400_, null) > 0;
   }

   public int[] m_7071_(Direction p_58363_) {
      if (p_58363_ == Direction.DOWN) {
         return f_58314_;
      } else {
         return p_58363_ == Direction.UP ? f_58313_ : f_58315_;
      }
   }

   public boolean m_7155_(int p_58336_, ItemStack p_58337_, @Nullable Direction p_58338_) {
      return this.m_7013_(p_58336_, p_58337_);
   }

   public boolean m_7157_(int p_58392_, ItemStack p_58393_, Direction p_58394_) {
      if (p_58394_ == Direction.DOWN && p_58392_ == 1) {
         return p_58393_.m_150930_(Items.f_42447_) || p_58393_.m_150930_(Items.f_42446_);
      } else {
         return true;
      }
   }

   public int m_6643_() {
      return this.f_58310_.size();
   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_58310_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack m_8020_(int p_58328_) {
      return this.f_58310_.get(p_58328_);
   }

   public ItemStack m_7407_(int p_58330_, int p_58331_) {
      return ContainerHelper.m_18969_(this.f_58310_, p_58330_, p_58331_);
   }

   public ItemStack m_8016_(int p_58387_) {
      return ContainerHelper.m_18966_(this.f_58310_, p_58387_);
   }

   public void m_6836_(int p_58333_, ItemStack p_58334_) {
      ItemStack itemstack = this.f_58310_.get(p_58333_);
      boolean flag = !p_58334_.m_41619_() && p_58334_.m_41656_(itemstack) && ItemStack.m_41658_(p_58334_, itemstack);
      this.f_58310_.set(p_58333_, p_58334_);
      if (p_58334_.m_41613_() > this.m_6893_()) {
         p_58334_.m_41764_(this.m_6893_());
      }

      if (p_58333_ == 0 && !flag) {
         this.f_58319_ = m_155009_(this.f_58857_, this.f_58312_, this);
         this.f_58318_ = 0;
         this.m_6596_();
      }

   }

   public boolean m_6542_(Player p_58340_) {
      if (this.f_58857_.m_7702_(this.f_58858_) != this) {
         return false;
      } else {
         return p_58340_.m_20275_((double)this.f_58858_.m_123341_() + 0.5D, (double)this.f_58858_.m_123342_() + 0.5D, (double)this.f_58858_.m_123343_() + 0.5D) <= 64.0D;
      }
   }

   public boolean m_7013_(int p_58389_, ItemStack p_58390_) {
      if (p_58389_ == 2) {
         return false;
      } else if (p_58389_ != 1) {
         return true;
      } else {
         ItemStack itemstack = this.f_58310_.get(1);
         return net.minecraftforge.common.ForgeHooks.getBurnTime(p_58390_, this.f_58312_) > 0 || p_58390_.m_150930_(Items.f_42446_) && !itemstack.m_150930_(Items.f_42446_);
      }
   }

   public void m_6211_() {
      this.f_58310_.clear();
   }

   public void m_6029_(@Nullable Recipe<?> p_58345_) {
      if (p_58345_ != null) {
         ResourceLocation resourcelocation = p_58345_.m_6423_();
         this.f_58320_.addTo(resourcelocation, 1);
      }

   }

   @Nullable
   public Recipe<?> m_7928_() {
      return null;
   }

   public void m_8015_(Player p_58396_) {
   }

   public void m_155003_(ServerPlayer p_155004_) {
      List<Recipe<?>> list = this.m_154995_(p_155004_.m_9236_(), p_155004_.m_20182_());
      p_155004_.m_7281_(list);
      this.f_58320_.clear();
   }

   public List<Recipe<?>> m_154995_(ServerLevel p_154996_, Vec3 p_154997_) {
      List<Recipe<?>> list = Lists.newArrayList();

      for(Entry<ResourceLocation> entry : this.f_58320_.object2IntEntrySet()) {
         p_154996_.m_7465_().m_44043_(entry.getKey()).ifPresent((p_155023_) -> {
            list.add(p_155023_);
            m_154998_(p_154996_, p_154997_, entry.getIntValue(), ((AbstractCookingRecipe)p_155023_).m_43750_());
         });
      }

      return list;
   }

   private static void m_154998_(ServerLevel p_154999_, Vec3 p_155000_, int p_155001_, float p_155002_) {
      int i = Mth.m_14143_((float)p_155001_ * p_155002_);
      float f = Mth.m_14187_((float)p_155001_ * p_155002_);
      if (f != 0.0F && Math.random() < (double)f) {
         ++i;
      }

      ExperienceOrb.m_147082_(p_154999_, p_155000_, i);
   }

   public void m_5809_(StackedContents p_58342_) {
      for(ItemStack itemstack : this.f_58310_) {
         p_58342_.m_36491_(itemstack);
      }

   }

   net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
           net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

   @Override
   public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
      if (!this.f_58859_ && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (facing == Direction.UP)
            return handlers[0].cast();
         else if (facing == Direction.DOWN)
            return handlers[1].cast();
         else
            return handlers[2].cast();
      }
      return super.getCapability(capability, facing);
   }

   @Override
   public void invalidateCaps() {
      super.invalidateCaps();
      for (int x = 0; x < handlers.length; x++)
        handlers[x].invalidate();
   }

   @Override
   public void reviveCaps() {
      super.reviveCaps();
      this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
   }
}
