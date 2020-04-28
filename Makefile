ALUTest:
	sbt 'testOnly toxic_v3.ALUSpec'

clean:
	rm -rf test_run_dir/*
