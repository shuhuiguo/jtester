Scenario: ��ӭ����һ
Given give greeting 
����${housemaster=jobs.he}�� ����${guest=mary.li}����ӭ��${greeting=welcome},ʱ��${date=2010-11-12}
When do greeting
��ӭ
Then check greeting 
��������Ϊ${jobs.he welcome mary.li on 2010-11-12.}

Scenario: ��ӭ������
Given give greeting 
 ����${guest=mary.li}������${housemaster=jobs.he}����ӭ��${greeting=welcome},ʱ��${date=2010-11-12}
When do greeting
��ӭ
Then check greeting 
��������Ϊ${jobs.he welcome mary.li on 2010-11-12.}

Scenario: ��ӭ������
Given give greeting 
 ��ӭ��${greeting=welcome},ʱ��${date=2010-11-12}������${guest=mary.li}������${housemaster=jobs.he}
When do greeting
��ӭ
Then check greeting 
��������Ϊ${jobs.he welcome mary.li on 2010-11-12.}