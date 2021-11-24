package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EntityDataAccessor implements DataAccessor {
   private static final SimpleCommandExceptionType f_139506_ = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.entity.invalid"));
   public static final Function<String, DataCommands.DataProvider> f_139505_ = (p_139517_) -> {
      return new DataCommands.DataProvider() {
         public DataAccessor m_7018_(CommandContext<CommandSourceStack> p_139530_) throws CommandSyntaxException {
            return new EntityDataAccessor(EntityArgument.m_91452_(p_139530_, p_139517_));
         }

         public ArgumentBuilder<CommandSourceStack, ?> m_7621_(ArgumentBuilder<CommandSourceStack, ?> p_139527_, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> p_139528_) {
            return p_139527_.then(Commands.m_82127_("entity").then(p_139528_.apply(Commands.m_82129_(p_139517_, EntityArgument.m_91449_()))));
         }
      };
   };
   private final Entity f_139507_;

   public EntityDataAccessor(Entity p_139510_) {
      this.f_139507_ = p_139510_;
   }

   public void m_7603_(CompoundTag p_139519_) throws CommandSyntaxException {
      if (this.f_139507_ instanceof Player) {
         throw f_139506_.create();
      } else {
         UUID uuid = this.f_139507_.m_142081_();
         this.f_139507_.m_20258_(p_139519_);
         this.f_139507_.m_20084_(uuid);
      }
   }

   public CompoundTag m_6184_() {
      return NbtPredicate.m_57485_(this.f_139507_);
   }

   public Component m_6934_() {
      return new TranslatableComponent("commands.data.entity.modified", this.f_139507_.m_5446_());
   }

   public Component m_7624_(Tag p_139521_) {
      return new TranslatableComponent("commands.data.entity.query", this.f_139507_.m_5446_(), NbtUtils.m_178061_(p_139521_));
   }

   public Component m_6066_(NbtPathArgument.NbtPath p_139513_, double p_139514_, int p_139515_) {
      return new TranslatableComponent("commands.data.entity.get", p_139513_, this.f_139507_.m_5446_(), String.format(Locale.ROOT, "%.2f", p_139514_), p_139515_);
   }
}