require 'open-uri'
require 'nokogiri'

url = 'http://curazy.com/archives/6493'

charset = nil
html = open(url) do |f|
  charset = f.charset
  f.read
end

page = Nokogiri::HTML.parse(html, nil, charset) #HTML����͂��I�u�W�F�N�g��
shuzo_meigen = page.search('pre')               #<pre>�^�O���w��
p shuzo_meigen.text                             #shuzo_meigen�Ŏ擾�����f�[�^���e�L�X�g�ɕϊ�
puts('出力しました。')
