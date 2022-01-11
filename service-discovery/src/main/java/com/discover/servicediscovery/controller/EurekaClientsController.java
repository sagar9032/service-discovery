package com.discover.servicediscovery.controller;

import com.discover.servicediscovery.model.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController @RequestMapping("/applications")
public class EurekaClientsController {

    @Autowired private PeerAwareInstanceRegistry registry;

    @GetMapping("/")
    public ResponseEntity<List<ServiceInfo>> getEurekaApplications() {
        List<Application> applications = registry.getApplications().getRegisteredApplications();
        if(applications.isEmpty()) { log.info("No applications registered.."); }
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo();
        for(Application application : applications) {
            for (InstanceInfo applicationsInstance : application.getInstances()) {
                serviceInfo.setAppName(applicationsInstance.getAppName());
                serviceInfo.setHomePageURL(applicationsInstance.getHomePageUrl());
                serviceInfo.setInstanceId(applicationsInstance.getInstanceId());
                serviceInfo.setStatus(applicationsInstance.getStatus());
                log.info("Application Details: {}", serviceInfo);
                serviceInfoList.add(serviceInfo);
                log.info(serviceInfoList.toString());
            }
        }
        return ResponseEntity.ok(serviceInfoList);
    }

}