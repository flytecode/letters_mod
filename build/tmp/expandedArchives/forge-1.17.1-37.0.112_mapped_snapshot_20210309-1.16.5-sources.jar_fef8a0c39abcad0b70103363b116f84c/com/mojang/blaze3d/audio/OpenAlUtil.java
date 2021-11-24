package com.mojang.blaze3d.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;

@OnlyIn(Dist.CLIENT)
public class OpenAlUtil {
   private static final Logger f_83780_ = LogManager.getLogger();

   private static String m_83782_(int p_83783_) {
      switch(p_83783_) {
      case 40961:
         return "Invalid name parameter.";
      case 40962:
         return "Invalid enumerated parameter value.";
      case 40963:
         return "Invalid parameter parameter value.";
      case 40964:
         return "Invalid operation.";
      case 40965:
         return "Unable to allocate memory.";
      default:
         return "An unrecognized error occurred.";
      }
   }

   static boolean m_83787_(String p_83788_) {
      int i = AL10.alGetError();
      if (i != 0) {
         f_83780_.error("{}: {}", p_83788_, m_83782_(i));
         return true;
      } else {
         return false;
      }
   }

   private static String m_83791_(int p_83792_) {
      switch(p_83792_) {
      case 40961:
         return "Invalid device.";
      case 40962:
         return "Invalid context.";
      case 40963:
         return "Illegal enum.";
      case 40964:
         return "Invalid value.";
      case 40965:
         return "Unable to allocate memory.";
      default:
         return "An unrecognized error occurred.";
      }
   }

   static boolean m_83784_(long p_83785_, String p_83786_) {
      int i = ALC10.alcGetError(p_83785_);
      if (i != 0) {
         f_83780_.error("{}{}: {}", p_83786_, p_83785_, m_83791_(i));
         return true;
      } else {
         return false;
      }
   }

   static int m_83789_(AudioFormat p_83790_) {
      Encoding encoding = p_83790_.getEncoding();
      int i = p_83790_.getChannels();
      int j = p_83790_.getSampleSizeInBits();
      if (encoding.equals(Encoding.PCM_UNSIGNED) || encoding.equals(Encoding.PCM_SIGNED)) {
         if (i == 1) {
            if (j == 8) {
               return 4352;
            }

            if (j == 16) {
               return 4353;
            }
         } else if (i == 2) {
            if (j == 8) {
               return 4354;
            }

            if (j == 16) {
               return 4355;
            }
         }
      }

      throw new IllegalArgumentException("Invalid audio format: " + p_83790_);
   }
}