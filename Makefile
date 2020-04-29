ALUTest:
	sbt 'testOnly toxic_v3.ALUSpec'

StackTest:
	sbt 'testOnly toxic_v3.StackSpec'

PCTest:
	sbt 'testOnly toxic_v3.PCSpec'

DecodeTest:
	sbt 'testOnly toxic_v3.DecodeSpec'

CoreTest:
	sbt 'testOnly toxic_v3.CoreSpec'

clean:
	rm -rf test_run_dir/*
