package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PackResourcesAdapterV4 implements PackResources {
   private static final Map<String, Pair<ChestType, ResourceLocation>> f_118737_ = Util.m_137469_(Maps.newHashMap(), (p_118784_) -> {
      p_118784_.put("textures/entity/chest/normal_left.png", new Pair<>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/normal_double.png")));
      p_118784_.put("textures/entity/chest/normal_right.png", new Pair<>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/normal_double.png")));
      p_118784_.put("textures/entity/chest/normal.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/normal.png")));
      p_118784_.put("textures/entity/chest/trapped_left.png", new Pair<>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/trapped_double.png")));
      p_118784_.put("textures/entity/chest/trapped_right.png", new Pair<>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/trapped_double.png")));
      p_118784_.put("textures/entity/chest/trapped.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/trapped.png")));
      p_118784_.put("textures/entity/chest/christmas_left.png", new Pair<>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/christmas_double.png")));
      p_118784_.put("textures/entity/chest/christmas_right.png", new Pair<>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/christmas_double.png")));
      p_118784_.put("textures/entity/chest/christmas.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/christmas.png")));
      p_118784_.put("textures/entity/chest/ender.png", new Pair<>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/ender.png")));
   });
   private static final List<String> f_118738_ = Lists.newArrayList("base", "border", "bricks", "circle", "creeper", "cross", "curly_border", "diagonal_left", "diagonal_right", "diagonal_up_left", "diagonal_up_right", "flower", "globe", "gradient", "gradient_up", "half_horizontal", "half_horizontal_bottom", "half_vertical", "half_vertical_right", "mojang", "rhombus", "skull", "small_stripes", "square_bottom_left", "square_bottom_right", "square_top_left", "square_top_right", "straight_cross", "stripe_bottom", "stripe_center", "stripe_downleft", "stripe_downright", "stripe_left", "stripe_middle", "stripe_right", "stripe_top", "triangle_bottom", "triangle_top", "triangles_bottom", "triangles_top");
   private static final Set<String> f_118739_ = f_118738_.stream().map((p_118795_) -> {
      return "textures/entity/shield/" + p_118795_ + ".png";
   }).collect(Collectors.toSet());
   private static final Set<String> f_118740_ = f_118738_.stream().map((p_118782_) -> {
      return "textures/entity/banner/" + p_118782_ + ".png";
   }).collect(Collectors.toSet());
   public static final ResourceLocation f_118734_ = new ResourceLocation("textures/entity/shield_base.png");
   public static final ResourceLocation f_118735_ = new ResourceLocation("textures/entity/banner_base.png");
   public static final int f_174837_ = 64;
   public static final int f_174838_ = 64;
   public static final int f_174839_ = 64;
   public static final ResourceLocation f_118736_ = new ResourceLocation("textures/entity/iron_golem.png");
   public static final String f_174840_ = "textures/entity/iron_golem/iron_golem.png";
   private final PackResources f_118741_;

   public PackResourcesAdapterV4(PackResources p_118744_) {
      this.f_118741_ = p_118744_;
   }

   public InputStream m_5542_(String p_118791_) throws IOException {
      return this.f_118741_.m_5542_(p_118791_);
   }

   public boolean m_7211_(PackType p_118786_, ResourceLocation p_118787_) {
      if (!"minecraft".equals(p_118787_.m_135827_())) {
         return this.f_118741_.m_7211_(p_118786_, p_118787_);
      } else {
         String s = p_118787_.m_135815_();
         if ("textures/misc/enchanted_item_glint.png".equals(s)) {
            return false;
         } else if ("textures/entity/iron_golem/iron_golem.png".equals(s)) {
            return this.f_118741_.m_7211_(p_118786_, f_118736_);
         } else if (!"textures/entity/conduit/wind.png".equals(s) && !"textures/entity/conduit/wind_vertical.png".equals(s)) {
            if (f_118739_.contains(s)) {
               return this.f_118741_.m_7211_(p_118786_, f_118734_) && this.f_118741_.m_7211_(p_118786_, p_118787_);
            } else if (!f_118740_.contains(s)) {
               Pair<ChestType, ResourceLocation> pair = f_118737_.get(s);
               return pair != null && this.f_118741_.m_7211_(p_118786_, pair.getSecond()) ? true : this.f_118741_.m_7211_(p_118786_, p_118787_);
            } else {
               return this.f_118741_.m_7211_(p_118786_, f_118735_) && this.f_118741_.m_7211_(p_118786_, p_118787_);
            }
         } else {
            return false;
         }
      }
   }

   public InputStream m_8031_(PackType p_118755_, ResourceLocation p_118756_) throws IOException {
      if (!"minecraft".equals(p_118756_.m_135827_())) {
         return this.f_118741_.m_8031_(p_118755_, p_118756_);
      } else {
         String s = p_118756_.m_135815_();
         if ("textures/entity/iron_golem/iron_golem.png".equals(s)) {
            return this.f_118741_.m_8031_(p_118755_, f_118736_);
         } else {
            if (f_118739_.contains(s)) {
               InputStream inputstream2 = m_118773_(this.f_118741_.m_8031_(p_118755_, f_118734_), this.f_118741_.m_8031_(p_118755_, p_118756_), 64, 2, 2, 12, 22);
               if (inputstream2 != null) {
                  return inputstream2;
               }
            } else if (f_118740_.contains(s)) {
               InputStream inputstream1 = m_118773_(this.f_118741_.m_8031_(p_118755_, f_118735_), this.f_118741_.m_8031_(p_118755_, p_118756_), 64, 0, 0, 42, 41);
               if (inputstream1 != null) {
                  return inputstream1;
               }
            } else {
               if ("textures/entity/enderdragon/dragon.png".equals(s) || "textures/entity/enderdragon/dragon_exploding.png".equals(s)) {
                  NativeImage nativeimage = NativeImage.m_85058_(this.f_118741_.m_8031_(p_118755_, p_118756_));

                  ByteArrayInputStream bytearrayinputstream;
                  try {
                     int j = nativeimage.m_84982_() / 256;

                     for(int k = 88 * j; k < 200 * j; ++k) {
                        for(int i = 56 * j; i < 112 * j; ++i) {
                           nativeimage.m_84988_(i, k, 0);
                        }
                     }

                     bytearrayinputstream = new ByteArrayInputStream(nativeimage.m_85121_());
                  } catch (Throwable throwable1) {
                     if (nativeimage != null) {
                        try {
                           nativeimage.close();
                        } catch (Throwable throwable) {
                           throwable1.addSuppressed(throwable);
                        }
                     }

                     throw throwable1;
                  }

                  if (nativeimage != null) {
                     nativeimage.close();
                  }

                  return bytearrayinputstream;
               }

               if ("textures/entity/conduit/closed_eye.png".equals(s) || "textures/entity/conduit/open_eye.png".equals(s)) {
                  return m_118771_(this.f_118741_.m_8031_(p_118755_, p_118756_));
               }

               Pair<ChestType, ResourceLocation> pair = f_118737_.get(s);
               if (pair != null) {
                  ChestType chesttype = pair.getFirst();
                  InputStream inputstream = this.f_118741_.m_8031_(p_118755_, pair.getSecond());
                  if (chesttype == ChestType.SINGLE) {
                     return m_118797_(inputstream);
                  }

                  if (chesttype == ChestType.LEFT) {
                     return m_118788_(inputstream);
                  }

                  if (chesttype == ChestType.RIGHT) {
                     return m_118792_(inputstream);
                  }
               }
            }

            return this.f_118741_.m_8031_(p_118755_, p_118756_);
         }
      }
   }

   @Nullable
   public static InputStream m_118773_(InputStream p_118774_, InputStream p_118775_, int p_118776_, int p_118777_, int p_118778_, int p_118779_, int p_118780_) throws IOException {
      NativeImage nativeimage = NativeImage.m_85058_(p_118774_);

      ByteArrayInputStream bytearrayinputstream;
      label105: {
         try {
            NativeImage nativeimage1;
            label107: {
               nativeimage1 = NativeImage.m_85058_(p_118775_);

               try {
                  int i = nativeimage.m_84982_();
                  int j = nativeimage.m_85084_();
                  if (i != nativeimage1.m_84982_() || j != nativeimage1.m_85084_()) {
                     break label107;
                  }

                  NativeImage nativeimage2 = new NativeImage(i, j, true);

                  try {
                     int k = i / p_118776_;

                     for(int l = p_118778_ * k; l < p_118780_ * k; ++l) {
                        for(int i1 = p_118777_ * k; i1 < p_118779_ * k; ++i1) {
                           int j1 = NativeImage.m_85085_(nativeimage1.m_84985_(i1, l));
                           int k1 = nativeimage.m_84985_(i1, l);
                           nativeimage2.m_84988_(i1, l, NativeImage.m_84992_(j1, NativeImage.m_85119_(k1), NativeImage.m_85103_(k1), NativeImage.m_85085_(k1)));
                        }
                     }

                     bytearrayinputstream = new ByteArrayInputStream(nativeimage2.m_85121_());
                  } catch (Throwable throwable3) {
                     try {
                        nativeimage2.close();
                     } catch (Throwable throwable2) {
                        throwable3.addSuppressed(throwable2);
                     }

                     throw throwable3;
                  }

                  nativeimage2.close();
               } catch (Throwable throwable4) {
                  if (nativeimage1 != null) {
                     try {
                        nativeimage1.close();
                     } catch (Throwable throwable1) {
                        throwable4.addSuppressed(throwable1);
                     }
                  }

                  throw throwable4;
               }

               if (nativeimage1 != null) {
                  nativeimage1.close();
               }
               break label105;
            }

            if (nativeimage1 != null) {
               nativeimage1.close();
            }
         } catch (Throwable throwable5) {
            if (nativeimage != null) {
               try {
                  nativeimage.close();
               } catch (Throwable throwable) {
                  throwable5.addSuppressed(throwable);
               }
            }

            throw throwable5;
         }

         if (nativeimage != null) {
            nativeimage.close();
         }

         return null;
      }

      if (nativeimage != null) {
         nativeimage.close();
      }

      return bytearrayinputstream;
   }

   public static InputStream m_118771_(InputStream p_118772_) throws IOException {
      NativeImage nativeimage = NativeImage.m_85058_(p_118772_);

      ByteArrayInputStream bytearrayinputstream;
      try {
         int i = nativeimage.m_84982_();
         int j = nativeimage.m_85084_();
         NativeImage nativeimage1 = new NativeImage(2 * i, 2 * j, true);

         try {
            m_118759_(nativeimage, nativeimage1, 0, 0, 0, 0, i, j, 1, false, false);
            bytearrayinputstream = new ByteArrayInputStream(nativeimage1.m_85121_());
         } catch (Throwable throwable2) {
            try {
               nativeimage1.close();
            } catch (Throwable throwable1) {
               throwable2.addSuppressed(throwable1);
            }

            throw throwable2;
         }

         nativeimage1.close();
      } catch (Throwable throwable3) {
         if (nativeimage != null) {
            try {
               nativeimage.close();
            } catch (Throwable throwable) {
               throwable3.addSuppressed(throwable);
            }
         }

         throw throwable3;
      }

      if (nativeimage != null) {
         nativeimage.close();
      }

      return bytearrayinputstream;
   }

   public static InputStream m_118788_(InputStream p_118789_) throws IOException {
      NativeImage nativeimage = NativeImage.m_85058_(p_118789_);

      ByteArrayInputStream bytearrayinputstream;
      try {
         int i = nativeimage.m_84982_();
         int j = nativeimage.m_85084_();
         NativeImage nativeimage1 = new NativeImage(i / 2, j, true);

         try {
            int k = j / 64;
            m_118759_(nativeimage, nativeimage1, 29, 0, 29, 0, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 59, 0, 14, 0, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 29, 14, 43, 14, 15, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 44, 14, 29, 14, 14, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 58, 14, 14, 14, 15, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 29, 19, 29, 19, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 59, 19, 14, 19, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 29, 33, 43, 33, 15, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 44, 33, 29, 33, 14, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 58, 33, 14, 33, 15, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 2, 0, 2, 0, 1, 1, k, false, true);
            m_118759_(nativeimage, nativeimage1, 4, 0, 1, 0, 1, 1, k, false, true);
            m_118759_(nativeimage, nativeimage1, 2, 1, 3, 1, 1, 4, k, true, true);
            m_118759_(nativeimage, nativeimage1, 3, 1, 2, 1, 1, 4, k, true, true);
            m_118759_(nativeimage, nativeimage1, 4, 1, 1, 1, 1, 4, k, true, true);
            bytearrayinputstream = new ByteArrayInputStream(nativeimage1.m_85121_());
         } catch (Throwable throwable2) {
            try {
               nativeimage1.close();
            } catch (Throwable throwable1) {
               throwable2.addSuppressed(throwable1);
            }

            throw throwable2;
         }

         nativeimage1.close();
      } catch (Throwable throwable3) {
         if (nativeimage != null) {
            try {
               nativeimage.close();
            } catch (Throwable throwable) {
               throwable3.addSuppressed(throwable);
            }
         }

         throw throwable3;
      }

      if (nativeimage != null) {
         nativeimage.close();
      }

      return bytearrayinputstream;
   }

   public static InputStream m_118792_(InputStream p_118793_) throws IOException {
      NativeImage nativeimage = NativeImage.m_85058_(p_118793_);

      ByteArrayInputStream bytearrayinputstream;
      try {
         int i = nativeimage.m_84982_();
         int j = nativeimage.m_85084_();
         NativeImage nativeimage1 = new NativeImage(i / 2, j, true);

         try {
            int k = j / 64;
            m_118759_(nativeimage, nativeimage1, 14, 0, 29, 0, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 44, 0, 14, 0, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 0, 14, 0, 14, 14, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 14, 14, 43, 14, 15, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 73, 14, 14, 14, 15, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 14, 19, 29, 19, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 44, 19, 14, 19, 15, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 0, 33, 0, 33, 14, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 14, 33, 43, 33, 15, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 73, 33, 14, 33, 15, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 1, 0, 2, 0, 1, 1, k, false, true);
            m_118759_(nativeimage, nativeimage1, 3, 0, 1, 0, 1, 1, k, false, true);
            m_118759_(nativeimage, nativeimage1, 0, 1, 0, 1, 1, 4, k, true, true);
            m_118759_(nativeimage, nativeimage1, 1, 1, 3, 1, 1, 4, k, true, true);
            m_118759_(nativeimage, nativeimage1, 5, 1, 1, 1, 1, 4, k, true, true);
            bytearrayinputstream = new ByteArrayInputStream(nativeimage1.m_85121_());
         } catch (Throwable throwable2) {
            try {
               nativeimage1.close();
            } catch (Throwable throwable1) {
               throwable2.addSuppressed(throwable1);
            }

            throw throwable2;
         }

         nativeimage1.close();
      } catch (Throwable throwable3) {
         if (nativeimage != null) {
            try {
               nativeimage.close();
            } catch (Throwable throwable) {
               throwable3.addSuppressed(throwable);
            }
         }

         throw throwable3;
      }

      if (nativeimage != null) {
         nativeimage.close();
      }

      return bytearrayinputstream;
   }

   public static InputStream m_118797_(InputStream p_118798_) throws IOException {
      NativeImage nativeimage = NativeImage.m_85058_(p_118798_);

      ByteArrayInputStream bytearrayinputstream;
      try {
         int i = nativeimage.m_84982_();
         int j = nativeimage.m_85084_();
         NativeImage nativeimage1 = new NativeImage(i, j, true);

         try {
            int k = j / 64;
            m_118759_(nativeimage, nativeimage1, 14, 0, 28, 0, 14, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 28, 0, 14, 0, 14, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 0, 14, 0, 14, 14, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 14, 14, 42, 14, 14, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 28, 14, 28, 14, 14, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 42, 14, 14, 14, 14, 5, k, true, true);
            m_118759_(nativeimage, nativeimage1, 14, 19, 28, 19, 14, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 28, 19, 14, 19, 14, 14, k, false, true);
            m_118759_(nativeimage, nativeimage1, 0, 33, 0, 33, 14, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 14, 33, 42, 33, 14, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 28, 33, 28, 33, 14, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 42, 33, 14, 33, 14, 10, k, true, true);
            m_118759_(nativeimage, nativeimage1, 1, 0, 3, 0, 2, 1, k, false, true);
            m_118759_(nativeimage, nativeimage1, 3, 0, 1, 0, 2, 1, k, false, true);
            m_118759_(nativeimage, nativeimage1, 0, 1, 0, 1, 1, 4, k, true, true);
            m_118759_(nativeimage, nativeimage1, 1, 1, 4, 1, 2, 4, k, true, true);
            m_118759_(nativeimage, nativeimage1, 3, 1, 3, 1, 1, 4, k, true, true);
            m_118759_(nativeimage, nativeimage1, 4, 1, 1, 1, 2, 4, k, true, true);
            bytearrayinputstream = new ByteArrayInputStream(nativeimage1.m_85121_());
         } catch (Throwable throwable2) {
            try {
               nativeimage1.close();
            } catch (Throwable throwable1) {
               throwable2.addSuppressed(throwable1);
            }

            throw throwable2;
         }

         nativeimage1.close();
      } catch (Throwable throwable3) {
         if (nativeimage != null) {
            try {
               nativeimage.close();
            } catch (Throwable throwable) {
               throwable3.addSuppressed(throwable);
            }
         }

         throw throwable3;
      }

      if (nativeimage != null) {
         nativeimage.close();
      }

      return bytearrayinputstream;
   }

   public Collection<ResourceLocation> m_7466_(PackType p_118749_, String p_118750_, String p_118751_, int p_118752_, Predicate<String> p_118753_) {
      return this.f_118741_.m_7466_(p_118749_, p_118750_, p_118751_, p_118752_, p_118753_);
   }

   public Set<String> m_5698_(PackType p_118747_) {
      return this.f_118741_.m_5698_(p_118747_);
   }

   @Nullable
   public <T> T m_5550_(MetadataSectionSerializer<T> p_118758_) throws IOException {
      return this.f_118741_.m_5550_(p_118758_);
   }

   public String m_8017_() {
      return this.f_118741_.m_8017_();
   }

   public void close() {
      this.f_118741_.close();
   }

   private static void m_118759_(NativeImage p_118760_, NativeImage p_118761_, int p_118762_, int p_118763_, int p_118764_, int p_118765_, int p_118766_, int p_118767_, int p_118768_, boolean p_118769_, boolean p_118770_) {
      p_118767_ = p_118767_ * p_118768_;
      p_118766_ = p_118766_ * p_118768_;
      p_118764_ = p_118764_ * p_118768_;
      p_118765_ = p_118765_ * p_118768_;
      p_118762_ = p_118762_ * p_118768_;
      p_118763_ = p_118763_ * p_118768_;

      for(int i = 0; i < p_118767_; ++i) {
         for(int j = 0; j < p_118766_; ++j) {
            p_118761_.m_84988_(p_118764_ + j, p_118765_ + i, p_118760_.m_84985_(p_118762_ + (p_118769_ ? p_118766_ - 1 - j : j), p_118763_ + (p_118770_ ? p_118767_ - 1 - i : i)));
         }
      }

   }
}