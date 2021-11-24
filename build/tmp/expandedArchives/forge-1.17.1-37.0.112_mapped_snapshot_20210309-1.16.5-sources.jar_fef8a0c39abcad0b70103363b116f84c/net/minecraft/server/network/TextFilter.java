package net.minecraft.server.network;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TextFilter {
   TextFilter f_143703_ = new TextFilter() {
      public void m_7674_() {
      }

      public void m_7670_() {
      }

      public CompletableFuture<TextFilter.FilteredText> m_6770_(String p_143708_) {
         return CompletableFuture.completedFuture(TextFilter.FilteredText.m_143720_(p_143708_));
      }

      public CompletableFuture<List<TextFilter.FilteredText>> m_5925_(List<String> p_143710_) {
         return CompletableFuture.completedFuture(p_143710_.stream().map(TextFilter.FilteredText::m_143720_).collect(ImmutableList.toImmutableList()));
      }
   };

   void m_7674_();

   void m_7670_();

   CompletableFuture<TextFilter.FilteredText> m_6770_(String p_10096_);

   CompletableFuture<List<TextFilter.FilteredText>> m_5925_(List<String> p_10097_);

   public static class FilteredText {
      public static final TextFilter.FilteredText f_143712_ = new TextFilter.FilteredText("", "");
      private final String f_143713_;
      private final String f_143714_;

      public FilteredText(String p_143717_, String p_143718_) {
         this.f_143713_ = p_143717_;
         this.f_143714_ = p_143718_;
      }

      public String m_143719_() {
         return this.f_143713_;
      }

      public String m_143722_() {
         return this.f_143714_;
      }

      public static TextFilter.FilteredText m_143720_(String p_143721_) {
         return new TextFilter.FilteredText(p_143721_, p_143721_);
      }

      public static TextFilter.FilteredText m_143723_(String p_143724_) {
         return new TextFilter.FilteredText(p_143724_, "");
      }
   }
}