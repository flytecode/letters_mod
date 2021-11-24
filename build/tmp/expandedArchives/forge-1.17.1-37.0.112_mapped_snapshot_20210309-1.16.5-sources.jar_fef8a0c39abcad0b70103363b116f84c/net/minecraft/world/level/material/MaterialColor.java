package net.minecraft.world.level.material;

public class MaterialColor {
   public static final MaterialColor[] f_76387_ = new MaterialColor[64];
   public static final MaterialColor f_76398_ = new MaterialColor(0, 0);
   public static final MaterialColor f_76399_ = new MaterialColor(1, 8368696);
   public static final MaterialColor f_76400_ = new MaterialColor(2, 16247203);
   public static final MaterialColor f_76401_ = new MaterialColor(3, 13092807);
   public static final MaterialColor f_76402_ = new MaterialColor(4, 16711680);
   public static final MaterialColor f_76403_ = new MaterialColor(5, 10526975);
   public static final MaterialColor f_76404_ = new MaterialColor(6, 10987431);
   public static final MaterialColor f_76405_ = new MaterialColor(7, 31744);
   public static final MaterialColor f_76406_ = new MaterialColor(8, 16777215);
   public static final MaterialColor f_76407_ = new MaterialColor(9, 10791096);
   public static final MaterialColor f_76408_ = new MaterialColor(10, 9923917);
   public static final MaterialColor f_76409_ = new MaterialColor(11, 7368816);
   public static final MaterialColor f_76410_ = new MaterialColor(12, 4210943);
   public static final MaterialColor f_76411_ = new MaterialColor(13, 9402184);
   public static final MaterialColor f_76412_ = new MaterialColor(14, 16776437);
   public static final MaterialColor f_76413_ = new MaterialColor(15, 14188339);
   public static final MaterialColor f_76414_ = new MaterialColor(16, 11685080);
   public static final MaterialColor f_76415_ = new MaterialColor(17, 6724056);
   public static final MaterialColor f_76416_ = new MaterialColor(18, 15066419);
   public static final MaterialColor f_76417_ = new MaterialColor(19, 8375321);
   public static final MaterialColor f_76418_ = new MaterialColor(20, 15892389);
   public static final MaterialColor f_76419_ = new MaterialColor(21, 5000268);
   public static final MaterialColor f_76420_ = new MaterialColor(22, 10066329);
   public static final MaterialColor f_76421_ = new MaterialColor(23, 5013401);
   public static final MaterialColor f_76422_ = new MaterialColor(24, 8339378);
   public static final MaterialColor f_76361_ = new MaterialColor(25, 3361970);
   public static final MaterialColor f_76362_ = new MaterialColor(26, 6704179);
   public static final MaterialColor f_76363_ = new MaterialColor(27, 6717235);
   public static final MaterialColor f_76364_ = new MaterialColor(28, 10040115);
   public static final MaterialColor f_76365_ = new MaterialColor(29, 1644825);
   public static final MaterialColor f_76366_ = new MaterialColor(30, 16445005);
   public static final MaterialColor f_76367_ = new MaterialColor(31, 6085589);
   public static final MaterialColor f_76368_ = new MaterialColor(32, 4882687);
   public static final MaterialColor f_76369_ = new MaterialColor(33, 55610);
   public static final MaterialColor f_76370_ = new MaterialColor(34, 8476209);
   public static final MaterialColor f_76371_ = new MaterialColor(35, 7340544);
   public static final MaterialColor f_76372_ = new MaterialColor(36, 13742497);
   public static final MaterialColor f_76373_ = new MaterialColor(37, 10441252);
   public static final MaterialColor f_76374_ = new MaterialColor(38, 9787244);
   public static final MaterialColor f_76375_ = new MaterialColor(39, 7367818);
   public static final MaterialColor f_76376_ = new MaterialColor(40, 12223780);
   public static final MaterialColor f_76377_ = new MaterialColor(41, 6780213);
   public static final MaterialColor f_76378_ = new MaterialColor(42, 10505550);
   public static final MaterialColor f_76379_ = new MaterialColor(43, 3746083);
   public static final MaterialColor f_76380_ = new MaterialColor(44, 8874850);
   public static final MaterialColor f_76381_ = new MaterialColor(45, 5725276);
   public static final MaterialColor f_76382_ = new MaterialColor(46, 8014168);
   public static final MaterialColor f_76383_ = new MaterialColor(47, 4996700);
   public static final MaterialColor f_76384_ = new MaterialColor(48, 4993571);
   public static final MaterialColor f_76385_ = new MaterialColor(49, 5001770);
   public static final MaterialColor f_76386_ = new MaterialColor(50, 9321518);
   public static final MaterialColor f_76388_ = new MaterialColor(51, 2430480);
   public static final MaterialColor f_76389_ = new MaterialColor(52, 12398641);
   public static final MaterialColor f_76390_ = new MaterialColor(53, 9715553);
   public static final MaterialColor f_76391_ = new MaterialColor(54, 6035741);
   public static final MaterialColor f_76392_ = new MaterialColor(55, 1474182);
   public static final MaterialColor f_76393_ = new MaterialColor(56, 3837580);
   public static final MaterialColor f_76394_ = new MaterialColor(57, 5647422);
   public static final MaterialColor f_76395_ = new MaterialColor(58, 1356933);
   public static final MaterialColor f_164534_ = new MaterialColor(59, 6579300);
   public static final MaterialColor f_164535_ = new MaterialColor(60, 14200723);
   public static final MaterialColor f_164536_ = new MaterialColor(61, 8365974);
   public final int f_76396_;
   public final int f_76397_;

   private MaterialColor(int p_76425_, int p_76426_) {
      if (p_76425_ >= 0 && p_76425_ <= 63) {
         this.f_76397_ = p_76425_;
         this.f_76396_ = p_76426_;
         f_76387_[p_76425_] = this;
      } else {
         throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
      }
   }

   public int m_76427_(int p_76428_) {
      int i = 220;
      if (p_76428_ == 3) {
         i = 135;
      }

      if (p_76428_ == 2) {
         i = 255;
      }

      if (p_76428_ == 1) {
         i = 220;
      }

      if (p_76428_ == 0) {
         i = 180;
      }

      int j = (this.f_76396_ >> 16 & 255) * i / 255;
      int k = (this.f_76396_ >> 8 & 255) * i / 255;
      int l = (this.f_76396_ & 255) * i / 255;
      return -16777216 | l << 16 | k << 8 | j;
   }
}