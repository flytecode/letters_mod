package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NbtPathArgument implements ArgumentType<NbtPathArgument.NbtPath> {
   private static final Collection<String> f_99484_ = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
   public static final SimpleCommandExceptionType f_99482_ = new SimpleCommandExceptionType(new TranslatableComponent("arguments.nbtpath.node.invalid"));
   public static final DynamicCommandExceptionType f_99483_ = new DynamicCommandExceptionType((p_99502_) -> {
      return new TranslatableComponent("arguments.nbtpath.nothing_found", p_99502_);
   });
   private static final char f_169530_ = '[';
   private static final char f_169531_ = ']';
   private static final char f_169532_ = '{';
   private static final char f_169533_ = '}';
   private static final char f_169534_ = '"';

   public static NbtPathArgument m_99487_() {
      return new NbtPathArgument();
   }

   public static NbtPathArgument.NbtPath m_99498_(CommandContext<CommandSourceStack> p_99499_, String p_99500_) {
      return p_99499_.getArgument(p_99500_, NbtPathArgument.NbtPath.class);
   }

   public NbtPathArgument.NbtPath parse(StringReader p_99491_) throws CommandSyntaxException {
      List<NbtPathArgument.Node> list = Lists.newArrayList();
      int i = p_99491_.getCursor();
      Object2IntMap<NbtPathArgument.Node> object2intmap = new Object2IntOpenHashMap<>();
      boolean flag = true;

      while(p_99491_.canRead() && p_99491_.peek() != ' ') {
         NbtPathArgument.Node nbtpathargument$node = m_99495_(p_99491_, flag);
         list.add(nbtpathargument$node);
         object2intmap.put(nbtpathargument$node, p_99491_.getCursor() - i);
         flag = false;
         if (p_99491_.canRead()) {
            char c0 = p_99491_.peek();
            if (c0 != ' ' && c0 != '[' && c0 != '{') {
               p_99491_.expect('.');
            }
         }
      }

      return new NbtPathArgument.NbtPath(p_99491_.getString().substring(i, p_99491_.getCursor()), list.toArray(new NbtPathArgument.Node[0]), object2intmap);
   }

   private static NbtPathArgument.Node m_99495_(StringReader p_99496_, boolean p_99497_) throws CommandSyntaxException {
      switch(p_99496_.peek()) {
      case '"':
         String s = p_99496_.readString();
         return m_99492_(p_99496_, s);
      case '[':
         p_99496_.skip();
         int j = p_99496_.peek();
         if (j == 123) {
            CompoundTag compoundtag1 = (new TagParser(p_99496_)).m_129373_();
            p_99496_.expect(']');
            return new NbtPathArgument.MatchElementNode(compoundtag1);
         } else {
            if (j == 93) {
               p_99496_.skip();
               return NbtPathArgument.AllElementsNode.f_99515_;
            }

            int i = p_99496_.readInt();
            p_99496_.expect(']');
            return new NbtPathArgument.IndexedElementNode(i);
         }
      case '{':
         if (!p_99497_) {
            throw f_99482_.createWithContext(p_99496_);
         }

         CompoundTag compoundtag = (new TagParser(p_99496_)).m_129373_();
         return new NbtPathArgument.MatchRootObjectNode(compoundtag);
      default:
         String s1 = m_99508_(p_99496_);
         return m_99492_(p_99496_, s1);
      }
   }

   private static NbtPathArgument.Node m_99492_(StringReader p_99493_, String p_99494_) throws CommandSyntaxException {
      if (p_99493_.canRead() && p_99493_.peek() == '{') {
         CompoundTag compoundtag = (new TagParser(p_99493_)).m_129373_();
         return new NbtPathArgument.MatchObjectNode(p_99494_, compoundtag);
      } else {
         return new NbtPathArgument.CompoundChildNode(p_99494_);
      }
   }

   private static String m_99508_(StringReader p_99509_) throws CommandSyntaxException {
      int i = p_99509_.getCursor();

      while(p_99509_.canRead() && m_99488_(p_99509_.peek())) {
         p_99509_.skip();
      }

      if (p_99509_.getCursor() == i) {
         throw f_99482_.createWithContext(p_99509_);
      } else {
         return p_99509_.getString().substring(i, p_99509_.getCursor());
      }
   }

   public Collection<String> getExamples() {
      return f_99484_;
   }

   private static boolean m_99488_(char p_99489_) {
      return p_99489_ != ' ' && p_99489_ != '"' && p_99489_ != '[' && p_99489_ != ']' && p_99489_ != '.' && p_99489_ != '{' && p_99489_ != '}';
   }

   static Predicate<Tag> m_99510_(CompoundTag p_99511_) {
      return (p_99507_) -> {
         return NbtUtils.m_129235_(p_99511_, p_99507_, true);
      };
   }

   static class AllElementsNode implements NbtPathArgument.Node {
      public static final NbtPathArgument.AllElementsNode f_99515_ = new NbtPathArgument.AllElementsNode();

      private AllElementsNode() {
      }

      public void m_7273_(Tag p_99522_, List<Tag> p_99523_) {
         if (p_99522_ instanceof CollectionTag) {
            p_99523_.addAll((CollectionTag)p_99522_);
         }

      }

      public void m_7876_(Tag p_99528_, Supplier<Tag> p_99529_, List<Tag> p_99530_) {
         if (p_99528_ instanceof CollectionTag) {
            CollectionTag<?> collectiontag = (CollectionTag)p_99528_;
            if (collectiontag.isEmpty()) {
               Tag tag = p_99529_.get();
               if (collectiontag.m_7614_(0, tag)) {
                  p_99530_.add(tag);
               }
            } else {
               p_99530_.addAll(collectiontag);
            }
         }

      }

      public Tag m_7510_() {
         return new ListTag();
      }

      public int m_5571_(Tag p_99525_, Supplier<Tag> p_99526_) {
         if (!(p_99525_ instanceof CollectionTag)) {
            return 0;
         } else {
            CollectionTag<?> collectiontag = (CollectionTag)p_99525_;
            int i = collectiontag.size();
            if (i == 0) {
               collectiontag.m_7614_(0, p_99526_.get());
               return 1;
            } else {
               Tag tag = p_99526_.get();
               int j = i - (int)collectiontag.stream().filter(tag::equals).count();
               if (j == 0) {
                  return 0;
               } else {
                  collectiontag.clear();
                  if (!collectiontag.m_7614_(0, tag)) {
                     return 0;
                  } else {
                     for(int k = 1; k < i; ++k) {
                        collectiontag.m_7614_(k, p_99526_.get());
                     }

                     return j;
                  }
               }
            }
         }
      }

      public int m_6015_(Tag p_99520_) {
         if (p_99520_ instanceof CollectionTag) {
            CollectionTag<?> collectiontag = (CollectionTag)p_99520_;
            int i = collectiontag.size();
            if (i > 0) {
               collectiontag.clear();
               return i;
            }
         }

         return 0;
      }
   }

   static class CompoundChildNode implements NbtPathArgument.Node {
      private final String f_99531_;

      public CompoundChildNode(String p_99533_) {
         this.f_99531_ = p_99533_;
      }

      public void m_7273_(Tag p_99538_, List<Tag> p_99539_) {
         if (p_99538_ instanceof CompoundTag) {
            Tag tag = ((CompoundTag)p_99538_).m_128423_(this.f_99531_);
            if (tag != null) {
               p_99539_.add(tag);
            }
         }

      }

      public void m_7876_(Tag p_99544_, Supplier<Tag> p_99545_, List<Tag> p_99546_) {
         if (p_99544_ instanceof CompoundTag) {
            CompoundTag compoundtag = (CompoundTag)p_99544_;
            Tag tag;
            if (compoundtag.m_128441_(this.f_99531_)) {
               tag = compoundtag.m_128423_(this.f_99531_);
            } else {
               tag = p_99545_.get();
               compoundtag.m_128365_(this.f_99531_, tag);
            }

            p_99546_.add(tag);
         }

      }

      public Tag m_7510_() {
         return new CompoundTag();
      }

      public int m_5571_(Tag p_99541_, Supplier<Tag> p_99542_) {
         if (p_99541_ instanceof CompoundTag) {
            CompoundTag compoundtag = (CompoundTag)p_99541_;
            Tag tag = p_99542_.get();
            Tag tag1 = compoundtag.m_128365_(this.f_99531_, tag);
            if (!tag.equals(tag1)) {
               return 1;
            }
         }

         return 0;
      }

      public int m_6015_(Tag p_99536_) {
         if (p_99536_ instanceof CompoundTag) {
            CompoundTag compoundtag = (CompoundTag)p_99536_;
            if (compoundtag.m_128441_(this.f_99531_)) {
               compoundtag.m_128473_(this.f_99531_);
               return 1;
            }
         }

         return 0;
      }
   }

   static class IndexedElementNode implements NbtPathArgument.Node {
      private final int f_99547_;

      public IndexedElementNode(int p_99549_) {
         this.f_99547_ = p_99549_;
      }

      public void m_7273_(Tag p_99554_, List<Tag> p_99555_) {
         if (p_99554_ instanceof CollectionTag) {
            CollectionTag<?> collectiontag = (CollectionTag)p_99554_;
            int i = collectiontag.size();
            int j = this.f_99547_ < 0 ? i + this.f_99547_ : this.f_99547_;
            if (0 <= j && j < i) {
               p_99555_.add(collectiontag.get(j));
            }
         }

      }

      public void m_7876_(Tag p_99560_, Supplier<Tag> p_99561_, List<Tag> p_99562_) {
         this.m_7273_(p_99560_, p_99562_);
      }

      public Tag m_7510_() {
         return new ListTag();
      }

      public int m_5571_(Tag p_99557_, Supplier<Tag> p_99558_) {
         if (p_99557_ instanceof CollectionTag) {
            CollectionTag<?> collectiontag = (CollectionTag)p_99557_;
            int i = collectiontag.size();
            int j = this.f_99547_ < 0 ? i + this.f_99547_ : this.f_99547_;
            if (0 <= j && j < i) {
               Tag tag = collectiontag.get(j);
               Tag tag1 = p_99558_.get();
               if (!tag1.equals(tag) && collectiontag.m_7615_(j, tag1)) {
                  return 1;
               }
            }
         }

         return 0;
      }

      public int m_6015_(Tag p_99552_) {
         if (p_99552_ instanceof CollectionTag) {
            CollectionTag<?> collectiontag = (CollectionTag)p_99552_;
            int i = collectiontag.size();
            int j = this.f_99547_ < 0 ? i + this.f_99547_ : this.f_99547_;
            if (0 <= j && j < i) {
               collectiontag.remove(j);
               return 1;
            }
         }

         return 0;
      }
   }

   static class MatchElementNode implements NbtPathArgument.Node {
      private final CompoundTag f_99563_;
      private final Predicate<Tag> f_99564_;

      public MatchElementNode(CompoundTag p_99566_) {
         this.f_99563_ = p_99566_;
         this.f_99564_ = NbtPathArgument.m_99510_(p_99566_);
      }

      public void m_7273_(Tag p_99575_, List<Tag> p_99576_) {
         if (p_99575_ instanceof ListTag) {
            ListTag listtag = (ListTag)p_99575_;
            listtag.stream().filter(this.f_99564_).forEach(p_99576_::add);
         }

      }

      public void m_7876_(Tag p_99581_, Supplier<Tag> p_99582_, List<Tag> p_99583_) {
         MutableBoolean mutableboolean = new MutableBoolean();
         if (p_99581_ instanceof ListTag) {
            ListTag listtag = (ListTag)p_99581_;
            listtag.stream().filter(this.f_99564_).forEach((p_99571_) -> {
               p_99583_.add(p_99571_);
               mutableboolean.setTrue();
            });
            if (mutableboolean.isFalse()) {
               CompoundTag compoundtag = this.f_99563_.m_6426_();
               listtag.add(compoundtag);
               p_99583_.add(compoundtag);
            }
         }

      }

      public Tag m_7510_() {
         return new ListTag();
      }

      public int m_5571_(Tag p_99578_, Supplier<Tag> p_99579_) {
         int i = 0;
         if (p_99578_ instanceof ListTag) {
            ListTag listtag = (ListTag)p_99578_;
            int j = listtag.size();
            if (j == 0) {
               listtag.add(p_99579_.get());
               ++i;
            } else {
               for(int k = 0; k < j; ++k) {
                  Tag tag = listtag.get(k);
                  if (this.f_99564_.test(tag)) {
                     Tag tag1 = p_99579_.get();
                     if (!tag1.equals(tag) && listtag.m_7615_(k, tag1)) {
                        ++i;
                     }
                  }
               }
            }
         }

         return i;
      }

      public int m_6015_(Tag p_99573_) {
         int i = 0;
         if (p_99573_ instanceof ListTag) {
            ListTag listtag = (ListTag)p_99573_;

            for(int j = listtag.size() - 1; j >= 0; --j) {
               if (this.f_99564_.test(listtag.get(j))) {
                  listtag.remove(j);
                  ++i;
               }
            }
         }

         return i;
      }
   }

   static class MatchObjectNode implements NbtPathArgument.Node {
      private final String f_99584_;
      private final CompoundTag f_99585_;
      private final Predicate<Tag> f_99586_;

      public MatchObjectNode(String p_99588_, CompoundTag p_99589_) {
         this.f_99584_ = p_99588_;
         this.f_99585_ = p_99589_;
         this.f_99586_ = NbtPathArgument.m_99510_(p_99589_);
      }

      public void m_7273_(Tag p_99594_, List<Tag> p_99595_) {
         if (p_99594_ instanceof CompoundTag) {
            Tag tag = ((CompoundTag)p_99594_).m_128423_(this.f_99584_);
            if (this.f_99586_.test(tag)) {
               p_99595_.add(tag);
            }
         }

      }

      public void m_7876_(Tag p_99600_, Supplier<Tag> p_99601_, List<Tag> p_99602_) {
         if (p_99600_ instanceof CompoundTag) {
            CompoundTag compoundtag = (CompoundTag)p_99600_;
            Tag tag = compoundtag.m_128423_(this.f_99584_);
            if (tag == null) {
               Tag compoundtag1 = this.f_99585_.m_6426_();
               compoundtag.m_128365_(this.f_99584_, compoundtag1);
               p_99602_.add(compoundtag1);
            } else if (this.f_99586_.test(tag)) {
               p_99602_.add(tag);
            }
         }

      }

      public Tag m_7510_() {
         return new CompoundTag();
      }

      public int m_5571_(Tag p_99597_, Supplier<Tag> p_99598_) {
         if (p_99597_ instanceof CompoundTag) {
            CompoundTag compoundtag = (CompoundTag)p_99597_;
            Tag tag = compoundtag.m_128423_(this.f_99584_);
            if (this.f_99586_.test(tag)) {
               Tag tag1 = p_99598_.get();
               if (!tag1.equals(tag)) {
                  compoundtag.m_128365_(this.f_99584_, tag1);
                  return 1;
               }
            }
         }

         return 0;
      }

      public int m_6015_(Tag p_99592_) {
         if (p_99592_ instanceof CompoundTag) {
            CompoundTag compoundtag = (CompoundTag)p_99592_;
            Tag tag = compoundtag.m_128423_(this.f_99584_);
            if (this.f_99586_.test(tag)) {
               compoundtag.m_128473_(this.f_99584_);
               return 1;
            }
         }

         return 0;
      }
   }

   static class MatchRootObjectNode implements NbtPathArgument.Node {
      private final Predicate<Tag> f_99603_;

      public MatchRootObjectNode(CompoundTag p_99605_) {
         this.f_99603_ = NbtPathArgument.m_99510_(p_99605_);
      }

      public void m_7273_(Tag p_99610_, List<Tag> p_99611_) {
         if (p_99610_ instanceof CompoundTag && this.f_99603_.test(p_99610_)) {
            p_99611_.add(p_99610_);
         }

      }

      public void m_7876_(Tag p_99616_, Supplier<Tag> p_99617_, List<Tag> p_99618_) {
         this.m_7273_(p_99616_, p_99618_);
      }

      public Tag m_7510_() {
         return new CompoundTag();
      }

      public int m_5571_(Tag p_99613_, Supplier<Tag> p_99614_) {
         return 0;
      }

      public int m_6015_(Tag p_99608_) {
         return 0;
      }
   }

   public static class NbtPath {
      private final String f_99619_;
      private final Object2IntMap<NbtPathArgument.Node> f_99620_;
      private final NbtPathArgument.Node[] f_99621_;

      public NbtPath(String p_99623_, NbtPathArgument.Node[] p_99624_, Object2IntMap<NbtPathArgument.Node> p_99625_) {
         this.f_99619_ = p_99623_;
         this.f_99621_ = p_99624_;
         this.f_99620_ = p_99625_;
      }

      public List<Tag> m_99638_(Tag p_99639_) throws CommandSyntaxException {
         List<Tag> list = Collections.singletonList(p_99639_);

         for(NbtPathArgument.Node nbtpathargument$node : this.f_99621_) {
            list = nbtpathargument$node.m_99653_(list);
            if (list.isEmpty()) {
               throw this.m_99626_(nbtpathargument$node);
            }
         }

         return list;
      }

      public int m_99643_(Tag p_99644_) {
         List<Tag> list = Collections.singletonList(p_99644_);

         for(NbtPathArgument.Node nbtpathargument$node : this.f_99621_) {
            list = nbtpathargument$node.m_99653_(list);
            if (list.isEmpty()) {
               return 0;
            }
         }

         return list.size();
      }

      private List<Tag> m_99650_(Tag p_99651_) throws CommandSyntaxException {
         List<Tag> list = Collections.singletonList(p_99651_);

         for(int i = 0; i < this.f_99621_.length - 1; ++i) {
            NbtPathArgument.Node nbtpathargument$node = this.f_99621_[i];
            int j = i + 1;
            list = nbtpathargument$node.m_99658_(list, this.f_99621_[j]::m_7510_);
            if (list.isEmpty()) {
               throw this.m_99626_(nbtpathargument$node);
            }
         }

         return list;
      }

      public List<Tag> m_99640_(Tag p_99641_, Supplier<Tag> p_99642_) throws CommandSyntaxException {
         List<Tag> list = this.m_99650_(p_99641_);
         NbtPathArgument.Node nbtpathargument$node = this.f_99621_[this.f_99621_.length - 1];
         return nbtpathargument$node.m_99658_(list, p_99642_);
      }

      private static int m_99635_(List<Tag> p_99636_, Function<Tag, Integer> p_99637_) {
         return p_99636_.stream().map(p_99637_).reduce(0, (p_99633_, p_99634_) -> {
            return p_99633_ + p_99634_;
         });
      }

      public int m_169535_(Tag p_169536_, Tag p_169537_) throws CommandSyntaxException {
         return this.m_99645_(p_169536_, p_169537_::m_6426_);
      }

      public int m_99645_(Tag p_99646_, Supplier<Tag> p_99647_) throws CommandSyntaxException {
         List<Tag> list = this.m_99650_(p_99646_);
         NbtPathArgument.Node nbtpathargument$node = this.f_99621_[this.f_99621_.length - 1];
         return m_99635_(list, (p_99631_) -> {
            return nbtpathargument$node.m_5571_(p_99631_, p_99647_);
         });
      }

      public int m_99648_(Tag p_99649_) {
         List<Tag> list = Collections.singletonList(p_99649_);

         for(int i = 0; i < this.f_99621_.length - 1; ++i) {
            list = this.f_99621_[i].m_99653_(list);
         }

         NbtPathArgument.Node nbtpathargument$node = this.f_99621_[this.f_99621_.length - 1];
         return m_99635_(list, nbtpathargument$node::m_6015_);
      }

      private CommandSyntaxException m_99626_(NbtPathArgument.Node p_99627_) {
         int i = this.f_99620_.getInt(p_99627_);
         return NbtPathArgument.f_99483_.create(this.f_99619_.substring(0, i));
      }

      public String toString() {
         return this.f_99619_;
      }
   }

   interface Node {
      void m_7273_(Tag p_99666_, List<Tag> p_99667_);

      void m_7876_(Tag p_99670_, Supplier<Tag> p_99671_, List<Tag> p_99672_);

      Tag m_7510_();

      int m_5571_(Tag p_99668_, Supplier<Tag> p_99669_);

      int m_6015_(Tag p_99665_);

      default List<Tag> m_99653_(List<Tag> p_99654_) {
         return this.m_99655_(p_99654_, this::m_7273_);
      }

      default List<Tag> m_99658_(List<Tag> p_99659_, Supplier<Tag> p_99660_) {
         return this.m_99655_(p_99659_, (p_99663_, p_99664_) -> {
            this.m_7876_(p_99663_, p_99660_, p_99664_);
         });
      }

      default List<Tag> m_99655_(List<Tag> p_99656_, BiConsumer<Tag, List<Tag>> p_99657_) {
         List<Tag> list = Lists.newArrayList();

         for(Tag tag : p_99656_) {
            p_99657_.accept(tag, list);
         }

         return list;
      }
   }
}