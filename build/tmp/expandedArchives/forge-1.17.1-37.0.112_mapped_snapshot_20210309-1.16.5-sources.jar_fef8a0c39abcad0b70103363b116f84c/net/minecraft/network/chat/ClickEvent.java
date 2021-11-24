package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickEvent {
   private final ClickEvent.Action f_130617_;
   private final String f_130618_;

   public ClickEvent(ClickEvent.Action p_130620_, String p_130621_) {
      this.f_130617_ = p_130620_;
      this.f_130618_ = p_130621_;
   }

   public ClickEvent.Action m_130622_() {
      return this.f_130617_;
   }

   public String m_130623_() {
      return this.f_130618_;
   }

   public boolean equals(Object p_130625_) {
      if (this == p_130625_) {
         return true;
      } else if (p_130625_ != null && this.getClass() == p_130625_.getClass()) {
         ClickEvent clickevent = (ClickEvent)p_130625_;
         if (this.f_130617_ != clickevent.f_130617_) {
            return false;
         } else {
            if (this.f_130618_ != null) {
               if (!this.f_130618_.equals(clickevent.f_130618_)) {
                  return false;
               }
            } else if (clickevent.f_130618_ != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "ClickEvent{action=" + this.f_130617_ + ", value='" + this.f_130618_ + "'}";
   }

   public int hashCode() {
      int i = this.f_130617_.hashCode();
      return 31 * i + (this.f_130618_ != null ? this.f_130618_.hashCode() : 0);
   }

   public static enum Action {
      OPEN_URL("open_url", true),
      OPEN_FILE("open_file", false),
      RUN_COMMAND("run_command", true),
      SUGGEST_COMMAND("suggest_command", true),
      CHANGE_PAGE("change_page", true),
      COPY_TO_CLIPBOARD("copy_to_clipboard", true);

      private static final Map<String, ClickEvent.Action> f_130634_ = Arrays.stream(values()).collect(Collectors.toMap(ClickEvent.Action::m_130649_, (p_130648_) -> {
         return p_130648_;
      }));
      private final boolean f_130635_;
      private final String f_130636_;

      private Action(String p_130642_, boolean p_130643_) {
         this.f_130636_ = p_130642_;
         this.f_130635_ = p_130643_;
      }

      public boolean m_130644_() {
         return this.f_130635_;
      }

      public String m_130649_() {
         return this.f_130636_;
      }

      public static ClickEvent.Action m_130645_(String p_130646_) {
         return f_130634_.get(p_130646_);
      }
   }
}