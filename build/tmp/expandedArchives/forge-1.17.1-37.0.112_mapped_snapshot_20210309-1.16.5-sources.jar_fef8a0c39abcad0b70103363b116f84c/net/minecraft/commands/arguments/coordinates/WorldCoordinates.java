package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WorldCoordinates implements Coordinates {
   private final WorldCoordinate f_120879_;
   private final WorldCoordinate f_120880_;
   private final WorldCoordinate f_120881_;

   public WorldCoordinates(WorldCoordinate p_120883_, WorldCoordinate p_120884_, WorldCoordinate p_120885_) {
      this.f_120879_ = p_120883_;
      this.f_120880_ = p_120884_;
      this.f_120881_ = p_120885_;
   }

   public Vec3 m_6955_(CommandSourceStack p_120893_) {
      Vec3 vec3 = p_120893_.m_81371_();
      return new Vec3(this.f_120879_.m_120867_(vec3.f_82479_), this.f_120880_.m_120867_(vec3.f_82480_), this.f_120881_.m_120867_(vec3.f_82481_));
   }

   public Vec2 m_6970_(CommandSourceStack p_120896_) {
      Vec2 vec2 = p_120896_.m_81376_();
      return new Vec2((float)this.f_120879_.m_120867_((double)vec2.f_82470_), (float)this.f_120880_.m_120867_((double)vec2.f_82471_));
   }

   public boolean m_6888_() {
      return this.f_120879_.m_120866_();
   }

   public boolean m_6892_() {
      return this.f_120880_.m_120866_();
   }

   public boolean m_6900_() {
      return this.f_120881_.m_120866_();
   }

   public boolean equals(Object p_120900_) {
      if (this == p_120900_) {
         return true;
      } else if (!(p_120900_ instanceof WorldCoordinates)) {
         return false;
      } else {
         WorldCoordinates worldcoordinates = (WorldCoordinates)p_120900_;
         if (!this.f_120879_.equals(worldcoordinates.f_120879_)) {
            return false;
         } else {
            return !this.f_120880_.equals(worldcoordinates.f_120880_) ? false : this.f_120881_.equals(worldcoordinates.f_120881_);
         }
      }
   }

   public static WorldCoordinates m_120887_(StringReader p_120888_) throws CommandSyntaxException {
      int i = p_120888_.getCursor();
      WorldCoordinate worldcoordinate = WorldCoordinate.m_120869_(p_120888_);
      if (p_120888_.canRead() && p_120888_.peek() == ' ') {
         p_120888_.skip();
         WorldCoordinate worldcoordinate1 = WorldCoordinate.m_120869_(p_120888_);
         if (p_120888_.canRead() && p_120888_.peek() == ' ') {
            p_120888_.skip();
            WorldCoordinate worldcoordinate2 = WorldCoordinate.m_120869_(p_120888_);
            return new WorldCoordinates(worldcoordinate, worldcoordinate1, worldcoordinate2);
         } else {
            p_120888_.setCursor(i);
            throw Vec3Argument.f_120834_.createWithContext(p_120888_);
         }
      } else {
         p_120888_.setCursor(i);
         throw Vec3Argument.f_120834_.createWithContext(p_120888_);
      }
   }

   public static WorldCoordinates m_120889_(StringReader p_120890_, boolean p_120891_) throws CommandSyntaxException {
      int i = p_120890_.getCursor();
      WorldCoordinate worldcoordinate = WorldCoordinate.m_120871_(p_120890_, p_120891_);
      if (p_120890_.canRead() && p_120890_.peek() == ' ') {
         p_120890_.skip();
         WorldCoordinate worldcoordinate1 = WorldCoordinate.m_120871_(p_120890_, false);
         if (p_120890_.canRead() && p_120890_.peek() == ' ') {
            p_120890_.skip();
            WorldCoordinate worldcoordinate2 = WorldCoordinate.m_120871_(p_120890_, p_120891_);
            return new WorldCoordinates(worldcoordinate, worldcoordinate1, worldcoordinate2);
         } else {
            p_120890_.setCursor(i);
            throw Vec3Argument.f_120834_.createWithContext(p_120890_);
         }
      } else {
         p_120890_.setCursor(i);
         throw Vec3Argument.f_120834_.createWithContext(p_120890_);
      }
   }

   public static WorldCoordinates m_175085_(double p_175086_, double p_175087_, double p_175088_) {
      return new WorldCoordinates(new WorldCoordinate(false, p_175086_), new WorldCoordinate(false, p_175087_), new WorldCoordinate(false, p_175088_));
   }

   public static WorldCoordinates m_175089_(Vec2 p_175090_) {
      return new WorldCoordinates(new WorldCoordinate(false, (double)p_175090_.f_82470_), new WorldCoordinate(false, (double)p_175090_.f_82471_), new WorldCoordinate(true, 0.0D));
   }

   public static WorldCoordinates m_120898_() {
      return new WorldCoordinates(new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D), new WorldCoordinate(true, 0.0D));
   }

   public int hashCode() {
      int i = this.f_120879_.hashCode();
      i = 31 * i + this.f_120880_.hashCode();
      return 31 * i + this.f_120881_.hashCode();
   }
}