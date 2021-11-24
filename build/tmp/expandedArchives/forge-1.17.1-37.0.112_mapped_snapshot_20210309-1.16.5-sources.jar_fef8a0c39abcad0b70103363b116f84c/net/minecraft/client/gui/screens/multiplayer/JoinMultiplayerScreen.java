package net.minecraft.client.gui.screens.multiplayer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DirectJoinServerScreen;
import net.minecraft.client.gui.screens.EditServerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.ServerStatusPinger;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.LanServer;
import net.minecraft.client.server.LanServerDetection;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class JoinMultiplayerScreen extends Screen {
   private static final Logger f_99674_ = LogManager.getLogger();
   private final ServerStatusPinger f_99675_ = new ServerStatusPinger();
   private final Screen f_99676_;
   protected ServerSelectionList f_99673_;
   private ServerList f_99677_;
   private Button f_99678_;
   private Button f_99679_;
   private Button f_99680_;
   private List<Component> f_99681_;
   private ServerData f_99682_;
   private LanServerDetection.LanServerList f_99683_;
   private LanServerDetection.LanServerDetector f_99684_;
   private boolean f_99685_;

   public JoinMultiplayerScreen(Screen p_99688_) {
      super(new TranslatableComponent("multiplayer.title"));
      this.f_99676_ = p_99688_;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_96541_.f_91068_.m_90926_(true);
      if (this.f_99685_) {
         this.f_99673_.m_93437_(this.f_96543_, this.f_96544_, 32, this.f_96544_ - 64);
      } else {
         this.f_99685_ = true;
         this.f_99677_ = new ServerList(this.f_96541_);
         this.f_99677_.m_105431_();
         this.f_99683_ = new LanServerDetection.LanServerList();

         try {
            this.f_99684_ = new LanServerDetection.LanServerDetector(this.f_99683_);
            this.f_99684_.start();
         } catch (Exception exception) {
            f_99674_.warn("Unable to start LAN server detection: {}", (Object)exception.getMessage());
         }

         this.f_99673_ = new ServerSelectionList(this, this.f_96541_, this.f_96543_, this.f_96544_, 32, this.f_96544_ - 64, 36);
         this.f_99673_.m_99797_(this.f_99677_);
      }

      this.m_7787_(this.f_99673_);
      this.f_99679_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 52, 100, 20, new TranslatableComponent("selectServer.select"), (p_99728_) -> {
         this.m_99729_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 50, this.f_96544_ - 52, 100, 20, new TranslatableComponent("selectServer.direct"), (p_99724_) -> {
         this.f_99682_ = new ServerData(I18n.m_118938_("selectServer.defaultName"), "", false);
         this.f_96541_.m_91152_(new DirectJoinServerScreen(this, this::m_99725_, this.f_99682_));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 4 + 50, this.f_96544_ - 52, 100, 20, new TranslatableComponent("selectServer.add"), (p_99720_) -> {
         this.f_99682_ = new ServerData(I18n.m_118938_("selectServer.defaultName"), "", false);
         this.f_96541_.m_91152_(new EditServerScreen(this, this::m_99721_, this.f_99682_));
      }));
      this.f_99678_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 28, 70, 20, new TranslatableComponent("selectServer.edit"), (p_99715_) -> {
         ServerSelectionList.Entry serverselectionlist$entry = this.f_99673_.m_93511_();
         if (serverselectionlist$entry instanceof ServerSelectionList.OnlineServerEntry) {
            ServerData serverdata = ((ServerSelectionList.OnlineServerEntry)serverselectionlist$entry).m_99898_();
            this.f_99682_ = new ServerData(serverdata.f_105362_, serverdata.f_105363_, false);
            this.f_99682_.m_105381_(serverdata);
            this.f_96541_.m_91152_(new EditServerScreen(this, this::m_99716_, this.f_99682_));
         }

      }));
      this.f_99680_ = this.m_142416_(new Button(this.f_96543_ / 2 - 74, this.f_96544_ - 28, 70, 20, new TranslatableComponent("selectServer.delete"), (p_99710_) -> {
         ServerSelectionList.Entry serverselectionlist$entry = this.f_99673_.m_93511_();
         if (serverselectionlist$entry instanceof ServerSelectionList.OnlineServerEntry) {
            String s = ((ServerSelectionList.OnlineServerEntry)serverselectionlist$entry).m_99898_().f_105362_;
            if (s != null) {
               Component component = new TranslatableComponent("selectServer.deleteQuestion");
               Component component1 = new TranslatableComponent("selectServer.deleteWarning", s);
               Component component2 = new TranslatableComponent("selectServer.deleteButton");
               Component component3 = CommonComponents.f_130656_;
               this.f_96541_.m_91152_(new ConfirmScreen(this::m_99711_, component, component1, component2, component3));
            }
         }

      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 28, 70, 20, new TranslatableComponent("selectServer.refresh"), (p_99706_) -> {
         this.m_99733_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 4 + 76, this.f_96544_ - 28, 75, 20, CommonComponents.f_130656_, (p_99699_) -> {
         this.f_96541_.m_91152_(this.f_99676_);
      }));
      this.m_99730_();
   }

   public void m_96624_() {
      super.m_96624_();
      if (this.f_99683_.m_120095_()) {
         List<LanServer> list = this.f_99683_.m_120100_();
         this.f_99683_.m_120099_();
         this.f_99673_.m_99799_(list);
      }

      this.f_99675_.m_105453_();
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
      if (this.f_99684_ != null) {
         this.f_99684_.interrupt();
         this.f_99684_ = null;
      }

      this.f_99675_.m_105465_();
   }

   private void m_99733_() {
      this.f_96541_.m_91152_(new JoinMultiplayerScreen(this.f_99676_));
   }

   private void m_99711_(boolean p_99712_) {
      ServerSelectionList.Entry serverselectionlist$entry = this.f_99673_.m_93511_();
      if (p_99712_ && serverselectionlist$entry instanceof ServerSelectionList.OnlineServerEntry) {
         this.f_99677_.m_105440_(((ServerSelectionList.OnlineServerEntry)serverselectionlist$entry).m_99898_());
         this.f_99677_.m_105442_();
         this.f_99673_.m_6987_((ServerSelectionList.Entry)null);
         this.f_99673_.m_99797_(this.f_99677_);
      }

      this.f_96541_.m_91152_(this);
   }

   private void m_99716_(boolean p_99717_) {
      ServerSelectionList.Entry serverselectionlist$entry = this.f_99673_.m_93511_();
      if (p_99717_ && serverselectionlist$entry instanceof ServerSelectionList.OnlineServerEntry) {
         ServerData serverdata = ((ServerSelectionList.OnlineServerEntry)serverselectionlist$entry).m_99898_();
         serverdata.f_105362_ = this.f_99682_.f_105362_;
         serverdata.f_105363_ = this.f_99682_.f_105363_;
         serverdata.m_105381_(this.f_99682_);
         this.f_99677_.m_105442_();
         this.f_99673_.m_99797_(this.f_99677_);
      }

      this.f_96541_.m_91152_(this);
   }

   private void m_99721_(boolean p_99722_) {
      if (p_99722_) {
         this.f_99677_.m_105443_(this.f_99682_);
         this.f_99677_.m_105442_();
         this.f_99673_.m_6987_((ServerSelectionList.Entry)null);
         this.f_99673_.m_99797_(this.f_99677_);
      }

      this.f_96541_.m_91152_(this);
   }

   private void m_99725_(boolean p_99726_) {
      if (p_99726_) {
         this.m_99702_(this.f_99682_);
      } else {
         this.f_96541_.m_91152_(this);
      }

   }

   public boolean m_7933_(int p_99690_, int p_99691_, int p_99692_) {
      if (super.m_7933_(p_99690_, p_99691_, p_99692_)) {
         return true;
      } else if (p_99690_ == 294) {
         this.m_99733_();
         return true;
      } else if (this.f_99673_.m_93511_() != null) {
         if (p_99690_ != 257 && p_99690_ != 335) {
            return this.f_99673_.m_7933_(p_99690_, p_99691_, p_99692_);
         } else {
            this.m_99729_();
            return true;
         }
      } else {
         return false;
      }
   }

   public void m_6305_(PoseStack p_99694_, int p_99695_, int p_99696_, float p_99697_) {
      this.f_99681_ = null;
      this.m_7333_(p_99694_);
      this.f_99673_.m_6305_(p_99694_, p_99695_, p_99696_, p_99697_);
      m_93215_(p_99694_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, 16777215);
      super.m_6305_(p_99694_, p_99695_, p_99696_, p_99697_);
      if (this.f_99681_ != null) {
         this.m_96597_(p_99694_, this.f_99681_, p_99695_, p_99696_);
      }

   }

   public void m_99729_() {
      ServerSelectionList.Entry serverselectionlist$entry = this.f_99673_.m_93511_();
      if (serverselectionlist$entry instanceof ServerSelectionList.OnlineServerEntry) {
         this.m_99702_(((ServerSelectionList.OnlineServerEntry)serverselectionlist$entry).m_99898_());
      } else if (serverselectionlist$entry instanceof ServerSelectionList.NetworkServerEntry) {
         LanServer lanserver = ((ServerSelectionList.NetworkServerEntry)serverselectionlist$entry).m_99838_();
         this.m_99702_(new ServerData(lanserver.m_120078_(), lanserver.m_120079_(), true));
      }

   }

   private void m_99702_(ServerData p_99703_) {
      ConnectScreen.m_169267_(this, this.f_96541_, ServerAddress.m_171864_(p_99703_.f_105363_), p_99703_);
   }

   public void m_99700_(ServerSelectionList.Entry p_99701_) {
      this.f_99673_.m_6987_(p_99701_);
      this.m_99730_();
   }

   protected void m_99730_() {
      this.f_99679_.f_93623_ = false;
      this.f_99678_.f_93623_ = false;
      this.f_99680_.f_93623_ = false;
      ServerSelectionList.Entry serverselectionlist$entry = this.f_99673_.m_93511_();
      if (serverselectionlist$entry != null && !(serverselectionlist$entry instanceof ServerSelectionList.LANHeader)) {
         this.f_99679_.f_93623_ = true;
         if (serverselectionlist$entry instanceof ServerSelectionList.OnlineServerEntry) {
            this.f_99678_.f_93623_ = true;
            this.f_99680_.f_93623_ = true;
         }
      }

   }

   @Override
   public void m_7379_() {
      this.f_96541_.m_91152_(this.f_99676_);
   }

   public ServerStatusPinger m_99731_() {
      return this.f_99675_;
   }

   public void m_99707_(List<Component> p_99708_) {
      this.f_99681_ = p_99708_;
   }

   public ServerList m_99732_() {
      return this.f_99677_;
   }
}
