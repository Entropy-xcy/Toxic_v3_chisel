package toxic_v3

import chisel3._
import chisel3.util._

class Decode extends Module {
	val io = IO(new Bundle {
		val ins           = Input(UInt(4.W))

        val push_en       = Output(UInt(1.W))
        val pop_en        = Output(UInt(1.W))
        val opcode        = Output(UInt(3.W))
        val sh_en         = Output(UInt(1.W))
        val branch_en     = Output(UInt(1.W))
        val branch_cond   = Output(UInt(1.W))
        val get_en        = Output(UInt(1.W))
        val put_en        = Output(UInt(1.W))
	})

    io.opcode := io.ins(3, 0)
    io.get_en := 0.U

    val is_get = io.ins === 14.U


    // Handle push_en
    io.push_en := io.ins(3) === 0.U | is_get

    // Handle pop_en
    io.pop_en := io.ins === 7.U | io.ins === 6.U | io.ins === 13.U | io.ins === 9.U

    // Handle sh_en
    io.sh_en := io.ins === 9.U

    // Handle branch_cond
    io.branch_cond := io.ins(0)

    // Handle branch_en
    io.branch_en := io.ins(3, 1) === 5.U
    
    // Handle get_en
    io.get_en := is_get

    // Handle put_en
    io.put_en := io.ins === 15.U
}
