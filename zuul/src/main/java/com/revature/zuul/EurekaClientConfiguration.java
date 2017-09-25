package com.revature.zuul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EurekaClientConfiguration {
    
    // Get the property variable service.ip.address, otherwise localhost
    @Value("${service.ip.address:localhost}")
    private String serviceIpAddress;
    
    // Get the property variable service.port, else set it to 9999.
    @Value("${service.port:8085}")
    private Integer serverPort;
    
    // Get the property variable within.ec2, else set it to false.
    @Value("${within.ec2:false}")
    private Boolean isWithinEc2;
    
    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils utils) {
        
        EurekaInstanceConfigBean eurekaBean = new EurekaInstanceConfigBean(utils);
                
        // Should always prefer registering with an ip address
        eurekaBean.setPreferIpAddress(true);
        // Set the port where we can find this service
        eurekaBean.setNonSecurePortEnabled(true);
        eurekaBean.setNonSecurePort(serverPort);
        
        if (isWithinEc2) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                // EC2s provide a specific address where we can find our public ip address
                String hostIpAddress = restTemplate.getForObject("http://169.254.169.254/latest/meta-data/public-ipv4", String.class);
                eurekaBean.setIpAddress(hostIpAddress);
            } catch (Exception e) {
                // Service ip address unable to be set (connection timed out)
                eurekaBean.setIpAddress(serviceIpAddress);
            }
        } else {
            eurekaBean.setIpAddress(serviceIpAddress);
        }
        
        return eurekaBean;
    }
    

}
