package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class ClientboundUpdateRecipesPacket implements Packet<ClientGamePacketListener> {
   private final List<Recipe<?>> f_133629_;

   public ClientboundUpdateRecipesPacket(Collection<Recipe<?>> p_133632_) {
      this.f_133629_ = Lists.newArrayList(p_133632_);
   }

   public ClientboundUpdateRecipesPacket(FriendlyByteBuf p_179468_) {
      this.f_133629_ = p_179468_.m_178366_(ClientboundUpdateRecipesPacket::m_133647_);
   }

   public void m_5779_(FriendlyByteBuf p_133646_) {
      p_133646_.m_178352_(this.f_133629_, ClientboundUpdateRecipesPacket::m_179469_);
   }

   public void m_5797_(ClientGamePacketListener p_133641_) {
      p_133641_.m_6327_(this);
   }

   public List<Recipe<?>> m_133644_() {
      return this.f_133629_;
   }

   public static Recipe<?> m_133647_(FriendlyByteBuf p_133648_) {
      ResourceLocation resourcelocation = p_133648_.m_130281_();
      ResourceLocation resourcelocation1 = p_133648_.m_130281_();
      return Registry.f_122865_.m_6612_(resourcelocation).orElseThrow(() -> {
         return new IllegalArgumentException("Unknown recipe serializer " + resourcelocation);
      }).m_8005_(resourcelocation1, p_133648_);
   }

   public static <T extends Recipe<?>> void m_179469_(FriendlyByteBuf p_179470_, T p_179471_) {
      p_179470_.m_130085_(Registry.f_122865_.m_7981_(p_179471_.m_7707_()));
      p_179470_.m_130085_(p_179471_.m_6423_());
      ((net.minecraft.world.item.crafting.RecipeSerializer<T>)p_179471_.m_7707_()).m_6178_(p_179470_, p_179471_);
   }
}