package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

public class HopperBlockEntity extends RandomizableContainerBlockEntity implements Hopper {
   public static final int f_155547_ = 8;
   public static final int f_155548_ = 5;
   private NonNullList<ItemStack> f_59301_ = NonNullList.m_122780_(5, ItemStack.f_41583_);
   private int f_59302_ = -1;
   private long f_59303_;

   public HopperBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
      super(BlockEntityType.f_58933_, p_155550_, p_155551_);
   }

   public void m_142466_(CompoundTag p_155588_) {
      super.m_142466_(p_155588_);
      this.f_59301_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (!this.m_59631_(p_155588_)) {
         ContainerHelper.m_18980_(p_155588_, this.f_59301_);
      }

      this.f_59302_ = p_155588_.m_128451_("TransferCooldown");
   }

   public CompoundTag m_6945_(CompoundTag p_59375_) {
      super.m_6945_(p_59375_);
      if (!this.m_59634_(p_59375_)) {
         ContainerHelper.m_18973_(p_59375_, this.f_59301_);
      }

      p_59375_.m_128405_("TransferCooldown", this.f_59302_);
      return p_59375_;
   }

   public int m_6643_() {
      return this.f_59301_.size();
   }

   public ItemStack m_7407_(int p_59309_, int p_59310_) {
      this.m_59640_((Player)null);
      return ContainerHelper.m_18969_(this.m_7086_(), p_59309_, p_59310_);
   }

   public void m_6836_(int p_59315_, ItemStack p_59316_) {
      this.m_59640_((Player)null);
      this.m_7086_().set(p_59315_, p_59316_);
      if (p_59316_.m_41613_() > this.m_6893_()) {
         p_59316_.m_41764_(this.m_6893_());
      }

   }

   protected Component m_6820_() {
      return new TranslatableComponent("container.hopper");
   }

   public static void m_155573_(Level p_155574_, BlockPos p_155575_, BlockState p_155576_, HopperBlockEntity p_155577_) {
      --p_155577_.f_59302_;
      p_155577_.f_59303_ = p_155574_.m_46467_();
      if (!p_155577_.m_59407_()) {
         p_155577_.m_59395_(0);
         m_155578_(p_155574_, p_155575_, p_155576_, p_155577_, () -> {
            return m_155552_(p_155574_, p_155577_);
         });
      }

   }

   private static boolean m_155578_(Level p_155579_, BlockPos p_155580_, BlockState p_155581_, HopperBlockEntity p_155582_, BooleanSupplier p_155583_) {
      if (p_155579_.f_46443_) {
         return false;
      } else {
         if (!p_155582_.m_59407_() && p_155581_.m_61143_(HopperBlock.f_54022_)) {
            boolean flag = false;
            if (!p_155582_.m_7983_()) {
               flag = ejectItems(p_155579_, p_155580_, p_155581_, p_155582_);
            }

            if (!p_155582_.m_59404_()) {
               flag |= p_155583_.getAsBoolean();
            }

            if (flag) {
               p_155582_.m_59395_(8);
               m_155232_(p_155579_, p_155580_, p_155581_);
               return true;
            }
         }

         return false;
      }
   }

   private boolean m_59404_() {
      for(ItemStack itemstack : this.f_59301_) {
         if (itemstack.m_41619_() || itemstack.m_41613_() != itemstack.m_41741_()) {
            return false;
         }
      }

      return true;
   }

   private static boolean ejectItems(Level p_155563_, BlockPos p_155564_, BlockState p_155565_, HopperBlockEntity p_155566_) {
      if (net.minecraftforge.items.VanillaInventoryCodeHooks.insertHook(p_155566_)) return true;
      Container container = m_155592_(p_155563_, p_155564_, p_155565_);
      if (container == null) {
         return false;
      } else {
         Direction direction = p_155565_.m_61143_(HopperBlock.f_54021_).m_122424_();
         if (m_59385_(container, direction)) {
            return false;
         } else {
            for(int i = 0; i < p_155566_.m_6643_(); ++i) {
               if (!p_155566_.m_8020_(i).m_41619_()) {
                  ItemStack itemstack = p_155566_.m_8020_(i).m_41777_();
                  ItemStack itemstack1 = m_59326_(p_155566_, container, p_155566_.m_7407_(i, 1), direction);
                  if (itemstack1.m_41619_()) {
                     container.m_6596_();
                     return true;
                  }

                  p_155566_.m_6836_(i, itemstack);
               }
            }

            return false;
         }
      }
   }

   private static IntStream m_59339_(Container p_59340_, Direction p_59341_) {
      return p_59340_ instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)p_59340_).m_7071_(p_59341_)) : IntStream.range(0, p_59340_.m_6643_());
   }

   private static boolean m_59385_(Container p_59386_, Direction p_59387_) {
      return m_59339_(p_59386_, p_59387_).allMatch((p_59379_) -> {
         ItemStack itemstack = p_59386_.m_8020_(p_59379_);
         return itemstack.m_41613_() >= itemstack.m_41741_();
      });
   }

   private static boolean m_59397_(Container p_59398_, Direction p_59399_) {
      return m_59339_(p_59398_, p_59399_).allMatch((p_59319_) -> {
         return p_59398_.m_8020_(p_59319_).m_41619_();
      });
   }

   public static boolean m_155552_(Level p_155553_, Hopper p_155554_) {
      Boolean ret = net.minecraftforge.items.VanillaInventoryCodeHooks.extractHook(p_155553_, p_155554_);
      if (ret != null) return ret;
      Container container = m_155596_(p_155553_, p_155554_);
      if (container != null) {
         Direction direction = Direction.DOWN;
         return m_59397_(container, direction) ? false : m_59339_(container, direction).anyMatch((p_59363_) -> {
            return m_59354_(p_155554_, container, p_59363_, direction);
         });
      } else {
         for(ItemEntity itementity : m_155589_(p_155553_, p_155554_)) {
            if (m_59331_(p_155554_, itementity)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean m_59354_(Hopper p_59355_, Container p_59356_, int p_59357_, Direction p_59358_) {
      ItemStack itemstack = p_59356_.m_8020_(p_59357_);
      if (!itemstack.m_41619_() && m_59380_(p_59356_, itemstack, p_59357_, p_59358_)) {
         ItemStack itemstack1 = itemstack.m_41777_();
         ItemStack itemstack2 = m_59326_(p_59356_, p_59355_, p_59356_.m_7407_(p_59357_, 1), (Direction)null);
         if (itemstack2.m_41619_()) {
            p_59356_.m_6596_();
            return true;
         }

         p_59356_.m_6836_(p_59357_, itemstack1);
      }

      return false;
   }

   public static boolean m_59331_(Container p_59332_, ItemEntity p_59333_) {
      boolean flag = false;
      ItemStack itemstack = p_59333_.m_32055_().m_41777_();
      ItemStack itemstack1 = m_59326_((Container)null, p_59332_, itemstack, (Direction)null);
      if (itemstack1.m_41619_()) {
         flag = true;
         p_59333_.m_146870_();
      } else {
         p_59333_.m_32045_(itemstack1);
      }

      return flag;
   }

   public static ItemStack m_59326_(@Nullable Container p_59327_, Container p_59328_, ItemStack p_59329_, @Nullable Direction p_59330_) {
      if (p_59328_ instanceof WorldlyContainer && p_59330_ != null) {
         WorldlyContainer worldlycontainer = (WorldlyContainer)p_59328_;
         int[] aint = worldlycontainer.m_7071_(p_59330_);

         for(int k = 0; k < aint.length && !p_59329_.m_41619_(); ++k) {
            p_59329_ = m_59320_(p_59327_, p_59328_, p_59329_, aint[k], p_59330_);
         }
      } else {
         int i = p_59328_.m_6643_();

         for(int j = 0; j < i && !p_59329_.m_41619_(); ++j) {
            p_59329_ = m_59320_(p_59327_, p_59328_, p_59329_, j, p_59330_);
         }
      }

      return p_59329_;
   }

   private static boolean m_59334_(Container p_59335_, ItemStack p_59336_, int p_59337_, @Nullable Direction p_59338_) {
      if (!p_59335_.m_7013_(p_59337_, p_59336_)) {
         return false;
      } else {
         return !(p_59335_ instanceof WorldlyContainer) || ((WorldlyContainer)p_59335_).m_7155_(p_59337_, p_59336_, p_59338_);
      }
   }

   private static boolean m_59380_(Container p_59381_, ItemStack p_59382_, int p_59383_, Direction p_59384_) {
      return !(p_59381_ instanceof WorldlyContainer) || ((WorldlyContainer)p_59381_).m_7157_(p_59383_, p_59382_, p_59384_);
   }

   private static ItemStack m_59320_(@Nullable Container p_59321_, Container p_59322_, ItemStack p_59323_, int p_59324_, @Nullable Direction p_59325_) {
      ItemStack itemstack = p_59322_.m_8020_(p_59324_);
      if (m_59334_(p_59322_, p_59323_, p_59324_, p_59325_)) {
         boolean flag = false;
         boolean flag1 = p_59322_.m_7983_();
         if (itemstack.m_41619_()) {
            p_59322_.m_6836_(p_59324_, p_59323_);
            p_59323_ = ItemStack.f_41583_;
            flag = true;
         } else if (m_59344_(itemstack, p_59323_)) {
            int i = p_59323_.m_41741_() - itemstack.m_41613_();
            int j = Math.min(p_59323_.m_41613_(), i);
            p_59323_.m_41774_(j);
            itemstack.m_41769_(j);
            flag = j > 0;
         }

         if (flag) {
            if (flag1 && p_59322_ instanceof HopperBlockEntity) {
               HopperBlockEntity hopperblockentity1 = (HopperBlockEntity)p_59322_;
               if (!hopperblockentity1.m_59409_()) {
                  int k = 0;
                  if (p_59321_ instanceof HopperBlockEntity) {
                     HopperBlockEntity hopperblockentity = (HopperBlockEntity)p_59321_;
                     if (hopperblockentity1.f_59303_ >= hopperblockentity.f_59303_) {
                        k = 1;
                     }
                  }

                  hopperblockentity1.m_59395_(8 - k);
               }
            }

            p_59322_.m_6596_();
         }
      }

      return p_59323_;
   }

   @Nullable
   private static Container m_155592_(Level p_155593_, BlockPos p_155594_, BlockState p_155595_) {
      Direction direction = p_155595_.m_61143_(HopperBlock.f_54021_);
      return m_59390_(p_155593_, p_155594_.m_142300_(direction));
   }

   @Nullable
   private static Container m_155596_(Level p_155597_, Hopper p_155598_) {
      return m_59347_(p_155597_, p_155598_.m_6343_(), p_155598_.m_6358_() + 1.0D, p_155598_.m_6446_());
   }

   public static List<ItemEntity> m_155589_(Level p_155590_, Hopper p_155591_) {
      return p_155591_.m_59300_().m_83299_().stream().flatMap((p_155558_) -> {
         return p_155590_.m_6443_(ItemEntity.class, p_155558_.m_82386_(p_155591_.m_6343_() - 0.5D, p_155591_.m_6358_() - 0.5D, p_155591_.m_6446_() - 0.5D), EntitySelector.f_20402_).stream();
      }).collect(Collectors.toList());
   }

   @Nullable
   public static Container m_59390_(Level p_59391_, BlockPos p_59392_) {
      return m_59347_(p_59391_, (double)p_59392_.m_123341_() + 0.5D, (double)p_59392_.m_123342_() + 0.5D, (double)p_59392_.m_123343_() + 0.5D);
   }

   @Nullable
   private static Container m_59347_(Level p_59348_, double p_59349_, double p_59350_, double p_59351_) {
      Container container = null;
      BlockPos blockpos = new BlockPos(p_59349_, p_59350_, p_59351_);
      BlockState blockstate = p_59348_.m_8055_(blockpos);
      Block block = blockstate.m_60734_();
      if (block instanceof WorldlyContainerHolder) {
         container = ((WorldlyContainerHolder)block).m_5840_(blockstate, p_59348_, blockpos);
      } else if (blockstate.m_155947_()) {
         BlockEntity blockentity = p_59348_.m_7702_(blockpos);
         if (blockentity instanceof Container) {
            container = (Container)blockentity;
            if (container instanceof ChestBlockEntity && block instanceof ChestBlock) {
               container = ChestBlock.m_51511_((ChestBlock)block, blockstate, p_59348_, blockpos, true);
            }
         }
      }

      if (container == null) {
         List<Entity> list = p_59348_.m_6249_((Entity)null, new AABB(p_59349_ - 0.5D, p_59350_ - 0.5D, p_59351_ - 0.5D, p_59349_ + 0.5D, p_59350_ + 0.5D, p_59351_ + 0.5D), EntitySelector.f_20405_);
         if (!list.isEmpty()) {
            container = (Container)list.get(p_59348_.f_46441_.nextInt(list.size()));
         }
      }

      return container;
   }

   private static boolean m_59344_(ItemStack p_59345_, ItemStack p_59346_) {
      if (!p_59345_.m_150930_(p_59346_.m_41720_())) {
         return false;
      } else if (p_59345_.m_41773_() != p_59346_.m_41773_()) {
         return false;
      } else if (p_59345_.m_41613_() > p_59345_.m_41741_()) {
         return false;
      } else {
         return ItemStack.m_41658_(p_59345_, p_59346_);
      }
   }

   public double m_6343_() {
      return (double)this.f_58858_.m_123341_() + 0.5D;
   }

   public double m_6358_() {
      return (double)this.f_58858_.m_123342_() + 0.5D;
   }

   public double m_6446_() {
      return (double)this.f_58858_.m_123343_() + 0.5D;
   }

   public void m_59395_(int p_59396_) {
      this.f_59302_ = p_59396_;
   }

   private boolean m_59407_() {
      return this.f_59302_ > 0;
   }

   public boolean m_59409_() {
      return this.f_59302_ > 8;
   }

   protected NonNullList<ItemStack> m_7086_() {
      return this.f_59301_;
   }

   protected void m_6520_(NonNullList<ItemStack> p_59371_) {
      this.f_59301_ = p_59371_;
   }

   public static void m_155567_(Level p_155568_, BlockPos p_155569_, BlockState p_155570_, Entity p_155571_, HopperBlockEntity p_155572_) {
      if (p_155571_ instanceof ItemEntity && Shapes.m_83157_(Shapes.m_83064_(p_155571_.m_142469_().m_82386_((double)(-p_155569_.m_123341_()), (double)(-p_155569_.m_123342_()), (double)(-p_155569_.m_123343_()))), p_155572_.m_59300_(), BooleanOp.f_82689_)) {
         m_155578_(p_155568_, p_155569_, p_155570_, p_155572_, () -> {
            return m_59331_(p_155572_, (ItemEntity)p_155571_);
         });
      }

   }

   protected AbstractContainerMenu m_6555_(int p_59312_, Inventory p_59313_) {
      return new HopperMenu(p_59312_, p_59313_, this);
   }

   @Override
   protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
      return new net.minecraftforge.items.VanillaHopperItemHandler(this);
   }

   public long getLastUpdateTime() {
      return this.f_59303_;
   }
}
