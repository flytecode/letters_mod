package net.minecraft.world.level.saveddata.maps;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MapBanner {
   private final BlockPos f_77766_;
   private final DyeColor f_77767_;
   @Nullable
   private final Component f_77768_;

   public MapBanner(BlockPos p_77770_, DyeColor p_77771_, @Nullable Component p_77772_) {
      this.f_77766_ = p_77770_;
      this.f_77767_ = p_77771_;
      this.f_77768_ = p_77772_;
   }

   public static MapBanner m_77777_(CompoundTag p_77778_) {
      BlockPos blockpos = NbtUtils.m_129239_(p_77778_.m_128469_("Pos"));
      DyeColor dyecolor = DyeColor.m_41057_(p_77778_.m_128461_("Color"), DyeColor.WHITE);
      Component component = p_77778_.m_128441_("Name") ? Component.Serializer.m_130701_(p_77778_.m_128461_("Name")) : null;
      return new MapBanner(blockpos, dyecolor, component);
   }

   @Nullable
   public static MapBanner m_77774_(BlockGetter p_77775_, BlockPos p_77776_) {
      BlockEntity blockentity = p_77775_.m_7702_(p_77776_);
      if (blockentity instanceof BannerBlockEntity) {
         BannerBlockEntity bannerblockentity = (BannerBlockEntity)blockentity;
         DyeColor dyecolor = bannerblockentity.m_155044_();
         Component component = bannerblockentity.m_8077_() ? bannerblockentity.m_7770_() : null;
         return new MapBanner(p_77776_, dyecolor, component);
      } else {
         return null;
      }
   }

   public BlockPos m_77773_() {
      return this.f_77766_;
   }

   public DyeColor m_164759_() {
      return this.f_77767_;
   }

   public MapDecoration.Type m_77782_() {
      switch(this.f_77767_) {
      case WHITE:
         return MapDecoration.Type.BANNER_WHITE;
      case ORANGE:
         return MapDecoration.Type.BANNER_ORANGE;
      case MAGENTA:
         return MapDecoration.Type.BANNER_MAGENTA;
      case LIGHT_BLUE:
         return MapDecoration.Type.BANNER_LIGHT_BLUE;
      case YELLOW:
         return MapDecoration.Type.BANNER_YELLOW;
      case LIME:
         return MapDecoration.Type.BANNER_LIME;
      case PINK:
         return MapDecoration.Type.BANNER_PINK;
      case GRAY:
         return MapDecoration.Type.BANNER_GRAY;
      case LIGHT_GRAY:
         return MapDecoration.Type.BANNER_LIGHT_GRAY;
      case CYAN:
         return MapDecoration.Type.BANNER_CYAN;
      case PURPLE:
         return MapDecoration.Type.BANNER_PURPLE;
      case BLUE:
         return MapDecoration.Type.BANNER_BLUE;
      case BROWN:
         return MapDecoration.Type.BANNER_BROWN;
      case GREEN:
         return MapDecoration.Type.BANNER_GREEN;
      case RED:
         return MapDecoration.Type.BANNER_RED;
      case BLACK:
      default:
         return MapDecoration.Type.BANNER_BLACK;
      }
   }

   @Nullable
   public Component m_77783_() {
      return this.f_77768_;
   }

   public boolean equals(Object p_77786_) {
      if (this == p_77786_) {
         return true;
      } else if (p_77786_ != null && this.getClass() == p_77786_.getClass()) {
         MapBanner mapbanner = (MapBanner)p_77786_;
         return Objects.equals(this.f_77766_, mapbanner.f_77766_) && this.f_77767_ == mapbanner.f_77767_ && Objects.equals(this.f_77768_, mapbanner.f_77768_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_77766_, this.f_77767_, this.f_77768_);
   }

   public CompoundTag m_77784_() {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128365_("Pos", NbtUtils.m_129224_(this.f_77766_));
      compoundtag.m_128359_("Color", this.f_77767_.m_41065_());
      if (this.f_77768_ != null) {
         compoundtag.m_128359_("Name", Component.Serializer.m_130703_(this.f_77768_));
      }

      return compoundtag;
   }

   public String m_77787_() {
      return "banner-" + this.f_77766_.m_123341_() + "," + this.f_77766_.m_123342_() + "," + this.f_77766_.m_123343_();
   }
}