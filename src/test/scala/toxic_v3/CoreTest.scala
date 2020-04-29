package toxic_v3

import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}

class CorePeekPokeTester(c: Core) extends PeekPokeTester(c) {
    poke(c.io.inst, 0)
    step(1)

    expect(c.io.putting, 0)
    expect(c.io.put, 0)
}


// Executor
class CoreSpec extends FlatSpec with Matchers {
    chisel3.iotesters.Driver.execute(Array("--backend-name", "verilator", "--is-verbose"), () => new Core) { c =>
    	new CorePeekPokeTester(c)
    } should be(true)
}

