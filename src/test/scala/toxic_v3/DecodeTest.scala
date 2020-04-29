package toxic_v3

import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}

class DecodePeekPokeTester(c: Decode) extends PeekPokeTester(c) {
    poke(c.io.ins, 15)
    step(1)

    expect(c.io.opcode, 7)
}


// Executor
class DecodeSpec extends FlatSpec with Matchers {
    chisel3.iotesters.Driver.execute(Array("--backend-name", "verilator", "--is-verbose"), () => new Decode) { c =>
    	new DecodePeekPokeTester(c)
    } should be(true)
}

