package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {
   private static final int f_155323_ = 1;
   private NonNullList<ItemStack> f_59073_ = NonNullList.m_122780_(27, ItemStack.f_41583_);
   private final ContainerOpenersCounter f_155324_ = new ContainerOpenersCounter() {
      protected void m_142292_(Level p_155357_, BlockPos p_155358_, BlockState p_155359_) {
         ChestBlockEntity.m_155338_(p_155357_, p_155358_, p_155359_, SoundEvents.f_11749_);
      }

      protected void m_142289_(Level p_155367_, BlockPos p_155368_, BlockState p_155369_) {
         ChestBlockEntity.m_155338_(p_155367_, p_155368_, p_155369_, SoundEvents.f_11747_);
      }

      protected void m_142148_(Level p_155361_, BlockPos p_155362_, BlockState p_155363_, int p_155364_, int p_155365_) {
         ChestBlockEntity.this.m_142151_(p_155361_, p_155362_, p_155363_, p_155364_, p_155365_);
      }

      protected boolean m_142718_(Player p_155355_) {
         if (!(p_155355_.f_36096_ instanceof ChestMenu)) {
            return false;
         } else {
            Container container = ((ChestMenu)p_155355_.f_36096_).m_39261_();
            return container == ChestBlockEntity.this || container instanceof CompoundContainer && ((CompoundContainer)container).m_18927_(ChestBlockEntity.this);
         }
      }
   };
   private final ChestLidController f_155325_ = new ChestLidController();

   protected ChestBlockEntity(BlockEntityType<?> p_155327_, BlockPos p_155328_, BlockState p_155329_) {
      super(p_155327_, p_155328_, p_155329_);
   }

   public ChestBlockEntity(BlockPos p_155331_, BlockState p_155332_) {
      this(BlockEntityType.f_58918_, p_155331_, p_155332_);
   }

   public int m_6643_() {
      return 27;
   }

   protected Component m_6820_() {
      return new TranslatableComponent("container.chest");
   }

   public void m_142466_(CompoundTag p_155349_) {
      super.m_142466_(p_155349_);
      this.f_59073_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (!this.m_59631_(p_155349_)) {
         ContainerHelper.m_18980_(p_155349_, this.f_59073_);
      }

   }

   public CompoundTag m_6945_(CompoundTag p_59112_) {
      super.m_6945_(p_59112_);
      if (!this.m_59634_(p_59112_)) {
         ContainerHelper.m_18973_(p_59112_, this.f_59073_);
      }

      return p_59112_;
   }

   public static void m_155343_(Level p_155344_, BlockPos p_155345_, BlockState p_155346_, ChestBlockEntity p_155347_) {
      p_155347_.f_155325_.m_155374_();
   }

   static void m_155338_(Level p_155339_, BlockPos p_155340_, BlockState p_155341_, SoundEvent p_155342_) {
      ChestType chesttype = p_155341_.m_61143_(ChestBlock.f_51479_);
      if (chesttype != ChestType.LEFT) {
         double d0 = (double)p_155340_.m_123341_() + 0.5D;
         double d1 = (double)p_155340_.m_123342_() + 0.5D;
         double d2 = (double)p_155340_.m_123343_() + 0.5D;
         if (chesttype == ChestType.RIGHT) {
            Direction direction = ChestBlock.m_51584_(p_155341_);
            d0 += (double)direction.m_122429_() * 0.5D;
            d2 += (double)direction.m_122431_() * 0.5D;
         }

         p_155339_.m_6263_((Player)null, d0, d1, d2, p_155342_, SoundSource.BLOCKS, 0.5F, p_155339_.f_46441_.nextFloat() * 0.1F + 0.9F);
      }
   }

   public boolean m_7531_(int p_59114_, int p_59115_) {
      if (p_59114_ == 1) {
         this.f_155325_.m_155377_(p_59115_ > 0);
         return true;
      } else {
         return super.m_7531_(p_59114_, p_59115_);
      }
   }

   public void m_5856_(Player p_59120_) {
      if (!this.f_58859_ && !p_59120_.m_5833_()) {
         this.f_155324_.m_155452_(p_59120_, this.m_58904_(), this.m_58899_(), this.m_58900_());
      }

   }

   public void m_5785_(Player p_59118_) {
      if (!this.f_58859_ && !p_59118_.m_5833_()) {
         this.f_155324_.m_155468_(p_59118_, this.m_58904_(), this.m_58899_(), this.m_58900_());
      }

   }

   protected NonNullList<ItemStack> m_7086_() {
      return this.f_59073_;
   }

   protected void m_6520_(NonNullList<ItemStack> p_59110_) {
      this.f_59073_ = p_59110_;
   }

   public float m_6683_(float p_59080_) {
      return this.f_155325_.m_155375_(p_59080_);
   }

   public static int m_59086_(BlockGetter p_59087_, BlockPos p_59088_) {
      BlockState blockstate = p_59087_.m_8055_(p_59088_);
      if (blockstate.m_155947_()) {
         BlockEntity blockentity = p_59087_.m_7702_(p_59088_);
         if (blockentity instanceof ChestBlockEntity) {
            return ((ChestBlockEntity)blockentity).f_155324_.m_155450_();
         }
      }

      return 0;
   }

   public static void m_59103_(ChestBlockEntity p_59104_, ChestBlockEntity p_59105_) {
      NonNullList<ItemStack> nonnulllist = p_59104_.m_7086_();
      p_59104_.m_6520_(p_59105_.m_7086_());
      p_59105_.m_6520_(nonnulllist);
   }

   protected AbstractContainerMenu m_6555_(int p_59082_, Inventory p_59083_) {
      return ChestMenu.m_39237_(p_59082_, p_59083_, this);
   }

   private net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandlerModifiable> chestHandler;
   @Override
   public void m_155250_(BlockState p_155251_) {
      super.m_155250_(p_155251_);
      if (this.chestHandler != null) {
         net.minecraftforge.common.util.LazyOptional<?> oldHandler = this.chestHandler;
         this.chestHandler = null;
         oldHandler.invalidate();
      }
   }

   @Override
   public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
       if (!this.f_58859_ && cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
          if (this.chestHandler == null)
             this.chestHandler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
          return this.chestHandler.cast();
       }
       return super.getCapability(cap, side);
   }

   private net.minecraftforge.items.IItemHandlerModifiable createHandler() {
      BlockState state = this.m_58900_();
      if (!(state.m_60734_() instanceof ChestBlock)) {
         return new net.minecraftforge.items.wrapper.InvWrapper(this);
      }
      Container inv = ChestBlock.m_51511_((ChestBlock) state.m_60734_(), state, m_58904_(), m_58899_(), true);
      return new net.minecraftforge.items.wrapper.InvWrapper(inv == null ? this : inv);
   }

   @Override
   public void invalidateCaps() {
      super.invalidateCaps();
      if (chestHandler != null) {
         chestHandler.invalidate();
         chestHandler = null;
      }
   }

   public void m_155350_() {
      if (!this.f_58859_) {
         this.f_155324_.m_155476_(this.m_58904_(), this.m_58899_(), this.m_58900_());
      }

   }

   protected void m_142151_(Level p_155333_, BlockPos p_155334_, BlockState p_155335_, int p_155336_, int p_155337_) {
      Block block = p_155335_.m_60734_();
      p_155333_.m_7696_(p_155334_, block, 1, p_155337_);
   }
}
