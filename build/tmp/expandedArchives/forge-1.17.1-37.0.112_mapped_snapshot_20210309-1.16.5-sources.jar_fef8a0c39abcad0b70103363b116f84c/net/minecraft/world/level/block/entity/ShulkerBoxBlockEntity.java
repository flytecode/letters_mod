package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShulkerBoxBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
   public static final int f_155657_ = 9;
   public static final int f_155658_ = 3;
   public static final int f_155659_ = 27;
   public static final int f_155660_ = 1;
   public static final int f_155661_ = 10;
   public static final float f_155662_ = 0.5F;
   public static final float f_155663_ = 270.0F;
   public static final String f_155664_ = "Items";
   private static final int[] f_59644_ = IntStream.range(0, 27).toArray();
   private NonNullList<ItemStack> f_59645_ = NonNullList.m_122780_(27, ItemStack.f_41583_);
   private int f_59646_;
   private ShulkerBoxBlockEntity.AnimationStatus f_59647_ = ShulkerBoxBlockEntity.AnimationStatus.CLOSED;
   private float f_59648_;
   private float f_59649_;
   @Nullable
   private final DyeColor f_59650_;

   public ShulkerBoxBlockEntity(@Nullable DyeColor p_155666_, BlockPos p_155667_, BlockState p_155668_) {
      super(BlockEntityType.f_58939_, p_155667_, p_155668_);
      this.f_59650_ = p_155666_;
   }

   public ShulkerBoxBlockEntity(BlockPos p_155670_, BlockState p_155671_) {
      super(BlockEntityType.f_58939_, p_155670_, p_155671_);
      this.f_59650_ = ShulkerBoxBlock.m_56262_(p_155671_.m_60734_());
   }

   public static void m_155672_(Level p_155673_, BlockPos p_155674_, BlockState p_155675_, ShulkerBoxBlockEntity p_155676_) {
      p_155676_.m_155679_(p_155673_, p_155674_, p_155675_);
   }

   private void m_155679_(Level p_155680_, BlockPos p_155681_, BlockState p_155682_) {
      this.f_59649_ = this.f_59648_;
      switch(this.f_59647_) {
      case CLOSED:
         this.f_59648_ = 0.0F;
         break;
      case OPENING:
         this.f_59648_ += 0.1F;
         if (this.f_59648_ >= 1.0F) {
            this.f_59647_ = ShulkerBoxBlockEntity.AnimationStatus.OPENED;
            this.f_59648_ = 1.0F;
            m_155687_(p_155680_, p_155681_, p_155682_);
         }

         this.m_155683_(p_155680_, p_155681_, p_155682_);
         break;
      case CLOSING:
         this.f_59648_ -= 0.1F;
         if (this.f_59648_ <= 0.0F) {
            this.f_59647_ = ShulkerBoxBlockEntity.AnimationStatus.CLOSED;
            this.f_59648_ = 0.0F;
            m_155687_(p_155680_, p_155681_, p_155682_);
         }
         break;
      case OPENED:
         this.f_59648_ = 1.0F;
      }

   }

   public ShulkerBoxBlockEntity.AnimationStatus m_59700_() {
      return this.f_59647_;
   }

   public AABB m_59666_(BlockState p_59667_) {
      return Shulker.m_149790_(p_59667_.m_61143_(ShulkerBoxBlock.f_56183_), 0.5F * this.m_59657_(1.0F));
   }

   private void m_155683_(Level p_155684_, BlockPos p_155685_, BlockState p_155686_) {
      if (p_155686_.m_60734_() instanceof ShulkerBoxBlock) {
         Direction direction = p_155686_.m_61143_(ShulkerBoxBlock.f_56183_);
         AABB aabb = Shulker.m_149793_(direction, this.f_59649_, this.f_59648_).m_82338_(p_155685_);
         List<Entity> list = p_155684_.m_45933_((Entity)null, aabb);
         if (!list.isEmpty()) {
            for(int i = 0; i < list.size(); ++i) {
               Entity entity = list.get(i);
               if (entity.m_7752_() != PushReaction.IGNORE) {
                  entity.m_6478_(MoverType.SHULKER_BOX, new Vec3((aabb.m_82362_() + 0.01D) * (double)direction.m_122429_(), (aabb.m_82376_() + 0.01D) * (double)direction.m_122430_(), (aabb.m_82385_() + 0.01D) * (double)direction.m_122431_()));
               }
            }

         }
      }
   }

   public int m_6643_() {
      return this.f_59645_.size();
   }

   public boolean m_7531_(int p_59678_, int p_59679_) {
      if (p_59678_ == 1) {
         this.f_59646_ = p_59679_;
         if (p_59679_ == 0) {
            this.f_59647_ = ShulkerBoxBlockEntity.AnimationStatus.CLOSING;
            m_155687_(this.m_58904_(), this.f_58858_, this.m_58900_());
         }

         if (p_59679_ == 1) {
            this.f_59647_ = ShulkerBoxBlockEntity.AnimationStatus.OPENING;
            m_155687_(this.m_58904_(), this.f_58858_, this.m_58900_());
         }

         return true;
      } else {
         return super.m_7531_(p_59678_, p_59679_);
      }
   }

   private static void m_155687_(Level p_155688_, BlockPos p_155689_, BlockState p_155690_) {
      p_155690_.m_60701_(p_155688_, p_155689_, 3);
   }

   public void m_5856_(Player p_59692_) {
      if (!p_59692_.m_5833_()) {
         if (this.f_59646_ < 0) {
            this.f_59646_ = 0;
         }

         ++this.f_59646_;
         this.f_58857_.m_7696_(this.f_58858_, this.m_58900_().m_60734_(), 1, this.f_59646_);
         if (this.f_59646_ == 1) {
            this.f_58857_.m_142346_(p_59692_, GameEvent.f_157803_, this.f_58858_);
            this.f_58857_.m_5594_((Player)null, this.f_58858_, SoundEvents.f_12409_, SoundSource.BLOCKS, 0.5F, this.f_58857_.f_46441_.nextFloat() * 0.1F + 0.9F);
         }
      }

   }

   public void m_5785_(Player p_59688_) {
      if (!p_59688_.m_5833_()) {
         --this.f_59646_;
         this.f_58857_.m_7696_(this.f_58858_, this.m_58900_().m_60734_(), 1, this.f_59646_);
         if (this.f_59646_ <= 0) {
            this.f_58857_.m_142346_(p_59688_, GameEvent.f_157802_, this.f_58858_);
            this.f_58857_.m_5594_((Player)null, this.f_58858_, SoundEvents.f_12408_, SoundSource.BLOCKS, 0.5F, this.f_58857_.f_46441_.nextFloat() * 0.1F + 0.9F);
         }
      }

   }

   protected Component m_6820_() {
      return new TranslatableComponent("container.shulkerBox");
   }

   public void m_142466_(CompoundTag p_155678_) {
      super.m_142466_(p_155678_);
      this.m_59693_(p_155678_);
   }

   public CompoundTag m_6945_(CompoundTag p_59676_) {
      super.m_6945_(p_59676_);
      return this.m_59695_(p_59676_);
   }

   public void m_59693_(CompoundTag p_59694_) {
      this.f_59645_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (!this.m_59631_(p_59694_) && p_59694_.m_128425_("Items", 9)) {
         ContainerHelper.m_18980_(p_59694_, this.f_59645_);
      }

   }

   public CompoundTag m_59695_(CompoundTag p_59696_) {
      if (!this.m_59634_(p_59696_)) {
         ContainerHelper.m_18976_(p_59696_, this.f_59645_, false);
      }

      return p_59696_;
   }

   protected NonNullList<ItemStack> m_7086_() {
      return this.f_59645_;
   }

   protected void m_6520_(NonNullList<ItemStack> p_59674_) {
      this.f_59645_ = p_59674_;
   }

   public int[] m_7071_(Direction p_59672_) {
      return f_59644_;
   }

   public boolean m_7155_(int p_59663_, ItemStack p_59664_, @Nullable Direction p_59665_) {
      return !(Block.m_49814_(p_59664_.m_41720_()) instanceof ShulkerBoxBlock);
   }

   public boolean m_7157_(int p_59682_, ItemStack p_59683_, Direction p_59684_) {
      return true;
   }

   public float m_59657_(float p_59658_) {
      return Mth.m_14179_(p_59658_, this.f_59649_, this.f_59648_);
   }

   @Nullable
   public DyeColor m_59701_() {
      return this.f_59650_;
   }

   protected AbstractContainerMenu m_6555_(int p_59660_, Inventory p_59661_) {
      return new ShulkerBoxMenu(p_59660_, p_59661_, this);
   }

   public boolean m_59702_() {
      return this.f_59647_ == ShulkerBoxBlockEntity.AnimationStatus.CLOSED;
   }

   @Override
   protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
      return new net.minecraftforge.items.wrapper.SidedInvWrapper(this, Direction.UP);
   }

   public static enum AnimationStatus {
      CLOSED,
      OPENING,
      OPENED,
      CLOSING;
   }
}
