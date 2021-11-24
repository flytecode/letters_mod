package net.minecraft.network.protocol.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory;

public class ClientboundStatusResponsePacket implements Packet<ClientStatusPacketListener> {
   public static final Gson f_134885_ = (new GsonBuilder()).registerTypeAdapter(ServerStatus.Version.class, new ServerStatus.Version.Serializer()).registerTypeAdapter(ServerStatus.Players.class, new ServerStatus.Players.Serializer()).registerTypeAdapter(ServerStatus.class, new ServerStatus.Serializer()).registerTypeHierarchyAdapter(Component.class, new Component.Serializer()).registerTypeHierarchyAdapter(Style.class, new Style.Serializer()).registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory()).create();
   private final ServerStatus f_134886_;

   public ClientboundStatusResponsePacket(ServerStatus p_134890_) {
      this.f_134886_ = p_134890_;
   }

   public ClientboundStatusResponsePacket(FriendlyByteBuf p_179834_) {
      this.f_134886_ = GsonHelper.m_13794_(f_134885_, p_179834_.m_130136_(32767), ServerStatus.class);
   }

   public void m_5779_(FriendlyByteBuf p_134899_) {
      p_134899_.m_130070_(this.f_134886_.getJson()); //Forge: Let the response cache the JSON
   }

   public void m_5797_(ClientStatusPacketListener p_134896_) {
      p_134896_.m_6440_(this);
   }

   public ServerStatus m_134897_() {
      return this.f_134886_;
   }
}
