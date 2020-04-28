package toxic_v3

import chisel3._
import chisel3.util._

class ALU extends Module {
	val io = IO(new Bundle {
		val opcode        = Input(UInt(3.W))
		val tos           = Input(UInt(4.W))
		val ntos          = Input(UInt(4.W))
		val out           = Output(UInt(4.W))
	})

	when(io.opcode(2).toBool){
		// ADD, NAND, LS, RS
		when(io.opcode(1, 0) === 0.U(2.W)){
			// ADD
			io.out := io.tos + io.ntos
		} .elsewhen(io.opcode(1, 0) === 1.U(2.W)){
			// NAND
			io.out := ~(io.tos & io.ntos)
		} .elsewhen(io.opcode(1, 0) === 2.U(2.W)){
			// RS
			io.out := Cat(0.U(1.W), io.tos(3), io.tos(2), io.tos(1))
		} .otherwise {
			// LS
			io.out := Cat(io.tos(2), io.tos(1), io.tos(0), 0.U(1.W))
		}
	} .otherwise {
		// P0, P1, DUP, CMP
		when(io.opcode(1, 0) === 0.U(2.W)){
			// P0
			io.out := 0.U
		} .elsewhen(io.opcode(1, 0) === 1.U(2.W)){
			// P1
			io.out := 1.U
		} .elsewhen(io.opcode(1, 0) === 2.U(2.W)){
			// CMP
			// FIXME
			io.out := Cat(0.U(1.W), (io.tos < io.ntos).asUInt, (io.tos > io.ntos).asUInt, (io.tos === io.ntos).asUInt)
		} .otherwise {
			// DUP
			io.out := io.tos
		}
	}
}

