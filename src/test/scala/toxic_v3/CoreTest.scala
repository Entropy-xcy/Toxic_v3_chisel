package toxic_v3

import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}

class CorePeekPokeTester(c: Core) extends PeekPokeTester(c) {
    // P1
    poke(c.io.inst, 1)
    step(1)
    expect(c.io.put, 1)

    // P1
    poke(c.io.inst, 1)
    step(1)
    expect(c.io.put, 1)

    // ADD
    poke(c.io.inst, 4)
    step(1)
    expect(c.io.put, 2)

    // LS
    poke(c.io.inst, 7)
    step(1)
    expect(c.io.put, 4)

    // ADD
    poke(c.io.inst, 4)
    step(1)
    expect(c.io.put, 5)

    // DUP
    poke(c.io.inst, 3)
    step(1)
    expect(c.io.put, 5)

    // POP
    poke(c.io.inst, 13)
    step(1)
    expect(c.io.put, 5)

    // P0
    poke(c.io.inst, 0)
    step(1)
    expect(c.io.put, 0)

    // ADD
    poke(c.io.inst, 4)
    step(1)
    expect(c.io.put, 5)

    // PUT
    poke(c.io.inst, 15)
    step(1)
    expect(c.io.put, 5)
    expect(c.io.putting, 1)

    // POP
    poke(c.io.inst, 13)
    step(1)
    expect(c.io.put, 0)
    expect(c.io.putting, 0)

    // GET
    poke(c.io.inst, 14)
    poke(c.io.get, 7)
    step(1)
    expect(c.io.put, 7)
    expect(c.io.putting, 0)

    // SH
    poke(c.io.inst, 9)
    step(1)
    expect(c.io.put, 0)
    expect(c.io.pc, 13)

    // B1 // not taken
    poke(c.io.inst, 11)
    step(1)
    expect(c.io.put, 0)
    expect(c.io.pc, 14)
}


// Executor
class CoreSpec extends FlatSpec with Matchers {
    chisel3.iotesters.Driver.execute(Array("--backend-name", "verilator", "--is-verbose"), () => new Core) { c =>
    	new CorePeekPokeTester(c)
    } should be(true)
}

