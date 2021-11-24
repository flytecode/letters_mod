package net.minecraft;

public class ReportedException extends RuntimeException {
   private final CrashReport f_134758_;

   public ReportedException(CrashReport p_134760_) {
      this.f_134758_ = p_134760_;
   }

   public CrashReport m_134761_() {
      return this.f_134758_;
   }

   public Throwable getCause() {
      return this.f_134758_.m_127524_();
   }

   public String getMessage() {
      return this.f_134758_.m_127511_();
   }
}