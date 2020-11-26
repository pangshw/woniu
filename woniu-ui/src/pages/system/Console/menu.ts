export default {
  routes: [
    {
      path: '/',
      title: '首页',
      children: []
    },
    {
      path: '/system/console',
      title: '导航示例',
      children: [
        {
          path: '',
          title: '资金管理',
          children: [
            {
              path: '',
              title: '资金计划',
              children: [
                {
                  path: '',
                  title: '计划模版',
                  children: []
                },
                {
                  path: '',
                  title: '资金计划汇总',
                  children: []
                },
                {
                  path: '',
                  title: '资金计划审批',
                  children: []
                },
                {
                  path: '',
                  title: '控制策略',
                  children: []
                },
                {
                  path: '',
                  title: '计划执行分析',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '资金结算',
              children: [
                {
                  path: '',
                  title: '账户管理',
                  children: []
                },
                {
                  path: '',
                  title: '结算业务',
                  children: []
                },
                {
                  path: '',
                  title: '资金划拨',
                  children: []
                },
                {
                  path: '',
                  title: '多结算中心',
                  children: []
                },
                {
                  path: '',
                  title: '手续费管理',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '投资管理',
              children: [
                {
                  path: '',
                  title: '股票投资',
                  children: []
                },
                {
                  path: '',
                  title: '长期股权投资',
                  children: []
                },
                {
                  path: '',
                  title: '存款管理',
                  children: []
                },
                {
                  path: '',
                  title: '委托贷款',
                  children: []
                },
                {
                  path: '',
                  title: '企业内部货款',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '融资管理',
              children: [
                {
                  path: '',
                  title: '授信额度',
                  children: []
                },
                {
                  path: '',
                  title: '银行借款',
                  children: []
                },
                {
                  path: '',
                  title: '票据管理',
                  children: []
                },
                {
                  path: '',
                  title: '或有负债管理',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '银企互联',
              children: [
                {
                  path: '',
                  title: '查询功能',
                  children: []
                },
                {
                  path: '',
                  title: '下载功能',
                  children: []
                },
                {
                  path: '',
                  title: '转账功能',
                  children: []
                },
              ]
            },
          ]
        },
        {
          path: '',
          title: '供应链管理',
          children: [
            {
              path: '',
              title: '采购管理',
              children: [
                {
                  path: '',
                  title: '采购合同',
                  children: []
                },
                {
                  path: '',
                  title: '采购申请',
                  children: []
                },
                {
                  path: '',
                  title: '采购询报比价',
                  children: []
                },
                {
                  path: '',
                  title: '供货信息',
                  children: []
                },
                {
                  path: '',
                  title: '退货申请单',
                  children: []
                },
                {
                  path: '',
                  title: '采购订单',
                  children: []
                },
                {
                  path: '',
                  title: '报表查询',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '委外管理',
              children: [
                {
                  path: '',
                  title: '委外申请单',
                  children: []
                },
                {
                  path: '',
                  title: '委外订单',
                  children: []
                },
                {
                  path: '',
                  title: '委外发料单',
                  children: []
                },
                {
                  path: '',
                  title: '委外收货单',
                  children: []
                },
                {
                  path: '',
                  title: '委外入库单',
                  children: []
                },
                {
                  path: '',
                  title: '委外结算',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '销售管理',
              children: [
                {
                  path: '',
                  title: '销售合同',
                  children: []
                },
                {
                  path: '',
                  title: '销售报价',
                  children: []
                },
                {
                  path: '',
                  title: '销售订单',
                  children: []
                },
                {
                  path: '',
                  title: '销售发货通知单',
                  children: []
                },
                {
                  path: '',
                  title: '销售退货申请单',
                  children: []
                },
                {
                  path: '',
                  title: '销售报表',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '销售政策',
              children: [
                {
                  path: '',
                  title: '销售价格',
                  children: []
                },
                {
                  path: '',
                  title: '信用管理',
                  children: []
                },
              ]
            },
            {
              path: '',
              title: '经销商协作',
              children: [
                {
                  path: '',
                  title: '渠道',
                  children: []
                },
                {
                  path: '',
                  title: '渠道终端',
                  children: []
                },
                {
                  path: '',
                  title: '陈列标准',
                  children: []
                },
                {
                  path: '',
                  title: '目标消费者',
                  children: []
                },
                {
                  path: '',
                  title: '网络订单',
                  children: []
                },
                {
                  path: '',
                  title: '渠道入库单',
                  children: []
                },
                {
                  path: '',
                  title: '渠道销量单',
                  children: []
                },
                {
                  path: '',
                  title: '渠道结存单',
                  children: []
                },
                {
                  path: '',
                  title: '渠道进销存单',
                  children: []
                },
              ]
            },
          ]
        },
        {
          path: '',
          title: '生产制造',
          children: []
        },
        {
          path: '',
          title: '协同平台',
          children: []
        },
      ]
    },
    {
      path: '/system/console',
      title: '系统管理',
      children: [
        {
          path: '',
          title: '系统工具',
          children: [
            {
              path: '',
              title: '系统工具',
              children: [
                {
                  path: '/system/devMeta',
                  title: '代码生成',
                }
              ]
            }
          ]
        }
      ]
    },
  ]
};
