## 登陆合集
#### 微信登陆Demo
**1.准备工作：nginx的配置文件加入**
```$xslt
location ^~ /wxlogins {
    proxy_pass   http://localhost:9898/logins;
}
```

**2.修改本地host文件加入**
```$xslt
127.0.0.1  www.txjava.cn
```

**3.启动项目**


**4.浏览器访问http://www.txjava.cn/wxlogins/page/login.html**

**5.扫码登陆，实现自己的业务逻辑**

**备注：配置文件中的appid和密钥为www.txjava.cn此机构提供**
