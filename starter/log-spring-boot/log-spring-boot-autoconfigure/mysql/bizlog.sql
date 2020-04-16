CREATE TABLE `bizlog` (
  `pkid` bigint(20) NOT NULL AUTO_INCREMENT,
  `modulename` varchar(100) DEFAULT NULL COMMENT '模块名称',
  `methodname` varchar(100) DEFAULT NULL COMMENT '方法名称',
  `requestip` varchar(50) DEFAULT NULL COMMENT '请求客户端IP',
  `requestparam` blob COMMENT '请求参数(会做压缩处理)',
  `responsecode` varchar(100) DEFAULT NULL COMMENT '响应结果对应的code,0代表成功',
  `loguserid` bigint(20) DEFAULT NULL COMMENT '操作用户id',
  `logusername` varchar(20) DEFAULT NULL COMMENT '操作用户名',
  `logtime` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `logcontent` varchar(1000) DEFAULT NULL COMMENT '操作详情',
  `logtype` int(10) DEFAULT '0' COMMENT '类型',
  `bizstate` int(10) DEFAULT '0' COMMENT '业务状态，因为请求成功，但是业务不一定成功(0：成功，1：失败, 2：异常)',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='业务日志';
