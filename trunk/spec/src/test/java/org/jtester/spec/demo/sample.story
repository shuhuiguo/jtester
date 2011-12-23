Story 订单投放场景演示

Given init customer member 
假定数据库中有个客户${memberId="ttttt"}其对应的${customerId="30"}

Given init customer status
假定客户${custeomerId=30}的客户原始状态${custmoerStatus=has_sign|已签单未到款}，客户类型为${status=potential|潜在客户}

Given init customer direct p4p status 
假定客户${custeomerId=30}的直销机会原始状态${status=assigned|已分发} 

When do work order publish 
对客户${memberId=ttttt}执行动作${action=publish|订单投放},订单类型为${orderType=cash|现金开户},客户类型为${custType=cash|现金客户}

Then check customer direct p4p
客户${customerId=30}的直销机会置为${status=succeed|结束 } 

Then check customer phone p4p
生成${count=1}条客户${customer_id = 30}的电销机会

Then check customer status
把客户${customer_id=30}状态调整为${status=not_consume|已开户未消耗}状态

Then check customer type
把客户${customer_id=30}类型调整为${custType=cash|现金客户}


Story 订单投放场景演示

Given init customer member 
假定数据库中有个客户${memberId="ttttt"}其对应的${customerId="30"}

Given init customer status
假定客户${custeomerId=30}的客户原始状态${custmoerStatus=has_sign|已签单未到款}，客户类型为${status=potential|潜在客户}

Given init customer direct p4p status 
假定客户${custeomerId=30}的直销机会原始状态${status=assigned|已分发} 

When do work order publish 
对客户${memberId=ttttt}执行动作${action=publish|订单投放},订单类型为${orderType=cash|现金开户},客户类型为${custType=cash|现金客户}

Then check customer direct p4p
客户${customerId=30}的直销机会置为${status=succeed|结束 } 

Then check customer phone p4p
生成${count=1}条客户${customer_id = 30}的电销机会

Then check customer status
把客户${customer_id=30}状态调整为${status=not_consume|已开户未消耗}状态

Then check customer type
把客户${customer_id=30}类型调整为${custType=cash|现金客户}

DataTable
|ddd|ddd|
|xxx|xxx|
|xxx|xxx|

Story 订单投放场景演示

Given init customer member 
假定数据库中有个客户${memberId="ttttt"}其对应的${customerId="30"}
Given init customer status
假定客户${custeomerId=30}的客户原始状态${custmoerStatus=has_sign|已签单未到款}，客户类型为${status=potential|潜在客户}
Given init customer direct p4p status 
假定客户${custeomerId=30}的直销机会原始状态${status=assigned|已分发} 
When do work order publish 
对客户${memberId=ttttt}执行动作${action=publish|订单投放},订单类型为${orderType=cash|现金开户},客户类型为${custType=cash|现金客户}
Then check customer direct p4p
客户${customerId=30}的直销机会置为${status=succeed|结束 } 
Then check customer phone p4p
生成${count=1}条客户${customer_id = 30}的电销机会
Then check customer status
把客户${customer_id=30}状态调整为${status=not_consume|已开户未消耗}状态
Then check customer type
把客户${customer_id=30}类型调整为${custType=cash|现金客户}