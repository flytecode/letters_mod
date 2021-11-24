package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockDataAccessor implements DataAccessor {
   static final SimpleCommandExceptionType f_139292_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.block.invalid"));
   public static final Function<String, DataCommands.DataProvider> f_139291_ = (p_139305_) -> {
      return new DataCommands.DataProvider() {
         public DataAccessor m_7018_(CommandContext<CommandSourceStack> p_139319_) throws CommandSyntaxException {
            BlockPos blockpos = BlockPosArgument.m_118242_(p_139319_, p_139305_ + "Pos");
            BlockEntity blockentity = p_139319_.getSource().m_81372_().m_7702_(blockpos);
            if (blockentity == null) {
               throw BlockDataAccessor.f_139292_.create();
            } else {
               return new BlockDataAccessor(blockentity, blockpos);
            }
         }

         public ArgumentBuilder<CommandSourceStack, ?> m_7621_(ArgumentBuilder<CommandSourceStack, ?> p_139316_, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> p_139317_) {
            return p_139316_.then(Commands.m_82127_("block").then(p_139317_.apply(Commands.m_82129_(p_139305_ + "Pos", BlockPosArgument.m_118239_()))));
         }
      };
   };
   private final BlockEntity f_139293_;
   private final BlockPos f_139294_;

   public BlockDataAccessor(BlockEntity p_139297_, BlockPos p_139298_) {
      this.f_139293_ = p_139297_;
      this.f_139294_ = p_139298_;
   }

   public void m_7603_(CompoundTag p_139307_) {
      p_139307_.m_128405_("x", this.f_139294_.m_123341_());
      p_139307_.m_128405_("y", this.f_139294_.m_123342_());
      p_139307_.m_128405_("z", this.f_139294_.m_123343_());
      BlockState blockstate = this.f_139293_.m_58904_().m_8055_(this.f_139294_);
      this.f_139293_.m_142466_(p_139307_);
      this.f_139293_.m_6596_();
      this.f_139293_.m_58904_().m_7260_(this.f_139294_, blockstate, blockstate, 3);
   }

   public CompoundTag m_6184_() {
      return this.f_139293_.m_6945_(new CompoundTag());
   }

   public Component m_6934_() {
      return new TranslatableComponent("commands.data.block.modified", this.f_139294_.m_123341_(), this.f_139294_.m_123342_(), this.f_139294_.m_123343_());
   }

   public Component m_7624_(Tag p_139309_) {
      return new TranslatableComponent("commands.data.block.query", this.f_139294_.m_123341_(), this.f_139294_.m_123342_(), this.f_139294_.m_123343_(), NbtUtils.m_178061_(p_139309_));
   }

   public Component m_6066_(NbtPathArgument.NbtPath p_139301_, double p_139302_, int p_139303_) {
      return new TranslatableComponent("commands.data.block.get", p_139301_, this.f_139294_.m_123341_(), this.f_139294_.m_123342_(), this.f_139294_.m_123343_(), String.format(Locale.ROOT, "%.2f", p_139302_), p_139303_);
   }
}