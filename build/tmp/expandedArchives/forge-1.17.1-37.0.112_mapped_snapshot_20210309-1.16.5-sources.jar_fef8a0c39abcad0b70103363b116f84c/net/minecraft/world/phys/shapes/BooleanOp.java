package net.minecraft.world.phys.shapes;

public interface BooleanOp {
   BooleanOp f_82681_ = (p_82747_, p_82748_) -> {
      return false;
   };
   BooleanOp f_82682_ = (p_82744_, p_82745_) -> {
      return !p_82744_ && !p_82745_;
   };
   BooleanOp f_82683_ = (p_82741_, p_82742_) -> {
      return p_82742_ && !p_82741_;
   };
   BooleanOp f_82684_ = (p_82738_, p_82739_) -> {
      return !p_82738_;
   };
   BooleanOp f_82685_ = (p_82735_, p_82736_) -> {
      return p_82735_ && !p_82736_;
   };
   BooleanOp f_82686_ = (p_82732_, p_82733_) -> {
      return !p_82733_;
   };
   BooleanOp f_82687_ = (p_82729_, p_82730_) -> {
      return p_82729_ != p_82730_;
   };
   BooleanOp f_82688_ = (p_82726_, p_82727_) -> {
      return !p_82726_ || !p_82727_;
   };
   BooleanOp f_82689_ = (p_82723_, p_82724_) -> {
      return p_82723_ && p_82724_;
   };
   BooleanOp f_82690_ = (p_82720_, p_82721_) -> {
      return p_82720_ == p_82721_;
   };
   BooleanOp f_82691_ = (p_82717_, p_82718_) -> {
      return p_82718_;
   };
   BooleanOp f_82692_ = (p_82714_, p_82715_) -> {
      return !p_82714_ || p_82715_;
   };
   BooleanOp f_82693_ = (p_82711_, p_82712_) -> {
      return p_82711_;
   };
   BooleanOp f_82694_ = (p_82708_, p_82709_) -> {
      return p_82708_ || !p_82709_;
   };
   BooleanOp f_82695_ = (p_82705_, p_82706_) -> {
      return p_82705_ || p_82706_;
   };
   BooleanOp f_82696_ = (p_82699_, p_82700_) -> {
      return true;
   };

   boolean m_82701_(boolean p_82702_, boolean p_82703_);
}