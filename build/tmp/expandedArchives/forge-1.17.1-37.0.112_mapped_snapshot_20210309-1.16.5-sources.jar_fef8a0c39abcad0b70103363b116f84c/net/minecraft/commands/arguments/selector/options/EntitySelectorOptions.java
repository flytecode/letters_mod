package net.minecraft.commands.arguments.selector.options;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.WrappedMinMaxBounds;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public class EntitySelectorOptions {
   private static final Map<String, EntitySelectorOptions.Option> f_121392_ = Maps.newHashMap();
   public static final DynamicCommandExceptionType f_121384_ = new DynamicCommandExceptionType((p_121520_) -> {
      return new TranslatableComponent("argument.entity.options.unknown", p_121520_);
   });
   public static final DynamicCommandExceptionType f_121385_ = new DynamicCommandExceptionType((p_121516_) -> {
      return new TranslatableComponent("argument.entity.options.inapplicable", p_121516_);
   });
   public static final SimpleCommandExceptionType f_121386_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.options.distance.negative"));
   public static final SimpleCommandExceptionType f_121387_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.options.level.negative"));
   public static final SimpleCommandExceptionType f_121388_ = new SimpleCommandExceptionType(new TranslatableComponent("argument.entity.options.limit.toosmall"));
   public static final DynamicCommandExceptionType f_121389_ = new DynamicCommandExceptionType((p_121508_) -> {
      return new TranslatableComponent("argument.entity.options.sort.irreversible", p_121508_);
   });
   public static final DynamicCommandExceptionType f_121390_ = new DynamicCommandExceptionType((p_121493_) -> {
      return new TranslatableComponent("argument.entity.options.mode.invalid", p_121493_);
   });
   public static final DynamicCommandExceptionType f_121391_ = new DynamicCommandExceptionType((p_121452_) -> {
      return new TranslatableComponent("argument.entity.options.type.invalid", p_121452_);
   });

   public static void m_121453_(String p_121454_, EntitySelectorOptions.Modifier p_121455_, Predicate<EntitySelectorParser> p_121456_, Component p_121457_) {
      f_121392_.put(p_121454_, new EntitySelectorOptions.Option(p_121455_, p_121456_, p_121457_));
   }

   public static void m_121426_() {
      if (f_121392_.isEmpty()) {
         m_121453_("name", (p_121425_) -> {
            int i = p_121425_.m_121346_().getCursor();
            boolean flag = p_121425_.m_121330_();
            String s = p_121425_.m_121346_().readString();
            if (p_121425_.m_121380_() && !flag) {
               p_121425_.m_121346_().setCursor(i);
               throw f_121385_.createWithContext(p_121425_.m_121346_(), "name");
            } else {
               if (flag) {
                  p_121425_.m_121315_(true);
               } else {
                  p_121425_.m_121302_(true);
               }

               p_121425_.m_121272_((p_175209_) -> {
                  return p_175209_.m_7755_().getString().equals(s) != flag;
               });
            }
         }, (p_121423_) -> {
            return !p_121423_.m_121379_();
         }, new TranslatableComponent("argument.entity.options.name.description"));
         m_121453_("distance", (p_121421_) -> {
            int i = p_121421_.m_121346_().getCursor();
            MinMaxBounds.Doubles minmaxbounds$doubles = MinMaxBounds.Doubles.m_154793_(p_121421_.m_121346_());
            if ((minmaxbounds$doubles.m_55305_() == null || !(minmaxbounds$doubles.m_55305_() < 0.0D)) && (minmaxbounds$doubles.m_55326_() == null || !(minmaxbounds$doubles.m_55326_() < 0.0D))) {
               p_121421_.m_175127_(minmaxbounds$doubles);
               p_121421_.m_121352_();
            } else {
               p_121421_.m_121346_().setCursor(i);
               throw f_121386_.createWithContext(p_121421_.m_121346_());
            }
         }, (p_121419_) -> {
            return p_121419_.m_175142_().m_55327_();
         }, new TranslatableComponent("argument.entity.options.distance.description"));
         m_121453_("level", (p_121417_) -> {
            int i = p_121417_.m_121346_().getCursor();
            MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55375_(p_121417_.m_121346_());
            if ((minmaxbounds$ints.m_55305_() == null || minmaxbounds$ints.m_55305_() >= 0) && (minmaxbounds$ints.m_55326_() == null || minmaxbounds$ints.m_55326_() >= 0)) {
               p_121417_.m_121245_(minmaxbounds$ints);
               p_121417_.m_121279_(false);
            } else {
               p_121417_.m_121346_().setCursor(i);
               throw f_121387_.createWithContext(p_121417_.m_121346_());
            }
         }, (p_121415_) -> {
            return p_121415_.m_121361_().m_55327_();
         }, new TranslatableComponent("argument.entity.options.level.description"));
         m_121453_("x", (p_121413_) -> {
            p_121413_.m_121352_();
            p_121413_.m_121231_(p_121413_.m_121346_().readDouble());
         }, (p_121411_) -> {
            return p_121411_.m_121371_() == null;
         }, new TranslatableComponent("argument.entity.options.x.description"));
         m_121453_("y", (p_121409_) -> {
            p_121409_.m_121352_();
            p_121409_.m_121282_(p_121409_.m_121346_().readDouble());
         }, (p_121407_) -> {
            return p_121407_.m_121372_() == null;
         }, new TranslatableComponent("argument.entity.options.y.description"));
         m_121453_("z", (p_121405_) -> {
            p_121405_.m_121352_();
            p_121405_.m_121305_(p_121405_.m_121346_().readDouble());
         }, (p_121403_) -> {
            return p_121403_.m_121373_() == null;
         }, new TranslatableComponent("argument.entity.options.z.description"));
         m_121453_("dx", (p_121401_) -> {
            p_121401_.m_121352_();
            p_121401_.m_121318_(p_121401_.m_121346_().readDouble());
         }, (p_121399_) -> {
            return p_121399_.m_121374_() == null;
         }, new TranslatableComponent("argument.entity.options.dx.description"));
         m_121453_("dy", (p_121397_) -> {
            p_121397_.m_121352_();
            p_121397_.m_121331_(p_121397_.m_121346_().readDouble());
         }, (p_121395_) -> {
            return p_121395_.m_121375_() == null;
         }, new TranslatableComponent("argument.entity.options.dy.description"));
         m_121453_("dz", (p_121562_) -> {
            p_121562_.m_121352_();
            p_121562_.m_121339_(p_121562_.m_121346_().readDouble());
         }, (p_121560_) -> {
            return p_121560_.m_121376_() == null;
         }, new TranslatableComponent("argument.entity.options.dz.description"));
         m_121453_("x_rotation", (p_121558_) -> {
            p_121558_.m_121252_(WrappedMinMaxBounds.m_75359_(p_121558_.m_121346_(), true, Mth::m_14177_));
         }, (p_121556_) -> {
            return p_121556_.m_121367_() == WrappedMinMaxBounds.f_75350_;
         }, new TranslatableComponent("argument.entity.options.x_rotation.description"));
         m_121453_("y_rotation", (p_121554_) -> {
            p_121554_.m_121289_(WrappedMinMaxBounds.m_75359_(p_121554_.m_121346_(), true, Mth::m_14177_));
         }, (p_121552_) -> {
            return p_121552_.m_121370_() == WrappedMinMaxBounds.f_75350_;
         }, new TranslatableComponent("argument.entity.options.y_rotation.description"));
         m_121453_("limit", (p_121550_) -> {
            int i = p_121550_.m_121346_().getCursor();
            int j = p_121550_.m_121346_().readInt();
            if (j < 1) {
               p_121550_.m_121346_().setCursor(i);
               throw f_121388_.createWithContext(p_121550_.m_121346_());
            } else {
               p_121550_.m_121237_(j);
               p_121550_.m_121328_(true);
            }
         }, (p_121548_) -> {
            return !p_121548_.m_121378_() && !p_121548_.m_121381_();
         }, new TranslatableComponent("argument.entity.options.limit.description"));
         m_121453_("sort", (p_121546_) -> {
            int i = p_121546_.m_121346_().getCursor();
            String s = p_121546_.m_121346_().readUnquotedString();
            p_121546_.m_121270_((p_175153_, p_175154_) -> {
               return SharedSuggestionProvider.m_82970_(Arrays.asList("nearest", "furthest", "random", "arbitrary"), p_175153_);
            });
            BiConsumer<Vec3, List<? extends Entity>> biconsumer;
            switch(s) {
            case "nearest":
               biconsumer = EntitySelectorParser.f_121197_;
               break;
            case "furthest":
               biconsumer = EntitySelectorParser.f_121198_;
               break;
            case "random":
               biconsumer = EntitySelectorParser.f_121199_;
               break;
            case "arbitrary":
               biconsumer = EntitySelectorParser.f_121196_;
               break;
            default:
               p_121546_.m_121346_().setCursor(i);
               throw f_121389_.createWithContext(p_121546_.m_121346_(), s);
            }

            p_121546_.m_121268_(biconsumer);
            p_121546_.m_121336_(true);
         }, (p_121544_) -> {
            return !p_121544_.m_121378_() && !p_121544_.m_121382_();
         }, new TranslatableComponent("argument.entity.options.sort.description"));
         m_121453_("gamemode", (p_121542_) -> {
            p_121542_.m_121270_((p_175193_, p_175194_) -> {
               String s1 = p_175193_.getRemaining().toLowerCase(Locale.ROOT);
               boolean flag1 = !p_121542_.m_121222_();
               boolean flag2 = true;
               if (!s1.isEmpty()) {
                  if (s1.charAt(0) == '!') {
                     flag1 = false;
                     s1 = s1.substring(1);
                  } else {
                     flag2 = false;
                  }
               }

               for(GameType gametype1 : GameType.values()) {
                  if (gametype1.m_46405_().toLowerCase(Locale.ROOT).startsWith(s1)) {
                     if (flag2) {
                        p_175193_.suggest("!" + gametype1.m_46405_());
                     }

                     if (flag1) {
                        p_175193_.suggest(gametype1.m_46405_());
                     }
                  }
               }

               return p_175193_.buildFuture();
            });
            int i = p_121542_.m_121346_().getCursor();
            boolean flag = p_121542_.m_121330_();
            if (p_121542_.m_121222_() && !flag) {
               p_121542_.m_121346_().setCursor(i);
               throw f_121385_.createWithContext(p_121542_.m_121346_(), "gamemode");
            } else {
               String s = p_121542_.m_121346_().readUnquotedString();
               GameType gametype = GameType.m_46402_(s, (GameType)null);
               if (gametype == null) {
                  p_121542_.m_121346_().setCursor(i);
                  throw f_121390_.createWithContext(p_121542_.m_121346_(), s);
               } else {
                  p_121542_.m_121279_(false);
                  p_121542_.m_121272_((p_175190_) -> {
                     if (!(p_175190_ instanceof ServerPlayer)) {
                        return false;
                     } else {
                        GameType gametype1 = ((ServerPlayer)p_175190_).f_8941_.m_9290_();
                        return flag ? gametype1 != gametype : gametype1 == gametype;
                     }
                  });
                  if (flag) {
                     p_121542_.m_121350_(true);
                  } else {
                     p_121542_.m_121344_(true);
                  }

               }
            }
         }, (p_121540_) -> {
            return !p_121540_.m_121383_();
         }, new TranslatableComponent("argument.entity.options.gamemode.description"));
         m_121453_("team", (p_121538_) -> {
            boolean flag = p_121538_.m_121330_();
            String s = p_121538_.m_121346_().readUnquotedString();
            p_121538_.m_121272_((p_175198_) -> {
               if (!(p_175198_ instanceof LivingEntity)) {
                  return false;
               } else {
                  Team team = p_175198_.m_5647_();
                  String s1 = team == null ? "" : team.m_5758_();
                  return s1.equals(s) != flag;
               }
            });
            if (flag) {
               p_121538_.m_121359_(true);
            } else {
               p_121538_.m_121356_(true);
            }

         }, (p_121536_) -> {
            return !p_121536_.m_121223_();
         }, new TranslatableComponent("argument.entity.options.team.description"));
         m_121453_("type", (p_121534_) -> {
            p_121534_.m_121270_((p_175161_, p_175162_) -> {
               SharedSuggestionProvider.m_82929_(Registry.f_122826_.m_6566_(), p_175161_, String.valueOf('!'));
               SharedSuggestionProvider.m_82929_(EntityTypeTags.m_13126_().m_13406_(), p_175161_, "!#");
               if (!p_121534_.m_121226_()) {
                  SharedSuggestionProvider.m_82926_(Registry.f_122826_.m_6566_(), p_175161_);
                  SharedSuggestionProvider.m_82929_(EntityTypeTags.m_13126_().m_13406_(), p_175161_, String.valueOf('#'));
               }

               return p_175161_.buildFuture();
            });
            int i = p_121534_.m_121346_().getCursor();
            boolean flag = p_121534_.m_121330_();
            if (p_121534_.m_121226_() && !flag) {
               p_121534_.m_121346_().setCursor(i);
               throw f_121385_.createWithContext(p_121534_.m_121346_(), "type");
            } else {
               if (flag) {
                  p_121534_.m_121224_();
               }

               if (p_121534_.m_121338_()) {
                  ResourceLocation resourcelocation = ResourceLocation.m_135818_(p_121534_.m_121346_());
                  p_121534_.m_121272_((p_175205_) -> {
                     return p_175205_.m_6095_().m_20609_(p_175205_.m_20194_().m_129895_().m_144452_(Registry.f_122903_).m_7689_(resourcelocation)) != flag;
                  });
               } else {
                  ResourceLocation resourcelocation1 = ResourceLocation.m_135818_(p_121534_.m_121346_());
                  EntityType<?> entitytype = Registry.f_122826_.m_6612_(resourcelocation1).orElseThrow(() -> {
                     p_121534_.m_121346_().setCursor(i);
                     return f_121391_.createWithContext(p_121534_.m_121346_(), resourcelocation1.toString());
                  });
                  if (Objects.equals(EntityType.f_20532_, entitytype) && !flag) {
                     p_121534_.m_121279_(false);
                  }

                  p_121534_.m_121272_((p_175151_) -> {
                     return Objects.equals(entitytype, p_175151_.m_6095_()) != flag;
                  });
                  if (!flag) {
                     p_121534_.m_121241_(entitytype);
                  }
               }

            }
         }, (p_121532_) -> {
            return !p_121532_.m_121225_();
         }, new TranslatableComponent("argument.entity.options.type.description"));
         m_121453_("tag", (p_121530_) -> {
            boolean flag = p_121530_.m_121330_();
            String s = p_121530_.m_121346_().readUnquotedString();
            p_121530_.m_121272_((p_175166_) -> {
               if ("".equals(s)) {
                  return p_175166_.m_19880_().isEmpty() != flag;
               } else {
                  return p_175166_.m_19880_().contains(s) != flag;
               }
            });
         }, (p_121528_) -> {
            return true;
         }, new TranslatableComponent("argument.entity.options.tag.description"));
         m_121453_("nbt", (p_121526_) -> {
            boolean flag = p_121526_.m_121330_();
            CompoundTag compoundtag = (new TagParser(p_121526_.m_121346_())).m_129373_();
            p_121526_.m_121272_((p_175176_) -> {
               CompoundTag compoundtag1 = p_175176_.m_20240_(new CompoundTag());
               if (p_175176_ instanceof ServerPlayer) {
                  ItemStack itemstack = ((ServerPlayer)p_175176_).m_150109_().m_36056_();
                  if (!itemstack.m_41619_()) {
                     compoundtag1.m_128365_("SelectedItem", itemstack.m_41739_(new CompoundTag()));
                  }
               }

               return NbtUtils.m_129235_(compoundtag, compoundtag1, true) != flag;
            });
         }, (p_121524_) -> {
            return true;
         }, new TranslatableComponent("argument.entity.options.nbt.description"));
         m_121453_("scores", (p_121522_) -> {
            StringReader stringreader = p_121522_.m_121346_();
            Map<String, MinMaxBounds.Ints> map = Maps.newHashMap();
            stringreader.expect('{');
            stringreader.skipWhitespace();

            while(stringreader.canRead() && stringreader.peek() != '}') {
               stringreader.skipWhitespace();
               String s = stringreader.readUnquotedString();
               stringreader.skipWhitespace();
               stringreader.expect('=');
               stringreader.skipWhitespace();
               MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55375_(stringreader);
               map.put(s, minmaxbounds$ints);
               stringreader.skipWhitespace();
               if (stringreader.canRead() && stringreader.peek() == ',') {
                  stringreader.skip();
               }
            }

            stringreader.expect('}');
            if (!map.isEmpty()) {
               p_121522_.m_121272_((p_175201_) -> {
                  Scoreboard scoreboard = p_175201_.m_20194_().m_129896_();
                  String s1 = p_175201_.m_6302_();

                  for(Entry<String, MinMaxBounds.Ints> entry : map.entrySet()) {
                     Objective objective = scoreboard.m_83477_(entry.getKey());
                     if (objective == null) {
                        return false;
                     }

                     if (!scoreboard.m_83461_(s1, objective)) {
                        return false;
                     }

                     Score score = scoreboard.m_83471_(s1, objective);
                     int i = score.m_83400_();
                     if (!entry.getValue().m_55390_(i)) {
                        return false;
                     }
                  }

                  return true;
               });
            }

            p_121522_.m_121365_(true);
         }, (p_121518_) -> {
            return !p_121518_.m_121227_();
         }, new TranslatableComponent("argument.entity.options.scores.description"));
         m_121453_("advancements", (p_121514_) -> {
            StringReader stringreader = p_121514_.m_121346_();
            Map<ResourceLocation, Predicate<AdvancementProgress>> map = Maps.newHashMap();
            stringreader.expect('{');
            stringreader.skipWhitespace();

            while(stringreader.canRead() && stringreader.peek() != '}') {
               stringreader.skipWhitespace();
               ResourceLocation resourcelocation = ResourceLocation.m_135818_(stringreader);
               stringreader.skipWhitespace();
               stringreader.expect('=');
               stringreader.skipWhitespace();
               if (stringreader.canRead() && stringreader.peek() == '{') {
                  Map<String, Predicate<CriterionProgress>> map1 = Maps.newHashMap();
                  stringreader.skipWhitespace();
                  stringreader.expect('{');
                  stringreader.skipWhitespace();

                  while(stringreader.canRead() && stringreader.peek() != '}') {
                     stringreader.skipWhitespace();
                     String s = stringreader.readUnquotedString();
                     stringreader.skipWhitespace();
                     stringreader.expect('=');
                     stringreader.skipWhitespace();
                     boolean flag1 = stringreader.readBoolean();
                     map1.put(s, (p_175186_) -> {
                        return p_175186_.m_12911_() == flag1;
                     });
                     stringreader.skipWhitespace();
                     if (stringreader.canRead() && stringreader.peek() == ',') {
                        stringreader.skip();
                     }
                  }

                  stringreader.skipWhitespace();
                  stringreader.expect('}');
                  stringreader.skipWhitespace();
                  map.put(resourcelocation, (p_175169_) -> {
                     for(Entry<String, Predicate<CriterionProgress>> entry : map1.entrySet()) {
                        CriterionProgress criterionprogress = p_175169_.m_8214_(entry.getKey());
                        if (criterionprogress == null || !entry.getValue().test(criterionprogress)) {
                           return false;
                        }
                     }

                     return true;
                  });
               } else {
                  boolean flag = stringreader.readBoolean();
                  map.put(resourcelocation, (p_175183_) -> {
                     return p_175183_.m_8193_() == flag;
                  });
               }

               stringreader.skipWhitespace();
               if (stringreader.canRead() && stringreader.peek() == ',') {
                  stringreader.skip();
               }
            }

            stringreader.expect('}');
            if (!map.isEmpty()) {
               p_121514_.m_121272_((p_175172_) -> {
                  if (!(p_175172_ instanceof ServerPlayer)) {
                     return false;
                  } else {
                     ServerPlayer serverplayer = (ServerPlayer)p_175172_;
                     PlayerAdvancements playeradvancements = serverplayer.m_8960_();
                     ServerAdvancementManager serveradvancementmanager = serverplayer.m_20194_().m_129889_();

                     for(Entry<ResourceLocation, Predicate<AdvancementProgress>> entry : map.entrySet()) {
                        Advancement advancement = serveradvancementmanager.m_136041_(entry.getKey());
                        if (advancement == null || !entry.getValue().test(playeradvancements.m_135996_(advancement))) {
                           return false;
                        }
                     }

                     return true;
                  }
               });
               p_121514_.m_121279_(false);
            }

            p_121514_.m_121368_(true);
         }, (p_121506_) -> {
            return !p_121506_.m_121228_();
         }, new TranslatableComponent("argument.entity.options.advancements.description"));
         m_121453_("predicate", (p_121487_) -> {
            boolean flag = p_121487_.m_121330_();
            ResourceLocation resourcelocation = ResourceLocation.m_135818_(p_121487_.m_121346_());
            p_121487_.m_121272_((p_175180_) -> {
               if (!(p_175180_.f_19853_ instanceof ServerLevel)) {
                  return false;
               } else {
                  ServerLevel serverlevel = (ServerLevel)p_175180_.f_19853_;
                  LootItemCondition lootitemcondition = serverlevel.m_142572_().m_129899_().m_79252_(resourcelocation);
                  if (lootitemcondition == null) {
                     return false;
                  } else {
                     LootContext lootcontext = (new LootContext.Builder(serverlevel)).m_78972_(LootContextParams.f_81455_, p_175180_).m_78972_(LootContextParams.f_81460_, p_175180_.m_20182_()).m_78975_(LootContextParamSets.f_81413_);
                     return flag ^ lootitemcondition.test(lootcontext);
                  }
               }
            });
         }, (p_121435_) -> {
            return true;
         }, new TranslatableComponent("argument.entity.options.predicate.description"));
      }
   }

   public static EntitySelectorOptions.Modifier m_121447_(EntitySelectorParser p_121448_, String p_121449_, int p_121450_) throws CommandSyntaxException {
      EntitySelectorOptions.Option entityselectoroptions$option = f_121392_.get(p_121449_);
      if (entityselectoroptions$option != null) {
         if (entityselectoroptions$option.f_121566_.test(p_121448_)) {
            return entityselectoroptions$option.f_121565_;
         } else {
            throw f_121385_.createWithContext(p_121448_.m_121346_(), p_121449_);
         }
      } else {
         p_121448_.m_121346_().setCursor(p_121450_);
         throw f_121384_.createWithContext(p_121448_.m_121346_(), p_121449_);
      }
   }

   public static void m_121440_(EntitySelectorParser p_121441_, SuggestionsBuilder p_121442_) {
      String s = p_121442_.getRemaining().toLowerCase(Locale.ROOT);

      for(Entry<String, EntitySelectorOptions.Option> entry : f_121392_.entrySet()) {
         if ((entry.getValue()).f_121566_.test(p_121441_) && entry.getKey().toLowerCase(Locale.ROOT).startsWith(s)) {
            p_121442_.suggest((String)entry.getKey() + "=", (entry.getValue()).f_121567_);
         }
      }

   }

   public interface Modifier {
      void m_121563_(EntitySelectorParser p_121564_) throws CommandSyntaxException;
   }

   static class Option {
      public final EntitySelectorOptions.Modifier f_121565_;
      public final Predicate<EntitySelectorParser> f_121566_;
      public final Component f_121567_;

      Option(EntitySelectorOptions.Modifier p_121569_, Predicate<EntitySelectorParser> p_121570_, Component p_121571_) {
         this.f_121565_ = p_121569_;
         this.f_121566_ = p_121570_;
         this.f_121567_ = p_121571_;
      }
   }
}