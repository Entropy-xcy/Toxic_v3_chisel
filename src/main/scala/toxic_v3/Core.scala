package toxic_v3

import chisel3._
import chisel3.util._

class Core extends Module {
	val io = IO(new Bundle {
        val get           = Input(UInt(4.W))
        val inst          = Input(UInt(4.W))

        val put           = Output(UInt(4.W))
        val putting       = Output(UInt(1.W))
        val pc            = Output(UInt(8.W))
	})

    val decode = Module(new Decode())
    val stack = Module(new Stack(4))
    val alu = Module(new ALU())
    val pc = Module(new PC())

    decode.io.ins := io.inst
    alu.io.opcode := decode.io.opcode
    stack.io.pop_en := decode.io.pop_en
    stack.io.push_en := decode.io.push_en

    val push_data_w = Wire(UInt(4.W))
    when(decode.io.get_en.asBool){
        push_data_w := io.get
    } .otherwise {
        push_data_w := alu.io.out
    }

    io.putting := decode.io.put_en
    
    stack.io.push_data := push_data_w

    alu.io.tos := stack.io.tos
    alu.io.ntos := stack.io.ntos

    pc.io.sh_en := decode.io.sh_en
    pc.io.branch_en := decode.io.branch_en
    pc.io.branch_cond := decode.io.branch_cond
    pc.io.tos := stack.io.tos

    io.put := stack.io.tos

    io.pc := pc.io.pc_addr
}
