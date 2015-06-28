require 'net/http'
require 'uri'
require 'rexml/document'

#xml = './view-source curazy.com archives 6493.xml'

uri = URI.parse "http://www.nicovideo.jp/ranking/fav/hourly/are?rss=2.0"

#File.open(xml) do |xmlfile|
# doc = REXML::Document.new(xmlfile)
#end

res = Net::HTTP.get uri
doc = REXML::Document.new res
titles_rx = []

#doc.elements.each('head/meta/meta/title'){|e| titles_rx << e.text}

doc.elements.each('/rss/channel/item/title'){|e| titles_rx << e.text}
titles_rx.first(100).each{|e| puts e}
