package net.minecraft.client.gui.screens.packs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PackSelectionModel {
   private final PackRepository f_99902_;
   final List<Pack> f_99903_;
   final List<Pack> f_99904_;
   final Function<Pack, ResourceLocation> f_99905_;
   final Runnable f_99906_;
   private final Consumer<PackRepository> f_99907_;

   public PackSelectionModel(Runnable p_99909_, Function<Pack, ResourceLocation> p_99910_, PackRepository p_99911_, Consumer<PackRepository> p_99912_) {
      this.f_99906_ = p_99909_;
      this.f_99905_ = p_99910_;
      this.f_99902_ = p_99911_;
      this.f_99903_ = Lists.newArrayList(p_99911_.m_10524_());
      Collections.reverse(this.f_99903_);
      this.f_99904_ = Lists.newArrayList(p_99911_.m_10519_());
      this.f_99904_.removeAll(this.f_99903_);
      this.f_99907_ = p_99912_;
   }

   public Stream<PackSelectionModel.Entry> m_99913_() {
      return this.f_99904_.stream().map((p_99920_) -> {
         return new PackSelectionModel.UnselectedPackEntry(p_99920_);
      });
   }

   public Stream<PackSelectionModel.Entry> m_99918_() {
      return this.f_99903_.stream().map((p_99915_) -> {
         return new PackSelectionModel.SelectedPackEntry(p_99915_);
      });
   }

   public void m_99923_() {
      this.f_99902_.m_10509_(Lists.reverse(this.f_99903_).stream().map(Pack::m_10446_).collect(ImmutableList.toImmutableList()));
      this.f_99907_.accept(this.f_99902_);
   }

   public void m_99926_() {
      this.f_99902_.m_10506_();
      this.f_99903_.retainAll(this.f_99902_.m_10519_());
      this.f_99904_.clear();
      this.f_99904_.addAll(this.f_99902_.m_10519_());
      this.f_99904_.removeAll(this.f_99903_);
   }

   @OnlyIn(Dist.CLIENT)
   public interface Entry {
      ResourceLocation m_6876_();

      PackCompatibility m_7709_();

      Component m_7356_();

      Component m_7359_();

      PackSource m_7485_();

      default Component m_99929_() {
         return this.m_7485_().m_10540_(this.m_7359_());
      }

      boolean m_7867_();

      boolean m_7844_();

      void m_7849_();

      void m_7850_();

      void m_7852_();

      void m_7845_();

      boolean m_7857_();

      default boolean m_99930_() {
         return !this.m_7857_();
      }

      default boolean m_99931_() {
         return this.m_7857_() && !this.m_7844_();
      }

      boolean m_7802_();

      boolean m_7803_();

      default boolean notHidden() { return true; }
   }

   @OnlyIn(Dist.CLIENT)
   abstract class EntryBase implements PackSelectionModel.Entry {
      private final Pack f_99933_;

      public EntryBase(Pack p_99936_) {
         this.f_99933_ = p_99936_;
      }

      protected abstract List<Pack> m_6956_();

      protected abstract List<Pack> m_6958_();

      public ResourceLocation m_6876_() {
         return PackSelectionModel.this.f_99905_.apply(this.f_99933_);
      }

      public PackCompatibility m_7709_() {
         return this.f_99933_.m_10443_();
      }

      public Component m_7356_() {
         return this.f_99933_.m_10429_();
      }

      public Component m_7359_() {
         return this.f_99933_.m_10442_();
      }

      public PackSource m_7485_() {
         return this.f_99933_.m_10453_();
      }

      public boolean m_7867_() {
         return this.f_99933_.m_10450_();
      }

      public boolean m_7844_() {
         return this.f_99933_.m_10449_();
      }

      protected void m_99950_() {
         this.m_6956_().remove(this.f_99933_);
         this.f_99933_.m_10451_().m_10470_(this.m_6958_(), this.f_99933_, Function.identity(), true);
         PackSelectionModel.this.f_99906_.run();
      }

      protected void m_99938_(int p_99939_) {
         List<Pack> list = this.m_6956_();
         int i = list.indexOf(this.f_99933_);
         list.remove(i);
         list.add(i + p_99939_, this.f_99933_);
         PackSelectionModel.this.f_99906_.run();
      }

      public boolean m_7802_() {
         List<Pack> list = this.m_6956_();
         int i = list.indexOf(this.f_99933_);
         return i > 0 && !list.get(i - 1).m_10450_();
      }

      public void m_7852_() {
         this.m_99938_(-1);
      }

      public boolean m_7803_() {
         List<Pack> list = this.m_6956_();
         int i = list.indexOf(this.f_99933_);
         return i >= 0 && i < list.size() - 1 && !list.get(i + 1).m_10450_();
      }

      public void m_7845_() {
         this.m_99938_(1);
      }

      @Override
      public boolean notHidden() {
          return !f_99933_.isHidden();
      }
   }

   @OnlyIn(Dist.CLIENT)
   class SelectedPackEntry extends PackSelectionModel.EntryBase {
      public SelectedPackEntry(Pack p_99954_) {
         super(p_99954_);
      }

      protected List<Pack> m_6956_() {
         return PackSelectionModel.this.f_99903_;
      }

      protected List<Pack> m_6958_() {
         return PackSelectionModel.this.f_99904_;
      }

      public boolean m_7857_() {
         return true;
      }

      public void m_7849_() {
      }

      public void m_7850_() {
         this.m_99950_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   class UnselectedPackEntry extends PackSelectionModel.EntryBase {
      public UnselectedPackEntry(Pack p_99963_) {
         super(p_99963_);
      }

      protected List<Pack> m_6956_() {
         return PackSelectionModel.this.f_99904_;
      }

      protected List<Pack> m_6958_() {
         return PackSelectionModel.this.f_99903_;
      }

      public boolean m_7857_() {
         return false;
      }

      public void m_7849_() {
         this.m_99950_();
      }

      public void m_7850_() {
      }
   }
}
