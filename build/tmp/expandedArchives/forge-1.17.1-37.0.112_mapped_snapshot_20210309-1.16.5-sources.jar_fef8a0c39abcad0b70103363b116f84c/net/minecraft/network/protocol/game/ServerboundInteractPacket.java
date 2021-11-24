package net.minecraft.network.protocol.game;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ServerboundInteractPacket implements Packet<ServerGamePacketListener> {
   private final int f_134030_;
   private final ServerboundInteractPacket.Action f_134031_;
   private final boolean f_134034_;
   static final ServerboundInteractPacket.Action f_179595_ = new ServerboundInteractPacket.Action() {
      public ServerboundInteractPacket.ActionType m_142249_() {
         return ServerboundInteractPacket.ActionType.ATTACK;
      }

      public void m_142457_(ServerboundInteractPacket.Handler p_179624_) {
         p_179624_.m_141994_();
      }

      public void m_142450_(FriendlyByteBuf p_179622_) {
      }
   };

   private ServerboundInteractPacket(int p_179598_, boolean p_179599_, ServerboundInteractPacket.Action p_179600_) {
      this.f_134030_ = p_179598_;
      this.f_134031_ = p_179600_;
      this.f_134034_ = p_179599_;
   }

   public static ServerboundInteractPacket m_179605_(Entity p_179606_, boolean p_179607_) {
      return new ServerboundInteractPacket(p_179606_.m_142049_(), p_179607_, f_179595_);
   }

   public static ServerboundInteractPacket m_179608_(Entity p_179609_, boolean p_179610_, InteractionHand p_179611_) {
      return new ServerboundInteractPacket(p_179609_.m_142049_(), p_179610_, new ServerboundInteractPacket.InteractionAction(p_179611_));
   }

   public static ServerboundInteractPacket m_179612_(Entity p_179613_, boolean p_179614_, InteractionHand p_179615_, Vec3 p_179616_) {
      return new ServerboundInteractPacket(p_179613_.m_142049_(), p_179614_, new ServerboundInteractPacket.InteractionAtLocationAction(p_179615_, p_179616_));
   }

   public ServerboundInteractPacket(FriendlyByteBuf p_179602_) {
      this.f_134030_ = p_179602_.m_130242_();
      ServerboundInteractPacket.ActionType serverboundinteractpacket$actiontype = p_179602_.m_130066_(ServerboundInteractPacket.ActionType.class);
      this.f_134031_ = serverboundinteractpacket$actiontype.f_179630_.apply(p_179602_);
      this.f_134034_ = p_179602_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_134058_) {
      p_134058_.m_130130_(this.f_134030_);
      p_134058_.m_130068_(this.f_134031_.m_142249_());
      this.f_134031_.m_142450_(p_134058_);
      p_134058_.writeBoolean(this.f_134034_);
   }

   public void m_5797_(ServerGamePacketListener p_134055_) {
      p_134055_.m_6946_(this);
   }

   @Nullable
   public Entity m_179603_(ServerLevel p_179604_) {
      return p_179604_.m_143317_(this.f_134030_);
   }

   public boolean m_134061_() {
      return this.f_134034_;
   }

   public void m_179617_(ServerboundInteractPacket.Handler p_179618_) {
      this.f_134031_.m_142457_(p_179618_);
   }

   interface Action {
      ServerboundInteractPacket.ActionType m_142249_();

      void m_142457_(ServerboundInteractPacket.Handler p_179626_);

      void m_142450_(FriendlyByteBuf p_179625_);
   }

   static enum ActionType {
      INTERACT(ServerboundInteractPacket.InteractionAction::new),
      ATTACK((p_179639_) -> {
         return ServerboundInteractPacket.f_179595_;
      }),
      INTERACT_AT(ServerboundInteractPacket.InteractionAtLocationAction::new);

      final Function<FriendlyByteBuf, ServerboundInteractPacket.Action> f_179630_;

      private ActionType(Function<FriendlyByteBuf, ServerboundInteractPacket.Action> p_179636_) {
         this.f_179630_ = p_179636_;
      }
   }

   public interface Handler {
      void m_142299_(InteractionHand p_179643_);

      void m_142143_(InteractionHand p_179644_, Vec3 p_179645_);

      void m_141994_();
   }

   static class InteractionAction implements ServerboundInteractPacket.Action {
      private final InteractionHand f_179646_;

      InteractionAction(InteractionHand p_179648_) {
         this.f_179646_ = p_179648_;
      }

      private InteractionAction(FriendlyByteBuf p_179650_) {
         this.f_179646_ = p_179650_.m_130066_(InteractionHand.class);
      }

      public ServerboundInteractPacket.ActionType m_142249_() {
         return ServerboundInteractPacket.ActionType.INTERACT;
      }

      public void m_142457_(ServerboundInteractPacket.Handler p_179655_) {
         p_179655_.m_142299_(this.f_179646_);
      }

      public void m_142450_(FriendlyByteBuf p_179653_) {
         p_179653_.m_130068_(this.f_179646_);
      }
   }

   static class InteractionAtLocationAction implements ServerboundInteractPacket.Action {
      private final InteractionHand f_179656_;
      private final Vec3 f_179657_;

      InteractionAtLocationAction(InteractionHand p_179659_, Vec3 p_179660_) {
         this.f_179656_ = p_179659_;
         this.f_179657_ = p_179660_;
      }

      private InteractionAtLocationAction(FriendlyByteBuf p_179662_) {
         this.f_179657_ = new Vec3((double)p_179662_.readFloat(), (double)p_179662_.readFloat(), (double)p_179662_.readFloat());
         this.f_179656_ = p_179662_.m_130066_(InteractionHand.class);
      }

      public ServerboundInteractPacket.ActionType m_142249_() {
         return ServerboundInteractPacket.ActionType.INTERACT_AT;
      }

      public void m_142457_(ServerboundInteractPacket.Handler p_179667_) {
         p_179667_.m_142143_(this.f_179656_, this.f_179657_);
      }

      public void m_142450_(FriendlyByteBuf p_179665_) {
         p_179665_.writeFloat((float)this.f_179657_.f_82479_);
         p_179665_.writeFloat((float)this.f_179657_.f_82480_);
         p_179665_.writeFloat((float)this.f_179657_.f_82481_);
         p_179665_.m_130068_(this.f_179656_);
      }
   }
}