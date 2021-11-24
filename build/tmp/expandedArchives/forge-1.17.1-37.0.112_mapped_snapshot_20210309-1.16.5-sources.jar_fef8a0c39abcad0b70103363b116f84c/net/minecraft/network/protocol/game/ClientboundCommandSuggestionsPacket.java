package net.minecraft.network.protocol.game;

import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.protocol.Packet;

public class ClientboundCommandSuggestionsPacket implements Packet<ClientGamePacketListener> {
   private final int f_131842_;
   private final Suggestions f_131843_;

   public ClientboundCommandSuggestionsPacket(int p_131846_, Suggestions p_131847_) {
      this.f_131842_ = p_131846_;
      this.f_131843_ = p_131847_;
   }

   public ClientboundCommandSuggestionsPacket(FriendlyByteBuf p_178790_) {
      this.f_131842_ = p_178790_.m_130242_();
      int i = p_178790_.m_130242_();
      int j = p_178790_.m_130242_();
      StringRange stringrange = StringRange.between(i, i + j);
      List<Suggestion> list = p_178790_.m_178366_((p_178793_) -> {
         String s = p_178793_.m_130277_();
         Component component = p_178793_.readBoolean() ? p_178793_.m_130238_() : null;
         return new Suggestion(stringrange, s, component);
      });
      this.f_131843_ = new Suggestions(stringrange, list);
   }

   public void m_5779_(FriendlyByteBuf p_131856_) {
      p_131856_.m_130130_(this.f_131842_);
      p_131856_.m_130130_(this.f_131843_.getRange().getStart());
      p_131856_.m_130130_(this.f_131843_.getRange().getLength());
      p_131856_.m_178352_(this.f_131843_.getList(), (p_178795_, p_178796_) -> {
         p_178795_.m_130070_(p_178796_.getText());
         p_178795_.writeBoolean(p_178796_.getTooltip() != null);
         if (p_178796_.getTooltip() != null) {
            p_178795_.m_130083_(ComponentUtils.m_130729_(p_178796_.getTooltip()));
         }

      });
   }

   public void m_5797_(ClientGamePacketListener p_131853_) {
      p_131853_.m_7589_(this);
   }

   public int m_131854_() {
      return this.f_131842_;
   }

   public Suggestions m_131857_() {
      return this.f_131843_;
   }
}