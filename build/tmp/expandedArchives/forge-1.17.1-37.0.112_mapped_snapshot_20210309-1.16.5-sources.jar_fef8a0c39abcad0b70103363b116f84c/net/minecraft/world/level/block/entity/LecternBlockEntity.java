package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class LecternBlockEntity extends BlockEntity implements Clearable, MenuProvider {
   public static final int f_155617_ = 0;
   public static final int f_155618_ = 1;
   public static final int f_155619_ = 0;
   public static final int f_155620_ = 1;
   private final Container f_59525_ = new Container() {
      public int m_6643_() {
         return 1;
      }

      public boolean m_7983_() {
         return LecternBlockEntity.this.f_59527_.m_41619_();
      }

      public ItemStack m_8020_(int p_59580_) {
         return p_59580_ == 0 ? LecternBlockEntity.this.f_59527_ : ItemStack.f_41583_;
      }

      public ItemStack m_7407_(int p_59582_, int p_59583_) {
         if (p_59582_ == 0) {
            ItemStack itemstack = LecternBlockEntity.this.f_59527_.m_41620_(p_59583_);
            if (LecternBlockEntity.this.f_59527_.m_41619_()) {
               LecternBlockEntity.this.m_59570_();
            }

            return itemstack;
         } else {
            return ItemStack.f_41583_;
         }
      }

      public ItemStack m_8016_(int p_59590_) {
         if (p_59590_ == 0) {
            ItemStack itemstack = LecternBlockEntity.this.f_59527_;
            LecternBlockEntity.this.f_59527_ = ItemStack.f_41583_;
            LecternBlockEntity.this.m_59570_();
            return itemstack;
         } else {
            return ItemStack.f_41583_;
         }
      }

      public void m_6836_(int p_59585_, ItemStack p_59586_) {
      }

      public int m_6893_() {
         return 1;
      }

      public void m_6596_() {
         LecternBlockEntity.this.m_6596_();
      }

      public boolean m_6542_(Player p_59588_) {
         if (LecternBlockEntity.this.f_58857_.m_7702_(LecternBlockEntity.this.f_58858_) != LecternBlockEntity.this) {
            return false;
         } else {
            return p_59588_.m_20275_((double)LecternBlockEntity.this.f_58858_.m_123341_() + 0.5D, (double)LecternBlockEntity.this.f_58858_.m_123342_() + 0.5D, (double)LecternBlockEntity.this.f_58858_.m_123343_() + 0.5D) > 64.0D ? false : LecternBlockEntity.this.m_59567_();
         }
      }

      public boolean m_7013_(int p_59592_, ItemStack p_59593_) {
         return false;
      }

      public void m_6211_() {
      }
   };
   private final ContainerData f_59526_ = new ContainerData() {
      public int m_6413_(int p_59600_) {
         return p_59600_ == 0 ? LecternBlockEntity.this.f_59528_ : 0;
      }

      public void m_8050_(int p_59602_, int p_59603_) {
         if (p_59602_ == 0) {
            LecternBlockEntity.this.m_59532_(p_59603_);
         }

      }

      public int m_6499_() {
         return 1;
      }
   };
   ItemStack f_59527_ = ItemStack.f_41583_;
   int f_59528_;
   private int f_59529_;

   public LecternBlockEntity(BlockPos p_155622_, BlockState p_155623_) {
      super(BlockEntityType.f_58908_, p_155622_, p_155623_);
   }

   public ItemStack m_59566_() {
      return this.f_59527_;
   }

   public boolean m_59567_() {
      return this.f_59527_.m_150930_(Items.f_42614_) || this.f_59527_.m_150930_(Items.f_42615_);
   }

   public void m_59536_(ItemStack p_59537_) {
      this.m_59538_(p_59537_, (Player)null);
   }

   void m_59570_() {
      this.f_59528_ = 0;
      this.f_59529_ = 0;
      LecternBlock.m_54497_(this.m_58904_(), this.m_58899_(), this.m_58900_(), false);
   }

   public void m_59538_(ItemStack p_59539_, @Nullable Player p_59540_) {
      this.f_59527_ = this.m_59554_(p_59539_, p_59540_);
      this.f_59528_ = 0;
      this.f_59529_ = WrittenBookItem.m_43477_(this.f_59527_);
      this.m_6596_();
   }

   void m_59532_(int p_59533_) {
      int i = Mth.m_14045_(p_59533_, 0, this.f_59529_ - 1);
      if (i != this.f_59528_) {
         this.f_59528_ = i;
         this.m_6596_();
         LecternBlock.m_54488_(this.m_58904_(), this.m_58899_(), this.m_58900_());
      }

   }

   public int m_59568_() {
      return this.f_59528_;
   }

   public int m_59569_() {
      float f = this.f_59529_ > 1 ? (float)this.m_59568_() / ((float)this.f_59529_ - 1.0F) : 1.0F;
      return Mth.m_14143_(f * 14.0F) + (this.m_59567_() ? 1 : 0);
   }

   private ItemStack m_59554_(ItemStack p_59555_, @Nullable Player p_59556_) {
      if (this.f_58857_ instanceof ServerLevel && p_59555_.m_150930_(Items.f_42615_)) {
         WrittenBookItem.m_43461_(p_59555_, this.m_59534_(p_59556_), p_59556_);
      }

      return p_59555_;
   }

   private CommandSourceStack m_59534_(@Nullable Player p_59535_) {
      String s;
      Component component;
      if (p_59535_ == null) {
         s = "Lectern";
         component = new TextComponent("Lectern");
      } else {
         s = p_59535_.m_7755_().getString();
         component = p_59535_.m_5446_();
      }

      Vec3 vec3 = Vec3.m_82512_(this.f_58858_);
      return new CommandSourceStack(CommandSource.f_80164_, vec3, Vec2.f_82462_, (ServerLevel)this.f_58857_, 2, s, component, this.f_58857_.m_142572_(), p_59535_);
   }

   public boolean m_6326_() {
      return true;
   }

   public void m_142466_(CompoundTag p_155625_) {
      super.m_142466_(p_155625_);
      if (p_155625_.m_128425_("Book", 10)) {
         this.f_59527_ = this.m_59554_(ItemStack.m_41712_(p_155625_.m_128469_("Book")), (Player)null);
      } else {
         this.f_59527_ = ItemStack.f_41583_;
      }

      this.f_59529_ = WrittenBookItem.m_43477_(this.f_59527_);
      this.f_59528_ = Mth.m_14045_(p_155625_.m_128451_("Page"), 0, this.f_59529_ - 1);
   }

   public CompoundTag m_6945_(CompoundTag p_59553_) {
      super.m_6945_(p_59553_);
      if (!this.m_59566_().m_41619_()) {
         p_59553_.m_128365_("Book", this.m_59566_().m_41739_(new CompoundTag()));
         p_59553_.m_128405_("Page", this.f_59528_);
      }

      return p_59553_;
   }

   public void m_6211_() {
      this.m_59536_(ItemStack.f_41583_);
   }

   public AbstractContainerMenu m_7208_(int p_59562_, Inventory p_59563_, Player p_59564_) {
      return new LecternMenu(p_59562_, this.f_59525_, this.f_59526_);
   }

   public Component m_5446_() {
      return new TranslatableComponent("container.lectern");
   }
}