# 一 密码授权模式(recource ovner password credentials)

post请求：http://127.0.0.1:8101/oauth/token?username=user&password=123456&grant_type=password&client_id=dev&client_secret=dev

# 二 客户端授权模式(client credentials)

post请求：http://127.0.0.1:8101/oauth/token?grant_type=client_credentials&client_id=dev&client_secret=dev

# 三 授权码授权模式(authorization code)

1. 第一步获取code: http://127.0.0.1:8101/oauth/authorize?response_type=code&client_id=dev&redirect_uri=http://www.baidu.com
2. 第二步通过返回结果获取到code。比如获取到code: L2oYjv(返回会跳转到https://www.baidu.com/?code=L2oYjv 这样我们就获得了code:L2oYjv)
3. 通过code换token: post请求：127.0.0.1:8101/oauth/token?grant_type=authorization_code&code=nNpHIA&client_id=dev&client_secret=dev&redirect_uri=http://www.baidu.com

# 四 简化模式(implicit)

http://127.0.0.1:8101/oauth/authorize?response_type=token&client_id=dev&redirect_uri=http://www.baidu.com

# 五 新增一种短信验证码



# 六 刷新token

post请求：http://127.0.0.1:8101/oauth/token?grant_type=refresh_token&refresh_token=afc84284-1735-430c-bbc0-42a518fb8547&client_id=dev&client_secret=dev
