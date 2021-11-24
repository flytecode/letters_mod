package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemInput implements Predicate<ItemStack> {
   private static final Dynamic2CommandExceptionType f_120972_ = new Dynamic2CommandExceptionType((p_120986_, p_120987_) -> {
      return new TranslatableComponent("arguments.item.overstacked", p_120986_, p_120987_);
   });
   private final Item f_120973_;
   @Nullable
   private final CompoundTag f_120974_;

   public ItemInput(Item p_120977_, @Nullable CompoundTag p_120978_) {
      this.f_120973_ = p_120977_;
      this.f_120974_ = p_120978_;
   }

   public Item m_120979_() {
      return this.f_120973_;
   }

   public boolean test(ItemStack p_120984_) {
      return p_120984_.m_150930_(this.f_120973_) && NbtUtils.m_129235_(this.f_120974_, p_120984_.m_41783_(), true);
   }

   public ItemStack m_120980_(int p_120981_, boolean p_120982_) throws CommandSyntaxException {
      ItemStack itemstack = new ItemStack(this.f_120973_, p_120981_);
      if (this.f_120974_ != null) {
         itemstack.m_41751_(this.f_120974_);
      }

      if (p_120982_ && p_120981_ > itemstack.m_41741_()) {
         throw f_120972_.create(Registry.f_122827_.m_7981_(this.f_120973_), itemstack.m_41741_());
      } else {
         return itemstack;
      }
   }

   public String m_120988_() {
      StringBuilder stringbuilder = new StringBuilder(Registry.f_122827_.m_7447_(this.f_120973_));
      if (this.f_120974_ != null) {
         stringbuilder.append((Object)this.f_120974_);
      }

      return stringbuilder.toString();
   }
}