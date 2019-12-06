package overun.domain;

/**
 * 微信服务器返回的用户信息封装类
 */
public class User {

	/**
	 * 微信扫码登录后台返回的是json数据
	 * {
		"openid":"OPENID",
		"nickname":"NICKNAME",
		"sex":1,
		"province":"PROVINCE",
		"city":"CITY",
		"country":"COUNTRY",
		"headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
		"privilege":[
		"PRIVILEGE1",
		"PRIVILEGE2"
		],
		"unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
		}
	 */

	/** 用户的唯一标识 */
	private String openid;
	/** 用户的昵称 */
	private String nickname;
	/** 用户的性别 */
	private String sex;
	/** 用户所在的省份 */
	private String province;
	/** 用户所在的城市 */
	private String city;
	/** 用户所在的国家 */
	private String country;
	/** 用户的头像URL地址 */
	private String headimgurl;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@Override
	public String toString() {
		return "User [openid=" + openid + ", nickname=" + nickname + ", sex=" + sex + ", province=" + province
				+ ", city=" + city + ", country=" + country + ", headimgurl=" + headimgurl + "]";
	}
}
