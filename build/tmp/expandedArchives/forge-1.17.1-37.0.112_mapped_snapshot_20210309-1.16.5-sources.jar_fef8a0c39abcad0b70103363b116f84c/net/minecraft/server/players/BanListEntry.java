package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public abstract class BanListEntry<T> extends StoredUserEntry<T> {
   public static final SimpleDateFormat f_10943_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   public static final String f_143953_ = "forever";
   protected final Date f_10944_;
   protected final String f_10945_;
   protected final Date f_10946_;
   protected final String f_10947_;

   public BanListEntry(T p_10953_, @Nullable Date p_10954_, @Nullable String p_10955_, @Nullable Date p_10956_, @Nullable String p_10957_) {
      super(p_10953_);
      this.f_10944_ = p_10954_ == null ? new Date() : p_10954_;
      this.f_10945_ = p_10955_ == null ? "(Unknown)" : p_10955_;
      this.f_10946_ = p_10956_;
      this.f_10947_ = p_10957_ == null ? "Banned by an operator." : p_10957_;
   }

   protected BanListEntry(T p_10950_, JsonObject p_10951_) {
      super(p_10950_);

      Date date;
      try {
         date = p_10951_.has("created") ? f_10943_.parse(p_10951_.get("created").getAsString()) : new Date();
      } catch (ParseException parseexception1) {
         date = new Date();
      }

      this.f_10944_ = date;
      this.f_10945_ = p_10951_.has("source") ? p_10951_.get("source").getAsString() : "(Unknown)";

      Date date1;
      try {
         date1 = p_10951_.has("expires") ? f_10943_.parse(p_10951_.get("expires").getAsString()) : null;
      } catch (ParseException parseexception) {
         date1 = null;
      }

      this.f_10946_ = date1;
      this.f_10947_ = p_10951_.has("reason") ? p_10951_.get("reason").getAsString() : "Banned by an operator.";
   }

   public Date m_143954_() {
      return this.f_10944_;
   }

   public String m_10960_() {
      return this.f_10945_;
   }

   public Date m_10961_() {
      return this.f_10946_;
   }

   public String m_10962_() {
      return this.f_10947_;
   }

   public abstract Component m_8003_();

   boolean m_7524_() {
      return this.f_10946_ == null ? false : this.f_10946_.before(new Date());
   }

   protected void m_6009_(JsonObject p_10959_) {
      p_10959_.addProperty("created", f_10943_.format(this.f_10944_));
      p_10959_.addProperty("source", this.f_10945_);
      p_10959_.addProperty("expires", this.f_10946_ == null ? "forever" : f_10943_.format(this.f_10946_));
      p_10959_.addProperty("reason", this.f_10947_);
   }
}