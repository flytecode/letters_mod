package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface MultiLineLabel {
   MultiLineLabel f_94331_ = new MultiLineLabel() {
      public int m_6276_(PoseStack p_94378_, int p_94379_, int p_94380_) {
         return p_94380_;
      }

      public int m_6514_(PoseStack p_94382_, int p_94383_, int p_94384_, int p_94385_, int p_94386_) {
         return p_94384_;
      }

      public int m_6516_(PoseStack p_94388_, int p_94389_, int p_94390_, int p_94391_, int p_94392_) {
         return p_94390_;
      }

      public int m_6508_(PoseStack p_94394_, int p_94395_, int p_94396_, int p_94397_, int p_94398_) {
         return p_94396_;
      }

      public int m_5770_() {
         return 0;
      }
   };

   static MultiLineLabel m_94341_(Font p_94342_, FormattedText p_94343_, int p_94344_) {
      return m_94361_(p_94342_, p_94342_.m_92923_(p_94343_, p_94344_).stream().map((p_94374_) -> {
         return new MultiLineLabel.TextWithWidth(p_94374_, p_94342_.m_92724_(p_94374_));
      }).collect(ImmutableList.toImmutableList()));
   }

   static MultiLineLabel m_94345_(Font p_94346_, FormattedText p_94347_, int p_94348_, int p_94349_) {
      return m_94361_(p_94346_, p_94346_.m_92923_(p_94347_, p_94348_).stream().limit((long)p_94349_).map((p_94371_) -> {
         return new MultiLineLabel.TextWithWidth(p_94371_, p_94346_.m_92724_(p_94371_));
      }).collect(ImmutableList.toImmutableList()));
   }

   static MultiLineLabel m_94350_(Font p_94351_, Component... p_94352_) {
      return m_94361_(p_94351_, Arrays.stream(p_94352_).map(Component::m_7532_).map((p_94360_) -> {
         return new MultiLineLabel.TextWithWidth(p_94360_, p_94351_.m_92724_(p_94360_));
      }).collect(ImmutableList.toImmutableList()));
   }

   static MultiLineLabel m_169036_(Font p_169037_, List<Component> p_169038_) {
      return m_94361_(p_169037_, p_169038_.stream().map(Component::m_7532_).map((p_169035_) -> {
         return new MultiLineLabel.TextWithWidth(p_169035_, p_169037_.m_92724_(p_169035_));
      }).collect(ImmutableList.toImmutableList()));
   }

   static MultiLineLabel m_94361_(final Font p_94362_, final List<MultiLineLabel.TextWithWidth> p_94363_) {
      return p_94363_.isEmpty() ? f_94331_ : new MultiLineLabel() {
         public int m_6276_(PoseStack p_94406_, int p_94407_, int p_94408_) {
            return this.m_6514_(p_94406_, p_94407_, p_94408_, 9, 16777215);
         }

         public int m_6514_(PoseStack p_94410_, int p_94411_, int p_94412_, int p_94413_, int p_94414_) {
            int i = p_94412_;

            for(MultiLineLabel.TextWithWidth multilinelabel$textwithwidth : p_94363_) {
               p_94362_.m_92744_(p_94410_, multilinelabel$textwithwidth.f_94427_, (float)(p_94411_ - multilinelabel$textwithwidth.f_94428_ / 2), (float)i, p_94414_);
               i += p_94413_;
            }

            return i;
         }

         public int m_6516_(PoseStack p_94416_, int p_94417_, int p_94418_, int p_94419_, int p_94420_) {
            int i = p_94418_;

            for(MultiLineLabel.TextWithWidth multilinelabel$textwithwidth : p_94363_) {
               p_94362_.m_92744_(p_94416_, multilinelabel$textwithwidth.f_94427_, (float)p_94417_, (float)i, p_94420_);
               i += p_94419_;
            }

            return i;
         }

         public int m_6508_(PoseStack p_94422_, int p_94423_, int p_94424_, int p_94425_, int p_94426_) {
            int i = p_94424_;

            for(MultiLineLabel.TextWithWidth multilinelabel$textwithwidth : p_94363_) {
               p_94362_.m_92877_(p_94422_, multilinelabel$textwithwidth.f_94427_, (float)p_94423_, (float)i, p_94426_);
               i += p_94425_;
            }

            return i;
         }

         public int m_5770_() {
            return p_94363_.size();
         }
      };
   }

   int m_6276_(PoseStack p_94333_, int p_94334_, int p_94335_);

   int m_6514_(PoseStack p_94336_, int p_94337_, int p_94338_, int p_94339_, int p_94340_);

   int m_6516_(PoseStack p_94353_, int p_94354_, int p_94355_, int p_94356_, int p_94357_);

   int m_6508_(PoseStack p_94364_, int p_94365_, int p_94366_, int p_94367_, int p_94368_);

   int m_5770_();

   @OnlyIn(Dist.CLIENT)
   public static class TextWithWidth {
      final FormattedCharSequence f_94427_;
      final int f_94428_;

      TextWithWidth(FormattedCharSequence p_94430_, int p_94431_) {
         this.f_94427_ = p_94430_;
         this.f_94428_ = p_94431_;
      }
   }
}