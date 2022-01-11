package com.discover.servicediscovery.model;

import com.netflix.appinfo.InstanceInfo;
import lombok.experimental.FieldDefaults;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@FieldDefaults(level= AccessLevel.PRIVATE) @Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class ServiceInfo {
    String appName;
    String homePageURL;
    String instanceId;
    InstanceInfo.InstanceStatus status;
}