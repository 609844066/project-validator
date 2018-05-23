# project-validator
初始化工程，支持基本数据格式校验

mark 2018.5.21 一口气写完的，还没有怎么测试，先提交上来再说

remark：最近刚到新公司接收项目，发现这里校验参数的没有做，纯硬编码的起判参数的值，主要是为了处理前端传的参数和后端字典的匹配

现在匆匆的实现了这个功能，后期有空会做更多的处理


未完待续....2018.5.21

2018.5.22
1、添加分组校验
2、修改校验值合法时必须传参标示问题，现在可以不传
3、错误描述添加统一配置



工程介绍：SpringBoot项目脚手架，利用spring aop+java反射实现自定义注解校验参数

example1：校验userName参数必填
@CheckParams(notNull = true)
private String userName;

example2：校验age参数为数值
@CheckParams(numeric = true)
private String age;

example3：校验phone参数非空、最小长度为1、最大长度为11
@CheckParams(notNull = true,minLen = 1,maxLen = 11)
private String phone;

example4：校验userType参数合法性，值在UserTypeEnum枚举中
@CheckParams(enumsValue= UserTypeEnum.class)
private String userType;

example5：此例子是用于保存接口不用传递id参数，但是修改接口需要传id，此处用分组校验来实现，
下面的意思是：分组为UserParam的id不能为空
@CheckParams(notNull = true,groups=UserParam.class)
private String id;
