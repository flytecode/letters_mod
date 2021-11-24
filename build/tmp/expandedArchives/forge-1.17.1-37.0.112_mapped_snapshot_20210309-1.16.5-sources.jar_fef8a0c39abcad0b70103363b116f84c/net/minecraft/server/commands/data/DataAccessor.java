package net.minecraft.server.commands.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public interface DataAccessor {
   void m_7603_(CompoundTag p_139323_) throws CommandSyntaxException;

   CompoundTag m_6184_() throws CommandSyntaxException;

   Component m_6934_();

   Component m_7624_(Tag p_139324_);

   Component m_6066_(NbtPathArgument.NbtPath p_139320_, double p_139321_, int p_139322_);
}