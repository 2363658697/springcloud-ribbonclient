package cn.et;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class SendController {

    @Autowired
    private RestTemplate restTemplate;
    
    
    //测试负载均衡
  /*  @Autowired  
    private LoadBalancerClient loadBalancer;  
  
    @RequestMapping("choosePub")  
    public String choosePub() {  
        StringBuffer sb=new StringBuffer();  
        for(int i=0;i<=10;i++) {  
            ServiceInstance ss=loadBalancer.choose("SENDMAIL");//从两个sendmail中选择一个 这里涉及到选择算法  
            sb.append(ss.getUri().toString()+"<br/>");  
        }  
        return sb.toString();  
    }  */
    
    
    @GetMapping("/email")
    public String sendEmail(String send_to,String send_subject,String send_content){
        
       // String controller="/send?send_to="+send_to+"&send_subject="+send_subject+"&send_content="+send_content;
                
        //被调用的服务的访问路径
        String controller="/gsend";
        
        controller+="?send_to="+send_to+"&send_subject="+send_subject+"&send_content="+send_content;
        
        String results = restTemplate.getForObject("http://SENDMAIL"+controller, String.class);
            
        return results;
        
    }
    
    
    @PostMapping("/mail")
    public String sendMail(String send_to,String send_subject,String send_content){
        
        String results =null;
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("send_to", send_to);
            map.put("send_subject", send_subject);
            map.put("send_content", send_content);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

            results = restTemplate.postForObject("http://SENDMAIL/psend", request, String.class);
        } catch (Exception e) {
            return "发送失败";
        }
            
        return results;        
    }
    
}
