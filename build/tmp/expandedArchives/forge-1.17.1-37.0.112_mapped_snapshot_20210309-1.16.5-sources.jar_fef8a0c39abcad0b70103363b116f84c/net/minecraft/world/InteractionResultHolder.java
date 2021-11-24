package net.minecraft.world;

public class InteractionResultHolder<T> {
   private final InteractionResult f_19084_;
   private final T f_19085_;

   public InteractionResultHolder(InteractionResult p_19087_, T p_19088_) {
      this.f_19084_ = p_19087_;
      this.f_19085_ = p_19088_;
   }

   public InteractionResult m_19089_() {
      return this.f_19084_;
   }

   public T m_19095_() {
      return this.f_19085_;
   }

   public static <T> InteractionResultHolder<T> m_19090_(T p_19091_) {
      return new InteractionResultHolder<>(InteractionResult.SUCCESS, p_19091_);
   }

   public static <T> InteractionResultHolder<T> m_19096_(T p_19097_) {
      return new InteractionResultHolder<>(InteractionResult.CONSUME, p_19097_);
   }

   public static <T> InteractionResultHolder<T> m_19098_(T p_19099_) {
      return new InteractionResultHolder<>(InteractionResult.PASS, p_19099_);
   }

   public static <T> InteractionResultHolder<T> m_19100_(T p_19101_) {
      return new InteractionResultHolder<>(InteractionResult.FAIL, p_19101_);
   }

   public static <T> InteractionResultHolder<T> m_19092_(T p_19093_, boolean p_19094_) {
      return p_19094_ ? m_19090_(p_19093_) : m_19096_(p_19093_);
   }
}