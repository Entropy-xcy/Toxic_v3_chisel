package toxic_v3

import chisel3._
import chisel3.util._

class Stack(addr_width: Int) extends Module {
	val io = IO(new Bundle {
		val pop_en        = Input(UInt(1.W))
        val push_en       = Input(UInt(1.W))
        val push_data     = Input(UInt(4.W))

        val tos           = Output(UInt(4.W))
        val ntos          = Output(UInt(4.W))
	})

    // stack pointer
    val sp = RegInit(0.U(addr_width.W))

    val stack = Reg(Vec(scala.math.pow(2, addr_width).toInt, UInt(4.W)))

    when(io.pop_en === 1.U & io.push_en ===1.U){
        // replace tos with push_data
        stack(sp - 1.U) := io.push_data
    } .elsewhen(io.pop_en === 0.U & io.push_en === 1.U){
        // push push_data to stack
        sp := sp + 1.U
        stack(sp) := io.push_data
    } .elsewhen(io.pop_en === 1.U & io.push_en === 0.U){
        // pop from stack
        sp := sp - 1.U
    }

    io.tos := stack(sp - 1.U);
    io.ntos := stack(sp - 2.U);
}
