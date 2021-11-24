package net.minecraft.world.level.levelgen.feature;

import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BuriedTreasurePieces;
import net.minecraft.world.level.levelgen.structure.DesertPyramidPiece;
import net.minecraft.world.level.levelgen.structure.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.IglooPieces;
import net.minecraft.world.level.levelgen.structure.JunglePyramidPiece;
import net.minecraft.world.level.levelgen.structure.MineShaftPieces;
import net.minecraft.world.level.levelgen.structure.NetherBridgePieces;
import net.minecraft.world.level.levelgen.structure.NetherFossilPieces;
import net.minecraft.world.level.levelgen.structure.OceanMonumentPieces;
import net.minecraft.world.level.levelgen.structure.OceanRuinPieces;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.ShipwreckPieces;
import net.minecraft.world.level.levelgen.structure.StrongholdPieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.SwamplandHutPiece;
import net.minecraft.world.level.levelgen.structure.WoodlandMansionPieces;

public interface StructurePieceType {
   StructurePieceType f_67132_ = m_67163_(MineShaftPieces.MineShaftCorridor::new, "MSCorridor");
   StructurePieceType f_67137_ = m_67163_(MineShaftPieces.MineShaftCrossing::new, "MSCrossing");
   StructurePieceType f_67138_ = m_67163_(MineShaftPieces.MineShaftRoom::new, "MSRoom");
   StructurePieceType f_67139_ = m_67163_(MineShaftPieces.MineShaftStairs::new, "MSStairs");
   StructurePieceType f_67140_ = m_67163_(NetherBridgePieces.BridgeCrossing::new, "NeBCr");
   StructurePieceType f_67141_ = m_67163_(NetherBridgePieces.BridgeEndFiller::new, "NeBEF");
   StructurePieceType f_67142_ = m_67163_(NetherBridgePieces.BridgeStraight::new, "NeBS");
   StructurePieceType f_67143_ = m_67163_(NetherBridgePieces.CastleCorridorStairsPiece::new, "NeCCS");
   StructurePieceType f_67144_ = m_67163_(NetherBridgePieces.CastleCorridorTBalconyPiece::new, "NeCTB");
   StructurePieceType f_67145_ = m_67163_(NetherBridgePieces.CastleEntrance::new, "NeCE");
   StructurePieceType f_67146_ = m_67163_(NetherBridgePieces.CastleSmallCorridorCrossingPiece::new, "NeSCSC");
   StructurePieceType f_67147_ = m_67163_(NetherBridgePieces.CastleSmallCorridorLeftTurnPiece::new, "NeSCLT");
   StructurePieceType f_67148_ = m_67163_(NetherBridgePieces.CastleSmallCorridorPiece::new, "NeSC");
   StructurePieceType f_67149_ = m_67163_(NetherBridgePieces.CastleSmallCorridorRightTurnPiece::new, "NeSCRT");
   StructurePieceType f_67150_ = m_67163_(NetherBridgePieces.CastleStalkRoom::new, "NeCSR");
   StructurePieceType f_67151_ = m_67163_(NetherBridgePieces.MonsterThrone::new, "NeMT");
   StructurePieceType f_67152_ = m_67163_(NetherBridgePieces.RoomCrossing::new, "NeRC");
   StructurePieceType f_67153_ = m_67163_(NetherBridgePieces.StairsRoom::new, "NeSR");
   StructurePieceType f_67154_ = m_67163_(NetherBridgePieces.StartPiece::new, "NeStart");
   StructurePieceType f_67155_ = m_67163_(StrongholdPieces.ChestCorridor::new, "SHCC");
   StructurePieceType f_67156_ = m_67163_(StrongholdPieces.FillerCorridor::new, "SHFC");
   StructurePieceType f_67157_ = m_67163_(StrongholdPieces.FiveCrossing::new, "SH5C");
   StructurePieceType f_67158_ = m_67163_(StrongholdPieces.LeftTurn::new, "SHLT");
   StructurePieceType f_67159_ = m_67163_(StrongholdPieces.Library::new, "SHLi");
   StructurePieceType f_67160_ = m_67163_(StrongholdPieces.PortalRoom::new, "SHPR");
   StructurePieceType f_67161_ = m_67163_(StrongholdPieces.PrisonHall::new, "SHPH");
   StructurePieceType f_67106_ = m_67163_(StrongholdPieces.RightTurn::new, "SHRT");
   StructurePieceType f_67107_ = m_67163_(StrongholdPieces.RoomCrossing::new, "SHRC");
   StructurePieceType f_67108_ = m_67163_(StrongholdPieces.StairsDown::new, "SHSD");
   StructurePieceType f_67109_ = m_67163_(StrongholdPieces.StartPiece::new, "SHStart");
   StructurePieceType f_67110_ = m_67163_(StrongholdPieces.Straight::new, "SHS");
   StructurePieceType f_67111_ = m_67163_(StrongholdPieces.StraightStairsDown::new, "SHSSD");
   StructurePieceType f_67112_ = m_67163_(JunglePyramidPiece::new, "TeJP");
   StructurePieceType f_67113_ = m_67163_(OceanRuinPieces.OceanRuinPiece::new, "ORP");
   StructurePieceType f_67114_ = m_67163_(IglooPieces.IglooPiece::new, "Iglu");
   StructurePieceType f_67115_ = m_67163_(RuinedPortalPiece::new, "RUPO");
   StructurePieceType f_67116_ = m_67163_(SwamplandHutPiece::new, "TeSH");
   StructurePieceType f_67117_ = m_67163_(DesertPyramidPiece::new, "TeDP");
   StructurePieceType f_67118_ = m_67163_(OceanMonumentPieces.MonumentBuilding::new, "OMB");
   StructurePieceType f_67119_ = m_67163_(OceanMonumentPieces.OceanMonumentCoreRoom::new, "OMCR");
   StructurePieceType f_67120_ = m_67163_(OceanMonumentPieces.OceanMonumentDoubleXRoom::new, "OMDXR");
   StructurePieceType f_67121_ = m_67163_(OceanMonumentPieces.OceanMonumentDoubleXYRoom::new, "OMDXYR");
   StructurePieceType f_67122_ = m_67163_(OceanMonumentPieces.OceanMonumentDoubleYRoom::new, "OMDYR");
   StructurePieceType f_67123_ = m_67163_(OceanMonumentPieces.OceanMonumentDoubleYZRoom::new, "OMDYZR");
   StructurePieceType f_67124_ = m_67163_(OceanMonumentPieces.OceanMonumentDoubleZRoom::new, "OMDZR");
   StructurePieceType f_67125_ = m_67163_(OceanMonumentPieces.OceanMonumentEntryRoom::new, "OMEntry");
   StructurePieceType f_67126_ = m_67163_(OceanMonumentPieces.OceanMonumentPenthouse::new, "OMPenthouse");
   StructurePieceType f_67127_ = m_67163_(OceanMonumentPieces.OceanMonumentSimpleRoom::new, "OMSimple");
   StructurePieceType f_67128_ = m_67163_(OceanMonumentPieces.OceanMonumentSimpleTopRoom::new, "OMSimpleT");
   StructurePieceType f_67129_ = m_67163_(OceanMonumentPieces.OceanMonumentWingRoom::new, "OMWR");
   StructurePieceType f_67130_ = m_67163_(EndCityPieces.EndCityPiece::new, "ECP");
   StructurePieceType f_67131_ = m_67163_(WoodlandMansionPieces.WoodlandMansionPiece::new, "WMP");
   StructurePieceType f_67133_ = m_67163_(BuriedTreasurePieces.BuriedTreasurePiece::new, "BTP");
   StructurePieceType f_67134_ = m_67163_(ShipwreckPieces.ShipwreckPiece::new, "Shipwreck");
   StructurePieceType f_67135_ = m_67163_(NetherFossilPieces.NetherFossilPiece::new, "NeFos");
   StructurePieceType f_67136_ = m_67163_(PoolElementStructurePiece::new, "jigsaw");

   StructurePiece m_160483_(ServerLevel p_160484_, CompoundTag p_160485_);

   static StructurePieceType m_67163_(StructurePieceType p_67164_, String p_67165_) {
      return Registry.m_122961_(Registry.f_122843_, p_67165_.toLowerCase(Locale.ROOT), p_67164_);
   }
}