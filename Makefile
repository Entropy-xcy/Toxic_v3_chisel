ALUTest:
	sbt 'testOnly toxic_v3.ALUSpec'

StackTest:
	sbt 'testOnly toxic_v3.StackSpec'

PCTest:
	sbt 'testOnly toxic_v3.PCSpec'

DecodeTest:
	sbt 'testOnly toxic_v3.DecodeSpec'

clean:
	rm -rf test_run_dir/*
