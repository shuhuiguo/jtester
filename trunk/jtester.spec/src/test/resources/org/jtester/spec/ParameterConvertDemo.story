Stroy JSpec�������Ͳ���ת����֤

Scenario ���ֻ�������ת��
When convert to primitive type
������ֵ=true��
������=123��
��������=234��
��������=23��
��������=23.12��
��˫����=234.23��
���ַ���=I am a String��

Scenario ����������ת��
When convert to big decimal and big integer
��������=123456789123456789��
��������=123456789123456789.12345��

Scenario ����ת����֤
When convert date
������=2010-12-20��
��ʱ��=12:30:45��
�����ڼ�ʱ��=2010-04-14 14:23:57��
��java_sql_Date=2010-04-14��
��java_sql_Timestamp=2010-04-14 14:23:57��

Scenario һά����ת��
When convert to int array
������=[1,2,3]��

Scenario ��ά����ת��
When convert to bool array
������=[[true, false],
		[true, true]]��

Scenario list ת��
When convert to List
���б�=[true,false]��

Scenario ��֤list����ת��
When convert to list generic
���б�=[{'first':'wu','last':'davey'},{'first':'he','last':'jobs'}]��
When convert to list hashmap
���б�=[{'first':'wu','last':'davey'},{'first':'he','last':'jobs'}]��

Scenario ��֤Dtoת��
When convet to dto
���û�={'first':'wu','last':'davey'}��

Scenario DataMap��HashMapת����֤
When convert to map
��DataMap={'first':'wu','last':'davey'}��
��HashMap={'first':'wu','last':'davey'}��