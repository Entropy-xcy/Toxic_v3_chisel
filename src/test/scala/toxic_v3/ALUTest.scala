package toxic_v3

import chisel3._
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{Matchers, FlatSpec}

class ALUPeekPokeTester(c: ALU) extends PeekPokeTester(c) {
	// 0 + 1 = 1
	poke(c.io.opcode, 4)
	poke(c.io.tos, 0)
	poke(c.io.ntos, 1)
	step(2)
	expect(c.io.out, 1)

	// 3 + 2 = 5
	poke(c.io.opcode, 4)
	poke(c.io.tos, 3)
	poke(c.io.ntos, 2)
	step(2)
	expect(c.io.out, 5)

	// 1111 + 1111 = 1110
	poke(c.io.opcode, 4)
	poke(c.io.tos, "b1111".U)
	poke(c.io.ntos, "b1111".U)
	step(2)
	expect(c.io.out, "b1110".U)

	// 1010 nand 1010 = 0101
	poke(c.io.opcode, 5)
	poke(c.io.tos, "b1010".U)
	poke(c.io.ntos, "b1010".U)
	step(2)
	expect(c.io.out, "b0101".U)

	// 1111 nand 0101 = 1010
	poke(c.io.opcode, 5)
	poke(c.io.tos, "b1111".U)
	poke(c.io.ntos, "b0101".U)
	step(2)
	expect(c.io.out, "b1010".U)

	// P0
	poke(c.io.opcode, 0)
	step(2)
	expect(c.io.out, "b0000".U)

	// P1
	poke(c.io.opcode, 1)
	step(2)
	expect(c.io.out, "b0001".U)

	// LS
	poke(c.io.opcode, 7)
	poke(c.io.tos, "b1111".U)
	step(2)
	expect(c.io.out, "b1110".U)

	// RS
	poke(c.io.opcode, 6)
	poke(c.io.tos, "b1111".U)
	step(2)
	expect(c.io.out, "b0111".U)

	// DUP
	poke(c.io.opcode, 3)
	poke(c.io.tos, "b1011".U)
	step(2)
	expect(c.io.out, "b1011".U)

	// 1110 cmp 0000 = 0010
	poke(c.io.opcode, 2)
	poke(c.io.tos, "b1110".U)
	poke(c.io.ntos, "b0000".U)
	step(2)
	expect(c.io.out, "b0010".U)

	// 1010 cmp 1010 = 0001
	poke(c.io.opcode, 2)
	poke(c.io.tos, "b1010".U)
	poke(c.io.ntos, "b1010".U)
	step(2)
	expect(c.io.out, "b0001".U)

	// 0001 cmp 0010 = 0100
	poke(c.io.opcode, 2)
	poke(c.io.tos, "b0001".U)
	poke(c.io.ntos, "b0010".U)
	step(2)
	expect(c.io.out, "b0100".U)
}

class ALUSpec extends FlatSpec with Matchers {
    chisel3.iotesters.Driver.execute(Array("--backend-name", "verilator", "--is-verbose"), () => new ALU) { c =>
    	new ALUPeekPokeTester(c)
    } should be(true)
}

