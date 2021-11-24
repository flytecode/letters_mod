package net.minecraft.server.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameProfileCache {
   private static final Logger f_10964_ = LogManager.getLogger();
   private static final int f_143955_ = 1000;
   private static final int f_143956_ = 1;
   private static boolean f_10965_;
   private final Map<String, GameProfileCache.GameProfileInfo> f_10966_ = Maps.newConcurrentMap();
   private final Map<UUID, GameProfileCache.GameProfileInfo> f_10967_ = Maps.newConcurrentMap();
   private final Map<String, CompletableFuture<Optional<GameProfile>>> f_143957_ = Maps.newConcurrentMap();
   private final GameProfileRepository f_10968_;
   private final Gson f_10969_ = (new GsonBuilder()).create();
   private final File f_10970_;
   private final AtomicLong f_10971_ = new AtomicLong();
   @Nullable
   private Executor f_143958_;

   public GameProfileCache(GameProfileRepository p_10974_, File p_10975_) {
      this.f_10968_ = p_10974_;
      this.f_10970_ = p_10975_;
      Lists.reverse(this.m_10976_()).forEach(this::m_10979_);
   }

   private void m_10979_(GameProfileCache.GameProfileInfo p_10980_) {
      GameProfile gameprofile = p_10980_.m_11028_();
      p_10980_.m_11029_(this.m_11008_());
      String s = gameprofile.getName();
      if (s != null) {
         this.f_10966_.put(s.toLowerCase(Locale.ROOT), p_10980_);
      }

      UUID uuid = gameprofile.getId();
      if (uuid != null) {
         this.f_10967_.put(uuid, p_10980_);
      }

   }

   private static Optional<GameProfile> m_10993_(GameProfileRepository p_10994_, String p_10995_) {
      final AtomicReference<GameProfile> atomicreference = new AtomicReference<>();
      ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
         public void onProfileLookupSucceeded(GameProfile p_11017_) {
            atomicreference.set(p_11017_);
         }

         public void onProfileLookupFailed(GameProfile p_11014_, Exception p_11015_) {
            atomicreference.set((GameProfile)null);
         }
      };
      p_10994_.findProfilesByNames(new String[]{p_10995_}, Agent.MINECRAFT, profilelookupcallback);
      GameProfile gameprofile = atomicreference.get();
      if (!m_11007_() && gameprofile == null) {
         UUID uuid = Player.m_36198_(new GameProfile((UUID)null, p_10995_));
         return Optional.of(new GameProfile(uuid, p_10995_));
      } else {
         return Optional.ofNullable(gameprofile);
      }
   }

   public static void m_11004_(boolean p_11005_) {
      f_10965_ = p_11005_;
   }

   private static boolean m_11007_() {
      return f_10965_;
   }

   public void m_10991_(GameProfile p_10992_) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(2, 1);
      Date date = calendar.getTime();
      GameProfileCache.GameProfileInfo gameprofilecache$gameprofileinfo = new GameProfileCache.GameProfileInfo(p_10992_, date);
      this.m_10979_(gameprofilecache$gameprofileinfo);
      this.m_11006_();
   }

   private long m_11008_() {
      return this.f_10971_.incrementAndGet();
   }

   public Optional<GameProfile> m_10996_(String p_10997_) {
      String s = p_10997_.toLowerCase(Locale.ROOT);
      GameProfileCache.GameProfileInfo gameprofilecache$gameprofileinfo = this.f_10966_.get(s);
      boolean flag = false;
      if (gameprofilecache$gameprofileinfo != null && (new Date()).getTime() >= gameprofilecache$gameprofileinfo.f_11019_.getTime()) {
         this.f_10967_.remove(gameprofilecache$gameprofileinfo.m_11028_().getId());
         this.f_10966_.remove(gameprofilecache$gameprofileinfo.m_11028_().getName().toLowerCase(Locale.ROOT));
         flag = true;
         gameprofilecache$gameprofileinfo = null;
      }

      Optional<GameProfile> optional;
      if (gameprofilecache$gameprofileinfo != null) {
         gameprofilecache$gameprofileinfo.m_11029_(this.m_11008_());
         optional = Optional.of(gameprofilecache$gameprofileinfo.m_11028_());
      } else {
         optional = m_10993_(this.f_10968_, s);
         if (optional.isPresent()) {
            this.m_10991_(optional.get());
            flag = false;
         }
      }

      if (flag) {
         this.m_11006_();
      }

      return optional;
   }

   public void m_143967_(String p_143968_, Consumer<Optional<GameProfile>> p_143969_) {
      if (this.f_143958_ == null) {
         throw new IllegalStateException("No executor");
      } else {
         CompletableFuture<Optional<GameProfile>> completablefuture = this.f_143957_.get(p_143968_);
         if (completablefuture != null) {
            this.f_143957_.put(p_143968_, completablefuture.whenCompleteAsync((p_143984_, p_143985_) -> {
               p_143969_.accept(p_143984_);
            }, this.f_143958_));
         } else {
            this.f_143957_.put(p_143968_, CompletableFuture.supplyAsync(() -> {
               return this.m_10996_(p_143968_);
            }, Util.m_137578_()).whenCompleteAsync((p_143965_, p_143966_) -> {
               this.f_143957_.remove(p_143968_);
            }, this.f_143958_).whenCompleteAsync((p_143978_, p_143979_) -> {
               p_143969_.accept(p_143978_);
            }, this.f_143958_));
         }

      }
   }

   public Optional<GameProfile> m_11002_(UUID p_11003_) {
      GameProfileCache.GameProfileInfo gameprofilecache$gameprofileinfo = this.f_10967_.get(p_11003_);
      if (gameprofilecache$gameprofileinfo == null) {
         return Optional.empty();
      } else {
         gameprofilecache$gameprofileinfo.m_11029_(this.m_11008_());
         return Optional.of(gameprofilecache$gameprofileinfo.m_11028_());
      }
   }

   public void m_143974_(Executor p_143975_) {
      this.f_143958_ = p_143975_;
   }

   private static DateFormat m_11009_() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   }

   public List<GameProfileCache.GameProfileInfo> m_10976_() {
      List<GameProfileCache.GameProfileInfo> list = Lists.newArrayList();

      try {
         Reader reader = Files.newReader(this.f_10970_, StandardCharsets.UTF_8);

         Object object;
         label61: {
            try {
               JsonArray jsonarray = this.f_10969_.fromJson(reader, JsonArray.class);
               if (jsonarray == null) {
                  object = list;
                  break label61;
               }

               DateFormat dateformat = m_11009_();
               jsonarray.forEach((p_143973_) -> {
                  m_10988_(p_143973_, dateformat).ifPresent(list::add);
               });
            } catch (Throwable throwable1) {
               if (reader != null) {
                  try {
                     reader.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (reader != null) {
               reader.close();
            }

            return list;
         }

         if (reader != null) {
            reader.close();
         }

         return (List<GameProfileCache.GameProfileInfo>)object;
      } catch (FileNotFoundException filenotfoundexception) {
      } catch (JsonParseException | IOException ioexception) {
         f_10964_.warn("Failed to load profile cache {}", this.f_10970_, ioexception);
      }

      return list;
   }

   public void m_11006_() {
      JsonArray jsonarray = new JsonArray();
      DateFormat dateformat = m_11009_();
      this.m_10977_(1000).forEach((p_143962_) -> {
         jsonarray.add(m_10981_(p_143962_, dateformat));
      });
      String s = this.f_10969_.toJson((JsonElement)jsonarray);

      try {
         Writer writer = Files.newWriter(this.f_10970_, StandardCharsets.UTF_8);

         try {
            writer.write(s);
         } catch (Throwable throwable1) {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (writer != null) {
            writer.close();
         }
      } catch (IOException ioexception) {
      }

   }

   private Stream<GameProfileCache.GameProfileInfo> m_10977_(int p_10978_) {
      return ImmutableList.copyOf(this.f_10967_.values()).stream().sorted(Comparator.comparing(GameProfileCache.GameProfileInfo::m_11034_).reversed()).limit((long)p_10978_);
   }

   private static JsonElement m_10981_(GameProfileCache.GameProfileInfo p_10982_, DateFormat p_10983_) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("name", p_10982_.m_11028_().getName());
      UUID uuid = p_10982_.m_11028_().getId();
      jsonobject.addProperty("uuid", uuid == null ? "" : uuid.toString());
      jsonobject.addProperty("expiresOn", p_10983_.format(p_10982_.m_11033_()));
      return jsonobject;
   }

   private static Optional<GameProfileCache.GameProfileInfo> m_10988_(JsonElement p_10989_, DateFormat p_10990_) {
      if (p_10989_.isJsonObject()) {
         JsonObject jsonobject = p_10989_.getAsJsonObject();
         JsonElement jsonelement = jsonobject.get("name");
         JsonElement jsonelement1 = jsonobject.get("uuid");
         JsonElement jsonelement2 = jsonobject.get("expiresOn");
         if (jsonelement != null && jsonelement1 != null) {
            String s = jsonelement1.getAsString();
            String s1 = jsonelement.getAsString();
            Date date = null;
            if (jsonelement2 != null) {
               try {
                  date = p_10990_.parse(jsonelement2.getAsString());
               } catch (ParseException parseexception) {
               }
            }

            if (s1 != null && s != null && date != null) {
               UUID uuid;
               try {
                  uuid = UUID.fromString(s);
               } catch (Throwable throwable) {
                  return Optional.empty();
               }

               return Optional.of(new GameProfileCache.GameProfileInfo(new GameProfile(uuid, s1), date));
            } else {
               return Optional.empty();
            }
         } else {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   static class GameProfileInfo {
      private final GameProfile f_11018_;
      final Date f_11019_;
      private volatile long f_11020_;

      GameProfileInfo(GameProfile p_11022_, Date p_11023_) {
         this.f_11018_ = p_11022_;
         this.f_11019_ = p_11023_;
      }

      public GameProfile m_11028_() {
         return this.f_11018_;
      }

      public Date m_11033_() {
         return this.f_11019_;
      }

      public void m_11029_(long p_11030_) {
         this.f_11020_ = p_11030_;
      }

      public long m_11034_() {
         return this.f_11020_;
      }
   }
}