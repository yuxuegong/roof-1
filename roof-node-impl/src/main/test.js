var a = {
	'templateId' : '',
	'templateContent' : '',
	'dataSourceContext' : [ {
		'name' : 'letv',
		'url' : 'jdbc:mysql://127.0.0.1:3306/node',
		'username' : 'root',
		'password' : '123'
	}, {
		'name' : 'letv1',
		'url' : 'jdbc:mysql://127.0.0.1:3306/ccc',
		'username' : 'root',
		'password' : '123'
	} ],
	'templateData' : [ {
		'key' : '123',
		'dataSourceName' : 'letv',
		'sql' : 'select * from s_dictionary where type = :type',
		'args' : {
			'type' : 'DEV_STATUS'
		}
	}, {
		'key' : '321',
		'dataSourceName' : 'letv',
		'sql' : 'select * from s_dictionary where type = :type',
		'args' : {
			'type' : 'ALARM_LEVEL'
		}
	} ],
	'fileUpload' : {
		'port' : 22,
		'uploadType' : 'SFTP',
		'remoteDirectory' : 'E:/spring-integration-samples/output',
		'hosts' : '192.168.159.149',
		'charset' : 'UTF-8',
		'remoteFileSeparator' : '',
		'user' : 'zz3310969',
		'password' : '3310969',
		'fileDirectory' : 'user/index',
		'fileName' : 'index.html',
		'operate' : 'REPLACE'
	}
}