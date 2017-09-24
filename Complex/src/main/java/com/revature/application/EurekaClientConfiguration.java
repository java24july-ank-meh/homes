package com.revature.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EurekaClientConfiguration {
	
	@Autowired
    Environment env;
    
    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils utils) {
        
        EurekaInstanceConfigBean eurekaBean = new EurekaInstanceConfigBean(utils);
       
        String isWithinContainer = env.getProperty("WITHIN_CONTAINER");
               
        if (isWithinContainer != null && isWithinContainer.equals("true")) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                String hostIpAddress = restTemplate.getForObject("http://169.254.169.254/latest/meta-data/public-ipv4", String.class);
                
                eurekaBean.setPreferIpAddress(true);
                eurekaBean.setIpAddress(hostIpAddress);
                
                eurekaBean.setNonSecurePortEnabled(true);
                eurekaBean.setNonSecurePort(Integer.parseInt(env.getProperty("SERVICE_PORT")));
            } catch (Exception e) {
                // Connection probably timed out
            }
        }
        
        return eurekaBean;
    }
    

}
