package com.xiaohuashifu.tm.config;

import com.google.gson.*;
import com.xiaohuashifu.tm.util.ftp.FTPClientTemplate;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述: 一些类库的单例
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-26
 */
@Configuration
public class SingletonBeansConfig {

    /**
     * Gson单例
     * @return Gson
     */
    @Bean
    public Gson gson() {
        // Date字符串转Date类型
        class DateAdapter implements JsonDeserializer<Date> {
            private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            public Date deserialize(JsonElement arg0, Type arg1,
                                    JsonDeserializationContext arg2) throws JsonParseException {
                try {
                    return df.parse(arg0.getAsString());
                } catch (ParseException | java.text.ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        return new GsonBuilder().registerTypeAdapter(Date.class,
                new DateAdapter()).create();
    }

    /**
     * RestTemplate单例
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        List<MediaType> mediaTypes = new ArrayList<>();
        //加入text/plain类型的支持
        mediaTypes.add(MediaType.TEXT_PLAIN);
        //加入text/html类型的支持
        mediaTypes.add(MediaType.TEXT_HTML);
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(mediaTypes);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(messageConverter);
        return restTemplate;
    }

    /**
     * FTPClientTemplate单例
     * @return FTPClientTemplate
     */
    @Bean
    public FTPClientTemplate ftpClientTemplate(@Value("${ftp.host}") String host,
                                               @Value("${ftp.username}") String username,
                                               @Value("${ftp.password}") String password) {
        return new FTPClientTemplate(host, username, password);
    }


    /**
     * dozer配置
     *
     * @return Mapper
     */
    @Bean
    public Mapper mapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }
    
    /**
     * springEL表达式解析器配置
     * @return ExpressionParser
     */
    @Bean
    public ExpressionParser expressionParser() {
    	return new SpelExpressionParser();
    }
    
    @Bean
    public EvaluationContext evaluationContext() {
        return SimpleEvaluationContext.forReadOnlyDataBinding().build();
    }

}
