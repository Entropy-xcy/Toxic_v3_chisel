package toxic_v3

import chisel3._
import chisel3.util._

class PC() extends Module {
	val io = IO(new Bundle {
        val sh_en         = Input(UInt(1.W))
        val branch_en     = Input(UInt(1.W))
        val branch_cond   = Input(UInt(1.W))
        val tos           = Input(UInt(4.W))

        val pc_addr       = Output(UInt(8.W))
	})

    val pc_next = RegInit(0.U(8.W))
    val shift = RegInit(0.U(8.W))

    // Basic PC logic
    pc_next := pc_next + 1.U
    io.pc_addr := pc_next

    // handle shift
    when(io.sh_en.asBool){
        shift := shift + io.tos
    }

    // handle branch
    when(io.branch_en.asBool){
        when(io.branch_cond === io.tos(0)){
            // branch taken
            pc_next := io.pc_addr + shift
            shift := 0.U
        }
    }
}
