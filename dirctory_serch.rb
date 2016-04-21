# encoding UTF-8

require "Find"
require "FileUtils"

Find.find("F:/ProgramSource/Ruby/src/testDir"){|f|
	next unless FileTest.file?(f)
	fstr = File.basename(f)
	next unless fstr.include?("a")
	topath = "F:/ProgramSource/Ruby/src/copy/"
	FileUtils.cp(f, topath)
	p "from:" + f
	p "to:" + topath
}