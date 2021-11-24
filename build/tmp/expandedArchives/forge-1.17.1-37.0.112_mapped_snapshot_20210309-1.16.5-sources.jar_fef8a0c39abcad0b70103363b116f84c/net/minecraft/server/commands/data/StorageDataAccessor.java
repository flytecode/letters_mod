package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.CommandStorage;

public class StorageDataAccessor implements DataAccessor {
   static final SuggestionProvider<CommandSourceStack> f_139532_ = (p_139547_, p_139548_) -> {
      return SharedSuggestionProvider.m_82957_(m_139560_(p_139547_).m_78036_(), p_139548_);
   };
   public static final Function<String, DataCommands.DataProvider> f_139531_ = (p_139554_) -> {
      return new DataCommands.DataProvider() {
         public DataAccessor m_7018_(CommandContext<CommandSourceStack> p_139570_) {
            return new StorageDataAccessor(StorageDataAccessor.m_139560_(p_139570_), ResourceLocationArgument.m_107011_(p_139570_, p_139554_));
         }

         public ArgumentBuilder<CommandSourceStack, ?> m_7621_(ArgumentBuilder<CommandSourceStack, ?> p_139567_, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> p_139568_) {
            return p_139567_.then(Commands.m_82127_("storage").then(p_139568_.apply(Commands.m_82129_(p_139554_, ResourceLocationArgument.m_106984_()).suggests(StorageDataAccessor.f_139532_))));
         }
      };
   };
   private final CommandStorage f_139533_;
   private final ResourceLocation f_139534_;

   static CommandStorage m_139560_(CommandContext<CommandSourceStack> p_139561_) {
      return p_139561_.getSource().m_81377_().m_129897_();
   }

   StorageDataAccessor(CommandStorage p_139537_, ResourceLocation p_139538_) {
      this.f_139533_ = p_139537_;
      this.f_139534_ = p_139538_;
   }

   public void m_7603_(CompoundTag p_139556_) {
      this.f_139533_.m_78046_(this.f_139534_, p_139556_);
   }

   public CompoundTag m_6184_() {
      return this.f_139533_.m_78044_(this.f_139534_);
   }

   public Component m_6934_() {
      return new TranslatableComponent("commands.data.storage.modified", this.f_139534_);
   }

   public Component m_7624_(Tag p_139558_) {
      return new TranslatableComponent("commands.data.storage.query", this.f_139534_, NbtUtils.m_178061_(p_139558_));
   }

   public Component m_6066_(NbtPathArgument.NbtPath p_139550_, double p_139551_, int p_139552_) {
      return new TranslatableComponent("commands.data.storage.get", p_139550_, this.f_139534_, String.format(Locale.ROOT, "%.2f", p_139551_), p_139552_);
   }
}