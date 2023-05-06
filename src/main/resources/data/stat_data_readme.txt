一、数据结构与规则配置说明：
1、脚本文件参见：stat_rule_config.sql
2、配置项说明：
target_type 指标类型：获取统计数据以指标类型为请求单位
target_name 指标名称：作为指标数据返回的key值
target_desc 指标描述：用于说明指标的作用或用途
rule_type 规则类型：val|list|chartByData ,当前做了最常规的3种，后期可根据实际需要扩展
sql_rule 统计规则-SQL语句:
         val类型为唯一返回值,SQL返回key规定为 val
         list类型的不限，根据需要与前端约定即可
         chartByData类型为常规统计图返回值，分类的categories在规则中以xkey作为数据key, datas项以val作为key返回
default_val 默认值：类似于常量定义，用于当前不具体计算或固定值指标，当默认值不为空，规则不再执行

二、客户端调用：
1、请求URL：
http://127.0.0.1:8081/fsb/api/statData/getDataByTargetType
2、请求参数示例：
{"targetType":"MainPage","startTime":"2018-12-21","endTime":"2019-12-21"}
targetType-指标类型(必选)
startTime-统计开始时间(可选)
endTime-统计结束时间(可选)
3、响应数据示例：
code-0表示响应成功
data-为响应数据根节点
    数据内容根据指标名称为key，指标值为值-值有多种类型(数值|数组|统计图形)
    a、数值类型：如指标名称为：maxDevice、totalDevice
    b、数组类型：如指标名称为：departNum
    c、统计图形：如指标名称为：departNumChar

{
	"code": 0,
	"msg": "操作成功！",
	"data": {
		"maxDevice": 12000,
		"totalDevice": 8,
		"departNum": [{
				"depatName": "外部董事",
				"userNum": 5
			},
			{
				"depatName": "移动门户测试",
				"userNum": 4
			},
			{
				"depatName": "集团公司人力资源部",
				"userNum": 1
			},
			{
				"depatName": "集团公司信息化部",
				"userNum": 2
			},
			{
				"depatName": "集团公司党群工作部",
				"userNum": 1
			},
			{
				"depatName": "集团公司办公室",
				"userNum": 4
			},
			{
				"depatName": "集团公司纪检监察办公室",
				"userNum": 3
			},
			{
				"depatName": "集团公司综合管理部",
				"userNum": 2
			},
			{
				"depatName": "集团公司财务部",
				"userNum": 2
			},
			{
				"depatName": "集团公司领导",
				"userNum": 2
			}
		],
		"departNumChar": {
			"datas": [
				5,
				4,
				1,
				2,
				1,
				4,
				3,
				2,
				2,
				2
			],
			"categories": [
				"外部董事",
				"移动门户测试",
				"集团公司人力资源部",
				"集团公司信息化部",
				"集团公司党群工作部",
				"集团公司办公室",
				"集团公司纪检监察办公室",
				"集团公司综合管理部",
				"集团公司财务部",
				"集团公司领导"
			]
		}
	}
}