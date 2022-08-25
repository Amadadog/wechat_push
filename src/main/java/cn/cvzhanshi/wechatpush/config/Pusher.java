package cn.cvzhanshi.wechatpush.config;


import cn.cvzhanshi.wechatpush.entity.Weather;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Map;

public class Pusher {

    public static void main(String[] args) {
        push();
    }
    private static String appId = "登录微信测试平台后获得";
    private static String secret = "登录微信测试平台后获得";



    public static void push(){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("")//扫描二维码关注测试号，获得的微信号
                .templateId("")//模板消息id
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        Weather weather = WeatherUtils.getWeather();
        Map<String, String> map = CaiHongPiUtils.getEnsentence();
        templateMessage.addData(new WxMpTemplateData("date","今天是" + weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("weather",weather.getText_now(),"#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "","#dcdcdc"));
        templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" ));
        templateMessage.addData(new WxMpTemplateData("address",weather.getAddress()+ "","#7cfc00" ));
        templateMessage.addData(new WxMpTemplateData("wind_class",weather.getWind_class()+ "","#42B857" ));
        templateMessage.addData(new WxMpTemplateData("wind_dir",weather.getWind_dir()+ "","#B95EA3" ));
        templateMessage.addData(new WxMpTemplateData("caihongpi",CaiHongPiUtils.getCaiHongPi(),"#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai",JiNianRiUtils.getLianAi()+"","#FF1493"));
        templateMessage.addData(new WxMpTemplateData("shengri1",JiNianRiUtils.getBirthday_Yang()+"","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("shengri2",JiNianRiUtils.getBirthday_Gao()+"","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("en",map.get("en") +"","#C71585"));
        templateMessage.addData(new WxMpTemplateData("zh",map.get("zh") +"","#C71585"));
        String beizhu = "茜茜❤强强,每天都要开心哟！";
        if(JiNianRiUtils.getLianAi() % 365 == 0){
            beizhu = "今天是恋爱" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！";
        }
        if(JiNianRiUtils.getBirthday_Yang()  == 0){
            beizhu = "今天是茜茜宝宝生日，生日快乐呀！";
        }
        if(JiNianRiUtils.getBirthday_Gao()  == 0){
            beizhu = "今天是强哥哥生日，生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
