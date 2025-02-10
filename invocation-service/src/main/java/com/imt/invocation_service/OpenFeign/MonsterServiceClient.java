package com.imt.invocation_service.OpenFeign;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "monster-service")
public interface MonsterServiceClient {


}
