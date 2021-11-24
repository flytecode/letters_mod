package net.minecraft.client.resources.sounds;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;

@OnlyIn(Dist.CLIENT)
public class SoundEventRegistrationSerializer implements JsonDeserializer<SoundEventRegistration> {
   public SoundEventRegistration deserialize(JsonElement p_119827_, Type p_119828_, JsonDeserializationContext p_119829_) throws JsonParseException {
      JsonObject jsonobject = GsonHelper.m_13918_(p_119827_, "entry");
      boolean flag = GsonHelper.m_13855_(jsonobject, "replace", false);
      String s = GsonHelper.m_13851_(jsonobject, "subtitle", (String)null);
      List<Sound> list = this.m_119830_(jsonobject);
      return new SoundEventRegistration(list, flag, s);
   }

   private List<Sound> m_119830_(JsonObject p_119831_) {
      List<Sound> list = Lists.newArrayList();
      if (p_119831_.has("sounds")) {
         JsonArray jsonarray = GsonHelper.m_13933_(p_119831_, "sounds");

         for(int i = 0; i < jsonarray.size(); ++i) {
            JsonElement jsonelement = jsonarray.get(i);
            if (GsonHelper.m_13803_(jsonelement)) {
               String s = GsonHelper.m_13805_(jsonelement, "sound");
               list.add(new Sound(s, 1.0F, 1.0F, 1, Sound.Type.FILE, false, false, 16));
            } else {
               list.add(this.m_119835_(GsonHelper.m_13918_(jsonelement, "sound")));
            }
         }
      }

      return list;
   }

   private Sound m_119835_(JsonObject p_119836_) {
      String s = GsonHelper.m_13906_(p_119836_, "name");
      Sound.Type sound$type = this.m_119832_(p_119836_, Sound.Type.FILE);
      float f = GsonHelper.m_13820_(p_119836_, "volume", 1.0F);
      Validate.isTrue(f > 0.0F, "Invalid volume");
      float f1 = GsonHelper.m_13820_(p_119836_, "pitch", 1.0F);
      Validate.isTrue(f1 > 0.0F, "Invalid pitch");
      int i = GsonHelper.m_13824_(p_119836_, "weight", 1);
      Validate.isTrue(i > 0, "Invalid weight");
      boolean flag = GsonHelper.m_13855_(p_119836_, "preload", false);
      boolean flag1 = GsonHelper.m_13855_(p_119836_, "stream", false);
      int j = GsonHelper.m_13824_(p_119836_, "attenuation_distance", 16);
      return new Sound(s, f, f1, i, sound$type, flag1, flag, j);
   }

   private Sound.Type m_119832_(JsonObject p_119833_, Sound.Type p_119834_) {
      Sound.Type sound$type = p_119834_;
      if (p_119833_.has("type")) {
         sound$type = Sound.Type.m_119810_(GsonHelper.m_13906_(p_119833_, "type"));
         Validate.notNull(sound$type, "Invalid type");
      }

      return sound$type;
   }
}