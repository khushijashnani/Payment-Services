package com.balancer;

import com.balancer.rmi.LoadBalancerImpl;
import com.balancer.rmi.LoadBalancerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LoadBalancerServer {

    public static void main(String[] args) throws Exception{
        LoadBalancerImpl loadBalancer = new LoadBalancerImpl();
        LoadBalancerInterface loadBalancerStub = (LoadBalancerInterface) UnicastRemoteObject.exportObject(loadBalancer,0);
        Registry registry = LocateRegistry.createRegistry(2000);
        registry.bind("balancer", loadBalancerStub);
        System.out.println("LoadBalancer Started. ");
    }

}
