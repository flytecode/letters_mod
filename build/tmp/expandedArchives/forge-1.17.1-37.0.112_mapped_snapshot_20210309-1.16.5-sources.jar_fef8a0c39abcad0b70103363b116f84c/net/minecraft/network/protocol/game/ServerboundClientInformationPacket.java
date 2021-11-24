package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;

public class ServerboundClientInformationPacket implements Packet<ServerGamePacketListener> {
   public static final int f_179549_ = 16;
   private final String f_133863_;
   private final int f_133864_;
   private final ChatVisiblity f_133865_;
   private final boolean f_133866_;
   private final int f_133867_;
   private final HumanoidArm f_133868_;
   private final boolean f_179550_;

   public ServerboundClientInformationPacket(String p_179552_, int p_179553_, ChatVisiblity p_179554_, boolean p_179555_, int p_179556_, HumanoidArm p_179557_, boolean p_179558_) {
      this.f_133863_ = p_179552_;
      this.f_133864_ = p_179553_;
      this.f_133865_ = p_179554_;
      this.f_133866_ = p_179555_;
      this.f_133867_ = p_179556_;
      this.f_133868_ = p_179557_;
      this.f_179550_ = p_179558_;
   }

   public ServerboundClientInformationPacket(FriendlyByteBuf p_179560_) {
      this.f_133863_ = p_179560_.m_130136_(16);
      this.f_133864_ = p_179560_.readByte();
      this.f_133865_ = p_179560_.m_130066_(ChatVisiblity.class);
      this.f_133866_ = p_179560_.readBoolean();
      this.f_133867_ = p_179560_.readUnsignedByte();
      this.f_133868_ = p_179560_.m_130066_(HumanoidArm.class);
      this.f_179550_ = p_179560_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_133884_) {
      p_133884_.m_130070_(this.f_133863_);
      p_133884_.writeByte(this.f_133864_);
      p_133884_.m_130068_(this.f_133865_);
      p_133884_.writeBoolean(this.f_133866_);
      p_133884_.writeByte(this.f_133867_);
      p_133884_.m_130068_(this.f_133868_);
      p_133884_.writeBoolean(this.f_179550_);
   }

   public void m_5797_(ServerGamePacketListener p_133882_) {
      p_133882_.m_5617_(this);
   }

   public String m_179561_() {
      return this.f_133863_;
   }

   public int m_179562_() {
      return this.f_133864_;
   }

   public ChatVisiblity m_133885_() {
      return this.f_133865_;
   }

   public boolean m_133886_() {
      return this.f_133866_;
   }

   public int m_133887_() {
      return this.f_133867_;
   }

   public HumanoidArm m_133888_() {
      return this.f_133868_;
   }

   public boolean m_179563_() {
      return this.f_179550_;
   }
}