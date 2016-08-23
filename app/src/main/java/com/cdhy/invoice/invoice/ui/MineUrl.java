package com.cdhy.invoice.invoice.ui;

/**
 * Created by Administrator on 2016/6/8.
 */
public class MineUrl {
    //   public static String urlHead = "http://192.168.0.123:8080/wxfp/";//接口地址前缀
    public static String urlHead = "http://www.hydzfp.com/test/wxfp/";//接口地址前缀
    public static String getMyFP = urlHead + "page/getMyFpList.do";//获取我的发票列表,'openid=' + 移动端所传特殊标识+'&type='+移动端类型(wx=微信，app=APP)+'&begin='+上次加载条数记录+'&size='+每次加载条数(不能超过10条)+'&type='移动端类型(wx=微信，app=APP)
    public static String getEntInfo = urlHead + "page/getEntInfoById.do";//'ent_uid=' + 企业用户ID+'&random_str='+请求随机字符串+'&req_enc_str='+请求密文
    public static String getFPInfo = urlHead + "page/fplist.do";
    public static String findQYUser = urlHead + "ecuser/findECUsersByQyyhid.do";//'qyyhid=' + 企业用户管理员ID+'&begin='+ 开始 + '&size='+长度
    public static String updatepasswd = urlHead + "ecuser/updatepasswd.do";//参数：'npassword=' + 新密码 +'&username='+ 用户名(手机号) + '&id='+子账户ID+'&password='+旧密码
    public static String Appurlhead = "http://www.hydzfp.com/test/wxfp/app/service.do";
    public static String newHead = "http://www.hydzfp.com/test/wxfp/app/service.do";

    public static String down = "http://www.hydzfp.com/test/wxfp/download/download.do";
    //  public static String newHead = "http://192.168.0.123:8080/wxfp/app/service.do";
    //   public static String down = "http://192.168.0.123:8080/wxfp/download/download.do";
    //   public static String uploda = "http://192.168.0.123:8080/wxfp/app/uploadCom.do";
    public static String findECUserByUsername = urlHead + "ecuser/findECUserByUsername.do";//'qyyhid=' + 企业用户管理员ID+'&username='+ 用户名(手机号)
    public static String restiPassword = urlHead + "page/resetpasswd.do";//qyyhid=' + 企业用户管理员ID+'&username='+ 用户名(手机号) + '&id='+子账户ID
    public static String addQYUser = urlHead + "ecuser/addECUser.do";//添加企业用户子账户 'qyyhid=' + 企业用户管理员ID+'&username='用户名(手机号) + '&password='+密码+'&kpzdid='+开票终端标识(可以为空)+'&zsxm='+真实姓名
    public static String delQYUser = urlHead + "ecuser/delECUser.do";//删除企业用户子账户 'id=' + 企业子用户ID+'&qyyhid='+企业用户管理员ID
    public static String upDataQYUser = urlHead + "ecuser/updateECUser.do";//'qyyhid=' + 企业用户管理员ID+'&username='+用户名(手机号) + '&password='+密码+'&kpzdid='+开票终端标识(可以为空)+'&zsxm='+真实姓名+'&kpqx='+kpqx+'&id='+id+'&disabled='+disabled
    public static String perLogin = urlHead + "page/login.do";//个人登录 username,password,openid,type
    public static String perRegister = urlHead + "page/regist.do";//个人注册 username，password
    public static String checkUser = urlHead + "page/checkuser.do";//判断用户名是否存在 username
    public static String bindTaiTou = urlHead + "page/bindFptaitou.do";//'zsxm=' + 抬头 + '&grdh=' + 个人电话 + '&grmail=' + 个人邮箱 + '&openid=' + 移动端所传特殊标识+'&type='移动端类型(wx=微信，app=APP)
    public static String getTaiTou = urlHead + "page/getFptaitou.do";//'openid=' + 移动端所传特殊标识+'&type='+移动端类型(wx=微信，app=APP)
    public static String getcardinfo = "http:/www.hydzfp.com/piaoju/other/getCardInfo.do";
    public static String newHeads = "http://www.hydzfp.com/piaoju/cdhy/service.do";
    public static String uploda = "http://www.hydzfp.com/piaoju/cdhy/uploadCom.do";

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        if(value.matches("\\d+")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 倒序输出
     * @param str
     * @return
     */
    public static String reverseSort(String str) {
        String str2 = "";
        for (int i = str.length() - 1; i > -1; i--) {
            str2 += String.valueOf(str.charAt(i));
        }
        return str2;
    }

    /**
     * 输出电话
     * @param result
     * @return
     */
    public static String num(String result){
        result = reverseSort(result);
        String num="";
        String name="";
        for (int i=0;i<=result.length();i++){
            String sub = result.substring(i,i+1);
            if(!isNumber(sub)){
                name = reverseSort(result.substring(i,result.length()));
                break;
            }
            num+=sub;
        }
        return  reverseSort(num)+"&"+name;
    }

}
