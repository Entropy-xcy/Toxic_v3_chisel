package toxic_v3

import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}

class PCPeekPokeTester(c: PC) extends PeekPokeTester(c) {
    step(1)
    expect(c.io.pc_addr, 1)

    step(1)
    expect(c.io.pc_addr, 2)

    poke(c.io.sh_en, 1)
    poke(c.io.tos, 2)
    step(1)

    poke(c.io.sh_en, 0)
    poke(c.io.branch_en, 1)
    poke(c.io.branch_cond, 0)
    step(1)

    expect(c.io.pc_addr, 5)

    poke(c.io.branch_en, 0)
    poke(c.io.sh_en, 1)
    poke(c.io.tos, 2)
    step(1)
    expect(c.io.pc_addr, 6)

    poke(c.io.sh_en, 0)
    poke(c.io.branch_en, 1)
    poke(c.io.branch_cond, 0)
    step(1)
    expect(c.io.pc_addr, 8)
}


// Executor
class PCSpec extends FlatSpec with Matchers {
    chisel3.iotesters.Driver.execute(Array("--backend-name", "verilator", "--is-verbose"), () => new PC) { c =>
    	new PCPeekPokeTester(c)
    } should be(true)
}

