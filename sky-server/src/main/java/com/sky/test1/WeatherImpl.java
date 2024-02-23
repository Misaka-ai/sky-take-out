package com.sky.test1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sky.mapper.WeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherImpl implements WeatherService {
    private final WeMapper weMapper;

    @Override
    @Scheduled(cron = "0 * * * * ?")
    public List<Weather> getWeather() {
        String url = "https://weather.cma.cn/api/now/58321";
        try {
            //创建请求客户端
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建了一个get请求
            HttpGet httpGet = new HttpGet(url);
            // 发送 HTTP GET 请求
            CloseableHttpResponse response = httpClient.execute(httpGet);

            try {
                System.out.println(response.getStatusLine());
                HttpEntity entity1 = response.getEntity();

                String json = EntityUtils.toString(entity1);
                // 解析 JSON 数据
                JSONObject jsonObject = JSON.parseObject(json);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject now = data.getJSONObject("now");
                String temperature = now.getString("temperature");

                //获取警告信息
                JSONArray jsonArray = data.getJSONArray("alarm");

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    System.out.println("object = " + object);

                }

                //获取城市
                JSONObject location = data.getJSONObject("location");
                String city = location.getString("name");
                System.out.println("当前城市为：" + city + "温度为：" + temperature);
                weMapper.insert(city, temperature, String.valueOf(jsonArray));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weMapper.selcect();
    }




}
